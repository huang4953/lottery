/**
 * Title: Half6Lottery.java
 * @Package com.success.lottery.util.core
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-3-28 下午02:21:25
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
 * 6场半全场处理类
 * @author gaoboqin
 * 2010-3-28 下午02:21:25
 *
 */
public class Half6Lottery extends LotteryAbstractTool implements LotteryInterf {

	private static Map<String,String> winRules;
	private static final String SPECIAL_NUMBER = "9";//该号码可以和任意号码匹配中奖
	
	static {
		winRules = new HashMap<String,String>();
		winRules.put("12", "1");//一等奖
	}
	/* (非 Javadoc)
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
			throw new LotteryUnDefineException("未找到玩法："+playType);
		}
	}
	/*
	 * (非 Javadoc)
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
	 * (非 Javadoc)
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
	 * (非 Javadoc)
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
	 * (非 Javadoc)
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
	 * (非 Javadoc)
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
	 * (非 Javadoc)
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
	 * (非 Javadoc)
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
	 * (非 Javadoc)
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
				throw new LotteryUnDefineException("未找到投注方式：" + betType);
			}
		} else {
			throw new LotteryUnDefineException("未找到玩法：" + playType);
		}
		return toSingle;
	}
	/*
	 * (非 Javadoc)
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
			throw new LotteryUnDefineException("未找到玩法：" + playType);
		}
	}
	/**
	 * 
	 * Title: half6Prize<br>
	 * Description: <br>
	 *              <br>6场半全场兑奖
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
		ArrayList<String> lotteryResultMap = splitLotteryResult(lotteryResult,new LotteryInfo()).getHalf6LotteryResult();//开奖号码结果
		String [] winResultMap = splitWinResult(winResult,new LotteryInfo()).getHalf6WinResult();//奖金结果
		if (HALF_6_BETTYPE_SINGLE.equals(betType)) {
			return singlePrize(lotteryResultMap, winResultMap, singleBet);
		} else if (HALF_6_BETTYPE_DUPLEX.equals(betType)) {
			return duplexPrize(lotteryResultMap,winResultMap,betCode);
		} else {
			throw new LotteryUnDefineException("未找到投注方式：" + betType);
		}
	}
	
	/**
	 * 
	 * Title: singlePrize
	 * Description: 6场全半场单式投注兑奖,对于开奖结果中为9的号码则该位置的任何投注号码都算做匹配上
	 * @param lotteryResult
	 * @param winResult
	 * @param singleBet
	 * @return List<List<String>>    返回类型
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
	 * Description:  6场全半场复式投注先拆为单式，再按照单式兑奖
	 * @param lotteryResult
	 * @param winResult
	 * @param betCode
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> duplexPrize(ArrayList<String> lotteryResult, String [] winResult,String betCode){
		List<String> duplexToSingle = super.combineDuplex(betCode,HALF_6_BET_GROUP_SEPARATOR,"\\*",1);
		return singlePrize(lotteryResult,winResult,duplexToSingle);
	}
	
	/**
	 * 
	 * Title: checkBet
	 * Description: 按照投注方式校验6场半全场投注串格式
	 * @param betType
	 * @param betString
	 * @throws LotteryUnDefineException
	 * @throws Exception    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	private boolean checkBet(String betType,String betString) throws LotteryUnDefineException,Exception{
		if(HALF_6_BETTYPE_SINGLE.equals(betType)){
			return check(HALF_6_BET_0_REGULAR,betString);
		}else if(HALF_6_BETTYPE_DUPLEX.equals(betType)){
			return check(HALF_6_BET_1_REGULAR,betString);
		}else {
			throw new LotteryUnDefineException("未找到投注方式："+betType);
		}
	}
	
	/**
	 * 
	 * Title: check
	 * Description: (这里用一句话描述这个方法的作用)
	 * @param betRegu
	 * @param betString
	 * @return boolean    返回类型
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
	 * (非 Javadoc)
	*Title: lotteryRandomBetCode
	*Description: 
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#lotteryRandomBetCode()
	 */
	public String lotteryRandomBetCode(){
		return null;
	}
	/*
	 * (非 Javadoc)
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
	 * (非 Javadoc)
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
	 * (非 Javadoc)
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
