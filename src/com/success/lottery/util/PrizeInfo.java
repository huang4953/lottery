/**
 * Title: PrizeInfo.java
 * @Package com.success.lottery.util
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-4-14 ����05:04:19
 * @version V1.0
 */
package com.success.lottery.util;

/**
 * com.success.lottery.util
 * PrizeInfo.java
 * PrizeInfo
 * (��������ҽ��󵥸�����Ľ������)
 * @author gaoboqin
 * 2011-4-14 ����05:04:19
 * 
 */

public class PrizeInfo {
	
	private String winCode;//�н��ĵ�ʽ����
	private int prizeLevel;//�н��ļ���
	private long basePrize = 0;//���𣬲�����׷�ӵ�,��������
	private long addPrize = 0;//׷�ӵĽ��𣬶Դ���͸��׷�ӣ���������
	private int taxPercent =0;//˰��
	private long aftTaxPrize =0;//˰��Ľ���
	private long taxPrize =0;//˰��
	
	public long getAddPrize() {
		return addPrize;
	}
	public void setAddPrize(long addPrize) {
		this.addPrize = addPrize;
	}
	public long getAftTaxPrize() {
		return aftTaxPrize;
	}
	public void setAftTaxPrize(long aftTaxPrize) {
		this.aftTaxPrize = aftTaxPrize;
	}
	public long getBasePrize() {
		return basePrize;
	}
	public void setBasePrize(long basePrize) {
		this.basePrize = basePrize;
	}
	public int getPrizeLevel() {
		return prizeLevel;
	}
	public void setPrizeLevel(int prizeLevel) {
		this.prizeLevel = prizeLevel;
	}
	public int getTaxPercent() {
		return taxPercent;
	}
	public void setTaxPercent(int taxPercent) {
		this.taxPercent = taxPercent;
	}
	public String getWinCode() {
		return winCode;
	}
	public void setWinCode(String winCode) {
		this.winCode = winCode;
	}
	public long getTaxPrize() {
		return taxPrize;
	}
	public void setTaxPrize(long taxPrize) {
		this.taxPrize = taxPrize;
	}
	
	
	

}
