/**
 * Title: ReportQueryAction.java
 * @Package com.success.lottery.web.admin.action
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-10 下午02:00:16
 * @version V1.0
 */
package com.success.lottery.web.admin.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.report.domain.ReportAccount;
import com.success.lottery.report.domain.ReportPrizeDomain;
import com.success.lottery.report.domain.ReportTransSortDomain;
import com.success.lottery.report.service.ReportStaticDefine;
import com.success.lottery.report.service.interf.ReportQueryServiceInterf;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.web.admin.webbean.PageList;
import com.success.lottery.web.formbean.Page;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.web.admin.action
 * ReportQueryAction.java
 * ReportQueryAction
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-10 下午02:00:16
 * 
 */

public class ReportQueryAction extends ActionSupport implements SessionAware {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 5814922318912327770L;
	
	//分页功能
	private Page page;
	
	private int pageSize=20;
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	

	private Map						session;
	
	private String p_lotteryId = "1000001";
	private String p_termNo_begin = "0";
	private String p_termNo_end = "0";
	private String prizeReportType = "";
	private Map<Integer, String> lotteryList;
	private String reportId;
	private ReportPrizeDomain reportDetail;
	private String lotteryName;
	private String beginDate;
	private String endDate;
	private ReportAccount reportAccount;
	private Map<String,String> transSortType;
	private String p_sortType = "1001";//默认为投注排名
	private List<ReportTransSortDomain>  reportSortList;
	
	
	/**
	 * 
	 * Title: getPrizeReports<br>
	 * Description: <br>
	 *              <br>查询中奖统计报表
	 * @return
	 */
	public String getPrizeReports(){
		try{
			this.setLotteryList(LotteryStaticDefine.getLotteryList());
			this.setPrizeReportType(ReportStaticDefine.prizeReportType.get(p_lotteryId));
			
			ReportQueryServiceInterf reportService = ApplicationContextUtils.getService("reportQueryService", ReportQueryServiceInterf.class);
			
			if(null == this.page){
				this.page = new Page(this.pageSize);
			}else{
				page.setPageSize(this.pageSize);
			}
			
			
			int totalNumber = reportService.getPrizeReportsCount(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo_begin(), this.getP_termNo_end());
			
			List<ReportPrizeDomain> retReportPrizeList = reportService.getPrizeReports(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo_begin(), 
					this.getP_termNo_end(), page.getFirst(), page.getPageSize());
			
			if(null != retReportPrizeList){
				page.setTotalCount(totalNumber);
				page.setResult(retReportPrizeList);
			}else{
				page.setTotalCount(0);
			}
			
		}catch(LotteryException e){
			e.printStackTrace();
			return ERROR; 
		}catch(Exception ex){
			ex.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 
	 * Title: getPrizeReportDetail<br>
	 * Description: <br>
	 *              <br>显示中奖统计报表的信息信息
	 * @return
	 */
	public String getPrizeReportDetail(){
		try{
			this.setPrizeReportType(ReportStaticDefine.prizeReportType.get(p_lotteryId));
			this.setLotteryName(ReportStaticDefine.reportLotteryName.get(this.getP_lotteryId()));
			ReportQueryServiceInterf reportService = ApplicationContextUtils.getService("reportQueryService", ReportQueryServiceInterf.class);
			this.reportDetail = reportService.getPrizeReportInfo(Integer.parseInt(this.getP_lotteryId()), Integer.parseInt(this.getReportId()));
		}catch(LotteryException e){
			e.printStackTrace();
			return ERROR; 
		}catch(Exception ex){
			ex.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 
	 * Title: getAccountTransReport<br>
	 * Description: <br>
	 *              <br>账户变动统计报表
	 * @return
	 */
	public String getAccountTransReport(){
		try{
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1);
			//String curTime = format.format(new java.util.Date());
			String curTime = format.format(cal.getTime());
			if(StringUtils.isEmpty(this.getBeginDate())){
				this.setBeginDate(curTime);
			}
			if(StringUtils.isEmpty(this.getEndDate())){
				this.setEndDate(curTime);
			}
			
			ReportQueryServiceInterf reportService = ApplicationContextUtils.getService("reportQueryService", ReportQueryServiceInterf.class);
			
			if(null == this.page){
				this.page = new Page(this.pageSize);
			}else{
				page.setPageSize(this.pageSize);
			}
			
			
			int totalNumber = reportService.getAccountReportsCount(this.getBeginDate(), this.getEndDate());
			
			List<ReportAccount> retReportAccountList = reportService.getAccountReports(this.getBeginDate(), this.getEndDate(), page.getFirst(), page.getPageSize());
			
			if(null != retReportAccountList){
				page.setTotalCount(totalNumber);
				page.setResult(retReportAccountList);
			}else{
				page.setTotalCount(0);
			}
			
		}catch(LotteryException e){
			e.printStackTrace();
			return ERROR; 
		}catch(Exception ex){
			ex.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 
	 * Title: getAccountReportDetail<br>
	 * Description: <br>
	 *              <br>账户变动明细
	 * @return
	 */
	public String getAccountReportDetail(){
		try{
			ReportQueryServiceInterf reportService = ApplicationContextUtils.getService("reportQueryService", ReportQueryServiceInterf.class);
			this.reportAccount = reportService.getReportAccountInfo(this.getReportId());
		}catch(LotteryException e){
			e.printStackTrace();
			return ERROR; 
		}catch(Exception ex){
			ex.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 
	 * Title: getTransSortReport<br>
	 * Description: <br>
	 *              <br>交易排名报表
	 * @return
	 */
	public String getTransSortReport(){
		try{
			this.transSortType = ReportStaticDefine.reportTransSortType;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String curTime = format.format(new java.util.Date());
			
			if(StringUtils.isEmpty(this.getBeginDate())){
				this.setBeginDate(curTime);
			}
			if(StringUtils.isEmpty(this.getEndDate())){
				this.setEndDate(curTime);
			}
			
			Date begDate = StringUtils.isEmpty(this.getBeginDate())?new Date() : format.parse(this.getBeginDate());
			Date enDate = StringUtils.isEmpty(this.getEndDate())?new Date() : format.parse(this.getEndDate());
			
			ReportQueryServiceInterf reportService = ApplicationContextUtils.getService("reportQueryService", ReportQueryServiceInterf.class);
			this.reportSortList = reportService.getReportTransSortInfos(this.getP_sortType(), 100, begDate, enDate);
		}catch(LotteryException e){
			e.printStackTrace();
			return ERROR; 
		}catch(Exception ex){
			ex.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}


	public String getP_lotteryId() {
		return p_lotteryId;
	}


	public void setP_lotteryId(String id) {
		p_lotteryId = id;
	}


	public String getP_termNo_begin() {
		return p_termNo_begin;
	}


	public void setP_termNo_begin(String no_begin) {
		p_termNo_begin = no_begin;
	}


	public String getP_termNo_end() {
		return p_termNo_end;
	}


	public void setP_termNo_end(String no_end) {
		p_termNo_end = no_end;
	}


	public Map<Integer, String> getLotteryList() {
		return lotteryList;
	}


	public void setLotteryList(Map<Integer, String> lotteryList) {
		this.lotteryList = lotteryList;
	}


	public String getPrizeReportType() {
		return prizeReportType;
	}


	public void setPrizeReportType(String prizeReportType) {
		this.prizeReportType = prizeReportType;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public ReportPrizeDomain getReportDetail() {
		return reportDetail;
	}

	public void setReportDetail(ReportPrizeDomain reportDetail) {
		this.reportDetail = reportDetail;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public ReportAccount getReportAccount() {
		return reportAccount;
	}

	public void setReportAccount(ReportAccount reportAccount) {
		this.reportAccount = reportAccount;
	}

	public Map<String, String> getTransSortType() {
		return transSortType;
	}

	public void setTransSortType(Map<String, String> transSortType) {
		this.transSortType = transSortType;
	}

	public List<ReportTransSortDomain> getReportSortList() {
		return reportSortList;
	}

	public void setReportSortList(List<ReportTransSortDomain> reportSortList) {
		this.reportSortList = reportSortList;
	}

	public String getP_sortType() {
		return p_sortType;
	}

	public void setP_sortType(String type) {
		p_sortType = type;
	}
}
