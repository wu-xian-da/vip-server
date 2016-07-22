package com.jianfei.core.service.base.impl;

import com.jianfei.core.bean.AppAirportTrans;
import com.jianfei.core.bean.AppCustomer;
import com.jianfei.core.bean.AppVersion;
import com.jianfei.core.bean.YdycAirports;
import com.jianfei.core.common.cache.JedisUtils;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.IdGen;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.dto.BaseDto;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.mapper.AppAirportTransMapper;
import com.jianfei.core.mapper.YdycAirportsMapper;
import com.jianfei.core.service.base.AirportTransManager;
import com.jianfei.core.service.user.VipUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    YdycAirportsMapper ydycAirportsMapper;
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
            return BaseMsgInfo.msgFail("用户不存在，接送机信息添加失败");
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
        YdycAirports airports = new YdycAirports();
        List<YdycAirports> objectList;
        if (StringUtils.isBlank(city)) {
            //查询缓存是否存在
            objectList = (List<YdycAirports>) JedisUtils.getObject("YDYC_AIRPORT_LIST");
            //不存在查询数据库
            if (objectList == null) {
                objectList = ydycAirportsMapper.selectAirports(airports);
                //数据库无论是否存在都设置值 防止缓存穿透
                JedisUtils.setObject("YDYC_AIRPORT_LIST", objectList, 0);
            }
        } else {
            airports.setCity(city);
            objectList = ydycAirportsMapper.selectAirports(airports);
        }
        return BaseMsgInfo.success(objectList);
    }


    /**
     * 根据城市获取易道城市列表
     *
     * @return
     */
    @Override
    public BaseMsgInfo getTransCityList() {
        //KEY为:YDYC_CITY
        //查询缓存是否存在
        List<BaseDto> objectList = (List<BaseDto>) JedisUtils.getObject("YDYC_CITY");
        //不存在查询数据库
        if (objectList == null) {
            objectList = ydycAirportsMapper.getCityList();
            //数据库无论是否存在都设置值 防止缓存穿透
            JedisUtils.setObject("YDYC_CITY", objectList, 0);
        }
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
        return appAirportTranses == null || appAirportTranses.size() <= 1 ? BaseMsgInfo.success(true) : BaseMsgInfo.success(false);
    }
}
