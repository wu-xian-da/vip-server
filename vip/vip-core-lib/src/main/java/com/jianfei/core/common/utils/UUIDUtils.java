package com.jianfei.core.common.utils;

import java.util.UUID;
/**
 * 基于UUID生成字符
 * @Description: TODO
 * @author guo.jian   
 * @Title: UUIDUtils.java
 * @date 2016年6月1日 下午1:26:54 
 * @Version 1.0.0
 */
public class UUIDUtils {
	/**
	 * 生成文件名
	 * @param fileName
	 * @return
	 */
	public static String returnNewFileName(String fileName){
		int beginIndex = fileName.lastIndexOf(".");
		String newFileName = UUID.randomUUID()+fileName.substring(beginIndex, fileName.length());
		return newFileName;
	}
}
