/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月17日-下午2:46:17
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.common.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.common.entity.Order;
import com.jianfei.common.mapper.OrderMapper;
import com.jianfei.core.bean.Role;



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
	
	/**
	 * 根据订单编号返回订单的详细信息
	 * @param orderId 订单编号
	 * @return
	 * Order
	 * @version  1.0.0
	 */
	public Order getOrderByOrderId(String orderId){
		Order orderInfo = orderMapper.getOrderByOrderId(orderId);
		logger.info("order_time="+orderInfo.getOrderTime());
		return orderInfo;
	}
	
	/**
	 * 
	 * @param pageNo 第几页
	 * @param pageSize 每页的条数
	 * @param params 查询条件
	 * @return
	 * PageInfo<Role>
	 * @version  1.0.0
	 */
	public PageInfo<Order> pageSel(int pageNo, int pageSize,
			Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<Order> list = orderMapper.get(params);
		PageInfo<Order> pageInfo = new PageInfo(list);
		return pageInfo;
	}
	
}
