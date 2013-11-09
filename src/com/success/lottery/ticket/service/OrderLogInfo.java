package com.success.lottery.ticket.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

import com.success.utils.LibingUtils;
public class OrderLogInfo{

	private String	name;
	private long	inTime;
	private long	outTime;
	private String	processorName;
	private String	station;
	private String orderId;
	private String	remark;
	private String	result;

	
	public String getOrderId(){
		return orderId;
	}

	
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
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

	public String getProcessorName(){
		return processorName;
	}

	public void setProcessorName(String processorName){
		this.processorName = processorName;
	}

	public String getStation(){
		return station;
	}

	public void setStation(String station){
		this.station = station;
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
	}
}
