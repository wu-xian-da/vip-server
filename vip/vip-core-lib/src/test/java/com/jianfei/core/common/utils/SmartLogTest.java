/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月27日-上午10:59:47
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Test;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月27日 上午10:59:47
 * 
 * @version 1.0.0
 *
 */
public class SmartLogTest {

	/**
	 * Test method for
	 * {@link com.jianfei.core.common.utils.SmartLog#trace(org.slf4j.Logger, java.lang.Object[])}
	 * .
	 */
	@Test
	public void testLog() {
		SmartLog.trace("1", "2", "3", "4");
		SmartLog.trace(SmartLog.get(SmartLogTest.class), "1", "2", "3", "4");
		SmartLog.debug("1", "2", "3");
		SmartLog.debug(SmartLog.get(SmartLogTest.class), "1", "2", "3", "4");
		SmartLog.info("1", "2", "3", "4");
		SmartLog.info(SmartLog.get(SmartLogTest.class), "1", "2", "3", "4");
		SmartLog.warn("1", "2", "3", "4");
		SmartLog.warn(SmartLog.get(SmartLogTest.class), "1", "2", "3", "4");
		SmartLog.error("1", "2", "3", "4");
		SmartLog.error(SmartLog.get(SmartLogTest.class), "1", "2", "3");
	}
}
