package cn.gx.modules.bug.util;


import cn.gx.modules.bug.bean.Charts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by always on 16/5/7.
 */
public class Total {

    private Charts charts;

    public static List<Charts> showBugStatus(){

        List<Charts> bugStatuseList=new ArrayList<Charts>();

        Charts newChart=new Charts(BugStatus.NEW,"#F7464A","#FF5A5E");
        Charts reopenChart=new Charts(BugStatus.REOPEN,"#46BFBD","#5AD3D1");
        Charts retestChart=new Charts(BugStatus.RETEST,"#FDB45C","#FFC870");

        Charts deferredChart=new Charts(BugStatus.DEFERRED,"#00FFFF","#00FFFF");
        Charts rejectedChart=new Charts(BugStatus.REJECTED_NOT_BUG,"#00008B","#00008B");
        Charts fixedChart=new Charts(BugStatus.FIXED,"#FFFFF0","#FFFFF0");


        bugStatuseList.add(newChart);
        bugStatuseList.add(reopenChart);
        bugStatuseList.add(retestChart);
        bugStatuseList.add(deferredChart);
        bugStatuseList.add(rejectedChart);
        bugStatuseList.add(fixedChart);

        return bugStatuseList;
    }
}
