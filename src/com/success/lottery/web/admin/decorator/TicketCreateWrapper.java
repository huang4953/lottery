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
//	<display:column title="���" ><c:out value="${ticketes_rowNum + ticketList.perPage * (ticketList.pageNumber - 1)}" /></display:column>
//	<display:column  property="orderId" title="�������"  />
//	<display:column  property="lotteryId" title="����"   />
//	<display:column  property="betTerm" title="�ں�"   />
//	<display:column  property="betType" title="Ͷע��ʽ"   />
//	<display:column  property="ticketTime" title="Ͷעʱ��"/>
//	<display:column  property="betAmount" title="Ͷע���"/>
//	<display:column  property="betMultiple" title="Ͷע����"/>
//	<display:column  property="betCode" title="Ͷע����"/>
//	<display:column  property="remark" title="��ע"/>	
	
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
		return "�˴���ע";
	}
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
	}
}
