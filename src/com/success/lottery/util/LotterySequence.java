package com.success.lottery.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 唐路彩票投注系统流水号获取工具，方法保证了统一标识的信息取到的流水号不会重复。
 * 唐路投注系统流水号规则 BBYYYYMMDDHHMISSnnnn
 * 		其中BB为标识，两位，如FA对应方案信息。
 * 		YYYYMMDDHHMISS，十四位，年月日时分秒
 * 		nnnn 四位序列号，不足四位左补'0'
 * 调用方法 LotterySequence.getInstance(BB).getSequence()，其中BB为要获取序列号信息的标识。
 * @author bing.li
 *
 */
public class LotterySequence{

	private static ConcurrentHashMap<String, LotterySequence> seqMap = new ConcurrentHashMap<String, LotterySequence>();
	private static String[] ZERO = {"000", "00", "0", ""};
	private static String SYS_PRE="W";

	private int seq = 1;
	private String prefix;
	
	static{
		SYS_PRE = LotteryTools.getSysSequencePre();
	}
	
	/**
	 * 这个方法提供构造LotterySequence对象实例，必须使用该方法得到LotterySequence对象实例，这样才能保证流水号唯一，保证线程安全。
	 * @param type - 所要获得LotterySequence对象实例的信息的标识，如方案信息流水号type = "FA"，彩票信息流水号type = "CP"。
	 * @return - 返回一个LotterySequence对象实例。
	 */
	public static LotterySequence getInstatce(String type){
		synchronized(type){
			String sysType = SYS_PRE + type;
			LotterySequence sequence = seqMap.get(sysType);
			if (sequence == null){
				sequence = new LotterySequence(sysType);
				seqMap.put(sysType, sequence);
			}
			return sequence;
		}
	}
	
	private LotterySequence(){
		
	}
	
	private LotterySequence(String type){
		this.prefix = type;
	}
	
	/**
	 * 获得一个唯一的流水号
	 * @param type - 所要获得流水号信息的标识，如方案信息流水号type = "FA"，彩票信息流水号type = "CP"。
	 * @return - 返回一个20位的流水号，格式 BBYYYYMMDDHHMISSnnnn
	 */
	public String getSequence(){
		Calendar calendar = Calendar.getInstance();
		String now = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
		String val = nextVal() + "";
		return prefix + now + ZERO[val.length() - 1] + val;
	}
	
	private synchronized int nextVal(){
		if (seq > 9999) {
			seq = 1;
		}
		return seq++;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){
		//测试99个线程同时取方案信息流水号
		for(int i = 0; i < 99; i++){
			new Thread(){
				public void run(){
					for(int j = 0; j < 20; j++){
						System.out.println(Thread.currentThread().getId() + "-" + LotterySequence.getInstatce("FA").getSequence());
					}
				}
			}.start();
		}
	}
}
