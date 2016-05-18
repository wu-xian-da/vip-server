/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-上午10:32:13
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.service.order.OrderService;

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
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/goOrderManagementView")
	public String orderList(){
		return "orders/orderManagement";
	}
	
	//返回所有的订单信息
	@RequestMapping("orderList")
	@ResponseBody
	public Map<Object,Object> list(){
		Map paramsMap = new HashMap();
		PageInfo<AppOrders> pageinfo = orderService.simplePage(1, 10, paramsMap);
		Map<Object,Object> map = new HashMap<Object,Object>();
		List<AppOrders> list = pageinfo.getList();
		for(AppOrders appOrder : list){
			if(appOrder.getOrderState() ==0){//未支付
				appOrder.setOperation("<button class='btn'>查看</button>");
			}else if(appOrder.getOrderState() == 1){//已支付
				appOrder.setOperation("<button class='btn'>查看</button><button class='btn btn-back'>退单申请</button>");
			}else if(appOrder.getOrderState() == 2){
				appOrder.setOperation("<button class='btn'>查看</button><input type='text' value='214' /> <button class='btn btn-confirm'>✓</button><button class='btn btn-close'>✕</button>");
			}else if(appOrder.getOrderState() ==3){//退款
				appOrder.setOperation("<button class='btn'>查看</button><button class='btn btn-refund' onclick='onRefund(2)'>退款</button>");
			}
		}
		map.put("total", pageinfo.getTotal());
		map.put("rows", list);
		return map;
		
	}
}	
