package com.jianfei.core.common.utils;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @Description: TODO
 * @author: wang.yingli@jianfeitech.com
 * @date: 2015年12月2日 下午4:41:21
 * 
 * @version 1.0.0
 *
 */
public class DateUtil {

	public static final String DATA_FORMAT_COMMON = "yyyyMMdd";

	public static final String ALL_FOMAT = "yyyy-MM-dd HH:mm:ss";

	public static Date getDate(String source, String pattern) {
		if (source == null) {
			return null;
		}
		DateFormat formater = new SimpleDateFormat(pattern);
		try {
			return formater.parse(source);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String getDateStr(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		DateFormat formater = new SimpleDateFormat(pattern);
		return formater.format(date);
	}

	public static Date getStart(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getEnd(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * 获取上一天时间
	 * 
	 * @author: wang.yingli@jianfeitech.com
	 * @createTime: 2015年9月14日 上午9:56:44
	 * @history:
	 * @param date
	 * @return Date
	 */
	public static Date getLastDay(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH - 1, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60
				* 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "."
				+ sss;
	}

	/**
	 * 获取下一天时间
	 * 
	 * @author: wang.yingli@jianfeitech.com
	 * @createTime: 2015年9月14日 上午9:56:29
	 * @history:
	 * @param date
	 * @return Date
	 */
	public static Date getNextDay(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH + 1, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * timeCompareTime(进行比较的时间与开始时间和结束时间进行比较， 小于开始时间返回0， 大于结束时间返回2，
	 * 处于开始时间和结束时间之间返回1。)
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param compareTime
	 *            进行比较的时间
	 * @return Integer
	 * @version 1.0.0
	 */
	public static Integer timeCompareTime(Date startTime, Date endTime,
			Date compareTime) {
		Integer result = 0;
		long start = startTime.getTime();
		long end = endTime.getTime();
		long compare = compareTime.getTime();
		if (compare > end) {
			result = 2;
		} else if (compare <= end && compare >= start) {
			result = 1;
		}
		return result;
	}

	/**
	 * 日期转字符串
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
		return "";
	}

	/**
	 * 日期转字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, "yyyy-MM-dd hh:mm:ss");
	}

	public static Map<String, Object> getCurrentTime() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentTime", dateToString(new Date(), "yyyy-MM-dd"));
		return map;
	}

	public static Map<String, Object> getDelayDate(int putoff) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(cal.getTime());
		cal.add(Calendar.MONTH, -putoff);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", sdf.format(cal.getTime()));
		map.put("month", sdf2.format(cal.getTime()));
		map.put("dataStr", map.get("year") + "年" + map.get("month") + "月");
		return map;
	}
	
	public static void main(String[] args) {
		System.out.println(JSONObject.toJSONString(getDelayDate(1)));
	}
}
