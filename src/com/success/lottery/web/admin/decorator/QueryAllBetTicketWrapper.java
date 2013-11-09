package com.success.lottery.web.admin.decorator;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.ticket.domain.BetTicketAcountDomain;
import com.success.lottery.util.LotteryTools;

public class QueryAllBetTicketWrapper extends TableDecorator {
	private String lotteryId;
	private String ticketSequence;
	private String subTicketSequence;
	private String orderId;
	private String printTime;
	private String betType;
	private String playType;
	private String betAmount;
	private String ticketStatus;
	private String prizeResult;
	private String preTaxPrize;
	private String showAccountDetail;
	private String planSource_name;
	public String getPlanSource_name() {
		BetTicketAcountDomain betOrder = (BetTicketAcountDomain)super.getCurrentRowObject();
		this.setPlanSource_name(LotteryStaticDefine.planSource.get(String.valueOf(betOrder.getPlanSource())));
		return this.planSource_name;
	}
	public void setPlanSource_name(String planSource_name) {
		this.planSource_name = planSource_name;
	}
	public String getShowAccountDetail() {
		BetTicketAcountDomain betOrder = (BetTicketAcountDomain)super.getCurrentRowObject();
		String mobilePhone = betOrder.getMobilePhone();
		this.setShowAccountDetail(LotteryStaticDefine.getUserDetailLink(mobilePhone));
		return this.showAccountDetail;
	}
	public void setShowAccountDetail(String showAccountDetail) {
		this.showAccountDetail = showAccountDetail;
	}
	public String getLotteryId() {
		BetTicketAcountDomain BetTicketAcountDomain  = (BetTicketAcountDomain)this.getCurrentRowObject();
		if(null == BetTicketAcountDomain.getLotteryId()){
			return null;
		}
		return LotteryTools.getLotteryName(BetTicketAcountDomain.getLotteryId());
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getTicketSequence() {
		BetTicketAcountDomain BetTicketAcountDomain  = (BetTicketAcountDomain)this.getCurrentRowObject();
		if(null==BetTicketAcountDomain.getTicketSequence()){
			return null;
		}
		this.setTicketSequence(LotteryStaticDefine.getAllTheLink("betTicketDetail.jhtml",
				"ticketSequence",BetTicketAcountDomain.getTicketSequence(),BetTicketAcountDomain.getTicketSequence()));
		return ticketSequence;
	}
	public void setTicketSequence(String ticketSequence) {
		this.ticketSequence = ticketSequence;
	}
	public String getOrderId() {
		BetTicketAcountDomain BetTicketAcountDomain  = (BetTicketAcountDomain)this.getCurrentRowObject();
		if(null==BetTicketAcountDomain.getOrderId()){
			return null;
		}
		this.setOrderId(LotteryStaticDefine.getAllTheLink("showOrderInfoDetail.jhtml",
				"orderId",BetTicketAcountDomain.getOrderId(),BetTicketAcountDomain.getOrderId()));
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSubTicketSequence() {
		BetTicketAcountDomain BetTicketAcountDomain  = (BetTicketAcountDomain)this.getCurrentRowObject();
		if(null==BetTicketAcountDomain.getTicketSequence()){
			return null;
		}
		this.setSubTicketSequence(LotteryStaticDefine.getSubAllTheLink("betTicketDetail.jhtml",
				"ticketSequence",BetTicketAcountDomain.getTicketSequence(),BetTicketAcountDomain.getTicketSequence()));
		return subTicketSequence;
	}
	public void setSubTicketSequence(String subTicketSequence) {
		this.subTicketSequence = subTicketSequence;
	}
	public String getPrintTime() {
		BetTicketAcountDomain BetTicketAcountDomain  = (BetTicketAcountDomain)this.getCurrentRowObject();
		if(null == BetTicketAcountDomain.getPrintTime()){
			return null;
		}
		this.setPrintTime(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(BetTicketAcountDomain.getPrintTime()).toString().trim());
		return this.printTime;
	}
	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}
	public String getBetType() {
		BetTicketAcountDomain BetTicketAcountDomain  = (BetTicketAcountDomain)this.getCurrentRowObject();
		if(null == BetTicketAcountDomain.getPlayType()||null==BetTicketAcountDomain.getLotteryId()||null==BetTicketAcountDomain.getBetType()){
			return null;
		}
		this.setBetType(LotteryTools.getPlayBetName(BetTicketAcountDomain.getLotteryId(), BetTicketAcountDomain.getPlayType(), BetTicketAcountDomain.getBetType()));
		return this.betType;
	}
	public void setBetType(String betType) {
		this.betType = betType;
	}
	public String getPlayType() {
		BetTicketAcountDomain BetTicketAcountDomain  = (BetTicketAcountDomain)this.getCurrentRowObject();
		this.setPlayType(LotteryTools.getPlayTypeName(BetTicketAcountDomain.getLotteryId(), BetTicketAcountDomain.getPlayType()));
		return playType;
	}
	public void setPlayType(String playType) {
		this.playType = playType;
	}
	public String getBetAmount() {
		BetTicketAcountDomain BetTicketAcountDomain  = (BetTicketAcountDomain)this.getCurrentRowObject();
		float betMoney = BetTicketAcountDomain.getBetAmount()/100;
		NumberFormat   nbf=NumberFormat.getInstance();   
		nbf.setMinimumFractionDigits(2);   
		nbf.setMaximumFractionDigits(2);   
		this.setBetAmount(nbf.format(Double.parseDouble(Float.toString(betMoney))));
		return betAmount;
	}
	public void setBetAmount(String betAmount) {
		this.betAmount = betAmount;
	}
	public String getTicketStatus() {
		BetTicketAcountDomain BetTicketAcountDomain  = (BetTicketAcountDomain)this.getCurrentRowObject();
		if(null == BetTicketAcountDomain.getTicketStatus()){
			return null;
		}
		this.setTicketStatus(LotteryStaticDefine.ticketStatusForWeb.get(BetTicketAcountDomain.getTicketStatus()+""));
		return ticketStatus;
	}
	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	public String getPrizeResult() {
		BetTicketAcountDomain BetTicketAcountDomain  = (BetTicketAcountDomain)this.getCurrentRowObject();
		if(null == BetTicketAcountDomain.getPrizeResult()){
			return null;
		}
		this.setPrizeResult(LotteryStaticDefine.ticketWinStatus.get(BetTicketAcountDomain.getPrizeResult()+""));
		return prizeResult;
	}
	public void setPrizeResult(String prizeResult) {
		this.prizeResult = prizeResult;
	}
	public String getPreTaxPrize() {
		BetTicketAcountDomain BetTicketAcountDomain  = (BetTicketAcountDomain)this.getCurrentRowObject();
		float prizeMoney = BetTicketAcountDomain.getPreTaxPrize()/100;
		NumberFormat   nbf=NumberFormat.getInstance();   
		nbf.setMinimumFractionDigits(2);   
		nbf.setMaximumFractionDigits(2);   
		this.setPreTaxPrize(nbf.format(Double.parseDouble(Float.toString(prizeMoney))));
		return preTaxPrize;
	}
	public void setPreTaxPrize(String preTaxPrize) {
		this.preTaxPrize = preTaxPrize;
	}
}
