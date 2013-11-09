package com.success.lottery.account.bill99.util;

public class OperateURL {
	//初始lasturl地址为文档中要求提交的地址
	public static String startUrl;

	/** 
	 * 功能函数：获取链接
	 * 组合所有的提交参数 
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
