package com.success.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *Description: 该类用来初始化spring应用上下文，提供静态方法获取bean实例 Copyright: Copyright (c) 2010
 * Company:XXXX有限公司
 * 
 * @version 1.0
 * @author gaoboqin
 * 
 */
public final class ApplicationContextUtils{

	/**
	 * 私有构造函数
	 * 
	 */
	private ApplicationContextUtils(){
	}

	private static Log			logger				= LogFactory.getLog(ApplicationContextUtils.class);
	private static String		springConfigfile	= "springConfFile";									// 属性文件的名称
	private static final String	IS_VALID			= "1";
	static BeanFactory			factory				= null;
	/**
	 * 静态初始化块，用来根据spring配置文件加载spring应用上下文ApplicationContext
	 */
	static{
		logger.debug("*********************ApplicationContextUtils init ***********************");
		try{
			String AppFilePath = PropertiesReader.getApplicationConfPath(springConfigfile, IS_VALID);// 读取属性文件得到的内容，为配置的要装载的Spring配置文件
			logger.debug("AppFilePath = " + AppFilePath);
			/*
			 * 如果没有配置装载那些spring文件，则从类路径装载所有以applicationContext开头的配置文件
			 */
			String applicationPath = (AppFilePath == null || AppFilePath.trim().length() == 0) ? "classpath*:**/applicationContext*.xml" : AppFilePath;
			logger.debug("applicationPath = " + applicationPath);
			String[] sprintFilePath = applicationPath.split(",");
			ApplicationContext context = new ClassPathXmlApplicationContext(sprintFilePath);
			factory = (BeanFactory)context;
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		logger.debug("*********************ApplicationContextUtils end***********************");
	}

	/**
	 * 用来从spring上下文根据传入的名称获取bean实例
	 * 
	 * @param serviceName
	 *            定义在spring配置文件中的bean标识
	 * @return Object 返回Object对象
	 * @exception 已经捕获NoSuchBeanDefinitionException
	 *                、BeansException异常，捕获该异常后return将返回null
	 * 
	 */
	public static Object getService(final String serviceName){
		try{
			return factory.getBean(serviceName);
		}catch(NoSuchBeanDefinitionException e){
			logger.error(e.getStackTrace());
			return null;
		}catch(BeansException ex){
			logger.error(ex.getStackTrace());
			return null;
		}
	}

	/**
	 * 用来从spring上下文根据传入的名称和类型返回bean实例
	 * 
	 * @param <T>
	 * @param name
	 *            定义在spring配置文件中的bean标识
	 * @param type
	 *            bean的类型
	 * @return 
	 * 		返回获取的bean实例，会根据传入的类型返回相应的类型，不需要强制转型
	 * 		无法获得bean实例或出现错误则返回 null
	 */
	public static <T>T getService(final String name, final Class<T> type){
		try{
			logger.debug("type = " + type.getClass().getName());
			return type.cast(factory.getBean(name, type));
		}catch(Exception e){
			logger.error("getService(name, type) exception: " + e);
			return null;
		}
	}

	public static BeanFactory getInstance(){
		return factory;
	}
}
