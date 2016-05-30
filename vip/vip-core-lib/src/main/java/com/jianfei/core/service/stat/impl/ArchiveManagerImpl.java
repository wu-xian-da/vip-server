/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午5:15:27
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.stat.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.common.cache.CacheCons;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.mapper.ArchiveMapper;
import com.jianfei.core.service.stat.ArchiveManager;

/**
 *
 * @Description: 订单归档，后台根据角色不同，首页展示不同的图表
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 下午5:15:27
 * 
 * @version 1.0.0
 *
 */
@Service
@Transactional
public class ArchiveManagerImpl implements ArchiveManager {

	@Autowired
	private ArchiveMapper archiveMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#masterTotal(java.util.Map)
	 */
	@Override
	public Map<String, Object> masterTotal(Map<String, Object> map) {
		return archiveMapper.masterTotal(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#masterTop(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> masterTop(Map<String, Object> map) {
		List<Map<String, Object>> list = null;
		Object object = JedisUtils.getObject(CacheCons.Sys.LAST_MONTH_TOP3);
		if (null != object) {
			list = (List<Map<String, Object>>) JedisUtils
					.getObject(CacheCons.Sys.LAST_MONTH_TOP3);
		} else {
			list = archiveMapper.masterTop(map);
			JedisUtils.setObject(CacheCons.Sys.LAST_MONTH_TOP3, list, 0);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#masterDraw(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> masterDraw(Map<String, Object> map,
			String cacheKey) {
		List<Map<String, Object>> maps = null;
		Object object = JedisUtils.getObject(cacheKey);
		if (null != object) {
			maps = (List<Map<String, Object>>) JedisUtils.getObject(cacheKey);
		} else {
			maps = archiveMapper.masterDraw(map);
			JedisUtils.setObject(cacheKey, maps, 0);
		}
		return maps;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#zhuGuanTotal(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> zhuGuanTotal(Map<String, Object> map,
			String cacheKey) {

		Map<String, Object> rsMap = null;
		Object object = JedisUtils
				.getObject(CacheCons.Sys.LAST_MONTH_TOTAL_ZHUGUAN);
		if (null != object) {
			rsMap = (Map<String, Object>) JedisUtils
					.getObject(CacheCons.Sys.LAST_MONTH_TOTAL_ZHUGUAN);
		} else {
			rsMap = archiveMapper.zhuGuanTotal(map);
			JedisUtils.setObject(CacheCons.Sys.LAST_MONTH_TOTAL_ZHUGUAN, rsMap,
					0);
		}
		return archiveMapper.zhuGuanTotal(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#zhuGuanAllAirPort(java.util
	 * .Map)
	 */
	@Override
	public List<Map<String, Object>> zhuGuanAllAirPort(Map<String, Object> map,
			String cacheKey) {
		// TODO
		return archiveMapper.zhuGuanAllAirPort(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#zhuGuanDraw(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> zhuGuanDraw(Map<String, Object> map,
			String cacheKey) {
		// TODO
		return archiveMapper.zhuGuanDraw(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#baseDailyExtract(java.util
	 * .Map)
	 */
	@Override
	public void baseDailyExtract(Map<String, Object> map) {
		archiveMapper.baseDailyExtract(map);
	}

}
