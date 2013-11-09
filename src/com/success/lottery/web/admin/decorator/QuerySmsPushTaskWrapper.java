package com.success.lottery.web.admin.decorator;

import java.text.SimpleDateFormat;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.sms.push.model.SmsPushTask;
import com.success.lottery.util.LotteryTools;

public class QuerySmsPushTaskWrapper extends TableDecorator{
	private String taskId;
	private String taskId_link;
	private String taskStatus_str;
	private String lotteryId;
	private String beginTime;
	private String endTime;
	public String getBeginTime() {
		SmsPushTask smsPushTask = (SmsPushTask)super.getCurrentRowObject();
		this.setBeginTime(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(smsPushTask.getBeginTime()).toString().trim());
		return this.beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		SmsPushTask smsPushTask = (SmsPushTask)super.getCurrentRowObject();
		this.setEndTime(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(smsPushTask.getEndTime()).toString().trim());
		return this.endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTaskId_link() {
		SmsPushTask smsPushTask = (SmsPushTask)super.getCurrentRowObject();
		this.setTaskId_link(LotteryStaticDefine.getSmsPushTaskLink(smsPushTask.getTaskId()).toString().trim());
		return this.taskId_link;
	}
	public void setTaskId_link(String taskId_link) {
		this.taskId_link = taskId_link;
	}
	public String getTaskStatus_str() {
		SmsPushTask smsPushTask = (SmsPushTask)super.getCurrentRowObject();
		this.setTaskStatus_str(LotteryStaticDefine.taskStautsForSms.get(smsPushTask.getTaskStatus()).toString().trim());
		return this.taskStatus_str;
	}
	public void setTaskStatus_str(String taskStatus_str) {
		this.taskStatus_str = taskStatus_str;
	}
	public String getLotteryId(){
		SmsPushTask smsPushTask  = (SmsPushTask)this.getCurrentRowObject();
		if(null == smsPushTask.getLotteryId()){
			return null;
		}
		return LotteryTools.getLotteryName(smsPushTask.getLotteryId());
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	public String getTaskId() {
		SmsPushTask smsPushTask = (SmsPushTask)super.getCurrentRowObject();
		this.setTaskId(LotteryStaticDefine.getSmsPushTaskIdLink(smsPushTask.getTaskId()).toString().trim());
		return this.taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
}
