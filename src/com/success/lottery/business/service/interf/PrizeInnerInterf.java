/**
 * Title: PrizeInnerInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(兑奖和派奖内部处理类)
 * @author gaoboqin
 * @date 2010-4-21 下午05:00:57
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.term.domain.LotteryTermModel;

/**
 * com.success.lottery.business.service.interf
 * PrizeInnerInterf.java
 * PrizeInnerInterf
 * 兑奖和派奖内部处理类
 * @author gaoboqin
 * 2010-4-21 下午05:00:57
 * 
 */

public interface PrizeInnerInterf {
	
	public static final int E_01_CODE = 510001;
	public static final String E_01_DESC = "该订单不符合兑奖条件！";
	
	public static final int E_04_CODE = 510002;
	public static final String E_04_DESC = "该订单对应的彩期不到兑奖时间！";
	
	public static final int E_05_CODE = 510003;
	public static final String E_05_DESC = "该订单对应的彩期与要兑奖的期数不一致！";
	
	public static final int E_09_CODE = 510009;
	public static final String E_09_DESC = "彩种[A]彩期[B]尚有[C]个订单未经出票确认，不能兑奖！";
	
	
	/*
	 * 以下异常为派奖异常
	 */
	public static final int E_06_CODE = 510004;
	public static final String E_06_DESC = "该订单不符合派奖条件！";
	
	public static final int E_07_CODE = 510005;
	public static final String E_07_DESC = "该订单对应的彩期不到派奖时间！";
	
	public static final int E_08_CODE = 510006;
	public static final String E_08_DESC = "该订单对应的彩期与要派奖的期数不一致！";
	
	/*
	 * 以下异常为程序异常
	 */
	public static final int E_02_CODE = 511001;
	public static final String E_02_DESC = "发生程序异常！";
	
	
	/**
	 * 
	 * Title: cashPrize<br>
	 * Description: <br>对单个订单兑奖,只对出票成功的订单兑奖,该方法需要实现以下步骤：
	 *            <br>(1)根据订单编号和订单状态为出票成功、中奖状态为0（未兑奖）查询订单并锁住该订单不允许其他操作
	 *            <br>(2)判断传入的参数彩种和彩期是否和订单的彩种彩期一致
	 *            <br>(3)根据彩种信息进行中奖号码匹配
	 *            <br>(4)获取中奖后是否停止追号
	 *            <br>(5)处理追号
	 *            <br>(6)更新订单状态为兑奖完成
	 * @param lotteryId 彩种id
	 * @param cashTerm 彩期
	 * @param orderId 订单编号
	 * @param termInfo 彩期信息
	 * @param isCheckLotteryId 是否需要判断彩种和彩期，单个订单和自动处理不需要判断，多个订单需要判断
	 * @return 中奖号码#奖金级别#奖金金额
	 * @throws LotteryException<br>
	 *                          <br>E_01_CODE
	 *                          <br>E_05_CODE
	 *                          <br>E_02_CODE
	 *                          <br>
	 *                          <br>
	 *                          <br>
	 *                          <br>
	 */
	public String cashPrize(int lotteryId,String cashTerm, String orderId,LotteryTermModel termInfo,boolean isCheckLotteryId,Map<String,String> outResult) throws LotteryException;
	/**
	 * 
	 * Title: dispatchPrize<br>
	 * Description: <br>代购派奖
	 *              <br>对单个订单派奖，只对兑奖成功或正在派奖的可以进行派奖<br>
	 * @param lotteryId
	 * @param dispatchTerm
	 * @param orderId
	 * @param isCheckLotteryId
	 * @return 奖金级别#奖金金额#处理结果
	 * @throws LotteryException<br>
	 *                          <br>510006:该订单对应的彩期与要派奖的期数不一致
	 *                          
	 */
	public String dispatchPrize(int lotteryId,String dispatchTerm, String orderId,boolean isCheckLotteryId) throws LotteryException;
	
	/**
	 * 
	 * Title: dispatchCoopPrize<br>
	 * Description: <br>合买派奖
	 *              <br>对合买的单个参与派奖
	 * @param lotteryId 彩种
	 * @param dispatchTerm 派奖彩期
	 * @param chuPiaoOrder 出票订单的id
	 * @param paiJiangOrder 派奖订单的id 
	 * @param isCheckLotteryId 是否比对彩期
	 * @return
	 * @throws LotteryException
	 */
	public String dispatchCoopPrize(int lotteryId,String dispatchTerm,String chuPiaoOrder, String paiJiangOrder,boolean isCheckLotteryId) throws LotteryException;
	
	/**
	 * 
	 * Title: checkTermCanCash<br>
	 * Description: <br>
	 *            校验彩期是否可以兑奖并返回彩期信息<br>
	 * @param lotteryId 彩种id
	 * @param termNo 期号
	 * @return 彩期信息
	 * @throws LotteryException<br>
	 *                          <br>510002:该订单对应的彩期不到兑奖时间
	 *                          <br>511001:兑奖程序出错
	 */
	public LotteryTermModel checkTermCanCash(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: checkTermCanDispatch<br>
	 * Description: <br>
	 *            校验彩期是否可以派奖奖并返回彩期信息<br>
	 * @param lotteryId
	 * @param termNo
	 * @return LotteryTermModel
	 * @throws LotteryException<br>
	 *                          <br>510005:该订单对应的彩期不到派奖时间
	 *                          <br>511001:派奖程序出错
	 */
	public LotteryTermModel checkTermCanDispatch(int lotteryId, String termNo) throws LotteryException;
	/**
	 * 
	 * Title: updateCompleteStatus<br>
	 * Description: <br>
	 *            根据条件更新彩期表的状态为兑奖完成或正在兑奖<br>
	 * @param lotteryId 彩种id
	 * @param termNo 期数
	 * @throws LotteryException<br>
	 *                          <br>
	 */
	public String updateCashPrizeCompleteStatus(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: updateDispatchPrizeCompleteStatus<br>
	 * Description: <br>
	 *            根据条件更新彩期表的状态为派奖完成或正在派奖<br>
	 * @param lotteryId 彩种id
	 * @param termNo 彩期
	 * @param termWinStatus 彩期的当前状态
	 * @throws LotteryException
	 */
	public void updateDispatchPrizeCompleteStatus(int lotteryId, String termNo,int termWinStatus) throws LotteryException;
	/**
	 * 
	 * Title: getOrderInfo<br>
	 * Description: <br>
	 *            根据订单id得到订单信息，不锁表<br>
	 * @param orderId 订单id
	 * @return BetOrderDomain
	 * @throws LotteryException<br>
	 *                          <br>511001:程序出错
	 */
	public BetOrderDomain getOrderInfo(String orderId) throws LotteryException;
	/**
	 * 
	 * Title: getNotCashOrder<br>
	 * Description: <br>
	 *            获取可以兑奖奖但尚未兑奖的订单编号<br>
	 * @param lotteryId 彩种id
	 * @param termNo 彩期
	 * @return 订单编号
	 * @throws LotteryException<br>
	 *                          <br>511001:兑奖程序出错
	 */
	public List<String> getNotCashOrder(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: getNotDispatchOrder<br>
	 * Description: <br>
	 *            获取可以派奖但尚未派奖的订单编号<br>
	 * @param lotteryId
	 * @param termNo
	 * @return List<String>
	 * @throws LotteryException
	 */
	public List<String> getNotDispatchOrder(int lotteryId,String termNo) throws LotteryException;
	
	/**
	 * 
	 * Title: dealNotTicketZhuiHao<br>
	 * Description: <br>
	 *              <br>处理尚未送入出票的追号订单
	 * @param lotteryId 彩种
	 * @param termNo 兑奖期的下一期
	 * @param limitNumber 限号信息
	 * @param orderId 需要处理的订单编号
	 * @throws LotteryException
	 */
	public String dealNotTicketZhuiHao(int lotteryId,String termNo,String limitNumber,String orderId) throws LotteryException;
	
	/**
	 * 
	 * Title: getNotTicketSucessOrderNum<br>
	 * Description: <br>
	 *              <br>查询一个彩期中订单状态是0，1，2，3的订单数量，如果能查到数据说明还有订单需要出票确认，该彩期不能兑奖
	 * @param lotteryId
	 * @param termNo
	 * @throws LotteryException
	 */
	public void checkNotTicketSucessOrder(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: getTermInfo<br>
	 * Description: <br>
	 *              <br>获取彩期信息
	 * @param lotteryId
	 * @param termNo
	 * @return
	 * @throws LotteryException
	 */
	public LotteryTermModel getTermInfo(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: queryOrderByStatus<br>
	 * Description: <br>
	 *              <br>获取指定状态的订单
	 * @param lotteryId
	 * @param termNo
	 * @param orderStatus
	 * @param winStatus
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryOrderByStatus(int lotteryId,String termNo,int orderStatus,List<Integer> winStatus) throws LotteryException;
	
	//public BetOrderDomain getCoopInfo(String coopInfoId) throws LotteryException;
	

}
