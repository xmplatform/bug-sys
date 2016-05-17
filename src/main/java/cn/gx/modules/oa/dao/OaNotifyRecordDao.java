/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.modules.oa.dao;

import java.util.List;

import cn.gx.common.persistence.CrudDao;
import cn.gx.common.persistence.annotation.MyBatisDao;
import cn.gx.modules.oa.entity.OaNotifyRecord;

/**
 * 通知通告记录DAO接口
 * @author bug
 * @version 2014-05-16
 */
@MyBatisDao
public interface OaNotifyRecordDao extends CrudDao<OaNotifyRecord> {

	/**
	 * 插入通知记录
	 * @param oaNotifyRecordList
	 * @return
	 */
	public int insertAll(List<OaNotifyRecord> oaNotifyRecordList);
	
	/**
	 * 根据通知ID删除通知记录
	 * @param oaNotifyId 通知ID
	 * @return
	 */
	public int deleteByOaNotifyId(String oaNotifyId);
	
}