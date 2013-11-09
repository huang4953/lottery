/**
 * Title: LBAP_term.java
 * @Package com.success.protocol.lbap
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-3 下午08:43:04
 * @version V1.0
 */
package com.success.protocol.lbap;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.success.lottery.util.SalesResult;
import com.success.lottery.util.WinResult;

/**
 * com.success.protocol.lbap
 * LBAP_term.java
 * LBAP_term
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-3 下午08:43:04
 * 
 */

public class LBAP_term {
	
	private String lotteryId;
	private String winTermNo;
	private String preTermNo;
	private String lotteryResult;
	private String missCount;
	private String limitNumber;
	private String salesVolume;
	private String jackpot;
	private String reserve;
	private List<WinResult> winResult;
	private List<Term> termList;
	
	public String encodeCurrent(){
		/*
		StringBuffer sb = new StringBuffer();
		sb.append("<>");
		sb.append("<userId>").append(this.getUserId()).append("</userId>");
		return sb.toString();
		*/
		
		String result = "";
		
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element termInfo = doc.addElement("termInfo");
		termInfo.addAttribute("winTermNo", this.getWinTermNo());
		termInfo.addAttribute("preTermNo", convert(this.getPreTermNo()));
		termInfo.addElement("lotteryResult").setText(convert(this.getLotteryResult()));
		termInfo.addElement("missCount").setText(convert(this.getMissCount()));
		termInfo.addElement("limitNumber").setText(convert(this.getLimitNumber()));
		termInfo.addElement("salesVolume").setText(convert(this.getSalesVolume()));
		termInfo.addElement("jackpot").setText(convert(this.getJackpot()));
		//奖金结果
		Element winResult = termInfo.addElement("winResult");
		if(this.getWinResult() != null){
			for(WinResult one : this.getWinResult()){
				Element win = winResult.addElement("win");
				win.addAttribute("id", String.valueOf(one.getWinId()));
				win.addElement("winCount").setText(convert(one.getWinCount()));
				win.addElement("winMoney").setText(convert(one.getWinMoney()));
				if(StringUtils.isNotEmpty(one.getWinAddCount()) && StringUtils.isNotEmpty(one.getWinAddMoney())){
					win.addElement("winAddCount").setText(one.getWinAddCount());
					win.addElement("winAddMoney").setText(one.getWinAddMoney());
				}
			}
		}
		//在售期彩种信息
		if(this.getTermList() != null){
			for(Term one : this.getTermList()){
				Element term = termInfo.addElement("term");
				term.addAttribute("termNo", one.getTermNo());
				term.addElement("termStatus").setText(String.valueOf(one.getTermStatus()));
				term.addElement("winStatus").setText(String.valueOf(one.getWinStatus()));
				term.addElement("saleStatus").setText(String.valueOf(one.getSaleStatus()));
				term.addElement("startTime").setText(convert(one.getStartTime()));
				term.addElement("deadLine").setText(convert(one.getDeadLine()));
				term.addElement("deadLine2").setText(convert(one.getDeadLine2()));
				term.addElement("winLine").setText(convert(one.getWinLine()));
				term.addElement("startTime2").setText(convert(one.getStartTime2()));
				term.addElement("deadLine3").setText(convert(one.getDeadLine3()));
				term.addElement("winLine2").setText(convert(one.getWinLine2()));
				term.addElement("changeLine").setText(convert(one.getChangeLine()));
				term.addElement("reserve").setText(convert(one.getReserve()));
				
				if(one.getGameInfo() != null){
					Element gameInfo = term.addElement("gameInfo");
					for(SalesResult sales : one.getGameInfo()){
						Element game = gameInfo.addElement("game");
						game.addAttribute("id", String.valueOf(sales.getSlaesId()));
						game.addElement("homeTeam").setText(sales.getHomeTeam());
						game.addElement("awayTeam").setText(sales.getAwayTeam());
						game.addElement("leagueMatch").setText(sales.getLeagueMatch());
						game.addElement("gameDate").setText(sales.getGameDate());
						game.addElement("reserve").setText(convert(sales.getReserve()));
					}
				}
			}
		}
		result = termInfo.asXML();
		
		return result;
		
	}
	
	public String encodeTermInfo(){
		String result = "";
		
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element termInfo = doc.addElement("termInfo");
		Term oneTerm = this.termList.get(0)==null ? new Term() : this.termList.get(0);
		termInfo.addElement("lotteryId").setText(this.getLotteryId());
		termInfo.addElement("termNo").setText(this.termList.get(0).getTermNo());
		termInfo.addElement("termStatus").setText(String.valueOf(oneTerm.getTermStatus()));
		termInfo.addElement("winStatus").setText(String.valueOf(oneTerm.getWinStatus()));
		termInfo.addElement("saleStatus").setText(String.valueOf(oneTerm.getSaleStatus()));
		termInfo.addElement("startTime").setText(convert(oneTerm.getStartTime()));
		termInfo.addElement("deadLine").setText(convert(oneTerm.getDeadLine()));
		termInfo.addElement("deadLine2").setText(convert(oneTerm.getDeadLine2()));
		termInfo.addElement("winLine").setText(convert(oneTerm.getWinLine()));
		termInfo.addElement("startTime2").setText(convert(oneTerm.getStartTime2()));
		termInfo.addElement("deadLine3").setText(convert(oneTerm.getDeadLine2()));
		termInfo.addElement("winLine2").setText(convert(oneTerm.getWinLine2()));
		termInfo.addElement("changeLine").setText(convert(oneTerm.getChangeLine()));
		termInfo.addElement("reserve").setText(convert(oneTerm.getReserve()));
		
		termInfo.addElement("lotteryResult").setText(convert(this.getLotteryResult()));
		termInfo.addElement("missCount").setText(convert(this.getMissCount()));
		termInfo.addElement("limitNumber").setText(convert(this.getLimitNumber()));
		termInfo.addElement("salesVolume").setText(convert(this.getSalesVolume()));
		termInfo.addElement("jackpot").setText(convert(this.getJackpot()));
		//销售信息
		if(oneTerm.getGameInfo() != null){
			Element gameInfo = termInfo.addElement("gameInfo");
			for(SalesResult sales : oneTerm.getGameInfo()){
				Element game = gameInfo.addElement("game");
				game.addAttribute("id", String.valueOf(sales.getSlaesId()));
				game.addElement("homeTeam").setText(sales.getHomeTeam());
				game.addElement("awayTeam").setText(sales.getAwayTeam());
				game.addElement("leagueMatch").setText(sales.getLeagueMatch());
				game.addElement("gameDate").setText(sales.getGameDate());
				game.addElement("reserve").setText(convert(sales.getReserve()));
			}
		}
		//奖金结果
		Element winResult = termInfo.addElement("winResult");
		if(this.getWinResult() != null){
			for(WinResult one : this.getWinResult()){
				Element win = winResult.addElement("win");
				win.addAttribute("id", String.valueOf(one.getWinId()));
				win.addElement("winCount").setText(one.getWinCount());
				win.addElement("winMoney").setText(one.getWinMoney());
				if(StringUtils.isNotEmpty(one.getWinAddCount()) && StringUtils.isNotEmpty(one.getWinAddMoney())){
					win.addElement("winAddCount").setText(one.getWinAddCount());
					win.addElement("winAddMoney").setText(one.getWinAddMoney());
				}
			}
		}
		
		result = termInfo.asXML().replaceAll("<termInfo>", "").replaceAll("</termInfo>", "").replace("<termInfo/>", "");
		
		return result;
	}
	
	private String convert(String str){
		return str == null ? "" : str.trim();
	}
	
	public String getJackpot() {
		return jackpot;
	}
	public void setJackpot(String jackpot) {
		this.jackpot = jackpot;
	}
	public String getLimitNumber() {
		return limitNumber;
	}
	public void setLimitNumber(String limitNumber) {
		this.limitNumber = limitNumber;
	}
	public String getLotteryResult() {
		return lotteryResult;
	}
	public void setLotteryResult(String lotteryResult) {
		this.lotteryResult = lotteryResult;
	}
	public String getMissCount() {
		return missCount;
	}
	public void setMissCount(String missCount) {
		this.missCount = missCount;
	}
	public String getPreTermNo() {
		return preTermNo;
	}
	public void setPreTermNo(String preTermNo) {
		this.preTermNo = preTermNo;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	public String getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}
	public List<WinResult> getWinResult() {
		return winResult;
	}
	public void setWinResult(List<WinResult> winResult) {
		this.winResult = winResult;
	}
	public String getWinTermNo() {
		return winTermNo;
	}
	public void setWinTermNo(String winTermNo) {
		this.winTermNo = winTermNo;
	}
	
	public List<Term> getTermList() {
		return termList;
	}

	public void setTermList(List<Term> termList) {
		this.termList = termList;
	}
	
	public Term getTerm(){
		return new Term();
	}
	
	public class Term{
		private String termNo;
		private int termStatus;
		private int winStatus;
		private int saleStatus;
		private String startTime;
		private String deadLine;
		private String deadLine2;
		private String winLine;
		private String startTime2;
		private String deadLine3;
		private String winLine2;
		private String changeLine;
		private List<SalesResult> gameInfo;
		private String reserve;
		public String getChangeLine() {
			return changeLine;
		}
		public void setChangeLine(String changeLine) {
			this.changeLine = changeLine;
		}
		public String getDeadLine() {
			return deadLine;
		}
		public void setDeadLine(String deadLine) {
			this.deadLine = deadLine;
		}
		public String getDeadLine2() {
			return deadLine2;
		}
		public void setDeadLine2(String deadLine2) {
			this.deadLine2 = deadLine2;
		}
		public String getDeadLine3() {
			return deadLine3;
		}
		public void setDeadLine3(String deadLine3) {
			this.deadLine3 = deadLine3;
		}
		
		public String getReserve() {
			return reserve;
		}
		public void setReserve(String reserve) {
			this.reserve = reserve;
		}
		public int getSaleStatus() {
			return saleStatus;
		}
		public void setSaleStatus(int saleStatus) {
			this.saleStatus = saleStatus;
		}
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public String getStartTime2() {
			return startTime2;
		}
		public void setStartTime2(String startTime2) {
			this.startTime2 = startTime2;
		}
		public String getTermNo() {
			return termNo;
		}
		public void setTermNo(String termNo) {
			this.termNo = termNo;
		}
		public int getTermStatus() {
			return termStatus;
		}
		public void setTermStatus(int termStatus) {
			this.termStatus = termStatus;
		}
		public String getWinLine() {
			return winLine;
		}
		public void setWinLine(String winLine) {
			this.winLine = winLine;
		}
		public String getWinLine2() {
			return winLine2;
		}
		public void setWinLine2(String winLine2) {
			this.winLine2 = winLine2;
		}
		public int getWinStatus() {
			return winStatus;
		}
		public void setWinStatus(int winStatus) {
			this.winStatus = winStatus;
		}
		public List<SalesResult> getGameInfo() {
			return gameInfo;
		}
		public void setGameInfo(List<SalesResult> gameInfo) {
			this.gameInfo = gameInfo;
		}
		
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
}


