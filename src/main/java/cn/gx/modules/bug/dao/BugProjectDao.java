/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.modules.bug.dao;

import cn.gx.common.persistence.CrudDao;
import cn.gx.common.persistence.annotation.MyBatisDao;
import cn.gx.modules.bug.bean.StatusBug;
import cn.gx.modules.bug.entity.BugProject;
import cn.gx.modules.bug.bean.Charts;
import cn.gx.modules.sys.entity.User;
import javafx.scene.chart.Chart;

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

    List<Charts> totalProjectByDay(Charts charts);

    List<User> getProjectPeople(User user);
}