/**
 * Title: LbapService.java
 * @Package com.success.lottery.business.service.impl
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-5 ����06:28:15
 * @version V1.0
 */
package com.success.lottery.business.service.impl;

import com.success.lottery.business.dao.impl.LbapDaoImpl;
import com.success.lottery.business.domain.LbapMsgDomain;
import com.success.lottery.business.service.interf.LbapServiceInterf;
import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.business.service.impl
 * LbapService.java
 * LbapService
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-5 ����06:28:15
 * 
 */

public class LbapService implements LbapServiceInterf {
	
	private LbapDaoImpl lbapDao;

	/* (�� Javadoc)
	 *Title: getLbapMsg
	 *Description: 
	 * @param clientid
	 * @param commandid
	 * @param messageid
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.LbapServiceInterf#getLbapMsg(java.lang.String, java.lang.String, java.lang.String)
	 */
	public LbapMsgDomain getLbapMsg(String clientid, String commandid,
			String messageid) throws LotteryException {
		LbapMsgDomain msg = null;
		try{
			LbapMsgDomain param = new LbapMsgDomain();
			param.setClientid(clientid);
			param.setCommandid(commandid);
			param.setMessageid(messageid);
			msg = this.getLbapDao().getLbapMsg(param);
		}catch(Exception e){
			throw new LotteryException(999999,"�������");
		}
		return msg;
	}

	/* (�� Javadoc)
	 *Title: insertLbapMsg
	 *Description: 
	 * @param clientid
	 * @param commandid
	 * @param messageid
	 * @param result
	 * @param messagebody
	 * @param reserve
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.LbapServiceInterf#insertLbapMsg(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String insertLbapMsg(String clientid, String commandid,
			String messageid, String result, String messagebody, String reserve)
			throws LotteryException {
		String ret = "";
		try{
			LbapMsgDomain param = new LbapMsgDomain();
			param.setClientid(clientid);
			param.setCommandid(commandid);
			param.setMessageid(messageid);
			param.setResult(result);
			param.setMessagebody(messagebody);
			param.setReserve(reserve);
			ret = this.getLbapDao().insertLbapMsg(param);
		}catch(Exception e){
			throw new LotteryException(999999,"�������");
		}
		return ret;
	}

	public LbapDaoImpl getLbapDao() {
		return lbapDao;
	}

	public void setLbapDao(LbapDaoImpl lbapDao) {
		this.lbapDao = lbapDao;
	}

}
