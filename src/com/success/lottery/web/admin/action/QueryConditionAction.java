/**
 * Title: QueryConditionAction.java
 * @Package com.success.lottery.web.admin.termmanager.action
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-5-19 下午10:42:29
 * @version V1.0
 */
package com.success.lottery.web.admin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;

import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.report.service.interf.ReportQueryServiceInterf;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.web.admin.termmanager.action
 * QueryConditionAction.java
 * QueryConditionAction
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-5-19 下午10:42:29
 * 
 */

public class QueryConditionAction extends ActionSupport {
	
	private String jsonString;
	
	private String page_type;
	
	private String lotteryId;
	
	
	public String queryTermList(){;
		List<String> termList = new ArrayList<String>();
		if(this.getPage_type().equals("L")){//开奖页面使用
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
				try {
					int limitNum = 50;
					if("1200001".equals(this.getLotteryId())){
						limitNum = 195;
					}
					termList.addAll(termManager.getLotteryLastTerm(Integer.parseInt(this.getLotteryId()),limitNum));
				} catch (LotteryException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				} catch (Exception e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		} else if(this.getPage_type().equals("A")){//libing add，用于查询所有彩期列表
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
				
				try {
					termList.addAll(termService.getTermnos(Integer.parseInt(lotteryId), -1));
				} catch (LotteryException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				} catch (Exception e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("X")){//修改销售信息页面使用
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
				try {
					termList.addAll(termManager.getLotteryCurrentAndNextTerm(Integer.parseInt(this.getLotteryId()),30));
				} catch (LotteryException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				} catch (Exception e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("C")){// libing add，用于出票查询彩期状态为1或2的彩期列表
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
				//LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
				try {
					termList.addAll(termService.queryTermNoIsOneOrTwo(Integer.parseInt(this.getLotteryId()),-1));
				} catch (LotteryException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				} catch (Exception e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("D")){//兑奖页面使用期号
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
				try {
					termList.addAll(termService.queryCanCashPrizeTermNo(Integer.parseInt(this.getLotteryId())));
				} catch (LotteryException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				} catch (Exception e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("P")){//派奖页面使用期号
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
				try {
					termList.addAll(termService.queryCanDispatchPrizeTermNo(Integer.parseInt(this.getLotteryId())));
				} catch (LotteryException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				} catch (Exception e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("REPORT")){//报表界面的彩期
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				ReportQueryServiceInterf reportService = ApplicationContextUtils.getService("reportQueryService", ReportQueryServiceInterf.class);
				try {
					termList.addAll(reportService.getPrizeReportTerms(Integer.parseInt(this.getLotteryId())));
				} catch (LotteryException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				} catch (Exception e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("LIMIT")){//限号页面使用期号
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
				try {
					termList.addAll(termService.queryLimitTermNo(Integer.parseInt(this.getLotteryId()),100));
				} catch (LotteryException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				} catch (Exception e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("DL3")){//未查票订单处理页面使用期号
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
				try {
					int limit = 20;
					if(Integer.parseInt(this.getLotteryId()) == 1200001){
						limit = 130;
					}
					termList.addAll(termService.queryDealLine3TermNo(Integer.parseInt(this.getLotteryId()),limit));
				} catch (LotteryException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				} catch (Exception e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("CE")){//出票错误处理页面使用
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
				try {
					int limit = 20;
					if(Integer.parseInt(this.getLotteryId()) == 1200001){
						limit = 130;
					}
					termList.addAll(termService.queryTermNoIsOneOrTwo(Integer.parseInt(this.getLotteryId()),limit));
				} catch (LotteryException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				} catch (Exception e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
			}
		}
		
		this.jsonString  = JSONArray.fromObject(termList).toString();
		return "ajaxjson";
	}
	
	public String queryLotteryList(){
		Map<Integer, String> lotteryList = new HashMap<Integer, String>();
		try{
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			lotteryList = termManager.getLotteryList();
		}catch(LotteryException e){
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		this.jsonString  = JSONArray.fromObject(lotteryList).toString();
		return "ajaxjson";
	}


	public String getJsonString() {
		return jsonString;
	}


	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getPage_type() {
		return page_type;
	}

	public void setPage_type(String page_type) {
		this.page_type = page_type;
	}

	
	

}
