/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月15日-下午4:47:57
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.thirdpart.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

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
import com.jianfei.core.common.pay.PreCreateResult;
import com.tencent.protocol.native_protocol.NativePayReqData;

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
public class AlipayManagerImplTest {

	//@Autowired
	//private AlipayPayManagerImpl alipayManagerImpl;
	@Autowired 
	WechatPayManagerImpl wechatiPayManager;
	
	@Test
	public void testGet() {
		//alipayManagerImpl.tradeQuery("abc123");
//		File fff = new File("apiclient_cert.p12");
//		fff.getAbsoluteFile();
//		fff.getName();
//		NativePayReqData req = new NativePayReqData("","空港卡","attach","ba00ecaaf5eb69e744692e9f0fded636",198000,
//				"","192.168.199.200","20160601152528","20160602155528","",
//				"detail","http://121.42.199.169","NATIVE","1001","","");
		
		NativePayReqData req = new NativePayReqData("","kongcard","","ba00ecaaf5eb69e744692e9f0fded636",198000,
				"","192.168.199.200","","","",
				"","http://121.42.199.169","NATIVE","1001","","");
		PreCreateResult result = wechatiPayManager.tradePrecreate(req);
		result.toString();
		//assertEquals("0", result.getCode());
	}

}
