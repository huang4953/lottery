package com.success.lottery.web.trade.action;

import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.business.service.interf.BetServiceInterf;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.web.enumerate.AjaxResultStatusType;
import com.success.lottery.web.formbean.JsonMsgBean;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;
import com.success.utils.ApplicationContextUtils;


/**    
 * @{#} LotteryTradeAction.java Create on Mar 24, 2010 4:20:08 PM    
 *    
 * Copyright (c) 2010 by success.    

 * @author Gavin

 * @version 1.0 

 * @description  �û�������Action

 */ 
@SuppressWarnings("serial")
public class LotteryTradeAction extends LotteryWebBaseActon{
	
	private String term;
	private BigDecimal amount;
	private String codes;
	private Integer multiple;
	private String lotteryType;
	private String playType;
	private int betType;
	private String iszj="false";
	private String planOpenType;//��������״̬
	private String totalUnit;//�ܷ���
	private String unitPrice;//ÿ�ݵ���
	private String selfBuyNum;//�Լ��Ϲ��ķݶ�
	private String unitBuySelf;//���׷ݶ�
	private String commisionPercent;//Ӷ�����
	private String planTitle;//��������
	private String planDescription;//��������
	private String planId;//����ID 
	private String cpUnit;//����ݶ�
	
	private String winStopped="false";
	//׷������
	private String [] terms;
	//׷�ű���
	private Integer [] multiples;
	//ÿ�ڽ��
	private BigDecimal [] amounts;
	
	public String index(){
		UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
		BetServiceInterf betService = ApplicationContextUtils.getService("busLotteryBetService",BetServiceInterf.class);
		
		try {
			betService.betWeb(customer.getUserId(), Integer.parseInt(lotteryType),Integer.parseInt(playType), betType, multiple, new Long(-1), amount.longValue(), term, null, Boolean.valueOf(winStopped), codes);
		} catch (LotteryException e) {
			
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg(AjaxResultStatusType._10000,e.getMessage()));
			return AJAXJSON;
			
		}
		
		super.setJsonString(JsonMsgBean.getResultStatusJsonStrByType(AjaxResultStatusType._0000));
		return AJAXJSON;
	}
	public String chase(){
		UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
		System.out.println("--------׷��---------");
		BetServiceInterf betService = ApplicationContextUtils.getService("busLotteryBetService",BetServiceInterf.class);
		try {
			betService.betWeb(customer.getUserId(), Integer.parseInt(lotteryType),Integer.parseInt(playType), betType, multiple, new Long(-1), amount.longValue(), term, toMap(), Boolean.valueOf(winStopped), codes);
		} catch (LotteryException e) {
			
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg(AjaxResultStatusType._10000,e.getMessage()));
			return AJAXJSON;
			
		}
		super.setJsonString(JsonMsgBean.getResultStatusJsonStrByType(AjaxResultStatusType._0000));		
		return AJAXJSON;
	}
	public String coopBetCreate() throws UnsupportedEncodingException{
		String title="";
		String body="";
		//���ñ����ʽ
		System.out.println("--------����---------");
		if(Integer.parseInt(lotteryType)==1300001&&codes.length()==27)
			betType=0;
		if(Integer.parseInt(lotteryType)==1300002&&codes.length()==17)
			betType=0;
		if(Integer.parseInt(lotteryType)==1300003&&codes.length()==23)
			betType=0;
		if(Integer.parseInt(lotteryType)==1300004&&codes.length()==15)
			betType=0;
		if(Integer.parseInt(lotteryType)==1300001||Integer.parseInt(lotteryType)==1300002||Integer.parseInt(lotteryType)==1300003||Integer.parseInt(lotteryType)==1300004)
		{
			if(betType==0)
				this.setCommisionPercent("4");
			title=URLDecoder.decode(this.getPlanTitle(),"UTF-8");
			body= URLDecoder.decode(getPlanDescription(),"UTF-8");
		}else
		{
			body=new String (this.getPlanDescription().getBytes("ISO-8859-1"),"UTF-8");
			title=new String (this.getPlanTitle().getBytes("ISO-8859-1"),"UTF-8");
		}
		UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
		BetServiceInterf betService = ApplicationContextUtils.getService("busLotteryBetService",BetServiceInterf.class);
		try{
			betService.coopBetCreate(customer.getUserId(), Integer.parseInt(lotteryType), Integer.parseInt(playType), betType, multiple, new Long(-1), -1, term, codes,Integer.parseInt(planOpenType) , Integer.parseInt(totalUnit), Integer.parseInt(unitPrice)*100, Integer.parseInt(selfBuyNum),Integer.parseInt(unitBuySelf) ,Integer.parseInt(commisionPercent) , 0,title , body);
		}catch(LotteryException e){
			
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg(AjaxResultStatusType._10000,e.getMessage()));
			return AJAXJSON;
		}
		super.setJsonString(JsonMsgBean.getResultStatusJsonStrByType(AjaxResultStatusType._0000));		
		return  AJAXJSON;
	}
	public String coopBetJoin(){
		System.out.println("-------------------�������---------------------------");
		BetServiceInterf betService = ApplicationContextUtils.getService("busLotteryBetService",BetServiceInterf.class);
		UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
		if(null==customer||customer.getUserId()==0)
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByType(AjaxResultStatusType._00000));
		else
		{
			try {
				betService.coopBetJoin(customer.getUserId(), planId, Integer.parseInt(cpUnit), Integer.parseInt(unitPrice)*Integer.parseInt(cpUnit));
			} catch (NumberFormatException e) {
				super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg(AjaxResultStatusType._10000,e.getMessage()));
				return AJAXJSON;
			} catch (LotteryException e) {
				super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg(AjaxResultStatusType._10000,e.getMessage()));
				return AJAXJSON;
			}
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByType(AjaxResultStatusType._0000));	
		}
		return AJAXJSON;
	}
	
	public String revocateCreatePlan(){
		BetServiceInterf betService = ApplicationContextUtils.getService("busLotteryBetService",BetServiceInterf.class);
		UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
		if(null==customer||customer.getUserId()==0)
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByType(AjaxResultStatusType._00000));
		else
		{
			try {
				betService.revocateCreatePlan(customer.getUserId(), planId);
				super.setJsonString(JsonMsgBean.getResultStatusJsonStrByType(AjaxResultStatusType._0000));
			} catch (LotteryException e) {
				super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg(AjaxResultStatusType._10000,e.getMessage()));
			}
		}
		return AJAXJSON;
	}
	
	public String revocateJionOrder(){
		BetServiceInterf betService = ApplicationContextUtils.getService("busLotteryBetService",BetServiceInterf.class);
		UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
		if(null==customer||customer.getUserId()==0)
			super.setJsonString(JsonMsgBean.getResultStatusJsonStrByType(AjaxResultStatusType._00000));
		else
		{
			try {
				betService.revocateJionOrder(customer.getUserId(),planId);
				super.setJsonString(JsonMsgBean.getResultStatusJsonStrByType(AjaxResultStatusType._0000));
			} catch (LotteryException e) {
				System.out.println("-===========================");
				System.out.println(customer.getUserId());
				System.out.println(planId);
				super.setJsonString(JsonMsgBean.getResultStatusJsonStrByTypeAndMsg(AjaxResultStatusType._10000,e.getMessage()));
			}
		}
		return AJAXJSON;
		
	}
	private Map<String,Integer> toMap(){
		Map<String,Integer> map = new HashMap<String,Integer>(terms.length);
		for(int i=0;i<terms.length;i++)	{
			//terms
			map.put(terms[i], multiples[i]);
		}
		return map;
	}
	public String together(){
		
		return AJAXJSON;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public void setTerms(String[] terms) {
		this.terms = terms;
	}
	public void setMultiples(Integer[] multiples) {
		this.multiples = multiples;
	}
	public void setAmounts(BigDecimal[] amounts) {
		this.amounts = amounts;
	}
	public void setIszj(String iszj) {
		this.iszj = iszj;
	}
	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}
	public void setPlayType(String playType) {
		this.playType = playType;
	}
	public void setWinStopped(String winStopped) {
		this.winStopped = winStopped;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	public void setBetType(int betType) {
		this.betType = betType;
	}
	public void setPlanOpenType(String planOpenType) {
		this.planOpenType = planOpenType;
	}
	public void setTotalUnit(String totalUnit) {
		this.totalUnit = totalUnit;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setSelfBuyNum(String selfBuyNum) {
		this.selfBuyNum = selfBuyNum;
	}
	public void setUnitBuySelf(String unitBuySelf) {
		this.unitBuySelf = unitBuySelf;
	}
	public void setCommisionPercent(String commisionPercent) {
		this.commisionPercent = commisionPercent;
	}
	public void setPlanTitle(String planTitle) {
		this.planTitle = planTitle;
	}
	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
	}
	public String getPlanTitle() {
		return planTitle;
	}
	public String getPlanDescription() {
		return planDescription;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public void setCpUnit(String cpUnit) {
		this.cpUnit = cpUnit;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public String getPlanId() {
		return planId;
	}
	public String getCpUnit() {
		return cpUnit;
	}
}
