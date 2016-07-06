/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月6日-上午11:15:50
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.enu;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月6日 上午11:15:50
 * 
 * @version 1.0.0
 *
 */
public enum PickUpType {
	/**
	 * 接送机类型
	 */
	acceptType("1", "接机"),

	/**
	 * 送机类型
	 */
	sendType("2", "送机"),
	/**
	 * 未投递
	 */
	unsubmited("1", "未投递"),
	/**
	 * 已投递
	 */
	submited("2", "已投递");

	private String type;
	private String value;

	private PickUpType(String type, String value) {
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
