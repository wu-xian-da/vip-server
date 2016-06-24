package com.jianfei.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jianfei.core.bean.AppConsume;
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
     * 订单ID
     */
    private String orderId;
    /**
     * 订单金钱
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
     * 用户性别
     */
    private int customerSex;

    /**
     * 使用过的费用
     */
    private float usedMoney;

    /**
     * 退款金额
     */
    private float returnMoney;

    /**
     * 实际费用
     */
    private float  realMoney;

    /**
     * 退款信息
     */
    private String returnInfo;

    /**
     * 打折比例
     */
    private String saleRate;

    /**
     * VIP卡使用记录
     */
    private List<AppConsume> cardUseList;

    /**
     * 付款方式
     */
    private int payType;

    /**
     * 付款ID
     */
    private String payUserId;

    /**
     * 发票状态
     */
    private int InvoiceSate;

    public String getVipCardNo() {
        return vipCardNo;
    }

    public void setVipCardNo(String vipCardNo) {
        this.vipCardNo = vipCardNo;
    }

    /**
     * createTime
     *
     * @return the createTime
     * @version 1.0.0
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
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


    public String getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(String saleRate) {
        this.saleRate = saleRate;
    }

    public List<AppConsume> getCardUseList() {
        return cardUseList;
    }

    public void setCardUseList(List<AppConsume> cardUseList) {
        this.cardUseList = cardUseList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPayUserId() {
        return payUserId;
    }

    public void setPayUserId(String payUserId) {
        this.payUserId = payUserId;
    }

    public int getInvoiceSate() {
        return InvoiceSate;
    }

    public void setInvoiceSate(int invoiceSate) {
        InvoiceSate = invoiceSate;
    }

    public int getCustomerSex() {
        return customerSex;
    }

    public void setCustomerSex(int customerSex) {
        this.customerSex = customerSex;
    }

    public float getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(float orderMoney) {
        this.orderMoney = orderMoney;
    }

    public float getUsedMoney() {
        return usedMoney;
    }

    public void setUsedMoney(float usedMoney) {
        this.usedMoney = usedMoney;
    }

    public float getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(float returnMoney) {
        this.returnMoney = returnMoney;
    }

    public String getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(String returnInfo) {
        this.returnInfo = returnInfo;
    }

    public float getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(float realMoney) {
        this.realMoney = realMoney;
    }
}
