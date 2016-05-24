/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月23日-下午4:20:02
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.service.thirdpart;

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
	static {
		System.out.print("abc");
	}
	
	public String tradeNo;
	/**
	 * 
	 * tradePrecreate(第一步：调用交易预创建接口，返回二维码url)
	 * @param requestBuilder
	 * @return
	 *PreCreateResult
	 * @version  1.0.0
	 */
	public abstract PreCreateResult tradePrecreate();
	
	/**
	 * 
	 * tradeQuery(轮询交易状态)
	 *void
	 * @version  1.0.0
	 */
	public abstract void tradeQuery(String tradeNo);
	
	/**
	 * 
	 * tradeRefund(发起退款)
	 *void
	 * @version  1.0.0
	 */
	public abstract void tradeRefund(String tradeNo);
}
