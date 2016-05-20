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
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.dto.OrderShowInfoDto;
import com.jianfei.core.service.order.impl.OrderManagerImpl;
import com.jianfei.core.service.thirdpart.impl.MsgInfoManagerImpl;

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
	private OrderManagerImpl orderManagerImpl;
	@Autowired
	private MsgInfoManagerImpl msgInfoManagerImpl;
	
	/*
	 * 跳转到订单列表页面
	 */
	@RequestMapping("/goOrderManagementView")
	public String orderList(){
		return "orders/orderManagement";
	}
	
	/*
	 * 根据订单号查询订单详细信息
	 */
	@RequestMapping(value="/returnOrderDetailInfoByOrderId")
	public String returnOrderDetailInfoByOrderId(String orderId){
		System.out.println("orderId="+orderId);
		return "orders/orderDetail.jsp";
	}
	/*
	 * 分页查询
	 */
	@RequestMapping("orderList")
	@ResponseBody
	public Map<Object,Object> list(){
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		PageInfo<OrderShowInfoDto> pageinfo = orderManagerImpl.simplePage(1, 10, paramsMap);
		Map<Object,Object> map = new HashMap<Object,Object>();
		List<OrderShowInfoDto> list = pageinfo.getList();
		for(OrderShowInfoDto appOrder : list){
			if(appOrder.getOrderState() ==0){//未支付
				appOrder.setOrderStateName("未支付");
				appOrder.setOperation("<button class='btn'><a href='/returnOrderDetailInfoByOrderId?orderId=1'>查看</a></button>");
			}else if(appOrder.getOrderState() == 1){//已支付
				appOrder.setOrderStateName("已支付");
				appOrder.setOperation("<button class='btn'><a href='order-details.html?id=1'>查看</a></button><button class='btn btn-back' onclick='onRefundApplication(1,this)'>退单申请</button>");
			}else if(appOrder.getOrderState() == 2){
				appOrder.setOrderStateName("正在审核");
				appOrder.setOperation("<button class='btn'>查看</button><input type='text' value='214' /> <button class='btn btn-confirm'>✓</button><button class='btn btn-close'>✕</button>");
			}else if(appOrder.getOrderState() ==3){//退款
				appOrder.setOrderStateName("审核通过");
				appOrder.setOperation("<button class='btn'>查看</button><button class='btn btn-refund' onclick='onRefund(2)'>退款</button>");
			}else if(appOrder.getOrderState() ==4){//退款成功
				appOrder.setOrderStateName("已退款");
				appOrder.setOperation("<button class='btn' onclick='showOrderDetailInfo()'>查看</button>");
			}
		}
		map.put("total", pageinfo.getTotal());
		map.put("rows", list);
		return map;
		
	}
	
	/*
	 * 退单申请,给用户发送短信验证码，并将短信验证码回显
	 */
	@RequestMapping(value="/applyBackCard")
	@ResponseBody
	public String applyBackCard(String orderId,String phone){
		//1、改变订单状态
		//2、发送验证码
		String smsCode = msgInfoManagerImpl.sendAndGetValidateCode(phone, MsgType.BACK_CARD);
		return "";
	}
	
	/*
	 * 审核通过
	 */
	@RequestMapping(value="/auditPass")
	public String auditPass(){
		return "";
	}
	
	/*
	 * 审核不通过
	 */
	@RequestMapping(value="/auditNotPass")
	public String auditNotPass(){
		return "";
	}
	
	/*
	 * 退款
	 */
	@RequestMapping(value="/refundMoney")
	public String refundMoney(){
		return "";
	}
}	
