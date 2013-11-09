/**
 * Title: LotteryTermDecorator.java
 * @Package com.success.lottery.web.admin.decorator
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-5-21 ����03:25:10
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
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-5-21 ����03:25:10
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
		// TODO �Զ����ɹ��캯�����
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
		win_name = win_name==null?"δ֪":win_name;
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
	 *              <br>������������
	 * @return String
	 */
	public String getWinOpLink() {
		StringBuffer sb = new StringBuffer();
		LotteryTermModel termModel = (LotteryTermModel)super.getCurrentRowObject();
		int lotteryId = termModel.getLotteryId();
		String termNo = termModel.getTermNo();
		int winStatus = termModel.getWinStatus();
		String reserve = termModel.getReserve1();
		String [] reserves = {"0","0","0"};//��ѯ�����Ĳ��֣����ڿ�ʼ�����ڽ���
		if(reserve != null){
			reserves = reserve.split("#");
		}
		
		sb.append(LotteryStaticDefine.getTermDetailLink(String.valueOf(lotteryId), termNo));
		if(winStatus == 1){
			/*
			sb.append(" | ").append("<a href=\"showInputWinInfo.jhtml?l_lotteryId=").append(lotteryId)
			.append("&l_termNo=").append(termNo).append("&p_lotteryId=").append(reserves[0]).append("&p_termNo_begin=")
			.append(reserves[1]).append("&p_termNo_end=").append(reserves[2]).append("\">����</a>");
			*/
			sb.append(" | ").append("<a href=\"#\" onclick=\"javascript:checkEhandTerm(").append(lotteryId).append(",").append(termNo)
			.append(",").append(reserves[0]).append(",").append(reserves[1]).append(",").append(reserves[2]).append(")\">����</a>");
		}
		this.setWinOpLink(sb.toString().trim());
		return this.winOpLink;
	}
	/**
	 * 
	 * Title: getLimitOpLink<br>
	 * Description: <br>
	 *              <br>�޺Ž�������
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
			.append(reserves[1]).append("&p_termNo_end=").append(reserves[2]).append("\">�����޺�</a>");
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
		term_name = term_name == null ? "δ֪" : term_name;
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
			.append(reserves[1]).append("&p_termNo_end=").append(reserves[2]).append("\">�޸�</a>");
		}
		this.setSaleOpLink(sb.toString());
		return this.saleOpLink;
	}


	public void setSaleOpLink(String saleOpLink) {
		this.saleOpLink = saleOpLink;
	}
	
	

}
