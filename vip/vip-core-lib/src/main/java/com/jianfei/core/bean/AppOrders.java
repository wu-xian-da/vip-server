package com.jianfei.core.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AppOrders implements Serializable{


    private static final long serialVersionUID = -4659744235608345081L;
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
     * 退卡申请途径
     */
    private int applyType;

    /**
     * 顾客 1:1
     */
    private AppCustomer customer;

    /**
     * 发票 1:1
     */
    private AppInvoice invoice;

    /**
     * 退卡信息 1:1
     */
    private AppCardBack cardBack;

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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
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


    public AppInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(AppInvoice invoice) {
        this.invoice = invoice;
    }

    public AppCardBack getCardBack() {
        return cardBack;
    }

    public void setCardBack(AppCardBack cardBack) {
        this.cardBack = cardBack;
    }

    public int getApplyType() {
        return applyType;
    }

    public void setApplyType(int applyType) {
        this.applyType = applyType;
    }

    @Override
    public String toString() {
        return "AppOrders{" +
                "orderId='" + orderId + '\'' +
                ", id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderTime=" + orderTime +
                ", orderState=" + orderState +
                ", payType=" + payType +
                ", serialId='" + serialId + '\'' +
                ", payMoney=" + payMoney +
                ", invoiceFlag=" + invoiceFlag +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                ", dtflag=" + dtflag +
                ", operation='" + operation + '\'' +
                ", saleNo='" + saleNo + '\'' +
                ", airportId='" + airportId + '\'' +
                ", payUserId='" + payUserId + '\'' +
                ", payTime=" + payTime +
                ", applyType=" + applyType +
                '}';
    }
}