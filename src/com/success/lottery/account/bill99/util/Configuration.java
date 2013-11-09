package com.success.lottery.account.bill99.util;

import java.util.ResourceBundle;

 
/**
 * @author lulijun
 *
 */
public class Configuration {
	

	private static Object lock              = new Object();
	private static Configuration config     = null;
	private static ResourceBundle rb        = null;
	private static final String CONFIG_FILE = "merchantInfo_99bill";
	
	private Configuration() {
		rb = ResourceBundle.getBundle(CONFIG_FILE);
	}
	
	public static Configuration getInstance() {
		synchronized(lock) {
			if(null == config) {
				config = new Configuration();
			}
		}
		return (config);
	}
	
	public String getKey(String key) {
		return rb.getString(key);
	}
}
