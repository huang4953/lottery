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
	 * ��Ͷע��������TicketRouter�����õ�TicketStation��Ʊ���У������������£�
	 * ��ͨ���ֲʣ�TicketStation.toUpperCase() + "NormalQueue"��
	 * ��Ƶ���ֲʣ�TicketStation.toUpperCase() + "FastQueue"�� 
	 * ��ʣ�TicketStation.toUpperCase() + "SoccerQueue"��
	 * TicketStation: ·�ɵ��ĳ�Ʊ�㣬��eHand,LPS
	 * ����LotteryId�Լ�AreaCode����·�ɣ�·��˳�����£� lotteryId.areaCode, lotteryId,
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
	 * ��ָ���ĳ�Ʊ�������ȡ��һ�������� ����TicketRouter�����õı�������ͨ���ֲʡ���Ƶ���ֲʡ���ʶ�����ȡ����Ҫ��Ʊ�Ķ�����
	 * 
	 * @param station: ·�ɸ�ʽ�ĳ�Ʊ�㣬��eHand,LPS
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
	 * ����Ʊ�������Ƿ����ָ���Ķ�������
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
	 * ����Ҫ��鶩����Ʊ����Ķ��������鶩����Ʊ������������ ���۳�Ʊ����������в��ֵĶ�����Ʊ�����鶼�����ͬһ�����С�
	 * ��鶩����Ʊ���������е����ƣ�CheckOrderRequestQueue
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
	 * �Ӽ�鶩����Ʊ�����������л�ȡһ���������Ʊ����Ķ��� 
	 * ��鶩����Ʊ���������е����ƣ�CheckOrderRequestQueue
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
	 * �������ϳ�Ʊ����Ķ������붩����Ʊ������������ ���۳�Ʊ����������в��ֵĶ�����Ʊ�������������ͬһ�����С�
	 * ������Ʊ���������е����ƣ�CheckOrderResultQueue
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
	 * �Ӷ�����Ʊ�����������л�ȡһ�������ϳ�Ʊ����Ķ��� 
	 * ������Ʊ���������е����ƣ�CheckOrderResultQueue
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
	 * ����鶩����Ʊ�������������Ƿ���ָ���Ķ�������
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
	 * ��鶩����Ʊ�������������Ƿ���ָ���Ķ�������
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
