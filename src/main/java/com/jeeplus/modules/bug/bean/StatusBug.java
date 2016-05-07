package com.jeeplus.modules.bug.bean;

import com.jeeplus.modules.bug.util.BugStatus;

/**
 * Created by always on 16/5/6.
 *
 * 一个月内,没中类型的 Bug 每天的 提交数量
 *
 */
public class StatusBug {

    public String projectId;

    public String bugStatus;

    public String dateStr;

    public Long num;


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setBugStatus(String bugStatus) {
        this.bugStatus = bugStatus;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }
}
