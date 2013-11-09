package com.success.admin.user.domain;
public class AdminRolePrivileges{

	private String	roleId;
	private String	privilegeId;
	private int		privilegeType	= 1;
	private String	accessKey;
	private String reserve;

	public String getRoleId(){
		return roleId;
	}

	public void setRoleId(String roleId){
		this.roleId = roleId;
	}

	public String getPrivilegeId(){
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId){
		this.privilegeId = privilegeId;
	}

	public int getPrivilegeType(){
		return privilegeType;
	}

	public void setPrivilegeType(int privilegeType){
		this.privilegeType = privilegeType;
	}

	public String getAccessKey(){
		return accessKey;
	}

	public void setAccessKey(String accessKey){
		this.accessKey = accessKey;
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
