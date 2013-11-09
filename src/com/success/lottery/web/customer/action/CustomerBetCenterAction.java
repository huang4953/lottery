package com.success.lottery.web.customer.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.business.service.interf.PlanOrderManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.domain.BetPlanDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.util.LotteryTools;
import com.success.lottery.web.enumerate.AjaxResultStatusType;
import com.success.lottery.web.formbean.JsonMsgBean;
import com.success.lottery.web.formbean.Page;
import com.success.lottery.web.trade.action.supper.LotteryWebPageBaseActon;
import com.success.utils.ApplicationContextUtils;

@SuppressWarnings("serial")
public class CustomerBetCenterAction extends LotteryWebPageBaseActon{
	private int type=-1;
	private Date startDate;
	private Date endDate;
	private Date begintime;
	private Date endtime;
	private String term;
	private int pagesize=9;
	private int bettype=-1;
	private int orderStatus=-1;
	private int winStatus=-1;
	private String orderId;
	
	private String planId;//方案编号
	private BetPlanDomain betPlan;//方案信息
	private List<BusBetOrderDomain>  betOrderList;//订单列表
	private String playBetName;//玩法中文
	private String planStatusName;//方案状态
	private LotteryTermModel termModel;//订单对应的彩期信息
	
	public String getZh(){
		if(null == super.page)
			super.page = new Page(pagesize);
		else
			page.setPageSize(pagesize);
		UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
		BetPlanOrderServiceInterf betOrderService = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
		try {
			List<BetOrderDomain> list = betOrderService.getUserChaseOrders(customer.getUserId(), type, "-1".equals(term) ? null : term, begintime, endtime, page.getFirst(), page.getPageSize());
			if(null!=list){
				page.setTotalCount(list.get(0).getLotteryId());
				list.remove(0);
				page.setResult(list);
			}else
				page.setTotalCount(0);
			
		} catch (LotteryException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String stopzh(){
		PlanOrderManagerInterf orderservice =  ApplicationContextUtils.getService("busPlanOrderManagerService",PlanOrderManagerInterf.class);
		try {
			orderservice.cancelAddOrder(orderId);
			orderId="操作成功";
		} catch (LotteryException e) {
			orderId=e.getMessage();
		}
		return getZh();
	}
	public String getdg(){
		if(null == super.page)
			super.page = new Page(pagesize);
		else
			page.setPageSize(pagesize);
		UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
		BetPlanOrderServiceInterf betOrderService = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
		try {
			List<BetOrderDomain> list = betOrderService.getUserOrders(customer.getUserId(), type, term, bettype, orderStatus, winStatus, begintime, endtime, page.getFirst(), page.getPageSize());
			if(null!=list){
				page.setTotalCount(list.get(0).getLotteryId());
				list.remove(0);
				page.setResult(list);
			}else
				page.setTotalCount(0);
		} catch (LotteryException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getXq(){
		try{
			/*
			 * 查订单信息
			 */
			
			BetPlanOrderServiceInterf betOrderService = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
			BetOrderDomain betOrder = betOrderService.queryBetOrderByOrderId(this.getOrderId());
			if(betOrder == null){
				throw new LotteryException(900001,"未查询到订单!");
			}
			int lotteryId = betOrder.getLotteryId();
			String termNo = betOrder.getBetTerm();
			this.setPlanId(betOrder.getPlanId());
			
			/*
			 * 查新彩期信息
			 */
			LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
			this.termModel = termService.queryTermInfo(lotteryId,termNo);
			if(this.getTermModel() == null){
				throw new LotteryException(900001,"未查询到订单对应的彩期信息!");
			}
			
			
			/*
			 * 查方案信息
			 */
			this.betPlan = betOrderService.queryBetPlanByPlanId(this.getPlanId());
			
			if(this.getBetPlan() == null){
				throw new LotteryException(900001,"未查询到订单对应的方案信息!");
			}
			this.setPlayBetName(LotteryTools.getPlayBetName(this.betPlan.getLotteryId(), this.betPlan.getPlayType(), this.betPlan.getBetType()));
			this.setPlanStatusName(LotteryStaticDefine.planStatus.get(this.betPlan.getPlanStatus()));
			/*
			 * 获取订单列表
			 */
			LotteryManagerInterf lotteryManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			this.setBetOrderList(lotteryManager.getOrdersByPlanId(this.getPlanId()));
			
		}catch(LotteryException e){
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return SUCCESS;
	}
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		if(StringUtils.isNotEmpty(term))
			this.term = term;
		else
			this.term=null;
	}
	public int getBettype() {
		return bettype;
	}
	public void setBettype(int bettype) {
		this.bettype = bettype;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public int getWinStatus() {
		return winStatus;
	}
	public void setWinStatus(int winStatus) {
		this.winStatus = winStatus;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public List<BusBetOrderDomain> getBetOrderList() {
		return betOrderList;
	}
	public void setBetOrderList(List<BusBetOrderDomain> betOrderList) {
		this.betOrderList = betOrderList;
	}
	public BetPlanDomain getBetPlan() {
		return betPlan;
	}
	public void setBetPlan(BetPlanDomain betPlan) {
		this.betPlan = betPlan;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlayBetName() {
		return playBetName;
	}
	public void setPlayBetName(String playBetName) {
		this.playBetName = playBetName;
	}
	public String getPlanStatusName() {
		return planStatusName;
	}
	public void setPlanStatusName(String planStatusName) {
		this.planStatusName = planStatusName;
	}
	public LotteryTermModel getTermModel() {
		return termModel;
	}
	public void setTermModel(LotteryTermModel termModel) {
		this.termModel = termModel;
	}
	public Date getBegintime() {
		return begintime;
	}
	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
}
