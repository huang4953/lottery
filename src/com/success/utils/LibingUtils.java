package com.success.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class LibingUtils{

	public static SimpleDateFormat	dateFormat	= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/** Creates a new instance of StringUtil */
	private LibingUtils(){
	}

	public static String time2String(long lTime){
		return dateFormat.format(new Date(lTime));
	}

	/**
	 * 得到一个有分割的字符窜的某个域如 getFiled("a0,a1,a2,a3,a4", ",", 2) 结果是"a2"。从零开始。
	 * 
	 * @param src
	 *            字符窜
	 * @param delim
	 *            分隔符
	 * @param idx
	 *            序号，从零开始
	 */
	public static String getField(String src, String delim, int idx){
		int beginIndex = 0;
		int endIndex = 0;
		for(int i = 0; i < idx; i++){
			endIndex = src.indexOf(delim, beginIndex);
			if(endIndex == -1)
				return null;
			beginIndex = endIndex + delim.length();
		}
		endIndex = src.indexOf(delim, beginIndex);
		if(endIndex == -1){
			return src.substring(beginIndex);
		}else{
			return src.substring(beginIndex, endIndex);
		}
	}

	public static String replaceField(String src, String delim, int idx, String str){
		int beginIndex = 0;
		int endIndex = 0;
		for(int i = 0; i < idx; i++){
			endIndex = src.indexOf(delim, beginIndex);
			if(endIndex == -1)
				return null;
			beginIndex = endIndex + delim.length();
		}
		endIndex = src.indexOf(delim, beginIndex);
		if(endIndex == -1){
			return src.substring(0, beginIndex) + str;
		}else{
			return src.substring(0, beginIndex) + str + src.substring(endIndex);
		}
	}

	public static int getFieldSize(String src, String delim){
		int count = 1;
		while(true){
			int i = src.indexOf(delim);
			if(i < 0)
				return count;
			src = src.substring(i + delim.length()).trim();
			count++;
		}
	}

	/**
	 * replace all s in msg with marker.
	 * 
	 * @param msg
	 *            the String will be modify
	 * @param s
	 *            the text to replace old marker text
	 * @param marker
	 *            the text to which msg is to be matched
	 */
	public static String replace(String msg, String s, String marker){
		int idx = msg.indexOf(marker);
		if(idx < 0)
			return msg;
		String msg1 = msg.substring(0, idx) + s + msg.substring(idx + marker.length());
		return replace(msg1, s, marker);
	}

	public static String replaceCRs(String msg){
		String rs = replace(msg, "<CR>", "\r");
		rs = replace(rs, "<LF>", "\n");
		rs = replace(rs, "<TAB>", "\t");
		return rs;
	}

	/**
	 * replace all s in msg with marker.
	 * 
	 * @param msg
	 *            the String will be modify
	 * @param i
	 *            the number to replace old marker text
	 * @param marker
	 *            the text to which msg is to be matched
	 */
	public static String replace(String msg, int i, String marker){
		return replace(msg, Integer.toString(i), marker);
	}

	/**
	 * 有些配置中的路径带有环境变量，但是java不认。于是就有了这个函数，他将用中确的值替换环境变量
	 * 
	 * @param path
	 *            输入的带有环境变量的路径
	 * @return 正确的路径
	 */
	public static String getPathByEnvVar(String path){
		if(path == null)
			return path;
		if(path.indexOf('$') < 0)
			return path;
		String preStr = path.substring(0, path.indexOf('$'));
		String subStr = path.substring(path.indexOf('$') + 1);
		int subStrLen = subStr.length();
		int endIndex = subStr.indexOf("\\");
		if(endIndex < 0)
			endIndex = subStrLen;
		int endIndex1 = subStr.indexOf('/');
		if(endIndex1 < 0)
			endIndex1 = subStrLen;
		endIndex = Math.min(endIndex, endIndex1);
		endIndex1 = subStr.indexOf('$');
		if(endIndex1 < 0)
			endIndex1 = subStrLen;
		endIndex = Math.min(endIndex, endIndex1);
		endIndex1 = subStr.indexOf('.');
		if(endIndex1 < 0)
			endIndex1 = subStrLen;
		endIndex = Math.min(endIndex, endIndex1);
		endIndex1 = subStr.indexOf(':');
		if(endIndex1 < 0)
			endIndex1 = subStrLen;
		endIndex = Math.min(endIndex, endIndex1);
		String postStr = subStr.substring(endIndex);
		String envVar = subStr.substring(0, endIndex);
		String value = System.getProperty(envVar);
		if(value == null)
			return path;
		return getPathByEnvVar(preStr + value + postStr);
	}

	/**
	 * 在一个列表中比配字符串。比如“AA,BB,^_^,哈哈”找“AA"返回true
	 * 
	 * @param word
	 *            要找的字符串
	 * @param wordList
	 *            字符串列表
	 * @param delim
	 *            分隔符
	 * @return 是否找到
	 */
	public static boolean containsItem(String word, String wordList, String delim){
		if(word == null)
			return false;
		StringTokenizer tok = new StringTokenizer(wordList, delim);
		while(tok.hasMoreTokens()){
			if(tok.nextToken().trim().equals(word)){
				return true;
			}
		}
		return false;
	}

	public static List<String> getFieldList(String cvsRecord, char fieldSeparator) throws Exception{
		return getFieldList(cvsRecord, String.valueOf(fieldSeparator));
	}

	public static List<String> getFieldList(String cvsRecord, String fieldSeparator) throws Exception{
		return getFieldList(cvsRecord, fieldSeparator, '"');
	}

	public static List<String> getFieldList(String cvsRecord, String fieldSeparator, char transf) throws Exception{
		boolean inQuotation = false;
		ArrayList<String> values = new ArrayList<String>();
		StringBuffer word = new StringBuffer(cvsRecord.length());
		char c = ' ';
		for(int i = 0; i < cvsRecord.length(); i++){
			c = cvsRecord.charAt(i);
			if(!inQuotation){
				if(cvsRecord.indexOf(fieldSeparator, i) == i){
					// if (c == FieldSeparator) {
					i += fieldSeparator.length() - 1;
					values.add(word.toString());
					word.delete(0, word.length());
				}else if(c == transf){
					inQuotation = true;
				}else{
					word.append(c);
				}
			}else{ // in a quotation
				if(c == transf){
					if(++i < cvsRecord.length()){
						c = cvsRecord.charAt(i);
						if(c == transf){
							word.append(c);
						}else if(cvsRecord.indexOf(fieldSeparator, i) == i){
							// } else if ( c == FieldSeparator) {
							i += fieldSeparator.length() - 1;
							values.add(word.toString());
							word.delete(0, word.length());
							inQuotation = false;
						}else{
							// word.append(transf);
							inQuotation = false;
							word.append(c);
						}
					}
				}else{
					word.append(c);
				}
			}
		}
		values.add(word.toString());
		return values;
	}

	public static boolean isIPV4(String s){
		try{
			String number = s.substring(0, s.indexOf('.'));
			if(Integer.parseInt(number) > 255)
				return false;
			s = s.substring(s.indexOf('.') + 1);
			number = s.substring(0, s.indexOf('.'));
			if(Integer.parseInt(number) > 255)
				return false;
			s = s.substring(s.indexOf('.') + 1);
			number = s.substring(0, s.indexOf('.'));
			if(Integer.parseInt(number) > 255)
				return false;
			number = s.substring(s.indexOf('.') + 1);
			if(Integer.parseInt(number) > 255)
				return false;
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public static String locateClassPath(String className){
		String str1 = className;
		if(!str1.startsWith("/")){
            str1 = "/" + str1;
		}
        str1 = str1.replace('.', '/');
        String str2 = str1 + ".properties";
        str1 = str1 + ".class";
        URL url = (new Object()).getClass().getResource(str1);
        if(url != null){
        	return "Class '" + str1 + "' found in '" + url.getFile() + "'";
        } else{
        	URL url1 = (new Object()).getClass().getResource(str2);
        	if(url1 != null){
        		return "Properties '" + str2 + "' found in '" + url1.getFile() + "'";
        	} else {
        		return "Class|Properties '" + className + "' not found in '" + System.getProperty("java.class.path") + "'";
        	}
        }
        
	}

	public static String getLocalIPAddress(){
		try{
			Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()){
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()){
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address){
						String address = ip.getHostAddress();
						if(!"127.0.0.1".equals(address.trim())){
							return address;
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "localhost";
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws Exception{
//		StringBuffer sb = new StringBuffer("tttt,NUll,hanullha,hanull,null,nullhaha,null,null,t3r23");
//		System.out.println(skipNull(sb).toString());
//		String s = "adfasfasd||afeiisss||898we4w||";
//		List<String> l = getFieldList(s, "||");
//		System.out.println(l.toString());
//		s = "adfasfasd||afeiisss||898we4w";
//		l = getFieldList(s, "||");
//		System.out.println(l.toString());
//		s = "||\"adfasf\"\"a\"\"s\"\"\"\"d||afeiisss\"||898we4w";
//		l = getFieldList(s, "||");
//		System.out.println(l.toString());
//		s = "\"adfasfasd||||afeiisss\"||898we4w";
//		l = getFieldList(s, '|');
//		System.out.println(l.toString());
//		s = "123:343:333:kkk:bbb";
//		for(int i = 0; i < 10; i++){
//			System.out.println(i + "=" + getField(s, ":", i));
//		}
//		System.out.println("---------------------");
//		s = ":343:333:kkk::bbb:::";
//		for(int i = 0; i < 10; i++){
//			System.out.println(i + "=" + getField(s, ":", i));
//		}
//		System.out.println("replace=" + replaceField(s, ":", 8, "haha"));
//		System.out.println("true=" + isIPV4("1.1.3.3"));
//		System.out.println("false=" + isIPV4("1.1.300.3"));
//		System.out.println("false=" + isIPV4("1.1.3"));
//		System.out.println("false=" + isIPV4("1.1..3"));
//		System.out.println("-----------------------------------");
//		s = "aaa bbb ccc\nddd\neee\tfff\rgggg\\nhhhh\\riiiii\\tjjjjjjjjj\tkkkkkkkk\rllllllllll\rmmmmmmmmmmm\nnnnnnnnnnnnnn";
//		System.out.println(s);
//		System.out.println(LibingUtils.replaceCRs(s));
//		System.out.println("-----------------------------------");
//		s = "aaa bbb ccc\nddd\neee\tfff\rgggg\\nhhhh\\riiiii\\tjjjjjjjjj\tkkkkkkkk\rllllllllll\rmmmmmmmmmmm\nnnnnnnnnnnnnn";
//		System.out.println(s);
//		System.out.println(LibingUtils.replaceCRs(s));
//		System.out.println("-----------------------------------");
//		s = "aaa,bbb,ccde,aadf";
//		System.out.println(s);
//		System.out.println(s.replaceAll(",", "<2C>"));
		
//		System.out.println(LibingUtils.locateClassPath("sun.security.rsa.RSASignature.MD5withRSA"));
		System.out.println(LibingUtils.getLocalIPAddress());
	}

	/**
	 * join up a column of the string array with the specified string
	 * 
	 * @param a
	 *            String array contains the data to be joined
	 * @param c
	 *            column which to be joined
	 * @param js
	 *            String used to join the array column with
	 * @return the array column join resulting string
	 */
	public static String join(String[][] a, int c, String js){
		if(a == null || a.length < 1)
			return null;
		if(js == null)
			js = "";
		StringBuffer s = new StringBuffer(a[0][c]);
		for(int i = 1; i < a.length; i++)
			s = s.append(js).append(a[i][c]);
		return s.toString();
	}

	/**
	 * join up the string array with the specified string
	 * 
	 * @param a
	 *            String array to be joined
	 * @param js
	 *            String used to join the array with
	 * @return the array join resulting string
	 */
	public static String join(String[] a, String js){
		if(a == null || a.length < 1)
			return null;
		if(js == null)
			js = "";
		StringBuffer s = new StringBuffer(a[0]);
		for(int i = 1; i < a.length; i++)
			s = s.append(js).append(a[i]);
		return s.toString();
	}

	/**
	 * join up the string form of int array with the specified string
	 * 
	 * @param a
	 *            int array to be joined
	 * @param js
	 *            String used to join the array with
	 * @return the array join resulting string
	 */
	public static String join(int[] a, String js){
		if(a == null || a.length < 1)
			return null;
		if(js == null)
			js = "";
		StringBuffer s = new StringBuffer("" + a[0]);
		for(int i = 1; i < a.length; i++)
			s = s.append(js).append(a[i]);
		return s.toString();
	}

	/**
	 * split the string to array according to the specified seperator
	 * 
	 * @param s
	 *            the string to be split
	 * @param sep
	 *            the string seperator
	 * @return the string array split from s with sep
	 */
	public static String[] split(String s, String sep){
		return split(s, sep, false, false);
	}

	/**
	 * split the string to two dimension array according to the specified
	 * seperators
	 * 
	 * @param s
	 *            the string to be split
	 * @param sep1
	 *            the string seperator for row
	 * @param sep2
	 *            the string seperator for column
	 * @return the string array split from s with sep1 and sep2
	 */
	public static String[][] split(String s, String sep1, String sep2){
		String r1[] = split(s, sep1);
		if(r1 == null)
			return null;
		String r2[][] = new String[r1.length][];
		for(int i = 0; i < r2.length; i++)
			r2[i] = split(r1[i], sep2);
		return r2;
	}

	/**
	 * split the string to array according to the specified seperator
	 * 
	 * @param s
	 *            the string to be split
	 * @param sep
	 *            the string seperator
	 * @param firstOnly
	 *            the string will be split for only once if true, the resulted
	 *            array will contain two elements atmost. and the string is
	 *            split from the location of where the sep first appears.
	 * @param ignoreCase
	 *            the seperator will be compared to the string case insensitive
	 *            if true
	 * @return the string array split from s with sep
	 */
	public static String[] split(String s, String sep, boolean firstOnly, boolean ignoreCase){
		if(s == null)
			return null;
		String[] a = {s};
		if(sep == null || sep.length() == 0)
			return a; // donot use evl() to make it possible for " " as sep, --
		// ken, 1-5-23 9:37
		Vector<String> v = new Vector<String>();
		int n, l = sep.length();
		while((n = indexof(s, sep, ignoreCase)) != -1){
			v.addElement(s.substring(0, n));
			s = s.substring(n + l);
			if(firstOnly)
				break;
		}
		v.addElement(s);
		a = new String[n = v.size()];
		for(int i = 0; i < n; i++)
			a[i] = (String)v.elementAt(i);
		return a;
	}

	/**
	 * s.indexOf(subs) with the case insensitive situation
	 * 
	 * @param s
	 *            the string to be checked in
	 * @param subs
	 *            the string to be checked for in s
	 * @param ignoreCase
	 *            case insensitive if true
	 * @return the first char offset of subs in s
	 */
	public static int indexof(String s, String subs, boolean ignoreCase){
		if(ignoreCase)
			return s.toLowerCase().indexOf(subs.toLowerCase());
		else
			return s.indexOf(subs);
	}

	/**
	 * @return ture when par is neither "" nor null
	 */
	public static boolean evl(String par){
		if(par == null || "".equals(par.trim()))
			return false;
		return true;
	}

	/**
	 * generate a random string contains only 0-9
	 * 
	 * @param minlen
	 *            the minimum length of the result string
	 * @param maxlen
	 *            the maximum length of the result string
	 */
	public static String getRandString(int minlen, int maxlen){
		String s = "";
		if(minlen > maxlen)
			minlen = maxlen;
		while(s.length() < maxlen)
			s = ("" + Math.random()).substring(2) + s;
		s = s.substring(s.length() - maxlen);
		int n = maxlen - minlen;
		if(n > 0)
			n = (int)Math.round(n * Math.random());
		if(n > 0)
			s = s.substring(n);
		return s;
		/*
		 * String rnd=""; while(rnd.length()<Math.max(maxlen,10))
		 * rnd+=(""+Math.random()).substring(2);
		 * n=Integer.parseInt(rnd.substring(0,1)); l=minlen+n%(maxlen-minlen+1);
		 * if(n==0) n=Integer.parseInt(rnd.substring(rnd.length()-1));
		 * rnd=rnd.substring(n)+rnd.substring(0,n);
		 * rnd=rnd.substring(l)+rnd.substring(0,l); return rnd.substring(0,l);
		 */
	}

	/**
	 * @return par when par is neither "" nor null, else def
	 */
	public static String nvl(String par, String def){
		if(par == null || "".equals(par.trim()))
			return def;
		return par;
	}

	/**
	 * case sensitive substitute for the first occurance of pt in s
	 * 
	 * @param s
	 *            original string to be substituted
	 * @param pt
	 *            string pattern to be substituted
	 * @param ns
	 *            string used to substitute the pattern
	 * @return the string after being substituted
	 */
	public static String substitute(String s, String pt, String ns){
		return substitute(s, pt, ns, "");
	}

	/**
	 * @param s
	 *            original string to be substituted
	 * @param pt
	 *            string pattern to be substituted
	 * @param ns
	 *            string used to substitute the pattern
	 * @param pr
	 *            substitution parameter, any string combination of: i: ignore
	 *            case g: global substitute
	 * @return the string after being substituted
	 */
	public static String substitute(String s, String pt, String ns, String pr){
		if(!evl(s) || !evl(pt))
			return s;
		if(ns == null)
			ns = "";
		if(pr == null)
			pr = "";
		else
			pr = pr.toLowerCase();
		boolean ignoreCase = pr.indexOf("i") > -1;
		boolean replaceAll = pr.indexOf("g") > -1;
		if(ignoreCase)
			if(replaceAll)
				return join(split(s, pt, false, true), ns); // pr=="ig"
			else
				return join(split(s, pt, true, true), ns); // pr=="i"
		else if(replaceAll)
			return join(split(s, pt), ns); // pr=="g"
		else
			return join(split(s, pt, true, false), ns); // pr==""
	}

	public static StringBuffer skipNull(StringBuffer sb){
		int count = 0;
		for(int i = 0; i < sb.length(); i++){
			switch(sb.charAt(i)){
				case '\n':
				case '\0':
					sb.delete(i, i + 1);
					i--;
					break;
				case ',':
					if(count == 4){
						sb.delete(i - 4, i);
						// System.out.println(sb.toString());
						i = i - 4;
					}
					count = 0;
					break;
				case 'n':
				case 'N':
					if(count == 0)
						count++;
					else
						count = -1;
					break;
				case 'u':
				case 'U':
					if(count == 1)
						count++;
					else
						count = -1;
					break;
				case 'l':
				case 'L':
					if(count == 2 || count == 3)
						count++;
					else
						count = -1;
					break;
				default:
					count = -1;
					break;
			}
		}
		if(count == 4){
			sb.delete(sb.length() - 4, sb.length());
		}
		return sb;
	}
	
	public static String getFormatTime(long millis, String format){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return new SimpleDateFormat(format).format(calendar.getTime());
	}
	
	public static String getFormatTime(String format){
		Calendar calendar = Calendar.getInstance();
		return new SimpleDateFormat("yyyyMMddHHmmss").format(calendar.getTime());
	}
	
	public static String trim0D0AStr(String s) {
		if (s == null)
			return s;
		s = s.trim();
		char[] c = s.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < c.length; i++){
			if (c[i] == 0x0d || c[i] == 0x0a){
				continue;
			}
			sb.append(c[i]);
		}
		return sb.toString();
	}

	/**
	 * 将单位为分的长整型转换为 3333.45 格式的字符串
	 * @param money
	 * @return
	 */
	public static String getFormatMoney(long money){
		double d = (double)money / 100;
		DecimalFormat fmt = new DecimalFormat("##0.00");
		return fmt.format(d);
	}
	
	// public static boolean evl(String par){
	// if(par==null || "".equals(par.trim())) return false;
	// return true;
	// }
	//
	// /**
	// @return par when par is neither "" nor null, else def
	// */
	// public static String nvl(String par,String def){
	// if(par==null || "".equals(par.trim())) return def;
	// return par;
	// }
}
