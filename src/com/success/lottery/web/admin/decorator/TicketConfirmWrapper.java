package com.success.lottery.web.admin.decorator;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.domain.WinOrderTicketDomain;
import com.success.lottery.util.LotteryTools;


public class TicketConfirmWrapper extends TableDecorator{
	public TicketConfirmWrapper(){
		super();
	}

	public String getSelected(){
		BetTicketDomain ticket = (BetTicketDomain)this.getCurrentRowObject();
		if(ticket.getTicketStatus() != 1){
			return "";
		}
		return "<input id=\"selected\" name=\"selected\" type=\"checkbox\" value=\"" + ticket.getTicketSequence() + "\" />";
	}

	public String getOrderId(){
		BetTicketDomain ticket = (BetTicketDomain)this.getCurrentRowObject();
		return LotteryStaticDefine.getOrderDetailLink(ticket.getOrderId(), ticket.getOrderId());
	}

	public String getLotteryId(){
		return LotteryTools.getLotteryName(((BetTicketDomain)this.getCurrentRowObject()).getLotteryId());
	}

	public String getBetType(){
		BetTicketDomain ticket = (BetTicketDomain)this.getCurrentRowObject();
		return LotteryTools.getPlayBetName(ticket.getLotteryId(), ticket.getPlayType(), ticket.getBetType());
	}

	public int getBetAmount(){
		return (int)(((BetTicketDomain)this.getCurrentRowObject()).getBetAmount() / 100);
	}	
	
	public String getTicketStatus(){
		return LotteryStaticDefine.ticketStatus.get("" + ((BetTicketDomain)this.getCurrentRowObject()).getTicketStatus());
	}
}
