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
    BaseMsgInfo addAirportTransInfo(AppAirportTrans airportTrans);

}
