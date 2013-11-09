package com.success.lottery.account.bill99.util;

import java.io.UnsupportedEncodingException;

import com.success.lottery.account.bill99.encrypt.util.MD5Util;


/**
 * @Description: 快钱人民币支付网关接口范例
 * @Copyright (c) 上海快钱信息服务有限公司
 * @version 2.0
 * 商户端提交参数操作类
 */

public class ParamOper {

	/**
	 * 函数：加密编码
	 * 对得到的签名字符串进行加密，并且返回加密后的signMsg
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
	 * 功能函数：
	 * 将变量值不为空的参数组成字符串 
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
