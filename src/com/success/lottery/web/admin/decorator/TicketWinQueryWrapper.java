package com.success.lottery.web.admin.decorator;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.domain.WinOrderTicketDomain;
import com.success.lottery.util.LotteryTools;


public class TicketWinQueryWrapper extends TableDecorator{

	public TicketWinQueryWrapper(){
		super();
	}
	
	public String getOrderId(){
		WinOrderTicketDomain ticket = (WinOrderTicketDomain)this.getCurrentRowObject();
		return LotteryStaticDefine.getOrderDetailLink(ticket.getOrderId(), ticket.getOrderId());
	}
	
	public String getLotteryId(){
		return LotteryTools.getLotteryName(((WinOrderTicketDomain)this.getCurrentRowObject()).getLotteryId());
	}

	public String getBetType(){
		WinOrderTicketDomain ticket = (WinOrderTicketDomain)this.getCurrentRowObject();
		return LotteryTools.getPlayBetName(ticket.getLotteryId(), ticket.getPlayType(), ticket.getBetType());
	}

	public int getBetAmount(){
		return (int)(((WinOrderTicketDomain)this.getCurrentRowObject()).getBetAmount() / 100);
	}	

	public long getPreTaxPrize(){
		return (long)(((WinOrderTicketDomain)this.getCurrentRowObject()).getPreTaxPrize() / 100);
	}
	
	public String getOrderStatus(){
		WinOrderTicketDomain ticket = (WinOrderTicketDomain)this.getCurrentRowObject();
		return LotteryStaticDefine.orderStatus.get(ticket.getOrderStatus() + "");
	}
}
