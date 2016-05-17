/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.modules.sys.dao;

import cn.gx.common.persistence.TreeDao;
import cn.gx.common.persistence.annotation.MyBatisDao;
import cn.gx.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author bug
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	public Office getByCode(String code);
}
