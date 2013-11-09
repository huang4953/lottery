/**
 * Title: DrawMoneyInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-27 ����11:31:14
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import java.util.HashMap;
import java.util.Map;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.business.service.interf
 * DrawMoneyInterf.java
 * DrawMoneyInterf
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-4-27 ����11:31:14
 * 
 */

public interface DrawMoneyInterf {
	/*
	 * �����쳣
	 */
	public static final int E_01_CODE = 521001;
	public static final String E_01_DESC = "�������쳣��";
	
	/*
	 * ҵ���쳣
	 */
	public static final int E_02_CODE = 520001;
	public static final String E_02_DESC = "[1]��������ȷ(2)��";
	
	public static final int E_03_CODE = 520002;
	public static final String E_03_DESC = "���ּ�¼�����ڣ�";
	
	public static final int E_04_CODE = 520003;
	public static final String E_04_DESC = "���ּ�¼�Ѿ�������[1]��";
	
	public static final int E_06_CODE=699001;
	public static final String E_06_DESC="���ÿ�Ǯ���ֽӿ�webservice����";
	/*
	 *��Ǯ�ӿ������쳣
	 */
	/**
	 * 
	 * Title: requestDrawMoney<br>
	 * Description: <br>
	 *              <br>�ύ������������
	 * @param userId �û��˻�id 
	 * @param bank �������� ����Ϊ��
	 * @param bankprovince ������������ʡ ����Ϊ��
	 * @param bankcity �����������ڳ��� ����Ϊ��
	 * @param bankname ���������� ����Ϊ��
	 * @param bankcardid ���п��� ����Ϊ��
	 * @param cardusername �ֿ������� ����Ϊ��
	 * @param drawMoney ���ֽ���λΪ��
	 * @param procedurefee ������ ��λΪ��
	 * @param reason ����ԭ����Ϊ��
	 * @return ���ɵ�������ˮ��
	 * @throws LotteryException<br>
	 *                          <br>E_02_CODE
	 *                          <br>E_01_CODE
	 *                          <br>�����˻����쳣
	 */
	public String requestDrawPrizeMoney(long userId, String bank,
			String bankprovince, String bankcity, String bankname,
			String bankcardid, String cardusername, int drawMoney,
			int procedurefee, String reason) throws LotteryException;
	/**
	 * 
	 * Title: requestDrawPrizeMoney<br>
	 * Description: <br>
	 *              <br>�ͻ�������
	 * @param userId �û�id
	 * @param bank �������� ����Ϊ��
	 * @param bankprovince ������������ʡ ����Ϊ��
	 * @param bankcity �����������ڳ��� ����Ϊ��
	 * @param bankname ���������� ����Ϊ��
	 * @param bankcardid ���п��� ����Ϊ��
	 * @param cardusername �ֿ������� ����Ϊ��
	 * @param drawMoney  ���ֽ���λΪ��
	 * @param procedurefee ������ ��λΪ��
	 * @param reason ����ԭ����Ϊ��
	 * @param clientId �ͻ���id
	 * @param clientSeq �ͻ������ɵ���ˮ��
	 * @return ordered#clientId#clientSeq#userId#prizeAccount#reserve
	 * @throws LotteryException
	 */
	public String requestDrawPrizeMoney(long userId, String bank,
			String bankprovince, String bankcity, String bankname,
			String bankcardid, String cardusername, int drawMoney,
			int procedurefee, String reason,String clientId,String clientSeq) throws LotteryException;
	/**
	 * 
	 * Title: agreeDrayMoney<br>
	 * Description: <br>
	 *              <br>ͬ�⽱���������� �����ÿ�Ǯ���ֽӿ�
	 * @param drawId ������ˮ�� ����Ϊ��
	 * @param opUser ����Ա ����Ϊ��
	 * @param reason ͬ��ԭ�� ����Ϊ��
	 * @throws LotteryException<br>
	 *                          <br>E_02_CODE
	 *                          <br>E_03_CODE
	 *                          <br>E_04_CODE
	 *                          <br>E_01_CODE
	 *                          <br>�����˻����쳣
	 */
	public void agreeDrawPrizeMoney(String drawId,String opUser,String reason) throws LotteryException;
	/**
	 * 
	 * Title: rejectDrawMoney<br>
	 * Description: <br>
	 *              <br>�ܾ�������������
	 * @param drawId ������ˮ�� ����Ϊ��
	 * @param opUser ����Ա ����Ϊ��
	 * @param reason �ܾ�ԭ�� ����Ϊ��
	 * @throws LotteryException<br>
	 *                          <br>E_02_CODE
	 *                          <br>E_03_CODE
	 *                          <br>E_04_CODE
	 *                          <br>E_01_CODE
	 *                          <br>�����˻����쳣                       
	 */
	public void rejectDrawPrizeMoney(String drawId,String opUser,String reason) throws LotteryException;
	
	/**
	 * 
	 * Title: adjustAccount<br>
	 * Description: <br>
	 *            �������<br>
	 * @param userId �û��˻�id 
	 * @param adjustType ������ʶ��A:��ʶ�������� B:��ʶ������� ����Ϊ��
	 * @param adjustMoney ��������λΪ�� ����Ϊ����
	 * @param reason ����˵�� ���Ȳ��ܳ���20������ ����Ϊ��
	 * @param opUser ����Ա ����Ϊ��
	 * @return ������ˮ��
	 * @throws LotteryException
	 */
	public String adjustAccount(long userId,String adjustType,int adjustMoney,String reason,String opUser) throws LotteryException;
	
	/**
	 * 
	 * Title: clientAdjustAccount<br>
	 * Description: <br>
	 *             <br> �ͻ��˳�ֵ
	 * @param userId
	 * @param adjustMoney
	 * @param adjustFee
	 * @param clientId
	 * @param clientSeq
	 * @param reserve
	 * @return orderId#clientId#clientSeq#userId#time#fundsAccount#reserve
	 * @throws LotteryException
	 */
	public String clientAdjustAccount(long userId,int adjustMoney,int adjustFee,String clientId,String clientSeq,String reserve) throws LotteryException;
	
	

}
