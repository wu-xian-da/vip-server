/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月26日-上午9:27:58
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import com.alibaba.fastjson.JSONObject;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月26日 上午9:27:58
 * 
 * @version 1.0.0
 *
 */
public class TestDateUtil {
	// 1、获取当月第一天
	@Test
	public void testForDate() {
		// 规定返回日期格式
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		// 设置为第一天
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = sf.format(gcLast.getTime());
		// 打印本月第一天
		System.out.println(day_first);
	}

	// 2、获取当月最后一天
	@Test
	public void testForDatelast() {
		// 获取Calendar
		Calendar calendar = Calendar.getInstance();
		// 设置日期为本月最大日期
		calendar.set(Calendar.DATE, calendar.getActualMaximum(calendar.DATE));
		// 设置日期格式
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String ss = sf.format(calendar.getTime());
		System.out.println(ss + " 23:59:59");
	}

	// 3、非常简单和实用的获取本月第一天和最后一天
	@Test
	public void testt() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String first = format.format(c.getTime());
		System.out.println("===============本月first day:" + first);

		// 获取当前月最后一天
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH,
				ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		System.out.println("===============本月last day:" + last);
	}

	// 4、获取上个月的第一天
	@Test
	public void getBeforeFirstMonthdate() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		System.out.println("上个月第一天：" + format.format(calendar.getTime()));
	}

	// 5、获取上个月的最后一天
	@Test
	public void getBeforeLastMonthdate() throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		System.out.println("上个月最后一天：" + sf.format(calendar.getTime()));
	}

	// 6、获取上个月的最后一天

	@Test
	public void addMinute() throws Exception {
		Date date = new Date();
		System.out.println(DateUtil.dateToString(date, DateUtil.ALL_FOMAT));
		Date d = DateUtil.addInteger(date, Calendar.MINUTE, -35);
		System.out.println(DateUtil.dateToString(d ,DateUtil.ALL_FOMAT));
	}
}
