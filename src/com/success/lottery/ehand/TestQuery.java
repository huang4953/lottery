package com.success.lottery.ehand;

import java.util.List;

import com.success.lottery.ehand.ehandserver.bussiness.EhandQueryService;
import com.success.lottery.ehand.ehandserver.bussiness.EhandTicketService;
import com.success.lottery.ehand.ehandserver.bussiness.model.EhandTermBussiModel;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.protocol.ticket.zzy.model.CashResult;
import com.success.protocol.ticket.zzy.model.TicketBetResult;
import com.success.protocol.ticket.zzy.model.TicketQueryResult;

public class TestQuery {
	/**
	 * 
	 * Title: test50200<br>
	 * Description: <br>
	 *              <br>奖期查询
	 */
	public static void test50200(){
		EhandTermBussiModel eterm = null;
		
		try {
			eterm = EhandQueryService.queryEterm50200(1200001);
			if(eterm != null){
				System.out.println(eterm.toString());
			}
		} catch (LotteryException e) {
			System.out.println("错误代码:"+e.getType());
			System.out.println("错误描述:"+e.getMessage());
			e.printStackTrace();
			
		}
	}
	/**
	 * 
	 * Title: test50203<br>
	 * Description: <br>
	 *              <br>测试开奖公告查询
	 */
	public static void test50203(){
		EhandTermBussiModel eterm = null;
		
		try {
			eterm = EhandQueryService.QueryEkjgg50203(1000001,"11016");
			if(eterm != null){
				System.out.println(eterm.toString());
			}
		} catch (LotteryException e) {
			System.out.println("错误代码:"+e.getType());
			System.out.println("错误描述:"+e.getMessage());
			e.printStackTrace();
			
		}
	}
	/**
	 * 
	 * Title: test50206<br>
	 * Description: <br>
	 *              <br>测试获取中奖数据
	 */
	public static void test50206(){
		List<CashResult> cashResultList = null;
		
		try {
			cashResultList = EhandQueryService.QueryCashResult50206(1200001, "11021236");
			if(cashResultList != null){
				for(CashResult one : cashResultList){
					System.out.println("中奖结果:" + one.toString());
				}
			}
		} catch (LotteryException e) {
			System.out.println("错误代码:"+e.getType());
			System.out.println("错误描述:"+e.getMessage());
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 * Title: test50208<br>
	 * Description: <br>
	 *              <br>销售及中奖按期查询测试
	 */
	public static void test50208(){
		EhandTermBussiModel eterm = null;
		
		try {
			eterm = EhandQueryService.QueryEtermSale50208(1200001,"11021236");
			if(eterm != null){
				System.out.println(eterm.toString());
			}
		} catch (LotteryException e) {
			System.out.println("错误代码:"+e.getType());
			System.out.println("错误描述:"+e.getMessage());
			e.printStackTrace();
			
		}
		
	}
	/**
	 * 
	 * Title: test50209<br>
	 * Description: <br>
	 *              <br>奖期状态情况查询
	 */
	public static void test50209(){
		EhandTermBussiModel eterm = null;
		
		try {
			eterm = EhandQueryService.QueryEterm50209(1200001,"11021236");
			if(eterm != null){
				System.out.println(eterm.toString());
			}
		} catch (LotteryException e) {
			System.out.println("错误代码:"+e.getType());
			System.out.println("错误描述:"+e.getMessage());
			e.printStackTrace();
			
		}
	}
	/**
	 * 
	 * Title: testSendTicket<br>
	 * Description: <br>
	 *              <br>测试出票
	 */
	public static void testSendTicket(){
		TicketBetResult printResult = null;
		
		try {
			//彩种id，彩期，玩法，投注方式，投注系统票Sequence，投注串，倍数，注数，投注金额
			BetTicketDomain printTicket = new BetTicketDomain();
			printTicket.setLotteryId(1000001);
			printTicket.setBetTerm("11017");
			printTicket.setPlayType(0);
			printTicket.setBetType(0);
			printTicket.setTicketSequence("CP201102122023170003");
			printTicket.setBetCode("3016201431|0311");
			printTicket.setBetMultiple(1);
			printTicket.setBetCount(1);
			printTicket.setBetAmount(200);
			printResult = EhandTicketService.printOneTicket(printTicket);
			if(printResult != null){
				System.out.println("结果:"+printResult);
			}
		} catch (LotteryException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * Title: testQuertTicketReault<br>
	 * Description: <br>
	 *              <br>获取一张票的出票结果
	 */
	public static void testQuertTicketReault(){
		TicketQueryResult printResult = null;
		try{
			printResult = EhandTicketService.QueryPrintTicket("CP201102161915110001");
			if(printResult != null){
				System.out.println("出票结果:"+printResult);
			}
		}catch(LotteryException e){
			e.printStackTrace();
		}
	}
	/**
	 * Title: main<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param args
	 */

	public static void main(String[] args) {
		System.out.println("***********begin");
		//测试奖期查询
		//test50200();
		//测试查询开奖公告
		test50203();
		
		//测试获取中奖数据
		//test50206();
		//测试销售及中奖情况
		//test50208();
		
		//奖期状态查询
		//test50209();
		
		//测试大票
		//testSendTicket();
		
		//测试查询票的出票结果
		//testQuertTicketReault();
		
		System.out.println("***********end");
		

	}

}
