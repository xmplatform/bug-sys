/**
 * Copyright &copy; 2015-2020 <a href="http://www.bug.org/">Bug</a> All rights reserved.
 */
package cn.gx.common.filter;

import cn.gx.common.utils.CacheUtils;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

/**
 * 页面高速缓存过滤器
 */
public class PageCachingFilter extends SimplePageCachingFilter {


	protected CacheManager getCacheManager() {
		return CacheUtils.getCacheManager();
	}
	
}
