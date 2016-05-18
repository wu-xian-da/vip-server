package com.jianfei.core.service.order.impl;

import com.jianfei.core.service.order.PayManager;

/**
 * 微信相关支付
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 16:46
 */
public class WeiCatPayImpl implements PayManager {
    /**
     * 根据订单号和金额生成支付URL
     *
     * @param orderNo 订单号
     * @param money   金额
     * @return
     */
    @Override
    public String getPayUrl(String orderNo, float money) {
        return null;
    }
}
