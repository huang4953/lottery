package com.success.lottery.ticket.domain;

import java.sql.Timestamp;
public class WinOrderTicketDomain{

	private String		orderId;
	private int			lotteryId;
	private String		betTerm;
	private int			playType;
	private int			betType;
	private int			betMultiple;
	private Timestamp	ticketTime;
	private String		areaCode;
	private String		printStation;
	private int			ticketStatus;
	private String		ticketId;
	private String		printerId;
	private String		printResult;
	private Timestamp	printTime;
	private String		ticketData;
	private String		ticketDataMD;
	private String		ticketPassword;
	private int			prizeResult	= 0;
	private int			saveStatus	= 0;
	private String		planId;
	private int			planSource;
	private long		userId;
	private String		betCode;
	private String		winCode;
	private int			betAmount;
	private Timestamp	orderTime;
	private int			orderStatus;
	private String		ticketStat;
	private int			winStatus;
	private long		preTaxPrize;
	private String		reserve;

	public String getOrderId(){
		return orderId;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public int getLotteryId(){
		return lotteryId;
	}

	public void setLotteryId(int lotteryId){
		this.lotteryId = lotteryId;
	}

	public String getBetTerm(){
		return betTerm;
	}

	public void setBetTerm(String betTerm){
		this.betTerm = betTerm;
	}

	public int getPlayType(){
		return playType;
	}

	public void setPlayType(int playType){
		this.playType = playType;
	}

	public int getBetType(){
		return betType;
	}

	public void setBetType(int betType){
		this.betType = betType;
	}

	public int getBetMultiple(){
		return betMultiple;
	}

	public void setBetMultiple(int betMultiple){
		this.betMultiple = betMultiple;
	}

	public Timestamp getTicketTime(){
		return ticketTime;
	}

	public void setTicketTime(Timestamp ticketTime){
		this.ticketTime = ticketTime;
	}

	public String getAreaCode(){
		return areaCode;
	}

	public void setAreaCode(String areaCode){
		this.areaCode = areaCode;
	}

	public String getPrintStation(){
		return printStation;
	}

	public void setPrintStation(String printStation){
		this.printStation = printStation;
	}

	public int getTicketStatus(){
		return ticketStatus;
	}

	public void setTicketStatus(int ticketStatus){
		this.ticketStatus = ticketStatus;
	}

	public String getTicketId(){
		return ticketId;
	}

	public void setTicketId(String ticketId){
		this.ticketId = ticketId;
	}

	public String getPrinterId(){
		return printerId;
	}

	public void setPrinterId(String printerId){
		this.printerId = printerId;
	}

	public String getPrintResult(){
		return printResult;
	}

	public void setPrintResult(String printResult){
		this.printResult = printResult;
	}

	public Timestamp getPrintTime(){
		return printTime;
	}

	public void setPrintTime(Timestamp printTime){
		this.printTime = printTime;
	}

	public String getTicketData(){
		return ticketData;
	}

	public void setTicketData(String ticketData){
		this.ticketData = ticketData;
	}

	public String getTicketDataMD(){
		return ticketDataMD;
	}

	public void setTicketDataMD(String ticketDataMD){
		this.ticketDataMD = ticketDataMD;
	}

	public String getTicketPassword(){
		return ticketPassword;
	}

	public void setTicketPassword(String ticketPassword){
		this.ticketPassword = ticketPassword;
	}

	public int getPrizeResult(){
		return prizeResult;
	}

	public void setPrizeResult(int prizeResult){
		this.prizeResult = prizeResult;
	}

	public int getSaveStatus(){
		return saveStatus;
	}

	public void setSaveStatus(int saveStatus){
		this.saveStatus = saveStatus;
	}

	public String getPlanId(){
		return planId;
	}

	public void setPlanId(String planId){
		this.planId = planId;
	}

	public int getPlanSource(){
		return planSource;
	}

	public void setPlanSource(int planSource){
		this.planSource = planSource;
	}

	public long getUserId(){
		return userId;
	}

	public void setUserId(long userId){
		this.userId = userId;
	}

	public String getBetCode(){
		return betCode;
	}

	public void setBetCode(String betCode){
		this.betCode = betCode;
	}

	public String getWinCode(){
		return winCode;
	}

	public void setWinCode(String winCode){
		this.winCode = winCode;
	}

	public int getBetAmount(){
		return betAmount;
	}

	public void setBetAmount(int betAmount){
		this.betAmount = betAmount;
	}

	public Timestamp getOrderTime(){
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime){
		this.orderTime = orderTime;
	}

	public int getOrderStatus(){
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus){
		this.orderStatus = orderStatus;
	}

	public String getTicketStat(){
		return ticketStat;
	}

	public void setTicketStat(String ticketStat){
		this.ticketStat = ticketStat;
	}

	public int getWinStatus(){
		return winStatus;
	}

	public void setWinStatus(int winStatus){
		this.winStatus = winStatus;
	}

	public long getPreTaxPrize(){
		return preTaxPrize;
	}

	public void setPreTaxPrize(long preTaxPrize){
		this.preTaxPrize = preTaxPrize;
	}

	public String getReserve(){
		return reserve;
	}

	public void setReserve(String reserve){
		this.reserve = reserve;
	}

	public static void main(String[] args){
		// TODO Auto-generated method stub
	}
}
