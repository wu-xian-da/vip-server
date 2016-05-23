/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-上午10:32:13
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppCardBack;
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.dto.OrderDetailInfo;
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
	@RequestMapping(value="/goOrderManagementView")
	public String orderList(HttpServletResponse response){
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("expires", -1);
		return "orders/orderManagement";
	}
	
	/*
	 * 跳转到退卡列表页面
	 */
	@RequestMapping(value="/goBackCardListManagementView")
	public String gotoBackCardList(HttpServletResponse response){
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("expires", -1);
		return "orders/backCardListManagement";
	}
	
	/*
	 * 根据订单号查询订单详细信息
	 */
	@RequestMapping(value="/returnOrderDetailInfoByOrderId")
	public ModelAndView returnOrderDetailInfoByOrderId(String orderId){
		OrderDetailInfo orderDetailInfo = orderManagerImpl.returnOrderDetailInfoByOrderId(orderId);
		return new ModelAndView("orders/orderDetail","orderDetailInfo",orderDetailInfo);
	}
	
	/*
	 * 订单列表分页查询
	 */
	@RequestMapping("orderList")
	@ResponseBody
	public Map<Object,Object> list(@RequestParam(value="page",defaultValue="1") Integer pageNo,@RequestParam(value="rows",defaultValue="10") Integer pageSize){
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		PageInfo<OrderShowInfoDto> pageinfo = orderManagerImpl.simplePage(pageNo, pageSize, paramsMap);
		Map<Object,Object> map = new HashMap<Object,Object>();
		List<OrderShowInfoDto> list = pageinfo.getList();
		String orderId = null;
		String phone = null;
		for(OrderShowInfoDto appOrder : list){
			if(appOrder.getOrderState() ==0){
				//未支付
				appOrder.setInvoiceFlagName(appOrder.getInvoiceFlag() ==0 ?"未开":"已开");
				orderId = appOrder.getOrderId();
				appOrder.setOrderStateName("未支付");
				appOrder.setOperation("<button class='btn'><a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'>查看</a></button>");
			}else if(appOrder.getOrderState() == 1){
				//已支付
				appOrder.setInvoiceFlagName(appOrder.getInvoiceFlag() ==0 ?"未开":"已开");
				appOrder.setOrderStateName("已支付");
				orderId = appOrder.getOrderId();
				phone = appOrder.getCustomerPhone();
				
				JSONObject outData = new JSONObject(); 
				outData.put("orderId", orderId);
				outData.put("opr", "0");
				outData.put("phone", phone);
				
				appOrder.setOperation("<button class='btn'><a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'>查看</a></button><button class='btn btn-back' onclick='onRefundApplication("+outData+",this)'>退单申请</button>");
			
			}else if(appOrder.getOrderState() == 2){
				appOrder.setInvoiceFlagName(appOrder.getInvoiceFlag() ==0 ?"未开":"已开");
				appOrder.setOrderStateName("正在审核");
				JSONObject outData = new JSONObject(); 
				orderId = appOrder.getOrderId();
				float remainMoney = orderManagerImpl.remainMoney(orderId);
				outData.put("remainMoney", remainMoney);
				outData.put("orderId", appOrder.getOrderId());
				appOrder.setOperation("<button class='btn'><a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'>查看</a></button><input type='text' value='214' /> <button class='btn btn-confirm' onclick='onRefund("+outData+")'>✓</button><button class='btn btn-close' onclick='onError("+outData+",this)'>✕</button>");
			
			}else if(appOrder.getOrderState() ==3){
				//退款
				appOrder.setInvoiceFlagName(appOrder.getInvoiceFlag() ==0 ?"未开":"已开");
				JSONObject outData = new JSONObject(); 
				orderId = appOrder.getOrderId();
				float remainMoney = orderManagerImpl.remainMoney(appOrder.getOrderId());//退款金额
				AppCardBack appCardBack = orderManagerImpl.selCustomerCard(appOrder.getOrderId());//退款卡号
				outData.put("remainMoney", remainMoney);
				outData.put("orderId", orderId);//订单号
				outData.put("backMoneyCard",appCardBack.getCustomerCard());
				outData.put("backType", appCardBack.getBackType());
				
				appOrder.setOrderStateName("审核通过");
				appOrder.setOperation("<button class='btn'><a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'>查看</a></button><button class='btn btn-refund' onclick='finalBackMoneyToUser("+outData+")'>退款</button>");
			
			}else if(appOrder.getOrderState() ==4){
				//退款成功
				appOrder.setInvoiceFlagName(appOrder.getInvoiceFlag() ==0 ?"未开":"已开");
				orderId = appOrder.getOrderId();
				appOrder.setOrderStateName("已退款");
				appOrder.setOperation("<button class='btn'><a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'>查看</a></button>");
			}
		}
		map.put("total", pageinfo.getTotal());
		map.put("rows", list);
		return map;
		
	}
	
	/*
	 * 退款列表分页查询
	 */
	@RequestMapping("backCardList")
	@ResponseBody
	public Map<Object,Object> backCardlist(@RequestParam(value="page",defaultValue="1") Integer pageNo,@RequestParam(value="rows",defaultValue="10") Integer pageSize){
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		PageInfo<OrderShowInfoDto> pageinfo = orderManagerImpl.backCardPage(pageNo, pageSize, paramsMap);
		Map<Object,Object> map = new HashMap<Object,Object>();
		List<OrderShowInfoDto> list = pageinfo.getList();
		String orderId = null;
		for(OrderShowInfoDto appOrder : list){
			if(appOrder.getOrderState() ==3){
				//退款
				JSONObject outData = new JSONObject(); 
				orderId = appOrder.getOrderId();
				float remainMoney = orderManagerImpl.remainMoney(appOrder.getOrderId());//退款金额
				AppCardBack appCardBack = orderManagerImpl.selCustomerCard(appOrder.getOrderId());//退款卡号
				outData.put("remainMoney", remainMoney);
				outData.put("orderId", orderId);//订单号
				outData.put("backMoneyCard",appCardBack.getCustomerCard());
				outData.put("backType", appCardBack.getBackType());
				outData.put("phone", appOrder.getCustomerPhone());
				appOrder.setOrderStateName("审核通过");
				appOrder.setOperation("<button class='btn'><a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'>查看</a></button><button class='btn btn-refund' onclick='finalBackMoneyToUser("+outData+")'>退款</button>");
			
			}else if(appOrder.getOrderState() ==4){
				//退款成功
				orderId = appOrder.getOrderId();
				appOrder.setOrderStateName("已退款");
				appOrder.setOperation("<button class='btn'><a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'>查看</a></button>");
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
		
		JSONObject outData = new JSONObject(); 
		float remainMoney = orderManagerImpl.remainMoney(orderId);
		outData.put("remainMoney", remainMoney);
		outData.put("orderId",orderId);
		outData.put("phone", phone);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", "1");
		map.put("orderStateName", "正在审核");
		map.put("data", "<input type='text' value='214' /> <button class='btn btn-confirm' onclick='onRefund("+outData+")'>✓</button><button class='btn btn-close' onclick='onError("+outData+",this)'>✕</button>");
		return map;
	}
	
	/*
	 * 订单退款审核不通过
	 */
	@RequestMapping(value="/applyBackCardaAudit")
	@ResponseBody
	public Map<String,Object> auditPass(String orderId,Integer opType,String phone){
		Map<String,Object> resMap = new HashMap<String,Object>();
		System.out.println("orderId="+orderId+"  opType="+opType);
		orderManagerImpl.updateOrderStateByOrderId(orderId, opType);
		
		JSONObject outData = new JSONObject(); 
		outData.put("orderId", orderId);
		outData.put("opr", "0");
		outData.put("phone", phone);
		
		resMap.put("result", "1");
		resMap.put("data", "<button class='btn'><a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'>查看</a></button><button class='btn btn-back' onclick='onRefundApplication("+outData+",this)'>退单申请</button>");
		resMap.put("orderStateName", "已支付");
		return resMap;	
		
	}
	
	/*
	 * 核算退卡金额,并将用户账号信息记录到退卡流水表中
	 */
	@RequestMapping(value="/onRefund")
	@ResponseBody
	public Map<String,Object> onRefund(String orderId,String backCardNo,String remainMoney,String payMethod,Integer opr){
		System.out.println("orderId="+orderId+" backCardNo="+backCardNo);
		//1、将订单状态有'正在审核'变成'审核通过'
		orderManagerImpl.updateOrderStateByOrderId(orderId, opr);
		//2、将退款信息录入到流水表中
		AppCardBack appCardBack = new AppCardBack();
		appCardBack.setBackId(UUID.randomUUID().toString());
		appCardBack.setCreateTime(new Date());
		appCardBack.setOrderId(orderId);
		appCardBack.setCustomerCard(backCardNo);
		appCardBack.setMoney(Float.parseFloat(remainMoney));
		appCardBack.setBackType(Integer.parseInt(payMethod));
		orderManagerImpl.insertBackCardInfo(appCardBack);
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("result", 1);//1代表成功
		resMap.put("data","<button class='btn'><a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'>查看</a></button><button class='btn btn-refund' onclick='finalBackMoneyToUser(500)'>退款</button>");
		resMap.put("orderStateName", "审核通过");
		
		return resMap;
	}
	
	/*
	 * 最终退款
	 */
	@RequestMapping(value="/finalRefundMoney")
	@ResponseBody
	public Map<String,Object> refundMoney(String orderId,Integer opr){
		//1、更新订单状态
		orderManagerImpl.updateOrderStateByOrderId(orderId, opr);
		//写退款流水
		Map<String,Object> parMap = new HashMap<String,Object>();
		parMap.put("finishTime", new Date());
		parMap.put("orderId", orderId);
		orderManagerImpl.updateBackCardByOrderId(parMap);
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("result", "1");
		resMap.put("data","<button class='btn'><a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'>查看</a></button>");
		resMap.put("orderStateName", "已退款");
		return resMap;
		
	}
}	
