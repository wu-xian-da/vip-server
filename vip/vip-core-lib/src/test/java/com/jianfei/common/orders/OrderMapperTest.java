/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月15日-下午3:54:37
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.common.orders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.jianfei.common.entity.Order;
import com.jianfei.common.service.OrderService;




/**
 *
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com
 * @date: 2016年5月15日 下午3:54:37
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class OrderMapperTest {

	@Autowired
	private OrderService orderService;
	
	//订单详情
	@Test
	public void testGetOrderInfo(){
		orderService.getOrderByOrderId("1001");
	}
	
	//分页查询
	@Test
	public void testPageSel(){
		Map<String, Object> map = new HashMap<>();
		PageInfo<Order> page = orderService.pageSel(2, 2, map);
		List<Order> orderList = page.getList();
		for(Order order : orderList){
			System.out.println("order_id:"+order.getOrderId()+" order_time:"+order.getOrderTime());
		}
	}
	

}
