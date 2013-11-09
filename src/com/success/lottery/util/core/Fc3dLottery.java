/**
 * Title: Fc3dLottery.java
 * @Package com.success.lottery.util.core
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-12-3 上午11:43:59
 * @version V1.0
 */
package com.success.lottery.util.core;

import java.util.List;

import com.success.lottery.util.LotteryInfo;

/**
 * com.success.lottery.util.core
 * Fc3dLottery.java
 * Fc3dLottery
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-12-3 上午11:43:59
 * 
 */

public class Fc3dLottery extends LotteryAbstractTool implements LotteryInterf {

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
		if (FC3D_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			return checkBet(betType, betString);
		}else {
			throw new LotteryUnDefineException("未找到玩法："+playType);
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
	 * Title: checkBet
	 * Description: 校验福彩3d的投注注码格式是否正确
	 * @param betType
	 * @param betString
	 * @throws LotteryUnDefineException
	 * @throws Exception    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	private boolean checkBet(String betType,String betString) throws LotteryUnDefineException,Exception{
		if(FC3D_BET_0_REGULAR.equals(betType)){//直选单式
			return checkSingle(FC3D_BETTYPE_0,betString);
		}else if(FC3D_BET_1_REGULAR.equals(betType)){//直选复式
			return checkDuplex(FC3D_BETTYPE_1,betString);
		}else if(FC3D_BET_2_REGULAR.equals(betType)){//组选单式
			return checkGroup(FC3D_BETTYPE_2,betString,2);
		}else if(FC3D_BET_3_REGULAR.equals(betType)){//组选3
			return checkGroup(FC3D_BETTYPE_3,betString,1);
		}else if(FC3D_BET_4_REGULAR.equals(betType)){//组选6
			return checkGroup(FC3D_BETTYPE_4,betString,1);
		}else{
			throw new LotteryUnDefineException("未找到投注方式："+betType);
		}
	}
	/**
	 * 
	 * Title: checkSingle<br>
	 * Description: <br>
	 *              <br>按照规则校验单式
	 * @param betRegu
	 * @param betString
	 * @return
	 */
	private boolean checkSingle(String betRegu,String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,FC3D_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(betRegu,group)){
				checkResult = false;
				break;
			}
		}
		return checkResult;
	}
	
	/**
	 * 
	 * Title: checkWeiRepeat<br>
	 * Description: <br>
	 *            先按照定义的规则校验再校验每一个位置的数字不能重复<br>
	 * @param betRegu
	 * @param betString
	 * @return boolean
	 */
	private boolean checkDuplex(String betRegu,String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,FC3D_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(betRegu,group)){
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
	/**
	 * 
	 * Title: checkGroup<br>
	 * Description: <br>
	 *              <br>校验组选规则
	 * @param betRegu
	 * @param betString
	 * @return
	 */
	private boolean checkGroup(String betRegu,String betString,int num){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,FC3D_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(betRegu,group)){
				checkResult = false;
				break;
			}else{
				if(super.checkRepeatNum(group.replace("*", ""),1) == num){
					checkResult = false;
					break;
				}
			}
		}
		return checkResult;
	}

}
