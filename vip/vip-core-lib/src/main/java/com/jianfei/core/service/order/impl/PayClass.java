package com.jianfei.core.service.order.impl;

import com.jianfei.core.service.order.PayManager;

/**
 * 付款
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 16:56
 */
public class PayClass {
    private PayManager payManager;

    /**
     * 构造方法
     * @param payManager
     */
    public PayClass(PayManager payManager) {
        this.payManager = payManager;
    }

    public String getPayUrl(String orderNo, float money){
      return   this.payManager.getPayUrl(orderNo,money);
    }
}
