package com.success.lottery.ticket.service;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.ssmp.monitor.SSMPException;
import com.success.utils.ssmp.monitor.ThreadSpectacle;


public class OrderMarker implements ThreadSpectacle{
	private String name;
	private String bootTime;

	private boolean isStop = false;
	private boolean isExit = false;
	private Log logger = LogFactory.getLog(OrderMarker.class.getName());
	
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
		return "T - ORDERMARKER - " + name + " - " + bootTime + " - Marked[" + ordersCount + "/" + sendSucc + "/" + sendFail + "] - " + isAlive();
	}

	@Override
	public void spectacle(String command, PrintWriter pw){
	}

	@Override
	public void stop() throws SSMPException{
		this.isStop = true;
	}

	@Override
	public void run(){
		logger.info(name + " is running...");
		String resource = "com.success.lottery.ticket.service.TicketRouter";
		BetPlanOrderServiceInterf lotteryBetOrderService = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
		while(!isStop){
			try{
				BetOrderDomain order = OrderDispatch.getCheckOrderResult();
				if(order != null){
					logger.debug("OrderMarker get a order(" + order.getOrderId() + ") from CheckOrderResultQueue to update order info.");
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s orderId     = " + order.getOrderId());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s planId		= " + order.getPlanId());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s planSource  = " + order.getPlanSource());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s chaseNumber = " + order.getChaseNumber());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s userId		= " + order.getUserId());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s areaCode	= " + order.getAreaCode());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s lotteryId   = " + order.getLotteryId());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s betTerm     = " + order.getBetTerm());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s playType    = " + order.getPlayType());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s betType     = " + order.getBetType());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s betMultiple = " + order.getBetMultiple());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s amount      = " + order.getBetAmount());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s betCode     = " + order.getBetCode());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s orderStatus = " + order.getOrderStatus());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s orderTime   = " + order.getOrderTime());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s ticketStat  = " + order.getTicketStat());
					logger.debug("OrderMarker get the order(" + order.getOrderId() + ")'s ticketTime  = " + order.getTicketTime());
					String rs = null;
					ordersCount++;
					OrderLogInfo info = new OrderLogInfo();
					info.setName("ORDERMARKER");
					info.setInTime(System.currentTimeMillis());
					info.setProcessorName(this.getName());
					info.setOrderId(order.getOrderId());
					info.addRemark("orderStatus", "" + order.getOrderStatus());
					info.addRemark("ticketStat", order.getTicketStat());
					switch(order.getOrderStatus()){
						case 4:
						case 5:
						case 6:
							//update betorder set orderstatus=?, ticketStat=? , ticketTime = now() where orderId=?
							try{
								logger.debug("OrderMarker update the order(" + order.getOrderId() + ") ticket print info BEGIN.");
								int rc = lotteryBetOrderService.updateBetOrderTicketStat(order.getOrderId(), order.getOrderStatus(), order.getTicketStat());
								if(rc != 1){
									logger.debug("OrderMarker update the order(" + order.getOrderId() + ") " + rc + " rows affected, it's not correct.");
								} else {
									logger.debug("OrderMarker update the order(" + order.getOrderId() + ") END, update success.");
								}
							}catch(Exception e){
								rs = e.toString();
								logger.debug("OrderMarker update the order(" + order.getOrderId() + ") occur unknow exception:" + e);
								if(logger.isDebugEnabled()){
									e.printStackTrace();
								}
							}
							break;
						default:
							rs = "ErrorStatus";
							break;
					}
					if(rs == null){
						sendSucc++;
					} else {
						sendFail++;
					}
					info.setResult(rs);
					info.setOutTime(System.currentTimeMillis());
					TicketLogger.getInstance("ORDER").log(info);
				} else {
					logger.debug("OrderMarker get 0 orders, sleep " + AutoProperties.getInt("orderMarker.interval" , 200, resource) + "ms.");
					try{
						Thread.sleep(AutoProperties.getInt("orderMarker.interval" , 200, resource));
					}catch(Exception e){
					}
				}
			}catch(Exception e){
				logger.error("OrderMarker occur unknow exception:" + e);
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
