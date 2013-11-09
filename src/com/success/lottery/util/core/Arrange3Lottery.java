/**
 * Title: Arrange3.java
 * @Package com.success.lottery.util.core
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-3-26 下午09:48:04
 * @version V1.0
 */
package com.success.lottery.util.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
 * Arrange3Lottery.java
 * Arrange3Lottery
 * 排列3处理类
 * @author gaoboqin
 * 2010-3-26 下午09:48:04
 *
 */
public class Arrange3Lottery extends LotteryAbstractTool implements LotteryInterf {
	
	private static Map<String,String> bindAll;//全部的
	private static Map<String,String> bindSimple;//只需要按照规则校验校验
	private static Map<String,String> bindDanTuo;//需要按照规则校验并需要校验胆拖
	private static Map<String,String> bindWeiRepeat;//需要按照规则校验并需要校验每一个位置重复的
	private static Map<String,String> bindAllWeiRepeatNum;//需要按照规则校验并需要校验所有位置重复的次数
	private static Map<String,String> bindAllWeiRepeat;//需要按照规则校验并需要校验所有位置重复
	
	private static Map<String,String> winRules;
	private static Map<String,List<String>> spanMap;//定义跨度表
	
	static {
		winRules = new HashMap<String,String>();
		winRules.put("d1", "1");//直选奖
		winRules.put("g3", "2");//组3奖
		winRules.put("g6", "3");//组6奖
	}
	
	/**
	 * d+数字表示直选的跨度，允许重复与不重复的
	 * 3g+数字表示组3的跨度，有2个数字相同的跨度
	 * 6g+数字表示组6的跨度，要求跨度的3各数字各不相同
	 * 为了统一处理，将组3也设置0跨度，跨度数组设置为空,组6没有0和1跨度，
	 */
	static {
		spanMap = new HashMap<String,List<String>>();
		spanMap.put("d0", Arrays.asList(new String[] {"000","111","222","333","444","555","666","777","888","999"}));
		spanMap.put("d1", Arrays.asList(new String[] {"001","011","112","122","223","233","334","445","455","556","566","667","677","778","788","889","899"}));
		//组3
		spanMap.put("3g0", Arrays.asList(new String[] {}));
		spanMap.put("3g1", Arrays.asList(new String[] {"001","011","112","122","223","233","334","445","455","556","566","667","677","778","788","889","899"}));
		spanMap.put("3g2", Arrays.asList(new String[] {"022", "113","133", "224", "244", "335", "355","446","466","557","577", "668", "688", "779","799"}));
		spanMap.put("3g3", Arrays.asList(new String[] {"003","033", "114","144", "225", "255", "336","366", "447", "477", "558", "588", "669","699"}));
		spanMap.put("3g4", Arrays.asList(new String[] {"004", "044", "115", "155", "226","266", "337",  "377", "448", "488", "559","599"}));
		spanMap.put("3g5", Arrays.asList(new String[] {"005", "055", "116", "166", "227",  "277", "338","388", "449","499"}));
		spanMap.put("3g6", Arrays.asList(new String[] {"006", "066", "117", "177", "228","288", "339","399"}));
		spanMap.put("3g7", Arrays.asList(new String[] {"007","077", "118","188", "229", "299"}));
		spanMap.put("3g8", Arrays.asList(new String[] {"008", "088", "119","199"}));
		spanMap.put("3g9", Arrays.asList(new String[] {"009","099"}));
		//组6
		spanMap.put("6g0", Arrays.asList(new String[] {}));
		spanMap.put("6g1", Arrays.asList(new String[] {}));
		spanMap.put("6g2", Arrays.asList(new String[] {"012","123", "234","345","456","567", "678","789"}));
		spanMap.put("6g3", Arrays.asList(new String[] {"013", "023", "124", "134", "235", "245", "346", "356","457", "467", "568","578", "679", "689"}));
		spanMap.put("6g4", Arrays.asList(new String[] {"014", "024", "034", "125", "135", "145","236", "246", "256", "347", "357", "367", "458","468", "478", "569", "579", "589"}));
		spanMap.put("6g5", Arrays.asList(new String[] {"015", "025", "035", "045","126", "136", "146", "156", "237", "247", "257", "267", "348", "358", "368","378", "459", "469", "479", "489"}));
		spanMap.put("6g6", Arrays.asList(new String[] {"016", "026", "036", "046", "056","127", "137", "147","157", "167", "238", "248", "258", "268", "278","349", "359", "369", "379", "389"}));
		spanMap.put("6g7", Arrays.asList(new String[] {"017", "027", "037", "047", "057", "067","128", "138","148", "158", "168", "178", "239", "249", "259", "269", "279","289"}));
		spanMap.put("6g8", Arrays.asList(new String[] {"018", "028", "038", "048", "058", "068", "078","129","139", "149", "159", "169", "179", "189"}));
		spanMap.put("6g9", Arrays.asList(new String[] {"019", "029", "039", "049", "059", "069", "079", "089"}));
	}

	/**
	 *Title: 
	 *Description: 
	 */
	public Arrange3Lottery() {
		super();
		bindAll = setBindAll();
		bindSimple = setBindSimple();
		bindDanTuo = setBindDanTuo();
		bindWeiRepeat = setBindWeiRepeat();
		bindAllWeiRepeatNum = setBindAllWeiRepeatNum();
		bindAllWeiRepeat = setBindAllWeiRepeat();
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
		if (ARRANGE_3_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
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
		TreeMap<String,String[]> arrangeThreeWinResult = info.getArrangeThreeWinResult();
		String result = "A1|A2|A3";
		for(Map.Entry<String, String[]> entry : arrangeThreeWinResult.entrySet()){
			StringBuffer sb = new StringBuffer();
			String key = entry.getKey();
			sb.append(key).append("-");
			String [] value = entry.getValue();
			if(value != null && value.length == 2){
				sb.append(value[0]).append("&").append(value[1]);
			}else{
				sb.append("0").append("&").append("0");
			}
			result = result.replaceAll("A"+key, sb.toString());
		}
		result = result.replaceAll("A1", "").replaceAll("A2", "").replaceAll("A3", "");
		return result;
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
		TreeMap<String,String[]> arrangeThreeWinResult = new TreeMap<String,String[]>();
		String [] groupArr = winResult.split("\\|",-1);
		for(String group : groupArr){
			String [] oneGroup = group.split("-");
			if(oneGroup != null && oneGroup.length == 2){
				String key = oneGroup[0];
				String [] value = oneGroup[1].split("&");
				arrangeThreeWinResult.put(key, value);
			}
		}
		info.setArrangeThreeWinResult(arrangeThreeWinResult);
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
		String result = mergeListToStr(info.getArrangeThreeLotteryResult());
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
		
		ArrayList<String> arrangeThreeLotteryResult = (ArrayList<String>)splitStrByLen(lotteryResult,1);
		info.setArrangeThreeLotteryResult(arrangeThreeLotteryResult);
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
	public String lotterySplit(String playType, String betType, String betCode)
			throws LotteryUnDefineException, Exception {
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
		if (ARRANGE_3_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			List<String> singleBet = Arrays.asList(splitGroup(betCode,
					ARRANGE_3_BET_GROUP_SEPARATOR, false));
			if (ARRANGE_3_BETTYPE_0.equals(betType)) {// 直选单式
				toSingle = singleBet;
			} else if (ARRANGE_3_BETTYPE_1.equals(betType)) {// 直选复式
				toSingle = combineDuplex(betCode, ARRANGE_3_BET_GROUP_SEPARATOR,"\\*", 1);
			} else if (ARRANGE_3_BETTYPE_2.equals(betType)) {
				toSingle = direct2ToSingle(singleBet);
			} else if (ARRANGE_3_BETTYPE_3.equals(betType)) {
				toSingle = singleBet;
			} else if (ARRANGE_3_BETTYPE_4.equals(betType)) {
				toSingle = group6_4ToSingle(singleBet);
			} else if (ARRANGE_3_BETTYPE_5.equals(betType)) {
				toSingle = group3_5ToSingle(singleBet);
			} else if (ARRANGE_3_BETTYPE_6.equals(betType)) {
				toSingle = group_3_6ToSingle(singleBet);
			} else if (ARRANGE_3_BETTYPE_7.equals(betType)) {
				toSingle = duplexToSingle(singleBet);
			} else if (ARRANGE_3_BETTYPE_8.equals(betType)) {
				toSingle = direct8ToSingle(singleBet);
			} else if (ARRANGE_3_BETTYPE_9.equals(betType)) {
				toSingle = group3_9ToSingle(singleBet);
			} else if (ARRANGE_3_BETTYPE_10.equals(betType)) {
				toSingle = group6_10ToSingle(singleBet);
			} else if (ARRANGE_3_BETTYPE_11.equals(betType)) {
				toSingle = direct11ToSingle(singleBet);
			} else if (ARRANGE_3_BETTYPE_12.equals(betType)) {
				toSingle = group3_12ToSingle(singleBet);
			} else if (ARRANGE_3_BETTYPE_13.equals(betType)) {
				toSingle = group6_13ToSingle(singleBet);
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
			String lotteryResult, String winReult, String betCode)
			throws LotteryUnDefineException, Exception {
		if (ARRANGE_3_PLAYTYPE_NO_ADDITIONAL.equals(playType)) {
			return arrange3Prize(playType, betType, lotteryResult, winReult,
					betCode);
		} else {
			throw new LotteryUnDefineException("未找到玩法：" + playType);
		}
	}
	/**
	 * 
	 * 
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
	private List<List<String>> arrange3Prize(String playType,String betType, String lotteryResult,
			String winResult, String betCode) throws LotteryUnDefineException,
			Exception {
		List<String> singleBet = Arrays.asList(splitGroup(betCode,ARRANGE_3_BET_GROUP_SEPARATOR, false));
		TreeMap<String,String[]> winResultMap = splitWinResult(winResult,new LotteryInfo()).getArrangeThreeWinResult();//奖金结果
		ArrayList<String> lotteryResultColl = splitLotteryResult(lotteryResult,new LotteryInfo()).getArrangeThreeLotteryResult();
		if (ARRANGE_3_BETTYPE_0.equals(betType)) {//直选单式
			return directSinglePrize(lotteryResultColl, winResultMap, singleBet);
		} else if (ARRANGE_3_BETTYPE_1.equals(betType)) {//直选复式
			return directDuplexPrize(lotteryResultColl,winResultMap,betCode);
		}else if (ARRANGE_3_BETTYPE_2.equals(betType)) {
			return direct2Prize(lotteryResultColl,winResultMap,singleBet);
		}else if (ARRANGE_3_BETTYPE_3.equals(betType)) {
			return groupSingle_3_6_Prize(lotteryResultColl,winResultMap,singleBet);
		}else if (ARRANGE_3_BETTYPE_4.equals(betType)) {
			return group6_4Prize(lotteryResultColl,winResultMap,singleBet);
		}else if (ARRANGE_3_BETTYPE_5.equals(betType)) {
			return group3_5Prize(lotteryResultColl,winResultMap,singleBet);
		}else if (ARRANGE_3_BETTYPE_6.equals(betType)) {
			return groupHeZhi_3_6_Prize(lotteryResultColl,winResultMap,singleBet);
		}else if (ARRANGE_3_BETTYPE_7.equals(betType)) {
			return direct7Prize(lotteryResultColl,winResultMap,singleBet);
		}else if (ARRANGE_3_BETTYPE_8.equals(betType)) {
			return direct8Prize(lotteryResultColl,winResultMap,singleBet);
		}else if (ARRANGE_3_BETTYPE_9.equals(betType)) {
			return group3_9Prize(lotteryResultColl,winResultMap,singleBet);
		}else if (ARRANGE_3_BETTYPE_10.equals(betType)) {
			return group6_10Prize(lotteryResultColl,winResultMap,singleBet);
		}else if (ARRANGE_3_BETTYPE_11.equals(betType)) {
			return direct11Prize(lotteryResultColl,winResultMap,singleBet);
		}else if (ARRANGE_3_BETTYPE_12.equals(betType)) {
			return group3_12Prize(lotteryResultColl,winResultMap,singleBet);
		}else if (ARRANGE_3_BETTYPE_13.equals(betType)) {
			return group6_13Prize(lotteryResultColl,winResultMap,singleBet);
		} else {
			throw new LotteryUnDefineException("未找到投注方式：" + betType);
		}
	}
	
	/**
	 * 
	 * 
	 * 直选复式兑奖，先拆分为直选单式，在按照直选单式兑奖
	 * @param lotteryResultColl
	 * @param winResultMap
	 * @param betCode
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> directDuplexPrize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, String betCode) {
		List<String> duplexToSingle = combineDuplex(betCode,ARRANGE_3_BET_GROUP_SEPARATOR, "\\*",1);
		return directSinglePrize(lotteryResultColl, winResultMap,duplexToSingle);
	}
	
	/**
	 * 
	 * 
	 * 7-直选组合
	 * @param lotteryResultColl
	 * @param winResultMap
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> direct7Prize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, List<String> singleBet) {
		List<String> duplexToSingle = duplexToSingle(singleBet);
		return directSinglePrize(lotteryResultColl, winResultMap,duplexToSingle);
	}
	/**
	 * 
	 * Title: duplexToSingle<br>
	 * Description: <br>
	 *            7-直选组合转为单式<br>
	 * @param singleBet
	 * @return List<String>
	 */
	private List<String> duplexToSingle(List<String> singleBet){
		List<String> duplexToSingle = new ArrayList<String>();
		for(String single : singleBet){
			duplexToSingle.addAll(arrange(splitStrByLen(single,1),3));
		}
		return duplexToSingle;
	}
	
	/**
	 * 
	 * 2-直选和值
	 * @param lotteryResultColl
	 * @param winResultMap
	 * @param singleBet 5^7^9
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> direct2Prize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, List<String> singleBet) {
		List<String> direct2ToSingle = direct2ToSingle(singleBet);
		return directSinglePrize(lotteryResultColl, winResultMap,direct2ToSingle);
	}
	/**
	 * 
	 * Title: direct2ToSingle<br>
	 * Description: <br>
	 *            2-直选合值转单式<br>
	 * @param singleBet
	 * @return List<String>
	 */
	private List<String> direct2ToSingle(List<String> singleBet){
		List<String> direct2ToSingle = new ArrayList<String>();
		for(String single : singleBet){
			direct2ToSingle.addAll(combineHeZhi(single));
		}
		return direct2ToSingle;
	}
	/**
	 * 
	 * 8-直选组合胆拖胆拖 拖码取组合，（胆码+拖码组合）再全排列
	 * @param lotteryResultColl
	 * @param winResultMap
	 * @param singleBet
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> direct8Prize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, List<String> singleBet) {
		List<String> direct8ToSingle = direct8ToSingle(singleBet);
		return directSinglePrize(lotteryResultColl, winResultMap,direct8ToSingle);
	}
	/**
	 * 
	 * Title: direct8ToSingle<br>
	 * Description: <br>
	 *            8-直选组合胆拖胆拖转单式<br>
	 * @param singleBet
	 * @return List<String>
	 */
	private List<String> direct8ToSingle(List<String> singleBet){
		List<String> direct8ToSingle = new ArrayList<String>();
		for(String single : singleBet){
			List<String> danTuoCombine = super.combineDanTuo(single, 3, 1, "\\*");
			List<String> danTuoArrange = super.arrange(danTuoCombine, 3);
			direct8ToSingle.addAll(danTuoArrange);
		}
		return direct8ToSingle;
	}
	/**
	 * 
	 * Title: direct11Prize
	 * Description:  11-直选跨度 取出跨度列表后再全排列
	 * @param lotteryResultColl
	 * @param winResultMap
	 * @param singleBet
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> direct11Prize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, List<String> singleBet) {
		List<String> direct11ToSingle = direct11ToSingle(singleBet);
		return directSinglePrize(lotteryResultColl, winResultMap,direct11ToSingle);
	}
	/**
	 * 
	 * Title: direct11ToSingle<br>
	 * Description: <br>
	 *            11-直选跨度转单式<br>
	 * @param singleBet
	 * @return List<String>
	 */ 
	private List<String> direct11ToSingle(List<String> singleBet){
		List<String> direct11ToSingle = new ArrayList<String>();
		for(String single : singleBet){
			List<String> kuaDu = new ArrayList<String>();
			if(Integer.parseInt(single) >=2 && Integer.parseInt(single) <=9){
				kuaDu.addAll(spanMap.get("3g"+single));
				kuaDu.addAll(spanMap.get("6g"+single));
			}else if(Integer.parseInt(single) >=0 && Integer.parseInt(single) <=1){
				kuaDu.addAll(spanMap.get("d"+single));
			}
			direct11ToSingle.addAll(super.arrange(kuaDu, 3));
		}
		return direct11ToSingle;
	}
	
	/**
	 * 
	 * Title: group3_11Prize
	 * Description:  12-组3跨度 直接取出组3的跨度组合即为组3的单式投注
	 * @param lotteryResultColl
	 * @param winResultMap
	 * @param singleBet
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> group3_12Prize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, List<String> singleBet) {
		List<String> group3_12ToSingle = group3_12ToSingle(singleBet);
		return group3SinglePrize(lotteryResultColl, winResultMap,group3_12ToSingle);
	}
	/**
	 * 
	 * Title: group3_12ToSingle<br>
	 * Description: <br>
	 *            12-组3跨度转单式<br>
	 * @param singleBet
	 * @return List<String>
	 */
	private List<String> group3_12ToSingle(List<String> singleBet){
		List<String> group3_12ToSingle = new ArrayList<String>();
		for(String single : singleBet){
			group3_12ToSingle.addAll(spanMap.get("3g"+single));
		}
		return group3_12ToSingle;
	}
	
	/**
	 * 
	 * Title: group6_13Prize
	 * Description:  13-组6跨度，直接取出组六的跨度即可
	 * @param lotteryResultColl
	 * @param winResultMap
	 * @param singleBet
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> group6_13Prize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, List<String> singleBet) {
		List<String> group6_13ToSingle =group6_13ToSingle(singleBet);
		return group6SinglePrize(lotteryResultColl, winResultMap,group6_13ToSingle);
	}
	/**
	 * 
	 * Title: group6_13ToSingle<br>
	 * Description: <br>
	 *            13-组6跨度转单式<br>
	 * @param singleBet
	 * @return List<String>
	 */
	private List<String> group6_13ToSingle(List<String> singleBet){
		List<String> group6_13ToSingle = new ArrayList<String>();
		for(String single : singleBet){
			group6_13ToSingle.addAll(spanMap.get("6g"+single));
		}
		return group6_13ToSingle;
	}
	/**
	 * 
	 * Title: group3_5Prize
	 * Description:   5-组3复式，取出号码序列中的任意2个组合，再对组合中的2位数字拼成3位数字
	 * @param lotteryResultColl
	 * @param winResultMap
	 * @param singleBet
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> group3_5Prize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, List<String> singleBet) {
		List<String> group3_5ToSingle = group3_5ToSingle(singleBet);
		return group3SinglePrize(lotteryResultColl, winResultMap,group3_5ToSingle);
	}
	/**
	 * 
	 * Title: group3_5ToSingle<br>
	 * Description: <br>
	 *            5-组3复式转为单式<br>
	 * @param singleBet
	 * @return List<String>
	 */
	private List<String> group3_5ToSingle(List<String> singleBet){
		List<String> group3_5ToSingle = new ArrayList<String>();
		for(String single : singleBet){
			List<String> combineTo2Wei = super.combineToList(Arrays.asList(single.split("\\*")),2);
			for(String oneCombine : combineTo2Wei){
				group3_5ToSingle.add(super.mergeListToStrBySign(super.splitStrByLen(oneCombine + oneCombine.substring(0, 1),1),"*"));
				group3_5ToSingle.add(super.mergeListToStrBySign(super.splitStrByLen(oneCombine + oneCombine.substring(1),1),"*"));
				
			}
		}
		return group3_5ToSingle;
	}
	
	/**
	 * 
	 * Title: group6_4Prize
	 * Description:  4-组选六复式 ,直接取出3个数的组合
	 * @param lotteryResultColl
	 * @param winResultMap
	 * @param singleBet
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> group6_4Prize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, List<String> singleBet) {
		List<String> group6_4ToSingle =group6_4ToSingle(singleBet);
		return group6SinglePrize(lotteryResultColl, winResultMap,group6_4ToSingle);
	}
	/**
	 * 
	 * Title: group6_4ToSingle<br>
	 * Description: <br>
	 *            4-组选六复式转单式<br>
	 * @param singleBet
	 * @return List<String>
	 */
	private List<String> group6_4ToSingle(List<String> singleBet){
		List<String> group6_4ToSingle = new ArrayList<String>();
		for(String single : singleBet){
			List<String> combineTo3Wei = super.combineToList(Arrays.asList(single.split("\\*")),3);
			group6_4ToSingle.addAll(this.formatSingleBet(combineTo3Wei));
		}
		return group6_4ToSingle;
	}
	/**
	 * 
	 * Title: formatSingleBet<br>
	 * Description: <br>
	 *              <br>把号码转化为用*分割的格式
	 * @param obj
	 * @return
	 */
	private List<String> formatSingleBet(List<String> obj){
		List<String> result = new ArrayList<String>();
		if(obj != null && !obj.isEmpty()){
			for(int i = 0 ; i < obj.size() ; i++){
				String one = obj.get(i);
				result.add(super.mergeListToStrBySign(super.splitStrByLen(one,1),"*"));
			}
		}
		return result;
	}
	
	/**
	 * 
	 * Title: group3_9Prize
	 * Description:  9-组选三胆拖 ,从拖码选择一位与胆码合并，再将合并后的拖码转为3位
	 * @param lotteryResultColl
	 * @param winResultMap
	 * @param singleBet
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> group3_9Prize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, List<String> singleBet) {
		List<String> group3_9ToSingle = group3_9ToSingle(singleBet);
		return group3SinglePrize(lotteryResultColl, winResultMap,group3_9ToSingle);
	}
	/**
	 * 
	 * Title: group3_9ToSingle<br>
	 * Description: <br>
	 *             9-组选三胆拖转单式<br>
	 * @param singleBet
	 * @return List<String>
	 */
	private List<String> group3_9ToSingle(List<String> singleBet){
		List<String> group3_9ToSingle = new ArrayList<String>();
		for(String single : singleBet){//1*23
			String[] danTuoArr = single.split("\\*");
			String danStr = danTuoArr[0];
			List<String> tuoList = super.splitStrByLen(danTuoArr[1], 1); 
			for(String tuoStr : tuoList){
				group3_9ToSingle.add(danStr + tuoStr + tuoStr);
				group3_9ToSingle.add(danStr + tuoStr + danStr);
			}
		}
		return group3_9ToSingle;
	}
	
	/**
	 * 
	 * Title: group6_10Prize
	 * Description:  10-组选六胆拖 先取拖码需要长度的数组集合，再胆码+拖码
	 * @param lotteryResultColl
	 * @param winResultMap
	 * @param singleBet
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> group6_10Prize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, List<String> singleBet) {
		List<String> group6_10ToSingle = group6_10ToSingle(singleBet);
		return group6SinglePrize(lotteryResultColl, winResultMap,group6_10ToSingle);
	}
	/**
	 * 
	 * Title: group6_10ToSingle<br>
	 * Description: <br>
	 *            10-组选六胆拖转单式<br>
	 * @param singleBet
	 * @return List<String>
	 */
	private List<String> group6_10ToSingle(List<String> singleBet){
		List<String> group6_10ToSingle = new ArrayList<String>();
		for(String single : singleBet){//12*34
			String[] danTuoArr = single.split("\\*");
			String danStr = danTuoArr[0];
			List<String> tuoList = null;
			if(danStr.length() == 2){
				tuoList = super.splitStrByLen(danTuoArr[1], 1);
			}else if(danStr.length() == 1){
				tuoList = super.combineToList(super.splitStrByLen(danTuoArr[1], 1), 2);
			}
			for(String tuoStr : tuoList){
				group6_10ToSingle.add(danStr + tuoStr);
			}
		}
		return group6_10ToSingle;
	}
	
	/**
	 * 
	 * 3-组选单式 投注的三个数中，其中两个相同按组选三处理，3个相同也按照组3处理,三个数各不相同，按组选六处理
	 * 修改为 3个相同的认为不合格,不算做有效的一注号码
	 * @param lotteryResultColl
	 * @param winResultMap
	 * @param singleBet
	 * @return List<List<String>>    返回类型
	 */
	private List<List<String>> groupSingle_3_6_Prize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, List<String> singleBet) {
		List<List<String>> result = new ArrayList<List<String>>();
		List<String> group_3_6ToGroup3 = new ArrayList<String>();
		List<String> group_3_6ToGroup6 = new ArrayList<String>();
		for(String single : singleBet){
			String singleFormat = mergeListToStr(Arrays.asList(single.split("\\*")));
			int repeatNum = super.checkRepeatNum(singleFormat, 1);
			if(repeatNum == 1){//有2个重复
				group_3_6ToGroup3.add(single);
			}else if(repeatNum == 0){//3个都不重复
				group_3_6ToGroup6.add(single);
			}
		}
		result.addAll(group3SinglePrize(lotteryResultColl, winResultMap,group_3_6ToGroup3));
		result.addAll(group6SinglePrize(lotteryResultColl, winResultMap,group_3_6ToGroup6));
		return result;
	}
	/**
	 * 
	 * Title: groupHeZhi_3_6_Prize<br>
	 * Description: 6-组选合值 其中两个相同按组选三处理，3个相同也按照组3处理,三个数各不相同，按组选六处理<br>
	 * @param lotteryResultColl
	 * @param winResultMap
	 * @param singleBet
	 * @return List<List<String>> 
	 */
	private List<List<String>> groupHeZhi_3_6_Prize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, List<String> singleBet) {
		List<List<String>> result = new ArrayList<List<String>>();
		Map<String,List<String>> innerResult = dealGroup_3_6(singleBet);
		result.addAll(group3SinglePrize(lotteryResultColl, winResultMap,innerResult.get("_3_6_3")));
		result.addAll(group6SinglePrize(lotteryResultColl, winResultMap,innerResult.get("_3_6_6")));
		return result;
	}
	/**
	 * 
	 * Title: group_3_6ToSingle<br>
	 * Description: <br>
	 *            6-组选合值转为单式<br>
	 * @param singleBet
	 * @return List<String>
	 */
	private List<String> group_3_6ToSingle(List<String> singleBet){
		List<String> group_3_6ToSingle = new ArrayList<String>();
		Map<String,List<String>> innerResult = dealGroup_3_6(singleBet);
		group_3_6ToSingle.addAll(innerResult.get("_3_6_3"));
		group_3_6ToSingle.addAll(innerResult.get("_3_6_6"));
		return group_3_6ToSingle;
	}
	/**
	 * 
	 * Title: dealGroup_3_6<br>
	 * Description: <br>
	 *            处理组选和值<br>
	 * @param singleBet
	 * @return Map<String,List<String>>
	 */
	private Map<String,List<String>> dealGroup_3_6(List<String> singleBet){
		Map<String,List<String>> resultMap = new HashMap<String,List<String>>();
		List<String> group_3_6ToGroup3 = new ArrayList<String>();
		List<String> group_3_6ToGroup6 = new ArrayList<String>();
		for(String single : singleBet){
			List<String> combineHeZhi = combineHeZhi(single);
			List<String> noRepeatHeZhi = wipeOffRepeat(combineHeZhi,"*");//组选号码是无顺序的，因此去掉合值中重复的排列数据
			for(String oneCombine : noRepeatHeZhi){//舍弃3个数字都重复的
				String singleFormat = mergeListToStr(Arrays.asList(oneCombine.split("\\*")));
				int repeatNum = super.checkRepeatNum(singleFormat, 1);
				if(repeatNum == 0){//3个数字都不重复,组6
					group_3_6ToGroup6.add(oneCombine);
				}else if(repeatNum == 1){//2个数字重复，组3
					group_3_6ToGroup3.add(oneCombine);
				}
			}
		}
		resultMap.put("_3_6_3", group_3_6ToGroup3);
		resultMap.put("_3_6_6", group_3_6ToGroup6);
 		return resultMap;
	}
	/**
	 * 
	 * Title: wipeOffRepeat<br>
	 * Description: <br>
	 *            去掉重复的字符串<br>
	 * @param targetList
	 * @param sign
	 * @return List<String>
	 */
	private List<String> wipeOffRepeat(List<String> targetList,String sign){
		List<String> result = new ArrayList<String>();
		if(targetList == null || targetList.size() == 0){
			return targetList;
		}
		
		for(String obj : targetList){
			String [] tmp = obj.split("\\"+sign);
			Arrays.sort(tmp);
			String sortStr = super.mergeListToStrBySign(Arrays.asList(tmp), sign);
			if(!result.contains(sortStr)){
				result.add(sortStr);
			}
			
		}
		return result;
	}
	/**
	 * 
	 * Title: combineDuplex
	 * Description:  2-直选和值
	 * @param betCode 5^7^9
	 * @param groupSign 
	 * @param weiSign 
	 * @return List<String>    返回类型
	 * @throws
	 */
	private List<String> combineHeZhi(String betCode){
		List<String> result = new ArrayList<String>();
		int betCodeInt = Integer.parseInt(betCode);
		Stack<List<String>> oneStack = new Stack<List<String>>();
		List<String> tong1 = new ArrayList<String>();
		List<String> tong2 = new ArrayList<String>();
		List<String> tong3 = new ArrayList<String>();
		for(int k = 0 ; k <=9 ;k++){
			String StrK = String.valueOf(k);
			tong1.add(StrK);
			tong2.add(StrK);
			tong3.add(StrK);
		}
		
		oneStack.push(tong3);
		oneStack.push(tong2);
		oneStack.push(tong1);
		while(oneStack.size() > 1){
			oneStack.push(combineTwo(oneStack.pop(),oneStack.pop(),"*"));
		}
		List<String> combineList = oneStack.pop();
		String[] tmp = null;
		for(String one : combineList){
			tmp = one.split("\\*");
			if((Integer.parseInt(tmp[0]) + Integer.parseInt(tmp[1]) + Integer.parseInt(tmp[2])) == betCodeInt){
				result.add(one);
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * Title: directSinglePrize
	 * Description:   直选单式兑奖 3个号码按顺序完全匹配
	 * @param playType
	 * @param lotteryResult
	 * @param winResult
	 * @param singleBet
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> directSinglePrize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, List<String> singleBet) {
		List<List<String>> prizeResult = new ArrayList<List<String>>();
		String lotteryResultBySign = mergeListToStrBySign(lotteryResultColl,
				"*");
		for (String single : singleBet) {
			if (lotteryResultBySign.equals(single)) {
				List<String> innerList = new ArrayList<String>();
				innerList.add(single);
				innerList.add(winRules.get("d1"));
				innerList.add((winResultMap.get(winRules.get("d1")))[1]);
				innerList.add("0");
				prizeResult.add(innerList);
			}
		}
		return prizeResult;
	}
	/**
	 * 
	 * Title: group3SinglePrize
	 * Description:  组3单式兑奖 3个号码中2个匹配 顺序不限
	 * @param playType
	 * @param lotteryResultColl
	 * @param winResultMap
	 * @param singleBet
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> group3SinglePrize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, List<String> singleBet) {
		List<List<String>> prizeResult = new ArrayList<List<String>>();
		
		int repeatNum = super.checkRepeatNum(super.mergeListToStr(lotteryResultColl), 1);
		if(repeatNum != 1){//没有2个重复的开奖号码，则组3的不可能中奖
			return prizeResult;
		}
		
		for(String single : singleBet){
			List<String> singleArr = Arrays.asList(single.split("\\*"));//将每注的号码拆分为数组
			String hitNum = hitHaoNum(singleArr,lotteryResultColl);
			if(hitNum.equals("3")){//对上3个才中奖
				List<String> innerList = new ArrayList<String>();
				innerList.add(single);
				innerList.add(winRules.get("g3"));
				innerList.add((winResultMap.get(winRules.get("g3")))[1]);
				innerList.add("0");//保证所有返回的集合统一有追加项，避免使用时数组越界
				prizeResult.add(innerList);
			}
		}

		return prizeResult;
	}
	
	/**
	 * 
	 * Title: group6SinglePrize
	 * Description:  组6兑奖，（1）开奖号码无重复，（2）3个号码全匹配，无顺序限制
	 * @param playType
	 * @param lotteryResultColl
	 * @param winResultMap
	 * @param singleBet
	 * @return List<List<String>>    返回类型
	 * @throws
	 */
	private List<List<String>> group6SinglePrize(ArrayList<String> lotteryResultColl,
			TreeMap<String, String[]> winResultMap, List<String> singleBet) {
		List<List<String>> prizeResult = new ArrayList<List<String>>();
		String lotteryResultStr = mergeListToStr(lotteryResultColl);
		if(!checkRepeat(lotteryResultStr,1)){//如果开奖号码有重复，则组6奖无号码中奖
			return prizeResult;
		}
		for(String single : singleBet){
			List<String> singleArr = Arrays.asList(single.split("\\*"));//将每注的号码拆分为数组
			String hitNum = hitHaoNum(singleArr,lotteryResultColl);
			if(hitNum.equals("3")){//对上3个才中奖
				List<String> innerList = new ArrayList<String>();
				innerList.add(single);
				innerList.add(winRules.get("g6"));
				innerList.add((winResultMap.get(winRules.get("g6")))[1]);
				innerList.add("0");//保证所有返回的集合统一有追加项，避免使用时数组越界
				prizeResult.add(innerList);
			}
		}

		return prizeResult;
	}
	/**
	 * 
	 * Title: checkBet
	 * Description: 校验排列3的投注注码格式是否正确
	 * @param betType
	 * @param betString
	 * @throws LotteryUnDefineException
	 * @throws Exception    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	private boolean checkBet(String betType,String betString) throws LotteryUnDefineException,Exception{
		if(bindSimple.containsKey(betType)){
			return checkSimple(bindAll.get(betType),betString);
		}else if(bindDanTuo.containsKey(betType)){
			return check(bindAll.get(betType),betString);
		}else if(bindWeiRepeat.containsKey(betType)){
			return checkWeiRepeat(bindAll.get(betType),betString);
		}else if(bindAllWeiRepeatNum.containsKey(betType)){
			return checkAllWeiRepeatNum(bindAll.get(betType),betString);
		}else if(bindAllWeiRepeat.containsKey(betType)){
			return checkAllWeiRepeat(bindAll.get(betType),betString);
		}else{
			throw new LotteryUnDefineException("未找到投注方式："+betType);
		}
	}
	private boolean checkSimple(String betRegu,String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,ARRANGE_3_BET_GROUP_SEPARATOR,true);
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
	 * Title: check
	 * Description: 排列3的胆拖方式的校验
	 * @param betRegu
	 * @param betString
	 * @return boolean    返回类型
	 * @throws
	 */
	private boolean check(String betRegu,String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,ARRANGE_3_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(betRegu,group)){
				checkResult = false;
				break;
			}else{
				if(!checkDanTuoRepeat(group)){
					checkResult = false;
					break;
				}
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
	private boolean checkWeiRepeat(String betRegu,String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,ARRANGE_3_BET_GROUP_SEPARATOR,true);
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
	 * Title: checkAllWeiRepeat<br>
	 * Description: <br>
	 *            先按照定义的规则校验再校验数字重复的位置数<br>
	 * @param betRegu
	 * @param betString
	 * @return  boolean
	 */
	private boolean checkAllWeiRepeatNum(String betRegu,String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,ARRANGE_3_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(betRegu,group)){
				checkResult = false;
				break;
			}else{
				if(super.checkRepeatNum(group.replace("*", ""),1) == 2){//3个数字都相同
					checkResult = false;
					break;
				}
			}
		}
		return checkResult;
	}
	/**
	 * 
	 * Title: <br>
	 * Description: <br>先按照定义的规则校验再校验所有位置的数字都不能重复
	 * @param betRegu
	 * @param betString
	 * @return boolean
	 */
	private boolean checkAllWeiRepeat(String betRegu,String betString){
		boolean checkResult = true;
		String [] betGroupByNum = splitGroup(betString,ARRANGE_3_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(betRegu,group)){
				checkResult = false;
				break;
			}else{
				if(!super.checkRepeat(group.replace("*", ""),1)){//有重复的数字
					checkResult = false;
					break;
				}
			}
		}
		return checkResult;
	}
	
	/**
	 * 
	 * Title: checkDanTuoRepeat
	 * Description: (这里用一句话描述这个方法的作用)
	 * @param target
	 * @return boolean    返回类型
	 * @throws
	 */
	private boolean checkDanTuoRepeat(String target) {
		boolean checkResult = true;

		String[] danTuoGroup = target.split("\\*");// 此处已经经过校验，前去和后区都包含胆码和拖码，所以数组为2个值
		List<String> dan = splitStrByLen(danTuoGroup[0], 1);
		List<String> tuo = splitStrByLen(danTuoGroup[1], 1);
		for (String singleTuo : tuo) {
			if (dan.contains(singleTuo)) {
				checkResult = false;
				break;
			}
		}

		return checkResult;
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
		String [] betGroupByNum = splitGroup(betString,ARRANGE_3_BET_GROUP_SEPARATOR,true);
		for(String group : betGroupByNum){
			if(!checkRules(ARRANGE_3_BET_0_REGULAR,group)){
				checkResult = false;
				break;
			}
		}
		if(checkResult){
			checkResult = checkBetNum(betString);
		}
		return checkResult;
	}
	
	/**
	 * 
	 * Title: checkBetNum
	 * Description:  校验单式的投注总注数
	 * @param betString
	 * @return boolean    返回类型
	 * @throws
	 */
	private boolean checkBetNum(String betString){
		boolean checkResult = true;
		String [] betArr = splitGroup(betString,ARRANGE_3_BET_GROUP_SEPARATOR,false);
		if(betArr.length != 5){
			checkResult = false;
		}
		return checkResult;
	}
	/**
	 * 
	 * Title: setBind
	 * Description: (这里用一句话描述这个方法的作用)
	 * @return Map<String,String>    返回类型
	 * @throws
	 */
	private Map<String,String> setBindAll(){
		Map <String,String> result = new HashMap<String,String>();
		result.put("0", ARRANGE_3_BET_0_REGULAR);
		result.put("1", ARRANGE_3_BET_1_REGULAR);
		result.put("2", ARRANGE_3_BET_2_REGULAR);
		result.put("3", ARRANGE_3_BET_3_REGULAR);
		result.put("4", ARRANGE_3_BET_4_REGULAR);
		result.put("5", ARRANGE_3_BET_5_REGULAR);
		result.put("6", ARRANGE_3_BET_6_REGULAR);
		result.put("7", ARRANGE_3_BET_7_REGULAR);
		result.put("8", ARRANGE_3_BET_8_REGULAR);
		result.put("9", ARRANGE_3_BET_9_REGULAR);
		result.put("10", ARRANGE_3_BET_10_REGULAR);
		result.put("11", ARRANGE_3_BET_11_REGULAR);
		result.put("12", ARRANGE_3_BET_12_REGULAR);
		result.put("13", ARRANGE_3_BET_13_REGULAR);
		return result;
	}
	/**
	 * 
	 * Title: setBind1<br>
	 * Description: <br>
	 *            能根据定义的规则直接校验的<br>
	 * @return Map<String,String>
	 */
	private Map<String,String> setBindSimple(){
		Map <String,String> result = new HashMap<String,String>();
		result.put("0", ARRANGE_3_BET_0_REGULAR);
		result.put("2", ARRANGE_3_BET_2_REGULAR);
		result.put("6", ARRANGE_3_BET_6_REGULAR);
		result.put("7", ARRANGE_3_BET_7_REGULAR);
		result.put("11", ARRANGE_3_BET_11_REGULAR);
		result.put("12", ARRANGE_3_BET_12_REGULAR);
		return result;
	}
	/**
	 * 
	 * Title: setBind2<br>
	 * Description: <br>
	 *            需要按照规则校验并需要校验胆拖的<br>
	 * @return Map<String,String>
	 */
	private Map<String,String> setBindDanTuo(){
		Map <String,String> result = new HashMap<String,String>();
		result.put("8", ARRANGE_3_BET_8_REGULAR);
		result.put("9", ARRANGE_3_BET_9_REGULAR);
		result.put("10", ARRANGE_3_BET_10_REGULAR);
		return result;
	}
	
	/**
	 * 
	 * Title: setBindRepeat<br>
	 * Description: <br>
	 *            需要按照规则校验并需要校验每一个位置重复的<br>
	 * @return Map<String,String>
	 */
	private Map<String,String> setBindWeiRepeat(){
		Map <String,String> result = new HashMap<String,String>();
		result.put("1", ARRANGE_3_BET_1_REGULAR);
		return result;
	}
	
	private Map<String,String> setBindAllWeiRepeatNum(){
		Map <String,String> result = new HashMap<String,String>();
		result.put("3", ARRANGE_3_BET_3_REGULAR);
		return result;
	}
	
	private Map<String,String> setBindAllWeiRepeat(){
		Map <String,String> result = new HashMap<String,String>();
		result.put("4", ARRANGE_3_BET_4_REGULAR);
		result.put("5", ARRANGE_3_BET_5_REGULAR);
		return result;
	}
	

	public String lotteryRandomBetCode(){
		String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		String result = "";
		Random random = new Random();
		result = random.nextInt(10) + "*";
		result = result + random.nextInt(10) + "*";
		result = result + random.nextInt(10);
		return result;
	}

	public String mergeSalesInfoResult(LotteryInfo info) {
		// TODO 自动生成方法存根
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
		if(lotteryResult == null || lotteryResult.length() != 3){
			return null;
		}
		int v100 = Integer.parseInt(lotteryResult.substring(0, 1));
		int v10 = Integer.parseInt(lotteryResult.substring(1, 2));
		int v1 = Integer.parseInt(lotteryResult.substring(2, 3));
		
		int digit100[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int digit10[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int digit1[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int sum[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		
 		if(StringUtils.isBlank(lastMissCount)){
			for(int i = 0; i < 26; i++){
				if(i == (v100 + v10 + v1)){
					sum[i] = 0;
				} else {
					sum[i]++;
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
			String[] ss = lastMissCount.split("\\|");
			if(ss == null || ss.length != 2){
				return null;
			}
			if(ss[1].split(",") == null || ss[1].split(",").length != 26){
				return null;
			}
			if(ss[0].split(";") == null || ss[0].split(";").length != 3){
				return null;
			} else {
				for(int i = 0; i < 3; i++){
					if(ss[0].split(";")[i].split(",") == null || ss[0].split(";")[i].split(",").length != 10){
						return null;
					}
				}
			}
			
			for(int i = 1; i < 27; i++){
				if(i == (v100 + v10 + v1)){
					sum[i - 1] = 0;
				} else {
					sum[i - 1] = Integer.parseInt(ss[1].split(",")[i - 1]);
					sum[i - 1]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v100){
					digit100[i] = 0;
				}else{
					digit100[i] = Integer.parseInt(ss[0].split(";")[0].split(",")[i]);
					digit100[i]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v10){
					digit10[i] = 0;
				}else{
					digit10[i] = Integer.parseInt(ss[0].split(";")[1].split(",")[i]);
					digit10[i]++;
				}
			}
			for(int i = 0; i < 10; i++){
				if(i == v1){
					digit1[i] = 0;
				}else{
					digit1[i] = Integer.parseInt(ss[0].split(";")[2].split(",")[i]);
					digit1[i]++;
				}
			}			
		}
 		String result = "";
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
 		result = result.substring(0, result.length() - 1) + "|";
 		for(int i = 0; i < 26; i++){
 			result = result + sum[i] + ",";
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
			String [] groups = missCount.split("\\|");//直选和和值
			
			if(groups.length == 2){
				//处理直选
				String [] directGroups = groups[0].split(";");//拆出来3个位置
				for(int i = 0; i< directGroups.length;i++){
					String [] innerMiss = directGroups[i].split(",");//拆出每个位置的遗漏号码
					for(int k = 0;k<innerMiss.length;k++){
						missCountResult.put("Z-"+String.valueOf(i+1)+"-"+String.valueOf(k), Integer.parseInt(innerMiss[k]));
					}
				}

				//处理和值
				String [] heZhiGroups = groups[1].split(",");//拆出和值的号码集合
				for(int m = 0 ; m< heZhiGroups.length;m++){
					missCountResult.put("H-"+String.valueOf(m+1), Integer.parseInt(heZhiGroups[m]));
				}
			}
			
 		}
		info.setMissCountResult(missCountResult);
		return info;
	}

	public static void main(String[] args) {
		//Arrange3Lottery a3 = new Arrange3Lottery();
		/*
		List<String> res = a3.combineDirect2("5");
		
		for(String one : res){
			System.out.println("one === "+one);
		}
		*/
		/*
		System.out.println("a  "+a3.spanMap.get("d0").size());
		System.out.println("b  "+a3.spanMap.get("3g0").size());
		System.out.println("c  "+a3.spanMap.get("6g0").size());
		*/
		
		Arrange3Lottery a3 = new Arrange3Lottery();
		//System.out.println(a3.lotteryRandomBetCode().replace("*", ""));
		String betCode = a3.lotteryRandomBetCode().replace("*", "");
		System.out.println(betCode);
		String lastMissCount = a3.getMissCount(betCode, null);
		System.out.println(lastMissCount);
		for(int i = 0; i < 50; i++){
			betCode = a3.lotteryRandomBetCode().replace("*", "");
			lastMissCount = a3.getMissCount(betCode, lastMissCount);
			System.out.println(betCode);
			System.out.println(lastMissCount);
		}
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.success.lottery.util.core.LotteryInterf#isLimitBet(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean isLimitBet(String playType,String betType, String limitNumberArrary,
			String betCode) throws LotteryUnDefineException {
		if(limitNumberArrary==null||("").equals(limitNumberArrary)){
			return false;
		}
		if(betCode==null||("").equals(betCode)){
			throw new LotteryUnDefineException("没找到投注串");
		}
		List<List<List<String>>> listTemp = this.lotteryArrayLimit(betType, limitNumberArrary, betCode);
		if(!listTemp.isEmpty()){
			return true;
		}
		return false;
	}

	/*
	 * 处理限号串含多个限号的情况
	 * 各个限号之间以,号隔开
	 * 其中含单个限号视为特殊情况也可直接调用此方法
	 */
	public List<List<List<String>>> lotteryArrayLimit(String betType,
			String limitNumberArrary, String betCode)
			throws LotteryUnDefineException {
		String[] temp = limitNumberArrary.split(",");
		List<List<List<String>>> listTemp = new ArrayList<List<List<String>>>();
		for(String limitNumber:temp){
			List <List<String>> listLimit = this.lotteryLimit(betType, limitNumber, betCode);
			if(!listLimit.isEmpty()){
				listTemp.add(listLimit);
			}
		}
		return listTemp;
	}

	
	
	/*
	 * 排列三限号的各种投注方式的处理方法
	 */
	private List<List<String>> lotteryLimit(String betType,
			String limitNumber, String betCode) throws LotteryUnDefineException {
		List<String> singleBet = Arrays.asList(splitGroup(betCode,ARRANGE_3_BET_GROUP_SEPARATOR, false));
		ArrayList<String> lotteryResultColl = splitLotteryResult(limitNumber,new LotteryInfo()).getArrangeThreeLotteryResult();
		if (ARRANGE_3_BETTYPE_0.equals(betType)) {//直选单式
			return directSingleLimit(lotteryResultColl, singleBet);
		}else if (ARRANGE_3_BETTYPE_1.equals(betType)) {//直选复式
			return directSingleLimit(lotteryResultColl,
					combineDuplex(betCode,ARRANGE_3_BET_GROUP_SEPARATOR, "\\*",1));
		}else if (ARRANGE_3_BETTYPE_2.equals(betType)) {//直选和值
			return directSingleLimit(lotteryResultColl,direct2ToSingle(singleBet));
		}else if (ARRANGE_3_BETTYPE_3.equals(betType)) {//组选单式
			return directSingleLimitNoorder(lotteryResultColl, singleBet);
		}else if (ARRANGE_3_BETTYPE_4.equals(betType)) {//组选六复式
			return directSingleLimitNoorder(lotteryResultColl,group6_4ToSingle(singleBet));
		}else if (ARRANGE_3_BETTYPE_5.equals(betType)) {//组选三复式
			return  directSingleLimitNoorder(lotteryResultColl,group3_5ToSingle(singleBet));
		}else if (ARRANGE_3_BETTYPE_6.equals(betType)) {//组选合值
			return directSingleLimitNoorder(lotteryResultColl,group_3_6ToSingle(singleBet));
		}else if (ARRANGE_3_BETTYPE_7.equals(betType)) {//直选组合
			return directSingleLimit(lotteryResultColl,duplexToSingle(singleBet));
		}else if (ARRANGE_3_BETTYPE_8.equals(betType)) {//直选组合胆拖
			return directSingleLimit(lotteryResultColl,direct8ToSingle(singleBet));
		}else if (ARRANGE_3_BETTYPE_9.equals(betType)) {//组选三胆拖
			return directSingleLimitNoorder(lotteryResultColl,group3_9ToSingle(singleBet));
		}else if (ARRANGE_3_BETTYPE_10.equals(betType)) {//组选六胆拖
			return directSingleLimitNoorder(lotteryResultColl,group6_10ToSingle(singleBet));
		}else if (ARRANGE_3_BETTYPE_11.equals(betType)) {//直选跨度
			return directSingleLimit(lotteryResultColl,direct11ToSingle(singleBet));
		}else if (ARRANGE_3_BETTYPE_12.equals(betType)) {//组选三跨度
			return directSingleLimitNoorder(lotteryResultColl,group3_12ToSingle(singleBet));
		}else if (ARRANGE_3_BETTYPE_13.equals(betType)) {//组选六跨度
			return directSingleLimitNoorder(lotteryResultColl,group6_13ToSingle(singleBet));
		}else {
			throw new LotteryUnDefineException("未找到投注方式：" + betType);
		}
	}

	/*
	 * 直选单式的有顺序限号匹配算法
	 * 返回含有限号号码的投注串的list
	 */
	private List<List<String>> directSingleLimit(
			ArrayList<String> lotteryResultColl, List<String> singleBet) {
		List<List<String>> prizeResult = new ArrayList<List<String>>();
		String lotteryResultBySign = mergeListToStrBySign(lotteryResultColl,
				"*");
		for (String single : singleBet) {
			if (lotteryResultBySign.equals(single)) {
				List<String> innerList = new ArrayList<String>();
				innerList.add(single);
				prizeResult.add(innerList);
			}
		}
		return prizeResult;
	}
	
	/*
	 * 直选单式的无顺序限号匹配算法
	 * 返回含有限号号码的投注串的list
	 */
	private List<List<String>> directSingleLimitNoorder(
			ArrayList<String> lotteryResultColl,List<String> singleBet) {
		List<List<String>> prizeResult = new ArrayList<List<String>>();
		for(String single : singleBet){
			List<String> singleArr = Arrays.asList(single.split("\\*"));//将每注的号码拆分为数组
			String hitNum = hitHaoNum(singleArr,lotteryResultColl);
			if(hitNum.equals("3")){//对上3个才限号
				List<String> innerList = new ArrayList<String>();
				innerList.add(single);
				innerList.add("0");//保证所有返回的集合统一有追加项，避免使用时数组越界
				prizeResult.add(innerList);
			}
		}

		return prizeResult;
	}
}
