package com.success.lottery.web.admin.decorator;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.util.LotteryTools;


public class QueryAllTermWrapper extends TableDecorator{

	public QueryAllTermWrapper(){
		super();
	}
	
	public String getLotteryId(){
		return LotteryTools.getLotteryName(((LotteryTermModel)this.getCurrentRowObject()).getLotteryId());
	}
	
	public String getTermNo(){
		String termno = ((LotteryTermModel)this.getCurrentRowObject()).getTermNo();
		String url = "<a href=\"#\" onclick=\"javascript:window.open('showTermInfoDetail.jhtml?l_lotteryId=" + 
			((LotteryTermModel)this.getCurrentRowObject()).getLotteryId() + "&l_termNo=" + termno + 
			"', 'newwindow', 'height=500, width=800, alwaysRaised=yes,dependent=yes,location=no, menubar=no, toolbar =no, " +
			"titlebar=no,scrollbars=yes, resizable=yes,status=no')\">" + termno + "</a>";
		return url;
	}
	
	public String getTermStatus(){
		return LotteryStaticDefine.termStatus.get("" + ((LotteryTermModel)this.getCurrentRowObject()).getTermStatus());
	}
	
	public String getSaleStatus(){
		return LotteryStaticDefine.saleStatus.get("" + ((LotteryTermModel)this.getCurrentRowObject()).getSaleStatus());
	}
	
	public String getWinStatus(){
		return LotteryStaticDefine.termWinStatus.get("" + ((LotteryTermModel)this.getCurrentRowObject()).getWinStatus());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
	}
}
