/**
 * Title: CommonTicketBet.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-12 下午05:00:17
 * @version V1.0
 */
package com.success.protocol.ticket.zzy.model;

/**
 * com.success.protocol.ticket.zzy
 * CommonTicketBet.java
 * CommonTicketBet
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-12 下午05:00:17
 * 
 */

public class CommonTicketBet {
	
	private String lotteryId;//采种
	private String termNo;//彩期
	private String schemeTitle = "";//方案标题
	private String schemeDescription="";//方案描述	可选
	private String schemeQuotient="1";//方案份额	1
	private String lotterySaleid;//销售方式，多注用^分开如：1^2^
	private String schemeLotteryInfo;//方案内容（投注内容,注：如果一个方案有多注，用^分开）
	private String schemeNumbers;//倍数(不填默认为1倍)	>=1
	private String lotteryNumbers;//彩票注数,注：没有成倍数之前
	private String schemeValue;//方案金额
	private String agentOperateId;//终端流水号
	private String viewFlag = "2";//1 发起方案，2 普通投注
	private String viewlimit = "1";//查看限制代码（0可以查看方案内容，1不能查看方案内容）
	private String isCheckOff = "0";//是否保底(1为保底，0为不保
	private String checkOffQuotient = "0";//购买份额(用户发起方案后，购买的份额，可以是0份)
	private String quotients = "1";//投注方式(1
	private String investTypeId = "1";//
	private String isAppend;//是否追加（如果追加，则一注为三元,此参数主要是针对超级大乐透玩法35选5有效) 
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 * @param playType
	 * @param betType
	 * @param ticketSequences
	 * @param betCode
	 * @param betmultiple
	 * @param betCount
	 * @param betAmount
	 */
	public CommonTicketBet(String lotteryId, String termNo,
			 String playType, String betType,String ticketSequences,String betCode,
			String betmultiple, String betCount, String betAmount) {
		this.lotteryId = lotteryId;
		this.termNo = termNo;
		this.agentOperateId = ticketSequences;
		this.isAppend = playType;
		this.lotterySaleid = betType;
		this.schemeNumbers = betmultiple;
		this.lotteryNumbers = betCount;
		this.schemeValue = betAmount;
		this.schemeLotteryInfo = betCode;
	}
	/**
	 * 
	 *Title: 
	 *Description:
	 */
	public CommonTicketBet(){
		
	}

	public String getAgentOperateId() {
		return agentOperateId;
	}

	public void setAgentOperateId(String agentOperateId) {
		this.agentOperateId = agentOperateId;
	}

	public String getCheckOffQuotient() {
		return checkOffQuotient;
	}

	public void setCheckOffQuotient(String checkOffQuotient) {
		this.checkOffQuotient = checkOffQuotient;
	}

	public String getInvestTypeId() {
		return investTypeId;
	}

	public void setInvestTypeId(String investTypeId) {
		this.investTypeId = investTypeId;
	}

	public String getIsAppend() {
		return isAppend;
	}

	public void setIsAppend(String isAppend) {
		this.isAppend = isAppend;
	}

	public String getIsCheckOff() {
		return isCheckOff;
	}

	public void setIsCheckOff(String isCheckOff) {
		this.isCheckOff = isCheckOff;
	}

	public String getLotteryNumbers() {
		return lotteryNumbers;
	}

	public void setLotteryNumbers(String lotteryNumbers) {
		this.lotteryNumbers = lotteryNumbers;
	}

	public String getLotterySaleid() {
		return lotterySaleid;
	}

	public void setLotterySaleid(String lotterySaleid) {
		this.lotterySaleid = lotterySaleid;
	}

	public String getQuotients() {
		return quotients;
	}

	public void setQuotients(String quotients) {
		this.quotients = quotients;
	}

	public String getSchemeDescription() {
		return schemeDescription;
	}

	public void setSchemeDescription(String schemeDescription) {
		this.schemeDescription = schemeDescription;
	}

	public String getSchemeLotteryInfo() {
		return schemeLotteryInfo;
	}

	public void setSchemeLotteryInfo(String schemeLotteryInfo) {
		this.schemeLotteryInfo = schemeLotteryInfo;
	}

	public String getSchemeNumbers() {
		return schemeNumbers;
	}

	public void setSchemeNumbers(String schemeNumbers) {
		this.schemeNumbers = schemeNumbers;
	}

	public String getSchemeQuotient() {
		return schemeQuotient;
	}

	public void setSchemeQuotient(String schemeQuotient) {
		this.schemeQuotient = schemeQuotient;
	}

	public String getSchemeTitle() {
		return schemeTitle;
	}

	public void setSchemeTitle(String schemeTitle) {
		this.schemeTitle = schemeTitle;
	}

	public String getSchemeValue() {
		return schemeValue;
	}

	public void setSchemeValue(String schemeValue) {
		this.schemeValue = schemeValue;
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}

	public String getViewlimit() {
		return viewlimit;
	}

	public void setViewlimit(String viewlimit) {
		this.viewlimit = viewlimit;
	}
	public String getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	
	

}
