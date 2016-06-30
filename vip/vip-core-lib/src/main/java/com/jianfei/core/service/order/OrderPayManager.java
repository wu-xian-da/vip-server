package com.jianfei.core.service.order;

import com.jianfei.core.common.enu.PayType;
import com.jianfei.core.dto.BaseMsgInfo;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/30 17:20
 */
public interface OrderPayManager {

    /**
     * 根据付款方式获取付款URL
     * @param orderId 订单ID
     * @param payType 付款方式
     * @return
     */
    BaseMsgInfo getPayUrl(String orderId, PayType payType);

    /**
     * 查询订单是否支付成功并改变订单状态
     * @param orderId 订单ID
     * @param payType 支付方式
     * @return
     */
    BaseMsgInfo checkThirdPay(String orderId, PayType payType);
}
