package com.success.lottery.web.converter;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.util.TypeConversionException;
import com.success.utils.DateUtil;

public class CalendarConverter extends StrutsTypeConverter {

	@SuppressWarnings("unchecked")	
	@Override
	public Object convertFromString(Map ctx, String[] value, Class arg2) {
		if (StringUtils.isEmpty(value[0])) {
			return null;
		}

		Calendar result = Calendar.getInstance();
		try {
			result.setTime(DateUtils.parseDate(value[0], new String[] {"yyyy/MM/dd","yyyy-MM-dd","yyyy-MM-dd hh:mm:ss","yyyy-MM-dd hh:mm","yyyyMMdd"}));
		} catch (ParseException pe) {
			pe.printStackTrace();
			throw new TypeConversionException(pe.getMessage());
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	public String convertToString(Map ctx, Object data) {
		return	DateUtil.toDateStamp((Calendar)data);
	}
}