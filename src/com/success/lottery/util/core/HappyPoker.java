/**
 * Title: HappyPoker.java
 * @Package com.success.lottery.util.core
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-3-29 ����08:13:51
 * @version V1.0
 */
package com.success.lottery.util.core;

import java.util.ArrayList;
import java.util.List;

import com.success.lottery.util.LotteryInfo;

/**
 * 
 * com.success.lottery.util.core
 * HappyPoker.java
 * HappyPoker
 * �����˿˴�����
 * @author gaoboqin
 * 2010-3-29 ����08:13:51
 *
 */
public class HappyPoker extends LotteryAbstractTool implements LotteryInterf {

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
		// TODO �Զ����ɷ������
		return false;
	}

	/* (�� Javadoc)
	 *Title: mergeLotteryResult
	 *Description: 
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#mergeLotteryResult(com.success.lottery.util.LotteryInfo)
	 */
	public String mergeLotteryResult(LotteryInfo info) {
		String result = mergeListToStr(info.getHappyPokerLotteryResult());
		return result;
	}

	/* (�� Javadoc)
	 *Title: mergeWinResult
	 *Description: 
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#mergeWinResult(com.success.lottery.util.LotteryInfo)
	 */
	public String mergeWinResult(LotteryInfo info) {
		// TODO �Զ����ɷ������
		return null;
	}

	/* (�� Javadoc)
	 *Title: splitLotteryResult
	 *Description: 
	 * @param lotteryResult
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#splitLotteryResult(java.lang.String)
	 */
	public LotteryInfo splitLotteryResult(String lotteryResult,LotteryInfo info) {
		ArrayList<String> happyPokerLotteryResult = (ArrayList<String>)splitStrByLen(lotteryResult,1);
		info.setHappyPokerLotteryResult(happyPokerLotteryResult);
		return info;
	}

	/* (�� Javadoc)
	 *Title: splitWinResult
	 *Description: 
	 * @param winResult
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#splitWinResult(java.lang.String)
	 */
	public LotteryInfo splitWinResult(String winResult,LotteryInfo info) {
		// TODO �Զ����ɷ������
		return null;
	}

	public List<List<String>> lotteryPrize(String playType, String betType, String lotteryResult, String winReslut, String betCode) {
		// TODO �Զ����ɷ������
		return null;
	}

	public String lotterySplit(String playType, String betType, String betCode) throws LotteryUnDefineException, Exception {
		// TODO �Զ����ɷ������
		return null;
	}

	public List<String> lotteryToSingle(String playType, String betType, String betCode) throws LotteryUnDefineException, Exception {
		// TODO �Զ����ɷ������
		return null;
	}

	
	public String lotteryRandomBetCode(){
		// TODO Auto-generated method stub
		return null;
	}

	public String mergeSalesInfoResult(LotteryInfo info) {
		// TODO �Զ����ɷ������
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
		// TODO Auto-generated method stub
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
