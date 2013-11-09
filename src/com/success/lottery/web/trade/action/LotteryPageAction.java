package com.success.lottery.web.trade.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.business.domain.BusCoopPlanDomain;
import com.success.lottery.business.domain.BusCpInfoDomain;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetPlanDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.web.formbean.Page;
import com.success.lottery.web.formbean.RaceBean;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.DateUtil;

/**
 * @{#} LotteryAction.java Create on Mar 25, 2010 7:53:51 PM
 * 
 * Copyright (c) 2010 by success.
 * 
 * @author Gavin
 * 
 * @version 1.0
 * 
 * @description 根据访问不同的彩种返回不同的投注页面
 */
@SuppressWarnings("serial")
public class LotteryPageAction extends LotteryWebBaseActon {
	private String type;
	private Map<String, Integer> yiLoumap;
	private LotteryTermModel currentTerm,historyTerm;
	private List<LotteryTermModel> historyTermList;//历史开奖期数
	private List<String[]> WinResultList; 
	private List<BusCoopPlanDomain>  coopList;
	private BusCoopPlanDomain coopPlan;//
	private String termNo;
	private List<BusCpInfoDomain> cpInfoList;
	private BetPlanDomain betPlan;
	private String [] betCodelist;
	private Page page;
	private String lotteryId;
	private String pageNo;//第几页
	private String planid;
	private int count;//参与合买人数
	private String userIdentify;//用户表示  昵称  电话号码
	private String SchemeAmount;//方案金额范围
	private String commissionStr;//提成比例范围
	private String planProgress;//方案状态 方案进度 0-满员，1-未满员，-1标识全部的
	public String getPlanProgress() {
		return planProgress;
	}
	public void setPlanProgress(String planProgress) {
		this.planProgress = planProgress;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String home() {
		setData();
		return SUCCESS;
	}
	public String dlt(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		try {
			currentTerm = termservice.queryTermCurrentInfo(1000001);
			yiLoumap= currentTerm.getLotteryInfo().getMissCountResult();
			historyTermList=termservice.queryHaveWinTermInfo(1000001, 10);
			this.historyTerm=termservice.queryTermLastCashInfo(1000001);
			this.WinResultList=new ArrayList<String[]>();
			Map<String, TreeMap<String, String[]>> superLotteryResult = currentTerm
					.getLotteryInfo().getSuperWinResult();
			String[] happyZodiacWinResult = termservice
					.queryTermCurrentInfo(1000005).getLotteryInfo()
					.getHappyZodiacWinResult();
			for (int i = 1; i < 9; i++)
				WinResultList.add(superLotteryResult.get("" + i).get("A"));
			WinResultList.add(happyZodiacWinResult);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String dltChinppedtube() throws NumberFormatException, LotteryException{
		this.setLotteryId("1000001");
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		this.setTermNo(termNo==null||"".equals(termNo)?termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId))==null?null:termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId)).getTermNo():termNo);
		setChippedtubeDate();
		return SUCCESS;
	}
	public String qxcChinppedtube()throws NumberFormatException, LotteryException{
		this.setLotteryId("1000002");
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		this.setTermNo(termNo==null||"".equals(termNo)?termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId))==null?null:termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId)).getTermNo():termNo);
		setChippedtubeDate();
		return SUCCESS;
	}
	public String plsChinppedtube()throws NumberFormatException, LotteryException{
		this.setLotteryId("1000003");
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		this.setTermNo(termNo==null||"".equals(termNo)?termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId))==null?null:termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId)).getTermNo():termNo);
		setChippedtubeDate();
		return SUCCESS;
	}
	public String plwChinppedtube()throws NumberFormatException, LotteryException{
		this.setLotteryId("1000004");
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		this.setTermNo(termNo==null||"".equals(termNo)?termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId))==null?null:termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId)).getTermNo():termNo);
		setChippedtubeDate();
		return SUCCESS;
	}
	public String rsChinppedtube()throws NumberFormatException, LotteryException{
		this.setLotteryId("1300001");
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		this.setTermNo(termNo==null||"".equals(termNo)?termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId),1)==null?null:termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId),1).get(0).getTermNo():termNo);
		setChippedtubeDate();
		return SUCCESS;
	}
	public String rjChinppedtube()throws NumberFormatException, LotteryException{
		this.setLotteryId("1300002");
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		this.setTermNo(termNo==null||"".equals(termNo)?termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId),1)==null?null:termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId),1).get(0).getTermNo():termNo);
		setChippedtubeDate();
		return SUCCESS;
	}
	public String zc6Chinppedtube()throws NumberFormatException, LotteryException{
		this.setLotteryId("1300003");
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		this.setTermNo(termNo==null||"".equals(termNo)?termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId),1)==null?null:termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId),1).get(0).getTermNo():termNo);
		setChippedtubeDate();
		return SUCCESS;
	}
	public String jq4Chinppedtube()throws NumberFormatException, LotteryException{
		this.setLotteryId("1300004");
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		this.setTermNo(termNo==null||"".equals(termNo)?termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId),1)==null?null:termservice.queryTermCurrentInfo(Integer.parseInt(lotteryId),1).get(0).getTermNo():termNo);
		setChippedtubeDate();
		return SUCCESS;
	}
	public void setChippedtubeDate(){
		if(null == page)
			page = new Page(20);
		else
			page.setPageSize(20);
		System.out.println(getLotteryId());
		if(getUserIdentify()==null||"".equals(getUserIdentify()))
			this.setUserIdentify(null);
		if(getLotteryId()==null||"".equals(getLotteryId()))
			this.setLotteryId("1000001");
		if(null==getCommissionStr()||"".equals(getCommissionStr()))
			setCommissionStr("0@100");
		if(null==getSchemeAmount()||"".equals(getSchemeAmount()))
				setSchemeAmount("-1@-1");
		if(null==getPlanProgress()||"".equals(getPlanProgress()))
			setPlanProgress("-1");
		if(null==getPageNo()||"".equals(getPageNo()))
			setPageNo("1");
		int planMoneyDown=Integer.parseInt(getSchemeAmount().split("@")[0]);
		int planMoneyUp=Integer.parseInt(getSchemeAmount().split("@")[1]);
		int tiChengDown=Integer.parseInt(getCommissionStr().split("@")[0]);
		int tiChengUp=Integer.parseInt(getCommissionStr().split("@")[1]);
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		LotteryManagerInterf managerService=ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
		try {
			currentTerm = termservice.queryTermCurrentInfo(Integer.parseInt(getLotteryId()));
			coopList=managerService.getCoopCanJoinOrder(Integer.parseInt(getLotteryId()), termNo,getUserIdentify(), Integer.parseInt(getPlanProgress()), planMoneyDown, planMoneyUp, tiChengDown, tiChengUp, Integer.parseInt(getPageNo()),20);
			historyTermList=termservice.queryTermCurrentInfo(Integer.parseInt(getLotteryId()),2);
			int count=managerService.getCoopCanJoinOrderCount(Integer.parseInt(getLotteryId()), currentTerm.getTermNo(),getUserIdentify(), Integer.parseInt(getPlanProgress()), planMoneyDown, planMoneyUp, tiChengDown, tiChengUp);
			System.out.println("count:"+count);
			if(null!=coopList){
				page.setTotalCount(count);
				page.setResult(coopList);
			}else
				page.setTotalCount(0);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String dltGoAttend(){
		if(null == page)
			page = new Page(20);
		else
			page.setPageSize(20);
		//读取数据
		if(getUserIdentify()==null||"".equals(getUserIdentify()))
			this.setUserIdentify(null);
		if(getLotteryId()==null||"".equals(getLotteryId()))
			this.setLotteryId("1000001");
		if(null==getCommissionStr()||"".equals(getCommissionStr()))
			setCommissionStr("0@100");
		if(null==getSchemeAmount()||"".equals(getSchemeAmount()))
				setSchemeAmount("-1@-1");
		if(null==getPlanProgress()||"".equals(getPlanProgress()))
			setPlanProgress("-1");
		if(null==getPageNo()||"".equals(getPageNo()))
			setPageNo("1");
		int planMoneyDown=Integer.parseInt(getSchemeAmount().split("@")[0]);
		int planMoneyUp=Integer.parseInt(getSchemeAmount().split("@")[1]);
		int tiChengDown=Integer.parseInt(getCommissionStr().split("@")[0]);
		int tiChengUp=Integer.parseInt(getCommissionStr().split("@")[1]);
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		LotteryManagerInterf managerService=ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
		try {
			currentTerm = termservice.queryTermCurrentInfo(Integer.parseInt(getLotteryId()));
			coopList=managerService.getCoopCanJoinOrder(Integer.parseInt(getLotteryId()), currentTerm.getTermNo(),getUserIdentify(), Integer.parseInt(getPlanProgress()), planMoneyDown, planMoneyUp, tiChengDown, tiChengUp, Integer.parseInt(getPageNo()),20);
			int count=managerService.getCoopCanJoinOrderCount(Integer.parseInt(getLotteryId()), currentTerm.getTermNo(),getUserIdentify(), Integer.parseInt(getPlanProgress()), planMoneyDown, planMoneyUp, tiChengDown, tiChengUp);
			
			if(null!=coopList){
				page.setTotalCount(count);
				page.setResult(coopList);
			}else
				page.setTotalCount(0);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String syxw(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		try {
			currentTerm = termservice.queryTermCurrentInfo(1200001);
			historyTermList=termservice.queryHaveWinTermInfo(1200001, 10);
			this.historyTerm=termservice.queryTermLastCashInfo(1200001);
			yiLoumap= currentTerm.getLotteryInfo().getMissCountResult();
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String pls(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		try {
			currentTerm = termservice.queryTermCurrentInfo(1000003);
			yiLoumap= currentTerm.getLotteryInfo().getMissCountResult();
			this.historyTerm=termservice.queryTermLastCashInfo(1000003);
			this.historyTermList=termservice.queryHaveWinTermInfo(1000003, 10);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String plw(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		try {
			currentTerm = termservice.queryTermCurrentInfo(1000004);
			yiLoumap= currentTerm.getLotteryInfo().getMissCountResult();
			this.historyTerm=termservice.queryTermLastCashInfo(1000004);
			this.historyTermList=termservice.queryHaveWinTermInfo(1000004, 10);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String qxc(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		try {
			currentTerm = termservice.queryTermCurrentInfo(1000002);
			yiLoumap= currentTerm.getLotteryInfo().getMissCountResult();
			this.historyTerm=termservice.queryTermLastCashInfo(1000002);
			this.historyTermList=termservice.queryHaveWinTermInfo(1000002, 10);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//详情 ogetherDetails
    public String ogetherDetails(){
    	Calendar calendar = Calendar.getInstance();
    	LotteryManagerInterf managerService=ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
    	AccountService accountService =  ApplicationContextUtils.getService("accountService",AccountService.class);
    	UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
    	try {
			coopPlan=managerService.getCoopPlanForWebShow(planid);
			coopPlan.setBetCode(managerService.planBetCodeIsShow(planid,customer==null?-1:(int)customer.getUserId()));
			count=managerService.getCoopForWebDetailCount(planid);
			betCodelist=coopPlan.getBetCode().split("\\^");
			coopPlan.setRealName(LotteryStaticDefine.planStatusForWebH.get(coopPlan.getPlanStatus()));
			//根据彩种彩期去找彩期
			//借用字段pageNo 表示十分截止
			if(calendar.after(coopPlan.getStartTerm()))
				pageNo="1";//合买截止之后
			else
				pageNo="0";//尚未截止
			//借用lotteryId 来表示发起人的昵称
			lotteryId=accountService.getUserInfo(coopPlan.getUserId()).getNickName();
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return "ogetherDetails";
    }
    //参与信息
    public String ogetherDatailsList(){
    	//查询所有认购信息
    	if(null==page)
    		page=new Page(10);
    	else
    		page.setPageSize(10);
    	LotteryManagerInterf managerService=ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
    	try {
    		cpInfoList=managerService.getCoopForWebDetail(planid, page.getPageNo() ,10);
    		count=managerService.getCoopForWebDetailCount(planid);
    		if(null!=cpInfoList){
    			page.setTotalCount(count);
			}else
				page.setTotalCount(0);
    		System.out.println(page.getTotalCount());
    	} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "ogetherDatailsList";
    }
    public String ogetherDatailsMyList(){
    	UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
    	LotteryManagerInterf managerService=ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			try {
				if(customer!=null&&customer.getUserId()!=0)
					cpInfoList=managerService.getCoopSelfForWebDetail(planid,(int)customer.getUserId());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LotteryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	return "ogetherDatailsMyList";
    }
	private void setData(){
		/**
		 * 
		 * 根据TYPE
		 * 获取当前期数 termNo=service.gettermno(type);
		 * 
		 * 获取下一期期数 termNo=service.gettermno(type);
		 * 
		 * 获取奖池 poolMoney=
		 * 
		 * 获取开奖时间 openLotteryTime=
		 * 
		 * 获取销售截止时间 stopBetTime=
		 * 
		 * 获取销售截止的时间  以秒为单位  gapStopBetTime=
		 * 
		 * */
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public Map<String, Integer> getYiLoumap() {
		return yiLoumap;
	}
	public List<LotteryTermModel> getHistoryTermList() {
		return historyTermList;
	}
	public LotteryTermModel getCurrentTerm() {
		return currentTerm;
	}
	public List<String[]> getWinResultList() {
		return WinResultList;
	}
	public LotteryTermModel getHistoryTerm() {
		return historyTerm;
	}
	public void setHistoryTerm(LotteryTermModel historyTerm) {
		this.historyTerm = historyTerm;
	}
	public List<BusCoopPlanDomain> getCoopList() {
		return coopList;
	}
	public void setCoopList(List<BusCoopPlanDomain> coopList) {
		this.coopList = coopList;
	}
	public String getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getUserIdentify() {
		return userIdentify;
	}
	public void setUserIdentify(String userIdentify) {
		this.userIdentify = userIdentify;
	}
	public String getSchemeAmount() {
		return SchemeAmount;
	}
	public void setSchemeAmount(String schemeAmount) {
		SchemeAmount = schemeAmount;
	}
	public String getCommissionStr() {
		return commissionStr;
	}
	public void setCommissionStr(String commissionStr) {
		this.commissionStr = commissionStr;
	}
	public void setWinResultList(List<String[]> winResultList) {
		WinResultList = winResultList;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getPlanid() {
		return planid;
	}
	public void setPlanid(String planid) {
		this.planid = planid;
	}
	public BetPlanDomain getBetPlan() {
		return betPlan;
	}
	public void setBetPlan(BetPlanDomain betPlan) {
		this.betPlan = betPlan;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String[] getBetCodelist() {
		return betCodelist;
	}
	public void setBetCodelist(String[] betCodelist) {
		this.betCodelist = betCodelist;
	}
	public List<BusCpInfoDomain> getCpInfoList() {
		return cpInfoList;
	}
	public void setCpInfoList(List<BusCpInfoDomain> cpInfoList) {
		this.cpInfoList = cpInfoList;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public BusCoopPlanDomain getCoopPlan() {
		return coopPlan;
	}
	public void setCoopPlan(BusCoopPlanDomain coopPlan) {
		this.coopPlan = coopPlan;
	}
}
