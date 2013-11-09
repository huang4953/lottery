package com.success.lottery.web.admin.action;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
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
import com.success.lottery.util.AreaMapTools;
import com.success.lottery.util.LotteryTools;
import com.success.lottery.web.admin.webbean.PageList;
import com.success.utils.ApplicationContextUtils;

public class BetTicketAction extends ActionSupport implements SessionAware{
	
	private long totalBetTicketNumber;//统计彩票数目
	private String totalBetTicketMoney;//统计投注彩票金额
	private long totalWinningBetTicketNumber;//统计中奖彩票数目
	private String totalWinningBetTicketMoney;//统计中奖彩票金额
	//以下是为订单详细列出的内容而定义的（也有为查询而定义的）
	private String orderId;//订单编号、用于查询订单也用于详细查看的参数
	//以下是为查询条件而定义的
	private Date beginTime;//开始时间
	private Date endTime;//结束时间
	private String ticketSequence;//彩票编号
	private Integer lotteryId;//彩种
	private String p_termNo_begin;//彩期开始
	private String p_termNo_end;//彩期结束
	private Map<Integer, String> lotteryList;//彩种列表
	private Integer ticketStatus;//彩票状态
	private Integer prizeResult;//中奖状态
	private Map<String, String> ticketStatusList;//彩票状态列表
	private Map<String, String> prizeResultList;//中奖状态列表
	private Map<String,String> planSource;
	private String accountId;
	private String planSourceId;
	private String userName;
	//以下是为彩票明细显示定义的
	private BetTicketAcountDomain betTicketDetail;//获得明细的实体
	private String show_error = "SUCESS";
	private int ex_code = -10000;
	private String ex_reason;
	
	//每一页的记录数
	private int					perPageNumber;
	//第几页
	private int					page;
	
	private PageList 				termList = new PageList();

	//总页数，使用static，避免在分页时重新查询总条数，还是有问题，多人同时查会改变总页数
	//目前每次都查询总条数；
	//可考虑的解决方案：
	//1-总条数放入session； 2-在数据库DAO中判断条件如果和上次相同则不做数据库查询直接返回
	private int totalNumber;
	@Override
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		
	}
	
	//时间转换成String
	public String Date2String(Timestamp param){
		if(null==param){
			return null;
		}
		return new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(param).toString().trim();
		
	}
	/**
	 * 
	 * Title: initPageParam<br>
	 * Description: <br>
	 *              <br>初始化化页面数据
	 */
	private void initPageParam(){
		try{
			//下拉框获得彩种
			this.setLotteryList(LotteryStaticDefine.getLotteryList());
			//下拉框获得彩票状态
			this.setTicketStatusList(LotteryStaticDefine.ticketStatusForQuery);
			//下拉框获得中奖状态
			this.setPrizeResultList(LotteryStaticDefine.ticketWinStatus);
			//下拉框投注渠道
			this.setPlanSource(LotteryStaticDefine.planSource);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	//彩票情况查询
	public String queryAllBetTicket(){
		initPageParam();
		BetTicketServiceInterf lotteryBetTicketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
		try{
			totalBetTicketNumber = lotteryBetTicketService.getBetTicketesNumber(orderId,ticketSequence,lotteryId,p_termNo_begin
					,p_termNo_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTime,endTime);
			totalBetTicketMoney = money2Double(lotteryBetTicketService.getBetTicketesMoney(orderId,ticketSequence,lotteryId,
					p_termNo_begin,p_termNo_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTime,endTime));
			List<BetTicketAcountDomain> list = null;
			if(perPageNumber==0){
				perPageNumber = 30;
			}
			if(page == 0){
				//第一次查询设置页数我为1
				page = 1;
			}
			totalNumber = lotteryBetTicketService.getBetTicketesCount(orderId,ticketSequence,lotteryId,p_termNo_begin
					,p_termNo_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTime,endTime);
			if(totalNumber<perPageNumber&&totalNumber!=0){
				perPageNumber = totalNumber;
			}
			list = lotteryBetTicketService.getBetTicketes(orderId,ticketSequence,lotteryId,p_termNo_begin
					,p_termNo_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTime,endTime, page, perPageNumber);
			termList.setPageNumber(page);
			termList.setPerPage(perPageNumber);
			termList.setFullListSize(totalNumber);
			termList.setList(list);
		}catch(LotteryException e){
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}
	
	
	
	
	//根据彩票编号去查看彩票情况
	public String betTicketDetail(){
		BetTicketServiceInterf lotteryBetTicketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
		try {
			betTicketDetail = lotteryBetTicketService.getBetTicketAcount(ticketSequence);
			ActionContext aContext = ActionContext.getContext();
			ValueStack valueStack = aContext.getValueStack(); 
			valueStack.set("lotteryName", LotteryTools.getLotteryName(betTicketDetail.getLotteryId()));
			valueStack.set("betType_str", LotteryTools.getPlayBetName(betTicketDetail.getLotteryId(), betTicketDetail.getPlayType(), 
					betTicketDetail.getBetType()));
			valueStack.set("playType_str", LotteryTools.getPlayTypeName(betTicketDetail.getLotteryId(), betTicketDetail.getPlayType()));
			valueStack.set("printTime_str", Date2String(betTicketDetail.getPrintTime()));
			valueStack.set("betAmount_str", money2Double(betTicketDetail.getBetAmount()));
			valueStack.set("preTaxPrize_str", money2Double(betTicketDetail.getPreTaxPrize()));
			valueStack.set("ticketStatus_str", LotteryStaticDefine.ticketStatusForWeb.get(betTicketDetail.getTicketStatus()+""));
			valueStack.set("prizeResult_str", LotteryStaticDefine.ticketWinStatus.get(betTicketDetail.getPrizeResult()+""));
			valueStack.set("planSource_str", LotteryStaticDefine.planSource.get(String.valueOf(betTicketDetail.getPlanSource())));
			valueStack.set("ticketTime_str", Date2String(betTicketDetail.getTicketTime()));
			valueStack.set("lastTicketTime_str", Date2String(betTicketDetail.getLastTicketTime()));
			valueStack.set("areaCode_str",AreaMapTools.getAreaName(betTicketDetail.getAreaCode()) );
			
		} catch (LotteryException e) {
			e.printStackTrace();
		}
		
		return "success";
	}
	//中奖彩票查询
	public String queryWinningBetTicket(){
		initPageParam();
		BetTicketServiceInterf lotteryBetTicketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
		try{
			totalWinningBetTicketNumber = lotteryBetTicketService.getWinningBetTicketesNumber(orderId,ticketSequence,lotteryId,p_termNo_begin
					,p_termNo_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTime,endTime);
			totalWinningBetTicketMoney = money2Double(lotteryBetTicketService.getWinningBetTicketesMoney(orderId,ticketSequence,lotteryId,
					p_termNo_begin,p_termNo_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTime,endTime));
			List<BetTicketAcountDomain> list = null;
			if(perPageNumber==0){
				perPageNumber = 30;
			}
			if(page == 0){
				//第一次查询设置页数我为1
				page = 1;
			}
			totalNumber = lotteryBetTicketService.getWinningBetTicketesCount(orderId,ticketSequence,lotteryId,p_termNo_begin
					,p_termNo_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTime,endTime);
			if(totalNumber<perPageNumber&&totalNumber!=0){
				perPageNumber = totalNumber;
			}
			list = lotteryBetTicketService.getWinningBetTicketes(orderId,ticketSequence,lotteryId,p_termNo_begin
					,p_termNo_end,ticketStatus,prizeResult,accountId,userName,planSourceId,beginTime,endTime, page, perPageNumber);
			termList.setPageNumber(page);
			termList.setPerPage(perPageNumber);
			termList.setFullListSize(totalNumber);
			termList.setList(list);
		}catch(LotteryException e){
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}

	//转换钱的方法
	private String money2Double(long money)   {  
		float betMoney = money/100;
		NumberFormat   nbf=NumberFormat.getInstance();   
		nbf.setMinimumFractionDigits(2);   
		nbf.setMaximumFractionDigits(2);     
		return   nbf.format(Double.parseDouble(Float.toString(betMoney)))+"元";  
	 }
	
	public int getPerPageNumber() {
		return perPageNumber;
	}


	public void setPerPageNumber(int perPageNumber) {
		this.perPageNumber = perPageNumber;
	}


	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public PageList getTermList() {
		return termList;
	}


	public void setTermList(PageList termList) {
		this.termList = termList;
	}


	public int getTotalNumber() {
		return totalNumber;
	}


	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}


	public Date getBeginTime() {
		return beginTime;
	}


	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public long getTotalBetTicketNumber() {
		return totalBetTicketNumber;
	}


	public void setTotalBetTicketNumber(long totalBetTicketNumber) {
		this.totalBetTicketNumber = totalBetTicketNumber;
	}


	public String getTotalBetTicketMoney() {
		return totalBetTicketMoney;
	}


	public void setTotalBetTicketMoney(String totalBetTicketMoney) {
		this.totalBetTicketMoney = totalBetTicketMoney;
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


	public String getTicketSequence() {
		return ticketSequence;
	}

	public void setTicketSequence(String ticketSequence) {
		this.ticketSequence = ticketSequence;
	}

	public BetTicketAcountDomain getBetTicketDetail() {
		return betTicketDetail;
	}

	public void setBetTicketDetail(BetTicketAcountDomain betTicketDetail) {
		this.betTicketDetail = betTicketDetail;
	}

	public Integer getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Integer lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getP_termNo_begin() {
		return p_termNo_begin;
	}

	public void setP_termNo_begin(String no_begin) {
		p_termNo_begin = no_begin;
	}

	public String getP_termNo_end() {
		return p_termNo_end;
	}

	public void setP_termNo_end(String no_end) {
		p_termNo_end = no_end;
	}

	public Map<Integer, String> getLotteryList() {
		return lotteryList;
	}

	public void setLotteryList(Map<Integer, String> lotteryList) {
		this.lotteryList = lotteryList;
	}

	public Integer getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(Integer ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public Integer getPrizeResult() {
		return prizeResult;
	}

	public void setPrizeResult(Integer prizeResult) {
		this.prizeResult = prizeResult;
	}

	public Map<String, String> getTicketStatusList() {
		return ticketStatusList;
	}

	public void setTicketStatusList(Map<String, String> ticketStatusList) {
		this.ticketStatusList = ticketStatusList;
	}

	public Map<String, String> getPrizeResultList() {
		return prizeResultList;
	}

	public void setPrizeResultList(Map<String, String> prizeResultList) {
		this.prizeResultList = prizeResultList;
	}

	public long getTotalWinningBetTicketNumber() {
		return totalWinningBetTicketNumber;
	}

	public void setTotalWinningBetTicketNumber(long totalWinningBetTicketNumber) {
		this.totalWinningBetTicketNumber = totalWinningBetTicketNumber;
	}

	public String getTotalWinningBetTicketMoney() {
		return totalWinningBetTicketMoney;
	}

	public void setTotalWinningBetTicketMoney(String totalWinningBetTicketMoney) {
		this.totalWinningBetTicketMoney = totalWinningBetTicketMoney;
	}
	public Map<String, String> getPlanSource() {
		return planSource;
	}
	public void setPlanSource(Map<String, String> planSource) {
		this.planSource = planSource;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getPlanSourceId() {
		return planSourceId;
	}
	public void setPlanSourceId(String planSourceId) {
		this.planSourceId = planSourceId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}




}
