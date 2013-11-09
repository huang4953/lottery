package com.success.admin.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.admin.user.dao.AdminUserDAO;
import com.success.admin.user.domain.AdminRolePrivileges;
import com.success.admin.user.domain.AdminUser;
import com.success.admin.user.domain.AdminUserRoles;
import com.success.admin.user.service.AdminUserService;
import com.success.lottery.exception.LotteryException;
import com.success.utils.ApplicationContextUtils;


public class AdminUserServiceImpl implements AdminUserService{

	private Log logger = LogFactory.getLog(AdminUserServiceImpl.class.getName());
	private AdminUserDAO adminUserDao;
	
	
	public AdminUserDAO getAdminUserDao(){
		return adminUserDao;
	}

	
	public void setAdminUserDao(AdminUserDAO adminUserDao){
		this.adminUserDao = adminUserDao;
	}

	@Override
	public void addAdminUser(AdminUser user, List<String> roles) throws LotteryException{
		try{
			user = adminUserDao.insertAdminUser(user);
			System.out.println("roles.size()="+roles.size());
			if(!roles.isEmpty()&& roles.size() >=1){
				adminUserDao.deleteAdminUserAllRoles(user.getUserId());
				for(String role : roles){
					AdminUserRoles userRole = new AdminUserRoles();
					userRole.setUserId(user.getUserId());
					userRole.setRoleId(role);
					adminUserDao.insertAdminUserRoles(userRole);
				}
			}
		}catch(Exception e){
			logger.error("addAdminUser(user) exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AdminUserService.ADDUSEREXCEPTION, AdminUserService.ADDUSEREXCEPTION_STR + e.toString());
		}
	}

	@Override
	public void changePassword(long userId, String oldPassword, String newPassword) throws LotteryException{
		try{
			int rc = adminUserDao.updateAdminUserPassword(userId, newPassword, oldPassword);
			if(rc != 1){
				throw new LotteryException(AdminUserService.NOPASSWORDUPDATE, AdminUserService.NOPASSWORDUPDATE_STR);
			}
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("addAdminUser(user) exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AdminUserService.ADDUSEREXCEPTION, AdminUserService.ADDUSEREXCEPTION_STR + e.toString());
		}
	}

	@Override
	public AdminUser login(String loginName, String password, String lastLoginIp) throws LotteryException{
		try{
			AdminUser user = adminUserDao.getAdminUser(loginName, password);
			if(user == null){
				throw new LotteryException(AdminUserService.LOGINFAILED, AdminUserService.LOGINFAILED_STR);
			} else {
				List<AdminRolePrivileges> privileges = adminUserDao.getAdminUserPrivileges(user.getUserId());
				if(privileges == null){
					throw new LotteryException(AdminUserService.LOGINNOPRIVILEGESFAILED, AdminUserService.LOGINNOPRIVILEGESFAILED_STR);
				}else{
					user.setPrivileges(privileges);
					if(StringUtils.isNotBlank(lastLoginIp)){
						try{
							adminUserDao.updateAdminUserLoginInfo(user.getUserId(), lastLoginIp);
						}catch(Exception e){
							logger.warn("login(" + loginName + ", " + password + ", " + lastLoginIp + ") update lastLoginInfo exception:" + e.toString());
							if(logger.isDebugEnabled()){
								e.printStackTrace();
							}
						}
						user.setLastLoginIp(lastLoginIp);
					}
				}
				return user;
			}
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("addAdminUser(user) exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AdminUserService.ADDUSEREXCEPTION, AdminUserService.ADDUSEREXCEPTION_STR + e.toString());
		}
	}

	@Override
	public void updateAdminUser(long userId, String name, int sex, int age, String telphone, String mobile, String email, String company, List<String> roles) throws LotteryException{
		try{
			AdminUser user = new AdminUser();
			user.setUserId(userId);
			user.setName(name);
			user.setSex(sex);
			user.setAge(age);
			user.setTelphone(telphone);
			user.setMobile(mobile);
			user.setEmail(email);
			user.setCompany(company);
			int rc = adminUserDao.updateAdminUser(user);
			if(rc != 1){
				throw new LotteryException(AdminUserService.NOADMINUSERUPDATE, AdminUserService.NOADMINUSERUPDATE_STR);
			}
			if(roles != null && roles.size() > 1){
				adminUserDao.deleteAdminUserAllRoles(userId);
				for(String role : roles){
					AdminUserRoles userRole = new AdminUserRoles();
					userRole.setUserId(userId);
					userRole.setRoleId(role);
					adminUserDao.insertAdminUserRoles(userRole);
				}
			}
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("addAdminUser(user) exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AdminUserService.ADDUSEREXCEPTION, AdminUserService.ADDUSEREXCEPTION_STR + e.toString());
		}
	}

	/**
	 * @param args
	 * @throws LotteryException 
	 */
	public static void main(String[] args) throws LotteryException{
		AdminUserService adminUserService = ApplicationContextUtils.getService("adminUserService", AdminUserService.class);
		AdminUser user = adminUserService.login("test", "333333", "172.16.0.16");
		System.out.println(user.getUserId());
		System.out.println(user.getName());
		System.out.println(user.getMobile());
		List<String> roles = new ArrayList<String>();
		for(AdminRolePrivileges privilege : user.getPrivileges()){
			System.out.println(privilege.getAccessKey());
			if(!roles.contains(privilege.getRoleId())){
				roles.add(privilege.getRoleId());
			}
		}

		if(!roles.contains("R100004")){
			roles.add("R100004");
		}
		adminUserService.updateAdminUser(6, "ΩÒÃÏ≤‚ ‘", 1, 25, user.getTelphone(), user.getMobile(), "test@chinatlt.com", user.getCompany(), roles);
		
		user = adminUserService.login("test", "333333", "172.16.0.16");
		System.out.println(user.getUserId());
		System.out.println(user.getName());
		System.out.println(user.getMobile());
		for(AdminRolePrivileges privilege : user.getPrivileges()){
			System.out.println(privilege.getAccessKey());		
		}
		
		//adminUserService.changePassword(user.getUserId(), "222222", "333333");
	}


	@Override
	public int getAdminUser4LoginName(String loginName) throws LotteryException{
		// TODO Auto-generated method stub
		int i = 0;
		try {
			i = adminUserDao.getAdminUser4LoginName(loginName);
		} catch (Exception e) {
			throw new LotteryException(NOTADMINUSER, NOTADMINUSER_STR + e.toString());
		}
		return i;
	}


	@Override
	public List<AdminUser> getAdminUser4LoginNameList(String loginName,
			int pageIndex, int perPageNumber) throws LotteryException{
		// TODO Auto-generated method stub
		int startNumber = perPageNumber * (pageIndex - 1);
		int endNumber = pageIndex * perPageNumber;
		List<AdminUser> list = null;
		try {
			list = adminUserDao.getAdminUser4LoginNameList(loginName,startNumber,endNumber);
			return list;
		} catch(Exception e){
			logger.error("getTermInfoCount(" + loginName+") exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(NOTADMINUSER, NOTADMINUSER_STR + e.toString());
		}
	}


	@Override
	public AdminUser getAdminUserById(Long userIdentify) throws LotteryException{
		AdminUser adminUser = adminUserDao.getAdminUser(userIdentify);
		return adminUser;
	}


	@Override
	public void updateAdminUser(AdminUser adminUser, List<String> roleIdlist) throws LotteryException{
		try{
			int i = adminUserDao.updateAdminUser(adminUser);
			adminUserDao.deleteAdminUserAllRoles(adminUser.getUserId());
			if(!roleIdlist.isEmpty()&& roleIdlist.size() >=1){
				for(String role : roleIdlist){
					AdminUserRoles userRoleInsert = new AdminUserRoles();
					userRoleInsert.setUserId(adminUser.getUserId());
					userRoleInsert.setRoleId(role);
					adminUserDao.insertAdminUserRoles(userRoleInsert);
//					adminUserDao.updateAdminUserRoles(userRoleInsert);
				}
			}
		}catch(Exception e){
			logger.error("addAdminUser(user) exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			e.printStackTrace();
			throw new LotteryException(AdminUserService.UPDATEUSEREXCEPTION, AdminUserService.UPDATEUSEREXCEPTION_STR + e.toString());
		}
		
	}


	@Override
	public AdminUser getAdminUser4LoginNameAndPws(String loginName,
			String password)throws LotteryException{
		// TODO Auto-generated method stub
		AdminUser adminUser = null;
		try {
			adminUser = adminUserDao.getAdminUser(loginName, password);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return adminUser;
	}


	@Override
	public void updatePassword(long userId, String newPassword, String oldPassword) throws LotteryException{
		try {
			int i = adminUserDao.updateAdminUserPassword(userId, newPassword, oldPassword);
		} catch (Exception e) {
			throw new LotteryException(UPDATEUSEREXCEPTION,UPDATEUSEREXCEPTION_STR);
		}
		
	}

}