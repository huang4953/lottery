package com.success.lottery.ticket.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.dao.impl.BetOrderDaoImpl;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.ticket.dao.impl.BetTicketDaoImpl;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.service.DYJSplitTools;
import com.success.lottery.ticket.service.OrderDispatch;
import com.success.lottery.ticket.service.TicketLogInfo;
import com.success.lottery.ticket.service.TicketLogger;
import com.success.lottery.ticket.service.interf.BetSplitterService;
import com.success.lottery.util.LotterySequence;
import com.success.lottery.util.LotteryTools;
import com.success.utils.AutoProperties;
public class QHTCOrderSplitter implements BetSplitterService{

	private BetTicketDaoImpl	betTicketDao;
	private BetOrderDaoImpl		betOrderDao;
	private Log					logger	= LogFactory.getLog(QHTCOrderSplitter.class.getName());

	@Override
	public String orderSplit(BetOrderDomain order) throws LotteryException{
		String rs = null;
		try{
			logger.debug("QHTCOrderSplitter get a order(" + order.getOrderId() + ") to check it whether splitted or not.");
			List<Integer> whoStatus = new ArrayList<Integer>();
			whoStatus.add(Integer.valueOf(0));
			whoStatus.add(Integer.valueOf(1));
			
			int count = betTicketDao.queryBetOrderIdTicket(order.getOrderId());
			if(count > 0){
				logger.debug("QHTCOrderSplitter get the order(" + order.getOrderId() + ") was splitted already.");
				rs = "AlreadySplitted";
			}else{
				logger.debug("QHTCOrderSplitter get the order(" + order.getOrderId() + ") don't split, begin split it.");
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(order.getBetCode());
				// 彩票总注数
				betTicket.setBetCount(1);
				betTicket.setBetAmount(order.getBetAmount());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("qhtc");
				betTicket.setTicketStatus(0);
				betTicket.setTicketSequence(LotterySequence.getInstatce("CP").getSequence());
				betTicketDao.insertBetTicket(betTicket);
			}
			betOrderDao.updateBetOrderStatus(order.getOrderId(), 2);
		}catch(Exception e){
			rs = e.toString();
			logger.error("split ticket exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(BetSplitterService.BETSPLITEXCEPTION, BetSplitterService.BETSPLITEXCEPTION_STR + e.toString());
		}
		return rs;
	}

	public BetTicketDaoImpl getBetTicketDao(){
		return betTicketDao;
	}

	public void setBetTicketDao(BetTicketDaoImpl betTicketDao){
		this.betTicketDao = betTicketDao;
	}

	public BetOrderDaoImpl getBetOrderDao(){
		return betOrderDao;
	}

	public void setBetOrderDao(BetOrderDaoImpl betOrderDao){
		this.betOrderDao = betOrderDao;
	}

	@Override
	public synchronized String createPrintFile(int lotteryId, String term, String username) throws LotteryException{
		try{
			if(lotteryId <= 0){
				//return null;
				lotteryId = -1;
			}
			if(StringUtils.isBlank(term)){
				//return null;
				term = null;
			}		
			List<BetTicketDomain> ticketes = this.betTicketDao.getTicketsToPrint(lotteryId, term);
			if(ticketes == null || ticketes.size() < 1){
				throw new LotteryException(BetSplitterService.NOTFOUNDPRINTTICKET, BetSplitterService.NOTFOUNDPRINTTICKET_STR);
			}
			
			logger.debug("ticketes:" + ticketes.size());
			List<String> betCodes = new ArrayList<String>();
			while(!ticketes.isEmpty()){
				String rs = null;
				BetTicketDomain ticket = ticketes.remove(0);
				int ticketCount = 1;
				long ticketAmount = ticket.getBetAmount() / 100;
				String extensionIdentify = ticket.getOrderId();
				int ticketStatus = 1;
				
				TicketLogInfo info = new TicketLogInfo();
				info.setName("TICKETSENDER");
				info.setInTime(System.currentTimeMillis());
				info.setProcessorName("QHTCOrderSplitter");
				info.setTicketSequence(ticket.getTicketSequence());
				info.setOrderId(ticket.getOrderId());
				info.addRemark("operator", username);
				info.setStation("qhtc");

				switch(getCreateFileRules(ticket.getLotteryId(), ticket.getPlayType(), ticket.getBetType())){
					case DYJSplitTools.ONEORDERFILE :
						//大乐透胆拖、排列三直选和值、排列三组选和值订单每个订单生成一个文件
						//NAME, inTime, outTime, processorName, ticketSequence, orderId, remark, station, result<br>
						try{
							betCodes = LotteryTools.lotteryBetToSingleList(ticket.getLotteryId(), ticket.getPlayType(), ticket.getBetType(), ticket.getBetCode());
							String fileName = DYJSplitTools.getTicketFileName(ticket.getLotteryId(), ticket.getBetTerm(), ticket.getPlayType(), ticket.getBetType(), ticket.getBetMultiple(), ticketAmount, extensionIdentify);
							String filePath = AutoProperties.getString("qhtc.filePath", ".", DYJSplitTools.CONFIGFILE);
							info.addRemark("fileName", fileName);
							PrintWriter pw = new PrintWriter(new File(filePath + fileName));
							for(String code : betCodes){
								pw.println(DYJSplitTools.convertBetCode(ticket.getLotteryId(), code));
							}
							pw.flush();
							pw.close();
							List<String> sequences = new ArrayList<String>();
							sequences.add(ticket.getTicketSequence());
							betTicketDao.updateTicketInfo(sequences, ticketStatus, fileName, String.format("%1$tY-%1$tm-%1$td %1$TH:%1$TM:%1$TS", Calendar.getInstance()), username, null, null, null);
						}catch(Exception e){
							rs = e.toString();
						}
						info.addRemark("rules", "" + DYJSplitTools.ONEORDERFILE);
						info.setOutTime(System.currentTimeMillis());
						info.setResult(rs);
						TicketLogger.getInstance("TICKET").log(info);
						break;
					case DYJSplitTools.SINGLEDUPLEXFILE :
						info.addRemark("rules", "" + DYJSplitTools.SINGLEDUPLEXFILE);
						List<BetTicketDomain> matchTicketes = new ArrayList<BetTicketDomain>();
						//以下是寻找可以组合在一起生成文件的订单
						for(BetTicketDomain t : ticketes){
							System.out.println("t.lotteryId=" + t.getLotteryId() + ", t.playType=" + t.getPlayType() + ", t.betType=" + t.getBetType() + ", t.betMultiple=" + t.getBetMultiple());
							System.out.println("r.lotteryId=" + ticket.getLotteryId() + ", r.playType=" + ticket.getPlayType() + ", r.betType=" + ticket.getBetType() + ", r.betMultiple=" + ticket.getBetMultiple());
							
//							System.out.println("lotteryId:" + (t.getLotteryId().intValue() == ticket.getLotteryId()));
//							System.out.println("playType:" + (t.getPlayType() == ticket.getPlayType()));
//							System.out.println("betType:" + (t.getBetType() == ticket.getBetType()));
//							System.out.println("betMultiple:" + (t.getBetMultiple() == ticket.getBetMultiple()));

							if(t.getLotteryId().intValue() == ticket.getLotteryId().intValue() && t.getBetTerm().trim().equals(ticket.getBetTerm().trim()) && t.getPlayType().intValue() == ticket.getPlayType().intValue() 
										&& t.getBetType().intValue() == ticket.getBetType().intValue() && t.getBetMultiple().intValue() == ticket.getBetMultiple().intValue()){
								ticketCount++;
								ticketAmount += t.getBetAmount() / 100;
								matchTicketes.add(t);
							}
						}
						extensionIdentify = "" + ticketCount;
						ticketes.removeAll(matchTicketes);
						matchTicketes.add(ticket);

						Map<String, String> resultes = new HashMap<String, String>();
						try{
							String fileName = DYJSplitTools.getTicketFileName(ticket.getLotteryId().intValue(), ticket.getBetTerm(), ticket.getPlayType(), ticket.getBetType(), ticket.getBetMultiple(), ticketAmount, extensionIdentify);
							String filePath = AutoProperties.getString("qhtc.filePath", ".", DYJSplitTools.CONFIGFILE);
							info.addRemark("fileName", fileName);
							PrintWriter pw = new PrintWriter(new File(filePath + fileName));
							List<String> sequences = new ArrayList<String>();
							//匹配到的Ticket记录
							for(BetTicketDomain t : matchTicketes){
								try{
									for(String code : t.getBetCode().split("\\^")){
										pw.println(DYJSplitTools.convertBetCode(ticket.getLotteryId(), code));
									}
								}catch(Exception e){
									resultes.put(t.getTicketSequence(), e.toString());
								}
								sequences.add(t.getTicketSequence());
							}
							pw.flush();
							pw.close();
							betTicketDao.updateTicketInfo(sequences, ticketStatus, fileName, String.format("%1$tY-%1$tm-%1$td %1$TH:%1$TM:%1$TS", Calendar.getInstance()), username, null, null, null);							
						}catch(Exception e){
							rs = e.toString();
						}

						//记录日志
						for(BetTicketDomain t : matchTicketes){
							info.setTicketSequence(t.getTicketSequence());
							info.setOrderId(t.getOrderId());
							info.setOutTime(System.currentTimeMillis());
							if(rs == null){
								info.setResult(resultes.get(t.getTicketSequence()));
							}else{
								info.setResult(rs);
							}
							TicketLogger.getInstance("TICKET").log(info);
						}
						break;
					case DYJSplitTools.UNDEFINE:
					default:
						ticketStatus = 9;
						info.addRemark("rules", "" + DYJSplitTools.UNDEFINE);
						rs = "ErrorRules";
						try{
							List<String> sequences = new ArrayList<String>();
							sequences.add(ticket.getTicketSequence());
							betTicketDao.updateTicketInfo(sequences, ticketStatus, null, String.format("%1$tY-%1$tm-%1$td %1$TH:%1$TM:%1$TS", Calendar.getInstance()), username, null, null, null);
						}catch(Exception e){
							rs = rs + ";" + e.toString();
						}
						info.addRemark("rules", "" + DYJSplitTools.ONEORDERFILE);
						info.setOutTime(System.currentTimeMillis());
						info.setResult(rs);
						TicketLogger.getInstance("TICKET").log(info);
						break;
				}
			}
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("createPrintFile(" + lotteryId + ", " + term + ", " + username + ") exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(BetSplitterService.CREATEFILETOPRINTEXCEPTION, BetSplitterService.CREATEFILETOPRINTEXCEPTION_STR + e.toString());
			
		}
		return null;
	}
	
	
	private int getCreateFileRules(int lotteryId, int playType, int betType){
		switch(lotteryId){
			case 1000001:
				switch(betType){
					case 0:
					case 1:
						return DYJSplitTools.SINGLEDUPLEXFILE;
					case 2:
						return DYJSplitTools.ONEORDERFILE;
					default:
						return DYJSplitTools.UNDEFINE;
				}
			case 1000002:
				switch(betType){
					case 0:
					case 1:
						return DYJSplitTools.SINGLEDUPLEXFILE;
					default:
						return DYJSplitTools.UNDEFINE;
				}
			case 1000003:
				switch(betType){
					case 0:
					case 1:
						return DYJSplitTools.SINGLEDUPLEXFILE;
					case 2:
						return DYJSplitTools.ONEORDERFILE;
					case 3:
					case 4:
					case 5:
						return DYJSplitTools.SINGLEDUPLEXFILE;
					case 6:
						return DYJSplitTools.ONEORDERFILE;
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:						
						return DYJSplitTools.UNDEFINE;
					default:
						return DYJSplitTools.UNDEFINE;
				}
			case 1000004:
				switch(betType){
					case 0:
					case 1:
						return DYJSplitTools.SINGLEDUPLEXFILE;
					default:
						return DYJSplitTools.UNDEFINE;
				}
			case 1000005:
				switch(betType){
					case 0:
					case 1:
						return DYJSplitTools.SINGLEDUPLEXFILE;
					case 2:
						return DYJSplitTools.UNDEFINE;
					default:
						return DYJSplitTools.UNDEFINE;
				}
			case 1300001:
			case 1300002:
				switch(betType){
					case 0:
					case 1:
						return DYJSplitTools.SINGLEDUPLEXFILE;
					default:
						return DYJSplitTools.UNDEFINE;
				}
			case 1300003:
			case 1300004:
			case 1200001:
			case 1200002:
				return DYJSplitTools.UNDEFINE;
			default:
				return DYJSplitTools.UNDEFINE;
		}
	}

	@Override
	public int confirmTicket(List<String> ticketSequences, int confirmFlag, String confirmMsg, String username) throws LotteryException{
		try{
			if(ticketSequences == null || ticketSequences.size() == 0){
				throw new LotteryException(BetSplitterService.NOTFOUNDCONFIRMTICKET, BetSplitterService.NOTFOUNDCONFIRMTICKET_STR);
			}
			int ticketStatus = 1;
			if(confirmFlag == 1){
				ticketStatus = 6;
			} else {
				ticketStatus = 7;
			}
			int rc = betTicketDao.updateMultipleTicketPrintResult(ticketSequences, ticketStatus, confirmMsg, username);
			if(rc == 0){
				throw new LotteryException(BetSplitterService.NOTFOUNDCONFIRMTICKET, BetSplitterService.NOTFOUNDCONFIRMTICKET_STR);
			}

			if("true".equals(AutoProperties.getString("qhtc.checkOrderImmediately", "false", DYJSplitTools.CONFIGFILE))){
				for(String ticketSequence : ticketSequences){
					BetOrderDomain order = betTicketDao.getOrder(ticketSequence);
					if(order != null){
						if(!OrderDispatch.containsCheckOrderRequest(order)){
							OrderDispatch.putCheckOrderRequest(order);
						}
					}
				}
			}
			return rc;
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new LotteryException(BetSplitterService.CONFIRMTICKETEXCEPTION, BetSplitterService.CONFIRMTICKETEXCEPTION_STR + e.toString());
		}
	}
	
	
	@Override
	public FileInputStream getPrintFileInputStream(String fileName) throws LotteryException{
		try{
			String filePath = AutoProperties.getString("qhtc.filePath", ".", DYJSplitTools.CONFIGFILE);
			return new FileInputStream(filePath + fileName);
		}catch(Exception e){
			e.printStackTrace();
			throw new LotteryException(BetSplitterService.GETDOWNLOADFILEEXCEPTION, BetSplitterService.GETDOWNLOADFILEEXCEPTION_STR + e.toString());
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		String betCode = "1*2*3^2*4*5*6^3*8*7*4";
		betCode = "1234";
		for(String code : betCode.split("\\^")){
			System.out.println(code);
		}
	}

}
