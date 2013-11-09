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
	 * ����Ʊ���������Ʊ���У������������£�
	 * ��ͨ���ֲʣ�TicketStation.toUpperCase() + "Ticket" + "NormalQueue"��
	 * ��Ƶ���ֲʣ�TicketStation.toUpperCase() + "Ticket" + "FastQueue"��
	 * ��ʣ�TicketStation.toUpperCase() + "Ticket" + "SoccerQueue"��
	 * TicketStation: ��Ʊʱ��д�ĳ�Ʊ�㣬�� eHand
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
	 * ��ָ���ĳ�Ʊ������ȡ��һ����Ʊ�� ����TicketRouter�����õı�������ͨ���ֲʡ���Ƶ���ֲʡ���ʶ�����ȡ����Ҫ��Ʊ�Ķ�����
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
	 * ����Ʊ�������Ƿ����ָ���Ĳ�Ʊ����
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
	 * ����Ҫ����Ʊ����Ĳ�Ʊ����ָ����־�ļ���Ʊ�����ѯ��������У��˷�����Ҫ���г�Ʊ������Ĳ�Ʊ����ָ��mark�Ķ��С�
	 * ����Ʊ��Ʊ���������е����ƣ�TicketStation + mark + "CheckTicketRequestQueue"
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
	 * ����Ҫ����Ʊ����Ĳ�Ʊ�������Ʊ�����ѯ��������У��˷��������в��ֵĳ�Ʊ�����鶼�����ͬһ�����С�
	 * ����Ʊ��Ʊ���������е����ƣ�TicketStation + "CheckTicketRequestQueue"
	 * @param ticket
	 * @return
	 */
	public static String putCheckTicketRequest(BetTicketDomain ticket){
		return putCheckTicketRequest(ticket, "");
	}

	/**
	 * �Ӽ���Ʊ�����������л�ȡһ���������Ʊ����Ĳ�Ʊ
	 * ����Ʊ���������е����ƣ�TicketStation + mark + "CheckTicketRequestQueue"
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
	 * ������Ʊ�������������Ƿ���ָ���Ĳ�Ʊ����
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
