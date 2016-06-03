package com.jianfei.core.service.thirdpart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateContentBuilder;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.jianfei.core.common.pay.PayQueryResult;
import com.jianfei.core.common.pay.PreCreateResult;
import com.tencent.protocol.native_protocol.NativePayReqData;

/**
 *
 * @Description: TODO
 * @author:liu.lei@jianfeitech.com 
 * @date: 2016年5月23日 下午4:20:02 
 * 
 * @version 1.0.0
 *
 */
public abstract class ThirdPayManager {

	
	/**
	 * 预下单请求
	 * tradePrecreate
	 * @param aliParam 
	 * @return
	 *PreCreateResult
	 * @version  1.0.0
	 */
	public abstract PreCreateResult tradePrecreate(Object payParam);
	
	
	/**
	 * 
	 * tradeQuery(轮询交易状态)
	 *void
	 * @version  1.0.0
	 */
	public abstract PayQueryResult tradeQuery(String tradeNo);
	
	/**
	 * 
	 * tradeRefund(发起退款)
	 *void
	 * @version  1.0.0
	 */
	public abstract void tradeRefund(String tradeNo);
	
	public abstract String notify(String objStr);
}
