package com.success.lottery.web.admin.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
import com.success.admin.user.AdminUserTools;
import com.success.admin.user.domain.AdminRolePrivileges;
import com.success.admin.user.domain.AdminUser;
import com.success.admin.user.service.AdminUserService;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.web.security.jcaptcha.CaptchaServiceSingleton;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.LibingUtils;

public class LoginAction extends ActionSupport implements SessionAware{

	private Map			session;
	private String		username;
	private String		password;
	private String		oldPassword;
	private String		newPassword;
	private String		logout;
	private String		name;
	private String		logErrMessage	= "";
	private String		lastLoginIP;
	private String		lastLoginTime;
	private String verifyCode;
	private String infor;
	private static Log	logger			= LogFactory.getLog(LoginAction.class.getName());
	
	//可用菜单TitleId, <privilegeId, accessKey>
	private Map<String, Map<String, String>> menuMap;
	private Map<String, String> titles;
	private Map<String, String> privileges = new HashMap<String, String>();

	public String index(){
		return "success";
	}

	public String top(){
		return "success";
	}

	public String menu(){
		AdminUser adminUser = (AdminUser)this.getSession().get("tlt.loginuser");
		if(adminUser == null){
			return "failed";
		}
		titles = AdminUserTools.getTitles();
		menuMap = new TreeMap<String, Map<String, String>>();
		if(adminUser.isAdministrator()){
			for(String titleId : titles.keySet()){
				if(AdminUserTools.isEnablePrivilegeTitle(titleId)){
					Map<String, String> privilegeInTitle = new TreeMap<String, String>();
					for(String privilegeId : AdminUserTools.getPrivileges(titleId)){
						if(AdminUserTools.isViewInMenu(privilegeId)){
							privilegeInTitle.put(privilegeId, AdminUserTools.getPrivilegeAccessKey(privilegeId));
							privileges.put(privilegeId, AdminUserTools.getPrivilegeName(privilegeId));
						}
					}
					if(privilegeInTitle.size() > 0){
						menuMap.put(titleId, privilegeInTitle);
					}
				}
			}
			return "success";
		}
		List<AdminRolePrivileges> privilegeList = adminUser.getPrivileges();
		for(String titleId : titles.keySet()){
			if(AdminUserTools.isEnablePrivilegeTitle(titleId)){
				Map<String, String> privilegeInTitle = new TreeMap<String, String>();
				for(AdminRolePrivileges privilege : privilegeList){
					if(AdminUserTools.isViewInMenu(privilege.getPrivilegeId())){
						if(AdminUserTools.isInTitle(privilege.getPrivilegeId(), titleId)){
							privilegeInTitle.put(privilege.getPrivilegeId(), AdminUserTools.getPrivilegeAccessKey(privilege.getPrivilegeId()));
							privileges.put(privilege.getPrivilegeId(), AdminUserTools.getPrivilegeName(privilege.getPrivilegeId()));
						}
					}
				}
				if(privilegeInTitle.size() > 0){
					menuMap.put(titleId, privilegeInTitle);
				}
			}
		}
		return "success";
	}

	@SuppressWarnings("unchecked")
	public String login(){
		String lastLoginIp = ServletActionContext.getRequest().getRemoteAddr();
		if(!StringUtils.isBlank(logout) && "true".equals(logout.trim())){
			
			Map param = new HashMap<String, String>();
			
			//AdminUser aUser = ((AdminUser)this.getSession().get("tlt.loginuser"));
			Object logOutUser = this.getSession()==null?null:this.getSession().get("tlt.loginuser");
			AdminUser aUser = logOutUser == null ? null : (AdminUser)logOutUser;
			if(aUser != null){
				param.put("userId", aUser.getUserId()+"");
				param.put("userName", aUser.getName());
				param.put("userKey", lastLoginIp);
				param.put("keyword1", aUser.getLoginName());
				OperatorLogger.log(10002, param);
				this.getSession().remove("tlt.loginuser");
			}
			
			return "logout";
		}
		
		
		AdminUser adminUser = (AdminUser) this.getSession().get("tlt.loginuser");
		if(null != adminUser){
			return "success";
		}
		
    	if(!CaptchaServiceSingleton.getInstance().validateResponseForID(ServletActionContext.getRequest().getSession().getId(), verifyCode)){
    		infor="验证码不正确";
    		Map param = new HashMap<String, String>();
			//如果adminUser为空，你如何取到他的UserID等等
			param.put("userId", 0+"");
			param.put("userKey", lastLoginIp);
			param.put("keyword1", username);
			param.put("errorMessage", infor);
			OperatorLogger.log(11001, param);
    		return "failed";
    	}
    	
		AdminUserService adminService = ApplicationContextUtils.getService("adminUserService", AdminUserService.class);
		if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
			//AdminUser adminUser = null;
			try{
				adminUser = adminService.login(username, password, lastLoginIp);
				if(null != adminUser){
					//ServletActionContext.getContext().getSession().put(arg0, arg1)
					this.getSession().put("tlt.loginuser", adminUser);
					Map param = new	HashMap<String, String>();
					param.put("userId", adminUser.getUserId()+"");
					param.put("userName", adminUser.getName());
					param.put("userKey", lastLoginIp);
					param.put("keyword1", username);
					OperatorLogger.log(10001, param);
					return "success";
				}else{
					logErrMessage = "用户名和密码错误";
					Map param = new HashMap<String, String>();
					//如果adminUser为空，你如何取到他的UserID等等
					param.put("userId", 0+"");
					param.put("userKey", lastLoginIp);
					param.put("keyword1", username);
					param.put("errorMessage", logErrMessage);
					OperatorLogger.log(11001, param);
					return "failed";
				}
				
				
		    	
			}catch(LotteryException e){
				Map param = new HashMap<String, String>();
				//如果adminUser为空，你如何取到他的UserID等等
				param.put("userId", 0+"");
				param.put("userKey", lastLoginIp);
				param.put("keyword1", username);
				param.put("errorMessage",e.getMessage());
				e.printStackTrace();
				return "failed";
			}
		}else{
			logErrMessage = "用户名和密码不能为空";
			Map param = new HashMap<String, String>();
			//如果adminUser为空，你如何取到他的UserID等等
			param.put("userId", 0+"");
//			param.put("userName", username);
			param.put("userKey", lastLoginIp);
//			param.put("keyword1", username);
			param.put("errorMessage",logErrMessage);
			return "failed";
		}
	}

	public String changePasswdShow(){
		AdminUser user = (AdminUser)this.getSession().get("tlt.loginuser");
		ActionContext aContext = ActionContext.getContext();
		ValueStack valueStack = aContext.getValueStack();
		valueStack.set("user", user);
		return "changePasswdShow";
	}

	public String mainframe(){
		// pzc注释的
		// this.setName(((LoginUser)this.getSession().get("tlt.loginuser")).getName());
		// this.setLastLoginIP(((LoginUser)this.getSession().get("tlt.loginuser")).getLastLoginIP());
		// this.setLastLoginTime(((LoginUser)this.getSession().get("tlt.loginuser")).getLastLoginTime());
		this.setName(((AdminUser)this.getSession().get("tlt.loginuser")).getName());
		this.setLastLoginIP(((AdminUser)this.getSession().get("tlt.loginuser")).getLastLoginIp());
		this.setLastLoginTime(((AdminUser)this.getSession().get("tlt.loginuser")).getLastLoginTime() + "");
		return "success";
	}

	@SuppressWarnings("unchecked")
	public String changepasswd(){
		AdminUser adminUser = (AdminUser)this.getSession().get("tlt.loginuser");
		AdminUserService adminService = ApplicationContextUtils.getService("adminUserService", AdminUserService.class);
		if(null != adminUser){
			try{
				adminService.updatePassword(adminUser.getUserId(), this.getNewPassword(), this.getOldPassword());
			}catch(LotteryException e){
				e.printStackTrace();
				Map param = new HashMap<String, String>();
				param.put("userId", adminUser.getUserId()+"");
				param.put("userName",adminUser.getName());
				param.put("userKey", ServletActionContext.getRequest().getRemoteAddr());
				param.put("keyword1", adminUser.getUserId()+"");
				param.put("errorMessage", e.getMessage());
				OperatorLogger.log(11004, param);
				return "changepasswdFailed";
			}
			Map param = new HashMap<String, String>();
			param.put("userId", adminUser.getUserId()+"");
			param.put("userName", adminUser.getName());
			param.put("userKey", ServletActionContext.getRequest().getRemoteAddr());
			param.put("keyword1", adminUser.getUserId()+"");
			OperatorLogger.log(10004, param);
			return "changepasswdSuccess";
		}else{
//			this.addFieldError("oldPassword", "第二次输入密码不正确，请重新输入");
			logErrMessage = "用户名和密码错误";
			Map param = new HashMap<String, String>();
			param.put("userId", 0+"");
			param.put("userKey", ServletActionContext.getRequest().getRemoteAddr());
			param.put("errorMessage", logErrMessage);
			OperatorLogger.log(11004, param);
			return "changepasswdFailed";
		}
	}

	public void validateChangepasswd(){
		AdminUser adminUser = (AdminUser)this.getSession().get("tlt.loginuser");
		AdminUserService adminService = ApplicationContextUtils.getService("adminUserService", AdminUserService.class);
		AdminUser adminUserQrry;
		ActionContext aContext = ActionContext.getContext();
		ValueStack valueStack = aContext.getValueStack();
		try{
			adminUserQrry = adminService.getAdminUser4LoginNameAndPws(adminUser.getLoginName(), this.getOldPassword());
			if(null == adminUserQrry){
				valueStack.set("user", adminUser);
				Map param = new HashMap<String, String>();
				param.put("userId", 0+"");
				param.put("userKey", ServletActionContext.getRequest().getRemoteAddr());
				param.put("errorMessage", "你输入的密码不对，请重新输入！");
				OperatorLogger.log(11004, param);
				this.addFieldError("", "你输入的密码不对，请重新输入！");
			}
		}catch(LotteryException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String welcome(){
		return "success";
	}

	
	public String getUsername(){
		return username;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getLogout(){
		return logout;
	}

	public void setLogout(String logout){
		this.logout = logout;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getLogErrMessage(){
		return logErrMessage;
	}

	public void setLogErrMessage(String logErrMessage){
		this.logErrMessage = logErrMessage;
	}

	public String getLastLoginIP(){
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP){
		this.lastLoginIP = lastLoginIP;
	}

	public String getLastLoginTime(){
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}

	public static void main(String[] args){
	}

	public String getOldPassword(){
		return oldPassword;
	}

	public void setOldPassword(String oldPassword){
		this.oldPassword = oldPassword;
	}

	public String getNewPassword(){
		return newPassword;
	}

	public void setNewPassword(String newPassword){
		this.newPassword = newPassword;
	}
	
	@Override
	public void setSession(Map session){
		this.session = session;
	}

	public Map getSession(){
		return this.session;
	}

	
	public Map<String, Map<String, String>> getMenuMap(){
		return menuMap;
	}

	
	public void setMenuMap(Map<String, Map<String, String>> menuMap){
		this.menuMap = menuMap;
	}

	
	public Map<String, String> getTitles(){
		return titles;
	}

	
	public void setTitles(Map<String, String> titles){
		this.titles = titles;
	}

	
	public Map<String, String> getPrivileges(){
		return privileges;
	}

	
	public void setPrivileges(Map<String, String> privileges){
		this.privileges = privileges;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getInfor() {
		return infor;
	}

	public void setInfor(String infor) {
		this.infor = infor;
	}

	
	
}
