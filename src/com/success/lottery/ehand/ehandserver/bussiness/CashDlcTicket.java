/**
 * Title: CashDlcTicket.java
 * @Package com.success.lottery.ehand.ehandserver.bussiness
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-25 ����05:13:28
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
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-25 ����05:13:28
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
			
			cashNum.put("cashTotalNum", "0");//�ҽ�������
			cashNum.put("cashCurNum", "0");//��ǰ�Ѿ��ҽ�����
			cashNum.put("cashPersent", "0");//��ǰ�Ѿ��ҽ�����
			
			/*
			 * ���öҽ������ҽ�
			 */
			CashPrizeInterf cashManager = ApplicationContextUtils.getService("busLotteryCashPrizeService",CashPrizeInterf.class);
			//�ҽ�
			Map<String,String> cashResult = cashManager.cashAutoOrder(this.lotteryId, this.termNo,cashNum,dbLog);
			
			//����׷��
			Map<String,String> zhuHaoResult = cashManager.dealNotTicketOrder(this.lotteryId, this.termNo, dbLog);
			
			//���²���״̬
			Map<String,String> upTermResult = cashManager.updateCashTermStatus(this.lotteryId, this.termNo, dbLog);
			
			Map<String,String> logParam = new HashMap<String, String>();
			logParam.put("userId", "0");
			logParam.put("userName", "admin");
			logParam.put("keyword1", "");
			logParam.put("keyword2", this.lotteryId+"|"+this.termNo);
			
			OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȿ���֪ͨ����öҽ��ɹ�", logParam);
		}catch(Exception e){
			e.printStackTrace();
			Map<String,String> logParam = new HashMap<String, String>();
			logParam.put("userId", "0");
			logParam.put("userName", "admin");
			logParam.put("keyword1", "");
			logParam.put("keyword2", this.lotteryId+"|"+this.termNo);
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȿ���֪ͨ����öҽ�����", logParam);
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
