/**
 * Title: ReportStaticDefine.java
 * @Package com.success.lottery.report.service
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-10 上午10:05:16
 * @version V1.0
 */
package com.success.lottery.report.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * com.success.lottery.report.service
 * ReportStaticDefine.java
 * ReportStaticDefine
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-10 上午10:05:16
 * 
 */

public class ReportStaticDefine {
	
	public static final String report_prize_super = "report_prize_super";
	public static final String report_prize_arrange3 = "report_prize_arrange3";
	public static final String report_prize_ball = "report_prize_ball";
	public static final String report_prize_arrange5 = "report_prize_arrange5";
	public static final String report_prize_seven = "report_prize_seven";
	public static final String report_prize_dlc = "report_prize_dlc";
	public static final String report_prize_ball6 = "report_prize_ball6";
	public static final String report_prize_ball4 = "report_prize_ball4";
	
	public static final String bet_Sort = "1001";
	public static final String prize_Sort = "1002";
	public static final String chongZhi_Sort = "1003";
	public static final String draw_Sort = "1004";
	
	public static Map<Integer,String> lotteryToReportTable;//彩种对应的中奖统计报表表名称
	
	public static Map<String,String> reportTransSortType;//报表交易排序类型定义
	
	public static Map<String,String> prizeReportType;//定义彩种对应的中奖统计报表类型
	
	public static Map<String,String> reportLotteryName;//定义报表的彩种中文名称
	
	static {
		
		lotteryToReportTable = new HashMap<Integer,String>();
		lotteryToReportTable.put(1000001, report_prize_super);//大乐透
		lotteryToReportTable.put(1000002, report_prize_seven);//七星彩
		lotteryToReportTable.put(1000003, report_prize_arrange3);//排列三
		lotteryToReportTable.put(1000004, report_prize_arrange5);//排列五
		lotteryToReportTable.put(1000005, report_prize_super);//生肖乐
		lotteryToReportTable.put(1300001, report_prize_ball);//胜负彩
		lotteryToReportTable.put(1300002, report_prize_ball);//任选九场
		lotteryToReportTable.put(1300003, report_prize_ball6);//6场半全场
		lotteryToReportTable.put(1300004, report_prize_ball4);//4场进球彩
		lotteryToReportTable.put(1200001, report_prize_dlc);//江西多乐彩
		
		reportTransSortType = new LinkedHashMap<String, String>();
		reportTransSortType.put("1001", "投注排名");
		reportTransSortType.put("1002", "中奖排名");
		reportTransSortType.put("1003", "充值排名");
		reportTransSortType.put("1004", "提现排名");
		
		prizeReportType = new HashMap<String, String>();
		prizeReportType.put("1000001", "1000001");//大乐透、生肖乐
		prizeReportType.put("1000002", "1000002");//七星彩
		prizeReportType.put("1000003", "1000003");//排列三
		prizeReportType.put("1000004", "1000004");//排列五
		prizeReportType.put("1000005", "1000001");//大乐透、生肖乐
		prizeReportType.put("1300001", "1300001");//胜负彩、任选九场
		prizeReportType.put("1300002", "1300001");//胜负彩、任选九场
		prizeReportType.put("1300003", "1300003");//6场半全场
		prizeReportType.put("1300004", "1300004");//4场进球彩
		prizeReportType.put("1200001", "1200001");//江西多乐彩
		
		reportLotteryName = new HashMap<String, String>();
		reportLotteryName.put("1000001", "大乐透");
		reportLotteryName.put("1000002", "七星彩");
		reportLotteryName.put("1000003", "排列3");
		reportLotteryName.put("1000004", "排列5");
		reportLotteryName.put("1000005", "大乐透");
		reportLotteryName.put("1300001", "足彩");
		reportLotteryName.put("1300002", "足彩");
		reportLotteryName.put("1300003", "6场半全场");
		reportLotteryName.put("1300004", "4场进球彩");
		reportLotteryName.put("1200001", "多乐彩");
	}

}
