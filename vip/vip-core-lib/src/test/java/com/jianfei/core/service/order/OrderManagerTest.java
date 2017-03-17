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
		addInfoDto1.setCustomerName("李向前");
		addInfoDto1.setPhone("13934057666");
		addInfoDto1.setCustomerIdenti("140423197207020017");
		addInfoDto1.setUno("019");
		addInfoDto1.setVipCardNo("86857118715");
		addInfoDto1.setAirportId("0468218270952502");

		OrderAddInfoDto addInfoDto2 = new OrderAddInfoDto();
		addInfoDto2.setCustomerName("贾平");
		addInfoDto2.setPhone("13935203343");
		addInfoDto2.setCustomerIdenti("140211196301011335");
		addInfoDto2.setUno("019");
		addInfoDto2.setVipCardNo("86818598042");
		addInfoDto2.setAirportId("0468218270952502");

		OrderAddInfoDto addInfoDto3 = new OrderAddInfoDto();
		addInfoDto3.setCustomerName("亢波");
		addInfoDto3.setPhone("15198874936");
		addInfoDto3.setCustomerIdenti("142225198802281834");
		addInfoDto3.setUno("029");
		addInfoDto3.setVipCardNo("86884121602");
		addInfoDto3.setAirportId("0468218270952502");

		OrderAddInfoDto addInfoDto4 = new OrderAddInfoDto();
		addInfoDto4.setCustomerName("刘阳");
		addInfoDto4.setPhone("18234040388");
		addInfoDto4.setCustomerIdenti("142730199210191539");
		addInfoDto4.setUno("015");
		addInfoDto4.setVipCardNo("86870469997");
		addInfoDto4.setAirportId("0468218270952502");

		OrderAddInfoDto addInfoDto5 = new OrderAddInfoDto();
		addInfoDto5.setCustomerName("罗彝科");
		addInfoDto5.setPhone("18758204992");
		addInfoDto5.setCustomerIdenti("532922198606040517");
		addInfoDto5.setUno("030");
		addInfoDto5.setVipCardNo("86878761669");
		addInfoDto5.setAirportId("0468045723714265");

		OrderAddInfoDto addInfoDto6 = new OrderAddInfoDto();
		addInfoDto6.setCustomerName("冷德涛");
		addInfoDto6.setPhone("13942662371");
		addInfoDto6.setCustomerIdenti("210225197405180150");
		addInfoDto6.setUno("031");
		addInfoDto6.setVipCardNo("86854676438");
		addInfoDto6.setAirportId("0468045723714265");

		OrderAddInfoDto addInfoDto7 = new OrderAddInfoDto();
		addInfoDto7.setCustomerName("刘怡");
		addInfoDto7.setPhone("13942030409");
		addInfoDto7.setCustomerIdenti("210203197311230120");
		addInfoDto7.setUno("027");
		addInfoDto7.setVipCardNo("86877296264");
		addInfoDto7.setAirportId("0468045723714265");

		List<OrderAddInfoDto> list = new ArrayList<>();
		list.add(addInfoDto1);
		list.add(addInfoDto2);
		list.add(addInfoDto3);
		list.add(addInfoDto4);
		list.add(addInfoDto5);
		list.add(addInfoDto6);
		list.add(addInfoDto7);
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
