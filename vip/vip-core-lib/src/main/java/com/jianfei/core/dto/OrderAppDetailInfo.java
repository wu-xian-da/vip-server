package com.jianfei.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/6 9:40
 */
public class OrderAppDetailInfo {
    /**
     * 订单编号
     */
    private String orderId;
    /**
     * 订单日期
     */
    private Date orderTime;
    /**
     * 用户名
     */
    private String customerName;
    /**
     * 用户手机号
     */
    private String customerPhone;
    /**
     * 用户性别
     */
    private int sex;
    /**
     * 是否需要发票 0代表不需要
     */
    private int invoiceFlag;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getInvoiceFlag() {
        return invoiceFlag;
    }

    public void setInvoiceFlag(int invoiceFlag) {
        this.invoiceFlag = invoiceFlag;
    }
}
