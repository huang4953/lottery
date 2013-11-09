package com.success.lottery.web.trade.action;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.web.formbean.RaceBean;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;
import com.success.utils.ApplicationContextUtils;

/**    
 * @{#} DrawLotteryAction.java Create on Apr 21, 2010 3:10:14 PM    
 *    
 * Copyright (c) 2010 by success.    

 * @author Gavin

 * @version 1.0 

 * @description  开奖结果Action
   

 */ 
@SuppressWarnings("serial")
public class DrawLotteryAction extends LotteryWebBaseActon{
	private String type;
	private int num=30;
	private String termNo;
	private LotteryTermModel termModel;
	private LotteryTermModel othertermModel;
	private List<LotteryTermModel> termList;
	private List<String> numlist;
	private Map<Integer,LotteryTermModel> termMap;
	private List<RaceBean> raceList;
	public String index(){
		/**
		 * 参数： type菜种编号 num查询数量 
		 * 	返回： 根据菜种和NUM查询该菜种最近 NUM期的 期号  开奖号码 开奖日期 
		 * ***/	
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		try {
			termList = termservice.queryAllLastTermInfo();
			termMap = new HashMap<Integer, LotteryTermModel>();
			for(LotteryTermModel term : termList)
				termMap.put(term.getLotteryId(), term);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String list(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		if(StringUtils.isEmpty(type))
			type="1000001";
		try {
			termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type), num);
			//获得进两百期彩期集合
			numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
			//获得日期列表
			if(type.equals("1200001"))
			{
				for (int i=0;i<numlist.size();i++ ) {
					   numlist.set(i, numlist.get(i).substring(0,6));
				}
				Set<String> set=new HashSet<String>(numlist);
				numlist.clear();
				for (String string : set) {
					numlist.add(string);
				}
			}
		}  catch (LotteryException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String syxw(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		if(StringUtils.isEmpty(type))
			type="1200001";
		    Calendar today=Calendar.getInstance();
		    SimpleDateFormat df=new SimpleDateFormat("yyMMdd");
			termList = termservice.queryHaveWinTermInfoToDate(df.format(today.getTime()));
			for (LotteryTermModel  m : termList) {
				 System.out.println(m.getLotteryResult());
			}
			this.getRequest().getSession().setAttribute("termListModel", termList);
		
		return SUCCESS;
	}
	public String detail(){
		/**queryTermInfo  queryTermLastCashInfo
		 * 参数： type菜种编号 
		 * 	返回： 查询该菜种最近一期的详细开奖
		 * ***/	
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		if(StringUtils.isEmpty(type))
			type="1000001";
		if(type.equals("1300002"))
			type="1300001";
		try {
			if(StringUtils.isEmpty(termNo))
				termModel = termservice.queryTermLastCashInfo(Integer.parseInt(type));
			else
				termModel = termservice.queryTermInfo(Integer.parseInt(type), termNo);
			if(null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermLastCashInfo(Integer.parseInt(type));
				if(type.equals("1000001"))
					othertermModel = termservice.queryTermInfo(Integer.parseInt("1000005"), termModel.getTermNo());
				if(type.equals("1300001")){
					raceList = RaceBean.getRaceBeanList(termModel);
					othertermModel = termservice.queryTermInfo(Integer.parseInt("1300002"), termModel.getTermNo());
					
				}
		}  catch (LotteryException e) {}
		//获得进两百期彩期集合
		try {
			numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String dltdrawdetail(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
			type="1000001";
		try {
			if(StringUtils.isEmpty(termNo))
				termModel = termservice.queryTermLastCashInfo(Integer.parseInt(type));
			else
				termModel = termservice.queryTermInfo(Integer.parseInt(type), termNo);
			if(null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermInfo(Integer.parseInt(type), "10031");

			othertermModel = termservice.queryTermInfo(Integer.parseInt("1000005"), termModel.getTermNo());
		}  catch (LotteryException e) {}
		//获得进两百期彩期集合
		try {
			numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String dlcdrawdetail(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		if(StringUtils.isEmpty(type))
			type="1200001";
		    Calendar today=Calendar.getInstance();
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		    if(termNo==null||"".equals(termNo.trim()))
		    	termNo=sf.format(today.getTime());
		    if(termNo.length()!=10)
		    	termNo=sf.format(today.getTime());
			termList = termservice.queryHaveWinTermInfoToDate((termNo.substring(2,4)+termNo.substring(5,7)+termNo.substring(8,10)));
			if(null==termList){
				termNo=sf.format(today.getTime());
				termList = termservice.queryHaveWinTermInfoToDate((termNo.substring(2,4)+termNo.substring(5,7)+termNo.substring(8,10)));
			}
			ArrayList<String> arr =new ArrayList<String>();
			for(int i=1;i<=200;i++)
			{
				arr.add(sf.format(today.getTime()));
				today.add(Calendar.DAY_OF_MONTH, -1);
			}
			this.setNumlist(arr);
			this.getRequest().getSession().setAttribute("termListModel", termList);
		return SUCCESS;
	}
	public String sfcdrawdetail(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		if(StringUtils.isEmpty(type))
			type="1300001";
		try {
			if(StringUtils.isEmpty(termNo))
				termModel = termservice.queryTermLastCashInfo(Integer.parseInt(type));
			else
				termModel = termservice.queryTermInfo(Integer.parseInt(type), termNo);
			if(null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermInfo(Integer.parseInt(type), "10031");
			raceList = RaceBean.getRaceBeanList(termModel);
			othertermModel = termservice.queryTermInfo(Integer.parseInt("1300002"), termModel.getTermNo());
		}  catch (LotteryException e) {}
		//获得进两百期彩期集合
		try {
			numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	public String jq4drawdetail(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		if(StringUtils.isEmpty(type))
			type="1300004";
		try {
			if(StringUtils.isEmpty(termNo))
				termModel = termservice.queryTermLastCashInfo(Integer.parseInt(type));
			else
				termModel = termservice.queryTermInfo(Integer.parseInt(type), termNo);
			if(null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermInfo(Integer.parseInt(type), "10031");
			raceList = RaceBean.getRaceBeanList(termModel);
		}  catch (LotteryException e) {}
		//获得进两百期彩期集合
		try {
			numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("===============================");
		return SUCCESS;
	}
	
	public String zc6drawdetail(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		type="1300003";
		System.out.println("11111");
		try {
			if(StringUtils.isEmpty(termNo))
				termModel = termservice.queryTermLastCashInfo(Integer.parseInt(type));
			else
				termModel = termservice.queryTermInfo(Integer.parseInt(type), termNo);
			if(null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermInfo(Integer.parseInt(type), "10031");
			raceList = RaceBean.getRaceBeanList(termModel);
			System.out.println(raceList.size());
		}  catch (LotteryException e) {}
		//获得进两百期彩期集合
		try {
			numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	public String qxcdrawdetail(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		type="1000002";
		System.out.println(type);
		try {
			if(StringUtils.isEmpty(termNo))
				termModel = termservice.queryTermLastCashInfo(Integer.parseInt(type));
			else
				termModel = termservice.queryTermInfo(Integer.parseInt(type), termNo);
			if(null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermInfo(Integer.parseInt(type), "10031");
			System.out.println("ssssssssssssssssssssssss");
			System.out.println(termModel.getLotteryInfo().getSevenColorWinResult().get("1")[0]);
		}  catch (LotteryException e) {}
		//获得进两百期彩期集合
		try {
			numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String plsdrawdetail(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		type="1000003";
		System.out.println(type);
		try {
			if(StringUtils.isEmpty(termNo))
				termModel = termservice.queryTermLastCashInfo(Integer.parseInt(type));
			else
				termModel = termservice.queryTermInfo(Integer.parseInt(type), termNo);
			if(null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermInfo(Integer.parseInt(type), "10031");
		}  catch (LotteryException e) {}
		//获得进两百期彩期集合
		try {
			numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String plwdrawdetail(){
		LotteryTermServiceInterf termservice = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		type="1000004";
		System.out.println(type);
		try {
			if(StringUtils.isEmpty(termNo))
				termModel = termservice.queryTermLastCashInfo(Integer.parseInt(type));
			else
				termModel = termservice.queryTermInfo(Integer.parseInt(type), termNo);
			if(null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermInfo(Integer.parseInt(type), "10031");
		}  catch (LotteryException e) {}
		//获得进两百期彩期集合
		try {
			numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
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
	public LotteryTermModel getTermModel() {
		return termModel;
	}
	public List<LotteryTermModel> getTermList() {
		return termList;
	}
	public Map<Integer, LotteryTermModel> getTermMap() {
		return termMap;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public LotteryTermModel getOthertermModel() {
		return othertermModel;
	}
	public List<RaceBean> getRaceList() {
		return raceList;
	}
	public List<String> getNumlist() {
		return numlist;
	}
	public void setNumlist(List<String> numlist) {
		this.numlist = numlist;
	}
	public String getTermNo() {
		return termNo;
	}
}
