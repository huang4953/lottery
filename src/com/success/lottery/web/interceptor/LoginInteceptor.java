package com.success.lottery.web.interceptor;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.web.enumerate.AjaxResultStatusType;
import com.success.lottery.web.formbean.JsonMsgBean;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;

/**    
 * @{#} LoginInteceptor.java Create on Apr 10, 2010 2:42:23 PM    
 *    
 * Copyright (c) 2010 by success.    

 * @author Gavin

 * @version 1.0 

 * @description  此类提供验证用户是否登录
   

 */ 
@SuppressWarnings("serial")
public class LoginInteceptor extends AbstractInterceptor {
	private String isAjax="false";
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		LotteryWebBaseActon action = (LotteryWebBaseActon) invocation.getAction();
		if(null == action.getCurCustomer()){
			action.setJsonString(JsonMsgBean.getResultStatusJsonStrByType(AjaxResultStatusType._00000));
			if(Boolean.valueOf(isAjax))//ajax异步请求 处理
				return LotteryWebBaseActon.AJAXJSON;
			else
			{
				//FORM请求 处理
				System.out.println(LotteryWebBaseActon.LOGIN);
				return LotteryWebBaseActon.LOGIN;
			}
		}
		return invocation.invoke();
	}
	public void setIsAjax(String isAjax) {
		this.isAjax = isAjax;
	}
	
}
