/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月6日-下午12:49:08
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.sys.impl;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.mapper.UserMapper;
import com.jianfei.core.service.base.AppAirportTransManager;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月6日 下午12:49:08
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
public class UserManagerImplTest {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AppAirportTransManager appAirportTransManager;

	/**
	 * Test method for
	 * {@link com.jianfei.core.service.sys.impl.UserManagerImpl#getUserByName(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetUserByName() {
		userMapper.getUserByName("iadmin");
	}

	@Test
	public void testPickupInfo() {
		System.out.println(JSONObject.toJSONString(appAirportTransManager
				.pickupLook("0467769561876078")));
	}

}
