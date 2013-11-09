package com.success.lottery.web.admin.decorator;

import java.text.SimpleDateFormat;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.sms.push.model.SmsPushData;

public class QuerySmsPushDataWrapper extends TableDecorator{
	private String id_link;
	private String sendStatus_str;
	private String beginTime;
	private String endTime;
	private String mixi_link;
	public String getMixi_link() {
		SmsPushData smsPushData = (SmsPushData)super.getCurrentRowObject();
		this.setMixi_link(LotteryStaticDefine.getSmsPushTaskDataLink(smsPushData.getId()).toString().trim());
		return this.mixi_link;
	}
	public void setMixi_link(String mixi_link) {
		this.mixi_link = mixi_link;
	}
	public String getBeginTime() {
		SmsPushData smsPushData = (SmsPushData)super.getCurrentRowObject();
		this.setBeginTime(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(smsPushData.getBeginTime()).toString().trim());
		return this.beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		SmsPushData smsPushData = (SmsPushData)super.getCurrentRowObject();
		this.setEndTime(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(smsPushData.getEndTime()).toString().trim());
		return this.endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getId_link() {
		SmsPushData smsPushData = (SmsPushData)super.getCurrentRowObject();
		this.setId_link(LotteryStaticDefine.getSmsPushDataLink(smsPushData.getId()).toString().trim());
		return this.id_link;
	}
	public void setId_link(String id_link) {
		this.id_link = id_link;
	}
	public String getSendStatus_str() {
		SmsPushData smsPushData = (SmsPushData)super.getCurrentRowObject();
		this.setSendStatus_str(LotteryStaticDefine.sendStatusForSms.get(smsPushData.getSendStatus()).toString().trim());
		return this.sendStatus_str;
	}
	public void setSendStatus_str(String sendStatus_str) {
		this.sendStatus_str = sendStatus_str;
	}
	
}
