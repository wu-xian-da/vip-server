/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月20日-下午2:53:19
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.bean;

/**
 * 订单vip卡表
 * @Description: TODO
 * @author: guo.jian@jianfeitech.com 
 * @date: 2016年5月20日 下午2:53:19 
 * 
 * @version 1.0.0
 *
 */
public class AppOrderCard {
	private String cardNo;//vip卡号
	private float initMoney;//初始金额
	/**
	 * cardNo
	 *
	 * @return  the cardNo
	 * @version   1.0.0
	*/
	
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * initMoney
	 *
	 * @return  the initMoney
	 * @version   1.0.0
	*/
	
	public float getInitMoney() {
		return initMoney;
	}
	/**
	 * @param initMoney the initMoney to set
	 */
	public void setInitMoney(float initMoney) {
		this.initMoney = initMoney;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppOrderCard [cardNo=" + cardNo + ", initMoney=" + initMoney + "]";
	}
	
}
