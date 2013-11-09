package com.success.lottery.account.service;

import java.util.Date;
import java.util.List;

import com.success.lottery.account.model.AccountTransactionModel;
import com.success.lottery.account.model.BillOrderModel;
import com.success.lottery.account.model.IPSOrderModel;
import com.success.lottery.exception.LotteryException;

/**
 * 快钱支付接口
 * @author aaron
 *
 */
public interface BillOrderService {
	public static final String resource = "com.success.lottery.account.service.bill";
	
	// 插入支付订单时出现异常
	public static final int		ADDIPSORDEREXCEPTION				= 101301;
	public static final String	ADDIPSORDEREXCEPTION_STR			= "生成订单时出现异常：";
	
	// 查询订单出现异常
	public static final int		GETORDEREXCEPTION					= 101302;
	public static final String	GETORDEREXCEPTION_STR				= "查询订单时出现程序异常：";

	// 处理IPS返回信息时出现程序异常
	public static final int		PROCESSIPSRETURNEXCEPTION			= 101303;
	public static final String	PROCESSIPSRETURNEXCEPTION_STR		= "处理99bill返回信息时出现程序异常：";

	// 查询订单信息时出现程序异常
	public static final int		GETORDERESEXCEPTION			= 101304;
	public static final String	GETORDERESEXCEPTION_STR		= "查询订单信息时出现程序异常：";

	
	
	// 没有查询到订单
	public static final int		ORDERNOTFOUND						= 100301;
	public static final String	ORDERNOTFOUND_STR					= "订单未找到";

	// 错误的快钱返回信息
	public static final int		IPSRETURNERROR						= 100302;
	public static final String	IPSRETURNERROR_STR					= "错误的快钱返回信息";
	
	// 重复的快钱返回信息
	public static final int		DUPLICATIONIPSRETURN						= 100303;
	public static final String	DUPLICATIONIPSRETURN_STR					= "重复的快钱返回信息";
	
	// 快钱返回信息的金额错误
	public static final int		AMOUNTERRORIPSRETURN						= 100304;
	public static final String	AMOUNTERRORIPSRETURN_STR					= "快钱返回信息中出现了错误的金额";
	
	// 更新快钱订单未能更新到记录
	public static final int		NOIPSORDERUPDATE							= 100305;
	public static final String	NOIPSORDERUPDATE_STR					= "没有订单记录被更新";
	
	// 更新快钱订单未能更新到记录
	public static final int		VERIFYIPSRETURNERROR							= 100306;
	public static final String	VERIFYIPSRETURNERROR_STR					= "校验快钱返回信息未通过";
	
	//没有找到交易明细记录
	public static final int     ACCOUNTTRANSACTIONNOTFIND                    =100401;
	public static final String     ACCOUNTTRANSACTIONNOTFIND_STR                    ="交易明细记录没有找到";
	
	/**
	 * 添加快钱充值 
	 * @param billOrder
	 */
	public String addBillOrder(long userId, String username,long amount)throws LotteryException;
	
	/**
	 * 根据orderId查询 快钱交易记录
	 * @param orderId
	 * @return
	 */
	public BillOrderModel getBillOrderByOrderId(String orderId) throws LotteryException;
	
	/**
	 * 根据orderId修改快钱交易记录 
	 * @param orderId
	 * @param dealId
	 * @param fee
	 * @param bankId
	 * @param bankDealId
	 * @param dealTime
	 * @param payAmount
	 * @param ext1
	 * @param ext2
	 * @param payResult
	 * @param errCode
	 * @param signMsg
	 * @param merchantSignMsg
	 * @return
	 * @throws LotteryException
	 */
	public int processBILLOrder(String orderId,String dealId,String fee,String bankId,String bankDealId,String dealTime,String payAmount,String ext1,String ext2,String payResult,String errCode,String signMsg,String merchantSignMsg) throws LotteryException;
	
	public AccountTransactionModel getUserTransactionBySourceSequence(String sourceSequence, int sourceType);
	/**
	 * 获得快钱订单信息，对于开始时间是取大于等于当日0时0分0秒，对于截至时间是取小于等于当日 23:59:59
	 * @param userIdentify
	 * 		用户标识符，可以是loginname, mobilephone, email, realname
	 * @param startDate
	 * 		开始时间，只有年月日，如果没有开始时间则填写 null 
	 * @param endDate
	 * 		截至时间，如果没有截至时间则填写 null 
	 * @param orderStatus
	 * 		查询所有填写-1，环迅支付订单状态，列表如下（5、6暂时不用）：
	 * 			0 - 等待环迅支付结果
	 * 			1 - 支付成功充值成功
	 * 			2 - 支付成功充值失败
	 * 			3 - 支付失败
	 * 			4 - 支付失败充值成功
	 * 			5 - 不平账处理成功
	 * 			6 - 不平账处理失败
	 * @param start
	 * @param count
	 * @return
	 * @throws LotteryException
	 */
	public List<BillOrderModel> getIPSOrderes(String userIdentify, Date startDate, Date endDate, int orderStatus, int start, int count) throws LotteryException;
	
	
}
