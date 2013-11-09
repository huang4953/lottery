package com.success.lottery.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.utils.AutoProperties;

public class AreaMapTools{
	private static Log logger = LogFactory.getLog("com.success.lottery.util.AreaMapTools");
	private static String areaConfig = "com.success.lottery.util.AreaMap";
	private static final String AREACODE_SEPARATOR = "\\|";//定义彩种的分隔符
	private static final String AREACODELIST_SUB_KEY = "AreaCodeList";

	/**
	 * 根据区域号，返回对应的区域名称
	 * @param areaCode 区域号 传入规则n加区域号
	 * @return 返回区域名称，null为出现异常或未找到
	 */
	public static String getAreaName(String areaCode){
		try{
			return AutoProperties.getString("n" + areaCode, null, areaConfig);
		}catch(Exception e){
			logger.error("getAreaName exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * 通过电话号码得到所在区域代码
	 * @param phone：要查找所在区域的电话号码
	 * @return 返回电话号码所在区域代码，未找到返回"00"，未知区域；
	 */   
	public static String getAreaCode(String phone) {
		String areaCode = "00";
		try{
			areaCode = AutoProperties.getStringX(phone, areaConfig);
		}catch(Exception e){
			areaCode = "00";
		}
		return areaCode;  
	}

	/**
	 * 通过AreaCodeList查出区域列表
	 * @return Map<区域编号, 地区名称>
	 */
	public static Map<String, String> getAreaCodeList(){
		Map<String, String> areaListMap = Collections.synchronizedSortedMap(new TreeMap<String, String>());
		try{
			String areaListStr = AutoProperties.getString(AREACODELIST_SUB_KEY, "", areaConfig);
			String[] areaCodeNames = areaListStr.split(AREACODE_SEPARATOR);
			for(String areaCodeName : areaCodeNames){
				String[] codeAndName = areaCodeName.split(",");
				areaListMap.put(codeAndName[0], codeAndName[1]);
			}
		}catch(Exception e){
			logger.error("AreaMapTools.getAreaCodeList occur Exception:" + e.getMessage());
		}
		return areaListMap;
	}
	
	public static void main(String[] args) {
		Map<String,String> areamap = getAreaCodeList(); //列表
		System.out.println(areamap);//取单一区域名
		String areaName =getAreaName("23");//取单一区域名
		System.out.println(areaName);//取单一区域名
		String s = getAreaCode("1376187");
		System.out.println(s);
	}
}
