/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午5:16:33
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.stat.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.service.stat.ArchiveManager;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 下午5:16:33
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml",
		"classpath:spring-context-jedis.xml" })
@Transactional
public class ArchiveManagerImplTest {

	@Autowired
	private ArchiveManager archiveManager;

	@Test
	public void testDateProvinceIdAirport() {
		Map<String, Object> mapCon = new HashMap<String, Object>();
		mapCon.put("currentTime", "2016-08-02");
		archiveManager.dateProvinceIdApportIds(mapCon);
	}

	@Test
	public void cacheDate() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentTime", "2016-08-02");
		archiveManager.dateProvinceIdCache(map);
	}

	@Test
	public void testDailyOrderArchice() {
//		List<String> list = dateList();
//		for (String str : list) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			Date date = DateUtil.getDate(str, "yyyy-MM-dd");
//			map.put("maxTime", DateUtil.dateToString(date, "yyyy-MM-dd"));
//			archiveManager.baseDailyExtract(map);
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}

	@Rollback(value = false)
	@Test
	public void testDailyOrderArchice2() {
		Map<String, Object> map = new HashMap<String, Object>();
		Date date = DateUtil.getDate("2016-08-05", "yyyy-MM-dd");
		map.put("maxTime", DateUtil.dateToString(date, "yyyy-MM-dd"));
		archiveManager.baseDailyExtract(map);
	}

	public List<String> dateList() {
		List<String> list = new ArrayList<String>();
		Date date = DateUtil.getDate("2016-06-01", DateUtil.NORMAL_DATE_FORMAT);
		for (int i = 0; i < 30; i++) {
			Date d = DateUtil.addDays(date, i);
			list.add(DateUtil.getDateStr(d, DateUtil.NORMAL_DATE_FORMAT));
		}
		for (String str : list) {
			System.out.println(str);
		}

		return list;
	}

	@Test
	public void testSelectAirportByProvinceIds() {
		List<Map<String, Object>> list = archiveManager
				.selectAirportByProvinceIds(new MapUtils.Builder().setKeyValue(
						"code", "0011").build());
		System.out.println(JSONObject.toJSONString(list));
	}

	@Test
	public void testValidateOrdereIsEfectiveAndHadnle() {
		Date d = DateUtil.addInteger(new Date(), Calendar.MINUTE, -30);
		String dataStr = DateUtil.dateToString(d, DateUtil.ALL_FOMAT);
		int a = archiveManager.validateOrdereIsEfectiveAndHadnle(dataStr);
		System.out.println(a);
	}

}
