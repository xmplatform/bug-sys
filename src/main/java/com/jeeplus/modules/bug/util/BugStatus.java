package com.jeeplus.modules.bug.util;

import org.springframework.http.HttpStatus;

/**
 * Created by always on 16/5/5.
 */
public enum  BugStatus {

    // poster_task 提交
    NEW("NEW","新建"),
    RENEW("RENEW","重复新建"),

    //testerLead_task
    GENUINE("GENUINE","有效"),
    NOGENUINE("NOGENUINE","无效"),

    //projectManager_task
    REJECTED_DUPLICATE("REJECTED_DUPLICATE","拒绝原因:已经重现了"),
    REJECTED_NOT_BUG("REJECTED_NOT_BUG","拒绝原因:不属于缺陷"),
    REJECTED_NOT_REPRODUCIBLE("REJECTED_NOT_REPRODUCIBLE","拒绝原因:难以复现"),
    DEFERRED("DEFERRED","延迟:日后修复"),

    ASSIGNED("ASSIGNED","分配给开发人员"),

    //developer_task
    OPEN("OPEN","接受任务,打开缺陷"),
    FIXED("FIXED","开发人员修复缺陷,待测试组检测"),

    //tester_task
    RETEST("RETEST","验证开发人员修复的缺陷"),

    REOPEN("REOPEN","依然存在缺陷,重新打开"),
    CLOSED("CLOSED","缺陷已验证,缺陷关闭");

    private final String status;
    private final String reasonPhrase;

    private BugStatus(String status,String reasonPhrase){
        this.status=status;
        this.reasonPhrase=reasonPhrase;
    }

    public String getStatus() {
        return status;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }


    public static BugStatus reasonPhraseOf(String status) {

        BugStatus [] statuArr = values();

        int len = statuArr.length;

        for(int i = 0; i < len; ++i) {
            BugStatus bugStatus = statuArr[i];
            if(bugStatus.status == status) {
                return bugStatus;
            }

;        }


        throw new IllegalArgumentException("No matching constant for [" + status + "]");
    }
}
