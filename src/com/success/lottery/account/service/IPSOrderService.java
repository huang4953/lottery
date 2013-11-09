package com.success.lottery.account.service;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

//import com.success.lottery.account.model.CheckIpsorderModel;
import com.success.lottery.account.model.IPSOrderModel;
import com.success.lottery.account.model.IPSReturnInfo;
import com.success.lottery.exception.LotteryException;

public interface IPSOrderService {
//	orderid amount userid currencytype gatewaytype attach ordermessage accounttransactionid 

	public static final String resource = "com.success.lottery.account.service.ips";

	// 插入支付订单时出现异常
	public static final int		ADDIPSORDEREXCEPTION				= 101301;
	public static final String	ADDIPSORDEREXCEPTION_STR			= "生成订单时出现异常：";
	
	// 查询订单出现异常
	public static final int		GETORDEREXCEPTION					= 101302;
	public static final String	GETORDEREXCEPTION_STR				= "查询订单时出现程序异常：";

	// 处理IPS返回信息时出现程序异常
	public static final int		PROCESSIPSRETURNEXCEPTION			= 101303;
	public static final String	PROCESSIPSRETURNEXCEPTION_STR		= "处理IPS返回信息时出现程序异常：";

	// 查询订单信息时出现程序异常
	public static final int		GETORDERESEXCEPTION			= 101304;
	public static final String	GETORDERESEXCEPTION_STR		= "查询订单信息时出现程序异常：";

	
	
	// 没有查询到订单
	public static final int		ORDERNOTFOUND						= 100301;
	public static final String	ORDERNOTFOUND_STR					= "订单未找到";

	// 错误的环迅返回信息
	public static final int		IPSRETURNERROR						= 100302;
	public static final String	IPSRETURNERROR_STR					= "错误的环迅返回信息";
	
	// 重复的环迅返回信息
	public static final int		DUPLICATIONIPSRETURN						= 100303;
	public static final String	DUPLICATIONIPSRETURN_STR					= "重复的环迅返回信息";
	
	// 环迅返回信息的金额错误
	public static final int		AMOUNTERRORIPSRETURN						= 100304;
	public static final String	AMOUNTERRORIPSRETURN_STR					= "环迅返回信息中出现了错误的金额";
	
	// 更新环迅订单未能更新到记录
	public static final int		NOIPSORDERUPDATE							= 100305;
	public static final String	NOIPSORDERUPDATE_STR					= "没有订单记录被更新";
	
	// 更新环迅订单未能更新到记录
	public static final int		VERIFYIPSRETURNERROR							= 100306;
	public static final String	VERIFYIPSRETURNERROR_STR					= "校验环迅返回信息未通过";
	
	
	/**
	 * 确认进行环迅支付，生成环迅支付订单
	 * @param userId
	 * 		进行环迅支付的用户id
	 * @param amount
	 * 		支付金额，单位分
	 * @param attach
	 * 		提交环迅订单请求时的商户数据包
	 * @return
	 * 		返回插入的环迅订单的订单编号，即需要提交到环迅的商户订单编号Billno
	 * @throws LotteryException <br>
	 * 		 IPSOrderService.ADDIPSORDEREXCEPTION
	 */
	public String addIPSOrder(long userId, int amount, String attach) throws LotteryException;
	
	/**
	 * 根据订单编号获取环迅支付记录信息，该方法用于普通查询订单信息
	 * @param orderId
	 * 		指定的环迅支付订单编号
	 * @return
	 * 		环迅支付信息，null为没有找到
	 * @throws LotteryException
	 */
	public IPSOrderModel getIPSOrder(String orderId) throws LotteryException;
	
	/**
	 * 对环迅支付返回的结果信息进行处理，根据订单编号查找相应记录，并根据结果对状态，结果进行更新；同时记录日志。<br>
	 * 此操作需要对记录进行行锁，避免重复交易<br>
	 * 处理流程如下：<br>
	 * 1、IPSOrderDAO.getIPSOrderForUpdate(orderId)，查询订单记录用于更新<br>
	 * 2、如果为null则记录日志，并抛出异常<br>
	 * 3、根据succFlag以及当前订单状态进行处理<br>
	 * 4、如果为成功并且orderStatus = 0，则进行本金充值<br>
	 * 5、更新orderStatus, checkedStatus以及其他状态信息
	 * @param orderId
	 * @param ipsBillNo
	 * @param succFlag
	 * @param ipsMsg
	 * @param ipsBankTime
	 * @return
	 * @throws LotteryException
	 * 		IPSRETURNERROR
	 * 		ORDERNOTFOUND
	 * 		DUPLICATIONIPSRETURN
	 * 		AMOUNTERRORIPSRETURN
	 * 		NOIPSORDERUPDATE
	 * 		PROCESSIPSRETURNEXCEPTION
	 */
	public int processIPSOrder(String orderId, String ipsBillNo, char succFlag, String ipsMsg, String ipsBankTime) throws LotteryException;
	
	
	/**
	 * 对环迅支付返回的结果信息进行处理，根据订单编号查找相应记录，并根据结果对状态，结果进行更新；同时记录日志。<br>
	 * 此操作需要对记录进行行锁，避免重复交易<br>
	 * 处理流程如下：<br>
	 * 1、IPSOrderDAO.getIPSOrderForUpdate(orderId)，查询订单记录用于更新<br>
	 * 2、如果为null则记录日志，并抛出异常<br>
	 * 3、进行校验，校验不通过抛出异常
	 * 4、根据succFlag以及当前订单状态进行处理<br>
	 * 5、如果为成功并且orderStatus = 0，则进行本金充值<br>
	 * 6、更新orderStatus, checkedStatus以及其他状态信息
	 * @param orderId
	 * @param ipsBillNo
	 * @param succFlag
	 * @param ipsMsg
	 * @param ipsBankTime
	 * @param retEncodeType
	 * @param signature
	 * @return
	 * @throws LotteryException
	 * 		IPSRETURNERROR
	 * 		ORDERNOTFOUND
	 * 		DUPLICATIONIPSRETURN
	 * 		AMOUNTERRORIPSRETURN
	 * 		NOIPSORDERUPDATE
	 * 		PROCESSIPSRETURNEXCEPTION
	 * 		VERIFYIPSRETURNERROR
	 */
	public int processIPSOrder(String orderId, String ipsBillNo, char succFlag, String ipsMsg, String ipsBankTime, String retEncodeType, String signature) throws LotteryException;
	public int processIPSOrder(IPSReturnInfo info) throws LotteryException;

	/**
	 * 获得环迅订单信息，对于开始时间是取大于等于当日0时0分0秒，对于截至时间是取小于等于当日 23:59:59
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
	public List<IPSOrderModel> getIPSOrderes(String userIdentify, Date startDate, Date endDate, int orderStatus, int start, int count) throws LotteryException;
}
