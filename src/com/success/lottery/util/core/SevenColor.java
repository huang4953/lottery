/**
 * Title: SevenColor.java
 * @Package com.success.lottery.util.core
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-3-27 ����11:20:49
 * @version V1.0
 */
package com.success.lottery.util.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;

import com.success.lottery.util.LotteryInfo;
/**
 * 
 * com.success.lottery.util.core
 * SevenColor.java
 * SevenColor
 * ���ǲʴ�����
 * @author gaoboqin
 * 2010-3-27 ����11:20:49
 *
 */
public class SevenColor extends LotteryAbstractTool implements LotteryInterf {

	/*
	 * �����н�����
	 */
	private static Map<String,String> winRules;
	private static final int PIPEILEN = 7;//���ǲ�ֻ��7λ��������
	
	static {
		winRules = new HashMap<String,String>();
		winRules.put("7", "1");//1�Ƚ�
		winRules.put("6", "2");//2�Ƚ�
		winRules.put("5", "3");//3�Ƚ�
		winRules.put("4", "4");//4�Ƚ�
		winRules.put("3", "5");//5�Ƚ�
		winRules.put("2", "6");//6�Ƚ�
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
		if (COLOR_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			return checkBet(betType, betString);
		}else {
			throw new LotteryUnDefineException("δ�ҵ��淨��"+playType);
		}
	}
	/**
	 * 
	 * Title: checkBet
	 * Description:  ����Ͷע��ʽУ��Ͷע���봮����ȷ��
	 * @param betType
	 * @param betString
	 * @throws LotteryUnDefineException
	 * @throws Exception    �趨�ļ�
	 * @return boolean    ��������
	 * @throws
	 */
	private boolean checkBet(String betType,String betString) throws LotteryUnDefineException,Exception{
		if(COLOR_BETTYPE_SINGLE.equals(betType)){
			return checkSingle(betString);
		}else if(COLOR_BETTYPE_DUPLEX.equals(betType)){
			return checkDuplex(betString);
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
		String [] betGroupByNum = splitGroup(betString,COLOR_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(COLOR_BET_0_REGULAR,group)){
				checkResult = false;
				break;
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
		String [] betGroupByNum = splitGroup(betString,COLOR_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(COLOR_BET_1_REGULAR,group)){
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
		if (COLOR_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			List<String> singleBet = Arrays.asList(splitGroup(betCode,COLOR_BET_GROUP_SEPARATOR, false));
			if (COLOR_BETTYPE_SINGLE.equals(betType)) {
				toSingle = singleBet;
			} else if (COLOR_BETTYPE_DUPLEX.equals(betType)) {
				toSingle = combineDuplex(betCode,COLOR_BET_GROUP_SEPARATOR,"\\*",1);
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
	public List<List<String>> lotteryPrize(String playType, String betType, String lotteryResult, String winReult, String betCode) 
							throws LotteryUnDefineException, Exception{
		if (COLOR_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			return sevenColorPrize(playType,betType,lotteryResult,winReult,betCode);
		} else {
			throw new LotteryUnDefineException("δ�ҵ��淨��" + playType);
		}
	}
	
	/*
	 * (�� Javadoc)
	*Title: mergeWinResult
	*Description: 
	* @param info
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#mergeWinResult(com.success.lottery.util.LotteryInfo)
	* 1-1&5000000|2-25&18489
	 */
	public String mergeWinResult(LotteryInfo info) {
		TreeMap<String,String[]> sevenColorWinResult = info.getSevenColorWinResult();
		StringBuffer result = new StringBuffer();
		boolean flag = false;
		for(Map.Entry<String, String[]> entry : sevenColorWinResult.entrySet()){
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
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#splitWinResult(java.lang.String)
	* 1-1&5000000|2-25&18489
	 */
	public LotteryInfo splitWinResult(String winResult,LotteryInfo info) {
		TreeMap<String,String[]> sevenColorWinResult = new TreeMap<String,String[]>();
		String [] groupArr = winResult.split("\\|");
		for(String group : groupArr){
			String [] oneGroup = group.split("-");
			if(oneGroup.length == 2){
				String key = oneGroup[0];
				String [] value = oneGroup[1].split("&");
				sevenColorWinResult.put(key, value);
			}
		}
		info.setSevenColorWinResult(sevenColorWinResult);
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
		String result = mergeListToStr(info.getSevenColorLotteryResult());
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
		ArrayList<String> sevenColorLotteryResult = (ArrayList<String>)splitStrByLen(lotteryResult,1);
		info.setSevenColorLotteryResult(sevenColorLotteryResult);
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
			String [] groups = missCount.split(";");
			for(int i = 0;i< groups.length;i++){
				String [] oneGroup = groups[i].split(",");
				for(int k = 0;k< oneGroup.length;k++){
					missCountResult.put("P-"+ String.valueOf(i+1) + "-"+String.valueOf(k), Integer.parseInt(oneGroup[k]));
				}
			}
		}
		info.setMissCountResult(missCountResult);
		return info;
	}
	
	/**
	 * 
	 * Title: sevenColorPrize
	 * Description:  �����ǲʰ���Ͷע��ʽ����
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
	private List<List<String>> sevenColorPrize(String playType,String betType, String lotteryResult,
			String winResult, String betCode) throws LotteryUnDefineException,
			Exception {
		if (COLOR_BETTYPE_SINGLE.equals(betType)) {
			List<String> singleBet = Arrays.asList(splitGroup(betCode,COLOR_BET_GROUP_SEPARATOR, false));
			return singlePrize(lotteryResult, winResult, singleBet);
		} else if (COLOR_BETTYPE_DUPLEX.equals(betType)) {
			return duplexPrize(lotteryResult,winResult,betCode);
		} else {
			throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��" + betType);
		}
	}
	
	/**
	 * 
	 * Title: singlePrize
	 * Description:  
	 * @param playType
	 * @param lotteryResult ArrayList<String> ������Ϊ<����>��Ӧ�ð���7������
	 * @param winResult TreeMap<String,String[]> �����ֱ�Ϊ:<һ�Ƚ������Ƚ�,[ע��,���]>
	 * @param singleBet Ͷע����ļ��� ������ע
	 * @return List<List<String>>    ��������
	 * @throws
	 */
	private List<List<String>> singlePrize(String lotteryResult, String winResult,List<String> singleBet){
		List<List<String>> prizeResult = null;// = new ArrayList<List<String>>();
		ArrayList<String> lotteryResultMap = splitLotteryResult(lotteryResult,new LotteryInfo()).getSevenColorLotteryResult();//����������
		TreeMap<String,String[]> winResultMap = splitWinResult(winResult,new LotteryInfo()).getSevenColorWinResult();//������
		prizeResult = hitHaoMa(singleBet,lotteryResultMap,winResultMap);
		return prizeResult;
	}
	/**
	 * 
	 * Title: duplexPrize
	 * Description:  ��ʽ�ҽ����Ȳ�Ϊ��ʽ���ٰ��յ�ʽ�ҽ�
	 * @param lotteryResult
	 * @param winResult
	 * @param betCode
	 * @return List<List<String>>    ��������
	 * @throws
	 */
	private List<List<String>> duplexPrize(String lotteryResult, String winResult,String betCode){
		List<String> duplexToSingle = combineDuplex(betCode,COLOR_BET_GROUP_SEPARATOR,"\\*",1);
		return singlePrize(lotteryResult,winResult,duplexToSingle);
	}
	
	/**
	 * 
	 * Title: hitHaoMa
	 * Description:  ����ʽͶע���϶ҽ������صļ���ֻ�����н��ĺ����Լ��н��ļ���ͽ���
	 *                       Ҫ��֤lotteryResult���ϵ�˳������
	 * @param playType
	 * @param singleBet
	 * @param lotteryResultMap 1234567
	 * @param winResultMap 1-1&5000000|2-25&18489 TreeMap<String,String[]> �����ֱ�Ϊ:<һ�Ƚ������Ƚ�,[ע��,���]>
	 * @return List<List<String>>    ��������
	 * @throws
	 */
	private List<List<String>> hitHaoMa(List<String> singleBet,ArrayList<String> lotteryResult,
			TreeMap<String,String[]> winResultMap){
		//Map<String,String> result = new HashMap<String,String>();
		List<List<String>> result = new ArrayList<List<String>>();
        for(String single : singleBet){//��ÿһע���д���
        	List<String> singleArr = Arrays.asList(single.split("\\*"));//��ÿע�ĺ�����Ϊ���飬Ҫ����˳��ÿ������Ԫ��Ϊһ������
			String hitNum = this.hitHaoNum(lotteryResult,singleArr,PIPEILEN);//����ƥ���ϵĺ������
			String zhongJangJiBie = winRules.get(hitNum);//�н��ȼ�
			if(zhongJangJiBie != null){
				List<String> innerList = new ArrayList<String>();
				innerList.add(single);
				innerList.add(zhongJangJiBie);
				String jinEr = (winResultMap.get(zhongJangJiBie))[1];//ȡ������
				innerList.add(jinEr);
				String addJinEr = "0";//��֤���з��صļ���ͳһ��׷�������ʹ��ʱ����Խ��
				innerList.add(addJinEr);
				result.add(innerList);
			}
			
		}
		return result;
	}
	/**
	 * 
	 * Title: hitHaoNum
	 * Description:  ��������������еĴ���
	 * @param lotteryResult �������
	 * @param singleBetArr ��ע����
	 * @param piPeiLen ƥ�䳤�ȣ���ֹ����Խ��
	 * @return String    ����ƥ���������
	 * @throws
	 */
	private String hitHaoNum(List<String> lotteryResult,List<String> singleBetArr,int piPeiLen){
		Stack<String> singleStack = new Stack<String>();//����ջ�������������ƥ���ϣ�����ջ�������ƥ�����ջ
		int max = 0;
		if(lotteryResult.size() != piPeiLen || singleBetArr.size() != piPeiLen){//��ֹ����Խ��
			return null;
		}
		
		for(int k = 0; k < piPeiLen;k++){
			if(lotteryResult.get(k).equals(singleBetArr.get(k))){
				singleStack.push(singleBetArr.get(k));
				max = max < singleStack.size()?singleStack.size():max;
			}else{
				if(!singleStack.empty()){
					singleStack.clear();
				}
			}
		}
		return String.valueOf(max);
	}
	
	public String lotteryRandomBetCode(){
		String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		String result = "";
		Random random = new Random();
		result = random.nextInt(10) + "*";
		result = result + random.nextInt(10) + "*";
		result = result + random.nextInt(10) + "*";
		result = result + random.nextInt(10) + "*";
		result = result + random.nextInt(10) + "*";
		result = result + random.nextInt(10) + "*";
		result = result + random.nextInt(10);
		return result;
	}
	public String mergeSalesInfoResult(LotteryInfo info) {
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
		if(lotteryResult == null || lotteryResult.length() != 7){
			return null;
		}
		int v1000000 = Integer.parseInt(lotteryResult.substring(0, 1));
		int v100000 = Integer.parseInt(lotteryResult.substring(1, 2));
		int v10000 = Integer.parseInt(lotteryResult.substring(2, 3));
		int v1000 = Integer.parseInt(lotteryResult.substring(3, 4));
		int v100 = Integer.parseInt(lotteryResult.substring(4, 5));
		int v10 = Integer.parseInt(lotteryResult.substring(5, 6));
		int v1 = Integer.parseInt(lotteryResult.substring(6, 7));
		
		int digit1000000[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int digit100000[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int digit10000[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int digit1000[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int digit100[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int digit10[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int digit1[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
 		if(StringUtils.isBlank(lastMissCount)){
			for(int i = 0; i < 10; i++){
				if(i == v1000000){
					digit1000000[i] = 0;
				}else{
					digit1000000[i]++;
				}
			}
 			for(int i = 0; i < 10; i++){
				if(i == v100000){
					digit100000[i] = 0;
				}else{
					digit100000[i]++;
				}
			}
 			for(int i = 0; i < 10; i++){
				if(i == v10000){
					digit10000[i] = 0;
				}else{
					digit10000[i]++;
				}
			}
 			for(int i = 0; i < 10; i++){
				if(i == v1000){
					digit1000[i] = 0;
				}else{
					digit1000[i]++;
				}
			}
 			for(int i = 0; i < 10; i++){
				if(i == v100){
					digit100[i] = 0;
				}else{
					digit100[i]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v10){
					digit10[i] = 0;
				}else{
					digit10[i]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v1){
					digit1[i] = 0;
				}else{
					digit1[i]++;
				}
			}
		}else{
			String[] ss = lastMissCount.split(";");
			if(ss == null || ss.length != 7){
				return null;
			}
			for(int i = 0; i < 7; i++){
				if(ss[i].split(",") == null || ss[i].split(",").length != 10){
					return null;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v1000000){
					digit1000000[i] = 0;
				}else{
					digit1000000[i] = Integer.parseInt(ss[0].split(",")[i]);
					digit1000000[i]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v100000){
					digit100000[i] = 0;
				}else{
					digit100000[i] = Integer.parseInt(ss[1].split(",")[i]);
					digit100000[i]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v10000){
					digit10000[i] = 0;
				}else{
					digit10000[i] = Integer.parseInt(ss[2].split(",")[i]);
					digit10000[i]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v1000){
					digit1000[i] = 0;
				}else{
					digit1000[i] = Integer.parseInt(ss[3].split(",")[i]);
					digit1000[i]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v100){
					digit100[i] = 0;
				}else{
					digit100[i] = Integer.parseInt(ss[4].split(",")[i]);
					digit100[i]++;
				}
			}	
			for(int i = 0; i < 10; i++){
				if(i == v10){
					digit10[i] = 0;
				}else{
					digit10[i] = Integer.parseInt(ss[5].split(",")[i]);
					digit10[i]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v1){
					digit1[i] = 0;
				}else{
					digit1[i] = Integer.parseInt(ss[6].split(",")[i]);
					digit1[i]++;
				}
			}	
		}
 		String result = "";
 		for(int i = 0; i < 10; i++){
 			result = result + digit1000000[i] + ",";
 		}
 		result = result.substring(0, result.length() - 1) + ";";
 		for(int i = 0; i < 10; i++){
 			result = result + digit100000[i] + ",";
 		}
 		result = result.substring(0, result.length() - 1) + ";";
 		for(int i = 0; i < 10; i++){
 			result = result + digit10000[i] + ",";
 		}
 		result = result.substring(0, result.length() - 1) + ";";
 		for(int i = 0; i < 10; i++){
 			result = result + digit1000[i] + ",";
 		}
 		result = result.substring(0, result.length() - 1) + ";";
 		for(int i = 0; i < 10; i++){
 			result = result + digit100[i] + ",";
 		}
 		result = result.substring(0, result.length() - 1) + ";";
 		for(int i = 0; i < 10; i++){
 			result = result + digit10[i] + ",";
 		}
 		result = result.substring(0, result.length() - 1) + ";";
 		for(int i = 0; i < 10; i++){
 			result = result + digit1[i] + ",";
 		}
 		result = result.substring(0, result.length() - 1);

 		return result;
	}
	public static void main(String[] args) {		
		SevenColor s7 = new SevenColor();
		String betCode = s7.lotteryRandomBetCode().replace("*", "");
		System.out.println(betCode);
		String lastMissCount = s7.getMissCount(betCode, null);
		System.out.println(lastMissCount);
		for(int i = 0; i < 50; i++){
			betCode = s7.lotteryRandomBetCode().replace("*", "");
			lastMissCount = s7.getMissCount(betCode, lastMissCount);
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
