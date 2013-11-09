/**
 * Title: WinOrCloseLottery.java
 * @Package com.success.lottery.util.core
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-3-28 ����12:10:52
 * @version V1.0
 */
package com.success.lottery.util.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.success.lottery.util.LotteryInfo;

/**
 * 
 * com.success.lottery.util.core
 * WinOrFailLottery.java
 * WinOrFailLottery
 * ʤ���ʴ�����
 * @author gaoboqin
 * 2010-3-28 ����12:10:52
 *
 */

public class WinOrFailLottery extends LotteryAbstractTool implements
		LotteryInterf {
	
	private static Map<String,String> winRules;
	
	private static final String SPECIAL_NUMBER = "9";//�ú�����Ժ��������ƥ���н�
	
	static {
		winRules = new HashMap<String,String>();
		winRules.put("14", "1");//һ�Ƚ�
		winRules.put("13", "2");//���Ƚ�
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
		if (WINORCLOSE_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
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
		TreeMap<String,String[]> winOrFailWinResult = info.getWinOrFailWinResult();
		StringBuffer result = new StringBuffer();
		boolean flag = false;
		for(Map.Entry<String, String[]> entry : winOrFailWinResult.entrySet()){
			String key = entry.getKey();
			if(flag){
				result.append("|");
			}else{
				flag = true;
			}
			result.append(key + "-");
			String [] value = entry.getValue();
			if(value.length == 2){
				result.append(value[0]);
				result.append("&");
				result.append(value[1]);
			}
		}
		return result.toString();
	}
	/*
	 * (�� Javadoc)
	*Title: splitWinResult
	*Description: 
	* @param winResult
	* @param info
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#splitWinResult(java.lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitWinResult(String winResult,LotteryInfo info) {
		TreeMap<String,String[]> winOrFailWinResult = new TreeMap<String,String[]>();
		String [] groupArr = winResult.split("\\|");
		for(String group : groupArr){
			String [] oneGroup = group.split("-");
			if(oneGroup.length == 2){
				String key = oneGroup[0];
				String [] value = oneGroup[1].split("&");
				winOrFailWinResult.put(key, value);
			}
		}
		info.setWinOrFailWinResult(winOrFailWinResult);
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
		String result = mergeListToStr(info.getWinOrFailLotteryResult());
		return result;
	}
	/*
	 * (�� Javadoc)
	*Title: splitLotteryResult
	*Description: 
	* @param lotteryResult
	* @param info
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#splitLotteryResult(java.lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitLotteryResult(String lotteryResult,LotteryInfo info) {
		ArrayList<String> winOrFailLotteryResult = (ArrayList<String>)splitStrByLen(lotteryResult,1);
		info.setWinOrFailLotteryResult(winOrFailLotteryResult);
		return info;
	}
	/*
	 * (�� Javadoc)
	*Title: mergeSalesInfoResult
	*Description: 
	* @param info
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#mergeSalesInfoResult(com.success.lottery.util.LotteryInfo)
	 */
	public String mergeSalesInfoResult(LotteryInfo info) {
		return super.mergeBallSalesInfoResult(info.getWinOrFailSaleInfo());
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
		if(salesInfo != null && !"".equals(salesInfo)){
			info.setWinOrFailSaleInfo(super.splitBallSalesInfoResult(salesInfo));
		}
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
		if (WINORCLOSE_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			if (WINORCLOSE_BETTYPE_SINGLE.equals(betType)) {
				toSingle = Arrays.asList(splitGroup(betCode,WINORCLOSE_BET_GROUP_SEPARATOR, false));
			} else if (WINORCLOSE_BETTYPE_DUPLEX.equals(betType)) {
				toSingle = super.combineDuplex(betCode,WINORCLOSE_BET_GROUP_SEPARATOR,"\\*",1);
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
		if (WINORCLOSE_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			return winOrFailPrize(playType, betType, lotteryResult, winResult, betCode);
		} else {
			throw new LotteryUnDefineException("δ�ҵ��淨��" + playType);
		}
	}
	/**
	 * 
	 * Title: winOrFailPrize
	 * Description:  ʤ���ʰ���Ͷע��ʽ�ҽ�
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
	private List<List<String>> winOrFailPrize(String playType,String betType, String lotteryResult,
			String winResult, String betCode) throws LotteryUnDefineException,
			Exception {
		List<String> singleBet = Arrays.asList(splitGroup(betCode,WINORCLOSE_BET_GROUP_SEPARATOR, false));
		ArrayList<String> lotteryResultMap = splitLotteryResult(lotteryResult,new LotteryInfo()).getWinOrFailLotteryResult();//����������
		TreeMap<String,String[]> winResultMap = splitWinResult(winResult,new LotteryInfo()).getWinOrFailWinResult();//������
		if (WINORCLOSE_BETTYPE_SINGLE.equals(betType)) {
			return singlePrize(lotteryResultMap, winResultMap, singleBet);
		} else if (WINORCLOSE_BETTYPE_DUPLEX.equals(betType)) {
			return duplexPrize(lotteryResultMap,winResultMap,betCode);
		} else {
			throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��" + betType);
		}
	}
	
	/**
	 * 
	 * Title: singlePrize
	 * Description:  ʤ���ʵ�ʽͶע�ҽ�
	 * @param lotteryResult
	 * @param winResult
	 * @param singleBet
	 * @return List<List<String>>    ��������
	 * @throws
	 */
	private List<List<String>> singlePrize(ArrayList<String> lotteryResult, TreeMap<String,String[]> winResult,List<String> singleBet){
		List<List<String>> prizeResult = new ArrayList<List<String>>();
		for (String single : singleBet) {
			String [] singleArr = single.split("\\*");
			int hitNum = 0;
			for(int k = 0 ; k < lotteryResult.size() ; k++){
				if(lotteryResult.get(k).equals(SPECIAL_NUMBER) || lotteryResult.get(k).equals(singleArr[k])){
					hitNum++;
				}
			}
			List<String> innerList = new ArrayList<String>();
			String jiBie = winRules.get(String.valueOf(hitNum));
			if(jiBie != null){
				innerList.add(single);
				innerList.add(jiBie);
				innerList.add(winResult.get(jiBie)[1]);
				innerList.add("0");
				prizeResult.add(innerList);
			}
		}
		return prizeResult;
	}
	/**
	 * 
	 * Title: duplexPrize
	 * Description:  ʤ���ʸ�ʽͶע�Ȳ�Ϊ��ʽ���ٰ��յ�ʽ�ҽ�
	 * @param lotteryResult
	 * @param winResult
	 * @param betCode
	 * @return List<List<String>>    ��������
	 * @throws
	 */
	private List<List<String>> duplexPrize(ArrayList<String> lotteryResult, TreeMap<String,String[]> winResult,String betCode){
		List<String> duplexToSingle = super.combineDuplex(betCode,WINORCLOSE_BET_GROUP_SEPARATOR,"\\*",1);
		return singlePrize(lotteryResult,winResult,duplexToSingle);
	}
	
	
	
	/**
	 * 
	 * Title: checkBet
	 * Description: (������һ�仰�����������������)
	 * @param betType
	 * @param betString
	 * @throws LotteryUnDefineException
	 * @throws Exception    �趨�ļ�
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkBet(String betType,String betString) throws LotteryUnDefineException,Exception{
		if(WINORCLOSE_BETTYPE_SINGLE.equals(betType)){
			return checkSingle(betString);
		}else if(WINORCLOSE_BETTYPE_DUPLEX.equals(betType)){
			return checkDuplex(betString);
		}else {
			throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��"+betType);
		}
	}
	/**
	 * 
	 * Title: checkSingle
	 * Description: (������һ�仰�����������������)
	 * @param betString
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkSingle(String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,WINORCLOSE_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(WINORCLOSE_BET_0_REGULAR,group)){
				checkResult = false;
				break;
			}
		}
		return checkResult;
	}
	/**
	 * 
	 * Title: checkDuplex
	 * Description: (������һ�仰�����������������)
	 * @param betString
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkDuplex(String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,WINORCLOSE_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(WINORCLOSE_BET_1_REGULAR,group)){
				checkResult = false;
				break;
			}
		}
		return checkResult;
	}
	
	public String lotteryRandomBetCode(){
		//  Auto-generated method stub
		return null;
	}
	@Override
	public String getMissCount(String lotteryResult, String lastMissCount){
		return null;
	}
	public LotteryInfo splitMissCount(String missCount, LotteryInfo info) {
		return info;
	}
	@Override
	public boolean isLimitBet(String playType,String betType, String limitNumber, String betCode) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
