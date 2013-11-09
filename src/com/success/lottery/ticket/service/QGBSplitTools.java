package com.success.lottery.ticket.service;

import java.util.Calendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.util.LotteryTools;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;


public class QGBSplitTools{

	public final static String CONFIGFILE = "com.success.lottery.ticket.service.TicketRouter";
	public static Log logger = LogFactory.getLog(QGBSplitTools.class.getName());

	public static int getDLTQMForDantuo(String betCode){
		return getDLTQHMNForDantuo(betCode, 1);
	}

	public static int getDLTQNForDantuo(String betCode){
		return getDLTQHMNForDantuo(betCode, 2);
	}

	public static int getDLTHMForDantuo(String betCode){
		return getDLTQHMNForDantuo(betCode, 3);
	}

	public static int getDLTHNForDantuo(String betCode){
		return getDLTQHMNForDantuo(betCode, 4);
	}

	public static List<String> getRepeatedHezhiCode(String[] betCodes, String[] cutRepeatedCodes){
		String[] copiedCodes = new String[betCodes.length];
		System.arraycopy(betCodes, 0, copiedCodes, 0, betCodes.length);
		List<String> repeatedCodes = new ArrayList<String>();
		for(int i = 0; i < betCodes.length; i++){
			for(int j = i + 1; j < copiedCodes.length; j++){
				if(betCodes[i].equals(copiedCodes[j])){
					copiedCodes[j] = "N";
					repeatedCodes.add(betCodes[i]);
					break;
				}
			}
		}
		int i = 0;
		for(String s : copiedCodes){
			if(!"N".equals(s)){
				cutRepeatedCodes[i++] = s;
			}
		}
		return repeatedCodes;
	}

	public static String getSingleBetCodeInDLTDantuo(String betCode){
		int qm = 0, qn = 0, hm = 0, hn = 0;
		String qs = betCode.split("\\|")[0];
		String hs = betCode.split("\\|")[1];
		String qds = qs.split("\\*")[0];
		String qts = qs.split("\\*")[1];
		String hds = hs.split("\\*")[0];
		String hts = hs.split("\\*")[1];

		if(StringUtils.isBlank(qds)){
			qm = 0;
		}else{
			qm = qds.length() / 2;
		}
		if(StringUtils.isBlank(qts)){
			qn = 0;
		}else{
			qn = qts.length() / 2;
		}
		if(StringUtils.isBlank(hds)){
			hm = 0;
		}else{
			hm = hds.length() / 2;
		}
		if(StringUtils.isBlank(hts)){
			hn = 0;
		}else{
			hn = hts.length() / 2;
		}
		if(qm + qn == 5 && hm + hn == 2){
			return qds + qts + "|" + hds + hts;
		}else{
			return betCode;
		}
	}

	/**
	 * 
	 * @param betCode
	 * @param type
	 *		1 - qm 前区胆码个数;
	 *		2 - qn 前区拖码个数;
	 *		3 - hm 后区胆码个数;
	 *		4 - hn 后区拖码个数;
	 * @return
	 */
	private static int getDLTQHMNForDantuo(String betCode, int type){
		int qm = 0, qn = 0, hm = 0, hn = 0;
		String qs = betCode.split("\\|")[0];
		String hs = betCode.split("\\|")[1];
		String qds = qs.split("\\*")[0];
		String qts = qs.split("\\*")[1];
		String hds = hs.split("\\*")[0];
		String hts = hs.split("\\*")[1];

		if(StringUtils.isBlank(qds)){
			qm = 0;
		}else{
			qm = qds.length() / 2;
		}
		if(StringUtils.isBlank(qts)){
			qn = 0;
		}else{
			qn = qts.length() / 2;
		}
		if(StringUtils.isBlank(hds)){
			hm = 0;
		}else{
			hm = hds.length() / 2;
		}
		if(StringUtils.isBlank(hts)){
			hn = 0;
		}else{
			hn = hts.length() / 2;
		}

		switch(type){
			case 1:
				return qm;
			case 2:
				return qn;
			case 3:
				return hm;
			case 4:
				return hn;
			default:
				return 0;
		}
	}

	public static void main(String[] args){	
		String betCode = "010203*03040506|01*0203";
		betCode = "01020304*070809|*021112";
		betCode = "01*07080910|*0211";
		betCode = "0107*080910|02*11";
		betCode = "0107*080910|0102*11";

		getDLTQMForDantuo(betCode);
		System.out.println(getSingleBetCodeInDLTDantuo(betCode));

		betCode = "1^2^3^3^4^5^6^6^2^7^8^3";
		betCode = "1";
		betCode = "1^2^3^4";
		betCode = "1^2^3^4^1^1";
		String[] betCodes = betCode.split("\\^");
		String[] resultCodes = new String[betCodes.length];
		System.out.println(getRepeatedHezhiCode(betCodes, resultCodes));
		for(String s : resultCodes){
			System.out.print(s + ", ");
		}
	}
}
