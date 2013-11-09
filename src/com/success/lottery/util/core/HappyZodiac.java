/**
 * Title: HappyZodiac.java
 * @Package com.success.lottery.util.core
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-3-27 ����10:34:21
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
 * HappyZodiac.java
 * HappyZodiac
 * ��Ф�ִ�����
 * @author gaoboqin
 * 2010-3-27 ����10:34:21
 *
 */
public class HappyZodiac extends LotteryAbstractTool implements LotteryInterf {

	private static Map<String,String> winRules;
	
	static {
		winRules = new HashMap<String,String>();
		winRules.put("2", "0");//0�Ƚ�
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
		if (HAPPY_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
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
		String [] happyZodiacWinResult = info.getHappyZodiacWinResult();
		StringBuffer result = new StringBuffer();
		if(happyZodiacWinResult.length == 2){
			result.append(happyZodiacWinResult[0]);
			result.append("&");
			result.append(happyZodiacWinResult[1]);
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
		String [] happyZodiacWinResult = winResult.split("&");
		info.setHappyZodiacWinResult(happyZodiacWinResult);
		return info;
	}
	/*
	 * (�� Javadoc)
	 *Title: mergeLotteryResult
	 *Description: 
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#mergeLotteryResult(com.success.lottery.util.LotteryInfo)
	 * 0102
	 */
	public String mergeLotteryResult(LotteryInfo info) {
		String result = mergeListToStr(info.getHappyZodiacLotteryResult());
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
		ArrayList<String> happyZodiacLotteryResult = (ArrayList<String>)splitStrByLen(lotteryResult,2);
		info.setHappyZodiacLotteryResult(happyZodiacLotteryResult);
		return info;
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
			String [] missCountArr = missCount.split(",");
			for(int i = 0 ; i< missCountArr.length;i++){
				missCountResult.put("B-"+String.format("%1$02d",i+1), Integer.parseInt(missCountArr[i]));
			}
		}
		info.setMissCountResult(missCountResult);
		return info;
	}
	/*
	 * (�� Javadoc)
	*Title: lotteryPrize
	*Description: ��Ф�ֶҽ�
	* @param playType �淨id
	* @param betType Ͷע��ʽid
	* @param lotteryResult �������
	* @param winResult ������
	* @param betCode Ͷע�����ַ���
	* @throws LotteryUnDefineException û���ҵ��淨��û���ҵ�Ͷע��ʽ
	* @throws Exception  �����������쳣
	* @see com.success.lottery.util.core.LotteryInterf#lotteryPrize(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<List<String>> lotteryPrize(String playType, String betType,
			String lotteryResult, String winResult, String betCode)
			throws LotteryUnDefineException, Exception {
		if (HAPPY_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			return happyZodiacPrize(playType, betType, lotteryResult,
					winResult, betCode);
		} else {
			throw new LotteryUnDefineException("δ�ҵ��淨��" + playType);
		}
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
	public String lotterySplit(String playType, String betType, String betCode)
			throws LotteryUnDefineException, Exception {
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
		if (HAPPY_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			List<String> singleBet = Arrays.asList(splitGroup(betCode,HAPPY_BET_GROUP_SEPARATOR, false));
			if (HAPPY_BETTYPE_SINGLE.equals(betType)) {// ��ʽ
				toSingle = singleBet;
			} else if (HAPPY_BETTYPE_DUPLEX.equals(betType)) {// ��ʽ
				toSingle = duplexToSingle(singleBet);
			} else if (HAPPY_BETTYPE_DANTUO.equals(betType)) {// ����
				toSingle = danTuoToSingle(singleBet);
			} else {
				throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��" + betType);
			}
		} else {
			throw new LotteryUnDefineException("δ�ҵ��淨��" + playType);
		}
		return toSingle;
	}
	/**
	 * 
	 * Title: happyZodiacPrize
	 * Description:  ����Ф�ְ���Ͷע��ʽ�ҽ�
	 * @param playType �淨id
	 * @param betType Ͷע��ʽid
	 * @param lotteryResult �������
	 * @param winResult ������
	 * @param betCode Ͷע���봮
	 * @throws LotteryUnDefineException û���ҵ�Ͷע��ʽ
	 * @throws Exception    ��������ʱ�쳣
	 * @return List<List<String>>    ��������
	 * @throws
	 */
	private List<List<String>> happyZodiacPrize(String playType,String betType, String lotteryResult,
			String winResult, String betCode) throws LotteryUnDefineException,
			Exception {
		List<String> singleBet = Arrays.asList(splitGroup(betCode,HAPPY_BET_GROUP_SEPARATOR, false));
		if (HAPPY_BETTYPE_SINGLE.equals(betType)) {//��ʽ
			return singlePrize(playType,lotteryResult, winResult, singleBet);
		} else if (HAPPY_BETTYPE_DUPLEX.equals(betType)) {//��ʽ
			return duplexPrize(playType,lotteryResult,winResult,singleBet);
		} else if (HAPPY_BETTYPE_DANTUO.equals(betType)) {//����
			return danTuoPrize(playType,lotteryResult,winResult,singleBet);
		} else {
			throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��" + betType);
		}

	}
	/**
	 * 
	 * Title: singlePrize
	 * Description:  ��Ф�ֵ�ʽ�ҽ�
	 * @param playType �淨id
	 * @param lotteryResult �������
	 * @param winResult ������
	 * @param singleBet ������ע�ļ��ϣ��������а��������ע
	 * @return List<List<String>>    ��������
	 * @throws
	 */
	private List<List<String>> singlePrize(String playType,String lotteryResult, String winResult,List<String> singleBet){
		List<List<String>> prizeResult = new ArrayList<List<String>>();
		ArrayList<String> lotteryResultMap = splitLotteryResult(lotteryResult,new LotteryInfo()).getHappyZodiacLotteryResult();//����������
		String [] winResultMap = splitWinResult(winResult,new LotteryInfo()).getHappyZodiacWinResult();
		for(String single : singleBet){
			List<String> singleList = splitStrByLen(single,2);//������Ͷע����Ϊ�����б�
			String hitNum = hitHaoNum(singleList,lotteryResultMap);
			String zhongJangJiBie = winRules.get(hitNum);//��Ф��ֻ����2�����н�
			if(zhongJangJiBie != null){
				List<String> innerList = new ArrayList<String>();
				innerList.add(single);
				innerList.add(zhongJangJiBie);
				innerList.add(winResultMap[1]);
				innerList.add("0");
				prizeResult.add(innerList);
			}
		}
		return prizeResult;
	}
	
	/**
	 * 
	 * Title: duplexPrize
	 * Description:   ��ʽ�ҽ�
	 * @param playType
	 * @param lotteryResult
	 * @param winResult
	 * @param singleBet 010203^040506^08091112
	 * @return List<List<String>>    ��������
	 * @throws
	 */
	private List<List<String>> duplexPrize(String playType,String lotteryResult, String winResult,List<String> singleBet){
		List<String> duplexToSingle = duplexToSingle(singleBet);
		return singlePrize(playType,lotteryResult,winResult,duplexToSingle);
	}
	/**
	 * 
	 * Title: duplexToSingle<br>
	 * Description: <br>
	 *            ����ʽ��Ϊ��ʽ����<br>
	 * @param singleBet
	 * @return List<String>
	 */
	private List<String> duplexToSingle(List<String> singleBet){
		List<String> duplexToSingle = new ArrayList<String>();
		for(String single : singleBet){//�Զ�ע���δ���
			duplexToSingle.addAll(combineToList(splitStrByLen(single,2),2));
		}
		return duplexToSingle;
	}
	
	/**
	 * 
	 * Title: danTuoPrize
	 * Description:  ���϶ҽ�
	 * @param playType
	 * @param lotteryResult
	 * @param winResult
	 * @param singleBet 01*0203^03*0506
	 * @return List<List<String>>    ��������
	 * @throws
	 */
	private List<List<String>> danTuoPrize(String playType,String lotteryResult, String winResult,List<String> singleBet){
		List<String> danTuoToSingle = danTuoToSingle(singleBet);
		return singlePrize(playType, lotteryResult, winResult, danTuoToSingle);
	}
	/**
	 * 
	 * Title: danTuoToSingle<br>
	 * Description: <br>
	 *            �����ϲ�Υ��ʽ����<br>
	 * @param singleBet
	 * @return List<String>
	 */
	private List<String> danTuoToSingle(List<String> singleBet){
		 List<String> danTuoToSingle = new ArrayList<String>();
		for (String single : singleBet) {
			danTuoToSingle.addAll(combineDanTuo(single, 2, 2, "\\*"));
		}
		return danTuoToSingle;
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
		if(HAPPY_BETTYPE_SINGLE.equals(betType)){
			return checkSingle(betString);
		}else if(HAPPY_BETTYPE_DUPLEX.equals(betType)){
			return checkDuplex(betString);
		}else if(HAPPY_BETTYPE_DANTUO.equals(betType)){
			return checkDanTuo(betString);
		}else {
			throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��"+betType);
		}
	}
	
	/**
	 * 
	 * Title: checkSingle
	 * Description:  У�鵥ʽ ������ʽ��Ͷע����ע��
	 * @param betString
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkSingle(String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,HAPPY_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(HAPPY_BET_0_REGULAR,group)){
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
		String [] betGroupByNum = splitGroup(betString,HAPPY_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(HAPPY_BET_1_REGULAR,group)){
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
		String [] betGroupByNum = splitGroup(betString,HAPPY_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(HAPPY_BET_2_REGULAR,group)){//��ʽ���ϸ�
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
	 * Description:  У�鵨�ϵ���λ��,���������>=3
	 * @param target
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkDanTuoTotalNum(String target){
		boolean checkResult = true;
		String [] DanTuoGroup = target.split("\\*");//�˴��Ѿ�����У�飬������������룬��������Ϊ2��ֵ
		List<String> dan = splitStrByLen(DanTuoGroup[0],2);
		List<String> tuo = splitStrByLen(DanTuoGroup[1],2);
		if((dan.size() + tuo.size()) < 3){
			checkResult = false;
			return checkResult;
		}
		return checkResult;
	}

	
	public String lotteryRandomBetCode(){
		String[] blues = {
			"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
			"11", "12"};
		Random random = new Random();
		int i = 0;
		String result = "";
		while(i < 2){
			int pos = random.nextInt(12);
			if(result.indexOf(blues[pos]) > -1){
				continue;
			} else {
				result = result + blues[pos];
				i++;
			}
		}
		return result;
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
		if(lotteryResult == null || lotteryResult.length() != 4){
			return null;
		}
		int v1 = Integer.parseInt(lotteryResult.substring(0, 2)) - 1;
		int v2 = Integer.parseInt(lotteryResult.substring(2, 4)) - 1;

		int digit[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
 		if(StringUtils.isBlank(lastMissCount)){
			for(int i = 0; i < 12; i++){
				if(i == v1 || i == v2){
					digit[i] = 0;
				} else {
					digit[i]++;
				}
			}
 		} else {
 			String[] ss = lastMissCount.split(",");
			if(ss == null || ss.length != 12){
				return null;
			}
			for(int i = 0; i < 12; i++){
				if(i == v1 || i == v2){
					digit[i] = 0;
				} else {
					digit[i] = Integer.parseInt(ss[i]);
					digit[i]++;
				}
			}
 		}
 		String result = "";
 		for(int i = 0; i < 12; i++){
 			result = result + digit[i] + ",";
 		}
 		result = result.substring(0, result.length() - 1);
		return result;

	}

	public static void main(String[] args) {
		HappyZodiac zodiac = new HappyZodiac();
		String betCode = zodiac.lotteryRandomBetCode();
		System.out.println(betCode);
		String lastMissCount = zodiac.getMissCount(betCode, null);
		System.out.println(lastMissCount);
		for(int i = 0; i < 500; i++){
			betCode = zodiac.lotteryRandomBetCode();
			lastMissCount = zodiac.getMissCount(betCode, lastMissCount);
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
