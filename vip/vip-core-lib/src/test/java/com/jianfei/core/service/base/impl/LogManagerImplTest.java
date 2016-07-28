/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月27日-下午6:28:23
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.service.LogManager;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月27日 下午6:28:23
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class LogManagerImplTest {

	@Autowired
	private LogManager aogManager;

	/**
	 * Test method for
	 * {@link com.jianfei.core.service.base.impl.LogManagerImpl#logList(java.util.Map)}
	 * .
	 */
	@Test
	public void testLogList() {
		aogManager.logList(new MapUtils.Builder().build());
	}

}
