package com.success.lottery.sms.push.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class SmsPushTask implements Serializable{
	/**
	 * 短信群发任务实体类
	 * @author suerguo
	 */
	private String taskId;	//短信群发任务id
	private long taskStatus;//任务状态，0-未开始，1-发送中，2-发送完成
	private String taskSummary;//短信群发任务摘要
	private String productId;//产品代码，内部定义代码，指明本次发送任务对那些产品的订购用户发送
	private String serviceId;//发送业务代码,内部定义字符串，指明本次发送任务的包月服务，发送时根据该字符串获取相应参数（如发送的服务代码，长号码等）
	private String content;//本次发送的内容；
	private String sendSummary;//短信群发任务发送摘要
	private Date beginTime;//发送的最早开始时间，发送时检查该时间，确认本条消息是否在有效发送时间内
	private Date endTime;//发送的最晚结束时间，发送时检查该时间，确认本条消息是否还可以发送，如当前时间超过该时间，则本条消息设置为超时
	
	private Integer lotteryId;//彩种id
	private String lotteryTerm;//varchar(32) not null,comment '当前期数'
	
	private String remark;//备注
	private String reserve;//预留	
	
	private Date taskTime;//任务生成时间
	private Date executionTime;//任务执行时间
	private String executionStat;//任务执行情况
	
	
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
