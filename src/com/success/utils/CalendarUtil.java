package com.success.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.time.DateUtils;

public class CalendarUtil {
	/**
	 * 将一个 年年年年月月日日 的字符串转换为日历类
	 * 
	 * @param parameterValue
	 */
	public static Calendar parseYYYYMMDD(String parameterValue) {
		String format = "yyyyMMdd";
		return parse(parameterValue, format);
	}

	public static Calendar parse(String string, String... format) {
		Calendar result = Calendar.getInstance();
		try {
			result.setTime(DateUtils.parseDate(string, format));
			return result;
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static String toYYYY_MM_DD(Calendar date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
	}
	
	public static Calendar getYesterday(){
		Calendar result = Calendar.getInstance();
		result.add(Calendar.DATE, -1);
		return result;
	}
	/**
	 * 返回此日期的零点整，如2007-3-14 19:00:35 返回 2007-3-14 00:00:00
	 * 
	 * @param date
	 */
	public static Calendar getTheDayZero(Calendar date) {
		Calendar result = (Calendar) date.clone();
		result.set(Calendar.HOUR_OF_DAY, 0);
		result.set(Calendar.MINUTE, 0);
		result.set(Calendar.SECOND, 0);
		result.set(Calendar.MILLISECOND, 0);
		return result;
	}
	/**
	 * 返回此日期的午夜，如2007-3-14 19:00:35 返回 2007-3-14 23:59:59
	 * 
	 * @param date
	 */
	public static Calendar getTheDayMidnight(Calendar date) {
		Calendar result = (Calendar) date.clone();
		result.set(Calendar.HOUR_OF_DAY, 23);
		result.set(Calendar.MINUTE, 59);
		result.set(Calendar.SECOND, 59);
		result.set(Calendar.MILLISECOND, 999);
		return result;
	}
	public static Calendar now() {
		return Calendar.getInstance();
	}
}
