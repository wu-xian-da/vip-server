package com.jianfei.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/6 15:10
 */
public class ReturnCardDto {
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
     * 退卡状态
     */
    private int backType;

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

    public int getBackType() {
        return backType;
    }

    public void setBackType(int backType) {
        this.backType = backType;
    }
}
