package com.jianfei.core.service.base.impl;

import com.jianfei.core.bean.AppAirportTrans;
import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.common.utils.IdGen;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.mapper.AppAirportTransMapper;
import com.jianfei.core.service.base.AirportTransManager;
import com.jianfei.core.service.user.VipUserManager;
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

    @Autowired
    private VipUserManager vipUserManager;
    /**
     * 添加接送机相关信息
     *
     * @param airportTrans
     * @return
     */
    @Override
    public BaseMsgInfo addAirportTransInfo(AppAirportTrans airportTrans) {
        //查找下相关用户
        AppCustomer customer= vipUserManager.getUser(airportTrans.getPhone());
        if (customer==null || StringUtils.isBlank(customer.getCustomerId())){
            BaseMsgInfo.msgFail("用户不存在，接送机信息添加失败");
        }
        airportTrans.setName(customer.getCustomerName());
        airportTrans.setId(IdGen.uuid());
        airportTrans.setCreateDate(new Date());
        int i = appAirportTransMapper.insertSelective(airportTrans);
        return i == 1 ? BaseMsgInfo.success(true) : BaseMsgInfo.msgFail("接送机信息添加失败");
    }
}
