/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月15日-下午4:47:57
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.jianfei.core.bean.Resource;
import com.jianfei.core.bean.Role;
import com.jianfei.core.common.utils.MapUtils;
import com.jianfei.core.service.sys.SystemService;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月15日 下午4:47:57
 * 
 * @version 1.0.0
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class RoleMapperTest {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private SystemService systemService;

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
	public void testSaveListOfMapOfStringObject() {
		fail("Not yet implemented");
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
	 * Test method for {@link com.jianfei.core.mapper.BaseMapper#delete(long)}.
	 */
	@Test
	public void testDelete() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String[] strings = "1,3,6,7,8,9,2".split(",");
		for (String str : strings) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleId",12l);
			map.put("resourceId",12l);
			list.add(map);
		}
		roleMapper.batchInsertRoleResource(list);
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.BaseMapper#get(java.util.Map)}.
	 */
	@Test
	public void testGet() {
		PageHelper.startPage(1, 3);
		List<Role> roles = systemService.getRoleMapper().get(
				new MapUtils.Builder().setKeyValue("name", "").build());
		for(Role role:roles){
			System.out.println(role.getName());
		}
		System.out.println(JSONObject.toJSONString(roles));
	}

}
