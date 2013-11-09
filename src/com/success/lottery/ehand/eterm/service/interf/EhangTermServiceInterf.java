/**
 * Title: EhangTermServiceInterf.java
 * @Package com.success.lottery.term.dao.interf
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-19 下午05:04:06
 * @version V1.0
 */
package com.success.lottery.ehand.eterm.service.interf;

import com.success.lottery.ehand.eterm.domain.EhandTermModel;
import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.term.dao.interf
 * EhangTermServiceInterf.java
 * EhangTermServiceInterf
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-19 下午05:04:06
 * 
 */

public interface EhangTermServiceInterf {
	public static final int E_1002_CODE = 311002;
	public static final String E_1002_DESC = "更新彩期信息出错！";
	public static final int E_1004_CODE = 311004;
	public static final String E_1004_DESC = "获取彩期信息出错！";
	
	/**
	 * 
	 * Title: getEhandTermInfoByBet<br>
	 * Description: <br>
	 *              <br>根据彩种和彩期获取ehand的彩期信息
	 * @param lotteryId 投注系统彩种
	 * @param issue 彩期
	 * @return
	 * @throws LotteryException
	 */
	public EhandTermModel getEhandTermInfoByBet(int lotteryId,String issue) throws LotteryException;
	/**
	 * 
	 * Title: getEhandTermInfo<br>
	 * Description: <br>
	 *              <br>根据彩种和彩期获取ehand的彩期信息
	 * @param ehandLotteryId ehand系统彩种
	 * @param issue
	 * @return
	 * @throws LotteryException
	 */
	public EhandTermModel getEhandTermInfo(String ehandLotteryId,String issue) throws LotteryException;
	/**
	 * 
	 * Title: updateEhandInfo<br>
	 * Description: <br>
	 *              <br>更新彩期的开始时间、结束时间、彩期状态
	 *              <br>两个彩种可选填一个，并且必须填一个
	 * @param lotteryId 投注系统彩种，如果不指定需要传入 0
	 * @param ehandLotteryId ehand彩种,如果不指定需要传入null
	 * @param issue 彩期
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param printStart
	 * @param printEnd
	 * @param status 彩期状态
	 * @return
	 * @throws LotteryException
	 */
	public int updateEhandInfo(int lotteryId,String ehandLotteryId,String issue,String startTime,String endTime,String printStart,String printEnd,int status) throws LotteryException;
	
	/**
	 * 
	 * Title: updateEhandInfo<br>
	 * Description: <br>
	 *              <br>更新彩期的开奖结果
	 *              <br>两个彩种可选填一个，并且必须填一个
	 * @param lotteryId 投注系统彩种，如果不指定需要传入 0
	 * @param ehandLotteryId ehand彩种,如果不指定需要传入null
	 * @param issue 彩期
	 * @param bonuscode 开奖结果
	 * @return
	 * @throws LotteryException
	 */
	public int updateEhandInfo(int lotteryId,String ehandLotteryId,String issue,String bonuscode,int status) throws LotteryException;
	/**
	 * 
	 * Title: updateEhandInfo<br>
	 * Description: <br>
	 *              <br>更新彩期的状态
	 *              <br>两个彩种可选填一个，并且必须填一个
	 * @param lotteryId 投注系统彩种，如果不指定需要传入 0
	 * @param ehandLotteryId ehand彩种,如果不指定需要传入null
	 * @param issue 彩期
	 * @param status 彩期状态
	 * @return
	 * @throws LotteryException
	 */
	public int updateEhandInfo(int lotteryId,String ehandLotteryId,String issue,int status) throws LotteryException;
	/**
	 * 
	 * Title: updateEhandInfoMoney<br>
	 * Description: <br>
	 *              <br>更新彩期的销售额及中奖额
	 *               <br>两个彩种可选填一个，并且必须填一个
	 * @param lotteryId 投注系统彩种，如果不指定需要传入 0
	 * @param ehandLotteryId  ehand彩种,如果不指定需要传入null
	 * @param issue 彩期
	 * @param salemoney 销售额
	 * @param bonusmoney 中奖额
	 * @return
	 * @throws LotteryException
	 */
	public int updateEhandInfoMoney(int lotteryId,String ehandLotteryId,String issue,String salemoney,String bonusmoney) throws LotteryException;
	
	/**
	 * 
	 * Title: insertEhandMsgLog<br>
	 * Description: <br>
	 *              <br>记录掌中奕的消息，不对外抛出异常
	 * @param msgType
	 * @param msgId
	 * @param msgUserId
	 * @param msgCommand
	 * @param msgKey
	 * @param msgCode
	 * @param msgContent
	 * @param reserve
	 */
	public void insertEhandMsgLog(int msgType,String msgId,String msgUserId,String msgCommand,String msgKey,String msgCode,String msgContent,String reserve);

}
