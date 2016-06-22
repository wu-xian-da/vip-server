package com.jianfei.core.common.pay;

public class PayQueryResult {
	/**
	 * code
	 * 0:已支付，其他非0值各个支付接口的含义不一样，具体可以参考各支付接口的返回代码
	 * 1:未支付，
	 * 2:交易关闭
	 * 3:出现错误
	 * 
	 */
	private String code;
	/**
	 * 对返回值的说明文字
	 */
	private String msg;
	/**
	 * 商户系统的订单号
	 */
	private String tradeNo;
	/**
	 * 第三方支付系统订单号
	 */
	private String outTradeNo;
	/**
	 * 付款账号：
	 * 
	 */
	private String payUserId;
	/**
	 * 付款时间
	 */
	private String payTime;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getPayUserId() {
		return payUserId;
	}
	public void setPayUserId(String payUserId) {
		this.payUserId = payUserId;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
	
	
}
