package com.jianfei.core.service.order;

import com.github.pagehelper.PageInfo;
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


    /**
     * 分页获取用户使用记录
     * @param pageNo 页数
     * @param pageSize 每页大小
     * @param vipCardNo Vip卡号
     * @return
     */
    PageInfo<AppConsume> pageVipUseInfo(int pageNo, int pageSize, String vipCardNo );

    /**
     * 根据vip卡No获取所有使用记录
     * @param vipCardNo vipCardNo
     * @return  List<AppCustomer>
     */
    List<AppConsume> getConsumesByVipNo( String vipCardNo );

    /**
     * 根据VIP卡号获取使用的金额
     * @param vipCardNo
     * @return
     */
    double getVipCardUseMoney(String vipCardNo);
}
