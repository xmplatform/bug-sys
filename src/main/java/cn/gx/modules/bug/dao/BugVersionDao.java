/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.modules.bug.dao;

import cn.gx.common.persistence.CrudDao;
import cn.gx.common.persistence.annotation.MyBatisDao;
import cn.gx.modules.bug.entity.BugVersion;

/**
 * 缺陷所属的项目DAO接口
 * @author xinguan
 * @version 2016-04-30
 */
@MyBatisDao
public interface BugVersionDao extends CrudDao<BugVersion> {
	
}