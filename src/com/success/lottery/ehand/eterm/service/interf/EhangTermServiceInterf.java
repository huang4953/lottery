/**
 * Title: EhangTermServiceInterf.java
 * @Package com.success.lottery.term.dao.interf
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-19 ����05:04:06
 * @version V1.0
 */
package com.success.lottery.ehand.eterm.service.interf;

import com.success.lottery.ehand.eterm.domain.EhandTermModel;
import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.term.dao.interf
 * EhangTermServiceInterf.java
 * EhangTermServiceInterf
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-19 ����05:04:06
 * 
 */

public interface EhangTermServiceInterf {
	public static final int E_1002_CODE = 311002;
	public static final String E_1002_DESC = "���²�����Ϣ����";
	public static final int E_1004_CODE = 311004;
	public static final String E_1004_DESC = "��ȡ������Ϣ����";
	
	/**
	 * 
	 * Title: getEhandTermInfoByBet<br>
	 * Description: <br>
	 *              <br>���ݲ��ֺͲ��ڻ�ȡehand�Ĳ�����Ϣ
	 * @param lotteryId Ͷעϵͳ����
	 * @param issue ����
	 * @return
	 * @throws LotteryException
	 */
	public EhandTermModel getEhandTermInfoByBet(int lotteryId,String issue) throws LotteryException;
	/**
	 * 
	 * Title: getEhandTermInfo<br>
	 * Description: <br>
	 *              <br>���ݲ��ֺͲ��ڻ�ȡehand�Ĳ�����Ϣ
	 * @param ehandLotteryId ehandϵͳ����
	 * @param issue
	 * @return
	 * @throws LotteryException
	 */
	public EhandTermModel getEhandTermInfo(String ehandLotteryId,String issue) throws LotteryException;
	/**
	 * 
	 * Title: updateEhandInfo<br>
	 * Description: <br>
	 *              <br>���²��ڵĿ�ʼʱ�䡢����ʱ�䡢����״̬
	 *              <br>�������ֿ�ѡ��һ�������ұ�����һ��
	 * @param lotteryId Ͷעϵͳ���֣������ָ����Ҫ���� 0
	 * @param ehandLotteryId ehand����,�����ָ����Ҫ����null
	 * @param issue ����
	 * @param startTime ��ʼʱ��
	 * @param endTime ����ʱ��
	 * @param printStart
	 * @param printEnd
	 * @param status ����״̬
	 * @return
	 * @throws LotteryException
	 */
	public int updateEhandInfo(int lotteryId,String ehandLotteryId,String issue,String startTime,String endTime,String printStart,String printEnd,int status) throws LotteryException;
	
	/**
	 * 
	 * Title: updateEhandInfo<br>
	 * Description: <br>
	 *              <br>���²��ڵĿ������
	 *              <br>�������ֿ�ѡ��һ�������ұ�����һ��
	 * @param lotteryId Ͷעϵͳ���֣������ָ����Ҫ���� 0
	 * @param ehandLotteryId ehand����,�����ָ����Ҫ����null
	 * @param issue ����
	 * @param bonuscode �������
	 * @return
	 * @throws LotteryException
	 */
	public int updateEhandInfo(int lotteryId,String ehandLotteryId,String issue,String bonuscode,int status) throws LotteryException;
	/**
	 * 
	 * Title: updateEhandInfo<br>
	 * Description: <br>
	 *              <br>���²��ڵ�״̬
	 *              <br>�������ֿ�ѡ��һ�������ұ�����һ��
	 * @param lotteryId Ͷעϵͳ���֣������ָ����Ҫ���� 0
	 * @param ehandLotteryId ehand����,�����ָ����Ҫ����null
	 * @param issue ����
	 * @param status ����״̬
	 * @return
	 * @throws LotteryException
	 */
	public int updateEhandInfo(int lotteryId,String ehandLotteryId,String issue,int status) throws LotteryException;
	/**
	 * 
	 * Title: updateEhandInfoMoney<br>
	 * Description: <br>
	 *              <br>���²��ڵ����۶�н���
	 *               <br>�������ֿ�ѡ��һ�������ұ�����һ��
	 * @param lotteryId Ͷעϵͳ���֣������ָ����Ҫ���� 0
	 * @param ehandLotteryId  ehand����,�����ָ����Ҫ����null
	 * @param issue ����
	 * @param salemoney ���۶�
	 * @param bonusmoney �н���
	 * @return
	 * @throws LotteryException
	 */
	public int updateEhandInfoMoney(int lotteryId,String ehandLotteryId,String issue,String salemoney,String bonusmoney) throws LotteryException;
	
	/**
	 * 
	 * Title: insertEhandMsgLog<br>
	 * Description: <br>
	 *              <br>��¼�����ȵ���Ϣ���������׳��쳣
	 * @param msgType
	 * @param msgId
	 * @param msgUserId
	 * @param msgCommand
	 * @param msgKey
	 * @param msgCode
	 * @param msgContent
	 * @param reserve
	 */
	public void insertEhandMsgLog(int msgType,String msgId,String msgUserId,String msgCommand,String msgKey,String msgCode,String msgContent,String reserve);

}
