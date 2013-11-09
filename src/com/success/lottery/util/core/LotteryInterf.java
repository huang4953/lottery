package com.success.lottery.util.core;

import java.util.List;

import com.success.lottery.util.LotteryInfo;

public interface LotteryInterf {
	
	public static final long SINGLEPRIZE = 200;//每一注的金额为2元
	public static final long ADDPRIZE = 300;//追加每一注的金额为3元
	public static final String ZHUSIGN = "#";
	
	/**
	 * 根据传入的参数玩法id、投注方式id、投注字符串依据校验规则，校验投注字符串是否正确<br>
	 *                      
	 * @param playType 玩法id
	 * @param betType  投注方式id
	 * @param betString 投注字符串
	 * @return boolean   
	 *                    true 投注字符串符合投注格式；false 投注字符串不符合投注格式 <br>
	 *                    如果没有对应的玩法和对应的投注方式会抛出LotteryUnDefineException<br>
	 * @throws LotteryUnDefineException
	 * @throws Exception
	 */
	public boolean checkBetType(String playType,String betType,String betString) throws LotteryUnDefineException,Exception;
	
	/**
	 * 
	 * 将奖金结果字符串拆分为集合，集合的结构由LotteryInfo类定义<br>
	 * @param winResult
	 * @param info 彩种相关信息定义类
	 * @return LotteryInfo 彩种相关信息定义类
	 */
	public LotteryInfo splitWinResult(String winResult,LotteryInfo info);
	
	/**
	 * 
	 * 合并奖金结果为字符串，集合的结构由LotteryInfo类定义<br>
	 * @param info
	 * @return String 按照字符串格式定义规则返回合并后的字符串
	 */
	public String mergeWinResult(LotteryInfo info);
	
	/**
	 * 
	 * 将开奖结果字符串拆分为集合形式，集合的结构由LotteryInfo类定义<br>
	 * @param lotteryResult 奖金结果字符串
	 * @param info 定义彩种相关信息的实例对象
	 * @return LotteryInfo  定义彩种相关信息的实例对象
	 */
	public LotteryInfo splitLotteryResult(String lotteryResult,LotteryInfo info);
	/**
	 * 
	 * 合并开奖结果为字符串，集合的结构由LotteryInfo类定义<br>
	 * @param info 定义彩种相关信息的实例对象
	 * @return String  照字符串格式定义规则返回合并后的字符串
	 */
	public String mergeLotteryResult(LotteryInfo info);
	/**
	 * 
	 * Title: splitSalesInfoResult<br>
	 * Description: <br>
	 *            拆分销售信息<br>
	 * @param salesInfo 销售信息字符串
	 * @param info 定义彩种相关信息的实例对象
	 * @return 定义彩种相关信息的实例对象
	 */
	public LotteryInfo splitSalesInfoResult(String salesInfo,LotteryInfo info);
	/**
	 * 
	 * Title: mergeSalesInfoResult<br>
	 * Description: <br>
	 *            合并销售信息为字符串<br>
	 * @param info 定义彩种相关信息的实例对象
	 * @return 合并后的字符串
	 */
	public String mergeSalesInfoResult(LotteryInfo info);
	/**
	 * 
	 * 根据玩法id，投注方式id，开奖结果，奖金结果，投注字符串进行彩票兑奖<br>
	 * @param playType 玩法id
	 * @param betType 投注方式id
	 * @param lotteryResult 开奖结果
	 * @param winResult 奖金结果
	 * @param betCode 投注号码字符串
	 * @return List&lt;List&lt;String&gt;&gt; 返回兑奖结果的集合，其中只包含中奖的投注号码   参数：&lt;&lt;单注注码, 奖金级别,奖金金额,追加金额&gt;&gt;
	 * @throws LotteryUnDefineException, Exception
	 */
	public List<List<String>> lotteryPrize(String playType,String betType,String lotteryResult,String winResult,String betCode) throws LotteryUnDefineException, Exception;
	
	/**
	 * 
	 * Title: lotterySplit<br>
	 * Description: <br>
	 *            计算投注字符串的单式注数和投注金额，投注金额已经转化为分<br>
	 * @param playType 玩法id
	 * @param betType  投注方式id
	 * @param betCode  投注字符串
	 * @return         返回投注注数的金额，用'#'符号分割，格式为：投注注数#投注金额
	 * @throws LotteryUnDefineException
	 * @throws Exception
	 */
	public String lotterySplit(String playType,String betType,String betCode) throws LotteryUnDefineException, Exception;
	
	/**
	 * 
	 * Title: lotteryToSingle<br>
	 * Description: <br>
	 *            将投注字符串拆为单式的集合<br>
	 * @param playType 玩法id
	 * @param betType 投注方式id
	 * @param betCode 投注字符串
	 * @return        相应彩种的单式投注集合
	 * @throws LotteryUnDefineException
	 * @throws Exception
	 */
	public List<String> lotteryToSingle(String playType,String betType,String betCode) throws LotteryUnDefineException, Exception;
	
	/**
	 * Title: lotteryRandomBetCode<br>
	 * Description: <br>
	 *            随机生成某一彩种的单式单倍一注<br>
	 * @return 返回产生的随机投注注码。返回null或空字符串为产生随机注码失败
	 */
	public String lotteryRandomBetCode();
	
	/**
	 * Title: getMissCount<br>
	 * Description
	 * 		获得新的遗漏字符串，字符串存储规则参考数据状态格式核心定义文档。<br>
	 * @param lotteryResult
	 * 		开奖结果字符串
	 * @param lastMissCount
	 * 		最近一次遗漏情况字符串
	 * @return
	 * 		最新的遗漏字符串，如果返回null，则表示该彩种不需要遗漏或计算遗漏时出现了错误。
	 */
	public String getMissCount(String lotteryResult, String lastMissCount);
	
	/**
	 * 
	 * Title: splitMissCount<br>
	 * Description: <br>
	 *            拆分遗漏信息<br>
	 * @param missCount 遗漏信息字符串
	 * @param info 定义彩种相关信息的实例对象
	 * @return 定义彩种相关信息的实例对象
	 */
	public LotteryInfo splitMissCount(String missCount,LotteryInfo info);
	/**
	 * 
	 * Title: isLimitBet<br>
	 * Description: <br>
	 *            是否限号判断<br>
	 * @param betType 投注方式
	 * @param limitNumberArrary 限号串（可能含多个限号，含多个限号的以","隔开）
	 * @param betCode 投注串（可能含多注投注号，含多个多注投注号以"^"隔开）
	 * @return true或者false
	 */
	public boolean isLimitBet(String playType,String betType, String limitNumberArrary,
			String betCode) throws LotteryUnDefineException;
	
}
