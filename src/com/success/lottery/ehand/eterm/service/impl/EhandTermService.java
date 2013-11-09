/**
 * Title: EhandTermService.java
 * @Package com.success.lottery.ehand.eterm.service.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-19 下午06:27:09
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
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-19 下午06:27:09
 * 
 */

public class EhandTermService implements EhangTermServiceInterf {
	private static Log logger = LogFactory.getLog(EhandTermService.class);
	
	private EhandTermDaoImpl ehandTermDao;
	private EhandMsgDao eMsgDao;

	/* (非 Javadoc)
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
			logger.error("方法:[getEhandTermInfo],获取掌中奕彩期信息出错!",e);
			throw new LotteryException(E_1004_CODE,E_1004_DESC);
		}
		return eTermInfo;
	}

	/* (非 Javadoc)
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
			logger.error("方法:[getEhandTermInfo],获取掌中奕彩期信息出错!",e);
			throw new LotteryException(E_1004_CODE,E_1004_DESC);
		}
		return eTermInfo;
	}

	/* (非 Javadoc)
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
			logger.error("方法[updateEhandInfo],更新掌中奕彩期的开始、结束时间、状态出错");
			throw e;
		}catch(Exception ex){
			logger.error("方法[updateEhandInfo],更新掌中奕彩期的开始、结束时间、状态出错",ex);
			throw new LotteryException(E_1002_CODE,E_1002_DESC);
		}
		return updateNum;
	}

	/* (非 Javadoc)
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
			logger.error("方法[updateEhandInfo],更新掌中奕彩期的开奖号码");
			throw e;
		}catch(Exception ex){
			logger.error("方法[updateEhandInfo],更新掌中奕彩期的开奖号码",ex);
			throw new LotteryException(E_1002_CODE,E_1002_DESC);
		}
		return updateNum;
	}

	/* (非 Javadoc)
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
			logger.error("方法[updateEhandInfo],更新掌中奕彩期的状态出错");
			throw e;
		}catch(Exception ex){
			logger.error("方法[updateEhandInfo],更新掌中奕彩期的状态出错",ex);
			throw new LotteryException(E_1002_CODE,E_1002_DESC);
		}
		return updateNum;
	}

	/* (非 Javadoc)
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
			logger.error("方法[updateEhandInfo],更新掌中奕彩期的销售金额、中奖金额出错");
			throw e;
		}catch(Exception ex){
			logger.error("方法[updateEhandInfo],更新掌中奕彩期的销售金额、中奖金额出错",ex);
			throw new LotteryException(E_1002_CODE,E_1002_DESC);
		}
		return updateNum;
	}
	/**
	 * 
	 * Title: updateEhandTermInfo<br>
	 * Description: <br>
	 *              <br>更新ehand表信息
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
			logger.error("方法[updateEhandTermInfo],更新掌中奕彩期信息出错!", e);
			throw new LotteryException(E_1002_CODE,E_1002_DESC);
		}
		return updateNum;
	}
	/*
	 * (非 Javadoc)
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
