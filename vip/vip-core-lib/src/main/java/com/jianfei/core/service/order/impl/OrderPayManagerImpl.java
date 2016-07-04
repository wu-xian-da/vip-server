package com.jianfei.core.service.order.impl;

import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateContentBuilder;
import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.common.enu.PayType;
import com.jianfei.core.common.enu.VipOrderState;
import com.jianfei.core.common.pay.PayQueryResult;
import com.jianfei.core.common.pay.PreCreateResult;
import com.jianfei.core.common.utils.GloabConfig;
import com.jianfei.core.common.utils.StringUtils;
import com.jianfei.core.dto.BaseMsgInfo;
import com.jianfei.core.service.order.OrderManager;
import com.jianfei.core.service.order.OrderPayManager;
import com.jianfei.core.service.thirdpart.ThirdPayManager;
import com.tencent.protocol.native_protocol.NativePayReqData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @version 1.0.0
 * @Description:
 * @author: liu.dongsong@jianfeitech.com
 * @date: 2016/6/30 17:22
 */
@Service
public class OrderPayManagerImpl implements OrderPayManager {

    @Autowired
    private ThirdPayManager aliPayManager;
    @Autowired
    private ThirdPayManager wechatiPayManager;
    @Autowired
    private ThirdPayManager yeepayManager;
    @Autowired
    private  OrderManager orderManager;
    /**
     * @param orderId
     * @param payType
     * @return
     */
    @Override
    public BaseMsgInfo checkThirdPay(String orderId, PayType payType) {
        //先查询订单是否存在及合法
        AppOrders appOrders=orderManager.getOrderInfoByOrderId(orderId);

        //如果订单存在 并且已付款
        if (appOrders==null || StringUtils.isBlank(appOrders.getOrderId())){
            return BaseMsgInfo.msgFail("订单不存在");
        }
        if (VipOrderState.ALREADY_PAY.getName() == appOrders.getOrderState()) {
            return BaseMsgInfo.success(true);
        }
        //查询第三方支付接口
        PayQueryResult result = new PayQueryResult();
        if (PayType.WXPAY.equals(payType)) {
            result = wechatiPayManager.tradeQuery(orderId);
        } else if (PayType.ALIPAY.equals(payType)) {
            result = aliPayManager.tradeQuery(orderId);
        } else if (PayType.BANKPAY.equals(payType)) {
            result = yeepayManager.tradeQuery(orderId);
        }
        if ("0".equals(result.getCode())) {
            appOrders.setPayUserId(result.getPayUserId());
            appOrders.setSerialId( result.getTradeNo());
            appOrders.setPayType(payType.getName());
            BaseMsgInfo baseMsgInfo = orderManager.updatePayState(appOrders);
            if (baseMsgInfo.getCode() < 0) {
                return baseMsgInfo;
            }
            return BaseMsgInfo.success(true);
        } else {
            return BaseMsgInfo.success(false);
        }
    }


    /**
     * 根据付款方式获取付款URL
     *
     * @param orderId 订单ID
     * @param payType 付款方式
     * @return
     */
    @Override
    public BaseMsgInfo getPayUrl(String orderId, PayType payType) {
        AppOrders appOrders = orderManager.getOrderInfoByOrderId(orderId);
        if (appOrders == null || StringUtils.isBlank(appOrders.getOrderId())) {
            return BaseMsgInfo.msgFail("订单不存在");
        }
        PreCreateResult preCreateResult = new PreCreateResult();
        if (PayType.WXPAY.equals(payType)) {
            /**
             * @param authCode 这个是扫码终端设备从用户手机上扫取到的支付授权号，这个号是跟用户用来支付的银行卡绑定的，有效期是1分钟
             * @param body 要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
             * @param attach 支付订单里面可以填的附加数据，API会将提交的这个附加数据原样返回
             * @param outTradeNo 商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
             * @param totalFee 订单总金额，单位为“分”，只能整数
             * @param deviceInfo 商户自己定义的扫码支付终端设备号，方便追溯这笔交易发生在哪台终端设备上
             * @param spBillCreateIP 订单生成的机器IP
             * @param timeStart 订单生成时间， 格式为yyyyMMddHHmmss，如2009年12 月25 日9 点10 分10 秒表示为20091225091010。时区为GMT+8 beijing。该时间取自商户服务器
             * @param timeExpire 订单失效时间，格式同上
             * @param goodsTag 商品标记，微信平台配置的商品标记，用于优惠券或者满减使用
             */
            NativePayReqData nativePayReqData = new NativePayReqData("", appOrders.getRemark1(), "", orderId, (int) (appOrders.getPayMoney() * 100),
                    "", "192.168.199.200", "", "", "", "", GloabConfig.getConfig("pay.notify.address") + "/pay/wechat_notify", "NATIVE", appOrders.getAirportId(), "", "");
            preCreateResult = wechatiPayManager.tradePrecreate(nativePayReqData);
        } else if (PayType.ALIPAY.equals(payType)) {
            GoodsDetail goodsDetail = GoodsDetail.newInstance(appOrders.getOrderId(), appOrders.getRemark1(), 1, 1);
            List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
            goodsDetailList.add(goodsDetail);
            AlipayTradePrecreateContentBuilder builder = new
                    AlipayTradePrecreateContentBuilder().setSubject(appOrders.getRemark1())
                    .setTotalAmount(appOrders.getPayMoney().toString()).setOutTradeNo(appOrders.getOrderId())
                    .setGoodsDetailList(goodsDetailList).setStoreId("test");

            preCreateResult = aliPayManager.tradePrecreate(builder);
        } else if (PayType.BANKPAY.equals(payType)) {
            preCreateResult.setCode("0");
            preCreateResult.setQrUrl(orderId);
        }
        if ("0".equals(preCreateResult.getCode())) {
            return BaseMsgInfo.success(preCreateResult.getQrUrl());
        } else {
            return new BaseMsgInfo().setCode(-1).setMsg(preCreateResult.getMsg());
        }
    }

}
