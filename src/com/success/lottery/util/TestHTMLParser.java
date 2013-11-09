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
		String regEx = "<.+?>"; // 表示标签
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 
	 * @param url
	 *            连接到的URL
	 * @param lotteryid
	 *            彩票类型
	 * @param termNo
	 *            期数
	 * @param winLine
	 *            该彩种官方公布的开奖时间
	 * @param deadLine
	 *            该彩种官方截至销售时间
	 * @param lotteryResult
	 *            中奖号码
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
		// 十一运夺金开奖详情需要设置编码
//		 parser.setEncoding("gb2312");
		try{
			parser.visitAllNodesWith(visitor);
		}catch(Exception e){
			// 为了山东体彩网十一运夺金编码问题
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
					
					// 大乐透开始
					if(lotteryid.equals("1000001") || lotteryid.equals("1000005")){
						if(child.indexOf("本期销量") == -1){
						}else{
							String salesVolume = replaceHtml(child.substring(child.indexOf("本期销量"), child.indexOf("12选2销量：")).replaceAll("&nbsp;", "")).substring(5);
							String jackpot = replaceHtml(child.substring(child.indexOf("奖池滚存："))).substring(5);
							String xuan2 = child.substring(child.indexOf("12选2销量："), child.indexOf("奖池滚存：")).replaceAll("&nbsp;", "");
							if(lotteryid.equals("1000001")){
								lottery.setSalesVolume(salesVolume.replaceAll("元", "").replaceAll("&nbsp;", ""));
								lottery.setJackpot(jackpot.replaceAll(",", "").replaceAll("元", "").replaceAll("&nbsp;", ""));
							}else if(lotteryid.equals("1000005")){
								lottery.setSalesVolume(replaceHtml(xuan2.replaceAll(",", "").substring(7).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "").replaceAll("元", "")));
							}
						}
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							;
							int x = test.indexOf("出球顺序：");
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
					// 大乐透结束
					/**
					 * 排列三
					 */
					if(lotteryid.equals("1000003")){
						if(child.indexOf("本期销量") == -1){
						}else{
							String salesVolume = replaceHtml(child.substring(child.indexOf("本期销量") + 5));
							lottery.setSalesVolume(salesVolume);
						}
					}
					/**
					 * 排列五
					 */
					if(lotteryid.equals("1000004")){
						if(child.indexOf("本期销量") == -1){
						}else{
							String salesVolume = replaceHtml(child.substring(child.indexOf("本期销量") + 5));
							lottery.setSalesVolume(salesVolume);
						}
					}
					/**
					 * 七星彩
					 */
					if(lotteryid.equals("1000002")){
						if(child.indexOf("本期销量") == -1){
						}else{
							String salesVolume = replaceHtml(child.substring(child.indexOf("本期销量"), child.indexOf("奖池滚存")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")).substring(5);
							lottery.setSalesVolume(salesVolume.replaceAll("&nbsp;", ""));
							String jackpot = replaceHtml(child.substring(child.indexOf("奖池滚存：")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")).substring(5);
							lottery.setJackpot(jackpot.replaceAll(",", ""));
						}
					}
					
					/*
					 * 六场半全场
					 */
					if(lotteryid.equals("1300003")||lotteryid.equals("1300004"))
					{
						lottery.setLotteryResult(lottery.getLotteryResult().replaceAll("\\*", "9"));
						if(child.indexOf("本期销量") == -1){
						}else{
							String salesVolume = replaceHtml(child.substring(child.indexOf("本期销量"), child.indexOf("奖池滚存")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")).substring(5);
							lottery.setSalesVolume(salesVolume.replaceAll("&nbsp;", ""));
							String jackpot = replaceHtml(child.substring(child.indexOf("奖池滚存：")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")).substring(5);
							lottery.setJackpot(jackpot.replaceAll(",", ""));
						}
					}
					/**
					 * 胜负彩和任选九
					 */
					if(lotteryid.equals("1300001") || lotteryid.equals("1300002")){
						lottery.setLotteryResult(lottery.getLotteryResult().replaceAll("\\*", "9"));
						if(child.indexOf("本期足彩胜负销量") == -1 && child.indexOf("本期胜负彩销量") == -1){
						}else{
							String salesVolume = "";
							//System.out.println("libing test sfc xiaoliang=" + child);
							String temp = (child.replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll("：", "").replaceAll(",", "").replaceAll(" ", "").replaceAll("&nbsp", "")).trim();
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
								//salesVolume = replaceHtml(child.substring(child.indexOf("本期足彩胜负销量") + 8, child.indexOf("本期任九销量")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""));
								salesVolume = temp.substring(pos1 + 20, posend1).replaceAll("元", "");
							}else if(lotteryid.equals("1300002")){
								//salesVolume = replaceHtml(child.substring(child.indexOf("本期任九销量") + 7, child.indexOf("足彩胜负奖池滚存")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "").replaceAll("&nbsp;", ""));
								salesVolume = temp.substring(pos2 + 20, posend2).replaceAll("元", "");
							}
							lottery.setSalesVolume(salesVolume);
							//String jackpot = replaceHtml(child.substring(child.indexOf("胜负彩奖池滚存") + 8));
							//lottery.setJackpot(jackpot.replaceAll(",", ""));
							String jackpot = temp.substring(pos3 + 20, posend3).replaceAll("元", "");
							lottery.setJackpot(jackpot);
						}
					}
				}
			}
		}
		// 大乐透
		if(lotteryid.equals("1000001")){
			winResult = getDLTWinResult(lotteryid, tables);
		}
		if(lotteryid.equals("1000005")){
			winResult = get12WinResult(lotteryid, tables);
		}
		// 大乐透结束
		// 排列三
		if(lotteryid.equals("1000003")){
			winResult = getPlsWinResult(lotteryid, tables);
		}
		// 排列五
		if(lotteryid.equals("1000004")){
			winResult = getPLWWinResult(lotteryid, tables);
		}
		// 七星彩
		if(lotteryid.equals("1000002")){
			winResult = getQXCWinResult(lotteryid, tables);
		}
		// 胜负彩
		if(lotteryid.equals("1300001")){
			winResult = getSfcWinResult(lotteryid, tables);
			saleinfo = LotteryTools.mergeSalesInfo(map);
			lottery.setSalesInfo(saleinfo);
			System.err.println(saleinfo);
		}
		// 任选九
		if(lotteryid.equals("1300002")){
			winResult = getRxjWinResult(lotteryid, tables);
			saleinfo = LotteryTools.mergeSalesInfo(map);
			lottery.setSalesInfo(saleinfo);
			System.err.println(saleinfo);
		}
		// 六场半全场与四场进球
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
		// System.err.println("=======彩种id========"+lottery.getLotteryId());
		// System.err.println("=======当前期数========"+lottery.getTermNo());
		// System.err.println("=======彩期状态========"+lottery.getTermStatus());
		// System.err.println("=======开奖========"+lottery.getWinStatus());
		// System.err.println("=======销售状态========"+lottery.getSaleStatus());
		// System.err.println("=======salesInfo========"+lottery.getSalesInfo());
		// System.err.println("=======该彩种官方截至销售时间========"+lottery.getDeadLine());
		// System.err.println("=======该彩种官方公布的开奖时间========"+lottery.getWinLine());
		// System.err.println("=======该彩种官方公布的最终兑奖截至时间========"+lottery.getChangeLine());
		// System.err.println("=======开奖结果========"+lottery.getLotteryResult());
		// System.err.println("=======开奖结果顺序========"+lottery.getLotteryResultPlus());
		// System.err.println("=======该彩种该期的销售总量========"+lottery.getSalesVolume());
		// System.err.println("=======该彩种当前期的累计奖池========"+lottery.getJackpot());
		// System.err.println("=======中奖结果========"+lottery.getWinResult());
		lottery.setWinResult(winResult);
		return lottery;
	}

	// 取各种中奖结果
	/**
	 * 取得任选九中奖结果
	 */
	public String getRxjWinResult(String lotteryid, TableTag[] tables){
		String[] rxj = new String[2];
		for(int i = 0; i < tables.length; i++){
			TableTag table = tables[i];
			TableRow rows[] = table.getRows();
			String tableClass = table.getAttribute("class");
			if(StringUtils.isNotEmpty(tableClass) && "kj_tablelist02".equals(tableClass.trim())){
				String childHtml = table.getChildrenHTML();
				if(childHtml.indexOf("开奖详情") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("任九") != -1){
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
	 * 取得胜负彩中奖结果
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
				if(childHtml.indexOf("开奖详情") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("一等奖") != -1){
								jiang = "1";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] sfc = {jiangshu, jiner};
								s.put(jiang, sfc);
							}else if(test.indexOf("二等奖") != -1){
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
	 * 取得六场半全场中奖结果
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
				if(childHtml.indexOf("开奖详情") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("一等奖") != -1){
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
	 * 得到七星彩中奖结果
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
				if(childHtml.indexOf("开奖详情") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("一等奖") != -1){
								jiang = "1";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}else if(test.indexOf("二等奖") != -1){
								jiang = "2";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}else if(test.indexOf("三等奖") != -1){
								jiang = "3";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}else if(test.indexOf("四等奖") != -1){
								jiang = "4";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}else if(test.indexOf("五等奖") != -1){
								jiang = "5";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}else if(test.indexOf("六等奖") != -1){
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
	 * 得到排列五中奖结果
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
				if(childHtml.indexOf("开奖详情") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("排列五直选") != -1){
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
	 * 得到排列三中奖结果
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
				if(childHtml.indexOf("开奖详情") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("排列三直选") != -1){
								jiang = "1";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}else if(test.indexOf("排列三组三") != -1){
								jiang = "2";
								TableColumn columnse = columns[j + 1];
								TableColumn columnth = columns[j + 2];
								String jiangshu = columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String jiner = columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "");
								String[] pls = {jiangshu, jiner};
								s.put(jiang, pls);
							}else if(test.indexOf("排列三组六") != -1){
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
	 * 得到12选二中奖结果
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
				if(childHtml.indexOf("开奖详情") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("12选2") != -1){
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
	 * 大乐透得到中奖结果
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
				if(childHtml.indexOf("开奖详情") < 0){
					continue;
				}
				for(int h = 0; h < rows.length; h++){
					TableRow row = rows[h];
					TableColumn columns[] = row.getColumns();
					for(int j = 0; j < columns.length; j++){
						TableColumn column = columns[j];
						if(column.getFirstChild() != null){
							String test = column.getFirstChild().getText();
							if(test.indexOf("一等奖") != -1){
								jiang = "1";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("追加") != -1){
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
							}else if(test.indexOf("二等奖") != -1){
								jiang = "2";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("追加") != -1){
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
							}else if(test.indexOf("三等奖") != -1){
								jiang = "3";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("追加") != -1){
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
							}else if(test.indexOf("四等奖") != -1){
								jiang = "4";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("追加") != -1){
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
							}else if(test.indexOf("五等奖") != -1){
								jiang = "5";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("追加") != -1){
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
							}else if(test.indexOf("六等奖") != -1){
								jiang = "6";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("追加") != -1){
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
							}else if(test.indexOf("七等奖") != -1){
								jiang = "7";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("追加") != -1){
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
							}else if(test.indexOf("八等奖") != -1){
								jiang = "8";
								TreeMap in = new TreeMap();
								TableColumn columnf = columns[j + 1];
								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
									TableColumn columnse = columns[j + 2];
									TableColumn columnth = columns[j + 3];
									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
									in.put("A", ta);
									TableRow rowin = rows[h + 1];
									TableColumn columnsin[] = rowin.getColumns();
									for(int z = 0; z < columnsin.length; z++){
										TableColumn column1 = columnsin[z];
										String test1 = column1.getFirstChild().getText();
										if(test1.indexOf("追加") != -1){
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
		// 十一运夺金开奖详情需要设置编码
		// parser.setEncoding("gb2312");
		try{
			parser.visitAllNodesWith(visitor);
		}catch(Exception e){
			// 为了山东体彩网十一运夺金编码问题
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
					if(child.indexOf("本期胜负彩销量") == -1){
					}else{
						String salesVolume = "";
//						if(lotteryid.equals("1300001")){
//							salesVolume = replaceHtml(child.substring(child.indexOf("本期胜负彩销量") + 8, child.indexOf("本期任九销量")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""));
//						}else if(lotteryid.equals("1300002")){
//							salesVolume = replaceHtml(child.substring(child.indexOf("本期任九销量") + 7, child.indexOf("胜负彩奖池滚存")).replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "").replaceAll("&nbsp;", ""));
//						}
						String jackpot = replaceHtml(child.substring(child.indexOf("胜负彩奖池滚存") + 8));
					}
				}
			}
		}
//		// 胜负彩
//		if(lotteryid.equals("1300001")){
//			winResult = getSfcWinResult(lotteryid, tables);
//			saleinfo = LotteryTools.mergeSalesInfo(map);
//			lottery.setSalesInfo(saleinfo);
//			System.err.println(saleinfo);
//		}
//		// 任选九
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
//							if(test.indexOf("一等奖") != -1){
//								jiang = "1";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("追加") != -1){
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
//							}else if(test.indexOf("二等奖") != -1){
//								jiang = "2";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("追加") != -1){
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
//							}else if(test.indexOf("三等奖") != -1){
//								jiang = "3";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("追加") != -1){
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
//							}else if(test.indexOf("四等奖") != -1){
//								jiang = "4";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("追加") != -1){
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
//							}else if(test.indexOf("五等奖") != -1){
//								jiang = "5";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("追加") != -1){
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
//							}else if(test.indexOf("六等奖") != -1){
//								jiang = "6";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("追加") != -1){
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
//							}else if(test.indexOf("七等奖") != -1){
//								jiang = "7";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("追加") != -1){
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
//							}else if(test.indexOf("八等奖") != -1){
//								jiang = "8";
//								TreeMap in = new TreeMap();
//								TableColumn columnf = columns[j + 1];
//								if(columnf.getFirstChild().getText().indexOf("基本") != -1){
//									TableColumn columnse = columns[j + 2];
//									TableColumn columnth = columns[j + 3];
//									String ta[] = {columnse.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", ""), columnth.getFirstChild().getText().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll(",", "").replaceAll(" ", "")};
//									in.put("A", ta);
//									TableRow rowin = rows[h + 1];
//									TableColumn columnsin[] = rowin.getColumns();
//									for(int z = 0; z < columnsin.length; z++){
//										TableColumn column1 = columnsin[z];
//										String test1 = column1.getFirstChild().getText();
//										if(test1.indexOf("追加") != -1){
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
