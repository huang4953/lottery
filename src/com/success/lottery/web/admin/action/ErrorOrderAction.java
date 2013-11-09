package com.success.lottery.web.admin.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.business.service.interf.BetServiceInterf;
import com.success.lottery.business.service.interf.DispatchPrizeInterf;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.domain.BetPlanDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.ticket.domain.BetTicketAcountDomain;
import com.success.lottery.ticket.service.interf.BetTicketServiceInterf;
import com.success.lottery.util.LotteryTools;
import com.success.lottery.web.admin.webbean.PageList;
import com.success.utils.ApplicationContextUtils;
/**
 * 
 * com.success.lottery.web.admin.action
 * ErrorOrderAction.java
 * ErrorOrderAction
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-4-27 下午06:51:51
 *
 */

public class ErrorOrderAction extends ActionSupport implements SessionAware{
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -2053599861967409948L;
	
	private Map						session;
	private String show_error = "SUCESS";
	private int ex_code;
	private String ex_reason;
	/*
	 * 界面初始化参数
	 */
	
	private Map<Integer, String> lotteryList;
	/*
	 * 界面回传参数
	 */
	private String p_lotteryId = "0";
	private String p_termNo_begin = "0";
	private String orderId;
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
	
	private List<BusBetOrderDomain> orderLists;
	
	private BetOrderDomain betOrder;//当前订单的信息
	private String planId;//方案编号
	private UserAccountModel userAccountInfo;
	private BetPlanDomain betPlan;//方案信息
	private String planTypeName;
	private String planOpenTypeName;
	private String playType_format;
	private String planStatusName;
	private List<BetTicketAcountDomain> betTicketList;
	private List<BusBetOrderDomain>  betOrderList = new ArrayList<BusBetOrderDomain>();//订单列表
	
	private long daiGouTuiKuan =0L;
	
	private String daiGouTkResult = "";
	
	private String heMaiTkResult = "";
	
	private String lotteryName;
	

	/**
	 * 
	 * Title: notChuPiaoOrderShow<br>
	 * Description: <br>
	 *            查询未出票的订单<br>
	 * @return String
	 */
	public String notChuPiaoOrderShow(){
		
		try {
			this.initPageParam();
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			List<BusBetOrderDomain> retOrderList = null;
			if (this.page == 0) {
				this.page = 1;
			}
			if (this.perPageNumber == 0) {
				this.perPageNumber = defaultPerPageNumber;
			}
			
			retOrderList = termManager.getNotChuPiaoOrders(Integer
					.parseInt(this.getP_lotteryId()), this.getP_termNo_begin(),
					this.page, this.perPageNumber);
			this.totalNumber = termManager.getNotChuPiaoOrderCount(Integer
					.parseInt(this.getP_lotteryId()), this.getP_termNo_begin());
			
			this.orderList.setPageNumber(this.page);
			this.orderList.setPerPage(this.perPageNumber);
			this.orderList.setFullListSize(this.totalNumber);
			this.orderList.setList(retOrderList);
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
	 * Title: upNotChuPiaoOrder<br>
	 * Description: <br>
	 *              <br>未出票订单处理
	 */
	public void upNotChuPiaoOrder(){
		String flg="处理失败";
		try{
			BetPlanOrderServiceInterf betOrder = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
			int result = betOrder.updateBetOrderTicketStat(this.getOrderId(), 6, "0-0-0");
			if(result != 0){
				flg="处理成功";
			}
			ServletActionContext.getResponse().setContentType("text/html;charset=GBK "); 
			PrintWriter out=ServletActionContext.getResponse().getWriter();
			out.print(flg);
			out.close();
		}catch(LotteryException e){
			e.printStackTrace();
			this.setEx_code(e.getType());
			this.setEx_reason(e.getMessage());
		}catch(Exception ex){
			ex.printStackTrace();
			this.setEx_code(99999);
			this.setEx_reason(ex.getMessage());
		}
		
	}
	/**
	 * 
	 * Title: daiGoucPErrorOrderShow<br>
	 * Description: <br>
	 *              <br>代购出票错误的订单查询
	 * @return
	 */
	public String daiGoucPErrorOrderShow(){
		
		try {
			this.initPageParam();
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			List<BusBetOrderDomain> retOrderList = null;
			if (this.page == 0) {
				this.page = 1;
			}
			if (this.perPageNumber == 0) {
				this.perPageNumber = defaultPerPageNumber;
			}
			
			retOrderList = termManager.getChuPiaoErrorOrders(0,Integer
					.parseInt(this.getP_lotteryId()), this.getP_termNo_begin(),
					this.page, this.perPageNumber);
			this.totalNumber = termManager.getChuPiaoErrorOrderCount(0,Integer
					.parseInt(this.getP_lotteryId()), this.getP_termNo_begin());
			
			this.orderList.setPageNumber(this.page);
			this.orderList.setPerPage(this.perPageNumber);
			this.orderList.setFullListSize(this.totalNumber);
			this.orderList.setList(retOrderList);
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
	 * Title: daiGouTuiKuanConfirm<br>
	 * Description: <br>
	 *              <br>代购退款确认界面显示
	 * @return
	 */
	public String daiGouTuiKuanConfirm(){
		try{
			/*
			 * 查订单信息
			 */
			
			BetPlanOrderServiceInterf betOrderService = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
			this.betOrder = betOrderService.queryBetOrderByOrderId(this.getOrderId());
			if(this.getBetOrder() == null){
				throw new LotteryException(900001,"未查询到订单!");
			}
			BusBetOrderDomain busBetOrder = new BusBetOrderDomain();
			busBetOrder.setLotteryId(this.betOrder.getLotteryId());
			busBetOrder.setBetTerm(this.betOrder.getBetTerm());
			busBetOrder.setBetMultiple(this.betOrder.getBetMultiple());
			busBetOrder.setBetAmount(this.betOrder.getBetAmount());
			busBetOrder.setWinStatus(this.betOrder.getWinStatus());
			busBetOrder.setWinCode(this.betOrder.getWinCode());
			busBetOrder.setPreTaxPrize(this.betOrder.getPreTaxPrize());
			busBetOrder.setAftTaxPrize(this.betOrder.getAftTaxPrize());
			busBetOrder.setTaxPrize(this.betOrder.getTaxPrize());
			busBetOrder.setDeductPrize(this.betOrder.getDeductPrize());
			busBetOrder.setCommissionPrize(this.betOrder.getCommissionPrize());
			busBetOrder.setOrderStatus(this.betOrder.getOrderStatus());
			busBetOrder.setTicketStat(this.betOrder.getTicketStat());
			this.betOrderList.add(busBetOrder);
			this.setLotteryName(LotteryTools.getLotteryName(this.betOrder.getLotteryId()));
			
			this.setPlanId(this.betOrder.getPlanId());
			long userId = this.betOrder.getUserId();
			
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
			
			//获取指定订单的彩票情况---根据orderId获得
			BetTicketServiceInterf lotteryBetTicketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
			this.betTicketList = lotteryBetTicketService.getBetTicketes4OrderId(orderId);
			
			if(this.betOrder.getOrderStatus() == 6 || "0-0-0".equals(this.betOrder.getTicketStat())){//全部失败了
				this.daiGouTuiKuan = this.betOrder.getBetAmount();
			}else if(this.betOrder.getOrderStatus() == 4){//部分失败
				if(this.betTicketList != null){
					for(BetTicketAcountDomain oneTicket : this.betTicketList){
						if(oneTicket.getTicketStatus() == 7){
							this.daiGouTuiKuan += oneTicket.getBetAmount();
						}
					}
				}
			}
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
	/**
	 * 
	 * Title: daGouTuiOrderKuan<br>
	 * Description: <br>
	 *              <br>代购错误订单退款处理
	 * @return
	 */
	public String daGouTuiOrderKuan(){
		try {
			BetServiceInterf betService = ApplicationContextUtils.getService("busLotteryBetService", BetServiceInterf.class);
			String result = betService.daiGouErrOrderDeal(this.getOrderId());
			if(result != null){
				StringBuffer sb = new StringBuffer();
				String [] tmpResult = result.split("#"); 
				//彩种#彩期#订单号#退款金额#处理结果
				sb.append("彩种:").append(LotteryTools.getLotteryName(Integer.parseInt(tmpResult[0])))
				.append("&nbsp;彩期:").append(tmpResult[1])
				.append("&nbsp;订单:").append(tmpResult[2])
				.append("&nbsp;退款金额:").append(Integer.parseInt(tmpResult[3])/100)
				.append("&nbsp;结果:").append(tmpResult[4]);
				if(sb.length() == 0){
					 this.heMaiTkResult = result;
				}else{
					this.heMaiTkResult = sb.toString();
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
	 * Title: heMaiCPErrorOrderShow<br>
	 * Description: <br>
	 *              <br>合买出票失败的订单查询显示
	 * @return
	 */
	public String heMaiCPErrorOrderShow(){
		
		try {
			this.initPageParam();
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			List<BusBetOrderDomain> retOrderList = null;
			if (this.page == 0) {
				this.page = 1;
			}
			if (this.perPageNumber == 0) {
				this.perPageNumber = defaultPerPageNumber;
			}
			
			retOrderList = termManager.getChuPiaoErrorOrders(1,Integer
					.parseInt(this.getP_lotteryId()), this.getP_termNo_begin(),
					this.page, this.perPageNumber);
			this.totalNumber = termManager.getChuPiaoErrorOrderCount(1,Integer
					.parseInt(this.getP_lotteryId()), this.getP_termNo_begin());
			
			this.orderList.setPageNumber(this.page);
			this.orderList.setPerPage(this.perPageNumber);
			this.orderList.setFullListSize(this.totalNumber);
			this.orderList.setList(retOrderList);
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
	 * Title: heMaiTuiKuanConfirm<br>
	 * Description: <br>
	 *              <br>合买错误退款确认信息
	 * @return
	 */
	public String heMaiTuiKuanConfirm(){
		try{
			/*
			 * 查订单信息
			 */
			
			BetPlanOrderServiceInterf betOrderService = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
			this.betOrder = betOrderService.queryBetOrderByOrderId(this.getOrderId());
			if(this.getBetOrder() == null){
				throw new LotteryException(900001,"未查询到订单!");
			}
			BusBetOrderDomain busBetOrder = new BusBetOrderDomain();
			busBetOrder.setLotteryId(this.betOrder.getLotteryId());
			busBetOrder.setBetTerm(this.betOrder.getBetTerm());
			busBetOrder.setBetMultiple(this.betOrder.getBetMultiple());
			busBetOrder.setBetAmount(this.betOrder.getBetAmount());
			busBetOrder.setWinStatus(this.betOrder.getWinStatus());
			busBetOrder.setWinCode(this.betOrder.getWinCode());
			busBetOrder.setPreTaxPrize(this.betOrder.getPreTaxPrize());
			busBetOrder.setAftTaxPrize(this.betOrder.getAftTaxPrize());
			busBetOrder.setTaxPrize(this.betOrder.getTaxPrize());
			busBetOrder.setDeductPrize(this.betOrder.getDeductPrize());
			busBetOrder.setCommissionPrize(this.betOrder.getCommissionPrize());
			busBetOrder.setOrderStatus(this.betOrder.getOrderStatus());
			busBetOrder.setTicketStat(this.betOrder.getTicketStat());
			this.betOrderList.add(busBetOrder);
			
			this.setLotteryName(LotteryTools.getLotteryName(this.betOrder.getLotteryId()));
			this.setPlanId(this.betOrder.getPlanId());
			long userId = this.betOrder.getUserId();
			
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
			
			//获取指定订单的彩票情况---根据orderId获得
			BetTicketServiceInterf lotteryBetTicketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
			this.betTicketList = lotteryBetTicketService.getBetTicketes4OrderId(orderId);
			
			if(this.betOrder.getOrderStatus() == 6 || "0-0-0".equals(this.betOrder.getTicketStat())){//全部失败了
				this.daiGouTuiKuan = this.betOrder.getBetAmount();
			}else if(this.betOrder.getOrderStatus() == 4){//部分失败
				if(this.betTicketList != null){
					for(BetTicketAcountDomain oneTicket : this.betTicketList){
						if(oneTicket.getTicketStatus() == 7){
							this.daiGouTuiKuan += oneTicket.getBetAmount();
						}
					}
				}
			}
			
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
	/**
	 * 
	 * Title: heMaiTuiOrderKuan<br>
	 * Description: <br>
	 *              <br>合买订单退款
	 * @return
	 */
	public String heMaiTuiOrderKuan(){
		try {
			BetServiceInterf betService = ApplicationContextUtils.getService("busLotteryBetService", BetServiceInterf.class);
			String result = betService.heMaiErrOrderDeal(this.getOrderId());
			if(result != null){
				StringBuffer sb = new StringBuffer();
				String [] tmpResult = result.split("#"); 
				//彩种#彩期#订单号#退款金额#处理结果
				sb.append("彩种:").append(LotteryTools.getLotteryName(Integer.parseInt(tmpResult[0])))
				.append("&nbsp;彩期:").append(tmpResult[1])
				.append("&nbsp;订单:").append(tmpResult[2])
				.append("&nbsp;退款总金额:").append(Integer.parseInt(tmpResult[3])/100).append("元")
				.append("&nbsp;结果:").append(tmpResult[5])
				.append("<br>").append("退款明细:").append(tmpResult[4]);
				if(sb.length() == 0){
					 this.daiGouTkResult = result;
				}else{
					this.daiGouTkResult = sb.toString();
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
	 *              <br>初始化化页面数据
	 */
	private void initPageParam(){
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

	public List<BusBetOrderDomain> getOrderLists() {
		return orderLists;
	}

	public void setOrderLists(List<BusBetOrderDomain> orderLists) {
		this.orderLists = orderLists;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getShow_error() {
		return show_error;
	}

	public void setShow_error(String show_error) {
		this.show_error = show_error;
	}

	public BetOrderDomain getBetOrder() {
		return betOrder;
	}

	public void setBetOrder(BetOrderDomain betOrder) {
		this.betOrder = betOrder;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public UserAccountModel getUserAccountInfo() {
		return userAccountInfo;
	}

	public void setUserAccountInfo(UserAccountModel userAccountInfo) {
		this.userAccountInfo = userAccountInfo;
	}

	public BetPlanDomain getBetPlan() {
		return betPlan;
	}

	public void setBetPlan(BetPlanDomain betPlan) {
		this.betPlan = betPlan;
	}

	public String getPlanOpenTypeName() {
		return planOpenTypeName;
	}

	public void setPlanOpenTypeName(String planOpenTypeName) {
		this.planOpenTypeName = planOpenTypeName;
	}

	public String getPlanTypeName() {
		return planTypeName;
	}

	public void setPlanTypeName(String planTypeName) {
		this.planTypeName = planTypeName;
	}

	public String getPlayType_format() {
		return playType_format;
	}

	public void setPlayType_format(String playType_format) {
		this.playType_format = playType_format;
	}

	public String getPlanStatusName() {
		return planStatusName;
	}

	public void setPlanStatusName(String planStatusName) {
		this.planStatusName = planStatusName;
	}

	public List<BetTicketAcountDomain> getBetTicketList() {
		return betTicketList;
	}

	public void setBetTicketList(List<BetTicketAcountDomain> betTicketList) {
		this.betTicketList = betTicketList;
	}

	public long getDaiGouTuiKuan() {
		return daiGouTuiKuan;
	}

	public void setDaiGouTuiKuan(long daiGouTuiKuan) {
		this.daiGouTuiKuan = daiGouTuiKuan;
	}

	public List<BusBetOrderDomain> getBetOrderList() {
		return betOrderList;
	}

	public void setBetOrderList(List<BusBetOrderDomain> betOrderList) {
		this.betOrderList = betOrderList;
	}

	public String getDaiGouTkResult() {
		return daiGouTkResult;
	}

	public void setDaiGouTkResult(String daiGouTkResult) {
		this.daiGouTkResult = daiGouTkResult;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getHeMaiTkResult() {
		return heMaiTkResult;
	}

	public void setHeMaiTkResult(String heMaiTkResult) {
		this.heMaiTkResult = heMaiTkResult;
	}
}
