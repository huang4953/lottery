/**
 * Title: LotteryTermSwitchServiceInterf.java
 * @Package com.success.lottery.term.service.interf
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-15 ����10:08:18
 * @version V1.0
 */
package com.success.lottery.term.service.interf;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.term.service.interf
 * LotteryTermSwitchServiceInterf.java
 * LotteryTermSwitchServiceInterf
 * �����л�����ӿ�
 * @author gaoboqin
 * 2010-4-15 ����10:08:18
 * 
 */

public interface LotteryTermSwitchServiceInterf {
	
	public static final int E_301101_CODE = 301101;
	public static final String E_301101_DESC = "�л�����ʱδ�ҵ����ֵķ��������ĵ�ǰ����Ϣ��";
	
	public static final int E_301102_CODE = 301102;
	public static final String E_301102_DESC = "�л�����ʱδ�ҵ����ֵķ�����������һ����Ϣ��";
	
	public static final int E_301103_CODE = 301103;
	public static final String E_301103_DESC = "�л�����ʱ���ݿⷢ������";
	
	public static final int E_301104_CODE = 301104;
	public static final String E_301104_DESC = "�л�����ʱ�����������";
	
	
	/**
	 * 
	 * Title: termSwitch<br>
	 * Description: <br>
	 *            �����л�<br>
	 * @throws LotteryException
	 */
	public void termSwitch() throws LotteryException;
	/**
	 * 
	 * Title: termSwitchByStartTime<br>
	 * Description: <br>
	 *              <br>������Ҫ����ǰ�ڵ�����״̬��δ������ʱ��Ĳ��ڵ�����״̬��Ϊ��������
	 * @throws LotteryException
	 */
	public void termSwitchByStartTime() throws LotteryException;

}
