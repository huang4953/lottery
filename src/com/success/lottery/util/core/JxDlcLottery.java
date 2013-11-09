/**
 * Title: JxDlcLottery.java
 * @Package com.success.lottery.util.core
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-3-29 上午09:57:28
 * @version V1.0
 */
package com.success.lottery.util.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.success.lottery.util.LotteryInfo;
/**
 * 
 * com.success.lottery.util.core
 * JxDlcLottery.java
 * JxDlcLottery
 * 十一运处理类
 * @author gaoboqin
 * 2010-3-29 上午09:57:28
 *
 */
public class JxDlcLottery extends LotteryAbstractTool implements
		LotteryInterf {
	
	private static List<String> allPlayList;//包含所有的玩法
	private static Map<String,Map<String,String>> simpleBetRule;//包含能按照配置的规则简单校验的规则
	private static Map<String,Map<String,String>> dabTuoBetRule;//校验胆拖,
	private static Map<String,Map<String,String>> dingWeiBetRule;//校验定位复式
	private static Map<String,Integer> danTuoNum;//定义胆拖必须满足的胆码和拖码的个数
	private static Map<String,String> winRules;//奖金规则定义
	
	static {
		allPlayList = setBindList();
		
		simpleBetRule = setBindSimpleBetRule();
		dabTuoBetRule = setBindDanTuoRule();
	    dingWeiBetRule = setBindDingWei();
		danTuoNum = setDanTuoNum();
		
		winRules = new HashMap<String,String>();
		winRules.put("1-1", "1");//任选一中1：投注的1个号码与当期顺序摇出的5个号码中的第1个号码相同，则中奖。
		winRules.put("2-2", "2");//任选二中2：投注的2个号码与当期摇出的5个号码中的任2个号码相同，则中奖。
		winRules.put("3-3", "3");//任选三中3：投注的3个号码与当期摇出的5个号码中的任3个号码相同，则中奖。
		winRules.put("4-4", "4");//任选四中4：投注的4个号码与当期摇出的5个号码中的任4个号码相同，则中奖。
		winRules.put("5-5", "5");//任选五中5：投注的5个号码与当期摇出的5个号码相同，则中奖。
		winRules.put("6-5", "6");//任选六中5：投注的6个号码中任5个号码与当期摇出的5个号码相同，则中奖。
		winRules.put("7-5", "7");//任选七中5：投注的7个号码中任5个号码与当期摇出的5个号码相同，则中奖。
		winRules.put("8-5", "8");//任选八中5：投注的8个号码中任5个号码与当期摇出的5个号码相同，则中奖。
		winRules.put("9-2", "9");//选前二直选：投注的2个号码与当期顺序摇出的5个号码中的前2个号码相同且顺序一致，则中奖。
		winRules.put("10-3", "10");//选前三直选：投注的3个号码与当期顺序摇出的5个号码中的前3个号码相同且顺序一致，则中奖。
		winRules.put("11-2", "11");//选前二组选：投注的2个号码与当期顺序摇出的5个号码中的前2个号码相同，则中奖。
		winRules.put("12-3", "12");//选前三组选：投注的3个号码与当期顺序摇出的5个号码中的前3个号码相同，则中奖。
	}
	/**
	 *Title: 
	 *Description: 
	 */
	public JxDlcLottery() {
		super();
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
		if (allPlayList.contains(playType)) {
			return checkBet(playType,betType, betString);
		} else {
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
		LinkedHashMap<String,String[]> elevenYunWinResult = info.getElevenYunWinResult();
		StringBuffer result = new StringBuffer();
		boolean flag = false;
		for(Map.Entry<String, String[]> entry : elevenYunWinResult.entrySet()){
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
	*Description: 拆分奖金结果
	* @param winResult
	* @param info
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#splitWinResult(java.lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitWinResult(String winResult,LotteryInfo info) {
		LinkedHashMap<String,String[]> elevenYunWinResult = new LinkedHashMap<String,String[]>();
		String [] groupArr = winResult.split("\\|");
		for(String group : groupArr){
			String [] oneGroup = group.split("-");
			if(oneGroup.length == 2){
				String key = oneGroup[0];
				String [] value = oneGroup[1].split("&");
				elevenYunWinResult.put(key, value);
			}
		}
		info.setElevenYunWinResult(elevenYunWinResult);
		return info;
	}
	/*
	 * (非 Javadoc)
	*Title: mergeLotteryResult
	*Description: 合并奖金结果
	* @param info
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#mergeLotteryResult(com.success.lottery.util.LotteryInfo)
	 */
	public String mergeLotteryResult(LotteryInfo info) {
		String result = mergeListToStr(info.getElevenYunLotteryResult());
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: splitLotteryResult
	*Description: 拆分开奖结果
	* @param lotteryResult
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#splitLotteryResult(java.lang.String)
	 */
	public LotteryInfo splitLotteryResult(String lotteryResult,LotteryInfo info) {
		ArrayList<String> elevenYunLotteryResult = (ArrayList<String>)splitStrByLen(lotteryResult,2);
		info.setElevenYunLotteryResult(elevenYunLotteryResult);
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
		List<String> singleBet = Arrays.asList(splitGroup(betCode,ELEVEN_BET_GROUP_SEPARATOR, false));
		if (ELEVEN_PLAYTYPE_1.equals(playType)) {
			toSingle = elevenArbitraryToSingle(betType,singleBet,0);
		}else if(ELEVEN_PLAYTYPE_2.equals(playType)){
			toSingle = elevenArbitraryToSingle(betType,singleBet,2);
		}else if(ELEVEN_PLAYTYPE_3.equals(playType)){
			toSingle = elevenArbitraryToSingle(betType,singleBet,3);
		}else if(ELEVEN_PLAYTYPE_4.equals(playType)){
			toSingle = elevenArbitraryToSingle(betType,singleBet,4);
		}else if(ELEVEN_PLAYTYPE_5.equals(playType)){
			toSingle = elevenArbitraryToSingle(betType,singleBet,5);
		}else if(ELEVEN_PLAYTYPE_6.equals(playType)){
			toSingle = elevenArbitraryToSingle(betType,singleBet,6);
		}else if(ELEVEN_PLAYTYPE_7.equals(playType)){
			toSingle = elevenArbitraryToSingle(betType,singleBet,7);
		}else if(ELEVEN_PLAYTYPE_8.equals(playType)){
			toSingle = elevenArbitraryToSingle(betType,singleBet,8);
		}else if(ELEVEN_PLAYTYPE_9.equals(playType)){
			toSingle = beforeDirectToSingle(betType,singleBet,betCode,2,2);
		}else if(ELEVEN_PLAYTYPE_10.equals(playType)){
			toSingle = beforeDirectToSingle(betType,singleBet,betCode,3,3);
		}else if(ELEVEN_PLAYTYPE_11.equals(playType)){
			toSingle = beforeGroupToSingle(betType,singleBet,2);
		}else if(ELEVEN_PLAYTYPE_12.equals(playType)){
			toSingle = beforeGroupToSingle(betType,singleBet,3);
		}else {
			throw new LotteryUnDefineException("未找到玩法：" + playType);
		}
		return toSingle;
	}
	/*
	 * (非 Javadoc)
	*Title: lotteryPrize
	*Description:  十一运夺金兑奖
	* @param playType 玩法id
	* @param betType 投注方式id
	* @param lotteryResult 开奖结果字符串
	* @param winResult 奖金结果字符串
	* @param betCode 投注字符串
	* @return List<List<String>> 中奖结果
	* @throws LotteryUnDefineException 未找到玩法或投注方式
	* @throws Exception
	* @see com.success.lottery.util.core.LotteryInterf#lotteryPrize(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<List<String>> lotteryPrize(String playType, String betType,
			String lotteryResult, String winResult, String betCode)
			throws LotteryUnDefineException, Exception {
		List<String> singleBet = Arrays.asList(splitGroup(betCode,ELEVEN_BET_GROUP_SEPARATOR, false));
		ArrayList<String> lotteryResultMap = splitLotteryResult(lotteryResult,new LotteryInfo()).getElevenYunLotteryResult();//开奖号码结果
		LinkedHashMap<String,String[]>  winResultMap = splitWinResult(winResult,new LotteryInfo()).getElevenYunWinResult();//奖金结果
		if (ELEVEN_PLAYTYPE_1.equals(playType)) {
			return prizeByPosition("1-1",lotteryResult, winResultMap, singleBet, 1, true);
		}else if(ELEVEN_PLAYTYPE_2.equals(playType)){
			return elevenArbitraryPrize("2-2",betType,lotteryResultMap,winResultMap, singleBet,2,2);
		}else if(ELEVEN_PLAYTYPE_3.equals(playType)){
			return elevenArbitraryPrize("3-3",betType,lotteryResultMap,winResultMap, singleBet,3,3);
		}else if(ELEVEN_PLAYTYPE_4.equals(playType)){
			return elevenArbitraryPrize("4-4",betType,lotteryResultMap,winResultMap, singleBet,4,4);
		}else if(ELEVEN_PLAYTYPE_5.equals(playType)){
			return elevenArbitraryPrize("5-5",betType,lotteryResultMap,winResultMap, singleBet,5,5);
		}else if(ELEVEN_PLAYTYPE_6.equals(playType)){
			return elevenArbitraryPrize("6-5",betType,lotteryResultMap,winResultMap, singleBet,5,6);
		}else if(ELEVEN_PLAYTYPE_7.equals(playType)){
			return elevenArbitraryPrize("7-5",betType,lotteryResultMap,winResultMap, singleBet,5,7);
		}else if(ELEVEN_PLAYTYPE_8.equals(playType)){
			return elevenArbitraryPrize("8-5",betType,lotteryResultMap,winResultMap, singleBet,5,8);
		}else if(ELEVEN_PLAYTYPE_9.equals(playType)){
			return beforeDirectPrize("9-2",betType,lotteryResult,winResultMap, singleBet,betCode,2,2, true);
		}else if(ELEVEN_PLAYTYPE_10.equals(playType)){
			return beforeDirectPrize("10-3",betType,lotteryResult,winResultMap, singleBet,betCode,3,3, true);
		}else if(ELEVEN_PLAYTYPE_11.equals(playType)){
			return beforeGroupPrize("11-2",betType,lotteryResult, winResultMap, singleBet, 2, 2);
		}else if(ELEVEN_PLAYTYPE_12.equals(playType)){
			return beforeGroupPrize("12-3",betType,lotteryResult, winResultMap, singleBet, 3, 3);
		}else {
			throw new LotteryUnDefineException("未找到玩法：" + playType);
		}
	}
	/*
	 * (非 Javadoc)
	 *Title: lotteryRandomBetCode
	 *Description: 
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#lotteryRandomBetCode()
	 */
	public String lotteryRandomBetCode(){
		//  Auto-generated method stub
		return null;
	}
	/*
	 * (非 Javadoc)
	 *Title: mergeSalesInfoResult
	 *Description: 没有销售信息，不需要实现
	 * @param info
	 * @return
	 * @see com.success.lottery.util.core.LotteryInterf#mergeSalesInfoResult(com.success.lottery.util.LotteryInfo)
	 */
	public String mergeSalesInfoResult(LotteryInfo info) {
		// TODO 自动生成方法存根
		return null;
	}
	/*
	 * (非 Javadoc)
	*Title: splitSalesInfoResult
	*Description: 没有销售信息，不需要实现
	* @param salesInfo
	* @param info
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#splitSalesInfoResult(java.lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitSalesInfoResult(String salesInfo, LotteryInfo info) {
		return info;
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
	@Override
	public String getMissCount(String lotteryResult, String lastMissCount){
		if(lotteryResult == null || lotteryResult.length() != 10){
			return null;
		}
		String result = "";
		String [] lastMissCountType = StringUtils.isEmpty(lastMissCount) ? new String[12] : lastMissCount.split("\\|") ;
		
		int h1 = Integer.parseInt(lotteryResult.substring(0, 2)) - 1;
		int h2 = Integer.parseInt(lotteryResult.substring(2, 4)) - 1;
		int h3 = Integer.parseInt(lotteryResult.substring(4, 6)) - 1;
		
		//计算任选2到任选8遗漏
		String missR = this.getMissCountRQ(lotteryResult, lastMissCountType[0],"R");
		if(missR != null){
			result = missR + "|";
		}else{
			return null;
		}
		
		//计算第一位遗漏
		String missZ1 = this.getMissCountZ(h1, lastMissCountType[1]);
		if(missZ1 != null){
			result = result + missZ1 + "|";
		}else{
			return null;
		}
		//计算第二位遗漏
		String missZ2 = this.getMissCountZ(h2, lastMissCountType[2]);
		if(missZ2 != null){
			result = result + missZ2 + "|";
		}else{
			return null;
		}
		//计算第3位遗漏
		String missZ3 = this.getMissCountZ(h3, lastMissCountType[3]);
		if(missZ3 != null){
			result = result + missZ3 + "|";
		}else{
			return null;
		}
		
		//计算前2组选
		String missQ2 = this.getMissCountRQ(lotteryResult, lastMissCountType[4],"Q2");
		if(missQ2 != null){
			result =result + missQ2 + "|";
		}else{
			return null;
		}
		//计算前3组选
		
		String missQ3 = this.getMissCountRQ(lotteryResult, lastMissCountType[5],"Q3");
		if(missQ3 != null){
			result = result + missQ3 + "|";
		}else{
			return null;
		}
		
		//计算奇数个数遗漏
		String missON = this.getMissCountNumber(lotteryResult, lastMissCountType[6],"ON");
		if(missON != null){
			result = result + missON + "|";
		}else{
			return null;
		}
		//计算偶数个数遗漏
		String missEN = this.getMissCountNumber(lotteryResult, lastMissCountType[7],"EN");
		if(missEN != null){
			result = result + missEN + "|";
		}else{
			return null;
		}
		//计算小数个数遗漏
		String missSN = this.getMissCountNumber(lotteryResult, lastMissCountType[8],"SN");
		if(missSN != null){
			result = result + missSN + "|";
		}else{
			return null;
		}
		//计算大数个数遗漏
		String missBN = this.getMissCountNumber(lotteryResult, lastMissCountType[9],"BN");
		if(missBN != null){
			result = result + missBN + "|";
		}else{
			return null;
		}
		//计算质数个数遗漏
		String missPN = this.getMissCountNumber(lotteryResult, lastMissCountType[10],"PN");
		if(missPN != null){
			result = result + missPN + "|";
		}else{
			return null;
		}
		//计算合数个数遗漏
		String missCN = this.getMissCountNumber(lotteryResult, lastMissCountType[11],"CN");
		if(missCN != null){
			result = result + missCN + "|";
		}else{
			return null;
		}
		
		if(result != null){
			if(result.endsWith("|")){
				result = result.substring(0, result.length() - 1);
			}
		}
		
		return result;
	}
	/**
	 * 
	 * Title: getMissCountNumber<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param lotteryResult
	 * @param lastMissCount
	 * @return
	 */
	private String getMissCountNumber(String lotteryResult, String lastMissCount,String type){
		String result = "";
		int typeNum = 0;//出现个数
		List<String> lotteryResultArr = super.splitStrByLen(lotteryResult, 2);
		
		for(String oneR : lotteryResultArr){
			if(type.equals("ON") && (oneR.equals("01") || oneR.equals("03") || oneR.equals("05") || oneR.equals("07") || oneR.equals("09") || oneR.equals("11"))){//奇数
				typeNum++;
			}
			if(type.equals("EN") && (oneR.equals("02") || oneR.equals("04") || oneR.equals("06") || oneR.equals("08") || oneR.equals("10"))){//偶数
				typeNum++;
			}
			if(type.equals("SN") && (oneR.equals("01") || oneR.equals("02") || oneR.equals("03") || oneR.equals("04") || oneR.equals("05"))){//小数
				typeNum++;
			}
			if(type.equals("BN") && (oneR.equals("06") || oneR.equals("07") || oneR.equals("08") || oneR.equals("09") || oneR.equals("10") || oneR.equals("11"))){//大数
				typeNum++;
			}
			if(type.equals("PN") && (oneR.equals("01") || oneR.equals("02") || oneR.equals("03") || oneR.equals("05") || oneR.equals("07") || oneR.equals("11"))){//质数
				typeNum++;
			}
			if(type.equals("CN") && (oneR.equals("04") || oneR.equals("06") || oneR.equals("08") || oneR.equals("09") || oneR.equals("10"))){//合数
				typeNum++;
			}
		}
		
		int [] digit = {0, 0, 0, 0, 0,0};
		
		if(StringUtils.isBlank(lastMissCount)){
			for(int i = 0; i < 6; i++){
				if(i == typeNum){
					digit[i] = 0;
				}else{
					digit[i]++;
				}
			}
		}else{
			String [] lastMissCountArr = lastMissCount.split(",");
			if(lastMissCountArr == null || lastMissCountArr.length != 6){
				return null;
			}
			for(int i = 0; i < 6; i++){
				if(i == typeNum){
					digit[i] = 0;
				}else{
					digit[i] = Integer.parseInt(lastMissCountArr[i]);
					digit[i]++;
				}
			}
		}
		
		for(int i = 0; i < 6; i++){
			result = result + digit[i] + ",";
		}
		
		result = result.substring(0, result.length() - 1);
		
		return result;
	}
	/**
	 * 
	 * Title: getMissCountR<br>
	 * Description: <br>
	 *              <br>计算任选2到任选8的遗漏,前2，前3的组选
	 * @param lotteryResult
	 * @param lastMissCount
	 * @return
	 */
	private String getMissCountRQ(String lotteryResult, String lastMissCount,String flag){
		String result = "";
		int h1 = Integer.parseInt(lotteryResult.substring(0, 2)) - 1;
		int h2 = Integer.parseInt(lotteryResult.substring(2, 4)) - 1;
		int h3 = Integer.parseInt(lotteryResult.substring(4, 6)) - 1;
		int h4 = Integer.parseInt(lotteryResult.substring(6, 8)) - 1;
		int h5 = Integer.parseInt(lotteryResult.substring(8, 10)) - 1;
		
		int digit[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		if(StringUtils.isBlank(lastMissCount)){
			for(int i = 0; i < 11; i++){
				if(flag.equals("R")){//任选
					if(i == h1 || i == h2 || i == h3 || i == h4 || i == h5){
						digit[i] = 0;
					}else{
						digit[i]++;
					}
				}else if(flag.equals("Q2")){
					if(i == h1 || i == h2 ){
						digit[i] = 0;
					}else{
						digit[i]++;
					}
				}else if(flag.equals("Q3")){
					if(i == h1 || i == h2 || i == h3){
						digit[i] = 0;
					}else{
						digit[i]++;
					}
				}
				
			}
		}else{
			String [] lastMissCountArr = lastMissCount.split(",");
			if(lastMissCountArr == null || lastMissCountArr.length != 11){
				return null;
			}
			for(int i = 0; i < 11; i++){
				if(flag.equals("R")){//任选
					if(i == h1 || i == h2 || i == h3 || i == h4 || i == h5){
						digit[i] = 0;
					}else{
						digit[i] = Integer.parseInt(lastMissCountArr[i]);
						digit[i]++;
					}
				}else if(flag.equals("Q2")){
					if(i == h1 || i == h2 ){
						digit[i] = 0;
					}else{
						digit[i] = Integer.parseInt(lastMissCountArr[i]);
						digit[i]++;
					}
				}else if(flag.equals("Q3")){
					if(i == h1 || i == h2 || i == h3){
						digit[i] = 0;
					}else{
						digit[i] = Integer.parseInt(lastMissCountArr[i]);
						digit[i]++;
					}
				}
			}
		}
		
		for(int i = 0; i < 11; i++){
			result = result + digit[i] + ",";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}
	/**
	 * 
	 * Title: getMissCountZ<br>
	 * Description: <br>
	 *              <br>
	 * @param posValue
	 * @param lastMissCount
	 * @return
	 */
	private String getMissCountZ(int posValue,String lastMissCount ){
		String result = "";
		int digit[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
		if(StringUtils.isBlank(lastMissCount)){
			for(int i = 0; i < 11; i++){
				if(i == posValue){
					digit[i] = 0;
				}else{
					digit[i]++;
				}
			}
		}else{
			String [] lastMissCountArr = lastMissCount.split(",");
			if(lastMissCountArr == null || lastMissCountArr.length != 11){
				return null;
			}
			for(int i = 0; i < 11; i++){
				if(i == posValue){
					digit[i] = 0;
				}else{
					digit[i] = Integer.parseInt(lastMissCountArr[i]);
					digit[i]++;
				}
			}
		}
		
		for(int i = 0; i < 11; i++){
			result = result + digit[i] + ",";
		}
		result = result.substring(0, result.length() - 1);
		
		return result;
		
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
			String [] missCountType = missCount.split("\\|");//12种遗漏
			for(int k = 0 ; k < missCountType.length ;k++){
				String missType = "";
				switch (k) {
				case 0:
					missType = "B-";
					break;
				case 1:
					missType = "Z1-";
					break;
				case 2:
					missType = "Z2-";
					break;
				case 3:
					missType = "Z3-";
					break;
				case 4:
					missType = "Q2-";
					break;
				case 5:
					missType = "Q3-";
					break;
				case 6:
					missType = "ON-";
					break;
				case 7:
					missType = "EN-";
					break;
				case 8:
					missType = "SN-";
					break;
				case 9:
					missType = "BN-";
					break;
				case 10:
					missType = "PN-";
					break;
				case 11:
					missType = "CN-";
					break;
				default:
					break;
				}
				
				String [] missCountArr = missCountType[k].split(",");
				for(int i = 0 ; i< missCountArr.length;i++){
					if (missType.equals("B-") || missType.equals("Z1-")
							|| missType.equals("Z2-") || missType.equals("Z3-")
							|| missType.equals("Q2-") || missType.equals("Q3-")) {
						missCountResult.put(missType+String.format("%1$02d",i+1), Integer.parseInt(missCountArr[i]));
					}else{
						missCountResult.put(missType+String.valueOf(i), Integer.parseInt(missCountArr[i]));
					}
					
				}
			}
			
		}
		info.setMissCountResult(missCountResult);
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
		boolean result = false;
		if(StringUtils.isEmpty(limitNumber) || StringUtils.isEmpty(betCode)){
			result = false;
			return result;
		}
		//将限号字符串转为集合的方式
		Map<String,String> limitInfoMap = new TreeMap<String,String>();
		String [] limitPool = limitNumber.split("\\|");
		if(limitPool != null && limitPool.length != 0){
			for(String limit : limitPool){
				String [] limitOne = limit.split("-");
				if(limitOne != null && limitOne.length == 2){
					limitInfoMap.put(limitOne[0], limitOne[1]);
				}
			}
		}
		
		//先判断玩法对应的是否有限号信息
		if(limitInfoMap.get(playType) == null || "".equals(limitInfoMap.get(playType).trim())){
			result = false;
		}
		
		//按照玩法判断是否限号
		List<String> playTypeLimit = Arrays.asList(limitInfoMap.get(playType).trim().split(","));
		String [] betArr = betCode.split("\\^");
		
		if (ELEVEN_PLAYTYPE_1.equals(playType)) {
 			for(String bet : betArr){
 				if(playTypeLimit.contains(bet)){
 					result = true;
 					break;
 				}
 			}
		}else if(ELEVEN_PLAYTYPE_2.equals(playType) || ELEVEN_PLAYTYPE_3.equals(playType) || ELEVEN_PLAYTYPE_4.equals(playType)
				|| ELEVEN_PLAYTYPE_5.equals(playType) || ELEVEN_PLAYTYPE_6.equals(playType) || ELEVEN_PLAYTYPE_7.equals(playType)
				|| ELEVEN_PLAYTYPE_8.equals(playType)){
			int singleBetNum = -1,piPeiNum = -1;
			
			if(ELEVEN_PLAYTYPE_2.equals(playType)){
				singleBetNum = 2; piPeiNum = 2;
			}
			if(ELEVEN_PLAYTYPE_3.equals(playType)){
				singleBetNum = 3; piPeiNum = 3;
			}
			if(ELEVEN_PLAYTYPE_4.equals(playType)){
				singleBetNum = 4; piPeiNum = 4;
			}
			if(ELEVEN_PLAYTYPE_5.equals(playType)){
				singleBetNum = 5; piPeiNum = 5;
			}
			if(ELEVEN_PLAYTYPE_6.equals(playType)){
				singleBetNum = 6; piPeiNum = 5;
			}
			if(ELEVEN_PLAYTYPE_7.equals(playType)){
				singleBetNum = 7; piPeiNum = 5;
			}
			if(ELEVEN_PLAYTYPE_8.equals(playType)){
				singleBetNum = 8; piPeiNum = 5;
			}
			try {
				List<String> toSingleBets = this.elevenArbitraryToSingle(betType,Arrays.asList(betArr),singleBetNum);
				for(String single : toSingleBets){
					for(String limitOne : playTypeLimit){
						if(Integer.parseInt(super.hitHaoNum(splitStrByLen(single,2), splitStrByLen(limitOne,2))) >= piPeiNum){
							result = true;
							break;
						}
					}
				}
			} catch (Exception e) {
				result = true;
			}
		}else if(ELEVEN_PLAYTYPE_9.equals(playType) || ELEVEN_PLAYTYPE_10.equals(playType)){
			int singleBetNum = -1;
			int piPeiNum = -1;
			
			if(ELEVEN_PLAYTYPE_9.equals(playType)){
				singleBetNum = 2; piPeiNum = 2;
			}
			if(ELEVEN_PLAYTYPE_10.equals(playType)){
				singleBetNum = 3; piPeiNum = 3;
			}
			
			try {
				List<String> toSingleBets = this.beforeDirectToSingle(betType,Arrays.asList(betArr),betCode,piPeiNum,singleBetNum);
				for(String single : toSingleBets){
					for(String limitOne : playTypeLimit){
						if(single.equals(limitOne)){//直选是按照顺序的，所以可以直接比较
							result = true;
							break;
						}
					}
				}
			} catch (LotteryUnDefineException e) {
				result = true;
				e.printStackTrace();
			} catch (Exception e) {
				result = true;
				e.printStackTrace();
			}
			
			
		}else if(ELEVEN_PLAYTYPE_11.equals(playType)){
			int singleBetNum = -1;
			int piPeiNum = -1;
			
			if(ELEVEN_PLAYTYPE_11.equals(playType)){
				singleBetNum = 2; piPeiNum = 2;
			}
			if(ELEVEN_PLAYTYPE_12.equals(playType)){
				singleBetNum = 3; piPeiNum = 3;
			}
			
			try {
				List<String> toSingleBets = this.beforeGroupToSingle(betType, Arrays.asList(betArr), singleBetNum);
				for(String single : toSingleBets){
					for(String limitOne : playTypeLimit){
						if(super.hitHaoNum(splitStrByLen(single,2), splitStrByLen(limitOne,2)).equals(String.valueOf(piPeiNum))){
							result = true;
							break;
						}
					}
				}
			} catch (LotteryUnDefineException e) {
				result = true;
				e.printStackTrace();
			} catch (Exception e) {
				result = true;
				e.printStackTrace();
			}
			
		}else {
			result = false;
		}
		
		return result;
	}
	
	/**
	 * 
	 * Title: beforeDirectPrize
	 * Description:  选前二直选 \ 选前三直选 兑奖
	 * @param winRulesKey
	 * @param betType
	 * @param lotteryResult
	 * @param elevenYunWinResult
	 * @param singleBets
	 * @param betCode
	 * @param piPeiNum 需要匹配的个数 选前二直选为2 选前三直选为3
	 * @param arrangeLen 排列的长度，选前二直选为2 选前三直选为3
	 * @throws LotteryUnDefineException
	 * @throws Exception    设定文件
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> beforeDirectPrize(String winRulesKey,String betType, String lotteryResult,
			Map<String, String[]> elevenYunWinResult,List<String> singleBets, String betCode ,int piPeiNum, int arrangeLen,boolean isOrder)
			throws LotteryUnDefineException, Exception {
		List<String> toSingle = this.beforeDirectToSingle(betType,singleBets,betCode,piPeiNum,arrangeLen);
		return prizeByPosition(winRulesKey, lotteryResult, elevenYunWinResult,toSingle, piPeiNum,isOrder);

	}
	/**
	 * 
	 * Title: beforeDirectToSingle<br>
	 * Description: <br>
	 *            选前二直选 \ 选前三直选转单式<br>
	 * @param betType
	 * @param singleBets
	 * @param betCode
	 * @param piPeiNum
	 * @param arrangeLen
	 * @return  List<String>
	 * @throws LotteryUnDefineException
	 * @throws Exception
	 */
	private List<String> beforeDirectToSingle(String betType,List<String> singleBets,String betCode,int piPeiNum,int arrangeLen) throws LotteryUnDefineException, Exception{
		if (ELEVEN_BETTYPE_0.equals(betType)) {//单式
			return singleBets;
		} else if (ELEVEN_BETTYPE_1.equals(betType)) {//复式,排列算法
			List<String> duplexToSingle = new ArrayList<String>();
			for(String single : singleBets){//对多注依次处理
				duplexToSingle.addAll(arrange(splitStrByLen(single,2),arrangeLen));
			}
			return duplexToSingle;
		} else if (ELEVEN_BETTYPE_2.equals(betType)) {//胆拖,先组合再排列
			List<String> danTuoToSingle = new ArrayList<String>();
			for(String single : singleBets){
				List<String> danTuoCombine = super.combineDanTuo(single, piPeiNum, 2, "\\*");
				List<String> danTuoArrange = super.arrange(danTuoCombine, piPeiNum);
				danTuoToSingle.addAll(danTuoArrange);
			}
			return danTuoToSingle;
		} else if(ELEVEN_BETTYPE_3.equals(betType)){//定位复式,
			List<String> combinedingWei = new ArrayList<String>();
			combinedingWei = super.combineDuplex(betCode, ELEVEN_BET_GROUP_SEPARATOR, "\\*", 2);
			//去掉重复的单式
			List<String> dingWei = new ArrayList<String>();//
			if(combinedingWei != null){
				for(String one : combinedingWei){
					String [] oneArr = one.split("\\*");
					if(oneArr.length == 2 && !(oneArr[0].equals(oneArr[1]))){
						dingWei.add(one);
					}
					if(oneArr.length == 3 && !(oneArr[0].equals(oneArr[1]) || oneArr[0].equals(oneArr[2]) || oneArr[1].equals(oneArr[2]))){
						dingWei.add(one);
					}
				}
			}
			return dingWei;
		} else {
			throw new LotteryUnDefineException("未找到投注方式：" + betType);
		}
	}
	/**
	 * 
	 * Title: beforeGroupPrize
	 * Description:  选前二组选 选前三组选
	 * @param winRulesKey
	 * @param betType
	 * @param lotteryResult
	 * @param elevenYunWinResult
	 * @param singleBets
	 * @param betCode
	 * @param piPeiNum 需要匹配上的个数 选前二组选为2 选前三组选为3
	 * @param combineLen 组合的长度 选前二组选为2 选前三组选为3
	 * @throws LotteryUnDefineException
	 * @throws Exception    设定文件
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> beforeGroupPrize(String winRulesKey,String betType, String lotteryResult,
			Map<String, String[]> elevenYunWinResult,List<String> singleBets,int piPeiNum, int combineLen)
			throws LotteryUnDefineException, Exception {
		List<String> toSingle = this.beforeGroupToSingle(betType, singleBets, combineLen);
		return prizeByPosition(winRulesKey, lotteryResult, elevenYunWinResult,toSingle, piPeiNum,false);
	}
	/**
	 * 
	 * Title: beforeGroupToSingle<br>
	 * Description: <br>
	 *            选前二组选 选前三组选转单式<br>
	 * @param betType
	 * @param singleBets
	 * @param combineLen
	 * @return List<String>
	 * @throws LotteryUnDefineException
	 * @throws Exception
	 */
	private List<String> beforeGroupToSingle(String betType,List<String> singleBets,int combineLen) throws LotteryUnDefineException, Exception{
		if (ELEVEN_BETTYPE_0.equals(betType)) {//单式
			return singleBets;
		} else if (ELEVEN_BETTYPE_1.equals(betType)) {//复式，按照组合
			List<String> duplexToSingle = new ArrayList<String>();
			for(String single : singleBets){//对多注依次处理
				duplexToSingle.addAll(combineToList(splitStrByLen(single,2),combineLen));
			}
			return duplexToSingle;
		} else if (ELEVEN_BETTYPE_2.equals(betType)) {//胆拖,按照组合
			List<String> danTuoToSingle = new ArrayList<String>();
			for(String single : singleBets){
				danTuoToSingle.addAll(combineDanTuo(single,combineLen,2,"\\*"));
			}
			return danTuoToSingle;
		} else {
			throw new LotteryUnDefineException("未找到投注方式：" + betType);
		}
	}
	/**
	 * 
	 * Title: elevenArbitraryPrize
	 * Description:  十一运任选的兑奖,包括任选2到任选8
	 * @param winRulesKey
	 * @param betType 投注方式
	 * @param lotteryResult
	 * @param elevenYunWinResult
	 * @param singleBets
	 * @param hitNum 要匹配上的号码个数
	 * @param combineLen 玩法中单式投注的号码个数
	 * @throws LotteryUnDefineException
	 * @throws Exception    设定文件
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> elevenArbitraryPrize(String winRulesKey,String betType,ArrayList<String> lotteryResult,
			Map<String, String[]> elevenYunWinResult,List<String> singleBets,int hitNum,int combineLen) throws LotteryUnDefineException, Exception {
		List<String> toSingle = this.elevenArbitraryToSingle(betType,singleBets,combineLen);
		return prizeByHitNum(winRulesKey, lotteryResult, elevenYunWinResult,toSingle, hitNum);
	}
	/**
	 * 
	 * Title: elevenArbitrary2ToSingle<br>
	 * Description: <br>
	 *            十一运任选的转单式<br>
	 * @param betType
	 * @param singleBets
	 * @param combineLen
	 * @return List<String>
	 * @throws LotteryUnDefineException
	 * @throws Exception
	 */
	private List<String> elevenArbitraryToSingle(String betType,List<String> singleBets,int combineLen) throws LotteryUnDefineException, Exception{
		if (ELEVEN_BETTYPE_0.equals(betType)) {//单式
			return singleBets;
		} else if (ELEVEN_BETTYPE_1.equals(betType)) {//复试
			List<String> duplexToSingle = new ArrayList<String>();
			for(String single : singleBets){//对多注依次处理
				duplexToSingle.addAll(combineToList(splitStrByLen(single,2),combineLen));
			}
			return duplexToSingle;
		} else if (ELEVEN_BETTYPE_2.equals(betType)) {//胆拖
			List<String> danTuoToSingle = new ArrayList<String>();
			for(String single : singleBets){
				danTuoToSingle.addAll(combineDanTuo(single,combineLen,2,"\\*"));
			}
			return danTuoToSingle;
		} else {
			throw new LotteryUnDefineException("未找到投注方式：" + betType);
		}
	}
	
	/**
	 * 
	 * Title: prizeByPosition
	 * Description:  十一运按照开奖结果的前n个号码有序或者无序匹配
	 * @param winRulesKey 兑奖规则定义中的key
	 * @param lotteryResult 开奖结果
	 * @param elevenYunWinResult 奖金结果
	 * @param singleBets 单注方式的投注集合
	 * @param piPeiNum 需要匹配的号码个数
	 * @param isOrder 是否有序，true有序 false 无序
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> prizeByPosition(String winRulesKey,
			String lotteryResult, Map<String, String[]> elevenYunWinResult,List<String> singleBets,
			int piPeiNum, boolean isOrder) {
		List<List<String>> result = new ArrayList<List<String>>();
		String subLottery = lotteryResult.substring(0,piPeiNum*2);//取开奖结果的前piPeiNum位
		for(String single : singleBets){
			List<String> innerList = new ArrayList<String>();
			if(single.indexOf("*") != -1){
				single = single.replaceAll("\\*","");
			}
			boolean isPiPei = false;
			if(isOrder){
				if(single.equals(subLottery)){
					isPiPei = true;
				}
			}else{
				if(super.hitHaoNum(splitStrByLen(single,2), splitStrByLen(subLottery,2)).equals(String.valueOf(piPeiNum))){
					isPiPei = true;
				}
			}
			if(isPiPei){
				innerList.add(single);
				innerList.add(winRules.get(winRulesKey));
				innerList.add(elevenYunWinResult.get(winRules.get(winRulesKey))[1]);
				innerList.add("0");
				result.add(innerList);
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * Title: prizeByHitNum
	 * Description:  十一运按照开奖结果无序匹配
	 * @param winRulesKey 兑奖规则定义的key
	 * @param lotteryResult 奖金结果
	 * @param elevenYunWinResult 开奖结果
	 * @param singleBets 单注方式的投注集合
	 * @param piPeiNum 需要匹配的号码个数
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> prizeByHitNum(String winRulesKey,
			 ArrayList<String> lotteryResult, Map<String, String[]> elevenYunWinResult,List<String> singleBets,
			int piPeiNum){
		List<List<String>> result = new ArrayList<List<String>>();
		for(String single : singleBets){
			List<String> innerList = new ArrayList<String>();
			if(Integer.parseInt(super.hitHaoNum(splitStrByLen(single,2), lotteryResult)) >= piPeiNum){
				innerList.add(single);
				innerList.add(winRules.get(winRulesKey));
				innerList.add(elevenYunWinResult.get(winRules.get(winRulesKey))[1]);
				innerList.add("0");
				result.add(innerList);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * Title: checkBet
	 * Description: 对符合玩法的规则校验投注方式
	 * @param playType
	 * @param betType
	 * @param betString
	 * @throws LotteryUnDefineException
	 * @throws Exception    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	private boolean checkBet(String playType,String betType,String betString) throws LotteryUnDefineException,Exception{
		if(simpleBetRule.containsKey(playType) && (simpleBetRule.get(playType)).containsKey(betType)){//能直接按照正则校验的
			return checkSimple((simpleBetRule.get(playType)).get(betType),betString);
		}else if(dabTuoBetRule.containsKey(playType) && (dabTuoBetRule.get(playType)).containsKey(betType)){
			return checkDanTuo((dabTuoBetRule.get(playType)).get(betType),betString,danTuoNum.get(playType+"_"+betType));
		}else if(dingWeiBetRule.containsKey(playType) && (dingWeiBetRule.get(playType)).containsKey(betType)){
			return checkDingWei((dingWeiBetRule.get(playType)).get(betType),betString);
		}else {
			throw new LotteryUnDefineException("未找到投注方式："+betType);
		}
	}
	
	/**
	 * 
	 * Title: checkSimple
	 * Description:  对应值需要简单的按照规则校验的规则，并且要校验号码不能重复
	 * @param regu
	 * @param betString
	 * @return boolean    返回类型
	 * @throws
	 */
	private boolean checkSimple(String regu,String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,ELEVEN_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(regu,group)){
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
	 * Description:  校验胆拖，包括格式、重复、总数
	 * @param betString
	 * @return boolean    返回类型
	 * @throws
	 */
	private boolean checkDanTuo(String regu,String betString,int num){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,ELEVEN_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(regu,group)){//格式不合格
				checkResult = false;
				break;
			}else if(!checkDanTuoRepeat(group,2)){//校验是否重复
				checkResult = false;
				break;
			}else if(!checkDanTuoTotalNum(group,num)){//校验总数是否够
				checkResult = false;
				break;
			}
		}
		return checkResult;
	} 
	/**
	 * 
	 * Title: checkDingWei<br>
	 * Description: <br>
	 *              <br>校验定位复式
	 * @param regu
	 * @param betString
	 * @return
	 */
	private boolean checkDingWei(String regu,String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,ELEVEN_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(regu,group)){//格式不合格
				checkResult = false;
				break;
			}else {//校验是否重复
				//同一个位不能重复
				String [] groupArr = group.split("\\*");
				for(String oneWei : groupArr){
					if(!super.checkRepeat(oneWei, 2)){//如果同一个位置有重复的号码
						return false;
					}
				}
				//位与位不能重复
				List<String> combinedingWei = new ArrayList<String>();
				combinedingWei = super.combineDuplex(group, ELEVEN_BET_GROUP_SEPARATOR, "\\*", 2);
				//去掉重复的单式
				List<String> dingWei = new ArrayList<String>();//
				if(combinedingWei != null){
					for(String one : combinedingWei){
						String [] oneArr = one.split("\\*");
						if(oneArr.length == 2 && !(oneArr[0].equals(oneArr[1]))){
							dingWei.add(one);
						}
						if(oneArr.length == 3 && !(oneArr[0].equals(oneArr[1]) || oneArr[0].equals(oneArr[2]) || oneArr[1].equals(oneArr[2]))){
							dingWei.add(one);
						}
					}
				}
				if(dingWei.size() == 0){
					checkResult = false;
					break;
				}
			}
		}
		return checkResult;
	} 
	
	/**
	 * 
	 * Title: checkDanTuoTotalNum
	 * Description:  校验胆拖的总位数,必须加起来>=3
	 * @param target
	 * @return boolean    返回类型
	 * @throws
	 */
	private boolean checkDanTuoTotalNum(String target,int num){
		boolean checkResult = true;
		String [] DanTuoGroup = target.split("\\*");//此处已经经过校验，包含胆码和拖码，所以数组为2个值
		List<String> dan = splitStrByLen(DanTuoGroup[0],2);
		List<String> tuo = splitStrByLen(DanTuoGroup[1],2);
		if((dan.size() + tuo.size()) < num){
			checkResult = false;
			return checkResult;
		}
		return checkResult;
	}
	
	/**
	 * 
	 * Title: setBindList
	 * Description:  所有玩法列表
	 * @return List<String>    返回类型
	 * @throws
	 */
	private static List<String> setBindList(){
		List<String> list = new ArrayList<String>();
		list.add(ELEVEN_PLAYTYPE_1);
		list.add(ELEVEN_PLAYTYPE_2);
		list.add(ELEVEN_PLAYTYPE_3);
		list.add(ELEVEN_PLAYTYPE_4);
		list.add(ELEVEN_PLAYTYPE_5);
		list.add(ELEVEN_PLAYTYPE_6);
		list.add(ELEVEN_PLAYTYPE_7);
		list.add(ELEVEN_PLAYTYPE_8);
		list.add(ELEVEN_PLAYTYPE_9);
		list.add(ELEVEN_PLAYTYPE_10);
		list.add(ELEVEN_PLAYTYPE_11);
		list.add(ELEVEN_PLAYTYPE_12);
		return list;
	}
	/**
	 * 
	 * Title: setBindSimpleBetRule
	 * Description: ( 可直接通过校验规则校验的
	 * @return Map<String,Map<String,String>>    返回类型
	 * @throws
	 */
	private static Map<String,Map<String,String>> setBindSimpleBetRule(){
		Map <String,Map<String,String>> result = new HashMap<String,Map<String,String>>();
		Map<String,String> sub1 = new HashMap<String,String>();
		sub1.put("0", ELEVEN_BET_1_0_REGULAR);
		result.put(ELEVEN_PLAYTYPE_1, sub1);
		
		Map<String,String> sub2 = new HashMap<String,String>();
		sub2.put("0", ELEVEN_BET_2_0_REGULAR);
		sub2.put("1", ELEVEN_BET_2_1_REGULAR);
		result.put(ELEVEN_PLAYTYPE_2, sub2);
		
		Map<String,String> sub3 = new HashMap<String,String>();
		sub3.put("0", ELEVEN_BET_3_0_REGULAR);
		sub3.put("1", ELEVEN_BET_3_1_REGULAR);
		result.put(ELEVEN_PLAYTYPE_3, sub3);
		
		Map<String,String> sub4 = new HashMap<String,String>();
		sub4.put("0", ELEVEN_BET_4_0_REGULAR);
		sub4.put("1", ELEVEN_BET_4_1_REGULAR);
		result.put(ELEVEN_PLAYTYPE_4, sub4);
		
		Map<String,String> sub5 = new HashMap<String,String>();
		sub5.put("0", ELEVEN_BET_5_0_REGULAR);
		sub5.put("1", ELEVEN_BET_5_1_REGULAR);
		result.put(ELEVEN_PLAYTYPE_5, sub5);
		
		Map<String,String> sub6 = new HashMap<String,String>();
		sub6.put("0", ELEVEN_BET_6_0_REGULAR);
		sub6.put("1", ELEVEN_BET_6_1_REGULAR);
		result.put(ELEVEN_PLAYTYPE_6, sub6);
		
		Map<String,String> sub7 = new HashMap<String,String>();
		sub7.put("0", ELEVEN_BET_7_0_REGULAR);
		sub7.put("1", ELEVEN_BET_7_1_REGULAR);
		result.put(ELEVEN_PLAYTYPE_7, sub7);
		
		Map<String,String> sub8 = new HashMap<String,String>();
		sub8.put("0", ELEVEN_BET_8_0_REGULAR);
		sub8.put("1", ELEVEN_BET_8_1_REGULAR);
		result.put(ELEVEN_PLAYTYPE_8, sub8);
		
		Map<String,String> sub9 = new HashMap<String,String>();
		sub9.put("0", ELEVEN_BET_9_0_REGULAR);
		sub9.put("1", ELEVEN_BET_9_1_REGULAR);
		result.put(ELEVEN_PLAYTYPE_9, sub9);
		
		Map<String,String> sub10 = new HashMap<String,String>();
		sub10.put("0", ELEVEN_BET_10_0_REGULAR);
		sub10.put("1", ELEVEN_BET_10_1_REGULAR);
		result.put(ELEVEN_PLAYTYPE_10, sub10);
		
		Map<String,String> sub11 = new HashMap<String,String>();
		sub11.put("0", ELEVEN_BET_11_0_REGULAR);
		sub11.put("1", ELEVEN_BET_11_1_REGULAR);
		result.put(ELEVEN_PLAYTYPE_11, sub11);
		
		Map<String,String> sub12 = new HashMap<String,String>();
		sub12.put("0", ELEVEN_BET_12_0_REGULAR);
		sub12.put("1", ELEVEN_BET_12_1_REGULAR);
		result.put(ELEVEN_PLAYTYPE_12, sub12);
		
		return result;
	}
	
	/**
	 * 
	 * Title: setBind3
	 * Description:  胆拖方式集合
	 * @return Map<String,Map<String,String>>    返回类型
	 * @throws
	 */
	private static Map<String,Map<String,String>> setBindDanTuoRule(){
		Map <String,Map<String,String>> result = new HashMap<String,Map<String,String>>();
		
		Map<String,String> sub2 = new HashMap<String,String>();
		sub2.put("2", ELEVEN_BET_2_2_REGULAR);
		result.put(ELEVEN_PLAYTYPE_2, sub2);
		
		Map<String,String> sub3 = new HashMap<String,String>();
		sub3.put("2", ELEVEN_BET_3_2_REGULAR);
		result.put(ELEVEN_PLAYTYPE_3, sub3);
		
		Map<String,String> sub4 = new HashMap<String,String>();
		sub4.put("2", ELEVEN_BET_4_2_REGULAR);
		result.put(ELEVEN_PLAYTYPE_4, sub4);
		
		Map<String,String> sub5 = new HashMap<String,String>();
		sub5.put("2", ELEVEN_BET_5_2_REGULAR);
		result.put(ELEVEN_PLAYTYPE_5, sub5);
		
		Map<String,String> sub6 = new HashMap<String,String>();
		sub6.put("2", ELEVEN_BET_6_2_REGULAR);
		result.put(ELEVEN_PLAYTYPE_6, sub6);
		
		Map<String,String> sub7 = new HashMap<String,String>();
		sub7.put("2", ELEVEN_BET_7_2_REGULAR);
		result.put(ELEVEN_PLAYTYPE_7, sub7);
		
		Map<String,String> sub8 = new HashMap<String,String>();
		sub8.put("2", ELEVEN_BET_8_2_REGULAR);
		result.put(ELEVEN_PLAYTYPE_8, sub8);
		
		Map<String,String> sub9 = new HashMap<String,String>();
		sub9.put("2", ELEVEN_BET_9_2_REGULAR);
		result.put(ELEVEN_PLAYTYPE_9, sub9);
		
		Map<String,String> sub10 = new HashMap<String,String>();
		sub10.put("2", ELEVEN_BET_10_2_REGULAR);
		result.put(ELEVEN_PLAYTYPE_10, sub10);
		
		Map<String,String> sub11 = new HashMap<String,String>();
		sub11.put("2", ELEVEN_BET_11_2_REGULAR);
		result.put(ELEVEN_PLAYTYPE_11, sub11);
		

		Map<String,String> sub12 = new HashMap<String,String>();
		sub12.put("2", ELEVEN_BET_12_2_REGULAR);
		result.put(ELEVEN_PLAYTYPE_12, sub12);
		
		return result;
	}
	/**
	 * 
	 * Title: setDanTuoNum<br>
	 * Description: <br>
	 *              <br>定义各个投注方式的胆拖必须满足的胆码和拖码的个数
	 * @return
	 */
	private static Map<String,Integer> setDanTuoNum(){
		Map<String,Integer> result = new HashMap<String,Integer>();
		result.put("2_2", 2);
		result.put("3_2", 3);
		result.put("4_2", 4);
		result.put("5_2", 5);
		result.put("6_2", 6);
		result.put("7_2", 7);
		result.put("8_2", 8);
		result.put("9_2", 2);
		result.put("10_2", 3);
		result.put("11_2", 2);
		result.put("12_2", 3);
		return result;
	}
	/**
	 * 
	 * Title: setBindDingWei<br>
	 * Description: <br>
	 *              <br>前2和前3的定位复式
	 * @return
	 */
	private static Map<String,Map<String,String>> setBindDingWei(){
		Map <String,Map<String,String>> result = new HashMap<String,Map<String,String>>();
		Map<String,String> sub9 = new HashMap<String,String>();
		sub9.put("3", ELEVEN_BET_9_3_REGULAR);
		result.put(ELEVEN_PLAYTYPE_9, sub9);
		
		Map<String,String> sub10 = new HashMap<String,String>();
		sub10.put("3", ELEVEN_BET_10_3_REGULAR);
		result.put(ELEVEN_PLAYTYPE_10, sub10);
		
		return result;
	}
	
	public static void main(String[] args) {
//		String lotteryResult = "0111100207";
//		String lastMissCount="1,3,0,2,0,2,0,0,2,0,1|3,36,9,4,22,2,0,1,10,6,13|39,23,0,15,3,51,9,4,8,2,6|1,8,29,2,4,3,34,6,7,0,15|3,23,0,4,3,2,0,1,8,2,6|1,8,0,2,3,2,0,1,7,0,6";
//		JxDlcLottery dlc = new JxDlcLottery();
//		String newMiss = dlc.getMissCount(lotteryResult, lastMissCount);
//		System.out.println("newMiss=="+newMiss);
		
		JxDlcLottery dlc = new JxDlcLottery();
//		try {
//			List<String> result = dlc.lotteryToSingle("9", "3", "0102*0102");
//			if(result != null){
//				for(String one : result){
//					System.out.println(one);
//				}
//			}
//		} catch (LotteryUnDefineException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		}
		
		String result = dlc.getMissCount("1001090307", "0,0,0,0,0,0,0,0,0,0,0|0,0,0,0,0,0,0,0,0,0,0|0,0,0,0,0,0,0,0,0,0,0|0,0,0,0,0,0,0,0,0,0,0|0,0,0,0,0,0,0,0,0,0,0|0,0,0,0,0,0,0,0,0,0,0|716,9,4,2,0,58|58,0,2,4,9,716|389,11,0,4,2,28|28,2,4,3,11,389|78,1,0,2,8,97|97,8,2,3,1,78");
		LotteryInfo info = new LotteryInfo();
		
		LotteryInfo info2 = dlc.splitMissCount("0,0,0,0,0,0,0,0,0,0,0|0,0,0,0,0,0,0,0,0,0,0|0,0,0,0,0,0,0,0,0,0,0|0,0,0,0,0,0,0,0,0,0,0|0,0,0,0,0,0,0,0,0,0,0|0,0,0,0,0,0,0,0,0,0,0|716,9,4,2,0,58|58,0,2,4,9,716|389,11,0,4,2,28|28,2,4,3,11,389|78,1,0,2,8,97|97,8,2,3,1,78", info);
		
		Map<String,Integer> result2 = info2.getMissCountResult();
		System.out.println("result ==== "+result2.get("ON-1"));		
	}
}
