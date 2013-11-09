/**
 * Title: BetPlanOrderServiceInterf.java
 * @Package com.success.lottery.order.service.interf
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-9 下午03:25:15
 * @version V1.0
 */
package com.success.lottery.order.service.interf;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.domain.BetPlanDomain;
import com.success.lottery.order.domain.CpInfoDomain;

/**
 * com.success.lottery.order.service.interf
 * BetPlanOrderServiceInterf.java
 * BetPlanOrderServiceInterf
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-9 下午03:25:15
 * 
 */

public interface BetPlanOrderServiceInterf {
	
	public static final int E_1001_CODE = 401001;
	public static final String E_1001_DESC = "生成方案表数据时发生错误！";
	public static final int E_1002_CODE = 401002;
	public static final String E_1002_DESC = "生成方案订单表数据时发生错误！";
	public static final int E_1003_CODE = 401003;
	public static final String E_1003_DESC = "获取数据时发生错误！";
	public static final int E_1004_CODE = 401004;
	public static final String E_1004_DESC = "更新数据时发生错误！";
	public static final int E_1005_CODE = 401005;
	public static final String E_1005_DESC = "生成彩票表数据时出错！";
	public static final int E_1006_CODE = 401006;
	public static final String E_1006_DESC = "生成合买订单表数据时发生错误！";
	
	public static final int E_1007_CODE = 401007;
	public static final String E_1007_DESC = "查询参数不允许为空！";
	
	/**
	 * 
	 * Title: insertBetPlanOrder<br>
	 * Description: <br>只能对代购使用
	 *              <br>生成方案并根据方案生成订单
	 * @param betPlan <br>
	 *               BetPlanDomain类对象，需要实例化该对象并设置必要的值<br>
	 *               该方法未对不能为null的属性做校验，其中不允许为null的属性如果未设置将抛出LotteryException<br>
	 *               对投注字符串代码是否合格也未做校验<br>
	 *               以后根据实际的业务需要再补上相关的校验<br>
	 *               未对账户余额进行校验<br>
	 *               BetPlanDomain对象中 List terms属性用来存储追号的期数，该列表不能包含当前的期数，如果没有追号，则设置该属性为null<br>
	 *               boolean isPutQuery 属性表示生成方案时是否直接写出票队列，默认为false<br>
	 * @param chuPiaoOrder 非追号的订单对象，可以直接送出票队列
	 *               
	 * @return 投注方案的编号和对应订单的编号#期数
	 * @throws LotteryException <br>
	 *                           <br>BetPlanOrderServiceInterf.E_1001_CODE:生成方案表数据时发生错误！<br>
	 *                           <br>BetPlanOrderServiceInterf.E_1002_CODE:生成方案订单表数据时发生错误<br>
	 */
	public Map<String,List<String>> insertBetPlanOrder(BetPlanDomain betPlan,BetOrderDomain chuPiaoOrder) throws LotteryException;
	
	/**
	 * 
	 * Title: updateBetPlanStatus<br>
	 * Description: <br>
	 *            更新方案表中方案的状态<br>
	 *            该方法只作简单的更新操作，不做逻辑上的判断<br>
	 * @param planId 方案编号
	 * @param planStatus 方案状态
	 * @return 更新成功的记录数
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1004_CODE:更新数据时发生错误
	 */
	public int updateBetPlanStatus(String planId,int planStatus) throws LotteryException;
	
	/**
	 * 
	 * Title: updateBetOrderStatus<br>
	 * Description: <br>
	 *            更新订单表中的订单状态<br>
	 *            该方法只作简单的更新操作，不做逻辑上的判断<br>
	 * @param orderId 订单编号
	 * @param orderStatus 订单状态
	 * @return 更新成功的记录数
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1004_CODE:更新数据时发生错误
	 */
	public int updateBetOrderStatus(String orderId,int orderStatus) throws LotteryException;
	/**
	 * 
	 * Title: updateBetOrderWinStatus<br>
	 * Description: <br>
	 *             更新订单表中的中奖状态<br>
	 *             该方法只作简单的更新操作，不做逻辑上的判断<br>
	 * @param orderId 订单编号
	 * @param winStatus 中奖状态
	 * @return 更新成功的记录数
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1004_CODE:更新数据时发生错误
	 */
	public int updateBetOrderWinStatus(String orderId,int winStatus) throws LotteryException;
	
	/**
	 * 
	 * Title: updateBetOrderAndWinStatus<br>
	 * Description: <br>
	 *            更新订单的中奖状态、中奖金额、备注信息，该备注信息存放中奖的号码串<br>
	 * @param orderId 订单id
	 * @param orderStatus 订单状态
	 * @param winStatus 中奖状态
	 * @param prize  税前奖金
	 * @param taxPrize 总税金
	 * @param aftTaxPrize 税后奖金
	 * @param deductPrize 提成金，用于合买
	 * @param commPrize 佣金，还未使用
	 * @param winCode 中奖号码串
	 * @return 更新成功的记录数
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1004_CODE:更新数据时发生错误
	 */
	public int updateBetOrderAndWinStatus(String orderId,int orderStatus,int winStatus,long prize,long taxPrize,long aftTaxPrize,long deductPrize,long commPrize,String winCode) throws LotteryException;
	
	/**
	 * 
	 * Title: queryBetPlanByPlanId<br>
	 * Description: <br>
	 *            根据方案编号查询方案信息<br>
	 * @param planId
	 * @return BetPlanDomain
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:获取数据时发生错误
	 */
	public BetPlanDomain queryBetPlanByPlanId(String planId) throws LotteryException;
	
	
	/**
	 * 
	 * Title: queryBetOrderByOrderId<br>
	 * Description: <br>
	 *            根据订单编号查询订单信息<br>
	 * @param orderId
	 * @return BetOrderDomain
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:获取数据时发生错误
	 */
	public BetOrderDomain queryBetOrderByOrderId(String orderId) throws LotteryException;
	
	/**
	 * 
	 * Title: queryBetOrderByOrderIdForUpdate<br>
	 * Description: <br>
	 *            根据订单编号查询订单信息同时锁住该条信息<br>
	 *            <br>该方法不允许随意调用
	 * @param orderId
	 * @return BetOrderDomain
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:获取数据时发生错误
	 */
	public BetOrderDomain queryBetOrderByOrderIdForUpdate(String orderId) throws LotteryException;
	
	/**
	 * 
	 * Title: queryOrderByOrderIdForSamePlan<br>
	 * Description: <br>
	 *            <br>根据方案编号查询与该订单属于同一方案的订单，不包含传入的订单编号
	 *            <br>
	 * @param planId 方案编号
	 * @param orderId 订单编号
	 * @param nextTerm 下一期的列表，如果为空或null则表示不限制
	 * @param orderStatus 订单状态
	 * @param winStatus 中奖状态
	 * @return List<BetOrderDomain>
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:获取数据时发生错误
	 */
	public List<BetOrderDomain> queryOrderByOrderIdForSamePlan(String planId,String orderId,List<String> nextTerm,List<Integer> orderStatus,int winStatus) throws LotteryException;
	/**
	 * 
	 * Title: queryNotCashOrderNum<br>
	 * Description: <br>
	 *            获取指定订单状态和开奖状态的订单数量的订单数量<br>
	 * @param lotteryId 彩种id
	 * @param termNo 期数
	 * @param orderStatus 订单状态
	 * @param winStatus 中奖状态列表
	 * @return  int
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:获取数据时发生错误
	 */
	public int queryOrderNumByStatus(int lotteryId,String termNo,int orderStatus,List<Integer> winStatus) throws LotteryException;
	/**
	 * 
	 * Title: queryOrderNumByStatus<br>
	 * Description: <br>
	 *              <br>获取指定订单状态和开奖状态的订单数量的订单数量
	 * @param lotteryId
	 * @param termNo
	 * @param orderStatus
	 * @param winStatus
	 * @return
	 * @throws LotteryException
	 */
	public int queryOrderNumByStatus(int lotteryId,String termNo,List<Integer> orderStatus,List<Integer> winStatus) throws LotteryException;
	/**
	 * 
	 * Title: queryNotCashOrder<br>
	 * Description: <br>
	 *            获取指定订单状态和开奖状态的订单编号<br>
	 * @param lotteryId 彩种id
	 * @param termNo 彩期
	 * @param orderStatus 订单状态
	 * @param winStatus 中奖状态列表
	 * @return 订单编号列表
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:获取数据时发生错误
	 */
	public List<String> queryOrderByStatus(int lotteryId,String termNo,int orderStatus,List<Integer> winStatus) throws LotteryException;
	/**
	 * 
	 * Title: queryOrderByStatus<br>
	 * Description: <br>
	 *              <br>获取指定订单状态和开奖状态的订单编号
	 * @param lotteryId 彩种id
	 * @param termNo 彩期
	 * @param orderStatus 订单状态
	 * @param winStatus 中奖状态列表
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryOrderByStatus(int lotteryId,String termNo,List<Integer> orderStatus,List<Integer> winStatus) throws LotteryException;
	/**
	 * Title: queryUndeliverTicketQueOrder<br>
	 * Description: <br>查询指定状态的订单
	 *              <br>该方法用于填充出票队列，因此如果orderStatus为null或空则直接返回null
	 *              <br>
	 * @param orderStatus 订单状态列表
	 * @param termInfo 可以出票的彩种彩期条件，格式如：(lotteryid=10000001 and betterm='10091') or (lotteryid=10000002 and betterm='10071')
	 * @param limitNumber 取限定的条数的记录
	 * @return 如果orderStatus为null或空则直接返回null
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:获取数据时发生错误
	 */
	public List<BetOrderDomain> getOrders(List<Integer> orderStatus, String termInfo, int limitNumber) throws LotteryException;

	/**
	 * Title: 查询要检查出票情况的订单<br>
	 * Description: <br>查询指定状态的订单，同时该订单所属彩票的彩票状态中必须有一条以上是指定的ticketStatus
	 *              <br>该方法用于获取已经出票并且有得到出票确认结果的但是订单还未得到最终结果的订单，因此如果orderStatus为null或空，或者ticketStatus为null或空，则直接返回null
	 *              <br>
	 * @param orderStatus
	 * @param ticketStatus
	 * @param limitNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BetOrderDomain> get2CheckOrders(List<Integer> orderStatus, List<Integer> ticketStatus, int limitNumber) throws LotteryException;

	/**
	 * 
	 * Title: getOrers<br>
	 * Description: <br>
	 *              <br>根据方案编号查询该方案下的所有订单，不对订单的状态做限制
	 * @param planId 方案编号 ，若为空则返回null
	 * @return  List<BetOrderDomain>
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:获取数据时发生错误
	 */
	public List<BetOrderDomain> getOrers(String planId) throws LotteryException;

	/**
	 * 更新指定订单流水号的订单状态
	 * @param orderIds 订单id列表
	 * @param orderStatus 订单状态
	 * @return 成功更新的记录数，如果订单列表为null或空，则程序直接返回0
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1004_CODE:更新数据时发生错误
	 */
	public int updateBetOrderStatus(List<String> orderIds, int orderStatus) throws LotteryException;

	/**
	 * 更新指定订单流水号的订单状态，只有当该订单状态为whoes中指定的订单状态时才更新
	 * @param orderIds 订单id列表
	 * @param orderStatus 订单状态
	 * @param whoes 当前要更新的订单的状态必须为whoes中的状态
	 * @return 成功更新的记录数，如果订单列表为null或空，则程序直接返回0
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1004_CODE:更新数据时发生错误
	 */
	public int updateBetOrderStatus(List<String> orderIds, int orderStatus, List<Integer> whoes) throws LotteryException;
	
	/**
	 * <br>更新指定订单流水号的订单状态以及出票情况
	 * <br>未对订单状态做业务上的判断
	 * @param orderId 订单id
	 * @param orderStatus  订单状态
	 * @param ticketStat 出票情况
	 * @return int 成功更新的记录数，如果订单id为null或空，则程序直接返回0
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1004_CODE:更新数据时发生错误
	 */
	public int updateBetOrderTicketStat(String orderId, int orderStatus, String ticketStat) throws LotteryException;
	
	/**
	 * 查询用户的符合条件的所有追号订单，对于开始时间是取大于等于当日0时0分0秒，对于截至时间是取小于等于当日 23:59:59
	 * @param userId
	 * 		用户id
	 * @param lotteryId
	 * 		彩种Id，如果查询所有彩种则填写0或-1
	 * @param termNo
	 * 		彩期，如果查询所有彩期则填写 null
	 * @param startDate
	 * 		开始时间，只有年月日，如果没有开始时间则填写 null
	 * @param endDate
	 * 		截至时间，如果没有截至时间则填写 null
	 * @param start
	 * 		查询开始的记录条数
	 * @param count
	 * 		查询多少条
	 * @return
	 * 		返回查询到的订单列表，该订单列表中index为0的BetOrderDomain.lotteryId 存放的为本次查询到的总条数，如没有查询到也可获得该值为0;
	 * @throws LotteryException
	 */
	public List<BetOrderDomain> getUserChaseOrders(long userId, int lotteryId, String termNo, Date startDate, Date endDate, int start, int count) throws LotteryException;
	
	/**
	 * 查询用户的符合条件的所有订单，对于开始时间是取大于等于当日0时0分0秒，对于截至时间是取小于等于当日 23:59:59
	 * @param userId
	 * 		用户id 
	 * @param lotteryId
	 * 		彩种Id，如果查询所有彩种则填写0或-1 
	 * @param termNo
	 * 		彩期，如果查询所有彩期则填写 null 
	 * @param planSource
	 * 		投注渠道，如果查询所有渠道则必须填写-1
	 * @param orderStatus
	 *		订单状态，如果查询所有状态则必须填写-1
	 *		填写以下值表示查询订单为： 
	 *		100 - 出票中 0,1,2
	 *		200 - 已出票（只包含出票成功的）5
	 *		300 - 已兑奖8
	 *		400 - 已派奖10
	 *		500 - 追号中13
	 *		600 - 已取消11,12,14
	 *		700 - 出票失败3,4,6
	 *		800 - 非追号以及撤销的订单，用户投注过的订单1-10
	 * @param winStatus
	 * 		中奖状态，如果查询所有状态则必须填写-1 
	 * @param startDate
	 * 		开始时间，只有年月日，如果没有开始时间则填写 null 
	 * @param endDate
	 * 		截至时间，如果没有截至时间则填写 null 
	 * @param start
	 * 		查询开始的记录条数 
	 * @param count
	 * 		查询多少条
	 * @return
	 * 		返回查询到的订单列表，该订单列表中index为0的BetOrderDomain.lotteryId 存放的为本次查询到的总条数，如没有查询到也可获得该值为0; 
	 * @throws LotteryException
	 */
	public List<BetOrderDomain> getUserOrders(long userId, int lotteryId, String termNo, int planSource, int orderStatus, int winStatus, Date startDate, Date endDate, int start, int count) throws LotteryException;
	
	/**
	 * 查询用户已经完成派奖的订单记录；对于开始时间是取大于等于当日0时0分0秒，对于截至时间是取小于等于当日 23:59:59
	 * @param userId
	 * 		用户id 
	 * @param lotteryId
	 * 		彩种Id，如果查询所有彩种则填写0或-1 
	 * @param termNo
	 * 		彩期，如果查询所有彩期则填写 null
	 * @param startDate
	 * 		开始时间，只有年月日，如果没有开始时间则填写 null 
	 * @param endDate
	 * 		截至时间，如果没有截至时间则填写 null 
	 * @param start
	 * 		查询开始的记录条数 
	 * @param count
	 * 		查询多少条
	 * @return
	 * 		返回查询到的订单列表
	 *		该列表中index为0的BetOrderDomain的以下字段中存放不同的值：
	 *			planSource：本次查询的总条数，即本次查询到的累计中奖次数
	 *			preTaxPrize：本次查询的总金额，即本次查询到的累计中奖金额
	 * @throws LotteryException
	 */
	public List<BetOrderDomain> getUserCashedOrders(long userId, int lotteryId, String termNo, Date startDate, Date endDate, int start, int count) throws LotteryException;
	
	/**
	 * 
	 * Title: queryBetPlanForUpdate<br>
	 * Description: <br>
	 *              <br>查询方案并锁定，用于合买参与
	 * @param planId
	 * @return
	 * @throws LotteryException
	 */
	public BetPlanDomain queryBetPlanForUpdate(String planId) throws LotteryException;
	/**
	 * 
	 * Title: updateBetPlanSelledUnit<br>
	 * Description: <br>
	 *              <br>更新合买方案进度和方案状态
	 * @param planId
	 * @param selledUnit
	 * @param planStatus
	 * @return
	 * @throws LotteryException
	 */
	public int updateBetPlanSelledUnit(String planId,int selledUnit,int planStatus) throws LotteryException;
	/**
	 * 
	 * Title: queryCoopInfoByPlanId<br>
	 * Description: <br>
	 *              <br>根据方案编号查询所有的参与信息
	 * @param planId
	 * @return
	 * @throws LotteryException
	 */
	public List<CpInfoDomain> queryCoopInfoByPlanId(String planId) throws LotteryException;
	/**
	 * 
	 * Title: updateCoopInfoStatus<br>
	 * Description: <br>
	 *              <br>更新合买参与信息的状态
	 * @param coopInfoId
	 * @param orderStatus
	 * @return
	 * @throws LotteryException
	 */
	public int updateCoopInfoStatus(String coopInfoId,int orderStatus) throws LotteryException;
	/**
	 * 
	 * Title: queryCoopInfoByIdForUpdate<br>
	 * Description: <br>
	 *              <br>根据参与合买信息id查参与信息
	 * @param coopInfoId
	 * @return
	 * @throws LotteryException
	 */
	public CpInfoDomain queryCoopInfoByIdForUpdate(String coopInfoId) throws LotteryException;
	/**
	 * 
	 * Title: createCoopBetOrder<br>
	 * Description: <br>
	 *              <br>生成合买的出票订单
	 * @param planId
	 * @param planSource
	 * @param userId
	 * @param areaCode
	 * @param lotteryId
	 * @param betTerm
	 * @param playType
	 * @param betType
	 * @param betCodeMode
	 * @param betCode
	 * @param betMultiple
	 * @param betAmount
	 * @param orderStatus
	 * @return
	 * @throws LotteryException
	 */
	public String createCoopBetOrder(String planId,int planSource,long userId,String areaCode,int lotteryId,String betTerm,int playType
			,int betType,int betCodeMode,String betCode,int betMultiple,int betAmount,int orderStatus) throws LotteryException;
	/**
	 * 
	 * Title: createCoopInfo<br>
	 * Description: <br>
	 *              <br>生成合买参与信息
	 * @param planId
	 * @param userId
	 * @param lotteryId
	 * @param betTerm
	 * @param playType
	 * @param betType
	 * @param cpOrderType
	 * @param cpUnit
	 * @param cpAmount
	 * @param orderStatus
	 * @return
	 * @throws LotteryException
	 */
	public String createCoopInfo(String planId,long userId,int lotteryId,String betTerm,int playType,int betType,int cpOrderType,int cpUnit,
			int cpAmount,int orderStatus) throws LotteryException;
	/**
	 * 
	 * Title: createCoopPlan<br>
	 * Description: <br>
	 *              <br>生成合买方案
	 * @param userId
	 * @param areaCode
	 * @param lotteryId
	 * @param playType
	 * @param betType
	 * @param betMultiple
	 * @param betTotalMoney
	 * @param term
	 * @param coopDeadLine
	 * @param betCode
	 * @param planOpenType
	 * @param totalUnit
	 * @param unitPrice
	 * @param selfBuyNum
	 * @param unitbuyself
	 * @param commisionPercent
	 * @param planSource
	 * @param planTitle
	 * @param planDescription
	 * @param planStatus
	 * @return
	 * @throws LotteryException
	 */
	public String createCoopPlan(long userId,String areaCode, int lotteryId, int playType,
			int betType, int betMultiple, int betTotalMoney,String term,Timestamp coopDeadLine, String betCode,
			int planOpenType, int totalUnit, int unitPrice, int selfBuyNum,
			int unitbuyself, int commisionPercent, int planSource,
			String planTitle, String planDescription,int planStatus) throws LotteryException;
	/**
	 * 
	 * Title: queryBetPlan<br>
	 * Description: <br>
	 *              <br>获取指定彩种彩期，类型，状态的方案
	 * @param lotteryAndTerm 格式：彩种，彩期
	 * @param planType 0:代购，1：合买
	 * @param planStatus 
	 * @return
	 * @throws LotteryException
	 */
	public List<BetPlanDomain> queryBetPlan(
			Map<Integer, String> lotteryAndTerm, List<Integer> planType,
			List<Integer> planStatus) throws LotteryException;
	/**
	 * 
	 * Title: updateCoopPrize<br>
	 * Description: <br>
	 *              <br>更新合买参与的中奖奖金，奖金为税后的并且已经被提成过的
	 * @param coopInfoId 参与信息id
	 * @param orderStatus 订单状态
	 * @param winStatus 奖金状态
	 * @param prize 奖金，分
	 * @return
	 * @throws LotteryException
	 */
	public int updateCoopPrize(String coopInfoId,int orderStatus,int winStatus,long prize) throws LotteryException;
	/**
	 * 
	 * Title: queryBetOrderByPlanId<br>
	 * Description: <br>
	 *              <br>根据方案编号查出票订单
	 * @param planId
	 * @return
	 * @throws LotteryException
	 */
	public List<BetOrderDomain> queryBetOrderByPlanId(String planId) throws LotteryException;
	/**
	 * 
	 * Title: queryCoopInfoByInfoId<br>
	 * Description: <br>
	 *              <br>根据合买参与订单编号查询参与信息
	 * @param coopInfoId
	 * @return
	 * @throws LotteryException
	 */
	public CpInfoDomain queryCoopInfoByInfoId(String coopInfoId) throws LotteryException;
	/**
	 * 
	 * Title: getNotDispatchCoopNum<br>
	 * Description: <br>
	 *              <br>查询一个合买方案还没有派奖完成的参与数
	 * @param planId
	 * @return
	 * @throws LotteryException
	 */
	public int getNotDispatchCoopNum(String planId) throws LotteryException;
	
	
}
