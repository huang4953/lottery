package com.success.lottery.ticket.service;

import java.util.Calendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.util.LotteryTools;


public class DYJSplitTools{

	//��Ӯ�ҳ�Ʊʱ�ϲ��ļ��Ĺ���-�����������ɵ����ļ�����ӦĿǰ���ϡ���ֵ
	public final static int ONEORDERFILE = 1;
	//��Ӯ�ҳ�Ʊʱ�ϲ��ļ��Ĺ���-��ʽ�͸�ʽ�ϲ������ļ�����ӦĿǰ�ĵ�ʽ��ʽ
	public final static int SINGLEDUPLEXFILE = 2;
	//��Ӯ�ҳ�Ʊʱ�ϲ��ļ��Ĺ���-δ�������
	public final static int UNDEFINE = -1;

	public final static String CONFIGFILE = "com.success.lottery.ticket.service.TicketRouter";

	public static Log logger = LogFactory.getLog(DYJSplitTools.class.getName());

	/**
	 * ����ļ���
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
	 * ��ĳһ���ֵĵ�ʽ��ʽͶע����ת��Ϊ��Ӯ��Ҫ����ļ���¼��ʽ
	 * ����͸ǰ���ͺ����üӺ�(��+)������
	 * ����͸���ֶ���2λ��ʾ����λ����߲�0
	 * ��ʽ����֮���ð�Ƕ��Ż�ո����
	 * ��ʽ������޹ص�ȫ���ö��Ż�ո�������͵�ʽ�ָ��һ�£�
	 * ��ʽ������йصģ�����3��5��7��14ʤ��������9����ĳ��λ���ϰѿ��ܵĺ���������д�����÷ָ 
	 * �����9������#��ʾδѡ��ó��� 
	 * @param lotteryId
	 * 		����id
	 * @param betCode
	 * 		ע�룬ֻ���ǵ�ʽ��ʽ
	 * @return
	 * 		����ת����ĸ�ʽ�������� null
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
