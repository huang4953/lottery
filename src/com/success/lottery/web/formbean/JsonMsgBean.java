package com.success.lottery.web.formbean;

import net.sf.json.JSONObject;

import com.success.lottery.web.enumerate.AjaxResultStatusType;

/**
 * @{#} JsonMsgBean.java Create on Apr 9, 2010 1:53:36 PM
 * 
 * Copyright (c) 2010 by success.
 * 
 * @author Gavin
 * 
 * @version 1.0
 * 
 * @description 此类提供了对ajax请求返回结构的封装
 * 
 * 
 */
public class JsonMsgBean {

	public static String RESULT_STATUS_STR = "status";
	public static String RESULT_MSG_STR = "message";

	public static String getResultStatusJsonStrByType(
			AjaxResultStatusType statusType) {
		JSONObject objec = new JSONObject();
		objec.put(RESULT_STATUS_STR, statusType.name());
		return objec.toString();
	}

	public static String getResultStatusJsonStrByTypeAndMsg(AjaxResultStatusType statusType, String msg) {
		JSONObject object = new JSONObject();
		object.put(RESULT_STATUS_STR, statusType.name());
		object.put(RESULT_MSG_STR, msg);
		return object.toString();
	}

}
