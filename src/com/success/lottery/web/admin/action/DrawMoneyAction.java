/**
 * Title: BetOrderInfoAction.java
 * @Package com.success.lottery.web.admin.action
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-5-25 上午11:13:34
 * @version V1.0
 */
package com.success.lottery.web.admin.action;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.success.admin.user.domain.AdminUser;
import com.success.lottery.account.model.DrawMoneyDomain;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.account.service.BaseDrawMoneyService;
import com.success.lottery.business.domain.BusBetOrderCountDomain;
import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.business.service.impl.CashPrizeService;
import com.success.lottery.business.service.interf.CashPrizeInterf;
import com.success.lottery.business.service.interf.DispatchPrizeInterf;
import com.success.lottery.business.service.interf.DrawMoneyInterf;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.business.service.interf.PrizeInnerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.util.AreaMapTools;
import com.success.lottery.util.LotteryTools;
import com.success.lottery.web.admin.webbean.PageList;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.web.admin.action
 * BetOrderInfoAction.java
 * BetOrderInfoAction
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-5-25 上午11:13:34
 * 
 */

public class DrawMoneyAction extends ActionSupport implements SessionAware{
	
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -7293411988259745647L;

	private Map						session;
	
	private int ex_code;
	private String ex_reason;
	private String show_error = "SUCESS";
	/*
	 * 界面初始化参数
	 */
	private Map<Integer, String> lotteryList;
	/*
	 * 界面回传参数
	 */
	private String province;
	private String city;
	private String accountId;
	private String begin_date;
	private String end_date;
	private String drawName;
	private String opUser;
	
	private String drawStatus;
	
	private String drawId;
	
	private String areaCode;
	
	/*
	 * 提现界面数据
	 */
	private  List<DrawMoneyDomain> drawPrizeList;
	
	private DrawMoneyDomain drawPrizeDomain;
	
	private String userMobilePhone;
	private String fundsaccount;
	private String prizeaccount;
	private String frozenaccount;
	private String idCard;
	
	private String drawPrizeMoney;
	
	private String drawType;
	
	private String dealReason;
	
	private String drawDealStatus;
	
	private String accountStatusName;
	
	private String accountUsrName;
	
	private String adjustMoney;
	
	private String procedureFee;
	
	
	
	/*
	 * 分页参数
	 */
	private String query;
	
	//每一页的记录数
	private int					perPageNumber;
	//第几页
	private int					page;
	
	private PageList 				drawHisList = new PageList();
	
	private PageList               accountList = new PageList();
	
	private PageList               accountHisList = new PageList();
	
	

	//总页数，使用static，避免在分页时重新查询总条数，还是有问题，多人同时查会改变总页数
	//目前每次都查询总条数；
	//可考虑的解决方案：
	//1-总条数放入session； 2-在数据库DAO中判断条件如果和上次相同则不做数据库查询直接返回
	private int totalNumber;
	
	private static int defaultPerPageNumber = 20;
	
	/**
	 * 
	 * Title: canDraPrizeShow<br>
	 * Description: <br>
	 *              <br>提现申请列表
	 * @return
	 */
	public String canDraPrizeShow(){
		try{
			BaseDrawMoneyService drawMoneyService = ApplicationContextUtils.getService("baseDrawMoneyService", BaseDrawMoneyService.class);
			this.drawPrizeList = drawMoneyService.queryDrawMoneyInfo(this
					.getProvince(), this.getCity(), this.getAccountId(), this
					.getDrawName(), this.getBegin_date(), this.getEnd_date());
			
		}catch(LotteryException e){
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
	 * Title: PrizeHistoryShow<br>
	 * Description: <br>
	 *              <br>提现列表查询
	 * @return
	 */
	public String PrizeHistoryShow(){
		try {
			BaseDrawMoneyService drawMoneyService = ApplicationContextUtils.getService("baseDrawMoneyService", BaseDrawMoneyService.class);
			List<DrawMoneyDomain> retDrawList = null;
			if (this.page == 0) {
				this.page = 1;
			}
			if (this.perPageNumber == 0) {
				this.perPageNumber = defaultPerPageNumber;
			}
			
			this.totalNumber = drawMoneyService.queryDrawMoneyHisCount(this
					.getProvince(), this.getCity(), this.getAccountId(), this
					.getDrawName(), this.getBegin_date(), this.getEnd_date(),
					this.getOpUser(), this.getDrawStatus());
			
			retDrawList = drawMoneyService.queryDrawMoneyHisInfo(this.getProvince(), this.getCity(), this.getAccountId(), this
					.getDrawName(), this.getBegin_date(), this.getEnd_date(),
					this.getOpUser(), this.getDrawStatus(), this.page, this.perPageNumber);
			
			this.drawHisList.setPageNumber(this.page);
			this.drawHisList.setPerPage(this.perPageNumber);
			this.drawHisList.setFullListSize(this.totalNumber);
			this.drawHisList.setList(retDrawList);
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
	 * Title: betOrderInfoShow<br>
	 * Description: <br>
	 *              <br>显示提现确认页面
	 * @return String
	 */
	public String drawConfirm(){
		
		try {
			BaseDrawMoneyService drawMoneyService = ApplicationContextUtils.getService("baseDrawMoneyService", BaseDrawMoneyService.class);
			AccountService accountService = ApplicationContextUtils.getService("accountService", AccountService.class);
			/*
			 * 得到提现记录
			 */
			this.drawPrizeDomain = drawMoneyService.queryDrawMoneyInfo(this.getDrawId());
			
			if(this.drawPrizeDomain != null){
				UserAccountModel userDomain = accountService.getUserInfoNoStatus(this.drawPrizeDomain.getUserid());
				this.userMobilePhone = userDomain.getMobilePhone();
				this.fundsaccount = this.formatMoney(userDomain.getFundsAccount());
				this.prizeaccount = this.formatMoney(userDomain.getPrizeAccount());
				this.frozenaccount = this.formatMoney(userDomain.getFrozenAccount());
				this.accountStatusName = LotteryStaticDefine.userStatus.get(String.valueOf(userDomain.getStatus()));
				this.idCard = userDomain.getIdCard();
				
			}
			
			this.drawPrizeMoney = this.formatMoney(this.drawPrizeDomain.getDrawmoney()); 
			this.procedureFee = this.formatMoney(this.drawPrizeDomain.getProcedurefee());
			
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
	
	public String drawPrizeDeal(){
		try{
			DrawMoneyInterf drawService = ApplicationContextUtils.getService("busDrawMoneyService", DrawMoneyInterf.class);
			AdminUser adminUser = (AdminUser)this.session.get("tlt.loginuser");
			if(adminUser == null){
				throw new LotteryException(99999,"会话过期请重新登录!");
			}
			String drawOpUser = adminUser.getLoginName();
			if("AGREE".equals(this.getDrawType())){
				drawService.agreeDrawPrizeMoney(this.getDrawId(), drawOpUser, this.getDealReason());
			}else if("REJECT".equals(this.getDrawType())){
				drawService.rejectDrawPrizeMoney(this.getDrawId(), drawOpUser, this.getDealReason());
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
	 * Title: showDrawDetail<br>
	 * Description: <br>
	 *              <br>提现记录详细信息界面
	 * @return
	 */
	public String showDrawDetail(){
		try {
			BaseDrawMoneyService drawMoneyService = ApplicationContextUtils.getService("baseDrawMoneyService", BaseDrawMoneyService.class);
			AccountService accountService = ApplicationContextUtils.getService("accountService", AccountService.class);
			/*
			 * 得到提现记录
			 */
			this.drawPrizeDomain = drawMoneyService.queryDrawMoneyInfo(this.getDrawId());
			
			if(this.drawPrizeDomain != null){
				UserAccountModel userDomain = accountService.getUserInfoNoStatus(this.drawPrizeDomain.getUserid());
				this.userMobilePhone = userDomain.getMobilePhone();
				this.fundsaccount = this.formatMoney(userDomain.getFundsAccount());
				this.prizeaccount = this.formatMoney(userDomain.getPrizeAccount());
				this.frozenaccount = this.formatMoney(userDomain.getFrozenAccount());
				this.accountStatusName = LotteryStaticDefine.userStatus.get(String.valueOf(userDomain.getStatus()));
				this.idCard = userDomain.getIdCard();
			}
			
			this.drawPrizeMoney = this.formatMoney(this.drawPrizeDomain.getDrawmoney());
			this.procedureFee = this.formatMoney(this.drawPrizeDomain.getProcedurefee());
			this.drawDealStatus = LotteryStaticDefine.drawStatus.get(this.drawPrizeDomain.getDrawstatus());
			
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
	 * Title: adjustAccountShow<br>
	 * Description: <br>
	 *              <br>本金调整显示界面
	 * @return
	 */
	public String adjustAccountShow(){
		try {
			BaseDrawMoneyService drawMoneyService = ApplicationContextUtils.getService("baseDrawMoneyService", BaseDrawMoneyService.class);
			List<UserAccountModel> retAccountList = null;
			if (this.page == 0) {
				this.page = 1;
			}
			if (this.perPageNumber == 0) {
				this.perPageNumber = defaultPerPageNumber;
			}
			
			this.totalNumber = drawMoneyService.queryAdjustAccountUserCount(this.getAccountId(), null, null);
			/*
			retDrawList = drawMoneyService.queryDrawMoneyHisInfo(this.getProvince(), this.getCity(), this.getAccountId(), this
					.getDrawName(), this.getBegin_date(), this.getEnd_date(),
					this.getOpUser(), this.getDrawStatus(), this.page, this.perPageNumber);
					*/
			retAccountList = drawMoneyService.queryAdjustAccountUserInfo(this.getAccountId(), null, null, this.page, this.perPageNumber);
			this.accountList.setPageNumber(this.page);
			this.accountList.setPerPage(this.perPageNumber);
			this.accountList.setFullListSize(this.totalNumber);
			this.accountList.setList(retAccountList);
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
	 * Title: adjustAccountHis<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @return
	 */
	public String adjustAccountHis(){
		try {
			BaseDrawMoneyService drawMoneyService = ApplicationContextUtils.getService("baseDrawMoneyService", BaseDrawMoneyService.class);
			List<DrawMoneyDomain> retAccountHisList = null;
			if (this.page == 0) {
				this.page = 1;
			}
			if (this.perPageNumber == 0) {
				this.perPageNumber = defaultPerPageNumber;
			}
			
			this.totalNumber = drawMoneyService.queryDrawAccountHisCount(this.getAccountId(), this.getAccountUsrName(), this.getBegin_date(), this.getEnd_date(), this.getOpUser());
			
			retAccountHisList = drawMoneyService.queryDrawAccountHisInfo(this.getAccountId(), this.getAccountUsrName(), this.getBegin_date(), this.getEnd_date(), this.getOpUser(), this.page, this.perPageNumber);
			this.accountHisList.setPageNumber(this.page);
			this.accountHisList.setPerPage(this.perPageNumber);
			this.accountHisList.setFullListSize(this.totalNumber);
			this.accountHisList.setList(retAccountHisList);
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
	
	public String adjustConfirm(){
		try {
			AccountService accountService = ApplicationContextUtils.getService("accountService", AccountService.class);
			
			UserAccountModel userDomain = accountService.getUserInfoNoStatus(Long.parseLong(this.getAccountId()));
			
			this.userMobilePhone = userDomain.getMobilePhone();
			this.fundsaccount = this.formatMoney(userDomain.getFundsAccount());
			this.prizeaccount = this.formatMoney(userDomain.getPrizeAccount());
			this.frozenaccount = this.formatMoney(userDomain.getFrozenAccount());
			this.accountStatusName = LotteryStaticDefine.userStatus.get(String.valueOf(userDomain.getStatus()));
			this.accountUsrName = userDomain.getRealName();
			
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
	
	public String adjustAccountDeal(){
		try{
			DrawMoneyInterf drawService = ApplicationContextUtils.getService("busDrawMoneyService", DrawMoneyInterf.class);
			AdminUser adminUser = (AdminUser)this.session.get("tlt.loginuser");
			if(adminUser == null){
				throw new LotteryException(99999,"会话过期请重新登录!");
			}
			String drawOpUser = adminUser.getLoginName();
			
			String adjustType = this.getAdjustMoney().startsWith("-") ? "B" : "A";
			
			int adjustMoneyFei = new BigDecimal(this.adjustMoney).abs().multiply(new BigDecimal("100")).intValue();
			
			this.drawId = drawService.adjustAccount(Integer.parseInt(this.getAccountId()), adjustType, adjustMoneyFei, this.getDealReason(), drawOpUser);
			
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
	
	private  String formatMoney(long money) {
		BigDecimal db = new BigDecimal(money);
		return new DecimalFormat("###,###,###.##").format(db.divide(new BigDecimal(100)));
	}
	
	public Map<Integer, String> getLotteryList() {
		return this.lotteryList;
	}

	public void setLotteryList(Map<Integer, String> lotteryList) {
		this.lotteryList = lotteryList;
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
	

	
	public String getShow_error() {
		return show_error;
	}
	public void setShow_error(String show_error) {
		this.show_error = show_error;
	}
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public List<DrawMoneyDomain> getDrawPrizeList() {
		return drawPrizeList;
	}
	public void setDrawPrizeList(List<DrawMoneyDomain> drawPrizeList) {
		this.drawPrizeList = drawPrizeList;
	}
	public String getDrawName() {
		return drawName;
	}
	public void setDrawName(String drawName) {
		this.drawName = drawName;
	}

	public PageList getDrawHisList() {
		return drawHisList;
	}

	public void setDrawHisList(PageList drawHisList) {
		this.drawHisList = drawHisList;
	}

	public String getOpUser() {
		return opUser;
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	public String getDrawStatus() {
		return drawStatus;
	}

	public void setDrawStatus(String drawStatus) {
		this.drawStatus = drawStatus;
	}

	public DrawMoneyDomain getDrawPrizeDomain() {
		return drawPrizeDomain;
	}

	public void setDrawPrizeDomain(DrawMoneyDomain drawPrizeDomain) {
		this.drawPrizeDomain = drawPrizeDomain;
	}

	public String getDrawId() {
		return drawId;
	}

	public void setDrawId(String drawId) {
		this.drawId = drawId;
	}

	public String getFrozenaccount() {
		return frozenaccount;
	}

	public void setFrozenaccount(String frozenaccount) {
		this.frozenaccount = frozenaccount;
	}

	public String getFundsaccount() {
		return fundsaccount;
	}

	public void setFundsaccount(String fundsaccount) {
		this.fundsaccount = fundsaccount;
	}

	public String getPrizeaccount() {
		return prizeaccount;
	}

	public void setPrizeaccount(String prizeaccount) {
		this.prizeaccount = prizeaccount;
	}

	public String getUserMobilePhone() {
		return userMobilePhone;
	}

	public void setUserMobilePhone(String userMobilePhone) {
		this.userMobilePhone = userMobilePhone;
	}

	public String getDrawPrizeMoney() {
		return drawPrizeMoney;
	}

	public void setDrawPrizeMoney(String drawPrizeMoney) {
		this.drawPrizeMoney = drawPrizeMoney;
	}

	public String getDrawType() {
		return drawType;
	}

	public void setDrawType(String drawType) {
		this.drawType = drawType;
	}
	public String getDealReason() {
		return dealReason;
	}
	public void setDealReason(String dealReason) {
		this.dealReason = dealReason;
	}
	public String getDrawDealStatus() {
		return drawDealStatus;
	}
	public void setDrawDealStatus(String drawDealStatus) {
		this.drawDealStatus = drawDealStatus;
	}
	public String getAccountStatusName() {
		return accountStatusName;
	}
	public void setAccountStatusName(String accountStatusName) {
		this.accountStatusName = accountStatusName;
	}
	public PageList getAccountList() {
		return accountList;
	}
	public void setAccountList(PageList accountList) {
		this.accountList = accountList;
	}
	public PageList getAccountHisList() {
		return accountHisList;
	}
	public void setAccountHisList(PageList accountHisList) {
		this.accountHisList = accountHisList;
	}
	public String getAccountUsrName() {
		return accountUsrName;
	}
	public void setAccountUsrName(String accountUsrName) {
		this.accountUsrName = accountUsrName;
	}
	public String getAdjustMoney() {
		return adjustMoney;
	}
	public void setAdjustMoney(String adjustMoney) {
		this.adjustMoney = adjustMoney;
	}
	public String getProcedureFee() {
		return procedureFee;
	}
	public void setProcedureFee(String procedureFee) {
		this.procedureFee = procedureFee;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

}
