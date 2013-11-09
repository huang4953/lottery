/**
 * Title: AccountAction.java
 * @Package com.success.lottery.web.admin.action
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-6-2 下午04:51:08
 * @version V1.0
 */
package com.success.lottery.web.admin.action;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.account.model.AccountTransactionModel;
import com.success.lottery.account.model.BillOrderModel;
import com.success.lottery.account.model.IPSOrderModel;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.account.service.BaseDrawMoneyService;
import com.success.lottery.account.service.BillOrderService;
import com.success.lottery.account.service.IPSOrderService;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.util.AreaMapTools;
import com.success.lottery.web.admin.webbean.PageList;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.web.admin.action
 * AccountAction.java
 * AccountAction
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-6-2 下午04:51:08
 * 
 */

public class AccountAction extends ActionSupport implements SessionAware {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -819283640520481539L;
	private Map						session;
	
	private int ex_code;
	private String ex_reason;
	
	private Map<String,String> areaCodeList;
	
	private Map<Integer,String> transTypeList;
	
	private String transType;
	
	private String areaCode;
	
	private String accountId;
	
	private String accountUsrName;
	
	private String  begin_date;
	private String  end_date;
	
	private long accountA =0L;
	private String accountAprize;
	private long accountB =0L;
	private String accountBprize;
	private long accountCD =0L;
	private String accountCDprize;
	
	private String ipsStatus;
	
	private String billStatus;
	
	private Map<Integer,String> ipsStatusList;
	
	private Map<Integer,String> billStatusList;
	
	
	/*
	 * 分页参数
	 */
	private String query;
	
	//每一页的记录数
	private int					perPageNumber;
	//第几页
	private int					page;
	private int totalNumber;
	private static int defaultPerPageNumber = 20;
	
	private PageList               accountShowList = new PageList();
	private PageList               accountTransList = new PageList();
	
	private PageList               ipsList = new PageList();
	
	public String accountShow(){
		try {
			this.areaCodeList = AreaMapTools.getAreaCodeList();
			
			BaseDrawMoneyService drawMoneyService = ApplicationContextUtils.getService("baseDrawMoneyService", BaseDrawMoneyService.class);
			List<UserAccountModel> retAccountList = null;
			if (this.page == 0) {
				this.page = 1;
			}
			if (this.perPageNumber == 0) {
				this.perPageNumber = defaultPerPageNumber;
			}
			String areaCode = (StringUtils.isEmpty(this.getAreaCode()) || this.getAreaCode().equals("-1")) ? null : this.getAreaCode();
			
			this.totalNumber = drawMoneyService.queryAdjustAccountUserCount(this.getAccountId(), this.getAccountUsrName(), areaCode);
			
			retAccountList = drawMoneyService.queryAdjustAccountUserInfo(this.getAccountId(), this.getAccountUsrName(), areaCode, this.page, this.perPageNumber);
			this.accountShowList.setPageNumber(this.page);
			this.accountShowList.setPerPage(this.perPageNumber);
			this.accountShowList.setFullListSize(this.totalNumber);
			this.accountShowList.setList(retAccountList);
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
	 * Title: accountTransactionShow<br>
	 * Description: <br>
	 *              <br>账户交易变动查询
	 * @return
	 */
	public String accountTransactionShow(){
		try{
			this.transTypeList = LotteryStaticDefine.transactionType;
			
			if (this.page == 0) {
				this.page = 1;
			}
			if (this.perPageNumber == 0) {
				this.perPageNumber = defaultPerPageNumber;
			}
			
			AccountService accountService = ApplicationContextUtils.getService("accountService", AccountService.class);
			
			int startNumber = 0;
			int endNumber = Integer.MAX_VALUE;
			if(this.page > 0 && this.perPageNumber > 0){
				startNumber = this.perPageNumber * (this.page - 1);
				endNumber = this.page * this.perPageNumber;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = StringUtils.isEmpty(this.getBegin_date()) ? null : sdf.parse(this.getBegin_date());
			Date endDate = StringUtils.isEmpty(this.getEnd_date()) ? null : sdf.parse(this.getEnd_date());
			
			List<AccountTransactionModel> result = accountService.getUserTransactiones(this.getAccountId(), startDate,
							endDate,Integer.parseInt(this.getTransType()==null?"-1":this.getTransType()),startNumber, endNumber);
			
			
			
			if(result != null && !result.isEmpty()){
				AccountTransactionModel totalCountDomain = result.get(0);
				this.totalNumber = totalCountDomain.getTransactionType();
				this.accountA = totalCountDomain.getFundsAccount();
				this.accountAprize = this.formatMoney(totalCountDomain.getPrizeAccount());
				this.accountB = totalCountDomain.getFrozenAccount();
				this.accountBprize = this.formatMoney(totalCountDomain.getCommisionAccount());
				this.accountCD = totalCountDomain.getAdvanceAccount() + totalCountDomain.getOtherAccount1();
				this.accountCDprize = this.formatMoney(totalCountDomain.getAwardAccount());
				result.remove(0);
			}
			
			this.accountTransList.setPageNumber(this.page);
			this.accountTransList.setPerPage(this.perPageNumber);
			this.accountTransList.setFullListSize(this.totalNumber);
			this.accountTransList.setList(result);
			
		}catch(LotteryException e){
			e.printStackTrace();
			this.setEx_code(99999);
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
	
	public String billInfoShow(){
		try{
			
			this.billStatusList = LotteryStaticDefine.billOrderStatus;
			
			if (this.page == 0) {
				this.page = 1;
			}
			if (this.perPageNumber == 0) {
				this.perPageNumber = defaultPerPageNumber;
			}
			BillOrderService billOrderService=ApplicationContextUtils.getService("billService",BillOrderService.class);
			IPSOrderService ipsService = ApplicationContextUtils.getService("ipsService", IPSOrderService.class);
			
			int startNumber = 0;
			int endNumber = Integer.MAX_VALUE;
			if(this.page > 0 && this.perPageNumber > 0){
				startNumber = this.perPageNumber * (this.page - 1);
				endNumber = this.page * this.perPageNumber;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = StringUtils.isEmpty(this.getBegin_date()) ? null : sdf.parse(this.getBegin_date());
			Date endDate = StringUtils.isEmpty(this.getEnd_date()) ? null : sdf.parse(this.getEnd_date());
			List<BillOrderModel> results=billOrderService.getIPSOrderes(StringUtils.isEmpty(this.getAccountId()) ? null : this.getAccountId().trim(),
					startDate, endDate, Integer.parseInt(this.getBillStatus() == null ? "-1" : this.getBillStatus()),startNumber, endNumber);
			if(results!=null&&results.size()>0)
			{
				this.totalNumber = results.get(0).getMaxCount();
				this.accountA = results.get(0).getOrderStatus();
				this.accountAprize = this.formatMoney(results.get(0).getUserId());
				this.accountB =results.get(0).getOrderAmount();
				this.accountBprize = this.formatMoney(results.get(0).getCheckedStatus());
				results.remove(0);
			}else
			{
				System.out.println("没有记录");
			}
			this.ipsList.setPageNumber(this.page);
			this.ipsList.setPerPage(this.perPageNumber);
			this.ipsList.setFullListSize(this.totalNumber);
			this.ipsList.setList(results);
			
		}catch(LotteryException e){
			e.printStackTrace();
			this.setEx_code(99999);
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
	public String ipsInfoShow(){
		try{
			
			this.ipsStatusList = LotteryStaticDefine.ipsOrderStatus;
			
			if (this.page == 0) {
				this.page = 1;
			}
			if (this.perPageNumber == 0) {
				this.perPageNumber = defaultPerPageNumber;
			}
			
			IPSOrderService ipsService = ApplicationContextUtils.getService("ipsService", IPSOrderService.class);
			
			int startNumber = 0;
			int endNumber = Integer.MAX_VALUE;
			if(this.page > 0 && this.perPageNumber > 0){
				startNumber = this.perPageNumber * (this.page - 1);
				endNumber = this.page * this.perPageNumber;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = StringUtils.isEmpty(this.getBegin_date()) ? null : sdf.parse(this.getBegin_date());
			Date endDate = StringUtils.isEmpty(this.getEnd_date()) ? null : sdf.parse(this.getEnd_date());
			
			List<IPSOrderModel> result = ipsService.getIPSOrderes(StringUtils.isEmpty(this.getAccountId()) ? null : this.getAccountId().trim(),
					startDate, endDate, Integer.parseInt(this.getIpsStatus() == null ? "-1" : this.getIpsStatus()),startNumber, endNumber);
			
			
			if(result != null && !result.isEmpty()){
				IPSOrderModel totalCountDomain = result.get(0);
				this.totalNumber = totalCountDomain.getOrderDate();
				this.accountA = totalCountDomain.getOrderStatus();
				this.accountAprize = this.formatMoney(totalCountDomain.getCheckedStatus());
				this.accountB = totalCountDomain.getAmount();
				this.accountBprize = this.formatMoney(totalCountDomain.getUserId());
				result.remove(0);
			}
			
			this.ipsList.setPageNumber(this.page);
			this.ipsList.setPerPage(this.perPageNumber);
			this.ipsList.setFullListSize(this.totalNumber);
			this.ipsList.setList(result);
			
		}catch(LotteryException e){
			e.printStackTrace();
			this.setEx_code(99999);
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
	
	public PageList getAccountShowList() {
		return accountShowList;
	}

	public void setAccountShowList(PageList accountShowList) {
		this.accountShowList = accountShowList;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Map<String, String> getAreaCodeList() {
		return areaCodeList;
	}

	public void setAreaCodeList(Map<String, String> areaCodeList) {
		this.areaCodeList = areaCodeList;
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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountUsrName() {
		return accountUsrName;
	}

	public void setAccountUsrName(String accountUsrName) {
		this.accountUsrName = accountUsrName;
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

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Map<Integer, String> getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(Map<Integer, String> transTypeList) {
		this.transTypeList = transTypeList;
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

	public PageList getAccountTransList() {
		return accountTransList;
	}

	public void setAccountTransList(PageList accountTransList) {
		this.accountTransList = accountTransList;
	}

	public long getAccountA() {
		return accountA;
	}

	public void setAccountA(long accountA) {
		this.accountA = accountA;
	}

	public long getAccountB() {
		return accountB;
	}

	public void setAccountB(long accountB) {
		this.accountB = accountB;
	}

	public long getAccountCD() {
		return accountCD;
	}

	public void setAccountCD(long accountCD) {
		this.accountCD = accountCD;
	}

	public static int getDefaultPerPageNumber() {
		return defaultPerPageNumber;
	}

	public static void setDefaultPerPageNumber(int defaultPerPageNumber) {
		AccountAction.defaultPerPageNumber = defaultPerPageNumber;
	}
	public Map getSession() {
		return session;
	}
	public void setSession(Map session) {
		this.session = session;
	}
	
	private  String formatMoney(long money) {
		BigDecimal db = new BigDecimal(money);
		return new DecimalFormat("###,###,###.##").format(db.divide(new BigDecimal(100)));
	}
	public String getAccountAprize() {
		return accountAprize;
	}
	public void setAccountAprize(String accountAprize) {
		this.accountAprize = accountAprize;
	}
	public String getAccountBprize() {
		return accountBprize;
	}
	public void setAccountBprize(String accountBprize) {
		this.accountBprize = accountBprize;
	}
	public String getAccountCDprize() {
		return accountCDprize;
	}
	public void setAccountCDprize(String accountCDprize) {
		this.accountCDprize = accountCDprize;
	}
	public String getIpsStatus() {
		return ipsStatus;
	}
	public void setIpsStatus(String ipsStatus) {
		this.ipsStatus = ipsStatus;
	}
	public Map<Integer, String> getIpsStatusList() {
		return ipsStatusList;
	}
	public void setIpsStatusList(Map<Integer, String> ipsStatusList) {
		this.ipsStatusList = ipsStatusList;
	}
	public PageList getIpsList() {
		return ipsList;
	}
	public void setIpsList(PageList ipsList) {
		this.ipsList = ipsList;
	}
	public Map<Integer, String> getBillStatusList() {
		return billStatusList;
	}
	public void setBillStatusList(Map<Integer, String> billStatusList) {
		this.billStatusList = billStatusList;
	}
	public String getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

}
