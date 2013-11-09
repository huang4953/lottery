package com.success.lottery.ticket.service.ehand;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.service.TicketDispatch;
import com.success.lottery.ticket.service.TicketLogInfo;
import com.success.lottery.ticket.service.TicketLogger;
import com.success.lottery.ticket.service.interf.BetTicketServiceInterf;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.ssmp.monitor.SSMPException;
import com.success.utils.ssmp.monitor.ThreadSpectacle;

import org.apache.commons.lang.StringUtils;

/**
 * 将需要发送的彩票信息从数据库中取出来放入队列。
 * @author bing.li
 */
public class TicketChecker implements ThreadSpectacle{

	private String name;
	private String bootTime;

	private boolean isStop = false;
	private boolean isExit = false;
	private Log logger = LogFactory.getLog(TicketChecker.class.getName());
	
	private int ticketCount;
	private int sendSucc;
	private int sendFail;

	private String station = "eHand";

	//彩种类型， 1-数字（乐透）型，2-高频类型，3-足彩型，4-竞彩型
	private int lotteryType = 1;
	
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
		if(StringUtils.isBlank(parameter)){
			logger.warn("TicketChecker is not set lotteryType, this checker will set lotteryType = 1.");
			this.lotteryType = 1;
		} else {
			try{
				lotteryType = Integer.parseInt(parameter.trim());
			}catch(Exception e){
				logger.warn("TicketChecker parseInt lotteryType parameter(" + parameter + ") to int occur exception: " + e.toString() + ", this checker will set lotteryType = 1.");
				lotteryType = 1;				
			}
			if(lotteryType < 1 || lotteryType > 4){
				logger.warn("TicketChecker lotteryType(" + lotteryType + ") is not a valid value, this checker will set lotteryType = 1.");
				lotteryType = 1;
			}
		}
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
		sb.append("\t\t");
		sb.append("station=").append(this.station).append("\n");
		sb.append("\n");
		sb.append("\t\t");
		sb.append("lotteryType=").append(this.lotteryType).append("\n");
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
			String resource = "com.success.lottery.ticket.service.TicketRouter";
			BetTicketServiceInterf lotteryBetTicketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
			List<Integer> ticketStatus = new ArrayList<Integer>();
			ticketStatus.add(2);

			String condition = "printStation='" + station + "'";
			String lotteryIds = AutoProperties.getString(station + ".ticketChecker." + lotteryType + ".lotteryIds", "", resource);
			if(StringUtils.isNotBlank(lotteryIds)){
				condition = condition + " and lotteryId in(" + lotteryIds + ")";
			}

			logger.debug(station + ".ticketChecker will get ticket, condition = " + condition + ", ticketStatus = " + ticketStatus.toString() + ", limitNumber = 0");
			List<BetTicketDomain> ticketes = lotteryBetTicketService.getTicketes(ticketStatus, condition, 0);
			for(BetTicketDomain ticket : ticketes){
				logger.debug(station + ".ticketChecker[" + lotteryType + "] load a ticket(" + ticket.getTicketSequence() + ") from database when start load.");
				String rs = TicketDispatch.putCheckTicketRequest(ticket, lotteryType + "");
				if(rs == null){
					logger.debug(station + ".ticketChecker[" + lotteryType + "] put the ticket(" + ticket.getTicketSequence() + ") into the SendTicketQueue success when start load.");
				}else{
					logger.debug(station + ".ticketChecker[" + lotteryType + "] put the ticket(" + ticket.getTicketSequence() + ") into the SendTicketQueue failed:" + rs + " when start load.");
				}
			}
		}catch(Exception e){
			logger.error(station + ".ticketChecker[" + lotteryType + "] occur unknow exception when start load:" + e);
		}
	}

	@Override
	public void run(){
		logger.info(name + " is running...");
		String resource = "com.success.lottery.ticket.service.TicketRouter";
		if("true".equalsIgnoreCase(AutoProperties.getString("ticketChecker.loadWhenStart", "false", resource).trim())){
			logger.debug("TicketChecker[" + lotteryType + "] will load the to send ticket from database when start.");
			loadSendTicketWhenStart();
			logger.debug("TicketChecker[" + lotteryType + "] load the to send ticket from database completed when start.");
		}

		BetTicketServiceInterf lotteryBetTicketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
		List<Integer> ticketStatus = new ArrayList<Integer>();
		ticketStatus.add(2);

		String condition = "printStation='" + station + "'";
		String lotteryIds = AutoProperties.getString(station + ".ticketChecker." + lotteryType + ".lotteryIds", "", resource);
		int timeGap = AutoProperties.getInt(station + ".ticketChecker." + lotteryType + ".timeGap", 30, resource);
		if(StringUtils.isNotBlank(lotteryIds)){
			condition = condition + " and lotteryId in(" + lotteryIds + ")";
		}
		//condition = condition +  " and (now() - printTime >=" + timeGap + ")";
		condition = condition +  " and coalesce((UNIX_TIMESTAMP(now()) - UNIX_TIMESTAMP(printtime)),-1) >=" + timeGap ;
		logger.debug(station + ".ticketChecker will get ticket, condition = " + condition + ", ticketStatus = " + ticketStatus.toString() + ", limitNumber = " + AutoProperties.getInt("ticketChecker.queryLimit" , 1000, resource));

		while(!isStop){
			try{
				if(timeGap != AutoProperties.getInt(station + ".ticketChecker." + lotteryType + ".timeGap", 30, resource)){
					lotteryIds = AutoProperties.getString(station + ".ticketChecker." + lotteryType + ".lotteryIds", "", resource);
					if(StringUtils.isNotBlank(lotteryIds)){
						condition = condition + " and lotteryId in(" + lotteryIds + ")";
					}
					//condition = condition +  " and now() - printTime >=" + AutoProperties.getInt(station + ".ticketChecker." + lotteryType + ".lotteryIds", 30, resource);
					
					condition = condition +  " and coalesce((UNIX_TIMESTAMP(now()) - UNIX_TIMESTAMP(printtime)),-1) >=" + AutoProperties.getInt(station + ".ticketChecker." + lotteryType + ".timeGap", 30, resource);
					
					
					logger.debug("TicketChecker[" + lotteryType + "]'s condition is changed, condition = " + condition + ", ticketStatus = " + ticketStatus.toString() + ", limitNumber = " + AutoProperties.getInt("ticketChecker.queryLimit" , 1000, resource));
				}

				List<BetTicketDomain> ticketes = lotteryBetTicketService.getTicketes(ticketStatus, condition, AutoProperties.getInt("ticketChecker.queryLimit" , 1000, resource));
				int count = ticketes == null ? 0 : ticketes.size();
				if(ticketes != null && ticketes.size() > 0){
					ticketCount = ticketCount + ticketes.size();
					for(BetTicketDomain ticket : ticketes){
						logger.debug("TicketChecker[" + lotteryType + "] get a ticket(" + ticket.getTicketSequence() + ").");
						TicketLogInfo info = new TicketLogInfo();
						info.setName("TICKETCHECKER");
						info.setInTime(System.currentTimeMillis());
						info.setProcessorName(this.getName());
						info.setTicketSequence(ticket.getTicketSequence());
						info.setOrderId(ticket.getOrderId());

						if(TicketDispatch.containsCheckTicketRequest(ticket, lotteryType + "")){
							logger.debug("TicketChecker[" + lotteryType + "] get the ticket(" + ticket.getTicketSequence() + ") was already sent to the queue.");
							sendFail++;
							continue;
						} else {
							String rs = null;
							if((rs = TicketDispatch.putCheckTicketRequest(ticket, lotteryType + "")) == null){
								logger.debug("TicketChecker[" + lotteryType + "] send the ticket(" + ticket.getTicketSequence() + ") to the queue success.");
								sendSucc++;
							} else {
								logger.debug("TicketChecker[" + lotteryType + "] send the ticket(" + ticket.getTicketSequence() + ") to the queue failed:" + rs + ".");
								sendFail++;
							}
							info.addRemark("lotteryType", "" + lotteryType);
							info.addRemark("station", station);
							info.setResult(rs);
							info.setOutTime(System.currentTimeMillis());
							TicketLogger.getInstance("TICKET").log(info);
						}
					}
				}
				logger.debug("TicketChecker["+ lotteryType + "] get " + count + " ticketes, sleep " + AutoProperties.getInt("ticketChecker.interval" , 200, resource) + "ms.");
				try{
					Thread.sleep(AutoProperties.getInt("ticketChecker.interval" , 200, resource));
				}catch(Exception e){
				}
			}catch(Exception e){
				logger.error("TicketChecker occur unknow exception:" + e);
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
				try{
					Thread.sleep(AutoProperties.getInt("ticketChecker.interval" , 200, resource));
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
