/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月5日-下午3:30:41
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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

	/**
	 * Test method for
	 * {@link com.jianfei.core.service.base.PickUpManager#resultMapList(java.util.Map)}
	 * .
	 */
	@Test
	public void testResultMapList() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.service.base.PickUpManager#updateState(java.util.Map)}
	 * .
	 */
	@Test
	public void testUpdateState() {
		fail("Not yet implemented");
	}

}
