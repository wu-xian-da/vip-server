package com.jianfei.core.service.base.impl;

import com.jianfei.core.bean.AppAirportTrans;
import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.IdGen;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.mapper.AppAirportTransMapper;
import com.jianfei.core.service.base.AirportTransManager;
import com.jianfei.core.service.user.VipUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public BaseMsgInfo addAirportTransInfo(AppAirportTrans airportTrans) throws Exception {
        //查找下相关用户
        AppCustomer customer= vipUserManager.getUser(airportTrans.getPhone());
        if (customer==null || StringUtils.isBlank(customer.getCustomerId())){
            BaseMsgInfo.msgFail("用户不存在，接送机信息添加失败");
        }
        //转换前台传入日期
        Date flightDate= DateUtil.parseDateTime(airportTrans.getFlightDateStr());
        Date goofDate=DateUtil.parseDateTime(airportTrans.getGooffDateStr());
        airportTrans.setFlightDate(flightDate);
        airportTrans.setGooffDate(goofDate);
        airportTrans.setName(customer.getCustomerName());
        airportTrans.setId(IdGen.uuid());
        airportTrans.setCreateDate(new Date());
        int i = appAirportTransMapper.insertSelective(airportTrans);
        return i == 1 ? BaseMsgInfo.success(true) : BaseMsgInfo.msgFail("接送机信息添加失败");
    }


    /**
     * 根据城市获取易道机场列表
     *
     * @param city 城市名
     * @return
     */
    @Override
    public BaseMsgInfo getTransAirportList(String city) {
        List<Object> objectList = JedisUtils.getObjectList("YONGCHEAIRPORTLIST");
        return BaseMsgInfo.success(objectList);
    }


    /**
     * 根据手机号获取接送机次数
     *
     * @param phone
     * @return
     */
    @Override
    public BaseMsgInfo getAirportTransNum(String phone) {
        List<AppAirportTrans> appAirportTranses = appAirportTransMapper.selectByPhone(phone);
        return appAirportTranses == null || appAirportTranses.size() == 0 ? BaseMsgInfo.success(true) : BaseMsgInfo.success(false);
    }
}
