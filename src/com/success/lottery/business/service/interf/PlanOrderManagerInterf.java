/**
 * Title: PlanOrderManagerInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-5-18 ����09:47:49
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.business.service.interf
 * PlanOrderManagerInterf.java
 * PlanOrderManagerInterf
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-5-18 ����09:47:49
 * 
 */

public interface PlanOrderManagerInterf {
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
	public static final String E_03_DESC = "�������ܳ�����";
	
	public static final int E_04_CODE = 520003;
	public static final String E_04_DESC = "���������ڣ�";
	
	public static final int E_05_CODE = 520004;
	public static final String E_05_DESC = "��������ʧ�ܣ�";
	
	/**
	 * 
	 * Title: cancelAddOrder<br>
	 * Description: <br>
	 *              <br>׷�Ŷ���������ֻ����δ��Ʊ�Ķ����ſ��Գ�������
	 * @param orderId �������
	 * @throws LotteryException<br>
	 *                          <br>PlanOrderManagerInterf.E_01_CODE:�������쳣
	 *                          <br>PlanOrderManagerInterf.E_02_CODE:��������ȷ
	 *                          <br>PlanOrderManagerInterf.E_03_CODE:�������ܳ���
	 *                          <br>PlanOrderManagerInterf.E_04_CODE:����������
	 *                          <br>PlanOrderManagerInterf.E_05_CODE:��������ʧ��
	 *                          <br>�����˻����쳣
	 */
	public void cancelAddOrder(String orderId) throws LotteryException;

}
