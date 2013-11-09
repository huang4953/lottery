package com.success.lottery.sms.push.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class SmsPushData implements Serializable{
	/**
	 * 短信群发数据实体类
	 * @author suerguo
	 */
	private Long id;	//短信群发数据id
	private String taskId;	//短信群发任务id
	private long sendStatus;//发送状态，0-未发送，1-已发送，2-需要重发，3-超时
	private String serviceId;//发送业务代码,内部定义字符串，指明本次发送任务的包月服务，发送时根据该字符串获取相应参数（如发送的服务代码，长号码等）
	private String mobilePhone;//发送的目标用户手机号码
	private String content;//发送的具体内容
	private long sendCount;//发送的总次数
	private Date beginTime;//发送的最早开始时间，发送时检查该时间，确认本条消息是否在有效发送时间内
	private Date endTime;//发送的最晚结束时间，发送时检查该时间，确认本条消息是否还可以发送，如当前时间超过该时间，则本条消息设置为超时
	private String sendSummary;
	private String remark;//备注
	private String reserve;//预留
	private Date sentTime;//数据发送的具体时间

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
