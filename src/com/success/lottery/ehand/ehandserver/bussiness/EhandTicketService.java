/**
 * Title: EhandTicketService.java
 * @Package com.success.lottery.ehand.ehandserver.bussiness
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-2-9 ����06:02:03
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
 * (��Ʊ��ص������Ƚӿ�)
 * @author gaoboqin
 * 2011-2-9 ����06:02:03
 * 
 */

public class EhandTicketService {
	private static Log logger = LogFactory.getLog(EhandTicketService.class);
	
	private static EhandLog ticketLog = EhandLog.getInstance("E_SENDTICKET");//��¼���͵ĺͷ��ص�Ʊ��Ϣ
	
	/**
	 * 
	 * Title: printOneTicket<br>
	 * Description: <br>
	 *              <br>�����ȳ�Ʊ
	 *              <br>ֻ�������ݽ�����û�������ݿ�ĸ��²���
	 *              <br>
	 * @param betTicket
	 *                 <br>BetTicketDomain��������ṩ������
	 *                 ����id�����ڣ��淨��Ͷע��ʽ��ͶעϵͳƱSequence��Ͷע����������ע����Ͷע���
	 *                 
	 * @return 
	 *        <br>����TicketBetResult��������Ϊ
	 *        errorCode���������
	 *        opreateId��������ˮ
	 *        ticketSequence��ƱSequence
	 *        accountValue���˻�����
	 * @throws LotteryException
	 *                         <br>EhandUtil.E_01_CODE,��������ͨѶ����
	 *                         <br>EhandUtil.E_02_CODE,��������ͨѶ���ص�״̬�벻��ȷ
	 *                         <br>EhandUtil.E_03_CODE,��������ͨѶ��ȷ���ص���Ϣ�������
	 *                         <br>EhandUtil.E_04_CODE,�������Ϊ��
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
	 *              <br>��ѯ����Ʊ�ĳ�Ʊ���
	 *              <br>ֻ�������ݽ�����û�������ݿ�ĸ��²���
	 * @param betTicketSequence
	 * @return
	 * @throws LotteryException
	 *                         <br>EhandUtil.E_01_CODE,��������ͨѶ����
	 *                         <br>EhandUtil.E_02_CODE,��������ͨѶ���ص�״̬�벻��ȷ
	 *                         <br>EhandUtil.E_03_CODE,��������ͨѶ��ȷ���ص���Ϣ�������
	 *                         <br>EhandUtil.E_04_CODE,�������Ϊ��
	 */
	public static TicketQueryResult QueryPrintTicket(String betTicketSequence) throws LotteryException{
		Map<String,String> logParam = null;
		TicketQueryResult printResult = null;
		
		if(StringUtils.isEmpty(betTicketSequence)){
			throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
		}
		logger.debug("�����ȷ��ͳ�Ʊ�����ѯ,��ѯƱ��ˮ:"+betTicketSequence);
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
			
			logger.debug("�����ȷ��ͳ�Ʊ�����ѯ,��ѯƱ��ˮ:"+betTicketSequence+"����Ʊ��Ϣ:"+printResult.toString());
			ticketLog.queryReceiveTicketLog(printResult.getErrorCode(), printResult.getTicketSequence(), printResult.getTicketId(),printResult.getPrintStatus());
			
		}catch(LotteryException e){
			logger.error("50205,�����Ȳ�ѯ��Ʊ�������:", e);
			logParam.put("keyword1", betTicketSequence);
			logParam.put("keyword2", e.getMessage());
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����Ȳ�ѯ��Ʊ��������쳣", logParam);
			
			throw e;
		}catch(Exception ex){
			logger.error("50205,�����Ȳ�ѯ��Ʊ�������:", ex);
			logParam.put("keyword1", betTicketSequence);
			logParam.put("keyword2", "");
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����Ȳ�ѯ��Ʊ��������쳣", logParam);
			
			throw new LotteryException(EhandUtil.E_03_CODE,EhandUtil.E_03_DESC);
		}
		return printResult;
	}
	
	/**
	 * 
	 * Title: printTickets<br>
	 * Description: <br>
	 *              <br>��Ʊ�������Ƚӿڲ�������Ӧ�Ľ��
	 * @param betTickets
	 * @return
	 * @throws LotteryException
	 *               <br>EhandUtil.E_01_CODE,��������ͨѶ����
	 *               <br>EhandUtil.E_02_CODE,��������ͨѶ���ص�״̬�벻��ȷ
	 *               <br>EhandUtil.E_03_CODE,��������ͨѶ��ȷ���ص���Ϣ�������
	 *               <br>EhandUtil.E_04_CODE,�������Ϊ��
	 */
	public static List<TicketBetResult> printTickets(List<BetTicketDomain> betTickets) throws LotteryException{
		List<TicketBetResult> sendResult = null;
		Map<String,String> logParam = null;
		try{
			logParam = getLogParam("");
			if(betTickets == null || betTickets.isEmpty()){
				logger.info("�ύ��Ʊ�������Ϊ��!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//�����ύ��Ʊ�Ĳ��ֽ�Ʊ��Ϊ��Ƶ�ͷǸ�Ƶ��������
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
			
			//�Բ�Ϊ�յ�Ʊ�����͵���Ʊϵͳ,�����͸�Ƶ
			if(gaoPintTickets != null && !gaoPintTickets.isEmpty()){//��Ƶ
				sendResult = sendGaoPinTicket(gaoPintTickets);
			}
			
			if(commonPrintTickets != null && !commonPrintTickets.isEmpty()){//�Ǹ�Ƶ
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
					OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "��������Ʊ�����쳣,����Ʊ���Ϊ��", logParam);
				}
				
				throw new LotteryException(EhandUtil.E_03_CODE,EhandUtil.E_03_DESC);
			}else{
//				for(TicketBetResult onePrintResult : sendResult){
//					if(!"0".equals(onePrintResult.getHeadErrorCode()) || !"0".equals(onePrintResult.getErrorCode())){
//						logParam.put("keyword1", onePrintResult.getTicketSequence());
//						logParam.put("keyword2", onePrintResult.getHeadErrorCode() + "|" + onePrintResult.getErrorCode());
//						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "��������Ʊ���ؽ��״̬�����", logParam);
//					}
//				}
				
				
			}
			
//			logParam.put("keyword2", "");
//			OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT,
//					OperatorLogger.PROCESSOR, "��������Ʊ�ɹ�,�ύƱ��:" + betTickets.size()
//							+ "��ӦƱ��:" + (sendResult == null ? 0 : sendResult.size()),logParam);
			
		}catch(LotteryException ex){
			throw ex;
		}catch(Exception e){
			logger.error("��Ʊ�������:", e);
			logParam.put("keyword2", "");
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "��������Ʊ�����쳣", logParam);
			
			throw new LotteryException(EhandUtil.E_03_CODE,EhandUtil.E_03_DESC);
		}
		return sendResult;
	}
	
	/**
	 * 
	 * Title: QueryPrintTicket50205<br>
	 * Description: <br>
	 *              <br>��Ʊ״̬��ѯ
	 *              <br>����Ӧ��������˶�Ӧ�����ݿ�
	 *              <br>�������,���ִ�д��� > 0
	 *              <br>��ѯ�����г�Ʊ״̬Ϊ:0,�ȴ���Ʊ��1����Ʊ�ɹ�������ֵΪ����,��ӦͶעϵͳƱ״̬��0-4(��Ʊ����ɹ��ȴ���Ʊ���,���������ݿ�)��1-6(��Ʊ�ɹ�)��������Ӧ7(��Ʊʧ��)
	 * @param betTicketSequence
	 * @return 
	 * @exception LotteryException
	 * 
	 */
	public static List<TicketQueryResult> QueryPrintTicket50205(List<String> betTicketSequence) throws LotteryException{
		boolean isNeedReSend = false;//�Ƿ���Ҫ���·���,true��ʾ��Ҫ���·���
		List<TicketQueryResult> queryResult = null;//���󷵻ص���Ӧ����
		Map<String,String> logParam = null;
		try{
			logParam = getLogParam(EhandUtil.commd_50205);
			
			if(betTicketSequence == null || betTicketSequence.isEmpty()){
				logger.info("��Ʊ״̬��ѯ�������Ϊ��!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//�������󲢵õ���Ӧ���
			for (int i = 0; i < EhandUtil.reSendNum; i++) {
				// �������󲢵õ�����ķ�������
				EhandDataPack responseTicketInfo = new TicketQueryQs50205(betTicketSequence).writePack();
				queryResult = (responseTicketInfo == null) ? null : ((TicketQueryQs50205)responseTicketInfo).getBetResultList();
				
				// У������Ľ�����������Ƿ����·�������
				if (queryResult != null && !queryResult.isEmpty()) {// �õ�����Ӧ����
					
					for (TicketQueryResult oneTicket : queryResult) {//�ò����Ƿ���Ҫ��Ҫȷ��
						String errCode = oneTicket.getErrorCode();
						if (!"0".equals(errCode)) {// ��Ϣ�ķ����벻��ȷ��0������ȷ�������Ĵ�����ȷ
							logger.error("��Ʊ״̬��ѯ���ش������:" + oneTicket.toString());
							// ���ݷ��صĴ���������Ƿ����·�������

							isNeedReSend = true;
							break;
						}
					}
					
				} else {// û�еõ���Ӧ����
					isNeedReSend = true;
				}

				// ����õ�����Ӧû��ͨ��У�飬�����·�������
				if (isNeedReSend) {// ��Ҫ���·���
					logger.info("��Ʊ״̬�����ѯ�ط����󣬵�" + (i+1) + "��");
					Thread.sleep(EhandUtil.reSendSleep);// ���5�����·���
				} else {
					break;
				}
			}
			
			//��ȷ�ĵõ�����Ӧ���ݣ�����Ӧ�������Ʊ��
			if (queryResult != null && !queryResult.isEmpty()) {
				int requestNum = betTicketSequence.size();//�����������
				int reponseNum = queryResult.size();//��Ӧ��������
				int failTicket = 0;//ʧ�ܴ����Ʊ����������Ӧ�����д�����>0�ĺ͸������ݿ�����ļ�¼��
				for (TicketQueryResult printTicket : queryResult) {
					logger.debug("��ѯ[50205]����Ʊ" + printTicket.toString());
					
					try{
						int upResult = -1;
						
						if(Integer.parseInt(printTicket.getErrorCode()) <=0){//���صĴ������ʾ����������ȷ
							upResult = upBetTicketTable(printTicket.getTicketSequence(),printTicket.getTicketId(),printTicket.getPrintStatus());
						}else{
							upResult = -1;
							logParam.put("keyword2", printTicket.getErrorCode());
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȳ�Ʊ״̬��ѯ���ش����״̬��", logParam);
						}
						
						if(upResult <= 0){//û�и��³ɹ�
							failTicket++;
							logParam.put("keyword2", printTicket.getTicketSequence());
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȳ�Ʊ״̬֪ͨ����ticket��ʧ��", logParam);
						}
						
					}catch(LotteryException e){//���²�Ʊ������쳣
						failTicket++;
						logParam.put("keyword2", printTicket.getTicketSequence());
						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȳ�Ʊ״̬֪ͨ����ticket��ʧ��", logParam);
					}
					
					//ȫ��������ɺ�дһ��ϵͳ��־
					logParam.put("keyword2", "��ѯ����:"+requestNum+"��Ӧ����:"+reponseNum+"ʧ������:"+failTicket);
					OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȳ�Ʊ״̬��ѯ����ticket��ɹ�", logParam);
				}
			}
		}catch(Exception e){
			logger.error("50205,��Ʊ״̬�����ѯ����:", e);
			
			logParam.put("keyword2", "");
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȳ�Ʊ״̬�����ѯ�����쳣", logParam);
		}
		return queryResult;
	}
	
	/**
	 * 
	 * Title: notice50204<br>
	 * Description: <br>
	 *              <br>��Ʊ״̬��֪ͨ�ӿڣ�ֻ�зǸ�Ƶ����֪ͨ
	 *              <br>֪ͨ�����г�Ʊ״̬Ϊ:0,�ȴ���Ʊ��1����Ʊ�ɹ�������ֵΪ����,��ӦͶעϵͳƱ״̬��0-4(��Ʊ����ɹ��ȴ���Ʊ���)��1-6(��Ʊ�ɹ�)��������Ӧ7(��Ʊʧ��)
	 *              <br>
	 * @param dataPack
	 * @return
	 */
	public static EhandDataPack notice50204(EhandDataPack dataPack){
		EhandDataPack ticketNoticesResponse = null;
		String dealResult = "0";//Ĭ�Ͻ��ܵ���֪ͨ����ɹ�
		Map<String,String> logParam = null;
		try{
			logParam = getLogParam(EhandUtil.commd_50204);
			
			TicketNoticeQs50204 ticketNotis = (TicketNoticeQs50204) dataPack;
			ticketNoticesResponse = new TicketNoticeQs50204(ticketNotis.getCommand(),ticketNotis.getUserid());//��Ӧ��Ϣ
			
			List<TicketQueryResult> betResultList = ticketNotis.getBetResultList();
			
			if(betResultList != null){//�յ������ݲ�Ϊ��
				
				int totalTicket = betResultList.size();//�յ�֪ͨ��������
				int failUpTicket = 0;//����Ʊ��ʧ�ܵļ�¼����
				
				for(TicketQueryResult printTicket : betResultList){
					logger.debug("֪ͨ����Ʊ"+printTicket.toString());
					ticketLog.noticeTicketLog(printTicket.getTicketSequence(), printTicket.getTicketId(), printTicket.getPrintStatus());
					try{
						if(!"0".equals(printTicket.getPrintStatus()) && !"1".equals(printTicket.getPrintStatus())){
							logParam.put("keyword1", printTicket.getPrintStatus());
							logParam.put("keyword2", printTicket.getTicketSequence());
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȳ�Ʊ״̬֪ͨ�յ���Ʊ״̬Ϊ����", logParam);
						}
						
						int upResult = upBetTicketTable(printTicket.getTicketSequence(),printTicket.getTicketId(),printTicket.getPrintStatus());
						
						if(upResult <= 0){//û�и��³ɹ�
							failUpTicket++;
							logParam.put("keyword1", printTicket.getPrintStatus());
							logParam.put("keyword2", printTicket.getTicketSequence());
							OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȳ�Ʊ״̬֪ͨ����ticket��ʧ��", logParam);
						}
						
					}catch(LotteryException e){//���²�Ʊ������쳣
						failUpTicket++;
						logParam.put("keyword1", printTicket.getPrintStatus());
						logParam.put("keyword2", printTicket.getTicketSequence());
						OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȳ�Ʊ״̬֪ͨ����ticket��ʧ��", logParam);
					}
					
				}
				
				//ȫ��������ɺ�дһ��ϵͳ��־
				logParam.put("keyword2", "������:"+totalTicket+"���³ɹ�:"+(totalTicket-failUpTicket)+"����ʧ��:"+failUpTicket);
				OperatorLogger.log(OperatorLogger.INFO, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȳ�Ʊ״̬֪ͨ����ticket��ɹ�", logParam);
				//������ɺ����÷���״̬Ϊ0
				dealResult = "0";
			}else{//�յ���֪ͨ����Ϊ��
				dealResult = "1";
				logParam.put("keyword2", "0");
				OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȳ�Ʊ֪ͨ�յ�������Ϊ��", logParam);
			}
			
		}catch(Exception e){
			logger.error("��Ʊ֪ͨ�ӿڴ������쳣:", e);
			
			logParam.put("keyword2", "");
			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȳ�Ʊ״̬֪ͨ�����쳣", logParam);
			
			ticketNoticesResponse = new TicketNoticeQs50204(EhandUtil.commd_50204,EhandUtil.SYS_DEFINE_USERID);//��Ӧ��Ϣ
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
	 *              <br>��Ƶ��Ʊ,���ֲ�
	 * @param sendTickets
	 * @return
	 */
	private static List<TicketBetResult> sendGaoPinTicket(List<TicketBet> sendTickets) throws LotteryException{
		boolean isNeedReSend = false;//�Ƿ���Ҫ���·���,true��ʾ��Ҫ���·���
		List<TicketBetResult> sendResult = null;//���󷵻ص���Ӧ����
		Map<String,String> logParam = null;
		try{
			logParam = getLogParam(EhandUtil.commd_50201);
			
			if(sendTickets == null || sendTickets.isEmpty()){
				logger.info("��Ƶ��Ʊ�������Ϊ��!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			EhandDataPack responseTicketInfo = new DlcBetQs50201(sendTickets).writePack();
			sendResult = (responseTicketInfo == null) ? null : ((DlcBetQs50201)responseTicketInfo).getBetResult();
			String sendErrCode = (responseTicketInfo == null) ? null : ((DlcBetQs50201)responseTicketInfo).getErrorcode();//���ͽ����ҵ�񷵻���
			logger.debug("��Ƶ��Ʊ������Ϣͷ״̬��:" + sendErrCode);
			
		}catch(LotteryException ex){
			logger.error("50201,��Ƶ��Ʊ����:", ex);
//			logParam.put("keyword2", String.valueOf(ex.getType()));
//			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȸ�Ƶ��Ʊ�����쳣", logParam);
			throw ex;
		}catch(Exception e){
			logger.error("50201,��Ƶ��Ʊ����:", e);
//			logParam.put("keyword2", "");
//			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "�����ȸ�Ƶ��Ʊ�����쳣", logParam);
			throw new LotteryException(EhandUtil.E_03_CODE,EhandUtil.E_03_DESC);
		}
		return sendResult;
	}
	/**
	 * 
	 * Title: sendCommonTicket<br>
	 * Description: <br>
	 *              <br>�Ǹ�Ƶ��Ʊ
	 * @param sendTickets
	 * @return
	 */
	private static List<TicketBetResult> sendCommonTicket(List<CommonTicketBet> sendTickets) throws LotteryException{
		boolean isNeedReSend = false;//�Ƿ���Ҫ���·���,true��ʾ��Ҫ���·���
		List<TicketBetResult> sendResult = null;//���󷵻ص���Ӧ����
		Map<String,String> logParam = null;
		try{
			logParam = getLogParam(EhandUtil.commd_50207);
			
			if(sendTickets == null || sendTickets.isEmpty()){
				logger.info("����Ͷע��Ʊ�������Ϊ��!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//�������󲢵õ�����ķ�������
			EhandDataPack responseTicketInfo = new CommonBetQs50207(sendTickets).writePack();
			sendResult = (responseTicketInfo == null) ? null : ((CommonBetQs50207)responseTicketInfo).getBetResult();
			String sendErrCode = (responseTicketInfo == null) ? null : ((CommonBetQs50207)responseTicketInfo).getErrorcode();//���ͽ���ķ�����
			logger.debug("����Ͷע��Ʊ������Ϣͷ״̬��:" + sendErrCode);
			
		}catch(LotteryException ex){
			logger.error("50207,����Ͷע��Ʊ����:", ex);
			
//			logParam.put("keyword2", String.valueOf(ex.getType()));
//			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "����������Ͷע��Ʊ�����쳣", logParam);
			
			throw ex;
		}catch(Exception e){
			logger.error("50207,����Ͷע��Ʊ����:", e);
			
//			logParam.put("keyword2", "");
//			OperatorLogger.log(OperatorLogger.ERROR, OperatorLogger.URGENT, OperatorLogger.PROCESSOR, "����������Ͷע��Ʊ�����쳣", logParam);
			
			throw new LotteryException(EhandUtil.E_03_CODE,EhandUtil.E_03_DESC);
		}
		return sendResult;
	}
	
	/**
	 * 
	 * Title: upBetTicketTable<br>
	 * Description: <br>
	 *              <br>����Ͷעϵͳ��ticket��
	 * @param ticketSequence
	 * @param ticketId
	 * @param printTicketStatus
	 * @return
	 * @throws LotteryException
	 */
	private static int upBetTicketTable(String ticketSequence,String ticketId,String printTicketStatus) throws LotteryException{
		int upResult = 1;
		int betSysTicketStatus = 6;//Ͷעϵͳ��Ʊ״̬��Ĭ������Ϊ�յ���֪ͨ��Ʊ�ɹ�
		
		if("0".equals(printTicketStatus)){
			betSysTicketStatus = 99;
		}else if("1".equals(printTicketStatus)){
			betSysTicketStatus = 6;
		}else{
			betSysTicketStatus = 7;
		}
		
		BetTicketServiceInterf betTicketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
		if(betSysTicketStatus != 99){//�����յ���֪ͨ���ݷ���Ʊ״̬��0�ģ��������ݿ����
			upResult = betTicketService.updateBetTicketPrintInfo(ticketSequence,betSysTicketStatus, ticketId,null, printTicketStatus, null, null, null);
		}
		
		return upResult;
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
