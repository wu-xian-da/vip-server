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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
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
		String orderId = null;
		String phone = null;
		for(OrderShowInfoDto appOrder : list){
			if(appOrder.getOrderState() ==0){
				//未支付
				appOrder.setOrderStateName("未支付");
				appOrder.setOperation("<button class='btn'><a href='/returnOrderDetailInfoByOrderId?orderId=1'>查看</a></button>");
			}else if(appOrder.getOrderState() == 1){
				//已支付
				appOrder.setOrderStateName("已支付");
				orderId = appOrder.getOrderId();
				phone = appOrder.getCustomerPhone();
				JSONObject outData = new JSONObject(); 
				outData.put("orderId", orderId);
				outData.put("opr", "0");
				outData.put("phone", phone);
				
				appOrder.setOperation("<button class='btn'><a href='order-details.html?id=1'>查看</a></button><button class='btn btn-back' onclick='onRefundApplication("+outData+",this)'>退单申请</button>");
			
			}else if(appOrder.getOrderState() == 2){
				appOrder.setOrderStateName("正在审核");
				JSONObject outData = new JSONObject(); 
				float remainMoney = orderManagerImpl.remainMoney(appOrder.getOrderId());
				outData.put("remainMoney", remainMoney);
				outData.put("orderId", appOrder.getOrderId());
				appOrder.setOperation("<button class='btn'>查看</button><input type='text' value='214' /> <button class='btn btn-confirm' onclick='onRefund("+outData+")'>✓</button><button class='btn btn-close'>✕</button>");
			
			}else if(appOrder.getOrderState() ==3){
				//退款
				appOrder.setOrderStateName("审核通过");
				appOrder.setOperation("<button class='btn'>查看</button><button class='btn btn-refund' onclick='finalBackMoneyToUser(500)'>退款</button>");
			
			}else if(appOrder.getOrderState() ==4){
				//退款成功
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
	public Map<String,Object> applyBackCard(String orderId,Integer operationType,String phone){
		System.out.println("orderId="+orderId+" operationType="+operationType+" phone="+phone);
		//1、改变订单状态
		orderManagerImpl.updateOrderStateByOrderId(orderId, operationType);
		//2、发送验证码
		String smsCode = msgInfoManagerImpl.sendAndGetValidateCode(phone, MsgType.BACK_CARD);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", "1");
		map.put("orderStateName", "正在审核");
		map.put("data", "<input type='text' value='214' /> <button class='btn btn-confirm' onClick='onSuccess(1,this)'>✓</button><button class='btn btn-close' onClick='onError(2,this)'>✕</button>");
		return map;
	}
	
	/*
	 * 订单退款审核
	 */
	@RequestMapping(value="/applyBackCardaAudit")
	@ResponseBody
	public Map<String,Object> auditPass(String orderId,Integer opType){
		Map<String,Object> resMap = new HashMap<String,Object>();
		orderId="dd1001";
		opType=1;
		orderManagerImpl.updateOrderStateByOrderId(orderId, opType);
		if (opType.equals("1")){
			float remainMoney = orderManagerImpl.remainMoney(orderId);
			resMap.put("result", "1");
			resMap.put("data", "<button class='btn'>查看</button><input type='text' value='214' /> <button class='btn btn-confirm' onclick='onRefund("+remainMoney+")'>✓</button><button class='btn btn-close'>✕</button>");
			resMap.put("orderStateName", "正在审核");
			
		}else if (opType == 3){
			resMap.put("result", "1");
			resMap.put("data", "<button class='btn'><a href='order-details.html?id=1'>查看</a></button><button class='btn btn-back' onclick='onRefundApplication(1,this)'>退单申请</button>");
			resMap.put("orderStateName", "已支付");
			
		}
		return resMap;	
		
	}
	
	/*
	 * 核算退卡金额,并将用户账号信息记录到退卡流水表中
	 */
	@RequestMapping(value="/onRefund")
	@ResponseBody
	public String onRefund(String orderId,String backCardNo,String remainMoney,String payMethod,Integer opr){
		System.out.println("orderId="+orderId+" backCardNo="+backCardNo);
		//1、将订单状态有'正在审核'变成'审核通过'
		orderManagerImpl.updateOrderStateByOrderId(orderId, opr);
		//2、将退款信息录入到流水表中
		
		return "{'result':'1','data':'120'}";
	}
	
	/*
	 * 退款
	 */
	@RequestMapping(value="/refundMoney")
	public String refundMoney(String orderId,String type,String account,String money){
		//写退款流水
		return "{'result':'1','data':'<button class='btn'>查看</button>'}";
	}
}	
