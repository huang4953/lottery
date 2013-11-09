package com.success.lottery.sms;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

import com.success.protocol.smip.SMIP_MT;
import com.success.utils.LibingUtils;


public class MT{

	private String clientName;
	private long sequence;
	private String msgId;
	private int pkTotal = 1;
	private int pkNumber = 1;
	private String toPhone;
	private String feePhone;
	private String spNum;
	private String spNumExt;
	private String channelId;
	private int type = 1;
	private String linkId;
	private int msgLength;
	private String msgContent;
	private String reserve;

	private String	toIp;
	private long	inTime;
	private long	outTime;
	private String	errMsg;
	private String	processorName;
	private String	remark;

	private int sendCount = 0;
	
	
	
	
	public int getSendCount(){
		return sendCount;
	}



	
	public void sendCount(){
		sendCount++;
	}



	public long getSequence(){
		return sequence;
	}


	
	public void setSequence(long sequence){
		this.sequence = sequence;
	}


	public String getToIp(){
		return toIp;
	}

	
	public void setToIp(String toIp){
		this.toIp = toIp;
	}

	
	public long getInTime(){
		return inTime;
	}

	
	public void setInTime(long inTime){
		this.inTime = inTime;
	}

	
	public long getOutTime(){
		return outTime;
	}

	
	public void setOutTime(long outTime){
		this.outTime = outTime;
	}

	
	public String getErrMsg(){
		return errMsg;
	}

	
	public void setErrMsg(String errMsg){
		this.errMsg = errMsg;
	}

	
	public String getProcessorName(){
		return processorName;
	}

	
	public void setProcessorName(String processorName){
		this.processorName = processorName;
	}

	
	public String getRemark(){
		return remark;
	}

	
	public void setRemark(String remark){
		this.remark = remark;
	}

	/**
	 * Push 短信时使用的MT对象构造方法
	 * @param clientName: 发送短信使用的SMSClient名称，决定了该MT对象下发时放入的队列，如果为null或""，则放入队列 "MTQue"
	 * @param phone: 发送短信对象的电话号码
	 * @param spNum: 发送短信时使用的特服号
	 * @param spNuxExt: 发送短信时使用的长号码
	 * @param channelId: 发送短信使用的ChannelId
	 * @param type: 发送短信的类型，1-Text 11-Wap Push
	 * @param msgContent: 发送短信的内容，GBK编码
	 */
	public MT(String clientName, String phone, String spNum, String spNumExt, String channelId, int type, String msgContent){
		this.clientName = clientName;
		this.toPhone = phone;
		this.feePhone = phone;
		this.spNum = spNum;
		this.spNumExt = spNumExt;
		this.channelId = channelId;
		this.type = type;
		this.msgContent = msgContent;
		if(StringUtils.isBlank(msgContent)){
			this.msgLength = 0;
		}else{
			try{
				this.msgLength = msgContent.getBytes("GBK").length;
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * IOD 短信时使用的MT对象构造方法
	 * @param mo: 上行时的MO对象
	 */
	public MT(MO mo){
		this.clientName = mo.getClientName();
		this.msgId = mo.getMsgId();
		this.toPhone = mo.getFromPhone();
		this.feePhone = mo.getFromPhone();
		this.spNum = mo.getSpNum();
		this.spNumExt = mo.getSpNumExt();
		this.linkId = mo.getLinkId();
	}

	public String getMsgId(){
		return msgId;
	}
	
	public void setMsgId(String msgId){
		this.msgId = msgId;
	}
	
	public int getPkTotal(){
		return pkTotal;
	}
	
	
	public String getClientName(){
		return clientName;
	}

	
	public void setClientName(String clientName){
		this.clientName = clientName;
	}

	public void setPkTotal(int pkTotal){
		this.pkTotal = pkTotal;
	}

	
	public int getPkNumber(){
		return pkNumber;
	}

	
	public void setPkNumber(int pkNumber){
		this.pkNumber = pkNumber;
	}

	
	public String getToPhone(){
		return toPhone;
	}

	
	public void setToPhone(String toPhone){
		this.toPhone = toPhone;
	}

	
	public String getFeePhone(){
		return feePhone;
	}

	
	public void setFeePhone(String feePhone){
		this.feePhone = feePhone;
	}

	
	public String getSpNum(){
		return spNum;
	}

	
	public void setSpNum(String spNum){
		this.spNum = spNum;
	}

	
	public String getSpNumExt(){
		return spNumExt;
	}

	
	public void setSpNumExt(String spNumExt){
		this.spNumExt = spNumExt;
	}

	
	public String getChannelId(){
		return channelId;
	}

	
	public void setChannelId(String channelId){
		this.channelId = channelId;
	}

	
	public int getType(){
		return type;
	}

	
	public void setType(int type){
		this.type = type;
	}

	
	public String getLinkId(){
		return linkId;
	}

	
	public void setLinkId(String linkId){
		this.linkId = linkId;
	}

	
	public int getMsgLength(){
		return msgLength;
	}

	
	public void setMsgLength(int msgLength){
		this.msgLength = msgLength;
	}

	
	public String getMsgContent(){
		return msgContent;
	}

	
	public void setMsgContent(String msgContent){
		this.msgContent = msgContent;
	}

	
	public String getReserve(){
		return reserve;
	}

	
	public void setReserve(String reserve){
		this.reserve = reserve;
	}

	public void addRemark(String name, String value){
		if(!name.startsWith("$")){
			name = "$(" + name + ")";
		}
		if(value == null || value.trim().length() == 0)
			value = "true";
		// if (value != null && value.trim().length() > 0) {
		value = value.trim();
		if(remark == null){
			remark = name + "=" + value;
		}else if(remark.indexOf(name) < 0){
			remark += ";" + name + "=" + value;
		}else{
			List fields = null;
			try{
				fields = LibingUtils.getFieldList(remark, ';');
				if(((String)fields.get(0)).indexOf(name) >= 0){
					remark = name + "=" + value;
				}else{
					remark = (String)fields.get(0);
				}
				for(int i = 1; i < fields.size(); i++){
					if(((String)fields.get(i)).indexOf(name) >= 0){
						remark += ";" + name + "=" + value;
					}else{
						remark += ";" + fields.get(i);
					}
				}
			}catch(Exception e){
				return;
			}
		}
	}

	public String getRemarkValue(String name){
		if(StringUtils.isBlank(remark)){
			return null;
		}
		if(!name.startsWith("$")){
			name = "$(" + name + ")";
		}
		int i = remark.indexOf(name);
		if(i < 0)
			return null;
		String s = remark.substring(i);
		i = s.indexOf(';');
		if(i == name.length())
			return null;
		if(i < 0){
			i = s.indexOf('=');
			if(i < 0)
				return null;
			return s.substring(i + 1);
		}else{
			return s.substring(name.length() + 1, i);
		}
	}

	public void trimSrcRemark(){
		if(StringUtils.isBlank(remark)){
			return;
		}
		if(remark.length() < 50)
			return;
		List<String> fields = null;
		try{
			fields = LibingUtils.getFieldList(remark, ';');
		}catch(Exception e){
			return;
		}
		HashSet<String> set = new HashSet<String>();
		for(String field : fields){
			if(field.indexOf("$(") > -1){
				set.add(field);
			}
		}
		remark = "";
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			remark += it.next() + ";";
		}
		remark = remark.substring(0, remark.length() - 1);
	}

	public SMIP_MT parseToSMIPMessage(){
		SMIP_MT mt = new SMIP_MT(0);
		mt.setMsgId(this.getMsgId());
		mt.setPkNumber(1);
		mt.setPkTotal(1);
		mt.setToPhone(this.getToPhone());
		mt.setFeePhone(this.getFeePhone());
		mt.setSpNum(this.getSpNum());
		mt.setSpNumExt(this.spNumExt);
		mt.setChannelId(this.getChannelId());
		mt.setType(this.getType());
		mt.setLinkId(this.getLinkId());
		mt.setMsgContent(msgContent);
		return mt;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb
			.append("MT{")
			.append("[msgId=" + this.msgId).append(";")
			.append("pkTotal=" + this.pkTotal).append(";")
			.append("pkNumber=" + this.pkNumber).append(";")
			.append("toPhone=" + this.toPhone).append(";")
			.append("feePhone=" + this.feePhone).append(";")
			.append("spNum=" + this.spNum).append(";")
			.append("spNumExt=" + this.spNumExt).append(";")
			.append("channelId=" + this.channelId).append(";")
			.append("type=" + this.type).append(";")
			.append("linkId=" + this.linkId).append(";")
			.append("msgLength=" + this.msgLength).append(";")
			.append("msgContent={" + this.msgContent).append("};")
			.append("reserve=" + this.reserve).append("][")
			.append("clientName=" + this.clientName).append(";")
			.append("processorName=" + this.processorName).append(";")
			.append("toIp=" + this.toIp).append(";")
			.append("inTime=" + this.inTime).append(";")
			.append("outTime=" + this.outTime).append(";")
			.append("errMsg={" + this.errMsg).append("};")
			.append("sendCount=" + this.sendCount).append(";")
			.append("remark={" + this.remark).append("}]")
			.append("}");
		return sb.toString();
	}
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
	}
}
