/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午5:16:02 Copyright (c) 2016建飞科联公司-版权所有
 */
package com.jianfei.core.service.thirdpart.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.common.enu.PayType;
import com.jianfei.core.service.order.OrderManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateContentBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.jianfei.core.common.enu.VipOrderState;
import com.jianfei.core.common.pay.PayNotifyRequest;
import com.jianfei.core.common.pay.PayQueryResult;
import com.jianfei.core.common.pay.PreCreateResult;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.mapper.AppOrdersMapper;
import com.jianfei.core.service.thirdpart.ThirdPayManager;

/**
 *
 * @Description: TODO
 * @author: liu.lei@jianfeitech.com
 * @date: 2016年5月23日 下午5:16:02
 *
 * @version 1.0.0
 *
 */
@Service("aliPayManager")
public class AlipayPayManagerImpl extends ThirdPayManager {
    private static Log log = LogFactory.getLog(AlipayPayManagerImpl.class);
    private static AlipayTradeService tradeService;
    @Autowired
    private OrderManager orderManager;

    static {
        // 支付宝环境初始化
        Configs.init("zfbinfo.properties");
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }

    /**
     * 预下单接口（支付宝扫码）
     *
     */
    @Override
    public PreCreateResult tradePrecreate(Object param) {
        AlipayTradePrecreateContentBuilder payParam = (AlipayTradePrecreateContentBuilder) param;
        //payParam.setNotifyUrl("");


        PreCreateResult tradeResult = new PreCreateResult();
        /**
         * param参数示例
         *
         * // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线， //
         * 需保证商户系统端不能重复，建议通过数据库sequence生成， String outTradeNo = "tradeprecreate"
         * + System.currentTimeMillis() + (long)(Math.random() * 10000000L);
         *
         * // (必填) 订单标题，粗略描述用户的支付目的。如“喜士多（浦东店）消费” String subject = "喜士多（浦东店）消费";
         *
         * // (必填) 订单总金额，单位为元，不能超过1亿元 //
         * 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
         * String totalAmount = "0.01";
         *
         * // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段 //
         * 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】 String
         * undiscountableAmount = "0";
         *
         * // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号) //
         * 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID String sellerId = "";
         *
         * // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元" String body =
         * "购买商品2件共15.00元";
         *
         * // 商户操作员编号，添加此参数可以为商户操作员做销售统计 String operatorId = "test_operator_id";
         *
         * // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持 String storeId =
         * "test_store_id";
         *
         * // 支付超时，定义为120分钟 String timeExpress = "120m";
         *
         * // 商品明细列表，需填写购买商品详细信息， List<GoodsDetail> goodsDetailList = new
         * ArrayList<GoodsDetail>(); //
         * 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
         * GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "全麦小面包",
         * 1500, 1); // 创建好一个商品后添加至商品明细列表 goodsDetailList.add(goods1);
         *
         * AlipayTradePrecreateContentBuilder builder = new
         * AlipayTradePrecreateContentBuilder() .setSubject(subject)
         * .setTotalAmount(totalAmount) .setOutTradeNo(outTradeNo)
         * .setUndiscountableAmount(undiscountableAmount) .setSellerId(sellerId)
         * .setBody(body) //.setOperatorId(operatorId) .setStoreId(storeId)
         * //.setExtendParams(extendParams) .setTimeExpress(timeExpress)
         * .setGoodsDetailList(goodsDetailList);
         **/
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(payParam);
        tradeResult.setTradeNo(payParam.getOutTradeNo());
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");
                AlipayTradePrecreateResponse response = result.getResponse();
                // dumpResponse(response);
                tradeResult.setCode("0");
                tradeResult.setMsg("SUCCESS");
                tradeResult.setQrUrl(response.getQrCode());
                break;
            case FAILED:
                log.error("支付宝预下单失败");
                tradeResult.setCode("1");
                tradeResult.setMsg("支付宝预下单失败");
                break;

            case UNKNOWN:
                log.error("系统异常，预下单状态未知");
                tradeResult.setCode("2");
                tradeResult.setMsg("系统异常，预下单状态未知");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常");
                tradeResult.setCode("3");
                tradeResult.setMsg("不支持的交易状态，交易返回异常");
                break;
        }
        return tradeResult;
    }

    @Override
    public PayQueryResult tradeQuery(String tradeNo) {
        PayQueryResult payQueryResult = new PayQueryResult();
        payQueryResult.setTradeNo(tradeNo);
        AlipayF2FQueryResult result = tradeService.queryTradeResult(tradeNo);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                String tradeStatus = result.getResponse().getTradeStatus();
                if (tradeStatus.equals("TRADE_SUCCESS")) {
                    payQueryResult.setCode("0");
                    payQueryResult.setMsg("已支付");
                } else if (tradeStatus.equals("WAIT_BUYER_PAY")) {
                    payQueryResult.setCode("1");
                    payQueryResult.setMsg("未支付");
                } else if (tradeStatus.equals("TRADE_CLOSED")) {
                    payQueryResult.setCode("2");
                    payQueryResult.setMsg("交易关闭");
                }
                payQueryResult.setOutTradeNo(result.getResponse().getTradeNo());
                payQueryResult.setPayTime(DateUtil.dateToString(result.getResponse().getSendPayDate(), "yyyy-MM-dd HH:mm:ss"));
                payQueryResult.setPayUserId(result.getResponse().getBuyerLogonId());

                break;

            case FAILED:
                log.error("查询返回该订单支付失败或被关闭");
                payQueryResult.setCode("3");
                payQueryResult.setMsg("ALIPAY_查询返回该订单支付失败或被关闭");
                break;

            case UNKNOWN:
                log.error("系统异常，订单支付状态未知!");
                payQueryResult.setCode("3");
                payQueryResult.setMsg("ALIPAY_系统异常，订单支付状态未知!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!");
                payQueryResult.setCode("3");
                payQueryResult.setMsg("ALIPAY_不支持的交易状态，交易返回异常!");
                break;
        }

        return payQueryResult;
    }

    @Override
    public void tradeRefund(String tradeNo) {
        // TODO Auto-generated method stub
    }

    @Override
    public String payNotify(PayNotifyRequest req) {
        if (req.getResultCode().equals("TRADE_SUCCESS")) {
            AppOrders appOrders = new AppOrders();
            appOrders.setOrderId(req.getOutTradeNo());
            appOrders.setPayUserId(req.getPayUserId());
            appOrders.setSerialId(req.getTradeNo());
            appOrders.setPayType(PayType.ALIPAY.getName());
            try {
                appOrders.setPayTime(DateUtil.parseDateTime(req.getPayTime()));
            }catch (Exception e){
            	appOrders.setPayTime(new Date());
                log.error(e);
            }
            orderManager.updatePayState(appOrders);
            return "success";
        } else
            return "failed";
    }

}
