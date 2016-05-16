/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月15日-下午3:54:37
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.GloabConfig;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月15日 下午3:54:37
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.BaseMapper#save(java.lang.Object)}.
	 */
	@Test
	public void testSaveT() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.BaseMapper#save(java.util.List)}.
	 */
	@Test
	public void testSaveListOfT() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.BaseMapper#saveMaps(java.util.List)}.
	 */
	@Test
	public void testSaveMaps() {
		userMapper.getUserByName("refineli");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.BaseMapper#update(java.lang.Object)}.
	 */
	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.BaseMapper#delete(java.lang.Object)}.
	 */
	@Test
	public void testDelete() {
		SimpleHash simpleHash = new SimpleHash("md5","123456",
				"refineli",1);
		System.out.println(simpleHash.toString());
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.BaseMapper#get(java.util.Map)}.
	 */
	@Test
	public void testGet() {
		PageHelper.startPage(1, 5);
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> list = userMapper.get(map);
		for (User user : list) {
			System.out.println(user.getName());
		}
	}

}
