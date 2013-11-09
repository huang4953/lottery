package com.success.lottery.sms;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

import com.success.protocol.smip.SMIP_MO;
import com.success.utils.LibingUtils;
public class MO{

	private String	clientName;
	private long sequence;
	private String	msgId		= "";
	private String	fromPhone	= "";
	private String	spNum		= "";
	private String	spNumExt	= "";
	private String	linkId		= "";
	private int		msgLength	= 0;
	private String	msgContent	= "";
	private String	reserve		= "";
	private String	fromIp;
	private long	inTime;
	private long	outTime;
	private String	errMsg;
	private String	processorName;
	private String	remark;

	
	public long getSequence(){
		return sequence;
	}

	
	public void setSequence(long sequence){
		this.sequence = sequence;
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

	public String getErrMsg(){
		return errMsg;
	}

	public void setErrMsg(String errMsg){
		this.errMsg = errMsg;
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

	public String getFromIp(){
		return fromIp;
	}

	public void setFromIp(String fromIp){
		this.fromIp = fromIp;
	}

	public MO(){
	}
	
	public MO(SMIP_MO mo){
		setSequence(mo.getSequenceID());
		if(!StringUtils.isBlank(mo.getMsgId())){
			setMsgId(mo.getMsgId().trim());
		}
		if(!StringUtils.isBlank(mo.getFromPhone())){
			setFromPhone(mo.getFromPhone().trim());
		}
		if(!StringUtils.isBlank(mo.getSpNum())){
			setSpNum(mo.getSpNum().trim());
		}
		if(!StringUtils.isBlank(mo.getSpNumExt())){
			setSpNumExt(mo.getSpNumExt().trim());
		}
		if(!StringUtils.isBlank(mo.getLinkId())){
			setLinkId(mo.getLinkId().trim());
		}
		setMsgLength(mo.getMsgLength());
		if(!StringUtils.isBlank(mo.getMsgContent())){
			setMsgContent(mo.getMsgContent().trim());
		}
		if(!StringUtils.isBlank(mo.getReserve())){
			setReserve(mo.getReserve().trim());
		}
	}

	public String getMsgId(){
		return msgId;
	}

	public void setMsgId(String msgId){
		this.msgId = msgId;
	}

	public String getFromPhone(){
		return fromPhone;
	}

	public void setFromPhone(String fromPhone){
		this.fromPhone = fromPhone;
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

	public String getClientName(){
		return clientName;
	}

	public void setClientName(String clientName){
		this.clientName = clientName;
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

	public String toString(){
		StringBuffer sb = new StringBuffer();	
		sb
			.append("MO{")
			.append("[msgId=" + this.msgId).append(";")
			.append("fromPhone=" + this.fromPhone).append(";")
			.append("spNum=" + this.spNum).append(";")
			.append("spNumExt=" + this.spNumExt).append(";")
			.append("linkId=" + this.linkId).append(";")
			.append("msgLength=" + this.msgLength).append(";")
			.append("msgContent={" + this.msgContent).append("};")
			.append("reserve=" + this.reserve).append("][")
			.append("clientName=" + this.clientName).append(";")
			.append("processorName=" + this.processorName).append(";")
			.append("fromIp=" + this.fromIp).append(";")
			.append("inTime=" + this.inTime).append(";")
			.append("outTime=" + this.outTime).append(";")
			.append("errMsg={" + this.errMsg).append("};")
			.append("remark={" + this.remark).append("}]")
			.append("}");
		return sb.toString();
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException{
		MO mo = new MO();
		mo.setClientName("SMSClient1");
		mo.setFromIp("192.168.0.12:6789");
		mo.setMsgId("87274628482223");
		mo.setFromPhone("13761874366");
		mo.setSpNum("10662001");
		mo.setSpNumExt("333");
		mo.setLinkId("4444444444");
		mo.setMsgContent("我爱比诶及噢乖哦啊付款");
		mo.setMsgLength("我爱比诶及噢乖哦啊付款".getBytes("GBK").length);
		mo.setInTime(System.currentTimeMillis());
		mo.addRemark("aaa", "1111");
		mo.addRemark("bbb", "222");
		mo.addRemark("bbb", "dasfds");
		System.out.println(mo.toString());

		mo.setErrMsg("dasf啊代理商房价,adsf拉登说");
		mo.setProcessorName("MOProcess1");
		System.out.println(mo.toString());
		
		MT mt = new MT(mo);
		mt.setChannelId("1000001");
		mt.setProcessorName("SMSProcessor1");
		mt.addRemark("fee", "1111");
		mt.addRemark("aaa", "222");
		mt.addRemark("ccc", "dasfds");
		mt.setInTime(mo.getInTime());
		mt.setOutTime(System.currentTimeMillis());
		mt.setMsgContent("专营您头啊回答十分激烈；非常好发大水");
		mt.setErrMsg("");
		System.out.println(mt.toString());
	}
}
