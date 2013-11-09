package com.success.lottery.web.admin.decorator;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.util.AreaMapTools;

public class QueryAllUserWrapper extends TableDecorator{
	private String editLink;
	private String mobilePhoneLink;
	private String status_str;
	private String areaCode_str;
	public QueryAllUserWrapper() {
		super();
	}
	public String getEditLink() {
		UserAccountModel userModel = (UserAccountModel)super.getCurrentRowObject();
		long userId = userModel.getUserId();
		this.setEditLink(LotteryStaticDefine.getUserEditLink(userId).trim());
		return this.editLink;
	}
	public void setEditLink(String editLink) {
		this.editLink = editLink;
	}
	public String getMobilePhoneLink() {
		UserAccountModel userModel = (UserAccountModel)super.getCurrentRowObject();
		String mobilePhone = userModel.getMobilePhone();
		this.setMobilePhoneLink(LotteryStaticDefine.getUserDetailLink(mobilePhone).toString().trim());
		return this.mobilePhoneLink;
	}
	public void setMobilePhoneLink(String mobilePhoneLink) {
		this.mobilePhoneLink = mobilePhoneLink;
	}
	public String getStatus_str() {
		StringBuffer sb = new StringBuffer();
		UserAccountModel userModel = (UserAccountModel)super.getCurrentRowObject();
		int status = userModel.getStatus();
		sb.append(LotteryStaticDefine.getUserStatusDefineMap(status+""));
		this.setStatus_str(sb.toString().trim());
		return this.status_str;
	}
	public void setStatus_str(String status_str) {
		this.status_str = status_str;
	}
	public String getAreaCode_str() {
		StringBuffer sb = new StringBuffer();
		UserAccountModel userModel = (UserAccountModel)super.getCurrentRowObject();
		sb.append(AreaMapTools.getAreaName(userModel.getAreaCode()));
		this.setAreaCode_str(sb.toString().trim());
		return this.areaCode_str;
	}
	public void setAreaCode_str(String areaCode_str) {
		this.areaCode_str = areaCode_str;
	}
}
