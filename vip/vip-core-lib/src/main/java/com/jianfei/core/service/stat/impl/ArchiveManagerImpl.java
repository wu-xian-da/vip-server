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
	@Override
	public List<Map<String, Object>> masterTop(Map<String, Object> map) {
		return archiveMapper.masterTop(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.stat.ArchiveManager#masterDraw(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> masterDraw(Map<String, Object> map) {
		return archiveMapper.masterDraw(map);
	}

}
