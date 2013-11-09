package com.success.lottery.web.trade.action;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;
import com.success.utils.ApplicationContextUtils;

/**    
 * @{#} LotteryDirectionAction.java Create on Apr 21, 2010 3:40:04 PM    
 *    
 * Copyright (c) 2010 by success.    

 * @author Gavin

 * @version 1.0 

 * @description  ×ßÊÆ·ÖÎöaction
   

 */ 
@SuppressWarnings("serial")
public class LotteryDirectionAction extends LotteryWebBaseActon{
	private String type;
	private int num=30;
	private List<LotteryTermModel> termList;
	private Map<String,Integer> yiLouMap;
	public String dlt(){
		queryHostoryTerm(1000001);
		return SUCCESS;
	}
	public String pls(){
		queryHostoryTerm(1000003);
		return SUCCESS;
	}
	public String plw(){
		queryHostoryTerm(1000004);
		return SUCCESS;
	}
	public String qxc(){
		queryHostoryTerm(1000002);
		return SUCCESS;
	}
	
	public String dlc(){
		queryHostoryTerm(1200001);
		return SUCCESS;
	}
	public String dlcz2(){
		queryHostoryTerm(1200001);
		return SUCCESS;
	}
	public String dlcz3(){
		queryHostoryTerm(1200001);
		return SUCCESS;
	}
	
	public String dlczu2(){
		queryHostoryTerm(1200001);
		return SUCCESS;
	}
	public String dlczu3(){
		queryHostoryTerm(1200001);
		return SUCCESS;
	}
	public void queryHostoryTerm(int lottype){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		if(lottype==1200001)
		{
			if(this.getNum()==0||this.getNum()==30)
				this.setNum(65);
		}else
		{
			if(this.getNum()==0)
				this.setNum(30);
			
		}
		try {
			termList = termservice.queryHaveWinTermInfo(lottype, num);
			//yiLouMap = termList.get(0).getLotteryInfo().getMissCountResult();
			Collections.reverse(termList);
		} catch (LotteryException e) {
			termList=null;
		}
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getNum() {
		return num;
	}
	public List<LotteryTermModel> getTermList() {
		return termList;
	}
	public Map<String, Integer> getYiLouMap() {
		return yiLouMap;
	}
}
