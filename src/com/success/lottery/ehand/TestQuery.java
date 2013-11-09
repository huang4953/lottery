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
	 *              <br>���ڲ�ѯ
	 */
	public static void test50200(){
		EhandTermBussiModel eterm = null;
		
		try {
			eterm = EhandQueryService.queryEterm50200(1200001);
			if(eterm != null){
				System.out.println(eterm.toString());
			}
		} catch (LotteryException e) {
			System.out.println("�������:"+e.getType());
			System.out.println("��������:"+e.getMessage());
			e.printStackTrace();
			
		}
	}
	/**
	 * 
	 * Title: test50203<br>
	 * Description: <br>
	 *              <br>���Կ��������ѯ
	 */
	public static void test50203(){
		EhandTermBussiModel eterm = null;
		
		try {
			eterm = EhandQueryService.QueryEkjgg50203(1000001,"11016");
			if(eterm != null){
				System.out.println(eterm.toString());
			}
		} catch (LotteryException e) {
			System.out.println("�������:"+e.getType());
			System.out.println("��������:"+e.getMessage());
			e.printStackTrace();
			
		}
	}
	/**
	 * 
	 * Title: test50206<br>
	 * Description: <br>
	 *              <br>���Ի�ȡ�н�����
	 */
	public static void test50206(){
		List<CashResult> cashResultList = null;
		
		try {
			cashResultList = EhandQueryService.QueryCashResult50206(1200001, "11021236");
			if(cashResultList != null){
				for(CashResult one : cashResultList){
					System.out.println("�н����:" + one.toString());
				}
			}
		} catch (LotteryException e) {
			System.out.println("�������:"+e.getType());
			System.out.println("��������:"+e.getMessage());
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 * Title: test50208<br>
	 * Description: <br>
	 *              <br>���ۼ��н����ڲ�ѯ����
	 */
	public static void test50208(){
		EhandTermBussiModel eterm = null;
		
		try {
			eterm = EhandQueryService.QueryEtermSale50208(1200001,"11021236");
			if(eterm != null){
				System.out.println(eterm.toString());
			}
		} catch (LotteryException e) {
			System.out.println("�������:"+e.getType());
			System.out.println("��������:"+e.getMessage());
			e.printStackTrace();
			
		}
		
	}
	/**
	 * 
	 * Title: test50209<br>
	 * Description: <br>
	 *              <br>����״̬�����ѯ
	 */
	public static void test50209(){
		EhandTermBussiModel eterm = null;
		
		try {
			eterm = EhandQueryService.QueryEterm50209(1200001,"11021236");
			if(eterm != null){
				System.out.println(eterm.toString());
			}
		} catch (LotteryException e) {
			System.out.println("�������:"+e.getType());
			System.out.println("��������:"+e.getMessage());
			e.printStackTrace();
			
		}
	}
	/**
	 * 
	 * Title: testSendTicket<br>
	 * Description: <br>
	 *              <br>���Գ�Ʊ
	 */
	public static void testSendTicket(){
		TicketBetResult printResult = null;
		
		try {
			//����id�����ڣ��淨��Ͷע��ʽ��ͶעϵͳƱSequence��Ͷע����������ע����Ͷע���
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
				System.out.println("���:"+printResult);
			}
		} catch (LotteryException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * Title: testQuertTicketReault<br>
	 * Description: <br>
	 *              <br>��ȡһ��Ʊ�ĳ�Ʊ���
	 */
	public static void testQuertTicketReault(){
		TicketQueryResult printResult = null;
		try{
			printResult = EhandTicketService.QueryPrintTicket("CP201102161915110001");
			if(printResult != null){
				System.out.println("��Ʊ���:"+printResult);
			}
		}catch(LotteryException e){
			e.printStackTrace();
		}
	}
	/**
	 * Title: main<br>
	 * Description: <br>
	 *            (������һ�仰�����������������)<br>
	 * @param args
	 */

	public static void main(String[] args) {
		System.out.println("***********begin");
		//���Խ��ڲ�ѯ
		//test50200();
		//���Բ�ѯ��������
		test50203();
		
		//���Ի�ȡ�н�����
		//test50206();
		//�������ۼ��н����
		//test50208();
		
		//����״̬��ѯ
		//test50209();
		
		//���Դ�Ʊ
		//testSendTicket();
		
		//���Բ�ѯƱ�ĳ�Ʊ���
		//testQuertTicketReault();
		
		System.out.println("***********end");
		

	}

}
