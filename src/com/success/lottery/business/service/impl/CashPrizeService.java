/**
 * Title: CashPrizeService.java
 * @Package com.success.lottery.business.service.impl
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-23 ����04:51:40
 * @version V1.0
 */
package com.success.lottery.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.business.service.interf.CashPrizeInterf;
import com.success.lottery.business.service.interf.PrizeInnerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.TermLog;

/**
 * com.success.lottery.business.service.impl
 * CashPrizeService.java
 * CashPrizeService
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-4-23 ����04:51:40
 * 
 */

public class CashPrizeService implements CashPrizeInterf {
	private static Log logger = LogFactory.getLog(CashPrizeService.class);
	private static TermLog log = TermLog.getInstance("DJ");
	
	private PrizeInnerInterf innerService;

	/* (�� Javadoc)
	 *Title: cashAutoOrder
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.CashPrizeInterf#cashAutoOrder(int, java.lang.String)
	 */
	public Map<String,String> cashAutoOrder(int lotteryId, String termNo,Map<String,String> cashNum,Map<String,String> dbLogMap)
			throws LotteryException {
		//List<String> result = null;
		Map<String,String> resultMap = new HashMap<String,String>();
		//�ɹ�(0)ʧ��(1)��ʶ#����#����#�������#�н�����#���𼶱�#������#Ͷע���
		int total_orders = 0;//�ܵĶ�����
		long total_tz_prize = 0L; //�ܶ�����Ͷע���
		int sucess_orders = 0;//�ɹ��ҽ�������
		long sucess_tz_prize = 0L;//�ɹ��ҽ�Ͷע���
		int fail_orders = 0;//ʧ�ܶҽ�������
		long fail_tz_prize = 0L;//ʧ�ܶҽ�Ͷע���
		int zj_orders = 0;//�н�������
		long zj_prize = 0L;//�н�������
		
		try {
			this.dbLog(40004,String.valueOf(lotteryId), termNo, "", "", "",dbLogMap);//��ʼ
			//result = new ArrayList<String>();
			if(cashNum == null){
				cashNum = new HashMap<String,String>();
				cashNum.put("cashTotalNum", "0");//�ҽ�������
				cashNum.put("cashCurNum", "0");//��ǰ�Ѿ��ҽ�����
				cashNum.put("cashPersent", "0");//��ǰ�Ѿ��ҽ��İٷֱ�
			}
			//У������Ƿ��ܶҽ�
			logger.info("�ҽ�---�Զ�����---��ʼ---"+"����:"+lotteryId+"����:"+termNo);
			LotteryTermModel lotteryTermInfo = this.getInnerService().checkTermCanCash(lotteryId, termNo);//�жϲ����Ƿ���Զҽ�
			logger.info("�ҽ�---�Զ�����---����:"+lotteryId+"����:"+termNo+"���Զҽ�");
			
			/*
			 * 
			 * �����Ƶ�ڵõ��õ����������ֱ�Ӷҽ�����Ҫ�����Ƿ񲻼��öҽ��ŵĶ�����Ʊ���?
			 */
			this.getInnerService().checkNotTicketSucessOrder(lotteryId, termNo);//�жϲ�Ʊ�Ƿ���û��ȷ�ϵ�
			logger.info("�ҽ�---�Զ�����---����:"+lotteryId+"����:"+termNo+"û����Ҫ��Ʊȷ�ϵĶ��������Զҽ�");
			
			
			//��ȡ���Զҽ��Ķ��������Ϣ,��ȡ����Ϊ������״̬Ϊ4��5������״̬Ϊ0��99����������ģ�
			List<String> orderList = this.getInnerService().getNotCashOrder(lotteryId, termNo);

			if(orderList != null && orderList.size() > 0){
				total_orders = orderList.size();
				cashNum.put("cashTotalNum", String.valueOf(total_orders));
			}else{
				cashNum.put("cashPersent", String.valueOf(100.0));//�ٷֱ�����Ϊ100.0
			}
			
			//ѭ���Զҽ�
			Map<String,String> outResult = new HashMap<String,String>();
			outResult.put("A", "0");
			outResult.put("B", "0");
			int curNum = 0;
			log.logInfo(lotteryId, termNo, "��ʼѭ���ҽ�");
			for(String singleOrder : orderList){//���δ���������ѭ���ڵķ������������ύ������
				try {
					String cashResult = this.getInnerService().cashPrize(lotteryId, termNo, singleOrder, lotteryTermInfo,false,outResult);
					total_tz_prize += Long.parseLong(outResult.get("A"));
					sucess_orders++;
					sucess_tz_prize += Long.parseLong(outResult.get("A"));
					String [] prizeResult = cashResult.split("#");
					if(!prizeResult[1].equals("1")){//�н���
						zj_orders++;
						zj_prize += Long.parseLong(prizeResult[2]);
					}
					//result.add("0" + "#" + lotteryId + "#" + termNo + "#" + singleOrder+"#"+ cashResult+"#"+outResult.get("A"));
					log.logInfo(lotteryId, termNo, singleOrder, cashResult);
				} catch (Exception e) {//���쳣���ܶ����׳�
					total_tz_prize += Long.parseLong(outResult.get("A"));
					fail_orders++;
					fail_tz_prize += Long.parseLong(outResult.get("A"));
					
					//result.add("1"+"#"+lotteryId+"#"+termNo+"#"+singleOrder+"#"+"##0"+"#"+outResult.get("A"));
					log.logInfo(lotteryId, termNo, singleOrder, "##�ҽ�ʧ��!");
					logger.error(lotteryId+"#"+termNo+"#"+singleOrder+"�ҽ�ʧ��ԭ��:", e);
					this.dbLog(41004, String.valueOf(lotteryId), termNo, singleOrder, outResult.get("B"), e.getMessage(),dbLogMap);//��������ʧ��
				}
				cashNum.put("cashCurNum", String.valueOf(++curNum));
				String precent = new java.text.DecimalFormat("#.0").format(((curNum/(float)total_orders)*1000)/10);
				cashNum.put("cashPersent", precent);
			}
			log.logInfo(lotteryId, termNo, "����ѭ���ҽ�");
			logger.info("�ҽ�---�Զ�����---���---"+"����:"+lotteryId+"����:"+termNo);
		} catch (LotteryException e) {
			this.dbLog(41005, String.valueOf(lotteryId), termNo, "", "", e.getMessage(),dbLogMap);//ʧ�ܽ���
			throw e;
		}finally{
			resultMap.put("total_orders", String.valueOf(total_orders));
			resultMap.put("total_tz_prize", String.valueOf(total_tz_prize/100f));
			resultMap.put("sucess_orders", String.valueOf(sucess_orders));
			resultMap.put("sucess_tz_prize", String.valueOf(sucess_tz_prize/100f));
			resultMap.put("fail_orders", String.valueOf(fail_orders));
			resultMap.put("fail_tz_prize", String.valueOf(fail_tz_prize/100f));
			resultMap.put("zj_orders", String.valueOf(zj_orders));
			resultMap.put("zj_prize", String.valueOf(zj_prize/100f));
		}
		this.dbLog(40005, String.valueOf(lotteryId), termNo, "", "", "",dbLogMap);//�ɹ�����
		return resultMap;
	}

	/*
	 * (�� Javadoc)
	*Title: cashMultiOrder
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param orderList
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.CashPrizeInterf#cashMultiOrder(int, java.lang.String, java.util.List)
	 */
	public List<String> cashMultiOrder(int lotteryId,String termNo,List<String> orderList) throws LotteryException {
		List<String> result = new ArrayList<String>();
		// ��ȡ������Ϣ
		logger.debug("�ҽ�---�������---��ʼ---"+"����:"+lotteryId+"����:"+termNo);
		LotteryTermModel lotteryTermInfo = this.getInnerService().checkTermCanCash(lotteryId, termNo);
		logger.debug("�ҽ�---�������---����:"+lotteryId+"����:"+termNo+"���Կ���");
		Map<String,String> outResult = new HashMap<String,String>();
		outResult.put("A", "0");
		for(String singleOrder : orderList){//���δ���������ѭ���ڵķ������������ύ������
			try {
				String cashResult = this.getInnerService().cashPrize(lotteryId, termNo, singleOrder, lotteryTermInfo,true,outResult);
				result.add("0" + "#" + lotteryId + "#" + termNo + "#" + singleOrder+"#" + cashResult + "#" + outResult.get("A"));
				log.logInfo(lotteryId, termNo, singleOrder, cashResult);
			} catch (Exception e) {//���쳣���ܶ����׳�
				result.add("1"+lotteryId+"#"+termNo+"#"+singleOrder+"#"+"##0" + "#" + outResult.get("A"));
				log.logInfo(lotteryId, termNo, singleOrder, "##�ҽ�ʧ��!");
				logger.error(lotteryId+"#"+termNo+"#"+singleOrder+"�ҽ�ʧ��ԭ��:", e);
			}
		}
		Map<String,String> dbLogMap = new HashMap<String,String>();//�˴���־����û������
		dbLogMap.put("userId", "");
		dbLogMap.put("userName", "");
		dbLogMap.put("userKey", "");
		
		this.getInnerService().updateCashPrizeCompleteStatus(lotteryId, termNo);//���²��ڱ�״̬�����Ƿ��Ѿ��ҽ����
		logger.debug("�ҽ�---�������---���---"+"����:"+lotteryId+"����:"+termNo);
		return result;
	}

	/* (�� Javadoc)
	 *Title: cashSingleOrder
	 *Description: 
	 * @param ordrId
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.CashPrizeInterf#cashSingleOrder(java.lang.String)
	 */
	public String cashSingleOrder(String orderId) throws LotteryException {
		logger.info("�ҽ�---��������---��ʼ---"+orderId);
		BetOrderDomain lotteryOrderInfo = this.getInnerService().getOrderInfo(orderId);//��ȡ������Ϣ
		int lotteryId = lotteryOrderInfo.getLotteryId();//����
		String termNo = lotteryOrderInfo.getBetTerm();//����
		logger.info("�ҽ�---��������---����:"+lotteryId+"����:"+termNo);
		LotteryTermModel lotteryTermInfo = this.getInnerService().checkTermCanCash(lotteryId, termNo);//У������Ƿ���Զҽ�
		logger.info("�ҽ�---��������---����:"+lotteryId+"����:"+termNo+"���Զҽ�");
		String cashResult = "";
		Map<String,String> outResult = new HashMap<String,String>();
		outResult.put("A", "0");
		try {
			
			cashResult = this.getInnerService().cashPrize(lotteryId, termNo, orderId, lotteryTermInfo,false,outResult);
			log.logInfo(lotteryId, termNo, orderId, cashResult);
		} catch (LotteryException e) {
			log.logInfo(lotteryId, termNo, orderId, "##�ҽ�ʧ��");
			logger.error(lotteryId+"#"+termNo+"#"+orderId+"�ҽ�ʧ��ԭ��:", e);
			throw e;
		}
		logger.info("�ҽ����:"+cashResult);
		Map<String,String> dbLogMap = new HashMap<String,String>();//�˴���־����û������
		dbLogMap.put("userId", "");
		dbLogMap.put("userName", "");
		dbLogMap.put("userKey", "");
		this.getInnerService().updateCashPrizeCompleteStatus(lotteryId, termNo);
		logger.info("����:"+lotteryId+"����:"+termNo+"�ҽ��ɹ�");
		logger.info("�ҽ�---��������---����---"+orderId);
		return new StringBuffer().append("0").append(lotteryId).append("#").append(termNo).append("#").append(orderId).append("#").append(cashResult).append("#").append(outResult.get("A")).toString();
	}
	/**
	 * 
	 * Title: dbLog<br>
	 * Description: <br>
	 *              <br>���ݿ���־��¼
	 * @param logId
	 * @param userId
	 * @param userName
	 * @param userKey
	 * @param keyword1
	 * @param keyword2
	 * @param keyword3
	 * @param keyword4
	 * @param errorMessage
	 */
	private void dbLog(int logId,String keyword1,String keyword2,String keyword3,String keyword4,String errorMessage,Map<String,String> dbLogMap){
		try {
			Map<String,String> param = new HashMap<String,String>();
			param.put("userId", dbLogMap.get("userId"));
			param.put("userName", dbLogMap.get("userName"));
			param.put("userKey", dbLogMap.get("userKey"));
			param.put("keyword1", keyword1);
			param.put("keyword2", keyword2);
			if(StringUtils.isNotEmpty(keyword3)){
				param.put("keyword3", keyword3);
			}
			if(StringUtils.isNotEmpty(keyword4)){
				param.put("keyword4", keyword4);
			}
			if(StringUtils.isNotEmpty(errorMessage)){
				param.put("errorMessage", errorMessage);
			}
			
			OperatorLogger.log(logId, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * (�� Javadoc)
	*Title: dealNotTicketOrder
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param dbLogMap
	* @return
	 *         total_num,�ܹ����Դ���Ķ�����
	 *         sucess_num,�ɹ�����Ķ�����
	 *         fail_num,����ʧ�ܵĵ�����
	 *         sucess_bet_num,תΪͶע�Ķ�����
	 *         sucess_limit_num,תΪ�޺ŵĶ�����
	 *         nextTerm,����Ĳ���
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.CashPrizeInterf#dealNotTicketOrder(int, java.lang.String, java.util.Map)
	 */
	public Map<String, String> dealNotTicketOrder(int lotteryId, String termNo, Map<String, String> dbLogMap) throws LotteryException {
		Map<String,String> dealResult = new HashMap<String,String>();
		int total_num = 0;
		int sucess_num = 0;
		int fail_num = 0;
		int sucess_bet_num = 0;
		int sucess_limit_num = 0;
		String nextTerm = "";
		try {
			
			LotteryTermModel lotteryTermInfo = this.getInnerService().getTermInfo(lotteryId, termNo);
			nextTerm = lotteryTermInfo.getNextTerm();
			
			List<Integer> winStatus = new ArrayList<Integer>();
			winStatus.add(0);
			List<String> needDealOrders = this.getInnerService().queryOrderByStatus(lotteryId, nextTerm, 13, winStatus);
			
			logger.info("׷�Ŵ���---��ʼ---"+"����:"+lotteryId+"�ҽ�����:"+termNo+"׷�Ų���:"+nextTerm);
			this.dbLog(40007, String.valueOf(lotteryId), termNo, nextTerm, "", "", dbLogMap);//����׷�ſ�ʼ
			//ȡ�����޺���Ϣ
			String limitNumber = this.getInnerService().getTermInfo(lotteryId,nextTerm).getLimitNumber();
			
			if(needDealOrders != null){
				total_num = needDealOrders.size();
			}
			//ѭ������׷�Ŷ�����ѭ����ÿһ������һ������������
			log.logInfo(lotteryId, termNo, "��ʼѭ������׷��");
			for(String orderId : needDealOrders){
				try {
					//������δ�����Ʊ���е�׷�Ŷ���,ֻ����һ�ڵĴ���,�����޺ŵĴ���
					String result = this.getInnerService().dealNotTicketZhuiHao(lotteryId, nextTerm, limitNumber, orderId);
					if(StringUtils.isNotEmpty(result)){
						if("0".equals(result)){
							sucess_bet_num++;
						}else{
							sucess_limit_num++;
						}
						sucess_num++;
					}
					log.logInfo(lotteryId, termNo, orderId, result);
				} catch (Exception e) {
					fail_num++;
					log.logInfo(lotteryId, termNo, orderId, "ʧ��");
					this.dbLog(41007, String.valueOf(lotteryId), termNo, orderId, "", e.getMessage(), dbLogMap);
				}
			}
			log.logInfo(lotteryId, termNo, "����ѭ������׷��");
		} catch (Exception e) {
			this.dbLog(41008, String.valueOf(lotteryId), termNo, nextTerm, "", e.getMessage(), dbLogMap);
		}finally{
			dealResult.put("total_num", String.valueOf(total_num));
			dealResult.put("sucess_num", String.valueOf(sucess_num));
			dealResult.put("fail_num", String.valueOf(fail_num));
			dealResult.put("sucess_bet_num", String.valueOf(sucess_bet_num));
			dealResult.put("sucess_limit_num", String.valueOf(sucess_limit_num));
			dealResult.put("nextTerm", nextTerm);
		}
		logger.info("׷�Ŵ���---����---"+"����:"+lotteryId+"�ҽ�����:"+termNo+"׷�Ų���:"+nextTerm);
		this.dbLog(40008, String.valueOf(lotteryId), termNo, nextTerm, "", "",
				dbLogMap);
		return dealResult;
	}
	/*
	 * (�� Javadoc)
	*Title: updateCashTermStatus
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param dbLogMap
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.CashPrizeInterf#updateCashTermStatus(int, java.lang.String, java.util.Map)
	 */
	public Map<String, String> updateCashTermStatus(int lotteryId, String termNo, Map<String, String> dbLogMap) throws LotteryException {
		Map<String, String> upResult = new HashMap<String,String>();
		String old_status = "";
		String new_status = "";
		try {
			String result = this.getInnerService().updateCashPrizeCompleteStatus(lotteryId, termNo);
			if(StringUtils.isNotEmpty(result)){
				old_status = result.split("#")[0];
				new_status = result.split("#")[1];
			}
			log.logInfo(lotteryId, termNo, "�ҽ������޸ĳɹ�,ԭ״̬:"+old_status+"�޸ĺ�״̬:"+new_status);
		} catch (Exception e) {
			log.logInfo(lotteryId, termNo, "�ҽ������޸�ʧ��,ʧ��ԭ��:"+e);
			this.dbLog(41006, String.valueOf(lotteryId), termNo, "", "", e.getMessage(), dbLogMap);
		}finally{
			upResult.put("old_status", old_status);
			upResult.put("new_status", new_status);
		}
		this.dbLog(40006, String.valueOf(lotteryId), termNo, "", "", "", dbLogMap);
		return upResult;
	}

	private PrizeInnerInterf getInnerService() {
		return this.innerService;
	}

	public void setInnerService(PrizeInnerInterf innerService) {
		this.innerService = innerService;
	}
	
}
