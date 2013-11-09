package com.success.lottery.ticket.service.interf;

import java.io.FileInputStream;
import java.util.List;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;


public interface BetSplitterService{
	//拆票时出现异常
	public static final int BETSPLITEXCEPTION = 411501;
	public static final String BETSPLITEXCEPTION_STR = "拆票时出现程序异常：";

	public static final int BETSPLITENQUEEXCEPTION = 411502;
	public static final String BETSPLITENQUEEXCEPTION_STR = "拆票后如队列错误：";

	//
	public static final int CREATEFILETOPRINTEXCEPTION = 411550;
	public static final String CREATEFILETOPRINTEXCEPTION_STR = "生成大赢家打印文件时出现时程序异常：";

	//生成出票文件时未找到可以出票的文件
	public static final int NOTFOUNDPRINTTICKET = 410501;
	public static final String NOTFOUNDPRINTTICKET_STR = "未找到需要生成文件的订单";
	
	//下载出票文件时获得下载文件inputstream错误
	public static final int GETDOWNLOADFILEEXCEPTION = 411502;
	public static final String GETDOWNLOADFILEEXCEPTION_STR = "获得下载文件句柄异常：";
	
	
	//下载出票文件时获得下载文件inputstream错误
	public static final int NOTFOUNDCONFIRMTICKET = 410502;
	public static final String NOTFOUNDCONFIRMTICKET_STR = "未找到需要确认打印结果的彩票信息";

	public static final int CONFIRMTICKETEXCEPTION = 411503;
	public static final String CONFIRMTICKETEXCEPTION_STR = "确认打印结果出现异常：";
	
	/**
	 * 对订单进行拆票，根据配置文件获取不同的Splitter，如青海体彩获得的是 QHTCOrderSplitter
	 * 拆票只是根据不同出票点的规则生成BetTicket表的相应记录。
	 * @param order
	 * @return
	 * @throws LotteryException
	 */
	public String orderSplit(BetOrderDomain order) throws LotteryException;
	
	/**
	 * 生成出票文件，是TicketProcessor的一种，这里偷懒直接定义了该接口，该接口名称更合适的应该是ticketProcess
	 * 这里定义的接口专用于大赢家出票文件生成，是由前台页面调用发起的
	 * @param lotteryId
	 * @param term
	 * @param username
	 * @return
	 * @throws LotteryException
	 */
	public String createPrintFile(int lotteryId, String term, String username) throws LotteryException;
	
	/**
	 * 获得下载文件的文件输入流
	 * @param fileName
	 * @return
	 * @throws LotteryException
	 */
	public FileInputStream getPrintFileInputStream(String fileName) throws LotteryException;
	
	public int confirmTicket(List<String> ticketSequences, int confirmFlag, String confirmMsg, String username) throws LotteryException;
}
