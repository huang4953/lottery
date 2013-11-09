/**
 * Title: ReportQueryServiceInterf.java
 * @Package com.success.lottery.report.service.interf
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-8-10 ����09:38:06
 * @version V1.0
 */
package com.success.lottery.report.service.interf;

import java.util.Date;
import java.util.List;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.report.domain.ReportAccount;
import com.success.lottery.report.domain.ReportPrizeDomain;
import com.success.lottery.report.domain.ReportTransSortDomain;

/**
 * com.success.lottery.report.service.interf
 * ReportQueryServiceInterf.java
 * ReportQueryServiceInterf
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-8-10 ����09:38:06
 * 
 */

public interface ReportQueryServiceInterf {
	
	public static final int E_01_CODE = 800002;
	public static final String E_01_DESC = "��ѯ�������";
	
	/**
	 * 
	 * Title: getPrizeReportTerms<br>
	 * Description: <br>
	 *              <br>���ݲ��ֲ�ѯ�н�ͳ�Ʊ������Ѿ����ɵĲ����б�
	 * @param lotteryId
	 * @return
	 * @throws LotteryException
	 */
	public List<String> getPrizeReportTerms(int lotteryId) throws LotteryException;
	
	/**
	 * 
	 * Title: getPrizeReports<br>
	 * Description: <br>
	 *              <br>��ҳ��ѯ�н�ͳ�Ʊ�������
	 * @param lotteryId ����,������ȫ��
	 * @param beginTermNo ����,���Ϊ0��ѯȫ��
	 * @param endTermNo ����,���Ϊ0��ѯȫ��
	 * @param pageIndex 
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<ReportPrizeDomain> getPrizeReports(int lotteryId,String beginTermNo,String endTermNo,int pageIndex, int perPageNumber)  throws LotteryException;
	/**
	 * 
	 * Title: getPrizeReportsCount<br>
	 * Description: <br>
	 *              <br>��ҳ��ѯ�н�ͳ�Ƶ�����
	 * @param lotteryId
	 * @param beginTermNo
	 * @param endTermNo
	 * @return
	 * @throws LotteryException
	 */
	public int getPrizeReportsCount(int lotteryId,String beginTermNo,String endTermNo) throws LotteryException;
 	/**
	 * 
	 * Title: getPrizeReportInfo<br>
	 * Description: <br>
	 *              <br>���ݲ��ֵı���������ѯ�������ϸ��Ϣ
	 * @param lotteryId
	 * @param reportId
	 * @return
	 * @throws LotteryException
	 */
	public ReportPrizeDomain getPrizeReportInfo(int lotteryId,int reportId) throws LotteryException;
	
	/**
	 * 
	 * Title: getAccountReports<br>
	 * Description: <br>
	 *              <br>���ݿ�ʼ�յĽ����ղ�ѯ���ױ䶯����
	 * @param beginDate ��ʼ�� ��ʽyyyy-MM-dd
	 * @param endDate ������ ��ʽyyyy-MM-dd
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<ReportAccount> getAccountReports(String beginDate,String endDate,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getAccountReportsCount<br>
	 * Description: <br>
	 *              <br>���ݿ�ʼ�յĽ����ղ�ѯ���ױ䶯�������������
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws LotteryException
	 */
	public int getAccountReportsCount(String beginDate,String endDate) throws LotteryException;
	/**
	 * 
	 * Title: getReportTransSortInfos<br>
	 * Description: <br>
	 *              <br>��ѯ��������
	 * @param transType ��������
	 * @param maxSortNum �������
	 * @param beginDate ��ʼ����  ����Ϊ��
	 * @param endDate �������� ����Ϊ��
	 * @return
	 * @throws LotteryException
	 */
	public List<ReportTransSortDomain> getReportTransSortInfos(String transType,int maxSortNum,Date beginDate,Date endDate) throws LotteryException;
	/**
	 * 
	 * Title: getReportAccountInfo<br>
	 * Description: <br>
	 *              <br>��ѯһ��������Ϣ
	 * @param reportId
	 * @return
	 * @throws LotteryException
	 */
	public ReportAccount getReportAccountInfo(String reportId) throws LotteryException;
	
	

}
