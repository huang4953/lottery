package com.success.lottery.ticket.service;

import java.util.Calendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.util.LotteryTools;


public class DYJSplitTools{

	//大赢家出票时合并文件的规则-单个订单生成单个文件，对应目前胆拖、和值
	public final static int ONEORDERFILE = 1;
	//大赢家出票时合并文件的规则-单式和复式合并生成文件，对应目前的单式复式
	public final static int SINGLEDUPLEXFILE = 2;
	//大赢家出票时合并文件的规则-未定义规则
	public final static int UNDEFINE = -1;

	public final static String CONFIGFILE = "com.success.lottery.ticket.service.TicketRouter";

	public static Log logger = LogFactory.getLog(DYJSplitTools.class.getName());

	/**
	 * 获得文件名
	 * @param lotteryId
	 * @param term
	 * @param playType
	 * @param betType
	 * @param betMulti
	 * @param amount
	 * @param extensionIdentify
	 * @return
	 */
	public static String getTicketFileName(int lotteryId, String term, int playType, int betType, int betMulti, long amount, String extensionIdentify){
		String lotteryName = LotteryTools.getLotteryName(lotteryId);
		String playBetName = LotteryTools.getPlayBetName(lotteryId, playType, betType);
		return lotteryName + "_" + term + "_" + playBetName + "_" + betMulti + "_" + amount + "_" + extensionIdentify + "_" + String.format("%1$tY%1$tm%1$td%1$TH%1$TM%1$TS", Calendar.getInstance()) + ".txt";
	}
	
	

	
	
	
	/**
	 * 对某一彩种的单式或复式投注内容转换为大赢家要求的文件记录格式
	 * 大乐透前区和后区用加号(即+)隔开；
	 * 大乐透数字都用2位表示，单位的左边补0
	 * 单式号码之间用半角逗号或空格隔开
	 * 复式和序号无关的全部用逗号或空格隔开，和单式分割方法一致；
	 * 复式和序号有关的（即排3，5，7，14胜负场，任9）在某个位置上把可能的号码连续书写，不用分割； 
	 * 足彩任9场中用#表示未选择该场次 
	 * @param lotteryId
	 * 		彩种id
	 * @param betCode
	 * 		注码，只能是单式或复式
	 * @return
	 * 		返回转换后的格式，出错返回 null
	 */
	public static String convertBetCode(int lotteryId, String betCode){
		String result = null;
		try{
			switch(lotteryId){
				case 1000001:
					result = "";
					int i = 0;
					for(char c : betCode.toCharArray()){
						if(c == '|'){
							result = result.substring(0, result.length() - 1) + "+";
						}else{
							if(i == 0){
								result += c;
								i++;
							} else {
								result += c + ",";
								i = 0;
							}
						}
					}
					result = result.substring(0, result.length() - 1);
					break;
				case 1000002:
				case 1000003:
				case 1000004:
					result = betCode.replace("*", ",");
					break;
				case 1000005:
					result = "";
					i = 0;
					for(char c : betCode.toCharArray()){
						if(i == 0){
							result += c;
							i++;
						} else {
							result += c + ",";
							i = 0;
						}
					}
					result = result.substring(0, result.length() - 1);
					break;
				case 1300001:
				case 1300002:
					result = betCode.replace("*", ",").replace("4", "#");
					break;
				default:
					return null;
			}
		}catch(Exception e){
			logger.error("convertBetCode(" + betCode + ") exception :" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return null;
		}
		return result;
	}
	
	
	public static String convertBetCode(int lotteryId, int playType, int betType, String betCode){
		String result = null;
		try{
			switch(lotteryId){
				case 1000001:
					result = "";
					int i = 0;
					for(char c : betCode.toCharArray()){
						if(c == '|'){
							result = result.substring(0, result.length() - 1) + "+";
						}else{
							if(i == 0){
								result += c;
								i++;
							} else {
								result += c + ",";
								i = 0;
							}
						}
					}
					result = result.substring(0, result.length() - 1);
					break;
				case 1000002:
					if(betType == 0){
						result = betCode.replace("*", "");
					}else{
						result = betCode.replace("*", ",");						
					}
					break;
				case 1000003:
					result = betCode.replace("*", ",");
					break;
				case 1000004:
					if(betType == 0){
						result = betCode.replace("*", "");
					}else{
						result = betCode.replace("*", ",");						
					}
					break;
				case 1000005:
					result = "";
					i = 0;
					for(char c : betCode.toCharArray()){
						if(i == 0){
							result += c;
							i++;
						} else {
							result += c + ",";
							i = 0;
						}
					}
					result = result.substring(0, result.length() - 1);
					break;
				case 1300001:
				case 1300002:
					result = betCode.replace("*", ",").replace("4", "#");
					break;
				default:
					return null;
			}
		}catch(Exception e){
			logger.error("convertBetCode(" + betCode + ") exception :" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return null;
		}
		return result;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args){	
		int lotteryId = 1000001;
		String betCode = "0102030405|0607";
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, betCode));
		betCode = "010203040523|060711";
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, betCode));
		lotteryId = 1000005;
		betCode = "0102";
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, betCode));
		betCode = "01020304";
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, betCode));
		lotteryId = 1000002;
		betCode = "0*1*2*3*4*5*6";
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, betCode));
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, 0, 0, betCode));
		betCode = "1*23*34*34*45*23*45";
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, betCode));
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, 0, 1, betCode));
		
		lotteryId = 1000003;
		betCode = "1*2*3";
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, betCode));
		betCode = "1*23*34";
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, betCode));
		
		lotteryId = 1000004;
		betCode = "0*1*2*3*4";
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, betCode));
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, 0, 0, betCode));
		betCode = "1*23*34*34*435";
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, betCode));
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, 0, 1, betCode));

		lotteryId = 1300001;
		betCode = "3*1*1*3*3*1*0*3*3*3*3*0*0*1";
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, betCode));
		betCode = "3*1*130*3*30*1*0*3*3*3*3*0";
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, betCode));

		lotteryId = 1300001;
		betCode = "3*4*1*3*4*1*0*4*3*4*3*4*1*3";
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, betCode));
		betCode = "3*4*1*30*4*1*0*4*3*4*3*4*1*30";
		System.out.println(DYJSplitTools.convertBetCode(lotteryId, betCode));

		System.out.println(DYJSplitTools.getTicketFileName(1000001, "10067", 1, 2, 50, 103424L, "3"));

		System.out.println(String.format("%1$tY-%1$tm-%1$td %1$TH:%1$TM:%1$TS", Calendar.getInstance()));
	}
}
