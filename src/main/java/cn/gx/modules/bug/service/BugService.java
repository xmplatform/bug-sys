/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.modules.bug.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gx.modules.act.entity.Act;
import cn.gx.modules.act.utils.ProcessDefCache;
import cn.gx.modules.bug.dao.BugProjectDao;
import cn.gx.modules.bug.entity.BugProject;
import cn.gx.modules.sys.entity.User;
import cn.gx.modules.sys.utils.DictUtils;
import cn.gx.modules.sys.utils.UserUtils;
import com.google.common.collect.Maps;
import cn.gx.common.utils.StringUtils;
import cn.gx.modules.act.service.ActTaskService;
import cn.gx.modules.act.utils.ActUtils;
import cn.gx.modules.bug.util.BugStatus;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gx.common.persistence.Page;
import cn.gx.common.service.CrudService;
import cn.gx.modules.bug.entity.Bug;
import cn.gx.modules.bug.dao.BugDao;

/**
 * 缺陷Service
 * @author xinguan
 * @version 2016-05-04
 */
@Service
@Transactional(readOnly = true)
public class BugService extends CrudService<BugDao, Bug> {



	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private BugProjectDao bugProjectDao;

	public Bug getByProcInsId(String procInsId) {
		return dao.getByProcInsId(procInsId);
	}

	public Page<Bug> findPage(Page<Bug> page, Bug bug) {
		return super.findPage(page, bug);
	}







	/**
	 * 审核新增或编辑
	 * @param bug
	 *
	 */
	@Transactional(readOnly = false)
	public void save(Bug bug) {


		/**
		 * 配置多流程 则要在这边进行更改 TODO
		 */
		BugProject bugProject = bug.getBugProject();
		String processKey=bugProjectDao.getProcessKey(bugProject.getId());

		//申请发起
		if (StringUtils.isBlank(bug.getId())){
			bug.preInsert();
			dao.insert(bug);
			// 启动流程
			Map vars=new HashMap();
			vars.put("status",bug.getBugStatus());
			String procInsId = actTaskService.startProcess(processKey, "bug", bug.getId(), "", vars);// 和流程建立关联关系

			// 刚才的任务到达下一组
			Task task = taskService.createTaskQuery().processInstanceId(procInsId).taskCandidateUser(bug.getAssign()).singleResult();
			if(task!=null){
				actTaskService.claim(task.getId(), bug.getAssign());

			}

		}
		//重新编辑
		else{
			bug.preUpdate();
			dao.update(bug);
			bug.getAct().setComment(BugStatus.reasonPhraseOf(bug.getBugStatus())+bug.getAct().getComment());

			// 完成任务流程
			Map<String,Object> vars= Maps.newHashMap();
			vars.put("status",bug.getBugStatus());

			actTaskService.complete(bug.getAct().getTaskId(), bug.getAct().getProcInsId(), bug.getAct().getComment(), bug.getSummary(),null);
		}

		super.save(bug);
	}

	/**
	 * 审核审批保存
	 * @param bug
	 */
	@Transactional(readOnly = false)
	public void auditSave(Bug bug) {

		String currentStatus=bug.getBugStatus();

		// 设置意见
		bug.getAct().setComment(BugStatus.reasonPhraseOf(currentStatus)+bug.getAct().getComment());

		bug.preUpdate();

		dao.update(bug);

		// 对不同环节的业务逻辑进行操作
		String taskDefKey = bug.getAct().getTaskDefKey();

//		// 审核环节
//		if ("audit".equals(taskDefKey)){
//
//		}
//		else if ("testerLead_task".equals(taskDefKey)){
//			bug.setTesterLeadText(bug.getAct().getComment());
//			dao.updateTesterLeadText(bug);
//		}
//		else if ("developerLead_task".equals(taskDefKey)){
//			bug.setDeveloperLeadText(bug.getAct().getComment());
//			dao.updateDeveloperLeadText(bug);
//		}
//		else if ("projectManager_task".equals(taskDefKey)){
//			bug.setProjectManager(bug.getAct().getComment());
//			dao.updateProjectManagerText(bug);
//		}
//		else if ("apply_end".equals(taskDefKey)){
//
//		}
//
//		// 未知环节，直接返回
//		else{
//			return;
//		}

		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("status", bug.getBugStatus());
		actTaskService.complete(bug.getAct().getTaskId(), bug.getAct().getProcInsId(), bug.getAct().getComment(), vars);

	}



	public Bug get(String id) {
		return super.get(id);
	}

	public List<Bug> findList(Bug bug) {
		return super.findList(bug);
	}
	
	@Transactional(readOnly = false)
	public void delete(Bug bug) {
		super.delete(bug);
	}

	/**
	 * 查询待办任务
	 */
	@Transactional(readOnly = true)
	public List<Bug> todoList(Act act) {
		String userId = UserUtils.getUser().getLoginName();



		List<Bug> result = new ArrayList<Bug>();

		// 根据当前人的ID查询
		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
				.includeProcessVariables().orderByTaskCreateTime().desc();

		// 设置查询条件
		if (StringUtils.isNotBlank(act.getProcDefKey())){
			todoTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getBeginDate() != null){
			todoTaskQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			todoTaskQuery.taskCreatedBefore(act.getEndDate());
		}

		// 查询列表
		List<Task> todoList = todoTaskQuery.list();
		for (Task task : todoList) {

			String processInstanceId = task.getProcessInstanceId();
			String processDefinitionId = task.getProcessDefinitionId();
			ProcessDefinition processDefinition = ProcessDefCache.get(processDefinitionId);
			Map<String, Object> processVariables = task.getProcessVariables();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			Bug bug = dao.getByProcInsId(processInstanceId);


			Act e = new Act();
			e.setVars(processVariables);
			e.setProcDef(processDefinition);//processDefinition
			e.setTask(task);//task
			e.setStatus("todo");

			bug.setAct(e);
			result.add(bug);
		}


		// =============== 等待签收的任务  ===============
		TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId)
				.includeProcessVariables().active().orderByTaskCreateTime().desc();

		// 设置查询条件
		if (StringUtils.isNotBlank(act.getProcDefKey())){
			toClaimQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getBeginDate() != null){
			toClaimQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			toClaimQuery.taskCreatedBefore(act.getEndDate());
		}

		// 查询列表
		List<Task> toClaimList = toClaimQuery.list();
		for (Task task : toClaimList) {
			String processInstanceId = task.getProcessInstanceId();
			String processDefinitionId = task.getProcessDefinitionId();
			ProcessDefinition processDefinition = ProcessDefCache.get(processDefinitionId);
			Map<String, Object> processVariables = task.getProcessVariables();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			Bug bug = dao.getByProcInsId(processInstanceId);


			Act e = new Act();
			e.setVars(processVariables);
			e.setProcDef(processDefinition);//processDefinition
			e.setTask(task);//task
			e.setStatus("claim");
			result.add(bug);
		}


		return result;
	}

	public  Page<Bug> todoPage(Page<Bug> page, Bug bug) {
		bug.setPage(page);
		List<Bug> bugs = this.todoList(bug.getAct());
		page.setList(bugs);
		return page;
	}

	public void startWorkflow(Bug bug) {
		this.save(bug);
	}

	public User getApplyUser(String procInsId) {

		return dao.getApplyUser(procInsId);
	}
	@Transactional(readOnly = false)
	public void completeBugTask(Bug bug) {

		String currentStatus=bug.getBugStatus();
		String statusLabel = DictUtils.getDictLabel(currentStatus, "bug_status", "通过");

		Act act = bug.getAct();

		// 设置意见
		act.setComment(statusLabel+act.getComment());

		bug.preUpdate();
		dao.completeBugTask(bug);
		//dao.update(bug);

		// 对不同环节的业务逻辑进行操作
		String taskDefKey = act.getTaskDefKey();


		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("status", currentStatus);
		actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(), vars);

		//下一个节点进行签收
		// 刚才的任务到达下一组
		Task task = taskService.createTaskQuery().processInstanceId(act.getProcInsId()).taskCandidateUser(bug.getAssign()).singleResult();
		if(task!=null){
			actTaskService.claim(task.getId(), bug.getAssign());

		}

	}
}