package com.jianfei.core.common.enu;

/**
 * 订单状态枚举类
 * @Description:
 * @author guo.jian   
 * @Title: VipOrderState.java
 * @date 2016年5月30日 下午3:47:29 
 * @Version 1.0.0
 */
public enum VipOrderState {
	/**
	 * 未支付
	 */
	NOT_PAY(0),
	
	/**
	 * 已支付
	 */
	ALREADY_PAY(1),
	
	/**
	 * 正在审核
	 */
	BEING_AUDITED(2),
	
	/**
	 * 审核通过
	 */
	
	AUDIT_PASS(3),
	
	/**
	 * 已退款
	 */
	ALREADY_REFUND(4),
	
	/**
	 * 已失效
	 */
	ALREADY_INVALID(5);
	
	private int name;

	private VipOrderState(int name){
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public int getName() {
		return name;
	}
}
