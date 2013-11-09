package com.success.lottery.web.trade.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.util.LotteryTools;
import com.success.lottery.web.formbean.RaceBean;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;
import com.success.utils.ApplicationContextUtils;

/**    
 * @{#} RacePageAction.java Create on May 5, 2010 10:44:18 AM    
 *    
 * Copyright (c) 2010 by success.    

 * @author Gavin

 * @version 1.0 

 * @description  ×ã²ÊÇëÇóaction

 */ 
@SuppressWarnings("serial")
public class RacePageAction extends LotteryWebBaseActon{
	private String type;
	private String term;
	private LotteryTermModel lotteryterm,historyTerm;
	private List<LotteryTermModel> currentTermList,historyTermList;
	private List<RaceBean> raceList;
	private int betmoney;
	private int betCount;
	private String betCode;
	private Map<Integer,String> codeMapList;
	public Map<Integer, String> getCodeMapList() {
		return codeMapList;
	}
	public void setCodeMapList(Map<Integer, String> codeMapList) {
		this.codeMapList = codeMapList;
	}
	public String getBetCode() {
		return betCode;
	}
	public void setBetCode(String betCode) {
		this.betCode = betCode;
	}
	public String rj(){
		setDataByType(1300002);
		return SUCCESS;
	}
	public String zc6(){
		setDataByType(1300003);
		return SUCCESS;
	}
	public String jq4(){
		setDataByType(1300004);
		return SUCCESS;
	}
	public String rs(){
		setDataByType(1300001);
		return SUCCESS;
	}
	public void setDataByType(int type){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		this.setBetCode(betCode);
		if(null!=betCode&&!"".equals(betCode))
		{
			codeMapList=new HashMap<Integer, String>();
			String result=LotteryTools.lotteryBetToSingle(type, 0, 1, betCode); 
			this.setBetmoney(Integer.parseInt(result.split("#")[1])/100);
			this.setBetCount(Integer.parseInt(result.split("#")[0]));
			String[] arr=betCode.split("\\*");
			System.out.println(betCode);
			for(int i=1;i<=arr.length;i++)
			{
				codeMapList.put(i,arr[i-1]);
			}
		}
		try {
			currentTermList = termservice.queryTermCurrentInfo(type,2);
			if(null!=currentTermList && currentTermList.size()>0){
				if(!StringUtils.isEmpty(term)){
					for(LotteryTermModel term : currentTermList){
						if(term.getTermNo().equals(this.term))
							lotteryterm =term;
					}
				}else
					lotteryterm = currentTermList.get(0);
					raceList = RaceBean.getRaceBeanList(lotteryterm);
			}
			historyTermList=termservice.queryHaveWinTermInfo(type, 18);
			this.historyTerm=termservice.queryTermLastCashInfo(type);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<RaceBean> getRaceList() {
		return raceList;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	
	public String getTerm() {
		return term;
	}
	public List<LotteryTermModel> getCurrentTermList() {
		return currentTermList;
	}
	public LotteryTermModel getLotteryterm() {
		return lotteryterm;
	}
	public LotteryTermModel getHistoryTerm() {
		return historyTerm;
	}
	public void setHistoryTerm(LotteryTermModel historyTerm) {
		this.historyTerm = historyTerm;
	}
	public List<LotteryTermModel> getHistoryTermList() {
		return historyTermList;
	}
	public void setHistoryTermList(List<LotteryTermModel> historyTermList) {
		this.historyTermList = historyTermList;
	}
	public int getBetmoney() {
		return betmoney;
	}
	public void setBetmoney(int betmoney) {
		this.betmoney = betmoney;
	}
	public int getBetCount() {
		return betCount;
	}
	public void setBetCount(int betCount) {
		this.betCount = betCount;
	}
}
	