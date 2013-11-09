/**
 * Title: EhandTermBussiModel.java
 * @Package com.success.lottery.ehand.eterm.domain
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-19 ����05:24:54
 * @version V1.0
 */
package com.success.lottery.ehand.eterm.domain;

import java.io.Serializable;

/**
 * com.success.lottery.ehand.eterm.domain
 * EhandTermBussiModel.java
 * EhandTermBussiModel
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-19 ����05:24:54
 * 
 */

public class EhandTermModel implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô)
	 */
	private static final long serialVersionUID = 2332166808683135525L;
	
	private int lotteryId = 0;//��Ӧlotteryterm�е�lotteryId
	private String ehandLotteryId;//�����Ĳ��ֱ��
	private String issue;//�����Ĳ��ֲ���
	private String startTime;//�����Ľӿڷ��صĿ�ʼʱ��
	private String endTime;//�����Ľӿڷ��صĽ���ʱ��
	private String printStart;//�����Ŀ��Գ�Ʊ�Ŀ�ʼʱ��
	private String printEnd;//�����Ŀ��Գ�Ʊ�Ľ���ʱ��
	private int status = -99;//�����Ľӿڷ��ز���״̬
	private String bonuscode;//�����Ľӿڷ��صı��ڿ�������
	private String salemoney;//�����Ľӿڷ��صı������۽���λ��
	private String bonusmoney;//�����Ľӿڷ��صı����н����λ��
	private String reserve;//�����Ľӿڷ��صı����н����λ��
	
	public String getBonuscode() {
		return bonuscode;
	}
	public void setBonuscode(String bonuscode) {
		this.bonuscode = bonuscode;
	}
	public String getBonusmoney() {
		return bonusmoney;
	}
	public void setBonusmoney(String bonusmoney) {
		this.bonusmoney = bonusmoney;
	}
	public String getEhandLotteryId() {
		return ehandLotteryId;
	}
	public void setEhandLotteryId(String ehandLotteryId) {
		this.ehandLotteryId = ehandLotteryId;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getPrintEnd() {
		return printEnd;
	}
	public void setPrintEnd(String printEnd) {
		this.printEnd = printEnd;
	}
	public String getPrintStart() {
		return printStart;
	}
	public void setPrintStart(String printStart) {
		this.printStart = printStart;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	public String getSalemoney() {
		return salemoney;
	}
	public void setSalemoney(String salemoney) {
		this.salemoney = salemoney;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
