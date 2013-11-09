package com.success.lottery.web.customer.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.success.lottery.account.model.AccountTransactionModel;
import com.success.lottery.account.model.DrawMoneyDomain;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.account.service.BaseDrawMoneyService;
import com.success.lottery.business.domain.BusCoopPlanDomain;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.web.formbean.Page;
import com.success.lottery.web.trade.action.supper.LotteryWebPageBaseActon;
import com.success.utils.ApplicationContextUtils;

@SuppressWarnings("serial")
public class CustomerAccount extends LotteryWebPageBaseActon {
	private int pagesize=9;
	private Date begintime;
	private Date endtime;
	private int ChannelStatus=-1;
	private int lotteryId=-1;
	private String term;
	private String planStatus;
	private String winStatus;
	
	private List<BusCoopPlanDomain>  coopList;
	private int cznumb;
	private long moneynumb;
	private long fundsAccount;
	private long frozenAccount;
	private long commisionAccount;
	private long advanceAccount;
	private long awardAccount;
	public String details(){
		if(null == super.page)
			super.page = new Page(pagesize);
		else
			page.setPageSize(pagesize);
		UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
		AccountService accountService = ApplicationContextUtils.getService("accountService",AccountService.class);
		try {
			List<AccountTransactionModel> resultList = accountService.getUserTransactiones(customer.getUserId(), begintime, endtime, ChannelStatus, page.getFirst(), page.getPageSize());
			if(null!=resultList){
				AccountTransactionModel obj = resultList.get(0);
				/*		
				  * 			transactionType：存放的为本次查询到的总条数，如没有查询到也可获得该值为0;
	 * 			fundsAccount：收入交易笔数；
	 * 			prizeAccount：收入交易金额
	 * 			frozenAccount：支出交易笔数
	 * 			commisionAccount：支出交易金额
	 * 			advanceAccount：冻结交易笔数
	 * 			awardAccount：冻结交易金额
	 * 			otherAccount1：解冻交易笔数
	 * 			otherAccount2：解冻交易金额*/	
				
				cznumb = (int)obj.getTransactionType();//总条数
				moneynumb = obj.getPrizeAccount();
				fundsAccount = obj.getFundsAccount();
				frozenAccount =obj.getFrozenAccount();
				commisionAccount = obj.getCommisionAccount();
				advanceAccount= obj.getAdvanceAccount()+obj.getOtherAccount1();
				awardAccount=(obj.getAwardAccount());
				
				
				page.setTotalCount(obj.getTransactionType());
				resultList.remove(0);
				page.setResult(resultList);
			}else
				page.setTotalCount(0);
		} catch (LotteryException e) {
			page.setTotalCount(0);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String  recharge(){
		if(null == super.page)
			super.page = new Page(pagesize);
		else
			page.setPageSize(pagesize);
		UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
		AccountService accountService = ApplicationContextUtils.getService("accountService",AccountService.class);
		try {
			List<AccountTransactionModel> resultList = accountService.getUser11001Transactiones(customer.getUserId(), begintime, endtime, ChannelStatus, page.getFirst(), page.getPageSize());
			if(null!=resultList){
				AccountTransactionModel obj = resultList.get(0);
				cznumb = obj.getTransactionType();//总计条数
				moneynumb=obj.getAmount();//总金额
				page.setTotalCount(cznumb);
				resultList.remove(0);
				page.setResult(resultList);
			}else
				page.setTotalCount(0);
		} catch (LotteryException e) {
			page.setTotalCount(0);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String prize(){
		if(null == super.page)
			super.page = new Page(pagesize);
		else
			page.setPageSize(pagesize);
		UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
		BetPlanOrderServiceInterf  betPlanOrderServiceInterf  = ApplicationContextUtils.getService("lotteryBetOrderService",BetPlanOrderServiceInterf.class);
		try {
			
			List<BetOrderDomain> resultList = betPlanOrderServiceInterf.getUserCashedOrders(customer.getUserId(),lotteryId, term,begintime, endtime, page.getFirst(), page.getPageSize());
			if(null!=resultList){
				BetOrderDomain obj = resultList.get(0);
				cznumb = obj.getPlanSource();
				moneynumb = obj.getPreTaxPrize();
				page.setTotalCount(obj.getPlanSource());
				resultList.remove(0);
				page.setResult(resultList);
			}else
				page.setTotalCount(0);
		} catch (LotteryException e) {
			page.setTotalCount(0);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String   withdraw(){
		if(null == super.page)
			super.page = new Page(pagesize);
		else
			page.setPageSize(pagesize);
		UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
		BaseDrawMoneyService baseDrawMoneyService = ApplicationContextUtils.getService("baseDrawMoneyService",BaseDrawMoneyService.class);
		try {
			List<DrawMoneyDomain> resultList = baseDrawMoneyService.getUserDrawMoney(customer.getUserId(), begintime, endtime, ChannelStatus, page.getFirst(), page.getPageSize());
			if(null!=resultList){
				DrawMoneyDomain obj = resultList.get(0);
				cznumb = obj.getDrawstatus();
				moneynumb = obj.getUserid();
				
				fundsAccount = obj.getDrawmoney();
				frozenAccount = obj.getDrawtype();
				
				advanceAccount = obj.getProcedurefee();
				awardAccount= obj.getOperatetype();
				page.setTotalCount(Integer.parseInt(obj.getDrawid()));
				resultList.remove(0);
				page.setResult(resultList);
			}else
				page.setTotalCount(0);
		} catch (LotteryException e) {
			page.setTotalCount(0);
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String initiatedChipped(){
		LotteryManagerInterf managerService=ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
		UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
		SimpleDateFormat datefomat=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=Calendar.getInstance();
		if(null == super.page)
			super.page = new Page(pagesize);
		else
			page.setPageSize(pagesize);
		if(lotteryId==0)
			lotteryId=-1;
		if(null==term||"".equals(term))
			term="-1";
		if(null==getPlanStatus()||getPlanStatus().equals(""))
			setPlanStatus("-1");
		if(null==getWinStatus()||"".equals(getWinStatus()))
			setWinStatus("-1");
		if(null==this.getEndtime())
			this.setEndtime(calendar.getTime());
			calendar.set(Calendar.DAY_OF_YEAR, -7);
		if(null==this.getBegintime())
			this.setBegintime(calendar.getTime());
		
		try {
			coopList=managerService.getCoopPlanCreateForWeb(0, lotteryId, term,Integer.parseInt(customer.getUserId()+""), Integer.parseInt(this.getPlanStatus().trim()), Integer.parseInt(this.getWinStatus().trim()), datefomat.format(this.getBegintime()), datefomat.format(this.getEndtime()), page.getPageNo(), page.getPageSize());
			int count=managerService.getCoopPlanCreateForWebCount(0, lotteryId, term,Integer.parseInt(customer.getUserId()+""), Integer.parseInt(this.getPlanStatus().trim()), Integer.parseInt(this.getWinStatus().trim()), datefomat.format(this.getBegintime()), datefomat.format(this.getEndtime()));
			if(null!=coopList){
				page.setTotalCount(count);
				page.setResult(coopList);
			}else
				page.setTotalCount(0);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String participatingChipped(){
		LotteryManagerInterf managerService=ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
		UserAccountModel customer = (UserAccountModel) super.getSession().get(SESSION_CUSTOMER_KEY);
		SimpleDateFormat datefomat=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=Calendar.getInstance();
		if(null == super.page)
			super.page = new Page(pagesize);
		else
			page.setPageSize(pagesize);
		if(lotteryId==0)
			lotteryId=-1;
		if(null==term||"".equals(term))
			term="-1";
		if(null==getPlanStatus()||getPlanStatus().equals(""))
			setPlanStatus("-1");
		if(null==getWinStatus()||"".equals(getWinStatus()))
			setWinStatus("-1");
		if(null==this.getEndtime())
			this.setEndtime(calendar.getTime());
			calendar.set(Calendar.DAY_OF_YEAR, -7);
		if(null==this.getBegintime())
			this.setBegintime(calendar.getTime());
		
		try {
			coopList=managerService.getCoopPlanCreateForWeb(1, lotteryId, term,Integer.parseInt(customer.getUserId()+""), Integer.parseInt(this.getPlanStatus().trim()), Integer.parseInt(this.getWinStatus().trim()), datefomat.format(this.getBegintime()), datefomat.format(this.getEndtime()), page.getPageNo(), page.getPageSize());
			int count=managerService.getCoopPlanCreateForWebCount(1, lotteryId, term,Integer.parseInt(customer.getUserId()+""), Integer.parseInt(this.getPlanStatus().trim()), Integer.parseInt(this.getWinStatus().trim()), datefomat.format(this.getBegintime()), datefomat.format(this.getEndtime()));
			if(null!=coopList){
				page.setTotalCount(count);
				page.setResult(coopList);
			}else
				page.setTotalCount(0);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public int getChannelStatus() {
		return ChannelStatus;
	}
	public void setChannelStatus(int channelStatus) {
		ChannelStatus = channelStatus;
	}
	public int getCznumb() {
		return cznumb;
	}
	public long getMoneynumb() {
		return moneynumb;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public long getFundsAccount() {
		return fundsAccount;
	}
	public long getFrozenAccount() {
		return frozenAccount;
	}
	public long getCommisionAccount() {
		return commisionAccount;
	}
	public long getAdvanceAccount() {
		return advanceAccount;
	}
	public long getAwardAccount() {
		return awardAccount;
	}
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getTerm() {
		return term;
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
	public List<BusCoopPlanDomain> getCoopList() {
		return coopList;
	}
	public void setCoopList(List<BusCoopPlanDomain> coopList) {
		this.coopList = coopList;
	}
	public String getPlanStatus() {
		return planStatus;
	}
	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}
	public String getWinStatus() {
		return winStatus;
	}
	public void setWinStatus(String winStatus) {
		this.winStatus = winStatus;
	}
}
