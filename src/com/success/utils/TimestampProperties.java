package com.success.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.Properties;

public class TimestampProperties {

	private long loadTime;
	private Properties props;
	private String resName;
	private boolean isFile = false;
	private String fileName = null;
	private File file = null;
	
	public TimestampProperties(String name) throws MissingResourceException, IOException {
		this.resName = name;
		load();
	}

	public void load() throws MissingResourceException, IOException {
		props = new Properties();
		InputStream in = getInputStream();
		if (in == null){
			throw new MissingResourceException("Missing resource: " + resName, this.getClass().getName(), resName);
		}
		props.load(in);
		try {
			in.close();
		} catch (Exception e) {
		}
		loadTime = System.currentTimeMillis();
	}

	public void setLoadTime(long pLoadTime) {
		this.loadTime = pLoadTime;
	}

	public void setProperties(Properties pProps) {
		this.props = pProps;
	}

	public long getLoadTime() {
		return loadTime;
	}

	public Properties getProperties() {
		return props;
	}

	public String getResName() {
		return resName;
	}

	public InputStream getInputStream(){
		String res = resName.replace('.', '/') + ".properties";	
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(res);		
		if (in == null){
			return null;
		} else {
			getFile();
		}
		return in;
	}
	
	public File getFile(){
		String res = resName.replace('.', '/') + ".properties";
		URL url = Thread.currentThread().getContextClassLoader().getResource(res);
		if(url == null){
			return null;
		}
		String urlStr = url.toString();
		if(urlStr.startsWith("jar")) {			
			fileName = url.toString().substring(9, urlStr.indexOf("!"));
			return new File(fileName);
		}
		if(urlStr.startsWith("file")){
			isFile = true;
			fileName = url.getFile();
			return new File(fileName); 
		}
		return null;
	}
	
//	public File getFile() {
//		
//		String res = resName.replace('.', '/') + ".properties";
//		//URL u = ReloadProperties.class.getClassLoader().getResource(res);
//		URL u = TimestampProperties.class.getClassLoader().getResource(res);
//		if (u == null){
//			System.out.println("getFile URL u=null");
//			return null;
//		}
//		System.out.println("getFile URL=" + u.toString());
//		return new File(u.getFile());
////		System.out.println(resName);
////		URL u = this.getClass().getClassLoader().getResource(resName);
////		if (u == null){
////			System.out.println("getFile URL u=null");
////			return null;
////		}
////		return new File(u.getFile());
//	}
	
	public static void main(String a[]) throws MissingResourceException, IOException {
		TimestampProperties timeProp = new TimestampProperties("com.success.lottery.util.LotteryType");
//		timeProp.getFile();
//		timeProp.getInputStream();
	}
}
