package com.jianfei.core.bean;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AppOrders implements Serializable{
    /**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @version 1.0.0
	 */
	
	private static final long serialVersionUID = -8836226513742947761L;

	private String orderId;

    private String id;

    private String customerId;

    private Date orderTime;

    private Integer orderState;

    private Integer payType;

    private String serialId;

    private Float payMoney;

    private Integer invoiceFlag;

    private String remark1;

    private String remark2;

    private Integer dtflag;
    
    private String operation;

    private String saleNo;

    /**
     * 机场ID
     */
    private String airportId;

    private String payUserId;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 顾客 1:1
     */
    private AppCustomer customer;

    /**
     * 卡 1:d
     */
    private List<AppVipcard> vipCards = Lists.newArrayList();
    
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

	public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public AppOrders setOrderState(Integer orderState) {
        this.orderState = orderState;
        return this;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId == null ? null : serialId.trim();
    }

    public Float getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Float payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getInvoiceFlag() {
        return invoiceFlag;
    }

    public void setInvoiceFlag(Integer invoiceFlag) {
        this.invoiceFlag = invoiceFlag;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    public Integer getDtflag() {
        return dtflag;
    }

    public void setDtflag(Integer dtflag) {
        this.dtflag = dtflag;
    }

    public String getSaleNo() {
        return saleNo;
    }

    public void setSaleNo(String saleNo) {
        this.saleNo = saleNo;
    }

    public String getAirportId() {
        return airportId;
    }

    public void setAirportId(String airportId) {
        this.airportId = airportId;
    }

    public String getPayUserId() {
        return payUserId;
    }

    public AppOrders setPayUserId(String payUserId) {
        this.payUserId = payUserId;
        return this;
    }

    public Date getPayTime() {
        return payTime;
    }

    public AppOrders setPayTime(Date payTime) {
        this.payTime = payTime;
        return this;
    }

    public AppCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(AppCustomer customer) {
        this.customer = customer;
    }

    public List<AppVipcard> getVipCards() {
        return vipCards;
    }

    public void setVipCards(List<AppVipcard> vipCards) {
        this.vipCards = vipCards;
    }
}