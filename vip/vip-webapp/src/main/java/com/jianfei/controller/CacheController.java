package com.jianfei.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.stat.ArchiveManager;

@Controller
@RequestMapping(value = "/cache")
public class CacheController {

	@Autowired
	private ArchiveManager archiveManager;

	@ResponseBody
	@RequestMapping(value = "/app/dp")
	public Map<String, Object> testCacheDp(String date) {
		if (StringUtils.isEmpty(date)) {
			return new MapUtils.Builder().setKeyValue("result", "error")
					.build();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentTime", date);
		archiveManager.dateProvinceIdCache(map);
		return new MapUtils.Builder().setKeyValue("result", "ok").build();
	}

	@ResponseBody
	@RequestMapping(value = "/app/dpa")
	public Map<String, Object> testCacheDpa(String date) {
		if (StringUtils.isEmpty(date)) {
			return new MapUtils.Builder().setKeyValue("result", "error")
					.build();
		}
		Map<String, Object> mapCon = new HashMap<String, Object>();
		mapCon.put("currentTime", date);
		archiveManager.dateProvinceIdApportIds(mapCon);
		return new MapUtils.Builder().setKeyValue("result", "ok").build();
	}

	@ResponseBody
	@RequestMapping(value = "/app/daily")
	public Map<String, Object> testCacheDaily(String date) {
		if (StringUtils.isEmpty(date)) {
			return new MapUtils.Builder().setKeyValue("result", "error")
					.build();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("maxTime", date);
		archiveManager.baseDailyExtract(map);
		return new MapUtils.Builder().setKeyValue("result", "ok").build();
	}
}
