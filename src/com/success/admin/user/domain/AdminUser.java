package com.success.admin.user.domain;

import java.sql.Timestamp;
import java.util.List;
public class AdminUser implements java.io.Serializable{

	private long		userId; //����ԱID
	private String		loginName;//����Ա��¼��
	private String		password;//����Ա����
	private String		name;//����Ա��ʵ����
	private int			sex		= 0;//����Ա�Ա�
	private int			age;//����Ա����
	private String		telphone;//����Ա�绰
	private String		mobile;//����Ա�ֻ�
	private String		email;//����ԱEmail
	private String		company;//����Ա��˾
	private Timestamp	createTime;//����Աע��ʱ��
	private Timestamp	lastLoginTime;//���һ�ε�¼ʱ��
	private String		lastLoginIp;//���һ�ε�¼IP
	private int			status	= 1;//����Ա״̬
	private String reserve;
	
	private List<AdminRolePrivileges> privileges;
	private List<AdminUserRoles> roles;//����Ա��ɫ
	
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
