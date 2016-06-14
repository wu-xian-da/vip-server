/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年6月1日-下午4:14:06
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.bean.AppConfig;
import com.jianfei.core.service.base.impl.AppConfigManagerImpl;



/**
 * 权益
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author guo.jian   
 * @Title: AppConfigManagerImplTest.java
 * @date 2016年6月14日 上午10:04:37 
 * @Version 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class AppConfigManagerImplTest {
	@Autowired
	private AppConfigManagerImpl appConfigManagerImpl;
	
	//分页测试
	@Test
	public void testpage(){
		appConfigManagerImpl.page(1, 10, null);
	}
	@Test
	public void testSel(){
		AppConfig appConfig = appConfigManagerImpl.selectByPrimaryKey("1");
		System.out.println(appConfig);
	}
}
