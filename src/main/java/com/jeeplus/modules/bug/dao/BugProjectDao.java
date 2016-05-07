/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.bug.dao;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.bug.bean.StatusBug;
import com.jeeplus.modules.bug.entity.BugProject;
import com.jeeplus.modules.bug.bean.Charts;

import java.util.List;

/**
 * 缺陷所属的项目DAO接口
 * @author xinguan
 * @version 2016-04-30
 */
@MyBatisDao
public interface BugProjectDao extends CrudDao<BugProject> {

    List<BugProject> findJoinList(String userId);


    String getName(String projectId);

    List<StatusBug> totalBugStatusNum(StatusBug statusBug);

    Charts totalBugStatus(Charts charts);
}