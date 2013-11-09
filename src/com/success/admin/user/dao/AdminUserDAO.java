package com.success.admin.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.admin.user.domain.AdminRolePrivileges;
import com.success.admin.user.domain.AdminUser;
import com.success.admin.user.domain.AdminUserRoles;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.MD5;


public class AdminUserDAO extends SqlMapClientDaoSupport{

	public AdminUser insertAdminUser(AdminUser user){
		return (AdminUser)this.getSqlMapClientTemplate().insert("adminUser.insertAdminUser", user);
	}
	
	public int updateAdminUserPassword(long userId, String password, String oldPassword){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", Long.valueOf(userId));
		paramMap.put("password", MD5.MD5Encode(password));
		paramMap.put("oldPassword", MD5.MD5Encode(oldPassword));
		return this.getSqlMapClientTemplate().update("adminUser.updateAdminUserPassword", paramMap);
	}

	public int updateAdminUser(AdminUser user){
		return this.getSqlMapClientTemplate().update("adminUser.updateAdminUser", user);
	}

	public int updateAdminUserLoginInfo(long userId, String lastLoginIp){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lastLoginIp", lastLoginIp);
		paramMap.put("userId", Long.valueOf(userId));
		return this.getSqlMapClientTemplate().update("adminUser.updateAdminUserLoginInfo", paramMap);
	}
	
	public AdminUser getAdminUser(String loginName, String password){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("loginName", loginName);
		paramMap.put("password", MD5.MD5Encode(password));
		return (AdminUser)this.getSqlMapClientTemplate().queryForObject("adminUser.getAdminUserForLogon", paramMap);
	}
	
	public List<AdminRolePrivileges> getAdminUserPrivileges(long userId){
		return (List<AdminRolePrivileges>)this.getSqlMapClientTemplate().queryForList("adminUser.getAdminUserPrivileges", Long.valueOf(userId));
	}
	
	public AdminUser getAdminUser(long userId){
		return (AdminUser)this.getSqlMapClientTemplate().queryForObject("adminUser.getAdminUser", Long.valueOf(userId));
	}
	
	public List<AdminUser> getAdminUseres(){
		return (List<AdminUser>)this.getSqlMapClientTemplate().queryForList("adminUser.getAdminUseres");
	}

	
	public void insertAdminUserRoles(AdminUserRoles userRole){
		this.getSqlMapClientTemplate().insert("adminUser.insertAdminUserRoles", userRole);
	}
	
	public int deleteAdminUserRoles(long userId, String roleId){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", Long.valueOf(userId));
		paramMap.put("roleId", roleId);
		return this.getSqlMapClientTemplate().delete("adminUser.deleteAdminUserRoles", paramMap);
	}
	
	public int deleteAdminUserAllRoles(long userId){
		return this.getSqlMapClientTemplate().delete("adminUser.deleteAdminUserAllRoles", Long.valueOf(userId));
	}
	
	public List<AdminUserRoles> getAdminUserRoles(long userId){
		return (List<AdminUserRoles>)this.getSqlMapClientTemplate().queryForList("adminUser.getAdminUserRoles", Long.valueOf(userId));
	}

	public static void main(String[] args){
		AdminUserDAO adminUserDao = ApplicationContextUtils.getService("adminUserDao", AdminUserDAO.class);
		
//		AdminUser user = new AdminUser();
//		user.setLoginName("test");
//		user.setPassword(MD5.MD5Encode("111111"));
//		user.setName("≤‚ ‘");
//		user.setSex(1);
//		user.setAge(30);
//		user.setMobile("13099998888");
//		adminUserDao.insertAdminUser(user);
		
//		System.out.println(adminUserDao.updateAdminUserPassword(6, "222222", "111111"));
//		System.out.println(adminUserDao.updateAdminUserPassword(5, "222222", "111111"));
				
//		System.out.println(adminUserDao.getAdminUser("test", "222222"));
//		System.out.println(adminUserDao.getAdminUser("test", "111111"));

//		AdminUserRoles userRoles = new AdminUserRoles();
//		userRoles.setUserId(6);
//		userRoles.setRoleId("R100001");
//		adminUserDao.insertAdminUserRoles(userRoles);
//		
//		userRoles.setUserId(6);
//		userRoles.setRoleId("R100002");
//		adminUserDao.insertAdminUserRoles(userRoles);
//		
//		userRoles.setUserId(6);
//		userRoles.setRoleId("R100003");
//		adminUserDao.insertAdminUserRoles(userRoles);

//		for(AdminRolePrivileges privilege : adminUserDao.getAdminUserPrivileges(6)){
//			System.out.println(privilege.getRoleId() + ", " + privilege.getAccessKey());
//		}
		
//		System.out.println(adminUserDao.deleteAdminUserRoles(6, "R100002"));
		
//		for(AdminRolePrivileges privilege : adminUserDao.getAdminUserPrivileges(6)){
//			System.out.println(privilege.getRoleId() + ", " + privilege.getAccessKey());
//		}

//		for(AdminUserRoles userRole : adminUserDao.getAdminUserRoles(6)){
//			System.out.println(userRole.getRoleId());
//		}
	}

	public int getAdminUser4LoginName(String loginName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", loginName);
		return this.getSqlMapClientTemplate().queryForList("adminUser.getAdminUser4lognameList", paramMap).size();
	}

	public List<AdminUser> getAdminUser4LoginNameList(String loginName,
			int startNumber, int endNumber) {
		// TODO Auto-generated method stub
		List<AdminUser> list = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", loginName);
		paramMap.put("startPageNumber", Integer.valueOf(startNumber));
		paramMap.put("endPageNumber", Integer.valueOf(endNumber-startNumber));
		list = this.getSqlMapClientTemplate().queryForList("adminUser.getAdminUser4lognameList", paramMap,
				startNumber, endNumber);
//		list = this.getSqlMapClientTemplate().queryForList("adminUser.getAdminUser4lognameList", paramMap);
		return list;
	}
}
