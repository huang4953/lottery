package com.success.lottery.web.trade.action.supper;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.exception.LotteryException;
import com.success.utils.ApplicationContextUtils;

/**    
 * @{#} LotteryWebBaseActon.java Create on Mar 24, 2010 4:20:21 PM    
 *    
 * Copyright (c) 2010 by success.    

 * @author Gavin

 * @version 1.0 

 * @description  购彩网WebUi的action父类
   
 */ 
@SuppressWarnings("serial")
public class LotteryWebBaseActon extends ActionSupport implements SessionAware{

	public static final String REDIRECT = "redirect";
	
	public static final String FAILURE = "failure";
	
	public static final String VIEW = "view";
	
	public static final String INDEX = "index";
	
	public static final String AJAXJSON = "ajaxjson";
	
	public static final String AJAXJSONTERM = "ajaxjsonterm";
	
	public static final String GOBAL_SUCCESS = "gobalSuccess";
	
	public static final String SESSION_CUSTOMER_KEY = "customer";
	
	//Session
	private Map<Object, Object> session;
	
	private String action = "index";
	
	private String jsonString;
	
	private String remoteIp;
	
	@SuppressWarnings("unchecked")
	protected String executeMethod(String method) throws Exception {
		Class[] c = null;
		Method m = this.getClass().getMethod(method, c);
		Object[] o = null;
		String result = (String) m.invoke(this, o);
		return result;
	}
	
	public String execute() {
		try {
			return this.executeMethod(this.getAction());
		} catch (Throwable e) {
			e.printStackTrace();
			this.addActionError(this.getText("error.message"));
			return ERROR;
		}
	}
	
	public  UserAccountModel getCurCustomer(){
		UserAccountModel customer=(UserAccountModel) getSession().get(SESSION_CUSTOMER_KEY);
		AccountService accountService =  ApplicationContextUtils.getService("accountService",AccountService.class);
		
		if(customer!=null){
			try {
				customer = accountService.getUserInfo(customer.getUserId());
				
			} catch (LotteryException e) {
				System.out.println(e.getMessage());
				customer=null;
			}
			saveCurCustomer(customer);
		}
		return customer;
	}
	
	/**
	 * 便利方法，得到当前 request
	 * 
	 * @return current request
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 便利方法，得到当前 response
	 * 
	 * @return current response
	 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	protected  void saveCurCustomer(UserAccountModel customer){
		getSession().put(SESSION_CUSTOMER_KEY,customer);
	}
	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map session) {
		this.session = session;
		
	}

	public Map<Object, Object> getSession() {
		return session;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	
}
