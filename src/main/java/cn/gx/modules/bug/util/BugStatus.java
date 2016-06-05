package cn.gx.modules.bug.util;

import cn.gx.modules.bug.entity.Bug;
import org.springframework.http.HttpStatus;

/**
 * Created by always on 16/5/5.
 */
public enum  BugStatus {


    NEW("NEW","新建","新建"),
    GENUINE("GENUINE","有效","有效"),
    ASSIGNED("ASSIGNED","分配","分配给开发人员"),
    TEST("TEST","已解决","开发人员已解决，待重新测试"),
    VERIFIED("VERIFIED","已验证","问题经过检验，被修复了"),

    DEFERRED("DEFERRED","延迟","延迟:日后修复"),
    REJECTED("REJECTED","拒绝","不是Bug;Bug 重复;Bug 难以复现"),
    REOPENED("REOPENED","重开","依然存在缺陷,重新打开");





    private final String status;
    private final String statusPhrase;
    private final String reasonPhrase;

    private BugStatus(String status,String statusPhrase,String reasonPhrase){
        this.status=status;
        this.statusPhrase=statusPhrase;
        this.reasonPhrase=reasonPhrase;
    }

    public String getStatus() {
        return status;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }


    public static String reasonPhraseOf(String status) {

        BugStatus [] statuArr = values();

        int len = statuArr.length;

        for(int i = 0; i < len; ++i) {
            BugStatus bugStatus = statuArr[i];
            if(bugStatus.status.equals(status)) {
                return bugStatus.reasonPhrase;
            }

;        }


        throw new IllegalArgumentException("No matching constant for [" + status + "]");
    }

    public static BugStatus statusOf(String statusPhrase){

        BugStatus [] statuArr = values();

        int len = statuArr.length;

        for(int i = 0; i < len; ++i) {
            BugStatus bugStatus = statuArr[i];
            if(bugStatus.statusPhrase.equals(statusPhrase)) {
                return bugStatus;
            }
        }


        throw new IllegalArgumentException("No matching constant for [" + statusPhrase + "]");
    }


    public static String statusPhraseOf(String status) {

        BugStatus [] statuArr = values();

        int len = statuArr.length;

        for(int i = 0; i < len; ++i) {
            BugStatus bugStatus = statuArr[i];
            if(bugStatus.status.equals(status)) {
                return bugStatus.statusPhrase;
            }
        }


        throw new IllegalArgumentException("No matching constant for [" + status + "]");
    }

    public String getStatusPhrase() {
        return statusPhrase;
    }

}
