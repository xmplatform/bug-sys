/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.bug.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.bug.entity.Bug;
import com.jeeplus.modules.bug.dao.BugDao;

/**
 * 缺陷Service
 * @author xinguan
 * @version 2016-04-30
 */
@Service
@Transactional(readOnly = true)
public class BugService extends CrudService<BugDao, Bug> {

	public Bug get(String id) {
		return super.get(id);
	}
	
	public List<Bug> findList(Bug bug) {
		return super.findList(bug);
	}
	
	public Page<Bug> findPage(Page<Bug> page, Bug bug) {
		return super.findPage(page, bug);
	}
	
	@Transactional(readOnly = false)
	public void save(Bug bug) {
		super.save(bug);
	}
	
	@Transactional(readOnly = false)
	public void delete(Bug bug) {
		super.delete(bug);
	}
	
}