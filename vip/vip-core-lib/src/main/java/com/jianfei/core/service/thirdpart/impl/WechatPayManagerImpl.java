//package com.jianfei.core.service.thirdpart.impl;
//
//import org.springframework.stereotype.Service;
//
//import com.jianfei.core.common.pay.PayQueryResult;
//import com.jianfei.core.common.pay.PreCreateResult;
//import com.jianfei.core.service.thirdpart.ThirdPayManager;
//import com.tencent.WXPay;
//import com.tencent.business.NativePayBusiness;
//import com.tencent.common.Util;
//import com.tencent.protocol.native_protocol.NativePayReqData;
//import com.tencent.protocol.native_protocol.NativePayResData;
//import com.tencent.protocol.native_query_protocol.NativePayQueryResData;
//
//@Service("WechatiPayManager")
//public class WechatPayManagerImpl extends ThirdPayManager {
//	static {
//		
//		//微信环境初始化
//		//WXPay.initSDKConfiguration(key, appID, mchID, sdbMchID, certLocalPath, certPassword);
//		
//	}
//	
//	/**
//	 * 预下单接口（微信扫码支付）
//	 */
//	@Override
//	public PreCreateResult tradePrecreate(Object param) {
//		NativePayReqData payParam = (NativePayReqData)param;
//		final PreCreateResult tradeResult = new PreCreateResult();
//		try {
//			WXPay.doNativePayBusiness(payParam,new NativePayBusiness.ResultListener() {
//				
//				@Override
//				public void onSuccess(NativePayResData nativePayResData) {
//					tradeResult.setCode("0");
//					tradeResult.setMsg("SUCCESS");
//					tradeResult.setQrUrl(nativePayResData.getCode_url());
//				}
//				
//				@Override
//				public void onFailBySignInvalid(NativePayResData nativePayResData) {
//					tradeResult.setCode("1");
//					tradeResult.setMsg("");
//				}
//				
//				@Override
//				public void onFailByReturnCodeFail(NativePayResData nativePayResData) {
//					tradeResult.setCode("2");
//					tradeResult.setMsg("");
//				}
//				
//				@Override
//				public void onFailByReturnCodeError(NativePayResData nativePayResData) {
//					tradeResult.setCode("3");
//					tradeResult.setMsg("");
//				}
//				
//				@Override
//				public void onFailByMoneyNotEnough(NativePayResData nativePayResData) {
//					tradeResult.setCode("4");
//					tradeResult.setMsg("");
//				}
//				
//				@Override
//				public void onFail(NativePayResData nativePayResData) {
//					tradeResult.setCode("5");
//					tradeResult.setMsg("调用失败");
//				}
//			});
//		} catch (Exception e) {
//			tradeResult.setCode("6");
//			tradeResult.setMsg("未知错误");
//			e.printStackTrace();
//		}
//		return tradeResult;
//	}
//
//	@Override
//	public PayQueryResult tradeQuery(String tradeNo) {
//		PayQueryResult payQueryResult = new PayQueryResult();
//		payQueryResult.setTradeNo(tradeNo);
//		try {
//			String result = WXPay.requestNativePayQueryService(tradeNo);
//			NativePayQueryResData nativePayQueryResData = (NativePayQueryResData)Util.getObjectFromXML(result, NativePayResData.class);
//			if (nativePayQueryResData.getReturn_code().equals("SUCCESS")){
//				payQueryResult.setCode("0");
//				payQueryResult.setMsg("SUCCESS");
//			}else {
//				payQueryResult.setCode("1");
//				payQueryResult.setMsg("FAILED");
//			}
//					
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return payQueryResult;
//	}
//
//	@Override
//	public void tradeRefund(String tradeNo) {
//		// TODO Auto-generated method stub
//
//	}
//
//}
