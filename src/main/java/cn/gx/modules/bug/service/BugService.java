/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.modules.bug.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import cn.gx.common.utils.StringUtils;
import cn.gx.modules.act.service.ActTaskService;
import cn.gx.modules.act.utils.ActUtils;
import cn.gx.modules.bug.util.BugStatus;
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
	
}