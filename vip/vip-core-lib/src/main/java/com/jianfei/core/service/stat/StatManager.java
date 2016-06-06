/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月19日-上午10:49:24
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.stat;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppAirportArchive;
import com.jianfei.core.bean.AppOrderArchive;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.dto.GraphDto;

import java.util.List;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com 
 * @date: 2016年5月19日 上午10:49:24 
 * 
 * @version 1.0.0
 * 
 */
public interface StatManager {

    /**
     * 获得个人两个日期间每天的订单统计数据
     * @param userId 用户标示
     * @param begin 开始日期
     * @param end 结束日期
     * @return
     */
    List<AppOrderArchive> getOrderStatByUserId(String userId, String begin, String end);

    /**
     * 获得某省两个日期间每天订单的统计数据
     * @param provinceId
     * @param begin
     * @param end
     * @return
     */
    List  getOrderStatByProvinceId(String provinceId,String begin,String end);

    /**
     * 分页获取个人每日开卡数据
     * @param pageDto 分页数据
     * @param userId 用户唯一标示
     * @return
     */
    PageInfo<AppOrderArchive> pageOrderStatByUserId(PageDto pageDto, String userId);

    /**
     * 获得某段时间区域每天开卡总数
     * @param province 区域列表
     * @param begin 开始时间
     * @param end 结束时间
     * @return
     */
    List getOrderStatByProvince(List<String> province,String begin,String end);

    /**
     * 获取机场一段时间内每个机场的开卡数
     * @param airportIds 机场IDs
     * @param begin 开始时间
     * @param end 结束时间
     * @return
     */
    List<AppAirportArchive> getAirportOrderStat(List<String> airportIds, String begin, String end);

    /**
     * 获取某段时间开卡人员分页列表
     * @param pageDto
     * @param begin
     * @param end
     * @param airportIds
     * @return
     */
    PageInfo<AppOrderArchive> pageOrderStat(PageDto pageDto,String begin, String end,List<String> airportIds);
}
