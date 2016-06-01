package com.jianfei.core.service.order;

import com.jianfei.core.bean.AppOrders;

/**
 * 支付相关接口
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 16:37
 */
public interface PayManager {
    /**
     * 根据订单信息生成支付URL
     * @return
     */
    String getPayUrl(AppOrders appOrders);
}
