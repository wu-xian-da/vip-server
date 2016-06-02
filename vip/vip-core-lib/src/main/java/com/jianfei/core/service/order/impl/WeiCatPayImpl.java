package com.jianfei.core.service.order.impl;

import com.jianfei.core.bean.AppOrders;
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
     * 根据订单信息生成支付URL
     *
     * @param appOrders
     * @return
     */
    @Override
    public String getPayUrl(AppOrders appOrders) {
        return null;
    }
}
