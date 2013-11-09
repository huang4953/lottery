package com.success.lottery.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.success.lottery.term.domain.LotteryTermModel;
public class TestXMLparser{

	/**
	 * 
	 * @param inFile
	 * @param url
	 * @param lotteryid
	 * @param sqlpath
	 * @param status
	 *            12����12ѡ��
	 * @throws Exception
	 */
	private void readXMLFile(String inFile, String url, String lotteryid, String fileName, String status, int afd) throws Exception{
		PrintWriter pw = new PrintWriter(new File(fileName));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try{
			db = dbf.newDocumentBuilder();
		}catch(Exception pce){
			System.err.println(pce); // ���쳣ʱ����쳣��Ϣ��Ȼ���˳�����ͬ
			System.exit(1);
		}
		org.w3c.dom.Document doc = null;
		try{
			doc = db.parse(inFile);
		}catch(DOMException dom){
			System.err.println(dom.getMessage());
			System.exit(1);
		}catch(IOException ioe){
			System.err.println(ioe);
			System.exit(1);
		}
		Element root = doc.getDocumentElement();
		NodeList books = root.getElementsByTagName("row");
		if(books != null){
			String sql = "";
			for(int i = 0; i < books.getLength(); i++){
				System.err.println("===" + i + "======================");
				Node book = books.item(i);
				String expect = book.getAttributes().getNamedItem("expect").getNodeValue();
				String nexttermno = "";
				if(i > 0){
					Node book2 = books.item(i - 1);
					nexttermno = book2.getAttributes().getNamedItem("expect").getNodeValue();
				}else{
					nexttermno = "" + (new Integer(expect) + 1);
				}
				String opencode = book.getAttributes().getNamedItem("opencode").getNodeValue();
				System.err.println(opencode);
				String opentime = book.getAttributes().getNamedItem("opentime").getNodeValue().substring(0, 10);
				// ϵͳ����ʱ��
				Timestamp startTime = null;
				// ϵͳֹ��ʱ��
				Timestamp deadLine = formatFromYYYYMMDD(opentime + " 19:30");
				// ����ֹ��ʱ��
				Timestamp deadLine2 = formatFromYYYYMMDD(opentime + " 19:00");
				// ϵͳ����ʱ��
				Timestamp winLine = formatFromYYYYMMDD(opentime + " 22:00");
				// �ٷ�����ʱ��
				Timestamp startTime2 = null;
				// �ٷ�ֹ��ʱ��
				Timestamp deadLine3 = formatFromYYYYMMDD(opentime + " 20:00");
				// �ٷ�����ʱ��
				Timestamp winLine2 = formatFromYYYYMMDD(opentime + " 22:00");
				TestHTMLParser t = new TestHTMLParser();
				LotteryTermModel lottery = t.htmlCreate(url + expect + ".shtml", lotteryid, expect, winLine, deadLine, opencode, nexttermno, status, afd);
				if(lotteryid.equals("1300001") || lotteryid.equals("1300002")){
					lottery.setStartTime(null);
					lottery.setDeadLine(null);
					lottery.setDeadLine2(null);
					lottery.setWinLine(formatFromYYYYMMDD(opentime + " 10:00"));
					lottery.setStartTime2(null);
					lottery.setDeadLine3(null);
					lottery.setWinLine2(formatFromYYYYMMDD(opentime + " 10:00"));
				}else{
					lottery.setStartTime(startTime);
					lottery.setDeadLine(deadLine);
					lottery.setDeadLine2(deadLine2);
					lottery.setWinLine(winLine);
					lottery.setStartTime2(startTime2);
					lottery.setDeadLine3(deadLine3);
					lottery.setWinLine2(winLine2);
				}
				// ϵͳ����ʱ��
				String startTimeStr = lottery.getStartTime() == null ? "null" : "'" + lottery.getStartTime() + "'";
				// ϵͳֹ��ʱ��
				String deadLineStr = lottery.getDeadLine() == null ? "null" : "'" + lottery.getDeadLine() + "'";
				// ����ֹ��ʱ��
				String deadLine2Str = lottery.getDeadLine2() == null ? "null" : "'" + lottery.getDeadLine2() + "'";
				// ϵͳ����ʱ��
				String winLineStr = lottery.getWinLine() == null ? "null" : "'" + lottery.getWinLine() + "'";
				// �ٷ�����ʱ��
				String startTime2Str = lottery.getStartTime2() == null ? "null" : "'" + lottery.getStartTime2() + "'";
				// �ٷ�ֹ��ʱ��
				String deadLine3Str = lottery.getDeadLine3() == null ? "null" : "'" + lottery.getDeadLine3() + "'";
				// �ٷ�����ʱ��
				String winLine2Str = lottery.getWinLine2() == null ? "null" : "'" + lottery.getWinLine2() + "'";
				// �ٷ��ҽ�ʱ��
				String changeLineStr = lottery.getChangeLine() == null ? "null" : "'" + lottery.getChangeLine() + "'";
				String missCountStr = lottery.getMissCount() == null ? "null" : "'" + lottery.getMissCount() + "'";
				String limitNumberStr = lottery.getLimitNumber() == null ? "null" : "'" + lottery.getLimitNumber() + "'";
//				if(lottery.getTermNo().equals("10119"))
//					break;
				System.err.println("=======����id========"+lottery.getLotteryId());
				 System.err.println("=======��ǰ����========"+lottery.getTermNo());
				 System.err.println("=======����״̬========"+lottery.getTermStatus());
				 System.err.println("=======����========"+lottery.getWinStatus());
				 System.err.println("=======����״̬========"+lottery.getSaleStatus());
				 System.err.println("=======salesInfo========"+lottery.getSalesInfo());
				 System.err.println("=======�ò��ֹٷ���������ʱ��========"+lottery.getDeadLine());
				 System.err.println("=======�ò��ֹٷ������Ŀ���ʱ��========"+lottery.getWinLine());
				 System.err.println("=======�ò��ֹٷ����������նҽ�����ʱ��========"+lottery.getChangeLine());
				 System.err.println("=======�������========"+lottery.getLotteryResult());
				 System.err.println("=======�������˳��========"+lottery.getLotteryResultPlus());
				 System.err.println("=======�ò��ָ��ڵ���������========"+lottery.getSalesVolume().replaceAll(",",""));
				 if(lottery.getJackpot()==null)
					 lottery.setJackpot("0");
				 System.err.println("=======�ò��ֵ�ǰ�ڵ��ۼƽ���========"+lottery.getJackpot().replaceAll(",",""));
				 
				 System.err.println("=======�н����========"+lottery.getWinResult());
				// sql+=lottery.getLotteryId();
				// sql+=lottery.getTermNo();
				// sql+=lottery.getTermStatus();
				// sql+=lottery.getWinStatus();
				// sql+=lottery.getSaleStatus();
				// sql+=lottery.getSalesInfo();
				// sql+=lottery.getDeadLine();
				// sql+=lottery.getWinLine();
				// sql+=lottery.getChangeLine();
				// sql+=lottery.getLotteryResult();
				// sql+=lottery.getLotteryResultPlus();
				// sql+=lottery.getSalesVolume();
				// sql+=lottery.getJackpot();
				// sql+=lottery.getWinResult();
				String lotteryResultPlus = "";
				if(StringUtils.isNotBlank(lottery.getLotteryResultPlus())){
					if(lottery.getLotteryResultPlus().equals("|")){
						lotteryResultPlus = "";
					}else{
						lotteryResultPlus = lottery.getLotteryResultPlus().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
					}
				}
				String jackpot = "";
				if(StringUtils.isNotBlank(lottery.getJackpot()) && StringUtils.isNotBlank(lottery.getJackpot().replaceAll(",", ""))){
					jackpot = lottery.getJackpot().replaceAll(",", "").replaceAll("Ԫ", "");
				}else{
					jackpot = "0";
				}
				String sale = "";
				if(StringUtils.isNotBlank(lottery.getSalesInfo())){
					sale = "'" + lottery.getSalesInfo() + "'";
				}else{
					sale = "null";
				}
				sql = "insert into `lotteryterm` " + "(lotteryid, termno, termstatus, winstatus, salestatus, salesinfo, " + "starttime, deadline, deadline2, winline, starttime2, deadline3, winline2, changeline, lotteryresult, " + "lotteryresultplus, misscount, limitnumber, salesvolume, jackpot, winresult, nextterm, reserve1) values (" + lottery.getLotteryId() + ", '" + lottery.getTermNo() + "', " + lottery.getTermStatus() + ", " + lottery.getWinStatus() + ", " + lottery.getSaleStatus() + ", " + sale + ", " + startTimeStr + ", " + deadLineStr + ", " + deadLine2Str + ", " + winLineStr + ", " + startTime2Str + ", " + deadLine3Str + ", " + winLine2Str + ", " + changeLineStr + ", " + "'" + lottery.getLotteryResult() + "', '" + lotteryResultPlus + "', " + missCountStr + ", " + limitNumberStr + ", '" + lottery.getSalesVolume().replaceAll(",", "").replaceAll("Ԫ", "").replaceAll("&nbsp;", "") + "', '" + jackpot.replaceAll("&nbsp;", "") + "', '" + lottery.getWinResult().replaceAll(" ", "") + "', '" + lottery.getNextTerm() + "', null);";
				// System.out.println(sql);
				
				pw.println(sql);
			}
		}
		pw.close();
	}

	public static Timestamp formatFromYYYYMMDD(String value){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try{
			date = sdf.parse(value);
		}catch(ParseException e){
			return null;
		}
		return new Timestamp(date.getTime());
	}

	public static void main(String[] args) throws Exception{
		
		//����͸����Ф�ֵ�lotteryresultplus�ֶ�Ϊ"|"����Ҫ�������ݿ��update
		String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		TestXMLparser t = new TestXMLparser();
		// ����͸
//		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/dlt/list.xml", "http://kaijiang.500wan.com/shtml/dlt/", "1000001", "d:\\test\\initsql\\dlt-" + date + ".sql", "", 60);
		// 12ѡ��
//		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/dlt/list.xml", "http://kaijiang.500wan.com/shtml/dlt/", "1000005", "d:\\test\\initsql\\sxl-" + date + ".sql", "", 60);
		// ������
//		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/pls/list.xml", "http://kaijiang.500wan.com/shtml/pls/", "1000003", "d:\\test\\initsql\\pls-" + date + ".sql", "", 30);
		// ������
//		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/plw/list.xml", "http://kaijiang.500wan.com/shtml/plw/", "1000004", "d:\\test\\initsql\\plw-" + date + ".sql", "", 30);
		// ���ǲ�
//		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/qxc/list.xml", "http://kaijiang.500wan.com/shtml/qxc/", "1000002", "d:\\test\\initsql\\qxc-" + date + ".sql", "", 60);
		// ʤ����
//		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/sfc/list.xml", "http://kaijiang.500wan.com/shtml/sfc/", "1300001", "d:\\test\\initsql\\sfc-" + date + ".sql", "", 60);
//		// ��ѡ��
//	    t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/sfc/list.xml", "http://kaijiang.500wan.com/shtml/sfc/", "1300002", "d:\\test\\initsql\\rxj-" + date + ".sql", "", 60);
	    // ������ȫ��
//		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/zc6/list.xml", "http://kaijiang.500wan.com/shtml/zc6/", "1300003", "d:\\test\\initsql\\zc6-" + date + ".sql", "", 60);

		//�ĳ������
		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/jq4/list.xml", "http://kaijiang.500wan.com/shtml/jq4/", "1300004","d:\\test\\initsql\\jq4-" + date + ".sql" , "", 60);
		//http://www.500wan.com/static/info/kaijiang/xml/jq4/list.xml        http://www.500wan.com/static/info/kaijiang/shtml/jq4/
		//http://www.500wan.com/static/info/kaijiang/xml/zc6/list.xml        http://www.500wan.com/static/info/kaijiang/shtml/zc6/
		// String ss = "21210446&nbsp;&nbsp;&nbsp;&nbsp;";
		// System.out.println(ss.replaceAll("&nbsp;", ""));
		System.err.println("===================end");
	}
}
