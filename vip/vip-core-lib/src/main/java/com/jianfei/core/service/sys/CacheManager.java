package com.jianfei.core.service.sys;

import java.util.Map;

import com.jianfei.core.common.utils.MessageDto;

/**
 * 缓存清除工具类
 * 
 * @author Administrator
 *
 */
public interface CacheManager {

	/**
	 * 清除首页缓存
	 * 
	 * @return
	 */
	MessageDto<String> cleanHomePageCache();

}
