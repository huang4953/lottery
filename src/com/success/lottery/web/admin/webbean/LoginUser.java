package com.success.lottery.web.admin.webbean;
public class LoginUser {

	private long	id;
	private String	username;
	private String	name;
	private String	lastLoginTime;
	private String	lastLoginIP;
	private int		status;
	private String	allowableUrls;
	private String	allowableTags;

//	public static getLoginUser(UserInfo){
//		
//	}
	
	public long getId(){
		return id;
	}

	public void setId(long id){
		this.id = id;
	}

	public String getUsername(){
		return username;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getLastLoginTime(){
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIP(){
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP){
		this.lastLoginIP = lastLoginIP;
	}

	public int getStatus(){
		return status;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public String getAllowableUrls(){
		return allowableUrls;
	}

	public void setAllowableUrls(String allowableUrls){
		this.allowableUrls = allowableUrls;
	}

	public String getAllowableTags(){
		return allowableTags;
	}

	public void setAllowableTags(String allowableTags){
		this.allowableTags = allowableTags;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
	}
}
