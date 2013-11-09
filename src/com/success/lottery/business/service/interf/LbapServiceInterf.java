/**
 * Title: LbapService.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-5 下午06:24:42
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import com.success.lottery.business.domain.LbapMsgDomain;
import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.business.service.interf
 * LbapService.java
 * LbapService
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-5 下午06:24:42
 * 
 */

public interface LbapServiceInterf {
	
	/**
	 * 
	 * Title: insertLbapMsg<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param clientid
	 * @param commandid
	 * @param messageid
	 * @param result
	 * @param messagebody
	 * @param reserve
	 * @return
	 * @throws LotteryException
	 */
	public String insertLbapMsg(String clientid,String commandid,String messageid,String result,String messagebody,String reserve) throws LotteryException;
	/**
	 * 
	 * Title: getLbapMsg<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param clientid
	 * @param commandid
	 * @param messageid
	 * @return
	 * @throws LotteryException
	 */
	public LbapMsgDomain getLbapMsg(String clientid,String commandid,String messageid) throws LotteryException;

}
