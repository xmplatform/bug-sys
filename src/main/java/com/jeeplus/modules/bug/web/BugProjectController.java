/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.bug.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;
import com.jeeplus.common.utils.Collections3;
import com.jeeplus.modules.bug.entity.Bug;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.OfficeService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.bug.entity.BugProject;
import com.jeeplus.modules.bug.service.BugProjectService;

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
	 * @param roleId
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
	 * @param role
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


	

}