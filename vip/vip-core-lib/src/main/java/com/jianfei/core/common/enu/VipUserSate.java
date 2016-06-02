package com.jianfei.core.common.enu;

/**
 * VIp用户状态
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 14:52
 */
public enum VipUserSate {
    /**
     * 初始化
     */
    NOT_ACTIVE(0),
    /**
     * 正常
     */
    ACTIVE(1),
    /**
     * 锁住
     */
    LOCKED(2);


    private int name;

    public int getName(){
        return this.name;
    }

    private VipUserSate(int name){
        this.name = name;
    }
}
