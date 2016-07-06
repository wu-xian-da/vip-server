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
		Object obj = JedisUtils.get("2016-06-27$370$0466996481520071");
		System.out.println(obj);
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#set(java.lang.String, java.lang.String, int)}
	 * .
	 */
	@Test
	public void testSet() {
		JedisUtils.setObject("2016-06-27$110$0466995347947498",
				"{'total':2,'avgNum_back':1.00,'cacheKey':'2016-06-27$110','pcount':1,'back_order_total':1,'avgNum':1.0000,'pid':'110'}",0);
		JedisUtils.setObject("2016-06-27$110$0466995391093644",
				"{'total':3,'avgNum_back':1.00,'cacheKey':'2016-06-27$110','pcount':1,'back_order_total':1,'avgNum':1.0000,'pid':'110'}",0);         
		JedisUtils.setObject("2016-06-27$120$0466996232803354",
				"{'total':5,'avgNum_back':1.00,'cacheKey':'2016-06-27$120','pcount':1,'back_order_total':0,'avgNum':1.0000,'pid':'120'}",0);
		JedisUtils.setObject("2016-06-27$130$0466996281393944",
				"{'total':2,'avgNum_back':1.00,'cacheKey':'2016-06-27$130','pcount':1,'back_order_total':0,'avgNum':1.0000,'pid':'130'}",0);
		JedisUtils.setObject("2016-06-27$370$0466996481520071",
				"{'total':6,'avgNum_back':2.00,'cacheKey':'2016-06-27$110','pcount':1,'back_order_total':2,'avgNum':1.0000,'pid':'370'}",0);
		JedisUtils.setObject("2016-06-27$410$0466996307279830",
				"{'total':2,'avgNum_back':1.00,'cacheKey':'2016-06-27$110','pcount':1,'back_order_total':0,'avgNum':1.0000,'pid':'410'}",0);
		/*
		[{pid=110, airportId=0467021250493044, anames=北京站}, 
		 {pid=110, airportId=0466995347947498, anames=北京首都国际机场},
		 {pid=110, airportId=0466995391093644, anames=北京南苑机场}, 
		 {pid=120, airportId=0466996232803354, anames=天津滨海国际机场}, 
		 {pid=130, airportId=0466996281393944, anames=石家庄正定国际机场}, 
		 {pid=370, airportId=0466996481520071, anames=青岛流亭国际机场}, 
		 {pid=410, airportId=0466996307279830, anames=郑州新郑国际机场}]
		 */
	}
	
	@Test
	public void removeKeyTest(){
		System.out.println(JedisUtils.del("2016-06-27$110$0466995347947498"));
		System.out.println(JedisUtils.del("2016-06-27$110$0466995391093644"));
		System.out.println(JedisUtils.del("2016-06-27$120$0466996232803354"));
		System.out.println(JedisUtils.del("2016-06-27$130$0466996281393944"));
		System.out.println(JedisUtils.del("2016-06-27$370$0466996481520071"));
		System.out.println(JedisUtils.del("2016-06-27$410$0466996307279830"));
	}

}
