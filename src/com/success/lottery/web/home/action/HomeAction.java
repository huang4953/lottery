package com.success.lottery.web.home.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.success.lottery.business.domain.PrizeUserDomain;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.util.MyComparator;
import com.success.lottery.web.formbean.RaceBean;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;
import com.success.utils.ApplicationContextUtils;

/**    
 * @{#} HomeAction.java Create on Jun 7, 2010 8:44:02 PM    
 *    
 * Copyright (c) 2010 by success.    

 * @author Gavin

 * @version 1.0 

 * @description  首页Action
   

 */ 
@SuppressWarnings("serial")
public class HomeAction extends LotteryWebBaseActon{
	private List<PrizeUserDomain> prizauserList;
	private List<PrizeUserDomain> userList;//最新中奖
	private String type;
	private String indexTerm;
	private String term;
	private List<RaceBean> raceList;
	private LotteryTermModel lotteryterm;
	private List<LotteryTermModel>  currentTermList;
	public List<LotteryTermModel> getCurrentTermList() {
		return currentTermList;
	}

	public void setCurrentTermList(List<LotteryTermModel> currentTermList) {
		this.currentTermList = currentTermList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	public String getTerm() {
		return term;
	}



	public void setTerm(String term) {
		this.term = term;
	}



	public LotteryTermModel getLotteryterm() {
		return lotteryterm;
	}



	public void setLotteryterm(LotteryTermModel lotteryterm) {
		this.lotteryterm = lotteryterm;
	}



	public String index(){
		LotteryManagerInterf lotteryManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
		try {
			//最新中奖
			userList=lotteryManager.getPrizeUser(4);
			//中奖排行
			prizauserList = lotteryManager.getPrizeUserOrder(16);
			Comparator comp = new MyComparator();
			Collections.sort(prizauserList,comp);  
		} catch (LotteryException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//首页投注页面
	public String  rsTouzhu(){
		setDataByType(1300001);
		return "rsTouzhu";
	}
	public String  rjTouzhu(){
		setDataByType(1300002);
		return "rjTouzhu";
	}	
	
	public String  zc6Touzhu(){
		setDataByType(1300003);
		return "zc6Touzhu";
	}
	public String  jq4Touzhu(){
		setDataByType(1300004);
		return "jq4Touzhu";
	}
	public void setDataByType(int type){
		System.out.println("====================");
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		try {
			currentTermList = termservice.queryTermCurrentInfo(type,2);
			if(null!=currentTermList && currentTermList.size()>0){
				if(!StringUtils.isEmpty(term)){
					for(LotteryTermModel term : currentTermList){
						if(term.getTermNo().equals(this.term))
							lotteryterm = term;
					}
				}else
					lotteryterm = currentTermList.get(0);
					raceList = RaceBean.getRaceBeanList(lotteryterm);
					indexTerm= currentTermList.get(0).getTermNo();
			}
			
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<PrizeUserDomain> getPrizauserList() {
		return prizauserList;
	}



	public List<RaceBean> getRaceList() {
		return raceList;
	}



	public void setRaceList(List<RaceBean> raceList) {
		this.raceList = raceList;
	}

	public List<PrizeUserDomain> getUserList() {
		return userList;
	}

	public void setUserList(List<PrizeUserDomain> userList) {
		this.userList = userList;
	}

	public String getIndexTerm() {
		return indexTerm;
	}

	public void setIndexTerm(String indexTerm) {
		this.indexTerm = indexTerm;
	}
	
}
