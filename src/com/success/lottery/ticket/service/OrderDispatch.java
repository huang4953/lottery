package com.success.lottery.ticket.service;

import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.util.LotteryTools;
import com.success.utils.AutoProperties;
import com.success.utils.ssmp.SSMPQueue;
public class OrderDispatch{

	private static Log		logger			= LogFactory.getLog(OrderDispatch.class.getName());
	private static String	routerConfig	= "com.success.lottery.ticket.service.TicketRouter";
	private static Random	random			= new Random();

	private static String getTicketStation(int lotteryId, String areaCode){
		String rs = null;
		if(areaCode != null){
			rs = AutoProperties.getString(lotteryId + "." + areaCode, null, routerConfig);
		}
		if(rs == null){
			rs = AutoProperties.getString(lotteryId + "", null, routerConfig);
		}
		if(rs == null){
			rs = AutoProperties.getString(lotteryId + ".default", null, routerConfig);
		}
		if(rs == null && areaCode != null){
			rs = AutoProperties.getString(areaCode, null, routerConfig);
		}
		if(rs == null){
			rs = AutoProperties.getString("default", null, routerConfig);
		}

		while(rs != null && rs.lastIndexOf(",LPS") < 0){
			rs = AutoProperties.getString(rs, null, routerConfig);
		}
		return rs;
	}

	/**
	 * 将投注订单放入TicketRouter中配置的TicketStation出票队列，队列名称如下：
	 * 普通数字彩：TicketStation.toUpperCase() + "NormalQueue"；
	 * 高频数字彩：TicketStation.toUpperCase() + "FastQueue"； 
	 * 足彩：TicketStation.toUpperCase() + "SoccerQueue"；
	 * TicketStation: 路由到的出票点，如eHand,LPS
	 * 根据LotteryId以及AreaCode进行路由，路由顺序如下： lotteryId.areaCode, lotteryId,
	 * lotteryId.default, areaCode, default
	 * 
	 * @param order
	 * @return
	 */
	public static String dispatch(BetOrderDomain order){
		String rs = null;
		try{
			int lotteryId = order.getLotteryId();
			String station = getTicketStation(lotteryId, order.getAreaCode());
			if(station == null){
				return "NotFoundTicketStation";
			}
			String queName = station.toUpperCase();
			switch(LotteryTools.getLotteryType(lotteryId)){
				case 1:
					queName = queName + "NormalQueue";
					break;
				case 2:
					queName = queName + "FastQueue";
					break;
				case 3:
					queName = queName + "SoccerQueue";
					break;
				default:
					return "LotteryTypeError";
			}
			SSMPQueue<BetOrderDomain> orderQue = SSMPQueue.getQueue(queName);
			if(!orderQue.offer(order)){
				rs = queName + " is full";
			}
		}catch(Exception e){
			rs = e.toString();
			logger.error("dispatch Order exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		return rs;
	}

	/**
	 * 从指定的出票点队列中取出一个订单； 根据TicketRouter中配置的比例从普通数字彩、高频数字彩、足彩队列中取出需要出票的订单；
	 * 
	 * @param station: 路由格式的出票点，如eHand,LPS
	 * @return
	 */
	public static BetOrderDomain getOrder(String station){
		String queName = "OrderQueue";
		try{
			int normalPercent = AutoProperties.getInt(station + ".normalQuePercent", 30, routerConfig);
			int fastPercent = AutoProperties.getInt(station + ".fastQuePercent", 50, routerConfig);
			int soccerPercent = AutoProperties.getInt(station + ".soccerQuePercent", 20, routerConfig);

			String fastQueName = station.toUpperCase() + "FastQueue";
			String normalQueName = station.toUpperCase() + "NormalQueue";
			String soccerQueName = station.toUpperCase() + "SoccerQueue";
			BetOrderDomain order = null;
			SSMPQueue<BetOrderDomain> orderQue;
			int randomNum = random.nextInt(101);
			if(randomNum > normalPercent && randomNum <= (normalPercent + fastPercent)){
				orderQue = SSMPQueue.getQueue(fastQueName);
				order = orderQue.poll();
				if(order == null){
					orderQue = SSMPQueue.getQueue(normalQueName);
					order = orderQue.poll();
					if(order == null){
						orderQue = SSMPQueue.getQueue(soccerQueName);
						return order = orderQue.poll();
					}else{
						return order;
					}
				} else {
					return order;
				}
			}else if(randomNum > (normalPercent + fastPercent) && randomNum <= (normalPercent + fastPercent + soccerPercent)){
				orderQue = SSMPQueue.getQueue(soccerQueName);
				order = orderQue.poll();
				if(order == null){
					orderQue = SSMPQueue.getQueue(fastQueName);
					order = orderQue.poll();
					if(order == null){
						orderQue = SSMPQueue.getQueue(normalQueName);
						return order = orderQue.poll();
					}else{
						return order;
					}
				} else {
					return order;
				}
			} else {
				orderQue = SSMPQueue.getQueue(normalQueName);
				order = orderQue.poll();
				if(order == null){
					orderQue = SSMPQueue.getQueue(fastQueName);
					order = orderQue.poll();
					if(order == null){
						orderQue = SSMPQueue.getQueue(soccerQueName);
						return order = orderQue.poll();
					}else{
						return order;
					}
				} else {
					return order;
				}
			}
		}catch(Exception e){
			logger.error("get Order from " + queName + " exception:" + e);
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * 检查出票队列中是否存在指定的订单对象
	 * 
	 * @param order
	 * @return
	 */
	public static boolean contains(BetOrderDomain order){
		try{
			int lotteryId = order.getLotteryId();
			String station = getTicketStation(lotteryId, order.getAreaCode());
			if(station == null){
				return false;
			}
			String queName = station.toUpperCase();
			switch(LotteryTools.getLotteryType(lotteryId)){
				case 1:
					queName = queName + "NormalQueue";
					break;
				case 2:
					queName = queName + "FastQueue";
					break;
				case 3:
					queName = queName + "SoccerQueue";
					break;
				default:
					return false;
			}
			SSMPQueue<BetOrderDomain> orderQue = SSMPQueue.getQueue(queName);
			return orderQue.contains(order);
		}catch(Exception e){
			logger.error("contains Order exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * 将需要检查订单出票情况的订单放入检查订单出票情况请求队列中 无论出票点在哪里，所有彩种的订单出票情况检查都会放入同一个队列。
	 * 检查订单出票情况请求队列的名称：CheckOrderRequestQueue
	 * 
	 * @param order
	 * @return
	 */
	public static String putCheckOrderRequest(BetOrderDomain order){
		String rs = null;
		try{
			SSMPQueue<BetOrderDomain> orderQue = SSMPQueue.getQueue("CheckOrderRequestQueue");
			if(!orderQue.offer(order)){
				rs = "CheckOrderRequestQueue is full";
			}
		}catch(Exception e){
			rs = e.toString();
			logger.error("putCheckOrderRequest Order exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		return rs;
	}

	/**
	 * 从检查订单出票情况请求队列中获取一个请求检查出票情况的订单 
	 * 检查订单出票情况请求队列的名称：CheckOrderRequestQueue
	 * 
	 * @return
	 */
	public static BetOrderDomain getCheckOrderRequest(){
		try{
			SSMPQueue<BetOrderDomain> orderQue = SSMPQueue.getQueue("CheckOrderRequestQueue");
				return orderQue.poll();
		}catch(Exception e){
			logger.error("get Order from CheckOrderRequestQueue exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * 将检查完毕出票情况的订单放入订单出票情况结果队列中 无论出票点在哪里，所有彩种的订单出票情况结果都会放入同一个队列。
	 * 订单出票情况结果队列的名称：CheckOrderResultQueue
	 * 
	 * @param order
	 * @return
	 */
	public static String putCheckOrderResult(BetOrderDomain order){
		String rs = null;
		try{
			SSMPQueue<BetOrderDomain> orderQue = SSMPQueue.getQueue("CheckOrderResultQueue");
			if(!orderQue.offer(order)){
				rs = "CheckOrderResultQueue is full";
			}
		}catch(Exception e){
			rs = e.toString();
			logger.error("putCheckOrderResult Order exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		return rs;
	}

	/**
	 * 从订单出票情况结果队列中获取一个检查完毕出票情况的订单 
	 * 订单出票情况结果队列的名称：CheckOrderResultQueue
	 * 
	 * @return
	 */
	public static BetOrderDomain getCheckOrderResult(){
		try{
			SSMPQueue<BetOrderDomain> orderQue = SSMPQueue.getQueue("CheckOrderResultQueue");
				return orderQue.poll();
		}catch(Exception e){
			logger.error("get Order from CheckOrderResultQueue exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * 检查检查订单出票情况请求队列中是否有指定的订单对象
	 * @param order
	 * @return
	 */
	public static boolean containsCheckOrderRequest(BetOrderDomain order){
		try{
			SSMPQueue<BetOrderDomain> orderQue = SSMPQueue.getQueue("CheckOrderRequestQueue");
			return orderQue.contains(order);
		}catch(Exception e){
			logger.error("containsCheckOrderRequest Order exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * 检查订单出票情况结果队列中是否有指定的订单对象
	 * @param order
	 * @return
	 */
	public static boolean containsCheckOrderResult(BetOrderDomain order){
		try{
			SSMPQueue<BetOrderDomain> orderQue = SSMPQueue.getQueue("CheckOrderResultQueue");
			return orderQue.contains(order);
		}catch(Exception e){
			logger.error("containsCheckOrderResult Order exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return false;
		}
	}

	public static boolean removeCheckOrderResult(BetOrderDomain order){
		try{
			SSMPQueue<BetOrderDomain> orderQue = SSMPQueue.getQueue("CheckOrderResultQueue");
			return orderQue.remove(order);
		}catch(Exception e){
			logger.error("removeCheckOrderResult Order exception:" + e);
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){
		/*
		double normalPercent = 30, i = 0;
		double fastPercent = 50, j = 0;
		double soccerPercent = 20, k = 0;
		int n = 1000;
		for(int m = 0; m < n; m++){
			int randomNum = random.nextInt(101);
			if(randomNum > normalPercent && randomNum <= (normalPercent + fastPercent)){
				System.out.println("FastQueue: " + j++);
				continue;
			}
			if(randomNum > (normalPercent + fastPercent) && randomNum <= (normalPercent + fastPercent + soccerPercent)){
				System.out.println("SoccerQueue: " + k++);
				continue;
			}
			System.out.println("NormalQueue: " + i++);
		}
		System.out.println("------------------------------------------");
		System.out.println("i = " + i + ", j = " + j + ", k = " + k);
		System.out.println("Normal:" + (double)(i / n * 100) + "%;  " + "Fast:" + (double)(j / n * 100) + "%;  " + "Soccer:" + (double)(k / n * 100) + "%");
		*/
		System.out.println(getTicketStation(1000001,"63"));
	}
}
