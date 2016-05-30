/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午5:30:48
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.StringUtils;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 下午5:30:48
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
public class ArchiveMapperTest {

	@Autowired
	private ArchiveMapper archiveMapper;

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.ArchiveMapper#masterTotal(java.util.Map)}.
	 */
	@Test
	public void testMasterTotal() {
		archiveMapper.masterTop(new MapUtils.Builder().build());
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.ArchiveMapper#masterTop(java.util.Map)}.
	 */
	@Test
	public void testMasterTop() {
		archiveMapper.masterTop(DateUtil.getDelayDate(1));
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.ArchiveMapper#masterDraw(java.util.Map)}.
	 */
	@Test
	public void testMasterDraw() {
		Map<String, Object> lastMoth = DateUtil.getDelayDate(1);
		List<Map<String, Object>> draw1 = archiveMapper.masterDraw(lastMoth);
		Map<Object, Object> draw1Map = new HashMap<Object, Object>();
		for (Map<String, Object> m1 : draw1) {
			draw1Map.put(m1.get("province"), m1.get("order_num"));
		}
		System.out.println(draw1Map.keySet().toString());
		System.out.println(draw1Map.values().toString());
		String draw1Province = StringUtils.join(draw1Map.keySet(), ",");
		System.out.println(draw1Province);
		String draw1Num = StringUtils.join(draw1Map.values(), ",");
		System.out.println(draw1Num);
	}

	@Test
	public void testBaseDailyExtract() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("yesterday", "2016-05-29");
		archiveMapper.baseDailyExtract(map);
	}
}
