/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午2:58:41
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.jianfei.core.bean.Role;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.MapUtils;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月23日 下午2:58:41
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
	@Autowired
	private RoleMapper roelMapper;

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.UserMapper#getUserByName(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetUserByName() {
		userMapper.getUserByName("admin");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.UserMapper#deleteRolesFromUser(java.lang.Long)}
	 * .
	 */
	@Test
	public void testDeleteRolesFromUser() {
		List<Role> roles = roelMapper.selectRoleByUserId(50l);
		if (!CollectionUtils.isEmpty(roles)) {
			System.out.println(roles.get(0).getUrl());
		}
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.UserMapper#batchInsertUserRole(java.util.List)}
	 * .
	 */
	@Test
	public void testBatchInsertUserRole() {
		PageHelper.startPage(1, 2);
		List<User> list = userMapper.get(new MapUtils.Builder().build());
		for (User user : list) {
			System.out.println(JSONObject.toJSONString(user));
		}
	}

}
