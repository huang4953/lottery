package com.success.lottery.web.admin.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.domain.BusCoopPlanDomain;
import com.success.lottery.business.domain.BusCpInfoDomain;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.web.admin.webbean.PageList;
import com.success.utils.ApplicationContextUtils;

/**
 * 
 * com.success.lottery.web.admin.action
 * BetCoopInfoAction.java
 * BetCoopInfoAction
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-4-22 下午12:59:31
 *
 */

public class BetCoopInfoAction extends ActionSupport implements SessionAware{
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -2053599861967409948L;
	
	private Map						session;
	
	private int ex_code;
	private String ex_reason;
	/*
	 * 界面初始化参数
	 */
	
	private Map<Integer,String> planStatusList;
	
	private Map<Integer, String> lotteryList;
	
	private Map<Integer,String> coopTypeList;
	
	private Map<String,String> coopStatusList;
	
	private Map<Integer,String> coopWinStatusList;
	/*
	 * 界面回传参数
	 */
	private String planId;
	private String user_account;
	private int planProgress = -1;
	private int planMoney = -1;
	private int tiChengPrec = -1;
	private String p_lotteryId;
	private String p_termNo_begin = "0";
	private String begin_date;
	private String end_date;
	private int p_planStatus = -1;
	private String coopId;
	private String coopType;
	private String p_coopStatus;
	private String winStatus;

	/*
	 * 分页参数
	 */
	private String query;
	
	//每一页的记录数
	private int					perPageNumber;
	//第几页
	private int					page;
	
	private PageList 				orderList = new PageList();
	//private PageList 				orderList;

	//总页数，使用static，避免在分页时重新查询总条数，还是有问题，多人同时查会改变总页数
	//目前每次都查询总条数；
	//可考虑的解决方案：
	//1-总条数放入session； 2-在数据库DAO中判断条件如果和上次相同则不做数据库查询直接返回
	private int totalNumber;
	
	private static int defaultPerPageNumber = 20;
	
	
	/**
	 * 
	 * Title: betOrderInfoShow<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @return String
	 */
	public String betPlanInfoShow(){
		
		try {
			this.initPageParam();
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			List<BusCoopPlanDomain> retPlanList = null;
			if (this.page == 0) {
				this.page = 1;
			}
			if (this.perPageNumber == 0) {
				this.perPageNumber = defaultPerPageNumber;
			}
			int planMoneyDown = -1;
			int planMoneyUp = -1;
			switch (this.getPlanMoney()) {
			case -1:
				planMoneyDown = -1;
				planMoneyUp = -1;
				break;
			case 0:
				planMoneyDown = 0;
				planMoneyUp = 100;
				break;
			case 1:
				planMoneyDown = 100;
				planMoneyUp = 500;
				break;
			case 2:
				planMoneyDown = 500;
				planMoneyUp = 1000;
				break;
			case 3:
				planMoneyDown = 1000;
				planMoneyUp = -1;
				break;
			default:
				planMoneyDown = -1;
				planMoneyUp = -1;
				break;
			}
			
			int tiChengDown = -1;
			int tiChengUp = -1;
			switch (this.getTiChengPrec()) {
			case -1:
				tiChengDown = -1;
				tiChengUp = -1;
				break;
			case 0:
				tiChengDown = 0;
				tiChengUp = 1;
				break;
			case 1:
				tiChengDown = 1;
				tiChengUp = 5;
				break;
			case 2:
				tiChengDown = 5;
				tiChengUp = -1;
				break;
			default:
				tiChengDown = -1;
				tiChengUp = -1;
				break;
			}
			
			this.totalNumber = termManager.getCoopPlansCount(Integer.parseInt(StringUtils.isEmpty(this.getP_lotteryId())?"0":this.getP_lotteryId()),
					this.getP_termNo_begin(),
					this.getPlanId(), this.getUser_account(), this.getPlanProgress(), planMoneyDown,
					planMoneyUp, tiChengDown, tiChengUp, this.getBegin_date(), this.getEnd_date(),
					this.getP_planStatus());
			
			retPlanList = termManager.getCoopPlans(Integer.parseInt(StringUtils.isEmpty(this.getP_lotteryId())?"0":this.getP_lotteryId()), 
					this.getP_termNo_begin(),
					this.getPlanId(), this.getUser_account(), this.getPlanProgress(), planMoneyDown,
					planMoneyUp, tiChengDown, tiChengUp, this.getBegin_date(), this.getEnd_date(),
					this.getP_planStatus(),
					this.page, this.perPageNumber);

			this.orderList.setPageNumber(this.page);
			this.orderList.setPerPage(this.perPageNumber);
			this.orderList.setFullListSize(this.totalNumber);
			this.orderList.setList(retPlanList);
		} catch(LotteryException e){
			e.printStackTrace();
			this.setEx_code(e.getType());
			this.setEx_reason(e.getMessage());
			return ERROR; 
		}catch(Exception ex){
			ex.printStackTrace();
			this.setEx_code(99999);
			this.setEx_reason(ex.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String betCoopCanYUInfoShow(){
		
		try {
			this.initPageParam();
			this.setCoopTypeList(LotteryStaticDefine.coopOrderType);
			this.setCoopStatusList(LotteryStaticDefine.coopOrderStatus);
			this.setCoopWinStatusList(LotteryStaticDefine.coopWinStatus);
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			List<BusCpInfoDomain> retPlanList = null;
			if (this.page == 0) {
				this.page = 1;
			}
			if (this.perPageNumber == 0) {
				this.perPageNumber = defaultPerPageNumber;
			}
			
			this.totalNumber = termManager.getCoopCanYuInfosCount(this.getP_lotteryId(), this.getP_termNo_begin(),
					this.getPlanId(), this.getCoopId(), this.getUser_account(), this.getBegin_date(), this.getEnd_date(),
					this.getCoopType(), this.getP_coopStatus(), this.getWinStatus());
			
			retPlanList = termManager.getCoopCanYuInfos(this.getP_lotteryId(), this.getP_termNo_begin(),
					this.getPlanId(), this.getCoopId(), this.getUser_account(), this.getBegin_date(), this.getEnd_date(),
					this.getCoopType(), this.getP_coopStatus(), this.getWinStatus(), this.page, this.perPageNumber);

			this.orderList.setPageNumber(this.page);
			this.orderList.setPerPage(this.perPageNumber);
			this.orderList.setFullListSize(this.totalNumber);
			this.orderList.setList(retPlanList);
		} catch(LotteryException e){
			e.printStackTrace();
			this.setEx_code(e.getType());
			this.setEx_reason(e.getMessage());
			return ERROR; 
		}catch(Exception ex){
			ex.printStackTrace();
			this.setEx_code(99999);
			this.setEx_reason(ex.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}
	
	

	
	/**
	 * 
	 * Title: initPageParam<br>
	 * Description: <br>
	 *              <br>初始化化页面数据
	 */
	private void initPageParam(){
		this.setPlanStatusList(LotteryStaticDefine.planStatusForQuery);
		this.setLotteryList(LotteryStaticDefine.getLotteryList());
	}
	
	public Map<Integer, String> getLotteryList() {
		return lotteryList;
	}

	public void setLotteryList(Map<Integer, String> lotteryList) {
		this.lotteryList = lotteryList;
	}

	public String getP_lotteryId() {
		return p_lotteryId;
	}

	public void setP_lotteryId(String id) {
		p_lotteryId = id;
	}

	public String getP_termNo_begin() {
		return p_termNo_begin;
	}

	public void setP_termNo_begin(String no_begin) {
		p_termNo_begin = no_begin;
	}
	public String getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPerPageNumber() {
		return perPageNumber;
	}
	public void setPerPageNumber(int perPageNumber) {
		this.perPageNumber = perPageNumber;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public int getEx_code() {
		return ex_code;
	}
	public void setEx_code(int ex_code) {
		this.ex_code = ex_code;
	}
	public String getEx_reason() {
		return ex_reason;
	}
	public void setEx_reason(String ex_reason) {
		this.ex_reason = ex_reason;
	}

	public Map getSession(){
		return session;
	}

	public void setSession(Map session){
		this.session = session;
	}

	public PageList getOrderList() {
		return orderList;
	}

	public void setOrderList(PageList orderList) {
		this.orderList = orderList;
	}


	public Map<Integer, String> getPlanStatusList() {
		return planStatusList;
	}



	public void setPlanStatusList(Map<Integer, String> planStatusList) {
		this.planStatusList = planStatusList;
	}



	public int getP_planStatus() {
		return p_planStatus;
	}



	public void setP_planStatus(int status) {
		p_planStatus = status;
	}



	public String getPlanId() {
		return planId;
	}



	public void setPlanId(String planId) {
		this.planId = planId;
	}



	public int getPlanMoney() {
		return planMoney;
	}



	public void setPlanMoney(int planMoney) {
		this.planMoney = planMoney;
	}



	public int getPlanProgress() {
		return planProgress;
	}



	public void setPlanProgress(int planProgress) {
		this.planProgress = planProgress;
	}



	public int getTiChengPrec() {
		return tiChengPrec;
	}



	public void setTiChengPrec(int tiChengPrec) {
		this.tiChengPrec = tiChengPrec;
	}



	public String getUser_account() {
		return user_account;
	}



	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}


	public Map<Integer, String> getCoopTypeList() {
		return coopTypeList;
	}


	public void setCoopTypeList(Map<Integer, String> coopTypeList) {
		this.coopTypeList = coopTypeList;
	}


	public Map<String, String> getCoopStatusList() {
		return coopStatusList;
	}


	public void setCoopStatusList(Map<String, String> coopStatusList) {
		this.coopStatusList = coopStatusList;
	}


	public Map<Integer, String> getCoopWinStatusList() {
		return coopWinStatusList;
	}


	public void setCoopWinStatusList(Map<Integer, String> coopWinStatusList) {
		this.coopWinStatusList = coopWinStatusList;
	}


	public String getCoopId() {
		return coopId;
	}


	public void setCoopId(String coopId) {
		this.coopId = coopId;
	}


	public String getCoopType() {
		return coopType;
	}


	public void setCoopType(String coopType) {
		this.coopType = coopType;
	}


	public String getP_coopStatus() {
		return p_coopStatus;
	}


	public void setP_coopStatus(String status) {
		p_coopStatus = status;
	}


	public String getWinStatus() {
		return winStatus;
	}


	public void setWinStatus(String winStatus) {
		this.winStatus = winStatus;
	}
	

}
