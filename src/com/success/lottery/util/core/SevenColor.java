/**
 * Title: SevenColor.java
 * @Package com.success.lottery.util.core
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-3-27 下午11:20:49
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
 * 七星彩处理类
 * @author gaoboqin
 * 2010-3-27 下午11:20:49
 *
 */
public class SevenColor extends LotteryAbstractTool implements LotteryInterf {

	/*
	 * 定义中奖条件
	 */
	private static Map<String,String> winRules;
	private static final int PIPEILEN = 7;//七星彩只有7位开奖号码
	
	static {
		winRules = new HashMap<String,String>();
		winRules.put("7", "1");//1等奖
		winRules.put("6", "2");//2等奖
		winRules.put("5", "3");//3等奖
		winRules.put("4", "4");//4等奖
		winRules.put("3", "5");//5等奖
		winRules.put("2", "6");//6等奖
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
		if (COLOR_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			return checkBet(betType, betString);
		}else {
			throw new LotteryUnDefineException("未找到玩法："+playType);
		}
	}
	/**
	 * 
	 * Title: checkBet
	 * Description:  按照投注方式校验投注号码串的正确性
	 * @param betType
	 * @param betString
	 * @throws LotteryUnDefineException
	 * @throws Exception    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	private boolean checkBet(String betType,String betString) throws LotteryUnDefineException,Exception{
		if(COLOR_BETTYPE_SINGLE.equals(betType)){
			return checkSingle(betString);
		}else if(COLOR_BETTYPE_DUPLEX.equals(betType)){
			return checkDuplex(betString);
		}else {
			throw new LotteryUnDefineException("未找到投注方式："+betType);
		}
	}
	
	/**
	 * 
	 * Title: checkSingle
	 * Description:  校验单式 包括格式的投注的总注数
	 * @param betString
	 * @return boolean    返回类型
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
	 * Description:  校验复式投注
	 * @param betString
	 * @return boolean    返回类型
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
		long zhuNum = lotteryToSingle(playType,betType,betCode).size();
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
		if (COLOR_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			List<String> singleBet = Arrays.asList(splitGroup(betCode,COLOR_BET_GROUP_SEPARATOR, false));
			if (COLOR_BETTYPE_SINGLE.equals(betType)) {
				toSingle = singleBet;
			} else if (COLOR_BETTYPE_DUPLEX.equals(betType)) {
				toSingle = combineDuplex(betCode,COLOR_BET_GROUP_SEPARATOR,"\\*",1);
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
	public List<List<String>> lotteryPrize(String playType, String betType, String lotteryResult, String winReult, String betCode) 
							throws LotteryUnDefineException, Exception{
		if (COLOR_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			return sevenColorPrize(playType,betType,lotteryResult,winReult,betCode);
		} else {
			throw new LotteryUnDefineException("未找到玩法：" + playType);
		}
	}
	
	/*
	 * (非 Javadoc)
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
	 * (非 Javadoc)
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
	 * (非 Javadoc)
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
	 * (非 Javadoc)
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
	 * (非 Javadoc)
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
	 * Description:  对七星彩按照投注方式开奖
	 * @param playType
	 * @param betType
	 * @param lotteryResult
	 * @param winResult
	 * @param betCode
	 * @throws LotteryUnDefineException
	 * @throws Exception    设定文件
	 * @return List<List<String>>    返回类型
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
			throw new LotteryUnDefineException("未找到投注方式：" + betType);
		}
	}
	
	/**
	 * 
	 * Title: singlePrize
	 * Description:  
	 * @param playType
	 * @param lotteryResult ArrayList<String> ，参数为<号码>，应该包含7个号码
	 * @param winResult TreeMap<String,String[]> 参数分别为:<一等奖到六等奖,[注数,金额]>
	 * @param singleBet 投注号码的集合 包含多注
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> singlePrize(String lotteryResult, String winResult,List<String> singleBet){
		List<List<String>> prizeResult = null;// = new ArrayList<List<String>>();
		ArrayList<String> lotteryResultMap = splitLotteryResult(lotteryResult,new LotteryInfo()).getSevenColorLotteryResult();//开奖号码结果
		TreeMap<String,String[]> winResultMap = splitWinResult(winResult,new LotteryInfo()).getSevenColorWinResult();//奖金结果
		prizeResult = hitHaoMa(singleBet,lotteryResultMap,winResultMap);
		return prizeResult;
	}
	/**
	 * 
	 * Title: duplexPrize
	 * Description:  复式兑奖，先拆为单式，再按照单式兑奖
	 * @param lotteryResult
	 * @param winResult
	 * @param betCode
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> duplexPrize(String lotteryResult, String winResult,String betCode){
		List<String> duplexToSingle = combineDuplex(betCode,COLOR_BET_GROUP_SEPARATOR,"\\*",1);
		return singlePrize(lotteryResult,winResult,duplexToSingle);
	}
	
	/**
	 * 
	 * Title: hitHaoMa
	 * Description:  将单式投注集合兑奖，返回的集合只包含中奖的号码以及中奖的级别和奖金
	 *                       要保证lotteryResult集合的顺序不能乱
	 * @param playType
	 * @param singleBet
	 * @param lotteryResultMap 1234567
	 * @param winResultMap 1-1&5000000|2-25&18489 TreeMap<String,String[]> 参数分别为:<一等奖到六等奖,[注数,金额]>
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> hitHaoMa(List<String> singleBet,ArrayList<String> lotteryResult,
			TreeMap<String,String[]> winResultMap){
		//Map<String,String> result = new HashMap<String,String>();
		List<List<String>> result = new ArrayList<List<String>>();
        for(String single : singleBet){//对每一注进行处理
        	List<String> singleArr = Arrays.asList(single.split("\\*"));//将每注的号码拆分为数组，要按照顺序，每个数组元素为一个号码
			String hitNum = this.hitHaoNum(lotteryResult,singleArr,PIPEILEN);//连续匹配上的号码个数
			String zhongJangJiBie = winRules.get(hitNum);//中奖等级
			if(zhongJangJiBie != null){
				List<String> innerList = new ArrayList<String>();
				innerList.add(single);
				innerList.add(zhongJangJiBie);
				String jinEr = (winResultMap.get(zhongJangJiBie))[1];//取基本奖
				innerList.add(jinEr);
				String addJinEr = "0";//保证所有返回的集合统一有追加项，避免使用时数组越界
				innerList.add(addJinEr);
				result.add(innerList);
			}
			
		}
		return result;
	}
	/**
	 * 
	 * Title: hitHaoNum
	 * Description:  计算号码连续命中的次数
	 * @param lotteryResult 开奖结果
	 * @param singleBetArr 单注集合
	 * @param piPeiLen 匹配长度，防止数组越界
	 * @return String    连续匹配的最大个数
	 * @throws
	 */
	private String hitHaoNum(List<String> lotteryResult,List<String> singleBetArr,int piPeiLen){
		Stack<String> singleStack = new Stack<String>();//定义栈，如果单个号码匹配上，则入栈，如果不匹配则出栈
		int max = 0;
		if(lotteryResult.size() != piPeiLen || singleBetArr.size() != piPeiLen){//防止数组越界
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
	 * (非 Javadoc)
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
