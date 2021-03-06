/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月22日-下午3:35:28
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

import com.jianfei.core.service.base.AppCustomerManager;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月22日 下午3:35:28
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class AppCustomerManagerImplTest {

	@Autowired
	private AppCustomerManager appCustomerManager;

	/**
	 * Test method for
	 * {@link com.jianfei.core.service.base.impl.AppCustomerManagerImpl#batchDealMsg(java.lang.String, java.lang.String, org.springframework.ui.Model)}
	 * .
	 */
	@Test
	public void testBatchDealMsg() {
	}

}
