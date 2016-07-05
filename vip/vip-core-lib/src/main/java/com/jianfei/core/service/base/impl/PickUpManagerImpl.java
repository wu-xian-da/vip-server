/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月5日-下午3:04:46
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.MessageDto.MsgFlag;
import com.jianfei.core.mapper.PickUpMapper;
import com.jianfei.core.service.base.PickUpManager;

/**
 *
 * @Description: 接送机业务层实现类
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月5日 下午3:04:46
 * 
 * @version 1.0.0
 *
 */
public class PickUpManagerImpl implements PickUpManager {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PickUpMapper pickUpMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.base.PickUpManager#resultMapList(java.util.Map)
	 */
	@Override
	public MessageDto<List<Map<String, Object>>> resultMapList(
			Map<String, Object> searchParams) {
		MessageDto<List<Map<String, Object>>> messageDto = new MessageDto<List<Map<String, Object>>>();
		try {
			List<Map<String, Object>> resultMaps = pickUpMapper
					.mapList(searchParams);
			messageDto.setOk(true).setMsgBody(MsgFlag.SUCCESS)
					.setData(resultMaps);
		} catch (Exception e) {
			logger.error("获取接送机器列表:{}", e.getMessage());
			messageDto.setMsgBody(MsgFlag.ERROR);
		}
		return messageDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.base.PickUpManager#updateState(java.util.Map)
	 */
	@Override
	public MessageDto<String> updateState(Map<String, Object> params) {
		MessageDto<String> messageDto = new MessageDto<String>();
		try {
			pickUpMapper.updateState(params);
			messageDto.setOk(true).setMsgBody(MsgFlag.SUCCESS);
		} catch (Exception e) {
			logger.error("接送机器状态失败：{}", e.getMessage());
			messageDto.setMsgBody(MsgFlag.ERROR);
		}
		return messageDto;
	}
}
