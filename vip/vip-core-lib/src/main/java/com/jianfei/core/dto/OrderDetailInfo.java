/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月19日-下午2:09:40
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 订单详细信息
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月19日 下午2:09:40 
 * 
 * @version 1.0.0
 *
 */
public class OrderDetailInfo extends OrderShowInfoDto{

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @version 1.0.0
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String agentPhone;//业务人员手机号
	private String vipCardNo;//vip卡号
	private int cardType;//卡类型 1->1年卡
	private float initMoney;//卡初始金额
	private float payMoney;//订单金额
	private int payMethod;//支付方式
	private Date payTime;//支付时间
	private Date activatTime;//激活时间
	private String customerIdent;//用户身份证号
	private int sex;//用户性别
	private String customerProvinceName;
	private String customerCityName;
	private String address;//常驻地址
	private String email;//邮箱地址
	private int invoiceType;//发票类型
	private String invoiceTitle;//发票抬头
	
	
	/**
	 * @return the customerProvinceName
	 */
	public String getCustomerProvinceName() {
		return customerProvinceName;
	}
	/**
	 * @param customerProvinceName the customerProvinceName to set
	 */
	public void setCustomerProvinceName(String customerProvinceName) {
		this.customerProvinceName = customerProvinceName;
	}
	/**
	 * @return the customerCityName
	 */
	public String getCustomerCityName() {
		return customerCityName;
	}
	/**
	 * @param customerCityName the customerCityName to set
	 */
	public void setCustomerCityName(String customerCityName) {
		this.customerCityName = customerCityName;
	}
	/**
	 * agentPhone
	 *
	 * @return  the agentPhone
	 * @version   1.0.0
	*/
	
	public String getAgentPhone() {
		return agentPhone;
	}
	/**
	 * @param agentPhone the agentPhone to set
	 */
	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
	}
	/**
	 * vipCardNo
	 *
	 * @return  the vipCardNo
	 * @version   1.0.0
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
	 * cardType
	 *
	 * @return  the cardType
	 * @version   1.0.0
	*/
	
	public int getCardType() {
		return cardType;
	}
	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(int cardType) {
		this.cardType = cardType;
	}
	/**
	 * initMoney
	 *
	 * @return  the initMoney
	 * @version   1.0.0
	*/
	
	public float getInitMoney() {
		return initMoney;
	}
	/**
	 * @param initMoney the initMoney to set
	 */
	public void setInitMoney(float initMoney) {
		this.initMoney = initMoney;
	}
	/**
	 * payMethod
	 *
	 * @return  the payMethod
	 * @version   1.0.0
	*/
	
	public int getPayMethod() {
		return payMethod;
	}
	/**
	 * @param payMethod the payMethod to set
	 */
	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}
	/**
	 * payTime
	 *
	 * @return  the payTime
	 * @version   1.0.0
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getPayTime() {
		return payTime;
	}
	/**
	 * @param payTime the payTime to set
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	/**
	 * activatTime
	 *
	 * @return  the activatTime
	 * @version   1.0.0
	*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getActivatTime() {
		return activatTime;
	}
	/**
	 * @param activatTime the activatTime to set
	 */
	public void setActivatTime(Date activatTime) {
		this.activatTime = activatTime;
	}
	/**
	 * customerIdent
	 *
	 * @return  the customerIdent
	 * @version   1.0.0
	*/
	
	public String getCustomerIdent() {
		return customerIdent;
	}
	/**
	 * @param customerIdent the customerIdent to set
	 */
	public void setCustomerIdent(String customerIdent) {
		this.customerIdent = customerIdent;
	}
	/**
	 * sex
	 *
	 * @return  the sex
	 * @version   1.0.0
	*/
	
	public int getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(int sex) {
		this.sex = sex;
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
	 * emailAddress
	 *
	 * @return  the emailAddress
	 * @version   1.0.0
	*/
	
	
	/**
	 * invoiceType
	 *
	 * @return  the invoiceType
	 * @version   1.0.0
	*/
	
	public int getInvoiceType() {
		return invoiceType;
	}
	/**
	 * email
	 *
	 * @return  the email
	 * @version   1.0.0
	*/
	
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @param invoiceType the invoiceType to set
	 */
	public void setInvoiceType(int invoiceType) {
		this.invoiceType = invoiceType;
	}
	/**
	 * invoiceTitle
	 *
	 * @return  the invoiceTitle
	 * @version   1.0.0
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
	
	
	public float getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(float payMoney) {
		this.payMoney = payMoney;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderDetailInfo [agentPhone=" + agentPhone + ", vipCardNo=" + vipCardNo + ", cardType=" + cardType
				+ ", initMoney=" + initMoney + ", payMethod=" + payMethod + ", payTime=" + payTime + ", activatTime="
				+ activatTime + ", customerIdent=" + customerIdent + ", sex=" + sex + ", address=" + address
				+ ", email=" + email + ", invoiceType=" + invoiceType + ", invoiceTitle=" + invoiceTitle + "]";
	}
	
	
	
	
}
