package com.jianfei.core.service.order;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.dto.OrderAddInfoDto;
import com.jianfei.core.service.order.impl.OrderManagerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class OrderManagerTest {
	@Autowired
	private OrderManagerImpl orderManagerImpl;

	public List<OrderAddInfoDto> returnOrderInfos() {
		OrderAddInfoDto addInfoDto1 = new OrderAddInfoDto();
		addInfoDto1.setCustomerName("倪必天");
		addInfoDto1.setPhone("15003669888");
		addInfoDto1.setCustomerIdenti("350181197808111738");
		addInfoDto1.setUno("020");
		addInfoDto1.setVipCardNo("86869297088");
		addInfoDto1.setAirportId("0468218270952502");

		List<OrderAddInfoDto> list = new ArrayList<>();
		list.add(addInfoDto1);
		return list;
	}

	/**
	 * 添加订单信息
	 * 
	 */
	@Test
	@Rollback(false)
	public void testAddOrderAndUserInfo() {
		List<OrderAddInfoDto> list = returnOrderInfos();
		try {
			for (OrderAddInfoDto orderAddInfoDto : list) {
				orderManagerImpl.addOrderAndUserInfoByOutLine(orderAddInfoDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
