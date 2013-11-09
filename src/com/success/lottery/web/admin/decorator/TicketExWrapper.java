/**
 * Title: TicketExWrapper.java
 * @Package com.success.lottery.web.admin.decorator
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-5 下午01:50:03
 * @version V1.0
 */
package com.success.lottery.web.admin.decorator;

import java.math.BigDecimal;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.util.LotteryTools;

/**
 * com.success.lottery.web.admin.decorator
 * TicketExWrapper.java
 * TicketExWrapper
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-5 下午01:50:03
 * 
 */

public class TicketExWrapper extends TableDecorator {
	
	private String lotteryCn_name;
	private String ticketStatus_name;
	private String playBet_name;
	
	private BigDecimal betAmount;
	
	public String getLotteryCn_name() {
		BetTicketDomain betOrder = (BetTicketDomain)super.getCurrentRowObject();
		this.setLotteryCn_name(LotteryTools.getLotteryName(betOrder.getLotteryId()));
		return this.lotteryCn_name;
	}

	public void setLotteryCn_name(String lotteryCn_name) {
		this.lotteryCn_name = lotteryCn_name;
	}

	public String getTicketStatus_name() {
		BetTicketDomain betTicket = (BetTicketDomain)super.getCurrentRowObject();
		return LotteryStaticDefine.ticketStatus.get(String.valueOf(betTicket.getTicketStatus()));
	}

	public void setTicketStatus_name(String ticketStatus_name) {
		this.ticketStatus_name = ticketStatus_name;
	}
	
	public String getPlayBet_name() {
		BetTicketDomain betTicket = (BetTicketDomain)super.getCurrentRowObject();
		this.setPlayBet_name(LotteryTools.getPlayBetName(betTicket.getLotteryId(),betTicket.getPlayType(),betTicket.getBetType()));
		return this.playBet_name;
	}

	public void setPlayBet_name(String playBet_name) {
		this.playBet_name = playBet_name;
	}

	public BigDecimal getBetAmount() {
		return new BigDecimal(((BetTicketDomain)this.getCurrentRowObject()).getBetAmount()).divide(new BigDecimal(100));
	}

	public void setBetAmount(BigDecimal betAmount) {
		this.betAmount = betAmount;
	}
	
}
