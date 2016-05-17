/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.modules.sys.dao;

import cn.gx.common.persistence.TreeDao;
import cn.gx.common.persistence.annotation.MyBatisDao;
import cn.gx.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author bug
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}
