/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-上午10:32:13
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单管理
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月18日 上午10:32:13 
 * 
 * @version 1.0.0
 *
 */
@Controller
public class OrderController {
	@RequestMapping("/goOrderManagementView")
	public String orderList(){
		return "orders/orderManagement";
	}
	
	//返回所有的订单信息
	@RequestMapping("orderList")
	@ResponseBody
	public Map<Object,Object> list(){
		Map<Object,Object> map = new HashMap<Object,Object>();
		return map;
		
	}
}	
