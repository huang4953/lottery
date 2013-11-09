package com.success.lottery.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *  
 * 彩票相关信息类。
 * 
 * @author qgb
 */
public class LotteryInfo{

	/**
	 * 
	 * Integer: playType
	 * String: playTypeName
	 */
	private Map<Integer, String> lotteryPlayMap;//玩法集合
	/**
	 * Integer: playType
	 * Map<Integer, String>: betType, betTypeName
	 */
	private Map<Integer, Map<Integer,String>> lotteryPlayBetMap;//玩法对应的投注方式集合
	
	/**
	 * LotteryTools.getLotteryPlayBetTypeList(int lotteryId,int playType)返回
	 * Integer: betType
	 * String: betTypeName
	 */
	private Map<Integer, String> lotteryBetTypeMapByPlayId;//投注方式集合
	private String lotteryPlayListStr ;//玩法规则字符串
	

	//定义奖金结果集合
	/*
	 * 超级大乐透，TreeMap<String,TreeMap<String,String[]>>，参数意思为：<一等奖到八等奖(用数字1-8表示),<基本奖或追加奖,[注数,金额]>>
	 */
	private  TreeMap<String,TreeMap<String,String[]>>  superWinResult;//超级大乐透，
	/*
	 * 生肖乐,数组长度为2，参数意思为：[注数，金额]
	 */
	private String [] happyZodiacWinResult;//生肖乐
	
	/*
	 * 七星彩 ,TreeMap<String,String[]> 参数分别为:<一等奖到六等奖,[注数,金额]>
	 */
	private TreeMap<String,String[]> sevenColorWinResult;
	
	/*
	 * 排列三 TreeMap<String,String[]> 参数分别为<直选奖、组三奖和组六奖用(分别用1,2,3代表),[注数,金额]>
	 */
	private TreeMap<String,String[]> arrangeThreeWinResult;
	
	/*
	 * 排列五 ,数组长度为2，参数意思为：[注数，金额]
	 */
	private String [] arrangeFiveWinResult;
	
	/*
	 * 胜负彩 TreeMap<String,String[]> 参数分别为<一等奖或二等奖,[注数,金额]>
	 */
	private TreeMap<String,String[]> winOrFailWinResult;
	/*
	 * 任选九场 String [] 参数为[注数,金额]
	 */
	private String [] arbitry9WinResult;
	/*
	 * 6场半全场  String [] 参数为[注数,金额]
	 */
	private String [] half6WinResult;
	/*
	 * 4场进球彩 String [] 参数为[注数,金额]
	 */ 
	private String [] ball4WinResult;
	/*
	 * 十一运夺金<奖项,[注数,金额]>
	 */
	private LinkedHashMap<String,String[]> elevenYunWinResult;
	/*
	 * 快乐扑克
	 */
	
	//定义开奖结果集合
	/*
	 * 超级大乐透 ,TreeMap<String,ArrayList<String>> ，参数为<前去|后区,<号码数组>>,其中前区用1表示，后区用2表示，两位一个号码，不足左补'0'
	 */
	private TreeMap<String,ArrayList<String>> superLotteryResult;
	/*
	 * 生肖乐,ArrayList<String> ，参数为<号码>,应该包含2个号码，两位一个号码，不足左补0
	 */
	private ArrayList<String> happyZodiacLotteryResult;
	/*
	 * 七星彩,ArrayList<String> ，参数为<号码>，应该包含7个号码
	 */
	private ArrayList<String> sevenColorLotteryResult;
	/*
	 * 排列三,ArrayList<String> ，参数为<号码>，应该包含3个号码
	 */
	private ArrayList<String> arrangeThreeLotteryResult;
	/*
	 * 排列五,ArrayList<String> ，参数为<号码>，应该包含5个号码
	 */
	private ArrayList<String> arrangeFiveLotteryResult;
	/*
	 * 胜负彩,ArrayList<String> ，参数为<号码>，应该包含14个号码
	 */
	private ArrayList<String> winOrFailLotteryResult;
	/*
	 * 任选九场,ArrayList<String> ，参数为<号码>，应该包含14个号码
	 */
	private ArrayList<String> arbitry9LotteryResult;
	/*
	 * 6场半全场,ArrayList<String> ，参数为<号码>，应该包含12个号码
	 */
	private ArrayList<String> half6LotteryResult;
	/*
	 * 4场进球彩,ArrayList<String> ，参数为<号码>，应该包含8个号码
	 */
	private ArrayList<String> ball4LotteryResult;//生肖乐
	/*
	 * 十一运夺金,ArrayList<String> ，参数为<号码>，两位一个号码，共五个号码
	 */
	private ArrayList<String> elevenYunLotteryResult;
	/*
	 * 快乐扑克,ArrayList<String> ，参数为<号码>，4位，一位一个号码，号码为2-9或A、J、Q、K，按照黑红梅方顺序
	 */
	private ArrayList<String> happyPokerLotteryResult;
	
	//定义遗漏信息结果集合
	/*
	 * 参数为:<key,value> value为遗漏的次数
	 * key说明：
	 *大乐透 标识-号码,H表示前区,号码为01-35;B-号码,B表示后区,号码为01-12
     *生肖乐 B-号码 号码为01-12
     *七星彩  P-位置-号码 ，其中位置为1-7,号码为0-9
     *排列三 Z-位置-号码 ,Z表示直选,位置为1-3,号码为0-9;  H-号码,H表示和值,号码为1-26
     *排列五 P-位置-号码 ,位置为1-5，号码为0-9
     *十一选五 遗漏可分为12种类型，第一种：任2到任8，B-号码；第一位遗漏，Z1-号码；第二位遗漏，Z2-号码；第三位遗漏，Z3-号码；
     *     前2组选遗漏，Q2-号码；前3组选遗漏，Q3-号码;奇数个数遗漏,ON-个数；偶数个数遗漏,EN-个数；小数个数遗漏,SN-个数；
     *     大数个数遗漏,BN-个数；质数个数遗漏,PN-个数；合数个数遗漏,CN-个数;其中个数范围为0-5
	 */
	private Map<String,Integer> missCountResult;
	
	
	
	
	
	/**
	 * 足彩胜负彩销售信息
	 */
	Map<Integer, Map<String,String>> winOrFailSaleInfo;
	
	/**
	 * 足彩任选九销售信息
	 */
	Map<Integer, Map<String,String>> arbitrary9SaleInfo;
	
	/**
	 * 足彩六场半全场销售信息
	 */
	Map<Integer, Map<String,String>> half6SaleInfo;
	

	/**
	 * 足彩四场进球彩销售信息
	 */
	Map<Integer, Map<String,String>> ball4SaleInfo;

	
	
	private Integer lotteryId;//
	//玩法列表

    /**
     * 玩法格式为1000001.playList=0,不追加&0,单式;1,复式;2,胆拖|1,追加&0,单式;1,复式;2,胆拖<br>
     * 如果玩法格式改变，则需要修改该方法<br>
     * 
     * @param lotteryPlayListStr
     */
	public void setLotteryPlayListStr(String lotteryPlayListStr) {
		this.lotteryPlayListStr = lotteryPlayListStr;//设置从配置文件中取得玩法规则字符串
	}

	public Map<Integer, Map<Integer, String>> getLotteryPlayBetMap() {
		return lotteryPlayBetMap;
	}

	public void setLotteryPlayBetMap(
			Map<Integer, Map<Integer, String>> lotteryPlayBetMap) {
		this.lotteryPlayBetMap = lotteryPlayBetMap;
	}

	public Integer getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Integer lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Map<Integer, String> getLotteryPlayMap() {
		return lotteryPlayMap;
	}

	public void setLotteryPlayMap(Map<Integer, String> lotteryPlayMap) {
		this.lotteryPlayMap = lotteryPlayMap;
	}

	public String getLotteryPlayListStr() {
		return lotteryPlayListStr;
	}

	public Map<Integer, String> getLotteryBetTypeMapByPlayId() {
		return lotteryBetTypeMapByPlayId;
	}

	public void setLotteryBetTypeMapByPlayId(
			Map<Integer, String> lotteryBetTypeMapByPlayId) {
		this.lotteryBetTypeMapByPlayId = lotteryBetTypeMapByPlayId;
	}

	public TreeMap<String, TreeMap<String, String[]>> getSuperWinResult() {
		return superWinResult;
	}

	public void setSuperWinResult(
			TreeMap<String, TreeMap<String, String[]>> superWinResult) {
		this.superWinResult = superWinResult;
	}

	public String[] getArbitry9WinResult() {
		return arbitry9WinResult;
	}

	public void setArbitry9WinResult(String[] arbitry9WinResult) {
		this.arbitry9WinResult = arbitry9WinResult;
	}

	public String[] getArrangeFiveWinResult() {
		return arrangeFiveWinResult;
	}

	public void setArrangeFiveWinResult(String[] arrangeFiveWinResult) {
		this.arrangeFiveWinResult = arrangeFiveWinResult;
	}

	public TreeMap<String, String[]> getArrangeThreeWinResult() {
		return arrangeThreeWinResult;
	}

	public void setArrangeThreeWinResult(
			TreeMap<String, String[]> arrangeThreeWinResult) {
		this.arrangeThreeWinResult = arrangeThreeWinResult;
	}

	public String[] getBall4WinResult() {
		return ball4WinResult;
	}

	public void setBall4WinResult(String[] ball4WinResult) {
		this.ball4WinResult = ball4WinResult;
	}

	public String[] getHalf6WinResult() {
		return half6WinResult;
	}

	public void setHalf6WinResult(String[] half6WinResult) {
		this.half6WinResult = half6WinResult;
	}

	public String[] getHappyZodiacWinResult() {
		return happyZodiacWinResult;
	}

	public void setHappyZodiacWinResult(String[] happyZodiacWinResult) {
		this.happyZodiacWinResult = happyZodiacWinResult;
	}

	public TreeMap<String, String[]> getSevenColorWinResult() {
		return sevenColorWinResult;
	}

	public void setSevenColorWinResult(TreeMap<String, String[]> sevenColorWinResult) {
		this.sevenColorWinResult = sevenColorWinResult;
	}

	public TreeMap<String, String[]> getWinOrFailWinResult() {
		return winOrFailWinResult;
	}

	public void setWinOrFailWinResult(TreeMap<String, String[]> winOrFailWinResult) {
		this.winOrFailWinResult = winOrFailWinResult;
	}

	public ArrayList<String> getArbitry9LotteryResult() {
		return arbitry9LotteryResult;
	}

	public void setArbitry9LotteryResult(ArrayList<String> arbitry9LotteryResult) {
		this.arbitry9LotteryResult = arbitry9LotteryResult;
	}

	public ArrayList<String> getArrangeFiveLotteryResult() {
		return arrangeFiveLotteryResult;
	}

	public void setArrangeFiveLotteryResult(
			ArrayList<String> arrangeFiveLotteryResult) {
		this.arrangeFiveLotteryResult = arrangeFiveLotteryResult;
	}

	public ArrayList<String> getArrangeThreeLotteryResult() {
		return arrangeThreeLotteryResult;
	}

	public void setArrangeThreeLotteryResult(
			ArrayList<String> arrangeThreeLotteryResult) {
		this.arrangeThreeLotteryResult = arrangeThreeLotteryResult;
	}

	public ArrayList<String> getBall4LotteryResult() {
		return ball4LotteryResult;
	}

	public void setBall4LotteryResult(ArrayList<String> ball4LotteryResult) {
		this.ball4LotteryResult = ball4LotteryResult;
	}

	public ArrayList<String> getElevenYunLotteryResult() {
		return elevenYunLotteryResult;
	}

	public void setElevenYunLotteryResult(ArrayList<String> elevenYunLotteryResult) {
		this.elevenYunLotteryResult = elevenYunLotteryResult;
	}

	public ArrayList<String> getHalf6LotteryResult() {
		return half6LotteryResult;
	}

	public void setHalf6LotteryResult(ArrayList<String> half6LotteryResult) {
		this.half6LotteryResult = half6LotteryResult;
	}

	public ArrayList<String> getHappyPokerLotteryResult() {
		return happyPokerLotteryResult;
	}

	public void setHappyPokerLotteryResult(ArrayList<String> happyPokerLotteryResult) {
		this.happyPokerLotteryResult = happyPokerLotteryResult;
	}

	public ArrayList<String> getHappyZodiacLotteryResult() {
		return happyZodiacLotteryResult;
	}

	public void setHappyZodiacLotteryResult(
			ArrayList<String> happyZodiacLotteryResult) {
		this.happyZodiacLotteryResult = happyZodiacLotteryResult;
	}

	public ArrayList<String> getSevenColorLotteryResult() {
		return sevenColorLotteryResult;
	}

	public void setSevenColorLotteryResult(ArrayList<String> sevenColorLotteryResult) {
		this.sevenColorLotteryResult = sevenColorLotteryResult;
	}
	public ArrayList<String> getWinOrFailLotteryResult() {
		return winOrFailLotteryResult;
	}

	public void setWinOrFailLotteryResult(ArrayList<String> winOrFailLotteryResult) {
		this.winOrFailLotteryResult = winOrFailLotteryResult;
	}

	public TreeMap<String, ArrayList<String>> getSuperLotteryResult() {
		return superLotteryResult;
	}

	public void setSuperLotteryResult(
			TreeMap<String, ArrayList<String>> superLotteryResult) {
		this.superLotteryResult = superLotteryResult;
	}

	public LinkedHashMap<String, String[]> getElevenYunWinResult() {
		return elevenYunWinResult;
	}

	public void setElevenYunWinResult(LinkedHashMap<String, String[]> elevenYunWinResult) {
		this.elevenYunWinResult = elevenYunWinResult;
	}

	public Map<Integer, Map<String, String>> getArbitrary9SaleInfo() {
		return arbitrary9SaleInfo;
	}

	public void setArbitrary9SaleInfo(
			Map<Integer, Map<String, String>> arbitrary9SaleInfo) {
		this.arbitrary9SaleInfo = arbitrary9SaleInfo;
	}

	public Map<Integer, Map<String, String>> getBall4SaleInfo() {
		return ball4SaleInfo;
	}

	public void setBall4SaleInfo(Map<Integer, Map<String, String>> ball4SaleInfo) {
		this.ball4SaleInfo = ball4SaleInfo;
	}

	public Map<Integer, Map<String, String>> getHalf6SaleInfo() {
		return half6SaleInfo;
	}

	public void setHalf6SaleInfo(Map<Integer, Map<String, String>> half6SaleInfo) {
		this.half6SaleInfo = half6SaleInfo;
	}

	public Map<Integer, Map<String, String>> getWinOrFailSaleInfo() {
		return winOrFailSaleInfo;
	}

	public void setWinOrFailSaleInfo(
			Map<Integer, Map<String, String>> winOrFailSaleInfo) {
		this.winOrFailSaleInfo = winOrFailSaleInfo;
	}

	public Map<String, Integer> getMissCountResult() {
		return missCountResult;
	}

	public void setMissCountResult(Map<String, Integer> missCountResult) {
		this.missCountResult = missCountResult;
	}
	
}
