/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午4:56:42
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.pay;

/**
 *
 * @Description: TODO
 * @author: liu.lei@jianfeitech.com 
 * @date: 2016年5月23日 下午4:56:42 
 * 
 * @version 1.0.0
 *
 */
public class PreCreateResult {
	private String tradeNo;
	private String code;
	private String msg;
	private String qrUrl;
	private String qrImageUrl;
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getQrUrl() {
		return qrUrl;
	}
	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}
	public String getQrImageUrl() {
		return qrImageUrl;
	}
	public void setQrImageUrl(String qrImageUrl) {
		this.qrImageUrl = qrImageUrl;
	}
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
	
}
