/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.dao.one;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.test.entity.one.FormLeave;

/**
 * 请假单DAO接口
 * @author lgf
 * @version 2016-03-13
 */
@MyBatisDao
public interface FormLeaveDao extends CrudDao<FormLeave> {
	
}