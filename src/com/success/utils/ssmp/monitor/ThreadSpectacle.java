package com.success.utils.ssmp.monitor;

import java.io.PrintWriter;

/**
 * 需要纳入SSMP管理的线程接口，当该对象的实例化是通过SSMPLoader完成时，SSMP通过本接口定义实现对各个线程的监控管理；
 * 在SSMPLoader的配置文件中通过以下配置可以启动该线程：
 * thread:threadName:threadClassName:parameters:
 * 其中 thread表明是一个线程
 * threadName:是该线程在SSMP中唯一的名称
 * threadClassName:为该线程的路径名称
 * parameters: 在配置文件中配置的该参数会SSMP会通过setParameter方法传递给要启动的线程
 * @author bing.li
 *
 */
public interface ThreadSpectacle extends Runnable{
	/**
	 * SSMP在实例化对象后，通过该方法将配置文件中配置的parameters传递给实例化的对象
	 * @param parameter
	 */
	public void setParameter(String parameter);
	/**
	 * SSMP中每个线程必须有一个唯一的名称，该名称在配置文件中配置，如果SSMP中已经存在一个同样名称的线程，将实例化失败。
	 * SSMP通过该方法通知线程，该线程在SSMP中的名称
	 * @param parameter
	 */
	public void setName(String name);
	
	/**
	 * 通过该方法返回线程在SSMP中注册时的名称，该方法或许并不为SSMP直接使用。
	 * @return
	 */
	public String getName();
	
	/**
	 * SSMP通过调用该方法停止线程
	 * @throws SSMPException
	 */
	public void stop() throws SSMPException;
	
	/**
	 * 该方法返回线程的一些基本信息用于SSMP监控线程情况，一般遵循一定的格式，格式如下：
	 * T - threadName - information - bootTime - other - isAlive
	 * @return
	 */
	public String showInfo();
	
	/**
	 * 该方法返回线程的一些详细信息用于SSMP监控线程情况，一般遵循一定的格式，格式如下：
	 * @return
	 */
	public String showDetail();

	/**
	 * 通过该方法获得线程是否还在运行，必须正确设置该方法确保返回线程的真实状态。
	 * @return
	 */
	public boolean isAlive();
	
	/**
	 * SSMP将监控命令通过该方法传递给线程，线程可解析命令，作出相应操作，并通过PrintWriter向SSMP监控平台打印信息
	 * @param command
	 * @param pw
	 */
	public void spectacle(String command, PrintWriter pw);
}
