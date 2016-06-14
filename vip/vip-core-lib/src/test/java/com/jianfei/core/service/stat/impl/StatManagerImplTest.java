/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月25日-下午5:16:33
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.stat.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jianfei.core.bean.AppOrderArchive;
import com.jianfei.core.dto.CharData;


/**
 * 归档表数据查询
 * @Description: TODO
 * @author guo.jian   
 * @Title: StatManagerImplTest.java
 * @date 2016年6月8日 下午5:30:27 
 * @Version 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml",
		"classpath:spring-context-jedis.xml" })
@Transactional
public class StatManagerImplTest {
	@Autowired
    private StatManagerImpl statManagerImpl;
	
	/**
	 *  根据业务人员id号查询某个时间段内的销售情况
	 */
	@Test
	public void selectCharDataByUserIdTest(){
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("saleNo", 3);
		paraMap.put("beginTime", "2016-02-02");
		paraMap.put("endTime", "2016-06-10");
		List<CharData> list = statManagerImpl.selectCharDataByUserId(paraMap);
		for(CharData a : list){
			System.out.println(a);
		}
		System.out.println("size="+list.size());
	}
}
