/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午4:56:42
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.thirdpart;

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
	private String result;
	private String qrUrl;
	private String qrImageUrl;
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
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
	
}
