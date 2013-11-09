package com.success.lottery.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ��·��ƱͶעϵͳ��ˮ�Ż�ȡ���ߣ�������֤��ͳһ��ʶ����Ϣȡ������ˮ�Ų����ظ���
 * ��·Ͷעϵͳ��ˮ�Ź��� BBYYYYMMDDHHMISSnnnn
 * 		����BBΪ��ʶ����λ����FA��Ӧ������Ϣ��
 * 		YYYYMMDDHHMISS��ʮ��λ��������ʱ����
 * 		nnnn ��λ���кţ�������λ��'0'
 * ���÷��� LotterySequence.getInstance(BB).getSequence()������BBΪҪ��ȡ���к���Ϣ�ı�ʶ��
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
	 * ��������ṩ����LotterySequence����ʵ��������ʹ�ø÷����õ�LotterySequence����ʵ�����������ܱ�֤��ˮ��Ψһ����֤�̰߳�ȫ��
	 * @param type - ��Ҫ���LotterySequence����ʵ������Ϣ�ı�ʶ���緽����Ϣ��ˮ��type = "FA"����Ʊ��Ϣ��ˮ��type = "CP"��
	 * @return - ����һ��LotterySequence����ʵ����
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
	 * ���һ��Ψһ����ˮ��
	 * @param type - ��Ҫ�����ˮ����Ϣ�ı�ʶ���緽����Ϣ��ˮ��type = "FA"����Ʊ��Ϣ��ˮ��type = "CP"��
	 * @return - ����һ��20λ����ˮ�ţ���ʽ BBYYYYMMDDHHMISSnnnn
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
		//����99���߳�ͬʱȡ������Ϣ��ˮ��
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
