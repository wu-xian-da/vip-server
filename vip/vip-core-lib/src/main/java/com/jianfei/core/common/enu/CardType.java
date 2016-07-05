package com.jianfei.core.common.enu;

/**
 * 证件类型
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 15:06
 */
public enum CardType {

    /**
     * 身份证
     */
    SFZ(1),
    /**
     * 护照
     */
    HZ(2),
    /**
     * 军官证
     */
    JGZ(3),
    /**
     * 回乡证
     */
    HXZ(4);

    private Integer name;

    public Integer getName(){
        return this.name;
    }

    private CardType(Integer name){
        this.name = name;
    }
}
