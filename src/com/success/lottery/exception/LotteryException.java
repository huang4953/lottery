package com.success.lottery.exception;

/**
 * ��·��Ʊϵͳ�Զ���Exception����Exception�������쳣��ţ�����ͳһ�滮������Ϣ��<br>
 * ��·����ģ���ṩ��Service���ֳ�����󡢳����쳣��ҵ���߼����󡢲�����ҵ�����ʱ������Ҫ�׳����쳣��<br>
 * �쳣�����ʱ����Ϊ6λ��ǰ��λ��������ģ�飬������Ա����ʱ����ñ�š�<br>
 * ����λ���������쳣���ͣ����ǳ������ͨѶ�������ݿ��д����ҵ���߼�����ȣ��ɿ�����Ա�������֣�ģ�鿪����ϸ���˵����<br>
 * ��3λ�ǲ�ͬ�쳣����ı�ţ��ɿ�����Ա���ж���ͬʱ�쳣�������ȷ������Ϣ��<br>
 * 		��: 100001Ϊ�˻�����ģ���û�ע��ʱ���ֵĳ����쳣��<br>
 * 			101002Ϊ���û��û����Ѿ����ڡ�<br>
 * @author bing.li
 */
public class LotteryException extends Exception{

	private int type;
	private String message = "";
	
	/**
	 * �����ָ���쳣��ŵ�LotteryException���쳣��ϢΪ""��
	 * @param type - �쳣���
	 */
	public LotteryException(int type){
		this.type = type;
	}
	
	/**
	 * �����ָ���쳣����Լ��쳣��Ϣ��LotteryException��
	 * @param type
	 * @param message
	 */
	public LotteryException(int type, String message){
		this.type = type;
		this.message = message;
	}
	
	/**
	 * ����쳣���
	 * @return �쳣���
	 */
	public int getType(){
		return type;
	}

	/**
	 * ����쳣������Ϣ
	 */
	public String getMessage(){
		return message;
	}

	/**
	 * @param args
	 * @throws LotteryException 
	 */
	public static void main(String[] args) {
		try{
			throw new LotteryException(100001, "����������");
		}catch(LotteryException e){
			System.out.println(e.toString());
			System.out.println(e.getType());
			e.printStackTrace();
		}
	}
}
