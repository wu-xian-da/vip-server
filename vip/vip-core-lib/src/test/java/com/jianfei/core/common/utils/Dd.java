/**
 * @项目名:vip
 * @版本信息:1.0
 * @date:2016年5月26日-上午9:55:57
 * Copyright (c) 2016建飞科联公司-版权所有
 *
 */
package com.jianfei.core.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @Description: TODO
 * @author: li.binbin@jianfeitech.com
 * @date: 2016年5月26日 上午9:55:57
 * 
 * @version 1.0.0
 *
 */
public class Dd {
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
		Date date = new Date();
		System.out.println(sdf.format(date));
		System.out.println(sdf.format(getDelayDate(date,5)));
		System.out.println(sdf2.format(getDelayDate(date,5)));
	}

	private static Date getDelayDate(Date date, int putoff) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -putoff);
		return cal.getTime();
	}
}
