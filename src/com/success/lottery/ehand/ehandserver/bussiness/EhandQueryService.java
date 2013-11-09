/**
 * Title: EhandQueryService.java
 * @Package com.success.lottery.ehand.ehandserver.bussiness
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-24 ����06:18:25
 * @version V1.0
 */
package com.success.lottery.ehand.ehandserver.bussiness;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.ehand.ehandserver.bussiness.model.EhandTermBussiModel;
import com.success.lottery.ehand.ehandserver.bussiness.model.QueryTermModel;
import com.success.lottery.exception.LotteryException;
import com.success.protocol.ticket.zzy.CashQueryQs50206;
import com.success.protocol.ticket.zzy.EhandDataPack;
import com.success.protocol.ticket.zzy.KjggQueryQs50203;
import com.success.protocol.ticket.zzy.TermQueryQs50200;
import com.success.protocol.ticket.zzy.TermQueryQs50209;
import com.success.protocol.ticket.zzy.TermSaleQs50208;
import com.success.protocol.ticket.zzy.model.CashResult;
import com.success.protocol.ticket.zzy.model.QueryTerm;
import com.success.protocol.ticket.zzy.model.QueryTermResult;
import com.success.protocol.ticket.zzy.model.TermSaleResult;
import com.success.protocol.ticket.zzy.util.EhandUtil;

/**
 * com.success.lottery.ehand.ehandserver.bussiness
 * EhandQueryService.java
 * EhandQueryService
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-24 ����06:18:25
 * 
 */

public class EhandQueryService {
	private static Log logger = LogFactory.getLog(EhandQueryService.class);
	
	/**
	 * 
	 * Title: queryEterm50200<br>
	 * Description: <br>
	 *              <br>���ݲ���id��ѯ������Ϣ
	 * @param betLotteryId Ͷעϵͳ�Ĳ���
	 * 
	 * @return
	 * @exception LotteryException
	 *                            <br>EhandUtil.E_01_CODE,��������ͨѶ����
	 *                            <br>EhandUtil.E_02_CODE,��������ͨѶ���ص�HTTP��Ӧ״̬����ȷ
	 *                            <br>EhandUtil.E_03_CODE,��������ͨѶ��ȷ���ص���Ϣ��������
	 *                            <br>EhandUtil.E_04_CODE,�����������ȷ
	 *                            <br>EhandUtil.E_07_CODE,�������󷵻ش�����[CODE]
	 *                            <br>EhandUtil.E_08_CODE,��������̷��������쳣
	 *                            <br>EhandUtil.E_09_CODE,��ѯ���󷵻ص�û�з�����Ϣ��
	 * 
	 */
	public static EhandTermBussiModel queryEterm50200(int betLotteryId) throws LotteryException{
		boolean isNeedReSend = false;//�Ƿ���Ҫ���·���,true��ʾ��Ҫ���·���
		QueryTermResult queryResult = null;//���󷵻ص���Ӧ����
		EhandTermBussiModel eTerms = null;
		String reponseErrCode = "";
		String reponseErrMsg = "";
		int excep_type = -1;
		String excep_message = "";
		try{
			String eLotteryId =  EhandUtil.lotteryToZzy.get(betLotteryId);//ת���������ȵĲ���
			
			if(StringUtils.isEmpty(eLotteryId)){
				logger.info("�����Ƚ��ڲ�ѯ�������Ϊ��[50200]!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//�������󲢵õ���Ӧ���
			for (int i = 0; i < EhandUtil.reSendNum; i++) {
				reponseErrCode = "";
				excep_type = -1;
				excep_message = "";
				//�������󲢵õ�����ķ�������
				List<String> requestModelInfo = new ArrayList<String>();
				requestModelInfo.add(eLotteryId);
				
				//�������󲢵õ��ظ���Ϣ
				EhandDataPack responseTermInfo = null;
				
				try {
					responseTermInfo = new TermQueryQs50200(requestModelInfo).writePack();
				} catch (LotteryException e) {
					logger.info("�����Ƚ��ڲ�ѯͨѶ�쳣:"+e.getType()+e.getMessage());
					excep_type = e.getType();
					excep_message = e.getMessage();
					continue;
				}
				
				if(responseTermInfo != null){
					List<QueryTermResult> innerReponseResult = ((TermQueryQs50200)responseTermInfo).getResponseResult();
					reponseErrCode = responseTermInfo.getErrorcode();
					reponseErrMsg = responseTermInfo.getErrormsg();
					if (!"0".equals(reponseErrCode)) {// ��Ϣ�ķ����벻��ȷ��0������ȷ�������Ĵ�����ȷ
						logger.error("�����Ƚ��ڲ�ѯ���ش������:" + reponseErrCode);
						isNeedReSend = true;
					}else{
						isNeedReSend = false;
						queryResult = (innerReponseResult == null || innerReponseResult.isEmpty()) ? null : innerReponseResult.get(0);
					}
				}else{//û�еõ���Ӧ����
					isNeedReSend = true;
				}
				
				//����õ�����Ӧû��ͨ��У�飬�����·�������
				if (isNeedReSend) {// ��Ҫ���·���
					logger.info("�����Ƚ��ڲ�ѯ[50200]�ط����󣬵�" + (i+1) + "��");
					Thread.sleep(EhandUtil.reSendSleep);// ���5�����·���
				} else {
					break;
				}
				
			}
			
			if(excep_type != -1){//ͨѶ���̷����쳣
				throw new LotteryException(excep_type,excep_message);
			}
			
			if(StringUtils.isNotEmpty(reponseErrCode) && !"0".equals(reponseErrCode)){//����ķ�����
				throw new LotteryException(EhandUtil.E_07_CODE,EhandUtil.E_07_DESC.replaceAll("CODE", reponseErrCode==null?"":reponseErrCode).replaceAll("MSG", reponseErrMsg==null?"":reponseErrMsg));
			}
			
			//��ȷ�ĵõ�����Ӧ���ݣ�����Ӧ���ת��Ϊ���ڶ���
			if(queryResult != null){//�õ�����Ӧ����
				eTerms = new EhandTermBussiModel();
				eTerms.setLotteryId(EhandUtil.zzyToLottery(queryResult.getLotteryId()));
				eTerms.setEhandLotteryId(queryResult.getLotteryId());
				eTerms.setIssue(queryResult.getTermNo());
				eTerms.setStartTime(queryResult.getStartTimeStamp());
				eTerms.setEndTime(queryResult.getEndTimeStamp());
				eTerms.setStatus(Integer.parseInt(queryResult.getStatus()));
			}else{
				if("0".equals(reponseErrCode)){
					throw new LotteryException(EhandUtil.E_09_CODE,EhandUtil.E_09_DESC);
				}else{
					throw new LotteryException(excep_type,excep_message);
				}
			}
			
		}catch(LotteryException ex){
			logger.error("50200,��ѯ���ڷ����쳣:"+ex.getType()+":"+ex.getMessage());
			throw ex;
		}catch(Exception e){
			logger.error("50200,��ѯ���ڳ���:", e);
			throw new LotteryException(EhandUtil.E_08_CODE,EhandUtil.E_08_DESC);
		}
		return eTerms;
	}
	
	/**
	 * 
	 * Title: QueryEkjgg50203<br>
	 * Description: <br>
	 *              <br>��ѯ��������
	 * @param betLotteryId Ͷעϵͳ����
	 * @param termNo ����
	 * @return
	 * @exception LotteryException
	 *                            <br>EhandUtil.E_01_CODE,��������ͨѶ����
	 *                            <br>EhandUtil.E_02_CODE,��������ͨѶ���ص�HTTP��Ӧ״̬����ȷ
	 *                            <br>EhandUtil.E_03_CODE,��������ͨѶ��ȷ���ص���Ϣ��������
	 *                            <br>EhandUtil.E_04_CODE,�����������ȷ
	 *                            <br>EhandUtil.E_07_CODE,�������󷵻ش�����[CODE]
	 *                            <br>EhandUtil.E_08_CODE,��������̷��������쳣
	 *                            <br>EhandUtil.E_09_CODE,��ѯ���󷵻ص�û�з�����Ϣ��
	 */
	public static EhandTermBussiModel QueryEkjgg50203(int betLotteryId,String termNo) throws LotteryException{
		EhandTermBussiModel eTerms = null;
		boolean isNeedReSend = false;//�Ƿ���Ҫ���·���,true��ʾ��Ҫ���·���
		QueryTermResult queryResult = null;//���󷵻ص���Ӧ����
		String reponseErrCode = "";
		String reponseErrMsg = "";
		int excep_type = -1;
		String excep_message = "";
		try{
			String eLotteryId =  EhandUtil.lotteryToZzy.get(betLotteryId);//ת���������ȵĲ���
			if(StringUtils.isEmpty(eLotteryId) || StringUtils.isEmpty(termNo)){
				logger.info("�����ȿ������������ѯ[50203]�������Ϊ��!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//�������󲢵õ���Ӧ���
			for (int i = 0; i < EhandUtil.reSendNum; i++) {
				reponseErrCode = "";
				excep_type = -1;
				excep_message = "";
				
				// �������󲢵õ�����ķ�������
				List<QueryTerm> requestModelInfo = new ArrayList<QueryTerm>();
				requestModelInfo.add(new QueryTerm(eLotteryId,termNo));
				//�������󲢵õ��ظ���Ϣ
				EhandDataPack responseTermInfo = null;
				
				try {
					responseTermInfo = new KjggQueryQs50203(requestModelInfo).writePack();
				} catch (LotteryException e) {
					logger.info("�����ȿ��������ѯͨѶ�쳣:"+e.getType()+e.getMessage());
					excep_type = e.getType();
					excep_message = e.getMessage();
					continue;
				}
				
				//У�鷵�ص���Ӧ�Ƿ���ȷ
				if(responseTermInfo != null){//�õ�����Ӧ����
					List<QueryTermResult> innerReponseResult = ((KjggQueryQs50203)responseTermInfo).getResponseResult();
					reponseErrCode = responseTermInfo.getErrorcode();
					reponseErrMsg = responseTermInfo.getErrormsg();
					if (!"0".equals(reponseErrCode)) {// ��Ϣ�ķ����벻��ȷ��0������ȷ�������Ĵ�����ȷ
						logger.error("�����ȿ������������ѯ���ش������:" + reponseErrCode);
						isNeedReSend = true;
					}else{
						isNeedReSend = false;
						queryResult = (innerReponseResult == null || innerReponseResult.isEmpty()) ? null : innerReponseResult.get(0);
					}
				}else{//û�еõ���Ӧ����
					isNeedReSend = true;
				}

				// ����õ�����Ӧû��ͨ��У�飬�����·�������
				if (isNeedReSend) {// ��Ҫ���·���
					logger.info("�����ȿ������������ѯ�ط����󣬵�" + (i+1) + "��");
					isNeedReSend = false;
					Thread.sleep(EhandUtil.reSendSleep);// ���5�����·���
				} else {
					break;
				}
			}
			
			if(excep_type != -1){//ͨѶ���̷����쳣
				throw new LotteryException(excep_type,excep_message);
			}
			
			if(StringUtils.isNotEmpty(reponseErrCode) && !"0".equals(reponseErrCode)){//����ķ�����
				throw new LotteryException(EhandUtil.E_07_CODE,EhandUtil.E_07_DESC.replaceAll("CODE", reponseErrCode==null?"":reponseErrCode).replaceAll("MSG", reponseErrMsg==null?"":reponseErrMsg));
			}
			
			//��ȷ�ĵõ�����Ӧ���ݣ�����Ӧ���ת��Ϊ���ڶ���
			if(queryResult != null){//�õ�����Ӧ����
				eTerms = new EhandTermBussiModel();
				eTerms.setLotteryId(EhandUtil.zzyToLottery(queryResult.getLotteryId()));
				eTerms.setEhandLotteryId(queryResult.getLotteryId());
				eTerms.setIssue(queryResult.getTermNo());
				eTerms.setBonuscode(queryResult.getLotteryResult());
			}else{
				if("0".equals(reponseErrCode)){
					throw new LotteryException(EhandUtil.E_09_CODE,EhandUtil.E_09_DESC);
				}else{
					throw new LotteryException(excep_type,excep_message);
				}
			}
			
		}catch(LotteryException ex){
			logger.error("50203,���������ѯ�����쳣:"+ex.getType()+":"+ex.getMessage());
			throw ex;
		}catch(Exception e){
			logger.error("50203,���������ѯ�����쳣:", e);
			throw new LotteryException(EhandUtil.E_08_CODE,EhandUtil.E_08_DESC);
		}
		return eTerms;
	}
	
	/**
	 * 
	 * Title: QueryCashResult50206<br>
	 * Description: <br>
	 *              <br>�н����ݲ�ѯ�ӿ�,�÷�����û�о������ʹ��
	 * @param betLotteryId
	 * @param termNo
	 * @return
	 * @exception LotteryException
	 *                            <br>EhandUtil.E_01_CODE,��������ͨѶ����
	 *                            <br>EhandUtil.E_02_CODE,��������ͨѶ���ص�HTTP��Ӧ״̬����ȷ
	 *                            <br>EhandUtil.E_03_CODE,��������ͨѶ��ȷ���ص���Ϣ��������
	 *                            <br>EhandUtil.E_04_CODE,�����������ȷ
	 *                            <br>EhandUtil.E_07_CODE,�������󷵻ش�����[CODE]
	 *                            <br>EhandUtil.E_08_CODE,��������̷��������쳣
	 *                            
	 */
	public static List<CashResult> QueryCashResult50206(int betLotteryId,String termNo) throws LotteryException{
		boolean isNeedReSend = false;//�Ƿ���Ҫ���·���,true��ʾ��Ҫ���·���
		List<CashResult> queryResult = null;//���󷵻ص���Ӧ����
		String reponseErrCode = "";
		String reponseErrMsg = "";
		int excep_type = -1;
		String excep_message = "";
		try{
			String eLotteryId =  EhandUtil.lotteryToZzy.get(betLotteryId);//ת���������ȵĲ���
			
			if(StringUtils.isEmpty(eLotteryId) || StringUtils.isEmpty(termNo)){
				logger.info("�������н����ݲ�ѯ����[50203]�������Ϊ��!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//�������󲢵õ���Ӧ���
			for (int i = 0; i < EhandUtil.reSendNum; i++) {
				reponseErrCode = "";
				excep_type = -1;
				excep_message = "";
				// �������󲢵õ�����ķ�������
				List<QueryTerm> requestModelInfo = new ArrayList<QueryTerm>();
				requestModelInfo.add(new QueryTerm(eLotteryId,termNo));
				EhandDataPack responseTermInfo = null;
				
				try {
					responseTermInfo = new CashQueryQs50206(requestModelInfo).writePack();
				} catch (LotteryException e) {
					logger.info("�������н����ݲ�ѯͨѶ�쳣:"+e.getType()+e.getMessage());
					excep_type = e.getType();
					excep_message = e.getMessage();
					continue;
				}
				
				//У�鷵�ص���Ӧ�Ƿ���ȷ
				if(responseTermInfo != null){//�õ�����Ӧ����
					reponseErrCode = responseTermInfo.getErrorcode();
					reponseErrMsg = responseTermInfo.getErrormsg();
					if (!"0".equals(reponseErrCode)) {// ��Ϣ�ķ����벻��ȷ��0������ȷ�������Ĵ�����ȷ
						logger.error("�������н����ݲ�ѯ���ش������:" + reponseErrCode);
						isNeedReSend = true;
					}else{
						isNeedReSend = false;
						queryResult = ((CashQueryQs50206)responseTermInfo).getCashResultList();
					}
				}else{//û�еõ���Ӧ����
					isNeedReSend = true;
				}

				// ����õ�����Ӧû��ͨ��У�飬�����·�������
				if (isNeedReSend) {// ��Ҫ���·���
					logger.info("�������н����ݲ�ѯ��ѯ�ط����󣬵�" + (i+1) + "��");
					Thread.sleep(EhandUtil.reSendSleep);// ���5�����·���
				} else {
					break;
				}
			}
			
			if(excep_type != -1){//ͨѶ�������쳣����
				throw new LotteryException(excep_type,excep_message);
			}
			
			if(StringUtils.isNotEmpty(reponseErrCode) && !"0".equals(reponseErrCode)){//����ķ�����
				throw new LotteryException(EhandUtil.E_07_CODE,EhandUtil.E_07_DESC.replaceAll("CODE", reponseErrCode==null?"":reponseErrCode).replaceAll("MSG", reponseErrMsg==null?"":reponseErrMsg));
			}
			
		}catch(LotteryException ex){
			logger.error("50206,�������н����ݲ�ѯ�����쳣:"+ex.getType());
			throw ex;
		}catch(Exception e){
			logger.error("50206,�������н����ݲ�ѯ�����쳣:", e);
			throw new LotteryException(EhandUtil.E_08_CODE,EhandUtil.E_08_DESC);
		}
		return queryResult;
	}
	
	/**
	 * 
	 * Title: QueryEtermSale50208<br>
	 * Description: <br>
	 *             <br>��ѯ���ڵ��������
	 * @param betLotteryId
	 * @param termNo
	 * @return
	 * @exception LotteryException
	 *                            <br>EhandUtil.E_01_CODE,��������ͨѶ����
	 *                            <br>EhandUtil.E_02_CODE,��������ͨѶ���ص�HTTP��Ӧ״̬����ȷ
	 *                            <br>EhandUtil.E_03_CODE,��������ͨѶ��ȷ���ص���Ϣ��������
	 *                            <br>EhandUtil.E_04_CODE,�����������ȷ
	 *                            <br>EhandUtil.E_07_CODE,�������󷵻ش�����[CODE]
	 *                            <br>EhandUtil.E_08_CODE,��������̷��������쳣
	 *                            <br>EhandUtil.E_09_CODE,��ѯ���󷵻ص�û�з�����Ϣ��
	 */
	public static EhandTermBussiModel QueryEtermSale50208(int betLotteryId,String termNo) throws LotteryException{
		boolean isNeedReSend = false;//�Ƿ���Ҫ���·���,true��ʾ��Ҫ���·���
		EhandTermBussiModel eTerms = null;
		TermSaleResult queryResult = null;
		String reponseErrCode = "";
		String reponseErrMsg = "";
		int excep_type = -1;
		String excep_message = "";
		try{
			String eLotteryId =  EhandUtil.lotteryToZzy.get(betLotteryId);//ת���������ȵĲ���
			if(StringUtils.isEmpty(eLotteryId) || StringUtils.isEmpty(termNo)){
				logger.info("�����Ȳ��ڵ����������ѯ����[50208]�������Ϊ��!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//�������󲢵õ���Ӧ���
			for (int i = 0; i < EhandUtil.reSendNum; i++) {
				reponseErrCode = "";
				excep_type = -1;
				excep_message = "";
				
				// �������󲢵õ�����ķ�������
				EhandDataPack responseTermInfo = null;
				List<QueryTerm> requestModelInfo = new ArrayList<QueryTerm>();
				requestModelInfo.add(new QueryTerm(eLotteryId,termNo));
				
				try {
					responseTermInfo = new TermSaleQs50208(requestModelInfo).writePack();
				} catch (LotteryException e) {
					logger.info("�����Ȳ��ڵ����������ѯͨѶ�쳣:"+e.getType()+e.getMessage());
					excep_type = e.getType();
					excep_message = e.getMessage();
					continue;
				}

				// У������Ľ�����������Ƿ����·�������
				if (responseTermInfo != null) {// �õ�����Ӧ����
					List<TermSaleResult> innerReponseResult = ((TermSaleQs50208)responseTermInfo).getTermSaleResult();
					reponseErrCode = responseTermInfo.getErrorcode();
					reponseErrMsg = responseTermInfo.getErrormsg();
					if (!"0".equals(reponseErrCode)) {// ��Ϣ�ķ����벻��ȷ��0������ȷ�������Ĵ�����ȷ
						logger.error("�����Ȳ��ڵ����������ѯ���ش������:" + reponseErrCode);
						isNeedReSend = true;
					}else{
						isNeedReSend = false;
						queryResult = (innerReponseResult == null || innerReponseResult.isEmpty()) ? null : innerReponseResult.get(0);
					}
				} else {// û�еõ���Ӧ����
					isNeedReSend = true;
				}

				// ����õ�����Ӧû��ͨ��У�飬�����·�������
				if (isNeedReSend) {// ��Ҫ���·���
					logger.info("�����Ȳ��ڵĽ������������ѯ�ط����󣬵�" + (i+1) + "��");
					isNeedReSend = false;
					Thread.sleep(EhandUtil.reSendSleep);// ���5�����·���
				} else {
					break;
				}
			}
			
			if(excep_type != -1){//ͨѶ���̷����쳣
				throw new LotteryException(excep_type,excep_message);
			}
			
			if(StringUtils.isNotEmpty(reponseErrCode) && !"0".equals(reponseErrCode)){//����ķ�����
				throw new LotteryException(EhandUtil.E_07_CODE,EhandUtil.E_07_DESC.replaceAll("CODE", reponseErrCode==null?"":reponseErrCode).replaceAll("MSG", reponseErrMsg==null?"":reponseErrMsg));
			}
			
			//��ȷ�ĵõ�����Ӧ���ݣ�����Ӧ���ת��Ϊ���ڶ���
			if(queryResult != null){//�õ�����Ӧ����
				eTerms = new EhandTermBussiModel();
				eTerms.setLotteryId(EhandUtil.zzyToLottery(queryResult.getLotteryId()));//תΪͶעϵͳ�Ĳ���id
				eTerms.setEhandLotteryId(queryResult.getLotteryId());
				eTerms.setIssue(queryResult.getTermNo());
				eTerms.setSalemoney(queryResult.getSaleMoney());
				eTerms.setBonusmoney(queryResult.getBonusMoney());
			}else{
				if("0".equals(reponseErrCode)){
					throw new LotteryException(EhandUtil.E_09_CODE,EhandUtil.E_09_DESC);
				}else{
					throw new LotteryException(excep_type,excep_message);
				}
			}
		}catch(LotteryException ex){
			logger.error("50208,�����Ȳ��ڵĽ������������ѯ�����쳣:"+ex.getType()+":"+ex.getMessage());
			throw ex;
		}catch(Exception e){
			logger.error("50208,�����Ȳ��ڵĽ������������ѯ�����쳣:", e);
			throw new LotteryException(EhandUtil.E_08_CODE,EhandUtil.E_08_DESC);
		}
		
		return eTerms;
	}
	
	/**
	 * 
	 * Title: QueryEterm50209<br>
	 * Description: <br>
	 *              <br>���������ȵĲ��ֺͲ��ڻ�ȡ�����Ȳ��ڵ�״̬
	 * @param betLotteryId Ͷעϵͳ�Ĳ���
	 * @param termNo ����
	 * @return
	 * @exception LotteryException
	 *                            <br>EhandUtil.E_01_CODE,��������ͨѶ����
	 *                            <br>EhandUtil.E_02_CODE,��������ͨѶ���ص�HTTP��Ӧ״̬����ȷ
	 *                            <br>EhandUtil.E_03_CODE,��������ͨѶ��ȷ���ص���Ϣ��������
	 *                            <br>EhandUtil.E_04_CODE,�����������ȷ
	 *                            <br>EhandUtil.E_07_CODE,�������󷵻ش�����[CODE]
	 *                            <br>EhandUtil.E_08_CODE,��������̷��������쳣
	 *                            <br>EhandUtil.E_09_CODE,��ѯ���󷵻ص�û�з�����Ϣ��
	 *                            
	 */
	public static EhandTermBussiModel QueryEterm50209(int betLotteryId,String termNo) throws LotteryException{
		boolean isNeedReSend = false;//�Ƿ���Ҫ���·���,true��ʾ��Ҫ���·���
		QueryTermResult queryResult = null;//���󷵻ص���Ӧ����
		EhandTermBussiModel eTerms = null;
		String reponseErrCode = "";
		String reponseErrMsg = "";
		int excep_type = -1;
		String excep_message = "";
		try{
			String eLotteryId =  EhandUtil.lotteryToZzy.get(betLotteryId);//ת���������ȵĲ���
			
			if(StringUtils.isEmpty(eLotteryId) || StringUtils.isEmpty(termNo)){
				logger.info("�����Ƚ���״̬��ѯ�������Ϊ��[50209]!");
				throw new LotteryException(EhandUtil.E_04_CODE,EhandUtil.E_04_DESC);
			}
			
			//�������󲢵õ���Ӧ���
			for (int i = 0; i < EhandUtil.reSendNum; i++) {
				reponseErrCode = "";
				excep_type = -1;
				excep_message = "";
				
				//�������󲢵õ�����ķ�������
				List<QueryTerm> requestModelInfo = new ArrayList<QueryTerm>();
				requestModelInfo.add(new QueryTerm(eLotteryId,termNo));
				
				//�������󲢵õ��ظ���Ϣ
				EhandDataPack responseTermInfo = null;
				
				try {
					responseTermInfo = new TermQueryQs50209(requestModelInfo).writePack();
				} catch (LotteryException e) {
					logger.info("�����Ȳ��ڵĽ���״̬�����ѯͨѶ�쳣:"+e.getType()+e.getMessage());
					excep_type = e.getType();
					excep_message = e.getMessage();
					continue;
				}
				
				if(responseTermInfo != null){
					List<QueryTermResult> innerReponseResult = ((TermQueryQs50209)responseTermInfo).getResponseResult();
					reponseErrCode = responseTermInfo.getErrorcode();
					reponseErrMsg = responseTermInfo.getErrormsg();
					if (!"0".equals(reponseErrCode)) {// ��Ϣ�ķ����벻��ȷ��0������ȷ�������Ĵ�����ȷ
						logger.error("�����Ȳ��ڵĽ��������ѯ���ش������:" + reponseErrCode);
						isNeedReSend = true;
					}else{
						isNeedReSend = false;
						queryResult = (innerReponseResult == null || innerReponseResult.isEmpty()) ? null : innerReponseResult.get(0);
					}
					
				}else{//û�еõ���Ӧ����
					isNeedReSend = true;
				}
				
				//����õ�����Ӧû��ͨ��У�飬�����·�������
				if (isNeedReSend) {// ��Ҫ���·���
					logger.info("�����Ƚ���״̬��ѯ�ط����󣬵�" + (i+1) + "��");
					Thread.sleep(EhandUtil.reSendSleep);// ���5�����·���
				} else {
					break;
				}
				
			}
			
			if(excep_type != -1){//ͨѶ���̷����쳣
				throw new LotteryException(excep_type,excep_message);
			}
			
			if(StringUtils.isNotEmpty(reponseErrCode) && !"0".equals(reponseErrCode)){//����ķ�����
				throw new LotteryException(EhandUtil.E_07_CODE,EhandUtil.E_07_DESC.replaceAll("CODE", reponseErrCode==null?"":reponseErrCode).replaceAll("MSG", reponseErrMsg==null?"":reponseErrMsg));
			}
			
			//��ȷ�ĵõ�����Ӧ���ݣ�����Ӧ���ת��Ϊ���ڶ���
			if(queryResult != null){//�õ�����Ӧ����
				eTerms = new EhandTermBussiModel();
				eTerms.setLotteryId(EhandUtil.zzyToLottery(queryResult.getLotteryId()));
				eTerms.setEhandLotteryId(queryResult.getLotteryId());
				eTerms.setIssue(queryResult.getTermNo());
				eTerms.setStatus(Integer.parseInt(queryResult.getStatus()));
			}else{
				if("0".equals(reponseErrCode)){
					throw new LotteryException(EhandUtil.E_09_CODE,EhandUtil.E_09_DESC);
				}else{
					throw new LotteryException(excep_type,excep_message);
				}
			}
			
		}catch(LotteryException ex){
			logger.error("50209,�����Ȳ��ڵĽ���״̬�����ѯ�����쳣:"+ex.getType()+":"+ex.getMessage());
			throw ex;
		}catch(Exception e){
			logger.error("50209,�����Ȳ��ڵĽ���״̬�����ѯ�����쳣:", e);
			throw new LotteryException(EhandUtil.E_08_CODE,EhandUtil.E_08_DESC);
		}
		return eTerms;
	}
}
