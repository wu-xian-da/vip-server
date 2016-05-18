package com.jianfei.core.service.order;

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
     * 根据订单号和金额生成支付URL
     * @param orderNo 订单号
     * @param money 金额
     * @return
     */
    String getPayUrl(String orderNo,float money);
}
