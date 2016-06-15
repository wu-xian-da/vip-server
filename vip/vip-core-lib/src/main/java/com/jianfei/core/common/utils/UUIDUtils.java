package com.jianfei.core.common.utils;

import java.util.Random;
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
	
	/**
	 * 生成主键(32位)
	 * @param fileName
	 * @return
	 */
	public static String getPrimaryKey(){
		String primaryKey = UUID.randomUUID().toString();
		return primaryKey.replaceAll("-", "");
	}

	/**
	 * 获取一定长度的随机字符串
	 * @param length 指定字符串长度
	 * @return 一定长度的字符串
	 */
	public static String getRandomStringByLength(int length) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
