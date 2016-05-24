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
	private AriPortService<AriPort> ariPortService;

	/**
	 * Test method for
	 * {@link com.jianfei.core.service.base.impl.AriPortServiceImpl#save(com.jianfei.core.bean.AriPort)}
	 * .
	 */
	@Test
	public void testSave() {
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.service.base.impl.AriPortServiceImpl#update(com.jianfei.core.bean.AriPort)}
	 * .
	 */
	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.service.base.impl.AriPortServiceImpl#get(java.util.Map)}
	 * .
	 */
	@Test
	public void testGet() {
		MessageDto<List<AriPort>> messageDto = ariPortService
				.get(new MapUtils.Builder().setKeyValue("id", "1111").build());
	}

}
