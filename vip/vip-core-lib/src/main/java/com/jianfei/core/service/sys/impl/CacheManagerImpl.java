package com.jianfei.core.service.sys.impl;

import org.springframework.stereotype.Service;

import com.jianfei.core.common.cache.CacheCons;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.service.sys.CacheManager;

@Service
public class CacheManagerImpl implements CacheManager {

	@Override
	public MessageDto<String> cleanHomePageCache() {
		MessageDto<String> messageDto = new MessageDto<String>();
		try {
			messageDto.setOk(true);
			JedisUtils.delObject(CacheCons.Sys.LAST_1_MONTH);
			JedisUtils.delObject(CacheCons.Sys.LAST_2_MONTH);
			JedisUtils.delObject(CacheCons.Sys.LAST_3_MONTH);
			JedisUtils.delObject(CacheCons.Sys.SYS_TOP3_MONTH);
		} catch (Exception e) {
			e.printStackTrace();
			messageDto.setMsgBody("清除首页缓存失败...").setOk(false);
		}
		return messageDto;
	}
}
