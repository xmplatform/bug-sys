import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by always on 16/5/13.
 */
public class Demo {

    @Test
    public void test(){


        int day=7;
        // 初始化日期
        LinkedHashMap<String,Integer> initValues=new LinkedHashMap<String,Integer>();

        // 初始化 7天内的默认数据为 0
        for (int i = day-1; i >0; i--) {
            //Calendar.get(Calendar.DAY_OF_WEEK);
            String dateStr=getBeforeDay(i);
            initValues.put(dateStr,0);
        }

        for (Map.Entry<String,Integer> s:
                initValues.entrySet()) {
            System.out.println(s.getKey()+":"+s.getValue());

        }
    }

    private String getBeforeDay(int i) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -i);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }
}
