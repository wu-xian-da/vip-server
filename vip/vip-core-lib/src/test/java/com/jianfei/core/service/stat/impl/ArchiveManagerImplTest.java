/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午5:16:33
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.stat.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
public class ArchiveManagerImplTest {

	@Autowired
	private ArchiveManager archiveManager;

	/**
	 * Test method for
	 * {@link com.jianfei.core.service.stat.impl.ArchiveManagerImpl#masterTop(java.util.Map)}
	 * .
	 */
	@Test
	public void testMasterTop() {
		List<Map<String, Object>> map = archiveManager
				.masterTop(new MapUtils.Builder().setKeyValue("start",
						new Date()).build());
		System.out.println(JSONObject.toJSONString(map));
	}

	@Test
	public void testZhuGuanAllAirPort() {
		Map<String, Object> lastMoth = DateUtil.getDelayDate(1);
		List<String> list = new ArrayList<String>();
		list.add("068ccfa912914ad9bd1b72a4c0b8b879");
		list.add("c76c31bad8b647d9a9a44b64a86f56b6");
		list.add("43778dcfcb4542ce80b3588dd759e530");
		lastMoth.put("ariportIds", list);
		List<Map<String, Object>> maps = archiveManager.zhuGuanAllAirPort(
				lastMoth, "");
		if (null == maps) {
			System.out.println("....");
		}
	}

	@Test
	public void testZhuGuanDraw() {
		Map<String, Object> lastMoth = DateUtil.getDelayDate(1);
		List<String> list = new ArrayList<String>();
		list.add("068ccfa912914ad9bd1b72a4c0b8b879");
		list.add("c76c31bad8b647d9a9a44b64a86f56b6");
		list.add("43778dcfcb4542ce80b3588dd759e530");
		lastMoth.put("ariportIds", list);
		List<Map<String, Object>> maps = archiveManager.zhuGuanDraw(lastMoth,
				"");
		System.out.println(maps);
	}

	@Test
	public void testDateProvinceIdRedisCache() {
		Map<String, Object> mapCon = new HashMap<String, Object>();
		mapCon.put("currentTime", "2016-05-04");
		List<Map<String, Object>> maps = archiveManager
				.dateProvinceIdRedisCache(mapCon);
		for (Map<String, Object> map : maps) {
			System.out.println(JSONObject.toJSONString(map));
		}
	}

	@Test
	public void testDateProvinceIdAirport() {
		Map<String, Object> mapCon = new HashMap<String, Object>();
		mapCon.put("currentTime", "2016-06-27");
		List<Map<String, Object>> maps = archiveManager
				.dateProvinceIdApportIds(mapCon);
		for (Map<String, Object> map : maps) {
			System.out.println(JSONObject.toJSONString(map));
		}
	}

	@Test
	public void cacheDate() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentTime", "2016-06-28");
		List<Map<String, Object>> datePid = archiveManager
				.dateProvinceIdRedisCache(map);
		System.out.println("~~~~~~~~~~~~datePid->"
				+ JSONObject.toJSONString(datePid));
		List<Map<String, Object>> datePidAid = archiveManager
				.dateProvinceIdApportIds(map);
		System.out.println("~~~~~~~~~~~~datePidAid->"
				+ JSONObject.toJSONString(datePidAid));
	}

	@Test
	public void testDailyOrderArchice() {
		List<String> list = dateList();
		for (String str : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			Date date = DateUtil.getDate(str, "yyyy-MM-dd");
			map.put("maxTime", DateUtil.dateToString(date, "yyyy-MM-dd"));
			archiveManager.baseDailyExtract(map);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
						"code", "900").build());
		System.out.println(JSONObject.toJSONString(list));
	}

}
