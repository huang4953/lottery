/**
 * Title: Arrange5Lottery.java
 * @Package com.success.lottery.util.core
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-3-27 ����11:55:25
 * @version V1.0
 */
package com.success.lottery.util.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.success.lottery.util.LotteryInfo;
/**
 * 
 * com.success.lottery.util.core
 * Arrange5Lottery.java
 * Arrange5Lottery
 * ����5������
 * @author gaoboqin
 * 2010-3-27 ����11:55:25
 *
 */
public class Arrange5Lottery extends LotteryAbstractTool implements
		LotteryInterf {

	private static Map<String,String> winRules;
	
	static {
		winRules = new HashMap<String,String>();
		winRules.put("5", "1");//ֻ��һ������
	}
	/* (�� Javadoc)
	 *Title: checkBetType
	 *Description: 
	 * @param playType
	 * @param betType
	 * @param betString
	 * @return
	 * @throws LotteryUnDefineException
	 * @throws Exception
	 * @see com.success.lottery.util.core.LotteryInterf#checkBetType(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean checkBetType(String playType, String betType,
			String betString) throws LotteryUnDefineException, Exception {
		if (ARRANGE_5_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			return checkBet(betType, betString);
		}else {
			throw new LotteryUnDefineException("δ�ҵ��淨��"+playType);
		}
	}
	/*
	 * (�� Javadoc)
	*Title: mergeWinResult
	*Description: 
	* @param info
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#mergeWinResult(com.success.lottery.util.LotteryInfo)
	 */
	public String mergeWinResult(LotteryInfo info) {
		String [] arrangeFiveWinResult = info.getArrangeFiveWinResult();
		StringBuffer result = new StringBuffer();
		if(arrangeFiveWinResult.length == 2){
			result.append(arrangeFiveWinResult[0]);
			result.append("&");
			result.append(arrangeFiveWinResult[1]);
		}
		return result.toString();
	}
	/*
	 * (�� Javadoc)
	*Title: splitWinResult
	*Description: 
	* @param winResult
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#splitWinResult(java.lang.String)
	 */
	public LotteryInfo splitWinResult(String winResult,LotteryInfo info) {
		String [] arrangeFiveWinResult = winResult.split("&");
		info.setArrangeFiveWinResult(arrangeFiveWinResult);
		return info;
	}
	/*
	 * (�� Javadoc)
	 *Title: mergeLotteryResult
	 *Description: 
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#mergeLotteryResult(com.success.lottery.util.LotteryInfo)
	 */
	public String mergeLotteryResult(LotteryInfo info) {
		String result = mergeListToStr(info.getArrangeFiveLotteryResult());
		return result;
	}
	/*
	 * (�� Javadoc)
	 *Title: splitLotteryResult
	 *Description: 
	 * @param lotteryResult
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#splitLotteryResult(java.lang.String)
	 */
	public LotteryInfo splitLotteryResult(String lotteryResult,LotteryInfo info) {
		ArrayList<String> arrangeFiveLotteryResult = (ArrayList<String>)splitStrByLen(lotteryResult,1);
		info.setArrangeFiveLotteryResult(arrangeFiveLotteryResult);
		return info;
	}
	/*
	 * (�� Javadoc)
	*Title: lotterySplit
	*Description: 
	* @param playType
	* @param betType
	* @param betCode
	* @return
	* @throws LotteryUnDefineException
	* @throws Exception
	* @see com.success.lottery.util.core.LotteryInterf#lotterySplit(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String lotterySplit(String playType, String betType, String betCode) throws LotteryUnDefineException, Exception {
		long zhuNum = lotteryToSingle(playType,betType,betCode).size();
		return String.valueOf(zhuNum) + ZHUSIGN + String.valueOf(zhuNum*SINGLEPRIZE);
	}
	/*
	 * (�� Javadoc)
	*Title: lotteryToSingle
	*Description: 
	* @param playType
	* @param betType
	* @param betCode
	* @return
	* @throws LotteryUnDefineException
	* @throws Exception
	* @see com.success.lottery.util.core.LotteryInterf#lotteryToSingle(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<String> lotteryToSingle(String playType, String betType, String betCode) throws LotteryUnDefineException, Exception {
		List<String> toSingle;
		if (ARRANGE_5_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			List<String> singleBet = Arrays.asList(splitGroup(betCode,ARRANGE_5_BET_GROUP_SEPARATOR, false));
			if (ARRANGE_5_BETTYPE_SINGLE.equals(betType)) {
				toSingle = singleBet;
			} else if (ARRANGE_5_BETTYPE_DUPLEX.equals(betType)) {
				toSingle = combineDuplex(betCode,ARRANGE_5_BET_GROUP_SEPARATOR,"\\*",1);
			} else {
				throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��" + betType);
			}
		} else {
			throw new LotteryUnDefineException("δ�ҵ��淨��" + playType);
		}
		return toSingle;
	}
	/*
	 * (�� Javadoc)
	*Title: lotteryPrize
	*Description: 
	* @param playType
	* @param betType
	* @param lotteryResult
	* @param winReslut
	* @param betCode
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#lotteryPrize(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<List<String>> lotteryPrize(String playType, String betType,
			String lotteryResult, String winResult, String betCode)
			throws LotteryUnDefineException, Exception {
		if (ARRANGE_5_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			return arrange5Prize(playType,betType,lotteryResult,winResult,betCode);
		} else {
			throw new LotteryUnDefineException("δ�ҵ��淨��" + playType);
		}
	}
	/**
	 * 
	 * Title: sevenColorPrize
	 * Description:  ������5����Ͷע��ʽ����
	 * @param playType
	 * @param betType
	 * @param lotteryResult
	 * @param winResult
	 * @param betCode
	 * @throws LotteryUnDefineException
	 * @throws Exception    �趨�ļ�
	 * @return List<List<String>>    ��������
	 * @throws
	 */
	private List<List<String>> arrange5Prize(String playType,String betType, String lotteryResult,
			String winResult, String betCode) throws LotteryUnDefineException,
			Exception {
		List<String> singleBet = Arrays.asList(splitGroup(betCode,ARRANGE_5_BET_GROUP_SEPARATOR, false));
		ArrayList<String> lotteryResultMap = splitLotteryResult(lotteryResult,new LotteryInfo()).getArrangeFiveLotteryResult();//����������
		String[] winResultMap = splitWinResult(winResult,new LotteryInfo()).getArrangeFiveWinResult();//������
		if (ARRANGE_5_BETTYPE_SINGLE.equals(betType)) {
			return singlePrize(lotteryResultMap, winResultMap, singleBet);
		} else if (ARRANGE_5_BETTYPE_DUPLEX.equals(betType)) {
			return duplexPrize(lotteryResultMap,winResultMap,betCode);
		} else {
			throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��" + betType);
		}
	}
	/**
	 * 
	 * Title: duplexPrize
	 * Description:  ��ʽ�ҽ����Ȳ�Ϊ��ʽ���ٰ��յ�ʽ�ҽ� ��������˳��Ҫ���
	 * @param lotteryResult
	 * @param winResult
	 * @param betCode
	 * @return List<List<String>>    ��������
	 * @throws
	 */
	private List<List<String>> duplexPrize(ArrayList<String> lotteryResult, String[] winResult,String betCode){
		List<String> duplexToSingle = combineDuplex(betCode,ARRANGE_5_BET_GROUP_SEPARATOR,"\\*",1);
		return singlePrize(lotteryResult,winResult,duplexToSingle);
	}
	/**
	 * 
	 * Title: singlePrize
	 * Description:  ����5��ʽ�ҽ� ��ֻ��һ�����5λ���밴��˳����ȫƥ��
	 * @param playType
	 * @param lotteryResult ArrayList<String> ������Ϊ<����>��Ӧ�ð���5������
	 * @param winResult String[] �����ֱ�Ϊ:[ע�������]
	 * @param singleBet Ͷע����ļ��� ������ע
	 * @return List<List<String>>    ��������
	 * @throws
	 */
	private List<List<String>> singlePrize(ArrayList<String> lotteryResult, String[] winResult,List<String> singleBet){
		List<List<String>> prizeResult = new ArrayList<List<String>>();
		String lotteryResultBySign = mergeListToStrBySign(lotteryResult,"*");
		for (String single : singleBet) {
			if (lotteryResultBySign.equals(single)) {
				List<String> innerList = new ArrayList<String>();
				innerList.add(single);
				innerList.add(winRules.get("5"));
				innerList.add(winResult[1]);
				innerList.add("0");
				prizeResult.add(innerList);
			}
		}
		return prizeResult;
	}
	
	/**
	 * 
	 * Title: checkBet
	 * Description: У��Ͷע��ʽ
	 * @param betType
	 * @param betString
	 * @throws LotteryUnDefineException
	 * @throws Exception    �趨�ļ�
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkBet(String betType,String betString) throws LotteryUnDefineException,Exception{
		if(ARRANGE_5_BETTYPE_SINGLE.equals(betType)){
			return checkSingle(betString);
		}else if(ARRANGE_5_BETTYPE_DUPLEX.equals(betType)){
			return checkDuplex(betString);
		}else {
			throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��"+betType);
		}
	}
	/**
	 * 
	 * Title: checkSingle
	 * Description: У������5��ʽͶע����ʽ
	 * @param betString
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkSingle(String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,ARRANGE_5_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(ARRANGE_5_BET_0_REGULAR,group)){
				checkResult = false;
				break;
			}
		}
		
		return checkResult;
	}
	
	/**
	 * 
	 * Title: checkDuplex
	 * Description: У������5��ʽͶע�ĸ�ʽ�Ƿ���ȷ
	 * @param betString
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkDuplex(String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,ARRANGE_5_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(ARRANGE_5_BET_1_REGULAR,group)){
				checkResult = false;
				break;
			}else{
				String [] betWei = group.split("\\*");
				for (String wei : betWei){
					if(!checkRepeat(wei,1)){
						checkResult = false;
						break;
					}
				}
				if(!checkResult){
					break;
				}
			}
		}
		return checkResult;
	}
	
	public String lotteryRandomBetCode(){
		String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		String result = "";
		Random random = new Random();
		result = random.nextInt(10) + "*";
		result = result + random.nextInt(10) + "*";
		result = result + random.nextInt(10) + "*";
		result = result + random.nextInt(10) + "*";
		result = result + random.nextInt(10);
		return result;
	}
	public String mergeSalesInfoResult(LotteryInfo info) {
		return null;
	}
	/*
	 * (�� Javadoc)
	*Title: splitSalesInfoResult
	*Description: 
	* @param salesInfo
	* @param info
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#splitSalesInfoResult(java.lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitSalesInfoResult(String salesInfo, LotteryInfo info) {
		return info;
	}
	@Override
	public String getMissCount(String lotteryResult, String lastMissCount){
		if(lotteryResult == null || lotteryResult.length() != 5){
			return null;
		}
		int v10000 = Integer.parseInt(lotteryResult.substring(0, 1));
		int v1000 = Integer.parseInt(lotteryResult.substring(1, 2));
		int v100 = Integer.parseInt(lotteryResult.substring(2, 3));
		int v10 = Integer.parseInt(lotteryResult.substring(3, 4));
		int v1 = Integer.parseInt(lotteryResult.substring(4, 5));
		
		int digit10000[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int digit1000[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int digit100[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int digit10[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int digit1[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
 		if(StringUtils.isBlank(lastMissCount)){
			for(int i = 0; i < 10; i++){
				if(i == v10000){
					digit10000[i] = 0;
				}else{
					digit10000[i]++;
				}
			}
 			for(int i = 0; i < 10; i++){
				if(i == v1000){
					digit1000[i] = 0;
				}else{
					digit1000[i]++;
				}
			}
 			for(int i = 0; i < 10; i++){
				if(i == v100){
					digit100[i] = 0;
				}else{
					digit100[i]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v10){
					digit10[i] = 0;
				}else{
					digit10[i]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v1){
					digit1[i] = 0;
				}else{
					digit1[i]++;
				}
			}
		}else{
			String[] ss = lastMissCount.split(";");
			if(ss == null || ss.length != 5){
				return null;
			}
			for(int i = 0; i < 5; i++){
				if(ss[i].split(",") == null || ss[i].split(",").length != 10){
					return null;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v10000){
					digit10000[i] = 0;
				}else{
					digit10000[i] = Integer.parseInt(ss[0].split(",")[i]);
					digit10000[i]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v1000){
					digit1000[i] = 0;
				}else{
					digit1000[i] = Integer.parseInt(ss[1].split(",")[i]);
					digit1000[i]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v100){
					digit100[i] = 0;
				}else{
					digit100[i] = Integer.parseInt(ss[2].split(",")[i]);
					digit100[i]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v10){
					digit10[i] = 0;
				}else{
					digit10[i] = Integer.parseInt(ss[3].split(",")[i]);
					digit10[i]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v1){
					digit1[i] = 0;
				}else{
					digit1[i] = Integer.parseInt(ss[4].split(",")[i]);
					digit1[i]++;
				}
			}			
		}
 		String result = "";
 		for(int i = 0; i < 10; i++){
 			result = result + digit10000[i] + ",";
 		}
 		result = result.substring(0, result.length() - 1) + ";";
 		for(int i = 0; i < 10; i++){
 			result = result + digit1000[i] + ",";
 		}
 		result = result.substring(0, result.length() - 1) + ";";
 		for(int i = 0; i < 10; i++){
 			result = result + digit100[i] + ",";
 		}
 		result = result.substring(0, result.length() - 1) + ";";
 		for(int i = 0; i < 10; i++){
 			result = result + digit10[i] + ",";
 		}
 		result = result.substring(0, result.length() - 1) + ";";
 		for(int i = 0; i < 10; i++){
 			result = result + digit1[i] + ",";
 		}
 		result = result.substring(0, result.length() - 1);
		return result;
	}
	
	/*
	 * (�� Javadoc)
	*Title: splitMissCount
	*Description: 
	* @param missCount
	* @param info
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#splitMissCount(java.lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitMissCount(String missCount, LotteryInfo info) {
		Map<String,Integer> missCountResult = new TreeMap<String,Integer>();
		if(missCount != null){
			String [] groups = missCount.split(";");//���λ��
			for(int i = 0;i < groups.length;i++){
				String [] innerMiss = groups[i].split(",");
				for(int k = 0;k < innerMiss.length ; k++){
					missCountResult.put("P-"+String.valueOf(i+1)+"-"+String.valueOf(k), Integer.parseInt(innerMiss[k]));
				}
			}
		}
		info.setMissCountResult(missCountResult);
		return info;
	}
	
	public static void main(String[] args) {		
		Arrange5Lottery a5 = new Arrange5Lottery();
		String betCode = a5.lotteryRandomBetCode().replace("*", "");
		System.out.println(betCode);
		String lastMissCount = a5.getMissCount(betCode, null);
		System.out.println(lastMissCount);
		for(int i = 0; i < 50; i++){
			betCode = a5.lotteryRandomBetCode().replace("*", "");
			lastMissCount = a5.getMissCount(betCode, lastMissCount);
			System.out.println(betCode);
			System.out.println(lastMissCount);
		}
	}
	@Override
	public boolean isLimitBet(String playType,String betType, String limitNumber, String betCode) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
