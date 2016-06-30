/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年6月15日-下午2:26:28
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.thirdpart.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.service.thirdpart.MsgInfoManager;
import com.jianfei.core.service.thirdpart.QueueManager;

/**
 *
 * @Description: 消息队列测试
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年6月15日 下午2:26:28
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml",
		"classpath:spring-context-jedis.xml" })
public class QueueManagerImplTest {

	@Autowired
	QueueManager queueManager;

	@Autowired
	private MsgInfoManager msgInfoManager;

	/**
	 * Test method for
	 * {@link com.jianfei.core.service.thirdpart.impl.QueueManagerImpl#processMessage(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testProcessMessage() {
		MessageDto<Map<String, String>> messageDto = queueManager
				.processMessage("QU", QueueManager.SMS_QUEUE_VIP_BAK);
		if (null != messageDto) {
			if (messageDto.isOk()) {
				LoggerFactory.getLogger(getClass()).info(
						JSONObject.toJSONString(messageDto));
			} else {
				LoggerFactory.getLogger(getClass()).error(
						JSONObject.toJSONString(messageDto));
			}
		}
	}

	@Test
	public void testProcedure() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userPhone", "13275601668");
		map.put("msgType", "005");
		map.put("userName", "refineli");
		map.put("vipCardNo", "07998371917");
		Map<String, String> m = new HashMap<String, String>();
		m.put("code", "smart001");
		m.put("time", "10分钟");
		map.put("msgBody", JSONObject.toJSONString(m));
		JedisUtils.lpushString("QU", JSONObject.toJSONString(map));
	}

	@Test
	public void sendMsg() {
		boolean isOk = msgInfoManager.sendMsgInfo("13275601668",
				"hello kitty...");
		System.out.println(isOk);
	}
	

}
