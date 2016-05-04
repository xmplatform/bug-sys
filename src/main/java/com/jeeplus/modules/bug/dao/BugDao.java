/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.bug.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.bug.entity.Bug;

/**
 * 缺陷DAO接口
 * @author xinguan
 * @version 2016-05-04
 */
@MyBatisDao
public interface BugDao extends CrudDao<Bug> {


    public Bug getByProcInsId(String procInsId);

    public int updateInsId(Bug bug);

    public int updateTestLeadText(Bug bug);

    public int updateDeveloperLeadText(Bug bug);

    public int updateProjectManagerText(Bug bug);
}