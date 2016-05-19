package com.jianfei.core.service.thirdpart.impl;

import com.jianfei.core.dto.AirportEasyUseInfo;
import com.jianfei.core.service.thirdpart.AirportEasyManager;

/**
 * 空港易行相关接口实现
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 18:05
 */
public class AirportEasyManagerImpl implements AirportEasyManager{



    /**
     * 激活VIP卡
     *
     * @param vipCardNo VIP卡号
     * @param userPhone VIP用户手机号
     * @param userName  VIP用户名
     * @return
     */
    @Override
    public boolean ativeVipCard(String vipCardNo, String userPhone, String userName) {
        return false;
    }

    /**
     * 禁用VIP卡
     *
     * @param vipCardNo vip卡号
     * @return
     */
    @Override
    public boolean disabledVipCard(String vipCardNo) {
        return false;
    }

    /**
     * 获取所有Vip卡使用记录
     *
     * @return
     */
    @Override
    public AirportEasyUseInfo getVipCardUseInfo() {
        return null;
    }

    /**
     * 确认已收到VIP卡使用记录
     *
     * @param batchNo 批次号
     * @return
     */
    @Override
    public boolean sendConfirmInfo(String batchNo) {
        return false;
    }
}
