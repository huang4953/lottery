/**
 * Title: EhandNoticeService.java
 * @Package com.success.lottery.ehandserver
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-18 下午01:33:07
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

import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.ehand.eterm.service.interf.EhangTermServiceInterf;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.util.LotteryTools;
import com.success.protocol.ticket.zzy.EhandDataPack;
import com.success.protocol.ticket.zzy.KjggNoticeQs;
import com.success.protocol.ticket.zzy.TermEndNoticeQs;
import com.success.protocol.ticket.zzy.TermNewNoticeQs;
import com.success.protocol.ticket.zzy.model.QueryTermResult;
import com.success.protocol.ticket.zzy.util.EhandUtil;
import com.success.utils.ApplicationContextUtils;


/**
 * com.success.lottery.ehandserver
 * EhandNoticeService.java
 * EhandNoticeService
 * 掌中奕接口业务处理类
 * @author gaoboqin
 * 2011-1-18 下午01:33:07
 * 
 */

public class EhandNoticeService {
	private static Log logger = LogFactory.getLog(EhandNoticeService.class);
	
	/**
	 * 
	 * Title: noticeKjgg<br>
	 * Description: <br>
	 *              <br>开奖公告通知
	 * @param dataPack
	 * @return
	 */
	public static EhandDataPack noticeKjgg(EhandDataPack dataPack){
		EhandDataPack kjggNoticesResponse = null;
		String dealResult = "0";//默认接受到的通知处理成功
		Map<String,String> logParam = null;
		try{
			logParam = getLogParam(EhandUtil.commd_90001);
			
			KjggNoticeQs kjggNotic = (KjggNoticeQs) dataPack;
			kjggNoticesResponse = new KjggNoticeQs(kjggNotic.getCommand(),kjggNotic.getUserid());//响应消息
			
			QueryTermResult kjTermResult = kjggNotic.getKjTermResult();
			
			if(kjTermResult != null && StringUtils.isNotEmpty(kjTermResult.getTermNo()) && StringUtils.isNotEmpty(kjTermResult.getLotteryResult())){
				logger.debug("开奖公告数据: " + kjTermResult.toString());
				
				EhangTermServiceInterf etermManager = ApplicationContextUtils.getService("ehandTermService", EhangTermServiceInterf.class);
				int noticeUpResult = 0;
				
				logParam.put("keyword2", kjTermResult.getLotteryId()+"|"+kjTermResult.getTermNo());
				if(EhandUtil.lotteryToZzy.containsValue(kjTermResult.getLotteryId())){//投注系统可以接受的彩种
					if(!"DLC".equals(kjTermResult.getLotteryId())){//非高频彩种
						//更新ehandterm表
						noticeUpResult = etermManager.updateEhandInfo(0, kjTermResult.getLotteryId(), kjTermResult.getTermNo(), kjTermResult.getLotteryResult(),6);
						if(noticeUpResult <= 0){//没有更新成功，则认为开奖通知处理失败
							dealResult = "1";
						}
						//写一条日志到系统日志表中
						if("1".equals(dealResult)){//更新失败
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕开奖通知更新ehandterm表失败", logParam);
						}else{//更新成功
							OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕开奖通知更新ehandterm表成功", logParam);
						}
						
					}else{//高频彩种
						//更新ehandterm表
						noticeUpResult = etermManager.updateEhandInfo(0, kjTermResult.getLotteryId(), kjTermResult.getTermNo(), kjTermResult.getLotteryResult(),6);
						if(noticeUpResult > 0){//掌中奕的彩期表更新成功
							OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕开奖通知更新ehandterm表成功", logParam);
							if(EhandUtil.DLC_CASH.equals("1")){//开奖完成后直接兑奖
								//更新投注系统的彩期表
								LotteryManagerInterf betLotteryResultService = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
								try {
									noticeUpResult = 0;
									noticeUpResult = betLotteryResultService.inputJxDlcWinInfo(kjTermResult.getTermNo(), kjTermResult.getLotteryResult());
								} catch (Exception e1) {
									logger.error("掌中奕开奖通知更新ehandterm表发生异常", e1);
								}
								
								if(noticeUpResult <= 0){//投注系统的彩期表更新失败
									OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕开奖通知更新投注系统彩期表失败", logParam);
								}else{//投注系统的彩期表更新成功，开始调用兑奖
									OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕开奖通知更新投注系统彩期表成功", logParam);
									
									//兑奖前先判断是否还有需要处理的票，如果有按照约定也要兑奖，但是要写一条系统日志
									if(!isCanCashTicket(EhandUtil.zzyToLottery(kjTermResult.getLotteryId()),kjTermResult.getTermNo())){
										OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕开奖通知彩期有未做处理的订单，请检查订单,程序继续兑奖", logParam);
									}
									
									//如果可以兑奖,调用一个线程开始兑奖,系统的兑奖异常不能影响通知的返回
									try{
										new CashDlcTicket(EhandUtil.zzyToLottery(kjTermResult.getLotteryId()),kjTermResult.getTermNo()).start();
									}catch(Exception e){
										OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕开奖调用兑奖出错", logParam);
									}
								}
							}
						}else{//掌中奕的彩期表更新失败
							dealResult = "1";
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕开奖通知更新ehandterm表失败", logParam);
						}
						
					}
				}
			}else{//收到的通知数据为空，有可能需要出错处理
				dealResult = "1";
				logParam.put("keyword2", "");
				OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕开奖通知收到的数据为空", logParam);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("掌中奕开奖通知处理发生异常", e);
			
			logParam.put("keyword2", "");
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕开奖通知处理发生异常", logParam);
			
			kjggNoticesResponse =  new KjggNoticeQs(EhandUtil.commd_90001,EhandUtil.SYS_DEFINE_USERID);
			dealResult = "1";
		}finally{
			kjggNoticesResponse.setErrorcode(dealResult);
		}
		return kjggNoticesResponse;
	}
	/**
	 * 
	 * Title: noticeNewTerm<br>
	 * Description: <br>
	 *              <br>新期通知
	 * @param dataPack
	 * @return
	 */
	public static EhandDataPack noticeNewTerm(EhandDataPack dataPack){
		EhandDataPack newTermNoticesResponse = null;
		int updateBetSysResult = -1;
		Map<String,String> logParam = null;
		try{
			logParam = getLogParam(EhandUtil.commd_90002);
			
			TermNewNoticeQs newTermNotice = (TermNewNoticeQs) dataPack;
			newTermNoticesResponse = new TermNewNoticeQs(newTermNotice.getCommand(),newTermNotice.getUserid());//响应消息
			
			QueryTermResult newTermResult = newTermNotice.getNewTermResult();
			
			if(newTermResult != null){
				logger.debug("掌中奕新期通知数据[" + newTermResult.toString() + "]");
				
				logParam.put("keyword2", newTermResult.getLotteryId()+"|"+newTermResult.getTermNo());
				if(EhandUtil.lotteryToZzy.containsValue(newTermResult.getLotteryId())){//投注系统可以接受的彩种
					try {
						EhangTermServiceInterf etermManager = ApplicationContextUtils.getService("ehandTermService", EhangTermServiceInterf.class);
						int betLotteryId = EhandUtil.zzyToLottery(newTermResult.getLotteryId());
						String printStart =  newTermResult.getStartTimeStamp();
						String printEnd =  LotteryTools.getLotteryPreDrawTicketTimer(betLotteryId, newTermResult.getEndTimeStamp(),"yyyyMMddHHmmss");
						updateBetSysResult = etermManager.updateEhandInfo(0,
								newTermResult.getLotteryId(),
								newTermResult.getTermNo(), newTermResult
										.getStartTimeStamp(),printStart,printEnd, newTermResult
										.getEndTimeStamp(), Integer
										.parseInt(newTermResult.getStatus()));
					} catch (Exception e) {
						logger.error("掌中奕新期通知更新ehandterm表出错:", e);
					}
					
					if(updateBetSysResult > 0){
						newTermNoticesResponse.setErrorcode("0");
						OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕新期通知更新ehandterm彩期表成功", logParam);
					}else{
						newTermNoticesResponse.setErrorcode("1");
						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕新期通知更新ehandterm彩期表失败", logParam);
					}
				}else{
					newTermNoticesResponse.setErrorcode("0");
				}
			}else{//收到的通知数据为空，有可能需要出错处理
				newTermNoticesResponse.setErrorcode("1");
				logParam.put("keyword2", "");
				OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕新期通知收到的数据为空", logParam);
			}
			
		}catch(Exception e){
			logger.error("掌中奕新期通知处理发生异常:", e);
			//记录数据库日志
			logParam.put("keyword2", "");
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕新期通知处理发生异常", logParam);
			
			newTermNoticesResponse =  new TermNewNoticeQs(EhandUtil.commd_90002,EhandUtil.SYS_DEFINE_USERID);
			newTermNoticesResponse.setErrorcode("1");
		}
		return newTermNoticesResponse;
	}
	/**
	 * 
	 * Title: noticeEndTerm<br>
	 * Description: <br>
	 *              <br>期结通知
	 * @param dataPack
	 * @return
	 */
	public static EhandDataPack noticeEndTerm(EhandDataPack dataPack){
		EhandDataPack endTermNoticesResponse = null;
		int updateBetSysResult = -1;
		Map<String,String> logParam = null;
		try{
			logParam = getLogParam(EhandUtil.commd_90003);
			
			TermEndNoticeQs endTermNotice = (TermEndNoticeQs) dataPack;
			endTermNoticesResponse = new TermEndNoticeQs(endTermNotice.getCommand(),endTermNotice.getUserid());//响应消息
			
			QueryTermResult endTermResult = endTermNotice.getEndTermResult();
			
			if(endTermResult != null){
				logger.debug("掌中奕期结通知数据[" + endTermResult.toString() + "]");
				
				logParam.put("keyword2", endTermResult.getLotteryId()+"|"+endTermResult.getTermNo());
				if(EhandUtil.lotteryToZzy.containsValue(endTermResult.getLotteryId())){//投注系统可以接受的彩种
					try {
						EhangTermServiceInterf etermManager = ApplicationContextUtils.getService("ehandTermService", EhangTermServiceInterf.class);
						updateBetSysResult = etermManager.updateEhandInfo(0,
								endTermResult.getLotteryId(),
								endTermResult.getTermNo(), Integer
										.parseInt(endTermResult.getStatus()));
					} catch (Exception e) {
						logger.error("掌中奕期结处理更新ehandterm表出错", e);
					}
					
					if(updateBetSysResult > 0){
						endTermNoticesResponse.setErrorcode("0");
						OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕期结通知更新ehandterm彩期表成功", logParam);
					}else{
						endTermNoticesResponse.setErrorcode("1");
						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕期结通知更新ehandterm彩期表失败", logParam);
					}
				}else{
					endTermNoticesResponse.setErrorcode("0");
				}
			}else{//收到的通知数据为空，有可能需要出错处理
				endTermNoticesResponse.setErrorcode("1");
				logParam.put("keyword2", "");
				OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕期结通知收到的数据为空", logParam);
			}
			
		}catch(Exception e){//出错处理,需要考虑如果收到的通知系统处理失败
			logger.error("掌中奕期结通知处理发生异常", e);
			
			logParam.put("keyword2", "");
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "掌中奕期结通知处理发生异常", logParam);
			
			endTermNoticesResponse =  new TermEndNoticeQs(EhandUtil.commd_90003,EhandUtil.SYS_DEFINE_USERID);
			endTermNoticesResponse.setErrorcode("1");
		}
		return endTermNoticesResponse;
	}
	/**
	 * 
	 * Title: isCanCashTicket<br>
	 * Description: <br>
	 *              <br>兑奖前先判断该彩期是否还有需要确认出票处理的订单
	 * @param lotteryId
	 * @param termNo
	 * @return
	 */
	private static boolean isCanCashTicket(int lotteryId,String termNo){
		boolean isCan = true;
		try{
			BetPlanOrderServiceInterf orderServer = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
			List<Integer>  orderStatus = new ArrayList<Integer>();
			orderStatus.add(0);
			orderStatus.add(1);
			orderStatus.add(2);
			orderStatus.add(3);
			int notCompleteOrder = orderServer.queryOrderNumByStatus(lotteryId, termNo, orderStatus, new ArrayList<Integer>());// 查是否还有需要确认出票处理的订单
			if(notCompleteOrder > 0){
				isCan = false;
			}
		}catch(Exception e){
			e.printStackTrace();
			isCan = false;
		}
		return isCan;
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
