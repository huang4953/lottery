/**
 * Title: DispatchPrizeInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: �ɷ�������ӿ�
 * @author gaoboqin
 * @date 2010-4-26 ����01:45:43
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import java.util.List;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.business.service.interf
 * DispatchPrizeInterf.java
 * DispatchPrizeInterf
 * �ɷ�����
 * @author gaoboqin
 * 2010-4-26 ����01:45:43
 * 
 */

public interface DispatchPrizeInterf {
	
	/**
	 * 
	 * Title: dispatchSingleOrder<br>
	 * Description: <br>
	 *            ���������ɷ�����<br>
	 * @param heMaiOrDaiGou ���������ʶ��0-���� 1-����
	 * @param chuPiaoorderId ��Ʊ������Id
	 * @param paiJiangOrder �ɽ�������id �Դ�����Ʊ�������ɽ�����һ�����Ժ����ɽ�������ָ�������Ķ���id
	 * @return ����#����#�������#���𼶱�#������#������
	 * @throws LotteryException<br>
	 *                          <br>510005:�ö�����Ӧ�Ĳ��ڲ����ɽ�ʱ��
	 *                          <br>510004:�ö����������ɽ�����
	 *                          <br>511001:�������
	 *                          <br>AccountService�ж�����˻������쳣
	 *                          
	 */
	public String dispatchSingleOrder(int heMaiOrDaiGou,String chuPiaoOrderId,String paiJiangOrder) throws LotteryException;
	
	/**
	 * 
	 * Title: dispatchMultiOrder<br>
	 * Description: <br>
	 *            �Բ��ֲ��ڵĶ�������ɷ�����<br>
	 * @param lotteryId ����
	 * @param termNo ����
	 * @param orderList �����б���
	 * @return ����#����#�������#���𼶱�#������#������
	 * @throws LotteryException<br>
	 *                          <br>510006:�ö�����Ӧ�Ĳ�����Ҫ�ɽ���������һ��
	 *                          <br>510005:�ö�����Ӧ�Ĳ��ڲ����ɽ�ʱ��
	 *                          <br>510004:�ö����������ɽ�����
	 *                          <br>511001:�������
	 *                          <br>AccountService�ж�����˻������쳣
	 */
	public List<String> dispatchMultiOrder(int lotteryId,String termNo,List<String> orderList) throws LotteryException;
	/**
	 * 
	 * Title: dispatchAutoOrder<br>
	 * Description: <br>
	 *            �Բ��ֺͲ��ڵ����ж����ɷ�����<br>
	 * @param lotteryId ����
	 * @param termNo ����
	 * @return ����#����#�������#���𼶱�#������#������
	 * @throws LotteryException<br>
	 *                          <br>510005:�ö�����Ӧ�Ĳ��ڲ����ɽ�ʱ��
	 *                          <br>510004:�ö����������ɽ�����
	 *                          <br>511001:�������
	 *                          <br>AccountService�ж�����˻������쳣
	 */
	public List<String> dispatchAutoOrder(int lotteryId,String termNo) throws LotteryException;

}
