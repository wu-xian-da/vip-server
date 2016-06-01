package com.jianfei.core.dto;

import com.jianfei.core.bean.AppCustomer;

import java.util.Date;
import java.util.List;

/**
 * 用户订单使用记录
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/1 15:50
 */
public class VipCardUseDetailInfo {
    /**
     * vipNo
     */
   private String vipCardNo;
    /**
     * 激活时间
     */
   private Date activeTime;
    /**
     * 订单时间
     */
   private float orderMoney;
    /**
     * 用户名称
     */
    private String customerName;
    /**
     * 用户手机号
     */
    private String customerPhone;
    /**
     * 用户身份证号
     */
    private String customerIdNo;
    /**
     * 使用过的费用
     */
    private float  usedMoney;

    /**
     * 打折比例
     */
    private String saleRate;

    /**
     * VIP卡使用记录
     */
    private List<AppCustomer> cardUseList;

    public String getVipCardNo() {
        return vipCardNo;
    }

    public void setVipCardNo(String vipCardNo) {
        this.vipCardNo = vipCardNo;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public float getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(float orderMoney) {
        this.orderMoney = orderMoney;
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

    public String getCustomerIdNo() {
        return customerIdNo;
    }

    public void setCustomerIdNo(String customerIdNo) {
        this.customerIdNo = customerIdNo;
    }

    public float getUsedMoney() {
        return usedMoney;
    }

    public void setUsedMoney(float usedMoney) {
        this.usedMoney = usedMoney;
    }

    public String getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(String saleRate) {
        this.saleRate = saleRate;
    }

    public List<AppCustomer> getCardUseList() {
        return cardUseList;
    }

    public void setCardUseList(List<AppCustomer> cardUseList) {
        this.cardUseList = cardUseList;
    }
}
