package com.success.lottery.web.admin.decorator;

import java.text.SimpleDateFormat;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.operatorlog.domain.OperationLog;
import com.success.lottery.sms.push.model.SmsPushTask;

public class QueryOperatorlogWrapper extends TableDecorator {
	private String level;
    private String type;
    private String rank;
    private String operattime;
	public String getLevel() {
		OperationLog operationLog = (OperationLog)super.getCurrentRowObject();
		this.setLevel(LotteryStaticDefine.operatorlogLevelStatus.get(operationLog.getLevel()));
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getType() {
		OperationLog operationLog = (OperationLog)super.getCurrentRowObject();
		this.setType(LotteryStaticDefine.operatorlogTypeStatus.get(operationLog.getType()));
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRank() {
		OperationLog operationLog = (OperationLog)super.getCurrentRowObject();
		this.setRank(LotteryStaticDefine.operatorlogRankStatus.get(operationLog.getRank()));
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getOperattime() {
		OperationLog operationLog = (OperationLog)super.getCurrentRowObject();
		this.setOperattime(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(operationLog.getOperattime()).toString().trim());
		return operattime;
	}
	public void setOperattime(String operattime) {
		this.operattime = operattime;
	}
}
