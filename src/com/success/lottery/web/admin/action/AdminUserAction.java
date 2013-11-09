package com.success.lottery.web.admin.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
import com.success.admin.user.AdminUserTools;
import com.success.admin.user.domain.AdminUser;
import com.success.admin.user.domain.AdminUserRoles;
import com.success.admin.user.service.AdminUserService;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.web.admin.webbean.PageList;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.LibingUtils;
import com.success.utils.MD5;

@SuppressWarnings("serial")
public class AdminUserAction extends ActionSupport implements SessionAware{

	@SuppressWarnings("unchecked")
	private Map						session;
	public String loginName;
	private String query;
//	private String roleResultList;
	private String password;
	private String name;
	private Integer sex;
	private Integer age;
	private String telphone;
	private String mobile;
	private String email;
	private long userId;
	private String company;
	private Long userIdentify;
	private Timestamp createTime;
	private Timestamp lastLoginTime;
	private String lastLoginIp;
	private String repassword;
	private String oldPassword;
	private String secondPassword;
	private String		logErrMessage	= "";
	private Map<String, String> roleSelectMap;
	private Map<String, String> rightRoleSelectMap = new HashMap();
	private List<String> roleResult;
	private Map sexList;
	private Map statusList;
	private int status;
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

	
	public String getQuery(){
		return query;
	}

	
	public void setQuery(String query){
		this.query = query;
	}

//	 访问页面时
	public String queryAdminUser(){
		AdminUserService adminService = ApplicationContextUtils.getService("adminUserService", AdminUserService.class);
		try{
			List<AdminUser> list = null;
			if(perPageNumber==0){
				perPageNumber = 30;
			}
			if(page == 0){
				//第一次查询所有
				page = 1;
			}
				// 查询所有后点击了分页
			totalNumber = adminService.getAdminUser4LoginName(this.getLoginName());
			if(totalNumber<perPageNumber&&totalNumber!=0){
				perPageNumber = totalNumber;
			}
			list = adminService.getAdminUser4LoginNameList(this.getLoginName(),page, perPageNumber);
			termList.setPageNumber(page);
			termList.setPerPage(perPageNumber);
			termList.setFullListSize(totalNumber);
			termList.setList(list);
		}catch(LotteryException e){
			e.printStackTrace();
			return "failed";
		}
		return "queryAdminUser";
	}
	
	public String addAdminUserShow(){
		Map map = AdminUserTools.getRoles();
		Map leftMap = new HashMap();
		Set<String> set = map.keySet();
		for(String str:set){
			if(!str.equals("R000002")){
				leftMap.put(str, map.get(str));
			}else{
				rightRoleSelectMap.put(str, (String) map.get(str));
			}
		}
		this.setRoleSelectMap(leftMap);
		this.setSexList(LotteryStaticDefine.userSex);
		this.setStatusList(LotteryStaticDefine.adminStatus);
		return "addAdminUserShow";
	}
	
	public String addAdminUser(){
		AdminUserService adminService = ApplicationContextUtils.getService("adminUserService", AdminUserService.class);
		AdminUser adminUser = new AdminUser();
		try {
			adminUser.setAge(this.getAge());
			adminUser.setCompany(this.getCompany());
			adminUser.setEmail(this.getEmail());
			adminUser.setLoginName(this.getLoginName());
			adminUser.setMobile(this.getMobile());
			adminUser.setName(this.getName());
			adminUser.setPassword(MD5.MD5Encode(this.getPassword()));
			adminUser.setSex(this.getSex());
			adminUser.setTelphone(this.getTelphone());
			adminUser.setStatus(this.getStatus());
			if(null==roleResult){
				roleResult = new ArrayList<String>();
				roleResult.add("R000002");
			}
			adminService.addAdminUser(adminUser, roleResult);
			return "addAdminUserSuccess";
		} catch (LotteryException e) {
			e.printStackTrace();
			return "addAdminUserFailed";
		}
		
	}

	
	public String changePerPasswordShow(){
		AdminUser adminUser = null;
		AdminUserService adminService = ApplicationContextUtils.getService("adminUserService", AdminUserService.class);
		try {
			if(null!=this.getUserIdentify()){
				adminUser = adminService.getAdminUserById(this.getUserIdentify());
			}else{
				adminUser = adminService.getAdminUserById(this.getUserId());
			}
		} catch (LotteryException e) {
			e.printStackTrace();
		}
		
		ActionContext aContext = ActionContext.getContext();
		ValueStack valueStack = aContext.getValueStack();
		valueStack.set("adminUser",adminUser);
		return "changePerPasswordShow";
	}
	
	@SuppressWarnings("unchecked")
	public String changePerPassword(){
		AdminUser adminUser = null;
		AdminUserService adminService = ApplicationContextUtils.getService("adminUserService", AdminUserService.class);
		try {
			if(null!=this.getUserIdentify()){
				adminUser = adminService.getAdminUserById(this.getUserIdentify());
			}else{
				adminUser = adminService.getAdminUserById(this.getUserId());
			}
			if(null!=adminUser){
				adminService.updatePassword(adminUser.getUserId(), this.getPassword(), this.getOldPassword());
				Map param = new HashMap<String, String>();
				param.put("userId", adminUser.getUserId()+"");
				param.put("userName", adminUser.getName());
				param.put("userKey", ServletActionContext.getRequest().getRemoteAddr());
				param.put("keyword1", adminUser.getUserId()+"");
				OperatorLogger.log(10004, param);
				return "changePerPasswordSuccess";
			}else{
				logErrMessage = "用户名和密码错误";
				Map param = new HashMap<String, String>();
				param.put("userId", 0+"");
				param.put("userKey", ServletActionContext.getRequest().getRemoteAddr());
				param.put("errorMessage", logErrMessage);
				OperatorLogger.log(11004, param);
				return "changePerPasswordFailed";
			}
		} catch (LotteryException e) {
			e.printStackTrace();
			Map param = new HashMap<String, String>();
			param.put("userId", 0+"");
			param.put("userKey",ServletActionContext.getRequest().getRemoteAddr());
			param.put("errorMessage", e.getMessage());
			OperatorLogger.log(11004, param);
			return "changePerPasswordFailed";
			
		}
	}
	
	public String updateAdminUserShow(){
		AdminUser adminUser = null;
		AdminUserService adminService = ApplicationContextUtils.getService("adminUserService", AdminUserService.class);
		try {
			if(null!=this.getUserIdentify()){
				adminUser = adminService.getAdminUserById(this.getUserIdentify());
			}else{
				adminUser = adminService.getAdminUserById(this.getUserId());
			}
			 
		} catch (LotteryException e) {
			e.printStackTrace();
		}
		
		Map map = AdminUserTools.getRoles();
		for(AdminUserRoles adminrole:adminUser.getRoles()){
			if(adminrole.getRoleId()!=null){
				rightRoleSelectMap.put(adminrole.getRoleId(), (String) map.get(adminrole.getRoleId()));
				map.remove(adminrole.getRoleId());
			}
		}
		this.setRoleSelectMap(map);
		this.setSexList(LotteryStaticDefine.userSex);
		this.setStatusList(LotteryStaticDefine.adminStatus);
		ActionContext aContext = ActionContext.getContext();
		ValueStack valueStack = aContext.getValueStack();
		if(null!=adminUser.getLastLoginTime()){
			java.util.Date dateLoginTime = new java.util.Date(adminUser.getLastLoginTime().getTime());
			valueStack.set("lastLoginTime_str", new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(dateLoginTime));
		}
		if(null!=adminUser.getCreateTime()){
			java.util.Date datecreateTime = new java.util.Date(adminUser.getCreateTime().getTime());
			valueStack.set("createTime_str", new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(datecreateTime));
		}
		valueStack.set("adminUser",adminUser);
		return "updateAdminUserShow";
	}
	
	@SuppressWarnings("unchecked")
	public String  updateAdminUser(){
		AdminUserService adminService = ApplicationContextUtils.getService("adminUserService", AdminUserService.class);
		AdminUser adminUser;
		try {
			adminUser = adminService.getAdminUserById(this.getUserId());
		} catch (LotteryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Map param = new HashMap<String, String>();
			param.put("userId", 0+"");
			param.put("userKey", ServletActionContext.getRequest().getRemoteAddr());
			OperatorLogger.log(11003, param);
			return "updateAdminUserFailed";
		}
		
		Map param = new HashMap<String, String>();
		param.put("userId", adminUser.getUserId()+"");
		param.put("userName", adminUser.getName());
		param.put("userKey", ServletActionContext.getRequest().getRemoteAddr());
		param.put("keyword1", adminUser.getUserId()+"");
		
		try {
			adminUser = adminService.getAdminUserById(this.getUserId());
			adminUser.setAge(this.getAge());
			adminUser.setCompany(this.getCompany());
			adminUser.setEmail(this.getEmail());
			adminUser.setLoginName(this.getLoginName());
			adminUser.setMobile(this.getMobile());
			adminUser.setName(this.getName());
			adminUser.setSex(this.getSex());
			adminUser.setTelphone(this.getTelphone());
//			adminUser.setCreateTime(adminUser.getCreateTime());
//			adminUser.setCreateTime(this.getCreateTime());
//			adminUser.setLastLoginTime(this.getLastLoginTime());
//			adminUser.setLastLoginIp(this.getLastLoginIp());
			OperatorLogger.log(10003, param);
			if(null==roleResult){
				roleResult = new ArrayList<String>();
				roleResult.add("R000002");
			}
			adminService.updateAdminUser(adminUser, roleResult);
			return "updateAdminUserSuccess";
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			param.put("errorMessage", e.getMessage());
			OperatorLogger.log(11003, param);
			e.printStackTrace();
			return "updateAdminUserFailed";
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void validateChangePerPassword(){
		ActionContext aContext = ActionContext.getContext();
		ValueStack valueStack = aContext.getValueStack();
		AdminUserService adminService = ApplicationContextUtils.getService("adminUserService", AdminUserService.class);
		AdminUser adminUserShow = null;
		try {
			adminUserShow = adminService.getAdminUserById(this.getUserId());
		} catch (LotteryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(!MD5.MD5Encode(this.getOldPassword()).equals(adminUserShow.getPassword())){
			Map param = new HashMap<String, String>();
			param.put("userId", adminUserShow.getUserId()+"");
			param.put("userName", adminUserShow.getName());
			param.put("userKey", ServletActionContext.getRequest().getRemoteAddr());
			param.put("keyword1", adminUserShow.getUserId()+"");
			param.put("errorMessage", "你输入的旧密码不对");
			OperatorLogger.log(11004, param);
			this.addFieldError("oldpassword", "你输入的旧密码不对");
		}
		valueStack.set("adminUser",adminUserShow);
	}
	public void validateUpdateAdminUser() {
   }
	
	public void validateAddAdminUser() {
	}
	/**
	 * @param args
	 */
	public static void main(String[] args){
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Long getUserIdentify() {
		return userIdentify;
	}

	public void setUserIdentify(Long userIdentify) {
		this.userIdentify = userIdentify;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getSecondPassword() {
		return secondPassword;
	}

	public void setSecondPassword(String secondPassword) {
		this.secondPassword = secondPassword;
	}

	
	public Map<String, String> getRoleSelectMap(){
		return roleSelectMap;
	}

	
	public void setRoleSelectMap(Map<String, String> roleSelectMap){
		this.roleSelectMap = roleSelectMap;
	}

	
	public Map<String, String> getRightRoleSelectMap(){
		return rightRoleSelectMap;
	}

	
	public void setRightRoleSelectMap(Map<String, String> rightRoleSelectMap){
		this.rightRoleSelectMap = rightRoleSelectMap;
	}
	
	public List<String> getRoleResult(){
		return roleResult;
	}

	public void setRoleResult(List<String> roleResult){
		this.roleResult = roleResult;
	}

	public Map getSexList() {
		return sexList;
	}

	public void setSexList(Map sexList) {
		this.sexList = sexList;
	}

	public Map getStatusList() {
		return statusList;
	}

	public void setStatusList(Map statusList) {
		this.statusList = statusList;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLogErrMessage() {
		return logErrMessage;
	}

	public void setLogErrMessage(String logErrMessage) {
		this.logErrMessage = logErrMessage;
	}

	
}

