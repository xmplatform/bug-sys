
import com.jeeplus.modules.bug.bean.StatusBug;
import com.jeeplus.modules.bug.service.BugProjectService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by always on 16/5/6.
 */

public class TotalTest extends BaseSpringTest {


    @Autowired
    private BugProjectService bugProjectService;

    @Test
    public void totalBugStatusNum() {

       // List<StatusBug> simpleBugList=bugProjectService.totalBugStatusNum("9f2a730b92c64d35acbdb541e26b6a73");

       // System.out.println(simpleBugList);

    }

}
