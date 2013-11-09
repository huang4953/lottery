/**
 * Title: TicketExDealInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-8-5 ����10:53:00
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.business.service.interf
 * TicketExDealInterf.java
 * TicketExDealInterf
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-8-5 ����10:53:00
 * 
 */

public interface TicketExDealInterf {
	
	/*
	 * �����쳣Ϊ��Ʊ�쳣������Ҫ���쳣����
	 */
	public static final int E_550010_CODE = 550010;
	public static final String E_550010_DESC = "��Ʊ��Ӧ�Ķ����Ѿ��ҽ����������޸Ķ���״̬��";
	
	
	public static final int E_550001_CODE = 550001;
	public static final String E_550001_DESC = "��Ʊ�쳣����������쳣��";
	
	/**
	 * 
	 * Title: ticketExDeal<br>
	 * Description: <br>
	 *              <br>�쳣��Ʊ����
	 * @param ticketSequence ��Ʊ��ˮ��
	 * @param ticketStatus ��Ʊ״̬
	 * @param ticketId Ʊ��
	 * @param printerId ��Ʊ�����
	 * @param printResult ��Ʊ���
	 * @param ticketData ��Ʊ������
	 * @param ticketDataMD ��ƱժҪ
	 * @param ticketPassword Ʊ����
	 * @throws LotteryException
	 */
	public void ticketExDeal(String ticketSequence,int ticketStatus,String ticketId,
			String printerId, String printResult, String ticketData,
			String ticketDataMD, String ticketPassword) throws LotteryException;

}
