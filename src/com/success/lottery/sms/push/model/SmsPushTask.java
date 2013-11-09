package com.success.lottery.sms.push.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class SmsPushTask implements Serializable{
	/**
	 * ����Ⱥ������ʵ����
	 * @author suerguo
	 */
	private String taskId;	//����Ⱥ������id
	private long taskStatus;//����״̬��0-δ��ʼ��1-�����У�2-�������
	private String taskSummary;//����Ⱥ������ժҪ
	private String productId;//��Ʒ���룬�ڲ�������룬ָ�����η����������Щ��Ʒ�Ķ����û�����
	private String serviceId;//����ҵ�����,�ڲ������ַ�����ָ�����η�������İ��·��񣬷���ʱ���ݸ��ַ�����ȡ��Ӧ�������緢�͵ķ�����룬������ȣ�
	private String content;//���η��͵����ݣ�
	private String sendSummary;//����Ⱥ��������ժҪ
	private Date beginTime;//���͵����翪ʼʱ�䣬����ʱ����ʱ�䣬ȷ�ϱ�����Ϣ�Ƿ�����Ч����ʱ����
	private Date endTime;//���͵��������ʱ�䣬����ʱ����ʱ�䣬ȷ�ϱ�����Ϣ�Ƿ񻹿��Է��ͣ��統ǰʱ�䳬����ʱ�䣬������Ϣ����Ϊ��ʱ
	
	private Integer lotteryId;//����id
	private String lotteryTerm;//varchar(32) not null,comment '��ǰ����'
	
	private String remark;//��ע
	private String reserve;//Ԥ��	
	
	private Date taskTime;//��������ʱ��
	private Date executionTime;//����ִ��ʱ��
	private String executionStat;//����ִ�����
	
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Date getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(Date taskTime) {
		this.taskTime = taskTime;
	}
	public Date getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}
	public String getExecutionStat() {
		return executionStat;
	}
	public void setExecutionStat(String executionStat) {
		this.executionStat = executionStat;
	}
	public long getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(long taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Integer getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Integer lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getLotteryTerm() {
		return lotteryTerm;
	}
	public void setLotteryTerm(String lotteryTerm) {
		this.lotteryTerm = lotteryTerm;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	public String getTaskSummary() {
		return taskSummary;
	}
	public void setTaskSummary(String taskSummary) {
		this.taskSummary = taskSummary;
	}
	public String getSendSummary() {
		return sendSummary;
	}
	public void setSendSummary(String sendSummary) {
		this.sendSummary = sendSummary;
	}
}
