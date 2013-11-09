package com.success.lottery.web;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.util.LotteryTools;


public class TestCash extends ActionSupport {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 3964760163897284409L;
	private int ex_code;
	private String ex_reason;
	
	private int lotteryId = 0;
	private int playType = 0;
	private int betType;
	private String lotteryResult;
	private String winReslut;
	private String betCode;
	private String singleBet;
	private String totalBet;
	
	private String show_error = "SUCESS";
	private List<List<String>> cashResult;
	
	public String testCash(){
		try {
			if(this.lotteryId != 0){
				if(!LotteryTools.checkLotteryBetFormat(this.getLotteryId(), this.getPlayType(), this.getBetType(), this.getBetCode())){
					throw new Exception("投注参数不正确");
				}
				String winTmp = this.winResult(this.getLotteryId());
				if(winTmp == null){
					throw new Exception("投注参数不正确");
				}
				this.setWinReslut(winTmp);
				
//				 List<List<String>> result = LotteryTools.lotteryPrize(this
//						.getLotteryId(), this.getPlayType(), this.getBetType(),
//						this.getLotteryResult(), this.getWinReslut(), this
//								.getBetCode());
				 List<List<String>> result = null;
				 if(result != null && !result.isEmpty()){
					 Collections.sort(result, new SortByPrizeLevel());
				 }
				 this.setCashResult(result);
				 List<String> splitResult = LotteryTools.lotteryBetToSingleList(this.getLotteryId(), this.getPlayType(), this.getBetType(), this.getBetCode());
				 if(splitResult != null){
					 StringBuffer sb = new StringBuffer();
					 for(String one : splitResult){
						 sb.append(one).append(",");
					 }
					 this.setSingleBet(sb.toString());
					 this.setTotalBet(String.valueOf(splitResult.size()));
				 }
			}
		
		}catch(Exception ex){
			ex.printStackTrace();
			this.setShow_error("ERROR");
			this.setEx_reason(ex.getMessage());
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	/*
	 * 
	 * 
	 * 
	 * 	public static final int LOTTERY_1000001 = 1000001;//大乐透
	public static final int LOTTERY_1000002 = 1000002;//七星彩
	public static final int LOTTERY_1000003 = 1000003;//排列三
	public static final int LOTTERY_1000004 = 1000004;//排列五
	public static final int LOTTERY_1000005 = 1000005;//生肖乐
	public static final int LOTTERY_1300001 = 1300001;//胜负彩
	public static final int LOTTERY_1300002 = 1300002;//任选九场
	public static final int LOTTERY_1300003 = 1300003;//6场半全场
	public static final int LOTTERY_1300004 = 1300004;//4场进球彩
	public static final int LOTTERY_1200001 = 1200001;//十一运夺金
	public static final int LOTTERY_1200002 = 1200002;//快乐扑克
	 */
	private String winResult(int lotteryId){
		String winResult = null;
		switch (lotteryId) {
		case 1000001:
			winResult = "1-A3&A5000000#B1&B3000000|2-A15&A198950#B3&B119370|3-A25&A26954#B10&B16172|4-A117&A3000#B35&B1500|5-A2412&A600#B641&B300|6-A9358&A100#B2446&B50|7-A121813&A10#B30412&B5|8-A1353222&A5";
			break;
		case 1000002:
			winResult = "1-2&5000000|2-22&21187|3-162&1800|4-2839&300|5-36143&20|6-455000&5";
			break;
		case 1000003:
			winResult = "1-12&1000|2-342&320|3-34&160";
			break;
		case 1000004:
			winResult = "5&100000";
			break;
		case 1000005:
			winResult = "4484&60";
			break;
		case 1300001:
			winResult = "1-1&5000000|2-35&64379";
			break;
		case 1300002:
			winResult = "174&37944";
			break;
		case 1300003:
			winResult = "100&20000";
			break;
		case 1300004:
			winResult = "6&147846";
			break;
		case 1200001:
			winResult = "1-1&13|2-2&6|3-3&19|4-4&78|5-5&540|6-6&90|7-7&26|8-8&9|9-9&130|10-10&1170|11-11&65|12-12&195";
			break;
		default:
			break;
		}
		return winResult;
	}

	public String getBetCode() {
		return betCode==null?null:betCode.trim();
	}

	public void setBetCode(String betCode) {
		this.betCode = betCode;
	}

	public int getBetType() {
		return betType;
	}

	public void setBetType(int betType) {
		this.betType = betType;
	}

	public int getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getLotteryResult() {
		return lotteryResult==null?null:lotteryResult.trim();
	}

	public void setLotteryResult(String lotteryResult) {
		this.lotteryResult = lotteryResult;
	}

	public int getPlayType() {
		return playType;
	}

	public void setPlayType(int playType) {
		this.playType = playType;
	}

	public String getWinReslut() {
		return winReslut;
	}

	public void setWinReslut(String winReslut) {
		this.winReslut = winReslut;
	}

	public int getEx_code() {
		return ex_code;
	}

	public void setEx_code(int ex_code) {
		this.ex_code = ex_code;
	}

	public String getEx_reason() {
		return ex_reason;
	}

	public void setEx_reason(String ex_reason) {
		this.ex_reason = ex_reason;
	}

	public List<List<String>> getCashResult() {
		return cashResult;
	}

	public void setCashResult(List<List<String>> cashResult) {
		this.cashResult = cashResult;
	}

	public String getShow_error() {
		return show_error;
	}

	public void setShow_error(String show_error) {
		this.show_error = show_error;
	}

	public String getSingleBet() {
		return singleBet;
	}

	public void setSingleBet(String singleBet) {
		this.singleBet = singleBet;
	}

	public String getTotalBet() {
		return totalBet;
	}

	public void setTotalBet(String totalBet) {
		this.totalBet = totalBet;
	}
}

class SortByPrizeLevel implements Comparator{    
	public int compare(Object obj1,Object obj2){    
	 List<String> list1 = (List<String>)obj1;    
	 List<String> list2 = (List<String>)obj2;    
	return list1.get(1).compareTo(list2.get(1));
}    
}
