package com.success.utils.ssmp;

import java.io.IOException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SSMPContextLoaderListener implements ServletContextListener{

	private Log logger = LogFactory.getLog(SSMPContextLoaderListener.class.getName());
	
	@Override
	public void contextDestroyed(ServletContextEvent event){
		SSMPLoader.stopAll();
		logger.info("SSMP is shutdown(notify all thread in SSMP to stop)");
	}

	@Override
	public void contextInitialized(ServletContextEvent event){
		String configFile = event.getServletContext().getRealPath("/") + event.getServletContext().getInitParameter("SSMPConfigLocation");
		SSMPLoader.setLoadFileName(configFile);
		logger.debug("SSMPLoader configFileName: " + SSMPLoader.getLoadFileName());		
		try{
			SSMPLoader.load();
		}catch(IOException e){
			logger.error("Failed: SSMPLoader exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		logger.info("SSMP is loaded!");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
	}
}
