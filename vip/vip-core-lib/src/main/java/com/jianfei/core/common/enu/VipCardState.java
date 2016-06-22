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
     * 未激活
     */
    NOT_ACTIVE(0),
    /**
     * 激活
     */
    ACTIVE(1),
    /**
     * 退卡
     */
    BACK_CARD(2);

    private Integer name;

    public Integer getName(){
        return this.name;
    }

    private VipCardState(Integer name){
        this.name = name;
    }
}
