/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-上午11:56:15
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.bean.Resource;
import com.jianfei.core.common.cache.JedisUtils;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月25日 上午11:56:15
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml",
		"classpath:spring-context-jedis.xml" })
@Transactional
public class JedisUtilsTest {

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#get(java.lang.String)}.
	 */
	@Test
	public void testGet() {
		JedisUtils.del("refineli");
		List<Resource> resources = new ArrayList<Resource>();
		resources.add(new Resource());
		System.out.println(JedisUtils.get("refineli"));
		JedisUtils.setObject("sss", resources, 10);
		resources = (List<Resource>) JedisUtils.getObject("sss");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#getObject(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetObject() {
		System.out.println(JedisUtils.getObject("2016-04-09$1"));
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#set(java.lang.String, java.lang.String, int)}
	 * .
	 */
	@Test
	public void testSet() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#setObject(java.lang.String, java.lang.Object, int)}
	 * .
	 */
	@Test
	public void testSetObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#getList(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetList() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#getObjectList(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetObjectList() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#setList(java.lang.String, java.util.List, int)}
	 * .
	 */
	@Test
	public void testSetList() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#setObjectList(java.lang.String, java.util.List, int)}
	 * .
	 */
	@Test
	public void testSetObjectList() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#listAdd(java.lang.String, java.lang.String[])}
	 * .
	 */
	@Test
	public void testListAdd() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#listObjectAdd(java.lang.String, java.lang.Object[])}
	 * .
	 */
	@Test
	public void testListObjectAdd() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#getSet(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetSet() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#getObjectSet(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetObjectSet() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#setSet(java.lang.String, java.util.Set, int)}
	 * .
	 */
	@Test
	public void testSetSet() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#setObjectSet(java.lang.String, java.util.Set, int)}
	 * .
	 */
	@Test
	public void testSetObjectSet() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#setSetAdd(java.lang.String, java.lang.String[])}
	 * .
	 */
	@Test
	public void testSetSetAdd() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#setSetObjectAdd(java.lang.String, java.lang.Object[])}
	 * .
	 */
	@Test
	public void testSetSetObjectAdd() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#getMap(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetMap() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#getObjectMap(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetObjectMap() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#setMap(java.lang.String, java.util.Map, int)}
	 * .
	 */
	@Test
	public void testSetMap() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#setObjectMap(java.lang.String, java.util.Map, int)}
	 * .
	 */
	@Test
	public void testSetObjectMap() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#mapPut(java.lang.String, java.util.Map)}
	 * .
	 */
	@Test
	public void testMapPut() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#mapObjectPut(java.lang.String, java.util.Map)}
	 * .
	 */
	@Test
	public void testMapObjectPut() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#mapRemove(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testMapRemove() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#mapObjectRemove(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testMapObjectRemove() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#mapExists(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testMapExists() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#mapObjectExists(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testMapObjectExists() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#del(java.lang.String)}.
	 */
	@Test
	public void testDel() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#delObject(java.lang.String)}
	 * .
	 */
	@Test
	public void testDelObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#exists(java.lang.String)}
	 * .
	 */
	@Test
	public void testExists() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#existsObject(java.lang.String)}
	 * .
	 */
	@Test
	public void testExistsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#getResource()}.
	 */
	@Test
	public void testGetResource() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#returnBrokenResource(redis.clients.jedis.Jedis)}
	 * .
	 */
	@Test
	public void testReturnBrokenResource() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#returnResource(redis.clients.jedis.Jedis)}
	 * .
	 */
	@Test
	public void testReturnResource() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#getBytesKey(java.lang.Object)}
	 * .
	 */
	@Test
	public void testGetBytesKey() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#toBytes(java.lang.Object)}
	 * .
	 */
	@Test
	public void testToBytes() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.cache.JedisUtils#toObject(byte[])}.
	 */
	@Test
	public void testToObject() {
		fail("Not yet implemented");
	}

}
