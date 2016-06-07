/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年6月3日-上午10:38:03
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.enu;

/**
 *
 * @Description: 发票类型
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年6月3日 上午10:38:03
 * 
 * @version 1.0.0
 *
 */
public enum PostType {
	/**
	 * 邮寄类型
	 */
	personType("1", "个人"),

	/**
	 * 邮寄类型
	 */
	companyType("0", "公司");

	private String type;
	private String value;

	private PostType(String type, String value) {
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
