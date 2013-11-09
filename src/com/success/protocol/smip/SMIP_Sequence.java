package com.success.protocol.smip;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;

public class SMIP_Sequence {
	
//	static class TTT implements Runnable{
//		private String appId;
//		public TTT(String appId){
//			this.appId = appId;
//		}
//		public void run(){
//			for(int i = 0; i < 20; i++){
//				int seq = SMIP_Sequence.getNextVal(appId);
//				Log.logSystemOut(Thread.currentThread().getName() + "-" + seq);
//				set.add(Integer.valueOf(seq));
//			}
//		}
//	}
//	public static Set<Integer> set = new TreeSet<Integer>();
	private static Hashtable<String, SMIP_Sequence> sequenceMap = new Hashtable<String, SMIP_Sequence>();
	private int seq = 0;
	private Object objLock = new Object();
	private SMIP_Sequence(){
	}

	private SMIP_Sequence(int start) {
		seq = start;
	}

	private synchronized static SMIP_Sequence getInstance(String appId) {
		SMIP_Sequence sequence = sequenceMap.get(appId);
		if (sequence == null) {
			sequence = new SMIP_Sequence(0);
			sequenceMap.put(appId, sequence);
		}
		return sequence;
	}

	public static int getNextVal(String appId){
		return getInstance(appId).nextVal();
	}
	
	private int nextVal(){
		Calendar c = Calendar.getInstance();
		int rc = 0;
		rc += c.get(Calendar.HOUR_OF_DAY) * 100000000;
		rc += c.get(Calendar.MINUTE) * 1000000;
		rc += c.get(Calendar.SECOND) * 10000;
		synchronized(objLock){
			if (seq > 9999) {
				seq = 0;
			}
			return rc + seq++;
		}
	}
	
	public static void main(String[] args) {
	}
}
