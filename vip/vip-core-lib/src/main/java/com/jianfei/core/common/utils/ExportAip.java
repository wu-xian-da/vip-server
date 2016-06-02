/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年6月1日-下午4:50:49
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年6月1日 下午4:50:49
 * 
 * @version 1.0.0
 *
 */
public class ExportAip {

	private String name;

	private String phone;

	private String dateTime;

	private String address;

	private String email;

	private String state;

	/**
	 * 创建一个新的实例 ExportAip.
	 *
	 */
	public ExportAip() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建一个新的实例 ExportAip.
	 *
	 * @param name
	 * @param phone
	 * @param dateTime
	 * @param address
	 * @param email
	 * @param state
	 */
	public ExportAip(String name, String phone, String dateTime,
			String address, String email, String state) {
		this.name = name;
		this.phone = phone;
		this.dateTime = dateTime;
		this.address = address;
		this.email = email;
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
