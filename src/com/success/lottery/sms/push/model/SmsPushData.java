package com.success.lottery.sms.push.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class SmsPushData implements Serializable{
	/**
	 * ����Ⱥ������ʵ����
	 * @author suerguo
	 */
	private Long id;	//����Ⱥ������id
	private String taskId;	//����Ⱥ������id
	private long sendStatus;//����״̬��0-δ���ͣ�1-�ѷ��ͣ�2-��Ҫ�ط���3-��ʱ
	private String serviceId;//����ҵ�����,�ڲ������ַ�����ָ�����η�������İ��·��񣬷���ʱ���ݸ��ַ�����ȡ��Ӧ�������緢�͵ķ�����룬������ȣ�
	private String mobilePhone;//���͵�Ŀ���û��ֻ�����
	private String content;//���͵ľ�������
	private long sendCount;//���͵��ܴ���
	private Date beginTime;//���͵����翪ʼʱ�䣬����ʱ����ʱ�䣬ȷ�ϱ�����Ϣ�Ƿ�����Ч����ʱ����
	private Date endTime;//���͵��������ʱ�䣬����ʱ����ʱ�䣬ȷ�ϱ�����Ϣ�Ƿ񻹿��Է��ͣ��統ǰʱ�䳬����ʱ�䣬������Ϣ����Ϊ��ʱ
	private String sendSummary;
	private String remark;//��ע
	private String reserve;//Ԥ��
	private Date sentTime;//���ݷ��͵ľ���ʱ��

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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getSendCount() {
		return sendCount;
	}
	public void setSendCount(long sendCount) {
		this.sendCount = sendCount;
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
	public String getSendSummary() {
		return sendSummary;
	}
	public void setSendSummary(String sendSummary) {
		this.sendSummary = sendSummary;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getSentTime() {
		return sentTime;
	}
	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}
	
}
