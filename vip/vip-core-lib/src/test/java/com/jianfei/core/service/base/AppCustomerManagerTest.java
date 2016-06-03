/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年6月1日-下午4:14:06
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.ExportAip;
import com.jianfei.core.common.utils.ExportExclUtils;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.service.order.OrderManager;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年6月1日 下午4:14:06
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class AppCustomerManagerTest {
	@Autowired
	private AppCustomerManager appCustomerManager;

	@Autowired
	private OrderManager orderManager;
	@Autowired
	private BusizzManager<User> busizzManager;

	@Test
	public void testsssssss() {
		Map<String, Object> map = DateUtil.getDelayDate(1);
		PageHelper.startPage(1, 10);
		map.put("today",
				DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		List<Map<String, Object>> list = busizzManager.listMap(map);
		System.out.println(JSONObject.toJSONString(list));
	}

	@Test
	public void testsssss() {
		List<Map<String, Object>> maps = busizzManager
				.selectMap(new MapUtils.Builder()
						.setKeyValue("dateTime", "2015-12-02")
						.setKeyValue("dtflag", "0").build());
		System.out.println(JSONObject.toJSONString(maps));
	}

	@Test
	public void testsss() {
		List<Map<String, Object>> maps = orderManager
				.selectOrder(new MapUtils.Builder()
						.setKeyValue("dateTime", "2015-12-02")
						.setKeyValue("orderState", 3).build());
		System.out.println(JSONObject.toJSONString(maps));
	}

	@Test
	public void test() throws Exception {
		MessageDto<List<AppCustomer>> messageDto = appCustomerManager
				.get(new MapUtils.Builder().build());
		ExportExclUtils<ExportAip> exclUtils = new ExportExclUtils<ExportAip>();
		String[] headers = { "姓名", "手机号", "日期", "常住地址", "邮箱", "用户状态" };
		List<ExportAip> dataset = new ArrayList<ExportAip>();
		for (AppCustomer appCustomer : messageDto.getData()) {
			ExportAip exportAip = new ExportAip(
					StringUtils.obj2String(appCustomer.getCustomerName()),
					StringUtils.obj2String(appCustomer.getPhone()),
					StringUtils.obj2String(appCustomer.getCreateTime()),
					StringUtils.obj2String(appCustomer.getAddress()),
					StringUtils.obj2String(appCustomer.getEmail()),
					StringUtils.obj2String(appCustomer.getOrderStatu()));
			dataset.add(exportAip);
		}
		OutputStream out = new FileOutputStream("E://a.xls");
		exclUtils.exportExcel(headers, dataset, out);
		out.close();
	}

}
