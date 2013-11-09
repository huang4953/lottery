/**
 * 
 */
package com.success.lottery.ticket.service;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.service.interf.BetTicketServiceInterf;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.ssmp.monitor.SSMPException;
import com.success.utils.ssmp.monitor.ThreadSpectacle;


/**
 * @author bing.li
 *
 */
public class TicketCheck implements ThreadSpectacle{

	private String name;
	private String bootTime;

	private boolean isStop = false;
	private boolean isExit = false;
	private Log logger = LogFactory.getLog(TicketCheck.class.getName());
	
	private int ordersCount;
	private int sendSucc;
	private int sendFail;
	
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
		sb.append("\t\t");
		sb.append("to be done");
		return sb.toString();
	}

	@Override
	public String showInfo(){
		return "T - TICKETCHECK - " + name + " - " + bootTime + " - Checked[" + ordersCount + "/" + sendSucc + "/" + sendFail + "] - " + isAlive();
	}

	@Override
	public void spectacle(String command, PrintWriter pw){
		if("loadCheckOrder".equalsIgnoreCase(command.trim())){
			loadCheckOrderWhenStart();
			pw.println("loaded need check order from database!");
			pw.flush();
		}else{
			pw.println(showInfo());
			pw.flush();
		}
	}

	@Override
	public void stop() throws SSMPException{
		this.isStop = true;
	}

	public synchronized void loadCheckOrderWhenStart(){
		try{
			BetPlanOrderServiceInterf lotteryBetOrderService = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
			List<Integer> orderStatus = new ArrayList<Integer>();
			orderStatus.add(Integer.valueOf(2));
			List<Integer> ticketStatus = new ArrayList<Integer>();
			ticketStatus.add(6);
			ticketStatus.add(7);
			ticketStatus.add(8);
			ticketStatus.add(9);

			List<BetOrderDomain> orders = lotteryBetOrderService.get2CheckOrders(orderStatus, ticketStatus, 4000);
			for(BetOrderDomain order : orders){
				logger.debug("TicketCheck load a order(" + order.getOrderId() + ") from database when start load.");
				if(OrderDispatch.containsCheckOrderRequest(order)){
					logger.debug("TicketCheck get the order(" + order.getOrderId() + ") is already in the CheckOrderRequestQueue when start load.");
					continue;
				} else {
					String rs = OrderDispatch.putCheckOrderRequest(order);
					if(rs == null){
						logger.debug("TicketCheck put the order(" + order.getOrderId() + ") into the CheckOrderRequestQueue success when start load.");
					}else{
						logger.debug("TicketCheck put the order(" + order.getOrderId() + ") into the CheckOrderRequestQueue failed:" + rs + " when start load.");
					}
				}
			}
		}catch(Exception e){
			logger.error("TicketCheck occur unknow exception when start load:" + e);
		}
	}
	
	@Override
	public void run(){
		logger.info(name + " is running...");
		String resource = "com.success.lottery.ticket.service.TicketRouter";
		if("true".equalsIgnoreCase(AutoProperties.getString("ticketCheck.loadWhenStart", "false", resource).trim())){
			logger.debug("TicketCheck will load the to check orders from database when start.");
			loadCheckOrderWhenStart();
			logger.debug("TicketCheck load the to check orders from database completed when start.");
		}
		BetTicketServiceInterf lotteryBetTicketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
		while(!isStop){
			try{
				BetOrderDomain order = OrderDispatch.getCheckOrderRequest();
				if(order != null){
					logger.debug("TicketCheck get a order(" + order.getOrderId() + ") from CheckOrderRequestQueue to check print ticket info.");
					logger.debug("TicketCheck check the order(" + order.getOrderId() + ")'s lotteryId   = " + order.getLotteryId());
					logger.debug("TicketCheck check the order(" + order.getOrderId() + ")'s betTerm     = " + order.getBetTerm());
					logger.debug("TicketCheck check the order(" + order.getOrderId() + ")'s playType    = " + order.getPlayType());
					logger.debug("TicketCheck check the order(" + order.getOrderId() + ")'s betType     = " + order.getBetType());
					logger.debug("TicketCheck check the order(" + order.getOrderId() + ")'s betMultiple = " + order.getBetMultiple());
					logger.debug("TicketCheck check the order(" + order.getOrderId() + ")'s amount      = " + order.getBetAmount());
					logger.debug("TicketCheck check the order(" + order.getOrderId() + ")'s betCode     = " + order.getBetCode());
					logger.debug("TicketCheck check the order(" + order.getOrderId() + ")'s orderStatus = " + order.getOrderStatus());
					logger.debug("TicketCheck check the order(" + order.getOrderId() + ")'s areaCode    = " + order.getAreaCode());
					ordersCount++;
					OrderLogInfo info = new OrderLogInfo();
					info.setName("ORDERCHECK");
					info.setInTime(System.currentTimeMillis());
					info.setProcessorName(this.getName());
					info.setOrderId(order.getOrderId());
					List<BetTicketDomain> ticketes = lotteryBetTicketService.getTickets(order.getOrderId());
					String rs = null;
					if(ticketes == null || ticketes.size() < 1){
						logger.debug("TicketCheck check the order(" + order.getOrderId() + ") not found its ticket.");
						rs = "TicketNotFound";
						info.setResult(rs);
						info.setOutTime(System.currentTimeMillis());
						TicketLogger.getInstance("ORDER").log(info);
					} else {
						int totalNumber = ticketes.size();
						logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get " + totalNumber + " ticketes.");
						int ticketSuccNumber = 0;
						int ticketFailNumber = 0;
						int count = 1;
						for(BetTicketDomain ticket : ticketes){
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get its betTicket " + count++ + " is:");
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s ticketSequence = " + ticket.getTicketSequence());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s orderId        = " + ticket.getOrderId());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s ticketStatus   = " + ticket.getTicketStatus());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s lotteryId      = " + ticket.getLotteryId());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s betTerm        = " + ticket.getBetTerm());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s playType       = " + ticket.getPlayType());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s betType        = " + ticket.getBetType());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s betMultiple    = " + ticket.getBetMultiple());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s betCode        = " + ticket.getBetCode());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s betCount       = " + ticket.getBetCount());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s betAmount      = " + ticket.getBetAmount());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s areaCode       = " + ticket.getAreaCode());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s ticketTime     = " + ticket.getTicketTime());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s lastTicketTime = " + ticket.getLastTicketTime());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s printStation   = " + ticket.getPrintStation());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s ticketId		  = " + ticket.getTicketId());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s printerId	  = " + ticket.getPrinterId());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s printResult	  = " + ticket.getPrintResult());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s printTime	  = " + ticket.getPrintTime());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s ticketData	  = " + ticket.getTicketData());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s ticketDataMD	  = " + ticket.getTicketDataMD());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s ticketPassword = " + ticket.getTicketPassword());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s preTaxPrize	  = " + ticket.getPreTaxPrize());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s prizeResult	  = " + ticket.getPrizeResult());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s saveStatus	  = " + ticket.getSaveStatus());
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") get ticket(" + ticket.getTicketSequence() + ")'s reserve		  = " + ticket.getReserve());
							logger.debug("");
							info.addRemark("T-" + ticket.getTicketSequence(), "" + ticket.getTicketStatus());
							switch(ticket.getTicketStatus()){
								case 0:
								case 1:
								case 2:
								case 3:
								case 4:
								case 5:
									break;
								case 6:
									ticketSuccNumber++;
									break;
								case 7:
								case 8:
								case 9:
									ticketFailNumber++;
									break;
								default:
									break;
							}
						}
						order.setTicketStat(totalNumber + "-" + ticketSuccNumber + "-" + ticketFailNumber);
						logger.debug("TicketCheck check the order(" + order.getOrderId() + ")'s print ticket Stat is:");
						logger.debug("TicketCheck check the order(" + order.getOrderId() + ")'s print ticket totalNumber = " + totalNumber);
						logger.debug("TicketCheck check the order(" + order.getOrderId() + ")'s print ticket ticketSuccNumber = " + ticketSuccNumber);
						logger.debug("TicketCheck check the order(" + order.getOrderId() + ")'s print ticket ticketFailNumber = " + ticketFailNumber);

						if(totalNumber == (ticketSuccNumber + ticketFailNumber)){
							int checkOrderStatus;
							if(ticketSuccNumber == totalNumber){
								checkOrderStatus = 5;
							}else if(ticketFailNumber == totalNumber){
								checkOrderStatus = 6;
							}else{
								checkOrderStatus = 4;
							}
							order.setOrderStatus(checkOrderStatus);
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") set orderStatus(" + checkOrderStatus + ") and will put into the CheckOrderResultQueue.");
							if((rs = OrderDispatch.putCheckOrderResult(order)) == null){
								logger.debug("TicketCheck put the order(" + order.getOrderId() + ") into the CheckOrderResultQueue success.");
								sendSucc++;
							} else {
								logger.debug("TicketCheck put the order(" + order.getOrderId() + ") into the CheckOrderResultQueue failed:" + rs + ".");
								sendFail++;
							}
							info.setResult(rs);
							info.setOutTime(System.currentTimeMillis());
							TicketLogger.getInstance("ORDER").log(info);
						}else{
							logger.debug("TicketCheck check the order(" + order.getOrderId() + ") totalNumber(" + totalNumber + ") is not equals ticketSuccNumber" + "(" + ticketSuccNumber + ") plus ticketFailNumber(" + ticketFailNumber + "), wait OrderCheck get it to check.");
						}
					}
				} else {
					logger.debug("TicketCheck get 0 order to check, sleep " + AutoProperties.getInt("ticketCheck.interval" , 200, resource) + "ms.");
					try{
						Thread.sleep(AutoProperties.getInt("ticketCheck.interval" , 200, resource));
					}catch(Exception e){
					}
				}
			}catch(Exception e){
				logger.error("TicketCheck occur unknow exception:" + e);
				if(logger.isDebugEnabled()){
					e.printStackTrace();
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
