package com.success.lottery.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import com.success.lottery.business.service.impl.LotteryManagerService;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.utils.ApplicationContextUtils;
public class SqlCreate{

	public static Date afterNDays(Timestamp time, int n){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time.getTime());
		c.add(Calendar.DATE, n);
		Date d2 = c.getTime();
		return d2;
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

	public void createSfc(String nowTearm, int lotteryId, String fileName) throws FileNotFoundException{
		String sql = "";
		int term = Integer.valueOf(nowTearm);
		PrintWriter pw = new PrintWriter(new File(fileName));
		for(int i = term + 1; i <= 10110; i++){
			sql = "insert into `lotteryterm` (lotteryid, termno, termstatus, winstatus, salestatus, nextterm) values (" + lotteryId + ", '" + i + "', 0, 1, 4, '" + (i + 1) + "');";
			// System.out.println(sql);
			pw.println(sql);
		}
		pw.close();
	}

	public void createPls(String nowTerm, String date, int lotteryid, String fileName) throws ParseException, FileNotFoundException{
		String sql = "";
		PrintWriter pw = new PrintWriter(new File(fileName));
		int term = Integer.valueOf(nowTerm);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		term = term + 1;
		int nextTerm = term + 1;
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		while(calendar.get(Calendar.YEAR) == 2010){
			Calendar deadLine = Calendar.getInstance();
			Calendar deadLine2 = Calendar.getInstance();
			Calendar deadLine3 = Calendar.getInstance();
			Calendar winLine = Calendar.getInstance();
			Calendar changeLine = Calendar.getInstance();
			deadLine.setTimeInMillis(calendar.getTimeInMillis());
			deadLine.set(Calendar.HOUR_OF_DAY, 19);
			deadLine.set(Calendar.MINUTE, 30);
			deadLine.set(Calendar.SECOND, 0);
			deadLine2.setTimeInMillis(calendar.getTimeInMillis());
			deadLine2.set(Calendar.HOUR_OF_DAY, 19);
			deadLine2.set(Calendar.MINUTE, 0);
			deadLine2.set(Calendar.SECOND, 0);
			deadLine3.setTimeInMillis(calendar.getTimeInMillis());
			deadLine3.set(Calendar.HOUR_OF_DAY, 20);
			deadLine3.set(Calendar.MINUTE, 0);
			deadLine3.set(Calendar.SECOND, 0);
			winLine.setTimeInMillis(calendar.getTimeInMillis());
			winLine.set(Calendar.HOUR_OF_DAY, 22);
			winLine.set(Calendar.MINUTE, 0);
			winLine.set(Calendar.SECOND, 0);
			changeLine.setTimeInMillis(winLine.getTimeInMillis());
			changeLine.add(Calendar.DAY_OF_YEAR, 59);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sql = "insert into `lotteryterm` (lotteryid, termno, termstatus, winstatus, salestatus, deadline, deadline2, winline, deadline3, " + "winline2, changeline, nextterm) values (" + lotteryid + ", '" + term++ + "', 0, 1, 4, '" + sdf.format(deadLine.getTime()) + "', '" + sdf.format(deadLine2.getTime()) + "', '" + sdf.format(winLine.getTime()) + "', '" + sdf.format(deadLine3.getTime()) + "', '" + sdf.format(winLine.getTime()) + "', '" + sdf.format(changeLine.getTime()) + "', '" + nextTerm++ + "');";
			pw.println(sql);
			// System.out.println(sql);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}
		pw.close();
	}

	public void createPlw(String nowTerm, String date, int lotteryid, String fileName) throws ParseException, FileNotFoundException{
		createPls(nowTerm, date, 1000004, fileName);
	}
	//初始化2011年超级大乐透数据  生肖乐
	/**
	 * @param fileName 新建SQL文件保存路径名称
	 * @throws ParseException FileNotFoundException
	 */
    public void createDlt2011(int lotteryid,String fileName) throws ParseException, FileNotFoundException{
    	PrintWriter pw = new PrintWriter(new File(fileName));
		String sql = "";
	    //	PrintWriter pw = new PrintWriter(new File(fileName));
		int term = Integer.valueOf(11000);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-01"));//停止销售日
		calendar.add(Calendar.HOUR_OF_DAY, 19);
		calendar.add(Calendar.MINUTE, 30);
		term = term + 1;
		int nextTerm = term + 1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// calendar.add(Calendar.DAY_OF_YEAR, 1);
		while(calendar.get(Calendar.YEAR)==2011){
			Calendar startTime= Calendar.getInstance();//系统开售时间  1
			startTime.setTimeInMillis(calendar.getTimeInMillis());
			startTime.set(Calendar.HOUR_OF_DAY,19);
			startTime.set(Calendar.MINUTE, 30);
			Calendar startTime2= Calendar.getInstance();//datetime comment '官方开售时间',  2
			startTime2.setTimeInMillis(startTime.getTimeInMillis());
			startTime2.set(Calendar.HOUR_OF_DAY, 20);
			startTime2.set(Calendar.MINUTE, 0);
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
				calendar.add(Calendar.DAY_OF_YEAR, 2);
			}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
				calendar.add(Calendar.DAY_OF_YEAR, 3);
			}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
				calendar.add(Calendar.DAY_OF_YEAR, 2);
			}
			//开始出售的时间
			
			Calendar deadLine = Calendar.getInstance();//datetime comment '系统止售时间', X
			Calendar deadLine2 = Calendar.getInstance();//datetime comment '合买止售时间', X
			Calendar deadLine3 = Calendar.getInstance();//datetime comment '官方止售时间',X
			Calendar winLine = Calendar.getInstance();//datetime comment '系统开奖时间', X
			Calendar changeLine = Calendar.getInstance();//datetime comment '该彩种官方公布的最终兑奖截至时间',
			deadLine.setTimeInMillis(calendar.getTimeInMillis());
			deadLine.set(Calendar.HOUR_OF_DAY, 19);
			deadLine.set(Calendar.MINUTE, 30);
			deadLine.set(Calendar.SECOND, 0);
			deadLine2.setTimeInMillis(calendar.getTimeInMillis());
			deadLine2.set(Calendar.HOUR_OF_DAY, 19);
			deadLine2.set(Calendar.MINUTE, 0);
			deadLine2.set(Calendar.SECOND, 0);
			deadLine3.setTimeInMillis(calendar.getTimeInMillis());
			deadLine3.set(Calendar.HOUR_OF_DAY, 20);
			deadLine3.set(Calendar.MINUTE, 0);
			deadLine3.set(Calendar.SECOND, 0);
			winLine.setTimeInMillis(calendar.getTimeInMillis());
			winLine.set(Calendar.HOUR_OF_DAY, 22);
			winLine.set(Calendar.MINUTE, 0);
			winLine.set(Calendar.SECOND, 0);
			changeLine.setTimeInMillis(winLine.getTimeInMillis());
			changeLine.add(Calendar.DAY_OF_YEAR, 59);
			//排除春节从除夕到正月初六是不开奖的既2011-02-02到2011-02-08不算彩旗
			Calendar sdate=Calendar.getInstance();
			sdate.set(Calendar.YEAR,2011);
			sdate.set(Calendar.MONTH, 1);
			sdate.set(Calendar.DATE, 2);
			sdate.set(Calendar.HOUR_OF_DAY, 0);
			sdate.set(Calendar.MINUTE, 0);
			sdate.set(Calendar.SECOND, 0);
			
			Calendar edate=Calendar.getInstance();
			edate.set(Calendar.YEAR,2011);
			edate.set(Calendar.MONTH, 1);
			edate.set(Calendar.DATE, 9);
			edate.set(Calendar.HOUR_OF_DAY, 0);
			edate.set(Calendar.MINUTE, 0);
			edate.set(Calendar.SECOND, 0);
			
			if(winLine.after(sdate)&&winLine.before(edate))
				continue;
			// System.out.println(sdf.format(deadLine.getTime()) + "     " +
			// sdf.format(deadLine2.getTime()) + "     " +
			// sdf.format(deadLine3.getTime()) + "     " +
			// sdf.format(winLine.getTime()));
			sql = "insert into `lotteryterm` (lotteryid, termno, termstatus, winstatus, salestatus, deadline, deadline2, winline, deadline3, " + "winline2, changeline,nextterm,starttime,starttime2) values (" + lotteryid + ", '" + term++ + "', 0, 1, 4, '" + sdf.format(deadLine.getTime()) + "', '" + sdf.format(deadLine2.getTime()) + "', '" + sdf.format(winLine.getTime()) + "', '" + sdf.format(deadLine3.getTime()) + "', '" + sdf.format(winLine.getTime()) + "', '" + sdf.format(changeLine.getTime()) + "', '" + nextTerm++ + "','" + sdf.format(startTime.getTime()) + "','" + sdf.format(startTime2.getTime()) + "');";
			pw.println(sql);
		}
		pw.close();
    }
    /**
     * 排列三 排列五 2011彩期数据初始化
     * @throws ParseException 
     * @throws FileNotFoundException 
     */
    public void createPls2011(int lotteryid,String fileName) throws ParseException, FileNotFoundException{
    	String sql = "";
    	PrintWriter pw = new PrintWriter(new File(fileName));
		int term = Integer.valueOf(11000);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-01"));
		term = term + 1;
		int nextTerm = term + 1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		while(calendar.get(Calendar.YEAR) == 2011){
			Calendar startTime= Calendar.getInstance();//系统开售时间  1
			startTime.setTimeInMillis(calendar.getTimeInMillis());
			startTime.set(Calendar.HOUR_OF_DAY,19);
			startTime.set(Calendar.MINUTE, 30);
			Calendar startTime2= Calendar.getInstance();//datetime comment '官方开售时间',  2
			startTime2.setTimeInMillis(startTime.getTimeInMillis());
			startTime2.set(Calendar.HOUR_OF_DAY, 20);
			startTime2.set(Calendar.MINUTE, 0);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Calendar deadLine = Calendar.getInstance();
			Calendar deadLine2 = Calendar.getInstance();
			Calendar deadLine3 = Calendar.getInstance();
			Calendar winLine = Calendar.getInstance();
			Calendar changeLine = Calendar.getInstance();
			deadLine.setTimeInMillis(calendar.getTimeInMillis());
			deadLine.set(Calendar.HOUR_OF_DAY, 19);
			deadLine.set(Calendar.MINUTE, 30);
			deadLine.set(Calendar.SECOND, 0);
			deadLine2.setTimeInMillis(calendar.getTimeInMillis());
			deadLine2.set(Calendar.HOUR_OF_DAY, 19);
			deadLine2.set(Calendar.MINUTE, 0);
			deadLine2.set(Calendar.SECOND, 0);
			deadLine3.setTimeInMillis(calendar.getTimeInMillis());
			deadLine3.set(Calendar.HOUR_OF_DAY, 20);
			deadLine3.set(Calendar.MINUTE, 0);
			deadLine3.set(Calendar.SECOND, 0);
			winLine.setTimeInMillis(calendar.getTimeInMillis());
			winLine.set(Calendar.HOUR_OF_DAY, 22);
			winLine.set(Calendar.MINUTE, 0);
			winLine.set(Calendar.SECOND, 0);
			changeLine.setTimeInMillis(winLine.getTimeInMillis());
			changeLine.add(Calendar.DAY_OF_YEAR, 59);
			
			//排除春节从除夕到正月初六是不开奖的既2011-02-02到2011-02-08不算彩旗
			Calendar sdate=Calendar.getInstance();
			sdate.set(Calendar.YEAR,2011);
			sdate.set(Calendar.MONTH, 1);
			sdate.set(Calendar.DATE, 3);
			sdate.set(Calendar.HOUR_OF_DAY, 0);
			sdate.set(Calendar.MINUTE, 0);
			sdate.set(Calendar.SECOND, 0);
			
			Calendar edate=Calendar.getInstance();
			edate.set(Calendar.YEAR,2011);
			edate.set(Calendar.MONTH, 1);
			edate.set(Calendar.DATE, 10);
			edate.set(Calendar.HOUR_OF_DAY, 0);
			edate.set(Calendar.MINUTE, 0);
			edate.set(Calendar.SECOND, 0);
			
			sql = "insert into `lotteryterm` (lotteryid, termno, termstatus, winstatus, salestatus, deadline, deadline2, winline, deadline3, " + "winline2, changeline, nextterm,starttime,starttime2) values ("+lotteryid+", '" + term++ + "', 0, 1, 4, '" + sdf.format(deadLine.getTime()) + "', '" + sdf.format(deadLine2.getTime()) + "', '" + sdf.format(winLine.getTime()) + "', '" + sdf.format(deadLine3.getTime()) + "', '" + sdf.format(winLine.getTime()) + "', '" + sdf.format(changeLine.getTime()) + "', '" + nextTerm++ + "','" + sdf.format(startTime.getTime()) + "','" + sdf.format(startTime2.getTime()) + "');";
			pw.println(sql);
		//	System.out.println(sql);
			
		}
		pw.close();
    }
	public void createDlt(String nowTerm, String date, int lotteryid, String fileName) throws ParseException, FileNotFoundException{
		String sql = "";
		PrintWriter pw = new PrintWriter(new File(fileName));
		int term = Integer.valueOf(nowTerm);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		term = term + 1;
		int nextTerm = term + 1;
		// calendar.add(Calendar.DAY_OF_YEAR, 1);
		while(calendar.get(Calendar.YEAR) == 2010){
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
				calendar.add(Calendar.DAY_OF_YEAR, 2);
			}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
				calendar.add(Calendar.DAY_OF_YEAR, 3);
			}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
				calendar.add(Calendar.DAY_OF_YEAR, 2);
			}
			Calendar deadLine = Calendar.getInstance();
			Calendar deadLine2 = Calendar.getInstance();
			Calendar deadLine3 = Calendar.getInstance();
			Calendar winLine = Calendar.getInstance();
			Calendar changeLine = Calendar.getInstance();
			deadLine.setTimeInMillis(calendar.getTimeInMillis());
			deadLine.set(Calendar.HOUR_OF_DAY, 19);
			deadLine.set(Calendar.MINUTE, 30);
			deadLine.set(Calendar.SECOND, 0);
			deadLine2.setTimeInMillis(calendar.getTimeInMillis());
			deadLine2.set(Calendar.HOUR_OF_DAY, 19);
			deadLine2.set(Calendar.MINUTE, 0);
			deadLine2.set(Calendar.SECOND, 0);
			deadLine3.setTimeInMillis(calendar.getTimeInMillis());
			deadLine3.set(Calendar.HOUR_OF_DAY, 20);
			deadLine3.set(Calendar.MINUTE, 0);
			deadLine3.set(Calendar.SECOND, 0);
			winLine.setTimeInMillis(calendar.getTimeInMillis());
			winLine.set(Calendar.HOUR_OF_DAY, 22);
			winLine.set(Calendar.MINUTE, 0);
			winLine.set(Calendar.SECOND, 0);
			changeLine.setTimeInMillis(winLine.getTimeInMillis());
			changeLine.add(Calendar.DAY_OF_YEAR, 59);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// System.out.println(sdf.format(deadLine.getTime()) + "     " +
			// sdf.format(deadLine2.getTime()) + "     " +
			// sdf.format(deadLine3.getTime()) + "     " +
			// sdf.format(winLine.getTime()));
			sql = "insert into `lotteryterm` (lotteryid, termno, termstatus, winstatus, salestatus, deadline, deadline2, winline, deadline3, " + "winline2, changeline, nextterm) values (" + lotteryid + ", '" + term++ + "', 0, 1, 4, '" + sdf.format(deadLine.getTime()) + "', '" + sdf.format(deadLine2.getTime()) + "', '" + sdf.format(winLine.getTime()) + "', '" + sdf.format(deadLine3.getTime()) + "', '" + sdf.format(winLine.getTime()) + "', '" + sdf.format(changeLine.getTime()) + "', '" + nextTerm++ + "');";
			pw.println(sql);
			// System.out.println(sql);
		}
		pw.close();
	}
	/**
	 * @param fileName
	 */
	public void createQxc2011(String fileName) throws ParseException, FileNotFoundException{
		String sql = "";
		PrintWriter pw = new PrintWriter(new File(fileName));
		int term = Integer.valueOf(11000);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-02"));
		term = term + 1;
		int nextTerm = term + 1;
		while(calendar.get(Calendar.YEAR) == 2011){
			Calendar startTime= Calendar.getInstance();//系统开售时间  1
			startTime.setTimeInMillis(calendar.getTimeInMillis());
			startTime.set(Calendar.HOUR_OF_DAY, 19);
			startTime.set(Calendar.MINUTE,30);
			Calendar startTime2= Calendar.getInstance();//datetime comment '官方开售时间',  2
			startTime2.setTimeInMillis(startTime.getTimeInMillis());
			startTime2.set(Calendar.HOUR_OF_DAY, 20);
			startTime2.set(Calendar.MINUTE, 0);
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY){
				calendar.add(Calendar.DAY_OF_YEAR, 3);
			}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
				calendar.add(Calendar.DAY_OF_YEAR, 2);
			}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				calendar.add(Calendar.DAY_OF_YEAR, 2);
			}
			Calendar deadLine = Calendar.getInstance();
			Calendar deadLine2 = Calendar.getInstance();
			Calendar deadLine3 = Calendar.getInstance();
			Calendar winLine = Calendar.getInstance();
			Calendar changeLine = Calendar.getInstance();
			deadLine.setTimeInMillis(calendar.getTimeInMillis());
			deadLine.set(Calendar.HOUR_OF_DAY, 19);
			deadLine.set(Calendar.MINUTE, 30);
			deadLine.set(Calendar.SECOND, 0);
			deadLine2.setTimeInMillis(calendar.getTimeInMillis());
			deadLine2.set(Calendar.HOUR_OF_DAY, 19);
			deadLine2.set(Calendar.MINUTE, 0);
			deadLine2.set(Calendar.SECOND, 0);
			deadLine3.setTimeInMillis(calendar.getTimeInMillis());
			deadLine3.set(Calendar.HOUR_OF_DAY, 20);
			deadLine3.set(Calendar.MINUTE, 0);
			deadLine3.set(Calendar.SECOND, 0);
			winLine.setTimeInMillis(calendar.getTimeInMillis());
			winLine.set(Calendar.HOUR_OF_DAY, 22);
			winLine.set(Calendar.MINUTE, 0);
			winLine.set(Calendar.SECOND, 0);
			changeLine.setTimeInMillis(winLine.getTimeInMillis());
			changeLine.add(Calendar.DAY_OF_YEAR, 59);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//排除春节从除夕到正月初六是不开奖的既2011-02-02到2011-02-08不算彩旗
			Calendar sdate=Calendar.getInstance();
			sdate.set(Calendar.YEAR,2011);
			sdate.set(Calendar.MONTH, 1);
			sdate.set(Calendar.DATE, 3);
			sdate.set(Calendar.HOUR_OF_DAY, 0);
			sdate.set(Calendar.MINUTE, 0);
			sdate.set(Calendar.SECOND, 0);
			
			Calendar edate=Calendar.getInstance();
			edate.set(Calendar.YEAR,2011);
			edate.set(Calendar.MONTH, 1);
			edate.set(Calendar.DATE, 10);
			edate.set(Calendar.HOUR_OF_DAY, 0);
			edate.set(Calendar.MINUTE, 0);
			edate.set(Calendar.SECOND, 0);
			
			if(winLine.after(sdate)&&winLine.before(edate))
				continue;
			
			sql = "insert into `lotteryterm` (lotteryid, termno, termstatus, winstatus, salestatus, deadline, deadline2, winline, deadline3, " + "winline2, changeline, nextterm,starttime,starttime2) values (1000002, '" + term++ + "', 0, 1, 4, '" + sdf.format(deadLine.getTime()) + "', '" + sdf.format(deadLine2.getTime()) + "', '" + sdf.format(winLine.getTime()) + "', '" + sdf.format(deadLine3.getTime()) + "', '" + sdf.format(winLine.getTime()) + "', '" + sdf.format(changeLine.getTime()) + "', '" + nextTerm++ + "','" + sdf.format(startTime.getTime()) + "','" + sdf.format(startTime2.getTime()) + "');";
			pw.println(sql);
		}
		pw.close();
	}
	public void createSxl(String nowTerm, String date, int lotteryid, String fileName) throws ParseException, FileNotFoundException{
		createDlt(nowTerm, date, 1000005, fileName);
	}

	public void createQxc(String nowTerm, String date, int lotteryid, String fileName) throws ParseException, FileNotFoundException{
		String sql = "";
		PrintWriter pw = new PrintWriter(new File(fileName));
		int term = Integer.valueOf(nowTerm);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		term = term + 1;
		int nextTerm = term + 1;
		// calendar.add(Calendar.DAY_OF_YEAR, 1);
		while(calendar.get(Calendar.YEAR) == 2010){
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY){
				calendar.add(Calendar.DAY_OF_YEAR, 3);
			}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
				calendar.add(Calendar.DAY_OF_YEAR, 2);
			}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				calendar.add(Calendar.DAY_OF_YEAR, 2);
			}
			Calendar deadLine = Calendar.getInstance();
			Calendar deadLine2 = Calendar.getInstance();
			Calendar deadLine3 = Calendar.getInstance();
			Calendar winLine = Calendar.getInstance();
			Calendar changeLine = Calendar.getInstance();
			deadLine.setTimeInMillis(calendar.getTimeInMillis());
			deadLine.set(Calendar.HOUR_OF_DAY, 19);
			deadLine.set(Calendar.MINUTE, 30);
			deadLine.set(Calendar.SECOND, 0);
			deadLine2.setTimeInMillis(calendar.getTimeInMillis());
			deadLine2.set(Calendar.HOUR_OF_DAY, 19);
			deadLine2.set(Calendar.MINUTE, 0);
			deadLine2.set(Calendar.SECOND, 0);
			deadLine3.setTimeInMillis(calendar.getTimeInMillis());
			deadLine3.set(Calendar.HOUR_OF_DAY, 20);
			deadLine3.set(Calendar.MINUTE, 0);
			deadLine3.set(Calendar.SECOND, 0);
			winLine.setTimeInMillis(calendar.getTimeInMillis());
			winLine.set(Calendar.HOUR_OF_DAY, 22);
			winLine.set(Calendar.MINUTE, 0);
			winLine.set(Calendar.SECOND, 0);
			changeLine.setTimeInMillis(winLine.getTimeInMillis());
			changeLine.add(Calendar.DAY_OF_YEAR, 59);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// System.out.println(sdf.format(deadLine.getTime()) + "     " +
			// sdf.format(deadLine2.getTime()) + "     " +
			// sdf.format(deadLine3.getTime()) + "     " +
			// sdf.format(winLine.getTime()));
			sql = "insert into `lotteryterm` (lotteryid, termno, termstatus, winstatus, salestatus, deadline, deadline2, winline, deadline3, " + "winline2, changeline, nextterm) values (" + lotteryid + ", '" + term++ + "', 0, 1, 4, '" + sdf.format(deadLine.getTime()) + "', '" + sdf.format(deadLine2.getTime()) + "', '" + sdf.format(winLine.getTime()) + "', '" + sdf.format(deadLine3.getTime()) + "', '" + sdf.format(winLine.getTime()) + "', '" + sdf.format(changeLine.getTime()) + "', '" + nextTerm++ + "');";
			pw.println(sql);
			// System.out.println(sql);
		}
		pw.close();
	}
	//胜负与任九2011年200期
	public void createZC(int lotteryid,String fileName) throws FileNotFoundException{
		String sql = "";
		int term = Integer.valueOf(11002);
		PrintWriter pw = new PrintWriter(new File(fileName));
		int nextTerm = term + 1;
		for(int i=0;i<=199;i++)
		{
			
			sql = "insert into `lotteryterm` (lotteryid, termno, termstatus, winstatus, salestatus,  nextterm) values (" + lotteryid + ", '" + term++ + "', 0, 1, 4,  '" + nextTerm++ + "');";
			pw.println(sql);
		}
		pw.close();
			// System.out.println(sql);
	}
	
    //初始化遗漏
	public void initMissCount(int lotteryId) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://172.16.0.96:3306/tlt", "tlt2010", "lottery");
		Statement stmt = con.createStatement();
		while(true){
			ResultSet rs = stmt.executeQuery("select termno, lotteryresult from lotteryterm where lotteryid=" + lotteryId + " and misscount is null and lotteryresult is not null order by termno limit 1");
			if(rs.next()){
				String term = rs.getString("termno");
				String lotteryResult = rs.getString("lotteryresult");
				System.out.println("term = " + term);
				System.out.println("lotteryresult = " + lotteryResult);
				rs = stmt.executeQuery("select misscount from lotteryterm where lotteryid=" + lotteryId + " and nextterm='" + term + "'");
				String missCount = null;
				if(rs.next()){
					missCount = rs.getString("misscount");
				}
				System.out.println("lastMissCount=" + missCount);
				missCount = LotteryTools.getMissCount(lotteryId, lotteryResult, missCount);
				System.out.println("newMissCount=" + missCount);
				missCount = ((missCount == null || "".equals(missCount.trim())) ? "null" : "'" + missCount.trim() + "'");
				String sql = "update lotteryterm set misscount=" + missCount + " where lotteryid=" + lotteryId + " and termno='" + term + "'";
				System.out.println(sql);
				stmt.executeUpdate(sql);
			}else{
				System.out.println("not found");
				break;
			}
		}
		stmt.close();
		con.close();
	}
	
	//修改数字彩的开售时间
	public void inintLotteryStartTime(int lotteryId,String termno)throws ClassNotFoundException, SQLException{
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://172.16.0.96:3306/tlt", "tlt2010", "lottery");
		Statement stmt = con.createStatement();
		while(true){
			ResultSet rs = stmt.executeQuery("select termno,nextterm,deadline,winline  from lotteryterm where lotteryid=" + lotteryId + " and  termno ='"+termno+"' ");
			if(rs.next()){
				String term = rs.getString("termno");
				termno=rs.getString("nextterm");
				String deadtime = rs.getString("deadline");
				String winline =rs.getString("winline");
			    String sql = "update lotteryterm set starttime='" + deadtime + "' , starttime2='"+winline+"' where lotteryid=" + lotteryId + " and termno='" + termno + "'";
				int count=stmt.executeUpdate(sql);
				
			}else{
				System.out.println("not found");
				break;
			}
		}
		stmt.close();
		
	}
	//修改进球四的结果集
	public void updateWinSales(String fileName) throws ClassNotFoundException, SQLException, FileNotFoundException{
		PrintWriter pw = new PrintWriter(new File(fileName));
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://172.16.0.96:3306/tlt", "tlt2010", "lottery");
		Statement stmt = con.createStatement();
		int termno=10001;
			while(true){
				ResultSet rs = stmt.executeQuery("select salesinfo  from lotteryterm where lotteryid=" + 1300004 + " and  termno='"+termno+"' ");
				if(rs.next()){
				    String salesinfo=rs.getString("salesinfo");
				    String[] arr=salesinfo.split("\\|");
				    String usales="";
				    for (int i=0;i<arr.length;i++) {
						if(i==0)
							usales+=arr[i];
						if(i==2)
							usales+="|"+arr[i].replaceFirst("3", "2");
						if(i==4)
							usales+="|"+arr[i].replaceFirst("5", "3");
						if(i==7)
							usales+="|"+arr[i].replaceFirst("8", "4");
					}
				     String sql="update lotteryterm set salesinfo='"+usales+"' where lotteryid=1300004 and termno='"+termno+"';";
					 pw.println(sql);
				     termno++;
				}else{
					System.out.println("not found");
					break;
				}
			}
			stmt.close();
			pw.close();
		}
		
    //录入开奖信息
	public void inputWinResult(int lotteryId) throws LotteryException{
		System.out.println("录入开奖信息开始");
		LotteryManagerInterf lotteryService = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
		switch(lotteryId){
			case 1000001:
				try{
//					String term = "10055";
//					String salesVolume = "62315353";
//					String jackpot = "17338573.67";
//					
//					//大乐透开奖结果
//					TreeMap<String, ArrayList<String>> lotteryResult = new TreeMap<String, ArrayList<String>>();
//					//大乐透开奖结果-前区
//					ArrayList<String> list = new ArrayList<String>();
//					list.add("13");
//					list.add("14");
//					list.add("17");
//					list.add("27");
//					list.add("32");
//					lotteryResult.put("1", list);					
//					//大乐透开奖结果-后区
//					ArrayList<String> list1 = new ArrayList<String>();
//					list1.add("02");
//					list1.add("10");
//					lotteryResult.put("2", list1);
//					
//					//大乐透奖金结果
//					TreeMap<String, TreeMap<String, String[]>> winResult = new TreeMap<String, TreeMap<String, String[]>>();
//					//大乐透奖金结果-一等奖
//					TreeMap<String, String[]> map1 = new TreeMap<String, String[]>();
//					map1.put("A", new String[]{"1", "5000000"});
//					map1.put("B", new String[]{"1", "3000000"});
//					winResult.put("1", map1);
//					//大乐透奖金结果-二等奖
//					TreeMap<String, String[]> map2 = new TreeMap<String, String[]>();
//					map2.put("A", new String[]{"18", "146291"});
//					map2.put("B", new String[]{"4", "87774"});
//					winResult.put("2", map2);
//					//大乐透奖金结果-三等奖
//					TreeMap<String, String[]> map3 = new TreeMap<String, String[]>();
//					map3.put("A", new String[]{"70", "9372"});
//					map3.put("B", new String[]{"16", "5623"});
//					winResult.put("3", map3);
//					//大乐透奖金结果-四等奖
//					TreeMap<String, String[]> map4 = new TreeMap<String, String[]>();
//					map4.put("A", new String[]{"169", "3000"});
//					map4.put("B", new String[]{"85", "1500"});
//					winResult.put("4", map4);
//					//大乐透奖金结果-五等奖
//					TreeMap<String, String[]> map5 = new TreeMap<String, String[]>();
//					map5.put("A", new String[]{"4297", "600"});
//					map5.put("B", new String[]{"841", "300"});
//					winResult.put("5", map5);
//					//大乐透奖金结果-六等奖
//					TreeMap<String, String[]> map6 = new TreeMap<String, String[]>();
//					map6.put("A", new String[]{"19002", "100"});
//					map6.put("B", new String[]{"4206", "50"});
//					winResult.put("6", map6);
//					//大乐透奖金结果-七等奖
//					TreeMap<String, String[]> map7 = new TreeMap<String, String[]>();
//					map7.put("A", new String[]{"156134", "10"});
//					map7.put("B", new String[]{"34166", "5"});
//					winResult.put("7", map7);
//					//大乐透奖金结果-八等奖
//					TreeMap<String, String[]> map8 = new TreeMap<String, String[]>();
//					map8.put("A", new String[]{"1661025", "5"});
//					winResult.put("8", map8);
					
//					String term = "10056";
//					String salesVolume = "56490604";
//					String jackpot = "19842358.14";
//
//					//大乐透开奖结果
//					TreeMap<String, ArrayList<String>> lotteryResult = new TreeMap<String, ArrayList<String>>();
//					//大乐透开奖结果-前区
//					ArrayList<String> list = new ArrayList<String>();
//					list.add("05");
//					list.add("09");
//					list.add("23");
//					list.add("25");
//					list.add("30");
//					lotteryResult.put("1", list);					
//					//大乐透开奖结果-后区
//					ArrayList<String> list1 = new ArrayList<String>();
//					list1.add("09");
//					list1.add("12");
//					lotteryResult.put("2", list1);
//					
//					//大乐透奖金结果
//					TreeMap<String, TreeMap<String, String[]>> winResult = new TreeMap<String, TreeMap<String, String[]>>();
//					//大乐透奖金结果-一等奖
//					TreeMap<String, String[]> map1 = new TreeMap<String, String[]>();
//					map1.put("A", new String[]{"1", "5000000"});
//					map1.put("B", new String[]{"0", "0"});
//					winResult.put("1", map1);
//					//大乐透奖金结果-二等奖
//					TreeMap<String, String[]> map2 = new TreeMap<String, String[]>();
//					map2.put("A", new String[]{"27", "65392"});
//					map2.put("B", new String[]{"6", "39235"});
//					winResult.put("2", map2);
//					//大乐透奖金结果-三等奖
//					TreeMap<String, String[]> map3 = new TreeMap<String, String[]>();
//					map3.put("A", new String[]{"56", "8309"});
//					map3.put("B", new String[]{"7", "4985"});
//					winResult.put("3", map3);
//					//大乐透奖金结果-四等奖
//					TreeMap<String, String[]> map4 = new TreeMap<String, String[]>();
//					map4.put("A", new String[]{"197", "3000"});
//					map4.put("B", new String[]{"59", "1500"});
//					winResult.put("4", map4);
//					//大乐透奖金结果-五等奖
//					TreeMap<String, String[]> map5 = new TreeMap<String, String[]>();
//					map5.put("A", new String[]{"4305", "600"});
//					map5.put("B", new String[]{"1087", "300"});
//					winResult.put("5", map5);
//					//大乐透奖金结果-六等奖
//					TreeMap<String, String[]> map6 = new TreeMap<String, String[]>();
//					map6.put("A", new String[]{"14285", "100"});
//					map6.put("B", new String[]{"3305", "50"});
//					winResult.put("6", map6);
//					//大乐透奖金结果-七等奖
//					TreeMap<String, String[]> map7 = new TreeMap<String, String[]>();
//					map7.put("A", new String[]{"206975", "10"});
//					map7.put("B", new String[]{"44693", "5"});
//					winResult.put("7", map7);
//					//大乐透奖金结果-八等奖
//					TreeMap<String, String[]> map8 = new TreeMap<String, String[]>();
//					map8.put("A", new String[]{"2039957", "5"});
//					winResult.put("8", map8);

					String term = "10057";
					String salesVolume = "18150134";
					String jackpot = "48320215.62";

					//大乐透开奖结果
					TreeMap<String, ArrayList<String>> lotteryResult = new TreeMap<String, ArrayList<String>>();
					//大乐透开奖结果-前区
					ArrayList<String> list = new ArrayList<String>();
					list.add("05");
					list.add("09");
					list.add("23");
					list.add("25");
					list.add("30");
					lotteryResult.put("1", list);					
					//大乐透开奖结果-后区
					ArrayList<String> list1 = new ArrayList<String>();
					list1.add("09");
					list1.add("12");
					lotteryResult.put("2", list1);
					
					//大乐透奖金结果
					TreeMap<String, TreeMap<String, String[]>> winResult = new TreeMap<String, TreeMap<String, String[]>>();
					//大乐透奖金结果-一等奖
					TreeMap<String, String[]> map1 = new TreeMap<String, String[]>();
					map1.put("A", new String[]{"1", "5000000"});
					map1.put("B", new String[]{"0", "0"});
					winResult.put("1", map1);
					//大乐透奖金结果-二等奖
					TreeMap<String, String[]> map2 = new TreeMap<String, String[]>();
					map2.put("A", new String[]{"27", "65392"});
					map2.put("B", new String[]{"6", "39235"});
					winResult.put("2", map2);
					//大乐透奖金结果-三等奖
					TreeMap<String, String[]> map3 = new TreeMap<String, String[]>();
					map3.put("A", new String[]{"56", "8309"});
					map3.put("B", new String[]{"7", "4985"});
					winResult.put("3", map3);
					//大乐透奖金结果-四等奖
					TreeMap<String, String[]> map4 = new TreeMap<String, String[]>();
					map4.put("A", new String[]{"197", "3000"});
					map4.put("B", new String[]{"59", "1500"});
					winResult.put("4", map4);
					//大乐透奖金结果-五等奖
					TreeMap<String, String[]> map5 = new TreeMap<String, String[]>();
					map5.put("A", new String[]{"4305", "600"});
					map5.put("B", new String[]{"1087", "300"});
					winResult.put("5", map5);
					//大乐透奖金结果-六等奖
					TreeMap<String, String[]> map6 = new TreeMap<String, String[]>();
					map6.put("A", new String[]{"14285", "100"});
					map6.put("B", new String[]{"3305", "50"});
					winResult.put("6", map6);
					//大乐透奖金结果-七等奖
					TreeMap<String, String[]> map7 = new TreeMap<String, String[]>();
					map7.put("A", new String[]{"206975", "10"});
					map7.put("B", new String[]{"44693", "5"});
					winResult.put("7", map7);
					//大乐透奖金结果-八等奖
					TreeMap<String, String[]> map8 = new TreeMap<String, String[]>();
					map8.put("A", new String[]{"2039957", "5"});
					winResult.put("8", map8);

					
					lotteryService.inputSuperWinInfo(term, lotteryResult, salesVolume, jackpot, winResult);
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case 1000002:
				try{
//					String term = "10056";
//					String salesVolume = "17740512";
//					String jackpot = "44337004.74";
//					
//					//七星彩开奖结果
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "8");
//					lotteryResult.put("2", "1");
//					lotteryResult.put("3", "9");
//					lotteryResult.put("4", "5");
//					lotteryResult.put("5", "3");
//					lotteryResult.put("6", "9");
//					lotteryResult.put("7", "4");
//					
//					//七星彩奖金结果
//					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
//					winResult.put("1", new String[]{"0", "0"});
//					winResult.put("2", new String[]{"13", "33608"});
//					winResult.put("3", new String[]{"199", "1800"});
//					winResult.put("4", new String[]{"3113", "300"});
//					winResult.put("5", new String[]{"40199", "20"});
//					winResult.put("6", new String[]{"445535", "5"});

//					String term = "10057";
//					String salesVolume = "18150134";
//					String jackpot = "48320215.62";
//					
//					//七星彩开奖结果
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "1");
//					lotteryResult.put("2", "5");
//					lotteryResult.put("3", "7");
//					lotteryResult.put("4", "4");
//					lotteryResult.put("5", "1");
//					lotteryResult.put("6", "4");
//					lotteryResult.put("7", "3");
//					
//					//七星彩奖金结果
//					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
//					winResult.put("1", new String[]{"0", "0"});
//					winResult.put("2", new String[]{"18", "24587"});
//					winResult.put("3", new String[]{"261", "1800"});
//					winResult.put("4", new String[]{"3013", "300"});
//					winResult.put("5", new String[]{"38791", "20"});
//					winResult.put("6", new String[]{"463651", "5"});

//					String term = "10061";
//					String salesVolume = "22575078";
//					String jackpot = "56627530.84";
//					
//					//七星彩开奖结果
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "6");
//					lotteryResult.put("2", "5");
//					lotteryResult.put("3", "4");
//					lotteryResult.put("4", "0");
//					lotteryResult.put("5", "7");
//					lotteryResult.put("6", "2");
//					lotteryResult.put("7", "4");
//					
//					//七星彩奖金结果
//					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
//					winResult.put("1", new String[]{"1", "5000000"});
//					winResult.put("2", new String[]{"16", "36389"});
//					winResult.put("3", new String[]{"300", "1800"});
//					winResult.put("4", new String[]{"3339", "300"});
//					winResult.put("5", new String[]{"46249", "20"});
//					winResult.put("6", new String[]{"554560", "5"});

					String term = "10062";
					String salesVolume = "17836286";
					String jackpot = "60460689.47";
					
					//七星彩开奖结果
					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
					lotteryResult.put("1", "4");
					lotteryResult.put("2", "9");
					lotteryResult.put("3", "2");
					lotteryResult.put("4", "2");
					lotteryResult.put("5", "6");
					lotteryResult.put("6", "0");
					lotteryResult.put("7", "7");
					
					//七星彩奖金结果
					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
					winResult.put("1", new String[]{"0", "0"});
					winResult.put("2", new String[]{"31", "13738"});
					winResult.put("3", new String[]{"274", "1800"});
					winResult.put("4", new String[]{"3333", "300"});
					winResult.put("5", new String[]{"38181", "20"});
					winResult.put("6", new String[]{"444799", "5"});

					
					lotteryService.inputSevenColorWinInfo(term, lotteryResult, salesVolume, jackpot, winResult);
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case 1000003:
				try{
//					String term = "10126";
//					String salesVolume = "23298680";
//					String jackpot = "0";
					
//					String term = "10127";
//					String salesVolume = "23528360";
//					String jackpot = "0";

//					String term = "10128";
//					String salesVolume = "22425928";
//					String jackpot = "0";

//					String term = "10129";
//					String salesVolume = "23070696";
//					String jackpot = "0";

//					String term = "10130";
//					String salesVolume = "21721244";
//					String jackpot = "0";

					String term = "10131";
					String salesVolume = "22687740";
					String jackpot = "0";

					
//					//排列三开奖结果
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "4");
//					lotteryResult.put("2", "8");
//					lotteryResult.put("3", "3");

//					//排列三开奖结果
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "2");
//					lotteryResult.put("2", "7");
//					lotteryResult.put("3", "7");
					
//					//排列三开奖结果
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "9");
//					lotteryResult.put("2", "1");
//					lotteryResult.put("3", "3");

//					//排列三开奖结果
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "1");
//					lotteryResult.put("2", "0");
//					lotteryResult.put("3", "1");					
					
//					//排列三开奖结果
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "3");
//					lotteryResult.put("2", "9");
//					lotteryResult.put("3", "5");

					//排列三开奖结果
					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
					lotteryResult.put("1", "3");
					lotteryResult.put("2", "3");
					lotteryResult.put("3", "6");

					
//					//排列三奖金结果
//					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
//					//排列三奖金结果-直选
//					winResult.put("1", new String[]{"8655", "1000"});
//					//排列三奖金结果-组三
//					winResult.put("2", new String[]{"0", "320"});
//					//排列三奖金结果-组六
//					winResult.put("3", new String[]{"21277", "160"});
					
//					//排列三奖金结果
//					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
//					//排列三奖金结果-直选
//					winResult.put("1", new String[]{"6343", "1000"});
//					//排列三奖金结果-组三
//					winResult.put("2", new String[]{"7025", "320"});
//					//排列三奖金结果-组六
//					winResult.put("3", new String[]{"0", "160"});

//					//排列三奖金结果
//					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
//					//排列三奖金结果-直选
//					winResult.put("1", new String[]{"11764", "1000"});
//					//排列三奖金结果-组三
//					winResult.put("2", new String[]{"0", "320"});
//					//排列三奖金结果-组六
//					winResult.put("3", new String[]{"24574", "160"});

//					//排列三奖金结果
//					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
//					//排列三奖金结果-直选
//					winResult.put("1", new String[]{"7738", "1000"});
//					//排列三奖金结果-组三
//					winResult.put("2", new String[]{"5858", "320"});
//					//排列三奖金结果-组六
//					winResult.put("3", new String[]{"0", "160"});
					
//					//排列三奖金结果
//					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
//					//排列三奖金结果-直选
//					winResult.put("1", new String[]{"8345", "1000"});
//					//排列三奖金结果-组三
//					winResult.put("2", new String[]{"0", "320"});
//					//排列三奖金结果-组六
//					winResult.put("3", new String[]{"16783", "160"});

					//排列三奖金结果
					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
					//排列三奖金结果-直选
					winResult.put("1", new String[]{"3854", "1000"});
					//排列三奖金结果-组三
					winResult.put("2", new String[]{"5202", "320"});
					//排列三奖金结果-组六
					winResult.put("3", new String[]{"0", "160"});

					
					lotteryService.inputArrangeThreeWinInfo(term, lotteryResult, salesVolume, winResult);
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case 1000004:
				try{
//					String term = "10126";
//					String salesVolume = "8439484";
//					String jackpot = "0";
					
//					String term = "10127";
//					String salesVolume = "8765430";
//					String jackpot = "0";

//					String term = "10128";
//					String salesVolume = "8130580";
//					String jackpot = "0";					
					
//					String term = "10129";
//					String salesVolume = "8217252";
//					String jackpot = "0";

//					String term = "10130";
//					String salesVolume = "8236296";
//					String jackpot = "0";

					String term = "10131";
					String salesVolume = "8307228";
					String jackpot = "0";
					
					
//					//排列五开奖结果
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "4");
//					lotteryResult.put("2", "8");
//					lotteryResult.put("3", "3");
//					lotteryResult.put("4", "6");
//					lotteryResult.put("5", "9");

//					//排列五开奖结果
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "2");
//					lotteryResult.put("2", "7");
//					lotteryResult.put("3", "7");
//					lotteryResult.put("4", "4");
//					lotteryResult.put("5", "2");

//					//排列五开奖结果
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "9");
//					lotteryResult.put("2", "1");
//					lotteryResult.put("3", "3");
//					lotteryResult.put("4", "9");
//					lotteryResult.put("5", "1");

//					//排列五开奖结果
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "1");
//					lotteryResult.put("2", "0");
//					lotteryResult.put("3", "1");
//					lotteryResult.put("4", "2");
//					lotteryResult.put("5", "1");

//					//排列五开奖结果
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "3");
//					lotteryResult.put("2", "9");
//					lotteryResult.put("3", "5");
//					lotteryResult.put("4", "0");
//					lotteryResult.put("5", "8");

					//排列五开奖结果
					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
					lotteryResult.put("1", "3");
					lotteryResult.put("2", "3");
					lotteryResult.put("3", "6");
					lotteryResult.put("4", "9");
					lotteryResult.put("5", "6");

					
					
//					//排列五奖金结果
//					HashMap<String,String> winResult = new HashMap<String,String>();
//					//排列五奖金结果-注数
//					winResult.put("A", "29");
//					//排列五奖金结果-金额
//					winResult.put("B", "100000");

					
//					//排列五奖金结果
//					HashMap<String,String> winResult = new HashMap<String,String>();
//					//排列五奖金结果-注数
//					winResult.put("A", "23");
//					//排列五奖金结果-金额
//					winResult.put("B", "100000");
					
//					//排列五奖金结果
//					HashMap<String,String> winResult = new HashMap<String,String>();
//					//排列五奖金结果-注数
//					winResult.put("A", "65");
//					//排列五奖金结果-金额
//					winResult.put("B", "100000");

//					//排列五奖金结果
//					HashMap<String,String> winResult = new HashMap<String,String>();
//					//排列五奖金结果-注数
//					winResult.put("A", "35");
//					//排列五奖金结果-金额
//					winResult.put("B", "100000");

//					//排列五奖金结果
//					HashMap<String,String> winResult = new HashMap<String,String>();
//					//排列五奖金结果-注数
//					winResult.put("A", "41");
//					//排列五奖金结果-金额
//					winResult.put("B", "100000");

					//排列五奖金结果
					HashMap<String,String> winResult = new HashMap<String,String>();
					//排列五奖金结果-注数
					winResult.put("A", "18");
					//排列五奖金结果-金额
					winResult.put("B", "100000");

					
					lotteryService.inputArrangeFiveWinInfo(term, lotteryResult, salesVolume, winResult);
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case 1000005:
				try{
//					String term = "10055";
//					String salesVolume = "1333632";
//					String jackpot = "0";
//					
//					//生肖乐开奖结果
//					ArrayList<String> lotteryResult = new ArrayList<String>();
//					lotteryResult.add("02");
//					lotteryResult.add("10");
//					
//					//生肖乐奖金结果
//					HashMap<String,String> winResult = new HashMap<String,String>();
//					//生肖乐奖金结果-注数
//					winResult.put("A", "3961");
//					//生肖乐奖金结果-金额
//					winResult.put("B", "60");
					
					String term = "10056";
					String salesVolume = "1212734";
					String jackpot = "0";
					
					//生肖乐开奖结果
					ArrayList<String> lotteryResult = new ArrayList<String>();
					lotteryResult.add("09");
					lotteryResult.add("12");
					
					//生肖乐奖金结果
					HashMap<String,String> winResult = new HashMap<String,String>();
					//生肖乐奖金结果-注数
					winResult.put("A", "12065");
					//生肖乐奖金结果-金额
					winResult.put("B", "60");
					
					lotteryService.inputHappyZodiacWinInfo(term, lotteryResult, salesVolume, winResult);
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case 1300001:
				try{
//					String term = "10048";
//					String salesVolume = "25408638";
//					String jackpot = "0";
//					
//					//足彩胜负彩开奖结果
//					ArrayList<String> lotteryResult = new ArrayList<String>();
//					lotteryResult.add(0, "3");
//					lotteryResult.add(1, "0");
//					lotteryResult.add(2, "1");
//					lotteryResult.add(3, "3");
//					lotteryResult.add(4, "0");
//					lotteryResult.add(5, "1");
//					lotteryResult.add(6, "1");
//					lotteryResult.add(7, "3");
//					lotteryResult.add(8, "3");
//					lotteryResult.add(9, "0");
//					lotteryResult.add(10, "3");
//					lotteryResult.add(11, "3");
//					lotteryResult.add(12, "3");
//					lotteryResult.add(13, "3");
//
//					
//					//足彩胜负彩奖金结果
//					TreeMap<String,String[]> winResult = new TreeMap<String,String[]>();
//					//一等奖
//					winResult.put("1", new String[]{"16", "711441"});
//					//二等奖
//					winResult.put("2", new String[]{"498", "9796"});
										
					String term = "10049";
					String salesVolume = "36393924";
					String jackpot = "0";
					
					//足彩胜负彩开奖结果
					ArrayList<String> lotteryResult = new ArrayList<String>();
					lotteryResult.add(0, "0");
					lotteryResult.add(1, "0");
					lotteryResult.add(2, "3");
					lotteryResult.add(3, "0");
					lotteryResult.add(4, "1");
					lotteryResult.add(5, "3");
					lotteryResult.add(6, "3");
					lotteryResult.add(7, "3");
					lotteryResult.add(8, "1");
					lotteryResult.add(9, "1");
					lotteryResult.add(10, "3");
					lotteryResult.add(11, "3");
					lotteryResult.add(12, "3");
					lotteryResult.add(13, "3");

					
					//足彩胜负彩奖金结果
					TreeMap<String,String[]> winResult = new TreeMap<String,String[]>();
					//一等奖
					winResult.put("1", new String[]{"411", "39670"});
					//二等奖
					winResult.put("2", new String[]{"9683", "721"});
					
					lotteryService.inputWinOrFailWinInfo(term, lotteryResult, salesVolume, jackpot, winResult);
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			case 1300002:
				try{
//					String term = "10048";
//					String salesVolume = "21793086";
//					String jackpot = "0";
//					
//					//足彩任选九开奖结果
//					ArrayList<String> lotteryResult = new ArrayList<String>();
//					lotteryResult.add(0, "3");
//					lotteryResult.add(1, "0");
//					lotteryResult.add(2, "1");
//					lotteryResult.add(3, "3");
//					lotteryResult.add(4, "0");
//					lotteryResult.add(5, "1");
//					lotteryResult.add(6, "1");
//					lotteryResult.add(7, "3");
//					lotteryResult.add(8, "3");
//					lotteryResult.add(9, "0");
//					lotteryResult.add(10, "3");
//					lotteryResult.add(11, "3");
//					lotteryResult.add(12, "3");
//					lotteryResult.add(13, "3");
//
//					
//					//足彩任选九奖金结果
//					Map<String, String> winResult = new HashMap<String, String>();
//					//注数
//					winResult.put("A", "5247");
//					//金额
//					winResult.put("B", "2658");
					
					String term = "10049";
					String salesVolume = "23369902";
					String jackpot = "0";
					
					//足彩任选九开奖结果
					ArrayList<String> lotteryResult = new ArrayList<String>();
					lotteryResult.add(0, "0");
					lotteryResult.add(1, "0");
					lotteryResult.add(2, "3");
					lotteryResult.add(3, "0");
					lotteryResult.add(4, "1");
					lotteryResult.add(5, "3");
					lotteryResult.add(6, "3");
					lotteryResult.add(7, "3");
					lotteryResult.add(8, "1");
					lotteryResult.add(9, "1");
					lotteryResult.add(10, "3");
					lotteryResult.add(11, "3");
					lotteryResult.add(12, "3");
					lotteryResult.add(13, "3");

					
					//足彩任选九奖金结果
					Map<String, String> winResult = new HashMap<String, String>();
					//注数
					winResult.put("A", "49592");
					//金额
					winResult.put("B", "301");
					
					lotteryService.inputArbitryNineWinInfo(term, lotteryResult, salesVolume, jackpot, winResult);
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			default:
				break;
		}
		System.out.println("录入开奖信息完毕");
	}

	public void inputSalesInfo() throws ClassNotFoundException, SQLException{
		System.out.println("录入足彩销售信息开始");

//		String term = "10055";
//		Map<Integer, Map<String,String>> gameMap = new HashMap<Integer, Map<String,String>>();
//		
//		Map<String, String> map1 = new HashMap<String, String>();
//		map1.put("A", "瑞士");
//		map1.put("B", "哥斯达黎加");
//		map1.put("C", "国际赛");
//		map1.put("D", "2010-06-02");
//		gameMap.put(Integer.valueOf(1), map1);
//
//		Map<String, String> map2 = new HashMap<String, String>();
//		map2.put("A", "荷兰");
//		map2.put("B", "加纳");
//		map2.put("C", "国际赛");
//		map2.put("D", "2010-06-02");
//		gameMap.put(Integer.valueOf(2), map2);
//
//		Map<String, String> map3 = new HashMap<String, String>();
//		map3.put("A", "葡萄牙");
//		map3.put("B", "喀麦隆");
//		map3.put("C", "国际赛");
//		map3.put("D", "2010-06-02");
//		gameMap.put(Integer.valueOf(3), map3);
//		
//		Map<String, String> map4 = new HashMap<String, String>();
//		map4.put("A", "挪威");
//		map4.put("B", "乌克兰");
//		map4.put("C", "国际赛");
//		map4.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(4), map4);
//
//		Map<String, String> map5 = new HashMap<String, String>();
//		map5.put("A", "白俄罗斯");
//		map5.put("B", "瑞典");
//		map5.put("C", "国际赛");
//		map5.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(5), map5);
//
//		Map<String, String> map6 = new HashMap<String, String>();
//		map6.put("A", "罗马尼亚");
//		map6.put("B", "马其顿");
//		map6.put("C", "国际赛");
//		map6.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(6), map6);
//
//		Map<String, String> map7 = new HashMap<String, String>();
//		map7.put("A", "希腊");
//		map7.put("B", "巴拉圭");
//		map7.put("C", "国际赛");
//		map7.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(7), map7);
//
//		Map<String, String> map8 = new HashMap<String, String>();
//		map8.put("A", "塞尔维亚");
//		map8.put("B", "波兰");
//		map8.put("C", "国际赛");
//		map8.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(8), map8);
//
//		Map<String, String> map9 = new HashMap<String, String>();
//		map9.put("A", "普鲁登特");
//		map9.put("B", "戈亚尼恩斯竞技");
//		map9.put("C", "巴甲");
//		map9.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(9), map9);
//
//		Map<String, String> map10 = new HashMap<String, String>();
//		map10.put("A", "巴拉纳竞技");
//		map10.put("B", "博塔弗戈");
//		map10.put("C", "巴甲");
//		map10.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(10), map10);
//
//		Map<String, String> map11 = new HashMap<String, String>();
//		map11.put("A", "塞阿拉");
//		map11.put("B", "奥瓦");
//		map11.put("C", "巴甲");
//		map11.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(11), map11);
//
//		Map<String, String> map12 = new HashMap<String, String>();
//		map12.put("A", "克鲁塞罗");
//		map12.put("B", "桑托斯");
//		map12.put("C", "巴甲");
//		map12.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(12), map12);
//
//		Map<String, String> map13 = new HashMap<String, String>();
//		map13.put("A", "戈伊亚斯");
//		map13.put("B", "圣保罗");
//		map13.put("C", "巴甲");
//		map13.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(13), map13);
//
//		Map<String, String> map14 = new HashMap<String, String>();
//		map14.put("A", "帕尔梅拉斯");
//		map14.put("B", "弗拉门戈");
//		map14.put("C", "巴甲");
//		map14.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(14), map14);
//
//
//		String salesInfo = LotteryTools.mergeSalesInfo(gameMap);
//		String starttime = "2010-05-30 20:00:00";
//		String deadline = "2010-06-01 19:30:00";
//		String deadline2 = "2010-06-01 19:00:00";
//		String winline = "2010-06-03 22:00:00";
//		String starttime2 = "2010-05-30 20:00:00";
//		String deadline3 = "2010-06-01 22:00:00";
//		String winline2 = "2010-06-03 20:00:00";
//		String changeline = "2010-08-01 20:00:00";


//		String term = "10056";
//		Map<Integer, Map<String,String>> gameMap = new HashMap<Integer, Map<String,String>>();
//		
//		Map<String, String> map1 = new HashMap<String, String>();
//		map1.put("A", "西班牙");
//		map1.put("B", "韩国");
//		map1.put("C", "国际赛");
//		map1.put("D", "2010-06-04");
//		gameMap.put(Integer.valueOf(1), map1);
//
//		Map<String, String> map2 = new HashMap<String, String>();
//		map2.put("A", "意大利");
//		map2.put("B", "墨西哥");
//		map2.put("C", "国际赛");
//		map2.put("D", "2010-06-04");
//		gameMap.put(Integer.valueOf(2), map2);
//
//		Map<String, String> map3 = new HashMap<String, String>();
//		map3.put("A", "德国");
//		map3.put("B", "波黑");
//		map3.put("C", "国际赛");
//		map3.put("D", "2010-06-04");
//		gameMap.put(Integer.valueOf(3), map3);
//		
//		Map<String, String> map4 = new HashMap<String, String>();
//		map4.put("A", "日本");
//		map4.put("B", "科特迪瓦");
//		map4.put("C", "国际赛");
//		map4.put("D", "2010-06-04");
//		gameMap.put(Integer.valueOf(4), map4);
//
//		Map<String, String> map5 = new HashMap<String, String>();
//		map5.put("A", "法国");
//		map5.put("B", "中国");
//		map5.put("C", "国际赛");
//		map5.put("D", "2010-06-05");
//		gameMap.put(Integer.valueOf(5), map5);
//
//		Map<String, String> map6 = new HashMap<String, String>();
//		map6.put("A", "卢森堡");
//		map6.put("B", "法罗群岛");
//		map6.put("C", "国际赛");
//		map6.put("D", "2010-06-05");
//		gameMap.put(Integer.valueOf(6), map6);
//
//		Map<String, String> map7 = new HashMap<String, String>();
//		map7.put("A", "斯洛文尼亚");
//		map7.put("B", "新西兰");
//		map7.put("C", "国际赛");
//		map7.put("D", "2010-06-05");
//		gameMap.put(Integer.valueOf(7), map7);
//
//		Map<String, String> map8 = new HashMap<String, String>();
//		map8.put("A", "荷兰");
//		map8.put("B", "匈牙利");
//		map8.put("C", "国际赛");
//		map8.put("D", "2010-06-05");
//		gameMap.put(Integer.valueOf(8), map8);
//
//		Map<String, String> map9 = new HashMap<String, String>();
//		map9.put("A", "南非");
//		map9.put("B", "丹麦");
//		map9.put("C", "国际赛");
//		map9.put("D", "2010-06-05");
//		gameMap.put(Integer.valueOf(9), map9);
//
//		Map<String, String> map10 = new HashMap<String, String>();
//		map10.put("A", "瑞士");
//		map10.put("B", "意大利");
//		map10.put("C", "国际赛");
//		map10.put("D", "2010-06-06");
//		gameMap.put(Integer.valueOf(10), map10);
//
//		Map<String, String> map11 = new HashMap<String, String>();
//		map11.put("A", "科林蒂安");
//		map11.put("B", "巴西国际");
//		map11.put("C", "巴甲");
//		map11.put("D", "2010-06-04");
//		gameMap.put(Integer.valueOf(11), map11);
//
//		Map<String, String> map12 = new HashMap<String, String>();
//		map12.put("A", "格雷米奥");
//		map12.put("B", "米涅罗竞技");
//		map12.put("C", "巴甲");
//		map12.put("D", "2010-06-04");
//		gameMap.put(Integer.valueOf(12), map12);
//
//		Map<String, String> map13 = new HashMap<String, String>();
//		map13.put("A", "奥瓦");
//		map13.put("B", "弗鲁米嫩塞");
//		map13.put("C", "巴甲");
//		map13.put("D", "2010-06-06");
//		gameMap.put(Integer.valueOf(13), map13);
//
//		Map<String, String> map14 = new HashMap<String, String>();
//		map14.put("A", "弗拉门戈");
//		map14.put("B", "戈伊亚斯");
//		map14.put("C", "巴甲");
//		map14.put("D", "2010-06-06");
//		gameMap.put(Integer.valueOf(14), map14);
//
//
//		String salesInfo = LotteryTools.mergeSalesInfo(gameMap);
//		String starttime = "2010-05-30 20:00:00";
//		String deadline = "2010-06-01 19:30:00";
//		String deadline2 = "2010-06-01 19:00:00";
//		String winline = "2010-06-06 22:00:00";
//		String starttime2 = "2010-05-30 20:00:00";
//		String deadline3 = "2010-06-01 22:00:00";
//		String winline2 = "2010-06-03 20:00:00";
//		String changeline = "2010-08-04 20:00:00";


//		String term = "10057";
//		Map<Integer, Map<String,String>> gameMap = new HashMap<Integer, Map<String,String>>();
//		
//		Map<String, String> map1 = new HashMap<String, String>();
//		map1.put("A", "阿尔巴塞特");
//		map1.put("B", "维尔瓦");
//		map1.put("C", "西乙");
//		map1.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(1), map1);
//
//		Map<String, String> map2 = new HashMap<String, String>();
//		map2.put("A", "皇家贝蒂斯");
//		map2.put("B", "努曼西亚");
//		map2.put("C", "西乙");
//		map2.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(2), map2);
//
//		Map<String, String> map3 = new HashMap<String, String>();
//		map3.put("A", "卡迪斯");
//		map3.put("B", "皇家社会");
//		map3.put("C", "西乙");
//		map3.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(3), map3);
//		
//		Map<String, String> map4 = new HashMap<String, String>();
//		map4.put("A", "卡斯迪隆");
//		map4.put("B", "萨拉曼卡");
//		map4.put("C", "西乙");
//		map4.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(4), map4);
//
//		Map<String, String> map5 = new HashMap<String, String>();
//		map5.put("A", "埃尔切");
//		map5.put("B", "穆尔西亚");
//		map5.put("C", "西乙");
//		map5.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(5), map5);
//
//		Map<String, String> map6 = new HashMap<String, String>();
//		map6.put("A", "维拉利尔B队");
//		map6.put("B", "韦斯卡");
//		map6.put("C", "西乙");
//		map6.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(6), map6);
//
//		Map<String, String> map7 = new HashMap<String, String>();
//		map7.put("A", "卡塔赫纳");
//		map7.put("B", "赫库斯");
//		map7.put("C", "西乙");
//		map7.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(7), map7);
//
//		Map<String, String> map8 = new HashMap<String, String>();
//		map8.put("A", "巴列卡诺");
//		map8.put("B", "科尔多瓦");
//		map8.put("C", "西乙");
//		map8.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(8), map8);
//
//		Map<String, String> map9 = new HashMap<String, String>();
//		map9.put("A", "塞尔塔");
//		map9.put("B", "塔拉戈纳");
//		map9.put("C", "西乙");
//		map9.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(9), map9);
//
//		Map<String, String> map10 = new HashMap<String, String>();
//		map10.put("A", "皇家伊伦联");
//		map10.put("B", "莱万特");
//		map10.put("C", "西乙");
//		map10.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(10), map10);
//
//		Map<String, String> map11 = new HashMap<String, String>();
//		map11.put("A", "拉斯帕尔马斯");
//		map11.put("B", "赫罗纳");
//		map11.put("C", "西乙");
//		map11.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(11), map11);
//
//		Map<String, String> map12 = new HashMap<String, String>();
//		map12.put("A", "博塔弗戈");
//		map12.put("B", "科林蒂安");
//		map12.put("C", "巴甲");
//		map12.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(12), map12);
//
//		Map<String, String> map13 = new HashMap<String, String>();
//		map13.put("A", "桑托斯");
//		map13.put("B", "瓦斯科达伽马");
//		map13.put("C", "巴甲");
//		map13.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(13), map13);
//
//		Map<String, String> map14 = new HashMap<String, String>();
//		map14.put("A", "圣保罗");
//		map14.put("B", "格雷米奥");
//		map14.put("C", "巴甲");
//		map14.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(14), map14);
//
//
//		String salesInfo = LotteryTools.mergeSalesInfo(gameMap);
//		String starttime = "2010-06-02 20:00:00";
//		String deadline = "2010-06-06 19:30:00";
//		String deadline2 = "2010-06-06 19:00:00";
//		String winline = "2010-06-07 22:00:00";
//		String starttime2 = "2010-06-02 20:00:00";
//		String deadline3 = "2010-06-06 22:00:00";
//		String winline2 = "2010-06-07 20:00:00";
//		String changeline = "2010-08-05 20:00:00";
		
//		String term = "10058";
//		Map<Integer, Map<String,String>> gameMap = new HashMap<Integer, Map<String,String>>();
//		
//		Map<String, String> map1 = new HashMap<String, String>();
//		map1.put("A", "南非");
//		map1.put("B", "墨西哥");
//		map1.put("C", "世界杯");
//		map1.put("D", "2010-06-11");
//		gameMap.put(Integer.valueOf(1), map1);
//
//		Map<String, String> map2 = new HashMap<String, String>();
//		map2.put("A", "乌拉圭");
//		map2.put("B", "法国");
//		map2.put("C", "世界杯");
//		map2.put("D", "2010-06-12");
//		gameMap.put(Integer.valueOf(2), map2);
//
//		Map<String, String> map3 = new HashMap<String, String>();
//		map3.put("A", "韩国");
//		map3.put("B", "希腊");
//		map3.put("C", "世界杯");
//		map3.put("D", "2010-06-12");
//		gameMap.put(Integer.valueOf(3), map3);
//		
//		Map<String, String> map4 = new HashMap<String, String>();
//		map4.put("A", "阿根廷");
//		map4.put("B", "尼日利亚");
//		map4.put("C", "世界杯");
//		map4.put("D", "2010-06-12");
//		gameMap.put(Integer.valueOf(4), map4);
//
//		Map<String, String> map5 = new HashMap<String, String>();
//		map5.put("A", "英格兰");
//		map5.put("B", "美国");
//		map5.put("C", "世界杯");
//		map5.put("D", "2010-06-13");
//		gameMap.put(Integer.valueOf(5), map5);
//
//		Map<String, String> map6 = new HashMap<String, String>();
//		map6.put("A", "阿尔及利亚");
//		map6.put("B", "斯洛文尼亚");
//		map6.put("C", "世界杯");
//		map6.put("D", "2010-06-13");
//		gameMap.put(Integer.valueOf(6), map6);
//
//		Map<String, String> map7 = new HashMap<String, String>();
//		map7.put("A", "塞尔维亚");
//		map7.put("B", "加纳");
//		map7.put("C", "世界杯");
//		map7.put("D", "2010-06-14");
//		gameMap.put(Integer.valueOf(7), map7);
//		
//		Map<String, String> map8 = new HashMap<String, String>();
//		map8.put("A", "德国");
//		map8.put("B", "澳大利亚");
//		map8.put("C", "世界杯");
//		map8.put("D", "2010-06-14");
//		gameMap.put(Integer.valueOf(8), map8);
//
//		Map<String, String> map9 = new HashMap<String, String>();
//		map9.put("A", "荷兰");
//		map9.put("B", "丹麦");
//		map9.put("C", "世界杯");
//		map9.put("D", "2010-06-14");
//		gameMap.put(Integer.valueOf(9), map9);
//
//		Map<String, String> map10 = new HashMap<String, String>();
//		map10.put("A", "日本");
//		map10.put("B", "喀麦隆");
//		map10.put("C", "世界杯");
//		map10.put("D", "2010-06-14");
//		gameMap.put(Integer.valueOf(10), map10);
//
//		Map<String, String> map11 = new HashMap<String, String>();
//		map11.put("A", "意大利");
//		map11.put("B", "巴拉圭");
//		map11.put("C", "世界杯");
//		map11.put("D", "2010-06-15");
//		gameMap.put(Integer.valueOf(11), map11);
//
//		Map<String, String> map12 = new HashMap<String, String>();
//		map12.put("A", "新西兰");
//		map12.put("B", "斯洛伐克");
//		map12.put("C", "世界杯");
//		map12.put("D", "2010-06-15");
//		gameMap.put(Integer.valueOf(12), map12);
//
//		Map<String, String> map13 = new HashMap<String, String>();
//		map13.put("A", "科特迪瓦");
//		map13.put("B", "葡萄牙");
//		map13.put("C", "世界杯");
//		map13.put("D", "2010-06-15");
//		gameMap.put(Integer.valueOf(13), map13);
//
//		Map<String, String> map14 = new HashMap<String, String>();
//		map14.put("A", "巴西");
//		map14.put("B", "朝鲜");
//		map14.put("C", "世界杯");
//		map14.put("D", "2010-06-16");
//		gameMap.put(Integer.valueOf(14), map14);
//
//		String salesInfo = LotteryTools.mergeSalesInfo(gameMap);
//		String starttime = "2010-06-07 20:00:00";
//		String deadline = "2010-06-11 19:30:00";
//		String deadline2 = "2010-06-11 19:00:00";
//		String winline = "2010-06-16 22:00:00";
//		String starttime2 = "2010-06-07 20:00:00";
//		String deadline3 = "2010-06-11 22:00:00";
//		String winline2 = "2010-06-07 20:00:00";
//		String changeline = "2010-08-05 20:00:00";

//		String term = "10059";
//		Map<Integer, Map<String,String>> gameMap = new HashMap<Integer, Map<String,String>>();
//		
//		Map<String, String> map1 = new HashMap<String, String>();
//		map1.put("A", "希腊");
//		map1.put("B", "尼日利亚");
//		map1.put("C", "世界杯");
//		map1.put("D", "2010-06-17");
//		gameMap.put(Integer.valueOf(1), map1);
//
//		Map<String, String> map2 = new HashMap<String, String>();
//		map2.put("A", "法国");
//		map2.put("B", "墨西哥");
//		map2.put("C", "世界杯");
//		map2.put("D", "2010-06-18");
//		gameMap.put(Integer.valueOf(2), map2);
//
//		Map<String, String> map3 = new HashMap<String, String>();
//		map3.put("A", "德国");
//		map3.put("B", "塞尔维亚");
//		map3.put("C", "世界杯");
//		map3.put("D", "2010-06-18");
//		gameMap.put(Integer.valueOf(3), map3);
//		
//		Map<String, String> map4 = new HashMap<String, String>();
//		map4.put("A", "斯洛文尼亚");
//		map4.put("B", "美国");
//		map4.put("C", "世界杯");
//		map4.put("D", "2010-06-18");
//		gameMap.put(Integer.valueOf(4), map4);
//
//		Map<String, String> map5 = new HashMap<String, String>();
//		map5.put("A", "英格兰");
//		map5.put("B", "阿尔及利亚");
//		map5.put("C", "世界杯");
//		map5.put("D", "2010-06-19");
//		gameMap.put(Integer.valueOf(5), map5);
//
//		Map<String, String> map6 = new HashMap<String, String>();
//		map6.put("A", "荷兰");
//		map6.put("B", "日本");
//		map6.put("C", "世界杯");
//		map6.put("D", "2010-06-19");
//		gameMap.put(Integer.valueOf(6), map6);
//
//		Map<String, String> map7 = new HashMap<String, String>();
//		map7.put("A", "加纳");
//		map7.put("B", "澳大利亚");
//		map7.put("C", "世界杯");
//		map7.put("D", "2010-06-19");
//		gameMap.put(Integer.valueOf(7), map7);
//		
//		Map<String, String> map8 = new HashMap<String, String>();
//		map8.put("A", "喀麦隆");
//		map8.put("B", "丹麦");
//		map8.put("C", "世界杯");
//		map8.put("D", "2010-06-20");
//		gameMap.put(Integer.valueOf(8), map8);
//
//		Map<String, String> map9 = new HashMap<String, String>();
//		map9.put("A", "斯洛伐克");
//		map9.put("B", "巴拉圭");
//		map9.put("C", "世界杯");
//		map9.put("D", "2010-06-20");
//		gameMap.put(Integer.valueOf(9), map9);
//
//		Map<String, String> map10 = new HashMap<String, String>();
//		map10.put("A", "意大利");
//		map10.put("B", "新西兰");
//		map10.put("C", "世界杯");
//		map10.put("D", "2010-06-20");
//		gameMap.put(Integer.valueOf(10), map10);
//
//		Map<String, String> map11 = new HashMap<String, String>();
//		map11.put("A", "巴西");
//		map11.put("B", "科特迪瓦");
//		map11.put("C", "世界杯");
//		map11.put("D", "2010-06-21");
//		gameMap.put(Integer.valueOf(11), map11);
//
//		Map<String, String> map12 = new HashMap<String, String>();
//		map12.put("A", "葡萄牙");
//		map12.put("B", "朝鲜");
//		map12.put("C", "世界杯");
//		map12.put("D", "2010-06-21");
//		gameMap.put(Integer.valueOf(12), map12);
//
//		Map<String, String> map13 = new HashMap<String, String>();
//		map13.put("A", "智利");
//		map13.put("B", "瑞士");
//		map13.put("C", "世界杯");
//		map13.put("D", "2010-06-21");
//		gameMap.put(Integer.valueOf(13), map13);
//
//		Map<String, String> map14 = new HashMap<String, String>();
//		map14.put("A", "西班牙");
//		map14.put("B", "洪都拉斯");
//		map14.put("C", "世界杯");
//		map14.put("D", "2010-06-21");
//		gameMap.put(Integer.valueOf(14), map14);
//
//		String salesInfo = LotteryTools.mergeSalesInfo(gameMap);
//		String starttime = "2010-06-12 20:00:00";
//		String deadline = "2010-06-17 19:30:00";
//		String deadline2 = "2010-06-17 19:00:00";
//		String winline = "2010-06-22 22:00:00";
//		String starttime2 = "2010-06-12 20:00:00";
//		String deadline3 = "2010-06-17 22:00:00";
//		String winline2 = "2010-06-22 20:00:00";
//		String changeline = "2010-08-20 20:00:00";

		String term = "10060";
		Map<Integer, Map<String,String>> gameMap = new HashMap<Integer, Map<String,String>>();
		
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("A", "希腊");
		map1.put("B", "阿根廷");
		map1.put("C", "世界杯");
		map1.put("D", "2010-06-23");
		gameMap.put(Integer.valueOf(1), map1);

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("A", "尼日利亚");
		map2.put("B", "韩国");
		map2.put("C", "世界杯");
		map2.put("D", "2010-06-23");
		gameMap.put(Integer.valueOf(2), map2);

		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("A", "斯洛文尼亚");
		map3.put("B", "英格兰");
		map3.put("C", "世界杯");
		map3.put("D", "2010-06-23");
		gameMap.put(Integer.valueOf(3), map3);
		
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("A", "美国");
		map4.put("B", "阿尔及利亚");
		map4.put("C", "世界杯");
		map4.put("D", "2010-06-23");
		gameMap.put(Integer.valueOf(4), map4);

		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("A", "澳大利亚");
		map5.put("B", "塞尔维亚");
		map5.put("C", "世界杯");
		map5.put("D", "2010-06-24");
		gameMap.put(Integer.valueOf(5), map5);

		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("A", "加纳");
		map6.put("B", "德国");
		map6.put("C", "世界杯");
		map6.put("D", "2010-06-24");
		gameMap.put(Integer.valueOf(6), map6);

		Map<String, String> map7 = new HashMap<String, String>();
		map7.put("A", "巴拉圭");
		map7.put("B", "新西兰");
		map7.put("C", "世界杯");
		map7.put("D", "2010-06-24");
		gameMap.put(Integer.valueOf(7), map7);
		
		Map<String, String> map8 = new HashMap<String, String>();
		map8.put("A", "斯洛伐克");
		map8.put("B", "意大利");
		map8.put("C", "世界杯");
		map8.put("D", "2010-06-24");
		gameMap.put(Integer.valueOf(8), map8);

		Map<String, String> map9 = new HashMap<String, String>();
		map9.put("A", "喀麦隆");
		map9.put("B", "荷兰");
		map9.put("C", "世界杯");
		map9.put("D", "2010-06-25");
		gameMap.put(Integer.valueOf(9), map9);

		Map<String, String> map10 = new HashMap<String, String>();
		map10.put("A", "丹麦");
		map10.put("B", "日本");
		map10.put("C", "世界杯");
		map10.put("D", "2010-06-25");
		gameMap.put(Integer.valueOf(10), map10);

		Map<String, String> map11 = new HashMap<String, String>();
		map11.put("A", "朝鲜");
		map11.put("B", "科特迪瓦");
		map11.put("C", "世界杯");
		map11.put("D", "2010-06-25");
		gameMap.put(Integer.valueOf(11), map11);

		Map<String, String> map12 = new HashMap<String, String>();
		map12.put("A", "葡萄牙");
		map12.put("B", "巴西");
		map12.put("C", "世界杯");
		map12.put("D", "2010-06-25");
		gameMap.put(Integer.valueOf(12), map12);

		Map<String, String> map13 = new HashMap<String, String>();
		map13.put("A", "智利");
		map13.put("B", "西班牙");
		map13.put("C", "世界杯");
		map13.put("D", "2010-06-25");
		gameMap.put(Integer.valueOf(13), map13);

		Map<String, String> map14 = new HashMap<String, String>();
		map14.put("A", "瑞士");
		map14.put("B", "洪都拉斯");
		map14.put("C", "世界杯");
		map14.put("D", "2010-06-26");
		gameMap.put(Integer.valueOf(14), map14);

		String salesInfo = LotteryTools.mergeSalesInfo(gameMap);
		String starttime = "2010-06-18 20:00:00";
		String deadline = "2010-06-22 19:30:00";
		String deadline2 = "2010-06-22 19:00:00";
		String winline = "2010-06-26 20:00:00";
		String starttime2 = "2010-06-18 20:00:00";
		String deadline3 = "2010-06-22 22:00:00";
		String winline2 = "2010-06-26 20:00:00";
		String changeline = "2010-08-24 20:00:00";

		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://172.16.0.201:3306/tlt", "tlt2010", "lottery");
		Statement stmt = con.createStatement();
		
		String sql = "update lotteryterm set salesinfo='" + salesInfo + "', starttime='" + starttime + "', deadline='" + 
				deadline + "', deadline2='" + deadline2 + "', winline='" + winline + "', starttime2='" + starttime2 + 
				"', deadline3='" + deadline3 + "', winline2='" + winline2 + "', changeline='" + changeline + "' where " +
				"lotteryid in(1300001, 1300002) and termno='" + term +"'";
		System.out.println("录入足彩销售信息结束，返回：" + stmt.executeUpdate(sql));
		stmt.close();
		con.close();
	}
	//修改多乐彩历史数据开奖结果
	public void updateDLCLS(String fileName) throws IOException{
		PrintWriter pw = new PrintWriter(new File(fileName));
		File file=new File("C:\\Users\\aaron\\Desktop\\多乐彩A.txt"); 
		BufferedReader input=new BufferedReader(new FileReader(file)); 
		try 
		{ 
			String text; 
			while((text=input.readLine())!=null) 
			{
				String termno=text.substring(2, 11).replaceAll("-", "");
				String result=text.substring(12, 26).replaceAll(",", "");
				String sql="update lotteryterm set termstatus=2, winstatus=8, salestatus=5, lotteryresult ='"+result+"' where lotteryid=1200001 and termno='"+termno+"';";
				pw.println(sql);
			}
		} catch(Exception ex) {}
		finally{
			input.close();
			pw.close();
		}
		System.out.println("the end");
		
	}
	
	
	public void insertDLCLS(String fileName) throws IOException{
		PrintWriter pw = new PrintWriter(new File(fileName));
		File file=new File("D:\\AAA.txt");
		BufferedReader input=new BufferedReader(new FileReader(file)); 
		try 
		{
			SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
			SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String text; 
			while((text=input.readLine())!=null) 
			{
				Calendar deadline=Calendar.getInstance();//系统止售
				Calendar deadline3=Calendar.getInstance();//官方止售时间
				Calendar starttime=Calendar.getInstance();//系统开售时间
				Calendar starttime2=Calendar.getInstance();//官方开售时间
				Calendar winline=Calendar.getInstance();//系统开奖时间
				Calendar winline2=Calendar.getInstance();//官方开奖时间
				Calendar deadline2=Calendar.getInstance();//合买止售时间
				Calendar calendar = Calendar.getInstance();

				String termno=text.substring(2, 11).replaceAll("-", "");
				String result=text.substring(12, 26).replaceAll(",", "");
				String numStr = text.substring(9, 11);
				String date = text.substring(0, 8);
				
				starttime.setTime(new SimpleDateFormat("yyyyMMdd").parse(date));
				starttime2.setTime(new SimpleDateFormat("yyyyMMdd").parse(date));
				deadline.setTime(new SimpleDateFormat("yyyyMMdd").parse(date));
				deadline2.setTime(new SimpleDateFormat("yyyyMMdd").parse(date));
				deadline3.setTime(new SimpleDateFormat("yyyyMMdd").parse(date));
				winline.setTime(new SimpleDateFormat("yyyyMMdd").parse(date));
				winline2.setTime(new SimpleDateFormat("yyyyMMdd").parse(date));
				calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(date));

				int num = Integer.parseInt(numStr);
				starttime.set(Calendar.HOUR_OF_DAY, 8);
				starttime2.set(Calendar.HOUR_OF_DAY, 9);
				deadline.set(Calendar.HOUR_OF_DAY, 9);
				deadline2.set(Calendar.HOUR_OF_DAY, 9);
				deadline3.set(Calendar.HOUR_OF_DAY, 9);
				winline.set(Calendar.HOUR_OF_DAY, 9);
				winline2.set(Calendar.HOUR_OF_DAY, 9);

				starttime.set(Calendar.MINUTE, 55);
				starttime2.set(Calendar.MINUTE, 0);
				deadline.set(Calendar.MINUTE, 7);
				deadline2.set(Calendar.MINUTE, 5);
				deadline3.set(Calendar.MINUTE, 10);
				winline.set(Calendar.MINUTE, 13);
				winline2.set(Calendar.MINUTE, 12);

				starttime.set(Calendar.SECOND, 0);
				starttime2.set(Calendar.SECOND, 0);
				deadline.set(Calendar.SECOND, 0);
				deadline2.set(Calendar.SECOND, 0);
				deadline3.set(Calendar.SECOND, 0);
				winline.set(Calendar.SECOND, 0);
				winline2.set(Calendar.SECOND, 0);

				String nextterm = "";
				if(num == 65){
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					nextterm = sdf.format(calendar.getTime()) + "01";
				}else{
					int ttt = Integer.parseInt(termno) + 1;
					nextterm = "" + ttt;
					if(nextterm.length() < 8){
						nextterm = "0" + nextterm;
					}
				}
				if(num == 1){
					starttime.add(Calendar.DAY_OF_MONTH, -1);
					starttime.set(Calendar.HOUR_OF_DAY, 21);
					starttime.set(Calendar.MINUTE, 55);

				}else {
					starttime.add(Calendar.MINUTE, (num - 1) * 12);
					starttime2.add(Calendar.MINUTE, (num - 1) * 12);
					deadline.add(Calendar.MINUTE, (num - 1) * 12);
					deadline2.add(Calendar.MINUTE, (num - 1) * 12);
					deadline3.add(Calendar.MINUTE, (num - 1) * 12);
					winline.add(Calendar.MINUTE, (num - 1) * 12);
					winline2.add(Calendar.MINUTE, (num - 1) * 12);
				}

				String sql="insert into lotteryterm (lotteryid, termno,nextterm ,termstatus, winstatus, salestatus,starttime,starttime2,"
						+ "deadline,winline2,deadline2,deadline3,winresult,lotteryresult) values(1200001,'"+termno+"','"+nextterm+"',2, 8, 5,'"
						+ sdf2.format(starttime.getTime())+"','"+sdf2.format(starttime2.getTime())+"','"+sdf2.format(deadline.getTime())
						+"','"+sdf2.format(winline2.getTime())+"','"+sdf2.format(deadline2.getTime())+"','"+sdf2.format(deadline3.getTime())
						+"','1-&13|2-&6|3-&19|4-&78|5-&540|6-&90|7-&26|8-&9|9-&130|10-&1170|11-&65|12-&195','" + result +"');";
				pw.println(sql);
			}
		} catch(Exception ex) {}
		finally{
			input.close();
			pw.close();
		}
		System.out.println("the end");
		
	}


	
	//生成2010-04-24到- 2011年12-31
	public void createDLC(String fileName) throws FileNotFoundException, ParseException{
		PrintWriter pw=new PrintWriter(new File(fileName));
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-10"));
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE, 0);
		while(true)
		{
			if(calendar.get(Calendar.YEAR)==2010||calendar.get(Calendar.YEAR)==2011)
			{
					//剔除春节期间	    
					Calendar sdate=Calendar.getInstance();
					sdate.set(Calendar.YEAR,2011);
					sdate.set(Calendar.MONTH, 1);
					sdate.set(Calendar.DATE, 2);
					sdate.set(Calendar.HOUR_OF_DAY, 0);
					sdate.set(Calendar.MINUTE, 0);
					sdate.set(Calendar.SECOND, 0);
				
					Calendar edate=Calendar.getInstance();
					edate.set(Calendar.YEAR,2011);
					edate.set(Calendar.MONTH, 1);
					edate.set(Calendar.DATE, 9);
					edate.set(Calendar.HOUR_OF_DAY, 0);
					edate.set(Calendar.MINUTE, 0);
					edate.set(Calendar.SECOND, 0);
				
					if(calendar.after(sdate)&&calendar.before(edate))
					{
						calendar.add(Calendar.DAY_OF_MONTH,1);
						continue;
					}
					Calendar deadline=Calendar.getInstance();//系统止售
					Calendar deanline3=Calendar.getInstance();//官方止售时间
					Calendar starttime=Calendar.getInstance();//系统开售时间
					Calendar starttime2=Calendar.getInstance();//官方开售时间
					Calendar winline=Calendar.getInstance();//系统开奖时间
					Calendar winline2=Calendar.getInstance();//官方开奖时间
					Calendar deadline2=Calendar.getInstance();//合买止售时间
					deadline.setTimeInMillis(calendar.getTimeInMillis());
					deanline3.setTimeInMillis(calendar.getTimeInMillis());
					starttime.setTimeInMillis(calendar.getTimeInMillis());
					starttime2.setTimeInMillis(calendar.getTimeInMillis());
					winline.setTimeInMillis(calendar.getTimeInMillis());
					winline2.setTimeInMillis(calendar.getTimeInMillis());
					deadline.set(Calendar.MINUTE,7);
					deadline2.set(Calendar.MINUTE,5);
					deanline3.set(Calendar.MINUTE,10);
					starttime2.set(Calendar.MINUTE, 0);
					winline.set(Calendar.MINUTE,12);
					winline2.set(Calendar.MINUTE, 12);
					for(int i=1;i<=65;i++){
						String termno="";
						String nextterm="";
						if(i<10)
						    termno=sdf.format(calendar.getTime())+"0"+i;
						else
							termno=sdf.format(calendar.getTime())+i;
						if(i<9)
							nextterm=sdf.format(calendar.getTime())+"0"+(i+1);
						else
							nextterm=sdf.format(calendar.getTime())+(i+1);
						if(i==1)
						{
							starttime.add(Calendar.DAY_OF_MONTH,-1);
							starttime.set(Calendar.HOUR_OF_DAY, 21);
							starttime.set(Calendar.MINUTE,55);
						}else 
						{
							starttime.setTimeInMillis(deadline.getTimeInMillis());
							starttime.add(Calendar.MINUTE, -12);
						}
						
						if(i==65){
							calendar.add(Calendar.DAY_OF_MONTH,1);
							nextterm=sdf.format(calendar.getTime())+"01";
							if(sdf.format(calendar.getTime()).equals("110201"))
								nextterm="11020901";
						}
						String sql="insert into lotteryterm (lotteryid, termno,nextterm ,termstatus, winstatus, salestatus,starttime,starttime2,deadline,winline2,deadline2,deadline3,winresult) values(1200001,'"+termno+"','"+nextterm+"',0, 1, 4,'"+sdf2.format(starttime.getTime())+"','"+sdf2.format(starttime2.getTime())+"','"+sdf2.format(deadline.getTime())+"','"+sdf2.format(winline2.getTime())+"','"+sdf2.format(deadline2.getTime())+"','"+sdf2.format(deanline3.getTime())+"','1-&13|2-&6|3-&19|4-&78|5-&540|6-&90|7-&26|8-&9|9-&130|10-&1170|11-&65|12-&195');";
						pw.println(sql);
						deadline2.add(Calendar.MINUTE, 12);
						deadline.add(Calendar.MINUTE, 12);
						deanline3.add(Calendar.MINUTE, 12);
						starttime2.add(Calendar.MINUTE, 12);
						winline.add(Calendar.MINUTE, 12);
						winline2.add(Calendar.MINUTE, 12);
						System.err.println(sql);
					}
			}
			else
			{
				break;
			}
		}
		
		pw.close();
		System.out.println("the end");
	}
	public static void main(String[] args) throws ParseException, ClassNotFoundException, SQLException, LotteryException, IOException{
//
//		System.out.println();
//		System.out.println((byte)"N".charAt(0));
//			
//		String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		
		//初始化2011年数字彩彩期
		SqlCreate sql = new SqlCreate();
		sql.insertDLCLS("d:\\testdlc.sql");
		//sql.createDLC("d:\\test\\initsql\\DLC11-1-18.sql");
//		sql.updateWinSales("d:\\test\\initsql\\updateWinresult.sql");
//		sql.createDLC("");
		//sql.updateDLCLS("d:\\test\\initsql\\DLCLS_update_11.sql");
//		System.out.println("sssssssssssssssssss");
//		sql.inintLotteryStartTime(1000003,"09001");
//		System.out.println("=======================");
//		sql.inintLotteryStartTime(1000004,"09001");
//		System.out.println("=======================");
//		sql.inintLotteryStartTime(1000005,"09001");
	    //sql.createDlt2011(1000001, "d:\\test\\initsql\\dlt2011.sql");
		//sql.createDlt2011(1000005, "d:\\test\\initsql\\sxl2011.sql");
		//sql.createQxc2011("d:\\test\\initsql\\qxc2011.sql");
//		sql.createZC(1300003, "d:\\test\\initsql\\zc6_2011.sql");
//		sql.createZC(1300004, "d:\\test\\initsql\\jq4_2011.sql");
//		System.out.println("the end ");
		//sql.createPls2011(1000003, "d:\\test\\initsql\\pls2011.sql");
	//	sql.createPls2011(1000004, "d:\\test\\initsql\\plw2011.sql");
//		System.out.println("=========the end=========");
//		 sql.createSfc("10061", 1300001, "d:\\test\\initsql\\sfcnew-" + date + ".sql");
//		 sql.createSfc("10061", 1300002, "d:\\test\\initsql\\rxjnew-" + date + ".sql");

		// sql.createPls("10125", "2010-05-12", 1000003,
		// "d:\\test\\initsql\\plsnew-" + date + ".sql");
		// sql.createPlw("10125", "2010-05-12", 1000004,
		// "d:\\test\\initsql\\plwnew-" + date + ".sql");
		// sql.createDlt("10054", "2010-05-12", 1000001,
		// "d:\\test\\initsql\\dltnew-" + date + ".sql");
		// sql.createSxl("10054", "2010-05-12", 1000005,
		// "d:\\test\\initsql\\sxlnew-" + date + ".sql");
		// sql.createQxc("10054", "2010-05-11", 1000002,
		// "d:\\test\\initsql\\qxcnew-" + date + ".sql");
//		 sql.initMissCount(1000001);
//		 sql.initMissCount(1000002);
//		 sql.initMissCount(1000003);
//		 sql.initMissCount(1000004);
//		 sql.initMissCount(1000005);
//		sql.initMissCount(1200001);
//         System.err.println("");
		
		//开奖
//		sql.inputWinResult(1000001);
//		sql.inputWinResult(1000005);
//		sql.inputWinResult(1000003);
//		sql.inputWinResult(1000004);
//		sql.inputWinResult(1000002);
//		sql.inputWinResult(1300001);
//		sql.inputWinResult(1300002);
		
//		sql.inputSalesInfo();
		
//		long l = Timestamp.valueOf("2010-05-17 20:00:00").getTime();
//		l = l + 59*24*60*60*1000L;
//		Timestamp t = new Timestamp(l);
//		System.out.println(t);
	}
}
