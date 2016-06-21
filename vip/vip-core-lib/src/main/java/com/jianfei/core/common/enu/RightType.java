package com.jianfei.core.common.enu;

public enum RightType {
	/**
	 * vip卡登录前权益
	 */
	BEFORE_LOGIN_RIGHT(1),
	
	/**
	 * 常见问题
	 */
	PROBLEM(2),
	
	/**
	 * 关于
	 */
	ABOUT(3),
	
	/**
	 * 登录后vip卡权益
	 */
	AFTER_LOGIN_RIGHT(4);
	
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
