package com.jianfei.core.service.order.impl;

import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateContentBuilder;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.service.order.PayManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 支付宝相关支付
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/5/18 16:49
 */
public class AliPayImpl implements PayManager {
    /**
     * 根据订单信息生成支付URL
     *
     * @param appOrders
     * @return
     */
    @Override
    public String getPayUrl(AppOrders appOrders) {
       // TODO 参考 AlipayPayManagerImpl
        GoodsDetail goodsDetail = GoodsDetail.newInstance(appOrders.getOrderId(), appOrders.getRemark1(), 1, 1);
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        goodsDetailList.add(goodsDetail);
       AlipayTradePrecreateContentBuilder builder = new
         		  AlipayTradePrecreateContentBuilder() .setSubject(appOrders.getRemark1())
         		  .setTotalAmount(appOrders.getPayMoney().toString()) .setOutTradeNo(appOrders.getId()).
                  setSellerId(appOrders.getId())
         		  .setGoodsDetailList(goodsDetailList);


        return null;
    }
}
