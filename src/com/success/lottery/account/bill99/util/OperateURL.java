package com.success.lottery.account.bill99.util;

public class OperateURL {
	//��ʼlasturl��ַΪ�ĵ���Ҫ���ύ�ĵ�ַ
	public static String startUrl;

	/** 
	 * ���ܺ�������ȡ����
	 * ������е��ύ���� 
	 * */
	public static StringBuffer appendParamU(StringBuffer url, String paramId,
			String paramValue) {
		if (url==null) {
			url=new StringBuffer();
			url.append(startUrl).append("?").append(paramId).append("=").append(paramValue);
		}else{
			url.append("&").append(paramId).append("=").append(paramValue);
		}
		return url;
	}
}
