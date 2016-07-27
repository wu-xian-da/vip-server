/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年7月27日-上午9:41:26
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @Description: 日志工具类
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年7月27日 上午9:41:26
 * 
 * @version 1.0.0
 *
 */

public class SmartLog {
	/**
	 * 获得Logger
	 * 
	 * @param clazz
	 *            日志发出的类
	 * @return Logger
	 */
	public static Logger get(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

	/**
	 * 获得Logger
	 * 
	 * @param name
	 *            自定义的日志发出者名称
	 * @return Logger
	 */
	public static Logger get(String name) {
		return LoggerFactory.getLogger(name);
	}

	/**
	 * 暂时不用
	 * 
	 * @return 获得日志，自动判定日志发出者
	 */
	public static Logger get() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return LoggerFactory.getLogger(stackTrace[2].getClassName());
	}

	/**
	 * Trace等级日志，小于debug<br>
	 * 
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void trace(Object... arguments) {
		get(SmartLog.class).trace(
				format(arguments),
				union(arguments,
						DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)));
	}

	/**
	 * Debug等级日志，小于Info<br>
	 * 
	 * @param arguments
	 *            module_type,search_key,operate_content
	 * @version 1.0.0
	 */
	public static void debug(Object... arguments) {
		get(SmartLog.class).debug(
				format(arguments),
				union(arguments,
						DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)));
	}

	/**
	 * Info等级日志，小于Warn<br>
	 * 
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void info(Object... arguments) {
		get(SmartLog.class).info(
				format(arguments),
				union(arguments,
						DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)));
	}

	/**
	 * Warn等级日志，小于Error<br>
	 * 
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void warn(Object... arguments) {
		get(SmartLog.class).warn(
				format(arguments),
				union(arguments,
						DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)));
	}

	/**
	 * Warn等级日志，小于Error<br>
	 * 
	 * @param e
	 *            需在日志中堆栈打印的异常
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void warn(Throwable e, Object... arguments) {
		get(SmartLog.class).warn(
				format(format(arguments),
						union(arguments, DateUtil.dateToString(new Date(),
								DateUtil.ALL_FOMAT))), e);
	}

	/**
	 * Error等级日志<br>
	 * 
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void error(Object... arguments) {
		get(SmartLog.class).error(
				format(arguments),
				union(arguments,
						DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)));
	}

	/**
	 * Error等级日志<br>
	 * 
	 * @param e
	 *            需在日志中堆栈打印的异常
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void error(Throwable e, Object... arguments) {
		get(SmartLog.class).error(
				format(format(arguments),
						union(arguments, DateUtil.dateToString(new Date(),
								DateUtil.ALL_FOMAT))), e);
	}

	/**
	 * 格式化文本
	 * 
	 * @param template
	 *            文本模板，被替换的部分用 {} 表示
	 * @param values
	 *            参数值
	 * @return 格式化后的文本
	 */
	private static String format(String template, Object... values) {
		return String.format(template.replace("{}", "%s"), values);
	}

	/**
	 * 格式化
	 * 
	 * @param arguments
	 * @return String
	 * @version 1.0.0
	 */
	@SuppressWarnings("unused")
	private static String format(Object... arguments) {
		StringBuffer formatBuffer = new StringBuffer();
		for (Object object : arguments) {
			formatBuffer.append("{}|");
		}
		return formatBuffer.append("{}").toString();
	}

	/**
	 * 合并参数
	 * 
	 * @param arguments
	 * @param obj
	 * @return Object[]
	 * @version 1.0.0
	 */
	private static Object[] union(Object[] arguments, Object... obj) {
		List<Object> list = new ArrayList<Object>();
		for (Object o : arguments) {
			list.add(o);
		}
		for (Object o : obj) {
			list.add(o);
		}
		return list.toArray(new Object[] {});
	}
}
