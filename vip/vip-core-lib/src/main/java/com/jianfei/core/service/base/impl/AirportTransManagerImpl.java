package com.jianfei.core.service.base.impl;

import com.jianfei.core.bean.AppAirportTrans;
import com.jianfei.core.common.utils.IdGen;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.mapper.AppAirportTransMapper;
import com.jianfei.core.service.base.AirportTransManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 接送机相关服务
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/7/6 9:07
 */
@Service
public class AirportTransManagerImpl implements AirportTransManager {

    @Autowired
    private AppAirportTransMapper appAirportTransMapper;
    /**
     * 添加接送机相关信息
     *
     * @param airportTrans
     * @return
     */
    @Override
    public BaseMsgInfo addAirportTransInfo(AppAirportTrans airportTrans) {
        airportTrans.setId(IdGen.uuid());
        airportTrans.setCreateDate(new Date());
        int i = appAirportTransMapper.insertSelective(airportTrans);
        return i == 1 ? BaseMsgInfo.success(true) : BaseMsgInfo.msgFail("接送机信息添加失败");
    }
}
