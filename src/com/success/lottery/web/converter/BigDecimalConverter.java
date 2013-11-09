package com.success.lottery.web.converter;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

public class BigDecimalConverter extends StrutsTypeConverter {

	@SuppressWarnings("unchecked")
	@Override
	public Object convertFromString(Map ctx, String[] value, Class arg2) {
		if (StringUtils.isEmpty(value[0])) {
			return null;
		}
		return new BigDecimal(value[0]);

	}

	@SuppressWarnings("unchecked")
	@Override
	public String convertToString(Map ctx, Object data) {
		return data.toString();
	}
}