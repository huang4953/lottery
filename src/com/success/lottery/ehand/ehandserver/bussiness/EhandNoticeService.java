/**
 * Title: EhandNoticeService.java
 * @Package com.success.lottery.ehandserver
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-18 ����01:33:07
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
 * �����Ƚӿ�ҵ������
 * @author gaoboqin
 * 2011-1-18 ����01:33:07
 * 
 */

public class EhandNoticeService {
	private static Log logger = LogFactory.getLog(EhandNoticeService.class);
	
	/**
	 * 
	 * Title: noticeKjgg<br>
	 * Description: <br>
	 *              <br>��������֪ͨ
	 * @param dataPack
	 * @return
	 */
	public static EhandDataPack noticeKjgg(EhandDataPack dataPack){
		EhandDataPack kjggNoticesResponse = null;
		String dealResult = "0";//Ĭ�Ͻ��ܵ���֪ͨ����ɹ�
		Map<String,String> logParam = null;
		try{
			logParam = getLogParam(EhandUtil.commd_90001);
			
			KjggNoticeQs kjggNotic = (KjggNoticeQs) dataPack;
			kjggNoticesResponse = new KjggNoticeQs(kjggNotic.getCommand(),kjggNotic.getUserid());//��Ӧ��Ϣ
			
			QueryTermResult kjTermResult = kjggNotic.getKjTermResult();
			
			if(kjTermResult != null && StringUtils.isNotEmpty(kjTermResult.getTermNo()) && StringUtils.isNotEmpty(kjTermResult.getLotteryResult())){
				logger.debug("������������: " + kjTermResult.toString());
				
				EhangTermServiceInterf etermManager = ApplicationContextUtils.getService("ehandTermService", EhangTermServiceInterf.class);
				int noticeUpResult = 0;
				
				logParam.put("keyword2", kjTermResult.getLotteryId()+"|"+kjTermResult.getTermNo());
				if(EhandUtil.lotteryToZzy.containsValue(kjTermResult.getLotteryId())){//Ͷעϵͳ���Խ��ܵĲ���
					if(!"DLC".equals(kjTermResult.getLotteryId())){//�Ǹ�Ƶ����
						//����ehandterm��
						noticeUpResult = etermManager.updateEhandInfo(0, kjTermResult.getLotteryId(), kjTermResult.getTermNo(), kjTermResult.getLotteryResult(),6);
						if(noticeUpResult <= 0){//û�и��³ɹ�������Ϊ����֪ͨ����ʧ��
							dealResult = "1";
						}
						//дһ����־��ϵͳ��־����
						if("1".equals(dealResult)){//����ʧ��
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȿ���֪ͨ����ehandterm��ʧ��", logParam);
						}else{//���³ɹ�
							OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȿ���֪ͨ����ehandterm��ɹ�", logParam);
						}
						
					}else{//��Ƶ����
						//����ehandterm��
						noticeUpResult = etermManager.updateEhandInfo(0, kjTermResult.getLotteryId(), kjTermResult.getTermNo(), kjTermResult.getLotteryResult(),6);
						if(noticeUpResult > 0){//�����ȵĲ��ڱ���³ɹ�
							OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȿ���֪ͨ����ehandterm��ɹ�", logParam);
							if(EhandUtil.DLC_CASH.equals("1")){//������ɺ�ֱ�Ӷҽ�
								//����Ͷעϵͳ�Ĳ��ڱ�
								LotteryManagerInterf betLotteryResultService = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
								try {
									noticeUpResult = 0;
									noticeUpResult = betLotteryResultService.inputJxDlcWinInfo(kjTermResult.getTermNo(), kjTermResult.getLotteryResult());
								} catch (Exception e1) {
									logger.error("�����ȿ���֪ͨ����ehandterm�����쳣", e1);
								}
								
								if(noticeUpResult <= 0){//Ͷעϵͳ�Ĳ��ڱ����ʧ��
									OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȿ���֪ͨ����Ͷעϵͳ���ڱ�ʧ��", logParam);
								}else{//Ͷעϵͳ�Ĳ��ڱ���³ɹ�����ʼ���öҽ�
									OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȿ���֪ͨ����Ͷעϵͳ���ڱ�ɹ�", logParam);
									
									//�ҽ�ǰ���ж��Ƿ�����Ҫ�����Ʊ������а���Լ��ҲҪ�ҽ�������Ҫдһ��ϵͳ��־
									if(!isCanCashTicket(EhandUtil.zzyToLottery(kjTermResult.getLotteryId()),kjTermResult.getTermNo())){
										OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȿ���֪ͨ������δ������Ķ��������鶩��,��������ҽ�", logParam);
									}
									
									//������Զҽ�,����һ���߳̿�ʼ�ҽ�,ϵͳ�Ķҽ��쳣����Ӱ��֪ͨ�ķ���
									try{
										new CashDlcTicket(EhandUtil.zzyToLottery(kjTermResult.getLotteryId()),kjTermResult.getTermNo()).start();
									}catch(Exception e){
										OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȿ������öҽ�����", logParam);
									}
								}
							}
						}else{//�����ȵĲ��ڱ����ʧ��
							dealResult = "1";
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȿ���֪ͨ����ehandterm��ʧ��", logParam);
						}
						
					}
				}
			}else{//�յ���֪ͨ����Ϊ�գ��п�����Ҫ������
				dealResult = "1";
				logParam.put("keyword2", "");
				OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȿ���֪ͨ�յ�������Ϊ��", logParam);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("�����ȿ���֪ͨ�������쳣", e);
			
			logParam.put("keyword2", "");
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȿ���֪ͨ�������쳣", logParam);
			
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
	 *              <br>����֪ͨ
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
			newTermNoticesResponse = new TermNewNoticeQs(newTermNotice.getCommand(),newTermNotice.getUserid());//��Ӧ��Ϣ
			
			QueryTermResult newTermResult = newTermNotice.getNewTermResult();
			
			if(newTermResult != null){
				logger.debug("����������֪ͨ����[" + newTermResult.toString() + "]");
				
				logParam.put("keyword2", newTermResult.getLotteryId()+"|"+newTermResult.getTermNo());
				if(EhandUtil.lotteryToZzy.containsValue(newTermResult.getLotteryId())){//Ͷעϵͳ���Խ��ܵĲ���
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
						logger.error("����������֪ͨ����ehandterm�����:", e);
					}
					
					if(updateBetSysResult > 0){
						newTermNoticesResponse.setErrorcode("0");
						OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "����������֪ͨ����ehandterm���ڱ�ɹ�", logParam);
					}else{
						newTermNoticesResponse.setErrorcode("1");
						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "����������֪ͨ����ehandterm���ڱ�ʧ��", logParam);
					}
				}else{
					newTermNoticesResponse.setErrorcode("0");
				}
			}else{//�յ���֪ͨ����Ϊ�գ��п�����Ҫ������
				newTermNoticesResponse.setErrorcode("1");
				logParam.put("keyword2", "");
				OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "����������֪ͨ�յ�������Ϊ��", logParam);
			}
			
		}catch(Exception e){
			logger.error("����������֪ͨ�������쳣:", e);
			//��¼���ݿ���־
			logParam.put("keyword2", "");
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "����������֪ͨ�������쳣", logParam);
			
			newTermNoticesResponse =  new TermNewNoticeQs(EhandUtil.commd_90002,EhandUtil.SYS_DEFINE_USERID);
			newTermNoticesResponse.setErrorcode("1");
		}
		return newTermNoticesResponse;
	}
	/**
	 * 
	 * Title: noticeEndTerm<br>
	 * Description: <br>
	 *              <br>�ڽ�֪ͨ
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
			endTermNoticesResponse = new TermEndNoticeQs(endTermNotice.getCommand(),endTermNotice.getUserid());//��Ӧ��Ϣ
			
			QueryTermResult endTermResult = endTermNotice.getEndTermResult();
			
			if(endTermResult != null){
				logger.debug("�������ڽ�֪ͨ����[" + endTermResult.toString() + "]");
				
				logParam.put("keyword2", endTermResult.getLotteryId()+"|"+endTermResult.getTermNo());
				if(EhandUtil.lotteryToZzy.containsValue(endTermResult.getLotteryId())){//Ͷעϵͳ���Խ��ܵĲ���
					try {
						EhangTermServiceInterf etermManager = ApplicationContextUtils.getService("ehandTermService", EhangTermServiceInterf.class);
						updateBetSysResult = etermManager.updateEhandInfo(0,
								endTermResult.getLotteryId(),
								endTermResult.getTermNo(), Integer
										.parseInt(endTermResult.getStatus()));
					} catch (Exception e) {
						logger.error("�������ڽᴦ�����ehandterm�����", e);
					}
					
					if(updateBetSysResult > 0){
						endTermNoticesResponse.setErrorcode("0");
						OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�������ڽ�֪ͨ����ehandterm���ڱ�ɹ�", logParam);
					}else{
						endTermNoticesResponse.setErrorcode("1");
						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�������ڽ�֪ͨ����ehandterm���ڱ�ʧ��", logParam);
					}
				}else{
					endTermNoticesResponse.setErrorcode("0");
				}
			}else{//�յ���֪ͨ����Ϊ�գ��п�����Ҫ������
				endTermNoticesResponse.setErrorcode("1");
				logParam.put("keyword2", "");
				OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�������ڽ�֪ͨ�յ�������Ϊ��", logParam);
			}
			
		}catch(Exception e){//������,��Ҫ��������յ���֪ͨϵͳ����ʧ��
			logger.error("�������ڽ�֪ͨ�������쳣", e);
			
			logParam.put("keyword2", "");
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�������ڽ�֪ͨ�������쳣", logParam);
			
			endTermNoticesResponse =  new TermEndNoticeQs(EhandUtil.commd_90003,EhandUtil.SYS_DEFINE_USERID);
			endTermNoticesResponse.setErrorcode("1");
		}
		return endTermNoticesResponse;
	}
	/**
	 * 
	 * Title: isCanCashTicket<br>
	 * Description: <br>
	 *              <br>�ҽ�ǰ���жϸò����Ƿ�����Ҫȷ�ϳ�Ʊ����Ķ���
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
			int notCompleteOrder = orderServer.queryOrderNumByStatus(lotteryId, termNo, orderStatus, new ArrayList<Integer>());// ���Ƿ�����Ҫȷ�ϳ�Ʊ����Ķ���
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
	 *              <br>Ĭ���������ݿ���־�û�
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
