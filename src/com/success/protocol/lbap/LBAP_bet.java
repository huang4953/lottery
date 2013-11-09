/**
 * Title: LBAP_bet.java
 * @Package com.success.protocol.lbap
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-4 下午03:31:36
 * @version V1.0
 */
package com.success.protocol.lbap;

import java.util.List;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * com.success.protocol.lbap
 * LBAP_bet.java
 * LBAP_bet
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-4 下午03:31:36
 * 
 */

public class LBAP_bet extends LBAP_DataPack {
	
	private String userId;
	private int sourceType;
	private int lotteryId;
	private int playType;
	private int betType;
	private int betMultiple;
	private String currentTerm;
	private String betCode;
	private int betNum;
	private int amount;
	private String reserve;
	private String winStopped;
	private TreeMap<String,Integer> chaseInfo ;
	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_bet() {
		// TODO 自动生成构造函数存根
	}

	/**
	 *Title: 
	 *Description: 
	 * @param version
	 * @param command
	 * @param clientId
	 * @param messageId
	 * @param encyptionType
	 * @param md
	 * @param messageBody
	 */
	public LBAP_bet(String version, String command, String clientId,
			String messageId, int encyptionType, String md, String messageBody) {
		super(version, command, clientId, messageId, encyptionType, md,
				messageBody);
		// TODO 自动生成构造函数存根
	}

	/**
	 *Title: 
	 *Description: 
	 * @param messageId
	 * @param encyptionType
	 */
	public LBAP_bet(String messageId, int encyptionType) {
		super(messageId, encyptionType);
		// TODO 自动生成构造函数存根
	}

	/* (非 Javadoc)
	 *Title: decodeMessageBody
	 *Description: 
	 * @throws Exception
	 * @see com.success.protocol.lbap.LBAP_DataPack#decodeMessageBody()
	 */
	@Override
	public void decodeMessageBody() throws Exception {
		Document document = DocumentHelper.parseText("<body>"+this.messageBody+"</body>");
		Node node = null;
		this.userId = (node = document.selectSingleNode("/body/userId")) == null ? null : node.getText();
		this.sourceType = (node = document.selectSingleNode("/body/sourceType")) == null ? -1 : Integer.parseInt(node.getText());
		this.lotteryId = (node = document.selectSingleNode("/body/lotteryId")) == null ? -1 : Integer.parseInt(node.getText());
		this.playType = (node = document.selectSingleNode("/body/playType")) == null ? -1 : Integer.parseInt(node.getText());
		this.betType = (node = document.selectSingleNode("/body/betType")) == null ? -1 : Integer.parseInt(node.getText());
		this.betMultiple = (node = document.selectSingleNode("/body/betMultiple")) == null ? -1 : Integer.parseInt(node.getText());
		this.currentTerm = (node = document.selectSingleNode("/body/currentTerm")) == null ? null : node.getText();
		this.betCode = (node = document.selectSingleNode("/body/betCode")) == null ? null : node.getText();
		this.betNum = (node = document.selectSingleNode("/body/betNum")) == null ? -1 : Integer.parseInt(node.getText());
		this.amount = (node = document.selectSingleNode("/body/amount")) == null ? -1 : Integer.parseInt(node.getText());
		this.reserve = (node = document.selectSingleNode("/body/reserve")) == null ? null : node.getText();
		Node chaseInfo = document.selectSingleNode("/body/chaseInfo");
		if(chaseInfo != null){
			TreeMap<String,Integer> chaseMap = new TreeMap<String,Integer>();
			this.setWinStopped(((Element)chaseInfo).attributeValue("winStopped"));
			List<Node> chase = chaseInfo.selectNodes("chase");
			for(Node one : chase){
				chaseMap.put(one.selectSingleNode("termNo").getText(), Integer.parseInt(one.selectSingleNode("multiple").getText()));
			}
			this.setChaseInfo(chaseMap);
		}
		this.reserve = (node = document.selectSingleNode("/body/reserve")) == null ? null : node.getText();

	}

	/* (非 Javadoc)
	 *Title: encodeMessageBody
	 *Description: 
	 * @see com.success.protocol.lbap.LBAP_DataPack#encodeMessageBody()
	 */
	@Override
	public void encodeMessageBody() {
		// TODO 自动生成方法存根

	}
	
	public String getBetCode() {
		return betCode;
	}

	public void setBetCode(String betCode) {
		this.betCode = betCode;
	}
	
	public int getBetType() {
		return betType;
	}

	public void setBetType(int betType) {
		this.betType = betType;
	}

	public TreeMap<String, Integer> getChaseInfo() {
		return chaseInfo;
	}

	public void setChaseInfo(TreeMap<String, Integer> chaseInfo) {
		this.chaseInfo = chaseInfo;
	}

	public String getCurrentTerm() {
		return currentTerm;
	}

	public void setCurrentTerm(String currentTerm) {
		this.currentTerm = currentTerm;
	}

	public int getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}

	public int getPlayType() {
		return playType;
	}

	public void setPlayType(int playType) {
		this.playType = playType;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getBetMultiple() {
		return betMultiple;
	}

	public void setBetMultiple(int betMultiple) {
		this.betMultiple = betMultiple;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getBetNum() {
		return betNum;
	}

	public void setBetNum(int betNum) {
		this.betNum = betNum;
	}

	public String getWinStopped() {
		return winStopped;
	}

	public void setWinStopped(String winStopped) {
		this.winStopped = winStopped;
	}

}
