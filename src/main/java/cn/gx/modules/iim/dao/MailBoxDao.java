/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">JeePlus</a> All rights reserved.
 */
package cn.gx.modules.iim.dao;

import cn.gx.common.persistence.CrudDao;
import cn.gx.common.persistence.annotation.MyBatisDao;
import cn.gx.modules.iim.entity.MailBox;

/**
 * 发件箱DAO接口
 * @author bug
 * @version 2015-11-15
 */
@MyBatisDao
public interface MailBoxDao extends CrudDao<MailBox> {
	
	public int getCount(MailBox entity);
	
}