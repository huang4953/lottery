package com.success.utils;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ��Դ�ļ���ȡ�࣬�ɶ�ȡ�����·���µ���Դ�ļ�
 * @author 
 * @version
 *
 */
@SuppressWarnings("unchecked")
public class AutoProperties {

	public static long delayTime = 30000;

	
	private static Hashtable propsTable = new Hashtable();

	private static Log log = LogFactory.getLog(AutoProperties.class);
	static {
		try {
			delayTime = Long.parseLong(System
					.getProperty("file.delay", "30000"));
		} catch (Exception e) {
		}
	}

	public synchronized static Properties getInstance(String resName)
			throws MissingResourceException, IOException {
		if (resName == null)
			new MissingResourceException("resource name is null",
					AutoProperties.class.getName(), "null");
		TimestampProperties timeProp = (TimestampProperties) propsTable
				.get(resName);
		if (timeProp == null) {
			timeProp = new TimestampProperties(resName);
			propsTable.put(resName, timeProp);
			return timeProp.getProperties();
		}
		if (needReload(timeProp)) {
			timeProp.load();
		}
		return timeProp.getProperties();
	}
	/**
	 * 
	 * @param timeProp
	 * @return
	 */

	private static boolean needReload(TimestampProperties timeProp) {
		File aFile = timeProp.getFile();
		return aFile.lastModified() != timeProp.getLoadTime();
//		boolean isNew = aFile.lastModified() != timeProp.getLoadTime();
//		boolean isDelayEnough = aFile.lastModified() + delayTime < System
//				.currentTimeMillis();
//		if (isNew && isDelayEnough) {
//			log.info("resource: " + timeProp.getResName() + " last modified: "
//					+ aFile.lastModified() + " last load: "
//					+ timeProp.getLoadTime() + " delay setting: " + delayTime
//					+ " current: " + System.currentTimeMillis());
//		}
//		return isNew && isDelayEnough;
	}
	
	/**
	 * ��ȡ��Դ�ļ���key��ֵ�������ȡ������key��defֵ��������õ���ֵת��Ϊint���ͣ�
	 * ��Դ�ļ�Ӧ������classpath·����
	 * @param key ��Դ�ļ��ж����key
	 * @param def �������Դ�ļ����Ҳ��������key�����ø�ֵ����
	 * @param resource ��Դ�ļ���·��������Դ�ļ�������չ��
	 * @return �õ���ת��Ϊint��valueֵ
	 */

	public static int getInt(String key, int def, String resource) {
		try {
			return Integer.parseInt(getString(key, "" + def, resource));
		} catch (Exception e) {
			return def;
		}
	}
	/**
	 * ��ȡ��Դ�ļ���key��ֵ�������ȡ������key��defֵ���,��Դ�ļ�Ӧ������classpath·����
	 * @param key ��Դ�ļ��ж����key
	 * @param def �������Դ�ļ����Ҳ��������key�����ø�ֵ����
	 * @param resource ��Դ�ļ���·��������Դ�ļ�������չ��
	 * @return �õ���valueֵ
	 */

	public static String getString(String key, String def, String resource) {
		Log logger = LogFactory.getLog(AutoProperties.class.getName() + "." + resource);
		Properties props;
		try {
			props = getInstance(resource);
			String res = (String) props.getProperty(key);
			if (res == null) {
				return def;
			} else {
				return res;
			}
		} catch (Exception e) {
			logger.error("getInstance(" + resource + ") occur Exception:"
					+ e.toString());

			if (logger.isDebugEnabled()) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	/**
	 * ��ȡ��Դ�ļ���key��ֵ����keyֵ����Դ�ļ��е�key���бȶԣ�
	 * ����Ҳ����򽫴����key��ȥĩβ��һ���ַ�����ѭ�����ԣ��������δ�ҵ��򷵻�null
	 * @param key ��Դ�ļ��ж����key
	 * @param resource ��Դ�ļ���·��������Դ�ļ�������չ��
	 * @return �õ���valueֵ��Ϊnull
	 * @throws MissingResourceException
	 * @throws IOException
	 * 
	 */

	public static String getStringX(String key, String resource)
			throws MissingResourceException, IOException {
		Properties props = getInstance(resource);
		String res = null;
		for (int i = key.length(); i > 0; i--) {
			res = (String) props.getProperty(key.substring(0, i));
			if (res != null) {
				return res;
			}
		}
		return res;
	}

	public static void main(String a[]) {
//		/*
//		try {
//			String x = AutoProperties.getStringX("13761874366",
//					"com.success.sms.fee.fee_blkmap");
//			System.out.println(x);
//		} catch (MissingResourceException e) {
//			// TODO �Զ����� catch ��
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO �Զ����� catch ��
//			e.printStackTrace();
//		}
//		*/
//		
//		String s = AutoProperties.getString("qingaobo", "2", "/WEB-INF/qin");
//		
//		System.out.println("s ==== "+ s);


	}
}
