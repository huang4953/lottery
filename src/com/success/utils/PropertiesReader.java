package com.success.utils;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertiesReader {
	
	 private static Log logger = LogFactory.getLog(ApplicationContextUtils.class);
	
	 public static void main(String dd[])throws Exception{
		 
     }
	 
	 public static String getApplicationConfPath(String filePath,String flag){
		 ResourceBundle resource;
		 
		 StringBuffer appFilePath = new StringBuffer();
		 
		 if(filePath == null || "".equals(filePath.trim())){
			 return "";
		 }
		 
		 try{
			 logger.debug("filePath = "+filePath);
			 resource = ResourceBundle.getBundle(filePath);
		 }catch(MissingResourceException e){
			 logger.debug("Œ¥’“µΩ≈‰÷√Œƒº˛springConfFile.properties");
			 logger.debug(e.getStackTrace());
			return "";
		 }
		 
		 Enumeration <String> enu = resource.getKeys();
		 
		 while(enu.hasMoreElements()) {
			 String key = enu.nextElement();
			 String value = resource.getString(key);
			 if(flag.equals(value)){
				 if(appFilePath.length() > 0){
					 appFilePath.append(",");
				 }
				 //appFilePath.append("\"");
				 appFilePath.append(key);
				 //appFilePath.append("\"");
			 }
		 } 
		 
		 logger.debug("appFilePath = " + appFilePath.toString());
		 
		 return appFilePath.toString();
	 }

}
