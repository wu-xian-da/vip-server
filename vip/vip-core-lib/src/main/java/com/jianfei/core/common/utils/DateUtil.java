package com.jianfei.core.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	public static final String COMPACT_DATE_FORMAT = "yyyyMMdd";
	public static final String YM = "yyyyMM";
	public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";
	public static final String NORMAL_DATE_FORMAT_NEW = "yyyy-mm-dd hh24:mi:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_ALL = "yyyyMMddHHmmssS";

	public static Long strDateToNum(String paramString) throws Exception {
		if (paramString == null)
			return null;
		String[] arrayOfString = null;
		String str = "";
		if (paramString.indexOf("-") >= 0) {
			arrayOfString = paramString.split("-");
			for (int i = 0; i < arrayOfString.length; ++i)
				str = str + arrayOfString[i];
			return Long.valueOf(Long.parseLong(str));
		}
		return Long.valueOf(Long.parseLong(paramString));
	}

	public static Long strDateToNum1(String paramString) throws Exception {
		if (paramString == null)
			return null;
		String[] arrayOfString = null;
		String str = "";
		if (paramString.indexOf("-") >= 0) {
			arrayOfString = paramString.split("-");
			for (int i = 0; i < arrayOfString.length; ++i)
				if (arrayOfString[i].length() == 1)
					str = str + "0" + arrayOfString[i];
				else
					str = str + arrayOfString[i];
			return Long.valueOf(Long.parseLong(str));
		}
		return Long.valueOf(Long.parseLong(paramString));
	}

	public static String numDateToStr(Long paramLong) {
		if (paramLong == null)
			return null;
		String str = null;
		str = paramLong.toString().substring(0, 4) + "-"
				+ paramLong.toString().substring(4, 6) + "-"
				+ paramLong.toString().substring(6, 8);
		return str;
	}

	public static java.util.Date stringToDate(String paramString1,
			String paramString2) throws Exception {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				paramString2);
		localSimpleDateFormat.setLenient(false);
		try {
			return localSimpleDateFormat.parse(paramString1);
		} catch (ParseException localParseException) {
			throw new Exception("解析日期字符串时出错！");
		}
	}

	public static java.util.Date compactStringToDate(String paramString)
			throws Exception {
		return stringToDate(paramString, "yyyyMMdd");
	}

	public static String dateToCompactString(java.util.Date paramDate) {
		return dateToString(paramDate, "yyyyMMdd");
	}

	public static String dateToNormalString(java.util.Date paramDate) {
		return dateToString(paramDate, "yyyy-MM-dd");
	}

	public static String compactStringDateToNormal(String paramString)
			throws Exception {
		return dateToNormalString(compactStringToDate(paramString));
	}

	public static int getDaysBetween(java.util.Date paramDate1,
			java.util.Date paramDate2) throws Exception {
		Calendar localCalendar1 = Calendar.getInstance();
		Calendar localCalendar2 = Calendar.getInstance();
		localCalendar1.setTime(paramDate1);
		localCalendar2.setTime(paramDate2);
		if (localCalendar1.after(localCalendar2))
			throw new Exception("起始日期小于终止日期!");
		int i = localCalendar2.get(6) - localCalendar1.get(6);
		int j = localCalendar2.get(1);
		if (localCalendar1.get(1) != j) {
			localCalendar1 = (Calendar) localCalendar1.clone();
			do {
				i += localCalendar1.getActualMaximum(6);
				localCalendar1.add(1, 1);
			} while (localCalendar1.get(1) != j);
		}
		return i;
	}

	public static java.util.Date addDays(java.util.Date paramDate, int paramInt) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		int i = localCalendar.get(6);
		localCalendar.set(6, i + paramInt);
		return localCalendar.getTime();
	}

	public static java.util.Date addDays(String paramString1,
			String paramString2, int paramInt) throws Exception {
		Calendar localCalendar = Calendar.getInstance();
		java.util.Date localDate = stringToDate(paramString1, paramString2);
		localCalendar.setTime(localDate);
		int i = localCalendar.get(6);
		localCalendar.set(6, i + paramInt);
		return localCalendar.getTime();
	}

	public static java.sql.Date getSqlDate(java.util.Date paramDate)
			throws Exception {
		java.sql.Date localDate = new java.sql.Date(paramDate.getTime());
		return localDate;
	}

	public static String formatDate(java.util.Date paramDate) {
		if (paramDate == null)
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		localSimpleDateFormat.setLenient(false);
		return localSimpleDateFormat.format(paramDate);
	}

	public static String formatDateTime(java.util.Date paramDate) {
		if (paramDate == null)
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		localSimpleDateFormat.setLenient(false);
		return localSimpleDateFormat.format(paramDate);
	}

	public static java.util.Date parseDate(String paramString) throws Exception {
		if ((paramString == null) || (paramString.trim().equals("")))
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		localSimpleDateFormat.setLenient(false);
		try {
			return localSimpleDateFormat.parse(paramString);
		} catch (ParseException localParseException) {
			throw new Exception("日期解析出错！", localParseException);
		}
	}

	public static java.util.Date parseDateTime(String paramString)
			throws Exception {
		if ((paramString == null) || (paramString.trim().equals("")))
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		localSimpleDateFormat.setLenient(false);
		try {
			return localSimpleDateFormat.parse(paramString);
		} catch (ParseException localParseException) {
			throw new Exception("时间解析异常！", localParseException);
		}
	}

	public static Integer getYM(String paramString) throws Exception {
		if (paramString == null)
			return null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		localSimpleDateFormat.setLenient(false);
		java.util.Date localDate;
		try {
			localDate = localSimpleDateFormat.parse(paramString);
		} catch (ParseException localParseException) {
			throw new Exception("时间解析异常！", localParseException);
		}
		return getYM(localDate);
	}

	public static Integer getYM(java.util.Date paramDate) {
		if (paramDate == null)
			return null;
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		int i = localCalendar.get(1);
		int j = localCalendar.get(2) + 1;
		return new Integer(i * 100 + j);
	}

	public static int addMonths(int paramInt1, int paramInt2) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(1, paramInt1 / 100);
		localCalendar.set(2, paramInt1 % 100 - 1);
		localCalendar.set(5, 1);
		localCalendar.add(2, paramInt2);
		return getYM(localCalendar.getTime()).intValue();
	}

	public static java.util.Date addMonths(java.util.Date paramDate,
			int paramInt) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		localCalendar.add(2, paramInt);
		return localCalendar.getTime();
	}

	public static int monthsBetween(int paramInt1, int paramInt2) {
		int i = paramInt2 / 100 * 12 + paramInt2 % 100
				- (paramInt1 / 100 * 12 + paramInt1 % 100);
		return i;
	}

	public static int monthsBetween(java.util.Date paramDate1,
			java.util.Date paramDate2) {
		return monthsBetween(getYM(paramDate1).intValue(), getYM(paramDate2)
				.intValue());
	}

	public static String getChineseDate(Calendar paramCalendar) {
		int i = paramCalendar.get(1);
		int j = paramCalendar.get(2);
		int k = paramCalendar.get(5);
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append(i);
		localStringBuffer.append("年");
		localStringBuffer.append(j + 1);
		localStringBuffer.append("月");
		localStringBuffer.append(k);
		localStringBuffer.append("日");
		return localStringBuffer.toString();
	}

	public static String getChineseWeekday(Calendar paramCalendar) {
		switch (paramCalendar.get(7)) {
		case 2:
			return "星期一";
		case 3:
			return "星期二";
		case 4:
			return "星期三";
		case 5:
			return "星期四";
		case 6:
			return "星期五";
		case 7:
			return "星期六";
		case 1:
			return "星期日";
		}
		return "未知";
	}

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

	public static Map<String, Object> dailyExtractDate() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("maxTime", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
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

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 * 
	 * @param date
	 *            日期
	 * @param dateType
	 *            类型
	 * @param amount
	 *            数值
	 * @return 计算后日期
	 */
	public static Date addInteger(Date date, int dateType, int amount) {
		Date myDate = null;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(dateType, amount);
			myDate = calendar.getTime();
		}
		return myDate;
	}
	
	/**
	 * 将日期转成特定的格式
	 */
	public static String formatterDate(Date date){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(date);
	}
}
