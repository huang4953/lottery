package com.success.lottery.web.admin.decorator;

import javax.servlet.jsp.PageContext;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;
public class BetAmountFormatDecorator implements DisplaytagColumnDecorator{

	@Override
	public Object decorate(Object columnValue, PageContext arg1, MediaTypeEnum arg2) throws DecoratorException{
		try{
			Object colVal = columnValue;
			if(columnValue != null){
				colVal = (Integer)columnValue / 100;
			}
			return colVal;
		}catch(Exception nfe){
		}
		return columnValue;
	}
}
