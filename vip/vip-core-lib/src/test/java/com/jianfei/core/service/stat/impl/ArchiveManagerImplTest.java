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
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class ArchiveManagerImplTest {

	@Autowired
	private ArchiveManager archiveManager;

	/**
	 * Test method for
	 * {@link com.jianfei.core.service.stat.impl.ArchiveManagerImpl#masterTotal(java.util.Map)}
	 * .
	 */
	@Test
	public void testMasterTotal() {
		Map<String, Object> map = archiveManager
				.masterTotal(new MapUtils.Builder()
						.setKeyValue("start", "2016-01-02")
						.setKeyValue("end", new Date()).build());
		System.out.println(JSONObject.toJSONString(map));
	}

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

	/**
	 * Test method for
	 * {@link com.jianfei.core.service.stat.impl.ArchiveManagerImpl#masterDraw(java.util.Map)}
	 * .
	 */
	@Test
	public void testzhuGuanTotal() {
		Map<String, Object> lastMoth = DateUtil.getDelayDate(1);
		List<String> list = new ArrayList<String>();
		list.add("068ccfa912914ad9bd1b72a4c0b8b879");
		list.add("c76c31bad8b647d9a9a44b64a86f56b6");
		list.add("43778dcfcb4542ce80b3588dd759e530");
		lastMoth.put("ariportIds", list);
		Map<String, Object> map = archiveManager.zhuGuanTotal(lastMoth, "");
		if (null == map) {
			System.out.println(".......................");
		} else {
			System.out.println(map.get("total"));
		}
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
	public void testSelectOrderMaxDay() {
		Map<String, Object> map = archiveManager.selectOrderMaxDay();
		System.out.println(map.get("maxTime"));
	}

	@Test
	public void testBaseDailyExtract() {
		archiveManager.baseDailyExtract(archiveManager.selectOrderMaxDay());
	}

	@Test
	public void testDailyOrderArchice() {
		archiveManager.baseDailyExtract(archiveManager.selectOrderMaxDay());
	}

}
