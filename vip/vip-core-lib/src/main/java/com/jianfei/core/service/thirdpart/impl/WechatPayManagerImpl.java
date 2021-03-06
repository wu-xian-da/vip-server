package com.jianfei.core.service.thirdpart.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jianfei.core.bean.AppOrders;
import com.jianfei.core.common.enu.PayType;
import com.jianfei.core.common.utils.DateUtil;
import com.jianfei.core.common.utils.GloabConfig;

import com.jianfei.core.service.order.OrderManager;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfei.core.common.enu.VipOrderState;
import com.jianfei.core.common.pay.PayNotifyRequest;
import com.jianfei.core.common.pay.PayQueryResult;
import com.jianfei.core.common.pay.PreCreateResult;
import com.jianfei.core.mapper.AppOrdersMapper;
import com.jianfei.core.service.thirdpart.ThirdPayManager;
import com.tencent.WXPay;
import com.tencent.business.NativePayBusiness;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.protocol.native_protocol.NativeNotifyReq;
import com.tencent.protocol.native_protocol.NativePayReqData;
import com.tencent.protocol.native_protocol.NativePayResData;
import com.tencent.protocol.native_query_protocol.NativePayQueryResData;

@Service("wechatiPayManager")
public class WechatPayManagerImpl extends ThirdPayManager {
	private static Log log = LogFactory.getLog(WechatPayManagerImpl.class);
	static {
		
		//微信环境初始化
		WXPay.initSDKConfiguration("5173af15bcd5ee99fb9ca5257ba4436a", "wx49a4c8c1d7ed22cb", 
				"1359191602", "", GloabConfig.getConfig("wechat.cert.location"), "1359191602");
		
	}
	public WechatPayManagerImpl() {
//		WXPay.initSDKConfiguration("900150983cd24fb0d6963f7d28e17f72", "wxde0de2c3d9c40770", "1349068301", "", 
//				"/apiclient_cert.p12", "1349068301");
	}
	
    @Autowired
    private OrderManager orderManager;
    
	/**
	 * 预下单接口（微信扫码支付）
	 */
	@Override
	public PreCreateResult tradePrecreate(Object param) {
		NativePayReqData payParam = (NativePayReqData)param;
		final PreCreateResult tradeResult = new PreCreateResult();
		try {
			WXPay.doNativePayBusiness(payParam,new NativePayBusiness.ResultListener() {
				
				@Override
				public void onSuccess(NativePayResData nativePayResData) {
					tradeResult.setCode("0");
					tradeResult.setMsg("SUCCESS");
					tradeResult.setQrUrl(nativePayResData.getCode_url());
				}
				
				@Override
				public void onFailBySignInvalid(NativePayResData nativePayResData) {
					tradeResult.setCode("1");
					tradeResult.setMsg("");
				}
				
				@Override
				public void onFailByReturnCodeFail(NativePayResData nativePayResData) {
					tradeResult.setCode("2");
					tradeResult.setMsg("");
				}
				
				@Override
				public void onFailByReturnCodeError(NativePayResData nativePayResData) {
					tradeResult.setCode("3");
					tradeResult.setMsg("");
				}
				
				@Override
				public void onFailByMoneyNotEnough(NativePayResData nativePayResData) {
					tradeResult.setCode("4");
					tradeResult.setMsg("");
				}
				
				@Override
				public void onFail(NativePayResData nativePayResData) {
					tradeResult.setCode("5");
					tradeResult.setMsg("调用失败");
				}
			});
		} catch (Exception e) {
			tradeResult.setCode("6");
			tradeResult.setMsg("未知错误");
			e.printStackTrace();
		}
		return tradeResult;
	}

	@Override
	public PayQueryResult tradeQuery(String tradeNo) {
		PayQueryResult payQueryResult = new PayQueryResult();
		payQueryResult.setTradeNo(tradeNo);
		try {
			String result = WXPay.requestNativePayQueryService(tradeNo);
			NativePayQueryResData nativePayQueryResData = (NativePayQueryResData)Util.getObjectFromXML(result, NativePayQueryResData.class);
			if (nativePayQueryResData.getReturn_code().equals("SUCCESS")){
				if(nativePayQueryResData.getResult_code().equals("SUCCESS")){
					if (nativePayQueryResData.getTrade_state().equals("SUCCESS")){
						payQueryResult.setCode("0");
						payQueryResult.setMsg("已支付");
					    try {
							Date payTime = DateUtils.parseDate(nativePayQueryResData.getTime_end(), "yyyyMMddHHmmss");
							payQueryResult.setPayTime(DateUtil.dateToString(payTime, "yyyy-MM-dd HH:mm:ss"));
						} catch (ParseException e2) {
							e2.printStackTrace();
						}
					}else if (nativePayQueryResData.getTrade_state().equals("NOTPAY")){
						payQueryResult.setCode("1");
						payQueryResult.setMsg("未支付");	
					}else if (nativePayQueryResData.getTrade_state().equals("CLOSED")){
						payQueryResult.setCode("2");
						payQueryResult.setMsg("交易关闭");
					}else{
						payQueryResult.setCode("3");
						payQueryResult.setMsg(nativePayQueryResData.getTrade_state());
					}
								
					payQueryResult.setOutTradeNo(nativePayQueryResData.getTransaction_id());

					payQueryResult.setPayUserId(nativePayQueryResData.getOpenid());
					
				}else{
					payQueryResult.setCode("3");
					payQueryResult.setMsg(nativePayQueryResData.getErr_code_des());
				}

			}else {
				payQueryResult.setCode("3");
				payQueryResult.setMsg(nativePayQueryResData.getReturn_msg());
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payQueryResult;
	}
	
	

	@Override
	public void tradeRefund(String tradeNo) {
		// TODO Auto-generated method stub

	}
	@Override
	public String payNotify(PayNotifyRequest req) {
		String result="";
		String resultCode = req.getResultCode();
		String returnCode = req.getReturnCode();
		String sign = req.getSign();
		String signResult = req.getSignResult();
		
		if (orderManager.getOrderInfoByOrderId(req.getOutTradeNo()).getOrderState().equals(VipOrderState.ALREADY_PAY.getName())){
			result = buildResult("SUCCESS", "OK");
			return result;
		}
		System.out.println(signResult+":"+sign);
		if (sign.equals(signResult)){
			if (returnCode.equals("SUCCESS")){
				if (resultCode.equals("SUCCESS")){
					AppOrders appOrders=new AppOrders();
					appOrders.setOrderId(req.getOutTradeNo());
					appOrders.setPayUserId(req.getPayUserId());
					appOrders.setSerialId(req.getTradeNo());
					appOrders.setPayType(PayType.WXPAY.getName());
					try {
						appOrders.setPayTime(DateUtil.parseDateTime(req.getPayTime()));
					}catch (Exception e){
						log.error(e);
						appOrders.setPayTime(new Date());
					}
					orderManager.updatePayState(appOrders);
				}
				result = buildResult("SUCCESS", "OK");
			}else{
				result = buildResult("FAIL", "发生错误");
			}
		}else
			result = buildResult("FAIL", "签名错误");
		return result;
	}
	
	public String buildResult(String returnCode,String returnMsg){
		StringBuffer sb =new StringBuffer();
		sb.append("<xml>");
		sb.append("<return_code><![CDATA["+returnCode+"]]></return_code>");
		sb.append("<return_msg><![CDATA["+returnMsg+"]]></return_msg>");
		sb.append("</xml>");
		return sb.toString();
	}

}
