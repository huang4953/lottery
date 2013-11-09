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
	private static final String AREACODE_SEPARATOR = "\\|";//������ֵķָ���
	private static final String AREACODELIST_SUB_KEY = "AreaCodeList";

	/**
	 * ��������ţ����ض�Ӧ����������
	 * @param areaCode ����� �������n�������
	 * @return �����������ƣ�nullΪ�����쳣��δ�ҵ�
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
	 * ͨ���绰����õ������������
	 * @param phone��Ҫ������������ĵ绰����
	 * @return ���ص绰��������������룬δ�ҵ�����"00"��δ֪����
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
	 * ͨ��AreaCodeList��������б�
	 * @return Map<������, ��������>
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
		Map<String,String> areamap = getAreaCodeList(); //�б�
		System.out.println(areamap);//ȡ��һ������
		String areaName =getAreaName("23");//ȡ��һ������
		System.out.println(areaName);//ȡ��һ������
		String s = getAreaCode("1376187");
		System.out.println(s);
	}
}
