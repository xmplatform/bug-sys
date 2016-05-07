import java.util.Arrays;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml","classpath:spring-context-activiti.xml","classpath:spring-context-jedis.xml","classpath:spring-context-shiro.xml","classpath:spring-mvc.xml"})
public class BaseSpringTest {

	protected static final Logger logger=Logger.getLogger(BaseSpringTest.class);
//	protected Json j=new Json();
//
//
//	protected String writeJsonByFilter(Object object, String[] includesProperties, String[] excludesProperties) {
//		FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
//		if (excludesProperties != null && excludesProperties.length > 0) {
//			filter.getExcludes().addAll(Arrays.<String> asList(excludesProperties));
//		}
//		if (includesProperties != null && includesProperties.length > 0) {
//			filter.getIncludes().addAll(Arrays.<String> asList(includesProperties));
//		}
//		String json = JSON.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat);
//		return json;
//	}

}
