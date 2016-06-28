package com.jianfei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.service.sys.CacheManager;

@Controller
@RequestMapping(value = "/cacheManager")
public class CacheController {

	@Autowired
	private CacheManager cacheManager;

	@ResponseBody
	@RequestMapping(value = "/clean/homeCache")
	public MessageDto<String> cleanHomePageCache() {
		return cacheManager.cleanHomePageCache();
	}
}
