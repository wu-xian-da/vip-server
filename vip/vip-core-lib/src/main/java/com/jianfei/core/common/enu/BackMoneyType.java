package com.jianfei.core.common.enu;

public enum BackMoneyType {
	/**
     * 微信支付
     */
    WXPAY(1),
    /**
     * 支付宝
     */
    ALIPAY(2),
    /**
     * 银行卡
     */
    BANKPAY(3),
    /**
     * 现金
     */
    CASHPAY(4);
    private int name;

    public int getName(){
        return this.name;
    }

    private BackMoneyType(int name){
        this.name = name;
    }
    
}
