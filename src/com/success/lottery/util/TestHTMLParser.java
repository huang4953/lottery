package com.success.lottery.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.htmlparser.Parser;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;

import com.success.lottery.term.domain.LotteryTermModel;
public class TestHTMLParser{

	public static Date afterNDays(Timestamp time, int n){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time.getTime());
		c.add(Calendar.DATE, n);
		Date d2 = c.getTime();
		return d2;
	}

	public static String replaceHtml(String html){
		String regEx = "<.+?>"; // ��ʾ��ǩ
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 
	 * @param url
	 *            ���ӵ���URL
	 * @param lotteryid
	 *            ��Ʊ����
	 * @param termNo
	 *            ����
	 * @param winLine
	 *            �ò��ֹٷ������Ŀ���ʱ��
	 * @param deadLine
	 *            �ò��ֹٷ���������ʱ��
	 * @param lotteryResult
	 *            �н�����
	 * @return
	 * @throws ParserException
	 */
	public LotteryTermModel htmlCreate(String url, String lotteryid, String termNo, Timestamp winLine, Timestamp deadLine, String lotteryResult, String nexttermno, String status, int afterday) throws ParserException{
		System.err.println(url);
		Parser parser = new Parser(url);
		HtmlPage visitor = new HtmlPage(parser);
		LotteryTermModel lottery = new LotteryTermModel();
		lottery.setLotteryId(new Integer(lotteryid));
		lottery.setTermNo(termNo);
		// lottery.setWinLine(winLine);
		// lottery.setDeadLine(deadLine);
		lottery.setLotteryResult(lotteryResult.replaceAll(",", ""));
		Date change = afterNDays(winLine, afterday);
		lottery.setChangeLine(new Timestamp(change.getTime()));
		lottery.setTermStatus(2);
		lottery.setWinStatus(8);
		lottery.setSaleStatus(5);
		lottery.setNextTerm(nexttermno);
		String winResult = "";
		// ʮһ�˶�𿪽�������Ҫ���ñ���
//		 parser.setEncoding("gb2312");
		try{
			parser.visitAllNodesWith(visitor);
		}catch(Exception e){
			// Ϊ��ɽ�������ʮһ�˶���������
			e.printStackTrace();
			parser.reset();
			parser.visitAllNodesWith(visitor);
		}
		Map<Integer, Map<String, String>> map = new HashMap<Integer, Map<String, String>>();
		String saleinfo = "";
		int xj = 1;
		TableTag[] tables = visitor.getTables();
		for(TableTag table : tables){
			TableRow rows[] = table.getRows();
			for(TableRow row : rows){
				TableColumn columns[] = row.getColumns();
				for(int j = 0; j < columns.length; j++){
					TableColumn column = columns[j];
					String child = column.getChildrenHTML();

					if(lotteryid.equals("1300001") || lotteryid.equals("1300002")){
						String sfc1 = column.getText();
						if(sfc1.indexOf("VS") == -1){
						}else{
							
							//String sfc2 = sfc1.substring(sfc1.indexOf("title")).replaceAll("&nbsp;", "").replaceAll("\"", "").trim();
							
							String title = column.getAttribute("title").replaceAll("&nbsp;", "");
							//System.out.println(title);
							if("-VS-".equalsIgnoreCase(title)){
								//System.out.println("sadfsdafasdfasdfas");
							} else{
								Map<String, String> inmap = new HashMap<String, String>();
								inmap.put("A", title.split("VS")[0].trim());
								inmap.put("B", title.split("VS")[1].trim());
								inmap.put("C", "");
								inmap.put("D", "");
								map.put(Integer.valueOf(xj), inmap);
							}
							xj++;
						}
					}
					if(lotteryid.equals("1300003")||lotteryid.equals("1300004")){
						String sfc1 = column.getText();
						if(sfc1.indexOf("VS") == -1){
						}else{
							
							//String sfc2 = sfc1.substring(sfc1.indexOf("title")).replaceAll("&nbsp;", "").replaceAll("\"", "").trim();
							
							String title = column.getAttribute("title").replaceAll("&nbsp;", "");
							System.err.println("title="+title);
							//System.out.println(title);
							if("-VS-".equalsIgnoreCase(title)){
								//System.out.println("sadfsdafasdfasdfas");
							} else{
								Map<String, String> inmap = new HashMap<String, String>();
								inmap.put("A", title.split("VS")[0].trim());
								inmap.put("B", title.split("VS")[1].trim());
								inmap.put("C", "");
								inmap.put("D", "");
								map.put(Integer.valueOf(xj), inmap);
							}
							xj++;
						}
					}
					
					// ����͸��ʼ
					if(lotteryid.equals("1000001") || lotteryid.equals("1000005")){
						if(child.indexOf("��������") == -1){
						}else{
							String salesVolume = replaceHtml(child.substring(child.indexOf("��������"), child.indexOf("12ѡ2������")).replaceAll("&nbsp;", "")).substring(5);
							String jackpot = replaceHtml(child.substring(child.indexOf("���ع��棺"))).substring(5);
							String xuan2 = child.substring(child.indexOf("12ѡ2������"), child.indexOf("���ع��棺")).replaceAll("&nbsp;", "");
							if(lotteryid.equals("1000001")){
								lottery.setSalesVolume(salesVolume.replaceAll("Ԫ", "").replaceAll("&nbsp;", ""));
								lottery.setJackpot(jackpot.replaceAll(",", "").replaceAll("Ԫ", "").replaceAll("&nbsp;", ""));
							}else if(lotteryid.equals("1000005")){
								lottery.setSalesVolume(replaceHtml(xuan2.replaceAll(",", "").substring(7).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "").replaceAll("Ԫ", "")));
							}
						}
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							;
							int x = test.indexOf("����˳��");
							if(x == -1){
							}else{
								String lotteryResultPlus = test.substring(x);
								TableColumn columnshun = columns[j + 1];
								lottery.setLotteryResultPlus(columnshun.getFirstChild().getText().replaceAll(" ", ""));
								if(lotteryid.equals("1000005")){
									lottery.setLotteryResult(lotteryResult.substring(lotteryResult.indexOf("|") + 1).replaceAll(",", ""));
									if(StringUtils.isNotBlank(columnshun.getFirstChild().getText().trim()) && !columnshun.getFirstChild().getText().trim().equals("|")){
										lottery.setLotteryResultPlus(columnshun.getFirstChild().getText().trim().substring(lotteryResult.indexOf("|") + 1).replaceAll(" ", ""));
									}
								}
							}
						}
					}
					// ����͸����
					/**
					 * ������
					 */
					if(lotteryid.equals("1000003")){
						if(child.indexOf("��������") == -1){
						}else{
							String salesVolume = replaceHtml(child.substring(child.indexOf("��������") + 5));
							lottery.setSalesVolume(salesVolume);
						}
					}
					/**
					 * ������
					 */
					if(lotteryid.equals("1000004")){
						if(child.indexOf("��������") == -1){
						}else{
							String salesVolume = replaceHtml(child.substring(child.indexOf("��������") + 5));
							lottery.setSalesVolume(salesVolume);
						}
					}
					/**
					 * ���ǲ�
					 */
					if(lotteryid.equals("1000002")){
						if(child.indexOf("��������") == -1){
						}else{
							String salesVolume = replaceHtml(child.substring(child.indexOf("��������"), child.indexOf("���ع���")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")).substring(5);
							lottery.setSalesVolume(salesVolume.replaceAll("&nbsp;", ""));
							String jackpot = replaceHtml(child.substring(child.indexOf("���ع��棺")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")).substring(5);
							lottery.setJackpot(jackpot.replaceAll(",", ""));
						}
					}
					
					/*
					 * ������ȫ��
					 */
					if(lotteryid.equals("1300003")||lotteryid.equals("1300004"))
					{
						lottery.setLotteryResult(lottery.getLotteryResult().replaceAll("\\*", "9"));
						if(child.indexOf("��������") == -1){
						}else{
							String salesVolume = replaceHtml(child.substring(child.indexOf("��������"), child.indexOf("���ع���")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")).substring(5);
							lottery.setSalesVolume(salesVolume.replaceAll("&nbsp;", ""));
							String jackpot = replaceHtml(child.substring(child.indexOf("���ع��棺")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")).substring(5);
							lottery.setJackpot(jackpot.replaceAll(",", ""));
						}
					}
					/**
					 * ʤ���ʺ���ѡ��
					 */
					if(lotteryid.equals("1300001") || lotteryid.equals("1300002")){
						lottery.setLotteryResult(lottery.getLotteryResult().replaceAll("\\*", "9"));
						if(child.indexOf("�������ʤ������") == -1 && child.indexOf("����ʤ��������") == -1){
						}else{
							String salesVolume = "";
							//System.out.println("libing test sfc xiaoliang=" + child);
							String temp = (child.replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll("��", "").replaceAll(",", "").replaceAll(" ", "").replaceAll("&nbsp", "")).trim();
//							//if(lottery.getTermNo().equals("03027")){
//								System.out.println("libing test sfc xiaoliang trim=" + temp);
//							//}
							int pos1 = temp.indexOf("<spanclass=\"cfont1\">");
							int pos2 = temp.indexOf("<spanclass=\"cfont1\">", pos1 + 20);
							int pos3 = temp.lastIndexOf("<spanclass=\"cfont1\">");
							int posend1 = temp.indexOf("</span>");
							int posend2 = temp.indexOf("</span>", posend1 + 7);
							int posend3 = temp.indexOf("</span>", posend2 + 7);

//							//if(lottery.getTermNo().equals("03027")){
//								System.out.println("libing test -- pos1=" + pos1 + ", pos2=" + pos2 + ", pos3=" + pos3 + ", posend1=" + posend1 + ", posend2=" + posend2 + ", posend3=" + posend3);
//								System.out.println("1st = " + temp.substring(pos1 + 20, posend1));
//								System.out.println("2nd = " + temp.substring(pos2 + 20, posend2));
//								System.out.println("3rd = " + temp.substring(pos3 + 20, posend3));
//
//
//							//}
							if(lotteryid.equals("1300001")){
								//salesVolume = replaceHtml(child.substring(child.indexOf("�������ʤ������") + 8, child.indexOf("�����ξ�����")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""));
								salesVolume = temp.substring(pos1 + 20, posend1).replaceAll("Ԫ", "");
							}else if(lotteryid.equals("1300002")){
								//salesVolume = replaceHtml(child.substring(child.indexOf("�����ξ�����") + 7, child.indexOf("���ʤ�����ع���")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "").replaceAll("&nbsp;", ""));
								salesVolume = temp.substring(pos2 + 20, posend2).replaceAll("Ԫ", "");
							}
							lottery.setSalesVolume(salesVolume);
							//String jackpot = replaceHtml(child.substring(child.indexOf("ʤ���ʽ��ع���") + 8));
							//lottery.setJackpot(jackpot.replaceAll(",", ""));
							String jackpot = temp.substring(pos3 + 20, posend3).replaceAll("Ԫ", "");
							lottery.setJackpot(jackpot);
						}
					}
				}
			}
		}
		// ����͸
		if(lotteryid.equals("1000001")){
			winResult = getDLTWinResult(lotteryid, tables);
		}
		if(lotteryid.equals("1000005")){
			winResult = get12WinResult(lotteryid, tables);
		}
		// ����͸����
		// ������
		if(lotteryid.equals("1000003")){
			winResult = getPlsWinResult(lotteryid, tables);
		}
		// ������
		if(lotteryid.equals("1000004")){
			winResult = getPLWWinResult(lotteryid, tables);
		}
		// ���ǲ�
		if(lotteryid.equals("1000002")){
			winResult = getQXCWinResult(lotteryid, tables);
		}
		// ʤ����
		if(lotteryid.equals("1300001")){
			winResult = getSfcWinResult(lotteryid, tables);
			saleinfo = LotteryTools.mergeSalesInfo(map);
			lottery.setSalesInfo(saleinfo);
			System.err.println(saleinfo);
		}
		// ��ѡ��
		if(lotteryid.equals("1300002")){
			winResult = getRxjWinResult(lotteryid, tables);
			saleinfo = LotteryTools.mergeSalesInfo(map);
			lottery.setSalesInfo(saleinfo);
			System.err.println(saleinfo);
		}
		// ������ȫ�����ĳ�����
		if(lotteryid.equals("1300003")||lotteryid.equals("1300004"))
		{
			winResult = getzc6WinResult(lotteryid, tables);
			winResult =winResult.replace("1-","");
			System.err.println("==============================");
			System.err.println(winResult);
			System.err.println("==============================");
			saleinfo = LotteryTools.mergeSalesInfo(map);
			lottery.setSalesInfo(saleinfo);
			System.err.println(saleinfo);
			
		}
		// System.err.println("=======����id========"+lottery.getLotteryId());
		// System.err.println("=======��ǰ����========"+lottery.getTermNo());
		// System.err.println("=======����״̬========"+lottery.getTermStatus());
		// System.err.println("=======����========"+lottery.getWinStatus());
		// System.err.println("=======����״̬========"+lottery.getSaleStatus());
		// System.err.println("=======salesInfo========"+lottery.getSalesInfo());
		// System.err.println("=======�ò��ֹٷ���������ʱ��========"+lottery.getDeadLine());
		// System.err.println("=======�ò��ֹٷ������Ŀ���ʱ��========"+lottery.getWinLine());
		// System.err.println("=======�ò��ֹٷ����������նҽ�����ʱ��========"+lottery.getChangeLine());
		// System.err.println("=======�������========"+lottery.getLotteryResult());
		// System.err.println("=======�������˳��========"+lottery.getLotteryResultPlus());
		// System.err.println("=======�ò��ָ��ڵ���������========"+lottery.getSalesVolume());
		// System.err.println("=======�ò��ֵ�ǰ�ڵ��ۼƽ���========"+lottery.getJackpot());
		// System.err.println("=======�н����========"+lottery.getWinResult());
		lottery.setWinResult(winResult);
		return lottery;
	}

	// ȡ�����н����
	/**
	 * ȡ����ѡ���н����
	 */
	public String getRxjWinResult(String lotteryid, TableTag[] tables){
		String[] rxj = new String[2];
		for(int i = 0; i < tables.length; i++){
			TableTag table = tables[i];
			TableRow rows[] = table.getRows();
			String tableClass = table.getAttribute("class");
			if(StringUtils.isNotEmpty(tableClass) && "kj_tablelist02".equals(tableClass.trim())){
				String childHtml = table.getChildrenHTML();
				if(childHtml.indexOf("��������") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("�ξ�") != -1){
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								for(int ts = 0; ts < rxj.length; ts++){
									if(ts == 0){
										rxj[ts] = jiangshu;
									}else{
										rxj[ts] = jiner;
									}
								}
							}
						}
					}
				}
			}
		}
		LotteryInfo li = new LotteryInfo();
		li.setArbitry9WinResult(rxj);
		String value = LotteryTools.mergeWinResult(1300002, li);
		return value;
	}

	/**
	 * ȡ��ʤ�����н����
	 */
	public String getSfcWinResult(String lotteryid, TableTag[] tables){
		TreeMap s = new TreeMap();
		String jiang = "";
		for(int i = 0; i < tables.length; i++){
			TableTag table = tables[i];
			TableRow rows[] = table.getRows();
			String tableClass = table.getAttribute("class");
			if(StringUtils.isNotEmpty(tableClass) && "kj_tablelist02".equals(tableClass.trim())){
				String childHtml = table.getChildrenHTML();
				if(childHtml.indexOf("��������") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("һ�Ƚ�") != -1){
								jiang = "1";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] sfc = {jiangshu, jiner};
								s.put(jiang, sfc);
							}else if(test.indexOf("���Ƚ�") != -1){
								jiang = "2";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] sfc = {jiangshu, jiner};
								s.put(jiang, sfc);
							}
						}
					}
				}
			}
		}
		LotteryInfo li = new LotteryInfo();
		li.setWinOrFailWinResult(s);
		String value = LotteryTools.mergeWinResult(1300001, li);
		return value;
	}
	/**
	 * ȡ��������ȫ���н����
	 */
	public String getzc6WinResult(String lotteryid, TableTag[] tables){
		TreeMap s = new TreeMap();
		String jiang = "";
		for(int i = 0; i < tables.length; i++){
			TableTag table = tables[i];
			TableRow rows[] = table.getRows();
			String tableClass = table.getAttribute("class");
			if(StringUtils.isNotEmpty(tableClass) && "kj_tablelist02".equals(tableClass.trim())){
				String childHtml = table.getChildrenHTML();
				if(childHtml.indexOf("��������") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("һ�Ƚ�") != -1){
								jiang = "1";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] zc6 = {jiangshu, jiner};
								s.put(jiang, zc6);
							}
						}
					}
				}
			}
		}
		LotteryInfo li = new LotteryInfo();
		li.setWinOrFailWinResult(s);
		String value = LotteryTools.mergeWinResult(1300001, li);
		return value;
	}
	/**
	 * �õ����ǲ��н����
	 * 
	 * @param lotteryid
	 * @param tables
	 * @return
	 */
	public String getQXCWinResult(String lotteryid, TableTag[] tables){
		TreeMap s = new TreeMap();
		String jiang = "";
		for(int i = 0; i < tables.length; i++){
			TableTag table = tables[i];
			TableRow rows[] = table.getRows();
			String tableClass = table.getAttribute("class");
			if(StringUtils.isNotEmpty(tableClass) && "kj_tablelist02".equals(tableClass.trim())){
				String childHtml = table.getChildrenHTML();
				if(childHtml.indexOf("��������") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("һ�Ƚ�") != -1){
								jiang = "1";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}else if(test.indexOf("���Ƚ�") != -1){
								jiang = "2";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}else if(test.indexOf("���Ƚ�") != -1){
								jiang = "3";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}else if(test.indexOf("�ĵȽ�") != -1){
								jiang = "4";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}else if(test.indexOf("��Ƚ�") != -1){
								jiang = "5";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}else if(test.indexOf("���Ƚ�") != -1){
								jiang = "6";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}
						}
					}
				}
			}
		}
		LotteryInfo li = new LotteryInfo();
		li.setSevenColorWinResult(s);
		String value = LotteryTools.mergeWinResult(1000002, li);
		return value;
	}

	/**
	 * �õ��������н����
	 * 
	 * @param lotteryid
	 * @param tables
	 * @return
	 */
	public String getPLWWinResult(String lotteryid, TableTag[] tables){
		String[] plw = new String[2];
		String jiang = "";
		for(int i = 0; i < tables.length; i++){
			TableTag table = tables[i];
			TableRow rows[] = table.getRows();
			String tableClass = table.getAttribute("class");
			if(StringUtils.isNotEmpty(tableClass) && "kj_tablelist02".equals(tableClass.trim())){
				String childHtml = table.getChildrenHTML();
				if(childHtml.indexOf("��������") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("������ֱѡ") != -1){
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								for(int ts = 0; ts < plw.length; ts++){
									if(ts == 0){
										plw[ts] = jiangshu;
									}else{
										plw[ts] = jiner;
									}
								}
							}
						}
					}
				}
			}
		}
		LotteryInfo li = new LotteryInfo();
		li.setArrangeFiveWinResult(plw);
		String value = LotteryTools.mergeWinResult(1000004, li);
		return value;
	}

	/**
	 * �õ��������н����
	 * 
	 * @param lotteryid
	 * @param tables
	 * @return
	 */
	public String getPlsWinResult(String lotteryid, TableTag[] tables){
		TreeMap s = new TreeMap();
		String jiang = "";
		for(int i = 0; i < tables.length; i++){
			TableTag table = tables[i];
			TableRow rows[] = table.getRows();
			String tableClass = table.getAttribute("class");
			if(StringUtils.isNotEmpty(tableClass) && "kj_tablelist02".equals(tableClass.trim())){
				String childHtml = table.getChildrenHTML();
				if(childHtml.indexOf("��������") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("������ֱѡ") != -1){
								jiang = "1";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}else if(test.indexOf("����������") != -1){
								jiang = "2";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}else if(test.indexOf("����������") != -1){
								jiang = "3";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}
						}
					}
				}
			}
		}
		LotteryInfo li = new LotteryInfo();
		li.setArrangeThreeWinResult(s);
		String value = LotteryTools.mergeWinResult(1000003, li);
		return value;
	}

	/**
	 * �õ�12ѡ���н����
	 * 
	 * @param lotteryid
	 * @param tables
	 * @return
	 */
	public String get12WinResult(String lotteryid, TableTag[] tables){
		String[] sxl = new String[2];
		String jiang = "";
		for(int i = 0; i < tables.length; i++){
			TableTag table = tables[i];
			TableRow rows[] = table.getRows();
			String tableClass = table.getAttribute("class");
			if(StringUtils.isNotEmpty(tableClass) && "kj_tablelist02".equals(tableClass.trim())){
				String childHtml = table.getChildrenHTML();
				if(childHtml.indexOf("��������") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("12ѡ2") != -1){
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								for(int ts = 0; ts < sxl.length; ts++){
									if(ts == 0){
										sxl[ts] = jiangshu;
									}else{
										sxl[ts] = jiner;
									}
								}
							}
						}
					}
				}
			}
		}
		LotteryInfo li = new LotteryInfo();
		li.setHappyZodiacWinResult(sxl);
		String value = LotteryTools.mergeWinResult(1000005, li);
		return value;
	}

	/**
	 * ����͸�õ��н����
	 */
	public String getDLTWinResult(String lotteryid, TableTag[] tables){
		TreeMap s = new TreeMap();
		String jiang = "";

		for(int i = 0; i < tables.length; i++){
			TableTag table = tables[i];
			TableRow rows[] = table.getRows();
			String tableClass = table.getAttribute("class");
			if(StringUtils.isNotEmpty(tableClass) && "kj_tablelist02".equals(tableClass.trim())){
				String childHtml = table.getChildrenHTML();
				if(childHtml.indexOf("��������") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("һ�Ƚ�") != -1){
								jiang = "1";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("����") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("׷��") != -1){
											TableColumn zhushu = columnsin[z + 1];
											TableColumn zuijiajiangjin = columnsin[z + 2];
											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
											in.put("B", zsxxxx);
										}
									}
								}else{
									TableColumn columnse = columns[j + 1];
									TableColumn columnth = columns[j + 2];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
								}
								s.put(jiang, in);
							}else if(test.indexOf("���Ƚ�") != -1){
								jiang = "2";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("����") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("׷��") != -1){
											TableColumn zhushu = columnsin[z + 1];
											TableColumn zuijiajiangjin = columnsin[z + 2];
											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
											in.put("B", zsxxxx);
										}
									}
								}else{
									TableColumn columnse = columns[j + 1];
									TableColumn columnth = columns[j + 2];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
								}
								s.put(jiang, in);
							}else if(test.indexOf("���Ƚ�") != -1){
								jiang = "3";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("����") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("׷��") != -1){
											TableColumn zhushu = columnsin[z + 1];
											TableColumn zuijiajiangjin = columnsin[z + 2];
											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
											in.put("B", zsxxxx);
										}
									}
								}else{
									TableColumn columnse = columns[j + 1];
									TableColumn columnth = columns[j + 2];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
								}
								s.put(jiang, in);
							}else if(test.indexOf("�ĵȽ�") != -1){
								jiang = "4";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("����") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("׷��") != -1){
											TableColumn zhushu = columnsin[z + 1];
											TableColumn zuijiajiangjin = columnsin[z + 2];
										
											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
											in.put("B", zsxxxx);
										}
									}
								}else{
									TableColumn columnse = columns[j + 1];
									TableColumn columnth = columns[j + 2];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
								}
								s.put(jiang, in);
							}else if(test.indexOf("��Ƚ�") != -1){
								jiang = "5";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("����") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("׷��") != -1){
											TableColumn zhushu = columnsin[z + 1];
											TableColumn zuijiajiangjin = columnsin[z + 2];
											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
											in.put("B", zsxxxx);
										}
									}
								}else{
									TableColumn columnse = columns[j + 1];
									TableColumn columnth = columns[j + 2];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
								}
								s.put(jiang, in);
							}else if(test.indexOf("���Ƚ�") != -1){
								jiang = "6";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("����") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("׷��") != -1){
											TableColumn zhushu = columnsin[z + 1];
											TableColumn zuijiajiangjin = columnsin[z + 2];
											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
											in.put("B", zsxxxx);
										}
									}
								}else{
									TableColumn columnse = columns[j + 1];
									TableColumn columnth = columns[j + 2];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
								}
								s.put(jiang, in);
							}else if(test.indexOf("�ߵȽ�") != -1){
								jiang = "7";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("����") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("׷��") != -1){
											TableColumn zhushu = columnsin[z + 1];
											TableColumn zuijiajiangjin = columnsin[z + 2];
											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
											in.put("B", zsxxxx);
										}
									}
								}else{
									TableColumn columnse = columns[j + 1];
									TableColumn columnth = columns[j + 2];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
								}
								s.put(jiang, in);
							}else if(test.indexOf("�˵Ƚ�") != -1){
								jiang = "8";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("����") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("׷��") != -1){
											TableColumn zhushu = columnsin[z + 1];
											TableColumn zuijiajiangjin = columnsin[z + 2];
											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
											in.put("B", zsxxxx);
										}
									}
								}else{
									TableColumn columnse = columns[j + 1];
									TableColumn columnth = columns[j + 2];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
								}
								s.put(jiang, in);
							}
						}
					}
				}
			}
		}
		LotteryInfo li = new LotteryInfo();
		li.setSuperWinResult(s);
		System.err.println("sss=" + s.toString());
		String value = LotteryTools.mergeWinResult(1000001, li);
		return value;
	}

	/**
	 * @param args
	 * @throws ParserException
	 */
	public static void main(String[] args) throws ParserException{
		String url = "http://www.500wan.com/static/info/kaijiang/shtml/dlt/07888.shtml";
		// String url =
		// "http://www1.sdticai.com/tcnews/news.asp?page=1&class=%BF%AA%BD%B1%BD%E1%B9%FB";
		// String url = "http://www1.sdticai.com/tcnews/data/13201.htm";
		url = "http://www.500wan.com/static/info/kaijiang/shtml/sfc/04025.shtml";
		
		Parser parser = new Parser(url);
		HtmlPage visitor = new HtmlPage(parser);
		// ʮһ�˶�𿪽�������Ҫ���ñ���
		// parser.setEncoding("gb2312");
		try{
			parser.visitAllNodesWith(visitor);
		}catch(Exception e){
			// Ϊ��ɽ�������ʮһ�˶���������
			e.printStackTrace();
			parser.reset();
			parser.visitAllNodesWith(visitor);
		}

		TableTag[] tables = visitor.getTables();
		for(TableTag table : tables){
			TableRow rows[] = table.getRows();
			for(TableRow row : rows){
				TableColumn columns[] = row.getColumns();
				for(int j = 0; j < columns.length; j++){
					TableColumn column = columns[j];
					String child = column.getChildrenHTML();
					String sfc1 = column.getText();
													
					if(sfc1.indexOf("VS") < 0){
					}else{
						String title = column.getAttribute("title").replaceAll("&nbsp;", "");
						System.out.println(title);
						if("-VS-".equalsIgnoreCase(title)){
							System.out.println("sadfsdafasdfasdfas");
						} else{
							System.out.println(title.split("VS")[0]);
							System.out.println(title.split("VS")[1]);
						}
						
						
						String sfc2 = sfc1.substring(sfc1.indexOf("title")).replaceAll("&nbsp;", "").replaceAll("\"", "").trim();
						if(sfc2.equals("-VS-")){
						}else{
							String A = sfc2.substring(6, sfc2.indexOf("VS"));
							String B = sfc2.substring(sfc2.indexOf("VS") + 2);
							if("-".equals(A.trim()) || "-".equals(B.trim())){							
							}else{
//								Map<String, String> inmap = new HashMap<String, String>();
//								inmap.put("A", A);
//								inmap.put("B", B);
//								inmap.put("C", "");
//								inmap.put("D", "");
//								map.put(Integer.valueOf(xj), inmap);
							}
						}
					}
//					}
					if(child.indexOf("����ʤ��������") == -1){
					}else{
						String salesVolume = "";
//						if(lotteryid.equals("1300001")){
//							salesVolume = replaceHtml(child.substring(child.indexOf("����ʤ��������") + 8, child.indexOf("�����ξ�����")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""));
//						}else if(lotteryid.equals("1300002")){
//							salesVolume = replaceHtml(child.substring(child.indexOf("�����ξ�����") + 7, child.indexOf("ʤ���ʽ��ع���")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "").replaceAll("&nbsp;", ""));
//						}
						String jackpot = replaceHtml(child.substring(child.indexOf("ʤ���ʽ��ع���") + 8));
					}
				}
			}
		}
//		// ʤ����
//		if(lotteryid.equals("1300001")){
//			winResult = getSfcWinResult(lotteryid, tables);
//			saleinfo = LotteryTools.mergeSalesInfo(map);
//			lottery.setSalesInfo(saleinfo);
//			System.err.println(saleinfo);
//		}
//		// ��ѡ��
//		if(lotteryid.equals("1300002")){
//			winResult = getRxjWinResult(lotteryid, tables);
//			saleinfo = LotteryTools.mergeSalesInfo(map);
//			lottery.setSalesInfo(saleinfo);
//			System.err.println(saleinfo);
//		}
		
		
		
//		TableTag[] tables = visitor.getTables();
//		TreeMap s = new TreeMap();
//		String jiang = "";
//		for(int i = 0; i < tables.length; i++){
//			TableTag table = tables[i];
//			TableRow rows[] = table.getRows();
//			if(i == tables.length - 1){
//				for(int h = 0; h < rows.length; h++){
//					TableRow row = rows[h];
//					TableColumn columns[] = row.getColumns();
//					for(int j = 0; j < columns.length; j++){
//						TableColumn column = columns[j];
//						if(column.getFirstChild() != null){
//							String test = column.getFirstChild().getText();
//							if(test.indexOf("һ�Ƚ�") != -1){
//								jiang = "1";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("����") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("׷��") != -1){
//											TableColumn zhushu = columnsin[z + 1];
//											TableColumn zuijiajiangjin = columnsin[z + 2];
//											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//											in.put("B", zsxxxx);
//										}
//									}
//								}else{
//									TableColumn columnse = columns[j + 1];
//									TableColumn columnth = columns[j + 2];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//								}
//								s.put(jiang, in);
//							}else if(test.indexOf("���Ƚ�") != -1){
//								jiang = "2";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("����") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("׷��") != -1){
//											TableColumn zhushu = columnsin[z + 1];
//											TableColumn zuijiajiangjin = columnsin[z + 2];
//											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//											in.put("B", zsxxxx);
//										}
//									}
//								}else{
//									TableColumn columnse = columns[j + 1];
//									TableColumn columnth = columns[j + 2];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//								}
//								s.put(jiang, in);
//							}else if(test.indexOf("���Ƚ�") != -1){
//								jiang = "3";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("����") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("׷��") != -1){
//											TableColumn zhushu = columnsin[z + 1];
//											TableColumn zuijiajiangjin = columnsin[z + 2];
//											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//											in.put("B", zsxxxx);
//										}
//									}
//								}else{
//									TableColumn columnse = columns[j + 1];
//									TableColumn columnth = columns[j + 2];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//								}
//								s.put(jiang, in);
//							}else if(test.indexOf("�ĵȽ�") != -1){
//								jiang = "4";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("����") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("׷��") != -1){
//											TableColumn zhushu = columnsin[z + 1];
//											TableColumn zuijiajiangjin = columnsin[z + 2];
//											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//											in.put("B", zsxxxx);
//										}
//									}
//								}else{
//									TableColumn columnse = columns[j + 1];
//									TableColumn columnth = columns[j + 2];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//								}
//								s.put(jiang, in);
//							}else if(test.indexOf("��Ƚ�") != -1){
//								jiang = "5";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("����") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("׷��") != -1){
//											TableColumn zhushu = columnsin[z + 1];
//											TableColumn zuijiajiangjin = columnsin[z + 2];
//											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//											in.put("B", zsxxxx);
//										}
//									}
//								}else{
//									TableColumn columnse = columns[j + 1];
//									TableColumn columnth = columns[j + 2];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//								}
//								s.put(jiang, in);
//							}else if(test.indexOf("���Ƚ�") != -1){
//								jiang = "6";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("����") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("׷��") != -1){
//											TableColumn zhushu = columnsin[z + 1];
//											TableColumn zuijiajiangjin = columnsin[z + 2];
//											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//											in.put("B", zsxxxx);
//										}
//									}
//								}else{
//									TableColumn columnse = columns[j + 1];
//									TableColumn columnth = columns[j + 2];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//								}
//								s.put(jiang, in);
//							}else if(test.indexOf("�ߵȽ�") != -1){
//								jiang = "7";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("����") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("׷��") != -1){
//											TableColumn zhushu = columnsin[z + 1];
//											TableColumn zuijiajiangjin = columnsin[z + 2];
//											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//											in.put("B", zsxxxx);
//										}
//									}
//								}else{
//									TableColumn columnse = columns[j + 1];
//									TableColumn columnth = columns[j + 2];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//								}
//								s.put(jiang, in);
//							}else if(test.indexOf("�˵Ƚ�") != -1){
//								jiang = "8";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("����") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("׷��") != -1){
//											TableColumn zhushu = columnsin[z + 1];
//											TableColumn zuijiajiangjin = columnsin[z + 2];
//											String zsxxxx[] = {zhushu.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), zuijiajiangjin.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//											in.put("B", zsxxxx);
//										}
//									}
//								}else{
//									TableColumn columnse = columns[j + 1];
//									TableColumn columnth = columns[j + 2];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//								}
//								s.put(jiang, in);
//							}
//						}
//					}
//				}
//			}
//		}
//		LotteryInfo li = new LotteryInfo();
//		li.setSuperWinResult(s);
//		String value = LotteryTools.mergeWinResult(1000001, li);
//		System.err.println(value);
	}
}
