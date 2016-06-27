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
		String rs = JedisUtils.get("MSG:TEMPLATE001");
		System.out.println(rs);
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#set(java.lang.String, java.lang.String, int)}
	 * .
	 */
	@Test
	public void testSet() {
		//2016-05-03$120$0465796345470881
		//北京 -测试场站
		// 2016-05-02$110$0465785487752808   {'total':10,'avgNum':5,'back_order_total':2}
		// 2016-05-03$110$0465785487752808   {'total':12,'avgNum':6,'back_order_total':5}
		// 2016-05-04$110$0465785487752808   {'total':6,'avgNum':3,'back_order_total':1}
		JedisUtils.setObject("2016-05-02$110$0465785487752808", "{'total':10,'avgNum':5,'back_order_total':2}",0);
		JedisUtils.setObject("2016-05-03$110$0465785487752808", "{'total':12,'avgNum':6,'back_order_total':5}",0);
		JedisUtils.setObject("2016-05-04$110$0465785487752808","{'total':6,'avgNum':3,'back_order_total':1}",0);
		
		//北京-北京首都国际机场
		// 2016-05-02$110$0465798272992435 {'total':10,'avgNum':5,'back_order_total':2}
		// 2016-05-03$110$0465798272992435 {'total':6,'avgNum':10,'back_order_total':3}
		// 2016-05-04$110$0465798272992435 {'total':8,'avgNum':8,'back_order_total':3}
		JedisUtils.setObject("2016-05-02$110$0465798272992435","{'total':10,'avgNum':5,'back_order_total':2}",0);
		JedisUtils.setObject("2016-05-03$110$0465798272992435","{'total':6,'avgNum':10,'back_order_total':3}",0);
		JedisUtils.setObject("2016-05-04$110$0465798272992435","{'total':8,'avgNum':8,'back_order_total':3}",0);
		
		//天津 - 天津场站1
		//2016-05-02$120$0465796345470881  {'total':20,'avgNum':3,'back_order_total':1}
		//2016-05-02$120$0465796345470881  {'total':23,'avgNum':5,'back_order_total':1}
		//2016-05-02$120$0465796345470881  {'total':25,'avgNum':6,'back_order_total':1}
		JedisUtils.setObject("2016-05-02$120$0465796345470881","{'total':20,'avgNum':3,'back_order_total':1}",0);
		JedisUtils.setObject("2016-05-03$120$0465796345470881","{'total':23,'avgNum':5,'back_order_total':1}",0);
		JedisUtils.setObject("2016-05-04$120$0465796345470881","{'total':25,'avgNum':6,'back_order_total':1}",0);
		

		//天津 - 天津场站2
		//2016-05-02$120$0465798075207510  {'total':20,'avgNum':3,'back_order_total':1}
		//2016-05-02$120$0465798075207510  {'total':23,'avgNum':5,'back_order_total':1}
		//2016-05-02$120$0465798075207510  {'total':25,'avgNum':6,'back_order_total':1}
		JedisUtils.setObject("2016-05-02$120$0465798075207510","{'total':20,'avgNum':3,'back_order_total':1}",0);
		JedisUtils.setObject("2016-05-03$120$0465798075207510","{'total':23,'avgNum':5,'back_order_total':1}",0);
		JedisUtils.setObject("2016-05-04$120$0465798075207510","{'total':25,'avgNum':6,'back_order_total':1}",0);
		
		//成都
		//2016-05-02$210$0465807419050017 {'total':15,'avgNum':5,'back_order_total':2}
		//2016-05-03$210$0465807419050017 {'total':10,'avgNum':10,'back_order_total':2}
		//2016-05-04$210$0465807419050017 {'total':20,'avgNum':3,'back_order_total':2}
		JedisUtils.setObject("2016-05-02$210$0465807419050017","{'total':15,'avgNum':5,'back_order_total':2}",0);
		JedisUtils.setObject("2016-05-03$210$0465807419050017","{'total':10,'avgNum':10,'back_order_total':2}",0);
		JedisUtils.setObject("2016-05-04$210$0465807419050017","{'total':20,'avgNum':3,'back_order_total':2}",0);
		
		Object rs = JedisUtils.getObject("2016-05-02$110$0465785487752808");
		System.out.println(rs);
	}

}
