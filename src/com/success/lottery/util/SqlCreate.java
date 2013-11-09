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
	//��ʼ��2011�곬������͸����  ��Ф��
	/**
	 * @param fileName �½�SQL�ļ�����·������
	 * @throws ParseException FileNotFoundException
	 */
    public void createDlt2011(int lotteryid,String fileName) throws ParseException, FileNotFoundException{
    	PrintWriter pw = new PrintWriter(new File(fileName));
		String sql = "";
	    //	PrintWriter pw = new PrintWriter(new File(fileName));
		int term = Integer.valueOf(11000);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-01"));//ֹͣ������
		calendar.add(Calendar.HOUR_OF_DAY, 19);
		calendar.add(Calendar.MINUTE, 30);
		term = term + 1;
		int nextTerm = term + 1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// calendar.add(Calendar.DAY_OF_YEAR, 1);
		while(calendar.get(Calendar.YEAR)==2011){
			Calendar startTime= Calendar.getInstance();//ϵͳ����ʱ��  1
			startTime.setTimeInMillis(calendar.getTimeInMillis());
			startTime.set(Calendar.HOUR_OF_DAY,19);
			startTime.set(Calendar.MINUTE, 30);
			Calendar startTime2= Calendar.getInstance();//datetime comment '�ٷ�����ʱ��',  2
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
			//��ʼ���۵�ʱ��
			
			Calendar deadLine = Calendar.getInstance();//datetime comment 'ϵͳֹ��ʱ��', X
			Calendar deadLine2 = Calendar.getInstance();//datetime comment '����ֹ��ʱ��', X
			Calendar deadLine3 = Calendar.getInstance();//datetime comment '�ٷ�ֹ��ʱ��',X
			Calendar winLine = Calendar.getInstance();//datetime comment 'ϵͳ����ʱ��', X
			Calendar changeLine = Calendar.getInstance();//datetime comment '�ò��ֹٷ����������նҽ�����ʱ��',
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
			//�ų����ڴӳ�Ϧ�����³����ǲ������ļ�2011-02-02��2011-02-08�������
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
     * ������ ������ 2011�������ݳ�ʼ��
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
			Calendar startTime= Calendar.getInstance();//ϵͳ����ʱ��  1
			startTime.setTimeInMillis(calendar.getTimeInMillis());
			startTime.set(Calendar.HOUR_OF_DAY,19);
			startTime.set(Calendar.MINUTE, 30);
			Calendar startTime2= Calendar.getInstance();//datetime comment '�ٷ�����ʱ��',  2
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
			
			//�ų����ڴӳ�Ϧ�����³����ǲ������ļ�2011-02-02��2011-02-08�������
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
			Calendar startTime= Calendar.getInstance();//ϵͳ����ʱ��  1
			startTime.setTimeInMillis(calendar.getTimeInMillis());
			startTime.set(Calendar.HOUR_OF_DAY, 19);
			startTime.set(Calendar.MINUTE,30);
			Calendar startTime2= Calendar.getInstance();//datetime comment '�ٷ�����ʱ��',  2
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
			//�ų����ڴӳ�Ϧ�����³����ǲ������ļ�2011-02-02��2011-02-08�������
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
	//ʤ�����ξ�2011��200��
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
	
    //��ʼ����©
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
	
	//�޸����ֲʵĿ���ʱ��
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
	//�޸Ľ����ĵĽ����
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
		
    //¼�뿪����Ϣ
	public void inputWinResult(int lotteryId) throws LotteryException{
		System.out.println("¼�뿪����Ϣ��ʼ");
		LotteryManagerInterf lotteryService = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
		switch(lotteryId){
			case 1000001:
				try{
//					String term = "10055";
//					String salesVolume = "62315353";
//					String jackpot = "17338573.67";
//					
//					//����͸�������
//					TreeMap<String, ArrayList<String>> lotteryResult = new TreeMap<String, ArrayList<String>>();
//					//����͸�������-ǰ��
//					ArrayList<String> list = new ArrayList<String>();
//					list.add("13");
//					list.add("14");
//					list.add("17");
//					list.add("27");
//					list.add("32");
//					lotteryResult.put("1", list);					
//					//����͸�������-����
//					ArrayList<String> list1 = new ArrayList<String>();
//					list1.add("02");
//					list1.add("10");
//					lotteryResult.put("2", list1);
//					
//					//����͸������
//					TreeMap<String, TreeMap<String, String[]>> winResult = new TreeMap<String, TreeMap<String, String[]>>();
//					//����͸������-һ�Ƚ�
//					TreeMap<String, String[]> map1 = new TreeMap<String, String[]>();
//					map1.put("A", new String[]{"1", "5000000"});
//					map1.put("B", new String[]{"1", "3000000"});
//					winResult.put("1", map1);
//					//����͸������-���Ƚ�
//					TreeMap<String, String[]> map2 = new TreeMap<String, String[]>();
//					map2.put("A", new String[]{"18", "146291"});
//					map2.put("B", new String[]{"4", "87774"});
//					winResult.put("2", map2);
//					//����͸������-���Ƚ�
//					TreeMap<String, String[]> map3 = new TreeMap<String, String[]>();
//					map3.put("A", new String[]{"70", "9372"});
//					map3.put("B", new String[]{"16", "5623"});
//					winResult.put("3", map3);
//					//����͸������-�ĵȽ�
//					TreeMap<String, String[]> map4 = new TreeMap<String, String[]>();
//					map4.put("A", new String[]{"169", "3000"});
//					map4.put("B", new String[]{"85", "1500"});
//					winResult.put("4", map4);
//					//����͸������-��Ƚ�
//					TreeMap<String, String[]> map5 = new TreeMap<String, String[]>();
//					map5.put("A", new String[]{"4297", "600"});
//					map5.put("B", new String[]{"841", "300"});
//					winResult.put("5", map5);
//					//����͸������-���Ƚ�
//					TreeMap<String, String[]> map6 = new TreeMap<String, String[]>();
//					map6.put("A", new String[]{"19002", "100"});
//					map6.put("B", new String[]{"4206", "50"});
//					winResult.put("6", map6);
//					//����͸������-�ߵȽ�
//					TreeMap<String, String[]> map7 = new TreeMap<String, String[]>();
//					map7.put("A", new String[]{"156134", "10"});
//					map7.put("B", new String[]{"34166", "5"});
//					winResult.put("7", map7);
//					//����͸������-�˵Ƚ�
//					TreeMap<String, String[]> map8 = new TreeMap<String, String[]>();
//					map8.put("A", new String[]{"1661025", "5"});
//					winResult.put("8", map8);
					
//					String term = "10056";
//					String salesVolume = "56490604";
//					String jackpot = "19842358.14";
//
//					//����͸�������
//					TreeMap<String, ArrayList<String>> lotteryResult = new TreeMap<String, ArrayList<String>>();
//					//����͸�������-ǰ��
//					ArrayList<String> list = new ArrayList<String>();
//					list.add("05");
//					list.add("09");
//					list.add("23");
//					list.add("25");
//					list.add("30");
//					lotteryResult.put("1", list);					
//					//����͸�������-����
//					ArrayList<String> list1 = new ArrayList<String>();
//					list1.add("09");
//					list1.add("12");
//					lotteryResult.put("2", list1);
//					
//					//����͸������
//					TreeMap<String, TreeMap<String, String[]>> winResult = new TreeMap<String, TreeMap<String, String[]>>();
//					//����͸������-һ�Ƚ�
//					TreeMap<String, String[]> map1 = new TreeMap<String, String[]>();
//					map1.put("A", new String[]{"1", "5000000"});
//					map1.put("B", new String[]{"0", "0"});
//					winResult.put("1", map1);
//					//����͸������-���Ƚ�
//					TreeMap<String, String[]> map2 = new TreeMap<String, String[]>();
//					map2.put("A", new String[]{"27", "65392"});
//					map2.put("B", new String[]{"6", "39235"});
//					winResult.put("2", map2);
//					//����͸������-���Ƚ�
//					TreeMap<String, String[]> map3 = new TreeMap<String, String[]>();
//					map3.put("A", new String[]{"56", "8309"});
//					map3.put("B", new String[]{"7", "4985"});
//					winResult.put("3", map3);
//					//����͸������-�ĵȽ�
//					TreeMap<String, String[]> map4 = new TreeMap<String, String[]>();
//					map4.put("A", new String[]{"197", "3000"});
//					map4.put("B", new String[]{"59", "1500"});
//					winResult.put("4", map4);
//					//����͸������-��Ƚ�
//					TreeMap<String, String[]> map5 = new TreeMap<String, String[]>();
//					map5.put("A", new String[]{"4305", "600"});
//					map5.put("B", new String[]{"1087", "300"});
//					winResult.put("5", map5);
//					//����͸������-���Ƚ�
//					TreeMap<String, String[]> map6 = new TreeMap<String, String[]>();
//					map6.put("A", new String[]{"14285", "100"});
//					map6.put("B", new String[]{"3305", "50"});
//					winResult.put("6", map6);
//					//����͸������-�ߵȽ�
//					TreeMap<String, String[]> map7 = new TreeMap<String, String[]>();
//					map7.put("A", new String[]{"206975", "10"});
//					map7.put("B", new String[]{"44693", "5"});
//					winResult.put("7", map7);
//					//����͸������-�˵Ƚ�
//					TreeMap<String, String[]> map8 = new TreeMap<String, String[]>();
//					map8.put("A", new String[]{"2039957", "5"});
//					winResult.put("8", map8);

					String term = "10057";
					String salesVolume = "18150134";
					String jackpot = "48320215.62";

					//����͸�������
					TreeMap<String, ArrayList<String>> lotteryResult = new TreeMap<String, ArrayList<String>>();
					//����͸�������-ǰ��
					ArrayList<String> list = new ArrayList<String>();
					list.add("05");
					list.add("09");
					list.add("23");
					list.add("25");
					list.add("30");
					lotteryResult.put("1", list);					
					//����͸�������-����
					ArrayList<String> list1 = new ArrayList<String>();
					list1.add("09");
					list1.add("12");
					lotteryResult.put("2", list1);
					
					//����͸������
					TreeMap<String, TreeMap<String, String[]>> winResult = new TreeMap<String, TreeMap<String, String[]>>();
					//����͸������-һ�Ƚ�
					TreeMap<String, String[]> map1 = new TreeMap<String, String[]>();
					map1.put("A", new String[]{"1", "5000000"});
					map1.put("B", new String[]{"0", "0"});
					winResult.put("1", map1);
					//����͸������-���Ƚ�
					TreeMap<String, String[]> map2 = new TreeMap<String, String[]>();
					map2.put("A", new String[]{"27", "65392"});
					map2.put("B", new String[]{"6", "39235"});
					winResult.put("2", map2);
					//����͸������-���Ƚ�
					TreeMap<String, String[]> map3 = new TreeMap<String, String[]>();
					map3.put("A", new String[]{"56", "8309"});
					map3.put("B", new String[]{"7", "4985"});
					winResult.put("3", map3);
					//����͸������-�ĵȽ�
					TreeMap<String, String[]> map4 = new TreeMap<String, String[]>();
					map4.put("A", new String[]{"197", "3000"});
					map4.put("B", new String[]{"59", "1500"});
					winResult.put("4", map4);
					//����͸������-��Ƚ�
					TreeMap<String, String[]> map5 = new TreeMap<String, String[]>();
					map5.put("A", new String[]{"4305", "600"});
					map5.put("B", new String[]{"1087", "300"});
					winResult.put("5", map5);
					//����͸������-���Ƚ�
					TreeMap<String, String[]> map6 = new TreeMap<String, String[]>();
					map6.put("A", new String[]{"14285", "100"});
					map6.put("B", new String[]{"3305", "50"});
					winResult.put("6", map6);
					//����͸������-�ߵȽ�
					TreeMap<String, String[]> map7 = new TreeMap<String, String[]>();
					map7.put("A", new String[]{"206975", "10"});
					map7.put("B", new String[]{"44693", "5"});
					winResult.put("7", map7);
					//����͸������-�˵Ƚ�
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
//					//���ǲʿ������
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "8");
//					lotteryResult.put("2", "1");
//					lotteryResult.put("3", "9");
//					lotteryResult.put("4", "5");
//					lotteryResult.put("5", "3");
//					lotteryResult.put("6", "9");
//					lotteryResult.put("7", "4");
//					
//					//���ǲʽ�����
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
//					//���ǲʿ������
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "1");
//					lotteryResult.put("2", "5");
//					lotteryResult.put("3", "7");
//					lotteryResult.put("4", "4");
//					lotteryResult.put("5", "1");
//					lotteryResult.put("6", "4");
//					lotteryResult.put("7", "3");
//					
//					//���ǲʽ�����
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
//					//���ǲʿ������
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "6");
//					lotteryResult.put("2", "5");
//					lotteryResult.put("3", "4");
//					lotteryResult.put("4", "0");
//					lotteryResult.put("5", "7");
//					lotteryResult.put("6", "2");
//					lotteryResult.put("7", "4");
//					
//					//���ǲʽ�����
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
					
					//���ǲʿ������
					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
					lotteryResult.put("1", "4");
					lotteryResult.put("2", "9");
					lotteryResult.put("3", "2");
					lotteryResult.put("4", "2");
					lotteryResult.put("5", "6");
					lotteryResult.put("6", "0");
					lotteryResult.put("7", "7");
					
					//���ǲʽ�����
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

					
//					//�������������
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "4");
//					lotteryResult.put("2", "8");
//					lotteryResult.put("3", "3");

//					//�������������
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "2");
//					lotteryResult.put("2", "7");
//					lotteryResult.put("3", "7");
					
//					//�������������
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "9");
//					lotteryResult.put("2", "1");
//					lotteryResult.put("3", "3");

//					//�������������
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "1");
//					lotteryResult.put("2", "0");
//					lotteryResult.put("3", "1");					
					
//					//�������������
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "3");
//					lotteryResult.put("2", "9");
//					lotteryResult.put("3", "5");

					//�������������
					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
					lotteryResult.put("1", "3");
					lotteryResult.put("2", "3");
					lotteryResult.put("3", "6");

					
//					//������������
//					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
//					//������������-ֱѡ
//					winResult.put("1", new String[]{"8655", "1000"});
//					//������������-����
//					winResult.put("2", new String[]{"0", "320"});
//					//������������-����
//					winResult.put("3", new String[]{"21277", "160"});
					
//					//������������
//					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
//					//������������-ֱѡ
//					winResult.put("1", new String[]{"6343", "1000"});
//					//������������-����
//					winResult.put("2", new String[]{"7025", "320"});
//					//������������-����
//					winResult.put("3", new String[]{"0", "160"});

//					//������������
//					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
//					//������������-ֱѡ
//					winResult.put("1", new String[]{"11764", "1000"});
//					//������������-����
//					winResult.put("2", new String[]{"0", "320"});
//					//������������-����
//					winResult.put("3", new String[]{"24574", "160"});

//					//������������
//					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
//					//������������-ֱѡ
//					winResult.put("1", new String[]{"7738", "1000"});
//					//������������-����
//					winResult.put("2", new String[]{"5858", "320"});
//					//������������-����
//					winResult.put("3", new String[]{"0", "160"});
					
//					//������������
//					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
//					//������������-ֱѡ
//					winResult.put("1", new String[]{"8345", "1000"});
//					//������������-����
//					winResult.put("2", new String[]{"0", "320"});
//					//������������-����
//					winResult.put("3", new String[]{"16783", "160"});

					//������������
					HashMap<String,String[]> winResult = new HashMap<String,String[]>();
					//������������-ֱѡ
					winResult.put("1", new String[]{"3854", "1000"});
					//������������-����
					winResult.put("2", new String[]{"5202", "320"});
					//������������-����
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
					
					
//					//�����忪�����
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "4");
//					lotteryResult.put("2", "8");
//					lotteryResult.put("3", "3");
//					lotteryResult.put("4", "6");
//					lotteryResult.put("5", "9");

//					//�����忪�����
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "2");
//					lotteryResult.put("2", "7");
//					lotteryResult.put("3", "7");
//					lotteryResult.put("4", "4");
//					lotteryResult.put("5", "2");

//					//�����忪�����
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "9");
//					lotteryResult.put("2", "1");
//					lotteryResult.put("3", "3");
//					lotteryResult.put("4", "9");
//					lotteryResult.put("5", "1");

//					//�����忪�����
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "1");
//					lotteryResult.put("2", "0");
//					lotteryResult.put("3", "1");
//					lotteryResult.put("4", "2");
//					lotteryResult.put("5", "1");

//					//�����忪�����
//					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
//					lotteryResult.put("1", "3");
//					lotteryResult.put("2", "9");
//					lotteryResult.put("3", "5");
//					lotteryResult.put("4", "0");
//					lotteryResult.put("5", "8");

					//�����忪�����
					TreeMap<String, String> lotteryResult = new TreeMap<String, String>();
					lotteryResult.put("1", "3");
					lotteryResult.put("2", "3");
					lotteryResult.put("3", "6");
					lotteryResult.put("4", "9");
					lotteryResult.put("5", "6");

					
					
//					//�����影����
//					HashMap<String,String> winResult = new HashMap<String,String>();
//					//�����影����-ע��
//					winResult.put("A", "29");
//					//�����影����-���
//					winResult.put("B", "100000");

					
//					//�����影����
//					HashMap<String,String> winResult = new HashMap<String,String>();
//					//�����影����-ע��
//					winResult.put("A", "23");
//					//�����影����-���
//					winResult.put("B", "100000");
					
//					//�����影����
//					HashMap<String,String> winResult = new HashMap<String,String>();
//					//�����影����-ע��
//					winResult.put("A", "65");
//					//�����影����-���
//					winResult.put("B", "100000");

//					//�����影����
//					HashMap<String,String> winResult = new HashMap<String,String>();
//					//�����影����-ע��
//					winResult.put("A", "35");
//					//�����影����-���
//					winResult.put("B", "100000");

//					//�����影����
//					HashMap<String,String> winResult = new HashMap<String,String>();
//					//�����影����-ע��
//					winResult.put("A", "41");
//					//�����影����-���
//					winResult.put("B", "100000");

					//�����影����
					HashMap<String,String> winResult = new HashMap<String,String>();
					//�����影����-ע��
					winResult.put("A", "18");
					//�����影����-���
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
//					//��Ф�ֿ������
//					ArrayList<String> lotteryResult = new ArrayList<String>();
//					lotteryResult.add("02");
//					lotteryResult.add("10");
//					
//					//��Ф�ֽ�����
//					HashMap<String,String> winResult = new HashMap<String,String>();
//					//��Ф�ֽ�����-ע��
//					winResult.put("A", "3961");
//					//��Ф�ֽ�����-���
//					winResult.put("B", "60");
					
					String term = "10056";
					String salesVolume = "1212734";
					String jackpot = "0";
					
					//��Ф�ֿ������
					ArrayList<String> lotteryResult = new ArrayList<String>();
					lotteryResult.add("09");
					lotteryResult.add("12");
					
					//��Ф�ֽ�����
					HashMap<String,String> winResult = new HashMap<String,String>();
					//��Ф�ֽ�����-ע��
					winResult.put("A", "12065");
					//��Ф�ֽ�����-���
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
//					//���ʤ���ʿ������
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
//					//���ʤ���ʽ�����
//					TreeMap<String,String[]> winResult = new TreeMap<String,String[]>();
//					//һ�Ƚ�
//					winResult.put("1", new String[]{"16", "711441"});
//					//���Ƚ�
//					winResult.put("2", new String[]{"498", "9796"});
										
					String term = "10049";
					String salesVolume = "36393924";
					String jackpot = "0";
					
					//���ʤ���ʿ������
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

					
					//���ʤ���ʽ�����
					TreeMap<String,String[]> winResult = new TreeMap<String,String[]>();
					//һ�Ƚ�
					winResult.put("1", new String[]{"411", "39670"});
					//���Ƚ�
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
//					//�����ѡ�ſ������
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
//					//�����ѡ�Ž�����
//					Map<String, String> winResult = new HashMap<String, String>();
//					//ע��
//					winResult.put("A", "5247");
//					//���
//					winResult.put("B", "2658");
					
					String term = "10049";
					String salesVolume = "23369902";
					String jackpot = "0";
					
					//�����ѡ�ſ������
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

					
					//�����ѡ�Ž�����
					Map<String, String> winResult = new HashMap<String, String>();
					//ע��
					winResult.put("A", "49592");
					//���
					winResult.put("B", "301");
					
					lotteryService.inputArbitryNineWinInfo(term, lotteryResult, salesVolume, jackpot, winResult);
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			default:
				break;
		}
		System.out.println("¼�뿪����Ϣ���");
	}

	public void inputSalesInfo() throws ClassNotFoundException, SQLException{
		System.out.println("¼�����������Ϣ��ʼ");

//		String term = "10055";
//		Map<Integer, Map<String,String>> gameMap = new HashMap<Integer, Map<String,String>>();
//		
//		Map<String, String> map1 = new HashMap<String, String>();
//		map1.put("A", "��ʿ");
//		map1.put("B", "��˹�����");
//		map1.put("C", "������");
//		map1.put("D", "2010-06-02");
//		gameMap.put(Integer.valueOf(1), map1);
//
//		Map<String, String> map2 = new HashMap<String, String>();
//		map2.put("A", "����");
//		map2.put("B", "����");
//		map2.put("C", "������");
//		map2.put("D", "2010-06-02");
//		gameMap.put(Integer.valueOf(2), map2);
//
//		Map<String, String> map3 = new HashMap<String, String>();
//		map3.put("A", "������");
//		map3.put("B", "����¡");
//		map3.put("C", "������");
//		map3.put("D", "2010-06-02");
//		gameMap.put(Integer.valueOf(3), map3);
//		
//		Map<String, String> map4 = new HashMap<String, String>();
//		map4.put("A", "Ų��");
//		map4.put("B", "�ڿ���");
//		map4.put("C", "������");
//		map4.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(4), map4);
//
//		Map<String, String> map5 = new HashMap<String, String>();
//		map5.put("A", "�׶���˹");
//		map5.put("B", "���");
//		map5.put("C", "������");
//		map5.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(5), map5);
//
//		Map<String, String> map6 = new HashMap<String, String>();
//		map6.put("A", "��������");
//		map6.put("B", "�����");
//		map6.put("C", "������");
//		map6.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(6), map6);
//
//		Map<String, String> map7 = new HashMap<String, String>();
//		map7.put("A", "ϣ��");
//		map7.put("B", "������");
//		map7.put("C", "������");
//		map7.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(7), map7);
//
//		Map<String, String> map8 = new HashMap<String, String>();
//		map8.put("A", "����ά��");
//		map8.put("B", "����");
//		map8.put("C", "������");
//		map8.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(8), map8);
//
//		Map<String, String> map9 = new HashMap<String, String>();
//		map9.put("A", "��³����");
//		map9.put("B", "�������˹����");
//		map9.put("C", "�ͼ�");
//		map9.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(9), map9);
//
//		Map<String, String> map10 = new HashMap<String, String>();
//		map10.put("A", "�����ɾ���");
//		map10.put("B", "��������");
//		map10.put("C", "�ͼ�");
//		map10.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(10), map10);
//
//		Map<String, String> map11 = new HashMap<String, String>();
//		map11.put("A", "������");
//		map11.put("B", "����");
//		map11.put("C", "�ͼ�");
//		map11.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(11), map11);
//
//		Map<String, String> map12 = new HashMap<String, String>();
//		map12.put("A", "��³����");
//		map12.put("B", "ɣ��˹");
//		map12.put("C", "�ͼ�");
//		map12.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(12), map12);
//
//		Map<String, String> map13 = new HashMap<String, String>();
//		map13.put("A", "������˹");
//		map13.put("B", "ʥ����");
//		map13.put("C", "�ͼ�");
//		map13.put("D", "2010-06-03");
//		gameMap.put(Integer.valueOf(13), map13);
//
//		Map<String, String> map14 = new HashMap<String, String>();
//		map14.put("A", "����÷��˹");
//		map14.put("B", "�����Ÿ�");
//		map14.put("C", "�ͼ�");
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
//		map1.put("A", "������");
//		map1.put("B", "����");
//		map1.put("C", "������");
//		map1.put("D", "2010-06-04");
//		gameMap.put(Integer.valueOf(1), map1);
//
//		Map<String, String> map2 = new HashMap<String, String>();
//		map2.put("A", "�����");
//		map2.put("B", "ī����");
//		map2.put("C", "������");
//		map2.put("D", "2010-06-04");
//		gameMap.put(Integer.valueOf(2), map2);
//
//		Map<String, String> map3 = new HashMap<String, String>();
//		map3.put("A", "�¹�");
//		map3.put("B", "����");
//		map3.put("C", "������");
//		map3.put("D", "2010-06-04");
//		gameMap.put(Integer.valueOf(3), map3);
//		
//		Map<String, String> map4 = new HashMap<String, String>();
//		map4.put("A", "�ձ�");
//		map4.put("B", "���ص���");
//		map4.put("C", "������");
//		map4.put("D", "2010-06-04");
//		gameMap.put(Integer.valueOf(4), map4);
//
//		Map<String, String> map5 = new HashMap<String, String>();
//		map5.put("A", "����");
//		map5.put("B", "�й�");
//		map5.put("C", "������");
//		map5.put("D", "2010-06-05");
//		gameMap.put(Integer.valueOf(5), map5);
//
//		Map<String, String> map6 = new HashMap<String, String>();
//		map6.put("A", "¬ɭ��");
//		map6.put("B", "����Ⱥ��");
//		map6.put("C", "������");
//		map6.put("D", "2010-06-05");
//		gameMap.put(Integer.valueOf(6), map6);
//
//		Map<String, String> map7 = new HashMap<String, String>();
//		map7.put("A", "˹��������");
//		map7.put("B", "������");
//		map7.put("C", "������");
//		map7.put("D", "2010-06-05");
//		gameMap.put(Integer.valueOf(7), map7);
//
//		Map<String, String> map8 = new HashMap<String, String>();
//		map8.put("A", "����");
//		map8.put("B", "������");
//		map8.put("C", "������");
//		map8.put("D", "2010-06-05");
//		gameMap.put(Integer.valueOf(8), map8);
//
//		Map<String, String> map9 = new HashMap<String, String>();
//		map9.put("A", "�Ϸ�");
//		map9.put("B", "����");
//		map9.put("C", "������");
//		map9.put("D", "2010-06-05");
//		gameMap.put(Integer.valueOf(9), map9);
//
//		Map<String, String> map10 = new HashMap<String, String>();
//		map10.put("A", "��ʿ");
//		map10.put("B", "�����");
//		map10.put("C", "������");
//		map10.put("D", "2010-06-06");
//		gameMap.put(Integer.valueOf(10), map10);
//
//		Map<String, String> map11 = new HashMap<String, String>();
//		map11.put("A", "���ֵٰ�");
//		map11.put("B", "��������");
//		map11.put("C", "�ͼ�");
//		map11.put("D", "2010-06-04");
//		gameMap.put(Integer.valueOf(11), map11);
//
//		Map<String, String> map12 = new HashMap<String, String>();
//		map12.put("A", "�����װ�");
//		map12.put("B", "�����޾���");
//		map12.put("C", "�ͼ�");
//		map12.put("D", "2010-06-04");
//		gameMap.put(Integer.valueOf(12), map12);
//
//		Map<String, String> map13 = new HashMap<String, String>();
//		map13.put("A", "����");
//		map13.put("B", "��³������");
//		map13.put("C", "�ͼ�");
//		map13.put("D", "2010-06-06");
//		gameMap.put(Integer.valueOf(13), map13);
//
//		Map<String, String> map14 = new HashMap<String, String>();
//		map14.put("A", "�����Ÿ�");
//		map14.put("B", "������˹");
//		map14.put("C", "�ͼ�");
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
//		map1.put("A", "����������");
//		map1.put("B", "ά����");
//		map1.put("C", "����");
//		map1.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(1), map1);
//
//		Map<String, String> map2 = new HashMap<String, String>();
//		map2.put("A", "�ʼұ���˹");
//		map2.put("B", "Ŭ������");
//		map2.put("C", "����");
//		map2.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(2), map2);
//
//		Map<String, String> map3 = new HashMap<String, String>();
//		map3.put("A", "����˹");
//		map3.put("B", "�ʼ����");
//		map3.put("C", "����");
//		map3.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(3), map3);
//		
//		Map<String, String> map4 = new HashMap<String, String>();
//		map4.put("A", "��˹��¡");
//		map4.put("B", "��������");
//		map4.put("C", "����");
//		map4.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(4), map4);
//
//		Map<String, String> map5 = new HashMap<String, String>();
//		map5.put("A", "������");
//		map5.put("B", "�¶�����");
//		map5.put("C", "����");
//		map5.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(5), map5);
//
//		Map<String, String> map6 = new HashMap<String, String>();
//		map6.put("A", "ά������B��");
//		map6.put("B", "Τ˹��");
//		map6.put("C", "����");
//		map6.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(6), map6);
//
//		Map<String, String> map7 = new HashMap<String, String>();
//		map7.put("A", "��������");
//		map7.put("B", "�տ�˹");
//		map7.put("C", "����");
//		map7.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(7), map7);
//
//		Map<String, String> map8 = new HashMap<String, String>();
//		map8.put("A", "���п�ŵ");
//		map8.put("B", "�ƶ�����");
//		map8.put("C", "����");
//		map8.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(8), map8);
//
//		Map<String, String> map9 = new HashMap<String, String>();
//		map9.put("A", "������");
//		map9.put("B", "��������");
//		map9.put("C", "����");
//		map9.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(9), map9);
//
//		Map<String, String> map10 = new HashMap<String, String>();
//		map10.put("A", "�ʼ�������");
//		map10.put("B", "������");
//		map10.put("C", "����");
//		map10.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(10), map10);
//
//		Map<String, String> map11 = new HashMap<String, String>();
//		map11.put("A", "��˹������˹");
//		map11.put("B", "������");
//		map11.put("C", "����");
//		map11.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(11), map11);
//
//		Map<String, String> map12 = new HashMap<String, String>();
//		map12.put("A", "��������");
//		map12.put("B", "���ֵٰ�");
//		map12.put("C", "�ͼ�");
//		map12.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(12), map12);
//
//		Map<String, String> map13 = new HashMap<String, String>();
//		map13.put("A", "ɣ��˹");
//		map13.put("B", "��˹�ƴ�٤��");
//		map13.put("C", "�ͼ�");
//		map13.put("D", "2010-06-07");
//		gameMap.put(Integer.valueOf(13), map13);
//
//		Map<String, String> map14 = new HashMap<String, String>();
//		map14.put("A", "ʥ����");
//		map14.put("B", "�����װ�");
//		map14.put("C", "�ͼ�");
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
//		map1.put("A", "�Ϸ�");
//		map1.put("B", "ī����");
//		map1.put("C", "���籭");
//		map1.put("D", "2010-06-11");
//		gameMap.put(Integer.valueOf(1), map1);
//
//		Map<String, String> map2 = new HashMap<String, String>();
//		map2.put("A", "������");
//		map2.put("B", "����");
//		map2.put("C", "���籭");
//		map2.put("D", "2010-06-12");
//		gameMap.put(Integer.valueOf(2), map2);
//
//		Map<String, String> map3 = new HashMap<String, String>();
//		map3.put("A", "����");
//		map3.put("B", "ϣ��");
//		map3.put("C", "���籭");
//		map3.put("D", "2010-06-12");
//		gameMap.put(Integer.valueOf(3), map3);
//		
//		Map<String, String> map4 = new HashMap<String, String>();
//		map4.put("A", "����͢");
//		map4.put("B", "��������");
//		map4.put("C", "���籭");
//		map4.put("D", "2010-06-12");
//		gameMap.put(Integer.valueOf(4), map4);
//
//		Map<String, String> map5 = new HashMap<String, String>();
//		map5.put("A", "Ӣ����");
//		map5.put("B", "����");
//		map5.put("C", "���籭");
//		map5.put("D", "2010-06-13");
//		gameMap.put(Integer.valueOf(5), map5);
//
//		Map<String, String> map6 = new HashMap<String, String>();
//		map6.put("A", "����������");
//		map6.put("B", "˹��������");
//		map6.put("C", "���籭");
//		map6.put("D", "2010-06-13");
//		gameMap.put(Integer.valueOf(6), map6);
//
//		Map<String, String> map7 = new HashMap<String, String>();
//		map7.put("A", "����ά��");
//		map7.put("B", "����");
//		map7.put("C", "���籭");
//		map7.put("D", "2010-06-14");
//		gameMap.put(Integer.valueOf(7), map7);
//		
//		Map<String, String> map8 = new HashMap<String, String>();
//		map8.put("A", "�¹�");
//		map8.put("B", "�Ĵ�����");
//		map8.put("C", "���籭");
//		map8.put("D", "2010-06-14");
//		gameMap.put(Integer.valueOf(8), map8);
//
//		Map<String, String> map9 = new HashMap<String, String>();
//		map9.put("A", "����");
//		map9.put("B", "����");
//		map9.put("C", "���籭");
//		map9.put("D", "2010-06-14");
//		gameMap.put(Integer.valueOf(9), map9);
//
//		Map<String, String> map10 = new HashMap<String, String>();
//		map10.put("A", "�ձ�");
//		map10.put("B", "����¡");
//		map10.put("C", "���籭");
//		map10.put("D", "2010-06-14");
//		gameMap.put(Integer.valueOf(10), map10);
//
//		Map<String, String> map11 = new HashMap<String, String>();
//		map11.put("A", "�����");
//		map11.put("B", "������");
//		map11.put("C", "���籭");
//		map11.put("D", "2010-06-15");
//		gameMap.put(Integer.valueOf(11), map11);
//
//		Map<String, String> map12 = new HashMap<String, String>();
//		map12.put("A", "������");
//		map12.put("B", "˹�工��");
//		map12.put("C", "���籭");
//		map12.put("D", "2010-06-15");
//		gameMap.put(Integer.valueOf(12), map12);
//
//		Map<String, String> map13 = new HashMap<String, String>();
//		map13.put("A", "���ص���");
//		map13.put("B", "������");
//		map13.put("C", "���籭");
//		map13.put("D", "2010-06-15");
//		gameMap.put(Integer.valueOf(13), map13);
//
//		Map<String, String> map14 = new HashMap<String, String>();
//		map14.put("A", "����");
//		map14.put("B", "����");
//		map14.put("C", "���籭");
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
//		map1.put("A", "ϣ��");
//		map1.put("B", "��������");
//		map1.put("C", "���籭");
//		map1.put("D", "2010-06-17");
//		gameMap.put(Integer.valueOf(1), map1);
//
//		Map<String, String> map2 = new HashMap<String, String>();
//		map2.put("A", "����");
//		map2.put("B", "ī����");
//		map2.put("C", "���籭");
//		map2.put("D", "2010-06-18");
//		gameMap.put(Integer.valueOf(2), map2);
//
//		Map<String, String> map3 = new HashMap<String, String>();
//		map3.put("A", "�¹�");
//		map3.put("B", "����ά��");
//		map3.put("C", "���籭");
//		map3.put("D", "2010-06-18");
//		gameMap.put(Integer.valueOf(3), map3);
//		
//		Map<String, String> map4 = new HashMap<String, String>();
//		map4.put("A", "˹��������");
//		map4.put("B", "����");
//		map4.put("C", "���籭");
//		map4.put("D", "2010-06-18");
//		gameMap.put(Integer.valueOf(4), map4);
//
//		Map<String, String> map5 = new HashMap<String, String>();
//		map5.put("A", "Ӣ����");
//		map5.put("B", "����������");
//		map5.put("C", "���籭");
//		map5.put("D", "2010-06-19");
//		gameMap.put(Integer.valueOf(5), map5);
//
//		Map<String, String> map6 = new HashMap<String, String>();
//		map6.put("A", "����");
//		map6.put("B", "�ձ�");
//		map6.put("C", "���籭");
//		map6.put("D", "2010-06-19");
//		gameMap.put(Integer.valueOf(6), map6);
//
//		Map<String, String> map7 = new HashMap<String, String>();
//		map7.put("A", "����");
//		map7.put("B", "�Ĵ�����");
//		map7.put("C", "���籭");
//		map7.put("D", "2010-06-19");
//		gameMap.put(Integer.valueOf(7), map7);
//		
//		Map<String, String> map8 = new HashMap<String, String>();
//		map8.put("A", "����¡");
//		map8.put("B", "����");
//		map8.put("C", "���籭");
//		map8.put("D", "2010-06-20");
//		gameMap.put(Integer.valueOf(8), map8);
//
//		Map<String, String> map9 = new HashMap<String, String>();
//		map9.put("A", "˹�工��");
//		map9.put("B", "������");
//		map9.put("C", "���籭");
//		map9.put("D", "2010-06-20");
//		gameMap.put(Integer.valueOf(9), map9);
//
//		Map<String, String> map10 = new HashMap<String, String>();
//		map10.put("A", "�����");
//		map10.put("B", "������");
//		map10.put("C", "���籭");
//		map10.put("D", "2010-06-20");
//		gameMap.put(Integer.valueOf(10), map10);
//
//		Map<String, String> map11 = new HashMap<String, String>();
//		map11.put("A", "����");
//		map11.put("B", "���ص���");
//		map11.put("C", "���籭");
//		map11.put("D", "2010-06-21");
//		gameMap.put(Integer.valueOf(11), map11);
//
//		Map<String, String> map12 = new HashMap<String, String>();
//		map12.put("A", "������");
//		map12.put("B", "����");
//		map12.put("C", "���籭");
//		map12.put("D", "2010-06-21");
//		gameMap.put(Integer.valueOf(12), map12);
//
//		Map<String, String> map13 = new HashMap<String, String>();
//		map13.put("A", "����");
//		map13.put("B", "��ʿ");
//		map13.put("C", "���籭");
//		map13.put("D", "2010-06-21");
//		gameMap.put(Integer.valueOf(13), map13);
//
//		Map<String, String> map14 = new HashMap<String, String>();
//		map14.put("A", "������");
//		map14.put("B", "�鶼��˹");
//		map14.put("C", "���籭");
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
		map1.put("A", "ϣ��");
		map1.put("B", "����͢");
		map1.put("C", "���籭");
		map1.put("D", "2010-06-23");
		gameMap.put(Integer.valueOf(1), map1);

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("A", "��������");
		map2.put("B", "����");
		map2.put("C", "���籭");
		map2.put("D", "2010-06-23");
		gameMap.put(Integer.valueOf(2), map2);

		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("A", "˹��������");
		map3.put("B", "Ӣ����");
		map3.put("C", "���籭");
		map3.put("D", "2010-06-23");
		gameMap.put(Integer.valueOf(3), map3);
		
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("A", "����");
		map4.put("B", "����������");
		map4.put("C", "���籭");
		map4.put("D", "2010-06-23");
		gameMap.put(Integer.valueOf(4), map4);

		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("A", "�Ĵ�����");
		map5.put("B", "����ά��");
		map5.put("C", "���籭");
		map5.put("D", "2010-06-24");
		gameMap.put(Integer.valueOf(5), map5);

		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("A", "����");
		map6.put("B", "�¹�");
		map6.put("C", "���籭");
		map6.put("D", "2010-06-24");
		gameMap.put(Integer.valueOf(6), map6);

		Map<String, String> map7 = new HashMap<String, String>();
		map7.put("A", "������");
		map7.put("B", "������");
		map7.put("C", "���籭");
		map7.put("D", "2010-06-24");
		gameMap.put(Integer.valueOf(7), map7);
		
		Map<String, String> map8 = new HashMap<String, String>();
		map8.put("A", "˹�工��");
		map8.put("B", "�����");
		map8.put("C", "���籭");
		map8.put("D", "2010-06-24");
		gameMap.put(Integer.valueOf(8), map8);

		Map<String, String> map9 = new HashMap<String, String>();
		map9.put("A", "����¡");
		map9.put("B", "����");
		map9.put("C", "���籭");
		map9.put("D", "2010-06-25");
		gameMap.put(Integer.valueOf(9), map9);

		Map<String, String> map10 = new HashMap<String, String>();
		map10.put("A", "����");
		map10.put("B", "�ձ�");
		map10.put("C", "���籭");
		map10.put("D", "2010-06-25");
		gameMap.put(Integer.valueOf(10), map10);

		Map<String, String> map11 = new HashMap<String, String>();
		map11.put("A", "����");
		map11.put("B", "���ص���");
		map11.put("C", "���籭");
		map11.put("D", "2010-06-25");
		gameMap.put(Integer.valueOf(11), map11);

		Map<String, String> map12 = new HashMap<String, String>();
		map12.put("A", "������");
		map12.put("B", "����");
		map12.put("C", "���籭");
		map12.put("D", "2010-06-25");
		gameMap.put(Integer.valueOf(12), map12);

		Map<String, String> map13 = new HashMap<String, String>();
		map13.put("A", "����");
		map13.put("B", "������");
		map13.put("C", "���籭");
		map13.put("D", "2010-06-25");
		gameMap.put(Integer.valueOf(13), map13);

		Map<String, String> map14 = new HashMap<String, String>();
		map14.put("A", "��ʿ");
		map14.put("B", "�鶼��˹");
		map14.put("C", "���籭");
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
		System.out.println("¼�����������Ϣ���������أ�" + stmt.executeUpdate(sql));
		stmt.close();
		con.close();
	}
	//�޸Ķ��ֲ���ʷ���ݿ������
	public void updateDLCLS(String fileName) throws IOException{
		PrintWriter pw = new PrintWriter(new File(fileName));
		File file=new File("C:\\Users\\aaron\\Desktop\\���ֲ�A.txt"); 
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
				Calendar deadline=Calendar.getInstance();//ϵͳֹ��
				Calendar deadline3=Calendar.getInstance();//�ٷ�ֹ��ʱ��
				Calendar starttime=Calendar.getInstance();//ϵͳ����ʱ��
				Calendar starttime2=Calendar.getInstance();//�ٷ�����ʱ��
				Calendar winline=Calendar.getInstance();//ϵͳ����ʱ��
				Calendar winline2=Calendar.getInstance();//�ٷ�����ʱ��
				Calendar deadline2=Calendar.getInstance();//����ֹ��ʱ��
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


	
	//����2010-04-24��- 2011��12-31
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
					//�޳������ڼ�	    
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
					Calendar deadline=Calendar.getInstance();//ϵͳֹ��
					Calendar deanline3=Calendar.getInstance();//�ٷ�ֹ��ʱ��
					Calendar starttime=Calendar.getInstance();//ϵͳ����ʱ��
					Calendar starttime2=Calendar.getInstance();//�ٷ�����ʱ��
					Calendar winline=Calendar.getInstance();//ϵͳ����ʱ��
					Calendar winline2=Calendar.getInstance();//�ٷ�����ʱ��
					Calendar deadline2=Calendar.getInstance();//����ֹ��ʱ��
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
		
		//��ʼ��2011�����ֲʲ���
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
		
		//����
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
