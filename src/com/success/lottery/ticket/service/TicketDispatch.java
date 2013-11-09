package com.success.lottery.ticket.service;

import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.util.LotteryTools;
import com.success.utils.AutoProperties;
import com.success.utils.ssmp.SSMPQueue;

import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class TicketDispatch{

	private static Log		logger			= LogFactory.getLog(TicketDispatch.class.getName());
	private static String	routerConfig	= "com.success.lottery.ticket.service.TicketRouter";
	private static Random	random			= new Random();

	/**
	 * 将出票订单放入出票队列，队列名称如下：
	 * 普通数字彩：TicketStation.toUpperCase() + "Ticket" + "NormalQueue"；
	 * 高频数字彩：TicketStation.toUpperCase() + "Ticket" + "FastQueue"；
	 * 足彩：TicketStation.toUpperCase() + "Ticket" + "SoccerQueue"；
	 * TicketStation: 拆票时填写的出票点，如 eHand
	 * @param ticket
	 * @return
	 */
	public static String dispatch(BetTicketDomain ticket){
		String rs = null;
		try{
			int lotteryId = ticket.getLotteryId();
			String station = ticket.getPrintStation();
			if(station == null){
				return "NotFoundTicketStation";
			}
			String queName = station.toUpperCase() + "Ticket";
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
			SSMPQueue<BetTicketDomain> ticketQue = SSMPQueue.getQueue(queName);
			if(!ticketQue.offer(ticket)){
				rs = queName + " is full";
			}
		}catch(Exception e){
			rs = e.toString();
			logger.error("dispatch Ticket exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		return rs;
	}

	/**
	 * 从指定的出票队列中取出一个彩票； 根据TicketRouter中配置的比例从普通数字彩、高频数字彩、足彩队列中取出需要出票的订单；
	 * @param station
	 * @return
	 */
	public static BetTicketDomain getTicket(String station){
		String queName = "TicketQueue";
		try{
			int normalPercent = AutoProperties.getInt(station + ",LPS.normalQuePercent", 30, routerConfig);
			int fastPercent = AutoProperties.getInt(station + ",LPS.fastQuePercent", 50, routerConfig);
			int soccerPercent = AutoProperties.getInt(station + ",LPS.soccerQuePercent", 20, routerConfig);

			String fastQueName = station.toUpperCase() + "Ticket" + "FastQueue";
			String normalQueName = station.toUpperCase() + "Ticket" + "NormalQueue";
			String soccerQueName = station.toUpperCase() + "Ticket" + "SoccerQueue";
			BetTicketDomain ticket = null;
			SSMPQueue<BetTicketDomain> ticketQue;
			int randomNum = random.nextInt(101);
			if(randomNum > normalPercent && randomNum <= (normalPercent + fastPercent)){
				ticketQue = SSMPQueue.getQueue(fastQueName);
				ticket = ticketQue.poll();
				if(ticket == null){
					ticketQue = SSMPQueue.getQueue(normalQueName);
					ticket = ticketQue.poll();
					if(ticket == null){
						ticketQue = SSMPQueue.getQueue(soccerQueName);
						return ticket = ticketQue.poll();
					}else{
						return ticket;
					}
				} else {
					return ticket;
				}
			}else if(randomNum > (normalPercent + fastPercent) && randomNum <= (normalPercent + fastPercent + soccerPercent)){
				ticketQue = SSMPQueue.getQueue(soccerQueName);
				ticket = ticketQue.poll();
				if(ticket == null){
					ticketQue = SSMPQueue.getQueue(fastQueName);
					ticket = ticketQue.poll();
					if(ticket == null){
						ticketQue = SSMPQueue.getQueue(normalQueName);
						return ticket = ticketQue.poll();
					}else{
						return ticket;
					}
				} else {
					return ticket;
				}
			} else {
				ticketQue = SSMPQueue.getQueue(normalQueName);
				ticket = ticketQue.poll();
				if(ticket == null){
					ticketQue = SSMPQueue.getQueue(fastQueName);
					ticket = ticketQue.poll();
					if(ticket == null){
						ticketQue = SSMPQueue.getQueue(soccerQueName);
						return ticket = ticketQue.poll();
					}else{
						return ticket;
					}
				} else {
					return ticket;
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
	 * 检查出票队列中是否存在指定的彩票对象
	 * @param ticket
	 * @return
	 */
	public static boolean contains(BetTicketDomain ticket){
		try{
			int lotteryId = ticket.getLotteryId();
			String station = ticket.getPrintStation();
			if(station == null){
				return false;
			}
			String queName = station.toUpperCase() + "Ticket";
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
			SSMPQueue<BetTicketDomain> ticketQue = SSMPQueue.getQueue(queName);
			return ticketQue.contains(ticket);
		}catch(Exception e){
			logger.error("contains Ticket exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * 将需要检查出票情况的彩票放入指定标志的检查出票情况查询请求队列中，此方法将要进行出票情况检查的彩票放入指定mark的队列。
	 * 检查彩票出票情况请求队列的名称：TicketStation + mark + "CheckTicketRequestQueue"
	 * @param ticket
	 * @param mark
	 * @return
	 */
	public static String putCheckTicketRequest(BetTicketDomain ticket, String mark){
		String rs = null;
		if(mark == null){
			mark = "";
		}
		try{
			String queName = ticket.getPrintStation().toUpperCase() + mark + "CheckTicketRequestQueue";
			SSMPQueue<BetTicketDomain> orderQue = SSMPQueue.getQueue(queName);
			if(!orderQue.offer(ticket)){
				rs = queName + " is full";
			}
		}catch(Exception e){
			rs = e.toString();
			logger.error("putCheckTicketRequest to Ticket exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		return rs;
	}

	/**
	 * 将需要检查出票情况的彩票放入检查出票情况查询请求队列中，此方芳将所有彩种的出票情况检查都会放入同一个队列。
	 * 检查彩票出票情况请求队列的名称：TicketStation + "CheckTicketRequestQueue"
	 * @param ticket
	 * @return
	 */
	public static String putCheckTicketRequest(BetTicketDomain ticket){
		return putCheckTicketRequest(ticket, "");
	}

	/**
	 * 从检查出票情况请求队列中获取一个请求检查出票情况的彩票
	 * 检查出票情况请求队列的名称：TicketStation + mark + "CheckTicketRequestQueue"
	 * 
	 * @return
	 */
	public static BetTicketDomain getCheckTicketRequest(String station, String mark){
		try{
			if(mark == null){
				mark = "";
			}
			String queName = station.toUpperCase() + mark + "CheckTicketRequestQueue";
			SSMPQueue<BetTicketDomain> ticketQue = SSMPQueue.getQueue(queName);
				return ticketQue.poll();
		}catch(Exception e){
			logger.error("get Ticket from " + station + "CheckTicketRequestQueue exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return null;
		}
	}

	public static BetTicketDomain getCheckTicketRequest(String station){
		return getCheckTicketRequest(station, "");
	}


	/**
	 * 检查检查出票情况请求队列中是否有指定的彩票对象
	 * @param ticket
	 * @return
	 */
	public static boolean containsCheckTicketRequest(BetTicketDomain ticket){
		return containsCheckTicketRequest(ticket, "");
	}

	public static boolean containsCheckTicketRequest(BetTicketDomain ticket, String mark){
		try{
			if(mark == null){
				mark = "";
			}
			String queName = ticket.getPrintStation().toUpperCase() + mark + "CheckTicketRequestQueue";
			SSMPQueue<BetTicketDomain> ticketQue = SSMPQueue.getQueue(queName);
			return ticketQue.contains(ticket);
		}catch(Exception e){
			logger.error("containsCheckTicketRequest Ticket exception:" + e.toString());
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
	}
}
