package com.jianfei.core.common.enu;

/**
 * 支付枚举类
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/30 10:58
 */
public enum PayType {

    /**
     * 微信支付
     */
    WXPAY("1"),
    /**
     * 支付宝
     */
    ALIPAY("2"),
    /**
     * 银行卡刷卡
     */
    BANKPAY("3"),
    /**
     * 现金支付
     */
    CASHPAY("4");
    private String name;

    public String getName(){
        return this.name;
    }

    private PayType(String name){
        this.name = name;
    }
    
}
