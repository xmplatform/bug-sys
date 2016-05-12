package cn.gx.modules.bug.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cn.gx.common.utils.CacheUtils;
import cn.gx.common.utils.SpringContextHolder;
import cn.gx.modules.bug.dao.BugDao;
import cn.gx.modules.bug.dao.BugProjectDao;
import cn.gx.modules.bug.entity.Bug;
import cn.gx.modules.bug.entity.BugProject;
import cn.gx.modules.sys.dao.DictDao;
import cn.gx.modules.sys.entity.Dict;
import cn.gx.modules.sys.utils.UserUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by always on 16/5/4.
 */
public class BugUtils {

    private static BugProjectDao bugProjectDao = SpringContextHolder.getBean(BugProjectDao.class);
    private static final String CACHE_PROJECT_MAP ="projectMap";

    /**
     *
     * 找到用户参与的项目
     * @return
     */
    public static List<BugProject> findJoinList(){

        String userId = UserUtils.getUser().getId();


        @SuppressWarnings("unchecked")
        Map<String, List<BugProject>> projectMap = (Map<String, List<BugProject>>) CacheUtils.get(CACHE_PROJECT_MAP);
        if (projectMap==null){
            projectMap = Maps.newHashMap();



            for (BugProject bugProject : bugProjectDao.findJoinList(userId)){
                List<BugProject> projectList = projectMap.get(userId);
                if (projectList != null){
                    projectList.add(bugProject);
                }else{
                    projectMap.put(userId, Lists.newArrayList(projectList));
                }
            }
            CacheUtils.put(CACHE_PROJECT_MAP, projectMap);
        }
        List<BugProject> projectList = projectMap.get(userId);
        if (projectList == null){
            projectList = Lists.newArrayList();
        }
        return projectList;
    }
}
