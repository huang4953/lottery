/**
 * Title: DispatchPrizeInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: 派发奖金处理接口
 * @author gaoboqin
 * @date 2010-4-26 下午01:45:43
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import java.util.List;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.business.service.interf
 * DispatchPrizeInterf.java
 * DispatchPrizeInterf
 * 派发奖金
 * @author gaoboqin
 * 2010-4-26 下午01:45:43
 * 
 */

public interface DispatchPrizeInterf {
	
	/**
	 * 
	 * Title: dispatchSingleOrder<br>
	 * Description: <br>
	 *            单个订单派发奖金<br>
	 * @param heMaiOrDaiGou 合买代购标识，0-代购 1-合买
	 * @param chuPiaoorderId 出票订单的Id
	 * @param paiJiangOrder 派奖订单的id 对代购出票订单和派奖订单一样，对合买派奖订单是指参与合买的订单id
	 * @return 彩种#彩期#订单编号#奖金级别#奖金金额#处理结果
	 * @throws LotteryException<br>
	 *                          <br>510005:该订单对应的彩期不到派奖时间
	 *                          <br>510004:该订单不符合派奖条件
	 *                          <br>511001:程序出错
	 *                          <br>AccountService中定义的账户处理异常
	 *                          
	 */
	public String dispatchSingleOrder(int heMaiOrDaiGou,String chuPiaoOrderId,String paiJiangOrder) throws LotteryException;
	
	/**
	 * 
	 * Title: dispatchMultiOrder<br>
	 * Description: <br>
	 *            对彩种彩期的多个订单派发奖金<br>
	 * @param lotteryId 彩种
	 * @param termNo 彩期
	 * @param orderList 订单列表集合
	 * @return 彩种#彩期#订单编号#奖金级别#奖金金额#处理结果
	 * @throws LotteryException<br>
	 *                          <br>510006:该订单对应的彩期与要派奖的期数不一致
	 *                          <br>510005:该订单对应的彩期不到派奖时间
	 *                          <br>510004:该订单不符合派奖条件
	 *                          <br>511001:程序出错
	 *                          <br>AccountService中定义的账户处理异常
	 */
	public List<String> dispatchMultiOrder(int lotteryId,String termNo,List<String> orderList) throws LotteryException;
	/**
	 * 
	 * Title: dispatchAutoOrder<br>
	 * Description: <br>
	 *            对彩种和彩期的所有订单派发奖金<br>
	 * @param lotteryId 彩种
	 * @param termNo 彩期
	 * @return 彩种#彩期#订单编号#奖金级别#奖金金额#处理结果
	 * @throws LotteryException<br>
	 *                          <br>510005:该订单对应的彩期不到派奖时间
	 *                          <br>510004:该订单不符合派奖条件
	 *                          <br>511001:程序出错
	 *                          <br>AccountService中定义的账户处理异常
	 */
	public List<String> dispatchAutoOrder(int lotteryId,String termNo) throws LotteryException;

}
