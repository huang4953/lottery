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
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.ticket.service.OrderDispatch;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.ssmp.monitor.SSMPException;
import com.success.utils.ssmp.monitor.ThreadSpectacle;


public class OrderSender implements ThreadSpectacle{

	private String name;
	private String bootTime;

	private boolean isStop = false;
	private boolean isExit = false;
	private Log logger = LogFactory.getLog(OrderSender.class.getName());
	
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
		return "T - " + name + " - " + bootTime + " - Order[" + ordersCount + "/" + sendSucc + "/" + sendFail + "] - " + isAlive();
	}

	@Override
	public void spectacle(String command, PrintWriter pw){
	}

	@Override
	public void stop() throws SSMPException{
		this.isStop = true;
		//Thread t = Thread.currentThread();
		//logger.debug("invoke stop method, the [" + t.toString() + "] invoke interrupt self");
		//t.interrupt();
	}

	@Override
	public void run(){
		logger.info(name + " is running...");
		String resource = "com.success.lottery.ticket.service.TicketRouter";
		BetPlanOrderServiceInterf lotteryBetOrderService = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
		LotteryTermServiceInterf lotteryTermService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		List<Integer> orderStatus = new ArrayList<Integer>();
		orderStatus.add(Integer.valueOf(0));
		orderStatus.add(Integer.valueOf(1));
		
		List<Integer> whoStatus = new ArrayList<Integer>();
		whoStatus.add(Integer.valueOf(0));
		
//		try{
//			List<BetOrderDomain> orders = lotteryBetOrderService.getOrders(orderStatus, 20);
//			System.out.println(orders.size());
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		System.out.println("@@@@@@@@@@@@@@@@@ " + lotteryBetOrderService + "@@@@@@@@@@@@@@");
		
		while(!isStop){
			try{
				String termInfo = lotteryTermService.getCanPrintCondition();
				List<BetOrderDomain> orders = lotteryBetOrderService.getOrders(orderStatus, termInfo, AutoProperties.getInt("orderSender.queryLimit" , 20, resource));
				List<String> orderIds = new ArrayList<String>();
				List<OrderLogInfo> logInfo = new ArrayList<OrderLogInfo>();
				if(orders != null && orders.size() > 0){				
					ordersCount = ordersCount + orders.size();
					for(BetOrderDomain order : orders){
						logger.debug("OrderSender get a order(" + order.getOrderId() + ").");
						OrderLogInfo info = new OrderLogInfo();
						info.setName("ORDERSENDER");
						info.setInTime(System.currentTimeMillis());
						info.setProcessorName(this.getName());
						info.setOrderId(order.getOrderId());
						
						if(OrderDispatch.contains(order)){
							logger.debug("OrderSender get the order(" + order.getOrderId() + ") was already sent to the queue.");
//							info.setResult("DuplicationOrder");
//							info.setOutTime(System.currentTimeMillis());
//							TicketLogger.getInstance("ORDER").log(info);
							sendFail++;
							continue;
						} else {
							String rs = null;
							if((rs = OrderDispatch.dispatch(order)) == null){
								logger.debug("OrderSender send the order(" + order.getOrderId() + ") to the queue success.");
								orderIds.add(order.getOrderId());
								info.addRemark("dispatch", "success");
								logInfo.add(info);
							} else {
								logger.debug("OrderSender send the order(" + order.getOrderId() + ") to the queue failed:" + rs + ".");
								sendFail++;
//								info.addRemark("dispatch", "failed");
//								info.setResult(rs);
//								info.setOutTime(System.currentTimeMillis());
//								TicketLogger.getInstance("ORDER").log(info);
							}
						}
					}
					String rs = null;
					if(orderIds.size() > 0){
						try{
							if(lotteryBetOrderService.updateBetOrderStatus(orderIds, 1, whoStatus) == 0){
								rs = "NoRowsUpdate";
								logger.debug("OrderSender update the orders(" + orderIds.toString() + ") orderStauts = 1 failed:" + rs);
								sendFail = sendFail + orderIds.size();
							}else{
								logger.debug("OrderSender update the orders(" + orderIds.toString() + ") orderStauts = 1 success.");
								sendSucc = sendSucc + orderIds.size();
								for(OrderLogInfo info : logInfo){
									info.setResult(rs);
									info.setOutTime(System.currentTimeMillis());
									TicketLogger.getInstance("ORDER").log(info);
								}
							}
						}catch(Exception e){
							logger.error("OrderSender update the orders(" + orderIds.toString() + ") orderStatus = 1 occur exception:" + e);
							if(logger.isDebugEnabled()){
								e.printStackTrace();
							}
							sendFail = sendFail + orderIds.size();
							rs = e.toString();
						}
					}
				}
				int count = orders == null ? 0 : orders.size();
				logger.debug("OrderSender get " + count + " orders by (" + termInfo + "), sleep " + AutoProperties.getInt("orderSender.interval" , 200, resource) + "ms.");
				try{
					Thread.sleep(AutoProperties.getInt("orderSender.interval" , 200, resource));
				}catch(Exception e){
				}

//				if(count == 0){
//					logger.debug("OrderSender get " + count + " orders by (" + termInfo + "), sleep " + AutoProperties.getInt("orderSender.interval" , 200, resource) + "ms.");
//					try{
//						Thread.sleep(AutoProperties.getInt("orderSender.interval" , 200, resource));
//					}catch(Exception e){
//					}
//				}else{
//					logger.debug("OrderSender get " + count + " orders by (" + termInfo + "), no sleep. ");
//				}
			}catch(Exception e){
				logger.error("OrderSender occur unknow exception:" + e);
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
				try{
					Thread.sleep(AutoProperties.getInt("orderSender.interval" , 200, resource));
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
