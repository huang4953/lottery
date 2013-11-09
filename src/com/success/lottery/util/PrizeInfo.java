/**
 * Title: PrizeInfo.java
 * @Package com.success.lottery.util
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-4-14 下午05:04:19
 * @version V1.0
 */
package com.success.lottery.util;

/**
 * com.success.lottery.util
 * PrizeInfo.java
 * PrizeInfo
 * (用来保存兑奖后单个号码的奖金情况)
 * @author gaoboqin
 * 2011-4-14 下午05:04:19
 * 
 */

public class PrizeInfo {
	
	private String winCode;//中奖的单式号码
	private int prizeLevel;//中奖的级别
	private long basePrize = 0;//奖金，不包含追加的,包含倍数
	private long addPrize = 0;//追加的奖金，对大乐透有追加，包含倍数
	private int taxPercent =0;//税率
	private long aftTaxPrize =0;//税后的奖金
	private long taxPrize =0;//税金
	
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
