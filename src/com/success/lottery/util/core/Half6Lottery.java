/**
 * Title: Half6Lottery.java
 * @Package com.success.lottery.util.core
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-3-28 ����02:21:25
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
 * Half6Lottery.java
 * Half6Lottery
 * 6����ȫ��������
 * @author gaoboqin
 * 2010-3-28 ����02:21:25
 *
 */
public class Half6Lottery extends LotteryAbstractTool implements LotteryInterf {

	private static Map<String,String> winRules;
	private static final String SPECIAL_NUMBER = "9";//�ú�����Ժ��������ƥ���н�
	
	static {
		winRules = new HashMap<String,String>();
		winRules.put("12", "1");//һ�Ƚ�
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
		if (HALF_6_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
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
		String [] half6WinResult = info.getHalf6WinResult();
		StringBuffer result = new StringBuffer();
		if(half6WinResult != null && half6WinResult.length == 2){
			result.append(half6WinResult[0]);
			result.append("&");
			result.append(half6WinResult[1]);
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
		String [] half6WinResult = winResult.split("&");
		info.setHalf6WinResult(half6WinResult);
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
		String result = mergeListToStr(info.getHalf6LotteryResult());
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
		ArrayList<String> half6LotteryResult = (ArrayList<String>)splitStrByLen(lotteryResult,1);
		info.setHalf6LotteryResult(half6LotteryResult);
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
		return super.mergeBallSalesInfoResult(info.getHalf6SaleInfo());
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
			info.setHalf6SaleInfo(super.splitBallSalesInfoResult(salesInfo));
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
		long zhuNum = this.lotteryToSingle(playType,betType,betCode).size();
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
		if (HALF_6_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			List<String> singleBet = Arrays.asList(splitGroup(betCode,HALF_6_BET_GROUP_SEPARATOR, false));
			if (HALF_6_BETTYPE_SINGLE.equals(betType)) {
				toSingle = singleBet;
			} else if (HALF_6_BETTYPE_DUPLEX.equals(betType)) {
				toSingle = super.combineDuplex(betCode,HALF_6_BET_GROUP_SEPARATOR,"\\*",1);
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
		if (HALF_6_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			return this.half6Prize(playType, betType, lotteryResult, winResult,
					betCode);
		} else {
			throw new LotteryUnDefineException("δ�ҵ��淨��" + playType);
		}
	}
	/**
	 * 
	 * Title: half6Prize<br>
	 * Description: <br>
	 *              <br>6����ȫ���ҽ�
	 * @param playType
	 * @param betType
	 * @param lotteryResult
	 * @param winResult
	 * @param betCode
	 * @return
	 * @throws LotteryUnDefineException
	 * @throws Exception
	 */
	private List<List<String>> half6Prize(String playType,String betType, String lotteryResult,
			String winResult, String betCode) throws LotteryUnDefineException,
			Exception {
		List<String> singleBet = Arrays.asList(splitGroup(betCode,HALF_6_BET_GROUP_SEPARATOR, false));
		ArrayList<String> lotteryResultMap = splitLotteryResult(lotteryResult,new LotteryInfo()).getHalf6LotteryResult();//����������
		String [] winResultMap = splitWinResult(winResult,new LotteryInfo()).getHalf6WinResult();//������
		if (HALF_6_BETTYPE_SINGLE.equals(betType)) {
			return singlePrize(lotteryResultMap, winResultMap, singleBet);
		} else if (HALF_6_BETTYPE_DUPLEX.equals(betType)) {
			return duplexPrize(lotteryResultMap,winResultMap,betCode);
		} else {
			throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��" + betType);
		}
	}
	
	/**
	 * 
	 * Title: singlePrize
	 * Description: 6��ȫ�볡��ʽͶע�ҽ�,���ڿ��������Ϊ9�ĺ������λ�õ��κ�Ͷע���붼����ƥ����
	 * @param lotteryResult
	 * @param winResult
	 * @param singleBet
	 * @return List<List<String>>    ��������
	 */
	private List<List<String>> singlePrize(ArrayList<String> lotteryResult, String [] winResult,List<String> singleBet){
		List<List<String>> prizeResult = new ArrayList<List<String>>();
		for (String single : singleBet) {
			String [] singleArr = single.split("\\*");
			int hitNum = 0;
			for(int k = 0 ; k < lotteryResult.size() ; k++){
				if(lotteryResult.get(k).equals(singleArr[k]) || lotteryResult.get(k).equals(SPECIAL_NUMBER)){
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
	 * Description:  6��ȫ�볡��ʽͶע�Ȳ�Ϊ��ʽ���ٰ��յ�ʽ�ҽ�
	 * @param lotteryResult
	 * @param winResult
	 * @param betCode
	 * @return List<List<String>>    ��������
	 * @throws
	 */
	private List<List<String>> duplexPrize(ArrayList<String> lotteryResult, String [] winResult,String betCode){
		List<String> duplexToSingle = super.combineDuplex(betCode,HALF_6_BET_GROUP_SEPARATOR,"\\*",1);
		return singlePrize(lotteryResult,winResult,duplexToSingle);
	}
	
	/**
	 * 
	 * Title: checkBet
	 * Description: ����Ͷע��ʽУ��6����ȫ��Ͷע����ʽ
	 * @param betType
	 * @param betString
	 * @throws LotteryUnDefineException
	 * @throws Exception    �趨�ļ�
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkBet(String betType,String betString) throws LotteryUnDefineException,Exception{
		if(HALF_6_BETTYPE_SINGLE.equals(betType)){
			return check(HALF_6_BET_0_REGULAR,betString);
		}else if(HALF_6_BETTYPE_DUPLEX.equals(betType)){
			return check(HALF_6_BET_1_REGULAR,betString);
		}else {
			throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��"+betType);
		}
	}
	
	/**
	 * 
	 * Title: check
	 * Description: (������һ�仰�����������������)
	 * @param betRegu
	 * @param betString
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean check(String betRegu,String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,HALF_6_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(betRegu,group)){
				checkResult = false;
				break;
			}
		}
		return checkResult;
	}
	/*
	 * (�� Javadoc)
	*Title: lotteryRandomBetCode
	*Description: 
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#lotteryRandomBetCode()
	 */
	public String lotteryRandomBetCode(){
		return null;
	}
	/*
	 * (�� Javadoc)
	*Title: getMissCount
	*Description: 
	* @param lotteryResult
	* @param lastMissCount
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#getMissCount(java.lang.String, java.lang.String)
	 */
	public String getMissCount(String lotteryResult, String lastMissCount){
		return null;
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
		return info;
	}
	/*
	 * (�� Javadoc)
	*Title: isLimitBet
	*Description: 
	* @param playType
	* @param betType
	* @param limitNumber
	* @param betCode
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#isLimitBet(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean isLimitBet(String playType,String betType, String limitNumber, String betCode) {
		return false;
	}
}
