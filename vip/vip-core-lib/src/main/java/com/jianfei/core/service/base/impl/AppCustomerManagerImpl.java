/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月24日-上午11:27:27
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.MessageDto.MsgFlag;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.mapper.AppCustomerMapper;
import com.jianfei.core.service.base.AppCustomerManager;

/**
 *
 * @Description:VIP用户管理
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月24日 上午11:27:27
 * 
 * @version 1.0.0
 *
 */
@Service
@Transactional
public class AppCustomerManagerImpl implements AppCustomerManager {

	@Autowired
	private AppCustomerMapper appCustomerMapper;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jianfei.core.service.base.AppCustomerService#get(java.util.Map)
	 */
	@Override
	public MessageDto<List<Map<String, Object>>> get(Map<String, Object> map) {
		MessageDto<List<Map<String, Object>>> messageDto = new MessageDto<List<Map<String, Object>>>();
		List<Map<String, Object>> appCustomers = appCustomerMapper.get(map);
		if (!CollectionUtils.isEmpty(appCustomers)) {
			messageDto.setData(appCustomers).setOk(true);
		}
		return messageDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.base.AppCustomerManager#delete(java.lang.String)
	 */
	@Override
	public MessageDto<String> delete(String id) {
		MessageDto<String> messageDto = new MessageDto<String>();
		try {
			appCustomerMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			logger.error("禁用VIP用户:{}", e.getMessage());
			return messageDto.setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return messageDto.setOk(true).setData(MessageDto.MsgFlag.SUCCESS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.base.AppCustomerManager#selectByPrimaryKey(java
	 * .lang.String)
	 */
	@Override
	public MessageDto<AppCustomer> selectByPrimaryKey(String id) {
		MessageDto<AppCustomer> messageDto = new MessageDto<AppCustomer>();
		try {
			AppCustomer appCustomer = appCustomerMapper.selectByPrimaryKey(id);
			if (null != appCustomer) {
				messageDto.setOk(true).setData(appCustomer);
			}
		} catch (Exception e) {
			logger.error("禁用VIP用户:{}", e.getMessage());
			return messageDto.setMsgBody(MessageDto.MsgFlag.ERROR);
		}
		return messageDto.setOk(true).setMsgBody(MsgFlag.SUCCESS);
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jianfei.core.service.base.AppCustomerManager#updateDeliveryState(
	 * java.lang.String)
	 */
	@Override
	public MessageDto<String> updateDeliveryState(String id) {
		MessageDto<String> messageDto = new MessageDto<String>();
		try {
			appCustomerMapper.updateDeliveryState(id);

			messageDto.setOk(true).setMsgBody(MsgFlag.SUCCESS);
		} catch (Exception e) {
			logger.error("更改用户投递状态为已投递失败...");
			messageDto.setMsgBody(MsgFlag.ERROR);
		}
		return messageDto;
	}
}
