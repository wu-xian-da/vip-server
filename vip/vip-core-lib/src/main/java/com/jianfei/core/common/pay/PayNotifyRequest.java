package com.jianfei.core.common.pay;


/**
 * 第三方支付回调通知请求
 * @author leoliu
 *
 */
public class PayNotifyRequest {
	private String tradeNo;		//第三方支付订单号
	private String outTradeNo;	//商户订单号
	private String payTime;		//支付时间
	private int payType;		//支付类型  1.alipay 2.wechat
	private String payUserId;	//支付用户ID  alipay为支付宝账号，wechat为openid
	private String sign;		//回调签名
	private String signResult;	//验签
	private String returnCode;	//wechat用通信返回码
	private String resultCode;	//wechat用交易返回码
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
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public String getPayUserId() {
		return payUserId;
	}
	public void setPayUserId(String payUserId) {
		this.payUserId = payUserId;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getSignResult() {
		return signResult;
	}
	public void setSignResult(String signResult) {
		this.signResult = signResult;
	}
	
}
