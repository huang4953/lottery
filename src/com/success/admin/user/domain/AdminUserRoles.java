package com.success.admin.user.domain;
public class AdminUserRoles{

	private long	userId;
	private String	roleId;
	private String	reserve;

	public long getUserId(){
		return userId;
	}

	public void setUserId(long userId){
		this.userId = userId;
	}

	public String getRoleId(){
		return roleId;
	}

	public void setRoleId(String roleId){
		this.roleId = roleId;
	}

	public String getReserve(){
		return reserve;
	}

	public void setReserve(String reserve){
		this.reserve = reserve;
	}

	public static void main(String[] args){
	}
}
