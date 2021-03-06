/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月15日-下午3:56:46
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianfei.core.bean.Menu;
import com.jianfei.core.bean.Resource;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月15日 下午3:56:46
 * 
 * @version 1.0.0
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class ResourceMapperTest {

	@Autowired
	private ResourceMapper resourceMapper;

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.BaseMapper#save(java.lang.Object)}.
	 */
	@Test
	public void testSaveT() {
		Resource resource = new Resource();
		resource.setName("refineli");
		resourceMapper.save(resource);
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.BaseMapper#save(java.util.List)}.
	 */
	@Test
	public void testSaveListOfT() {
		List<Resource> resources = resourceMapper.selectChildResorceByPid(1l);
		System.out.println(resources.size());
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.BaseMapper#saveMaps(java.util.List)}.
	 */
	@Test
	public void testSaveMaps() {
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.BaseMapper#update(java.lang.Object)}.
	 */
	@Test
	public void testUpdate() {
		Resource resource = new Resource();
		resource.setName("refineli");
		resource.setId(22l);
		resourceMapper.update(resource);
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.BaseMapper#delete(java.lang.Object)}.
	 */
	@Test
	public void testDelete() {
		resourceMapper.delete(1l);
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.BaseMapper#get(java.util.Map)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGet() throws Exception {
		List<Resource> list = resourceMapper.get(new HashMap<String, Object>());
		List<Menu> menus = com.jianfei.core.bean.Menu.getSecondMenu(list);
		System.out.println(JSONObject.toJSONString(menus));

	}
}
