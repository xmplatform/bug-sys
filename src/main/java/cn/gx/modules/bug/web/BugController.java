/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">JeePlus</a> All rights reserved.
 */
package cn.gx.modules.bug.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gx.modules.bug.entity.BugProject;
import cn.gx.modules.bug.util.BugStatus;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

		String view ="bugForm";

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
		return "redirect:" + adminPath + "/act/task/todo/";
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
	

}