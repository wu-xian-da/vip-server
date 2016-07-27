package com.jianfei.core.common.enu;

/**
 * 模块枚举类
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author guo.jian   
 * @Title: ModuleType.java
 * @date 2016年7月27日 下午2:12:53 
 * @Version 1.0.0
 */
public enum ModuleType {
	/**
	 * 订单模块标识
	 */
	ORDER_MODULE("001"),
	
	/**
	 * 短信模块
	 */
	MESSAGE_MODULE("002"),
	
	/**
	 * vip卡模块
	 */
	VIPCARD_MODULE("003");
	
	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	private ModuleType(String name) {
		this.name = name;
	}
	
}
