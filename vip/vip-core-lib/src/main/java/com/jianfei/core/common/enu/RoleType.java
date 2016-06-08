package com.jianfei.core.common.enu;

public enum RoleType {
	/**
	 * 经理类型
	 */
	managerType("1", "经理"),
	/**
	 * 主管类型
	 */
	masterType("2", "主管"),
	/**
	 * 人力
	 */
	hrType("3", "人力"),
	/**
	 * 客服
	 */
	serviceType("4", "人力"),
	caiwuType("5", "财务"), ;

	private String type;
	private String value;

	private RoleType(String type, String value) {
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
