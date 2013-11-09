/**
 * Title: EhandTermService.java
 * @Package com.success.lottery.ehand.eterm.service.impl
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-19 ����06:27:09
 * @version V1.0
 */
package com.success.lottery.ehand.eterm.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.ehand.eterm.dao.impl.EhandMsgDao;
import com.success.lottery.ehand.eterm.dao.impl.EhandTermDaoImpl;
import com.success.lottery.ehand.eterm.domain.EhandMsgModel;
import com.success.lottery.ehand.eterm.domain.EhandTermModel;
import com.success.lottery.ehand.eterm.service.interf.EhangTermServiceInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.util.LotteryTools;

/**
 * com.success.lottery.ehand.eterm.service.impl
 * EhandTermService.java
 * EhandTermService
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-19 ����06:27:09
 * 
 */

public class EhandTermService implements EhangTermServiceInterf {
	private static Log logger = LogFactory.getLog(EhandTermService.class);
	
	private EhandTermDaoImpl ehandTermDao;
	private EhandMsgDao eMsgDao;

	/* (�� Javadoc)
	 *Title: getEhandTermInfoByBet
	 *Description: 
	 * @param lotteryId
	 * @param issue
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.ehand.eterm.service.interf.EhangTermServiceInterf#getEhandTermInfo(int, java.lang.String)
	 */
	public EhandTermModel getEhandTermInfoByBet(int lotteryId, String issue)
			throws LotteryException {
		EhandTermModel eTermInfo = null;
		try{
			eTermInfo = this.getEhandTermDao().getEhandTermInfo(lotteryId, null, issue);
		}catch(Exception e){
			logger.error("����:[getEhandTermInfo],��ȡ�����Ȳ�����Ϣ����!",e);
			throw new LotteryException(E_1004_CODE,E_1004_DESC);
		}
		return eTermInfo;
	}

	/* (�� Javadoc)
	 *Title: getEhandTermInfo
	 *Description: 
	 * @param ehandLotteryId
	 * @param issue
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.ehand.eterm.service.interf.EhangTermServiceInterf#getEhandTermInfo(java.lang.String, java.lang.String)
	 */
	public EhandTermModel getEhandTermInfo(String ehandLotteryId, String issue)
			throws LotteryException {
		EhandTermModel eTermInfo = null;
		try{
			eTermInfo = this.getEhandTermDao().getEhandTermInfo(0, ehandLotteryId, issue);
		}catch(Exception e){
			logger.error("����:[getEhandTermInfo],��ȡ�����Ȳ�����Ϣ����!",e);
			throw new LotteryException(E_1004_CODE,E_1004_DESC);
		}
		return eTermInfo;
	}

	/* (�� Javadoc)
	 *Title: updateEhandInfo
	 *Description: 
	 * @param lotteryId
	 * @param ehandLotteryId
	 * @param issue
	 * @param startTime
	 * @param endTime
	 * @param status
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.ehand.eterm.service.interf.EhangTermServiceInterf#updateEhandInfo(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)
	 */
	public int updateEhandInfo(int lotteryId, String ehandLotteryId,
			String issue, String startTime, String endTime,String printStart,String printEnd, int status)
			throws LotteryException {
		int updateNum = 0;
		try{
			EhandTermModel eterm = new EhandTermModel();
			eterm.setLotteryId(lotteryId);
			eterm.setEhandLotteryId(ehandLotteryId);
			eterm.setIssue(issue);
			eterm.setStartTime(startTime);
			eterm.setEndTime(endTime);
			eterm.setPrintStart(printStart);
			eterm.setPrintEnd(printEnd);
			eterm.setStatus(status);
			updateNum = this.updateEhandTermInfo(eterm);
		}catch(LotteryException e){
			logger.error("����[updateEhandInfo],���������Ȳ��ڵĿ�ʼ������ʱ�䡢״̬����");
			throw e;
		}catch(Exception ex){
			logger.error("����[updateEhandInfo],���������Ȳ��ڵĿ�ʼ������ʱ�䡢״̬����",ex);
			throw new LotteryException(E_1002_CODE,E_1002_DESC);
		}
		return updateNum;
	}

	/* (�� Javadoc)
	 *Title: updateEhandInfo
	 *Description: 
	 * @param lotteryId
	 * @param ehandLotteryId
	 * @param issue
	 * @param bonuscode
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.ehand.eterm.service.interf.EhangTermServiceInterf#updateEhandInfo(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int updateEhandInfo(int lotteryId, String ehandLotteryId,
			String issue, String bonuscode,int status) throws LotteryException {
		int updateNum = 0;
		try{
			EhandTermModel eterm = new EhandTermModel();
			eterm.setLotteryId(lotteryId);
			eterm.setEhandLotteryId(ehandLotteryId);
			eterm.setIssue(issue);
			eterm.setBonuscode(bonuscode);
			eterm.setStatus(status);
			updateNum = this.updateEhandTermInfo(eterm);
		}catch(LotteryException e){
			logger.error("����[updateEhandInfo],���������Ȳ��ڵĿ�������");
			throw e;
		}catch(Exception ex){
			logger.error("����[updateEhandInfo],���������Ȳ��ڵĿ�������",ex);
			throw new LotteryException(E_1002_CODE,E_1002_DESC);
		}
		return updateNum;
	}

	/* (�� Javadoc)
	 *Title: updateEhandInfo
	 *Description: 
	 * @param lotteryId
	 * @param ehandLotteryId
	 * @param issue
	 * @param status
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.ehand.eterm.service.interf.EhangTermServiceInterf#updateEhandInfo(int, java.lang.String, java.lang.String, int)
	 */
	public int updateEhandInfo(int lotteryId, String ehandLotteryId,
			String issue, int status) throws LotteryException {
		int updateNum = 0;
		try{
			EhandTermModel eterm = new EhandTermModel();
			eterm.setLotteryId(lotteryId);
			eterm.setEhandLotteryId(ehandLotteryId);
			eterm.setIssue(issue);
			eterm.setStatus(status);
			updateNum = this.updateEhandTermInfo(eterm);
		}catch(LotteryException e){
			logger.error("����[updateEhandInfo],���������Ȳ��ڵ�״̬����");
			throw e;
		}catch(Exception ex){
			logger.error("����[updateEhandInfo],���������Ȳ��ڵ�״̬����",ex);
			throw new LotteryException(E_1002_CODE,E_1002_DESC);
		}
		return updateNum;
	}

	/* (�� Javadoc)
	 *Title: updateEhandInfoMoney
	 *Description: 
	 * @param lotteryId
	 * @param ehandLotteryId
	 * @param issue
	 * @param salemoney
	 * @param bonusmoney
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.ehand.eterm.service.interf.EhangTermServiceInterf#updateEhandInfoMoney(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int updateEhandInfoMoney(int lotteryId, String ehandLotteryId,
			String issue, String salemoney, String bonusmoney)
			throws LotteryException {
		int updateNum = 0;
		try{
			EhandTermModel eterm = new EhandTermModel();
			eterm.setLotteryId(lotteryId);
			eterm.setEhandLotteryId(ehandLotteryId);
			eterm.setIssue(issue);
			eterm.setSalemoney(salemoney);
			eterm.setBonusmoney(bonusmoney);
			updateNum = this.updateEhandTermInfo(eterm);
		}catch(LotteryException e){
			logger.error("����[updateEhandInfo],���������Ȳ��ڵ����۽��н�������");
			throw e;
		}catch(Exception ex){
			logger.error("����[updateEhandInfo],���������Ȳ��ڵ����۽��н�������",ex);
			throw new LotteryException(E_1002_CODE,E_1002_DESC);
		}
		return updateNum;
	}
	/**
	 * 
	 * Title: updateEhandTermInfo<br>
	 * Description: <br>
	 *              <br>����ehand����Ϣ
	 * @param eTermInfo
	 * @return
	 * @throws LotteryException
	 */
	private int updateEhandTermInfo(EhandTermModel eTermInfo) throws LotteryException{
		int updateNum = 0;
		try{
			if(eTermInfo == null){
				throw new Exception();
			}
			updateNum = this.getEhandTermDao().updateEhandTermInfo(eTermInfo);
		}catch(Exception e){
			logger.error("����[updateEhandTermInfo],���������Ȳ�����Ϣ����!", e);
			throw new LotteryException(E_1002_CODE,E_1002_DESC);
		}
		return updateNum;
	}
	/*
	 * (�� Javadoc)
	*Title: insertEhandMsgLog
	*Description: 
	* @param msgType
	* @param msgId
	* @param msgUserId
	* @param msgCommand
	* @param msgKey
	* @param msgCode
	* @param msgContent
	* @param reserve
	* @see com.success.lottery.ehand.eterm.service.interf.EhangTermServiceInterf#insertEhandMsgLog(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void insertEhandMsgLog(int msgType, String msgId, String msgUserId, String msgCommand, String msgKey, String msgCode, String msgContent, String reserve) {
		try{
			EhandMsgModel eMsgInfo = new EhandMsgModel(msgType,msgId,msgUserId,msgCommand,msgKey,msgCode,msgContent,reserve);
			this.getEMsgDao().insertEhandMsg(eMsgInfo);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EhandTermDaoImpl getEhandTermDao() {
		return ehandTermDao;
	}

	public void setEhandTermDao(EhandTermDaoImpl ehandTermDao) {
		this.ehandTermDao = ehandTermDao;
	}

	public EhandMsgDao getEMsgDao() {
		return eMsgDao;
	}

	public void setEMsgDao(EhandMsgDao msgDao) {
		eMsgDao = msgDao;
	}

}
