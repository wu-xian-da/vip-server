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
	 * @return 获得日志，自动判定日志发出者
	 */
	public static Logger get() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return LoggerFactory.getLogger(stackTrace[2].getClassName());
	}

	/**
	 * @return 获得日志，自动判定日志发出者
	 */
	private static Logger innerGet() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return LoggerFactory.getLogger(stackTrace[3].getClassName());
	}

	/**
	 * Trace等级日志，小于debug<br>
	 * 由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！
	 * 
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void trace(Object... arguments) {
		get().trace(
				format(arguments),
				union(arguments,
						DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)));
	}

	/**
	 * Trace等级日志，小于debug<br>
	 * 
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void trace(Logger logger, Object... arguments) {
		logger.trace(
				format(arguments),
				union(arguments,
						DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)));
	}

	/**
	 * Debug等级日志，小于Info<br>
	 * 由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！
	 * 
	 * @param arguments
	 *            module_type,search_key,operate_content
	 * @version 1.0.0
	 */
	public static void debug(Object... arguments) {
		String format = format(arguments);
		innerGet().debug(
				format,
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
	public static void debug(Logger logger, Object... arguments) {
		logger.debug(
				format(arguments),
				union(arguments,
						DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)));
	}

	/**
	 * Info等级日志，小于Warn<br>
	 * 由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！
	 * 
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void info(Object... arguments) {
		get().info(
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
	public static void info(Logger logger, Object... arguments) {
		logger.info(
				format(arguments),
				union(arguments,
						DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)));
	}

	/**
	 * Warn等级日志，小于Error<br>
	 * 由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！
	 * 
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void warn(Object... arguments) {
		get().warn(
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
	public static void warn(Logger logger, Object... arguments) {
		logger.warn(
				format(arguments),
				union(arguments,
						DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)));
	}

	/**
	 * Warn等级日志，小于Error<br>
	 * 由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！
	 * 
	 * @param e
	 *            需在日志中堆栈打印的异常
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void warn(Throwable e, Object... arguments) {
		get().warn(
				format(format(arguments),
						union(arguments, DateUtil.dateToString(new Date(),
								DateUtil.ALL_FOMAT))), e);
	}

	/**
	 * Warn等级日志，小于Error<br>
	 * 
	 * @param e
	 *            需在日志中堆栈打印的异常
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void warn(Logger logger, Throwable e, Object... arguments) {
		logger.warn(
				format(format(arguments),
						union(arguments, DateUtil.dateToString(new Date(),
								DateUtil.ALL_FOMAT))), e);
	}

	/**
	 * Error等级日志<br>
	 * 由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！
	 * 
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void error(Object... arguments) {
		get().error(
				format(arguments),
				union(arguments,
						DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)));
	}

	/**
	 * Error等级日志<br>
	 * 
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void error(Logger logger, Object... arguments) {
		logger.error(
				format(arguments),
				union(arguments,
						DateUtil.dateToString(new Date(), DateUtil.ALL_FOMAT)));
	}

	/**
	 * Error等级日志<br>
	 * 由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！
	 * 
	 * @param e
	 *            需在日志中堆栈打印的异常
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void error(Throwable e, Object... arguments) {
		get().error(
				format(format(arguments),
						union(arguments, DateUtil.dateToString(new Date(),
								DateUtil.ALL_FOMAT))), e);
	}

	/**
	 * Error等级日志<br>
	 * 
	 * @param e
	 *            需在日志中堆栈打印的异常
	 * @param arguments
	 *            module_type,search_key,operate_content
	 */
	public static void error(Logger logger, Throwable e, Object... arguments) {
		logger.error(
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
