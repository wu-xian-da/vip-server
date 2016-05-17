/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月17日-下午2:46:17
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.common.entity.Order;
import com.jianfei.common.mapper.OrderMapper;



/**
 * 订单操作
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月17日 下午2:46:17 
 * 
 * @version 1.0.0
 *
 */
@Service
@Transactional
public class OrderService {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private OrderMapper orderMapper;
	
	public Order getOrderByOrderId(String orderId){
		Order orderInfo = orderMapper.getOrderByOrderId(orderId);
		logger.info("order_time="+orderInfo.getOrderTime());
		return orderInfo;
	}
	
}
