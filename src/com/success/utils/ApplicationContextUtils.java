package com.success.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *Description: ����������ʼ��springӦ�������ģ��ṩ��̬������ȡbeanʵ�� Copyright: Copyright (c) 2010
 * Company:XXXX���޹�˾
 * 
 * @version 1.0
 * @author gaoboqin
 * 
 */
public final class ApplicationContextUtils{

	/**
	 * ˽�й��캯��
	 * 
	 */
	private ApplicationContextUtils(){
	}

	private static Log			logger				= LogFactory.getLog(ApplicationContextUtils.class);
	private static String		springConfigfile	= "springConfFile";									// �����ļ�������
	private static final String	IS_VALID			= "1";
	static BeanFactory			factory				= null;
	/**
	 * ��̬��ʼ���飬��������spring�����ļ�����springӦ��������ApplicationContext
	 */
	static{
		logger.debug("*********************ApplicationContextUtils init ***********************");
		try{
			String AppFilePath = PropertiesReader.getApplicationConfPath(springConfigfile, IS_VALID);// ��ȡ�����ļ��õ������ݣ�Ϊ���õ�Ҫװ�ص�Spring�����ļ�
			logger.debug("AppFilePath = " + AppFilePath);
			/*
			 * ���û������װ����Щspring�ļ��������·��װ��������applicationContext��ͷ�������ļ�
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
	 * ������spring�����ĸ��ݴ�������ƻ�ȡbeanʵ��
	 * 
	 * @param serviceName
	 *            ������spring�����ļ��е�bean��ʶ
	 * @return Object ����Object����
	 * @exception �Ѿ�����NoSuchBeanDefinitionException
	 *                ��BeansException�쳣��������쳣��return������null
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
	 * ������spring�����ĸ��ݴ�������ƺ����ͷ���beanʵ��
	 * 
	 * @param <T>
	 * @param name
	 *            ������spring�����ļ��е�bean��ʶ
	 * @param type
	 *            bean������
	 * @return 
	 * 		���ػ�ȡ��beanʵ��������ݴ�������ͷ�����Ӧ�����ͣ�����Ҫǿ��ת��
	 * 		�޷����beanʵ������ִ����򷵻� null
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
