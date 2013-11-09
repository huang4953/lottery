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
	
	//ÿһҳ�ļ�¼��
	private int					perPageNumber;
	//�ڼ�ҳ
	private int					page;
	
	private PageList 				termList = new PageList();

	//��ҳ����ʹ��static�������ڷ�ҳʱ���²�ѯ�����������������⣬����ͬʱ���ı���ҳ��
	//Ŀǰÿ�ζ���ѯ��������
	//�ɿ��ǵĽ��������
	//1-����������session�� 2-�����ݿ�DAO���ж�����������ϴ���ͬ�������ݿ��ѯֱ�ӷ���
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

	// ����ҳ��ʱ
	public String queryAllMember(){
		this.setAreaMap(AreaMapTools.getAreaCodeList());
		AccountService acctService = ApplicationContextUtils.getService("accountService", AccountService.class);
		try{
			List<UserAccountModel> list = null;
			if(perPageNumber==0){
				perPageNumber = 30;
			}
			if(page == 0){
				//��һ�β�ѯ����ҳ����Ϊ1
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
	 * ��Ա������ϸ��
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
		valueStack.set("fundsAccount_yuan", fundsAccount_yuan+"Ԫ");
		valueStack.set("prizeAccount_yuan", prizeAccount_yuan+"Ԫ");
		valueStack.set("frozenAccount_yuan", frozenAccount_yuan+"Ԫ");
		if(null!=member.getBirthday()){
			java.util.Date date = new java.util.Date(member.getBirthday().getTime());
			valueStack.set("birthday_str", new SimpleDateFormat("yyyy��MM��dd��").format(date));
		}
		if(null!=member.getLastLoginTime()){
			java.util.Date dateLoginTime = new java.util.Date(member.getLastLoginTime().getTime());
			valueStack.set("lastLoginTime_str", new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��").format(dateLoginTime));
		}
		if(null!=member.getCreateTime()){
			java.util.Date datecreateTime = new java.util.Date(member.getCreateTime().getTime());
			valueStack.set("createTime_str", new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��").format(datecreateTime));
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
