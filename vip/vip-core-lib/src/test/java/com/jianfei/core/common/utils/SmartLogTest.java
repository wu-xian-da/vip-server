/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月27日-上午10:59:47
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 *
 * @Description:
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月27日 上午10:59:47
 * 
 * @version 1.0.0
 *
 */
public class SmartLogTest {

	/**
	 * 将日志内容记入在新文件中，方便分析
	 * 
	 * @version 1.0.0
	 */
	@Test
	public void testLogInFlume() {

		// 仅仅打印文本信息，日志文件的格式已经组装好
		SmartLog.trace("module_type", "search_key", "operate_content");
		SmartLog.debug("module_type", "search_key", "operate_content");
		SmartLog.info("module_type", "search_key", "operate_content");
		SmartLog.warn("module_type", "search_key", "operate_content");
		SmartLog.error("module_type", "search_key", "operate_content");

		// 需在日志中堆栈打印的异常 ，如果使用此方法，异常信息也为记入在新的日志文件中
		Exception exception = new RuntimeException("ceshi...");
		SmartLog.warn(exception, "module_type", "search_key", "operate_content");
		SmartLog.error(exception, "module_type", "search_key",
				"operate_content");

	}

	/**
	 * 测试以前的日志不会记入在新文件中
	 * 
	 * @version 1.0.0
	 */
	@Test
	public void testLogNotInFlume() {
		LoggerFactory.getLogger(SmartLogTest.class).error("kkkkkkkkkk");
	}
}
