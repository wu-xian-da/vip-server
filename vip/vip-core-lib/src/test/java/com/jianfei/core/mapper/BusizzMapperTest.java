/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午3:46:30
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.bean.User;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.MapUtils;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月23日 下午3:46:30
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class BusizzMapperTest {

	@Autowired
	private BusizzMapper busizzMapper;

	@Test
	public void test() {
		User user = new User();
		user.setJob("厂长");
		user.setName("tom");
		user.setCode("2344556f");
		user.setSex(0);
		user.setPassword("12321321");
		user.setPhone("1232333");
		user.setUserType(GloabConfig.BUSSNISS_USER);
		busizzMapper.save(user);
	}

	@Test
	public void testGet() {
		List<User> list = busizzMapper.get(new MapUtils.Builder().build());
		for(User user:list){
			System.out.println(user.getName());
		}
	}

}
