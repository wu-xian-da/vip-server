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
import com.jianfei.core.dto.OrderAppDetailInfo;

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
     * 分页获取个人每日开卡数据
     * @param pageDto 分页数据
     * @param userId 用户唯一标示
     * @return
     */
    PageInfo<AppOrderArchive> pageOrderStatByUserId(PageDto pageDto, String userId);

    /**
     * 获取某天某人的开卡记录
     * @param userId
     * @param date
     * @return
     */
    List<OrderAppDetailInfo> listOrderByUserId(String userId,String date);

}
