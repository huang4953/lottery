/**
 * @Title: InitApplicationContextListener.java
 * @Package com.success.utils
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-28 下午04:39:15
 * @version V1.0
 */
package com.success.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * com.success.utils
 * InitApplicationContextListener.java
 * InitApplicationContextListener
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-28 下午04:39:15
 * 
 */

public class InitApplicationContextListener implements ServletContextListener {

	/* (非 Javadoc)
	 *Title: contextDestroyed
	 *Description: 
	 * @param arg0
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		BeanFactory bf = (BeanFactory) ContextLoader.getCurrentWebApplicationContext();
		if (bf instanceof ConfigurableApplicationContext) {
			((ConfigurableApplicationContext)bf).close();
		}
	}

	/* (非 Javadoc)
	 *Title: contextInitialized
	 *Description: 
	 * @param arg0
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("@@@@@@@@@@@@@@@@@@000");
		System.out.println("context === "+ApplicationContextUtils.getInstance());
		System.out.println("@@@@@@@@@@@@@@@@@@111");

	}

}
