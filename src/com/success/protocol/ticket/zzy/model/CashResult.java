/**
 * Title: CashResult.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-12 下午04:46:10
 * @version V1.0
 */
package com.success.protocol.ticket.zzy.model;

/**
 * com.success.protocol.ticket.zzy
 * CashResult.java
 * CashResult
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-12 下午04:46:10
 * 
 */

public class CashResult {
	
	private String ticketSequence;//投注系统票的流水号
	private String preTaxPrize;//税前奖金
	private String taxPrize;//税后奖金
	
	/**
	 *Title: 
	 *Description: 
	 * @param ticketSequence
	 * @param preTaxPrize
	 * @param taxPrize
	 */
	public CashResult(String ticketSequence, String preTaxPrize, String taxPrize) {
		super();
		this.ticketSequence = ticketSequence;
		this.preTaxPrize = preTaxPrize;
		this.taxPrize = taxPrize;
	}
	
	public String toString(){
		return "ticketSequence:"+ticketSequence+"#"+"preTaxPrize:"+preTaxPrize+"#"+"taxPrize:"+taxPrize;
	}
	
	public String getPreTaxPrize() {
		return preTaxPrize;
	}
	public void setPreTaxPrize(String preTaxPrize) {
		this.preTaxPrize = preTaxPrize;
	}
	public String getTaxPrize() {
		return taxPrize;
	}
	public void setTaxPrize(String taxPrize) {
		this.taxPrize = taxPrize;
	}
	public String getTicketSequence() {
		return ticketSequence;
	}
	public void setTicketSequence(String ticketSequence) {
		this.ticketSequence = ticketSequence;
	}
	
	
	
	

}
