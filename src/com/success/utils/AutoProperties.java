package com.success.utils;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 资源文件读取类，可读取类加载路径下的资源文件
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
	 * 读取资源文件中key的值，如果读取不到该key用def值替代并将得到的值转换为int类型，
	 * 资源文件应定义在classpath路径下
	 * @param key 资源文件中定义的key
	 * @param def 如果在资源文件中找不到定义的key，则用该值代替
	 * @param resource 资源文件的路径，对资源文件不加扩展名
	 * @return 得到的转换为int的value值
	 */

	public static int getInt(String key, int def, String resource) {
		try {
			return Integer.parseInt(getString(key, "" + def, resource));
		} catch (Exception e) {
			return def;
		}
	}
	/**
	 * 读取资源文件中key的值，如果读取不到该key用def值替代,资源文件应定义在classpath路径下
	 * @param key 资源文件中定义的key
	 * @param def 如果在资源文件中找不到定义的key，则用该值代替
	 * @param resource 资源文件的路径，对资源文件不加扩展名
	 * @return 得到的value值
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
	 * 读取资源文件中key的值，将key值与资源文件中的key进行比对，
	 * 如果找不到则将传入的key截去末尾的一个字符进行循环不对，如果最终未找到则返回null
	 * @param key 资源文件中定义的key
	 * @param resource 资源文件的路径，对资源文件不加扩展名
	 * @return 得到的value值或为null
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
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		}
//		*/
//		
//		String s = AutoProperties.getString("qingaobo", "2", "/WEB-INF/qin");
//		
//		System.out.println("s ==== "+ s);


	}
}
