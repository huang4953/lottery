package com.success.lottery.web.customer.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.struts2.ServletActionContext;

import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.web.enumerate.AjaxResultStatusType;
import com.success.lottery.web.formbean.JsonMsgBean;
import com.success.lottery.web.security.jcaptcha.CaptchaServiceSingleton;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;
import com.success.utils.ApplicationContextUtils;

/**    
 * @{#} ClientLoginAction.java Create on May 12, 2010 6:13:21 PM    
 *    
 * Copyright (c) 2010 by success.    

 * @author Gavin

 * @version 1.0 

 * @description  �û���¼���
 */ 
@SuppressWarnings("serial")
public class ClientLoginAction  extends LotteryWebBaseActon{

	private String nickName;
	private String password;
	private String verifyCode;
	private String infor;
	private String showMoney;
	private String email;
	private String id;
	private String menuCount;
	private String mobilePhone;
	private UserAccountModel customer;
	private String gobillUrl;//

	public String getGobillUrl() {
		return gobillUrl;
	}
	public void setGobillUrl(String gobillUrl) {
		this.gobillUrl = gobillUrl;
	}
	
	public String userMoney(){
		return "userMoney";
	}
	public String index(){
		UserAccountModel useraccount=getCurCustomer();
		if(null==useraccount||useraccount.getUserId()==0)
			return "loginout";
		else
			return "index";
	}
	public String register(){
		return SUCCESS;
	}
	public String regsub() throws UnsupportedEncodingException{
		if(!CaptchaServiceSingleton.getInstance().validateResponseForID(getRequest().getSession().getId(), verifyCode)){
    		infor="��֤�벻��ȷ";
    		return "INPUT";
    	}
		AccountService accountService =  ApplicationContextUtils.getService("accountService",AccountService.class);
		this.getRequest().setCharacterEncoding("GBK");
		try {
			customer=accountService.registerByWeb(this.getNickName(), this.getMobilePhone(), this.getNickName(), password, email);
			customer = accountService.login(nickName, password, getRemoteIp());
			saveCurCustomer(customer);
			
			this.getRequest().getSession().setAttribute("eml", this.getEmail());
			return "validateEml";
		} catch (LotteryException e) {
			infor=e.getMessage();
			return INPUT;
		}
	}
	public void flgOnlylabeled() throws IOException{
		AccountService accountService =  ApplicationContextUtils.getService("accountService",AccountService.class);
		this.getRequest().setCharacterEncoding("GBK");
		PrintWriter out=this.getResponse().getWriter();
		System.out.println(id);
		out.println(accountService.flgOnlylabeled(id));
		out.close();
	}
	public String checkLogin(){
		customer = getCurCustomer();
		this.getSession().put("customer", null);
		return "loginout";
	}
	//��ת��¼
    public String Jumplogin(){
    	return "Jumplogin";
    }
	public String showHead(){
		return "showHead";
	}
	//�жϵ�¼
	public void judgmentLogin() throws IOException{
		this.getResponse().setContentType( "text/html;charset=GBK "); 
		PrintWriter out=this.getResponse().getWriter();
		customer=this.getCurCustomer();
		if(customer==null||customer.getUserId()==0)
				out.println(0);//��¼��ʱ
		else
				out.println(1);//��Ч�ĵ�¼
		out.close();
		
	}
    public String loginout(){
    	getSession().put(SESSION_CUSTOMER_KEY,null);
    	infor="";
    	super.setJsonString(JsonMsgBean.getResultStatusJsonStrByType(AjaxResultStatusType._0000));
		return AJAXJSON;
	}
    public String outLogin(){
    	getSession().put(SESSION_CUSTOMER_KEY,null);
    	return "index";
    }
    public void remoreCustomer() throws IOException{
		//���ñ����ʽ
		this.getResponse().setContentType( "text/html;charset=GBK "); 
		PrintWriter out=this.getResponse().getWriter();
		getSession().put(SESSION_CUSTOMER_KEY,null);
		out.println("1");
//		out.flush();
//		out.close();
	}
    public String ajaxloginout(){
    	getSession().put(SESSION_CUSTOMER_KEY,null);
    	infor="";
    	super.setJsonString(JsonMsgBean.getResultStatusJsonStrByType(AjaxResultStatusType._0000));
		return AJAXJSON;
	}
    public String login(){
    		if(this.getCurCustomer()!=null&&this.getCurCustomer().getUserId()!=0)
	    	  return "loginout";
	      	if((this.verifyCode==null||this.verifyCode.equals(""))&&(this.getNickName()==null||"".equals(this.getNickName()))&&(this.getPassword()==null||this.getPassword().equals("")))
	    	  return "loginin";
	      	try {
	      		if(!CaptchaServiceSingleton.getInstance().validateResponseForID(getRequest().getSession().getId(), verifyCode)){
		    		infor="";
		    		return "loginin";
		    	}
			} catch (Exception e) {
				return "loginin";	
			}
	    	AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
			try {//"admin1", "admin"
				customer = userservice.login(nickName, password, getRemoteIp());
				
				if(null != customer){
	    			saveCurCustomer(customer);
				}
	    		else{
		    		infor="�˻��������";
		    		return "loginin";
		    	}
			} catch (LotteryException e) {
				infor="�˻��������";
				return "loginin";
			}
    	return "loginout";
    }
    public void showMoney() throws IOException{
    	ServletActionContext.getRequest().getSession().setAttribute("showMoney",showMoney);
    }
    
    //���ò˵���Ч��
    public void showMenu(){
    	ServletActionContext.getRequest().getSession().setAttribute("menuCount",menuCount);
    }
    
    
    public void getMoney()throws IOException{
    	this.getResponse().setContentType( "text/html;charset=GBK ");
		PrintWriter out=this.getResponse().getWriter();
    		out.println((this.getCurCustomer().getFundsAccount()+this.getCurCustomer().getPrizeAccount())*1.0/100);
    	out.close();
    }
    public void newAjaxLogin() throws IOException{
    	this.getResponse().setContentType( "text/html;charset=GBK ");
		PrintWriter out=this.getResponse().getWriter();
		//this.getRequest().setCharacterEncoding("UTF-8");
    	if(!CaptchaServiceSingleton.getInstance().validateResponseForID(getRequest().getSession().getId(), verifyCode)){
    		//��֤�벻��ȷ
    		out.println(10000);
    	}else
    	{
    		AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
    		try {//"admin1", "admin"
    			customer = userservice.login(URLDecoder.decode(this.getNickName(),"UTF-8"), password, getRemoteIp());
    			if(null != customer){
    				saveCurCustomer(customer);
    				out.println(20000);
    			}
    			else{
    				//�û��������벻��ȷ
    				out.println(30000);
    			}
    		} catch (LotteryException e) {
    			//����δ֪�쳣�������µ�¼�����ٴε�¼���ɹ�������ϵ�ͷ�
    			out.println(e.getMessage());
    		}
    	}
    	out.close();
    }
    public String ajaxLogin(){
    	if(!CaptchaServiceSingleton.getInstance().validateResponseForID(getRequest().getSession().getId(), verifyCode)){
    		super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg(AjaxResultStatusType._0001,"��֤�벻��ȷ"));
    		return AJAXJSON;
    	}
    	AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
		try {//"admin1", "admin"
			customer = userservice.login(nickName, password, getRemoteIp());
			if(null != customer)
    			saveCurCustomer(customer);
	    	else{
	    		super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg(AjaxResultStatusType._0001,"�˻��������"));
	    		return AJAXJSON;
	    	}
		} catch (LotteryException e) {
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg(AjaxResultStatusType._10000,e.getMessage()));
			return AJAXJSON;
		}
		super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg(AjaxResultStatusType._0000, customer.getNickName()));
		return AJAXJSON;
    }
    //����û���
    public void getUName() throws IOException{
    	PrintWriter out=this.getResponse().getWriter();
    	this.getResponse().setCharacterEncoding("GB2312");
    	System.out.println("======= �㵽����ʲô��ʽ");
    	System.out.println(this.getCurCustomer().getLoginName());
    	out.println(this.getCurCustomer().getLoginName());
    	System.out.println(111111);
    	out.close();
    }
	public String getInfor() {
		return infor;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public UserAccountModel getCustomer() {
		return customer;
	}

	public String getPassword() {
		return password;
	}

	public String getNickName() {
		return nickName;
	}
	public void setShowMoney(String showMoney) {
		this.showMoney = showMoney;
	}
	public void setMenuCount(String menuCount) {
		this.menuCount = menuCount;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
}
