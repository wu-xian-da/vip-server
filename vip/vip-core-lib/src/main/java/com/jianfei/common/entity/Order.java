/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月17日-下午2:50:12
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.common.entity;

import java.io.Serializable;

/**
 *	订单实体类
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月17日 下午2:50:12 
 * 
 * @version 1.0.0
 *
 */
public class Order implements Serializable{
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @version 1.0.0
	 */
	
	private static final long serialVersionUID = 1L;
	private String orderId;
	private String orderTime;
	/**
	 * orderId
	 *
	 * @return  the orderId
	 * @version   1.0.0
	*/
	
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * orderTime
	 *
	 * @return  the orderTime
	 * @version   1.0.0
	*/
	
	public String getOrderTime() {
		return orderTime;
	}
	/**
	 * @param orderTime the orderTime to set
	 */
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	
}
