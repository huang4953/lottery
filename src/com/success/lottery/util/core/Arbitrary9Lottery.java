/**
 * Title: Arbitrary9Lottery.java
 * @Package com.success.lottery.util.core
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-3-28 ����01:46:11
 * @version V1.0
 */
package com.success.lottery.util.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.success.lottery.util.LotteryInfo;
/**
 * 
 * com.success.lottery.util.core
 * 
 * Arbitrary9Lottery.java
 * 
 * Arbitrary9Lottery
 * 
 * ��ѡ9��������
 * @author gaoboqin
 * 
 * 2010-3-28 ����01:46:11
 *
 */
public class Arbitrary9Lottery extends LotteryAbstractTool implements
		LotteryInterf {
	
	private static Map<String,String> winRules;
	private static final String SPECIAL_NUMBER = "9";//�ú�����Ժ��������ƥ���н�
	
	static {
		winRules = new HashMap<String,String>();
		winRules.put("9", "1");//һ�Ƚ�
	}

	/*
	 * (�� Javadoc)
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
		if (ARBITRARY_9_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
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
		String [] arbitry9WinResult = info.getArbitry9WinResult();
		StringBuffer result = new StringBuffer();
		if(arbitry9WinResult.length == 2){
			result.append(arbitry9WinResult[0]);
			result.append("&");
			result.append(arbitry9WinResult[1]);
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
		String [] arbitry9WinResult = winResult.split("&");
		info.setArbitry9WinResult(arbitry9WinResult);
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
		String result = mergeListToStr(info.getArbitry9LotteryResult());
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
		 ArrayList<String> arbitry9LotteryResult = (ArrayList<String>)splitStrByLen(lotteryResult,1);
		info.setArbitry9LotteryResult(arbitry9LotteryResult);
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
		return super.mergeBallSalesInfoResult(info.getArbitrary9SaleInfo());
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
			info.setArbitrary9SaleInfo(super.splitBallSalesInfoResult(salesInfo));
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
		if (ARBITRARY_9_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			List<String> singleBet = Arrays.asList(splitGroup(betCode,WINORCLOSE_BET_GROUP_SEPARATOR, false));
			if (ARBITRARY_9_BETTYPE_SINGLE.equals(betType)) {
				toSingle = singleBet;
			} else if (ARBITRARY_9_BETTYPE_DUPLEX.equals(betType)) {
				toSingle = super.combineDuplex(betCode,ARBITRARY_9_BET_GROUP_SEPARATOR,"\\*",1);
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
		if (ARBITRARY_9_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			return arbitrary9Prize(playType, betType, lotteryResult, winResult,
					betCode);
		} else {
			throw new LotteryUnDefineException("δ�ҵ��淨��" + playType);
		}
	}
	
	private List<List<String>> arbitrary9Prize(String playType,String betType, String lotteryResult,
			String winResult, String betCode) throws LotteryUnDefineException,
			Exception {
		List<String> singleBet = Arrays.asList(splitGroup(betCode,WINORCLOSE_BET_GROUP_SEPARATOR, false));
		ArrayList<String> lotteryResultMap = splitLotteryResult(lotteryResult,new LotteryInfo()).getArbitry9LotteryResult();//����������
		String [] winResultMap = splitWinResult(winResult,new LotteryInfo()).getArbitry9WinResult();//������
		if (ARBITRARY_9_BETTYPE_SINGLE.equals(betType)) {
			return singlePrize(lotteryResultMap, winResultMap, singleBet);
		} else if (ARBITRARY_9_BETTYPE_DUPLEX.equals(betType)) {
			return duplexPrize(lotteryResultMap,winResultMap,betCode);
		} else {
			throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��" + betType);
		}
	}
	
	/**
	 * 
	 * Title: singlePrize
	 * Description:  ��ѡ9��ʽͶע�ҽ�
	 * @param lotteryResult
	 * @param winResult
	 * @param singleBet
	 * @return List<List<String>>    ��������
	 * @throws
	 */
	private List<List<String>> singlePrize(ArrayList<String> lotteryResult, String [] winResult,List<String> singleBet){
		List<List<String>> prizeResult = new ArrayList<List<String>>();
		for (String single : singleBet) {
			String [] singleArr = single.split("\\*");
			int hitNum = 0;
			for(int k = 0 ; k < lotteryResult.size() ; k++){
				if((lotteryResult.get(k).equals(SPECIAL_NUMBER) &&  !singleArr[k].equals("4")) || lotteryResult.get(k).equals(singleArr[k])){
					hitNum++;
				}
			}
			List<String> innerList = new ArrayList<String>();
			String jiBie = winRules.get(String.valueOf(hitNum));
			if(jiBie != null){
				innerList.add(single);
				innerList.add(jiBie);
				innerList.add(winResult[1]);
				innerList.add("0");
				prizeResult.add(innerList);
			}
		}
		return prizeResult;
	}
	/**
	 * 
	 * Title: duplexPrize
	 * Description:  ��ѡ9��ʽͶע�Ȳ�Ϊ��ʽ���ٰ��յ�ʽ�ҽ�
	 * @param lotteryResult
	 * @param winResult
	 * @param betCode
	 * @return List<List<String>> 
	 * @throws
	 */
	private List<List<String>> duplexPrize(ArrayList<String> lotteryResult, String [] winResult,String betCode){
		List<String> duplexToSingle = super.combineDuplex(betCode,ARBITRARY_9_BET_GROUP_SEPARATOR,"\\*",1);
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
	 * 
	 */
	private boolean checkBet(String betType,String betString) throws LotteryUnDefineException,Exception{
		if(ARBITRARY_9_BETTYPE_SINGLE.equals(betType)){
			return checkSingle(betString);
		}else if(ARBITRARY_9_BETTYPE_DUPLEX.equals(betType)){
			return checkDuplex(betString);
		}else {
			throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��"+betType);
		}
	}
	/**
	 * 
	 * Title: checkSingle
	 * Description: У����ѡ9�ĵ�ʽͶע
	 * @param betString
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkSingle(String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,ARBITRARY_9_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(ARBITRARY_9_BET_0_REGULAR,group)){
				checkResult = false;
				break;
			}else{
				if(checkCharViews(group,"4") != 5){
					checkResult = false;
					break;
				}
			}
		}
		return checkResult;
	}
	/**
	 * 
	 * Title: checkDuplex
	 * Description: У����ѡ9�ĸ�ʽͶע
	 * @param betString
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkDuplex(String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,ARBITRARY_9_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(ARBITRARY_9_BET_1_REGULAR,group)){
				checkResult = false;
				break;
			}else {
				if(checkCharViews(group,"4") != 5){
					checkResult = false;
					break;
				}
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
		// TODO �Զ����ɷ������
		return info;
	}

	@Override
	public boolean isLimitBet(String playType,String betType, String limitNumber, String betCode) {
		// TODO Auto-generated method stub
		return false;
	}
}
