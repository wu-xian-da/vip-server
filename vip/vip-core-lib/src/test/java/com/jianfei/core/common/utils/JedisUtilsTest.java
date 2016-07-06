/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-上午11:56:15
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.enu.MsgType;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 上午11:56:15
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml",
		"classpath:spring-context-jedis.xml" })
@Transactional
public class JedisUtilsTest {

	@Test
	public void consumer() {
	}

	@Test
	public void producer() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userPhone", "13275601668");
		map.put("userName", "refineli");
		map.put("vipCardNo", "smart0012");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("code", "134");
		m.put("time", "5分钟");
		map.put("msgBody", JSONObject.toJSONString(m));
		map.put("msgType", MsgType.LOGIN);
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#get(java.lang.String)}.
	 */
	@Test
	public void testGet() {
		String rs = JedisUtils.set("test", "测试", 0);
		System.out.println(JedisUtils.get("test"));
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#getObject(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetObject() {
		String rs = JedisUtils.get("2016-06-28$110");
		System.out.println(rs);
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#set(java.lang.String, java.lang.String, int)}
	 * .
	 */
	@Test
	public void testSet() {
		String a = JedisUtils.setObject("2016-06-27$110",
				"{'total':1,'avgNum_back':0.0000,'cacheKey':'2016-06-27$110','pcount':1,'back_order_total':0,'avgNum':1.0000,'pid':'110'}",0);
				
		System.out.println(a);
	}
	
	@Test
	public void removeKeyTest(){
		System.out.println(JedisUtils.del("2016-06-27$410"));
	}

}
