/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.bug.service;

import java.util.List;

import com.jeeplus.modules.oa.entity.OaNotify;
import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.bug.entity.BugProject;
import com.jeeplus.modules.bug.dao.BugProjectDao;
import com.jeeplus.modules.bug.entity.BugVersion;
import com.jeeplus.modules.bug.dao.BugVersionDao;

/**
 * 缺陷所属的项目Service
 * @author xinguan
 * @version 2016-04-30
 */
@Service
@Transactional(readOnly = true)
public class BugProjectService extends CrudService<BugProjectDao, BugProject> {

	@Autowired
	private BugVersionDao bugVersionDao;

	@Autowired
	private SystemService systemService;
	
	public BugProject get(String id) {
		BugProject bugProject = super.get(id);
		bugProject.setBugVersionList(bugVersionDao.findList(new BugVersion(bugProject)));
		return bugProject;
	}
	
	public List<BugProject> findList(BugProject bugProject) {
		return super.findList(bugProject);
	}
	
	public Page<BugProject> findPage(Page<BugProject> page, BugProject bugProject) {
		return super.findPage(page, bugProject);
	}
	
	@Transactional(readOnly = false)
	public void save(BugProject bugProject) {
		super.save(bugProject);
		for (BugVersion bugVersion : bugProject.getBugVersionList()){
			if (bugVersion.getId() == null){
				continue;
			}
			if (BugVersion.DEL_FLAG_NORMAL.equals(bugVersion.getDelFlag())){
				if (StringUtils.isBlank(bugVersion.getId())){
					bugVersion.setBugProject(bugProject);
					bugVersion.preInsert();
					bugVersionDao.insert(bugVersion);
				}else{
					bugVersion.preUpdate();
					bugVersionDao.update(bugVersion);
				}
			}else{
				bugVersionDao.delete(bugVersion);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BugProject bugProject) {
		super.delete(bugProject);
		bugVersionDao.delete(new BugVersion(bugProject));
	}

	public Page<BugProject> find(Page<BugProject> page, BugProject bugProject) {

		bugProject.setPage(page);
		page.setList(dao.findList(bugProject));
		return page;
	}


	public List<BugVersion> findProjectVersionList(String projectId) {

		return bugVersionDao.findList(new BugVersion(new BugProject(projectId)));
	}
}