/**
 * Title: LbapUtils.java
 * @Package com.success.lottery.lbapserver
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-2 ����04:03:54
 * @version V1.0
 */
package com.success.lottery.lbapserver;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * com.success.lottery.lbapserver
 * LbapUtils.java
 * LbapUtils
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-2 ����04:03:55
 * 
 */

public class LbapUtils {
	
	public final static java.util.Date string2Date(String format,String dateString)
	  throws java.lang.Exception {
	  DateFormat dateFormat = new SimpleDateFormat(format);
	  dateFormat.setLenient(false);
	  java.util.Date timeDate = dateFormat.parse(dateString);//util����
	  return timeDate;
	 }
	
	public final static String Date2string(String format,Date date)
	  throws java.lang.Exception {
	  DateFormat dateFormat = new SimpleDateFormat(format);
	  dateFormat.setLenient(false);
	  String DateString = dateFormat.format(date);//util����
	  return DateString;
	 }
	
	public static final String TimeStamp2String(Timestamp now){
		if(now == null){
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String result = null;
		if(now != null){
			result = df.format(now);
		}
		return result;
	}
	
	

	/**
	 *Title: 
	 *Description: 
	 */
	public LbapUtils() {
		// TODO �Զ����ɹ��캯�����
	}
	
	public static void main(String[] args) throws Exception {
		//String date = "20100918";
		//System.out.println("aaaa==="+LbapUtils.string2Date("yyyyMMdd",date));
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String str = df.format(now);
		System.out.println("str==="+str);
	}

}
