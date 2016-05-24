/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月19日-下午2:19:11
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.thirdpart.impl;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.bean.AriPort;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.service.base.AriPortService;
import com.jianfei.core.service.thirdpart.PreCreateResult;
import com.jianfei.core.service.thirdpart.ThirdPayManager;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月19日 下午2:19:11
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class AliPayManagerImplTest {

	@Autowired
	private ThirdPayManager aliPayManager;




	@Test
	public void testTradePrecreate() {
		PreCreateResult result = aliPayManager.tradePrecreate();
		Assert.assertEquals("120", result.getTradeNo());
	}


}
