package com.jianfei.core.service.thirdpart.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jianfei.core.bean.AppConsume;
import com.jianfei.core.dto.AirportEasyUseInfo;
import com.jianfei.core.service.thirdpart.AirportEasyManager;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
public class AirportEasyManagerTest {
	@Autowired
	private AirportEasyManager airportEasyManager;
	
	/**
	 * 根据卡号查询核销记录
	 */
	@Test
	public void testReadDisCodeData() {
		AirportEasyUseInfo airportEasyUseInfo = airportEasyManager.readDisCodeData("86846443442");
		List<AppConsume> list = airportEasyUseInfo.getConsumeList();
		for(AppConsume appConsume :list){
			System.out.println(appConsume);
		}
		
	}

}
