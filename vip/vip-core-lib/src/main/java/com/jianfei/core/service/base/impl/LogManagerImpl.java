/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月27日-下午6:22:06
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfei.core.mapper.LogMapper;
import com.jianfei.core.service.LogManager;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月27日 下午6:22:06
 * 
 * @version 1.0.0
 *
 */
@Service
public class LogManagerImpl implements LogManager {
	@Autowired
	private LogMapper logMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.LogManager#logList(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> logList(Map<String, Object> map) {
		return logMapper.mapList(map);
	}

}
