package com.success.lottery.sms.process;

import java.io.UnsupportedEncodingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.sms.MO;


public abstract class SMSService{

	public final static int SMSFORMATERROR = 880001;
	public final static String SMSFORMATERROR_STR = "短信投注格式错误";
	
	
	
	/**
	 * 分为业务处理部分和短信下发部分
	 * 业务处理必须得到结果，并根据结果下发短信
	 * 短信必须发送，仅当MT放入队列出错时会无法发送
	 * 如果业务处理和短信下发都成功，则返回null
	 * 如果业务处理或短信下发有一种失败，则返回 以'&&' 分隔的字符串
	 * 前半部分为MO业务处理失败的错误信息，如成功则是空字符串；
	 * 后半部分为短信下发处理失败的错误信息，如果成功则是空字符串
	 * 如果返回 && 分隔的两个字符串，则认为都成功。
	 * @param cmd: 短信指令
	 * @param mo: MO上行短信；
	 * 		系统借用了MO.getOutTime()获得的是MOProcessor获得该MO的时间，下发短信时必须设置MT的inTime=MO.getOutTime;
	 * 		还需要使用MT.setProcessorName(mo.getProcessorName);来设置该条MT是由哪个Processor生成的
	 * @return: 
	 */
	public abstract String process(String cmd, MO mo);
	
	public static String string2Ascii(String sour){
		if(sour.trim().matches("([a-zA-Z0-9])*")){
			return sour.trim().toUpperCase();
		}
		char[] chars = sour.toCharArray();
		String rs = "";
		for(char c : chars){
			rs = rs + Integer.toHexString((int)c);
		}
		return rs.toUpperCase();
	}

	public static SMSService getProcessor(String cmd) {
		Log logger = LogFactory.getLog(SMSService.class.getName());
		SMSService service = null;
		String className = "com.success.lottery.sms.process.SMSService" + string2Ascii(cmd);
		try{
			service = (SMSService)Class.forName(className).newInstance();
		}catch(Exception e){
			logger.debug("getProcessor for " + cmd + " exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		if(service == null){
			try{
				service = (SMSService)Class.forName(className, true, Thread.currentThread().getContextClassLoader()).newInstance();
			}catch(Exception e){
				logger.error("getProcessor for " + cmd + " exception:" + e.toString());
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
			}
		}
		return service;
	}

	public static void main(String[] args){
		if(args == null || args.length < 1 || args[0] == null || "".equals(args[0].trim())){
			System.out.println("Usage: java com.success.lottery.sms.process.SMSService string");
			return;
		}
		System.out.println(args[0] + " to Hex ascii :" + string2Ascii(args[0]));
		System.out.println(string2Ascii("AAA"));
		System.out.println(string2Ascii("?"));
		System.out.println(string2Ascii("？"));
		System.out.println(string2Ascii("新年快乐"));
		System.out.println(string2Ascii("?？"));
		System.out.println(string2Ascii("3"));
		System.out.println(string2Ascii("dlT"));
	}
}
