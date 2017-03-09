package com.jianfei.core.dto;

import com.jianfei.core.common.myAnnotation.ExcelAnnotation;

@ExcelAnnotation(name = "vip卡核销记录表")
public class VipCardConsumeDto {
	@ExcelAnnotation(name = "业务员姓名")
	private String agentName;

	@ExcelAnnotation(name = "用户姓名")
	private String customerName;

	@ExcelAnnotation(name = "用户手机号")
	private String customerPhone;

	@ExcelAnnotation(name = "订单号")
	private String orderId;

	@ExcelAnnotation(name = "vip卡号")
	private String cardNo;

	@ExcelAnnotation(name = "订单状态")
	private String orderStateName;

	@ExcelAnnotation(name = "消费次数")
	private int consumeNum;

	@ExcelAnnotation(name = "消费记录")
	private String consumeInfo;

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOrderStateName() {
		return orderStateName;
	}

	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getConsumeNum() {
		return consumeNum;
	}

	public void setConsumeNum(int consumeNum) {
		this.consumeNum = consumeNum;
	}

	public String getConsumeInfo() {
		return consumeInfo;
	}

	public void setConsumeInfo(String consumeInfo) {
		this.consumeInfo = consumeInfo;
	}

}
