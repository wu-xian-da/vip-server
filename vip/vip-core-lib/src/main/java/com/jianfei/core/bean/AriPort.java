/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月17日-下午5:32:01
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.bean;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jianfei.core.common.utils.IdGen;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月17日 下午5:32:01
 * 
 * @version 1.0.0
 *
 */
public class AriPort implements Serializable {

	private static final long serialVersionUID = 570879643301988138L;
	private String id = IdGen.uuid();
	private String name;
	private Integer state;
	private Integer agentNum;
	private String province;
	private String city;
	private String country;
	private String address;
	private Integer longitude;
	private Integer latitude;
	private String headerName;
	private String headerPhone;
	private Integer dtflag;

	private List<AppOrders> appOrders = Lists.newArrayList();
	/**
	 * id
	 *
	 * @return  the id
	 * @version   1.0.0
	 */
	
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * name
	 *
	 * @return  the name
	 * @version   1.0.0
	 */
	
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * state
	 *
	 * @return  the state
	 * @version   1.0.0
	 */
	
	public Integer getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * agentNum
	 *
	 * @return  the agentNum
	 * @version   1.0.0
	 */
	
	public Integer getAgentNum() {
		return agentNum;
	}

	/**
	 * @param agentNum the agentNum to set
	 */
	public void setAgentNum(Integer agentNum) {
		this.agentNum = agentNum;
	}

	/**
	 * province
	 *
	 * @return  the province
	 * @version   1.0.0
	 */
	
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * city
	 *
	 * @return  the city
	 * @version   1.0.0
	 */
	
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * country
	 *
	 * @return  the country
	 * @version   1.0.0
	 */
	
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * address
	 *
	 * @return  the address
	 * @version   1.0.0
	 */
	
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * longitude
	 *
	 * @return  the longitude
	 * @version   1.0.0
	 */
	
	public Integer getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Integer longitude) {
		this.longitude = longitude;
	}

	/**
	 * latitude
	 *
	 * @return  the latitude
	 * @version   1.0.0
	 */
	
	public Integer getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Integer latitude) {
		this.latitude = latitude;
	}

	/**
	 * headerName
	 *
	 * @return  the headerName
	 * @version   1.0.0
	 */
	
	public String getHeaderName() {
		return headerName;
	}

	/**
	 * @param headerName the headerName to set
	 */
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	/**
	 * headerPhone
	 *
	 * @return  the headerPhone
	 * @version   1.0.0
	 */
	
	public String getHeaderPhone() {
		return headerPhone;
	}

	/**
	 * @param headerPhone the headerPhone to set
	 */
	public void setHeaderPhone(String headerPhone) {
		this.headerPhone = headerPhone;
	}

	/**
	 * dtflag
	 *
	 * @return  the dtflag
	 * @version   1.0.0
	 */
	
	public Integer getDtflag() {
		return dtflag;
	}

	/**
	 * @param dtflag the dtflag to set
	 */
	public void setDtflag(Integer dtflag) {
		this.dtflag = dtflag;
	}

	/**
	 * appOrders
	 *
	 * @return  the appOrders
	 * @version   1.0.0
	 */
	
	public List<AppOrders> getAppOrders() {
		return appOrders;
	}

	/**
	 * @param appOrders the appOrders to set
	 */
	public void setAppOrders(List<AppOrders> appOrders) {
		this.appOrders = appOrders;
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
}
