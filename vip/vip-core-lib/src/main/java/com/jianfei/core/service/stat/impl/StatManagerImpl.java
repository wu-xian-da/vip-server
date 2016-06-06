package com.jianfei.core.service.stat.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jianfei.core.bean.AppAirportArchive;
import com.jianfei.core.bean.AppOrderArchive;
import com.jianfei.core.common.utils.PageDto;
import com.jianfei.core.dto.OrderAppDetailInfo;
import com.jianfei.core.dto.OrderShowInfoDto;
import com.jianfei.core.mapper.AppOrderArchiveMapper;
import com.jianfei.core.mapper.ArchiveMapper;
import com.jianfei.core.service.stat.StatManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/20 16:11
 */
@Service
public class StatManagerImpl implements StatManager {
    @Autowired
    private AppOrderArchiveMapper appOrderArchiveMapper;

    /**
     * 分页获取个人每日开卡数据
     *
     * @param pageDto 分页数据
     * @param userId  用户唯一标示
     * @return
     */
    @Override
    public PageInfo<AppOrderArchive> pageOrderStatByUserId(PageDto pageDto, String userId) {
        PageHelper.startPage(pageDto.getPageNo(), pageDto.getPageSize());
        List<AppOrderArchive> list = appOrderArchiveMapper.selectByUserId(userId);
        PageInfo<AppOrderArchive> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 获取某天某人的开卡记录
     *
     * @param userId
     * @param date
     * @return
     */
    @Override
    public List<OrderAppDetailInfo> listOrderByUserId(String userId, String date) {
        return appOrderArchiveMapper.listOrderByUserId(userId, date);
    }
}
