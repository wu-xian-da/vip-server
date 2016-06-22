package com.jianfei.core.common.enu;
/**
 * 卡类型枚举类
 * @Description: TODO
 * @author guo.jian   
 * @Title: VipCardType.java
 * @date 2016年6月22日 上午10:38:31 
 * @Version 1.0.0
 */
public enum VipCardType {
	/**
	 * 没有激活
	 */
	NO_ATIVATE(0),
	
	/**
	 * 激活成功
	 */
	ACTIVATE_SUCCESS(1),
	
	/**
	 *退卡
	 */
	back_card(2),
	
	/**
	 * 激活失败
	 */
	ACTIVATE_FAIL(3);
	
	private int name;

	private VipCardType(int name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public int getName() {
		return name;
	}
	
	
	
	
	
	
}
