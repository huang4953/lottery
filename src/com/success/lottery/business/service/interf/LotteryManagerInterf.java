/**
 * Title: LotteryManagerInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: 彩期管理服务接口，提供彩期的开奖信息输入、销售信息的输入、限号信息的输入
 * @author gaoboqin
 * @date 2010-5-13 上午11:53:00
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.success.lottery.business.domain.BusBetOrderCountDomain;
import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.domain.BusCoopPlanDomain;
import com.success.lottery.business.domain.BusCpInfoDomain;
import com.success.lottery.business.domain.PrizeUserDomain;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;

/**
 * com.success.lottery.business.service.interf
 * LotteryManagerInterf.java
 * LotteryManagerInterf
 * 彩期管理服务接口，提供彩期的开奖信息输入、销售信息的输入、限号信息的输入
 * @author gaoboqin
 * 2010-5-13 上午11:53:00
 * 
 */

public interface LotteryManagerInterf {
	//LotteryList=1000001,大乐透|1000002,七星彩|1000003,排列三|1000004,排列五|1000005,生肖乐|1300001,胜负彩|1300002,任选九场|1300003,6场半全场|1300004,4场进球彩|1200001,十一运夺金|1200002,快乐扑克
	
	public static final int E_01_CODE = 530001;
	public static final String E_01_DESC = "[1]参数不正确[2]！";
	
	public static final int E_02_CODE = 531001;
	public static final String E_02_DESC = "程序出错！";
	
	public static final int E_03_CODE = 531002;
	public static final String E_03_DESC = "输入开奖信息时未更新到对应的数据！";
	
	public static final int E_04_CODE = 531003;
	public static final String E_04_DESC = "输入销售信息时未更新到对应的数据！";
	
	public static final int E_05_CODE = 531004;
	public static final String E_05_DESC = "输入限号信息时未更新到对应的数据！";
	
	public static final int E_06_CODE = 531005;
	public static final String E_06_DESC = "获取开奖信息列表数据出错！";
	
	
	/**
	 * 
	 * Title: inputSuperWinInfo<br>
	 * Description: <br>
	 * 大乐透开奖信息输入<br>
	 * 输入的参数都不能为空<br>
	 * @param termNo
	 *            彩期
	 * @param lotteryResult
	 *            开奖结果 参数为<前去|后区,<号码集合>>,其中前区用1表示，后区用2表示，两位一个号码，不足左补'0',前区号码范围为:01-35,后区号码范围为01-12,号码集合无顺序要求
	 * @param salesVolume
	 *            当期销量
	 * @param jackpot
	 *            累计奖池
	 * @param winResult
	 *            奖金结果 奖金单位为元 参数意思为：<一等奖到八等奖(用数字1-8表示),<基本奖(A)或追加(B)奖,[注数,金额]>>,8等奖无追加因此8等奖可不填写追加的集合元素
	 * @return int 成功更新的记录条数
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:参数不正确
	 *                          <br>LotteryManagerInterf.E_02_CODE:输入开奖信息程序出错
	 *                          <br>LotteryManagerInterf.E_03_CODE:输入开奖信息时未更新到对应的开奖数据
	 *                          <br>
	 */
	public int inputSuperWinInfo(String termNo,
			TreeMap<String,ArrayList<String>> lotteryResult, String salesVolume,
			String jackpot, TreeMap<String,TreeMap<String,String[]>> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputhappyZodiacWinInfo<br>
	 * Description: <br>
	 * 生肖乐开奖信息输入<br>
	 * 输入的参数都不能为空<br>
	 * 
	 * @param termNo
	 *            彩期
	 * @param lotteryResult
	 *            开奖结果 参数为<号码>,应该包含2个号码，两位一个号码，不足左补0,号码范围为:01-12，无顺序要求
	 * @param salesVolume
	 *            当期销量
	 * @param winResult
	 *            奖金结果 奖金单位为元 参数为 <A(注数)B(金额)标识,值>,如:<A,10> <B,200>
	 * @return int 成功更新的记录条数
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:参数不正确
	 *                          <br>LotteryManagerInterf.E_02_CODE:输入开奖信息程序出错
	 *                          <br>LotteryManagerInterf.E_03_CODE:输入开奖信息时未更新到对应的开奖数据
	 *                          <br>
	 */
	public int inputHappyZodiacWinInfo(String termNo,
			ArrayList<String> lotteryResult, String salesVolume,
			Map<String,String> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputSuperAndHappyWinInfo<br>
	 * Description: <br>
	 *              <br>大乐透和生肖乐开奖信息输入，该方法会同时输入大乐透和生效乐的开奖信息，失误失败会一齐回滚
	 * @param termNo
	 * @param lotteryResultS
	 * @param salesVolumeS
	 * @param jackpotS
	 * @param winResultS
	 * @param lotteryResultH
	 * @param salesVolumeH
	 * @param winResultH
	 * @return int
	 * @throws LotteryException
	 */
	public int inputSuperAndHappyWinInfo(String termNo,
			TreeMap<String,ArrayList<String>> lotteryResultS, String salesVolumeS,
			String jackpotS, TreeMap<String,TreeMap<String,String[]>> winResultS,
			ArrayList<String> lotteryResultH, String salesVolumeH,
			Map<String,String> winResultH) throws LotteryException;
	/**
	 * 
	 * Title: inputSevenColorWinInfo<br>
	 * Description: <br>
	 * 七星彩开奖结果输入<br>
	 * 
	 * @param termNo
	 *            彩期
	 * @param lotteryResult
	 *            开奖结果 参数为：<位置序号(1-7),号码>,按照顺序代表7个位置的中奖号码，中奖号码范围为:0-9
	 * @param salesVolume
	 *            当期销量
	 * @param jackpot
	 *            累计奖池
	 * @param winResult
	 *            奖金结果 参数分别为:<一等奖到六等奖(用数字1-6标识),[注数,金额]>
	 * @return int
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:参数不正确
	 *                          <br>LotteryManagerInterf.E_02_CODE:输入开奖信息程序出错
	 *                          <br>LotteryManagerInterf.E_03_CODE:输入开奖信息时未更新到对应的开奖数据
	 *                          <br>
	 */
	public int inputSevenColorWinInfo(String termNo,
			Map<String,String> lotteryResult,String salesVolume,
			String jackpot, Map<String,String[]> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputArrangeThreeWinInfo<br>
	 * Description: <br>
	 * 排列3开奖信息输入<br>
	 * 
	 * @param termNo
	 *            彩期
	 * @param lotteryResult
	 *            开奖结果 参数为<位置(1-3),号码>，位置标识的1(百位)，2(十位),3(个位),应该包含3个号码,号码范围为:0-9
	 * @param salesVolume
	 *            当期销量
	 * @param winResult
	 *            奖金结果 参数分别为<直选奖、组三奖和组六奖用(分别用1,2,3代表),[注数,金额]>，如果没有组3或组6则该集合元素不设置
	 * @return int
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:参数不正确
	 *                          <br>LotteryManagerInterf.E_02_CODE:输入开奖信息程序出错
	 *                          <br>LotteryManagerInterf.E_03_CODE:输入开奖信息时未更新到对应的开奖数据
	 *                          <br>
	 */
	public int inputArrangeThreeWinInfo(String termNo,
			Map<String,String> lotteryResult,String salesVolume,
			Map<String,String[]> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputArrangeFiveWinInfo<br>
	 * Description: <br>
	 * 排列5开奖信息输入<br>
	 * 
	 * @param termNo
	 *            彩期
	 * @param lotteryResult
	 *            开奖结果 开奖结果 参数为<位置(1-5),号码>，位置标识的1-5从高位到低位,应该包含5个号码,号码范围为:0-9
	 * @param salesVolume
	 *            当期销量
	 * @param winResult
	 *            奖金结果 奖金单位为元 参数为 <A(注数)B(金额)标识,值>,如:<A,10> <B,200>
	 * @return int
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:参数不正确
	 *                          <br>LotteryManagerInterf.E_02_CODE:输入开奖信息程序出错
	 *                          <br>LotteryManagerInterf.E_03_CODE:输入开奖信息时未更新到对应的开奖数据
	 *                          <br>
	 */
	public int inputArrangeFiveWinInfo(String termNo,
			Map<String,String> lotteryResult,String salesVolume,
			Map<String,String> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputArrange3and5WinInfo<br>
	 * Description: <br>
	 *              <br>排列3和排列5开奖信息输入，该方法会同时输入排列3和排列5的开奖信息，失败会一齐回滚
	 * @param termNo
	 * @param lotteryResult3
	 * @param salesVolume3
	 * @param winResult3
	 * @param lotteryResult5
	 * @param salesVolume5
	 * @param winResult5
	 * @return int
	 * @throws LotteryException
	 */
	public int inputArrange3and5WinInfo(String termNo,
			Map<String,String> lotteryResult3,String salesVolume3,
			Map<String,String[]> winResult3,
			Map<String,String> lotteryResult5,String salesVolume5,
			Map<String,String> winResult5) throws LotteryException;
	/**
	 * 
	 * Title: inputWinOrFailWinInfo<br>
	 * Description: <br>
	 * 胜负彩开奖信息输入<br>
	 * 
	 * @param termNo
	 *            彩期
	 * @param lotteryResult
	 *            开奖结果 参数为<号码>，应该包含14个号码，有顺序要求 号码范围为0,1,3,9
	 * @param salesVolume
	 *            当期销量
	 * @param jackpot
	 *            累计奖池
	 * @param winResult
	 *            奖金结果 参数为:<一等奖(1)二等奖(2),[注数,金额]>
	 * 
	 * @return int
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:参数不正确
	 *                          <br>LotteryManagerInterf.E_02_CODE:输入开奖信息程序出错
	 *                          <br>LotteryManagerInterf.E_03_CODE:输入开奖信息时未更新到对应的开奖数据
	 *                          <br>
	 */
	public int inputWinOrFailWinInfo(String termNo,
			ArrayList<String> lotteryResult, String salesVolume,
			String jackpot, TreeMap<String,String[]> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputArbitryNineWinInfo<br>
	 * Description: <br>
	 * 任选择9开奖信息输入<br>
	 * 
	 * @param termNo
	 *            彩期
	 * @param lotteryResult
	 *            开奖结果 开奖结果 参数为<号码>，应该包含14个号码，有顺序要求 号码范围为0,1,3,9
	 * @param salesVolume
	 *            当期销量
	 * @param jackpot
	 *            累计奖池
	 * @param winResult
	 *            奖金结果 奖金单位为元 参数为 <A(注数)B(金额)标识,值>,如:<A,10> <B,200>
	 * @return int
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:参数不正确
	 *                          <br>LotteryManagerInterf.E_02_CODE:输入开奖信息程序出错
	 *                          <br>LotteryManagerInterf.E_03_CODE:输入开奖信息时未更新到对应的开奖数据
	 *                          <br>
	 */
	public int inputArbitryNineWinInfo(String termNo,
			ArrayList<String> lotteryResult, String salesVolume,
			String jackpot, Map<String,String> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputWinAndNineWinInfo<br>
	 * Description: <br>
	 *              <br>胜负彩和任选9开奖信息输入
	 * @param termNo
	 * @param lotteryResultW
	 * @param salesVolumeW
	 * @param jackpotW
	 * @param winResultW
	 * @param lotteryResult9
	 * @param salesVolume9
	 * @param jackpot9
	 * @param winResult9
	 * @return int
	 * @throws LotteryException
	 */
	public int inputWinAndNineWinInfo(String termNo,
			ArrayList<String> lotteryResultW, String salesVolumeW,
			String jackpotW, TreeMap<String,String[]> winResultW,
			ArrayList<String> lotteryResult9, String salesVolume9,
			String jackpot9, Map<String,String> winResult9) throws LotteryException;
	
	/**
	 * 没有实现 
	 * Title: inputHalfSixWinInfo<br>
	 * Description: <br>
	 * 6场半全场开奖信息输入<br>
	 * 
	 * @param termNo
	 * @param lotteryResult
	 * @param lotteryResultPlus
	 * @param salesVolume
	 * @param jackpot
	 * @param winResult
	 * @return int
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:参数不正确
	 *                          <br>LotteryManagerInterf.E_02_CODE:输入开奖信息程序出错
	 *                          <br>LotteryManagerInterf.E_03_CODE:输入开奖信息时未更新到对应的开奖数据
	 *                          <br>
	 */
	public int inputHalfSixWinInfo(String termNo,
			ArrayList<String> lotteryResult,String salesVolume,
			String jackpot, Map<String,String> winResult) throws LotteryException;
	/**
	 * 没有实现
	 * Title: inputBallFourWinInfo<br>
	 * Description: <br>
	 * 4场进球彩开奖信息输入<br>
	 * 
	 * @param termNo
	 * @param lotteryResult
	 * @param salesVolume
	 * @param jackpot
	 * @param winResult
	 * @return int
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:参数不正确
	 *                          <br>LotteryManagerInterf.E_02_CODE:输入开奖信息程序出错
	 *                          <br>LotteryManagerInterf.E_03_CODE:输入开奖信息时未更新到对应的开奖数据
	 *                          <br>
	 */
	public int inputBallFourWinInfo(String termNo,
			ArrayList<String> lotteryResult, String salesVolume,
			String jackpot, Map<String,String> winResult) throws LotteryException;
	
	/**
	 * 
	 * Title: inputJxDlcWinInfo<br>
	 * Description: <br>
	 * 江西多乐彩开奖信息输入<br>
	 * 
	 * @param termNo 彩期
	 * @param lotteryResult 开奖结果
	 * @param salesVolume 当期销量
	 * @param jackpot 累计奖池
	 * @param winResult 奖金结果
	 * @return int 成功更新的记录条数
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:参数不正确
	 *                          <br>LotteryManagerInterf.E_02_CODE:输入开奖信息程序出错
	 *                          <br>LotteryManagerInterf.E_03_CODE:输入开奖信息时未更新到对应的开奖数据
	 *                          <br>
	 */
	public int inputJxDlcWinInfo(String termNo,
			ArrayList<String> lotteryResult, String salesVolume,
			String jackpot, LinkedHashMap<String,String[]> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputJxDlcWinInfo<br>
	 * Description: <br>
	 *              <br>江西多乐彩开奖信息更新，开奖号码格式：两位一个号码，共五个号码，不足两位左补0，要自己保证号码格式的正确
	 * @param termNo
	 * @param lotteryResult
	 * @return
	 * @throws LotteryException
	 */
	public int inputJxDlcWinInfo(String termNo,String lotteryResult) throws LotteryException;
	
	/**
	 * 
	 * Title: inputWinInfo<br>
	 * Description: <br>
	 *            用字符串的方式输入开奖信息<br>
	 * @param lotteryId 彩种
	 * @param termNo 彩期
	 * @param lotteryResult 开奖结果
	 * @param lotteryResultPlus 开奖结果顺序
	 * @param salesVolume 当期销量
	 * @param jackpot 累计奖池
	 * @param winResult 奖金结果
	 * @return int  更新成功的记录数
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:参数不正确
	 *                          <br>LotteryManagerInterf.E_02_CODE:输入开奖信息程序出错
	 *                          <br>LotteryManagerInterf.E_03_CODE:输入开奖信息时未更新到对应的开奖数据
	 *                          <br>
	 */
	public int inputWinInfo(int lotteryId,String termNo,
			String lotteryResult,String lotteryResultPlus,String salesVolume, String jackpot,
			String winResult) throws LotteryException;
	
	/**
	 * 
	 * Title: inputSaleInfo<br>
	 * Description: <br>
	 *              <br>销售信息输入
	 *              <br>时间格式为 yyyy-MM-dd HH:mm:ss
	 *              <br>只有足彩可以输入销售信息，如果其他彩种调用将返回异常
	 *              <br>只可以对未售期并且销售状态为未到销售时间的彩期可以输入销售信息
	 * @param lotteryId 彩种 不能为空
	 * @param termNo 期号 不能为空
	 * @param startTime 系统开售时间 不能为空
	 * @param startTime2 官方开售时间 不能为空
	 * @param deadLine 系统止售时间 不能为空
	 * @param deadLine3 官方止售时间 不能为空
	 * @param deadLine2 合买止售时间 
	 * @param winLine 系统开奖时间 
	 * @param winLine2 官方开奖时间
	 * @param saleInfo 销售信息集合 
	 *            Map&lt;Integer,Map&lt;String,String&gt;&gt;<br>
	 *            Map&lt;场次,Map&lt;key,value&gt;&gt;<br>
	 *            key定义为：<br>
	 *            A 表示主场
	 *            B 表示客场
	 *            C 表示赛制
	 *            D 表示比赛时间
	 * @return int 
	 * @throws LotteryException
	 */
	public int inputSaleInfo(int lotteryId, String termNo, String startTime,
			String startTime2, String deadLine, String deadLine3,
			String deadLine2, String winLine, String winLine2, Map<Integer, Map<String,String>> saleInfo)
			throws LotteryException;
	
	/**
	 * 
	 * Title: inputSaleInfo<br>
	 * Description: <br>
	 *              <br>输入胜负彩和任选择9的销售信息，两个彩种一起输入
	 * @param termNo
	 * @param startTime
	 * @param startTime2
	 * @param deadLine
	 * @param deadLine3
	 * @param deadLine2
	 * @param winLine
	 * @param winLine2
	 * @param saleInfo
	 * @return
	 * @throws LotteryException
	 */
	public int inputSaleInfo(String termNo, String startTime,
			String startTime2, String deadLine, String deadLine3,
			String deadLine2, String winLine, String winLine2, Map<Integer, Map<String,String>> saleInfo)
			throws LotteryException;
	
	/**
	 * 
	 * Title: inputTermLimitNumber<br>
	 * Description: <br>
	 *            输入彩期的限号信息，直接输入字符串，目前只有排列3有限号，所以未对字符串做解析<br>
	 * @param lotteryId 彩种
	 * @param termNo 彩期
	 * @param limitNumber 限制号码
	 * @throws LotteryException<br>
	 *                          <br>LotteryManagerInterf.E_01_CODE:参数不正确
	 *                          <br>otteryManagerInterf.E_05_CODE:输入限号信息时未更新到对应的数据
	 *                          <br>otteryManagerInterf.E_02_CODE:程序出错
	 *                          <br>LotteryTermServiceInterf.E_1011_CODE:更新限号信息时出错
	 */
	public void inputTermLimitNumber(int lotteryId,String termNo,String limitNumber) throws LotteryException;
	
	/**
	 * 
	 * Title: getLotteryList<br>
	 * Description: <br>
	 *              <br>获取系统定义的所有启用的彩种
	 * @return Map<Integer,String>
	 * @throws LotteryException
	 */
	public Map<Integer,String> getLotteryList() throws LotteryException;
	/**
	 * 
	 * Title: getLotteryLastTerm<br>
	 * Description: <br>
	 *              <br>获取已经开奖的和可以开奖的彩种期号列表 
	 * @param lotteryId
	 * @return List<String>
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:获取彩期信息出错
	 */
	public List<String> getLotteryLastTerm(int lotteryId,int limitNum) throws LotteryException;
	
	/**
	 * 
	 * Title: getLotteryCurrentAndNextTerm<br>
	 * Description: <br>
	 *              <br>获取当前期和未售期的彩期列表
	 * @param lotteryId
	 * @param limitNum
	 * @return List<String>
	 * @throws LotteryException
	 */
	public List<String> getLotteryCurrentAndNextTerm(int lotteryId,int limitNum) throws LotteryException;
	
	/**
	 * 
	 * Title: getLotteryLastTermInfo<br>
	 * Description: <br>
	 *              <br>获取已经开奖的和可以开奖的彩种期号列表 
	 * @param lotteryId 彩种id 若为null或空则查所有的彩种
	 * @param begin_term 开始期号 若为null或空 则认为不限制
	 * @param end_term 截至期号 若为null或空 则认为不限制
	 * @param limitNum 限制条数 如为0则认为不限制条数
	 * @return List<LotteryTermModel>
	 * @throws LotteryException <br>LotteryTermServiceInterf.E_06_CODE:获取开奖信息列表数据出错
	 */
	public List<LotteryTermModel> getLotteryLastTermInfo(String lotteryId,String begin_term,String end_term,int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: getLotteryNextTermInfo<br>
	 * Description: <br>
	 *              <br>查询当前期的和未售期的彩期信息，用于修改销售信息
	 * @param lotteryId
	 * @param begin_term
	 * @param end_term
	 * @param limitNum
	 * @return  List<LotteryTermModel>
	 * @throws LotteryException
	 */
	public List<LotteryTermModel> getLotteryNextTermInfo(String lotteryId,String begin_term,String end_term,int limitNum) throws LotteryException;
	
	/**
	 * 
	 * Title: getLotteryLastWinInfo<br>
	 * Description: <br>
	 *              <br>获取彩期的最近的开奖信息 
	 * @param lotteryId 彩种
	 * @return LotteryTermModel
	 * @throws LotteryException
	 */
	public LotteryTermModel getLotteryLastWinInfo(int lotteryId) throws LotteryException;
	
	/**
	 * 
	 * Title: getbetOrderInfo<br>
	 * Description: <br>
	 *              <br>后台查询投注交易列表
	 * @param accountId
	 * @param userName
	 * @param lotteryId
	 * @param termNo
	 * @param betType
	 * @param betStatus
	 * @param winStatus
	 * @param begin_time
	 * @param end_time
	 * @param pageIndex
	 * @param perPageNumber
	 * @return List<BusBetOrderDomain>
	 * @throws LotteryException
	 */
	public List<BusBetOrderDomain> getbetOrderInfoS(String accountId,
			String userName, String lotteryId, String termNo, String planSource,
			String betStatus, String winStatus, String begin_time,
			String end_time, int pageIndex, int perPageNumber)
			throws LotteryException;
	/**
	 * 
	 * Title: getbetOrderInfosCount<br>
	 * Description: <br>
	 *              <br>后台查询投注交易列表总条数
	 * @param accountId
	 * @param userName
	 * @param lotteryId
	 * @param termNo
	 * @param planSource
	 * @param betStatus
	 * @param winStatus
	 * @param begin_time
	 * @param end_time
	 * @return int
	 * @throws LotteryException
	 */
	public int getbetOrderInfosCount(String accountId,
			String userName, String lotteryId, String termNo, String planSource,
			String betStatus, String winStatus, String begin_time,
			String end_time) throws LotteryException;
	/**
	 * 
	 * Title: getCanCashOrderInfoS<br>
	 * Description: <br>
	 *              <br>查询可以兑奖的订单列表,该方法做了修改，可以查出出票成功之前的所有订单
	 * @param lotteryId
	 * @param termNo
	 * @param pageIndex
	 * @param perPageNumber
	 * @return List<BusBetOrderDomain>
	 * @throws LotteryException
	 */
	public List<BusBetOrderDomain> getCanCashOrderInfoS(String lotteryId,String termNo,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCanCashOrderInfosCount<br>
	 * Description: <br>
	 *              <br>查询可以兑奖的订单列表的记录数,,该方法做了修改，可以查出出票成功之前的所有订单
	 * @param lotteryId
	 * @param termNo
	 * @return int
	 * @throws LotteryException
	 */
	public int getCanCashOrderInfosCount(String lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: getCanDispatchOrderInfoS<br>
	 * Description: <br>
	 *              <br>查询代购派奖订单列表
	 * 
	 * @param lotteryId
	 * @param termNo
	 * @param userIdentify
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusBetOrderDomain> getCanDispatchOrderInfoS(String lotteryId,String termNo,String userIdentify,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCanDispatchOrderInfosCount<br>
	 * Description: <br>
	 *              <br>查询代购派奖订单列表条数
	 * 
	 * @param lotteryId
	 * @param termNo
	 * @param userIdentify
	 * @return Map<String,Long>
	 * @throws LotteryException
	 */
	public BusBetOrderCountDomain getCanDispatchOrderInfosCount(String lotteryId,String termNo,String userIdentify) throws LotteryException;
	
	
	/**
	 * 
	 * Title: getOrdersByPlanId<br>
	 * Description: <br>
	 *              <br>根据方案编号查新方案下的所有订单，附加了彩期的信息,用于显示订单的详细信息
	 * @return List<BusBetOrderDomain>
	 * @throws LotteryException
	 */
	public List<BusBetOrderDomain> getOrdersByPlanId(String planId) throws LotteryException;
	
	/**
	 * 
	 * Title: getPrizeUser<br>
	 * Description: <br>
	 *              <br>查询用户最新中奖
	 * @param limitNum 限制条数，若<0则认为不限制
	 * @return 中奖的用户信息
	 * @throws LotteryException
	 */
	public List<PrizeUserDomain> getPrizeUser(int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: getPrizeOrder<br>
	 * Description: <br>
	 *              <br>首页查询中奖排行
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<PrizeUserDomain> getPrizeUserOrder(int limitNum) throws LotteryException;
	
	/**
	 * Title: inputUpdatefailOrder<br>
	 * Description: <br>
	 *              <br>处理出票失败订单
	 * @param orderId
	 * @param winStatus
	 * @param wincode
	 * @return
	 * @throws LotteryException
	 */
	public int inputUpdatefailOrder(String orderId,int winStatus,String wincode)throws LotteryException;
	/**
	 * 
	 * Title: getNeedLimitLotteryList<br>
	 * Description: <br>
	 *              <br>获取需要限号的彩期
	 * @return
	 * @throws LotteryException
	 */
	public Map<Integer,String> getNeedLimitLotteryList() throws LotteryException;
	
	/**
	 * 
	 * Title: getCoopCanDispatch<br>
	 * Description: <br>
	 *              <br>查询合买派奖订单列表
	 * 
	 * @param lotteryId
	 * @param termNo
	 * @param userIdentify
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusCpInfoDomain> getCoopCanDispatch(String lotteryId,String termNo,String userIdentify,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCoopCanDispatchCount<br>
	 * Description: <br>
	 *              <br>查询合买派奖订单列表条数
	 * 
	 * @param lotteryId
	 * @param termNo
	 * @param userIdentify
	 * @return Map<String,Long>
	 * @throws LotteryException
	 */
	public BusBetOrderCountDomain getCoopCanDispatchCount(String lotteryId,String termNo,String userIdentify) throws LotteryException;
	/**
	 * 
	 * Title: getCoopCanJoinOrder<br>
	 * Description: <br>
	 *              <br>获取单个彩种的可参与合买的方案列表
	 *              <br>页面方案金额划分标准为：[0,100)100元以下,[100,500),[500,1000),[1000,-1)1000元以上,单位为元,全部则上限和下限制都用-1标识
	 *              <br>页面提成标准:[0,1)提成0，[1,5)提成1%-5%，[5,100)提成5%以上
	 *              <br>
	 *              <br>
	 * @param lotteryId 彩种
	 * @param termNo 彩期，目前只有在彩期可售时合买才能参与
	 * @param userIdentify 发起合买的用户标识,不限制则用null
	 * @param planProgress 方案进度 0-满员，1-未满员，-1标识全部的
	 * @param planMoneyDown 方案金额下限，大于等于下限值，无下限用-1标识
	 * @param planMoneyUp 方案金额上限 小于上限值，无上限用-1标识
	 * @param tiChengDown 提成下限，大于等于下线，
	 * @param tiChengUp 提成上限，小于上限
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusCoopPlanDomain> getCoopCanJoinOrder(int lotteryId, String termNo,
			String userIdentify, int planProgress, int planMoneyDown,
			int planMoneyUp, int tiChengDown,int tiChengUp,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCoopCanJoinOrderCount<br>
	 * Description: <br>
	 *              <br>获取单个彩种的可参与合买的方案统计，用于分页需要
	 * @param lotteryId
	 * @param termNo
	 * @param userIdentify
	 * @param planProgress
	 * @param planMoneyDown
	 * @param planMoneyUp
	 * @param tiChengDown
	 * @param tiChengUp
	 * @return
	 * @throws LotteryException
	 */
	public int getCoopCanJoinOrderCount(int lotteryId, String termNo,
			String userIdentify, int planProgress, int planMoneyDown,
			int planMoneyUp, int tiChengDown,int tiChengUp) throws LotteryException;
	
	/**
	 * 
	 * Title: getCoopPlans<br>
	 * Description: <br>
	 *              <br>后台查询合买方案列表
	 *              <br>页面方案金额划分标准为：[0,100)100元以下,[100,500),[500,1000),[1000,-1)1000元以上,单位为元,全部则上限和下限制都用-1标识
	 *              <br>页面提成标准:[0,1)提成0，[1,5)提成1%-5%，[5,100)提成5%以上
	 * @param lotteryId 彩种 传入0标识所有彩种
	 * @param termNo 彩期 传入0标识所有彩期
	 * @param planId 方案编号
	 * @param userIdentify 用户标识
	 * @param planProgress 方案进度 0-满员，1-未满员，-1标识全部的
	 * @param planMoneyDown 方案金额下限，大于等于下限值，无下限用-1标识
	 * @param planMoneyUp 方案金额上限 小于上限值，无上限用-1标识
	 * @param tiChengDown 提成下限，大于等于下线
	 * @param tiChengUp 提成上限，小于上限
	 * @param begin_time 方案提交开始时间
	 * @param end_time 方案提交结束时间
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusCoopPlanDomain> getCoopPlans(int lotteryId, String termNo,String planId,
			String userIdentify, int planProgress, int planMoneyDown,
			int planMoneyUp, int tiChengDown,int tiChengUp,String begin_time,
			String end_time,int planStatus,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCoopPlansCount<br>
	 * Description: <br>
	 *              <br>后台查询合买方案统计条数
	 * @param lotteryId
	 * @param termNo
	 * @param planId
	 * @param userIdentify
	 * @param planProgress
	 * @param planMoneyDown
	 * @param planMoneyUp
	 * @param tiChengDown
	 * @param tiChengUp
	 * @param begin_time
	 * @param end_time
	 * @return
	 * @throws LotteryException
	 */
	public int getCoopPlansCount(int lotteryId, String termNo,String planId,
			String userIdentify, int planProgress, int planMoneyDown,
			int planMoneyUp, int tiChengDown,int tiChengUp,String begin_time,
			String end_time,int planStatus) throws LotteryException;
	/**
	 * 
	 * Title: getCoopCanYuInfos<br>
	 * Description: <br>
	 *              <br>后台查询参与合买的参与信息列表
	 * @param lotteryId 0 所有彩种
	 * @param termNo 0 所有彩期
	 * @param planId 方案编号
	 * @param coopInfoId 参与订单编号
	 * @param jionUser 参与用户标识
	 * @param begin_date 参与开始时间
	 * @param end_date 参与结束时间
	 * @param coopType 参与类型 -1标识全部  0-发起认购，1参与认购，2-发起保底，3-保底转投注
	 * @param orderStatus 参与信息状态 -1 标识全部 0-进行中，1-系统撤单，2-发起人撤单，3-参与人撤单，4-合买满员生成投注，5-保底冻结，6-保底撤销,7-已经兑奖,8-已派奖
	 * @param winStatus 奖金状态 -1标识全部 0-未兑奖，1-未中奖，2-中小奖，3-中大奖
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusCpInfoDomain> getCoopCanYuInfos(String lotteryId,
			String termNo, String planId, String coopInfoId, String jionUser,
			String begin_date, String end_date, String coopType,
			String orderStatus, String winStatus, int pageIndex,
			int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCoopCanYuInfosCount<br>
	 * Description: <br>
	 *              <br>后台查询参与合买的参与信息的统计
	 * @param lotteryId
	 * @param termNo
	 * @param planId
	 * @param coopInfoId
	 * @param jionUser
	 * @param begin_date
	 * @param end_date
	 * @param coopType
	 * @param orderStatus
	 * @param winStatus
	 * @return
	 * @throws LotteryException
	 */
	public int getCoopCanYuInfosCount(String lotteryId,
			String termNo, String planId, String coopInfoId, String jionUser,
			String begin_date, String end_date, String coopType,
			String orderStatus, String winStatus) throws LotteryException;
	/**
	 * 
	 * Title: getCoopPlanForWeb<br>
	 * Description: <br>
	 *              <br>前台账户中心查询发起的或者参与的合买
	 *              <br>返回的对象中借用planSource标识中奖情况,借用chaseCount标识税前的奖金，单位分
	 *              <br>界面显示字段:lotteryId,startTerm,planId,planTitle,planTime(发起时间),
	 *              unitAmount(方案金额),totalUnit(总份数),unitBuySelf(保底份数),selledUnit(已认购),planStatus(方案状态),
	 *              planSource(中奖情况),chaseCount(方案税前奖金)
	 *              <br>对合买发起如果(unitBuySelf+selledUnit)*2 > totalUnit,则操作中不能加"撤销"操作,合买参与没有撤销方案的连接
	 *              
	 *              
	 * @param faQiOrCanYu 0-发起，1-参与
	 * @param lotteryId 彩种 0-所有彩种,彩种里面不能包含多乐彩
	 * @param betTerm 彩期 0-所有彩期
	 * @param userId 发起或参与的用户id
	 * @param planStatus 方案状态：-1标识全部，100-进行中，101-出票中，102-已出票，103-已兑奖，104-已派奖，105-出票失败，106-已撤销
	 * @param winStatus -1标识全部,0-未对奖，1-未中奖，2-中小奖，3-中大奖
	 * @param begin_date 开始时间
	 * @param end_date 结束时间
	 * @return
	 * @throws LotteryException
	 */
	public List<BusCoopPlanDomain> getCoopPlanCreateForWeb(int faQiOrCanYu,
			int lotteryId, String betTerm, int userId, int planStatus,
			int winStatus,String begin_date, String end_date,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCoopPlanCreateForWebCount<br>
	 * Description: <br>
	 *              <br>前台账户中心查询发起的合买和参与的合买
	 * @param faQiOrCanYu 
	 * @param lotteryId
	 * @param betTerm
	 * @param userId
	 * @param planStatus
	 * @param winStatus
	 * @param begin_date
	 * @param end_date
	 * @return
	 * @throws LotteryException
	 */
	public int getCoopPlanCreateForWebCount(int faQiOrCanYu,int lotteryId, String betTerm, int userId, int planStatus,
			int winStatus,String begin_date, String end_date) throws LotteryException;
	/**
	 * 
	 * Title: getCoopForWebDetail<br>
	 * Description: <br>
	 *              <br>前台合买详情中的所有参与信息列表
	 * @param planId 方案编号
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusCpInfoDomain> getCoopForWebDetail(String planId,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCoopForWebDetailCount<br>
	 * Description: <br>
	 *              <br>前台合买详情中的所有参与信息列表统计
	 * @param planId 方案编号
	 * @return
	 * @throws LotteryException
	 */
	public int getCoopForWebDetailCount(String planId) throws LotteryException;
	/**
	 * 
	 * Title: getCoopSelfForWebDetail<br>
	 * Description: <br>
	 *              <br>前台合买详情中自己参与的合买参与信息,不需要分页
	 * @param planId
	 * @param userId
	 * @return
	 * @throws LotteryException
	 */
	public List<BusCpInfoDomain> getCoopSelfForWebDetail(String planId,int userId) throws LotteryException;
	/**
	 * 
	 * Title: getNotChuPiaoOrders<br>
	 * Description: <br>
	 *              <br>后台获取官方止售后还没有能查票的订单列表
	 * @param lotteryId 0-全部
	 * @param termNo 0-全部
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusBetOrderDomain> getNotChuPiaoOrders(int lotteryId,String termNo,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getNotChuPiaoOrderCount<br>
	 * Description: <br>
	 *              <br>后台获取官方止售后还没有能查票的订单统计
	 * @param lotteryId
	 * @param termNo
	 * @return
	 * @throws LotteryException
	 */
	public int getNotChuPiaoOrderCount(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: getChuPiaoErrorOrders<br>
	 * Description: <br>
	 *              <br>后台查询代购或者合买的出票错误的订单列表
	 * @param daiOrHe 0-代购 ，1-合买
	 * @param lotteryId
	 * @param termNo
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusBetOrderDomain> getChuPiaoErrorOrders(int daiOrHe,int lotteryId,String termNo,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getChuPiaoErrorOrderCount<br>
	 * Description: <br>
	 *              <br>后台查询代购或者合买的出票错误的订单统计
	 * @param daiOrHe
	 * @param lotteryId
	 * @param termNo
	 * @return
	 * @throws LotteryException
	 */
	public int getChuPiaoErrorOrderCount(int daiOrHe,int lotteryId,String termNo) throws LotteryException;
	
	/**
	 * 
	 * Title: planBetCodeIsShow<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param planId
	 * @param userId 如果未登录用-1标识
	 * @return
	 * @throws LotteryException
	 */
	public String planBetCodeIsShow(String planId,int userId) throws LotteryException;
	/**
	 * 
	 * Title: getCoopPlanForWebShow<br>
	 * Description: <br>
	 *              <br>获取合买的方案信息，用于前台的方案详细信息显示，包含开奖信息以及中奖信息
	 *              <br>界面显示:
	 *              <br>开奖结果取areaCode字段内容
	 *              <br>中奖情况取reserve字段内容
	 *              <br>方案状态取planStatus字段，用LotteryStaticDefine.planStatusForWebH转
	 * @param planId
	 * @return
	 * @throws LotteryException
	 */
	public BusCoopPlanDomain getCoopPlanForWebShow(String planId) throws LotteryException;
	
}
