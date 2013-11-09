/**
 * Title: BetOrderInfoDetailAction.java
 * @Package com.success.lottery.web.admin.action
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-5-26 ����02:55:52
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
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-5-26 ����02:55:52
 * 
 */

public class BetOrderInfoDetailAction extends ActionSupport {
	
	/**
	 * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô)
	 */
	private static final long serialVersionUID = 5606628338563174057L;
	private String show_error = "SUCESS";
	private int ex_code = -10000;
	private String ex_reason;
	
	/*
	 * ��Ҫ���ⲿ����Ĳ���
	 */
	private String orderId;//�������
	
	/*
	 * ��ϸ��Ϣ����Ҫʹ�õ�����
	 */
	private BetPlanDomain betPlan;//������Ϣ
	private BetOrderDomain betOrder;//��ǰ��������Ϣ
	private LotteryTermModel termModel;//������Ӧ�Ĳ�����Ϣ
	private List<BusBetOrderDomain>  betOrderList;//�����б�
	private String playType_format;
	private String planId;//�������
	private UserAccountModel userAccountInfo;
	private String planStatusName;
	private List<BetTicketAcountDomain> detTicketList;
	private String planTypeName;
	private String planOpenTypeName;
	
	
	
	
	public String showOrderInfoDetail(){
		try{
			/*
			 * �鶩����Ϣ
			 */
			
			BetPlanOrderServiceInterf betOrderService = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
			this.betOrder = betOrderService.queryBetOrderByOrderId(this.getOrderId());
			if(this.getBetOrder() == null){
				throw new LotteryException(900001,"δ��ѯ������!");
			}
			int lotteryId = this.betOrder.getLotteryId();
			String termNo = this.betOrder.getBetTerm();
			this.setPlanId(this.betOrder.getPlanId());
			long userId = this.betOrder.getUserId();
			/*
			 * ���²�����Ϣ
			 */
			LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
			this.termModel = termService.queryTermInfo(lotteryId,termNo);
			if(this.getTermModel() == null){
				throw new LotteryException(900001,"δ��ѯ��������Ӧ�Ĳ�����Ϣ!");
			}
			/*
			 * ���û���Ϣ
			 */
			AccountService userAccountService = ApplicationContextUtils.getService("accountService", AccountService.class);
			
			this.setUserAccountInfo(userAccountService.getUserInfoNoStatus(userId));
			/*
			 * �鷽����Ϣ
			 */
			this.betPlan = betOrderService.queryBetPlanByPlanId(this.getPlanId());
			
			if(this.getBetPlan() == null){
				throw new LotteryException(900001,"δ��ѯ��������Ӧ�ķ�����Ϣ!");
			}
			this.planOpenTypeName = LotteryStaticDefine.planOpenType.get(this.betPlan.getPlanOpenType());
			this.planTypeName = LotteryStaticDefine.planType.get(this.betPlan.getPlanType());
			this.setPlayType_format(LotteryTools.getPlayBetName(this.betPlan.getLotteryId(),this.betPlan.getPlayType(),this.betPlan.getBetType()));
			this.setPlanStatusName(LotteryStaticDefine.planStatus.get(this.betPlan.getPlanStatus()));
			/*
			 * ��ȡ�����б�
			 */
			LotteryManagerInterf lotteryManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			this.setBetOrderList(lotteryManager.getOrdersByPlanId(this.getPlanId()));
			
			//��ȡָ�������Ĳ�Ʊ���---����orderId���
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
