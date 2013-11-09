/**
 * Title: DrawMoneyWrapper.java
 * @Package com.success.lottery.web.admin.decorator
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-6-1 下午12:46:41
 * @version V1.0
 */
package com.success.lottery.web.admin.decorator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.account.model.DrawMoneyDomain;
import com.success.lottery.business.service.LotteryStaticDefine;

/**
 * com.success.lottery.web.admin.decorator
 * DrawMoneyWrapper.java
 * DrawMoneyWrapper
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-6-1 下午12:46:41
 * 
 */

public class DrawMoneyWrapper extends TableDecorator{
	
	private static String open_format = "height=470, width=550, " +
	"alwaysRaised=yes,dependent=yes,location=no, menubar=no, " +
	"toolbar =no, titlebar=no,scrollbars=yes, resizable=yes,status=no";
	
	private String drawMoney;
	
	private String drawPrizeLink;
	
	private String drawStatusName;
	
	private String showAccountDetail;
	
	private String showDrawDetail;
	

	public String getDrawMoney() {
		DrawMoneyDomain drawDomain = (DrawMoneyDomain)super.getCurrentRowObject();
		BigDecimal db = new BigDecimal(drawDomain.getDrawmoney());
		this.drawMoney = new DecimalFormat("###,###,###.##").format(db.divide(new BigDecimal(100)));
		return this.drawMoney;
	}

	public void setDrawMoney(String drawMoney) {
		this.drawMoney = drawMoney;
	}

	public String getDrawPrizeLink() {
		DrawMoneyDomain drawDomain = (DrawMoneyDomain)super.getCurrentRowObject();
		String drawid = drawDomain.getDrawid();
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('drawPrizeConfirm.jhtml?drawId=").append(drawid).append("&drawType=").append("AGREE")
		.append("','newwindow1','").append(open_format).append("')\">通过</a>")
		.append("&nbsp;|&nbsp;<a  href=\"#\" onclick=\"javascript:window.open('drawPrizeConfirm.jhtml?drawId=").append(drawid).append("&drawType=").append("REJECT")
		.append("','newwindow2','").append(open_format).append("')\">拒绝</a>");
		this.setDrawPrizeLink(sb.toString());
		return this.drawPrizeLink;
	}

	public void setDrawPrizeLink(String drawPrizeLink) {
		this.drawPrizeLink = drawPrizeLink;
	}

	public String getDrawStatusName() {
		DrawMoneyDomain drawDomain = (DrawMoneyDomain)super.getCurrentRowObject();
		int status = drawDomain.getDrawstatus();
		this.setDrawStatusName(LotteryStaticDefine.drawStatus.get(status));
		return this.drawStatusName;
	}

	public void setDrawStatusName(String drawStatusName) {
		this.drawStatusName = drawStatusName;
	}

	public String getShowAccountDetail() {
		DrawMoneyDomain drawDomain = (DrawMoneyDomain)super.getCurrentRowObject();
		String userMobilePhone = drawDomain.getUserPhone();
		this.setShowAccountDetail(LotteryStaticDefine.getUserDetailLink(userMobilePhone));
		return this.showAccountDetail;
	}

	public void setShowAccountDetail(String showAccountDetail) {
		this.showAccountDetail = showAccountDetail;
	}

	public String getShowDrawDetail() {
		DrawMoneyDomain drawDomain = (DrawMoneyDomain)super.getCurrentRowObject();
		String drawid = drawDomain.getDrawid();
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('drawPrizeDetail.jhtml?drawId=").append(drawid)
		.append("','newwindow2','").append(open_format).append("')\">").append(drawid).append("</a>");
		this.setShowDrawDetail(sb.toString());
		return this.showDrawDetail;
	}

	public void setShowDrawDetail(String showDrawDetail) {
		this.showDrawDetail = showDrawDetail;
	}
	
}
