package com.jianfei.core.service.thirdpart;

import com.jianfei.core.dto.AirportEasyUseInfo;


/**
 * 空港易行相关接口封装
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 17:43
 */
public interface AirportEasyManager {

    /**
     * 激活VIP卡
     *
     * @param vipCardNo VIP卡号
     * @param userPhone VIP用户手机号
     * @param userName  VIP用户名
     * @return
     */
    boolean ativeVipCard(String vipCardNo, String userPhone, String userName);

    /**
     * 禁用VIP卡
     *
     * @param vipCardNo vip卡号
     * @return
     */
    boolean disabledVipCard(String vipCardNo);

    /**
     * 获取所有Vip卡使用记录
     *
     * @return
     */
    AirportEasyUseInfo getVipCardUseInfo();

    /**
     * 确认已收到VIP卡使用记录
     *
     * @param batchNo 批次号
     * @return
     */
    boolean sendConfirmInfo(String batchNo);

}
