/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.modules.bug.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gx.common.utils.Collections3;
import cn.gx.modules.act.entity.Act;
import cn.gx.modules.act.service.ActProcessService;
import cn.gx.modules.act.service.ActTaskService;
import cn.gx.modules.bug.bean.Json;
import cn.gx.modules.bug.bean.StatusBug;
import cn.gx.modules.bug.bean.TaskCount;
import cn.gx.modules.bug.entity.Bug;
import cn.gx.modules.bug.entity.BugVersion;
import cn.gx.modules.bug.bean.Charts;
import cn.gx.modules.bug.service.BugService;
import cn.gx.modules.sys.entity.User;
import cn.gx.modules.sys.service.OfficeService;
import cn.gx.modules.sys.service.SystemService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.task.TaskDefinition;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
import cn.gx.modules.bug.entity.BugProject;
import cn.gx.modules.bug.service.BugProjectService;

/**
 * 缺陷所属的项目Controller
 * @author xinguan
 * @version 2016-04-30
 */
@Controller
@RequestMapping(value = "${adminPath}/bug/bugProject")
public class BugProjectController extends BaseController {

	@Autowired
	private BugProjectService bugProjectService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private ActProcessService actProcessService;

	@Autowired
	private BugService bugService;

	@ModelAttribute
	public BugProject get(@RequestParam(required=false) String id) {
		BugProject entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bugProjectService.get(id);
		}
		if (entity == null){
			entity = new BugProject();
		}
		return entity;
	}



	/**
	 * 项目列表页面
	 */
	@RequiresPermissions("bug:bugProject:list")
	@RequestMapping(value = {"list", ""})
	public String list(BugProject bugProject, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BugProject> page = bugProjectService.findPage(new Page<BugProject>(request, response), bugProject);
		model.addAttribute("page", page);
		return "modules/bug/bugProjectList";
	}



	/**
	 * 查看，增加，编辑项目表单页面
	 */
	@RequiresPermissions(value={"bug:bugProject:view","bug:bugProject:add","bug:bugProject:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(BugProject bugProject, Model model) {

		model.addAttribute("processList",actProcessService.processList());
		model.addAttribute("bugProject", bugProject);
		return "modules/bug/bugProjectForm";
	}

	/**
	 * 保存项目
	 */
	@RequiresPermissions(value={"bug:bugProject:add","bug:bugProject:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(BugProject bugProject, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, bugProject)){
			return form(bugProject, model);
		}
		if(!bugProject.getIsNewRecord()){//编辑表单保存
			BugProject t = bugProjectService.get(bugProject.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(bugProject, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			bugProjectService.save(t);//保存
		}else{//新增表单保存
			bugProjectService.save(bugProject);//保存
		}
		addMessage(redirectAttributes, "保存项目成功");
		return "redirect:"+Global.getAdminPath()+"/bug/bugProject/?repage";
	}

	/**
	 * 删除项目
	 */
	@RequiresPermissions("bug:bugProject:del")
	@RequestMapping(value = "delete")
	public String delete(BugProject bugProject, RedirectAttributes redirectAttributes) {
		bugProjectService.delete(bugProject);
		addMessage(redirectAttributes, "删除项目成功");
		return "redirect:"+Global.getAdminPath()+"/bug/bugProject/?repage";
	}

	/**
	 * 批量删除项目
	 */
	@RequiresPermissions("bug:bugProject:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			bugProjectService.delete(bugProjectService.get(id));
		}
		addMessage(redirectAttributes, "删除项目成功");
		return "redirect:"+Global.getAdminPath()+"/bug/bugProject/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("bug:bugProject:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(BugProject bugProject, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "项目"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BugProject> page = bugProjectService.findPage(new Page<BugProject>(request, response, -1), bugProject);
    		new ExportExcel("项目", BugProject.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出项目记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bug/bugProject/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("bug:bugProject:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BugProject> list = ei.getDataList(BugProject.class);
			for (BugProject bugProject : list){
				bugProjectService.save(bugProject);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条项目记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入项目失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bug/bugProject/?repage";
    }

	/**
	 * 下载导入项目数据模板
	 */
	@RequiresPermissions("bug:bugProject:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "项目数据导入模板.xlsx";
    		List<BugProject> list = Lists.newArrayList();
    		new ExportExcel("项目数据", BugProject.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/bug/bugProject/?repage";
    }

	/**
	 * 分配用户:给指定项目分配用户的页面
	 *
	 * @param bugProject
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:assign")
	@RequestMapping(value = "assign")
	public String assign(BugProject bugProject, Model model) {
		List<User> userList = systemService.findUser(new User(new BugProject(bugProject.getId())));
		model.addAttribute("userList", userList);
		return "modules/bug/bugProjectAssign";
	}


	/**
	 * 分配用户 -- 从项目中移除用户
	 * @param userId
	 * @param projectId
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:assign")
	@RequestMapping(value = "outUser")
	public String outUser(String userId, String projectId, RedirectAttributes redirectAttributes) {

		BugProject bugProject = bugProjectService.get(projectId);
		User user = systemService.getUser(userId);

		Boolean flag = systemService.outUserInBugProject(projectId, userId);
		if (!flag) {
			addMessage(redirectAttributes, "用户【" + user.getName() + "】从项目【" + bugProject.getName() + "】中移除失败！");
		}else {
			addMessage(redirectAttributes, "用户【" + user.getName() + "】从项目【" + bugProject.getName() + "】中移除成功！");
		}
		return "redirect:" + adminPath + "/bug/bugProject/assign?id="+bugProject.getId();
	}

	/**
	 *
	 * 选择用户 -- 打开用户分配对话框
	 * @param bugProject
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:assign")
	@RequestMapping(value = "usertobugProject")
	public String selectUserToBugProject(BugProject bugProject, Model model) {
		List<User> userList = systemService.findUser(new User(new BugProject(bugProject.getId())));
		model.addAttribute("bugProject", bugProject);
		model.addAttribute("userList", userList);
		model.addAttribute("selectIds", Collections3.extractToString(userList, "id", ","));
		model.addAttribute("officeList", officeService.findAll());
		return "modules/bug/selectUserToBugProject";
	}


	/**
	 * 选择用户:确认分配
	 * @param bugProject
	 * @param idsArr
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:assign")
	@RequestMapping(value = "assignBugProject")
	public String assignRole(BugProject bugProject, String[] idsArr, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/bug/bugProject/assign?id="+bugProject.getId();
		}


		Object[] objects=systemService.assignBatchUserToBugProject(bugProject,idsArr);
		addMessage(redirectAttributes, "已成功分配 "+objects[0]+" 个用户"+objects[1]);
		return "redirect:" + adminPath + "/bug/bugProject/assign?id="+bugProject.getId();
	}

	/**
	 * 我的项目列表
	 */
	@RequestMapping(value = "self")
	public String selfList(BugProject bugProject, HttpServletRequest request, HttpServletResponse response, Model model) {
		bugProject.setSelf(true);
		Page<BugProject> page = bugProjectService.find(new Page<BugProject>(request, response), bugProject);
		model.addAttribute("page", page);
		return "modules/bug/bugProjectList";
	}

	/**
	 *
	 * 我所有的项目列表
	 * @return
     */

	@RequestMapping(value = "selfJson")
	@ResponseBody
	public List<BugProject> selfAllList(BugProject bugProject){
		bugProject.setSelf(true);
		bugProject.setActive(true);
		return bugProjectService.findList(bugProject);
	}

	/**
	 *
	 * 指定项目版本列表
	 * @return
	 */
	@RequestMapping(value = "findProjectVersionJson")
	@ResponseBody
	@Deprecated
	public List<BugVersion> getProjectVersion(String projectId, HttpServletRequest request){

		return  bugProjectService.findProjectVersionList(projectId);
	}

	/**
	 *
	 * 指定项目版本列表
	 * @return
	 */
	@RequestMapping(value = "loadProjectVersion")
	@ResponseBody
	public Json loadProjectVersion(String projectId, HttpServletRequest request){

		Json json=new Json();
		try {

			List<BugVersion> bugVersionList=bugProjectService.findProjectVersionList(projectId);
			json.setMsg("加载项目状态成功");
			json.setSuccess(true);
			json.setData(bugVersionList);
		}catch (Exception e){
			json.setMsg(e.getMessage());
		}

		return json;
	}
	/**
	 *
	 * 指定项目版本以及流程的下一个节点
	 * @return
	 */
	@RequestMapping(value = "loadProjectVersionAndNextTask")
	@ResponseBody
	public Json loadProjectVersionAndNextTask(String projectId, HttpServletRequest request){

		Json json=new Json();
		try {

			List<List> results=new ArrayList<List>();

			List<BugVersion> bugVersionList=bugProjectService.findProjectVersionList(projectId);
			results.add(bugVersionList);

			BugProject bugProject = bugProjectService.get(projectId);

			TaskDefinition taskDefinition = actTaskService.nextStartEvent(bugProject.getProcessKey(),"");
			Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
			for (Expression e: candidateGroupIdExpressions) {
				String group = e.getExpressionText();// group
				List<User> groupUserList=bugProjectService.findUserListByRole(projectId,group);
				results.add(groupUserList);
			}
			json.setMsg("加载项目状态成功");
			json.setSuccess(true);
			json.setData(results);
		}catch (Exception e){
			json.setMsg(e.getMessage());
		}

		return json;
	}

	/**
	 * 根据 projectId 返回 bug 所有详细信息.
	 * 统计
	 * @param projectId
	 * @return
     */
	@RequestMapping(value = "detail")
	public String projectBugDetail(String projectId,Model model){

		BugProject bugProject = bugProjectService.get(projectId);

		//bugProject
		List<User> userList=bugProjectService.getProjectPeople(projectId);

		for (User u: userList) {
			System.out.println(u.getPhoto());
		}
		bugProject.setUserList(userList);

		Act act=new Act();

		//当前用户,指定项目的 任务.
		//指派,你提交,你参与
		//actTaskService.todoList(act);

		TaskCount taskCount=bugService.totalTaskCount(projectId);



		//总计 count ,type
		//List<List<StatusBug>> totalList = bugProjectService.totalBugStatusNum(projectId);



		//新建
		//List<StatusBug> new

		//重开

		//解决

		//不解决 2e22d9691f684bfd863a107ae222398d

		//已关闭
		//model.addAllAttributes(totalList);
		model.addAttribute(bugProject);

		model.addAttribute("taskCount",taskCount);
		return "modules/bug/projectDetail";
	}

	/**
	 * 指定项目的状态
	 * @param projectId
	 * @param model
     * @return
     */
	@RequestMapping(value = "projectStatus",method = RequestMethod.GET)
	@ResponseBody
	public Json getProjectStatus(String projectId,Model model){

		Json json=new Json();
		try {

			List<Charts> data=bugProjectService.getProjectStatus(projectId);
			json.setMsg("加载项目状态成功");
			json.setSuccess(true);
			json.setData(data);
		}catch (Exception e){
			json.setMsg(e.getMessage());
		}

		return json;
	}


	/**
	 * 指定项目
	 * @param projectId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "totalProjectByDay",method = RequestMethod.GET)
	@ResponseBody
	public Json totalProjectByDay(String projectId,int day,Model model){

		Json json=new Json();
		try {

			Map<String, Object> data = bugProjectService.totalProjectByDay(projectId, day);
			json.setMsg("加载项目状态成功");
			json.setSuccess(true);
			json.setData(data);
		}catch (Exception e){
			json.setMsg(e.getMessage());
		}

		return json;
	}




}