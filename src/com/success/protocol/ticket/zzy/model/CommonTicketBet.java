/**
 * Title: CommonTicketBet.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-12 ����05:00:17
 * @version V1.0
 */
package com.success.protocol.ticket.zzy.model;

/**
 * com.success.protocol.ticket.zzy
 * CommonTicketBet.java
 * CommonTicketBet
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-12 ����05:00:17
 * 
 */

public class CommonTicketBet {
	
	private String lotteryId;//����
	private String termNo;//����
	private String schemeTitle = "";//��������
	private String schemeDescription="";//��������	��ѡ
	private String schemeQuotient="1";//�����ݶ�	1
	private String lotterySaleid;//���۷�ʽ����ע��^�ֿ��磺1^2^
	private String schemeLotteryInfo;//�������ݣ�Ͷע����,ע�����һ�������ж�ע����^�ֿ���
	private String schemeNumbers;//����(����Ĭ��Ϊ1��)	>=1
	private String lotteryNumbers;//��Ʊע��,ע��û�гɱ���֮ǰ
	private String schemeValue;//�������
	private String agentOperateId;//�ն���ˮ��
	private String viewFlag = "2";//1 ���𷽰���2 ��ͨͶע
	private String viewlimit = "1";//�鿴���ƴ��루0���Բ鿴�������ݣ�1���ܲ鿴�������ݣ�
	private String isCheckOff = "0";//�Ƿ񱣵�(1Ϊ���ף�0Ϊ����
	private String checkOffQuotient = "0";//����ݶ�(�û����𷽰��󣬹���ķݶ������0��)
	private String quotients = "1";//Ͷע��ʽ(1
	private String investTypeId = "1";//
	private String isAppend;//�Ƿ�׷�ӣ����׷�ӣ���һעΪ��Ԫ,�˲�����Ҫ����Գ�������͸�淨35ѡ5��Ч) 
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 * @param playType
	 * @param betType
	 * @param ticketSequences
	 * @param betCode
	 * @param betmultiple
	 * @param betCount
	 * @param betAmount
	 */
	public CommonTicketBet(String lotteryId, String termNo,
			 String playType, String betType,String ticketSequences,String betCode,
			String betmultiple, String betCount, String betAmount) {
		this.lotteryId = lotteryId;
		this.termNo = termNo;
		this.agentOperateId = ticketSequences;
		this.isAppend = playType;
		this.lotterySaleid = betType;
		this.schemeNumbers = betmultiple;
		this.lotteryNumbers = betCount;
		this.schemeValue = betAmount;
		this.schemeLotteryInfo = betCode;
	}
	/**
	 * 
	 *Title: 
	 *Description:
	 */
	public CommonTicketBet(){
		
	}

	public String getAgentOperateId() {
		return agentOperateId;
	}

	public void setAgentOperateId(String agentOperateId) {
		this.agentOperateId = agentOperateId;
	}

	public String getCheckOffQuotient() {
		return checkOffQuotient;
	}

	public void setCheckOffQuotient(String checkOffQuotient) {
		this.checkOffQuotient = checkOffQuotient;
	}

	public String getInvestTypeId() {
		return investTypeId;
	}

	public void setInvestTypeId(String investTypeId) {
		this.investTypeId = investTypeId;
	}

	public String getIsAppend() {
		return isAppend;
	}

	public void setIsAppend(String isAppend) {
		this.isAppend = isAppend;
	}

	public String getIsCheckOff() {
		return isCheckOff;
	}

	public void setIsCheckOff(String isCheckOff) {
		this.isCheckOff = isCheckOff;
	}

	public String getLotteryNumbers() {
		return lotteryNumbers;
	}

	public void setLotteryNumbers(String lotteryNumbers) {
		this.lotteryNumbers = lotteryNumbers;
	}

	public String getLotterySaleid() {
		return lotterySaleid;
	}

	public void setLotterySaleid(String lotterySaleid) {
		this.lotterySaleid = lotterySaleid;
	}

	public String getQuotients() {
		return quotients;
	}

	public void setQuotients(String quotients) {
		this.quotients = quotients;
	}

	public String getSchemeDescription() {
		return schemeDescription;
	}

	public void setSchemeDescription(String schemeDescription) {
		this.schemeDescription = schemeDescription;
	}

	public String getSchemeLotteryInfo() {
		return schemeLotteryInfo;
	}

	public void setSchemeLotteryInfo(String schemeLotteryInfo) {
		this.schemeLotteryInfo = schemeLotteryInfo;
	}

	public String getSchemeNumbers() {
		return schemeNumbers;
	}

	public void setSchemeNumbers(String schemeNumbers) {
		this.schemeNumbers = schemeNumbers;
	}

	public String getSchemeQuotient() {
		return schemeQuotient;
	}

	public void setSchemeQuotient(String schemeQuotient) {
		this.schemeQuotient = schemeQuotient;
	}

	public String getSchemeTitle() {
		return schemeTitle;
	}

	public void setSchemeTitle(String schemeTitle) {
		this.schemeTitle = schemeTitle;
	}

	public String getSchemeValue() {
		return schemeValue;
	}

	public void setSchemeValue(String schemeValue) {
		this.schemeValue = schemeValue;
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}

	public String getViewlimit() {
		return viewlimit;
	}

	public void setViewlimit(String viewlimit) {
		this.viewlimit = viewlimit;
	}
	public String getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	
	

}
