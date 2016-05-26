/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午11:15:33
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
 * @date: 2016年5月23日 下午11:15:33
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class AppPictureMapperTest {

	@Autowired
	private AppPictureMapper appPictureMapper;

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.AppPictureMapper#deleteByPrimaryKey(java.lang.Integer)}
	 * .
	 */
	@Test
	public void testDeleteByPrimaryKey() {
		appPictureMapper.get(new MapUtils.Builder().build());
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.AppPictureMapper#insert(com.jianfei.core.bean.AppPicture)}
	 * .
	 */
	@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.AppPictureMapper#insertSelective(com.jianfei.core.bean.AppPicture)}
	 * .
	 */
	@Test
	public void testInsertSelective() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.AppPictureMapper#selectByPrimaryKey(java.lang.Integer)}
	 * .
	 */
	@Test
	public void testSelectByPrimaryKey() {
		appPictureMapper.selectByPrimaryKey(1);
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.AppPictureMapper#updateByPrimaryKeySelective(com.jianfei.core.bean.AppPicture)}
	 * .
	 */
	@Test
	public void testUpdateByPrimaryKeySelective() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.AppPictureMapper#updateByPrimaryKey(com.jianfei.core.bean.AppPicture)}
	 * .
	 */
	@Test
	public void testUpdateByPrimaryKey() {
		fail("Not yet implemented");
	}

}
