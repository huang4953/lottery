/**
 * Title: QueryConditionAction.java
 * @Package com.success.lottery.web.admin.termmanager.action
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-5-19 ����10:42:29
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
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-5-19 ����10:42:29
 * 
 */

public class QueryConditionAction extends ActionSupport {
	
	private String jsonString;
	
	private String page_type;
	
	private String lotteryId;
	
	
	public String queryTermList(){;
		List<String> termList = new ArrayList<String>();
		if(this.getPage_type().equals("L")){//����ҳ��ʹ��
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
				try {
					int limitNum = 50;
					if("1200001".equals(this.getLotteryId())){
						limitNum = 195;
					}
					termList.addAll(termManager.getLotteryLastTerm(Integer.parseInt(this.getLotteryId()),limitNum));
				} catch (LotteryException e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				} catch (Exception e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				}
			}
		} else if(this.getPage_type().equals("A")){//libing add�����ڲ�ѯ���в����б�
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
				
				try {
					termList.addAll(termService.getTermnos(Integer.parseInt(lotteryId), -1));
				} catch (LotteryException e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				} catch (Exception e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("X")){//�޸�������Ϣҳ��ʹ��
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
				try {
					termList.addAll(termManager.getLotteryCurrentAndNextTerm(Integer.parseInt(this.getLotteryId()),30));
				} catch (LotteryException e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				} catch (Exception e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("C")){// libing add�����ڳ�Ʊ��ѯ����״̬Ϊ1��2�Ĳ����б�
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
				//LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
				try {
					termList.addAll(termService.queryTermNoIsOneOrTwo(Integer.parseInt(this.getLotteryId()),-1));
				} catch (LotteryException e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				} catch (Exception e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("D")){//�ҽ�ҳ��ʹ���ں�
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
				try {
					termList.addAll(termService.queryCanCashPrizeTermNo(Integer.parseInt(this.getLotteryId())));
				} catch (LotteryException e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				} catch (Exception e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("P")){//�ɽ�ҳ��ʹ���ں�
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
				try {
					termList.addAll(termService.queryCanDispatchPrizeTermNo(Integer.parseInt(this.getLotteryId())));
				} catch (LotteryException e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				} catch (Exception e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("REPORT")){//�������Ĳ���
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				ReportQueryServiceInterf reportService = ApplicationContextUtils.getService("reportQueryService", ReportQueryServiceInterf.class);
				try {
					termList.addAll(reportService.getPrizeReportTerms(Integer.parseInt(this.getLotteryId())));
				} catch (LotteryException e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				} catch (Exception e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("LIMIT")){//�޺�ҳ��ʹ���ں�
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
				try {
					termList.addAll(termService.queryLimitTermNo(Integer.parseInt(this.getLotteryId()),100));
				} catch (LotteryException e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				} catch (Exception e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("DL3")){//δ��Ʊ��������ҳ��ʹ���ں�
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
				try {
					int limit = 20;
					if(Integer.parseInt(this.getLotteryId()) == 1200001){
						limit = 130;
					}
					termList.addAll(termService.queryDealLine3TermNo(Integer.parseInt(this.getLotteryId()),limit));
				} catch (LotteryException e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				} catch (Exception e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				}
			}
		}else if(this.getPage_type().equals("CE")){//��Ʊ������ҳ��ʹ��
			if(StringUtils.isNotEmpty(this.getLotteryId()) && !"0".equals(this.getLotteryId())){
				LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
				try {
					int limit = 20;
					if(Integer.parseInt(this.getLotteryId()) == 1200001){
						limit = 130;
					}
					termList.addAll(termService.queryTermNoIsOneOrTwo(Integer.parseInt(this.getLotteryId()),limit));
				} catch (LotteryException e) {
					// TODO �Զ����� catch ��
					e.printStackTrace();
				} catch (Exception e) {
					// TODO �Զ����� catch ��
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
