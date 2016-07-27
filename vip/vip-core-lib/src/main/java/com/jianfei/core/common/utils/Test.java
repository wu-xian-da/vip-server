/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月27日-上午9:51:15
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import org.slf4j.Logger;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月27日 上午9:51:15
 * 
 * @version 1.0.0
 *
 */
public class Test {
	private static Logger log = SmartLog.get();

	public static void main(String[] args) {
		// 第一种使用方法（效率低）
		SmartLog.debug("我是一条debug消息");
		SmartLog.debug("122","wwww","5555");

		// 第二种使用方法
		SmartLog.debug("hello", "kitty");
		RuntimeException e = new RuntimeException("错误");
		SmartLog.error(e, "123","312","333");

	}
}
