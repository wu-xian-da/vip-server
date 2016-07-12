/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年6月1日-下午4:14:06
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.enu.VipCardState;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.ExportAip;
import com.jianfei.core.common.utils.ExportExclUtils;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
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

	@Autowired
	private VipCardManager vipCardManager;

	@Test
	public void testCu() throws Exception {
		MessageDto<List<Map<String, Object>>> messageDto = appCustomerManager
				.get(new MapUtils.Builder().build());
		ExportExclUtils<ExportAip> exclUtils = new ExportExclUtils<ExportAip>();
		if (messageDto.isOk()) {
			List<ExportAip> dataset = new ArrayList<ExportAip>();
			for (Map<String, Object> map : messageDto.getData()) {
//				ExportAip exportAip = new ExportAip(map.get("customer_name"),
//						map.get("customer_phone"), map.get("sex"),
//						map.get("card_type"), map.get("customer_identi"),
//						map.get("birthday"), map.get("insured"),
//						map.get("orderstate"));
//				dataset.add(exportAip);
			}
			File file = new File("E:\\a.xls");
			OutputStream output = new FileOutputStream(file);
			exclUtils.exportExcel(new String[] { "姓名", "手机号", "性别", "证件类型",
					"证件号码", "出生日期", "投保状态", "用户状态" }, dataset, output);
		}
	}

	@Test
	public void testsssssss() {
		Map<String, Object> map = DateUtil.getDelayDate(1);
		PageHelper.startPage(1, 10);
		map.put("today", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
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

	@Rollback(false)
	@Test
	public void tesD() {
		AppVipcard vipcard = vipCardManager.getVipCardByNo("1516326");
		Date expireDate = DateUtil.addDays(new Date(), vipcard.getValideTime());
		boolean isOk = vipCardManager.activeAppCard(new MapUtils.Builder()
				.setKeyValue("expiryTime", expireDate)
				.setKeyValue("card_state", VipCardState.ACTIVE.getName())
				.setKeyValue("cardNo", "1516326").build());
		System.out.println(isOk);
	}

	@Test
	public void testsss() {
		List<Map<String, Object>> maps = orderManager
				.selectOrder(new MapUtils.Builder()
						.setKeyValue("dateTime", "2015-12-02")
						.setKeyValue("orderState", 3).build());
		System.out.println(JSONObject.toJSONString(maps));
	}

}
