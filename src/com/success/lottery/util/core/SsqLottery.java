/**
 * Title: SsqLottery.java
 * @Package com.success.lottery.util.core
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-12-3 ����11:15:04
 * @version V1.0
 */
package com.success.lottery.util.core;

import java.util.List;

import com.success.lottery.util.LotteryInfo;

/**
 * com.success.lottery.util.core
 * SsqLottery.java
 * SsqLottery
 * ˫ɫ��
 * @author gaoboqin
 * 2010-12-3 ����11:15:04
 * 
 */

public class SsqLottery extends LotteryAbstractTool implements LotteryInterf {

	/* (�� Javadoc)
	 *Title: checkBetType
	 *Description: 
	 * ���ݴ���Ĳ����淨id��Ͷע��ʽid��Ͷע�ַ�������У����� У��Ͷע�ַ����Ƿ���ȷ
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
		if(SSQ_PLATTYPE_NO_ADDITIONAL.equals(playType)){
			return checkBet(betType, betString);
		}else {
			throw new LotteryUnDefineException("δ�ҵ��淨��" + playType);
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
	 * Title: checkBet Description: У��Ͷע��ʽ�Ƿ���ȷ
	 * 
	 * @param betType
	 *            Ͷע��ʽ
	 * @param betString
	 *            Ͷע�ַ���
	 * @return boolean true ��ʶƥ�� false ��ʾ��ƥ��
	 * @throws LotteryUnDefineException
	 *             ,Exception ���û�ж�Ӧ���淨�Ͷ�Ӧ��Ͷע��ʽ���׳�LotteryUnDefineException
	 */
	private boolean checkBet(String betType, String betString) throws LotteryUnDefineException, Exception{
		if(SSQ_BETTYPE_SINGLE.equals(betType)){
			return checkSingle(betString);
		}else if(SSQ_BETTYPE_DUPLEX.equals(betType)){
			return checkDuplex(betString);
		}else if(SSQ_BETTYPE_DANTUO.equals(betType)){
			return false;//����û��ʵ��
		}else{
			throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��" + betType);
		}
	}
	
	/**
	 * 
	 * Title: checkSingle Description: У�鵥ʽ
	 * 
	 * @param betString
	 * @return boolean ��������
	 * @throws
	 */
	private boolean checkSingle(String betString){
		boolean checkResult = true;
		String[] betGroupByNum = splitGroup(betString, SSQ_BET_GROUP_SEPARATOR, true);
		for(String group : betGroupByNum){
			if(!checkRules(SSQ_BET_0_REGULAR, group)){
				checkResult = false;
				break;
			}else{
				if(!isRightByQu(group)){
					checkResult = false;
					break;
				}
			}
		}
		return checkResult;
	}

	/**
	 * 
	 * Title: checkDuplex Description: У�鸴ʽ ,��У���ʽ���ظ�
	 * 
	 * @param betString
	 * @return boolean ��������
	 * @throws
	 */
	private boolean checkDuplex(String betString){
		boolean checkResult = true;
		String[] betGroupByNum = splitGroup(betString, SSQ_BET_GROUP_SEPARATOR, true);
		for(String group : betGroupByNum){
			if(!checkRules(SSQ_BET_1_REGULAR, group)){
				checkResult = false;
				break;
			}else{
				if(!isRightByQu(group)){
					checkResult = false;
					break;
				}
			}
		}
		return checkResult;
	}
	
	/**
	 * 
	 * Title: isRightByQu Description: �ֱ�У�����������Ͷע�����Ƿ��ظ�
	 * 
	 * @param target
	 *            ҪУ���Ͷע���루��ĳһ��Ͷע���룬���Ƕ����
	 * @return boolean true У��ϸ�false У�鲻�ϸ�
	 * @throws
	 */
	private boolean isRightByQu(String target){
		boolean checkRepeat = true;
		String[] quGroup = target.split("\\|");
		for(String group : quGroup){
			if(!checkRepeat(group, 2)){
				checkRepeat = false;
				break;
			}
		}
		return checkRepeat;
	}

}
