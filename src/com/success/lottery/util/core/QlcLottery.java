/**
 * Title: QlcLottery.java
 * @Package com.success.lottery.util.core
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-12-3 ����11:43:25
 * @version V1.0
 */
package com.success.lottery.util.core;

import java.util.List;

import com.success.lottery.util.LotteryInfo;

/**
 * com.success.lottery.util.core
 * QlcLottery.java
 * QlcLottery
 * �������ֲ�
 * @author gaoboqin
 * 2010-12-3 ����11:43:25
 * 
 */

public class QlcLottery extends LotteryAbstractTool implements LotteryInterf {

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
		if (QLC_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			return checkBet(betType, betString);
		}else {
			throw new LotteryUnDefineException("δ�ҵ��淨��"+playType);
		}
	}

	/* (�� Javadoc)
	 *Title: getMissCount
	 *Description: 
	 * @param lotteryResult
	 * @param lastMissCount
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#getMissCount(java.lang.String, java.lang.String)
	 */
	public String getMissCount(String lotteryResult, String lastMissCount) {
		// TODO �Զ����ɷ������
		return null;
	}

	/* (�� Javadoc)
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
		// TODO �Զ����ɷ������
		return false;
	}

	/* (�� Javadoc)
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
		// TODO �Զ����ɷ������
		return null;
	}

	/* (�� Javadoc)
	 *Title: lotteryRandomBetCode
	 *Description: 
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#lotteryRandomBetCode()
	 */
	public String lotteryRandomBetCode() {
		// TODO �Զ����ɷ������
		return null;
	}

	/* (�� Javadoc)
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
		// TODO �Զ����ɷ������
		return null;
	}

	/* (�� Javadoc)
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
		// TODO �Զ����ɷ������
		return null;
	}

	/* (�� Javadoc)
	 *Title: mergeLotteryResult
	 *Description: 
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#mergeLotteryResult(com.success.lottery.util.LotteryInfo)
	 */
	public String mergeLotteryResult(LotteryInfo info) {
		// TODO �Զ����ɷ������
		return null;
	}

	/* (�� Javadoc)
	 *Title: mergeSalesInfoResult
	 *Description: 
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#mergeSalesInfoResult(com.success.lottery.util.LotteryInfo)
	 */
	public String mergeSalesInfoResult(LotteryInfo info) {
		// TODO �Զ����ɷ������
		return null;
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
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#splitLotteryResult(java.lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitLotteryResult(String lotteryResult, LotteryInfo info) {
		// TODO �Զ����ɷ������
		return null;
	}

	/* (�� Javadoc)
	 *Title: splitMissCount
	 *Description: 
	 * @param missCount
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#splitMissCount(java.lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitMissCount(String missCount, LotteryInfo info) {
		// TODO �Զ����ɷ������
		return null;
	}

	/* (�� Javadoc)
	 *Title: splitSalesInfoResult
	 *Description: 
	 * @param salesInfo
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#splitSalesInfoResult(java.lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitSalesInfoResult(String salesInfo, LotteryInfo info) {
		// TODO �Զ����ɷ������
		return null;
	}

	/* (�� Javadoc)
	 *Title: splitWinResult
	 *Description: 
	 * @param winResult
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#splitWinResult(java.lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitWinResult(String winResult, LotteryInfo info) {
		// TODO �Զ����ɷ������
		return null;
	}
	
	/**
	 * 
	 * Title: checkBet
	 * Description: (������һ�仰�����������������)
	 * @param betType
	 * @param betString
	 * @throws LotteryUnDefineException
	 * @throws Exception    �趨�ļ�
	 * @return boolean 
	 * @throws
	 */
	private boolean checkBet(String betType,String betString) throws LotteryUnDefineException,Exception{
		if(QLC_BETTYPE_SINGLE.equals(betType)){
			return checkSingle(betString);
		}else if(QLC_BETTYPE_DUPLEX.equals(betType)){
			return checkDuplex(betString);
		}else if(QLC_BETTYPE_DANTUO.equals(betType)){
			return checkDanTuo(betString);
		}else {
			throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��"+betType);
		}
	}
	
	/**
	 * 
	 * Title: checkSingle
	 * Description:  У�鵥ʽ ����Ͷע��ʽ�Ͳ������ظ�
	 * @param betString
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkSingle(String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,QLC_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(QLC_BET_0_REGULAR,group)){
				checkResult = false;
				break;
			}else{
				if(!checkRepeat(group,2)){
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
	 * Description:  У�鸴ʽͶע
	 * @param betString
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkDuplex(String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,QLC_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(QLC_BET_1_REGULAR,group)){
				checkResult = false;
				break;
			}else{
				if(!checkRepeat(group,2)){
					checkResult = false;
					break;
				}
			}
		}
		return checkResult;
	}
	
	/**
	 * 
	 * Title: checkDanTuo
	 * Description:  У�鵨�ϣ�������ʽ���ظ�������
	 * @param betString
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkDanTuo(String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,QLC_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(QLC_BET_2_REGULAR,group)){//��ʽ���ϸ�
				checkResult = false;
				break;
			}else if(!checkDanTuoRepeat(group,2)){//У���Ƿ��ظ�,����ǰ���ͺ���
				checkResult = false;
				break;
			}else if(!checkDanTuoTotalNum(group)){//У�������Ƿ�
				checkResult = false;
				break;
			}
		}
		return checkResult;
	} 
	
	/**
	 * 
	 * Title: checkDanTuoTotalNum
	 * Description:  У�鵨�ϵ���λ��,���������>=7
	 * @param target
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkDanTuoTotalNum(String target){
		boolean checkResult = true;
		String [] DanTuoGroup = target.split("\\*");//�˴��Ѿ�����У�飬������������룬��������Ϊ2��ֵ
		List<String> dan = splitStrByLen(DanTuoGroup[0],2);
		List<String> tuo = splitStrByLen(DanTuoGroup[1],2);
		if((dan.size() + tuo.size()) < 7){
			checkResult = false;
			return checkResult;
		}
		return checkResult;
	}

}
