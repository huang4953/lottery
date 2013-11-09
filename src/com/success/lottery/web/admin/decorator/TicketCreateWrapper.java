package com.success.lottery.web.admin.decorator;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.domain.WinOrderTicketDomain;
import com.success.lottery.util.LotteryTools;


public class TicketCreateWrapper extends TableDecorator{

	public TicketCreateWrapper(){
		super();
	}
	
//	decorator="com.success.lottery.web.admin.decorator.TicketCreateWrapper">
//	<display:column title="序号" ><c:out value="${ticketes_rowNum + ticketList.perPage * (ticketList.pageNumber - 1)}" /></display:column>
//	<display:column  property="orderId" title="订单编号"  />
//	<display:column  property="lotteryId" title="彩种"   />
//	<display:column  property="betTerm" title="期号"   />
//	<display:column  property="betType" title="投注方式"   />
//	<display:column  property="ticketTime" title="投注时间"/>
//	<display:column  property="betAmount" title="投注金额"/>
//	<display:column  property="betMultiple" title="投注倍数"/>
//	<display:column  property="betCode" title="投注内容"/>
//	<display:column  property="remark" title="备注"/>	
	
//	public String getOrderId(){
//		return LotteryStaticDefine.getOrderDetailLink(((BetTicketDomain)this.getCurrentRowObject()).getOrderId());
//	}

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
	
	public String getRemark(){
		return "此处备注";
	}
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
	}
}
