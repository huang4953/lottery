package com.success.lottery.ticket.service.interf;

import java.sql.Date;
import java.util.List;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.ticket.domain.BetTicketAcountDomain;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.domain.WinOrderTicketDomain;

/**
 * 
 * com.success.lottery.ticket.service.interf
 * BetTicketServiceInterf.java
 * BetTicketServiceInterf
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-14 上午09:42:51
 *
 */

public interface BetTicketServiceInterf {
	
	public static final int E_411003_CODE = 411003;
	public static final String E_411003_DESC = "获取数据时发生错误！";
	public static final int E_411004_CODE = 411004;
	public static final String E_411004_DESC = "更新数据时发生错误！";
	public static final int E_411005_CODE = 411005;
	public static final String E_411005_DESC = "生成彩票表数据时出错！";
	public static final int E_411007_CODE = 411007;
	public static final String E_411007_DESC = "彩票情况查询出错！";
	public static final int E_411017_CODE = 411017;
	public static final String E_411017_DESC = "彩票总条数查询出错！";
	public static final int E_411009_CODE = 411009;
	public static final String E_411009_DESC = "中奖彩票查询出错！";
	public static final int E_411019_CODE = 411019;
	public static final String E_411019_DESC = "中奖彩票总条数查询出错！";
	public static final int E_4110021_CODE = 411021;
	public static final String E_411021_DESC = "彩票统计出错！";
	public static final int E_4110023_CODE = 411023;
	public static final String E_411023_DESC = "彩票投注金额统计出错！";
	public static final int E_4110031_CODE = 411031;
	public static final String E_411031_DESC = "中奖彩票统计出错！";
	public static final int E_4110033_CODE = 411033;
	public static final String E_411033_DESC = "中奖彩票奖金统计出错！";
	public static final int E_4110011_CODE = 411011;
	public static final String E_411011_DESC = "订单明细查询出错！";
	/**
	 * 
	 * Title: insertBetTicket<br>
	 * Description: <br>
	 *            生成彩票信息<br>
	 *            该方法只是插入彩票表，不作任何的逻辑校验<br>
	 * @param betTicket 彩票信息实体对象，其中主键ticketSequence不需要指定
	 * @return 生成表数据的主键
	 * @throws LotteryException<br>
	 *                           411005:生成彩票表数据时出错
	 */
	public String insertBetTicket(BetTicketDomain betTicket) throws LotteryException;
	
	/**
	 * 
	 * Title: insertBetTicketBatch<br>
	 * Description: <br>
	 *            批量插入彩票表<br>
	 * @param betTicketList 包含彩票信息的线性集合
	 * @return 生成的彩票流水号集合
	 * @throws LotteryException<br>
	 *                          411005:生成彩票表数据时出错
	 */
	public List<String> insertBetTicketBatch(List<BetTicketDomain> betTicketList) throws LotteryException;
	/**
	 * 
	 * Title: updateBetTicketStatus<br>
	 * Description: <br>
	 *            更新彩票状态<br>
	 * @param ticketSequence 彩票流水号
	 * @param ticketStatus 出票状态
	 * @return 成功更新的记录数
	 * @throws LotteryException<br>
	 *                          411004:更新数据时发生错误
	 */
	public int updateBetTicketStatus(String ticketSequence,int ticketStatus) throws LotteryException;

	/**
	 * 更新指定彩票流水号的彩票状态，只有当该彩票状态为whoes中指定的彩票状态时才更新
	 * @param ticketSequences: 彩票流水号列表
	 * @param ticketStatus 彩票状态
	 * @param whoes 当前要更新的彩票的状态必须为whoes中的状态，如whoes为null，则无限制
	 * @return 成功更新的记录数，如果彩票流水号列表为null或空，则程序直接返回0
	 * @throws LotteryException<br>
	 *                          <br>BetTicketServiceInterf.E_411004_CODE:更新数据时发生错误
	 */
	public int updateBetTicketesStatus(List<String> ticketSequences, int ticketStatus, List<Integer> whoes) throws LotteryException;

	/**
	 * 
	 * Title: updateBetTicketPrintInfo<br>
	 * Description: <br>
	 *            更新彩票打票系统返回结果<br>
	 * @param ticketSequence 彩票流水号
	 * @param ticketStatus 彩票状态
	 * @param ticketId 票号
	 * @param printerId 打票机编号
	 * @param printResult 出票结果
	 * @param ticketData 彩票数据流
	 * @param ticketDataMD 彩票摘要
	 * @param ticketPassword 票密码
	 * @return 成功更新的记录数
	 * @throws LotteryException<br>
	 *                           411004:更新数据时发生错误
	 */
	public int updateBetTicketPrintInfo(String ticketSequence,int ticketStatus, String ticketId,
			String printerId, String printResult, String ticketData,
			String ticketDataMD, String ticketPassword) throws LotteryException;
	/**
	 * 
	 * Title: updateBetTicketPrizeResult<br>
	 * Description: <br>
	 *            更新彩票的中奖结果<br>
	 * @param ticketSequence 彩票流水号
	 * @param preTaxPrize 中奖金额
	 * @param prizeResult 兑奖结果
	 * @return 成功更新的记录数
	 * @throws LotteryException<br>
	 *                           411004:更新数据时发生错误
	 */
	public int updateBetTicketPrizeResult(String ticketSequence,long preTaxPrize,int prizeResult) throws LotteryException;
	
	/**
	 * 
	 * Title: queryBetOrderIdTicket<br>
	 * Description: <br>
	 *            根据订单编号查询订单在投注彩票信息表中的记录数<br>
	 *            可用于判断该订单是否出票<br>
	 * @param orderId 订单编号
	 * @return 返回count的记录数
	 * @throws LotteryException <br>
	 *                            411003:获取数据时发生错误
	 */
	public int queryBetOrderIdTicket(String orderId) throws LotteryException;
	
	/**
	 * 根据订单编号查询所有该订单的出票信息
	 * @param orderId
	 * 		指定的订单编号
	 * @return
	 * 		查询不到返回null
	 * @throws LotteryException
	 */
	public List<BetTicketDomain> getTickets(String orderId) throws LotteryException;
	
	/**
	 * 获得可以生成出票文件的出票记录条数
	 * @param lotteryId
	 * 		必须选择lotteryId
	 * @param term
	 * 		必须选择一个彩期
	 * @return
	 * 		查询到的记录条数，如果lotteryId小于等于0或者term为null，则返回0
	 * @throws LotteryException
	 */
	public int getCreateFileTicketesCount(int lotteryId, String term) throws LotteryException;
	
	/**
	 * 获得可以生成出票文件的出票记录 
	 * @param lotteryId
	 * 		必须选择lotteryId 
	 * @param term
	 * 		必须选择一个彩期 
	 * @param pageNumber
	 * 		查询第几页
	 * @param perPageNumber
	 * 		每页的条数
	 * @return
	 * 		查询到的记录，如果没有查询到记录或者lotteryId小于等于0或者term为null，则返回null 
	 * @throws LotteryException
	 */
	public List<BetTicketDomain> getCreateFileTicketes(int lotteryId, String term, int pageNumber, int perPageNumber) throws LotteryException;
	
	/**
	 * 获得可以下载的出票文件总数
	 * @param lotteryId
	 * 		彩种id
	 * @param term
	 * 		彩期
	 * @return
	 * 		查询到的记录条数
	 * @throws LotteryException
	 */
	public int getTicketFilesCount(int lotteryId, String term) throws LotteryException;

	/**
	 * 获得可以下载的出票文件记录 
	 * @param lotteryId
	 * 		彩种id
	 * @param term
	 * 		彩期
	 * @return
	 * 	查询到的记录，没有返回null
	 * @throws LotteryException
	 */
	public List<BetTicketDomain> getTicketFiles(int lotteryId, String term, int pageNumber, int perPageNumber) throws LotteryException;
	
	/**
	 * 获得需要确认出票结果的订单记录总条数
	 * @param lotteryId
	 * 		彩种Id；如果填写-1，则查询所有
	 * @param term
	 * 		彩期；如果填写null，则查询所有
	 * @param ticketStatus
	 * 		出票状态，1-已出票 6-出票成功 7-出票失败；如果填写-1，则查询所有
	 * @return
	 * 		返回条数
	 * @throws LotteryException
	 */
	public int getConfirmTicketesCount(int lotteryId, String term, int ticketStatus) throws LotteryException;

	/**
	 * 获得需要确认出票结果的订单记录
	 * @param lotteryId
	 * 		彩种Id；如果填写-1，则查询所有
	 * @param term
	 * 		彩期；如果填写null，则查询所有
	 * @param ticketStatus
	 * 		出票状态，1-已出票 6-出票成功 7-出票失败；如果填写-1，则查询所有
	 * @return
	 * 		返回出票结果List，没有为null
	 * @throws LotteryException
	 */
	public List<BetTicketDomain> getConfirmTicketes(int lotteryId, String term, int ticketStatus, int pageNumber, int perPageNumber) throws LotteryException;

	/**
	 * 获得中奖的彩票订单信息总条数
	 * @param lotteryId
	 * 		彩种Id，查所有填写-1
	 * @param term
	 * 		彩期，查所有填写null
	 * @param orderId
	 * 		订单编号，查所有填写null
	 * @return
	 * 		返回符合条件的中奖彩票订单条数
	 * @throws LotteryException
	 */
	public int getWinTicketesCount(int lotteryId, String term, String orderId) throws LotteryException;
	
	/**
	 * 获得中奖的彩票订单信息总条数 
	 * @param lotteryId
	 * 		彩种Id，查所有填写-1
	 * @param term
	 * 		彩期，查所有填写null
	 * @param orderId
	 * 		订单编号，查所有填写null
	 * @return
	 * 		返回符合条件的中奖彩票订单
	 * @throws LotteryException
	 */
	public List<WinOrderTicketDomain> getWinTicketes(int lotteryId, String term, String orderId, int pageNumber, int perPageNumber) throws LotteryException;
	
	/**
	 * 根据彩票流水号获得彩票信息
	 * @param ticketSequence
	 * @return
	 * @throws LotteryException
	 */
	public BetTicketDomain getTicket(String ticketSequence) throws LotteryException;
	
	/**
	 * 根据彩票流水号获得对应的订单信息
	 * @param ticketSequence
	 * @return
	 * @throws LotteryException
	 */
	public BetOrderDomain getOrderByTicketSequence(String ticketSequence) throws LotteryException;
	/**
	 * 彩票情况查询的总记录数
	 * @param orderId订单编号
	 * @param ticketSequence彩票编号
	 * @param lotteryId彩种id
	 * @param betTerm_begin彩期最小范围
	 * @param betTerm_end彩期最大范围
	 * @param ticketStatus彩票状态
	 * @param prizeResult中奖状态  
	 * @param beginTime出票的最小时间 
	 * @param endTime出票的最大时间 
	 * @return int返回列表的总记录数
	 * @throws LotteryException
	 */
	public int getBetTicketesCount(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime, Date endTime)throws LotteryException;
	/**
	 * 彩票情况查询的条目
	 * @param orderId订单编号
	 * @param ticketSequence彩票编号
	 * @param lotteryId彩种id
	 * @param betTerm_begin彩期最小范围
	 * @param betTerm_end彩期最大范围
	 * @param ticketStatus彩票状态
	 * @param prizeResult中奖状态  
	 * @param beginTime出票的最小时间 
	 * @param endTime出票的最大时间 
	 * @return List<BetTicketDomain>返回彩票的列表数据
	 * @throws LotteryException
	 */
	public List<BetTicketAcountDomain> getBetTicketes(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime, Date endTime,
			int page, int perPageNumber)throws LotteryException;
	/**
	 * 查询彩票的总投注数
	 * @param orderId订单编号
	 * @param ticketSequence彩票编号
	 * @param lotteryId彩种id
	 * @param betTerm_begin彩期最小范围
	 * @param betTerm_end彩期最大范围
	 * @param ticketStatus彩票状态
	 * @param prizeResult中奖状态  
	 * @param beginTime出票的最小时间 
	 * @param endTime出票的最大时间 
	 * @return long返回彩票总数目统计
	 * @throws LotteryException
	 */
	public long getBetTicketesNumber(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult, String accountId,
			String userName, String planSourceId,Date beginTime, Date endTime)throws LotteryException;
	/**
	 * 查询彩票的总投注金额
	 * @param orderId订单编号
	 * @param ticketSequence彩票编号
	 * @param lotteryId彩种id
	 * @param betTerm_begin彩期最小范围
	 * @param betTerm_end彩期最大范围
	 * @param ticketStatus彩票状态
	 * @param prizeResult中奖状态  
	 * @param beginTime出票的最小时间 
	 * @param endTime出票的最大时间 
	 * @return long返回彩票的总投注金额数
	 * @throws LotteryException
	 */
	public long getBetTicketesMoney(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime, Date endTime)throws LotteryException;
	/**
	 * 中奖彩票的总条数
	* @param orderId订单编号
	 * @param ticketSequence彩票编号
	 * @param lotteryId彩种id
	 * @param betTerm_begin彩期最小范围
	 * @param betTerm_end彩期最大范围
	 * @param ticketStatus彩票状态
	 * @param prizeResult中奖状态  
	 * @param beginTime出票的最小时间 
	 * @param endTime出票的最大时间 
	 * @return int 返回中奖彩票的中记录数
	 * @throws LotteryException
	 */
	public int getWinningBetTicketesCount(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime, Date endTime)throws LotteryException;
	/**
	 * 中奖彩票的条目
	* @param orderId订单编号
	 * @param ticketSequence彩票编号
	 * @param lotteryId彩种id
	 * @param betTerm_begin彩期最小范围
	 * @param betTerm_end彩期最大范围
	 * @param ticketStatus彩票状态
	 * @param prizeResult中奖状态  
	 * @param beginTime出票的最小时间 
	 * @param endTime出票的最大时间 
	 * @return List<BetTicketDomain>返回中奖的彩票列表
	 * @throws LotteryException
	 */
	public List<BetTicketAcountDomain> getWinningBetTicketes(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime,
			Date endTime, int page, int perPageNumber)throws LotteryException;
	/**
	 * 彩票按orderId去查询
	 * @param orderId
	 * @return List<BetTicketDomain>按orderId去查询返回满足orderId的所有彩票列表
	 * @throws LotteryException
	 */
	public List<BetTicketAcountDomain> getBetTicketes4OrderId(String orderId)throws LotteryException;
	/**
	 * 中奖数目统计
	* @param orderId订单编号
	 * @param ticketSequence彩票编号
	 * @param lotteryId彩种id
	 * @param betTerm_begin彩期最小范围
	 * @param betTerm_end彩期最大范围
	 * @param ticketStatus彩票状态
	 * @param prizeResult中奖状态  
	 * @param beginTime出票的最小时间 
	 * @param endTime出票的最大时间 
	 * @return long返回中奖的总统计数
	 * @throws LotteryException
	 */
	public long getWinningBetTicketesNumber(String orderId,String ticketSequence, Integer lotteryId, String betTerm_begin,
			String betTerm_end, Integer ticketStatus, Integer prizeResult,
			String accountId,String userName, String planSourceId,Date beginTime, Date endTime)throws LotteryException;
	/**
	 * 中奖彩票金额统计
	* @param orderId订单编号
	 * @param ticketSequence彩票编号
	 * @param lotteryId彩种id
	 * @param betTerm_begin彩期最小范围
	 * @param betTerm_end彩期最大范围
	 * @param ticketStatus彩票状态
	 * @param prizeResult中奖状态  
	 * @param beginTime出票的最小时间 
	 * @param endTime出票的最大时间 
	 * @return long返回中奖的奖金总统计
	 * @throws LotteryException
	 */
	public long getWinningBetTicketesMoney(String orderId,String ticketSequence, Integer lotteryId, String betTerm_begin,
			String betTerm_end, Integer ticketStatus, Integer prizeResult,
			String accountId,String userName, String planSourceId,Date beginTime, Date endTime)throws LotteryException;

	public BetTicketAcountDomain getBetTicketAcount(String ticketSequence)throws LotteryException;

	/**
	 * Title: getTicketes<br>
	 * Description: <br>查询指定状态的彩票
	 *              <br>该方法用于填充彩票出票队列，因此如果ticketStatus为null或空则直接返回null
	 *              <br>
	 * @param ticketStatus: 彩票状态列表
	 * @param condition: 获取彩票的附件条件，当为null时，则只使用ticketStatus为条件
	 * @param limitNumber: 指定每次获取的最大数量，当为0时，则不限制数量
	 * @return: 返回符合条件的彩票列表。
	 * @throws LotteryException<br>
	 *                          <br>BetTicketServiceInterf.E_411003_CODE:获取数据时发生错误
	 */
	public List<BetTicketDomain> getTicketes(List<Integer> ticketStatus, String condition, int limitNumber) throws LotteryException;

	

}
