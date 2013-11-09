/**
 * Title: LbapService.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-5 ����06:24:42
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import com.success.lottery.business.domain.LbapMsgDomain;
import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.business.service.interf
 * LbapService.java
 * LbapService
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-5 ����06:24:42
 * 
 */

public interface LbapServiceInterf {
	
	/**
	 * 
	 * Title: insertLbapMsg<br>
	 * Description: <br>
	 *            (������һ�仰�����������������)<br>
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
	 *            (������һ�仰�����������������)<br>
	 * @param clientid
	 * @param commandid
	 * @param messageid
	 * @return
	 * @throws LotteryException
	 */
	public LbapMsgDomain getLbapMsg(String clientid,String commandid,String messageid) throws LotteryException;

}
