/**
 * Title: CreateReport.java
 * @Package com.success.lottery.report.service
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-9 下午02:22:12
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
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-9 下午02:22:12
 * 
 */

public class CreateReport implements WrapperListener{
	
	private static final String REPORT_TYPE_01 = "01";//生成中奖统计数据
	private static final String REPORT_TYPE_02 = "02";//生成账户变动统计
	
	/**
	 * 
	 * Title: createPrizeReport<br>
	 * Description: <br>
	 *              <br>生成中奖统计数据
	 */
	private static void createPrizeReport(){
		ReportServiceInterf reportService = ApplicationContextUtils.getService("reportCreateService", ReportServiceInterf.class);
		try{
			reportService.createPrizeReportSuper();//大乐透
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			reportService.createPrizeReportBall();//胜负彩
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			reportService.createPrizeReportArrange3();//排列3
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			reportService.createPrizeReportArrange5();//排列5
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			reportService.createPrizeReportSeven();//七星彩
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			reportService.createPrizeReportBall4();//4场进球彩
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			reportService.createPrizeReportBall6();//6场半全场
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			reportService.createPrizeReportDlc();//江西多乐彩
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * Title: createAccountReport<br>
	 * Description: <br>
	 *              <br>生成账户变动统计,该统计完全依赖程序运行的时间
	 */
	private static void createAccountReport(){
		try {
			ReportServiceInterf reportService = ApplicationContextUtils.getService("reportCreateService", ReportServiceInterf.class);
			reportService.createAccountReport();//生成账户变动统计
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Title: main<br>
	 * Description: <br>
	 *              <br>报表数据生成执行入口
	 * @param args
	 */

	public static void main(String[] args) {
		if(args == null || args.length < 1){//如果不设置则两类报表都运行
			//生成中奖统计数据
			CreateReport.createPrizeReport();
			//生成账户变动统计
			CreateReport.createAccountReport();
		}else if(REPORT_TYPE_01.equals(args[0])){
			//生成中奖统计数据
			CreateReport.createPrizeReport();
		}else if(REPORT_TYPE_02.equals(args[0])){
			//生成账户变动统计
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
		if(args == null || args.length < 1){//如果不设置则两类报表都运行
			//生成中奖统计数据
			CreateReport.createPrizeReport();
			//生成账户变动统计
			CreateReport.createAccountReport();
		}else if(REPORT_TYPE_01.equals(args[0])){
			//生成中奖统计数据
			CreateReport.createPrizeReport();
		}else if(REPORT_TYPE_02.equals(args[0])){
			//生成账户变动统计
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
