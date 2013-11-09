/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.success.lottery.ticket.service.impl;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.dao.impl.BetOrderDaoImpl;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.ticket.dao.impl.BetTicketDaoImpl;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.service.EHandSplitTools;
import com.success.lottery.ticket.service.TicketLogInfo;
import com.success.lottery.ticket.service.TicketLogger;
import com.success.lottery.ticket.service.interf.BetSplitterService;
import com.success.lottery.util.LotterySequence;
import com.success.lottery.util.LotteryTools;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author bing.li
 */
public class EHandOrderSplitter implements BetSplitterService{
	
	private BetTicketDaoImpl	betTicketDao;
	private BetOrderDaoImpl		betOrderDao;
	private Random				random	= new Random();
	private Log					logger	= LogFactory.getLog(EHandOrderSplitter.class.getName());

	@Override
	public String orderSplit(BetOrderDomain order) throws LotteryException {
		String rs = null;
		List<TicketLogInfo> ticketLogs = new ArrayList<TicketLogInfo>();
		try{
			int randomInt = random.nextInt(10);
			logger.debug("EHandOrderSplitter get a order(" + order.getOrderId() + ") to check it whether splitted or not.");
			int count = betTicketDao.queryBetOrderIdTicket(order.getOrderId());
			if(count > 0){
				logger.debug("EHandOrderSplitter get the order(" + order.getOrderId() + ") was splitted to " + count + " ticket already.");
				rs = "AlreadySplitted";
			}else{
				logger.debug("EHandOrderSplitter get the order(" + order.getOrderId() + ") not splitted, begin split it.");
				List<BetTicketDomain> ticketes = splitOrder(order);
				if(ticketes == null || ticketes.size() <= 0){
					logger.warn("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket, but get 0 ticketes.");
					rs = "SplitGet0Ticketes";
				}else{
                    logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to " + ticketes.size() + " ticketes(" + ticketes.toString() + ").");
					count = 1;
					int total = ticketes.size();
					for(BetTicketDomain ticket : ticketes){
						logger.debug("EHandOrderSplitter splitted the order(" + order.getOrderId() + ")'s " + count + " of " + total + " ticket(" + ticket.getTicketSequence() + ").");
						ticket.setSaveStatus(randomInt);
						ticket.setTicketStatus(0);
						betTicketDao.insertBetTicket(ticket);
						logger.debug("EHandOrderSplitter insert the splitted order(" + order.getOrderId() + ")'s " + count + " of " + total + " ticket(" + ticket.getTicketSequence() + ") into database.");
//						while((rs = TicketDispatch.dispatch(ticket)) != null){
//							//报警 记录日志
//							logger.warn("EHandOrderSplitter enque the splitted order(" + order.getOrderId() + ")'s " + count + " of " + total + " ticket(" + ticket.getTicketSequence() + ") into queue.");
//							try{
//								Thread.sleep(3000);
//							}catch(Exception e){
//							}
//						}
						TicketLogInfo info = new TicketLogInfo();
						info.setName("ORDERSPLITTER");
						info.setInTime(System.currentTimeMillis());
						info.setProcessorName("EHandOrderSplitter");
						info.setTicketSequence(ticket.getTicketSequence());
						info.setOrderId(ticket.getOrderId());
						info.addRemark("operator", "auto");
						info.setStation("eHand");
						info.addRemark("random", "" + randomInt);
						ticketLogs.add(info);
						count++;
					}
					betOrderDao.updateBetOrderStatus(order.getOrderId(), 2);
					logger.debug("EHandOrderSplitter update the splitted order(" + order.getOrderId() + ")'s orderStatus=2.");
					for(TicketLogInfo info : ticketLogs){
						info.setOutTime(System.currentTimeMillis());
						info.setResult(null);
						TicketLogger.getInstance("TICKET").log(info);
					}
				}
			}
		}catch(Exception e){
			rs = e.toString();
			logger.error("EHandOrderSplitter split the order(" + order.getOrderId() + ") occur unknow exception:" + e);
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			logger.error("EHandOrderSplitter split the order(" + order.getOrderId() + ") occur exception, so all database operate will be rollback.");
			throw new LotteryException(BetSplitterService.BETSPLITEXCEPTION, BetSplitterService.BETSPLITEXCEPTION_STR + e.toString());
		}
		return rs;
	}

	@Override
	public String createPrintFile(int lotteryId, String term, String username) throws LotteryException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public FileInputStream getPrintFileInputStream(String fileName) throws LotteryException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int confirmTicket(List<String> ticketSequences, int confirmFlag, String confirmMsg, String username) throws LotteryException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	private List<BetTicketDomain> splitOrder(BetOrderDomain order){
		logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ")'s lotteryId   = " + order.getLotteryId());
		logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ")'s betTerm     = " + order.getBetTerm());
		logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ")'s playType    = " + order.getPlayType());
		logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ")'s betType     = " + order.getBetType());
		logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ")'s betMultiple = " + order.getBetMultiple());
		logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ")'s amount      = " + order.getBetAmount());
		logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ")'s betCode     = " + order.getBetCode());
		logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ")'s orderStatus = " + order.getOrderStatus());
		logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ")'s areaCode    = " + order.getAreaCode());
		switch(order.getLotteryId()){
			case 1000001:
				return split1000001Order(order);
			case 1000002:
				return split1000002Order(order);
			case 1000003:
				return split1000003Order(order);
			case 1000004:
				return split1000004Order(order);
			case 1000005:
				return split1000005Order(order);
			case 1200001:
				return split1200001Order(order);
			case 1300001:
				return split1300001Order(order);
			case 1300002:
				return split1300002Order(order);
			case 1300003:
				return split1300003Order(order);
			case 1300004:
				return split1300004Order(order);
			default:
				throw new UnsupportedOperationException("Not supported lotteryId " + order.getLotteryId() + ".");
		}
	}

	private List<BetTicketDomain> split1000001Order(BetOrderDomain order){
		if(StringUtils.isBlank(order.getBetCode())){
			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
		}
		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();

		// 大乐透单式
		if(order.getBetType() == 0){
			String betCode = order.getBetCode().trim();
			int len = betCode.length();
			int count = 1;
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLT single bet, betCode length = " + len);
			for(int pos = 0; pos < len; pos = pos + 80){
				String code = betCode.substring(pos, betCode.substring(pos).length() < 79 ? pos + betCode.substring(pos).length() : pos + 79);
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
					betCount = Integer.parseInt(result.split("#")[0]);
					amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else if(order.getBetType() == 1){
			// 大乐透复式
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLT duplex bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				if(betCode.length() == 15){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
			
			// 大乐透复式中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLT single bet in duplex bet, betCode = " + betCode);
				int len = betCode.length();
				for(int pos = 0; pos < len; pos = pos + 80){
					String code = betCode.substring(pos, betCode.substring(pos).length() < 79 ? pos + betCode.substring(pos).length() : pos + 79);
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(0);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else if(order.getBetType() == 2){
			// 大乐透胆拖
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLT dantuo bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				betCode = EHandSplitTools.getSingleBetCodeInDLTDantuo(betCode);
				if(betCode.length() == 15){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
			// 大乐透胆拖中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLT single bet in dantuo bet, betCode = " + betCode);
				int len = betCode.length();
				for(int pos = 0; pos < len; pos = pos + 80){
					String code = betCode.substring(pos, betCode.substring(pos).length() < 79 ? pos + betCode.substring(pos).length() : pos + 79);
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(0);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else {
			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
		}
		return ticketes;
	}

	private List<BetTicketDomain> split1000002Order(BetOrderDomain order){
		if(StringUtils.isBlank(order.getBetCode())){
			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
		}
		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();
		// 七星彩单式
		if(order.getBetType() == 0){
			String betCode = order.getBetCode().trim();
			int len = betCode.length();
			int count = 1;
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is QXC single bet, betCode length = " + len);
			for(int pos = 0; pos < len; pos = pos + 70){
				String code = betCode.substring(pos, betCode.substring(pos).length() < 69 ? pos + betCode.substring(pos).length() : pos + 69);
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
					betCount = Integer.parseInt(result.split("#")[0]);
					amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else if(order.getBetType() == 1){
			// 七星彩复式
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is QXC duplex bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				if(betCode.length() == 13){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}

			// 七星彩复式中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is QXC single bet in duplex bet, betCode = " + betCode);
				int len = betCode.length();
				for(int pos = 0; pos < len; pos = pos + 70){
					String code = betCode.substring(pos, betCode.substring(pos).length() < 69 ? pos + betCode.substring(pos).length() : pos + 69);
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(0);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else {
			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
		}
		return ticketes;

	}

	private List<BetTicketDomain> split1000003Order(BetOrderDomain order){
		if(StringUtils.isBlank(order.getBetCode())){
			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
		}
		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();
		// 排列三单式
		if(order.getBetType() == 0){
			String betCode = order.getBetCode().trim();
			int len = betCode.length();
			int count = 1;
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is PLS single bet, betCode length = " + len);
			for(int pos = 0; pos < len; pos = pos + 30){
				String code = betCode.substring(pos, betCode.substring(pos).length() < 29 ? pos + betCode.substring(pos).length() : pos + 29);
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
					betCount = Integer.parseInt(result.split("#")[0]);
					amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else if(order.getBetType() == 1){
			//排列三直选复式
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is PLS duplex bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				if(betCode.length() == 5){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}

			// 排列三直选复式中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is PLS single bet in duplex bet, betCode = " + betCode);
				int len = betCode.length();
				for(int pos = 0; pos < len; pos = pos + 30){
					String code = betCode.substring(pos, betCode.substring(pos).length() < 29 ? pos + betCode.substring(pos).length() : pos + 29);
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(0);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else if(order.getBetType() == 2){
			//排列三直选和值
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is PLS Zhixuan hezhi bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String[] resultCodes = new String[betCodes.length];
			List<String> repeatedCodes = EHandSplitTools.getRepeatedHezhiCode(betCodes, resultCodes);
			String betCode = "";
			for(String code : resultCodes){
				if(code != null && !"".equals(code.trim())){
					betCode = betCode + code + "^";
				}
			}
			if(betCode.length() > 0){
				betCode = betCode.substring(0, betCode.length() - 1);
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
				} else {
					try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(betCode);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
			for(String code : repeatedCodes){
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else if(order.getBetType() == 3){
			//排列三组选单式
			String betCode = order.getBetCode().trim();
			int len = betCode.length();
			int count = 1;
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is PLS Zuxuan single bet, betCode length = " + len);
			for(int pos = 0; pos < len; pos = pos + 30){
				String code = betCode.substring(pos, betCode.substring(pos).length() < 29 ? pos + betCode.substring(pos).length() : pos + 29);
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
					betCount = Integer.parseInt(result.split("#")[0]);
					amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else if(order.getBetType() == 4){
			//组选六复式
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is PLS Zuxuan6 duplex bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				if(betCode.length() == 5){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}

			// 排列三组选六复式中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is PLS Zuxuan single bet in Zuxuan6 duplex bet, betCode = " + betCode);
				int len = betCode.length();
				for(int pos = 0; pos < len; pos = pos + 30){
					String code = betCode.substring(pos, betCode.substring(pos).length() < 29 ? pos + betCode.substring(pos).length() : pos + 29);
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 3, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(3);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else if(order.getBetType() == 5){
			//组选三复式，不可能有单式
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is PLS Zuxuan3 duplex bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
				} else {
					try{
					betCount = Integer.parseInt(result.split("#")[0]);
					amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(betCode);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else if(order.getBetType() == 6){
			//排列三组选合值
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is PLS Zuxuan hezhi bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String[] resultCodes = new String[betCodes.length];
			List<String> repeatedCodes = EHandSplitTools.getRepeatedHezhiCode(betCodes, resultCodes);
			String betCode = "";
			for(String code : resultCodes){
				if(code != null && !"".equals(code.trim())){
					betCode = betCode + code + "^";
				}
			}
			if(betCode.length() > 0){
				betCode = betCode.substring(0, betCode.length() - 1);
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
				} else {
					try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(betCode);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
			for(String code : repeatedCodes){
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else {
			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
		}
		return ticketes;
	}

	private List<BetTicketDomain> split1000004Order(BetOrderDomain order){
		if(StringUtils.isBlank(order.getBetCode())){
			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
		}
		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();
		// 排列五单式
		if(order.getBetType() == 0){
			String betCode = order.getBetCode().trim();
			int len = betCode.length();
			int count = 1;
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is PLW single bet, betCode length = " + len);
			for(int pos = 0; pos < len; pos = pos + 50){
				String code = betCode.substring(pos, betCode.substring(pos).length() < 49 ? pos + betCode.substring(pos).length() : pos + 49);
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
					betCount = Integer.parseInt(result.split("#")[0]);
					amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else if(order.getBetType() == 1){
			// 排列五复式
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is PLW duplex bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				if(betCode.length() == 9){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}

			// 排列五复式中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is PLW single bet in duplex bet, betCode = " + betCode);
				int len = betCode.length();
				for(int pos = 0; pos < len; pos = pos + 50){
					String code = betCode.substring(pos, betCode.substring(pos).length() < 49 ? pos + betCode.substring(pos).length() : pos + 49);
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(0);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else {
			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
		}
		return ticketes;

	}

	private List<BetTicketDomain> split1000005Order(BetOrderDomain order){
		if(StringUtils.isBlank(order.getBetCode())){
			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
		}
		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();
		// 生肖乐单式
		if(order.getBetType() == 0){
			String betCode = order.getBetCode().trim();
			int len = betCode.length();
			int count = 1;
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is SXL single bet, betCode length = " + len);
			for(int pos = 0; pos < len; pos = pos + 25){
				String code = betCode.substring(pos, betCode.substring(pos).length() < 24 ? pos + betCode.substring(pos).length() : pos + 24);
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
					betCount = Integer.parseInt(result.split("#")[0]);
					amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else if(order.getBetType() == 1){
			//生肖乐复式
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is SXL duplex bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				if(betCode.length() == 4){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}

			// 生肖乐复式中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is SXL single bet in duplex bet, betCode = " + betCode);
				int len = betCode.length();
				for(int pos = 0; pos < len; pos = pos + 25){
					String code = betCode.substring(pos, betCode.substring(pos).length() < 24 ? pos + betCode.substring(pos).length() : pos + 24);
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(0);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else {
			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
		}
		return ticketes;
		
	}

	private List<BetTicketDomain> split1300001Order(BetOrderDomain order){
		if(StringUtils.isBlank(order.getBetCode())){
			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
		}
		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();
		// 足彩胜负彩单式
		if(order.getBetType() == 0){
			String betCode = order.getBetCode().trim();
			int len = betCode.length();
			int count = 1;
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is ZQC-SFC single bet, betCode length = " + len);
			for(int pos = 0; pos < len; pos = pos + 140){
				String code = betCode.substring(pos, betCode.substring(pos).length() < 139 ? pos + betCode.substring(pos).length() : pos + 139);
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
					betCount = Integer.parseInt(result.split("#")[0]);
					amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else if(order.getBetType() == 1){
			//足彩胜负彩复式
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is ZQC-SFC duplex bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				if(betCode.length() == 27){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}

			// 足彩胜负彩复式中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is ZQC-SFC single bet in duplex bet, betCode = " + betCode);
				int len = betCode.length();
				for(int pos = 0; pos < len; pos = pos + 140){
					String code = betCode.substring(pos, betCode.substring(pos).length() < 139 ? pos + betCode.substring(pos).length() : pos + 139);
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(0);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else {
			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
		}
		return ticketes;
	}

	private List<BetTicketDomain> split1300002Order(BetOrderDomain order){
		if(StringUtils.isBlank(order.getBetCode())){
			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
		}
		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();
		// 足彩任选九单式
		if(order.getBetType() == 0){
			String betCode = order.getBetCode().trim();
			int len = betCode.length();
			int count = 1;
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is ZQC-RXJ single bet, betCode length = " + len);
			for(int pos = 0; pos < len; pos = pos + 140){
				String code = betCode.substring(pos, betCode.substring(pos).length() < 139 ? pos + betCode.substring(pos).length() : pos + 139);
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
					betCount = Integer.parseInt(result.split("#")[0]);
					amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else if(order.getBetType() == 1){
			//足彩胜负彩复式
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is ZQC-RXJ duplex bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				if(betCode.length() == 27){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}

			// 足彩任选九复式中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is ZQC-RXJ single bet in duplex bet, betCode = " + betCode);
				int len = betCode.length();
				for(int pos = 0; pos < len; pos = pos + 140){
					String code = betCode.substring(pos, betCode.substring(pos).length() < 139 ? pos + betCode.substring(pos).length() : pos + 139);
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(0);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else {
			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
		}
		return ticketes;
	}

	private List<BetTicketDomain> split1300003Order(BetOrderDomain order){
		if(StringUtils.isBlank(order.getBetCode())){
			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
		}
		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();
		// 足彩半全场单式
		if(order.getBetType() == 0){
			String betCode = order.getBetCode().trim();
			int len = betCode.length();
			int count = 1;
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is ZQC-BQC single bet, betCode length = " + len);
			for(int pos = 0; pos < len; pos = pos + 120){
				String code = betCode.substring(pos, betCode.substring(pos).length() < 119 ? pos + betCode.substring(pos).length() : pos + 119);
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
					betCount = Integer.parseInt(result.split("#")[0]);
					amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else if(order.getBetType() == 1){
			//足彩半全场复式
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is ZQC-BQC duplex bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				if(betCode.length() == 23){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}

			// 足彩半全场复式中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is ZQC-BQC single bet in duplex bet, betCode = " + betCode);
				int len = betCode.length();
				for(int pos = 0; pos < len; pos = pos + 120){
					String code = betCode.substring(pos, betCode.substring(pos).length() < 119 ? pos + betCode.substring(pos).length() : pos + 119);
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(0);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else {
			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
		}
		return ticketes;
	}

	private List<BetTicketDomain> split1300004Order(BetOrderDomain order){
		if(StringUtils.isBlank(order.getBetCode())){
			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
		}
		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();
		// 足彩进球彩单式
		if(order.getBetType() == 0){
			String betCode = order.getBetCode().trim();
			int len = betCode.length();
			int count = 1;
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is ZQC-JQC single bet, betCode length = " + len);
			for(int pos = 0; pos < len; pos = pos + 80){
				String code = betCode.substring(pos, betCode.substring(pos).length() < 79 ? pos + betCode.substring(pos).length() : pos + 79);
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
					betCount = Integer.parseInt(result.split("#")[0]);
					amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else if(order.getBetType() == 1){
			//足彩进球彩复式
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is ZQC-JQC duplex bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				if(betCode.length() == 15){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}

			// 足彩进球彩复式中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is ZQC-JQC single bet in duplex bet, betCode = " + betCode);
				int len = betCode.length();
				for(int pos = 0; pos < len; pos = pos + 80){
					String code = betCode.substring(pos, betCode.substring(pos).length() < 79 ? pos + betCode.substring(pos).length() : pos + 79);
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(0);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else {
			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
		}
		return ticketes;
	}

	private List<BetTicketDomain> split1200001Order(BetOrderDomain order){
		if(StringUtils.isBlank(order.getBetCode())){
			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
		}
		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();

		switch(order.getPlayType()){
			case 1:
				return split1200001_1(order);
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				return split1200001_2_8(order, order.getPlayType());
			case 9:
			case 10:
				return split1200001_9_10(order, order.getPlayType());
			case 11:
			case 12:
				return split1200001_11_12(order, order.getPlayType());
			default:
				throw new UnsupportedOperationException("Not supported playType " + order.getPlayType() + ".");
		}
	}

	private List<BetTicketDomain> split1200001_1(BetOrderDomain order){
		if(StringUtils.isBlank(order.getBetCode())){
			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
		}
		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();

		// 多乐彩任选一单式
		if(order.getBetType() == 0){
			String betCode = order.getBetCode().trim();
			int len = betCode.length();
			int count = 1;
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX1 single bet, betCode length = " + len);
			for(int pos = 0; pos < len; pos = pos + 15){
				String code = betCode.substring(pos, betCode.substring(pos).length() < 14 ? pos + betCode.substring(pos).length() : pos + 14);
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
					betCount = Integer.parseInt(result.split("#")[0]);
					amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else {
			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
		}
		return ticketes;
	}
	
	private List<BetTicketDomain> split1200001_2_8(BetOrderDomain order, int playType){
		if(StringUtils.isBlank(order.getBetCode())){
			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
		}
		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();

		// 多乐彩任选单式
		if(order.getBetType() == 0){
			String betCode = order.getBetCode().trim();
			int len = betCode.length();
			int count = 1;
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX" + playType + " single bet, betCode length = " + len);
			int length = (playType * 2 + 1) * 5;
			for(int pos = 0; pos < len; pos = pos + length){
				String code = betCode.substring(pos, betCode.substring(pos).length() < (length - 1) ? pos + betCode.substring(pos).length() : pos + (length - 1));
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
					betCount = Integer.parseInt(result.split("#")[0]);
					amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else if(order.getBetType() == 1){
			// 多乐彩任选复式
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX" + playType + " duplex bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				if(betCode.length() == playType * 2){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
			
			// 多乐彩任选复式中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX" + playType + " single bet in duplex bet, betCode = " + betCode);
				int len = betCode.length();
				int length = (playType * 2 + 1) * 5;
				for(int pos = 0; pos < len; pos = pos + length){
					String code = betCode.substring(pos, betCode.substring(pos).length() < (length - 1) ? pos + betCode.substring(pos).length() : pos + (length - 1));
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(0);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else if(order.getBetType() == 2){
			// 多乐彩任选胆拖
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX" + playType + " dantuo bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				betCode = EHandSplitTools.getSingleBetCodeInDLCRXDantuo(betCode, playType);
				if(betCode.length() == (playType * 2)){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
			// 多乐彩胆拖中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX" + playType + " single bet in dantuo bet, betCode = " + betCode);
				int len = betCode.length();
				int length = (playType * 2 + 1) * 5;
				for(int pos = 0; pos < len; pos = pos + length){
					String code = betCode.substring(pos, betCode.substring(pos).length() < (length - 1) ? pos + betCode.substring(pos).length() : pos + (length - 1));
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(0);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else {
			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
		}
		return ticketes;
	}

	private List<BetTicketDomain> split1200001_9_10(BetOrderDomain order, int playType){
		if(StringUtils.isBlank(order.getBetCode())){
			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
		}
		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();

		// 多乐彩直选单式
		if(order.getBetType() == 0){
			String betCode = order.getBetCode().trim();
			int len = betCode.length();
			int count = 1;
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-ZhiXuan" + (playType - 7) + " single bet, betCode length = " + len);
			int length = ((playType - 7) * 2 + 1) * 5;
			for(int pos = 0; pos < len; pos = pos + length){
				String code = betCode.substring(pos, betCode.substring(pos).length() < (length - 1) ? pos + betCode.substring(pos).length() : pos + (length - 1));
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
					betCount = Integer.parseInt(result.split("#")[0]);
					amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else if(order.getBetType() == 1){
			// 多乐彩直选复式
			throw new UnsupportedOperationException("Not supported DLC-ZhiXuan's betType:" + order.getBetType());
		} else if(order.getBetType() == 2){
			// 多乐彩直选胆拖
			throw new UnsupportedOperationException("Not supported DLC-ZhiXuan's betType:" + order.getBetType());
		} else if(order.getBetType() == 3){
			//多乐彩直选定位
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-ZhiXuan" + (playType - 7) + " DingWei bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				betCode = EHandSplitTools.getSingleBetCodeInDLCZhiXuanDingwei(betCode, playType - 7);
				if(betCode.length() == (playType - 7) * 2){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
			// 多乐彩直选中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-ZhiXuan" + (playType - 7) + " single bet in DingWei bet, betCode = " + betCode);
				int len = betCode.length();
				int length = ((playType - 7) * 2 + 1) * 5;
				for(int pos = 0; pos < len; pos = pos + length){
					String code = betCode.substring(pos, betCode.substring(pos).length() < (length - 1) ? pos + betCode.substring(pos).length() : pos + (length - 1));
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(0);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else {
			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
		}
		return ticketes;
	}

	private List<BetTicketDomain> split1200001_11_12(BetOrderDomain order, int playType){
		if(StringUtils.isBlank(order.getBetCode())){
			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
		}
		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();
		// 多乐彩组选单式
		if(order.getBetType() == 0){
			String betCode = order.getBetCode().trim();
			int len = betCode.length();
			int count = 1;
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-ZuXuan" + (playType - 9) + " single bet, betCode length = " + len);
			int length = ((playType - 9) * 2 + 1) * 5;
			for(int pos = 0; pos < len; pos = pos + length){
				String code = betCode.substring(pos, betCode.substring(pos).length() < (length - 1) ? pos + betCode.substring(pos).length() : pos + (length - 1));
				// 彩票的注数金额
				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
				int betCount = 0;
				int amount = 0;
				if(result == null || result.split("#") == null || result.split("#").length != 2){
					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
				} else {
					try{
					betCount = Integer.parseInt(result.split("#")[0]);
					amount = Integer.parseInt(result.split("#")[1]);
					}catch(Exception e){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
					}
				}
				BetTicketDomain betTicket = new BetTicketDomain();
				betTicket.setOrderId(order.getOrderId());
				betTicket.setLotteryId(order.getLotteryId());
				betTicket.setBetTerm(order.getBetTerm());
				betTicket.setPlayType(order.getPlayType());
				betTicket.setBetType(order.getBetType());
				betTicket.setBetMultiple(order.getBetMultiple());
				betTicket.setBetCode(code);
				betTicket.setBetCount(betCount);
				betTicket.setBetAmount(amount * order.getBetMultiple());
				betTicket.setAreaCode(order.getAreaCode());
				betTicket.setPrintStation("eHand");
				betTicket.setTicketStatus(0);
				String sequence = LotterySequence.getInstatce("CP").getSequence();
				betTicket.setTicketSequence(sequence);
				ticketes.add(betTicket);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
			}
		} else if(order.getBetType() == 1){
			// 多乐彩组选复式
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-ZuXuan" + (playType - 9) + " duplex bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				if(betCode.length() == (playType - 9) * 2){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}

			// 多乐彩组选复式中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-ZuXuan" + (playType - 9) + " single bet in duplex bet, betCode = " + betCode);
				int len = betCode.length();
				int length = ((playType - 9) * 2 + 1) * 5;
				for(int pos = 0; pos < len; pos = pos + length){
					String code = betCode.substring(pos, betCode.substring(pos).length() < (length - 1) ? pos + betCode.substring(pos).length() : pos + (length - 1));
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(0);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else if(order.getBetType() == 2){
			// 多乐彩组选胆拖
			String[] betCodes = order.getBetCode().trim().split("\\^");
			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-ZuXuan" + (playType - 9) + " dantuo bet, betCode.split(^).size=" + betCodes.length);
			int count = 1;
			String singleCodes = "";
			for(String betCode : betCodes){
				betCode = EHandSplitTools.getSingleBetCodeInDLCZuXuanDantuo(betCode, playType - 9);
				if(betCode.length() == ((playType - 9) * 2)){
					singleCodes = singleCodes + betCode + "^";
				} else {
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(order.getBetType());
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(betCode);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
			// 多乐彩胆拖中的单式
			if(singleCodes.length() > 0){
				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-ZuXuan" + (playType - 9) + " single bet in dantuo bet, betCode = " + betCode);
				int len = betCode.length();
				int length = ((playType - 9) * 2 + 1) * 5;
				for(int pos = 0; pos < len; pos = pos + length){
					String code = betCode.substring(pos, betCode.substring(pos).length() < (length - 1) ? pos + betCode.substring(pos).length() : pos + (length - 1));
					// 彩票的注数金额
					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
					int betCount = 0;
					int amount = 0;
					if(result == null || result.split("#") == null || result.split("#").length != 2){
						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
					} else {
						try{
						betCount = Integer.parseInt(result.split("#")[0]);
						amount = Integer.parseInt(result.split("#")[1]);
						}catch(Exception e){
							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
						}
					}
					BetTicketDomain betTicket = new BetTicketDomain();
					betTicket.setOrderId(order.getOrderId());
					betTicket.setLotteryId(order.getLotteryId());
					betTicket.setBetTerm(order.getBetTerm());
					betTicket.setPlayType(order.getPlayType());
					betTicket.setBetType(0);
					betTicket.setBetMultiple(order.getBetMultiple());
					betTicket.setBetCode(code);
					betTicket.setBetCount(betCount);
					betTicket.setBetAmount(amount * order.getBetMultiple());
					betTicket.setAreaCode(order.getAreaCode());
					betTicket.setPrintStation("eHand");
					betTicket.setTicketStatus(0);
					String sequence = LotterySequence.getInstatce("CP").getSequence();
					betTicket.setTicketSequence(sequence);
					ticketes.add(betTicket);
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
				}
			}
		} else {
			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
		}
		return ticketes;
	}

//	private List<BetTicketDomain> split1200001_2(BetOrderDomain order){
//		if(StringUtils.isBlank(order.getBetCode())){
//			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
//		}
//		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();
//
//		// 多乐彩任选二单式
//		if(order.getBetType() == 0){
//			String betCode = order.getBetCode().trim();
//			int len = betCode.length();
//			int count = 1;
//			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX2 single bet, betCode length = " + len);
//			for(int pos = 0; pos < len; pos = pos + 25){
//				String code = betCode.substring(pos, betCode.substring(pos).length() < 24 ? pos + betCode.substring(pos).length() : pos + 24);
//				// 彩票的注数金额
//				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
//				int betCount = 0;
//				int amount = 0;
//				if(result == null || result.split("#") == null || result.split("#").length != 2){
//					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
//				} else {
//					try{
//					betCount = Integer.parseInt(result.split("#")[0]);
//					amount = Integer.parseInt(result.split("#")[1]);
//					}catch(Exception e){
//						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
//					}
//				}
//				BetTicketDomain betTicket = new BetTicketDomain();
//				betTicket.setOrderId(order.getOrderId());
//				betTicket.setLotteryId(order.getLotteryId());
//				betTicket.setBetTerm(order.getBetTerm());
//				betTicket.setPlayType(order.getPlayType());
//				betTicket.setBetType(order.getBetType());
//				betTicket.setBetMultiple(order.getBetMultiple());
//				betTicket.setBetCode(code);
//				betTicket.setBetCount(betCount);
//				betTicket.setBetAmount(amount * order.getBetMultiple());
//				betTicket.setAreaCode(order.getAreaCode());
//				betTicket.setPrintStation("eHand");
//				betTicket.setTicketStatus(0);
//				String sequence = LotterySequence.getInstatce("CP").getSequence();
//				betTicket.setTicketSequence(sequence);
//				ticketes.add(betTicket);
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
//			}
//		} else if(order.getBetType() == 1){
//			// 多乐彩任选二复式
//			String[] betCodes = order.getBetCode().trim().split("\\^");
//			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX2 duplex bet, betCode.split(^).size=" + betCodes.length);
//			int count = 1;
//			String singleCodes = "";
//			for(String betCode : betCodes){
//				if(betCode.length() == 4){
//					singleCodes = singleCodes + betCode + "^";
//				} else {
//					// 彩票的注数金额
//					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
//					int betCount = 0;
//					int amount = 0;
//					if(result == null || result.split("#") == null || result.split("#").length != 2){
//						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
//					} else {
//						try{
//						betCount = Integer.parseInt(result.split("#")[0]);
//						amount = Integer.parseInt(result.split("#")[1]);
//						}catch(Exception e){
//							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
//						}
//					}
//					BetTicketDomain betTicket = new BetTicketDomain();
//					betTicket.setOrderId(order.getOrderId());
//					betTicket.setLotteryId(order.getLotteryId());
//					betTicket.setBetTerm(order.getBetTerm());
//					betTicket.setPlayType(order.getPlayType());
//					betTicket.setBetType(order.getBetType());
//					betTicket.setBetMultiple(order.getBetMultiple());
//					betTicket.setBetCode(betCode);
//					betTicket.setBetCount(betCount);
//					betTicket.setBetAmount(amount * order.getBetMultiple());
//					betTicket.setAreaCode(order.getAreaCode());
//					betTicket.setPrintStation("eHand");
//					betTicket.setTicketStatus(0);
//					String sequence = LotterySequence.getInstatce("CP").getSequence();
//					betTicket.setTicketSequence(sequence);
//					ticketes.add(betTicket);
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
//				}
//			}
//
//			// 多乐彩任选二复式中的单式
//			if(singleCodes.length() > 0){
//				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX2 single bet in duplex bet, betCode = " + betCode);
//				int len = betCode.length();
//				for(int pos = 0; pos < len; pos = pos + 25){
//					String code = betCode.substring(pos, betCode.substring(pos).length() < 24 ? pos + betCode.substring(pos).length() : pos + 24);
//					// 彩票的注数金额
//					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
//					int betCount = 0;
//					int amount = 0;
//					if(result == null || result.split("#") == null || result.split("#").length != 2){
//						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
//					} else {
//						try{
//						betCount = Integer.parseInt(result.split("#")[0]);
//						amount = Integer.parseInt(result.split("#")[1]);
//						}catch(Exception e){
//							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
//						}
//					}
//					BetTicketDomain betTicket = new BetTicketDomain();
//					betTicket.setOrderId(order.getOrderId());
//					betTicket.setLotteryId(order.getLotteryId());
//					betTicket.setBetTerm(order.getBetTerm());
//					betTicket.setPlayType(order.getPlayType());
//					betTicket.setBetType(0);
//					betTicket.setBetMultiple(order.getBetMultiple());
//					betTicket.setBetCode(code);
//					betTicket.setBetCount(betCount);
//					betTicket.setBetAmount(amount * order.getBetMultiple());
//					betTicket.setAreaCode(order.getAreaCode());
//					betTicket.setPrintStation("eHand");
//					betTicket.setTicketStatus(0);
//					String sequence = LotterySequence.getInstatce("CP").getSequence();
//					betTicket.setTicketSequence(sequence);
//					ticketes.add(betTicket);
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
//				}
//			}
//		} else if(order.getBetType() == 2){
//			// 多乐彩任选二胆拖
//			String[] betCodes = order.getBetCode().trim().split("\\^");
//			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX2 dantuo bet, betCode.split(^).size=" + betCodes.length);
//			int count = 1;
//			String singleCodes = "";
//			for(String betCode : betCodes){
//				betCode = EHandSplitTools.getSingleBetCodeInDLCRXDantuo(betCode, 2);
//				if(betCode.length() == 4){
//					singleCodes = singleCodes + betCode + "^";
//				} else {
//					// 彩票的注数金额
//					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
//					int betCount = 0;
//					int amount = 0;
//					if(result == null || result.split("#") == null || result.split("#").length != 2){
//						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
//					} else {
//						try{
//						betCount = Integer.parseInt(result.split("#")[0]);
//						amount = Integer.parseInt(result.split("#")[1]);
//						}catch(Exception e){
//							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
//						}
//					}
//					BetTicketDomain betTicket = new BetTicketDomain();
//					betTicket.setOrderId(order.getOrderId());
//					betTicket.setLotteryId(order.getLotteryId());
//					betTicket.setBetTerm(order.getBetTerm());
//					betTicket.setPlayType(order.getPlayType());
//					betTicket.setBetType(order.getBetType());
//					betTicket.setBetMultiple(order.getBetMultiple());
//					betTicket.setBetCode(betCode);
//					betTicket.setBetCount(betCount);
//					betTicket.setBetAmount(amount * order.getBetMultiple());
//					betTicket.setAreaCode(order.getAreaCode());
//					betTicket.setPrintStation("eHand");
//					betTicket.setTicketStatus(0);
//					String sequence = LotterySequence.getInstatce("CP").getSequence();
//					betTicket.setTicketSequence(sequence);
//					ticketes.add(betTicket);
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
//				}
//			}
//			// 多乐彩胆拖中的单式
//			if(singleCodes.length() > 0){
//				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX2 single bet in dantuo bet, betCode = " + betCode);
//				int len = betCode.length();
//				for(int pos = 0; pos < len; pos = pos + 25){
//					String code = betCode.substring(pos, betCode.substring(pos).length() < 24 ? pos + betCode.substring(pos).length() : pos + 24);
//					// 彩票的注数金额
//					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
//					int betCount = 0;
//					int amount = 0;
//					if(result == null || result.split("#") == null || result.split("#").length != 2){
//						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
//					} else {
//						try{
//						betCount = Integer.parseInt(result.split("#")[0]);
//						amount = Integer.parseInt(result.split("#")[1]);
//						}catch(Exception e){
//							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
//						}
//					}
//					BetTicketDomain betTicket = new BetTicketDomain();
//					betTicket.setOrderId(order.getOrderId());
//					betTicket.setLotteryId(order.getLotteryId());
//					betTicket.setBetTerm(order.getBetTerm());
//					betTicket.setPlayType(order.getPlayType());
//					betTicket.setBetType(0);
//					betTicket.setBetMultiple(order.getBetMultiple());
//					betTicket.setBetCode(code);
//					betTicket.setBetCount(betCount);
//					betTicket.setBetAmount(amount * order.getBetMultiple());
//					betTicket.setAreaCode(order.getAreaCode());
//					betTicket.setPrintStation("eHand");
//					betTicket.setTicketStatus(0);
//					String sequence = LotterySequence.getInstatce("CP").getSequence();
//					betTicket.setTicketSequence(sequence);
//					ticketes.add(betTicket);
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
//				}
//			}
//		} else {
//			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
//		}
//		return ticketes;
//	}
//
//
//	private List<BetTicketDomain> split1200001_3(BetOrderDomain order){
//		if(StringUtils.isBlank(order.getBetCode())){
//			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
//		}
//		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();
//
//		// 多乐彩任选三单式
//		if(order.getBetType() == 0){
//			String betCode = order.getBetCode().trim();
//			int len = betCode.length();
//			int count = 1;
//			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX3 single bet, betCode length = " + len);
//			for(int pos = 0; pos < len; pos = pos + 35){
//				String code = betCode.substring(pos, betCode.substring(pos).length() < 34 ? pos + betCode.substring(pos).length() : pos + 34);
//				// 彩票的注数金额
//				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
//				int betCount = 0;
//				int amount = 0;
//				if(result == null || result.split("#") == null || result.split("#").length != 2){
//					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
//				} else {
//					try{
//					betCount = Integer.parseInt(result.split("#")[0]);
//					amount = Integer.parseInt(result.split("#")[1]);
//					}catch(Exception e){
//						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
//					}
//				}
//				BetTicketDomain betTicket = new BetTicketDomain();
//				betTicket.setOrderId(order.getOrderId());
//				betTicket.setLotteryId(order.getLotteryId());
//				betTicket.setBetTerm(order.getBetTerm());
//				betTicket.setPlayType(order.getPlayType());
//				betTicket.setBetType(order.getBetType());
//				betTicket.setBetMultiple(order.getBetMultiple());
//				betTicket.setBetCode(code);
//				betTicket.setBetCount(betCount);
//				betTicket.setBetAmount(amount * order.getBetMultiple());
//				betTicket.setAreaCode(order.getAreaCode());
//				betTicket.setPrintStation("eHand");
//				betTicket.setTicketStatus(0);
//				String sequence = LotterySequence.getInstatce("CP").getSequence();
//				betTicket.setTicketSequence(sequence);
//				ticketes.add(betTicket);
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
//			}
//		} else if(order.getBetType() == 1){
//			// 多乐彩任选三复式
//			String[] betCodes = order.getBetCode().trim().split("\\^");
//			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX3 duplex bet, betCode.split(^).size=" + betCodes.length);
//			int count = 1;
//			String singleCodes = "";
//			for(String betCode : betCodes){
//				if(betCode.length() == 6){
//					singleCodes = singleCodes + betCode + "^";
//				} else {
//					// 彩票的注数金额
//					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
//					int betCount = 0;
//					int amount = 0;
//					if(result == null || result.split("#") == null || result.split("#").length != 2){
//						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
//					} else {
//						try{
//						betCount = Integer.parseInt(result.split("#")[0]);
//						amount = Integer.parseInt(result.split("#")[1]);
//						}catch(Exception e){
//							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
//						}
//					}
//					BetTicketDomain betTicket = new BetTicketDomain();
//					betTicket.setOrderId(order.getOrderId());
//					betTicket.setLotteryId(order.getLotteryId());
//					betTicket.setBetTerm(order.getBetTerm());
//					betTicket.setPlayType(order.getPlayType());
//					betTicket.setBetType(order.getBetType());
//					betTicket.setBetMultiple(order.getBetMultiple());
//					betTicket.setBetCode(betCode);
//					betTicket.setBetCount(betCount);
//					betTicket.setBetAmount(amount * order.getBetMultiple());
//					betTicket.setAreaCode(order.getAreaCode());
//					betTicket.setPrintStation("eHand");
//					betTicket.setTicketStatus(0);
//					String sequence = LotterySequence.getInstatce("CP").getSequence();
//					betTicket.setTicketSequence(sequence);
//					ticketes.add(betTicket);
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
//				}
//			}
//
//			// 多乐彩任选三复式中的单式
//			if(singleCodes.length() > 0){
//				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX3 single bet in duplex bet, betCode = " + betCode);
//				int len = betCode.length();
//				for(int pos = 0; pos < len; pos = pos + 35){
//					String code = betCode.substring(pos, betCode.substring(pos).length() < 34 ? pos + betCode.substring(pos).length() : pos + 34);
//					// 彩票的注数金额
//					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
//					int betCount = 0;
//					int amount = 0;
//					if(result == null || result.split("#") == null || result.split("#").length != 2){
//						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
//					} else {
//						try{
//						betCount = Integer.parseInt(result.split("#")[0]);
//						amount = Integer.parseInt(result.split("#")[1]);
//						}catch(Exception e){
//							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
//						}
//					}
//					BetTicketDomain betTicket = new BetTicketDomain();
//					betTicket.setOrderId(order.getOrderId());
//					betTicket.setLotteryId(order.getLotteryId());
//					betTicket.setBetTerm(order.getBetTerm());
//					betTicket.setPlayType(order.getPlayType());
//					betTicket.setBetType(0);
//					betTicket.setBetMultiple(order.getBetMultiple());
//					betTicket.setBetCode(code);
//					betTicket.setBetCount(betCount);
//					betTicket.setBetAmount(amount * order.getBetMultiple());
//					betTicket.setAreaCode(order.getAreaCode());
//					betTicket.setPrintStation("eHand");
//					betTicket.setTicketStatus(0);
//					String sequence = LotterySequence.getInstatce("CP").getSequence();
//					betTicket.setTicketSequence(sequence);
//					ticketes.add(betTicket);
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
//				}
//			}
//		} else if(order.getBetType() == 2){
//			// 多乐彩任选三胆拖
//			String[] betCodes = order.getBetCode().trim().split("\\^");
//			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX3 dantuo bet, betCode.split(^).size=" + betCodes.length);
//			int count = 1;
//			String singleCodes = "";
//			for(String betCode : betCodes){
//				betCode = EHandSplitTools.getSingleBetCodeInDLCRXDantuo(betCode, 3);
//				if(betCode.length() == 6){
//					singleCodes = singleCodes + betCode + "^";
//				} else {
//					// 彩票的注数金额
//					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
//					int betCount = 0;
//					int amount = 0;
//					if(result == null || result.split("#") == null || result.split("#").length != 2){
//						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
//					} else {
//						try{
//						betCount = Integer.parseInt(result.split("#")[0]);
//						amount = Integer.parseInt(result.split("#")[1]);
//						}catch(Exception e){
//							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
//						}
//					}
//					BetTicketDomain betTicket = new BetTicketDomain();
//					betTicket.setOrderId(order.getOrderId());
//					betTicket.setLotteryId(order.getLotteryId());
//					betTicket.setBetTerm(order.getBetTerm());
//					betTicket.setPlayType(order.getPlayType());
//					betTicket.setBetType(order.getBetType());
//					betTicket.setBetMultiple(order.getBetMultiple());
//					betTicket.setBetCode(betCode);
//					betTicket.setBetCount(betCount);
//					betTicket.setBetAmount(amount * order.getBetMultiple());
//					betTicket.setAreaCode(order.getAreaCode());
//					betTicket.setPrintStation("eHand");
//					betTicket.setTicketStatus(0);
//					String sequence = LotterySequence.getInstatce("CP").getSequence();
//					betTicket.setTicketSequence(sequence);
//					ticketes.add(betTicket);
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
//				}
//			}
//			// 多乐彩胆拖中的单式
//			if(singleCodes.length() > 0){
//				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX3 single bet in dantuo bet, betCode = " + betCode);
//				int len = betCode.length();
//				for(int pos = 0; pos < len; pos = pos + 35){
//					String code = betCode.substring(pos, betCode.substring(pos).length() < 34 ? pos + betCode.substring(pos).length() : pos + 34);
//					// 彩票的注数金额
//					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
//					int betCount = 0;
//					int amount = 0;
//					if(result == null || result.split("#") == null || result.split("#").length != 2){
//						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
//					} else {
//						try{
//						betCount = Integer.parseInt(result.split("#")[0]);
//						amount = Integer.parseInt(result.split("#")[1]);
//						}catch(Exception e){
//							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
//						}
//					}
//					BetTicketDomain betTicket = new BetTicketDomain();
//					betTicket.setOrderId(order.getOrderId());
//					betTicket.setLotteryId(order.getLotteryId());
//					betTicket.setBetTerm(order.getBetTerm());
//					betTicket.setPlayType(order.getPlayType());
//					betTicket.setBetType(0);
//					betTicket.setBetMultiple(order.getBetMultiple());
//					betTicket.setBetCode(code);
//					betTicket.setBetCount(betCount);
//					betTicket.setBetAmount(amount * order.getBetMultiple());
//					betTicket.setAreaCode(order.getAreaCode());
//					betTicket.setPrintStation("eHand");
//					betTicket.setTicketStatus(0);
//					String sequence = LotterySequence.getInstatce("CP").getSequence();
//					betTicket.setTicketSequence(sequence);
//					ticketes.add(betTicket);
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
//				}
//			}
//		} else {
//			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
//		}
//		return ticketes;
//	}
//
//	private List<BetTicketDomain> split1200001_4(BetOrderDomain order){
//		if(StringUtils.isBlank(order.getBetCode())){
//			throw new UnsupportedOperationException("Not supported lotteryId betCode is null");
//		}
//		List<BetTicketDomain> ticketes = new ArrayList<BetTicketDomain>();
//
//		// 多乐彩任选四单式
//		if(order.getBetType() == 0){
//			String betCode = order.getBetCode().trim();
//			int len = betCode.length();
//			int count = 1;
//			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX4 single bet, betCode length = " + len);
//			for(int pos = 0; pos < len; pos = pos + 45){
//				String code = betCode.substring(pos, betCode.substring(pos).length() < 44 ? pos + betCode.substring(pos).length() : pos + 44);
//				// 彩票的注数金额
//				String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), code);
//				int betCount = 0;
//				int amount = 0;
//				if(result == null || result.split("#") == null || result.split("#").length != 2){
//					throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
//				} else {
//					try{
//					betCount = Integer.parseInt(result.split("#")[0]);
//					amount = Integer.parseInt(result.split("#")[1]);
//					}catch(Exception e){
//						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
//					}
//				}
//				BetTicketDomain betTicket = new BetTicketDomain();
//				betTicket.setOrderId(order.getOrderId());
//				betTicket.setLotteryId(order.getLotteryId());
//				betTicket.setBetTerm(order.getBetTerm());
//				betTicket.setPlayType(order.getPlayType());
//				betTicket.setBetType(order.getBetType());
//				betTicket.setBetMultiple(order.getBetMultiple());
//				betTicket.setBetCode(code);
//				betTicket.setBetCount(betCount);
//				betTicket.setBetAmount(amount * order.getBetMultiple());
//				betTicket.setAreaCode(order.getAreaCode());
//				betTicket.setPrintStation("eHand");
//				betTicket.setTicketStatus(0);
//				String sequence = LotterySequence.getInstatce("CP").getSequence();
//				betTicket.setTicketSequence(sequence);
//				ticketes.add(betTicket);
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
//			}
//		} else if(order.getBetType() == 1){
//			// 多乐彩任选四复式
//			String[] betCodes = order.getBetCode().trim().split("\\^");
//			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX4 duplex bet, betCode.split(^).size=" + betCodes.length);
//			int count = 1;
//			String singleCodes = "";
//			for(String betCode : betCodes){
//				if(betCode.length() == 8){
//					singleCodes = singleCodes + betCode + "^";
//				} else {
//					// 彩票的注数金额
//					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
//					int betCount = 0;
//					int amount = 0;
//					if(result == null || result.split("#") == null || result.split("#").length != 2){
//						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
//					} else {
//						try{
//						betCount = Integer.parseInt(result.split("#")[0]);
//						amount = Integer.parseInt(result.split("#")[1]);
//						}catch(Exception e){
//							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
//						}
//					}
//					BetTicketDomain betTicket = new BetTicketDomain();
//					betTicket.setOrderId(order.getOrderId());
//					betTicket.setLotteryId(order.getLotteryId());
//					betTicket.setBetTerm(order.getBetTerm());
//					betTicket.setPlayType(order.getPlayType());
//					betTicket.setBetType(order.getBetType());
//					betTicket.setBetMultiple(order.getBetMultiple());
//					betTicket.setBetCode(betCode);
//					betTicket.setBetCount(betCount);
//					betTicket.setBetAmount(amount * order.getBetMultiple());
//					betTicket.setAreaCode(order.getAreaCode());
//					betTicket.setPrintStation("eHand");
//					betTicket.setTicketStatus(0);
//					String sequence = LotterySequence.getInstatce("CP").getSequence();
//					betTicket.setTicketSequence(sequence);
//					ticketes.add(betTicket);
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
//				}
//			}
//
//			// 多乐彩任选四复式中的单式
//			if(singleCodes.length() > 0){
//				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX4 single bet in duplex bet, betCode = " + betCode);
//				int len = betCode.length();
//				for(int pos = 0; pos < len; pos = pos + 45){
//					String code = betCode.substring(pos, betCode.substring(pos).length() < 44 ? pos + betCode.substring(pos).length() : pos + 44);
//					// 彩票的注数金额
//					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
//					int betCount = 0;
//					int amount = 0;
//					if(result == null || result.split("#") == null || result.split("#").length != 2){
//						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
//					} else {
//						try{
//						betCount = Integer.parseInt(result.split("#")[0]);
//						amount = Integer.parseInt(result.split("#")[1]);
//						}catch(Exception e){
//							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
//						}
//					}
//					BetTicketDomain betTicket = new BetTicketDomain();
//					betTicket.setOrderId(order.getOrderId());
//					betTicket.setLotteryId(order.getLotteryId());
//					betTicket.setBetTerm(order.getBetTerm());
//					betTicket.setPlayType(order.getPlayType());
//					betTicket.setBetType(0);
//					betTicket.setBetMultiple(order.getBetMultiple());
//					betTicket.setBetCode(code);
//					betTicket.setBetCount(betCount);
//					betTicket.setBetAmount(amount * order.getBetMultiple());
//					betTicket.setAreaCode(order.getAreaCode());
//					betTicket.setPrintStation("eHand");
//					betTicket.setTicketStatus(0);
//					String sequence = LotterySequence.getInstatce("CP").getSequence();
//					betTicket.setTicketSequence(sequence);
//					ticketes.add(betTicket);
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
//				}
//			}
//		} else if(order.getBetType() == 2){
//			// 多乐彩任选四胆拖
//			String[] betCodes = order.getBetCode().trim().split("\\^");
//			logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX4 dantuo bet, betCode.split(^).size=" + betCodes.length);
//			int count = 1;
//			String singleCodes = "";
//			for(String betCode : betCodes){
//				betCode = EHandSplitTools.getSingleBetCodeInDLCRXDantuo(betCode, 4);
//				if(betCode.length() == 8){
//					singleCodes = singleCodes + betCode + "^";
//				} else {
//					// 彩票的注数金额
//					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), order.getBetType(), betCode);
//					int betCount = 0;
//					int amount = 0;
//					if(result == null || result.split("#") == null || result.split("#").length != 2){
//						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + betCode);
//					} else {
//						try{
//						betCount = Integer.parseInt(result.split("#")[0]);
//						amount = Integer.parseInt(result.split("#")[1]);
//						}catch(Exception e){
//							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
//						}
//					}
//					BetTicketDomain betTicket = new BetTicketDomain();
//					betTicket.setOrderId(order.getOrderId());
//					betTicket.setLotteryId(order.getLotteryId());
//					betTicket.setBetTerm(order.getBetTerm());
//					betTicket.setPlayType(order.getPlayType());
//					betTicket.setBetType(order.getBetType());
//					betTicket.setBetMultiple(order.getBetMultiple());
//					betTicket.setBetCode(betCode);
//					betTicket.setBetCount(betCount);
//					betTicket.setBetAmount(amount * order.getBetMultiple());
//					betTicket.setAreaCode(order.getAreaCode());
//					betTicket.setPrintStation("eHand");
//					betTicket.setTicketStatus(0);
//					String sequence = LotterySequence.getInstatce("CP").getSequence();
//					betTicket.setTicketSequence(sequence);
//					ticketes.add(betTicket);
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
//				}
//			}
//			// 多乐彩胆拖中的单式
//			if(singleCodes.length() > 0){
//				String betCode = singleCodes.substring(0, singleCodes.length() - 1);
//				logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") is DLC-RX4 single bet in dantuo bet, betCode = " + betCode);
//				int len = betCode.length();
//				for(int pos = 0; pos < len; pos = pos + 45){
//					String code = betCode.substring(pos, betCode.substring(pos).length() < 44 ? pos + betCode.substring(pos).length() : pos + 44);
//					// 彩票的注数金额
//					String result = LotteryTools.lotteryBetToSingle(order.getLotteryId(), order.getPlayType(), 0, code);
//					int betCount = 0;
//					int amount = 0;
//					if(result == null || result.split("#") == null || result.split("#").length != 2){
//						throw new UnsupportedOperationException("Not supported get lotteryBetToSingle, code = " + code);
//					} else {
//						try{
//						betCount = Integer.parseInt(result.split("#")[0]);
//						amount = Integer.parseInt(result.split("#")[1]);
//						}catch(Exception e){
//							throw new UnsupportedOperationException("Not supported get lotteryBetToSingle to parseInt, result = " + result);
//						}
//					}
//					BetTicketDomain betTicket = new BetTicketDomain();
//					betTicket.setOrderId(order.getOrderId());
//					betTicket.setLotteryId(order.getLotteryId());
//					betTicket.setBetTerm(order.getBetTerm());
//					betTicket.setPlayType(order.getPlayType());
//					betTicket.setBetType(0);
//					betTicket.setBetMultiple(order.getBetMultiple());
//					betTicket.setBetCode(code);
//					betTicket.setBetCount(betCount);
//					betTicket.setBetAmount(amount * order.getBetMultiple());
//					betTicket.setAreaCode(order.getAreaCode());
//					betTicket.setPrintStation("eHand");
//					betTicket.setTicketStatus(0);
//					String sequence = LotterySequence.getInstatce("CP").getSequence();
//					betTicket.setTicketSequence(sequence);
//					ticketes.add(betTicket);
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to betTicket " + count++ + " is:");
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketSequence = " + betTicket.getTicketSequence());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s orderId        = " + betTicket.getOrderId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s lotteryId      = " + betTicket.getLotteryId());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betTerm        = " + betTicket.getBetTerm());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s playType       = " + betTicket.getPlayType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betType        = " + betTicket.getBetType());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betMultiple    = " + betTicket.getBetMultiple());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCode        = " + betTicket.getBetCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betCount       = " + betTicket.getBetCount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s betAmount      = " + betTicket.getBetAmount());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s areaCode       = " + betTicket.getAreaCode());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s printStation   = " + betTicket.getPrintStation());
//					logger.debug("EHandOrderSplitter split the order(" + order.getOrderId() + ") to ticket(" + sequence + ")'s ticketStatus   = " + betTicket.getTicketStatus());
//				}
//			}
//		} else {
//			throw new UnsupportedOperationException("Not supported betType " + order.getBetType() + ".");
//		}
//		return ticketes;
//	}

	public BetOrderDaoImpl getBetOrderDao() {
		return betOrderDao;
	}

	public void setBetOrderDao(BetOrderDaoImpl betOrderDao) {
		this.betOrderDao = betOrderDao;
	}

	public BetTicketDaoImpl getBetTicketDao() {
		return betTicketDao;
	}

	public void setBetTicketDao(BetTicketDaoImpl betTicketDao) {
		this.betTicketDao = betTicketDao;
	}

	public static void main(String[] args){

		int lotteryId, playType, betType, betMultiple;
		String betCode;
		betMultiple = 1;

		//大乐透单式
		lotteryId = 1000001;
		playType = 0;
		betType = 0;
		betCode = "0102030405|0607^0809101112|0809^0809101112|0809^0809101112|0809^0809101112|0809^0304101112|0809";
		betCode = "0102030405|0607^0809101112|0809^0809101112|0809^0809101112|0809^0809101112|0809";
		betCode = "0102030405|0607^0809101112|0809";
		betCode = "0102030405|0607";


		//大乐透复式
		lotteryId = 1000001;
		playType = 0;
		betType = 1;
		betCode = "0102030405|0607^010203040506|060708^08091011121314|080911^0102030405|0607^0102030405|0607^080910111214|0809^08071309101112|0809^0102030405|0607^0102030405|0607^0809101112|08091011^03040506101112|080709^0102030405|0607^0102030405|0607^0102030405|0607";
		betCode = "010203040506|060708";
		betCode = "010203040506|060708^0102030405|0607";
		betCode = "010203040506|060708^010203040506|060708^010203040506|060708";
		betCode = "0102030405|0607^0809101112|0809^0809101112|0809^0809101112|0809^0809101112|0809^0304101112|0809";

		//大乐透胆拖
		lotteryId = 1000001;
		playType = 1;
		betType = 2;
		betCode = "01*02030405|06*07^01*02030405|*0607^01*02030405|*0607^01*02030405|*0607^01*02030405|*0607^01*02030405|*0607^01*02030405|*0607^01*02030405|*0607^01*02030405|*0607^01*02030405|*0607";
		betCode = "0102*03040506|*060708^01*02030405|*0607^01*02030405|*0607";
		betCode = "1112*03040506|11*0708^0102*03040506|*060708^01*02030405|*0607^01*02030405|*0607";

		//七星彩单式
		lotteryId = 1000002;
		playType = 0;
		betType = 0;
		betCode = "1*2*3*3*3*3*3^2*3*4*4*4*4*4^1*2*3*3*3*3*3^2*3*4*4*4*4*4^1*2*3*3*3*3*3^2*3*4*4*4*4*4^1*2*3*3*3*3*3^2*3*4*4*4*4*4^1*2*3*3*3*3*3^2*3*4*4*4*4*4";
		betCode = "1*2*3*3*3*3*3^2*3*4*4*4*4*4^1*2*3*3*3*3*3^2*3*4*4*4*4*4^1*2*3*3*3*3*3^2*3*4*4*4*4*4";
		betCode = "1*2*3*3*3*3*3";
		betCode = "1*2*3*3*3*3*3^2*3*4*4*4*4*4^1*2*3*3*3*3*3^2*3*4*4*4*4*4^1*2*3*3*3*3*3";

		//七星彩复式
		lotteryId = 1000002;
		playType = 0;
		betType = 1;
		betCode = "1*2*345*3*3*3*3^2*3*4*4*4*4*4^1*2*3*3*3*3*3^2*3*4*4*478*4*4^1*2*3*3*3*3*3^2*3*4*4*4*4*4^1*2*3*3*3*3*3^2*3*4*4*4*4*4^1*2*3*3*3*3*3^2*3*4*4*4*4*4";
		betCode = "1*2*345*356*3*3*3^2*32*478*4*46*4*4^14*23*37*38*3*3*3^26*3*49*4*4*4*4^1*2*3*3*3*3*3^2*3*4*4*4*4*4";
		betCode = "1*2*34*356*3456*3*3";

		//排列三直选单式
		lotteryId = 1000003;
		playType = 0;
		betType = 0;
		betCode = "1*2*3^2*3*4^1*2*3^4*4*4^1*2*3^2*3*4^1*2*3^3*4*4^2*3*3^2*3*4";
		betCode = "1*2*3^2*3*4^1*2*3^4*4*4^1*2*3^2*3*4^1*2*3^3*4*4^2*3*3";
		betCode = "1*2*3";
		betCode = "1*2*3^2*3*4^1*2*3^4*4*4^1*2*3";
		betCode = "1*2*3^2*3*4^1*2*3^4*4*4";


		//排列三直选复式
		lotteryId = 1000003;
		playType = 0;
		betType = 1;
		betCode = "1*2*3^2*3*4^1*2*3^4*4*4^1*2*3^2*3*4^1*2*3^3*4*4^2*3*3^2*3*4^2*3*4";
		betCode = "1*23*34^25*3*4^1*25*36^45*46*47^1*2*3^24*36*4^1*26*3^3*4*4^2*13*123^23*356*4^2*3*4";
		betCode = "123*234*35^123*78*9";

		//排列三直选和值
		lotteryId = 1000003;
		playType = 0;
		betType = 2;
		betCode = "1^2^3^4^1^2^3^4";
		betCode = "1^2^3^4^5^6^7^8";
		betCode = "2^2^2^2^2^6^7^8";
		betCode = "6";

		//排列三组选单式
		lotteryId = 1000003;
		playType = 0;
		betType = 3;
		betCode = "1*2*3^2*3*4^1*2*3^4*4*4^1*2*3^2*3*4^1*2*3^3*4*4^2*3*3^2*3*4";
		betCode = "1*2*3^2*3*4^1*2*3^4*4*4^1*2*3^2*3*4^1*2*3^3*4*4^2*3*3";
		betCode = "1*2*3";
		betCode = "1*2*3^2*3*4^1*2*3^4*4*4^1*2*3";
		betCode = "1*2*3^2*3*4^1*2*3^4*4*4";

		//排列三组选六复式
		lotteryId = 1000003;
		playType = 0;
		betType = 4;
		betCode = "1*2*3^2*3*4^1*2*3^4*5*6^1*2*3^2*3*4^1*2*3^3*4*7^2*3*8^2*3*4^2*3*9";
		betCode = "1*2*3*4^2*5*3*4^1*2*5*3*6^4*5*6*7^1*2*3^2*4*3*6^1*2*6*3^3*4*5^2*1*3*7^2*3*5*6*4^2*3*4";
		betCode = "1*2*3*4*5^1*2*3*7*8*9";
		betCode = "1*2*3*4*5";
		betCode = "1*2*3";

		//排列三组选三复式
		lotteryId = 1000003;
		playType = 0;
		betType = 5;
		betCode = "1*2*3^2*3*4^1*2*3^4*5*6^1*2*3^2*3*4^1*2*3^3*4*7^2*3*8^2*3*4^2*3*9";
		betCode = "1*2^2*5*3*4^5*3^4*5*6*7^1*2*3^2*4*3*6^1*2*6*3^3*4*5^2*1*3*7^2*3*5*6*4^2*3*4";
		betCode = "1*2^1*2*3";
		betCode = "1*2*3";
		betCode = "1*2";


		//排列三组选和值
		lotteryId = 1000003;
		playType = 0;
		betType = 6;
		betCode = "1^2^3^4^1^2^3^4";
		betCode = "1^2^3^4^5^6^7^8";
		betCode = "2^2^2^2^2^6^7^8";
		betCode = "6";

		//排列五单式
		lotteryId = 1000004;
		playType = 0;
		betType = 0;
		betCode = "1*2*3*3*3^2*3*4*4*4^1*2*3*3*3^2*3*4*4*4^1*2*3*3*3^2*3*4*4*4^1*2*3*3*3^2*3*4*4*4^1*2*3*3*3^2*3*4*4*4";
		betCode = "1*2*3*3*3^2*3*4*4*4^1*2*3*3*3^2*3*4*4*4^1*2*3*3*3^2*3*4*4*4";
		betCode = "1*2*3*3*3";
		betCode = "1*2*3*3*3^2*3*4*4*4^1*2*3*3*3^2*3*4*4*4^1*2*3*3*3";

		//排列五复式
		lotteryId = 1000004;
		playType = 0;
		betType = 1;
		betCode = "1*2*345*3*3^2*3*4*4*4^1*2*3*3*3^2*3*4*4*478^1*2*3*3*3^2*3*4*4*4^1*2*3*3*3^2*3*4*4*4^1*2*3*3*3^2*3*4*4*4";
		betCode = "1*2*345*356*3^2*32*478*4*46^14*23*37*38*3^26*3*49*4*4^1*2*3*3*3^2*3*4*4*4";
		betCode = "1*2*34*356*3456";

		//生肖乐单式
		lotteryId = 1000005;
		playType = 0;
		betType = 0;
		betCode = "0102^0203^0203^0203^0203^0203^0203^0203^0203^0203";
		betCode = "0102^0203^0203^0203^0203^0203^0203^0203^0203^0203^0809";
		betCode = "0102^0203^0203^0203^0203^0203^0203^0203^0809";
		betCode = "0102^0203^0203^0203^0203";
		betCode = "0102";


		//生肖乐复式
		lotteryId = 1000005;
		playType = 0;
		betType = 1;
		betCode = "0102^0203^0203^0203^0203^0203^0203^0203^0203^0203";
		betCode = "010203^020304^02030506^0203^02030809^02031112^0203^0203^02030809^02030506^0809";
		betCode = "01020304^02030708^0203";
		betCode = "01020304";
		
		//足球彩胜负彩单式
		lotteryId = 1300001;
		playType = 0;
		betType = 0;
		betCode = "3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3";
		betCode = "3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3";
		betCode = "3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3";
		betCode = "3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3";
		betCode = "3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3";
		betCode = "3*1*1*3*3*1*0*3*3*3*3*0*1*3";

		//足球彩胜负彩复式
		lotteryId = 1300001;
		playType = 0;
		betType = 1;
		betCode = "3*013*1*3*3*1*0*3*3*3*3*0*1*3^3*1*13*03*3*1*0*3*3*3*3*0*1*3^3*1*1*3*03*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*013^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*13*13*3*1*0*3*3*3*3*0*1*3^3*1*013*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*013*1*3";
		betCode = "3*1*13*3*3*1*0*3*3*3*3*0*1*3^3*13*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*1*0*3*3*3*3*0*1*3^3*1*1*3*3*13*0*3*3*3*3*0*1*3";
		betCode = "3*013*13*03*3*1*0*3*3*3*3*0*1*3^013*1*13*3*3*1*01*3*03*3*3*0*1*3";
		betCode = "3*013*1*3*13*13*013*3*3*3*3*0*1*13";

		//足球彩半全场单式
		lotteryId = 1300003;
		playType = 0;
		betType = 0;
		betCode = "3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0";
		betCode = "3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0^3*1*1*3*3*1*0*3*3*3*3*0";
		betCode = "3*1*1*3*3*1*0*3*3*3*3*0";

		//足球彩半全场复式
		lotteryId = 1300003;
		playType = 0;
		betType = 1;
		betCode = "3*013*1*3*3*1*0*3*3*3*3*0^13*03*3*1*0*3*3*3*3*0*1*3^1*3*03*1*0*3*3*3*3*0*1*3^1*3*3*1*0*3*3*3*3*0*1*3^1*3*3*1*0*3*3*3*3*0*1*013^1*3*3*1*0*3*3*3*3*0*1*3^13*13*3*1*0*3*3*3*3*0*1*3^013*3*3*1*0*3*3*3*3*0*1*3^1*3*3*1*0*3*3*3*3*0*1*3^1*3*3*1*0*3*3*3*3*013*1*3";

		//足球彩进球彩单式
		lotteryId = 1300004;
		playType = 0;
		betType = 0;
		betCode = "3*1*1*3*3*1*0*3^3*1*0*3*3*3*3*0^3*1*0*3*3*3*3*0^3*1*0*3*3*3*3*0^3*1*0*3*3*3*3*0^3*1*0*3*3*3*3*0^3*1*0*3*3*3*3*0^3*1*0*3*3*3*3*0^3*1*0*3*3*3*3*0^3*1*0*3*3*3*3*0";
		betCode = "3*1*1*3*3*1*0*3^3*1*1*3*3*1*0*3^3*1*1*3*3*1*0*3^3*1*1*3*3*1*0*3^3*1*1*3*3*1*0*3^3*1*1*3*3*1*0*3^3*1*1*3*3*1*0*3^3*1*1*3*3*1*0*3^3*1*1*3*3*1*0*3^3*1*1*3*3*1*0*3^3*1*1*3*3*1*0*3";
		betCode = "3*1*1*3*3*1*0*3";

		//足球彩进球彩复式
		lotteryId = 1300004;
		playType = 0;
		betType = 1;
		betCode = "3*013*1*1*0*3*3*3^13*03*3*1*0*3*3*0^1*3*03*1*3*3*3*0^1*3*0*3*3*3*3*0^3*1*0*3*3*0*1*013^3*1*0*3*3*0*1*3^13*13*3*3*3*0*1*3^013*3*3*3*3*0*1*3^0*3*3*3*3*0*1*3^0*3*3*3*3*013*1*3";

		//多乐彩任选一单式
		lotteryId = 1200001;
		playType = 1;
		betType = 0;
		betMultiple=1;
		betCode = "01^02^03^04^05^06^07^08^09^10";
		betCode = "01^02^03^04^05^06^07^08^09^10^11";
		betCode = "01";
		betCode = "01^02^03^04^05";

		//多乐彩任选二单式
		playType = 2;
		betType = 0;
		betCode = "0102^0203^0304^0405^0506^0607^0708^0809^0910^1011";
		//betCode = "0102^0203^0304^0405^0506^0607^0708^0809^0910^1011^0111";
		//betCode = "0102";
		//betCode = "0102^0203^0304^0405^0506";

		//多乐彩任选二复式
		playType = 2;
		betType = 1;
		betCode = "010203^020304^0304050607^04050809^050601^06070910^0708^0809^0910^1011";
		betCode = "0102030405^0203^0304^0405^0506^0607";
		betCode = "0102040506";

		//多乐彩任选二胆拖
		playType = 2;
		betType = 2;
		betCode = "01*020304^02*0304^03*04050607^04*050809^05*0601^06*070910^07*08^08*09^09*10^10*11";
		betCode = "01*02030405^02*03";
		betCode = "01*02";
		betCode = "01*02^03*04";

		//多乐彩任选三单式
		playType = 3;
		betType = 0;
		betCode = "010203^020304^030405^040506^050607^060708^070809^080910^091011^031011";
		betCode = "010203^020304^030405^040506^050607^060708^070809^080910^091011^021011^010311";
		betCode = "010203";
		betCode = "010203^020304^030405^040506^050607";

		//多乐彩任选三复式
		playType = 3;
		betType = 1;
		betCode = "01020304^02030405^03040506^040506^050607^060708^070809^080910^091011^031011";
		betCode = "0102030405";
		betCode = "010203^020304^030405^040506^050607";

		//多乐彩直选二单式
		playType = 9;
		betType = 0;
		betCode = "0102^0203^0304^0405^0506^0607^0708^0809^0910^0310^0411";
		betCode = "0102";
		betCode = "0102^0203^0304^0405^0506";

		//多乐彩直选三单式
		playType = 10;
		betType = 0;
		betCode = "010203^020304^030405^040506^050607^060708^070809^080910^091011^030410^040811";
		betCode = "010203";
		betCode = "010203^020304^030405^040506^050607";

		//多乐彩直选二定位
		playType = 9;
		betType = 3;
		betCode = "010203*020304^02*03^0304*05^04*05^05*06^0607*0607^0708*091011^0809*09^0911*10^03*10^04*11";
		betCode = "010203*010203";
		betCode = "01*02";

		//多乐彩直选三定位
		playType = 10;
		betType = 3;
		betCode = "010203*020304*020304^02*03*04^0304*05*06^04*05*06^05*06*07^0607*0607*0608^0708*091011*091011^0809*09*10^0911*10*11^03*10*11^04*10*11";
		betCode = "010203*010203*010203";
		betCode = "01*02*03";

		//多乐彩组选二单式
		playType = 11;
		betType = 0;
		betCode = "0102^0203^0304^0405^0506^0607^0708^0809^0910^0310^0411";
		betCode = "0102";
		betCode = "0102^0203^0304^0405^0506";

		//多乐彩组选三单式
		playType = 12;
		betType = 0;
		betCode = "010203^020304^030405^040506^050607^060708^070809^080910^091011^030410^040811";
		betCode = "010203";
		betCode = "010203^020304^030405^040506^050607";

		//多乐彩组选二复式
		playType = 11;
		betType = 1;
		betCode = "010203^020304^0304050607^04050809^050601^06070910^0708^0809^0910^1011";
		betCode = "0102030405^0203^0304^0405^0506^0607";
		betCode = "0102040506";

		//多乐彩组选三复式
		playType = 12;
		betType = 1;
		betCode = "01020304^02030405^03040506^040506^050607^060708^070809^080910^091011^031011";
		betCode = "0102030405";
		betCode = "010203^020304^030405^040506^050607";


		//多乐彩组选二胆拖
		playType = 11;
		betType = 2;
		betCode = "01*020304^02*03^03*0506^04*05^05*06^07*0608^07*091011^08*09^09*1011^03*10^04*11";
		betCode = "01*02030405";
		betCode = "01*02";

		//多乐彩组选三胆拖
		playType = 12;
		betType = 2;
		betCode = "0102*0304^0304*05^0304*0506^04*0506^0506*07^0607*0108^0708*091011^0809*10^0911*0510^03*1011^0410*11";
		betCode = "0102*030405";
		betCode = "0102*03";
		betCode = "01*0203";

		BetOrderDomain order = new BetOrderDomain();
		order.setOrderId("DD100000000000000001");
		order.setPlanId("FA100000000000000001");
		order.setAreaCode("31");
		order.setBetTerm("10081");
		order.setBetMultiple(betMultiple);
		order.setLotteryId(lotteryId);
		order.setPlayType(playType);
		order.setBetType(betType);
		order.setBetCode(betCode);

		EHandOrderSplitter splitter = new EHandOrderSplitter();
		List<BetTicketDomain> ticketes = splitter.splitOrder(order);
	}
}
