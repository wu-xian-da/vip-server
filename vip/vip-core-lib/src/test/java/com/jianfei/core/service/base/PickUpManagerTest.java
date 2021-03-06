/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月5日-下午3:30:41
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.bean.AppPicture;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *
 * @Description: 接送机单元
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月5日 下午3:30:41
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class PickUpManagerTest {

	@Autowired
	AppAirportTransManager pickUpManager;

	@Autowired
	private AppPictureManager appPictureManager;

	@Test
	public void testImageSelect() {
		MessageDto<List<AppPicture>> messageDto = appPictureManager
				.get(new MapUtils.Builder().setKeyValue("imagetype", 2).build());
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.service.base.AppAirportTransManager#resultMapList(java.util.Map)}
	 * .
	 */
	@Test
	public void testResultMapList() {
		MessageDto<List<Map<String, Object>>> messageDto = pickUpManager
				.resultMapList(new com.jianfei.core.common.utils.MapUtils.Builder()
						.setKeyValue("pickup_type", 2).build());
		System.out.println(JSONObject.toJSONString(messageDto));
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.service.base.AppAirportTransManager#updateState(java.util.Map)}
	 * .
	 */
	@Test
	public void testUpdateState() {
		pickUpManager
				.updateState(new com.jianfei.core.common.utils.MapUtils.Builder()
						.setKeyValue("submit", 2).setKeyValue("id", 99).build());
	}

}
