package com.success.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.HtmlPage;
import com.sun.net.httpserver.spi.HttpServerProvider;


public class GetMobileArea{

	private String getAreaCode(String areaName){
		if(areaName == null || "".equals(areaName.trim())){
			return "00";
		}
		if(areaName.trim().indexOf("北京") >= 0){
			return "11";
		}
		if(areaName.trim().indexOf("天津") >= 0){
			return "12";
		}
		if(areaName.trim().indexOf("河北") >= 0){
			return "13";
		}
		if(areaName.trim().indexOf("山西") >= 0){
			return "14";
		}
		if(areaName.trim().indexOf("内蒙古") >= 0){
			return "15";
		}
		if(areaName.trim().indexOf("辽宁") >= 0){
			return "21";
		}
		if(areaName.trim().indexOf("吉林") >= 0){
			return "22";
		}
		if(areaName.trim().indexOf("黑龙江") >= 0){
			return "23";
		}
		if(areaName.trim().indexOf("上海") >= 0){
			return "31";
		}
		if(areaName.trim().indexOf("江苏") >= 0){
			return "32";
		}
		if(areaName.trim().indexOf("浙江") >= 0){
			return "33";
		}
		if(areaName.trim().indexOf("安徽") >= 0){
			return "34";
		}
		if(areaName.trim().indexOf("福建") >= 0){
			return "35";
		}
		if(areaName.trim().indexOf("江西") >= 0){
			return "36";
		}
		if(areaName.trim().indexOf("山东") >= 0){
			return "37";
		}
		if(areaName.trim().indexOf("河南") >= 0){
			return "41";
		}
		if(areaName.trim().indexOf("湖北") >= 0){
			return "42";
		}
		if(areaName.trim().indexOf("湖南") >= 0){
			return "43";
		}
		if(areaName.trim().indexOf("广东") >= 0){
			return "44";
		}
		if(areaName.trim().indexOf("广西") >= 0){
			return "45";
		}
		if(areaName.trim().indexOf("海南") >= 0){
			return "46";
		}
		if(areaName.trim().indexOf("重庆") >= 0){
			return "50";
		}
		if(areaName.trim().indexOf("四川") >= 0){
			return "51";
		}
		if(areaName.trim().indexOf("贵州") >= 0){
			return "52";
		}
		if(areaName.trim().indexOf("云南") >= 0){
			return "53";
		}
		if(areaName.trim().indexOf("西藏") >= 0){
			return "54";
		}
		if(areaName.trim().indexOf("陕西") >= 0){
			return "61";
		}
		if(areaName.trim().indexOf("甘肃") >= 0){
			return "62";
		}
		if(areaName.trim().indexOf("青海") >= 0){
			return "63";
		}
		if(areaName.trim().indexOf("宁夏") >= 0){
			return "64";
		}
		if(areaName.trim().indexOf("新疆") >= 0){
			return "65";
		}
		if(areaName.trim().indexOf("台湾") >= 0){
			return "71";
		}
		if(areaName.trim().indexOf("香港") >= 0){
			return "81";
		}
		if(areaName.trim().indexOf("澳门") >= 0){
			return "82";
		}
		return "00";
	}

	public void getMobileFromInternet(String fileName) throws FileNotFoundException, ParserException{
		//http://mobile.yqie.com/guishudi/130/1.html
		//String url = "http://mobile.yqie.com/guishudi/130/1.html";
		String codes[] = {"130", "131", "132", "133", "134", "135", "136", "137", "138", "139", "150", "151", "152", "153", "155", "156", "157", "158", "159", "186", "187", "188", "189"};
		//String fileName = "d:\\test\\initsql\\areamap-source.txt";
		PrintWriter pw = new PrintWriter(new File(fileName));
		PrintWriter pwDetail = new PrintWriter(new File(fileName + ".detail"));
		for(String code : codes){
			System.out.println("正在获取 " + code + " 号段信息...");
			for(int i = 1; i <= 200; i++){
				String url = "http://mobile.yqie.com/guishudi/" + code + "/" + i + ".html";
				Parser parser = new Parser(url);
				HtmlPage visitor = new HtmlPage(parser);
				
				//十一运夺金开奖详情需要设置编码
				//parser.setEncoding("gb2312");

				try{
					parser.visitAllNodesWith(visitor);
				}catch(Exception e){
					//为了山东体彩网十一运夺金编码问题
					e.printStackTrace();
					parser.reset();
					parser.visitAllNodesWith(visitor);
				}

				TableTag[] tables = visitor.getTables();
				for(TableTag table : tables){
					if("AutoNumber1".equals(table.getAttribute("id"))){
						TableRow rows[] = table.getRows();
						boolean first = true;
						for(TableRow row : rows){
							if(first){
								first = false;
								continue;
							}
							TableColumn columns[] = row.getColumns();
							//System.out.println(((LinkTag)columns[0].getFirstChild()).getStringText().trim() + "=" + getAreaCode(columns[1].getStringText().trim()) + "\t#" + columns[1].getStringText().trim() + "\t" + columns[2].getStringText().trim());
							pw.println(((LinkTag)columns[0].getFirstChild()).getStringText().trim() + "=" + getAreaCode(columns[1].getStringText().trim()));
							pwDetail.println(((LinkTag)columns[0].getFirstChild()).getStringText().trim() + "=" + getAreaCode(columns[1].getStringText().trim()) + "," + columns[1].getStringText().trim() + "," + columns[2].getStringText().trim());
						}
					}
				}		
			}
			System.out.println("获取 " + code + " 号段信息完毕!!!");
		}
		pw.close();
		pwDetail.close();
	}

	public void compressAreaMap(String resource, String resourceDetail, int pos) throws MissingResourceException, IOException{

//		PrintWriter pw = new PrintWriter(new File("d:\\test\\initsql\\cHaoduan" + pos + ".properties"));
//		//PrintWriter pw = new PrintWriter(new File("/home/libing/test/area/cHaoduan" + pos + ".properties"));
//		PrintWriter pwDetail = new PrintWriter(new File("d:\\test\\initsql\\cHaoduanDetail" + pos + ".properties"));
//		//PrintWriter pwDetail = new PrintWriter(new File("/home/libing/test/area/cHaoduanDetail" + pos + ".properties"));
		
		Properties p = AutoProperties.getInstance(resource);
		Set<String> keys = new TreeSet<String>();
		keys.addAll(p.stringPropertyNames());
		
		Set<String> keyyy = new HashSet<String>();
		int i = 0, i5 = 0, i6 = 0, i7 = 0;
		for(String key : keys){
			if("00".equals(p.getProperty(key))){
				i++;
			}
//			if(key.length() == 6){
//				i6++;
//			} else if(key.length() == 5){
//				i5++;
//				System.out.println(key + "=" + p.getProperty(key));
//			} else if(key.length() == 7){
//				i7++;
//			} else {
//				System.err.println(key + "=" + p.getProperty(key));
//			}

//			if(key.length() == (pos + 1)){
//				String cKey = key.substring(0, pos);
//				String value = AutoProperties.getString(key, "error", resource);
//				if(keyyy.contains(cKey)){
//					continue;
//				}else{
//					boolean same = true;
//					for(int j = 0; j < 10; j++){
//						if(!value.equals(AutoProperties.getString(cKey + ("" + j), "error2", resource))){
//							same = false;
//							break;
//						}
//					}
//					if(same){
////						pw.println(cKey + "=" + value);
////						pwDetail.println(cKey + "=" + AutoProperties.getString(key, "error3", resourceDetail));
//						System.out.println(cKey);
//						keyyy.add(cKey);
//					}else{
////						pw.println(key + "=" + value);
////						pwDetail.println(key + "=" + AutoProperties.getString(key, "error4", resourceDetail));
//					}
//				}
//			} else {
////				pw.println(key + "=" + AutoProperties.getString(key, "error5", resource));
////				pwDetail.println(key + "=" + AutoProperties.getString(key, "error5", resourceDetail));
//			}
		}
//		pw.close();
//		pwDetail.close();
		System.err.println("i=" + i + ", i5=" + i5 + ", i6=" + i6 + ", i7=" + i7);
	}
	
	public void compressAreaMap(int pos) throws FileNotFoundException{
		String res1 = "com.success.utils.haoduan";
		String res2 = "com.success.utils.haoduanDetail";
		
		//PrintWriter pw = new PrintWriter(new File("d:\\test\\initsql\\cHaoduan" + pos + ".properties"));
		PrintWriter pw = new PrintWriter(new File("/home/libing/test/area/cHaoduan" + pos + ".properties"));
		//PrintWriter pwDetail = new PrintWriter(new File("d:\\test\\initsql\\cHaoduanDetail" + pos + ".properties"));
		PrintWriter pwDetail = new PrintWriter(new File("/home/libing/test/area/cHaoduanDetail" + pos + ".properties"));
		
		String codes[] = {"130", "131", "132", "133", "134", "135", "136", "137", "138", "139", "150", "151", "152", "153", "155", "156", "157", "158", "159", "186", "187", "188", "189"};
		//String codes[] = {"130"};
		String ZERO[] = {"00", "0", ""};
		String MIN[] = {"0", "00", "000", "0000"};
		String MAX[] = {"9", "99", "999", "9999"};
		int POS[] = {10, 100, 1000};
		for(String code : codes){
			System.out.println("正在处理 " + code + MIN[pos - 4] + " -- " + code + MAX[pos - 4] + " 号段");
			for(int i = 0; i < POS[pos - 4]; i++){
				String key = code + ZERO[("" + i).length() - 1] + i;
				System.out.println("\t正在处理 " + key + " 号段");
				String value = AutoProperties.getString(key + "0", "00", res1);
				boolean same = true;
				for(int j = 1; j < 10; j++){
					//System.out.println("\t\t正在处理 " + key + ("" + j) + " 号段");;
					if(!value.equals(AutoProperties.getString(key + ("" + j), "00", res1))){
						same = false;
						break;
					}
				}

				if(same){
					pw.println(key + "=" + value);
					pwDetail.println(key + "=" + AutoProperties.getString(key + "0", "00,,", res2));
				}else{
					for(int k = 0; k < 10; k++){
						pw.println(key + k + "=" + AutoProperties.getString(key + k, "00", res1));
						pwDetail.println(key + k + "=" + AutoProperties.getString(key + k, "00,,", res2));
					}
				}
			}
			pw.flush();
			pwDetail.flush();
			System.out.println("处理完成 " + code + MIN[pos - 3] + " -- " + code + MAX[pos - 3] + " 号段");
		}
		pw.close();
		pwDetail.close();
	}

	private String getOperator(String key){
		String uc[] = {"130", "131", "132", "155", "156", "186"};
		String ct[] = {"133", "153", "189"};
		for(String s : uc){
			if(key.startsWith(s)){
				return "uc";
			}
		}
		for(String s : ct){
			if(key.startsWith(s)){
				return "ct";
			}
		}
		return "yd";
	}
	
	public void getAreaCodeList(String areaCode) throws MissingResourceException, IOException{
		Properties p = AutoProperties.getInstance("com.success.utils.cHaoduan5");
		Set<String> keys = new TreeSet<String>();
		keys.addAll(p.stringPropertyNames());
		for(String key : keys){
			if(areaCode.equals(p.getProperty(key)) && "uc".equals(getOperator(key))){
				//System.out.println(key + "=" + AutoProperties.getString(key, "error", "com.success.utils.cHaoduanDetail5"));
				System.out.println(key + "=ucqh");
			}
		}
	}
	
	/**
	 * @param args
	 * @throws ParserException 
	 * @throws IOException 
	 * @throws MissingResourceException 
	 */
	public static void main(String[] args) throws ParserException, MissingResourceException, IOException{
		GetMobileArea mobileAreaTools = new GetMobileArea();
		//mobileAreaTools.getMobileFromInternet("d:\\test\\initsql\\haoduan.source");
		//String resource = "com.success.utils.haoduan";
		//mobileAreaTools.compressAreaMap(6);
		//mobileAreaTools.compressAreaMap("com.success.utils.cHaoduan5", "com.success.utils.cHaoduanDetail5", 4);
		mobileAreaTools.getAreaCodeList("63");
	}
}
