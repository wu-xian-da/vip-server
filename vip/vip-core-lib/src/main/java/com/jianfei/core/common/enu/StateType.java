package com.jianfei.core.common.enu;

/**
 * 逻辑删除状态
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/2 15:18
 */
public enum StateType {
    /**
     * 存在
     */
    EXIST(0),
    /**
     * 逻辑删除
     */
    NOT_EXIST(1);

    private int name;

    private StateType(int name){
        this.name = name;
    }

    /**
     * @return the name
     */
    public int getName() {
        return name;
    }
}
