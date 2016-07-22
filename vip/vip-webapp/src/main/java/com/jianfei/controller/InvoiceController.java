package com.jianfei.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppInvoice;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.common.enu.InvoiceState;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.StateChangeUtils;
import com.jianfei.core.dto.OrderShowInfoDto;
import com.jianfei.core.service.base.impl.AppInvoiceManagerImpl;
import com.jianfei.core.service.order.impl.OrderManagerImpl;

/**
 * 发票控制类
 * @Description: TODO
 * @author guo.jian   
 * @Title: InvoiceController.java
 * @date 2016年7月21日 上午9:34:08 
 * @Version 1.0.0
 */
@Controller
public class InvoiceController {
	private static String staticPath =  GloabConfig.getConfig("static.resource.server.address");
	@Autowired
	private OrderManagerImpl orderManagerImpl;
	@Autowired
	private AppInvoiceManagerImpl appInvoiceManagerImpl;
	
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
			@RequestParam(value="rows",defaultValue="20") Integer pageSize,
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
				//订单状态
				int ordersState = invoiceInfo.getOrderState();
				invoiceInfo.setOrderStateName(StateChangeUtils.returnOrderStateName(ordersState));
				//发票状态
				int invoiceState = invoiceInfo.getInvoiceFlag();
				//发票种类
				int invoiceKind = invoiceInfo.getInvoiceKind();
				//组装用于显示的数据
				JSONObject outData = new JSONObject(); 
				outData.put("invoiceId", invoiceInfo.getInvoiceId());
				outData.put("orderId", invoiceInfo.getOrderId());
				outData.put("invoiceKind", invoiceKind);
				outData.put("invoiceContent", invoiceInfo.getInvoiceContent());
				outData.put("customerName", invoiceInfo.getCustomerName());
				outData.put("customerPhone", invoiceInfo.getCustomerPhone());
				outData.put("provinceName",invoiceInfo.getProvinceName());
				outData.put("cityName",invoiceInfo.getCityName());
				outData.put("countryName",invoiceInfo.getCountryName());
				outData.put("address",invoiceInfo.getAddress());
				outData.put("invoiceTitle", invoiceInfo.getInvoiceTitle());
				outData.put("postCode", invoiceInfo.getPostCode());
				if(invoiceKind == 0){//--普通发票
					
				}else{//--专用发票
					outData.put("companyName", invoiceInfo.getCompanyName());
					outData.put("companyAddress", invoiceInfo.getCompanyAddress());
					outData.put("companyPhone", invoiceInfo.getCompanyPhone());
					outData.put("businessLicenseUrl",staticPath+invoiceInfo.getBusinessLicenseUrl());
					outData.put("companyTaxNo", invoiceInfo.getCompanyTaxNo());
				}
				if(invoiceState == 1){//发票未邮寄
					invoiceInfo.setInvoiceFlagName("发票未邮寄");
					if(ordersState == 3 || ordersState == 4){
						invoiceInfo.setOperation("<button class='btn'onclick='lookOverInvoiceInfo("+outData+")'>查看</button>");
					}else{
						
						invoiceInfo.setOperation("<button class='btn btn-back' onclick='drawBill("+outData+")'>开发票</button>");
					}
					
				}
				if(invoiceState == 2){//发票已邮寄
					outData.put("invoiceNo", invoiceInfo.getInvoiceNo());
					invoiceInfo.setOperation("<button class='btn'onclick='lookOverInvoiceInfo("+outData+")'>查看</button>");
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
		//更新发票表信息
		AppInvoice appInvoice = new AppInvoice();
		appInvoice.setInvoiceNo(invoiceNo);
		appInvoice.setInvoiceId(invoiceId);
		appInvoice.setInvoiceState(InvoiceState.SEND_INVOICE.getName());
		appInvoice.setUpdateTime(new Date());
		appInvoiceManagerImpl.updateByPrimaryKeySelective(appInvoice);
		//将订单的发票状态改为已邮寄
		AppOrders addInfoDto = new AppOrders();
		addInfoDto.setOrderId(orderId);
		addInfoDto.setInvoiceFlag(InvoiceState.SEND_INVOICE.getName());
		orderManagerImpl.updateOrderInfo(addInfoDto);
		resMap.put("result", 1);
		return resMap;
	}
}
