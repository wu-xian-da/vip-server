/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-上午10:32:13
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppCardBack;
import com.jianfei.core.bean.AppConsume;
import com.jianfei.core.bean.AppInvoice;
import com.jianfei.core.bean.AppOrderCard;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.bean.AppUserFeedback;
import com.jianfei.core.bean.AppVipcard;
import com.jianfei.core.bean.AriPort;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.enu.ModuleType;
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.common.enu.PayType;
import com.jianfei.core.common.enu.VipCardState;
import com.jianfei.core.common.enu.VipUserSate;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.SmartLog;
import com.jianfei.core.common.utils.StateChangeUtils;
import com.jianfei.core.common.utils.UUIDUtils;
import com.jianfei.core.dto.AirportEasyUseInfo;
import com.jianfei.core.dto.OrderDetailInfo;
import com.jianfei.core.dto.OrderShowInfoDto;
import com.jianfei.core.dto.ServiceMsgBuilder;
import com.jianfei.core.service.base.AriPortManager;
import com.jianfei.core.service.base.impl.AppInvoiceManagerImpl;
import com.jianfei.core.service.base.impl.AppUserFeedbackImpl;
import com.jianfei.core.service.base.impl.ValidateCodeManagerImpl;
import com.jianfei.core.service.base.impl.VipCardManagerImpl;
import com.jianfei.core.service.order.impl.OrderManagerImpl;
import com.jianfei.core.service.thirdpart.impl.AirportEasyManagerImpl;
import com.jianfei.core.service.user.impl.VipUserManagerImpl;

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
public class OrderController extends BaseController {
	private Logger logger = Logger.getLogger(OrderController.class);
	private static String staticPath =  GloabConfig.getConfig("static.resource.server.address");
	
	@Autowired
	private OrderManagerImpl orderManagerImpl;
	@Autowired
	private AppInvoiceManagerImpl appInvoiceManagerImpl;
	@Autowired
	private AriPortManager ariPortService;
	@Autowired
	private ValidateCodeManagerImpl validateCodeManager;
	@Autowired
	private AppUserFeedbackImpl appUserFeedbackImpl;
	@Autowired
	private VipCardManagerImpl vipCardManagerImpl;
	@Autowired
	private AirportEasyManagerImpl airportEasyManagerImpl;
	@Autowired
	private VipUserManagerImpl vipUserManager;
	
	/**
	 * 跳转到订单列表页面
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value="system:orderList:home")
	@RequestMapping(value="/goOrderManagementView")
	public String orderList(HttpServletResponse response,Model model){
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("expires", -1);
		//所有的机场列表
		List<AriPort> list = returnAirportInfoList();
		model.addAttribute("airPostList", list);
		return "orders/orderManagement";
	}
	
	/**
	 * 跳转到退卡列表页面
	 * @param response
	 * @return
	 */
	@RequiresPermissions(value="system:backList:home")
	@RequestMapping(value="/goBackCardListManagementView")
	public String gotoBackCardList(HttpServletResponse response){
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("expires", -1);
		return "orders/backCardListManagement";
	}
	
	/**
	 * 根据订单号查询订单详细信息
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/returnOrderDetailInfoByOrderId")
	public String returnOrderDetailInfoByOrderId(String orderId,Model model){
		//1、订单基本信息
		OrderDetailInfo orderDetailInfo = orderManagerImpl.returnOrderDetailInfoByOrderId(orderId);
		//2、订单对应卡片基本信息
		AppVipcard vipCardInfo = vipCardManagerImpl.selVipCardInfoByOrderId(orderId);
		//3、 发票信息
		AppInvoice appInvoice = appInvoiceManagerImpl.selInvoiceInfoByOrderId(orderId);
		if(appInvoice !=null){
			appInvoice.setBusinessLicenseUrl(appInvoice.getBusinessLicenseUrl()==null ? "": staticPath+appInvoice.getBusinessLicenseUrl());
		}
		//4 退卡余额信息
		AppCardBack appCardBack = orderManagerImpl.selCustomerCard(orderId);
		//最终退款金额
		float finalBackMoney = (float)orderManagerImpl.remainMoney(orderId);
		//5 反馈信息 根据用户id
		//5.1 根据orderId获取用户id
		AppOrders appOrders = orderManagerImpl.selectByPrimaryKey(orderId); 
		List<AppUserFeedback> appuserFeedBackInfoList  = appUserFeedbackImpl.getFeedBackInfoListByUserId(appOrders.getCustomerId());
		
		// >>>>>>>>>>>>>>>>6 vip使用记录 根据cardno-->改为从第三方接口获取
		//6.1 根据orderId 获取carNo
		AppOrderCard appOrderCard = orderManagerImpl.selectByOrderId(orderId);
		//List<AppConsume> consumeList = orderManagerImpl.selectByVipCardNo(appOrderCard.getCardNo());
		List<AppConsume> consumeList = null;
		AirportEasyUseInfo airportEasyUseInfo = airportEasyManagerImpl.readDisCodeData(appOrderCard.getCardNo());
		if(airportEasyUseInfo != null){
			consumeList = airportEasyUseInfo.getConsumeList();
			if(consumeList.size()>0){
				model.addAttribute("activityTime", consumeList.get(0).getConsumeTime());
			}
		}
		model.addAttribute("finalBackMoney", finalBackMoney);
		model.addAttribute("orderDetailInfo", orderDetailInfo);
		model.addAttribute("cardInfo", vipCardInfo);
		if(appInvoice !=null){
			model.addAttribute("invoice", appInvoice);
		}
		if(appCardBack != null){
			if(appCardBack.getAgreementUrl() !=null){
				appCardBack.setAgreementUrl(staticPath+appCardBack.getAgreementUrl());
			}
			model.addAttribute("appCardBack", appCardBack);
		}
		if(appuserFeedBackInfoList != null){
			model.addAttribute("appuserFeedBackInfoList", appuserFeedBackInfoList);
		}
		if(consumeList !=null){
			model.addAttribute("consumeList", consumeList);
		}
		return "orders/orderDetail";
		
	}
	
	/**
	 * 订单列表分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param startTime
	 * @param endTime
	 * @param airportId
	 * @param orderState
	 * @param invoiceState
	 * @param phoneOrUserName
	 * @return
	 */
	@RequestMapping("orderList")
	@ResponseBody
	public Map<Object,Object> list(@RequestParam(value="page",defaultValue="1") Integer pageNo,
			@RequestParam(value="rows",defaultValue="20") Integer pageSize,
			@RequestParam(value="startTime",defaultValue="") String startTime,
			@RequestParam(value="endTime",defaultValue="") String endTime,
			@RequestParam(value="airportId",required=false,defaultValue="") String airportId,
			@RequestParam(value="orderState",required=false,defaultValue="") String orderState,
			@RequestParam(value="invoiceState",required=false,defaultValue="") String invoiceState,
			@RequestParam(value="payType",required=false,defaultValue="") String payType,
			@RequestParam(value="phoneOrUserName",required=false,defaultValue="") String phoneOrUserName){
		
		//用户可以看到机场列表
		List<String> aiportIdList = returnAirportIdList();
		
		//设置刷选条件
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		if(!startTime.equals("")){
			paramsMap.put("startTime",startTime);
		}
		if(!endTime.equals("")){
			paramsMap.put("endTime", endTime);
		}
		if(!phoneOrUserName.equals("")){
			paramsMap.put("phoneOrUserName",phoneOrUserName );
		}
		if(aiportIdList !=null && aiportIdList.size() >0){
			paramsMap.put("aiportIdList", aiportIdList);
		}
		if(!airportId.equals("")){
			paramsMap.put("airportId",airportId);
		}
		if(!orderState.equals("")){
			paramsMap.put("orderState", orderState);
		}
		if(!payType.equals("")){
			paramsMap.put("payType", payType);
		}
		if(!invoiceState.equals("")){
			paramsMap.put("invoiceState", invoiceState);
		}
		
		PageInfo<OrderShowInfoDto> pageinfo = orderManagerImpl.simplePage(pageNo, pageSize, paramsMap);
		Map<Object,Object> map = new HashMap<Object,Object>();
		List<OrderShowInfoDto> list = pageinfo.getList();
		String orderId = null;
		String phone = null;
		
		//权限校验
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		
		for(OrderShowInfoDto appOrder : list){
			int invoiceFlag = appOrder.getInvoiceFlag();
			appOrder.setInvoiceFlagName(StateChangeUtils.returnInvoiceFlagName(invoiceFlag));
			appOrder.setPayTypeName(StateChangeUtils.returnPayTypeName(appOrder.getPayType()));
			if(appOrder.getOrderState() ==0){
				//未支付
				orderId = appOrder.getOrderId();
				appOrder.setOrderStateName("正在支付");
				appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
			}else if(appOrder.getOrderState() == 1){
				//已支付
				appOrder.setOrderStateName("已支付");
				orderId = appOrder.getOrderId();
				phone = appOrder.getCustomerPhone();
				
				JSONObject outData = new JSONObject(); 
				outData.put("orderId", orderId);
				outData.put("opr", "0");
				outData.put("phone", phone);
				outData.put("invoice",appOrder.getInvoiceFlag());
				
				//新需求新增字段
				float remainMoney = (float)orderManagerImpl.remainMoney(orderId);
				outData.put("remainMoney", remainMoney);
				outData.put("payType", appOrder.getPayType());//支付方式
				
				//是否有‘申请退款’权限
				boolean flag = subject.isPermitted("system:order:applyBackMoney");
				if(flag){
					//appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a><button class='btn btn-back' onclick='onRefundApplication("+outData+",this)'>退单申请</button>");
					appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a><button class='btn btn-back' onclick='onRefund("+outData+")'>退单申请</button>");
				}else{
					appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
				}
				
			}else if(appOrder.getOrderState() == 2){
				/*appOrder.setOrderStateName("正在审核");
				JSONObject outData = new JSONObject(); 
				orderId = appOrder.getOrderId();
				double remainMoney = orderManagerImpl.remainMoney(orderId);
				outData.put("remainMoney", remainMoney);
				outData.put("orderId", appOrder.getOrderId());
				outData.put("phone", appOrder.getCustomerPhone());
				outData.put("payType", appOrder.getPayType());//支付方式
				//2、获取验证码
				String smsCode = validateCodeManager.getSendValidateCode(appOrder.getCustomerPhone(), MsgType.SELECT);
				if(smsCode == null){
					smsCode ="";
				}
				//是否有审核的权限
				boolean flag = subject.isPermitted("system:order:audit");
				if(flag){
					appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a><input style='width:50px' type='text' value='"+smsCode+"' /> <button class='btn btn-confirm' onclick='onRefund("+outData+")'>✓</button><button class='btn btn-close' onclick='onError("+outData+",this)'>✕</button>");
				}else{
					appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
				}*/
				
			
			}else if(appOrder.getOrderState() ==3){
				//退款
				JSONObject outData = new JSONObject(); 
				orderId = appOrder.getOrderId();
				float remainMoney = (float) orderManagerImpl.remainMoney(appOrder.getOrderId());//退款金额
				AppCardBack appCardBack = orderManagerImpl.selCustomerCard(appOrder.getOrderId());//退款卡号
				outData.put("remainMoney", remainMoney);
				outData.put("orderId", orderId);//订单号
				outData.put("backMoneyCard",appCardBack.getCustomerCard());
				outData.put("backType", appCardBack.getBackType());
				outData.put("backName", appCardBack.getBankName());
				outData.put("customerName", appCardBack.getCustomerName());
				outData.put("applyBackCardMethod",appOrder.getApplyType());
				outData.put("orderPayType",appOrder.getPayType());
				//发票状态
				AppOrders orderInfo = orderManagerImpl.getOrderInfoByOrderId(appOrder.getOrderId());
				outData.put("invoice", orderInfo.getInvoiceFlag());
				appOrder.setOrderStateName("审核通过");
				
				//是否有最终退款查看的权限
				boolean flag = subject.isPermitted("system:order:refundsel");
				if(flag){
					appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a><button class='btn btn-back' onclick='finalBackMoneyToUser("+outData+")'>退款</button>");
				}else{
					appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
				}
				
			}else if(appOrder.getOrderState() ==4){
				//退款成功
				orderId = appOrder.getOrderId();
				appOrder.setOrderStateName("已退款");
				appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
			}else if(appOrder.getOrderState() ==5){
				//已失效
				orderId = appOrder.getOrderId();
				appOrder.setOrderStateName("已失效");
				appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
			}
		}
		map.put("total", pageinfo.getTotal());
		map.put("rows", list);
		return map;
		
	}
	
	/**
	 * 退款列表分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param backType
	 * @param applyType
	 * @param orderState
	 * @return
	 */
	@RequestMapping("backCardList")
	@ResponseBody
	public Map<Object,Object> backCardlist(@RequestParam(value="page",defaultValue="1") Integer pageNo,
			@RequestParam(value="rows",defaultValue="20") Integer pageSize,
			@RequestParam(value="backType",defaultValue="") String backType,
			@RequestParam(value="applyType",defaultValue="") String applyType,
			@RequestParam(value="orderState",defaultValue="") String orderState,
			@RequestParam(value="phoneOrUserName",defaultValue="") String phoneOrUserName){
		
		//用户可以看到机场列表
		List<String> aiportIdList = returnAirportIdList();
		
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		//退款方式
		if(!backType.equals("")){
			paramsMap.put("backType", backType);
		}
		//申请方式
		if(!applyType.equals("")){
			paramsMap.put("applyType", applyType);
		}
		//订单状态
		if(!orderState.equals("")){
			paramsMap.put("orderState", orderState);
		}
		//搜索关键字
		if(!phoneOrUserName.equals("")){
			paramsMap.put("phoneOrUserName", phoneOrUserName);
		}
		//机场id列表
		if(aiportIdList !=null && aiportIdList.size() >0){
			paramsMap.put("aiportIdList", aiportIdList);
		}
		
		PageInfo<OrderShowInfoDto> pageinfo = orderManagerImpl.backCardPage(pageNo, pageSize, paramsMap);
		Map<Object,Object> map = new HashMap<Object,Object>();
		List<OrderShowInfoDto> list = pageinfo.getList();
		List<OrderShowInfoDto> resList = new ArrayList<OrderShowInfoDto>();
		String orderId = null;
		if(list != null && list.size() >0){
			for(OrderShowInfoDto appOrder : list){
				//1、申请方式
				int applyTypes = appOrder.getApplyType();
				//1.1、申请方式的中文名称
				appOrder.setApplyTypeName(StateChangeUtils.returnApplyBackCardMethodName(applyTypes));
				//2、卡状态
				int cardState =  appOrder.getCardState();
				appOrder.setCardStateName(StateChangeUtils.returnCardStateName(cardState)); 
				//3、退卡方式
				int backCardTypes = appOrder.getBackType();
				appOrder.setBackTypeName(StateChangeUtils.returnBackMoneyMethodName(backCardTypes));
				
				if(appOrder.getOrderState() ==3){
					//退款
					JSONObject outData = new JSONObject(); 
					orderId = appOrder.getOrderId();
					float remainMoney = (float) orderManagerImpl.remainMoney(appOrder.getOrderId());//退款金额
					AppCardBack appCardBack = orderManagerImpl.selCustomerCard(appOrder.getOrderId());//退款卡号
					outData.put("remainMoney", remainMoney);
					outData.put("orderId", orderId);//订单号
					outData.put("backMoneyCard",appCardBack.getCustomerCard());
					outData.put("backType", appCardBack.getBackType());
					outData.put("phone", appOrder.getCustomerPhone());
					outData.put("backName", appCardBack.getBankName());
					outData.put("customerName", appCardBack.getCustomerName());
					outData.put("orderPayType",appOrder.getPayType());
					//申请方式
					outData.put("applyBackCardMethod", appOrder.getApplyType());
					//发票状态
					AppOrders orderInfo = orderManagerImpl.getOrderInfoByOrderId(appOrder.getOrderId());
					outData.put("invoice", orderInfo.getInvoiceFlag());
					
					//订单状态
					appOrder.setOrderStateName("审核通过");
					
					//权限校验
					org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
					//是否有最终退款查看的权限
					boolean flag = subject.isPermitted("system:order:refundsel");
					if(flag){
						appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a><button class='btn btn-back' onclick='finalBackMoneyToUser("+outData+")'>退款</button>");
					}else{
						appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
					}
					
					resList.add(appOrder);
				}else if(appOrder.getOrderState() ==4){
					//退款成功
					orderId = appOrder.getOrderId();
					appOrder.setOrderStateName("已退款");
					
					//卡片状态
					if(cardState == 5){
						appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a><a href='unbundCard?vipCardNo="+appOrder.getVipCardNo()+"'><button class='btn' style='background:#698DC3'>解绑</button></a>");
					}else{
						appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
					}
					
					resList.add(appOrder);
				}
			}
			if(resList !=null && resList.size()>0){
				map.put("total", pageinfo.getTotal());
			}else{
				map.put("total", 0);
			}
			
		}else{
			map.put("total", 0);
		}
		map.put("rows", resList);
		return map;
		
	}
	
	/**
	 * 退单申请,给用户发送短信验证码，并将短信验证码回显
	 * 
	 * @param orderId
	 * @param operationType
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/applyBackCard")
	@ResponseBody
	public Map<String, Object> applyBackCard(String orderId, Integer operationType, String phone) {
		User user = getCurrentUser();
		// 订单基本信息
		OrderDetailInfo orderDetailInfo = orderManagerImpl.selLogInfoByOrderId(orderId);
		// 1、改变订单状态
		try {
			orderManagerImpl.updateOrderStateByOrderId(orderId, operationType);
			
			//**日志记录（正常）
			SmartLog.info(ModuleType.ORDER_MODULE.getName(),phone,
					"【订单模块-退款申请】，订单编号："+orderId+"，用户手机号："+phone+"，用户姓名："+orderDetailInfo.getCustomerName()+
					"，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【订单状态 已支付->正在审核】，操作结果：【成功】");
		} catch (Exception e) {
			//**日志记录（异常）
			SmartLog.error(e,ModuleType.ORDER_MODULE.getName(),phone,
					"【订单模块-退款申请】，订单编号："+orderId+"，用户手机号："+phone+"，用户姓名："+orderDetailInfo.getCustomerName()+
					"，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【订单状态 已支付->正在审核】，操作结果：【失败】");
		}

		// 2、获取验证码
		String smsCode = validateCodeManager.getValidateCode(phone, MsgType.SELECT);
		// 发送短信****
		try {
			validateCodeManager.sendMsgInfo(phone, MsgType.SELECT, smsCode);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("【订单模块-申请退款操作】发送验证码短信出错：" + e.getMessage());
		}

		JSONObject outData = new JSONObject();
		double remainMoney = orderManagerImpl.remainMoney(orderId);
		outData.put("remainMoney", remainMoney);
		outData.put("orderId", orderId);
		outData.put("phone", phone);
		outData.put("payType", orderDetailInfo.getPayMethod());// 支付方式
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "1");
		map.put("orderStateName", "正在审核");
		// 权限校验
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		// 是否有审核的权限
		boolean flag = subject.isPermitted("system:order:audit");
		if (flag) {
			map.put("data", "<input style='width:50px' type='text' value='" + smsCode
					+ "' /> <button class='btn btn-confirm' onclick='onRefund(" + outData
					+ ")'>✓</button><button class='btn btn-close' onclick='onError(" + outData + ",this)'>✕</button>");
		} else {
			map.put("data", "");
		}
		return map;
	}
	
	/**
	 * 订单退款审核不通过
	 * @param orderId
	 * @param opType
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="/applyBackCardaAudit")
	@ResponseBody
	public Map<String,Object> auditNoPass(String orderId,Integer opType,String phone){
		User user = getCurrentUser();
		//1、审核不通过
		try {
			orderManagerImpl.updateOrderStateByOrderId(orderId, opType);
			//***日志记录（正常）
			SmartLog.info(ModuleType.ORDER_MODULE.getName(),phone,
					"【订单模块-审核不通过】，订单编号："+orderId+"，用户手机号："+phone+"，用户姓名：【】"+
					"，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【订单状态 正在审核->已支付】，操作结果：【成功】");
		} catch (Exception e) {
			//***日志记录（异常）
			SmartLog.error(e,ModuleType.ORDER_MODULE.getName(),phone,
					"【订单模块-审核不通过】，订单编号："+orderId+"，用户手机号："+phone+"，用户姓名：【】"+
					"，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【订单状态 正在审核->已支付】，操作结果：【失败】");
		}
		
		AppOrders orderInfo = orderManagerImpl.getOrderInfoByOrderId(orderId);
		JSONObject outData = new JSONObject(); 
		outData.put("orderId", orderId);
		outData.put("opr", "0");
		outData.put("phone", phone);
		outData.put("invoice", orderInfo.getInvoiceFlag());
		
		//权限校验
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		//是否有退款申请的权限
		boolean flag = subject.isPermitted("system:order:applyBackMoney");
		Map<String,Object> resMap = new HashMap<String,Object>();
		if(flag){
			resMap.put("data", "<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a><button class='btn btn-back' onclick='onRefundApplication("+outData+",this)'>退单申请</button>");
		}else{
			resMap.put("data", "<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
		}
		resMap.put("orderStateName", "已支付");
		resMap.put("result", "1");
		return resMap;	
		
	}
	
	/**
	 * 核算退卡金额,并将用户账号信息记录到退卡流水表中
	 * @param orderId
	 * @param backCardNo
	 * @param remainMoney
	 * @param payMethod
	 * @param opr
	 * @param userNames
	 * @param banckName
	 * @return
	 */
	@RequestMapping(value="/onRefund")
	@ResponseBody
	public Map<String,Object> onRefund(String orderId,String backCardNo,String remainMoney,String payMethod,Integer opr,
			@RequestParam(value="userNames",defaultValue="",required=false) String userNames,
			@RequestParam(value="banckName",defaultValue="",required=false) String banckName){
		User user = getCurrentUser();
		OrderDetailInfo orderDetailInfo = orderManagerImpl.selLogInfoByOrderId(orderId);
		//****审批人员id
		String userId = user.getId()+"";
		
		//1、将订单状态有'正在审核'变成'审核通过'，退款申请变成客服
		try {
			orderManagerImpl.updateOrderStateByOrderId(orderId, opr);
			//***日志记录（正常）
			SmartLog.info(ModuleType.ORDER_MODULE.getName(),orderDetailInfo.getCustomerPhone(),
					"【订单模块-审核通过】，订单编号："+orderId+"，用户手机号："+orderDetailInfo.getCustomerPhone()+"，用户姓名："+orderDetailInfo.getCustomerName()+
					"，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【将订单状态由 正在审核->审核通过，将订单】，操作结果：【成功】");
		} catch (Exception e) {
			//***日志记录（异常）
			SmartLog.error(e,ModuleType.ORDER_MODULE.getName(),orderDetailInfo.getCustomerPhone(),
					"【订单模块-审核通过】，订单编号："+orderId+"，用户手机号："+orderDetailInfo.getCustomerPhone()+"，用户姓名："+orderDetailInfo.getCustomerName()+
					"，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【将订单状态由 正在审核->审核通过】，操作结果：【失败】");
		}
		
		//2、将退款信息录入到流水表中
		AppCardBack appCardBack = new AppCardBack();
		appCardBack.setServiceMoney((float) orderManagerImpl.calculateServiceMoney(orderId));
		appCardBack.setBackId(UUIDUtils.getPrimaryKey());
		appCardBack.setCreateTime(new Date());
		appCardBack.setOrderId(orderId);
		appCardBack.setCustomerCard(backCardNo);
		appCardBack.setMoney(Float.parseFloat(remainMoney));
		appCardBack.setBackType(Integer.parseInt(payMethod));
		appCardBack.setCreaterId(userId);
		appCardBack.setCreateName(user.getName());
		appCardBack.setBankName(banckName);//开户行
		//开户者姓名
		if(userNames.equals("")){
			appCardBack.setCustomerName(orderDetailInfo.getCustomerName());
		}else{
			appCardBack.setCustomerName(userNames);
		}
		
		appCardBack.setSafeMoney(100);//保险费
		try {
			orderManagerImpl.insertBackCardInfo(appCardBack);
			//***日志记录（正常）
			SmartLog.info(ModuleType.ORDER_MODULE.getName(),orderDetailInfo.getCustomerPhone(),
					"【订单模块-审核通过-记录退款信息】，订单编号："+orderId+"，用户手机号："+orderDetailInfo.getCustomerPhone()+"，用户姓名："+orderDetailInfo.getCustomerName()+
					"，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【核算金额："+remainMoney+"，并将退款申请信息记录到退款表中】，操作结果：【成功】");
		} catch (Exception e) {
			//***日志记录（异常）
			SmartLog.error(e,ModuleType.ORDER_MODULE.getName(),orderDetailInfo.getCustomerPhone(),
					"【订单模块-审核通过-记录退款信息】，订单编号："+orderId+"，用户手机号："+orderDetailInfo.getCustomerPhone()+"，用户姓名："+orderDetailInfo.getCustomerName()+
					"，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【核算金额："+remainMoney+"，并将退款申请信息记录到退款表中】，操作结果：【失败】");
		}
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("result", 1);//1代表成功
		
		//权限校验
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		//是否有退款查看的权限
		boolean flag = subject.isPermitted("system:order:refundsel");
		if(flag){
			resMap.put("data","<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a><button class='btn btn-back' onclick='finalBackMoneyToUser(500)'>退款</button>");
		}else{
			resMap.put("data","<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
		}
		// 3、*****给用户发送短信 内容：用户名、卡号
		OrderDetailInfo orderDetailInfos = orderManagerImpl.returnOrderDetailInfoByOrderId(orderId);
		// 4、将用户状态变为禁用
		vipUserManager.updateUserSate(orderDetailInfos.getCustomerPhone(),VipUserSate.NOT_ACTIVE);
		//5.1调用发送短信接口 现金 006 其他：009
		try {
			//5.2支付方式
			int payTypes = orderDetailInfos.getPayMethod();
			String msgType = "";
			if(payTypes == PayType.CASHPAY.getName()){
				msgType = MsgType.BACK_CARD_APPLY.getName();
			}else{
				msgType = MsgType.QT_BACK_CARD_APPLY.getName();
			}
			ServiceMsgBuilder msgBuilder = new ServiceMsgBuilder().setUserPhone(orderDetailInfos.getCustomerPhone()).
					setUserName(orderDetailInfos.getCustomerName()).setVipCardNo(orderDetailInfos.getVipCardNo()).
					setMsgType(msgType);
			orderManagerImpl.sendMessageOfOrder(msgBuilder);
		} catch (Exception e) {
			logger.error("【订单模块-退卡申请完成】发送短信失败："+e.getMessage());
		}
		
		resMap.put("orderStateName", "审核通过");
		return resMap;
	}
	
	/**
	 * 最终退款
	 * @param orderId
	 * @param opr
	 * @return
	 */
	@RequestMapping(value="/finalRefundMoney")
	@ResponseBody
	public Map<String,Object> refundMoney(String orderId,Integer opr){
		User user = getCurrentUser();
		//根据订单编号返回订单详情
		OrderDetailInfo orderDetailInfos = orderManagerImpl.selLogInfoByOrderId(orderId);
		//****审核员id
		String userId = user.getId()+"";
		
		//1、更新订单状态
		try {
			orderManagerImpl.updateOrderStateByOrderId(orderId, opr);
			//***日志记录（正常）
			SmartLog.info(ModuleType.ORDER_MODULE.getName(),orderDetailInfos.getCustomerPhone(),
					"【订单模块-最终退款】，订单编号："+orderId+"，用户手机号："+orderDetailInfos.getCustomerPhone()+"，用户姓名："+orderDetailInfos.getCustomerName()+
					"，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【将订单状态由 审核通过->已退款】，操作结果：【成功】");
		} catch (Exception e) {
			//***日志记录（异常）
			SmartLog.error(e,ModuleType.ORDER_MODULE.getName(),orderDetailInfos.getCustomerPhone(),
					"【订单模块-最终退款】，订单编号："+orderId+"，用户手机号："+orderDetailInfos.getCustomerPhone()+"，用户姓名："+orderDetailInfos.getCustomerName()+
					"，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【将订单状态由 审核通过->已退款】，操作结果：【失败】");
		}
		
		//写退款流水
		//2、更新退卡表状态
		Map<String,Object> parMap = new HashMap<String,Object>();
		double finalBackMoney = orderManagerImpl.remainMoney(orderId);
		parMap.put("finalBackMoney", finalBackMoney);
		parMap.put("serviceMoney",orderManagerImpl.calculateServiceMoney(orderId));
		parMap.put("finishTime", new Date());
		parMap.put("orderId", orderId);
		parMap.put("checkId", userId);
		try {
			orderManagerImpl.updateBackCardByOrderId(parMap);
			//***日志记录（正常）
			SmartLog.info(ModuleType.ORDER_MODULE.getName(),orderDetailInfos.getCustomerPhone(),
					"【订单模块-最终退款-更新退款流水表】，订单编号："+orderId+"，用户手机号："+orderDetailInfos.getCustomerPhone()+"，用户姓名："+orderDetailInfos.getCustomerName()+
					"，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【更新退款流水表，最终退款金额为："+finalBackMoney+"】，操作结果：【成功】");
		} catch (Exception e) {
			//***日志记录（异常）
			SmartLog.error(e,ModuleType.ORDER_MODULE.getName(),orderDetailInfos.getCustomerPhone(),
					"【订单模块-最终退款-更新退款流水表】，订单编号："+orderId+"，用户手机号："+orderDetailInfos.getCustomerPhone()+"，用户姓名："+orderDetailInfos.getCustomerName()+
					"，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【更新退款流水表，最终退款金额为："+finalBackMoney+"】，操作结果：【失败】");
		}
		
		//3、更新卡状态 将开状态变为已退卡
		AppVipcard appVipcard = new AppVipcard();
		appVipcard.setCardNo(orderDetailInfos.getVipCardNo());
		appVipcard.setCardState(VipCardState.BACK_CARD.getName());
		try {
			vipCardManagerImpl.updateByPrimaryKeySelective(appVipcard);
			//***日志记录（正常）
			SmartLog.info(ModuleType.VIPCARD_MODULE.getName(),orderDetailInfos.getVipCardNo(),
					"【订单模块-最终退款-更改卡状态】，订单编号："+orderId+"，用户手机号："+orderDetailInfos.getCustomerPhone()+"，用户姓名："+orderDetailInfos.getCustomerName()+
					"，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【将卡状态变为-已退卡】，操作结果：【成功】");
		} catch (Exception e) {
			//***日志记录（异常）
			SmartLog.error(ModuleType.VIPCARD_MODULE.getName(),orderDetailInfos.getVipCardNo(),
					"【订单模块-最终退款-更改卡状态】，订单编号："+orderId+"，用户手机号："+orderDetailInfos.getCustomerPhone()+"，用户姓名："+orderDetailInfos.getCustomerName()+
					"，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【将卡状态变为-已退卡】，操作结果：【失败】");
		}
		
		// 4发送短信  内容如下：用户名+卡号+退款金额
		// 4.1退款金额
		double remainMoneys = orderManagerImpl.remainMoney(orderId);
		JSONObject object=new JSONObject();
	    object.put("returnMoney",remainMoneys);
		// 4.2发送短信
		try {
			ServiceMsgBuilder msgBuilder = new ServiceMsgBuilder().setUserPhone(orderDetailInfos.getCustomerPhone()).
					setUserName(orderDetailInfos.getCustomerName()).setVipCardNo(orderDetailInfos.getVipCardNo()).
					setMsgType(MsgType.BACK_CARD_FINISH.getName()).setMsgBody(object.toJSONString());
			orderManagerImpl.sendMessageOfOrder(msgBuilder);
		} catch (Exception e) {
			logger.error("【订单模块-最终退款】发送短信失败："+e.getMessage());
		}
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("result", "1");
		resMap.put("data","<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
		resMap.put("orderStateName", "已退款");
		return resMap;
	}
	
	
	/**
	 * 返回当前用户可以查看的机场信息
	 */
	public List<AriPort> returnAirportInfoList() {
		// 用户可以看到机场列表
		User user = getCurrentUser();
		List<AriPort> airportList = user.getAripors();
		return  airportList;
	}
	
	/**
	 * 返回当前用户可以查看的机场列表id
	 * @return
	 */
	public List<String> returnAirportIdList() {
		// 用户可以看到机场列表
		User user = getCurrentUser();
		List<AriPort> airportList = user.getAripors();
		// 机场id列表
		List<String> aiportIdList = new ArrayList<String>();
		for(AriPort ariPort :airportList){
			aiportIdList.add(ariPort.getId());
		}
		return aiportIdList;
	}
	
	/**
	 * 返回所有的机场列表
	 * @return
	 */
	@RequestMapping("returnAirPortList")
	@ResponseBody
	public Map<String,Object> returnAirPortList(){
		//返回所有的场站信息
		MessageDto<List<AriPort>> list= ariPortService.get(null);
		List<AriPort> airportList = list.getData();
		Map<String , Object> resMap = new HashMap<String,Object>();
		resMap.put("result", 1);
		resMap.put("airportList", airportList);
		return resMap;
	}
	
	
	/**
	 * 将用户刷选的订单信息导出到excle表格
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("exportOrderInfoToExcel")
	public void exportOrderInfoToExcel(@RequestParam(value = "startTime", defaultValue = "") String startTime,
			@RequestParam(value = "endTime", defaultValue = "") String endTime,
			@RequestParam(value = "airportId", required = false, defaultValue = "") String airportId,
			@RequestParam(value = "orderState", required = false, defaultValue = "") String orderState,
			@RequestParam(value = "invoiceState", required = false, defaultValue = "") String invoiceState,
			@RequestParam(value = "phoneOrUserName", required = false, defaultValue = "") String phoneOrUserName,
			HttpServletRequest request, HttpServletResponse response) {

		//1 用户可以看到机场列表
		List<String> aiportIdList = returnAirportIdList();
		//2 设置刷选条件
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		if (!startTime.equals("")) {
			paramsMap.put("startTime", startTime);
		}
		if (!endTime.equals("")) {
			paramsMap.put("endTime", endTime);
		}
		if (!phoneOrUserName.equals("")) {
			paramsMap.put("phoneOrUserName", phoneOrUserName);
		}
		if (aiportIdList != null && aiportIdList.size() > 0) {
			paramsMap.put("aiportIdList", aiportIdList);
		}
		if (!airportId.equals("")) {
			paramsMap.put("airportId", airportId);
		}if(!orderState.equals("")){
			paramsMap.put("orderState", orderState);
		}
		if(!invoiceState.equals("")){
			paramsMap.put("invoiceState", invoiceState);
		}
		orderManagerImpl.exportOrderInfo(paramsMap, request, response);
	}
	
	
	/**
	 * 解绑操作
	 * @param card
	 */
	@RequestMapping("unbundCard")
	public String unbundCard(@RequestParam(value="vipCardNo",required=true) String vipCardNo){
		User user = getCurrentUser();
		try {
			//if(airportEasyManagerImpl.disabledVipCard(vipCardNo)){
				//更新卡状态，将解绑失败变成已退卡
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("card_state", VipCardState.BACK_CARD.getName());
				map.put("cardNo", vipCardNo);
				try {
					vipCardManagerImpl.activeAppCard(map);
					//***日志记录（正常）
					SmartLog.info(ModuleType.VIPCARD_MODULE.getName(),vipCardNo,
							"【退款列表-手动解绑】，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【将卡状态变为-已退卡】，操作结果：【成功】");
				} catch (Exception e) {
					//****日志记录（异常）
					SmartLog.error(e,ModuleType.VIPCARD_MODULE.getName(),vipCardNo,
							"【退款列表-手动解绑】，操作员编号："+user.getId()+"，操作员姓名："+user.getName()+"，操作内容：【将卡状态变为-已退卡】，操作结果：【失败】");
				}
				
				
			//};
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:goBackCardListManagementView";
	}
	
}	
