/**
 * Title: BetOrderInfoDetailAction.java
 * @Package com.success.lottery.web.admin.action
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-5-26 下午02:55:52
 * @version V1.0
 */
package com.success.lottery.web.admin.action;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.domain.BetPlanDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.ticket.domain.BetTicketAcountDomain;
import com.success.lottery.ticket.service.interf.BetTicketServiceInterf;
import com.success.lottery.util.LotteryTools;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.web.admin.action
 * BetOrderInfoDetailAction.java
 * BetOrderInfoDetailAction
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-5-26 下午02:55:52
 * 
 */

public class BetOrderInfoDetailAction extends ActionSupport {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 5606628338563174057L;
	private String show_error = "SUCESS";
	private int ex_code = -10000;
	private String ex_reason;
	
	/*
	 * 需要由外部传入的参数
	 */
	private String orderId;//订单编号
	
	/*
	 * 详细信息界面要使用的数据
	 */
	private BetPlanDomain betPlan;//方案信息
	private BetOrderDomain betOrder;//当前订单的信息
	private LotteryTermModel termModel;//订单对应的彩期信息
	private List<BusBetOrderDomain>  betOrderList;//订单列表
	private String playType_format;
	private String planId;//方案编号
	private UserAccountModel userAccountInfo;
	private String planStatusName;
	private List<BetTicketAcountDomain> detTicketList;
	private String planTypeName;
	private String planOpenTypeName;
	
	
	
	
	public String showOrderInfoDetail(){
		try{
			/*
			 * 查订单信息
			 */
			
			BetPlanOrderServiceInterf betOrderService = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
			this.betOrder = betOrderService.queryBetOrderByOrderId(this.getOrderId());
			if(this.getBetOrder() == null){
				throw new LotteryException(900001,"未查询到订单!");
			}
			int lotteryId = this.betOrder.getLotteryId();
			String termNo = this.betOrder.getBetTerm();
			this.setPlanId(this.betOrder.getPlanId());
			long userId = this.betOrder.getUserId();
			/*
			 * 查新彩期信息
			 */
			LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
			this.termModel = termService.queryTermInfo(lotteryId,termNo);
			if(this.getTermModel() == null){
				throw new LotteryException(900001,"未查询到订单对应的彩期信息!");
			}
			/*
			 * 查用户信息
			 */
			AccountService userAccountService = ApplicationContextUtils.getService("accountService", AccountService.class);
			
			this.setUserAccountInfo(userAccountService.getUserInfoNoStatus(userId));
			/*
			 * 查方案信息
			 */
			this.betPlan = betOrderService.queryBetPlanByPlanId(this.getPlanId());
			
			if(this.getBetPlan() == null){
				throw new LotteryException(900001,"未查询到订单对应的方案信息!");
			}
			this.planOpenTypeName = LotteryStaticDefine.planOpenType.get(this.betPlan.getPlanOpenType());
			this.planTypeName = LotteryStaticDefine.planType.get(this.betPlan.getPlanType());
			this.setPlayType_format(LotteryTools.getPlayBetName(this.betPlan.getLotteryId(),this.betPlan.getPlayType(),this.betPlan.getBetType()));
			this.setPlanStatusName(LotteryStaticDefine.planStatus.get(this.betPlan.getPlanStatus()));
			/*
			 * 获取订单列表
			 */
			LotteryManagerInterf lotteryManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			this.setBetOrderList(lotteryManager.getOrdersByPlanId(this.getPlanId()));
			
			//获取指定订单的彩票情况---根据orderId获得
			BetTicketServiceInterf lotteryBetTicketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
			detTicketList = lotteryBetTicketService.getBetTicketes4OrderId(orderId);
		}catch(LotteryException e){
			e.printStackTrace();
			this.setShow_error("ERROR");
			this.setEx_code(e.getType());
			this.setEx_reason(e.getMessage());
			return ERROR; 
		}catch(Exception ex){
			ex.printStackTrace();
			this.setShow_error("ERROR");
			this.setEx_code(99999);
			this.setEx_reason(ex.getMessage());
			return ERROR;
		}
		this.setShow_error("SUCESS");
		return SUCCESS;
	}
	
	
	
	public String getOrderId() {
		return this.orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public BetOrderDomain getBetOrder() {
		return betOrder;
	}



	public void setBetOrder(BetOrderDomain betOrder) {
		this.betOrder = betOrder;
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



	public String getShow_error() {
		return show_error;
	}



	public void setShow_error(String show_error) {
		this.show_error = show_error;
	}



	public LotteryTermModel getTermModel() {
		return termModel;
	}



	public void setTermModel(LotteryTermModel termModel) {
		this.termModel = termModel;
	}



	public String getPlanId() {
		return planId;
	}



	public void setPlanId(String planId) {
		this.planId = planId;
	}


	public BetPlanDomain getBetPlan() {
		return betPlan;
	}



	public void setBetPlan(BetPlanDomain betPlan) {
		this.betPlan = betPlan;
	}


	public List<BusBetOrderDomain> getBetOrderList() {
		return betOrderList;
	}



	public void setBetOrderList(List<BusBetOrderDomain> betOrderList) {
		this.betOrderList = betOrderList;
	}



	public String getPlayType_format() {
		return playType_format;
	}



	public void setPlayType_format(String playType_format) {
		this.playType_format = playType_format;
	}



	public UserAccountModel getUserAccountInfo() {
		return userAccountInfo;
	}



	public void setUserAccountInfo(UserAccountModel userAccountInfo) {
		this.userAccountInfo = userAccountInfo;
	}



	public String getPlanStatusName() {
		return planStatusName;
	}



	public void setPlanStatusName(String planStatusName) {
		this.planStatusName = planStatusName;
	}



	public List<BetTicketAcountDomain> getDetTicketList() {
		return detTicketList;
	}



	public void setDetTicketList(List<BetTicketAcountDomain> detTicketList) {
		this.detTicketList = detTicketList;
	}



	public String getPlanTypeName() {
		return planTypeName;
	}



	public void setPlanTypeName(String planTypeName) {
		this.planTypeName = planTypeName;
	}



	public String getPlanOpenTypeName() {
		return planOpenTypeName;
	}



	public void setPlanOpenTypeName(String planOpenTypeName) {
		this.planOpenTypeName = planOpenTypeName;
	}
}
