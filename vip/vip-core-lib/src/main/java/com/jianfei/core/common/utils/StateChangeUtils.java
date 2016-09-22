package com.jianfei.core.common.utils;
/**
 * 用于映射状态和中文名称
 * @Description: TODO
 * @author guo.jian   
 * @Title: OrderStateChangeUtils.java
 * @date 2016年7月20日 下午4:53:09 
 * @Version 1.0.0
 */
public class StateChangeUtils {
	/**
	 * 返回申请退卡途径的中文名称
	 * @param applyMethod
	 * @return
	 */
	public static String returnApplyBackCardMethodName(Integer applyMethod){
		String methodName = "";
		if(applyMethod == 0){
			methodName = "现场";
		}else if(applyMethod == 1){
			methodName = "客服";
		}else{
			methodName = "紧急";
		}
		return methodName;
	}
	
	/**
	 * 返回退款金额的支付方式
	 * @param backMoneyMethod
	 * @return
	 */
	public static String returnBackMoneyMethodName(Integer backMoneyMethod){
		String backMoneyMethodName = "";
		if(backMoneyMethod == 1){
			backMoneyMethodName = "微信";
		}else if(backMoneyMethod == 2){
			backMoneyMethodName = "支付宝";
		}else if(backMoneyMethod == 3){
			backMoneyMethodName = "银行卡";
		}else{
			backMoneyMethodName = "紧急退款";
		}
		return backMoneyMethodName;
	}
	
	/**
	 * 根据卡状态返回卡状态的中文提示
	 * @param cardState
	 * @return
	 */
	public static String returnCardStateName(Integer cardState){
		String cardStateName = "";
		if(cardState == 0){
			cardStateName = "未绑定";
		}else if(cardState == 1){
			cardStateName = "绑定成功";
		}else if(cardState == 2){
			cardStateName = "已退卡";
		}else if(cardState == 3){
			cardStateName = "绑定失败";
		}else if(cardState == 4){
			cardStateName = "待绑定";
		}else if(cardState == 5){
			cardStateName = "解绑失败";
		}else if(cardState == 6){
			cardStateName = "绑定成功已激活";
		}else{
			cardStateName = "已过期";
		}
		return cardStateName;
	}
	
	/**
	 * 根据订单状态返回中文名称
	 * @param orderState
	 * @return
	 */
	public static String returnOrderStateName(Integer orderState){
		String orderStateName = "";
		if(orderState == 0){
			orderStateName = "未支付";
		}else if(orderState == 1){
			orderStateName = "已支付";
		}else if(orderState == 2){
			orderStateName = "正在审核";
		}else if(orderState == 3){
			orderStateName = "审核通过";
		}else if(orderState == 4){
			orderStateName = "已退款";
		}else{
			orderStateName = "已失效";
		}
		return orderStateName;
	}
	
	/**
	 * 根据发票状态返回发票的中文名称
	 * @param invoiceFlag
	 * @return
	 */
	public static String returnInvoiceFlagName(Integer invoiceFlag){
		String invoiceFlagName = "";
		if(invoiceFlag == 0){
			invoiceFlagName = "不需要";
		}else if(invoiceFlag == 1){
			invoiceFlagName = "未邮寄"; 
		}else{
			invoiceFlagName = "已邮寄";
		}
		return invoiceFlagName;
	}
	
	
}
