/**
 * Title: DispatchPrizeService.java
 * @Package com.success.lottery.business.service.impl
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-26 ����02:28:08
 * @version V1.0
 */
package com.success.lottery.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.business.service.interf.DispatchPrizeInterf;
import com.success.lottery.business.service.interf.PrizeInnerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.TermLog;

/**
 * com.success.lottery.business.service.impl
 * DispatchPrizeService.java
 * DispatchPrizeService
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-4-26 ����02:28:08
 * 
 */

public class DispatchPrizeService implements DispatchPrizeInterf {
	private static Log logger = LogFactory.getLog(CashPrizeService.class);
	private static TermLog log = TermLog.getInstance("PJ");
	private PrizeInnerInterf innerService;

	/* (�� Javadoc)
	 *Title: dispatchAutoOrder
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.DispatchPrizeInterf#dispatchAutoOrder(int, java.lang.String)
	 */
	public List<String> dispatchAutoOrder(int lotteryId, String termNo)
			throws LotteryException {
		List<String> result = new ArrayList<String>();
		//У������Ƿ����ɽ�
		logger.debug("�ɽ�---�Զ�����---��ʼ---"+"����:"+lotteryId+"����:"+termNo);
		LotteryTermModel lotteryTermInfo = this.getInnerService().checkTermCanDispatch(lotteryId, termNo);
		logger.debug("�ɽ�---�Զ�����---����:"+lotteryId+"����:"+termNo+"�����ɽ�");
		//��ȡ�����ɽ��Ķ��������Ϣ
		List<String> orderList = this.getInnerService().getNotDispatchOrder(lotteryId, termNo);
		
       //ѭ���Զҽ�
		for(String singleOrder : orderList){//���δ���������ѭ���ڵķ������������ύ������
			try {
				String cashResult = this.getInnerService().dispatchPrize(lotteryId, termNo, singleOrder, false);
				result.add(cashResult);
				log.logInfo(lotteryId, termNo, singleOrder, cashResult);
			} catch (Exception e) {//���쳣���ܶ����׳�
				result.add(lotteryId+"#"+termNo+"#"+singleOrder+"#"+"##�ɽ�ʧ��");
				log.logInfo(lotteryId, termNo, singleOrder, "##�ɽ�ʧ��");
				logger.error(lotteryId+"#"+termNo+"#"+singleOrder+"�ɽ�ʧ��ԭ��:", e);
			}
		}
		
		//���²���״̬
		this.getInnerService().updateDispatchPrizeCompleteStatus(lotteryId, termNo, lotteryTermInfo.getWinStatus());
		logger.debug("�ɽ�---�Զ�����---���---"+"����:"+lotteryId+"����:"+termNo);
		return result;
	}

	/* (�� Javadoc)
	 *Title: dispatchMultiOrder
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 * @param orderList
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.DispatchPrizeInterf#dispatchMultiOrder(int, java.lang.String, java.util.List)
	 */
	public List<String> dispatchMultiOrder(int lotteryId, String termNo,
			List<String> orderList) throws LotteryException {
		List<String> result = new ArrayList<String>();
        //��ȡ������Ϣ
		logger.debug("�ɽ�---�������---��ʼ---"+"����:"+lotteryId+"����:"+termNo);
		LotteryTermModel lotteryTermInfo = this.getInnerService().checkTermCanDispatch(lotteryId, termNo);
		logger.debug("�ɽ�---�������---����:"+lotteryId+"����:"+termNo+"�����ɽ�");
		
		for(String singleOrder : orderList){//���δ���������ѭ���ڵķ������������ύ������
			try {
				String dispatchResult = this.getInnerService().dispatchPrize(lotteryId, termNo, singleOrder, true);
				result.add(dispatchResult);
				log.logInfo(lotteryId, termNo, singleOrder, dispatchResult);
			} catch (Exception e) {//���쳣���ܶ����׳�
				result.add(lotteryId+"#"+termNo+"#"+singleOrder+"#"+"##�ɽ�ʧ��");
				log.logInfo(lotteryId, termNo, singleOrder, "##�ɽ�ʧ��");
				logger.error(lotteryId+"#"+termNo+"#"+singleOrder+"�ɽ�ʧ��ԭ��:", e);
			}
		}
		this.getInnerService().updateDispatchPrizeCompleteStatus(lotteryId, termNo, lotteryTermInfo.getWinStatus());
		logger.debug("�ɽ�---�������---���---"+"����:"+lotteryId+"����:"+termNo);
		return result;
	}

	/*
	 * (�� Javadoc)
	*Title: dispatchSingleOrder
	*Description: 
	* @param heMaiOrDaiGou
	* @param chuPiaoorderId
	* @param paiJiangOrder
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.DispatchPrizeInterf#dispatchSingleOrder(int, java.lang.String, java.lang.String)
	 */
	public String dispatchSingleOrder(int heMaiOrDaiGou,String chuPiaoOrderId,String paiJiangOrder) throws LotteryException {
		logger.debug("�ɽ�---��������---��ʼ---"+paiJiangOrder);
		//�Ժ���ʹ�����Ʊ������һ����
		BetOrderDomain lotteryOrderInfo = this.getInnerService().getOrderInfo(chuPiaoOrderId);//��ȡ������Ϣ
		int lotteryId = lotteryOrderInfo.getLotteryId();//����
		String termNo = lotteryOrderInfo.getBetTerm();//����
		logger.debug("�ɽ�---��������---����:"+lotteryId+"����:"+termNo);
		LotteryTermModel lotteryTermInfo = this.getInnerService().checkTermCanDispatch(lotteryId, termNo);
		logger.debug("�ҽ�---��������---����:"+lotteryId+"����:"+termNo+"�����ɽ�");
		
		String dispatchPrize = "";
		try {
			if(heMaiOrDaiGou == 0){//����
				logger.debug("�ɽ�---��������-�����ɽ�");
				dispatchPrize = this.getInnerService().dispatchPrize(lotteryId, termNo, paiJiangOrder, false);
				
			}else if(heMaiOrDaiGou == 1){//����
				logger.debug("�ɽ�---��������-�����ɽ�");
				dispatchPrize = this.getInnerService().dispatchCoopPrize(lotteryId, termNo, chuPiaoOrderId, paiJiangOrder, false);
			}
			
			log.logInfo(lotteryId, termNo, paiJiangOrder, dispatchPrize);
		} catch (LotteryException e) {
			log.logInfo(lotteryId, termNo, paiJiangOrder, "##�ɽ�ʧ��");
			logger.error(lotteryId+"#"+termNo+"#"+paiJiangOrder+"�ɽ�ʧ��ԭ��:", e);
			throw e;
		}
		logger.debug("�ɽ����:"+dispatchPrize);
		
		this.getInnerService().updateDispatchPrizeCompleteStatus(lotteryId, termNo, lotteryTermInfo.getWinStatus());
		logger.debug("����:"+lotteryId+"����:"+termNo+"�ɽ��ɹ�");
		logger.debug("�ɽ�---��������---����---"+"");
		return new StringBuffer().append(lotteryId).append("#").append(termNo).append("#").append(paiJiangOrder).append("#").append(dispatchPrize).toString();
	}

	private PrizeInnerInterf getInnerService() {
		return innerService;
	}

	public void setInnerService(PrizeInnerInterf innerService) {
		this.innerService = innerService;
	}

}
