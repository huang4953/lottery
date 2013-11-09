/**
 * Title: DrawMoneyDomain.java
 * @Package com.success.lottery.account.model
 * Description: 提现表领域对象
 * @author gaoboqin
 * @date 2010-4-27 上午09:35:23
 * @version V1.0
 */
package com.success.lottery.account.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * com.success.lottery.account.model
 * DrawMoneyDomain.java
 * DrawMoneyDomain
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-27 上午09:35:23
 * 
 */

public class DrawMoneyDomain implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 8022230677254670074L;
	private String    drawid;         
	private long    userid;           
	private int    drawstatus = 0;    
	private int    drawmoney;         
	private Timestamp    submittime;  
	private String    drawreason;     
	private Timestamp    dealtime; 
	private String    dealreason;  
	private String    reserve;
	private int drawtype;
	private String bank;
	private String bankprovince;
	private String bankcity;
	private String bankname;
	private String bankcardid;
	private String cardusername;
	private int procedurefee;
	private String opuser;
	private int operatetype;
	private String operatesequence;
	private String source;
	private String sourcesequence;
	
	
	private String userPhone;
	
	private String userRealName;
	
	public String getDealreason() {
		return dealreason;
	}
	public void setDealreason(String dealreason) {
		this.dealreason = dealreason;
	}
	public Timestamp getDealtime() {
		return dealtime;
	}
	public void setDealtime(Timestamp dealtime) {
		this.dealtime = dealtime;
	}
	public String getDrawid() {
		return drawid;
	}
	public void setDrawid(String drawid) {
		this.drawid = drawid;
	}
	public int getDrawmoney() {
		return drawmoney;
	}
	public void setDrawmoney(int drawmoney) {
		this.drawmoney = drawmoney;
	}
	public String getDrawreason() {
		return drawreason;
	}
	public void setDrawreason(String drawreason) {
		this.drawreason = drawreason;
	}
	public int getDrawstatus() {
		return drawstatus;
	}
	public void setDrawstatus(int drawstatus) {
		this.drawstatus = drawstatus;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	public Timestamp getSubmittime() {
		return submittime;
	}
	public void setSubmittime(Timestamp submittime) {
		this.submittime = submittime;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public int getDrawtype() {
		return drawtype;
	}
	public void setDrawtype(int drawtype) {
		this.drawtype = drawtype;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBankcardid() {
		return bankcardid;
	}
	public void setBankcardid(String bankcardid) {
		this.bankcardid = bankcardid;
	}
	public String getBankcity() {
		return bankcity;
	}
	public void setBankcity(String bankcity) {
		this.bankcity = bankcity;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBankprovince() {
		return bankprovince;
	}
	public void setBankprovince(String bankprovince) {
		this.bankprovince = bankprovince;
	}
	public String getCardusername() {
		return cardusername;
	}
	public void setCardusername(String cardusername) {
		this.cardusername = cardusername;
	}
	public String getOpuser() {
		return opuser;
	}
	public void setOpuser(String opuser) {
		this.opuser = opuser;
	}
	public int getProcedurefee() {
		return procedurefee;
	}
	public void setProcedurefee(int procedurefee) {
		this.procedurefee = procedurefee;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	
	public int getOperatetype(){
		return operatetype;
	}
	
	public void setOperatetype(int operatetype){
		this.operatetype = operatetype;
	}
	
	public String getOperatesequence(){
		return operatesequence;
	}
	
	public void setOperatesequence(String operatesequence){
		this.operatesequence = operatesequence;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourcesequence() {
		return sourcesequence;
	}
	public void setSourcesequence(String sourcesequence) {
		this.sourcesequence = sourcesequence;
	}     

	
}
