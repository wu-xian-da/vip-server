/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年8月5日-上午10:37:50
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.mapper;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jianfei.core.dto.SalesRankingDto;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年8月5日 上午10:37:50
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class AppOrderArchiveMapperTest {
	@Autowired
	AppOrderArchiveMapper appOrderArchiveMapper;

	/**
	 * Test method for
	 * {@link com.jianfei.core.mapper.AppOrderArchiveMapper#salesRanking(java.util.Map)}
	 * .
	 */
	@Test
	public void testSalesRanking() {

		List<SalesRankingDto> dtos = appOrderArchiveMapper
				.salesRanking(new com.jianfei.core.common.utils.MapUtils.Builder()
						.setKeyValue("pageStart", 1)
						.setKeyValue("pageSize", 10)
						.setKeyValue("startTime", "2016-01-01")
						.setKeyValue("endTime", "2016-09-12")
						.setKeyValue("pid", "110")
						.setKeyValue("airport_id", "333").build());
		System.out.println(JSONObject.toJSONString(dtos));
	}
}
