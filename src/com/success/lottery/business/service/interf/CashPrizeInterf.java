/**
 * Title: CashPrizeInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-21 上午10:31:44
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.business.service.interf
 * CashPrizeInterf.java
 * CashPrizeInterf
 * 兑奖
 * @author gaoboqin
 * 2010-4-21 上午10:31:44
 * 
 */

public interface CashPrizeInterf {
	
	/**
	 * 
	 * Title: cashSingleOrder<br>
	 * Description: <br>对单个订单兑奖,只对出票成功的订单兑奖,该方法需要实现以下步骤：
	 *              <br>根据订单获取彩期信息
	 *              <br>兑奖
	 *              <br>更新彩期状态看是否全部对完奖
	 *              <br>
	 * @param orderId  订单编号
	 * @return  0#彩种#彩期#订单编号#中奖号码#奖金级别#奖金金额#投注金额
	 * @throws LotteryException<br>
	 *                          <br>
	 *                          <br>510002:该订单对应的彩期不到兑奖时间
	 *                          <br>510001:该订单不符合兑奖条件
	 *                          <br>511001:兑奖程序出错
	 *                          <br>AccountService中定义的账户处理异常
	 *                          <br>
	 */
	public String cashSingleOrder(String orderId) throws LotteryException;
	/**
	 * 
	 * Title: cashMultiOrder<br>
	 * Description: <br>
	 *            对多个订单兑奖<br>
	 * @param lotteryId 彩种id
	 * @param termNo 彩期
	 * @param orderList 订单编号列表
	 * @return 
	 * 
	 * 		成功(0)失败(1)标识#彩种#彩期#订单编号#中奖号码#奖金级别#奖金金额#投注金额
	 * @throws LotteryException<br>
	 *                          <br>
	 *                          <br>510002:该订单对应的彩期不到兑奖时间
	 *                          <br>510003:该订单对应的彩期与要兑奖的期数不一致
	 *                          <br>510001:该订单不符合兑奖条件
	 *                          <br>511001:兑奖程序出错
	 *                          <br>AccountService中定义的账户处理异常
	 *                          <br>
	 */
	public List<String> cashMultiOrder(int lotteryId,String termNo,List<String> orderList) throws LotteryException;
	/**
	 * 
	 * Title: cashAutoOrder<br>
	 * Description: <br>
	 *            批量兑奖<br>
	 * @param lotteryId 彩种id
	 * @param termNo 期数
	 * @param cashNum 
	 *         cashTotalNum,总共可以兑奖的条数
	 *         cashCurNum，当前已经处理的条数
	 *         cashPersent，处理的百分比,保留一位小数
	 * @param dbLogMap 包含用户信息
	 * @return 
	 * 成功(0)失败(1)标识#彩种#彩期#订单编号#中奖号码#奖金级别#奖金金额#投注金额
	 * 	    int total_orders = 0;//总的订单数
	 *		long total_tz_prize = 0L; //总订单的投注金额
	 *		int sucess_orders = 0;//成功兑奖订单数
	 *		long sucess_tz_prize = 0L;//成功兑奖投注金额
	 *		int fail_orders = 0;//失败兑奖订单数
	 *		long fail_tz_prize = 0L;//失败兑奖投注金额
	 *		int zj_orders = 0;//中奖订单数
	 *		long zj_prize = 0L;//中奖奖金数
	 * @throws LotteryException<br>
	 *                         <br>
	 *                          <br>510002:该订单对应的彩期不到兑奖时间
	 *                          <br>510001:该订单不符合兑奖条件
	 *                          <br>511001:兑奖程序出错
	 *                          <br>AccountService中定义的账户处理异常
	 *                          <br> 
	 */
	public Map<String,String> cashAutoOrder(int lotteryId,String termNo,Map<String,String> cashNum,Map<String,String> dbLogMap) throws LotteryException;
	/**
	 * 
	 * Title: dealNotTicketOrder<br>
	 * Description: <br>
	 *              <br>对兑奖彩期的下一期的追号处理，包括限号和追号转投注的处理
	 * @param lotteryId
	 * @param termNo
	 * @param dbLogMap
	 * @return 
	 *         total_num,总共可以处理的订单数
	 *         sucess_num,成功处理的订单数
	 *         fail_num,处理失败的当单数
	 *         sucess_bet_num,转为投注的订单数
	 *         sucess_limit_num,转为限号的订单数
	 *         nextTerm,处理的彩期
	 * @throws LotteryException
	 */
	public Map<String,String> dealNotTicketOrder(int lotteryId,String termNo,Map<String,String> dbLogMap) throws LotteryException;
	
	/**
	 * 
	 * Title: updateCashTermStatus<br>
	 * Description: <br>
	 *              <br>兑奖完成（包括追号限号处理）后的彩期状态修改
	 * @param lotteryId
	 * @param termNo
	 * @param dbLogMap
	 * @return
	 *         old_status,彩期原始状态
	 *         new_status,修改后的彩期状态
	 * @throws LotteryException
	 */
	public Map<String,String> updateCashTermStatus(int lotteryId,String termNo,Map<String,String> dbLogMap) throws LotteryException;
	
	
}
