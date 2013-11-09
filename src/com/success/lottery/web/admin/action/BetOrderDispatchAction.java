/**
 * Title: BetOrderInfoAction.java
 * @Package com.success.lottery.web.admin.action
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-5-25 ����11:13:34
 * @version V1.0
 */
package com.success.lottery.web.admin.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.business.domain.BusBetOrderCountDomain;
import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.domain.BusCpInfoDomain;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.business.service.impl.CashPrizeService;
import com.success.lottery.business.service.interf.CashPrizeInterf;
import com.success.lottery.business.service.interf.DispatchPrizeInterf;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.business.service.interf.PrizeInnerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.domain.BetPlanDomain;
import com.success.lottery.order.domain.CpInfoDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.util.AreaMapTools;
import com.success.lottery.util.LotteryTools;
import com.success.lottery.web.admin.webbean.PageList;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.web.admin.action
 * BetOrderInfoAction.java
 * BetOrderInfoAction
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-5-25 ����11:13:34
 * 
 */

public class BetOrderDispatchAction extends ActionSupport implements SessionAware{
	
	/**
	 * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô)
	 */
	private static final long serialVersionUID = 6529769304632462766L;

	private Map						session;
	
	private int ex_code;
	private String ex_reason;
	private String show_error = "SUCESS";
	/*
	 * �����ʼ������
	 */
	private Map<Integer, String> lotteryList;
	/*
	 * ����ش�����
	 */
	private String p_lotteryId;
	private String p_termNo_begin = "0";
	private String userId;
	private int touZhuType = 0;
	
	
	/*
	 * ��ҳ����
	 */
	private String query;
	
	//ÿһҳ�ļ�¼��
	private int					perPageNumber;
	//�ڼ�ҳ
	private int					page;
	
	private PageList 				orderList = new PageList();

	//��ҳ����ʹ��static�������ڷ�ҳʱ���²�ѯ�����������������⣬����ͬʱ���ı���ҳ��
	//Ŀǰÿ�ζ���ѯ��������
	//�ɿ��ǵĽ��������
	//1-����������session�� 2-�����ݿ�DAO���ж�����������ϴ���ͬ�������ݿ��ѯֱ�ӷ���
	private int totalNumber;
	
	private static int defaultPerPageNumber = 20;
	
	private Long total_prize = 0L;
	private Long total_aftTaxPrize = 0L;
	
	private String planId;
	private String orderId;//�Դ���Ϊ��Ʊ������id���Ժ���Ϊ������Ϣ��id
	private String chuPiaoOrderId;//�Դ���Ϊ��Ʊ������id����orderId��ͬ���Ժ���Ϊ��Ʊ������id
	private BetOrderDomain betOrder;
	private BetPlanDomain betPlan;
	private CpInfoDomain coopInfo;
	
	private UserAccountModel userAccount;
	
	private String planSourceName;
	
	private String playName;
	
	private String orderStatusName;
	private String winStatusName;
	private String ticketStatusName;
	private String planTypeName;
	private String planStopName;
	private String planStatusName;
	private String planOpenTypeName;
	private String coopOrderStatusName;
	private String coopWinstatusName;
	private String coopOrderTypeName;
	
	private String userStatusName;
	
	private String userAreaName;
	
	private String dispatchResult;
	
	private int planType;
	
	
	/*
	 * �ҽ����ҳ����Ҫ�ṩ�Ĳ���
	 */
	
	private String lotteryName;
	
	/**
	 * 
	 * Title: betOrderInfoShow<br>
	 * Description: <br>
	 *              <br>��ʾ�ɽ������б�
	 * @return String
	 */
	public String betOrderDispatchShow(){
		
		try {
			this.initPageParam();
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			List<BusBetOrderDomain> retOrderList = null;
			List<BusCpInfoDomain> coopOrderList = null;
			if (this.page == 0) {
				this.page = 1;
			}
			if (this.perPageNumber == 0) {
				this.perPageNumber = defaultPerPageNumber;
			}
			
			if(this.getTouZhuType() == 0){//����
				BusBetOrderCountDomain dispatchCount = termManager.getCanDispatchOrderInfosCount(this.getP_lotteryId(), this.getP_termNo_begin(),this.getUserId());
				
				this.totalNumber = dispatchCount.getTotalNum();
				this.total_prize = dispatchCount.getTotalPrize()/100;
				this.total_aftTaxPrize = dispatchCount.getTotalAftTaxPrize()/100;
				
				retOrderList = termManager.getCanDispatchOrderInfoS(this.getP_lotteryId(), this.getP_termNo_begin(),this.getUserId(), this.page, this.perPageNumber);
				
				this.orderList.setPageNumber(this.page);
				this.orderList.setPerPage(this.perPageNumber);
				this.orderList.setFullListSize(this.totalNumber);
				this.orderList.setList(retOrderList);
			}else if(this.getTouZhuType() == 1){//����
				BusBetOrderCountDomain dispatchCount = termManager.getCoopCanDispatchCount(this.getP_lotteryId(), this.getP_termNo_begin(),this.getUserId());
				
				this.totalNumber = dispatchCount.getTotalNum();
				this.total_prize = dispatchCount.getTotalPrize()/100;
				
				coopOrderList = termManager.getCoopCanDispatch(this.getP_lotteryId(), this.getP_termNo_begin(),this.getUserId(), this.page, this.perPageNumber);
				
				this.orderList.setPageNumber(this.page);
				this.orderList.setPerPage(this.perPageNumber);
				this.orderList.setFullListSize(this.totalNumber);
				this.orderList.setList(coopOrderList);
			}
			
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
	 * Title: dispatchOrderConfig<br>
	 * Description: <br>
	 *              <br>�ɽ�����ȷ��ҳ��
	 * @return String
	 */
	public String dispatchOrderConfig(){
		try {
			BetPlanOrderServiceInterf betOrderService = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
			/*
			 * �Ȼ�÷�����Ϣ
			 */
			this.betPlan = betOrderService.queryBetPlanByPlanId(this.getPlanId());
			this.planTypeName = LotteryStaticDefine.planType.get(this.betPlan.getPlanType());
			this.planStopName = LotteryStaticDefine.planStop.get(this.betPlan.getWinStoped());
			this.planStatusName = LotteryStaticDefine.planStatus.get(this.betPlan.getPlanStatus());
			this.planSourceName = LotteryStaticDefine.planSource.get(String.valueOf(this.betPlan.getPlanSource()));
			this.planOpenTypeName = LotteryStaticDefine.planOpenType.get(this.betPlan.getPlanOpenType());
			this.planType = this.betPlan.getPlanType();
			this.lotteryName = LotteryTools.getLotteryName(this.betPlan.getLotteryId());
			this.playName = LotteryTools.getPlayBetName(this.betPlan.getLotteryId(), this.betPlan.getPlayType(), this.betPlan.getBetType());
			
			if(this.planType == 0){//����
				this.betOrder = betOrderService.queryBetOrderByOrderId(this.getOrderId());
				this.orderStatusName = LotteryStaticDefine.orderStatus.get(String.valueOf(this.betOrder.getOrderStatus()));
				this.winStatusName = LotteryStaticDefine.orderWinStatus.get(String.valueOf(this.betOrder.getWinStatus()));
				this.chuPiaoOrderId = this.betOrder.getOrderId();
			}else if(this.planType == 1){//����
				//�Ȳ�ѯ��Ʊ����
				List<BetOrderDomain> chuPiaoOrders = betOrderService.queryBetOrderByPlanId(this.betPlan.getPlanId());
				if(chuPiaoOrders == null || chuPiaoOrders.size() > 1){
					throw new Exception("�����Ʊ������ѯ����");
				}
				
				this.betOrder = chuPiaoOrders.get(0);
				this.orderStatusName = LotteryStaticDefine.orderStatus.get(String.valueOf(this.betOrder.getOrderStatus()));
				this.winStatusName = LotteryStaticDefine.orderWinStatus.get(String.valueOf(this.betOrder.getWinStatus()));
				this.coopInfo = betOrderService.queryCoopInfoByInfoId(this.orderId);
				this.coopOrderStatusName = LotteryStaticDefine.coopOrderStatus.get(String.valueOf(this.coopInfo.getOrderStatus()));
				this.coopWinstatusName = LotteryStaticDefine.coopWinStatus.get(this.coopInfo.getWinStatus());
				this.coopOrderTypeName = LotteryStaticDefine.coopOrderType.get(this.coopInfo.getCpOrderType());
				this.chuPiaoOrderId = this.betOrder.getOrderId();
			}
			
			/*
			 * ���ݶ����õ��û��˻���Ϣ
			 */
			long orderUserId = this.betOrder.getUserId();
			AccountService accountService = ApplicationContextUtils.getService("accountService", AccountService.class);
			this.userAccount = accountService.getUserInfoNoStatus((int)orderUserId);
			this.userStatusName = LotteryStaticDefine.userStatus.get(String.valueOf(this.userAccount.getStatus()));
			this.userAreaName = AreaMapTools.getAreaName(this.userAccount.getAreaCode());
		} catch(LotteryException e){
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
		
		return SUCCESS;
	}
	
	
	public String orderDispatch(){
		try {
			/*
			 * ���õ����ɽ�����
			 */
			System.out.println("this.getOrderId()==="+this.getOrderId());
			DispatchPrizeInterf dispatchService = ApplicationContextUtils.getService("busLotteryDispatchPrizeService", DispatchPrizeInterf.class);
			String result = dispatchService.dispatchSingleOrder(this.getPlanType(),this.getChuPiaoOrderId(),this.getOrderId());
			if(result != null){
				StringBuffer sb = new StringBuffer();
				String [] tmpResult = result.split("#"); 
				//����#����#�������#���𼶱�#������#������
				try{
					sb.append("����:").append(LotteryTools.getLotteryName(Integer.parseInt(tmpResult[0]))).append("&nbsp;����:").append(tmpResult[1])
					.append("&nbsp;����:").append(tmpResult[2]).append("&nbsp;���𼶱�:").append(LotteryStaticDefine.orderWinStatus.get(tmpResult[3]))
					.append("&nbsp;������:").append(Integer.parseInt(tmpResult[4])/100).append("&nbsp;���:").append(tmpResult[5]);
				}catch(Exception e){
					
				}
				if(sb.length() == 0){
					 this.dispatchResult = result;
				}else{
					this.dispatchResult = sb.toString();
				}
				
			}
			
		} catch(LotteryException e){
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
	
	/**
	 * 
	 * Title: initPageParam<br>
	 * Description: <br>
	 *              <br>��ʼ����ҳ������
	 */
	private void initPageParam(){
		this.setLotteryList(LotteryStaticDefine.getLotteryList());
	}
	
	public Map<Integer, String> getLotteryList() {
		return this.lotteryList;
	}

	public void setLotteryList(Map<Integer, String> lotteryList) {
		this.lotteryList = lotteryList;
	}

	public String getP_lotteryId() {
		return this.p_lotteryId;
	}

	public void setP_lotteryId(String id) {
		this.p_lotteryId = id;
	}

	public String getP_termNo_begin() {
		return this.p_termNo_begin;
	}

	public void setP_termNo_begin(String no_begin) {
		this.p_termNo_begin = no_begin;
	}
	
	public int getPage() {
		return this.page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPerPageNumber() {
		return this.perPageNumber;
	}
	public void setPerPageNumber(int perPageNumber) {
		this.perPageNumber = perPageNumber;
	}
	public String getQuery() {
		return this.query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public int getTotalNumber() {
		return this.totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public int getEx_code() {
		return this.ex_code;
	}
	public void setEx_code(int ex_code) {
		this.ex_code = ex_code;
	}
	public String getEx_reason() {
		return this.ex_reason;
	}
	public void setEx_reason(String ex_reason) {
		this.ex_reason = ex_reason;
	}

	public Map getSession(){
		return this.session;
	}

	public void setSession(Map session){
		this.session = session;
	}

	public PageList getOrderList() {
		return this.orderList;
	}

	public void setOrderList(PageList orderList) {
		this.orderList = orderList;
	}

	

	public String getLotteryName() {
		return this.lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getTotal_prize() {
		return total_prize;
	}

	public void setTotal_prize(long total_prize) {
		this.total_prize = total_prize;
	}
	public String getOrderId() {
		return orderId;
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
	public UserAccountModel getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(UserAccountModel userAccount) {
		this.userAccount = userAccount;
	}
	public String getShow_error() {
		return show_error;
	}
	public void setShow_error(String show_error) {
		this.show_error = show_error;
	}
	public String getPlanSourceName() {
		return planSourceName;
	}
	public void setPlanSourceName(String planSourceName) {
		this.planSourceName = planSourceName;
	}
	public String getPlayName() {
		return playName;
	}
	public void setPlayName(String playName) {
		this.playName = playName;
	}
	public String getOrderStatusName() {
		return orderStatusName;
	}
	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}
	public String getTicketStatusName() {
		return ticketStatusName;
	}
	public void setTicketStatusName(String ticketStatusName) {
		this.ticketStatusName = ticketStatusName;
	}
	public String getWinStatusName() {
		return winStatusName;
	}
	public void setWinStatusName(String winStatusName) {
		this.winStatusName = winStatusName;
	}
	public void setTotal_prize(Long total_prize) {
		this.total_prize = total_prize;
	}
	public String getUserStatusName() {
		return userStatusName;
	}
	public void setUserStatusName(String userStatusName) {
		this.userStatusName = userStatusName;
	}
	public String getUserAreaName() {
		return userAreaName;
	}
	public void setUserAreaName(String userAreaName) {
		this.userAreaName = userAreaName;
	}
	public String getDispatchResult() {
		return dispatchResult;
	}
	public void setDispatchResult(String dispatchResult) {
		this.dispatchResult = dispatchResult;
	}
	public int getTouZhuType() {
		return touZhuType;
	}
	public void setTouZhuType(int touZhuType) {
		this.touZhuType = touZhuType;
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
	public String getPlanTypeName() {
		return planTypeName;
	}
	public void setPlanTypeName(String planTypeName) {
		this.planTypeName = planTypeName;
	}
	public String getPlanStatusName() {
		return planStatusName;
	}
	public void setPlanStatusName(String planStatusName) {
		this.planStatusName = planStatusName;
	}
	public String getPlanStopName() {
		return planStopName;
	}
	public void setPlanStopName(String planStopName) {
		this.planStopName = planStopName;
	}
	public String getPlanOpenTypeName() {
		return planOpenTypeName;
	}
	public void setPlanOpenTypeName(String planOpenTypeName) {
		this.planOpenTypeName = planOpenTypeName;
	}
	public int getPlanType() {
		return planType;
	}
	public void setPlanType(int planType) {
		this.planType = planType;
	}
	public CpInfoDomain getCoopInfo() {
		return coopInfo;
	}
	public void setCoopInfo(CpInfoDomain coopInfo) {
		this.coopInfo = coopInfo;
	}
	public String getCoopOrderStatusName() {
		return coopOrderStatusName;
	}
	public void setCoopOrderStatusName(String coopOrderStatusName) {
		this.coopOrderStatusName = coopOrderStatusName;
	}
	public String getCoopWinstatusName() {
		return coopWinstatusName;
	}
	public void setCoopWinstatusName(String coopWinstatusName) {
		this.coopWinstatusName = coopWinstatusName;
	}
	public String getCoopOrderTypeName() {
		return coopOrderTypeName;
	}
	public void setCoopOrderTypeName(String coopOrderTypeName) {
		this.coopOrderTypeName = coopOrderTypeName;
	}
	public String getChuPiaoOrderId() {
		return chuPiaoOrderId;
	}
	public void setChuPiaoOrderId(String chuPiaoOrderId) {
		this.chuPiaoOrderId = chuPiaoOrderId;
	}
	public Long getTotal_aftTaxPrize() {
		return total_aftTaxPrize;
	}
	public void setTotal_aftTaxPrize(Long total_aftTaxPrize) {
		this.total_aftTaxPrize = total_aftTaxPrize;
	}

}
