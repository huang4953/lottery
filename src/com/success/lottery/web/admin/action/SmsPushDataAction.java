package com.success.lottery.web.admin.action;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.sms.push.model.SmsPushData;
import com.success.lottery.sms.push.service.SmsPushDataService;
import com.success.lottery.web.admin.webbean.PageList;
import com.success.utils.ApplicationContextUtils;

public class SmsPushDataAction extends ActionSupport implements SessionAware {
	private long id;
	private String taskId;
	private long sendStatus;
	private String serviceId;
	private String mobilePhone;
	private Date beginTime;
	private Date endTime;
	private Map<Long, String> sendStatusList;
	//ÿһҳ�ļ�¼��
	private int					perPageNumber;
	//�ڼ�ҳ
	private int					page;
	private PageList 				termList = new PageList();

	//��ҳ����ʹ��static�������ڷ�ҳʱ���²�ѯ�����������������⣬����ͬʱ���ı���ҳ��
	//Ŀǰÿ�ζ���ѯ��������
	//�ɿ��ǵĽ��������
	//1-����������session�� 2-�����ݿ�DAO���ж�����������ϴ���ͬ�������ݿ��ѯֱ�ӷ���
	private int totalNumber;
	@Override
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		
	}

	
	//����ҳ��ʱ�Ĳ�ѯ
	public String queryAllSmsPushData() {
		try{
			//�������÷���״̬
			this.setSendStatusList(LotteryStaticDefine.sendStatusForSms);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		SmsPushDataService smsPushDataService = ApplicationContextUtils.getService("smsPushDataService", SmsPushDataService.class);
		try{
			List<SmsPushData> list = null;
			if(perPageNumber==0){
				perPageNumber = 30;
			}
			if(page == 0){
				//��һ�β�ѯ����ҳ����Ϊ1
				page = 1;
			}
			totalNumber = smsPushDataService.getSmsPushDataListCount(taskId, sendStatus, mobilePhone, serviceId, beginTime, endTime);
			if(totalNumber<perPageNumber&&totalNumber!=0){
				perPageNumber = totalNumber;
			}
			list = smsPushDataService.getSmsPushDataList(taskId, sendStatus, mobilePhone, serviceId, beginTime, endTime, page, perPageNumber);
			termList.setPageNumber(page);
			termList.setPerPage(perPageNumber);
			termList.setFullListSize(totalNumber);
			termList.setList(list);
		}catch(LotteryException e){
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}


	
	//��ϸ��ѯ
	public String showSmsPushDataDetail() {
		SmsPushData smsPushData = null;
		try {
			SmsPushDataService smsPushDataService = ApplicationContextUtils.getService("smsPushDataService", SmsPushDataService.class);
			smsPushData = smsPushDataService.getSmsPushData(id);
			ActionContext aContext = ActionContext.getContext();
			ValueStack valueStack = aContext.getValueStack();
			String sendStatus_str = LotteryStaticDefine.sendStatusForSms.get(smsPushData.getSendStatus());
			String beginTime_str  = null;
			String endTime_str = null;
			String sentTime_str = null;
			if(null!=smsPushData.getBeginTime()){
				beginTime_str = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��").format(smsPushData.getBeginTime());
			}
			if(null!=smsPushData.getEndTime()){
				endTime_str = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��").format(smsPushData.getEndTime());
			}
			if(null!=smsPushData.getSentTime()){
				sentTime_str = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��").format(smsPushData.getSentTime());
			}
			valueStack.set("smsPushData", smsPushData);
			valueStack.set("sendStatus_str", sendStatus_str);
			valueStack.set("beginTime_str", beginTime_str);
			valueStack.set("endTime_str", endTime_str);
			valueStack.set("sentTime_str", sentTime_str);
		} catch (LotteryException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	public int getPerPageNumber() {
		return perPageNumber;
	}


	public void setPerPageNumber(int perPageNumber) {
		this.perPageNumber = perPageNumber;
	}


	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public PageList getTermList() {
		return termList;
	}


	public void setTermList(PageList termList) {
		this.termList = termList;
	}


	public int getTotalNumber() {
		return totalNumber;
	}


	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}


	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public long getSendStatus() {
		return sendStatus;
	}


	public void setSendStatus(long sendStatus) {
		this.sendStatus = sendStatus;
	}


	public String getServiceId() {
		return serviceId;
	}


	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}


	public String getMobilePhone() {
		return mobilePhone;
	}


	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}


	public Date getBeginTime() {
		return beginTime;
	}


	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public Map<Long, String> getSendStatusList() {
		return sendStatusList;
	}


	public void setSendStatusList(Map<Long, String> sendStatusList) {
		this.sendStatusList = sendStatusList;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}




}
