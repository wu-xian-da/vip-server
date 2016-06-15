/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年6月15日-下午2:26:28
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.thirdpart.impl;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.service.thirdpart.QueueManager;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年6月15日 下午2:26:28
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml",
		"classpath:spring-context-jedis.xml" })
@Transactional
public class QueueManagerImplTest {

	@Autowired
	QueueManager queueManager;

	/**
	 * Test method for
	 * {@link com.jianfei.core.service.thirdpart.impl.QueueManagerImpl#processMessage(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testProcessMessage() {
		MessageDto<Map<String, String>> messageDto = queueManager
				.processMessage("SMS_QUEUES_VIP", "SMS_QUEUES_VIP_BAK");
		System.out.println(JSONObject.toJSONString(messageDto));
	}

}
