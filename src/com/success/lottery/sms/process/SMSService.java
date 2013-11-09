package com.success.lottery.sms.process;

import java.io.UnsupportedEncodingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.sms.MO;


public abstract class SMSService{

	public final static int SMSFORMATERROR = 880001;
	public final static String SMSFORMATERROR_STR = "����Ͷע��ʽ����";
	
	
	
	/**
	 * ��Ϊҵ�����ֺͶ����·�����
	 * ҵ�������õ�����������ݽ���·�����
	 * ���ű��뷢�ͣ�����MT������г���ʱ���޷�����
	 * ���ҵ����Ͷ����·����ɹ����򷵻�null
	 * ���ҵ���������·���һ��ʧ�ܣ��򷵻� ��'&&' �ָ����ַ���
	 * ǰ�벿��ΪMOҵ����ʧ�ܵĴ�����Ϣ����ɹ����ǿ��ַ�����
	 * ��벿��Ϊ�����·�����ʧ�ܵĴ�����Ϣ������ɹ����ǿ��ַ���
	 * ������� && �ָ��������ַ���������Ϊ���ɹ���
	 * @param cmd: ����ָ��
	 * @param mo: MO���ж��ţ�
	 * 		ϵͳ������MO.getOutTime()��õ���MOProcessor��ø�MO��ʱ�䣬�·�����ʱ��������MT��inTime=MO.getOutTime;
	 * 		����Ҫʹ��MT.setProcessorName(mo.getProcessorName);�����ø���MT�����ĸ�Processor���ɵ�
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
		System.out.println(string2Ascii("��"));
		System.out.println(string2Ascii("�������"));
		System.out.println(string2Ascii("?��"));
		System.out.println(string2Ascii("3"));
		System.out.println(string2Ascii("dlT"));
	}
}
