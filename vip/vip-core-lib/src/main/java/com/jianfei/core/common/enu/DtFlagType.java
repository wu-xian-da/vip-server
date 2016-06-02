package com.jianfei.core.common.enu;
/**
 * 逻辑删除枚举类
 * @Description: TODO
 * @author guo.jian   
 * @Title: DtFlagType.java
 * @date 2016年6月2日 下午4:52:10 
 * @Version 1.0.0
 */
public enum DtFlagType {
	/**
	 * 是否删除（否）
	 */
	NOT_DELETE(0),
	
	/**
	 * 是否删除（是）
	 */
	ALREADY_DELETE(1);
	
	private int name;
	private DtFlagType(int name){
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public int getName() {
		return name;
	}
	
	
}
