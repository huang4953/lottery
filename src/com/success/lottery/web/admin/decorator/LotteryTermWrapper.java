/**
 * Title: LotteryTermDecorator.java
 * @Package com.success.lottery.web.admin.decorator
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-5-21 下午03:25:10
 * @version V1.0
 */
package com.success.lottery.web.admin.decorator;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.util.LotteryTools;

/**
 * com.success.lottery.web.admin.decorator
 * LotteryTermDecorator.java
 * LotteryTermDecorator
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-5-21 下午03:25:10
 * 
 */

public class LotteryTermWrapper extends TableDecorator {
	
	private String winOpLink;
	private String limitOpLink;
	private String saleOpLink;
	private String lotteryCn_name;
	private String win_status_name;
	private String term_status_name;
	
	/**
	 *Title: 
	 *Description: 
	 */
	public LotteryTermWrapper() {
		// TODO 自动生成构造函数存根
	}


	public String getLotteryCn_name() {
		LotteryTermModel termModel = (LotteryTermModel)super.getCurrentRowObject();
		this.setLotteryCn_name(LotteryTools.getLotteryName(termModel.getLotteryId()));
		return this.lotteryCn_name;
	}



	public void setLotteryCn_name(String lotteryCn_name) {
		this.lotteryCn_name = lotteryCn_name;
	}



	public String getWin_status_name() {
		LotteryTermModel termModel = (LotteryTermModel)super.getCurrentRowObject();
		int winStatus = termModel.getWinStatus();
		String win_name = LotteryStaticDefine.termWinStatus.get(""+winStatus);
		win_name = win_name==null?"未知":win_name;
		this.setWin_status_name(win_name);
		return this.win_status_name;
	}



	public void setWin_status_name(String win_status_name) {
		this.win_status_name = win_status_name;
	}
	/**
	 * 
	 * Title: getWinOpLink<br>
	 * Description: <br>
	 *              <br>开奖界面连接
	 * @return String
	 */
	public String getWinOpLink() {
		StringBuffer sb = new StringBuffer();
		LotteryTermModel termModel = (LotteryTermModel)super.getCurrentRowObject();
		int lotteryId = termModel.getLotteryId();
		String termNo = termModel.getTermNo();
		int winStatus = termModel.getWinStatus();
		String reserve = termModel.getReserve1();
		String [] reserves = {"0","0","0"};//查询条件的彩种，彩期开始，彩期结束
		if(reserve != null){
			reserves = reserve.split("#");
		}
		
		sb.append(LotteryStaticDefine.getTermDetailLink(String.valueOf(lotteryId), termNo));
		if(winStatus == 1){
			/*
			sb.append(" | ").append("<a href=\"showInputWinInfo.jhtml?l_lotteryId=").append(lotteryId)
			.append("&l_termNo=").append(termNo).append("&p_lotteryId=").append(reserves[0]).append("&p_termNo_begin=")
			.append(reserves[1]).append("&p_termNo_end=").append(reserves[2]).append("\">开奖</a>");
			*/
			sb.append(" | ").append("<a href=\"#\" onclick=\"javascript:checkEhandTerm(").append(lotteryId).append(",").append(termNo)
			.append(",").append(reserves[0]).append(",").append(reserves[1]).append(",").append(reserves[2]).append(")\">开奖</a>");
		}
		this.setWinOpLink(sb.toString().trim());
		return this.winOpLink;
	}
	/**
	 * 
	 * Title: getLimitOpLink<br>
	 * Description: <br>
	 *              <br>限号界面连接
	 * @return String
	 */
	public String getLimitOpLink(){
		StringBuffer sb = new StringBuffer();
		LotteryTermModel termModel = (LotteryTermModel)super.getCurrentRowObject();
		int lotteryId = termModel.getLotteryId();
		String termNo = termModel.getTermNo();
		int termStatus = termModel.getTermStatus();
		String reserve = termModel.getReserve1();
		String [] reserves = {"0","0","0"};
		if(reserve != null){
			reserves = reserve.split("#");
		}
		sb.append(LotteryStaticDefine.getTermDetailLink(String.valueOf(lotteryId), termNo));
		if(termStatus == 1){
			sb.append(" | ").append("<a href=\"showlimitInputInfo.jhtml?lotteryId=").append(lotteryId)
			.append("&termNo=").append(termNo).append("&p_lotteryId=").append(reserves[0]).append("&p_termNo_begin=")
			.append(reserves[1]).append("&p_termNo_end=").append(reserves[2]).append("\">新增限号</a>");
		}
		this.setLimitOpLink(sb.toString());
		return this.limitOpLink;
	}


	public void setWinOpLink(String winOpLink) {
		this.winOpLink = winOpLink;
	}


	public String getTerm_status_name() {
		LotteryTermModel termModel = (LotteryTermModel)super.getCurrentRowObject();
		int termStatus = termModel.getTermStatus();
		String term_name = LotteryStaticDefine.termStatus.get(""+termStatus);
		term_name = term_name == null ? "未知" : term_name;
		this.setTerm_status_name(term_name);
		return this.term_status_name;
	}


	public void setTerm_status_name(String term_status_name) {
		this.term_status_name = term_status_name;
	}


	public void setLimitOpLink(String limitOpLink) {
		this.limitOpLink = limitOpLink;
	}


	public String getSaleOpLink() {
		StringBuffer sb = new StringBuffer();
		LotteryTermModel termModel = (LotteryTermModel)super.getCurrentRowObject();
		int lotteryId = termModel.getLotteryId();
		String termNo = termModel.getTermNo();
		int termStatus = termModel.getTermStatus();
		String reserve = termModel.getReserve1();
		String [] reserves = {"0","0","0"};
		if(reserve != null){
			reserves = reserve.split("#");
		}
		sb.append(LotteryStaticDefine.getTermDetailLink(String.valueOf(lotteryId), termNo));
		if(termStatus == 0){
			sb.append(" | ").append("<a href=\"showInputSaleInfo.jhtml?l_lotteryId=").append(lotteryId)
			.append("&l_termNo=").append(termNo).append("&p_lotteryId=").append(reserves[0]).append("&p_termNo_begin=")
			.append(reserves[1]).append("&p_termNo_end=").append(reserves[2]).append("\">修改</a>");
		}
		this.setSaleOpLink(sb.toString());
		return this.saleOpLink;
	}


	public void setSaleOpLink(String saleOpLink) {
		this.saleOpLink = saleOpLink;
	}
	
	

}
