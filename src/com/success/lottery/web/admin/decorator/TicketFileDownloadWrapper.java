package com.success.lottery.web.admin.decorator;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.util.LotteryTools;


public class TicketFileDownloadWrapper extends TableDecorator{
	public TicketFileDownloadWrapper(){
		super();
	}
	public String getLotteryId(){
		return LotteryTools.getLotteryName(((BetTicketDomain)this.getCurrentRowObject()).getLotteryId());
	}
	public String getBetType(){
		BetTicketDomain ticket = (BetTicketDomain)this.getCurrentRowObject();
		return LotteryTools.getPlayBetName(ticket.getLotteryId(), ticket.getPlayType(), ticket.getBetType());
	}
	 
	public String getFileName(){
		BetTicketDomain ticket = (BetTicketDomain)this.getCurrentRowObject();
		return "<a href=\"download.jhtml?ticketSequence=" + ticket.getTicketSequence() + "\" >" + ticket.getTicketData() + "</a>";
	}
}
