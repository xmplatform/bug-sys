/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">JeePlus</a> All rights reserved.
 */
package cn.gx.modules.sys.dao;

import java.util.List;

import cn.gx.common.persistence.CrudDao;
import cn.gx.common.persistence.annotation.MyBatisDao;
import cn.gx.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author bug
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
}
