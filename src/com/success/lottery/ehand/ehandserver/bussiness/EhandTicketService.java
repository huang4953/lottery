/**
 * Title: EhandTicketService.java
 * @Package com.success.lottery.ehand.ehandserver.bussiness
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-2-9 下午06:02:03
 * @version V1.0
 */
package com.success.lottery.ehand.ehandserver.bussiness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.service.interf.BetTicketServiceInterf;
import com.success.protocol.ticket.zzy.CommonBetQs50207;
import com.success.protocol.ticket.zzy.DlcBetQs50201;
import com.success.protocol.ticket.zzy.EhandDataPack;
import com.success.protocol.ticket.zzy.TicketNoticeQs50204;
import com.success.protocol.ticket.zzy.TicketQueryQs50205;
import com.success.protocol.ticket.zzy.model.CommonTicketBet;
import com.success.protocol.ticket.zzy.model.TicketBet;
import com.success.protocol.ticket.zzy.model.TicketBetResult;
import com.success.protocol.ticket.zzy.model.TicketQueryResult;
import com.success.protocol.ticket.zzy.util.EhandUtil;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.ehand.ehandserver.bussiness
 * EhandTicketService.java
 * EhandTicketService
 * (与票相关的掌中奕接口)
 * @author gaoboqin
 * 2011-2-9 下午06:02:03
 * 
 */

public class EhandTicketService {
	private static Log logger = LogFactory.getLog(EhandTicketService.class);
	
	private static EhandLog ticketLog = EhandLog.getInstance("E_SENDTICKET");//记录发送的和返回的票信息
	
	/**
	 * 
	 * Title: printOneTicket<br>
	 * Description: <br>
	 *              <br>掌中奕出票
	 *              <br>只做了数据交换，没有做数据库的更新操作
	 *              <br>
	 * @param betTicket
	 *                 <br>BetTicketDomain对象必须提供的属性
	 *                 彩种id，彩期，玩法，投注方式，投注系统票Sequence，投注串，倍数，注数，投注金额
	 *                 
	 * @return 
	 *        <br>返回TicketBetResult对象，属性为
	 *        errorCode：错误代码
	 *        opreateId：交易流水
	 *        ticketSequence：票Sequence
	 *        accountValue：账户余额，分
	 * @throws LotteryException
	 *                         <br>EhandUtil.E_01_CODE,发送请求通讯错误
	 *                         <br>EhandUtil.E_02_CODE,发送请求通讯返回但状态码不正确
	 *                         <br>EhandUtil.E_03_CODE,发送请求通讯正确返回但消息处理错误
	 *                         <br>EhandUtil.E_04_CODE,请求参数为空
	 */
	public static TicketBetResult printOneTicket(BetTicketDomain betTicket) throws LotteryException{
		TicketBetResult printResult = null;
		
		if(betTicket == null){
			throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
		}
		
		ticketLog.sendTicketLog(betTicket.getLotteryId(), betTicket
				.getBetTerm(), betTicket.getTicketSequence(), betTicket
				.getPlayType(), betTicket.getBetType(), betTicket
				.getBetMultiple(), betTicket.getBetCount(), betTicket
				.getBetAmount(), betTicket.getBetCode());
		
		List<BetTicketDomain> sendData = new ArrayList<BetTicketDomain>();
		sendData.add(betTicket);
		List<TicketBetResult> reponseData = printTickets(sendData);
		
		printResult = reponseData.get(0);
		
		if(printResult == null){
			throw new LotteryException(EhandUtil.E_03_CODE,EhandUtil.E_03_DESC);
		}
		
		ticketLog.receiveTicketLog(printResult.getErrorCode(), printResult
				.getTicketSequence(), printResult.getOpreateId(), printResult
				.getAccountValue());
		
		return printResult;
		
	}
	/**
	 * 
	 * Title: QueryPrintTicket<br>
	 * Description: <br>
	 *              <br>查询单个票的出票情况
	 *              <br>只做了数据交换，没有做数据库的更新操作
	 * @param betTicketSequence
	 * @return
	 * @throws LotteryException
	 *                         <br>EhandUtil.E_01_CODE,发送请求通讯错误
	 *                         <br>EhandUtil.E_02_CODE,发送请求通讯返回但状态码不正确
	 *                         <br>EhandUtil.E_03_CODE,发送请求通讯正确返回但消息处理错误
	 *                         <br>EhandUtil.E_04_CODE,请求参数为空
	 */
	public static TicketQueryResult QueryPrintTicket(String betTicketSequence) throws LotteryException{
		Map<String,String> logParam = null;
		TicketQueryResult printResult = null;
		
		if(StringUtils.isEmpty(betTicketSequence)){
			throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
		}
		logger.debug("掌中奕发送出票情况查询,查询票流水:"+betTicketSequence);
		ticketLog.querySendTicketLog(betTicketSequence);
		try{
			logParam = getLogParam(EhandUtil.commd_50205);
			
			List<String> sendData = new ArrayList<String>();
			sendData.add(betTicketSequence);
			
			EhandDataPack responseTicketInfo = new TicketQueryQs50205(sendData).writePack();
			List<TicketQueryResult> reponseData = (responseTicketInfo == null) ? null : ((TicketQueryQs50205)responseTicketInfo).getBetResultList();
			
			if(reponseData == null || reponseData.isEmpty()){
				throw new LotteryException(EhandUtil.E_03_CODE,EhandUtil.E_03_DESC);
			}
			
			printResult = reponseData.get(0);
			
			if(printResult == null){
				throw new LotteryException(EhandUtil.E_03_CODE,EhandUtil.E_03_DESC);
			}
			
			logger.debug("掌中奕发送出票情况查询,查询票流水:"+betTicketSequence+"返回票信息:"+printResult.toString());
			ticketLog.queryReceiveTicketLog(printResult.getErrorCode(), printResult.getTicketSequence(), printResult.getTicketId(),printResult.getPrintStatus());
			
		}catch(LotteryException e){
			logger.error("50205,掌中奕查询出票情况出错:", e);
			logParam.put("keyword1", betTicketSequence);
			logParam.put("keyword2", e.getMessage());
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕查询出票情况发生异常", logParam);
			
			throw e;
		}catch(Exception ex){
			logger.error("50205,掌中奕查询出票情况出错:", ex);
			logParam.put("keyword1", betTicketSequence);
			logParam.put("keyword2", "");
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕查询出票情况发生异常", logParam);
			
			throw new LotteryException(EhandUtil.E_03_CODE,EhandUtil.E_03_DESC);
		}
		return printResult;
	}
	
	/**
	 * 
	 * Title: printTickets<br>
	 * Description: <br>
	 *              <br>送票到掌中奕接口并返回响应的结果
	 * @param betTickets
	 * @return
	 * @throws LotteryException
	 *               <br>EhandUtil.E_01_CODE,发送请求通讯错误
	 *               <br>EhandUtil.E_02_CODE,发送请求通讯返回但状态码不正确
	 *               <br>EhandUtil.E_03_CODE,发送请求通讯正确返回但消息处理错误
	 *               <br>EhandUtil.E_04_CODE,请求参数为空
	 */
	public static List<TicketBetResult> printTickets(List<BetTicketDomain> betTickets) throws LotteryException{
		List<TicketBetResult> sendResult = null;
		Map<String,String> logParam = null;
		try{
			logParam = getLogParam("");
			if(betTickets == null || betTickets.isEmpty()){
				logger.info("提交出票请求参数为空!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//根据提交的票的彩种将票分为高频和非高频两个集合
			List<TicketBet> gaoPintTickets = new ArrayList<TicketBet>();
			List<CommonTicketBet> commonPrintTickets = new ArrayList<CommonTicketBet>();
			for(BetTicketDomain oneticket : betTickets){
				Integer betlotteryId =  oneticket.getLotteryId();
				String ehandLotteryId = EhandUtil.lotteryToZzy(betlotteryId);
				if(LotteryStaticDefine.LOTTERY_1200001 == betlotteryId){
					TicketBet oneGaopinTicket = new TicketBet(ehandLotteryId,oneticket.getBetTerm(),String.valueOf(oneticket.getPlayType()),
							String.valueOf(oneticket.getBetType()),oneticket.getTicketSequence(),oneticket.getBetCode(),
							String.valueOf(oneticket.getBetMultiple()),String.valueOf(oneticket.getBetCount()),String.valueOf(oneticket.getBetAmount()));
					gaoPintTickets.add(oneGaopinTicket);
				}else{
					CommonTicketBet oneCommonTicket = new CommonTicketBet(ehandLotteryId,oneticket.getBetTerm(),String.valueOf(oneticket.getPlayType()),
							String.valueOf(oneticket.getBetType()),oneticket.getTicketSequence(),oneticket.getBetCode(),
							String.valueOf(oneticket.getBetMultiple()),String.valueOf(oneticket.getBetCount()),String.valueOf(oneticket.getBetAmount()));
					commonPrintTickets.add(oneCommonTicket);
				}
			}
			
			//对不为空的票集合送到出票系统,优先送高频
			if(gaoPintTickets != null && !gaoPintTickets.isEmpty()){//高频
				sendResult = sendGaoPinTicket(gaoPintTickets);
			}
			
			if(commonPrintTickets != null && !commonPrintTickets.isEmpty()){//非高频
				if(sendResult == null){
					sendResult = sendCommonTicket(commonPrintTickets);
				}else{
					sendResult.addAll(sendCommonTicket(commonPrintTickets));
				}
			}
			
			if(sendResult == null || sendResult.isEmpty()){
				for(BetTicketDomain oneticket : betTickets){
					int betLotteryId = oneticket.getLotteryId();
					String betTerm = oneticket.getBetTerm();
					String betSequences = oneticket.getTicketSequence();
					logParam.put("keyword1", String.valueOf(betLotteryId)+"|"+betTerm);
					logParam.put("keyword2", betSequences);
					OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕送票发生异常,返回票结果为空", logParam);
				}
				
				throw new LotteryException(EhandUtil.E_03_CODE,EhandUtil.E_03_DESC);
			}else{
//				for(TicketBetResult onePrintResult : sendResult){
//					if(!"0".equals(onePrintResult.getHeadErrorCode()) || !"0".equals(onePrintResult.getErrorCode())){
//						logParam.put("keyword1", onePrintResult.getTicketSequence());
//						logParam.put("keyword2", onePrintResult.getHeadErrorCode() + "|" + onePrintResult.getErrorCode());
//						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕送票返回结果状态码出错", logParam);
//					}
//				}
				
				
			}
			
//			logParam.put("keyword2", "");
//			OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT,
//					OperatorLogger.PROCESSOR, "掌中奕送票成功,提交票数:" + betTickets.size()
//							+ "响应票数:" + (sendResult == null ? 0 : sendResult.size()),logParam);
			
		}catch(LotteryException ex){
			throw ex;
		}catch(Exception e){
			logger.error("送票程序出错:", e);
			logParam.put("keyword2", "");
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕送票发生异常", logParam);
			
			throw new LotteryException(EhandUtil.E_03_CODE,EhandUtil.E_03_DESC);
		}
		return sendResult;
	}
	
	/**
	 * 
	 * Title: QueryPrintTicket50205<br>
	 * Description: <br>
	 *              <br>出票状态查询
	 *              <br>将响应结果更新了对应的数据库
	 *              <br>错误代码,如果执行错误 > 0
	 *              <br>查询数据中出票状态为:0,等待出票；1，出票成功；其他值为错误,对应投注系统票状态：0-4(打票请求成功等待出票结果,不更新数据库)，1-6(出票成功)，其他对应7(出票失败)
	 * @param betTicketSequence
	 * @return 
	 * @exception LotteryException
	 * 
	 */
	public static List<TicketQueryResult> QueryPrintTicket50205(List<String> betTicketSequence) throws LotteryException{
		boolean isNeedReSend = false;//是否需要重新发送,true表示需要重新发送
		List<TicketQueryResult> queryResult = null;//请求返回的响应数据
		Map<String,String> logParam = null;
		try{
			logParam = getLogParam(EhandUtil.commd_50205);
			
			if(betTicketSequence == null || betTicketSequence.isEmpty()){
				logger.info("出票状态查询请求参数为空!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//发送请求并得到响应结果
			for (int i = 0; i < EhandUtil.reSendNum; i++) {
				// 发送请求并得到请求的返回数据
				EhandDataPack responseTicketInfo = new TicketQueryQs50205(betTicketSequence).writePack();
				queryResult = (responseTicketInfo == null) ? null : ((TicketQueryQs50205)responseTicketInfo).getBetResultList();
				
				// 校验请求的结果，并决定是否重新发送请求
				if (queryResult != null && !queryResult.isEmpty()) {// 得到了响应数据
					
					for (TicketQueryResult oneTicket : queryResult) {//该部分是否需要还要确定
						String errCode = oneTicket.getErrorCode();
						if (!"0".equals(errCode)) {// 消息的返回码不正确，0代表正确，其他的代表不正确
							logger.error("出票状态查询返回错误代码:" + oneTicket.toString());
							// 根据返回的错误码决定是否重新发送请求

							isNeedReSend = true;
							break;
						}
					}
					
				} else {// 没有得到响应数据
					isNeedReSend = true;
				}

				// 如果得到的响应没有通过校验，则重新发送请求
				if (isNeedReSend) {// 需要重新发送
					logger.info("出票状态情况查询重发请求，第" + (i+1) + "次");
					Thread.sleep(EhandUtil.reSendSleep);// 间隔5秒重新发送
				} else {
					break;
				}
			}
			
			//正确的得到了响应数据，将响应结果更新票表
			if (queryResult != null && !queryResult.isEmpty()) {
				int requestNum = betTicketSequence.size();//请求的总条数
				int reponseNum = queryResult.size();//响应的总条数
				int failTicket = 0;//失败处理的票数，包括响应数据中错误吗>0的和更新数据库表出错的记录数
				for (TicketQueryResult printTicket : queryResult) {
					logger.debug("查询[50205]返回票" + printTicket.toString());
					
					try{
						int upResult = -1;
						
						if(Integer.parseInt(printTicket.getErrorCode()) <=0){//返回的错误码表示该条数据正确
							upResult = upBetTicketTable(printTicket.getTicketSequence(),printTicket.getTicketId(),printTicket.getPrintStatus());
						}else{
							upResult = -1;
							logParam.put("keyword2", printTicket.getErrorCode());
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕出票状态查询返回错误的状态码", logParam);
						}
						
						if(upResult <= 0){//没有更新成功
							failTicket++;
							logParam.put("keyword2", printTicket.getTicketSequence());
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕出票状态通知更新ticket表失败", logParam);
						}
						
					}catch(LotteryException e){//更新彩票表出现异常
						failTicket++;
						logParam.put("keyword2", printTicket.getTicketSequence());
						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕出票状态通知更新ticket表失败", logParam);
					}
					
					//全部处理完成后写一条系统日志
					logParam.put("keyword2", "查询条数:"+requestNum+"响应条数:"+reponseNum+"失败条数:"+failTicket);
					OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕出票状态查询更新ticket表成功", logParam);
				}
			}
		}catch(Exception e){
			logger.error("50205,出票状态情况查询出错:", e);
			
			logParam.put("keyword2", "");
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕出票状态情况查询发生异常", logParam);
		}
		return queryResult;
	}
	
	/**
	 * 
	 * Title: notice50204<br>
	 * Description: <br>
	 *              <br>出票状态的通知接口，只有非高频的有通知
	 *              <br>通知数据中出票状态为:0,等待出票；1，出票成功；其他值为错误,对应投注系统票状态：0-4(打票请求成功等待出票结果)，1-6(出票成功)，其他对应7(出票失败)
	 *              <br>
	 * @param dataPack
	 * @return
	 */
	public static EhandDataPack notice50204(EhandDataPack dataPack){
		EhandDataPack ticketNoticesResponse = null;
		String dealResult = "0";//默认接受到的通知处理成功
		Map<String,String> logParam = null;
		try{
			logParam = getLogParam(EhandUtil.commd_50204);
			
			TicketNoticeQs50204 ticketNotis = (TicketNoticeQs50204) dataPack;
			ticketNoticesResponse = new TicketNoticeQs50204(ticketNotis.getCommand(),ticketNotis.getUserid());//响应消息
			
			List<TicketQueryResult> betResultList = ticketNotis.getBetResultList();
			
			if(betResultList != null){//收到的数据不为空
				
				int totalTicket = betResultList.size();//收到通知的总条数
				int failUpTicket = 0;//更新票表失败的记录条数
				
				for(TicketQueryResult printTicket : betResultList){
					logger.debug("通知返回票"+printTicket.toString());
					ticketLog.noticeTicketLog(printTicket.getTicketSequence(), printTicket.getTicketId(), printTicket.getPrintStatus());
					try{
						if(!"0".equals(printTicket.getPrintStatus()) && !"1".equals(printTicket.getPrintStatus())){
							logParam.put("keyword1", printTicket.getPrintStatus());
							logParam.put("keyword2", printTicket.getTicketSequence());
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕出票状态通知收到出票状态为错误", logParam);
						}
						
						int upResult = upBetTicketTable(printTicket.getTicketSequence(),printTicket.getTicketId(),printTicket.getPrintStatus());
						
						if(upResult <= 0){//没有更新成功
							failUpTicket++;
							logParam.put("keyword1", printTicket.getPrintStatus());
							logParam.put("keyword2", printTicket.getTicketSequence());
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕出票状态通知更新ticket表失败", logParam);
						}
						
					}catch(LotteryException e){//更新彩票表出现异常
						failUpTicket++;
						logParam.put("keyword1", printTicket.getPrintStatus());
						logParam.put("keyword2", printTicket.getTicketSequence());
						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕出票状态通知更新ticket表失败", logParam);
					}
					
				}
				
				//全部处理完成后写一条系统日志
				logParam.put("keyword2", "总条数:"+totalTicket+"更新成功:"+(totalTicket-failUpTicket)+"更新失败:"+failUpTicket);
				OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕出票状态通知更新ticket表成功", logParam);
				//处理完成后设置返回状态为0
				dealResult = "0";
			}else{//收到的通知数据为空
				dealResult = "1";
				logParam.put("keyword2", "0");
				OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕出票通知收到的数据为空", logParam);
			}
			
		}catch(Exception e){
			logger.error("出票通知接口处理发生异常:", e);
			
			logParam.put("keyword2", "");
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕出票状态通知发生异常", logParam);
			
			ticketNoticesResponse = new TicketNoticeQs50204(EhandUtil.commd_50204,EhandUtil.SYS_DEFINE_USERID);//响应消息
			dealResult = "1";
		}finally{
			ticketNoticesResponse.setErrorcode(dealResult);
		}
		return ticketNoticesResponse;
	}
	
	/**
	 * 
	 * Title: sendGaoPinTicket<br>
	 * Description: <br>
	 *              <br>高频送票,多乐彩
	 * @param sendTickets
	 * @return
	 */
	private static List<TicketBetResult> sendGaoPinTicket(List<TicketBet> sendTickets) throws LotteryException{
		boolean isNeedReSend = false;//是否需要重新发送,true表示需要重新发送
		List<TicketBetResult> sendResult = null;//请求返回的响应数据
		Map<String,String> logParam = null;
		try{
			logParam = getLogParam(EhandUtil.commd_50201);
			
			if(sendTickets == null || sendTickets.isEmpty()){
				logger.info("高频出票请求参数为空!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			EhandDataPack responseTicketInfo = new DlcBetQs50201(sendTickets).writePack();
			sendResult = (responseTicketInfo == null) ? null : ((DlcBetQs50201)responseTicketInfo).getBetResult();
			String sendErrCode = (responseTicketInfo == null) ? null : ((DlcBetQs50201)responseTicketInfo).getErrorcode();//发送结果的业务返回码
			logger.debug("高频出票返回消息头状态码:" + sendErrCode);
			
		}catch(LotteryException ex){
			logger.error("50201,高频送票出错:", ex);
//			logParam.put("keyword2", String.valueOf(ex.getType()));
//			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕高频出票发生异常", logParam);
			throw ex;
		}catch(Exception e){
			logger.error("50201,高频送票出错:", e);
//			logParam.put("keyword2", "");
//			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕高频出票发生异常", logParam);
			throw new LotteryException(EhandUtil.E_03_CODE,EhandUtil.E_03_DESC);
		}
		return sendResult;
	}
	/**
	 * 
	 * Title: sendCommonTicket<br>
	 * Description: <br>
	 *              <br>非高频送票
	 * @param sendTickets
	 * @return
	 */
	private static List<TicketBetResult> sendCommonTicket(List<CommonTicketBet> sendTickets) throws LotteryException{
		boolean isNeedReSend = false;//是否需要重新发送,true表示需要重新发送
		List<TicketBetResult> sendResult = null;//请求返回的响应数据
		Map<String,String> logParam = null;
		try{
			logParam = getLogParam(EhandUtil.commd_50207);
			
			if(sendTickets == null || sendTickets.isEmpty()){
				logger.info("其他投注出票请求参数为空!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//发送请求并得到请求的返回数据
			EhandDataPack responseTicketInfo = new CommonBetQs50207(sendTickets).writePack();
			sendResult = (responseTicketInfo == null) ? null : ((CommonBetQs50207)responseTicketInfo).getBetResult();
			String sendErrCode = (responseTicketInfo == null) ? null : ((CommonBetQs50207)responseTicketInfo).getErrorcode();//发送结果的返回码
			logger.debug("其他投注出票返回消息头状态码:" + sendErrCode);
			
		}catch(LotteryException ex){
			logger.error("50207,其他投注送票出错:", ex);
			
//			logParam.put("keyword2", String.valueOf(ex.getType()));
//			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕其他投注出票发生异常", logParam);
			
			throw ex;
		}catch(Exception e){
			logger.error("50207,其他投注送票出错:", e);
			
//			logParam.put("keyword2", "");
//			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕其他投注出票发生异常", logParam);
			
			throw new LotteryException(EhandUtil.E_03_CODE,EhandUtil.E_03_DESC);
		}
		return sendResult;
	}
	
	/**
	 * 
	 * Title: upBetTicketTable<br>
	 * Description: <br>
	 *              <br>更新投注系统的ticket表
	 * @param ticketSequence
	 * @param ticketId
	 * @param printTicketStatus
	 * @return
	 * @throws LotteryException
	 */
	private static int upBetTicketTable(String ticketSequence,String ticketId,String printTicketStatus) throws LotteryException{
		int upResult = 1;
		int betSysTicketStatus = 6;//投注系统的票状态，默认设置为收到的通知彩票成功
		
		if("0".equals(printTicketStatus)){
			betSysTicketStatus = 99;
		}else if("1".equals(printTicketStatus)){
			betSysTicketStatus = 6;
		}else{
			betSysTicketStatus = 7;
		}
		
		BetTicketServiceInterf betTicketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
		if(betSysTicketStatus != 99){//对于收到的通知数据返回票状态是0的，不对数据库操作
			upResult = betTicketService.updateBetTicketPrintInfo(ticketSequence,betSysTicketStatus, ticketId,null, printTicketStatus, null, null, null);
		}
		
		return upResult;
	}
	
	/**
	 * 
	 * Title: getLogParam<br>
	 * Description: <br>
	 *              <br>默认设置数据库日志用户
	 * @param keyWord1
	 * @return
	 */
	private static Map<String,String> getLogParam(String keyWord1){
		Map<String,String> logParam = new HashMap<String, String>();
		logParam.put("userId", "0");
		logParam.put("userName", "admin");
		logParam.put("keyword1", keyWord1);
		return logParam;
	}

}
