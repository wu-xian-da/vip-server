package com.jianfei.core.common.enu;

public enum RightType {
	/**
	 * vip卡权益
	 */
	RIGHT(1),
	
	/**
	 * 常见问题
	 */
	PROBLEM(2),
	
	/**
	 * 关于
	 */
	ABOUT(3);
	private int name;

	private RightType(int name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public int getName() {
		return name;
	}
	
}
