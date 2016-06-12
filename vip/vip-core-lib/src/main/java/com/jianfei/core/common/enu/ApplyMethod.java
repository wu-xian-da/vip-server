package com.jianfei.core.common.enu;
/**
 * vip卡申请方式
 * @Description: TODO
 * @author guo.jian   
 * @Title: ApplyMethod.java
 * @date 2016年6月12日 下午1:48:42 
 * @Version 1.0.0
 */
public enum ApplyMethod {
	/**
	 * 客服申请
	 */
	CUSTOMER_SERVICE_APPLY(0),
	
	/**
	 * 现场申请
	 */
	SCENE_APPLY(1);
	private int name;

	private ApplyMethod(int name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public int getName() {
		return name;
	}

	
	
}
