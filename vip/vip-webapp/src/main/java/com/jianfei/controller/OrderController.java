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

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppCardBack;
import com.jianfei.core.bean.AppInvoice;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.bean.AriPort;
import com.jianfei.core.bean.User;
import com.jianfei.core.common.enu.InvoiceState;
import com.jianfei.core.common.enu.MsgType;
import com.jianfei.core.common.utils.MessageDto;
import com.jianfei.core.common.utils.UUIDUtils;
import com.jianfei.core.dto.OrderDetailInfo;
import com.jianfei.core.dto.OrderShowInfoDto;
import com.jianfei.core.service.base.AppInvoiceManager;
import com.jianfei.core.service.base.AriPortManager;
import com.jianfei.core.service.base.impl.ValidateCodeManagerImpl;
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
public class OrderController extends BaseController {
	private Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private OrderManagerImpl orderManagerImpl;
	@Autowired
	private MsgInfoManagerImpl msgInfoManagerImpl;
	@Autowired
	private AppInvoiceManager appInvoiceManagerImpl;
	@Autowired
	private AriPortManager ariPortService;
	@Autowired
	private ValidateCodeManagerImpl validateCodeManager;
	
	/*
	 * 跳转到订单列表页面
	 */
	@RequiresPermissions(value="system:orderList:home")
	@RequestMapping(value="/goOrderManagementView")
	public String orderList(HttpServletResponse response,Model model){
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("expires", -1);
		//所有的机场列表
		Map<String,Object> resMap = new HashMap<String,Object>();
		List<Map<String, Object>> list = ariPortService.mapList(resMap);
		model.addAttribute("airPostList", list);
		return "orders/orderManagement";
	}
	
	/*
	 * 跳转到退卡列表页面
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
	 * 跳转到发票列表页面
	 * @return
	 */
	@RequestMapping("/gotoInvoiceListView")
	public String gotoInvoiceListView(){
		return "orders/invoiceListManagement";
	}
	
	/**
	 * 获取需要开发票的订单
	 * @param pageNo
	 * @param pageSize
	 * @param phoneOrUserName
	 * @return
	 */
	@RequestMapping("invoiceList")
	@ResponseBody
	public Map<String,Object> invoiceList(@RequestParam(value="page",defaultValue="1") Integer pageNo,
			@RequestParam(value="rows",defaultValue="10") Integer pageSize,
			@RequestParam(value="invoiceFlag",required=false,defaultValue="") String invoiceFlag,
			@RequestParam(value="phoneOrUserName",required=false,defaultValue="") String phoneOrUserName){
		Map<String,Object> map = new HashMap<String,Object>();
		if(!invoiceFlag.equals("")){
			map.put("invoiceFlag", invoiceFlag);
		}
		if(!phoneOrUserName.equals("")){
			map.put("phoneOrUserName", phoneOrUserName);
		}
		Map<String,Object> resMap = new HashMap<String,Object>();
		PageInfo<OrderShowInfoDto> pageInfo = orderManagerImpl.invoicePageList(pageNo, pageSize, map);
		List<OrderShowInfoDto> invoiceList = pageInfo.getList();
		if(invoiceList == null || invoiceList.size() ==0){
			resMap.put("total", 0);
		}else{
			for(OrderShowInfoDto invoiceInfo : invoiceList){
				int invoiceState = invoiceInfo.getInvoiceFlag();
				JSONObject outData = new JSONObject(); 
				outData.put("invoiceId", invoiceInfo.getInvoiceId());
				outData.put("orderId", invoiceInfo.getOrderId());
				//订单编号
				String orderId = invoiceInfo.getOrderId();
				if(invoiceState == 1){//发票未邮寄
					invoiceInfo.setInvoiceFlagName("发票未邮寄");
					invoiceInfo.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a><button class='btn btn-back' onclick='drawBill("+outData+")'>开发票</button>");
					//invoiceInfo.setOperation("<button class='btn btn-back' onclick='drawBill()'>开发票</button>");
				}
				if(invoiceState == 2){//发票已邮寄
					invoiceInfo.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
					invoiceInfo.setInvoiceFlagName("发票已邮寄");
				}
			}
			resMap.put("total", invoiceList.size());
		}
		resMap.put("rows", invoiceList);
		return resMap;
	}
	
	/**
	 * 将发票单号记录到数据表中
	 * @return
	 */
	@RequestMapping("handelInvoiceInfo")
	@ResponseBody
	public Map<String,Object> handelInvoiceInfo(String invoiceId,String invoiceNo,String orderId){
		Map<String,Object> resMap = new HashMap<String,Object>();
		//将订单号保存到订单表中
		AppInvoice appInvoice = new AppInvoice();
		appInvoice.setInvoiceNo(invoiceNo);
		appInvoice.setInvoiceId(invoiceId);
		appInvoice.setInvoiceType(InvoiceState.SEND_INVOICE.getName());
		appInvoiceManagerImpl.updateByPrimaryKeySelective(appInvoice);
		//将订单的发票状态改为已邮寄
		AppOrders addInfoDto = new AppOrders();
		addInfoDto.setOrderId(orderId);
		addInfoDto.setInvoiceFlag(2);
		orderManagerImpl.updateOrderPayInfo(addInfoDto);
		resMap.put("result", 1);
		return resMap;
	}
	
	/*
	 * 根据订单号查询订单详细信息
	 */
	@RequestMapping(value="/returnOrderDetailInfoByOrderId")
	public String returnOrderDetailInfoByOrderId(String orderId,Model model){
		//订单基本信息
		OrderDetailInfo orderDetailInfo = orderManagerImpl.returnOrderDetailInfoByOrderId(orderId);
		//发票信息
		AppInvoice appInvoice = appInvoiceManagerImpl.selInvoiceInfoByOrderId(orderId);
		//退卡余额信息
		AppCardBack appCardBack = orderManagerImpl.selCustomerCard(orderId);
		
		model.addAttribute("orderDetailInfo", orderDetailInfo);
		if(appInvoice !=null){
			model.addAttribute("invoice", appInvoice);
		}
		if(appCardBack != null){
			model.addAttribute("appCardBack", appCardBack);
		}
		return "orders/orderDetail";
		
	}
	
	/*
	 * 订单列表分页查询
	 */
	@RequestMapping("orderList")
	@ResponseBody
	public Map<Object,Object> list(@RequestParam(value="page",defaultValue="1") Integer pageNo,
			@RequestParam(value="rows",defaultValue="10") Integer pageSize,
			@RequestParam(value="startTime",defaultValue="") String startTime,
			@RequestParam(value="endTime",defaultValue="") String endTime,
			@RequestParam(value="airportId",required=false,defaultValue="") String airportId,
			@RequestParam(value="orderState",required=false,defaultValue="5") Integer orderState,
			@RequestParam(value="invoiceState",required=false,defaultValue="3") Integer invoiceState,
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
		
		paramsMap.put("orderState",orderState);
		paramsMap.put("invoiceState", invoiceState);
		
		
		PageInfo<OrderShowInfoDto> pageinfo = orderManagerImpl.simplePage(pageNo, pageSize, paramsMap);
		Map<Object,Object> map = new HashMap<Object,Object>();
		List<OrderShowInfoDto> list = pageinfo.getList();
		String orderId = null;
		String phone = null;
		
		//权限校验
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		
		for(OrderShowInfoDto appOrder : list){
			int invoiceFlag = appOrder.getInvoiceFlag();
			appOrder.setInvoiceFlagName(returnInvoiceFlagName(invoiceFlag));
			if(appOrder.getOrderState() ==0){
				//未支付
				orderId = appOrder.getOrderId();
				appOrder.setOrderStateName("未支付");
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
				//是否有‘申请退款’权限
				boolean flag = subject.isPermitted("system:order:applyBackMoney");
				if(flag){
					appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a><button class='btn btn-back' onclick='onRefundApplication("+outData+",this)'>退单申请</button>");
				}else{
					appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
				}
				
				
			}else if(appOrder.getOrderState() == 2){
				appOrder.setOrderStateName("正在审核");
				JSONObject outData = new JSONObject(); 
				orderId = appOrder.getOrderId();
				double remainMoney = orderManagerImpl.remainMoney(orderId);
				outData.put("remainMoney", remainMoney);
				outData.put("orderId", appOrder.getOrderId());
				outData.put("phone", appOrder.getCustomerPhone());
				//2、获取验证码
				String smsCode = validateCodeManager.getSendValidateCode(appOrder.getCustomerPhone(), MsgType.BACK_CARD_APPLY);
				
				//是否有审核的权限
				boolean flag = subject.isPermitted("system:order:audit");
				if(flag){
					appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a><input style='width:50px' type='text' value='"+smsCode+"' /> <button class='btn btn-confirm' onclick='onRefund("+outData+")'>✓</button><button class='btn btn-close' onclick='onError("+outData+",this)'>✕</button>");
				}else{
					appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
				}
				
			
			}else if(appOrder.getOrderState() ==3){
				//退款
				JSONObject outData = new JSONObject(); 
				orderId = appOrder.getOrderId();
				double remainMoney = orderManagerImpl.remainMoney(appOrder.getOrderId());//退款金额
				AppCardBack appCardBack = orderManagerImpl.selCustomerCard(appOrder.getOrderId());//退款卡号
				outData.put("remainMoney", remainMoney);
				outData.put("orderId", orderId);//订单号
				outData.put("backMoneyCard",appCardBack.getCustomerCard());
				outData.put("backType", appCardBack.getBackType());
				outData.put("backName", appCardBack.getBankName());
				outData.put("customerName", appCardBack.getCustomerName());
				
				//发票状态
				AppOrders orderInfo = orderManagerImpl.getOrderInfoByOrderId(appOrder.getOrderId());
				outData.put("invoice", orderInfo.getInvoiceFlag());
				appOrder.setOrderStateName("审核通过");
				
				//是否有最终退款查看的权限
				boolean flag = subject.isPermitted("system:order:refundsel");
				if(flag){
					appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a><button class='btn btn-refund' onclick='finalBackMoneyToUser("+outData+")'>退款</button>");
				}else{
					appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
				}
				
			
			}else if(appOrder.getOrderState() ==4){
				//退款成功
				orderId = appOrder.getOrderId();
				appOrder.setOrderStateName("已退款");
				appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
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
	public Map<Object,Object> backCardlist(@RequestParam(value="page",defaultValue="1") Integer pageNo,
			@RequestParam(value="rows",defaultValue="10") Integer pageSize,
			@RequestParam(value="backType",defaultValue="") String backType,
			@RequestParam(value="applyType",defaultValue="") String applyType,
			@RequestParam(value="orderState",defaultValue="10") Integer orderState){
		
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
		paramsMap.put("orderState", orderState);
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
				if(appOrder.getOrderState() ==3){
					//退款
					JSONObject outData = new JSONObject(); 
					orderId = appOrder.getOrderId();
					double remainMoney = orderManagerImpl.remainMoney(appOrder.getOrderId());//退款金额
					AppCardBack appCardBack = orderManagerImpl.selCustomerCard(appOrder.getOrderId());//退款卡号
					outData.put("remainMoney", remainMoney);
					outData.put("orderId", orderId);//订单号
					outData.put("backMoneyCard",appCardBack.getCustomerCard());
					outData.put("backType", appCardBack.getBackType());
					outData.put("phone", appOrder.getCustomerPhone());
					appOrder.setOrderStateName("审核通过");
					outData.put("backName", appCardBack.getBankName());
					outData.put("customerName", appCardBack.getCustomerName());
					//发票状态
					AppOrders orderInfo = orderManagerImpl.getOrderInfoByOrderId(appOrder.getOrderId());
					outData.put("invoice", orderInfo.getInvoiceFlag());
					//申请方式
					int applyTypes = appOrder.getApplyType();
					if(applyTypes == 0){
						appOrder.setApplyTypeName("客服");
					}else{
						appOrder.setApplyTypeName("现场");
					}
					//退卡方式
					int backCardTypes = appOrder.getBackType();
					if(backCardTypes == 1){
						appOrder.setBackTypeName("微信");
					}else if(backCardTypes == 2){
						appOrder.setBackTypeName("支付宝");
					}else if(backCardTypes == 3){
						appOrder.setBackTypeName("银行卡");
					}else{
						appOrder.setBackTypeName("现场");
					}
					
					
					//权限校验
					org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
					//是否有最终退款查看的权限
					boolean flag = subject.isPermitted("system:order:refundsel");
					if(flag){
						appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a><button class='btn btn-refund' onclick='finalBackMoneyToUser("+outData+")'>退款</button>");
					}else{
						appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
					}
					
					resList.add(appOrder);
				}else if(appOrder.getOrderState() ==4){
					//退款成功
					orderId = appOrder.getOrderId();
					appOrder.setOrderStateName("已退款");
					//申请方式
					int applyTypes = appOrder.getApplyType();
					if(applyTypes == 0){
						appOrder.setApplyTypeName("客服");
					}else{
						appOrder.setApplyTypeName("现场");
					}
					//退卡方式
					int backCardTypes = appOrder.getBackType();
					if(backCardTypes == 1){
						appOrder.setBackTypeName("微信");
					}else if(backCardTypes == 2){
						appOrder.setBackTypeName("支付宝");
					}else if(backCardTypes == 3){
						appOrder.setBackTypeName("银行卡");
					}else{
						appOrder.setBackTypeName("现场");
					}
					appOrder.setOperation("<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
					resList.add(appOrder);
				}
			}
			if(resList !=null && resList.size()>0){
				map.put("total", resList.size());
			}else{
				map.put("total", 0);
			}
			
		}else{
			map.put("total", 0);
		}
		
		map.put("rows", resList);
		return map;
		
	}
	
	
	/*
	 * 退单申请,给用户发送短信验证码，并将短信验证码回显
	 */
	@RequestMapping(value="/applyBackCard")
	@ResponseBody
	public Map<String,Object> applyBackCard(String orderId,Integer operationType,String phone){
		//1、改变订单状态
		orderManagerImpl.updateOrderStateByOrderId(orderId, operationType);
		//2、获取验证码
		String smsCode = validateCodeManager.getValidateCode(phone, MsgType.BACK_CARD_APPLY);
		//发送短信****
		try {
			validateCodeManager.sendMsgInfo(phone, MsgType.BACK_CARD_APPLY, smsCode);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("发送短信失败");
		}
		
		
		JSONObject outData = new JSONObject(); 
		double remainMoney = orderManagerImpl.remainMoney(orderId);
		outData.put("remainMoney", remainMoney);
		outData.put("orderId",orderId);
		outData.put("phone", phone);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", "1");
		map.put("orderStateName", "正在审核");
		//权限校验
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		//是否有审核的权限
		boolean flag = subject.isPermitted("system:order:audit");
		if(flag){
			map.put("data", "<input style='width:50px' type='text' value='"+smsCode+"' /> <button class='btn btn-confirm' onclick='onRefund("+outData+")'>✓</button><button class='btn btn-close' onclick='onError("+outData+",this)'>✕</button>");
		}else{
			map.put("data", "");
		}
		
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
		AppOrders orderInfo = orderManagerImpl.getOrderInfoByOrderId(orderId);
		JSONObject outData = new JSONObject(); 
		outData.put("orderId", orderId);
		outData.put("opr", "0");
		outData.put("phone", phone);
		outData.put("invoice", orderInfo.getInvoiceFlag());
		
		resMap.put("result", "1");
		
		//权限校验
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		//是否有退款申请的权限
		boolean flag = subject.isPermitted("system:order:applyBackMoney");
		if(flag){
			resMap.put("data", "<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a><button class='btn btn-back' onclick='onRefundApplication("+outData+",this)'>退单申请</button>");
		}else{
			resMap.put("data", "<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
		}
		
		resMap.put("orderStateName", "已支付");
		return resMap;	
		
	}
	
	/*
	 * 核算退卡金额,并将用户账号信息记录到退卡流水表中
	 */
	@RequestMapping(value="/onRefund")
	@ResponseBody
	public Map<String,Object> onRefund(String orderId,String backCardNo,String remainMoney,String payMethod,Integer opr,
			@RequestParam(value="userNames",defaultValue="",required=false) String userNames,
			@RequestParam(value="banckName",defaultValue="",required=false) String banckName){
		System.out.println("orderId="+orderId+" backCardNo="+backCardNo);
		//1、将订单状态有'正在审核'变成'审核通过'
		orderManagerImpl.updateOrderStateByOrderId(orderId, opr);
		
		//2、将退款信息录入到流水表中
		
		User user = getCurrentUser();
		//****审批人员id
		String userId = user.getId()+"";
		
		AppCardBack appCardBack = new AppCardBack();
		appCardBack.setBackId(UUIDUtils.getPrimaryKey());
		appCardBack.setCreateTime(new Date());
		appCardBack.setOrderId(orderId);
		appCardBack.setCustomerCard(backCardNo);
		appCardBack.setMoney(Float.parseFloat(remainMoney));
		appCardBack.setBackType(Integer.parseInt(payMethod));
		appCardBack.setCreaterId(userId);
		appCardBack.setBankName(banckName);//开户行
		appCardBack.setCustomerName(userNames);//开户者姓名
		orderManagerImpl.insertBackCardInfo(appCardBack);
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("result", 1);//1代表成功
		
		//权限校验
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		//是否有退款查看的权限
		boolean flag = subject.isPermitted("system:order:refundsel");
		if(flag){
			resMap.put("data","<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a><button class='btn btn-refund' onclick='finalBackMoneyToUser(500)'>退款</button>");
		}else{
			resMap.put("data","<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
		}
		
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

		User user = getCurrentUser();
		//****审核员id
		String userId = user.getId()+"";
		
		Map<String,Object> parMap = new HashMap<String,Object>();
		parMap.put("finishTime", new Date());
		parMap.put("orderId", orderId);
		parMap.put("checkId", userId);
		orderManagerImpl.updateBackCardByOrderId(parMap);
		
		Map<String,Object> resMap = new HashMap<String,Object>();
		resMap.put("result", "1");
		resMap.put("data","<a href='returnOrderDetailInfoByOrderId?orderId="+orderId+"'><button class='btn'>查看</button></a>");
		resMap.put("orderStateName", "已退款");
		return resMap;
		
	}
	
	/**
	 * 返回当前用户可以查看的机场列表
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
	 * 根据发票状态返回发票的中文名称
	 * @param invoiceFlag
	 * @return
	 */
	public String returnInvoiceFlagName(Integer invoiceFlag){
		String invoiceFlagName = "";
		if(invoiceFlag == 0){
			invoiceFlagName = "未开";
		}else if(invoiceFlag == 1){
			invoiceFlagName = "发票未邮寄"; 
		}else{
			invoiceFlagName = "发票已邮寄";
		}
		return invoiceFlagName;
	}
}	
