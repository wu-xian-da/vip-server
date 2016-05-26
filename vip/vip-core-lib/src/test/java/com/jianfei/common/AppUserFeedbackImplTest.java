package com.jianfei.common;

/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月15日-下午4:47:57
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppUserFeedback;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.service.base.impl.AppUserFeedbackImpl;
import com.jianfei.core.service.base.impl.VipCardManagerImpl;



/**
 *
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com
 * @date: 2016年5月15日 下午4:47:57
 * 
 * @version 1.0.0
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence.xml" })
@Transactional
public class AppUserFeedbackImplTest {
	@Autowired
	private AppUserFeedbackImpl appUserFeedbackImpl;
	
	//分页测试
	@Test
	public void pageListTest(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("feedbackState", 2);
		PageInfo<AppUserFeedback> pageinfo = appUserFeedbackImpl.pageList(1, 2, params);
		System.out.println("总的条数："+pageinfo.getTotal());
		List<AppUserFeedback> list = pageinfo.getList();
		for(AppUserFeedback appUserFeedback : list){
			System.out.println(appUserFeedback);
		}
	}
	
	@Test
	public void delTest(){
		appUserFeedbackImpl.delFeedbackInfoById("1");
	}
	
}
