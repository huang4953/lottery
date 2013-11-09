/**
 * Title: ReportTransSortDomain.java
 * @Package com.success.lottery.report.domain
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-10 上午11:28:30
 * @version V1.0
 */
package com.success.lottery.report.domain;

import java.io.Serializable;

/**
 * com.success.lottery.report.domain
 * ReportTransSortDomain.java
 * ReportTransSortDomain
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-10 上午11:28:30
 * 
 */

public class ReportTransSortDomain implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1551611589468471601L;
	public String mobilePhone;
	public String realName;
	public String amount;
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}

}
