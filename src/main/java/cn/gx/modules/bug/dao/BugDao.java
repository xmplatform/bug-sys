/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.modules.bug.dao;

import cn.gx.common.persistence.CrudDao;
import cn.gx.common.persistence.annotation.MyBatisDao;
import cn.gx.modules.bug.entity.Bug;
import cn.gx.modules.sys.entity.User;

/**
 * 缺陷DAO接口
 * @author xinguan
 * @version 2016-05-04
 */
@MyBatisDao
public interface BugDao extends CrudDao<Bug> {


    public Bug getByProcInsId(String procInsId);

    public int updateInsId(Bug bug);

    public int updateTesterLeadText(Bug bug);

    public int updateDeveloperLeadText(Bug bug);

    public int updateProjectManagerText(Bug bug);

    /**
     * 解决 bug
     * @param bug
     * @return
     */
    public int updateSolution(Bug bug);

    /**
     * 完成任务
     * @param bug
     * @return
     */
    public int completeBugTask(Bug bug);

    /**
     * 根据 procInsId,获取bug 的创建者
     * @param procInsId
     * @return
     */
    User getApplyUser(String procInsId);
}