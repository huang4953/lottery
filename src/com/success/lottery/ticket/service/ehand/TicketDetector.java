/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.success.lottery.ticket.service.ehand;

import com.success.lottery.ehand.ehandserver.bussiness.EhandTicketService;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.service.TicketDispatch;
import com.success.lottery.ticket.service.TicketLogInfo;
import com.success.lottery.ticket.service.TicketLogger;
import com.success.lottery.ticket.service.interf.BetTicketServiceInterf;
import com.success.protocol.ticket.zzy.model.TicketBetResult;
import com.success.protocol.ticket.zzy.model.TicketQueryResult;
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
public class TicketDetector implements ThreadSpectacle{

	private Semaphore semaphore;

	private String name;
	private String bootTime;

	private boolean isStop = false;
	private boolean isExit = false;
	private Log logger = LogFactory.getLog(TicketDetector.class.getName());
	
	private int ticketCount;
	private int transSucc;
	private int transFail;

	private String station = "eHand";
	private int lotteryType = 1;

	public void setParameter(String parameter) {
		bootTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		if(StringUtils.isBlank(parameter)){
			logger.warn("TicketDetector is not set lotteryType, this detector will set lotteryType = 1.");
			this.lotteryType = 1;
		} else {
			try{
				lotteryType = Integer.parseInt(parameter.trim());
			}catch(Exception e){
				logger.warn("TicketDetector parseInt lotteryType parameter(" + parameter + ") to int occur exception: " + e.toString() + ", this detector will set lotteryType = 1.");
				lotteryType = 1;
			}
			if(lotteryType < 1 || lotteryType > 4){
				logger.warn("TicketDetector lotteryType(" + lotteryType + ") is not a valid value, this detector will set lotteryType = 1.");
				lotteryType = 1;
			}
		}
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
		return "T - TICKETDETECTOR[" + lotteryType + "] - " + name + " - " + bootTime + " - detected[" + ticketCount + "/" + transSucc + "/" + transFail + "] - " + isAlive();
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
		sb.append("station=").append(this.station).append("\n");
		sb.append("\n");
		sb.append("\t\t");
		sb.append("lotteryType=").append(this.lotteryType).append("\n");
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
		if("loadDetectedTicket".equalsIgnoreCase(command.trim())){
			//loadSendTicketWhenStart();
			pw.println("loaded need detect ticket from database!");
			pw.flush();
		}else{
			pw.println(showInfo());
			pw.flush();
		}
	}

	public void run() {
		if(isStop){
			logger.warn("TicketDetector's parameter is null, will exit...");
			return;
		}
		logger.info(name + " is running...");
		String resource = "com.success.lottery.ticket.service.TicketRouter";
		semaphore = new Semaphore(AutoProperties.getInt(station + ".ticketDetector." + lotteryType + ".available", 50, resource), true);
		long interval = AutoProperties.getInt(station + ".ticketDetector." + lotteryType + ".interval", 200, resource);
		while(!isStop){
			try{
				semaphore.acquire();
			}catch(InterruptedException e){
			}
			try{
				logger.debug("TicketDetector[" + lotteryType + "] get a available semaphore, " + semaphore.availablePermits() + " permits available.");
				BetTicketDomain ticket = TicketDispatch.getCheckTicketRequest(station, "" + lotteryType);
				if(ticket != null){
					TicketDetect detect = new TicketDetect(ticket);
					detect.setName("TicketDetect-" + ticket.getTicketSequence());
					logger.debug("TicketDetector[" + lotteryType + "] get a ticket(" + ticket.getTicketSequence() + "), will start TicketDetect[" + detect.getName() + "] thread.");
					detect.start();
				}else{
					semaphore.release();
					logger.debug("TicketDetector[" + lotteryType + "] get null ticket, release the semaphore and sleep " + interval + "ms, " + semaphore.availablePermits() + " permits available now. ");
					try{
						Thread.sleep(interval);
					}catch(Exception e){
					}
				}
			}catch(Exception e){
				logger.warn("TicketDetector[" + lotteryType + "] occur unknow Exception:" + e);
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
				logger.debug("TicketDetector[" + lotteryType + "] occur excepiton, release the semaphore, " + semaphore.availablePermits() + " permits available now.");
			}
		}
	}
	
	private class TicketDetect extends Thread{
		private BetTicketDomain ticket;
		private Map<String,String> logParam = new HashMap<String, String>();
		
		public TicketDetect(BetTicketDomain ticket){
			this.ticket = ticket;
			
			logParam.put("userId", "0");
			logParam.put("userName", "admin");
			logParam.put("keyword1", String.valueOf(ticket.getLotteryId()) + "|" + ticket.getBetTerm());
			logParam.put("keyword2", ticket.getTicketSequence());
			
		}
		public void run(){
			try{
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ") to " + station + " is:");
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s ticketSequence = " + ticket.getTicketSequence());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s orderId		  = " + ticket.getOrderId());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s lotteryId	  = " + ticket.getLotteryId());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s betTerm		  = " + ticket.getBetTerm());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s playType		  = " + ticket.getPlayType());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s betType		  = " + ticket.getBetType());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s betMultiple	  = " + ticket.getBetMultiple());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s betCode		  = " + ticket.getBetCode());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s betCount		  = " + ticket.getBetCount());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s betAmount	  = " + ticket.getBetAmount());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s ticketTime	  = " + ticket.getTicketTime());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s lastTicketTime = " + ticket.getLastTicketTime());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s areaCode		  = " + ticket.getAreaCode());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s printStation	  = " + ticket.getPrintStation());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s ticketStatus	  = " + ticket.getTicketStatus());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s ticketId		  = " + ticket.getTicketId());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s printerId	  = " + ticket.getPrinterId());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s printResult	  = " + ticket.getPrintResult());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s printTime	  = " + ticket.getPrintTime());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s ticketData	  = " + ticket.getTicketData());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s ticketDataMD	  = " + ticket.getTicketDataMD());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s ticketPassword = " + ticket.getTicketPassword());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s preTaxPrize	  = " + ticket.getPreTaxPrize());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s prizeResult	  = " + ticket.getPrizeResult());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s saveStatus	  = " + ticket.getSaveStatus());
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket(" + ticket.getTicketSequence() + ")'s reserve		  = " + ticket.getReserve());

				//���͵�EHand���õ���Ӧ��������ݷ�����������
				ticketCount++;
				TicketLogInfo info = new TicketLogInfo();
				String rs = null;
				String ticketReponseErrCode = null;//������Ϣ�Ĵ�����
				try{
					info.setName("TICKETDETECT");
					info.setInTime(System.currentTimeMillis());
					info.setProcessorName(this.getName());
					info.setTicketSequence(ticket.getTicketSequence());
					info.setOrderId(ticket.getOrderId());
					
					boolean isNeedReSend = false;//�Ƿ���Ҫ���·���,Ĭ�ϲ���Ҫ
					
					TicketQueryResult printTicketResult = null;
					
					//���������ý��
					try{
						printTicketResult = EhandTicketService.QueryPrintTicket(ticket.getTicketSequence());
					}catch(LotteryException e){
						logger.error("��Ʊ�͵�������ϵͳ�����쳣:"+ e.getType() + "#" + e.getMessage());
						//������Ʊ�ӿڷ��ص��쳣���ǲ������·��͵��쳣������ͨѶ����ͨѶ��Ӧ������Ϣ�������������������
					}
					
					//�Եõ��Ľ��������
					int betTicketStatus = 2;//����Ҫ���µ�Ͷעϵͳ��Ʊ״̬
					String printId = null,printResult = null,ticketData = null;
					
					ticketReponseErrCode = printTicketResult==null?null:printTicketResult.getErrorCode();
					
					if(printTicketResult == null || !"0".equals(ticketReponseErrCode)){//��ѯʧ��û�з���ֵ���߷��ص�״̬���ʾִ�д���
						transFail++;
						logger.debug("TicketDetector query the ticket(" + ticket.getTicketSequence() + ") from ehand fail.reponseCode:"+ticketReponseErrCode);
						//дһ��ϵͳ��־
						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����Ȳ�ѯ��Ʊ״̬ʧ��,��Ӧ��:"+ticketReponseErrCode, logParam);
						//����Ʊ��״̬
						/*
						 * ��һ�ε��߼���û�������
						 */
						if("11004".equals(ticketReponseErrCode)){//�������ˮ�ţ���ʾ�Ӵ�Ʊϵͳû�в�ѯ����һ��Ʊ
							betTicketStatus = 7;
							printResult = ticketReponseErrCode;
						}else{
							betTicketStatus = 2;
						}
						
						rs = "��ѯʧ��,��Ӧ��:"+ ticketReponseErrCode +"Ʊ״̬��Ϊ"+betTicketStatus;
						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "������Ʊ��Ʊ״̬��ѯ���ش���״̬��:"+ticketReponseErrCode, logParam);
					}else{
						String printTicketStatus = printTicketResult.getPrintStatus();
						logger.debug("TicketDetector query the ticket(" + ticket.getTicketSequence() + ") from ehand success.reponseCode:" + ticketReponseErrCode +" printTicketStatus:"+printTicketStatus);
						transSucc++;
						if("0".equals(printTicketStatus)){//�ȴ���Ʊ
							betTicketStatus = 2;//��Ʊ״̬����Ϊ2������״̬Ϊ2��Ʊ����Ʊ��ĸ���
							rs = "��ѯ�ɹ�,��Ӧ��:"+ticketReponseErrCode+" ��Ʊ״̬:"+printTicketStatus+" ���ı���Ʊ״̬";
						}else if("1".equals(printTicketStatus)){//��Ʊ�ɹ�
							betTicketStatus = 6;
							printId = printTicketResult.getTicketId();
							printResult = printTicketStatus;
							rs = "��ѯ�ɹ�,��Ӧ��:"+ticketReponseErrCode+" ��Ʊ״̬:"+printTicketStatus+" ���Ʊ״̬��Ϊ:"+betTicketStatus;
						}else{//����ֵΪ����
							betTicketStatus = 7;
							printId = printTicketResult.getTicketId();
							printResult = printTicketStatus;
							rs = "��ѯ�ɹ�,��Ӧ��:"+ticketReponseErrCode+" ��Ʊ״̬:"+printTicketStatus+" ���Ʊ״̬��Ϊ:"+betTicketStatus;
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "������Ʊ��Ʊ״̬��ѯ������:"+ticketReponseErrCode+"Ʊ״̬:"+printTicketStatus, logParam);
						}
						
					}
					//����Ͷעϵͳ��Ʊ��
					try{
						BetTicketServiceInterf betTicektService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
						if(betTicketStatus != 2){//���������ݿ�
							int upResult = betTicektService.updateBetTicketPrintInfo(ticket.getTicketSequence(), betTicketStatus, printId, null, printResult, null, null, null);
							if(upResult <= 0){
								logger.debug("TicketDetector query the ticket(" + ticket.getTicketSequence() + ") from ehand update betticket table fail,ticketstatus::" + betTicketStatus);
								OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "������Ʊ��ѯ��Ʊ״̬���ظ���Ʊ��δ���µ���Ӧ������", logParam);
								rs = rs + " ����Ʊʧ��,δ���µ���Ӧ��Ʊ";
							}else {
								logger.debug("TicketDetector query the ticket(" + ticket.getTicketSequence() + ") from ehand update betticket table success,ticketstatus::" + betTicketStatus);
								rs = rs + " ����Ʊ�ɹ�";
							}
						}
						
					}catch(Exception e){
						logger.debug("TicketDetector query the ticket(" + ticket.getTicketSequence() + ") from ehand update betticket table fail,exception:" + e);
						rs = rs + " ����Ʊʧ��,�����쳣";
						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "������Ʊ��Ʊ״̬��ѯ���ظ���Ʊ��ʧ��", logParam);
					}
					
					
				}catch(Exception e){
					rs = rs + "�������쳣";
					logger.error("TicketDetector query the ticket(" + ticket.getTicketSequence() + ") from ehand fail.", e);
					OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȳ�Ʊ״̬��ѯ�������쳣:"+e.getMessage(), logParam);
				}
				
				info.setResult(rs);
				info.setOutTime(System.currentTimeMillis());
				TicketLogger.getInstance("TICKET").log(info);
				
			}catch(Exception e){
			}finally{
				semaphore.release();
				logger.debug("TicketDetect[" + this.getName() + "] detect the ticket is done and release the semaphore, " + semaphore.availablePermits() + " permits available now.");
			}
		}
	}
}
