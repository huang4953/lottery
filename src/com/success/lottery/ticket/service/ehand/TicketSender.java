package com.success.lottery.ticket.service.ehand;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.service.EHandSplitTools;
import com.success.lottery.ticket.service.TicketDispatch;
import com.success.lottery.ticket.service.TicketLogInfo;
import com.success.lottery.ticket.service.TicketLogger;
import com.success.lottery.ticket.service.interf.BetTicketServiceInterf;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.ssmp.monitor.SSMPException;
import com.success.utils.ssmp.monitor.ThreadSpectacle;

/**
 * 将需要发送的彩票信息从数据库中取出来放入队列。
 * @author bing.li
 */
public class TicketSender implements ThreadSpectacle{

	private String name;
	private String bootTime;

	private boolean isStop = false;
	private boolean isExit = false;
	private Log logger = LogFactory.getLog(TicketSender.class.getName());
	
	private int ticketCount;
	private int sendSucc;
	private int sendFail;

	private String station = "eHand";
	
	@Override
	public String getName(){
		return this.name;
	}

	@Override
	public boolean isAlive(){
		return !isStop && !isExit;
	}

	@Override
	public void setName(String name){
		this.name = name;
	}

	@Override
	public void setParameter(String parameter){
		bootTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
	}

	@Override
	public String showDetail(){
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
		sb.append("station=").append(this.station).append("\n");
		sb.append("\n");
		sb.append("\t\t");
		sb.append("to be done");
		return sb.toString();
	}

	@Override
	public String showInfo(){
		return "T - " + name + " - " + bootTime + " - Ticket[" + ticketCount + "/" + sendSucc + "/" + sendFail + "] - " + isAlive();
	}

	@Override
	public void spectacle(String command, PrintWriter pw){
	}

	@Override
	public void stop() throws SSMPException{
		this.isStop = true;
	}

	public synchronized void loadSendTicketWhenStart(){
		try{
			BetTicketServiceInterf lotteryBetTicketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
			List<Integer> ticketStatus = new ArrayList<Integer>();
			ticketStatus.add(1);
			ticketStatus.add(3);
			ticketStatus.add(4);

			List<BetTicketDomain> ticketes = lotteryBetTicketService.getTicketes(ticketStatus, "printStation='" + station + "'", 0);
			for(BetTicketDomain ticket : ticketes){
				logger.debug("TicketSender load a ticket(" + ticket.getTicketSequence() + ") from database when start load.");
				
				//转换投注串格式
				String printCode = EHandSplitTools.betCodeToPrintCode(ticket.getLotteryId(), ticket.getPlayType(), ticket.getBetType(), ticket.getBetCode());
				//转换投注方式
				int printType = EHandSplitTools.betTypeToPrintType(ticket.getLotteryId(), ticket.getPlayType(), ticket.getBetType());
				logger.debug("TicketSender convert a ticket(" + ticket.getTicketSequence() + ") betType from:"+ticket.getBetType() + " to:" + printType +" betCode from:"+ticket.getBetCode() + " to:"+printCode);
				ticket.setBetType(printType);
				ticket.setBetCode(printCode);
				
				String rs = TicketDispatch.dispatch(ticket);
				if(rs == null){
					logger.debug("TicketSender put the ticket(" + ticket.getTicketSequence() + ") into the SendTicketQueue success when start load.");
				}else{
					logger.debug("TicketSender put the ticket(" + ticket.getTicketSequence() + ") into the SendTicketQueue failed:" + rs + " when start load.");
				}
			}
		}catch(Exception e){
			logger.error("TicketSender occur unknow exception when start load:" + e);
		}
	}

	@Override
	public void run(){
		logger.info(name + " is running...");
		String resource = "com.success.lottery.ticket.service.TicketRouter";
		if("true".equalsIgnoreCase(AutoProperties.getString("ticketSender.loadWhenStart", "false", resource).trim())){
			logger.debug("TicketSender will load the to send ticket from database when start.");
			loadSendTicketWhenStart();
			logger.debug("TicketSender load the to send ticket from database completed when start.");
		}

		BetTicketServiceInterf lotteryBetTicketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
		//LotteryTermServiceInterf lotteryTermService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		List<Integer> ticketStatus = new ArrayList<Integer>();
		ticketStatus.add(Integer.valueOf(0));
		ticketStatus.add(Integer.valueOf(3));
		ticketStatus.add(Integer.valueOf(4));
		ticketStatus.add(Integer.valueOf(5));
	
		while(!isStop){
			try{
				List<BetTicketDomain> ticketes = lotteryBetTicketService.getTicketes(ticketStatus, "printStation='" + station + "'", AutoProperties.getInt("ticketSender.queryLimit" , 1000, resource));

				List<String> ticketSequences = new ArrayList<String>();
				List<TicketLogInfo> logInfo = new ArrayList<TicketLogInfo>();
				if(ticketes != null && ticketes.size() > 0){
					ticketCount = ticketCount + ticketes.size();
					for(BetTicketDomain ticket : ticketes){
						logger.debug("TicketSender get a ticket(" + ticket.getTicketSequence() + ").");
						TicketLogInfo info = new TicketLogInfo();
						info.setName("TICKETSENDER");
						info.setInTime(System.currentTimeMillis());
						info.setProcessorName(this.getName());
						info.setTicketSequence(ticket.getTicketSequence());
						info.setOrderId(ticket.getOrderId());

						if(TicketDispatch.contains(ticket)){
							logger.debug("TicketSender get the ticket(" + ticket.getTicketSequence() + ") was already sent to the queue.");
							sendFail++;
							continue;
						} else {
							//转换投注串格式
							String printCode = EHandSplitTools.betCodeToPrintCode(ticket.getLotteryId(), ticket.getPlayType(), ticket.getBetType(), ticket.getBetCode());
							//转换投注方式
							int printType = EHandSplitTools.betTypeToPrintType(ticket.getLotteryId(), ticket.getPlayType(), ticket.getBetType());
							logger.debug("TicketSender convert a ticket(" + ticket.getTicketSequence() + ") betType from:"+ticket.getBetType() + " to:" + printType +" betCode from:"+ticket.getBetCode() + " to:"+printCode);
							info.setRemark("convert betType from:"+ticket.getBetType() + " to:" + printType +" betCode from:"+ticket.getBetCode() + " to:"+printCode);
							ticket.setBetType(printType);
							ticket.setBetCode(printCode);
							
							String rs = null;
							if((rs = TicketDispatch.dispatch(ticket)) == null){
								logger.debug("TicketSender send the ticket(" + ticket.getTicketSequence() + ") to the queue success.");
								ticketSequences.add(ticket.getTicketSequence());
								info.addRemark("dispatch", "success");
								logInfo.add(info);
							} else {
								logger.debug("TicketSender send the ticket(" + ticket.getTicketSequence() + ") to the queue failed:" + rs + ".");
								sendFail++;
							}
						}
					}
					String rs = null;
					if(ticketSequences.size() > 0){
						try{
							int rc = 0;
							if((rc = lotteryBetTicketService.updateBetTicketesStatus(ticketSequences, 1, ticketStatus)) == 0){
								rs = "NoRowsUpdate";
								logger.debug("TicketSender update the ticketes(" + ticketSequences.toString() + ") ticketStauts = 1 failed:" + rs);
								sendFail = sendFail + ticketSequences.size();
							}else{
								logger.debug("TicketSender update " + rc + " ticketes(" + ticketSequences.toString() + ") ticketStauts = 1 success.");
								sendSucc = sendSucc + ticketSequences.size();
								for(TicketLogInfo info : logInfo){
									info.setResult(rs);
									info.setOutTime(System.currentTimeMillis());
									TicketLogger.getInstance("TICKET").log(info);
								}
							}
						}catch(Exception e){
							logger.error("TicketSender update the ticketes(" + ticketSequences.toString() + ") ticketStatus = 1 occur exception:" + e);
							if(logger.isDebugEnabled()){
								e.printStackTrace();
							}
							sendFail = sendFail + ticketSequences.size();
							rs = e.toString();
						}
					}
				}
				int count = ticketSequences == null ? 0 : ticketSequences.size();
				logger.debug("TicketSender get " + count + " ticketes, sleep " + AutoProperties.getInt("ticketSender.interval" , 200, resource) + "ms.");
				try{
					Thread.sleep(AutoProperties.getInt("ticketSender.interval" , 200, resource));
				}catch(Exception e){
				}
			}catch(Exception e){
				logger.error("TicketSender occur unknow exception:" + e);
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
				try{
					Thread.sleep(AutoProperties.getInt("ticketSender.interval" , 200, resource));
				}catch(Exception e1){
				}
			}
		}
		isExit = true;
		logger.info(name + " is shutdown!");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){
	}
}
