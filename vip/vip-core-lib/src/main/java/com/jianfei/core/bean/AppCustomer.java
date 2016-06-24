package com.jianfei.core.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

public class AppCustomer implements Serializable {
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @version 1.0.0
	 */

	private static final long serialVersionUID = 1590028943359497362L;

	private String customerId;

	private String customerName;

	private String customerIdenti;

	private String phone;

	private Integer sex;

	private String email;

	private String address;
	@JsonIgnore
	private String password;
	@JsonIgnore
	private Integer useType;

	private String remark1;
	@JsonIgnore
	private Integer dtflag;

	private String province;

	private String city;

	@JsonIgnore
	private String country;

	private Date createTime;

	private List<AppVipcard> vipCards= Lists.newArrayList();

	private String code;

	private String provinceName;

	private String cityName;

	/**
	 * createTime
	 *
	 * @return the createTime
	 * @version 1.0.0
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId == null ? null : customerId.trim();
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName == null ? null : customerName.trim();
	}

	public String getCustomerIdenti() {
		return customerIdenti;
	}

	public void setCustomerIdenti(String customerIdenti) {
		this.customerIdenti = customerIdenti == null ? null : customerIdenti
				.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Integer getUseType() {
		return useType;
	}

	public void setUseType(Integer useType) {
		this.useType = useType;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1 == null ? null : remark1.trim();
	}

	public Integer getDtflag() {
		return dtflag;
	}

	public void setDtflag(Integer dtflag) {
		this.dtflag = dtflag;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country == null ? null : country.trim();
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}


	public String getCode() {
		//TODO 用户二维码生成规则
		return "111111";
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<AppVipcard> getVipCards() {
		return vipCards;
	}

	public void setVipCards(List<AppVipcard> vipCards) {
		this.vipCards = vipCards;
	}
}