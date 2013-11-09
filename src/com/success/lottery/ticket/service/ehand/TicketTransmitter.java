/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.success.lottery.ticket.service.ehand;

import com.success.lottery.ehand.ehandserver.bussiness.EhandTicketService;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.service.DYJSplitTools;
import com.success.lottery.ticket.service.TicketDispatch;
import com.success.lottery.ticket.service.TicketLogInfo;
import com.success.lottery.ticket.service.TicketLogger;
import com.success.lottery.ticket.service.interf.BetTicketServiceInterf;
import com.success.protocol.ticket.zzy.model.TicketBetResult;
import com.success.protocol.ticket.zzy.util.EhandUtil;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.ssmp.monitor.SSMPException;
import com.success.utils.ssmp.monitor.ThreadSpectacle;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author bing.li
 */
public class TicketTransmitter implements ThreadSpectacle{

	private Semaphore semaphore;

	private String name;
	private String bootTime;

	private boolean isStop = false;
	private boolean isExit = false;
	private Log logger = LogFactory.getLog(TicketTransmitter.class.getName());
	
	private int ticketCount;
	private int transSucc;
	private int transFail;

	private String station = "eHand";
	
	private String [] reSendCode;//Ʊ��������Ҫ���·��͵ķ�����
	private String[] sendSucessCode;//��Ʊ�������ʾ�ɹ��ķ�����
	private String[] sendFailCode;//��Ʊ�������ʾ��Ʊʧ�ܵķ�����
	private String[] termEndCode;//��Ʊ�������ʾ��Ʊϵͳ�ڽ᲻���ټ�����
	private String[] canNotSendCode;//��Ʊ�������ʾ��Ʊϵͳ����ʧ�ܲ��Ҳ�������

	public void setParameter(String parameter) {
		bootTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void stop() throws SSMPException {
		this.isStop = true;
	}

	public String showInfo() {
		return "T - TICKETTRANSMITTER - " + name + " - " + bootTime + " - Transmitted[" + ticketCount + "/" + transSucc + "/" + transFail + "] - " + isAlive();
	}

	public String showDetail() {
		StringBuffer sb = new StringBuffer();
		sb.append(showInfo() + "\n");
		sb.append("\t").append(this.name + "'s Parameter:").append("\n");
		sb.append("\t\t");
		sb.append("isStop=" + this.isStop).append("\n");
		sb.append("\t\t");
		sb.append("isExit=").append(this.isExit).append("\n");
		sb.append("\t\t");
		sb.append("isAlive=").append(this.isAlive()).append("\n");
		sb.append("\n");
		sb.append("\t\t");
		sb.append("ticketCount=").append(this.ticketCount).append("\n");
		sb.append("\t\t");
		sb.append("transSucc=").append(this.transSucc).append("\n");
		sb.append("\t\t");
		sb.append("transFail=").append(this.transFail).append("\n");
		sb.append("to be done");
		return sb.toString();
	}

	public boolean isAlive() {
		return !isStop && !isExit;
	}

	public void spectacle(String command, PrintWriter pw) {
		if("loadTrnasmittedTicket".equalsIgnoreCase(command.trim())){
			//loadSendTicketWhenStart();
			pw.println("loaded need sned ticket from database!");
			pw.flush();
		}else{
			pw.println(showInfo());
			pw.flush();
		}
	}

	public void run() {
		if(isStop){
			logger.warn("TicketTransmitter's parameter is null, will exit...");
			return;
		}
		logger.info(name + " is running...");
		String resource = "com.success.lottery.ticket.service.TicketRouter";
		semaphore = new Semaphore(AutoProperties.getInt(station + ".ticketTransmitter.available", 50, resource), true);
		long interval = AutoProperties.getInt(station + ".ticketTransmitter.interval", 200, resource);
		//��Ʊ������Ķ��弯��
		reSendCode = AutoProperties.getString(station + ".ticketTransmitter.reSendCode", "12903", resource).split(",");
		sendSucessCode = AutoProperties.getString(station + ".ticketTransmitter.sendSucessCode", "0", resource).split(",");
		sendFailCode = AutoProperties.getString(station + ".ticketTransmitter.sendFailCode",
						"11016,11017,11018,12001,12002,12003,12033,12034,12501,12509,12510,12600,12900,12901,12902,14001,14002",resource).split(",");
		termEndCode = AutoProperties.getString(station + ".ticketTransmitter.termEndCode", "11016,12501", resource).split(",");
		canNotSendCode = AutoProperties.getString(station + ".ticketTransmitter.canNotSendCode", "12002,12003,12034", resource).split(",");
		
		while(!isStop){
			try{
				semaphore.acquire();
			}catch(InterruptedException e){
			}
			try{
				logger.debug("TicketTransmitter get a available semaphore, " + semaphore.availablePermits() + " permits available.");
				BetTicketDomain ticket = TicketDispatch.getTicket(station);
				if(ticket != null){
					TicketTransmit transmit = new TicketTransmit(ticket);
					transmit.setName("TicketTrnasmit-" + ticket.getTicketSequence());
					logger.debug("TicketTransmitter get a ticket(" + ticket.getTicketSequence() + "), will start TicketTransmit[" + transmit.getName() + "] thread.");
					transmit.start();
				}else{
					semaphore.release();
					logger.debug("TicketTransmitter get null ticket, release the semaphore and sleep " + interval + "ms, " + semaphore.availablePermits() + " permits available now. ");
					try{
						Thread.sleep(interval);
					}catch(Exception e){
					}
				}
			}catch(Exception e){
				logger.warn("TicketTransmitter occur unknow Exception:" + e);
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
				logger.debug("TicketTransmitter occur excepiton, release the semaphore, " + semaphore.availablePermits() + " permits available now.");
			}
		}
	}
	
	private class TicketTransmit extends Thread{
		private BetTicketDomain ticket;
		private String resource = "com.success.lottery.ticket.service.TicketRouter";
		
		private int reSendNum;
		private long reSendSleep;
		private Map<String,String> logParam = new HashMap<String, String>();
		
		public TicketTransmit(BetTicketDomain ticket){
			this.ticket = ticket;
			reSendNum = AutoProperties.getInt(station + ".ticketTransmitter.reSendNum", 3, resource);
			reSendSleep = AutoProperties.getInt(station + ".ticketTransmitter.reSendSleep", 1000, resource);
			
			logParam.put("userId", "0");
			logParam.put("userName", "admin");
			logParam.put("keyword1", String.valueOf(ticket.getLotteryId()) + "|" + ticket.getBetTerm());
			logParam.put("keyword2", ticket.getTicketSequence());
			
		}
		public void run(){
			try{
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ") to " + station + " is:");
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s ticketSequence = " + ticket.getTicketSequence());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s orderId		  = " + ticket.getOrderId());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s lotteryId	  = " + ticket.getLotteryId());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s betTerm		  = " + ticket.getBetTerm());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s playType		  = " + ticket.getPlayType());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s betType		  = " + ticket.getBetType());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s betMultiple	  = " + ticket.getBetMultiple());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s betCode		  = " + ticket.getBetCode());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s betCount		  = " + ticket.getBetCount());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s betAmount	  = " + ticket.getBetAmount());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s ticketTime	  = " + ticket.getTicketTime());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s lastTicketTime = " + ticket.getLastTicketTime());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s areaCode		  = " + ticket.getAreaCode());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s printStation	  = " + ticket.getPrintStation());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s ticketStatus	  = " + ticket.getTicketStatus());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s ticketId		  = " + ticket.getTicketId());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s printerId	  = " + ticket.getPrinterId());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s printResult	  = " + ticket.getPrintResult());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s printTime	  = " + ticket.getPrintTime());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s ticketData	  = " + ticket.getTicketData());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s ticketDataMD	  = " + ticket.getTicketDataMD());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s ticketPassword = " + ticket.getTicketPassword());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s preTaxPrize	  = " + ticket.getPreTaxPrize());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s prizeResult	  = " + ticket.getPrizeResult());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s saveStatus	  = " + ticket.getSaveStatus());
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket(" + ticket.getTicketSequence() + ")'s reserve		  = " + ticket.getReserve());

				//���͵�EHand���õ���Ӧ��������ݷ�����������
				ticketCount++;
				TicketLogInfo info = new TicketLogInfo();
				String rs = null;
				String ticketReponseStatus = null;//������Ϣ�Ĵ�����
				try{
					info.setName("TICKETTRANSMITTER");
					info.setInTime(System.currentTimeMillis());
					info.setProcessorName(this.getName());
					info.setTicketSequence(ticket.getTicketSequence());
					info.setOrderId(ticket.getOrderId());
					
					boolean isNeedReSend = false;//�Ƿ���Ҫ���·���,Ĭ�ϲ���Ҫ
					
					TicketBetResult printTicketResult = null;
					//���������ý��
					for(int i = 0 ; i < reSendNum;i++){
						printTicketResult = null;
						//������Ʊ�ӿڵõ���Ӧ���
						try{
							printTicketResult = EhandTicketService.printOneTicket(ticket);
						}catch(LotteryException e){
							logger.error("��Ʊ�͵�������ϵͳ�����쳣:"+ e.getType() + "#" + e.getMessage());
							//������Ʊ�ӿڷ��ص��쳣���ǲ������·��͵��쳣������ͨѶ����ͨѶ��Ӧ������Ϣ�������������������
							isNeedReSend = false;
						}
						
						//�������û�з����쳣����Ʊ��ҵ����,��Ʊ�ӿڷ��ص�printTicketResult���󲻿���Ϊ��ֵ
						ticketReponseStatus = printTicketResult.getErrorCode();
						if(Arrays.asList(reSendCode).contains(ticketReponseStatus)){//���ݷ�������Ҫ������Ʊ
							isNeedReSend = true;
						}else{//����Ҫ������Ʊ������Ϊ��ȷ����Ϊ����
							isNeedReSend = false;
						}
						
						if (isNeedReSend) {// ��Ҫ���·���
							logger.info("��Ʊ�͵�������ϵͳ�ط����󣬵�" + (i+1) + "��");
							Thread.sleep(reSendSleep);// ���1�����·���
						} else {
							break;
						}
					}
					
					//�Եõ��Ľ��������
					int betTicketStatus = -1;//����Ҫ���µ�Ͷעϵͳ��Ʊ״̬
					String printId = null,printResult = null,ticketData = null;
					
					if(printTicketResult == null || Arrays.asList(reSendCode).contains(ticketReponseStatus)){//��������ʧ�ܣ���m���ط�������n�β��ɹ�������Ϊ��״̬
						transFail++;
						logger.debug("TicketTransmitter send the ticket(" + ticket.getTicketSequence() + ") to ehand fail.reponseCode:"+ticketReponseStatus);
						//дһ��ϵͳ��־
						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "������Ʊ��Ʊʧ��,��Ӧ��:"+ticketReponseStatus, logParam);
						//����Ʊ��״̬Ϊԭ����״̬(��������Ʊ��״̬)���ύ��Ʊ����ʧ��
						betTicketStatus = ticket.getTicketStatus();
						printResult = ticketReponseStatus;
						rs = "�ط���ʧ��,��Ӧ��:" + ticketReponseStatus + "Ʊ״̬��Ϊ"+betTicketStatus;
					}else{
						String ticketSendStatus = printTicketResult.getErrorCode();
						logger.debug("TicketTransmitter send the ticket(" + ticket.getTicketSequence() + ") to ehand success.reponseCode:"+ticketSendStatus);
						transSucc++;
						if(Arrays.asList(sendSucessCode).contains(ticketSendStatus)){//��Ӧ״̬��Ϊ0��ʾ��Ʊ�ɹ�
							betTicketStatus = 2;
							printId = printTicketResult.getOpreateId();
							printResult = "0";
							ticketData = printTicketResult.getAccountValue();
							rs = "���ͷ��سɹ�,��Ӧ��:"+ticketSendStatus+"Ʊ״̬��Ϊ:" + betTicketStatus;
						}else if(Arrays.asList(sendFailCode).contains(ticketSendStatus)){//����Ϊʧ�ܵķ����룬������Ʊ��״̬
							betTicketStatus = ticket.getTicketStatus();
							printId = printTicketResult.getOpreateId();
							printResult = ticketSendStatus;
							ticketData = printTicketResult.getHeaddErrorMsg();
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "������Ʊ��Ʊ���ش���״̬��:"+ticketSendStatus, logParam);
							rs = "���ͷ���ʧ��,��Ӧ��:"+printResult+"Ʊ״̬��Ϊ:" + betTicketStatus;
						}else if(Arrays.asList(termEndCode).contains(ticketSendStatus)){//���ڽ����������ټ�����
							betTicketStatus = 8;
							printId = printTicketResult.getOpreateId();
							printResult = ticketSendStatus;
							ticketData = printTicketResult.getHeaddErrorMsg();
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "������Ʊ��Ʊ���ش���״̬��:"+ticketSendStatus, logParam);
							rs = "���ͷ���ʧ��,��Ӧ��:"+printResult+"Ʊ״̬��Ϊ:" + betTicketStatus;
						}else if(Arrays.asList(canNotSendCode).contains(ticketSendStatus)){//�����������͵�״̬������Ч�Ĳ�Ʊ�淨
							betTicketStatus = 9;
							printId = printTicketResult.getOpreateId();
							printResult = ticketSendStatus;
							ticketData = printTicketResult.getHeaddErrorMsg();
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "������Ʊ��Ʊ���ش���״̬��:"+ticketSendStatus, logParam);
							rs = "���ͷ���ʧ��,��Ӧ��:"+printResult+"Ʊ״̬��Ϊ:" + betTicketStatus;
						}else{//δ����ķ����룬��δ���,�Ƚ�Ʊ��״̬����ΪƱ��ԭ��״̬���ύ��Ʊ������δ֪
							betTicketStatus = ticket.getTicketStatus();
							printId = printTicketResult.getOpreateId();
							printResult = ticketSendStatus;
							ticketData = printTicketResult.getHeaddErrorMsg();
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "������Ʊ��Ʊ����δ֪״̬:"+ticketSendStatus, logParam);
							rs = "���ͷ���,��Ӧ��:" + ticketSendStatus;
						}
					}
					//����Ͷעϵͳ��Ʊ��
					try{
						BetTicketServiceInterf betTicektService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
						int upResult = betTicektService.updateBetTicketPrintInfo(ticket.getTicketSequence(), betTicketStatus, null, printId, printResult, ticketData, null, null);
						if(upResult <= 0){
							logger.debug("TicketTransmitter send the ticket(" + ticket.getTicketSequence() + ") to ehand update betticket table fail,ticketstatus::" + betTicketStatus);
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "������Ʊ��Ʊ���ظ���Ʊ��δ���µ���Ӧ������", logParam);
							rs = rs + " ����Ʊʧ��,δ���µ���Ӧ��Ʊ";
						}else {
							logger.debug("TicketTransmitter send the ticket(" + ticket.getTicketSequence() + ") to ehand update betticket table success,ticketstatus::" + betTicketStatus);
							rs = rs + " ����Ʊ�ɹ�";
						}
					}catch(Exception e){
						logger.debug("TicketTransmitter send the ticket(" + ticket.getTicketSequence() + ") to ehand update betticket table fail,exception:" + e);
						rs = rs + " ����Ʊʧ��,�����쳣";
						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "������Ʊ��Ʊ���ظ���Ʊ��ʧ��", logParam);
					}
					
					
				}catch(Exception e){
					rs = rs + "�������쳣";
					logger.error("TicketTransmitter send the ticket(" + ticket.getTicketSequence() + ") to ehand fail.", e);
					OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "������Ʊ��Ʊ�������쳣:"+e.getMessage(), logParam);
				}
				
				info.setResult(rs);
				info.setOutTime(System.currentTimeMillis());
				TicketLogger.getInstance("TICKET").log(info);
				
			}catch(Exception e){
			}finally{
				semaphore.release();
				logger.debug("TicketTransmit[" + this.getName() + "] transmit the ticket is done and release the semaphore, " + semaphore.availablePermits() + " permits available now.");
			}
		}
	}
}
