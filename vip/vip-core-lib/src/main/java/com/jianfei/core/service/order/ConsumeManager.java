package com.jianfei.core.service.order;

import com.jianfei.core.bean.AppConsume;

import java.util.List;

/**
 * 消费记录接口
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/26 8:44
 */
public interface ConsumeManager {
    /**
     * 添加消费记录
     * @param appConsume
     * @return
     */
    boolean addConsume(AppConsume appConsume);

    /***
     * 根据用户手机号获取消费记录
     * @param phone 手机号
     * @return
     */
    List<AppConsume> listConsume(String phone);
}
