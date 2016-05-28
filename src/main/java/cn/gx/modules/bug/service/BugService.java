/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.modules.bug.service;

import java.util.*;

import cn.gx.common.mail.MailSendUtils;
import cn.gx.modules.act.entity.Act;
import cn.gx.modules.act.utils.ProcessDefCache;
import cn.gx.modules.bug.bean.TaskCount;
import cn.gx.modules.bug.dao.BugProjectDao;
import cn.gx.modules.bug.entity.BugProject;
import cn.gx.modules.bug.exception.NoSuchUserTaskRuntimeException;
import cn.gx.modules.oa.entity.OaNotify;
import cn.gx.modules.oa.service.OaNotifyService;
import cn.gx.modules.sys.dao.UserDao;
import cn.gx.modules.sys.entity.SystemConfig;
import cn.gx.modules.sys.entity.User;
import cn.gx.modules.sys.service.SystemConfigService;
import cn.gx.modules.sys.utils.DictUtils;
import cn.gx.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.gx.common.utils.StringUtils;
import cn.gx.modules.act.service.ActTaskService;
import cn.gx.modules.act.utils.ActUtils;
import cn.gx.modules.bug.util.BugStatus;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.NativeExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.explorer.util.StringUtil;
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

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private HistoryService historyService;


	@Autowired
	private OaNotifyService oaNotifyService;


	@Autowired
	private SystemConfigService systemConfigService;


	@Autowired
	private UserDao userDao;

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


	public Page<Bug> historicPage(Page<Bug> page, Bug bug) {
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());

		Act act=bug.getAct();
		HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).finished()
				.includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();

		// 设置查询条件
		if (StringUtils.isNotBlank(act.getProcDefKey())){
			histTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getBeginDate() != null){
			histTaskQuery.taskCompletedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			histTaskQuery.taskCompletedBefore(act.getEndDate());
		}

		// 查询总数
		//page.setCount(histTaskQuery.count());

		// 查询列表
		List<HistoricTaskInstance> histList = histTaskQuery.listPage(page.getFirstResult(), page.getMaxResults());
		//处理分页问题
		List<Act> actList= Lists.newArrayList();
		List<Bug> resultList= new ArrayList<Bug>();
		for (HistoricTaskInstance histTask : histList) {


			String processInstanceId = histTask.getProcessInstanceId();
			Bug dbBug = dao.getByProcInsId(processInstanceId);
			if(dbBug!=null){
				Act e = new Act();
				actList.add(e);
				e.setHistTask(histTask);
				e.setVars(histTask.getProcessVariables());
				e.setProcDef(ProcessDefCache.get(histTask.getProcessDefinitionId()));
				e.setStatus("finish");// TODO
				dbBug.setAct(e);
				resultList.add(dbBug);
			}

		}
		page.setCount(resultList.size());
		page.setList(resultList);
		return page;
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



	/**
	 * 流程定义对象缓存
	 *
	 * @param definitionMap
	 * @param processDefinitionId
	 *
	 *
	 */
	private void definitionCache(Map<String, ProcessDefinition> definitionMap, String processDefinitionId) {
		if (definitionMap.get(processDefinitionId) == null) {
			ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
			processDefinitionQuery.processDefinitionId(processDefinitionId);
			ProcessDefinition processDefinition = processDefinitionQuery.singleResult();

			// 放入缓存
			definitionMap.put(processDefinitionId, processDefinition);
		}
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

		Act act = bug.getAct();

		String currentStatus=bug.getBugStatus();

		bug.preUpdate();
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = act.getTaskDefKey();

		if ("developer_task".equals(taskDefKey)){
			if ("FIXED".equals(currentStatus)){
				dao.solutionBug(bug);
			}else{
				logger.debug("什么原因.....");
			}

		}
		// 要不要分开处理啊
		else if ("tester_task".equals(taskDefKey)){
			dao.completeBugTask(bug);
		}else if("poster_task".equals(taskDefKey)){
			dao.completeBugTask(bug);
		}else if ("testerLead_task".equals(taskDefKey)){
			dao.completeBugTask(bug);
		}else if ("projectManager_task".equals(taskDefKey)){
			dao.completeBugTask(bug);
		}else if ("developerLead_task".equals(taskDefKey)){
			throw new NoSuchUserTaskRuntimeException(taskDefKey);
		}

		// 更新 activiti 记录
		String statusLabel = DictUtils.getDictLabel(currentStatus, "bug_status", "通过");
		// 设置意见
		act.setComment(statusLabel+act.getComment());
		//dao.update(bug);
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("status", currentStatus);
		actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(), vars);

		//下一个节点进行签收
		// 刚才的任务到达下一组
		Task task = taskService.createTaskQuery().processInstanceId(act.getProcInsId()).taskCandidateUser(bug.getAssign()).singleResult();
		if(task!=null){
			actTaskService.claim(task.getId(), bug.getAssign());

			User user=new User();
			user.setLoginName(bug.getAssign());
			user = userDao.getByLoginName(user);
			String address=user.getEmail();
			// 邮件通知
			if (user.getOpenEmail()==1&&StringUtils.isNotEmpty(address)){
				SystemConfig config = systemConfigService.get("1");
				String title="Bug 任务";
				String html="<html>\n" +
						"<body>\n" +
						"  你好 %s,<br/><br/>\n" +
						"  <p>您有项目为「%s」的 Bug 任务要进行处理</p>\n" +
						"  <hr/>\n" +
						"</body>\n" +
						"</html>";
				String content = String.format(html, user.getName(), bug.getName());
				boolean issuccess = MailSendUtils.sendEmail(config.getSmtp(), config.getPort(), config.getMailName(), config.getMailPassword(), address, title, content, "0");
				System.out.println("邮件发送:"+issuccess);

			}
			// 站内通知
			if(user.getOpenNotify()==1){

				OaNotify oaNotify=new OaNotify();

				oaNotify.setTitle("Bug 任务");
				String str="您好%s,您有项目为「%s」的 Bug 任务要进行处理";
				String content= String.format(str,user.getName(),bug.getName());
				oaNotify.setContent(content);
				oaNotify.setStatus("1");
				oaNotify.setOaNotifyRecordIds(user.getId());
				oaNotifyService.save(oaNotify);
			}

		}

	}

	public List<User> findNextTaskUserList(String proInstId, String projectId, TaskDefinition taskDefinition) {
		List<User> userList=new ArrayList<User>();
		Expression assigneeExpression = taskDefinition.getAssigneeExpression();

		if (assigneeExpression!=null){
			String applyIdEL = assigneeExpression.getExpressionText();

			if (StringUtils.contains(applyIdEL,"apply")){//如果是启动人的
				User user=this.getApplyUser(proInstId);
				userList.add(user);
			}else{
				logger.debug("直接是分配的用户....类型太多了....以后考虑吧........");
			}


		}

		Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();

		//Map<String,List<User>> groupMap=new HashMap<String, List<User>>();

		for (Expression e: candidateGroupIdExpressions) {
			String enname = e.getExpressionText();// group

			List<User> groupUserList=userDao.findUserListByRoleEnname(projectId,enname);
			userList.addAll(groupUserList);
			//groupMap.put(group,groupUserList);

		}
		return userList;
	}


	public TaskCount totalTaskCount(String projectId) {

		String taskUserId=UserUtils.getUser().getLoginName();
		String userId=UserUtils.getUser().getId();

		Bug bug=new Bug();
		bug.setBugProject(new BugProject(projectId));
		// projectId 的所有bug
		List<Bug> bugList = dao.findList(bug);

		int joinCount=0;
		int todoCount=0;
		int applyCount=0;

		for (Bug dbBug :bugList) {


			if (taskUserId.equals(dbBug.getAssign())){
				todoCount++;
			}

			if (userId.equals(dbBug.getCreateBy())){
				applyCount++;
			}


		}

		// 简单的做法
		NativeExecutionQuery nativeExecutionQuery = runtimeService.createNativeExecutionQuery();

		String sql = "select distinct * from "
				+ "(select RES.* from ACT_RU_EXECUTION RES "
				+ " left join ACT_HI_TASKINST ART "
				+ " on ART.PROC_INST_ID_ = RES.PROC_INST_ID_ "
				+ " left join ACT_HI_PROCINST AHP on AHP.PROC_INST_ID_ = RES.PROC_INST_ID_  "
				+ " left join bug b on b.proc_ins_id=ART.PROC_INST_ID_"//qu
				+ " where b.bug_project_id= #{projectId}"
				+ " and  SUSPENSION_STATE_ = '1' "
				+ " and (ART.ASSIGNEE_ = #{taskUserId} or AHP.START_USER_ID_ = #{taskUserId}) "
				+ " and ACT_ID_ is not null "
				+ " and IS_ACTIVE_ = '1' "
				+ " order by ART.START_TIME_ desc"
				+ ") as r";
		nativeExecutionQuery.parameter("taskUserId",taskUserId);
		nativeExecutionQuery.parameter("projectId",projectId);

		joinCount = (int) nativeExecutionQuery.sql("select count(*) from (" + sql + ") as a").count();


		return new TaskCount(todoCount,joinCount,applyCount);
	}
}