package com.jianfei.core.service.order;

import java.lang.reflect.InvocationTargetException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.dto.OrderAddInfoDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class OrderManagerTest {
	@Autowired
	private OrderManager orderManager;
	
	/**
	 * 添加订单信息
	 * 
	 */
	@Test
	@Rollback(false)
	public void testAddOrderAndUserInfo() {
		OrderAddInfoDto addInfoDto = new OrderAddInfoDto();
		addInfoDto.setCustomerName("武俊德");
		addInfoDto.setPhone("13835033266");
		addInfoDto.setCustomerIdenti("142228196612100019");
		addInfoDto.setUno("012");
		addInfoDto.setVipCardNo("86870855294");
		addInfoDto.setAirportId("0468218270952502");
		try {
			orderManager.addOrderAndUserInfo(addInfoDto);
		} catch (InvocationTargetException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
