/**
 * Title: CashDlcTicket.java
 * @Package com.success.lottery.ehand.ehandserver.bussiness
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-25 下午05:13:28
 * @version V1.0
 */
package com.success.lottery.ehand.ehandserver.bussiness;

import java.util.HashMap;
import java.util.Map;

import com.success.lottery.business.service.interf.CashPrizeInterf;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.ehand.ehandserver.bussiness
 * CashDlcTicket.java
 * CashDlcTicket
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-25 下午05:13:28
 * 
 */

public class CashDlcTicket extends Thread {
	private int lotteryId;
	private String termNo;
	
	public CashDlcTicket(int lotteryId,String termNo){
		this.lotteryId = lotteryId;
		this.termNo = termNo;
	}
	/**
	 * 
	 */
	public void run(){
		try{
			Map<String,String> cashNum = new HashMap<String,String>();
			Map<String,String> dbLog = new HashMap<String,String>();
			dbLog.put("userId", "0");
			dbLog.put("userName", "admin");
			//dbLog.put("userKey", "0.0.0.0");
			
			cashNum.put("cashTotalNum", "0");//兑奖总条数
			cashNum.put("cashCurNum", "0");//当前已经兑奖条数
			cashNum.put("cashPersent", "0");//当前已经兑奖条数
			
			/*
			 * 调用兑奖方法兑奖
			 */
			CashPrizeInterf cashManager = ApplicationContextUtils.getService("busLotteryCashPrizeService",CashPrizeInterf.class);
			//兑奖
			Map<String,String> cashResult = cashManager.cashAutoOrder(this.lotteryId, this.termNo,cashNum,dbLog);
			
			//处理追号
			Map<String,String> zhuHaoResult = cashManager.dealNotTicketOrder(this.lotteryId, this.termNo, dbLog);
			
			//更新彩期状态
			Map<String,String> upTermResult = cashManager.updateCashTermStatus(this.lotteryId, this.termNo, dbLog);
			
			Map<String,String> logParam = new HashMap<String, String>();
			logParam.put("userId", "0");
			logParam.put("userName", "admin");
			logParam.put("keyword1", "");
			logParam.put("keyword2", this.lotteryId+"|"+this.termNo);
			
			OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕开奖通知后调用兑奖成功", logParam);
		}catch(Exception e){
			e.printStackTrace();
			Map<String,String> logParam = new HashMap<String, String>();
			logParam.put("userId", "0");
			logParam.put("userName", "admin");
			logParam.put("keyword1", "");
			logParam.put("keyword2", this.lotteryId+"|"+this.termNo);
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕开奖通知后调用兑奖出错", logParam);
		}
	}
	
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

}
