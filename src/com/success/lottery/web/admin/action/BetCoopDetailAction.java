
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
 * 
 * com.success.lottery.web.admin.action
 * BetCoopDetailAction.java
 * BetCoopDetailAction
 * 合买详细信息
 * @author gaoboqin
 * 2011-4-22 下午07:54:28
 *
 */

public class BetCoopDetailAction extends ActionSupport implements SessionAware{
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 6529769304632462766L;

	private Map						session;
	
	private int ex_code;
	private String ex_reason;
	private String show_error = "SUCESS";
	
	private String userId;
	private int touZhuType = 0;
	
	
	/*
	 * 分页参数
	 */
	private String query;
	
	//每一页的记录数
	private int					perPageNumber;
	//第几页
	private int					page;
	
	private PageList 				orderList = new PageList();

	//总页数，使用static，避免在分页时重新查询总条数，还是有问题，多人同时查会改变总页数
	//目前每次都查询总条数；
	//可考虑的解决方案：
	//1-总条数放入session； 2-在数据库DAO中判断条件如果和上次相同则不做数据库查询直接返回
	private int totalNumber;
	
	private static int defaultPerPageNumber = 10;
	
	private Long total_prize = 0L;
	private Long total_aftTaxPrize = 0L;
	
	private String planId;
	private String coopOrderId;//参与订单的编号
	private String chuPiaoOrderId;//对代购为出票订单的id，和orderId相同，对合买为出票订单的id
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
	
	
	private String lotteryName;
	
	
	private int havaChuPiao = 0;
	private int isCanYuOrder = 0;
	
	
	/**
	 * 
	 * Title: dispatchOrderConfig<br>
	 * Description: <br>
	 *              <br>合买参与详情
	 * @return String
	 */
	public String betCoopDetailInfo(){
		try {
			BetPlanOrderServiceInterf betOrderService = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
			/*
			 * 先获得方案信息
			 */
			this.betPlan = betOrderService.queryBetPlanByPlanId(this.getPlanId());
			if(this.betPlan == null){
				throw new Exception("未查到对应的方案信息");
			}
			this.planTypeName = LotteryStaticDefine.planType.get(this.betPlan.getPlanType());
			this.planStopName = LotteryStaticDefine.planStop.get(this.betPlan.getWinStoped());
			this.planStatusName = LotteryStaticDefine.planStatus.get(this.betPlan.getPlanStatus());
			this.planSourceName = LotteryStaticDefine.planSource.get(String.valueOf(this.betPlan.getPlanSource()));
			this.planOpenTypeName = LotteryStaticDefine.planOpenType.get(this.betPlan.getPlanOpenType());
			this.planType = this.betPlan.getPlanType();
			this.lotteryName = LotteryTools.getLotteryName(this.betPlan.getLotteryId());
			this.playName = LotteryTools.getPlayBetName(this.betPlan.getLotteryId(), this.betPlan.getPlayType(), this.betPlan.getBetType());
			
			if(this.betPlan.getPlanStatus() == 1 || this.betPlan.getPlanStatus() == 2 ||this.betPlan.getPlanStatus() == 3){//已经生成投注订单
				//先查询出票订单
				List<BetOrderDomain> chuPiaoOrders = betOrderService.queryBetOrderByPlanId(this.betPlan.getPlanId());
				if(chuPiaoOrders == null || chuPiaoOrders.size() > 1){
					throw new Exception("合买出票订单查询错误");
				}
				
				this.betOrder = chuPiaoOrders.get(0);
				this.orderStatusName = LotteryStaticDefine.orderStatus.get(String.valueOf(this.betOrder.getOrderStatus()));
				this.winStatusName = LotteryStaticDefine.orderWinStatus.get(String.valueOf(this.betOrder.getWinStatus()));
				this.chuPiaoOrderId = this.betOrder.getOrderId();
				this.setHavaChuPiao(1);
			}
			
			if(StringUtils.isNotEmpty(this.getCoopOrderId())){//如果传入了参与订单的编号
				//查询传入的参与信息
				this.setIsCanYuOrder(1);
				this.coopInfo = betOrderService.queryCoopInfoByInfoId(this.getCoopOrderId());
				this.coopOrderStatusName = LotteryStaticDefine.coopOrderStatus.get(String.valueOf(this.coopInfo.getOrderStatus()));
				this.coopWinstatusName = LotteryStaticDefine.coopWinStatus.get(this.coopInfo.getWinStatus());
				this.coopOrderTypeName = LotteryStaticDefine.coopOrderType.get(this.coopInfo.getCpOrderType());
			}
			
			/*
			 * 查询参与人的账户信息
			 */
			long orderUserId = StringUtils.isNotEmpty(this.getCoopOrderId())?this.coopInfo.getUserId():this.betPlan.getUserId();
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
	/**
	 * 
	 * Title: coopInfoList<br>
	 * Description: <br>
	 *              <br>合买详情中的参与信息列表
	 * @return
	 */
	public String coopInfoList(){
		try {
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			List<BusCpInfoDomain> retPlanList = null;
			if (this.page == 0) {
				this.page = 1;
			}
			if (this.perPageNumber == 0) {
				this.perPageNumber = defaultPerPageNumber;
			}
			
			this.totalNumber = termManager.getCoopCanYuInfosCount("0", "0",
					this.getPlanId(), null, null, null, null,null,null, null);
			
			retPlanList = termManager.getCoopCanYuInfos("0", "0",
					this.getPlanId(), null, null, null, null,null,null, null, this.page, this.perPageNumber);

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

	public String getCoopOrderId() {
		return coopOrderId;
	}

	public void setCoopOrderId(String coopOrderId) {
		this.coopOrderId = coopOrderId;
	}

	public int getHavaChuPiao() {
		return havaChuPiao;
	}

	public void setHavaChuPiao(int havaChuPiao) {
		this.havaChuPiao = havaChuPiao;
	}

	public int getIsCanYuOrder() {
		return isCanYuOrder;
	}

	public void setIsCanYuOrder(int isCanYuOrder) {
		this.isCanYuOrder = isCanYuOrder;
	}

}
