package com.jianfei.core.common.enu;
/**
 * 发票状态枚举类
 * @Description: TODO
 * @author guo.jian   
 * @Title: InvoiceState.java
 * @date 2016年6月2日 下午5:07:21 
 * @Version 1.0.0
 */
public enum InvoiceState {
	/**
	 * 不需要发票
	 */
	NOT_NEED_INVOICE(0),
	/**
	 * 需要发票
	 */
	NEED_INVOICE(1),
	
	/**
	 * 发票已邮寄
	 */
	SEND_INVOICE(2),
	;

	private int name;

	private InvoiceState(int name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public int getName() {
		return name;
	}
	
}
