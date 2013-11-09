package com.success.lottery.web.admin.decorator;

import java.io.UnsupportedEncodingException;

import org.displaytag.decorator.TableDecorator;

import com.success.admin.user.AdminUserTools;
import com.success.admin.user.domain.AdminUser;
import com.success.admin.user.domain.AdminUserRoles;
import com.success.lottery.business.service.LotteryStaticDefine;

public class QueryAllAdminUserWrapper extends TableDecorator{
	private String editLink;
	private String roles_str;
	private String sex_str;
	public String getSex_str() {
		StringBuffer sb = new StringBuffer();
		AdminUser admin = (AdminUser)super.getCurrentRowObject();
		sb.append(LotteryStaticDefine.userSex.get(admin.getSex()+""));
		this.setSex_str( sb.toString().trim());
		return this.sex_str;
	}
	public void setSex_str(String sex_str) {
		this.sex_str = sex_str;
	}
	public QueryAllAdminUserWrapper() {
		super();
	}
	public String getEditLink() {
		AdminUser admin = (AdminUser)super.getCurrentRowObject();
		long userid = admin.getUserId();
		this.setEditLink(LotteryStaticDefine.getAdminEditLink(userid).toString().trim());
		return this.editLink;
	}
	public void setEditLink(String editLink) {
		this.editLink = editLink;
	}
	
	public String getRoles_str() {
		StringBuffer sb = new StringBuffer();
		AdminUser admin = (AdminUser)super.getCurrentRowObject();
		String str = "";
		for(AdminUserRoles adminrole:admin.getRoles()){
			if(adminrole.getRoleId()!=null){
				String text = AdminUserTools.getRoleName(adminrole.getRoleId());
				if(null!=text){
					sb.append(text);
				}else{
					sb.append("");
				}
				
			}
			sb.append(",");
		}
		str = sb.toString();
		if(str.length()>=1)
		str = str.substring(0,str.length()-1);
		this.setRoles_str(str.trim());
		return this.roles_str;
	}
	public void setRoles_str(String roles_str) {
		this.roles_str = roles_str;
	}
}
