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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderShowInfoDto [orderId=" + orderId + ", orderTime=" + orderTime + ", airportName=" + airportName
				+ ", agentName=" + agentName + ", customerName=" + customerName + ", customerPhone=" + customerPhone
				+ ", orderState=" + orderState + ", orderStateName=" + orderStateName + ", invoiceFlag=" + invoiceFlag
				+ ", invoiceFlagName=" + invoiceFlagName + "]";
	}
	
	
}
