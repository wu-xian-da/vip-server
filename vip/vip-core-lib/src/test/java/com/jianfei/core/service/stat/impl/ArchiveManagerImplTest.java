/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午5:16:33
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.stat.impl;

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
	}

}
