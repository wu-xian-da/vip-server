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
     *VIP app首页
     */
    VIP_APP_HOME("001"),
    /**
     *
     */
    SALE_APP_HOME("002"),
    /**
     * 退卡
     */
    VIP_ROOM("003");
    private String name;

    public String getName(){
        return this.name;
    }

    private PictureType(String name){
        this.name = name;
    }


}
