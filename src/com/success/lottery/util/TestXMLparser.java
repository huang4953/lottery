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
	 *            12代表12选二
	 * @throws Exception
	 */
	private void readXMLFile(String inFile, String url, String lotteryid, String fileName, String status, int afd) throws Exception{
		PrintWriter pw = new PrintWriter(new File(fileName));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try{
			db = dbf.newDocumentBuilder();
		}catch(Exception pce){
			System.err.println(pce); // 出异常时输出异常信息，然后退出，下同
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
				// 系统开售时间
				Timestamp startTime = null;
				// 系统止售时间
				Timestamp deadLine = formatFromYYYYMMDD(opentime + " 19:30");
				// 合买止售时间
				Timestamp deadLine2 = formatFromYYYYMMDD(opentime + " 19:00");
				// 系统开奖时间
				Timestamp winLine = formatFromYYYYMMDD(opentime + " 22:00");
				// 官方开售时间
				Timestamp startTime2 = null;
				// 官方止售时间
				Timestamp deadLine3 = formatFromYYYYMMDD(opentime + " 20:00");
				// 官方开奖时间
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
				// 系统开售时间
				String startTimeStr = lottery.getStartTime() == null ? "null" : "'" + lottery.getStartTime() + "'";
				// 系统止售时间
				String deadLineStr = lottery.getDeadLine() == null ? "null" : "'" + lottery.getDeadLine() + "'";
				// 合买止售时间
				String deadLine2Str = lottery.getDeadLine2() == null ? "null" : "'" + lottery.getDeadLine2() + "'";
				// 系统开奖时间
				String winLineStr = lottery.getWinLine() == null ? "null" : "'" + lottery.getWinLine() + "'";
				// 官方开售时间
				String startTime2Str = lottery.getStartTime2() == null ? "null" : "'" + lottery.getStartTime2() + "'";
				// 官方止售时间
				String deadLine3Str = lottery.getDeadLine3() == null ? "null" : "'" + lottery.getDeadLine3() + "'";
				// 官方开奖时间
				String winLine2Str = lottery.getWinLine2() == null ? "null" : "'" + lottery.getWinLine2() + "'";
				// 官方兑奖时间
				String changeLineStr = lottery.getChangeLine() == null ? "null" : "'" + lottery.getChangeLine() + "'";
				String missCountStr = lottery.getMissCount() == null ? "null" : "'" + lottery.getMissCount() + "'";
				String limitNumberStr = lottery.getLimitNumber() == null ? "null" : "'" + lottery.getLimitNumber() + "'";
//				if(lottery.getTermNo().equals("10119"))
//					break;
				System.err.println("=======彩种id========"+lottery.getLotteryId());
				 System.err.println("=======当前期数========"+lottery.getTermNo());
				 System.err.println("=======彩期状态========"+lottery.getTermStatus());
				 System.err.println("=======开奖========"+lottery.getWinStatus());
				 System.err.println("=======销售状态========"+lottery.getSaleStatus());
				 System.err.println("=======salesInfo========"+lottery.getSalesInfo());
				 System.err.println("=======该彩种官方截至销售时间========"+lottery.getDeadLine());
				 System.err.println("=======该彩种官方公布的开奖时间========"+lottery.getWinLine());
				 System.err.println("=======该彩种官方公布的最终兑奖截至时间========"+lottery.getChangeLine());
				 System.err.println("=======开奖结果========"+lottery.getLotteryResult());
				 System.err.println("=======开奖结果顺序========"+lottery.getLotteryResultPlus());
				 System.err.println("=======该彩种该期的销售总量========"+lottery.getSalesVolume().replaceAll(",",""));
				 if(lottery.getJackpot()==null)
					 lottery.setJackpot("0");
				 System.err.println("=======该彩种当前期的累计奖池========"+lottery.getJackpot().replaceAll(",",""));
				 
				 System.err.println("=======中奖结果========"+lottery.getWinResult());
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
					jackpot = lottery.getJackpot().replaceAll(",", "").replaceAll("元", "");
				}else{
					jackpot = "0";
				}
				String sale = "";
				if(StringUtils.isNotBlank(lottery.getSalesInfo())){
					sale = "'" + lottery.getSalesInfo() + "'";
				}else{
					sale = "null";
				}
				sql = "insert into `lotteryterm` " + "(lotteryid, termno, termstatus, winstatus, salestatus, salesinfo, " + "starttime, deadline, deadline2, winline, starttime2, deadline3, winline2, changeline, lotteryresult, " + "lotteryresultplus, misscount, limitnumber, salesvolume, jackpot, winresult, nextterm, reserve1) values (" + lottery.getLotteryId() + ", '" + lottery.getTermNo() + "', " + lottery.getTermStatus() + ", " + lottery.getWinStatus() + ", " + lottery.getSaleStatus() + ", " + sale + ", " + startTimeStr + ", " + deadLineStr + ", " + deadLine2Str + ", " + winLineStr + ", " + startTime2Str + ", " + deadLine3Str + ", " + winLine2Str + ", " + changeLineStr + ", " + "'" + lottery.getLotteryResult() + "', '" + lotteryResultPlus + "', " + missCountStr + ", " + limitNumberStr + ", '" + lottery.getSalesVolume().replaceAll(",", "").replaceAll("元", "").replaceAll("&nbsp;", "") + "', '" + jackpot.replaceAll("&nbsp;", "") + "', '" + lottery.getWinResult().replaceAll(" ", "") + "', '" + lottery.getNextTerm() + "', null);";
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
		
		//大乐透和生肖乐的lotteryresultplus字段为"|"，需要导入数据库后update
		String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		TestXMLparser t = new TestXMLparser();
		// 大乐透
//		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/dlt/list.xml", "http://kaijiang.500wan.com/shtml/dlt/", "1000001", "d:\\test\\initsql\\dlt-" + date + ".sql", "", 60);
		// 12选二
//		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/dlt/list.xml", "http://kaijiang.500wan.com/shtml/dlt/", "1000005", "d:\\test\\initsql\\sxl-" + date + ".sql", "", 60);
		// 排列三
//		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/pls/list.xml", "http://kaijiang.500wan.com/shtml/pls/", "1000003", "d:\\test\\initsql\\pls-" + date + ".sql", "", 30);
		// 排列五
//		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/plw/list.xml", "http://kaijiang.500wan.com/shtml/plw/", "1000004", "d:\\test\\initsql\\plw-" + date + ".sql", "", 30);
		// 七星彩
//		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/qxc/list.xml", "http://kaijiang.500wan.com/shtml/qxc/", "1000002", "d:\\test\\initsql\\qxc-" + date + ".sql", "", 60);
		// 胜负彩
//		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/sfc/list.xml", "http://kaijiang.500wan.com/shtml/sfc/", "1300001", "d:\\test\\initsql\\sfc-" + date + ".sql", "", 60);
//		// 任选九
//	    t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/sfc/list.xml", "http://kaijiang.500wan.com/shtml/sfc/", "1300002", "d:\\test\\initsql\\rxj-" + date + ".sql", "", 60);
	    // 六场半全场
//		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/zc6/list.xml", "http://kaijiang.500wan.com/shtml/zc6/", "1300003", "d:\\test\\initsql\\zc6-" + date + ".sql", "", 60);

		//四场进球彩
		t.readXMLFile("http://www.500wan.com/static/info/kaijiang/xml/jq4/list.xml", "http://kaijiang.500wan.com/shtml/jq4/", "1300004","d:\\test\\initsql\\jq4-" + date + ".sql" , "", 60);
		//http://www.500wan.com/static/info/kaijiang/xml/jq4/list.xml        http://www.500wan.com/static/info/kaijiang/shtml/jq4/
		//http://www.500wan.com/static/info/kaijiang/xml/zc6/list.xml        http://www.500wan.com/static/info/kaijiang/shtml/zc6/
		// String ss = "21210446&nbsp;&nbsp;&nbsp;&nbsp;";
		// System.out.println(ss.replaceAll("&nbsp;", ""));
		System.err.println("===================end");
	}
}
