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
        Charts assignChart=new Charts(BugStatus.ASSIGNED,"#F7464A","#FF5A5E");

        Charts retestChart=new Charts(BugStatus.TEST,"#FDB45C","#FFC870");
        Charts fixedChart=new Charts(BugStatus.VERIFIED,"#FFFFF0","#FFFFF0");

        Charts deferredChart=new Charts(BugStatus.DEFERRED,"#00FFFF","#00FFFF");
        Charts rejectedChart=new Charts(BugStatus.REJECTED,"#00008B","#00008B");
        Charts reopenChart=new Charts(BugStatus.REOPENED,"#46BFBD","#5AD3D1");



        bugStatuseList.add(newChart);
        bugStatuseList.add(assignChart);
        bugStatuseList.add(reopenChart);
        bugStatuseList.add(retestChart);
        bugStatuseList.add(deferredChart);
        bugStatuseList.add(rejectedChart);
        bugStatuseList.add(fixedChart);

        return bugStatuseList;
    }
}
