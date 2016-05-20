package com.jianfei.core.service.stat.impl;

import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppAirportArchive;
import com.jianfei.core.bean.AppOrderArchive;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.service.stat.StatManager;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/20 16:11
 */
public class StatManagerImpl implements StatManager {

    /**
     * 获得个人两个日期间每天的订单统计数据
     *
     * @param userId 用户标示
     * @param begin  开始日期
     * @param end    结束日期
     * @return
     */
    @Override
    public List<AppOrderArchive> getOrderStatByUserId(String userId, String begin, String end) {
        return null;
    }

    /**
     * 获得某省两个日期间每天订单的统计数据
     *
     * @param provinceId
     * @param begin
     * @param end
     * @return
     */
    @Override
    public List getOrderStatByProvinceId(String provinceId, String begin, String end) {
        return null;
    }

    /**
     * 分页获取个人每日开卡数据
     *
     * @param pageDto 分页数据
     * @param userId  用户唯一标示
     * @return
     */
    @Override
    public PageInfo<AppOrderArchive> pageOrderStatByUserId(PageDto pageDto, String userId) {
        return null;
    }

    /**
     * 获得某段时间区域每天开卡总数
     *
     * @param province 区域列表
     * @param begin    开始时间
     * @param end      结束时间
     * @return
     */
    @Override
    public List getOrderStatByProvince(List<String> province, String begin, String end) {
        return null;
    }

    /**
     * 获取机场一段时间内每个机场的开卡数
     *
     * @param airportIds 机场IDs
     * @param begin      开始时间
     * @param end        结束时间
     * @return
     */
    @Override
    public List<AppAirportArchive> getAirportOrderStat(List<String> airportIds, String begin, String end) {
        return null;
    }

    /**
     * 获取某段时间开卡人员分页列表
     *
     * @param pageDto
     * @param begin
     * @param end
     * @param airportIds
     * @return
     */
    @Override
    public PageInfo<AppOrderArchive> pageOrderStat(PageDto pageDto, String begin, String end, List<String> airportIds) {
        return null;
    }
}
