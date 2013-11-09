package com.success.admin.user.service;

import java.util.List;

import com.success.admin.user.domain.AdminUser;
import com.success.lottery.exception.LotteryException;


public interface AdminUserService{
	
	public final static int ADDUSEREXCEPTION = 161001;
	public final static String ADDUSEREXCEPTION_STR = "添加用户时出现程序异常：";
	
	public final static int NOPASSWORDUPDATE = 160001;
	public final static String NOPASSWORDUPDATE_STR = "修改用户密码未能更新成功，UserId或旧密码错误";
	
	public final static int LOGINFAILED = 160002;
	public final static String LOGINFAILED_STR = "登录失败！登录用户名和密码不正确";

	public final static int LOGINNOPRIVILEGESFAILED = 160003;
	public final static String LOGINNOPRIVILEGESFAILED_STR = "登录失败！未能取到用户权限";

	public final static int NOADMINUSERUPDATE = 160004;
	public final static String NOADMINUSERUPDATE_STR = "更新用户信息失败，没有找到用户";
	
	public final static int NOTADMINUSER = 160005;
	public final static String NOTADMINUSER_STR = "没有找到用户";
	public final static int  UPDATEUSEREXCEPTION = 160006;
	public final static String  UPDATEUSEREXCEPTION_STR = "修改管理员失败";
	/**
	 * 添加后台用户，包括用户角色信息；当用户角色信息不为空(roles != null && roles.size() > 0)时将更新用户角色信息；
	 * @param user
	 * @param roles
	 * @throws LotteryException
	 * 		ADDUSEREXCEPTION
	 */
	public void addAdminUser(AdminUser user, List<String> roles) throws LotteryException;
	
	/**
	 * 用户登录
	 * @param loginName
	 * 		用户登录名
	 * @param password
	 * 		用户密码
	 * @param lastLoginIp
	 * 		用户登录IP，如果不为空则更新，更新失败不影响登录
	 * @return
	 * 		登录后返回用户信息，List<AdminRolePrivileges> privileges 中是用户的权限信息
	 * @throws LotteryException
	 * 		LOGINFAILED
	 * 		LOGINNOPRIVILEGESFAILED
	 * 		ADDUSEREXCEPTION
	 */
	public AdminUser login(String loginName, String password, String lastLoginIp) throws LotteryException;
	
	/**
	 * 用户更新密码
	 * @param userId
	 * 		用户Id
	 * @param oldPassword
	 * 		旧密码
	 * @param newPassword
	 * 		新密码
	 * @throws LotteryException
	 * 		NOPASSWORDUPDATE
	 * 		ADDUSEREXCEPTION
	 */
	public void changePassword(long userId, String oldPassword, String newPassword) throws LotteryException;
	
	/**
	 * 修改用户信息，包括用户角色信息；当用户角色信息不为空(roles != null && roles.size() > 0)时将更新用户角色信息；
	 * @param userId
	 * 		用户Id
	 * @param name
	 * 		用户名称
	 * @param sex
	 * 		用户性别
	 * @param age
	 * 		用户年龄
	 * @param telphone
	 * 		用户电话
	 * @param mobile
	 * 		用户手机
	 * @param email
	 * 		用户Email地址
	 * @param company
	 * 		用户所属公司
	 * @param roles
	 * 		用户权限信息，List中存放的是角色Id
	 * @throws LotteryException
	 * 		NOADMINUSERUPDATE
	 * 		ADDUSEREXCEPTION
	 */
	public void updateAdminUser(long userId, String name, int sex, int age, String telphone, String mobile, String email, String company, List<String> roles) throws LotteryException;

	public int getAdminUser4LoginName(String loginName) throws LotteryException;

	public List<AdminUser> getAdminUser4LoginNameList(String loginName,
			int page, int perPageNumber)throws LotteryException;

	public AdminUser getAdminUserById(Long userIdentify)throws LotteryException;

	public void updateAdminUser(AdminUser adminUser, List<String> roleIdlist)throws LotteryException;

	public AdminUser getAdminUser4LoginNameAndPws(String username,
			String password) throws LotteryException;

	public void updatePassword(long userId, String encode, String encode2) throws LotteryException;

}
