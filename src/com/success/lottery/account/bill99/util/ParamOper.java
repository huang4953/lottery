package com.success.lottery.account.bill99.util;

import java.io.UnsupportedEncodingException;

import com.success.lottery.account.bill99.encrypt.util.MD5Util;


/**
 * @Description: ��Ǯ�����֧�����ؽӿڷ���
 * @Copyright (c) �Ϻ���Ǯ��Ϣ�������޹�˾
 * @version 2.0
 * �̻����ύ����������
 */

public class ParamOper {

	/**
	 * ���������ܱ���
	 * �Եõ���ǩ���ַ������м��ܣ����ҷ��ؼ��ܺ��signMsg
	 * */
	public static String signMsg(String signMsgVal,String charsetName) {
		try {
			return MD5Util.md5Hex(signMsgVal.getBytes(charsetName)).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	 * ���ܺ�����
	 * ������ֵ��Ϊ�յĲ�������ַ��� 
	 * */
	public static StringBuffer appendParam(StringBuffer returnStr, String paramId,
			String paramValue) {
		if(paramValue==null){
			paramValue="";
		}
		if(returnStr==null){
			returnStr=new StringBuffer();
			if(!"".equals(paramValue)){
				returnStr.append(paramId).append("=").append(paramValue);
			}
		}else{
			if(!"".equals(paramValue)){
				returnStr.append("&").append(paramId).append("=").append(paramValue);
			}
		}
		return returnStr;
	}
}
