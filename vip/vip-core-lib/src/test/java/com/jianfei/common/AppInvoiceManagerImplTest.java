package com.jianfei.common;

/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月15日-下午4:47:57
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.service.base.AppInvoiceManager;



/**
 *
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com
 * @date: 2016年5月15日 下午4:47:57
 * 
 * @version 1.0.0
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class AppInvoiceManagerImplTest {
	@Autowired
	private AppInvoiceManager appInvoiceManagerImpl;
	
	//根据订单号返回发票信息
	@Test
	public void selTest(){
		System.out.println(appInvoiceManagerImpl.selInvoiceInfoByOrderId("dd1001"));
	}
	
}
