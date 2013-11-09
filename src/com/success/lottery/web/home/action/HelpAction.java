package com.success.lottery.web.home.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;
import com.success.utils.ApplicationContextUtils;
import com.sun.org.apache.bcel.internal.generic.SIPUSH;

/**
 * 辅助功能aciton
 * @author aaron
 *
 */
public class HelpAction extends LotteryWebBaseActon {
   
	private List<LotteryTermModel> termList;
	private Map<Integer,LotteryTermModel> termMap;
	
	public Map<Integer, LotteryTermModel> getTermMap() {
		return termMap;
	}


	public void setTermMap(Map<Integer, LotteryTermModel> termMap) {
		this.termMap = termMap;
	}


	//最新开奖公告
	public String announcement(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
			try {
				termList = termservice.queryAllLastTermInfo();
				termMap = new HashMap<Integer, LotteryTermModel>();
				for (LotteryTermModel term : termList) 
				{
					termMap.put(term.getLotteryId(), term);
				}
				
			} catch (LotteryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return "announcement";
	}
    public String ratherforecast(){
    	LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
    	SimpleDateFormat dateFamte=new SimpleDateFormat("MM-dd HH:mm");
    	LotteryTermModel term=new LotteryTermModel();
    	termMap = new HashMap<Integer, LotteryTermModel>();
		try {
				
				for(int i=1000001;i<=1000005;i++)
				{
					if(i==1000005)
						i=1200001;
					term=termservice.queryTermCurrentInfo(i);
					if(term!=null)
						term.setReserve1(dateFamte.format(term.getWinLine2()));//借用备注字段
					termMap.put(i, term);
					
				}
				
	      } catch (LotteryException e) {
				e.printStackTrace();
			}
    	return "ratherforecast";
    }
    public String trends(){
    	return "trends";
    }

	public List<LotteryTermModel> getTermList() {
		return termList;
	}


	public void setTermList(List<LotteryTermModel> termList) {
		this.termList = termList;
	}

}
