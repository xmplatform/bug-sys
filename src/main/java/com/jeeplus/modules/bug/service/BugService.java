/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.bug.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.act.service.ActTaskService;
import com.jeeplus.modules.act.utils.ActUtils;
import com.jeeplus.modules.oa.entity.TestAudit;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.bug.entity.Bug;
import com.jeeplus.modules.bug.dao.BugDao;

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

		//申请发起
		if (StringUtils.isBlank(bug.getId())){
			bug.preInsert();
			dao.insert(bug);

			// 启动流程
			actTaskService.startProcess(ActUtils.PD_BUG_AUDIT[0],ActUtils.PD_BUG_AUDIT[1],bug.getId());
		}
		//重新编辑
		else{
			bug.preUpdate();
			dao.update(bug);
			bug.getAct().setComment((ActUtils.FLAG.equals(bug.getAct().getFlag()) ?"[重申]":"[销毁]")+bug.getAct().getComment());

			// 完成任务流程
			Map<String,Object> vars= Maps.newHashMap();
			vars.put("pass",ActUtils.FLAG.equals(bug.getAct().getFlag())?"1":"0");

			actTaskService.complete(bug.getAct().getTaskId(), bug.getAct().getProcInsId(), bug.getAct().getComment(), bug.getSummary(), vars);
		}

		super.save(bug);
	}

	/**
	 * 审核审批保存
	 * @param bug
	 */
	@Transactional(readOnly = false)
	public void auditSave(Bug bug) {

		// 设置意见
		bug.getAct().setComment((ActUtils.FLAG.equals(bug.getAct().getFlag())?"[同意] ":"[驳回] ")+bug.getAct().getComment());

		bug.preUpdate();

		// 对不同环节的业务逻辑进行操作
		String taskDefKey = bug.getAct().getTaskDefKey();

		// 审核环节
		if ("audit".equals(taskDefKey)){

		}
		else if ("testLead".equals(taskDefKey)){
			bug.setTestLeadText(bug.getAct().getComment());
			dao.updateTestLeadText(bug);
		}
		else if ("developerLead".equals(taskDefKey)){
			bug.setDeveloperLeadText(bug.getAct().getComment());
			dao.updateDeveloperLeadText(bug);
		}
		else if ("projectManager".equals(taskDefKey)){
			bug.setProjectManager(bug.getAct().getComment());
			dao.updateDeveloperLeadText(bug);
		}
		else if ("apply_end".equals(taskDefKey)){

		}

		// 未知环节，直接返回
		else{
			return;
		}

		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", ActUtils.FLAG.equals(bug.getAct().getFlag())? "1" : "0");
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
	
}