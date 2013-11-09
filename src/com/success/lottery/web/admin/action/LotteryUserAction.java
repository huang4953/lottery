package com.success.lottery.web.admin.action;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.util.AreaMapTools;
import com.success.lottery.web.admin.webbean.PageList;
import com.success.utils.ApplicationContextUtils;

public class LotteryUserAction extends ActionSupport implements SessionAware{

	@SuppressWarnings("unchecked")
	private Map						session;
	private String userIdentify;
	private String mobilePhone;
	private String realName;
	private String idCard;
	private Date create_time_begin;
	private Date create_time_ends;
	private String areaCode;
	private Map areaMap;

//	private String query;
	
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
	
	public Map getSession(){
		return session;
	}

	public void setSession(Map session){
		this.session = session;
	}

	public int getPerPageNumber(){
		return perPageNumber;
	}

	public void setPerPageNumber(int perPageNumber){
		this.perPageNumber = perPageNumber;
	}

	
	
	
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}


	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Date getCreate_time_begin() {
		return create_time_begin;
	}

	public void setCreate_time_begin(Date create_time_begin) {
		this.create_time_begin = create_time_begin;
	}

	public Date getCreate_time_ends() {
		return create_time_ends;
	}

	public void setCreate_time_ends(Date create_time_ends) {
		this.create_time_ends = create_time_ends;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public int getPage(){
		return page;
	}

	
	public void setPage(int page){
		this.page = page;
	}
	
	public PageList getTermList(){
		return termList;
	}

	
	public void setTermList(PageList termList){
		this.termList = termList;
	}

	
//	public String getQuery(){
//		return query;
//	}
//
//	
//	public void setQuery(String query){
//		this.query = query;
//	}

	// 访问页面时
	public String queryAllMember(){
		this.setAreaMap(AreaMapTools.getAreaCodeList());
		AccountService acctService = ApplicationContextUtils.getService("accountService", AccountService.class);
		try{
			List<UserAccountModel> list = null;
			if(perPageNumber==0){
				perPageNumber = 30;
			}
			if(page == 0){
				//第一次查询设置页数我为1
				page = 1;
			}
			totalNumber = acctService.getUseresCount(this.getMobilePhone(),this.getRealName(),
					this.getIdCard(),this.getCreate_time_begin(),this.getCreate_time_ends(),this.getAreaCode());
			if(totalNumber<perPageNumber&&totalNumber!=0){
				perPageNumber = totalNumber;
			}
			list = acctService.getUseresInfo(this.getMobilePhone(),this.getRealName(),
					this.getIdCard(),this.getCreate_time_begin(),this.getCreate_time_ends(),this.getAreaCode(), page, perPageNumber);
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

	/*
	 * 会员管理明细单
	 */
	public String showUserInfoDetail(){
		ActionContext aContext = ActionContext.getContext();
		UserAccountModel member = null;
		 try {
			 AccountService acctService = ApplicationContextUtils.getService("accountService", AccountService.class);
			 member = acctService.getUserInfo(this.getUserIdentify());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ValueStack valueStack = aContext.getValueStack();
		valueStack.set("member",member);
		double fundsAccount_yuan = (double) (Math.round(member.getFundsAccount())/100.0);
		double prizeAccount_yuan = (double) (Math.round(member.getPrizeAccount())/100.0);
		double frozenAccount_yuan = (double) (Math.round(member.getFrozenAccount())/100.0);
		valueStack.set("fundsAccount_yuan", fundsAccount_yuan+"元");
		valueStack.set("prizeAccount_yuan", prizeAccount_yuan+"元");
		valueStack.set("frozenAccount_yuan", frozenAccount_yuan+"元");
		if(null!=member.getBirthday()){
			java.util.Date date = new java.util.Date(member.getBirthday().getTime());
			valueStack.set("birthday_str", new SimpleDateFormat("yyyy年MM月dd日").format(date));
		}
		if(null!=member.getLastLoginTime()){
			java.util.Date dateLoginTime = new java.util.Date(member.getLastLoginTime().getTime());
			valueStack.set("lastLoginTime_str", new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(dateLoginTime));
		}
		if(null!=member.getCreateTime()){
			java.util.Date datecreateTime = new java.util.Date(member.getCreateTime().getTime());
			valueStack.set("createTime_str", new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(datecreateTime));
		}
		valueStack.set("status_str", LotteryStaticDefine.getUserStatusDefineMap(member.getStatus()+""));
		valueStack.set("areaCode_str", AreaMapTools.getAreaName(member.getAreaCode()));
		valueStack.set("bindMobileFlag_str", LotteryStaticDefine.mobileStatus.get(member.isBindMobileFlag()));
		valueStack.set("sex_str", LotteryStaticDefine.userSex.get(member.getSex()+""));
		valueStack.set("bindEmailFlag_str", LotteryStaticDefine.emailStatus.get(member.isBindEmailFlag()));
		return "showUserInfoDetail";
	}
	/**
	 * @param args
	 */
	public static void main(String[] args){
	}

	public String getUserIdentify() {
		return userIdentify;
	}

	public void setUserIdentify(String userIdentify) {
		this.userIdentify = userIdentify;
	}

	public Map getAreaMap() {
		return areaMap;
	}

	public void setAreaMap(Map areaMap) {
		this.areaMap = areaMap;
	}
}
