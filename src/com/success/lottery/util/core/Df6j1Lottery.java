/**
 * Title: Df6j1Lottery.java
 * @Package com.success.lottery.util.core
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-12-3 上午11:45:09
 * @version V1.0
 */
package com.success.lottery.util.core;

import java.util.List;

import com.success.lottery.util.LotteryInfo;

/**
 * com.success.lottery.util.core
 * Df6j1Lottery.java
 * Df6j1Lottery
 * 江西东方6+1
 * @author gaoboqin
 * 2010-12-3 上午11:45:09
 * 
 */

public class Df6j1Lottery extends LotteryAbstractTool implements LotteryInterf {

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
		if(DF6J1_PLATTYPE_NO_ADDITIONAL.equals(playType)){
			return checkBet(betType, betString);
		}else {
			throw new LotteryUnDefineException("未找到玩法：" + playType);
		}
	}

	/* (非 Javadoc)
	 *Title: getMissCount
	 *Description: 
	 * @param lotteryResult
	 * @param lastMissCount
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#getMissCount(java.lang.String, java.lang.String)
	 */
	public String getMissCount(String lotteryResult, String lastMissCount) {
		// TODO 自动生成方法存根
		return null;
	}

	/* (非 Javadoc)
	 *Title: isLimitBet
	 *Description: 
	 * @param playType
	 * @param betType
	 * @param limitNumberArrary
	 * @param betCode
	 * @return
	 * @throws LotteryUnDefineException
	 * @see com.success.lottery.util.core.LotteryInterf#isLimitBet(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean isLimitBet(String playType, String betType,
			String limitNumberArrary, String betCode)
			throws LotteryUnDefineException {
		// TODO 自动生成方法存根
		return false;
	}

	/* (非 Javadoc)
	 *Title: lotteryPrize
	 *Description: 
	 * @param playType
	 * @param betType
	 * @param lotteryResult
	 * @param winResult
	 * @param betCode
	 * @return
	 * @throws LotteryUnDefineException
	 * @throws Exception
	 * @see com.success.lottery.util.core.LotteryInterf#lotteryPrize(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<List<String>> lotteryPrize(String playType, String betType,
			String lotteryResult, String winResult, String betCode)
			throws LotteryUnDefineException, Exception {
		// TODO 自动生成方法存根
		return null;
	}

	/* (非 Javadoc)
	 *Title: lotteryRandomBetCode
	 *Description: 
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#lotteryRandomBetCode()
	 */
	public String lotteryRandomBetCode() {
		// TODO 自动生成方法存根
		return null;
	}

	/* (非 Javadoc)
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
	public String lotterySplit(String playType, String betType, String betCode)
			throws LotteryUnDefineException, Exception {
		// TODO 自动生成方法存根
		return null;
	}

	/* (非 Javadoc)
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
	public List<String> lotteryToSingle(String playType, String betType,
			String betCode) throws LotteryUnDefineException, Exception {
		// TODO 自动生成方法存根
		return null;
	}

	/* (非 Javadoc)
	 *Title: mergeLotteryResult
	 *Description: 
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#mergeLotteryResult(com.success.lottery.util.LotteryInfo)
	 */
	public String mergeLotteryResult(LotteryInfo info) {
		// TODO 自动生成方法存根
		return null;
	}

	/* (非 Javadoc)
	 *Title: mergeSalesInfoResult
	 *Description: 
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#mergeSalesInfoResult(com.success.lottery.util.LotteryInfo)
	 */
	public String mergeSalesInfoResult(LotteryInfo info) {
		// TODO 自动生成方法存根
		return null;
	}

	/* (非 Javadoc)
	 *Title: mergeWinResult
	 *Description: 
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#mergeWinResult(com.success.lottery.util.LotteryInfo)
	 */
	public String mergeWinResult(LotteryInfo info) {
		// TODO 自动生成方法存根
		return null;
	}

	/* (非 Javadoc)
	 *Title: splitLotteryResult
	 *Description: 
	 * @param lotteryResult
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#splitLotteryResult(java.lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitLotteryResult(String lotteryResult, LotteryInfo info) {
		// TODO 自动生成方法存根
		return null;
	}

	/* (非 Javadoc)
	 *Title: splitMissCount
	 *Description: 
	 * @param missCount
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#splitMissCount(java.lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitMissCount(String missCount, LotteryInfo info) {
		// TODO 自动生成方法存根
		return null;
	}

	/* (非 Javadoc)
	 *Title: splitSalesInfoResult
	 *Description: 
	 * @param salesInfo
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#splitSalesInfoResult(java.lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitSalesInfoResult(String salesInfo, LotteryInfo info) {
		// TODO 自动生成方法存根
		return null;
	}

	/* (非 Javadoc)
	 *Title: splitWinResult
	 *Description: 
	 * @param winResult
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#splitWinResult(java.lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitWinResult(String winResult, LotteryInfo info) {
		// TODO 自动生成方法存根
		return null;
	}
	
	/**
	 * 
	 * Title: checkBet Description: 校验投注格式是否正确
	 * 
	 * @param betType
	 *            投注方式
	 * @param betString
	 *            投注字符串
	 * @return boolean true 标识匹配 false 表示不匹配
	 * @throws LotteryUnDefineException
	 *             ,Exception 如果没有对应的玩法和对应的投注方式会抛出LotteryUnDefineException
	 */
	private boolean checkBet(String betType, String betString) throws LotteryUnDefineException, Exception{
		if(DF6J1_BETTYPE_SINGLE.equals(betType)){
			return checkSingle(betString);
		}else if(DF6J1_BETTYPE_DUPLEX.equals(betType)){
			return checkDuplex(betString);
		}else{
			throw new LotteryUnDefineException("未找到投注方式：" + betType);
		}
	}
	
	/**
	 * 
	 * Title: checkSingle Description: 校验单式
	 * 
	 * @param betString
	 * @return boolean 返回类型
	 * @throws
	 */
	private boolean checkSingle(String betString){
		boolean checkResult = true;
		String[] betGroupByNum = splitGroup(betString, DF6J1_BET_GROUP_SEPARATOR, true);
		for(String group : betGroupByNum){
			if(!checkRules(DF6J1_BET_0_REGULAR, group)){
				checkResult = false;
				break;
			}
		}
		return checkResult;
	}

	/**
	 * 
	 * Title: checkDuplex Description: 校验复式 
	 * 
	 * @param betString
	 * @return boolean 返回类型
	 * @throws
	 */
	private boolean checkDuplex(String betString){
		boolean checkResult = true;
		String[] betGroupByNum = splitGroup(betString, DF6J1_BET_GROUP_SEPARATOR, true);
		for(String group : betGroupByNum){
			if(!checkRules(DF6J1_BET_1_REGULAR, group)){
				checkResult = false;
				break;
			}else{
				if(!isRepeatByQu(group)){
					checkResult = false;
					break;
				}
			}
		}
		return checkResult;
	}
	
	/**
	 * 
	 * Title: isRightByQu Description:
	 * 
	 * @param target
	 *            要校验的投注号码（是某一个投注号码，不是多个）
	 * @return boolean true 校验合格，false 校验不合格
	 * @throws
	 */
	private boolean isRepeatByQu(String target){
		boolean checkRepeat = true;
		String[] quGroup = target.split("\\|");
		
		String [] betWei = quGroup[0].split("\\*");//基本区
		
		for (String wei : betWei){
			if(!checkRepeat(wei,1)){
				return false;
			}
		}
		
		if(!checkRepeat(quGroup[1],2)){//生肖区
			return false;
		}
		
		return checkRepeat;
	}

}
