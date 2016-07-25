package com.jianfei.core.service.base;

import com.jianfei.core.bean.AppAirportTrans;
import com.jianfei.core.dto.BaseMsgInfo;

/**
 * 接送机相关服务
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/7/6 9:03
 */
public interface AirportTransManager {

    /**
     * 添加接送机相关信息
     * @param airportTrans
     * @return
     */
    BaseMsgInfo addAirportTransInfo(AppAirportTrans airportTrans) throws Exception;

    /**
     * 根据城市获取易道机场列表
     * @param city 城市名
     * @return
     */
    BaseMsgInfo getTransAirportList(String city);


    /**
     * 根据手机号获取接送机次数
     * @param phone
     * @return
     */
    BaseMsgInfo getAirportTransNum(String phone);


    /**
     * 根据城市获取易道城市列表
     * @return
     */
    BaseMsgInfo getTransCityList();

}
