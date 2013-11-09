package com.success.admin.user.domain;

import java.sql.Timestamp;
import java.util.List;
public class AdminUser implements java.io.Serializable{

	private long		userId; //管理员ID
	private String		loginName;//管理员登录名
	private String		password;//管理员密码
	private String		name;//管理员真实姓名
	private int			sex		= 0;//管理员性别
	private int			age;//管理员年龄
	private String		telphone;//管理员电话
	private String		mobile;//管理员手机
	private String		email;//管理员Email
	private String		company;//管理员公司
	private Timestamp	createTime;//管理员注册时间
	private Timestamp	lastLoginTime;//最后一次登录时间
	private String		lastLoginIp;//最后一次登录IP
	private int			status	= 1;//管理员状态
	private String reserve;
	
	private List<AdminRolePrivileges> privileges;
	private List<AdminUserRoles> roles;//管理员角色
	
	public long getUserId(){
		return userId;
	}

	public void setUserId(long userId){
		this.userId = userId;
	}

	public String getLoginName(){
		return loginName;
	}

	public void setLoginName(String loginName){
		this.loginName = loginName;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public int getSex(){
		return sex;
	}

	public void setSex(int sex){
		this.sex = sex;
	}

	public int getAge(){
		return age;
	}

	public void setAge(int age){
		this.age = age;
	}

	public String getTelphone(){
		return telphone;
	}

	public void setTelphone(String telphone){
		this.telphone = telphone;
	}

	public String getMobile(){
		return mobile;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getCompany(){
		return company;
	}

	public void setCompany(String company){
		this.company = company;
	}

	public Timestamp getCreateTime(){
		return createTime;
	}

	public void setCreateTime(Timestamp createTime){
		this.createTime = createTime;
	}

	public Timestamp getLastLoginTime(){
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp(){
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp){
		this.lastLoginIp = lastLoginIp;
	}

	public int getStatus(){
		return status;
	}

	public void setStatus(int status){
		this.status = status;
	}

	
	public String getReserve(){
		return reserve;
	}

	
	public void setReserve(String reserve){
		this.reserve = reserve;
	}

	
	public List<AdminRolePrivileges> getPrivileges(){
		return privileges;
	}

	
	public void setPrivileges(List<AdminRolePrivileges> privileges){
		this.privileges = privileges;
	}

	
//	public List<String> getRoles(){
//		return roles;
//	}
//
//	
//	public void setRoles(List<String> roles){
//		this.roles = roles;
//	}

	public static void main(String[] args){
	}

	public List<AdminUserRoles> getRoles() {
		return roles;
	}

	public void setRoles(List<AdminUserRoles> roles) {
		this.roles = roles;
	}

	public boolean isAdministrator(){
		for(AdminRolePrivileges privileges : this.getPrivileges()){
			if("all".equals(privileges.getPrivilegeId().trim())){
				return true;
			}
		}
		return false;
	}
	
	public boolean isAccessible(String url){
		for(AdminRolePrivileges privileges : this.getPrivileges()){
			if(url.indexOf(privileges.getAccessKey().trim()) > -1){
				return true;
			}
		}
		return false;
	}
}
