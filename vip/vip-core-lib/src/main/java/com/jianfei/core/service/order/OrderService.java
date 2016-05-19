/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-下午4:02:09
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.dto.OrderShowInfoDto;
import com.jianfei.core.mapper.AppOrdersMapper;

/**
 *
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月18日 下午4:02:09 
 * 
 * @version 1.0.0
 *
 */
@Service
public class OrderService{
	@Autowired
	private AppOrdersMapper appOrdersMapper;
	/**
	 * queryPage(分页查询)
	 *
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 *            封装查询参数
	 * @return Page<Role>
	 * @version 1.0.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageInfo<AppOrders> simplePage(int pageNo, int pageSize,
			Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<AppOrders> list = appOrdersMapper.get(params);
		PageInfo<AppOrders> pageInfo = new PageInfo(list);
		return pageInfo;
	}
	
	/**
	 * queryPage(分页查询2)
	 *
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 *            封装查询参数
	 * @return Page<Role>
	 * @version 1.0.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageInfo<OrderShowInfoDto> getOrderPage(int pageNo, int pageSize,
			Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<OrderShowInfoDto> list = appOrdersMapper.getOrderListByPageNo(params);
		PageInfo<OrderShowInfoDto> pageInfo = new PageInfo(list);
		return pageInfo;
	}
}
