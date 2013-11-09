/**
 * Title: CashPrizeInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-21 ����10:31:44
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.business.service.interf
 * CashPrizeInterf.java
 * CashPrizeInterf
 * �ҽ�
 * @author gaoboqin
 * 2010-4-21 ����10:31:44
 * 
 */

public interface CashPrizeInterf {
	
	/**
	 * 
	 * Title: cashSingleOrder<br>
	 * Description: <br>�Ե��������ҽ�,ֻ�Գ�Ʊ�ɹ��Ķ����ҽ�,�÷�����Ҫʵ�����²��裺
	 *              <br>���ݶ�����ȡ������Ϣ
	 *              <br>�ҽ�
	 *              <br>���²���״̬���Ƿ�ȫ�����꽱
	 *              <br>
	 * @param orderId  �������
	 * @return  0#����#����#�������#�н�����#���𼶱�#������#Ͷע���
	 * @throws LotteryException<br>
	 *                          <br>
	 *                          <br>510002:�ö�����Ӧ�Ĳ��ڲ����ҽ�ʱ��
	 *                          <br>510001:�ö��������϶ҽ�����
	 *                          <br>511001:�ҽ��������
	 *                          <br>AccountService�ж�����˻������쳣
	 *                          <br>
	 */
	public String cashSingleOrder(String orderId) throws LotteryException;
	/**
	 * 
	 * Title: cashMultiOrder<br>
	 * Description: <br>
	 *            �Զ�������ҽ�<br>
	 * @param lotteryId ����id
	 * @param termNo ����
	 * @param orderList ��������б�
	 * @return 
	 * 
	 * 		�ɹ�(0)ʧ��(1)��ʶ#����#����#�������#�н�����#���𼶱�#������#Ͷע���
	 * @throws LotteryException<br>
	 *                          <br>
	 *                          <br>510002:�ö�����Ӧ�Ĳ��ڲ����ҽ�ʱ��
	 *                          <br>510003:�ö�����Ӧ�Ĳ�����Ҫ�ҽ���������һ��
	 *                          <br>510001:�ö��������϶ҽ�����
	 *                          <br>511001:�ҽ��������
	 *                          <br>AccountService�ж�����˻������쳣
	 *                          <br>
	 */
	public List<String> cashMultiOrder(int lotteryId,String termNo,List<String> orderList) throws LotteryException;
	/**
	 * 
	 * Title: cashAutoOrder<br>
	 * Description: <br>
	 *            �����ҽ�<br>
	 * @param lotteryId ����id
	 * @param termNo ����
	 * @param cashNum 
	 *         cashTotalNum,�ܹ����Զҽ�������
	 *         cashCurNum����ǰ�Ѿ����������
	 *         cashPersent������İٷֱ�,����һλС��
	 * @param dbLogMap �����û���Ϣ
	 * @return 
	 * �ɹ�(0)ʧ��(1)��ʶ#����#����#�������#�н�����#���𼶱�#������#Ͷע���
	 * 	    int total_orders = 0;//�ܵĶ�����
	 *		long total_tz_prize = 0L; //�ܶ�����Ͷע���
	 *		int sucess_orders = 0;//�ɹ��ҽ�������
	 *		long sucess_tz_prize = 0L;//�ɹ��ҽ�Ͷע���
	 *		int fail_orders = 0;//ʧ�ܶҽ�������
	 *		long fail_tz_prize = 0L;//ʧ�ܶҽ�Ͷע���
	 *		int zj_orders = 0;//�н�������
	 *		long zj_prize = 0L;//�н�������
	 * @throws LotteryException<br>
	 *                         <br>
	 *                          <br>510002:�ö�����Ӧ�Ĳ��ڲ����ҽ�ʱ��
	 *                          <br>510001:�ö��������϶ҽ�����
	 *                          <br>511001:�ҽ��������
	 *                          <br>AccountService�ж�����˻������쳣
	 *                          <br> 
	 */
	public Map<String,String> cashAutoOrder(int lotteryId,String termNo,Map<String,String> cashNum,Map<String,String> dbLogMap) throws LotteryException;
	/**
	 * 
	 * Title: dealNotTicketOrder<br>
	 * Description: <br>
	 *              <br>�Զҽ����ڵ���һ�ڵ�׷�Ŵ��������޺ź�׷��תͶע�Ĵ���
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
	 */
	public Map<String,String> dealNotTicketOrder(int lotteryId,String termNo,Map<String,String> dbLogMap) throws LotteryException;
	
	/**
	 * 
	 * Title: updateCashTermStatus<br>
	 * Description: <br>
	 *              <br>�ҽ���ɣ�����׷���޺Ŵ�����Ĳ���״̬�޸�
	 * @param lotteryId
	 * @param termNo
	 * @param dbLogMap
	 * @return
	 *         old_status,����ԭʼ״̬
	 *         new_status,�޸ĺ�Ĳ���״̬
	 * @throws LotteryException
	 */
	public Map<String,String> updateCashTermStatus(int lotteryId,String termNo,Map<String,String> dbLogMap) throws LotteryException;
	
	
}
