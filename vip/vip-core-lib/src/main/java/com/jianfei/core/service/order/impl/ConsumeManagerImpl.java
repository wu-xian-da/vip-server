package com.jianfei.core.service.order.impl;

import com.jianfei.core.bean.AppConsume;
import com.jianfei.core.mapper.AppConsumeMapper;
import com.jianfei.core.service.order.ConsumeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消费记录查询
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/26 8:49
 */
@Service
public class ConsumeManagerImpl implements ConsumeManager {

    @Autowired
     private AppConsumeMapper appConsumeMapper;
    /**
     * 添加消费记录
     *
     * @param appConsume
     * @return
     */
    @Override
    public boolean addConsume(AppConsume appConsume) {
        return false;
    }

    /***
     * 根据用户手机号获取消费记录
     *
     * @param phone 手机号
     * @return
     */
    @Override
    public List<AppConsume> listConsume(String phone) {
        return appConsumeMapper.listConsumeByUserPhone(phone);
    }
}
