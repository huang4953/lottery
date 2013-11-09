/**
 * Title: UserAccountWrapper.java
 * @Package com.success.lottery.web.admin.decorator
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-6-2 上午10:43:05
 * @version V1.0
 */
package com.success.lottery.web.admin.decorator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.util.AreaMapTools;

/**
 * com.success.lottery.web.admin.decorator
 * UserAccountWrapper.java
 * UserAccountWrapper
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-6-2 上午10:43:05
 * 
 */

public class UserAccountWrapper extends TableDecorator {
	
	private static String open_format = "height=450, width=600, " +
	"alwaysRaised=yes,dependent=yes,location=no, menubar=no, " +
	"toolbar =no, titlebar=no,scrollbars=yes, resizable=yes,status=no";
	private String userStatus;
	
	private String adjustLink;
	
	private String accountDetailLink;
	
	private String fundsAccountF;
	private String prizeAccountF;
	private String frozenAccountF;
	
	private String areaCodeName;
	
	

	public String getUserStatus() {
		this.setUserStatus(LotteryStaticDefine.userStatus.get(String.valueOf(((UserAccountModel)this.getCurrentRowObject()).getStatus())));
		return this.userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getAccountDetailLink() {
		this.setAccountDetailLink(LotteryStaticDefine.getUserDetailLink(((UserAccountModel)this.getCurrentRowObject()).getMobilePhone()));
		return this.accountDetailLink;
	}

	public void setAccountDetailLink(String accountDetailLink) {
		this.accountDetailLink = accountDetailLink;
	}

	public String getAdjustLink() {
		UserAccountModel userModel = (UserAccountModel)super.getCurrentRowObject();
		StringBuffer sb = new StringBuffer();
		int userStatus = userModel.getStatus();
		long accountId = userModel.getUserId();
		if(userStatus == 1){//用户正常
			sb.append("<a  href=\"#\" onclick=\"javascript:window.open('adjustconfirm.jhtml?accountId=").append(accountId)
			.append("','adjustaccount','").append(open_format).append("')\">").append("调整").append("</a>");
		}
		this.setAdjustLink(sb.toString());
		return this.adjustLink;
	}

	public void setAdjustLink(String adjustLink) {
		this.adjustLink = adjustLink;
	}
	
	private  String formatMoney(long money) {
		BigDecimal db = new BigDecimal(money);
		return new DecimalFormat("###,###,###.##").format(db.divide(new BigDecimal(100)));
	}

	public String getFrozenAccountF() {
		this.setFrozenAccountF(this.formatMoney(((UserAccountModel)this.getCurrentRowObject()).getFrozenAccount()));
		return this.frozenAccountF;
	}

	public void setFrozenAccountF(String frozenAccountF) {
		this.frozenAccountF = frozenAccountF;
	}

	public String getFundsAccountF() {
		this.setFundsAccountF(this.formatMoney(((UserAccountModel)this.getCurrentRowObject()).getFundsAccount()));
		return this.fundsAccountF;
	}

	public void setFundsAccountF(String fundsAccountF) {
		this.fundsAccountF = fundsAccountF;
	}

	public String getPrizeAccountF() {
		this.setPrizeAccountF(this.formatMoney(((UserAccountModel)this.getCurrentRowObject()).getPrizeAccount()));
		return this.prizeAccountF;
	}

	public void setPrizeAccountF(String prizeAccountF) {
		this.prizeAccountF = prizeAccountF;
	}

	public String getAreaCodeName() {
		this.setAreaCodeName(AreaMapTools.getAreaName(((UserAccountModel)this.getCurrentRowObject()).getAreaCode()));
		return this.areaCodeName;
	}

	public void setAreaCodeName(String areaCodeName) {
		this.areaCodeName = areaCodeName;
	}
}
