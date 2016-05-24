/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月15日-下午4:47:57
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.common.orders;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppCardBack;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.bean.Resource;
import com.jianfei.core.bean.Role;
import com.jianfei.core.dto.OrderShowInfoDto;
import com.jianfei.core.service.order.impl.OrderManagerImpl;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月15日 下午4:47:57
 * 
 * @version 1.0.0
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class OrderMapperTest {

	@Autowired
	private OrderManagerImpl orderManagerImpl;

	//订单列表分页测试
	@Test
	public void testGet() {
		PageHelper.startPage(1, 2);
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("endTime", "");
		map.put("airportId", "0");
		map.put("orderState", "5");
		
		map.put("invoiceState", 0);
		//map.put("phoneOrUserName","");
		PageInfo<OrderShowInfoDto> pageinfo = orderManagerImpl.simplePage(1, 2, map);
		System.out.println("a="+pageinfo.getTotal());
	}
	//退款列表
	@Test
	public void testBackcardpage(){
		PageHelper.startPage(1, 2);
		Map<String, Object> map = new HashMap<String, Object>();
	
		PageInfo<OrderShowInfoDto> pageinfo = orderManagerImpl.backCardPage(1, 2, map);
		List<OrderShowInfoDto> list = pageinfo.getList();
		for(OrderShowInfoDto a : list){
			System.out.println(a);
		}
		//System.out.println("a="+pageinfo.getTotal());
	}
	
	//更新订单状态
	@Test
	public void testUpdate(){
		orderManagerImpl.updateOrderStateByOrderId("dd1001", 1);
	}
	
	//vip卡初始金额
	@Test
	public void testInitVipMoney(){
		orderManagerImpl.remainMoney("dd10056");
	}
	//记录退卡流水号
	@Test
	public void testInsertBackCardInfo(){
		AppCardBack appCardBack = new AppCardBack();
		appCardBack.setBackId(UUID.randomUUID().toString());
		appCardBack.setBackType(1);
		appCardBack.setCustomerCard("1234558252");
		
		orderManagerImpl.insertBackCardInfo(appCardBack);
	}
	//查询用户的退款卡号
	@Test
	public void testUserCard(){
		System.out.println(orderManagerImpl.selCustomerCard("dd1003"));
	}
	//更新退款流水号
	@Test
	public void testUpdateBackCard(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("finishTime", new Date());
		map.put("checkId", "1001");
		map.put("orderId", "dd1003");
		orderManagerImpl.updateBackCardByOrderId(map);
	}
	//根据订单编号返回订单信息
	@Test
	public void testSelDetailOrderInfo(){
		System.out.println(orderManagerImpl.returnOrderDetailInfoByOrderId("dd1001"));
	}
	

}
