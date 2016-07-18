/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-上午11:56:15
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.bean.AppConsume;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.common.enu.VipCardState;
import com.jianfei.core.service.base.VipCardManager;

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

	@Autowired
	private VipCardManager vipCardManager;

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

		System.out.println(JSONObject.toJSONString(JedisUtils
				.get("2016-06-27$410$0466996307279830")));
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
		JedisUtils
				.setObject(
						"2016-06-27$110$0466995347947498",
						"{'total':2,'avgNum_back':1.00,'cacheKey':'2016-06-27$110','pcount':1,'back_order_total':1,'avgNum':1.0000,'pid':'110'}",
						0);
		JedisUtils
				.setObject(
						"2016-06-27$110$0466995391093644",
						"{'total':3,'avgNum_back':1.00,'cacheKey':'2016-06-27$110','pcount':1,'back_order_total':1,'avgNum':1.0000,'pid':'110'}",
						0);
		JedisUtils
				.setObject(
						"2016-06-27$120$0466996232803354",
						"{'total':5,'avgNum_back':1.00,'cacheKey':'2016-06-27$120','pcount':1,'back_order_total':0,'avgNum':1.0000,'pid':'120'}",
						0);
		JedisUtils
				.setObject(
						"2016-06-27$130$0466996281393944",
						"{'total':2,'avgNum_back':1.00,'cacheKey':'2016-06-27$130','pcount':1,'back_order_total':0,'avgNum':1.0000,'pid':'130'}",
						0);
		JedisUtils
				.setObject(
						"2016-06-27$370$0466996481520071",
						"{'total':6,'avgNum_back':2.00,'cacheKey':'2016-06-27$110','pcount':1,'back_order_total':2,'avgNum':1.0000,'pid':'370'}",
						0);
		JedisUtils
				.setObject(
						"2016-06-27$410$0466996307279830",
						"{'total':2,'avgNum_back':1.00,'cacheKey':'2016-06-27$110','pcount':1,'back_order_total':0,'avgNum':1.0000,'pid':'410'}",
						0);
		/*
		 * [{pid=110, airportId=0467021250493044, anames=北京站}, {pid=110,
		 * airportId=0466995347947498, anames=北京首都国际机场}, {pid=110,
		 * airportId=0466995391093644, anames=北京南苑机场}, {pid=120,
		 * airportId=0466996232803354, anames=天津滨海国际机场}, {pid=130,
		 * airportId=0466996281393944, anames=石家庄正定国际机场}, {pid=370,
		 * airportId=0466996481520071, anames=青岛流亭国际机场}, {pid=410,
		 * airportId=0466996307279830, anames=郑州新郑国际机场}]
		 */
	}

	@Test
	public void removeKeyTest() {
		System.out.println(JedisUtils.del("2016-06-27$110$0466995347947498"));
		System.out.println(JedisUtils.del("2016-06-27$110$0466995391093644"));
		System.out.println(JedisUtils.del("2016-06-27$120$0466996232803354"));
		System.out.println(JedisUtils.del("2016-06-27$130$0466996281393944"));
		System.out.println(JedisUtils.del("2016-06-27$370$0466996481520071"));
		System.out.println(JedisUtils.del("2016-06-27$410$0466996307279830"));
	}

	@Test
	@Rollback(false)
	public void testIncr() {
		JedisUtils.del("07900690876");
		AppConsume appConsume = new AppConsume();
		appConsume.setCardNo("07900690876");
		appConsume.setConsumeTime(new Date());
		firstServiceHandle(appConsume);
		JedisUtils.del("07900690876");
	}

	@Test
	public void tesst() {
		boolean isOk = vipCardManager.activeAppCard(new MapUtils.Builder()
				.setKeyValue("activeTime", new Date())
				// .setKeyValue("expiryTime", expireDate)
				.setKeyValue("card_state", VipCardState.ACTIVE.getName())// 更改VIP卡状态
				.setKeyValue("cardNo", "07900690876").build());
		System.out.println(isOk);
	}

	/**
	 * 判断卡是否的hi第一次消费<br>
	 * A.如果是<br>
	 * 1.更改卡的第一次消费时间<br>
	 * 2。根据卡的有效期计算卡的到期时间<br>
	 * 3.redis内存计数器对该卡的消费次数统计<br>
	 * B.如果不是<br>
	 * 3.redis内存计数器对该卡的消费次数统计<br>
	 * 
	 * @param appConsume
	 *            卡的信息
	 * @return true:是第一次消费，false:不是第一次消费
	 */
	private boolean firstServiceHandle(AppConsume appConsume) {
		if (ObjectUtils.isEmpty(appConsume)
				|| ObjectUtils.isEmpty(appConsume.getConsumeTime())
				|| StringUtils.isEmpty(appConsume.getCardNo())) {
			throw new IllegalArgumentException("参数不为空为空|消费时间不能为空|卡号不能为空...");
		}
		// 根据卡号从数据库中获取卡的信息
		AppVipcard vipcard = vipCardManager.getVipCardByNo(appConsume
				.getCardNo());
		if (ObjectUtils.isEmpty(vipcard)) {
			throw new IllegalArgumentException("数据查询不到卡号为"
					+ appConsume.getCardNo() + "的信息，无法获取卡的有效期，请排查");
		}
		// 计算有效时间
		Date expireDate = DateUtil.addDays(appConsume.getConsumeTime(),
				vipcard.getValideTime());
		// 消费次数计算
		Long result = JedisUtils.incr(appConsume.getCardNo());
		if (1 == result) {// 第一次消费
			// 更新卡的第一次消费时间和卡有效期到期时间
			if (vipCardManager.activeAppCard(new MapUtils.Builder()
					.setKeyValue("fst", new Date())
					.setKeyValue("cardNo", appConsume.getCardNo())
					.setKeyValue("expiryTime", expireDate).build())) {
				String infoMsg = "卡号为"
						+ appConsume.getCardNo()
						+ "第一次消费时间为:"
						+ DateUtil.dateToString(appConsume.getConsumeTime(),
								DateUtil.ALL_FOMAT);
				System.out.println(infoMsg);
			} else {
				String erroMsg = "卡号为" + appConsume.getCardNo()
						+ "第一次消费信息保存到数据库失败。。。";
				System.out.println(erroMsg);
			}
			return true;
		}
		return false;
	}

}
