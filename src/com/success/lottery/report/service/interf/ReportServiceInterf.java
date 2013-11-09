/**
 * Title: ReportServiceInterf.java
 * @Package com.success.lottery.report.service.interf
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-8-9 ����10:14:47
 * @version V1.0
 */
package com.success.lottery.report.service.interf;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.report.service.interf
 * ReportServiceInterf.java
 * ReportServiceInterf
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-8-9 ����10:14:47
 * 
 */

public interface ReportServiceInterf {
	
	public static final int E_01_CODE = 800001;
	public static final String E_01_DESC = "���ɱ���ʧ��";
	
	/**
	 * 
	 * Title: createPrizeReportSuper<br>
	 * Description: <br>
	 *              <br>���ɴ���͸�н�ͳ������
	 * @throws LotteryException
	 */
	public void createPrizeReportSuper() throws LotteryException;
	/**
	 * 
	 * Title: createPrizeReportBall<br>
	 * Description: <br>
	 *              <br>����ʤ�����н�ͳ������
	 * @throws LotteryException
	 */
	public void createPrizeReportBall() throws LotteryException;
	/**
	 * 
	 * Title: createPrizeReportArrange3<br>
	 * Description: <br>
	 *              <br>��������3�н�ͳ������
	 * @throws LotteryException
	 */
	public void createPrizeReportArrange3() throws LotteryException;
	/**
	 * 
	 * Title: createPrizeReportArrange5<br>
	 * Description: <br>
	 *              <br>��������5�н�ͳ������
	 * @throws LotteryException
	 */
	public void createPrizeReportArrange5() throws LotteryException;
	/**
	 * 
	 * Title: createPrizeReportSeven<br>
	 * Description: <br>
	 *              <br>�������ǲ��н�ͳ������
	 * @throws LotteryException
	 */
	public void createPrizeReportSeven() throws LotteryException;
	/**
	 * 
	 * Title: createPrizeReportDlc<br>
	 * Description: <br>���ɽ������ֲ��н�ͳ������
	 *             <br>
	 * @throws LotteryException
	 */
	public void createPrizeReportDlc() throws LotteryException;
	/**
	 * 
	 * Title: createPrizeReportBall6<br>
	 * Description: <br>
	 *              <br>����6����ȫ���н�ͳ������
	 * @throws LotteryException
	 */
	public void createPrizeReportBall6() throws LotteryException;
	/**
	 * 
	 * Title: createPrizeReportBall4<br>
	 * Description: <br>
	 *              <br>����4��������н�ͳ������
	 * @throws LotteryException
	 */
	public void createPrizeReportBall4() throws LotteryException;
	/**
	 * 
	 * Title: createAccountReport<br>
	 * Description: <br>
	 *              <br>�����˻�ͳ�Ʊ���,��ͳ����ȫ���ڳ��������ʱ��
	 * @throws LotteryException
	 */
	public void createAccountReport() throws LotteryException;

}
