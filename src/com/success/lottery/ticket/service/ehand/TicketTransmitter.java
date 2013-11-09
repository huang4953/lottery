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
	
	private String [] reSendCode;//票返回码需要重新发送的返回码
	private String[] sendSucessCode;//送票返回码表示成功的返回码
	private String[] sendFailCode;//送票返回码表示出票失败的返回码
	private String[] termEndCode;//送票返回码表示打票系统期结不能再继续送
	private String[] canNotSendCode;//送票返回码表示打票系统请求失败并且不可重试

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
		//送票返回码的定义集合
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

				//发送到EHand并得到响应结果，根据返回码做处理
				ticketCount++;
				TicketLogInfo info = new TicketLogInfo();
				String rs = null;
				String ticketReponseStatus = null;//返回信息的错误码
				try{
					info.setName("TICKETTRANSMITTER");
					info.setInTime(System.currentTimeMillis());
					info.setProcessorName(this.getName());
					info.setTicketSequence(ticket.getTicketSequence());
					info.setOrderId(ticket.getOrderId());
					
					boolean isNeedReSend = false;//是否需要重新发送,默认不需要
					
					TicketBetResult printTicketResult = null;
					//发送请求获得结果
					for(int i = 0 ; i < reSendNum;i++){
						printTicketResult = null;
						//调用送票接口得到响应结果
						try{
							printTicketResult = EhandTicketService.printOneTicket(ticket);
						}catch(LotteryException e){
							logger.error("将票送到掌中奕系统发生异常:"+ e.getType() + "#" + e.getMessage());
							//调用送票接口返回的异常都是不可重新发送的异常，包括通讯错误，通讯响应错误，消息解析错误，请求参数错误
							isNeedReSend = false;
						}
						
						//如果上面没有发生异常，对票做业务处理,送票接口返回的printTicketResult对象不可能为空值
						ticketReponseStatus = printTicketResult.getErrorCode();
						if(Arrays.asList(reSendCode).contains(ticketReponseStatus)){//根据返回码需要重新送票
							isNeedReSend = true;
						}else{//不需要重新送票，或者为正确或者为错误
							isNeedReSend = false;
						}
						
						if (isNeedReSend) {// 需要重新发送
							logger.info("将票送到掌中奕系统重发请求，第" + (i+1) + "次");
							Thread.sleep(reSendSleep);// 间隔1秒重新发送
						} else {
							break;
						}
					}
					
					//对得到的结果做处理
					int betTicketStatus = -1;//最终要更新的投注系统的票状态
					String printId = null,printResult = null,ticketData = null;
					
					if(printTicketResult == null || Arrays.asList(reSendCode).contains(ticketReponseStatus)){//发送请求失败，隔m秒重发，连续n次不成功后设置为该状态
						transFail++;
						logger.debug("TicketTransmitter send the ticket(" + ticket.getTicketSequence() + ") to ehand fail.reponseCode:"+ticketReponseStatus);
						//写一条系统日志
						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕票送票失败,响应码:"+ticketReponseStatus, logParam);
						//更新票的状态为原来的状态(即不更新票的状态)，提交出票请求失败
						betTicketStatus = ticket.getTicketStatus();
						printResult = ticketReponseStatus;
						rs = "重发后失败,响应码:" + ticketReponseStatus + "票状态置为"+betTicketStatus;
					}else{
						String ticketSendStatus = printTicketResult.getErrorCode();
						logger.debug("TicketTransmitter send the ticket(" + ticket.getTicketSequence() + ") to ehand success.reponseCode:"+ticketSendStatus);
						transSucc++;
						if(Arrays.asList(sendSucessCode).contains(ticketSendStatus)){//响应状态码为0表示送票成功
							betTicketStatus = 2;
							printId = printTicketResult.getOpreateId();
							printResult = "0";
							ticketData = printTicketResult.getAccountValue();
							rs = "发送返回成功,响应码:"+ticketSendStatus+"票状态置为:" + betTicketStatus;
						}else if(Arrays.asList(sendFailCode).contains(ticketSendStatus)){//定义为失败的返回码，不更新票的状态
							betTicketStatus = ticket.getTicketStatus();
							printId = printTicketResult.getOpreateId();
							printResult = ticketSendStatus;
							ticketData = printTicketResult.getHeaddErrorMsg();
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕票送票返回错误状态码:"+ticketSendStatus, logParam);
							rs = "发送返回失败,响应码:"+printResult+"票状态置为:" + betTicketStatus;
						}else if(Arrays.asList(termEndCode).contains(ticketSendStatus)){//彩期结束，不能再继续送
							betTicketStatus = 8;
							printId = printTicketResult.getOpreateId();
							printResult = ticketSendStatus;
							ticketData = printTicketResult.getHeaddErrorMsg();
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕票送票返回错误状态码:"+ticketSendStatus, logParam);
							rs = "发送返回失败,响应码:"+printResult+"票状态置为:" + betTicketStatus;
						}else if(Arrays.asList(canNotSendCode).contains(ticketSendStatus)){//其他不可重送的状态，如无效的彩票玩法
							betTicketStatus = 9;
							printId = printTicketResult.getOpreateId();
							printResult = ticketSendStatus;
							ticketData = printTicketResult.getHeaddErrorMsg();
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕票送票返回错误状态码:"+ticketSendStatus, logParam);
							rs = "发送返回失败,响应码:"+printResult+"票状态置为:" + betTicketStatus;
						}else{//未定义的返回码，如何处理,先将票的状态设置为票的原来状态，提交出票请求结果未知
							betTicketStatus = ticket.getTicketStatus();
							printId = printTicketResult.getOpreateId();
							printResult = ticketSendStatus;
							ticketData = printTicketResult.getHeaddErrorMsg();
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕票送票返回未知状态:"+ticketSendStatus, logParam);
							rs = "发送返回,响应码:" + ticketSendStatus;
						}
					}
					//更新投注系统的票表
					try{
						BetTicketServiceInterf betTicektService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
						int upResult = betTicektService.updateBetTicketPrintInfo(ticket.getTicketSequence(), betTicketStatus, null, printId, printResult, ticketData, null, null);
						if(upResult <= 0){
							logger.debug("TicketTransmitter send the ticket(" + ticket.getTicketSequence() + ") to ehand update betticket table fail,ticketstatus::" + betTicketStatus);
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕票送票返回更新票表未更新到对应的数据", logParam);
							rs = rs + " 更新票失败,未更新到对应的票";
						}else {
							logger.debug("TicketTransmitter send the ticket(" + ticket.getTicketSequence() + ") to ehand update betticket table success,ticketstatus::" + betTicketStatus);
							rs = rs + " 更新票成功";
						}
					}catch(Exception e){
						logger.debug("TicketTransmitter send the ticket(" + ticket.getTicketSequence() + ") to ehand update betticket table fail,exception:" + e);
						rs = rs + " 更新票失败,发生异常";
						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕票送票返回更新票表失败", logParam);
					}
					
					
				}catch(Exception e){
					rs = rs + "程序发生异常";
					logger.error("TicketTransmitter send the ticket(" + ticket.getTicketSequence() + ") to ehand fail.", e);
					OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕票送票程序发生异常:"+e.getMessage(), logParam);
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
