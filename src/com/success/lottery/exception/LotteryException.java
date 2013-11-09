package com.success.lottery.exception;

/**
 * 唐路彩票系统自定义Exception，该Exception定义了异常编号，用于统一规划错误信息；<br>
 * 唐路所有模块提供的Service出现程序错误、程序异常、业务逻辑错误、不符合业务规则时，都需要抛出该异常。<br>
 * 异常编号暂时定义为6位，前两位用来区分模块，开发人员开发时分配该编号。<br>
 * 第三位用来区分异常类型，如是程序错误、通讯错误、数据库读写错误、业务逻辑错误等，由开发人员自行区分，模块开发完毕给出说明。<br>
 * 后3位是不同异常错误的编号，由开发人员自行定义同时异常需给出明确错误信息。<br>
 * 		如: 100001为账户管理模块用户注册时出现的程序异常。<br>
 * 			101002为该用户用户名已经存在。<br>
 * @author bing.li
 */
public class LotteryException extends Exception{

	private int type;
	private String message = "";
	
	/**
	 * 构造带指定异常编号的LotteryException，异常消息为""；
	 * @param type - 异常编号
	 */
	public LotteryException(int type){
		this.type = type;
	}
	
	/**
	 * 构造带指定异常编号以及异常信息的LotteryException；
	 * @param type
	 * @param message
	 */
	public LotteryException(int type, String message){
		this.type = type;
		this.message = message;
	}
	
	/**
	 * 获得异常编号
	 * @return 异常编号
	 */
	public int getType(){
		return type;
	}

	/**
	 * 获得异常错误信息
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
			throw new LotteryException(100001, "哈哈哈哈哈");
		}catch(LotteryException e){
			System.out.println(e.toString());
			System.out.println(e.getType());
			e.printStackTrace();
		}
	}
}
