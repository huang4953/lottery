package com.success.lottery.web.trade.action;

import net.sf.json.JSONArray;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.web.enumerate.AjaxResultStatusType;
import com.success.lottery.web.formbean.JsonMsgBean;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;
import com.success.utils.ApplicationContextUtils;

@SuppressWarnings("serial")
public class LotteryTermAction extends LotteryWebBaseActon {
	private String type;
	private int num;

	public String history() {
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		try {
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg( AjaxResultStatusType._0000, JSONArray.fromObject(
					termservice.getTermnos(Integer.parseInt(type), num)).toString()));
		} catch (LotteryException e) {
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg(
					AjaxResultStatusType._10000, e.getMessage()));
		}
		return AJAXJSON;
	}
	public String future() {
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		try {
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg( AjaxResultStatusType._0000, JSONArray.fromObject(
					termservice.queryCanAddTermNo(Integer.parseInt(type), num)).toString()));
		} catch (LotteryException e) {
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg(
					AjaxResultStatusType._10000, e.getMessage()));
		}
		return AJAXJSON;
	}
	public String all(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		try {
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg( AjaxResultStatusType._0000, JSONArray.fromObject(
					termservice.getTermnos(Integer.parseInt(type), -1)).toString()));
		} catch (LotteryException e) {
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg(
					AjaxResultStatusType._10000, e.getMessage()));
		}
		return AJAXJSON;
	}
	public String haveDispatch() {
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		try {
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg( AjaxResultStatusType._0000, JSONArray.fromObject(
					termservice.queryHaveDispathTermNos(Integer.parseInt(type), num)).toString()));
		} catch (LotteryException e) {
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg(
					AjaxResultStatusType._10000, e.getMessage()));
		}
		return AJAXJSON;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
