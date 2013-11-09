package com.success.lottery.account.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

import com.success.utils.LibingUtils;

public class IPSReturnInfo{

	private long	inTime;
	private String	billNo;
	private String	mercode;
	private String	currencyType;
	private String	amount;
	private int		date;
	private char	succ;
	private String	msg;
	private String	attach;
	private String	ipsOrderId;
	private String	retenCodeType;
	private String	signature;
	private String	ipsBankTime;
	private String remark;
	private String	result;

	public long getInTime(){
		return inTime;
	}

	public void setInTime(long inTime){
		this.inTime = inTime;
	}

	public String getBillNo(){
		return billNo;
	}

	public void setBillNo(String billNo){
		this.billNo = billNo;
	}

	public String getMercode(){
		return mercode;
	}

	public void setMercode(String mercode){
		this.mercode = mercode;
	}

	public String getCurrencyType(){
		return currencyType;
	}

	public void setCurrencyType(String currencyType){
		this.currencyType = currencyType;
	}

	public String getAmount(){
		return amount;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public int getDate(){
		return date;
	}

	public void setDate(int date){
		this.date = date;
	}

	public char getSucc(){
		return succ;
	}

	public void setSucc(char succ){
		this.succ=succ;
	}

	public String getMsg(){
		return msg;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getAttach(){
		return attach;
	}

	public void setAttach(String attach){
		this.attach = attach;
	}

	public String getIpsOrderId(){
		return ipsOrderId;
	}

	public void setIpsOrderId(String ipsOrderId){
		this.ipsOrderId = ipsOrderId;
	}

	public String getRetenCodeType(){
		return retenCodeType;
	}

	public void setRetenCodeType(String retenCodeType){
		this.retenCodeType = retenCodeType;
	}

	public String getSignature(){
		return signature;
	}

	public void setSignature(String signature){
		this.signature = signature;
	}

	public String getIpsBankTime(){
		return ipsBankTime;
	}

	public void setIpsBankTime(String ipsBankTime){
		this.ipsBankTime = ipsBankTime;
	}

	
	public String getRemark(){
		return remark;
	}

	
	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getResult(){
		return result;
	}

	public void setResult(String result){
		this.result = result;
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
			List<String> fields = null;
			try{
				fields = LibingUtils.getFieldList(remark, ';');
				if(((String)fields.get(0)).indexOf(name) >= 0){
					remark = name + "=" + value;
				}else{
					remark = (String)fields.get(0);
				}
				for(int i = 1; i < fields.size(); i++){
					if((fields.get(i)).indexOf(name) >= 0){
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
	}
}
