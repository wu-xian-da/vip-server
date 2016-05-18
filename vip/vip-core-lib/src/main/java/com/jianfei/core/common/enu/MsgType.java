package com.jianfei.core.common.enu;

/**
 *  验证码类型
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 15:25
 */
public enum MsgType {

    /**
     * 注册
     */
    REGISTER("001"),
    /**
     * 登录
     */
    LOGIN("002"),
    /**
     * 退卡
     */
    BACK_CARD("003");
    private String name;

    public String getName(){
        return this.name;
    }

    private MsgType(String name){
        this.name = name;
    }
}
