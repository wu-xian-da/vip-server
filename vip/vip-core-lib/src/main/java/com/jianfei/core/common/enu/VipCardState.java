package com.jianfei.core.common.enu;

/**
 * Vip卡状态
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 15:06
 */
public enum VipCardState {

    /**
     * 未绑定
     */
    NOT_ACTIVE(0),
    /**
     * 绑定成功未激活
     */
    ACTIVE(1),
    /**
     * 已退卡
     */
    BACK_CARD(2),
    /**
     * 绑定失败
     */
    ACTIVATE_FAIL(3),
    /**
     * 待绑定
     */
    TO_ACTIVATE(4),
    /**
     * 解绑失败
     */
	UNBUNDLING_FAIL(5),
    /**
     * 绑定成功已激活
     */
    ACTIVE_USE(6),
	
    /**
     * 已过期
     */
	CARD_EXPIRED(7);


    private Integer name;

    public Integer getName(){
        return this.name;
    }

    private VipCardState(Integer name){
        this.name = name;
    }
}
