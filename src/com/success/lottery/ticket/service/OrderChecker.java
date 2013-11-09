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
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.ssmp.monitor.SSMPException;
import com.success.utils.ssmp.monitor.ThreadSpectacle;


public class OrderChecker implements ThreadSpectacle{

	private String name;
	private String bootTime;

	private boolean isStop = false;
	private boolean isExit = false;
	private Log logger = LogFactory.getLog(OrderChecker.class.getName());
	
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
		return "T - " + name + " - " + bootTime + " - Request[" + ordersCount + "/" + sendSucc + "/" + sendFail + "] - " + isAlive();
	}

	@Override
	public void spectacle(String command, PrintWriter pw){
	}

	@Override
	public void stop() throws SSMPException{
		this.isStop = true;
//		Thread t = Thread.currentThread();
//		logger.debug("invoke stop method, the [" + t.toString() + "] invoke interrupt self");
//		t.interrupt();
	}

	@Override
	public void run(){
		logger.info(name + " is running...");
		String resource = "com.success.lottery.ticket.service.TicketRouter";
		BetPlanOrderServiceInterf lotteryBetOrderService = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
		List<Integer> orderStatus = new ArrayList<Integer>();
		orderStatus.add(Integer.valueOf(2));
		List<Integer> ticketStatus = new ArrayList<Integer>();
		ticketStatus.add(6);
		ticketStatus.add(7);
		ticketStatus.add(8);
		ticketStatus.add(9);
		while(!isStop){
			try{
				List<BetOrderDomain> orders = lotteryBetOrderService.get2CheckOrders(orderStatus, ticketStatus, AutoProperties.getInt("orderChecker.queryLimit" , 20, resource));
				if(orders != null && orders.size() > 0){
					ordersCount = ordersCount + orders.size();
					for(BetOrderDomain order : orders){
						logger.debug("OrderChecker get a order(" + order.getOrderId() + ").");
//						OrderLogInfo info = new OrderLogInfo();
//						info.setName("ORDERCHECKER");
//						info.setInTime(System.currentTimeMillis());
//						info.setProcessorName(this.getName());
//						info.setOrderId(order.getOrderId());
						if(OrderDispatch.containsCheckOrderRequest(order)){
							logger.debug("OrderChecker get the order(" + order.getOrderId() + ") is already in the CheckOrderRequestQueue.");
//							info.setResult("DuplicationOrder");
//							info.setOutTime(System.currentTimeMillis());
//							TicketLogger.getInstance("ORDER").log(info);
							continue;
						} else {
							String rs = null;
							if((rs = OrderDispatch.putCheckOrderRequest(order)) == null){
								logger.debug("OrderChecker put the order(" + order.getOrderId() + ") into the CheckOrderRequestQueue success.");
								sendSucc++;
//								info.addRemark("dispatch", "success");
							} else {
								logger.debug("OrderChecker put the order(" + order.getOrderId() + ") into the CheckOrderRequestQueue failed:" + rs + ".");
								sendFail++;
//								info.addRemark("dispatch", "failed");
//								info.setResult(rs);
							}
						}
//						info.setOutTime(System.currentTimeMillis());
//						TicketLogger.getInstance("ORDER").log(info);
					}
				}
				int count = orders == null ? 0 : orders.size();
				logger.debug("OrderChecker get " + count + " orders, sleep " + AutoProperties.getInt("orderChecker.interval" , 200, resource) + "ms.");
				try{
					Thread.sleep(AutoProperties.getInt("orderChecker.interval" , 200, resource));
				}catch(Exception e){
				}


//				if(count == 0){
//					logger.debug("OrderChecker get 0 orders, sleep " + AutoProperties.getInt("orderChecker.interval" , 200, resource) + "ms.");
//					try{
//						Thread.sleep(AutoProperties.getInt("orderChecker.interval" , 200, resource));
//					}catch(Exception e){
//					}
//				}else{
//					logger.debug("OrderChecker get " + count + " orders, no sleep. ");
//				}

			}catch(Exception e){
				logger.error("OrderChecker occur unknow exception:" + e);
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
				try{
					Thread.sleep(AutoProperties.getInt("orderChecker.interval" , 200, resource));
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
