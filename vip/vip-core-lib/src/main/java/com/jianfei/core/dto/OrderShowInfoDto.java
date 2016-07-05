/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月18日-下午8:39:34
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jianfei.core.common.utils.GloabConfig;

/**
 * 订单列表页面展示的订单信息
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月18日 下午8:39:34 
 * 
 * @version 1.0.0
 *
 */
public class OrderShowInfoDto implements Serializable {
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @version 1.0.0
	 */
	
	private static final long serialVersionUID = 1L;
	private String orderId;//订单编号
	private Date orderTime;//订单日期
	private String airportName;//场站名称
	private String agentName;//业务员名称
	private String customerName;//用户名称
	private String customerPhone;//用户手机
	private int orderState;//订单状态 
	private String orderStateName;//订单状态名称
	private int invoiceFlag;//是否需要发票 0代表不需要
	private String invoiceFlagName;//是否需要发票的中文提示
	private String operation;//操作项
	private int applyType;//申请方式
	private String applyTypeName;
	private String backTypeName;
	private int backType;//退卡方式
	private float remainMoney;//余额
	private String vipCardNo;//卡号
    private int cardState;//卡状态
    private String cardStateName;//卡状态的中文提示
    
    private String invoiceId; //发票id
    private String invoiceNo;//发票单号
    private int invoiceKind;//发票种类
    private String companyName;//公司名称
    private String companyAddress;//公司地址
    private String companyPhone;//公司电话
    private String businessLicenseUrl;//营业执照url
    private String companyTaxNo;//税务发票
    private String invoiceContent;//发票内容
    private String provinceName;
    private String cityName;
    private String countryName;
    private String address;
    private String invoiceTitle;
    private String postCode;
    
	/**
	 * @return the cardStateName
	 */
	public String getCardStateName() {
		return cardStateName;
	}
	/**
	 * @param cardStateName the cardStateName to set
	 */
	public void setCardStateName(String cardStateName) {
		this.cardStateName = cardStateName;
	}
	/**
	 * @return the vipCardNo
	 */
	public String getVipCardNo() {
		return vipCardNo;
	}
	/**
	 * @param vipCardNo the vipCardNo to set
	 */
	public void setVipCardNo(String vipCardNo) {
		this.vipCardNo = vipCardNo;
	}
	/**
	 * @return the cardState
	 */
	public int getCardState() {
		return cardState;
	}
	/**
	 * @param cardState the cardState to set
	 */
	public void setCardState(int cardState) {
		this.cardState = cardState;
	}
	/**
	 * @return the invoiceId
	 */
	public String getInvoiceId() {
		return invoiceId;
	}
	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	/**
	 * @return the invoiceNo
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}
	/**
	 * @param invoiceNo the invoiceNo to set
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	/**
	 * @return the applyTypeName
	 */
	public String getApplyTypeName() {
		return applyTypeName;
	}
	/**
	 * @param applyTypeName the applyTypeName to set
	 */
	public void setApplyTypeName(String applyTypeName) {
		this.applyTypeName = applyTypeName;
	}
	/**
	 * @return the backTypeName
	 */
	public String getBackTypeName() {
		return backTypeName;
	}
	/**
	 * @param backTypeName the backTypeName to set
	 */
	public void setBackTypeName(String backTypeName) {
		this.backTypeName = backTypeName;
	}
	/**
	 * applyType
	 *
	 * @return  the applyType
	 * @version   1.0.0
	*/
	
	public int getApplyType() {
		return applyType;
	}
	/**
	 * @param applyType the applyType to set
	 */
	public void setApplyType(int applyType) {
		this.applyType = applyType;
	}
	/**
	 * backType
	 *
	 * @return  the backType
	 * @version   1.0.0
	*/
	
	public int getBackType() {
		return backType;
	}
	/**
	 * @param backType the backType to set
	 */
	public void setBackType(int backType) {
		this.backType = backType;
	}
	/**
	 * remainMoney
	 *
	 * @return  the remainMoney
	 * @version   1.0.0
	*/
	
	public float getRemainMoney() {
		return remainMoney;
	}
	/**
	 * @param remainMoney the remainMoney to set
	 */
	public void setRemainMoney(float remainMoney) {
		this.remainMoney = remainMoney;
	}
	/**
	 * operation
	 *
	 * @return  the operation
	 * @version   1.0.0
	*/
	
	public String getOperation() {
		return operation;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	/**
	 * orderId
	 *
	 * @return  the orderId
	 * @version   1.0.0
	*/
	
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * orderTime
	 *
	 * @return  the orderTime
	 * @version   1.0.0
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getOrderTime() {
		return orderTime;
	}
	/**
	 * @param orderTime the orderTime to set
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	/**
	 * airportName
	 *
	 * @return  the airportName
	 * @version   1.0.0
	*/
	
	public String getAirportName() {
		return airportName;
	}
	/**
	 * @param airportName the airportName to set
	 */
	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}
	/**
	 * agentName
	 *
	 * @return  the agentName
	 * @version   1.0.0
	*/
	
	public String getAgentName() {
		return agentName;
	}
	/**
	 * @param agentName the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	/**
	 * customerName
	 *
	 * @return  the customerName
	 * @version   1.0.0
	*/
	
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * customerPhone
	 *
	 * @return  the customerPhone
	 * @version   1.0.0
	*/
	
	public String getCustomerPhone() {
		return customerPhone;
	}
	/**
	 * @param customerPhone the customerPhone to set
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	/**
	 * orderState
	 *
	 * @return  the orderState
	 * @version   1.0.0
	*/
	
	public int getOrderState() {
		return orderState;
	}
	/**
	 * @param orderState the orderState to set
	 */
	public void setOrderState(int orderState) {
		this.orderState = orderState;
	}
	/**
	 * orderStateName
	 *
	 * @return  the orderStateName
	 * @version   1.0.0
	*/
	
	public String getOrderStateName() {
		return orderStateName;
	}
	/**
	 * @param orderStateName the orderStateName to set
	 */
	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}
	/**
	 * invoiceFlag
	 *
	 * @return  the invoiceFlag
	 * @version   1.0.0
	*/
	
	public int getInvoiceFlag() {
		return invoiceFlag;
	}
	/**
	 * @param invoiceFlag the invoiceFlag to set
	 */
	public void setInvoiceFlag(int invoiceFlag) {
		this.invoiceFlag = invoiceFlag;
	}
	/**
	 * invoiceFlagName
	 *
	 * @return  the invoiceFlagName
	 * @version   1.0.0
	*/
	
	public String getInvoiceFlagName() {
		return invoiceFlagName;
	}
	/**
	 * @param invoiceFlagName the invoiceFlagName to set
	 */
	public void setInvoiceFlagName(String invoiceFlagName) {
		this.invoiceFlagName = invoiceFlagName;
	}
	/**
	 * @return the invoiceKind
	 */
	public int getInvoiceKind() {
		return invoiceKind;
	}
	/**
	 * @param invoiceKind the invoiceKind to set
	 */
	public void setInvoiceKind(int invoiceKind) {
		this.invoiceKind = invoiceKind;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the companyAddress
	 */
	public String getCompanyAddress() {
		return companyAddress;
	}
	/**
	 * @param companyAddress the companyAddress to set
	 */
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	/**
	 * @return the companyPhone
	 */
	public String getCompanyPhone() {
		return companyPhone;
	}
	/**
	 * @param companyPhone the companyPhone to set
	 */
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	/**
	 * @return the businessLicenseUrl
	 */
	public String getBusinessLicenseUrl() {
		return GloabConfig.getConfig("static.resource.server.address")+businessLicenseUrl;
	}
	/**
	 * @param businessLicenseUrl the businessLicenseUrl to set
	 */
	public void setBusinessLicenseUrl(String businessLicenseUrl) {
		this.businessLicenseUrl = businessLicenseUrl;
	}
	/**
	 * @return the companyTaxNo
	 */
	public String getCompanyTaxNo() {
		return companyTaxNo;
	}
	/**
	 * @param companyTaxNo the companyTaxNo to set
	 */
	public void setCompanyTaxNo(String companyTaxNo) {
		this.companyTaxNo = companyTaxNo;
	}
	/**
	 * @return the invoiceContent
	 */
	public String getInvoiceContent() {
		return invoiceContent;
	}
	/**
	 * @param invoiceContent the invoiceContent to set
	 */
	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}
	/**
	 * @param provinceName the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}
	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	/**
	 * @return the address
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
	 * @return the invoiceTitle
	 */
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	/**
	 * @param invoiceTitle the invoiceTitle to set
	 */
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}
	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	
	
	
}
