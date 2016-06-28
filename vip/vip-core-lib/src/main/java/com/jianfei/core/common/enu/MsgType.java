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
     * 退卡查询
     */
    SELECT("003"),

    /**
     * 退卡完成后短信
     */
    BACK_CARD_FINISH("004"),

    /**
     * 激活VIP卡
     */
    ACTIVE_CARD("005"),
    /**
     * 退卡申请后短信
     */
    BACK_CARD_APPLY("006"),
    /**
     * 紧急退卡完成
     */
    RIGHT_BACK_CARD("007");

    private String name;

    public String getName(){
        return this.name;
    }

    private MsgType(String name){
        this.name = name;
    }
}
