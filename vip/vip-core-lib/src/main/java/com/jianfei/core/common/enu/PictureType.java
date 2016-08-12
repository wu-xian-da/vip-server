package com.jianfei.core.common.enu;

/**
 * 轮播图片位置
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/20 16:51
 */
public enum PictureType {

    /**
     * 业务APP轮播图
     */
    SALE_APP_HOME(0),

    /**
     *VIP app首页轮播图
     */
    VIP_APP_HOME(1),

    /**
     *  VIP APP合作按钮
     */
    VIP_APP_HOME_MODULE(2),
    /**
     *  VIP 权益
     */
    BEFORE_VIP_APP_RIGHT(3),
    /**
     *  VIP 权益
     */
    AFTER_VIP_APP_RIGHT(4);

    private int name;

    public int getName(){
        return this.name;
    }

    private PictureType(int name){
        this.name = name;
    }

}
