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

import org.apache.poi.util.SystemOutLogger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.bean.SysViproom;
import com.jianfei.core.service.base.impl.VipRoomManagerImpl;



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
public class VipRoomManagerImpTest {
	@Autowired
	private VipRoomManagerImpl vipRoomManagerImp;
	
	//分页测试
	@Test
	public void pageListTest(){
		Map<String,Object> params = new HashMap<String,Object>();
		//params.put("searchContent", "北京机场11");
		PageInfo<SysViproom> pageinfo = vipRoomManagerImp.simplePage(1, 2, params);
		System.out.println("总的条数："+pageinfo.getTotal());
		List<SysViproom> list = pageinfo.getList();
		for(SysViproom SysViproom : list){
			System.out.println(SysViproom);
		}
	}
	
	//逻辑删除vip室信息
	@Test
	public void delVipRoom(){
		vipRoomManagerImp.delVipRoom("1001");
	}
	//添加vip室信息
	@Test
	public void addvipRoom(){
		SysViproom room = new SysViproom();
		room.setViproomId("2852ejeafafd-ajfafyhhua");
		room.setViproomName("test贵宾室");
		room.setSingleconsumeMoney(200f);
		room.setProvince("1001");
		room.setAirportId("100002002");
		room.setRemark1("免费水果");
		int flag = vipRoomManagerImp.addVipRoom(room);
		System.out.println("flag="+flag);
	}
	
	//返回vip室信息
	@Test
	public void selroomTest(){
		System.out.println(vipRoomManagerImp.selVipRoomById("1001"));
	}
	
}
