package com.jianfei.core.service.thirdpart;

/**
 * 距离计算相关
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/19 17:56
 */
public interface DistanceManager {

    /**
     * 根据坐标获取城市名
     * @param x X坐标
     * @param y Y坐标
     * @return 城市名
     */
    String getCityNameByCoordinate(String x,String y);


    /**
     * 就算两个坐标的数据
     * @param x X坐标
     * @param y Y坐标
     * @return 城市名
     */
    String getDistanceBetewn(String x,String y,String otherX,String otherY);
}
