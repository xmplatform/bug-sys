/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.modules.bug.web;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gx.common.utils.CacheUtils;
import cn.gx.modules.act.entity.Act;
import cn.gx.modules.act.service.ActTaskService;
import cn.gx.modules.act.utils.ActPage;
import cn.gx.modules.act.utils.PageUtil;
import cn.gx.modules.act.utils.ProcessDefCache;
import cn.gx.modules.bug.bean.Charts;
import cn.gx.modules.bug.bean.Json;
import cn.gx.modules.bug.entity.BugProject;
import cn.gx.modules.bug.exception.NoUserTaskToEndEventException;
import cn.gx.modules.bug.service.BugProjectService;
import cn.gx.modules.bug.util.BugStatus;
import cn.gx.modules.sys.entity.User;
import cn.gx.modules.sys.service.SystemService;
import cn.gx.modules.sys.utils.UserUtils;
import org.activiti.engine.*;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.NativeExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ModelAndViewMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import cn.gx.common.utils.DateUtils;
import cn.gx.common.utils.MyBeanUtils;
import cn.gx.common.config.Global;
import cn.gx.common.persistence.Page;
import cn.gx.common.web.BaseController;
import cn.gx.common.utils.StringUtils;
import cn.gx.common.utils.excel.ExportExcel;
import cn.gx.common.utils.excel.ImportExcel;
import cn.gx.modules.bug.entity.Bug;
import cn.gx.modules.bug.service.BugService;

/**
 * 缺陷Controller
 * @author xinguan
 * @version 2016-05-04
 */
@Controller
@RequestMapping(value = "${adminPath}/bug/bug")
public class BugController extends BaseController {

	@Autowired
	private BugService bugService;


	@Autowired
	private BugProjectService bugProjectService;

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private HistoryService historyService;

	
	@ModelAttribute
	public Bug get(@RequestParam(required=false) String id) {
		Bug entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bugService.get(id);
		}
		if (entity == null){
			entity = new Bug();
		}
		return entity;
	}
	
	/**
	 * 缺陷列表页面
	 */
	@RequiresPermissions("bug:bug:list")
	@RequestMapping(value = {"list", ""})
	public String list(Bug bug, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Bug> page = bugService.findPage(new Page<Bug>(request, response), bug);
		model.addAttribute("page", page);
		return "modules/bug/bugList";
	}


	/**
	 * 草稿箱列表页面
	 */
	@RequiresPermissions("bug:bug:task")
	@RequestMapping(value = "draft")
	public String draftList(Bug bug, HttpServletRequest request, HttpServletResponse response, Model model) {

		bug.setSelf(true);
		bug.setBugStatus("DRAFT");//草稿
		Page<Bug> page = bugService.findPage(new Page<Bug>(request, response), bug);
		model.addAttribute("page", page);
		return "modules/bug/bugDraftList";
	}




	@RequestMapping(value = "statusList")
	public String statusList(String projectId,String statusPhrase,HttpServletRequest request,HttpServletResponse response,Model model){

		Bug bug=new Bug();
		bug.setBugProject(new BugProject(projectId));
		bug.setBugStatus(BugStatus.statusOf(statusPhrase).getStatus());
		Page<Bug> page = bugService.findPage(new Page<Bug>(request, response), bug);
		model.addAttribute("page", page);
		return "modules/bug/bugList";
	}

	/**
	 * 查看，增加，编辑缺陷表单页面
	 *
	 * 申请单填写
	 */
	@RequiresPermissions(value={"bug:bug:view","bug:bug:add","bug:bug:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Bug bug, Model model) {



		String view="bugForm";

		if(StringUtils.isBlank(bug.getId())){
			bug.setBugStatus(BugStatus.NEW.getStatus());
		}else{

			// 任务编号
			String taskDefKey=bug.getAct().getTaskDefKey();
			//bug.setBugStatus(null);

			// 测试主管审核不通过,重新提交
			if ("posterTask".equals(taskDefKey)){
				view="bugForm";
			}else {
				view="bugAudit";
			}

			// bug 关闭
			if (bug.getAct().isFinishTask()){
				view="bugView";
			}
		}


		model.addAttribute("bug", bug);
		return "modules/bug/"+view;
	}





	/**
	 * 保存缺陷
	 */
	@RequiresPermissions(value={"bug:bug:add","bug:bug:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Bug bug, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, bug)){
			return form(bug, model);
		}
		if(!bug.getIsNewRecord()){//编辑表单保存
			Bug t = bugService.get(bug.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(bug, t);//将编辑表单中的非NULL值覆盖数据库记录中的值

			bugService.save(t);//保存
		}else{//新增表单保存
			bug.setUpdateDate(new Date());
			bugService.save(bug);//保存
		}
		addMessage(redirectAttributes, "提交缺陷'" + bug.getName() + "'成功");
		//return "redirect:"+Global.getAdminPath()+"/bug/bug/?repage";
		return "redirect:" + adminPath + "/act/task/todo/";//到我提交的
	}

	/**
	 * 缺陷执行（完成任务）
	 * @param bug
	 * @param model
	 * @return
	 */
	@RequiresPermissions("bug:bug:edit")
	@RequestMapping(value = "saveAudit")
	public String saveAudit(Bug bug, Model model) throws Exception {
		if (StringUtils.isBlank(bug.getBugStatus())
				||StringUtils.isBlank(bug.getAct().getComment())){
			addMessage(model, "请填写审核意见。");
			return form(bug, model);
		}
		Bug t = bugService.get(bug.getId());//从数据库取出记录的值
		MyBeanUtils.copyBeanNotNull2Bean(bug, t);//将编辑表单中的非NULL值覆盖数据库记录中的值

		bugService.auditSave(bug);
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	/**
	 * 删除缺陷
	 */
	@RequiresPermissions("bug:bug:del")
	@RequestMapping(value = "delete")
	public String delete(Bug bug, RedirectAttributes redirectAttributes) {
		bugService.delete(bug);
		addMessage(redirectAttributes, "删除缺陷成功");
		return "redirect:"+Global.getAdminPath()+"/bug/bug/?repage";
	}
	
	/**
	 * 批量删除缺陷
	 */
	@RequiresPermissions("bug:bug:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			bugService.delete(bugService.get(id));
		}
		addMessage(redirectAttributes, "删除缺陷成功");
		return "redirect:"+Global.getAdminPath()+"/bug/bug/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("bug:bug:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Bug bug, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "缺陷"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Bug> page = bugService.findPage(new Page<Bug>(request, response, -1), bug);
    		new ExportExcel("缺陷", Bug.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出缺陷记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bug/bug/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("bug:bug:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Bug> list = ei.getDataList(Bug.class);
			for (Bug bug : list){
				bugService.save(bug);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条缺陷记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入缺陷失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bug/bug/?repage";
    }
	
	/**
	 * 下载导入缺陷数据模板
	 */
	@RequiresPermissions("bug:bug:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "缺陷数据导入模板.xlsx";
    		List<Bug> list = Lists.newArrayList(); 
    		new ExportExcel("缺陷数据", Bug.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bug/bug/?repage";
    }




	/**
	 * 获取待办列表
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	@RequestMapping(value = "task/todo")
	@RequiresPermissions("bug:bug:task")
	public String todoList(Bug bug,Act act,HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

		bug.setAct(act);
		Page<Bug> page = bugService.todoPage(new Page<Bug>(request,response),bug);
		model.addAttribute("page", page);

		return "modules/bug/bugTaskTodoList";
	}

	/**
	 * 获取已办任务
	 * @param page
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	@RequestMapping(value = "task/historic")
	@RequiresPermissions("bug:bug:task")
	public String historicList(Bug bug,Act act, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		bug.setAct(act);
		Page<Bug> page = bugService.historicPage(new Page<Bug>(request,response), bug);
		model.addAttribute("page", page);

		return "modules/bug/bugTaskHistoricList";
	}



	// TODO
	@RequestMapping(value = "task/join")
	public String joinList(Bug bug,HttpServletRequest request,HttpServletResponse response,Model model){
//
//		//ModelAndView mav = new ModelAndView("modules/bug/bugTaskJoinList");//"modules/bug/bug_"+taskDefKey;
//	    /* 标准查询
//	    List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().list();
//	    List<Execution> list = runtimeService.createExecutionQuery().list();
//	    mav.addObject("list", list);
//	    */
//
//
//
//
//		User user = UserUtils.getUser();
//
//		Page<Bug> page = new Page<Bug>(request, response);
//
//		//查询指定用户参与的流程信息 （流程历史  用户参与 ）
//		List<HistoricProcessInstance> historicProcessInstances = historyService
//				.createHistoricProcessInstanceQuery().involvedUser(user.getLoginName())
//				.orderByProcessInstanceStartTime().desc().listPage(page.getFirstResult(), page.getMaxResults());
//		//封装成bug
//		List<Bug> result=new ArrayList<Bug>();
//
//		for (HistoricProcessInstance hpi:historicProcessInstances){
//			String processInstanceId = hpi.getId();
//			String processDefinitionId = hpi.getProcessDefinitionId();
//			ProcessDefinition processDefinition = ProcessDefCache.get(processDefinitionId);
//			Map<String, Object> processVariables = hpi.getProcessVariables();
//			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
//
//			Bug dbBug = bugService.getByProcInsId(processInstanceId);
//
//
//			Act e = new Act();
//			e.setVars(processVariables);
//			e.setProcDef(processDefinition);//processDefinition
//			e.setTask(task);//task
//			e.setStatus("todo");
//
//			bug.setAct(e);
//			result.add(dbBug);
//		}
//
//
//		page.setList(bugList);
//		page.setCount(result.size());
//		model.addAttribute("page", page);

		return "modules/bug/bugTaskJoinList";
	}





	/**
	 *  提交问题单
	 * @param bug
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="apply")
	@RequiresPermissions("bug:bug:task")
	public String createForm(Bug bug,Model model){

		BugProject bugProject=new BugProject();
		bugProject.setSelf(true);//自己参与的项目
		bugProject.setActive(true);//有流程的项目
		List<BugProject> bugProjectList = bugProjectService.findList(bugProject);
		model.addAttribute("bug", bug);
		model.addAttribute("bugProjectList", bugProjectList);
		return "modules/bug/bug_apply";
	}

	/**
	 * 到达处理 bug 页面
	 *
	 * @param bug
	 */

	@RequestMapping(value = "task/view/{taskId}")
	@RequiresPermissions("bug:bug:task")
	public String showTaskView(@PathVariable("taskId") String taskId,Bug bug,Model model) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String taskName = task.getName();
		String taskDefKey = task.getTaskDefinitionKey();
		String procDefId = task.getProcessDefinitionId();
		String procInsId=task.getProcessInstanceId();

		Act act=new Act();
		act.setTaskId(taskId);
		act.setTaskName(taskName);
		act.setTaskDefKey(taskDefKey);
		act.setProcDefId(procDefId);
		act.setProcInsId(procInsId);

		bug.setAct(act);
		model.addAttribute("bug", bug);
		return "modules/bug/bug_"+taskDefKey;
	}



	/**
	 * 启动流程,提交 bug 单
	 */
	@RequestMapping(value = "start", method = RequestMethod.POST)
	@RequiresPermissions("bug:bug:task")
	public String  start(Bug bug,HttpServletRequest request,RedirectAttributes redirectAttributes){
		try {

			bugService.save(bug);
			if ("DRAFT".equals(bug.getBugStatus())){// 草稿只保存,退出
				addMessage(redirectAttributes,"message", "问题单草稿已保存");
			}else{
				Map<String, Object> variables = new HashMap<String, Object>();
				bugService.startWorkflow(bug,request.getContextPath());// + processInstance.getId()
				addMessage(redirectAttributes,"message", "问题单提交成功,流程启动");
			}


		} catch (ActivitiException e) {
			if (e.getMessage().indexOf("no processes deployed with key") != -1) {
				logger.warn("没有部署流程!", e);
				addMessage(redirectAttributes,"error", "没有部署请假流程");
			} else {
				logger.error("启动请假流程失败：", e);
				addMessage(redirectAttributes,"error","系统内部错误！");
			}
		} catch (Exception e) {
			logger.error("启动请假流程失败：", e);
			addMessage(redirectAttributes,"error", "系统内部错误！");
		}
		return "redirect:"+Global.getAdminPath()+"/bug/bug/apply";
	}




	/**
	 * 根据当前任务条件,选择下一个节点任务中,用户组分配信息.
	 * @param proInstId
	 * @param status
	 * @param projectId
     * @return
     */
	@RequestMapping(value = "task/next")
	@ResponseBody
	public Json getNextTaskGroup(String proInstId, String status,String projectId){

		Json json=new Json();
		try {

			String elString = String.format("${status.equals('%s')}", status);

			TaskDefinition taskDefinition = actTaskService.nextTaskDefinition(proInstId, elString);

			List<User> userList = bugService.findNextTaskUserList(proInstId, projectId, taskDefinition);
			json.setMsg("加载下一个节点成员成功");
			json.setData(userList);
			json.setSuccess(true);

		}catch (NoUserTaskToEndEventException e){//流程结束.
			json.setMsg(e.getMessage());
		}catch (Exception e){
			json.setMsg(e.getMessage());
		}

		return json;
	}
/**
	 * 根据当前任务条件,选择下一个节点任务中,用户组分配信息.
	 * @param proInstId
	 * @param status
	 * @param projectId
     * @return
     */
	@RequestMapping(value = "task/startNext")
	@ResponseBody
	public Json getStartEventNextTaskGroup(String projectId,String status){

		Json json=new Json();
		try {

			String elString = String.format("${status.equals('%s')}", status);

			BugProject bugProject = bugProjectService.get(projectId);
			TaskDefinition taskDefinition = actTaskService.nextStartEvent(bugProject.getProcessKey(),elString);

			List<User> userList=new ArrayList<User>();
			Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
			for (Expression e: candidateGroupIdExpressions) {
				String group = e.getExpressionText();// group
				List<User> groupUserList=bugProjectService.findUserListByRole(projectId,group);
				userList.addAll(groupUserList);
			}
			json.setMsg("加载下一个节点成员成功");
			json.setData(userList);
			json.setSuccess(true);

		}catch (NoUserTaskToEndEventException e){//流程结束.
			json.setMsg(e.getMessage());
		}catch (Exception e){
			json.setMsg(e.getMessage());
		}

		return json;
	}



	/**
	 * 缺陷执行（完成任务）
	 * TODO 授权也改一下
	 * @param bug
	 * @param model
	 * @return
	 */
	@RequiresPermissions("bug:bug:task")
	@RequestMapping(value = "completeTask")
	public String completeTask(Bug bug, Model model,HttpServletRequest request){
		if (!beanValidator(model, bug)){
			return form(bug, model);
		}
		bugService.completeBugTask(bug,request.getContextPath());
		return "redirect:" + adminPath + "/bug/bug/task/todo/";
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

	

}