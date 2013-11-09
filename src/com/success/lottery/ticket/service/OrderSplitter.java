package com.success.lottery.ticket.service;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.ticket.service.interf.BetSplitterService;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.ssmp.monitor.SSMPException;
import com.success.utils.ssmp.monitor.ThreadSpectacle;


public class OrderSplitter implements ThreadSpectacle{

	private String name;
	private String bootTime;
	private String station = "qhtc,LPS";
	
	private boolean isStop = false;
	private boolean isExit = false;
	private Log logger = LogFactory.getLog(OrderSplitter.class.getName());
	
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
		if(StringUtils.isBlank(parameter)){
			isStop = true;
		} else {
			station = parameter.trim();
			logger = LogFactory.getLog("com.success.lottery.ticket.service." + station.replaceAll(",LPS", "").toUpperCase() + "OrderSplitter");
			bootTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
			isStop = false;
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
		sb.append("\t\t");
		sb.append("station=").append(this.station).append("\n");
		sb.append("\n");
		sb.append("\t\t");
		sb.append("to be done");
		return sb.toString();
	}

	@Override
	public String showInfo(){
		return "T - ORDERSPLITTER - " + name + " - " + bootTime + " - Splited[" + ordersCount + "/" + sendSucc + "/" + sendFail + "] - " + isAlive();
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
		if(isStop){
			logger.warn("OrderSplitter parameter is null, will exit...");
			return;
		}
		logger.info(name + " is running...");
		String resource = "com.success.lottery.ticket.service.TicketRouter";
		//qhtcBetSplitterService
		String serviceName = AutoProperties.getString(station + ".betSplitterService" , station.replaceAll(",LPS", "").toUpperCase() + "BetSplitterService", resource);
		BetSplitterService betSplitterService = null;
		try{
			//这里会抛异常吗，如果抛异常要捕获
			betSplitterService = ApplicationContextUtils.getService(serviceName, BetSplitterService.class);
			if(betSplitterService == null){
				logger.error("OrderSplitter not found the " + station + "'s " + serviceName + ".");
				//动态加载
			}
		}catch(Exception e){
			logger.error("OrderSplitter load the " + station + "'s " + serviceName + " occur exception:" + e);
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		if(betSplitterService == null){
			logger.warn("OrderSplitter " + station + "'s " + serviceName + " is null, will exit...");
			isStop = true;
		}
		while(!isStop){
			try{
				BetOrderDomain order = OrderDispatch.getOrder(station);
				if(order != null){
					long start = System.currentTimeMillis();
					logger.debug("OrderSplitter get a order(" + order.getOrderId() + ").");
					String rs = null;
					ordersCount++;
					OrderLogInfo info = new OrderLogInfo();
					info.setName("ORDERSPLITTER");
					info.setInTime(System.currentTimeMillis());
					info.setProcessorName(this.getName());
					info.setOrderId(order.getOrderId());
					if(order.getOrderStatus() >= 2){
						logger.debug("OrderSplitter check the order(" + order.getOrderId() + ")'s orderStatus(" + order.getOrderStatus() + ") >= 2 .");
						info.addRemark("orderStatus", "" + order.getOrderStatus());
						rs = "ErrorOrderStatus";
					} else {
						try{
							logger.debug("OrderSplitter split the order(" + order.getOrderId() + ") to ticket BEGIN.");
							rs = betSplitterService.orderSplit(order);
							info.addRemark("split", "true");
						}catch(LotteryException e){
							logger.debug("OrderSplitter split the order(" + order.getOrderId() + ") occur exception:" + e);
							rs = e.toString();
						}
						if(rs == null){
							logger.debug("OrderSplitter split the order(" + order.getOrderId() + ") to ticket success.");
							sendSucc++;
						} else {
							logger.debug("OrderSplitter split the order(" + order.getOrderId() + ") to ticket failed:" + rs);
							sendFail++;
						}
						logger.debug("OrderSplitter split the order(" + order.getOrderId() + ") END, result is " + (rs == null ? "OK!" : rs));
					}
					info.setResult(rs);
					info.setOutTime(System.currentTimeMillis());
					long t = System.currentTimeMillis() - start;
					info.addRemark("t", "" + t);
					TicketLogger.getInstance("ORDER").log(info);
				} else {
					logger.debug("OrderSplitter get 0 orders, sleep " + AutoProperties.getInt("orderSplitter.interval" , 200, resource) + "ms.");
					try{
						Thread.sleep(AutoProperties.getInt("orderSplitter.interval" , 200, resource));
					}catch(Exception e){
					}
				}
			}catch(Exception e){
				logger.error("OrderSplitter occur unknow exception:" + e);
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
		System.out.println("qhtc,LPS".replaceAll(",LPS", ""));
	}
}
