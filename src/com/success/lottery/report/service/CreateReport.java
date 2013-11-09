/**
 * Title: CreateReport.java
 * @Package com.success.lottery.report.service
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-8-9 ����02:22:12
 * @version V1.0
 */
package com.success.lottery.report.service;

import com.success.lottery.report.service.interf.ReportServiceInterf;
import com.success.utils.ApplicationContextUtils;

import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

/**
 * com.success.lottery.report.service
 * CreateReport.java
 * CreateReport
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-8-9 ����02:22:12
 * 
 */

public class CreateReport implements WrapperListener{
	
	private static final String REPORT_TYPE_01 = "01";//�����н�ͳ������
	private static final String REPORT_TYPE_02 = "02";//�����˻��䶯ͳ��
	
	/**
	 * 
	 * Title: createPrizeReport<br>
	 * Description: <br>
	 *              <br>�����н�ͳ������
	 */
	private static void createPrizeReport(){
		ReportServiceInterf reportService = ApplicationContextUtils.getService("reportCreateService", ReportServiceInterf.class);
		try{
			reportService.createPrizeReportSuper();//����͸
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			reportService.createPrizeReportBall();//ʤ����
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			reportService.createPrizeReportArrange3();//����3
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			reportService.createPrizeReportArrange5();//����5
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			reportService.createPrizeReportSeven();//���ǲ�
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			reportService.createPrizeReportBall4();//4�������
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			reportService.createPrizeReportBall6();//6����ȫ��
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			reportService.createPrizeReportDlc();//�������ֲ�
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * Title: createAccountReport<br>
	 * Description: <br>
	 *              <br>�����˻��䶯ͳ��,��ͳ����ȫ�����������е�ʱ��
	 */
	private static void createAccountReport(){
		try {
			ReportServiceInterf reportService = ApplicationContextUtils.getService("reportCreateService", ReportServiceInterf.class);
			reportService.createAccountReport();//�����˻��䶯ͳ��
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Title: main<br>
	 * Description: <br>
	 *              <br>������������ִ�����
	 * @param args
	 */

	public static void main(String[] args) {
		if(args == null || args.length < 1){//��������������౨������
			//�����н�ͳ������
			CreateReport.createPrizeReport();
			//�����˻��䶯ͳ��
			CreateReport.createAccountReport();
		}else if(REPORT_TYPE_01.equals(args[0])){
			//�����н�ͳ������
			CreateReport.createPrizeReport();
		}else if(REPORT_TYPE_02.equals(args[0])){
			//�����˻��䶯ͳ��
			CreateReport.createAccountReport();
		}
	}

	@Override
	public void controlEvent(int event) {
        if(!WrapperManager.isControlledByNativeWrapper() && (event == 200 || event == 201 || event == 203))
            WrapperManager.stop(0);
	}

	@Override
	public Integer start(String[] args) {
		if(args == null || args.length < 1){//��������������౨������
			//�����н�ͳ������
			CreateReport.createPrizeReport();
			//�����˻��䶯ͳ��
			CreateReport.createAccountReport();
		}else if(REPORT_TYPE_01.equals(args[0])){
			//�����н�ͳ������
			CreateReport.createPrizeReport();
		}else if(REPORT_TYPE_02.equals(args[0])){
			//�����˻��䶯ͳ��
			CreateReport.createAccountReport();
		}
		return null;
		//return Integer.valueOf(stop(0));
	}

	@Override
	public int stop(int i) {
		return i;
	}

}
