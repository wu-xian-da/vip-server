/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午5:30:48
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.common.utils.MapUtils;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 下午5:30:48
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class ArchiveMapperTest {

	@Autowired
	private ArchiveMapper archiveMapper;

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.ArchiveMapper#masterTotal(java.util.Map)}.
	 */
	@Test
	public void testMasterTotal() {
		archiveMapper.masterTop(new MapUtils.Builder().build());
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.ArchiveMapper#masterTop(java.util.Map)}.
	 */
	@Test
	public void testMasterTop() {
		archiveMapper.masterTop(new MapUtils.Builder().build());
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.ArchiveMapper#masterDraw(java.util.Map)}.
	 */
	@Test
	public void testMasterDraw() {
		fail("Not yet implemented");
	}

}
