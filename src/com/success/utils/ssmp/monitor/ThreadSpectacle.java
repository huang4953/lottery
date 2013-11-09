package com.success.utils.ssmp.monitor;

import java.io.PrintWriter;

/**
 * ��Ҫ����SSMP������߳̽ӿڣ����ö����ʵ������ͨ��SSMPLoader���ʱ��SSMPͨ�����ӿڶ���ʵ�ֶԸ����̵߳ļ�ع���
 * ��SSMPLoader�������ļ���ͨ���������ÿ����������̣߳�
 * thread:threadName:threadClassName:parameters:
 * ���� thread������һ���߳�
 * threadName:�Ǹ��߳���SSMP��Ψһ������
 * threadClassName:Ϊ���̵߳�·������
 * parameters: �������ļ������õĸò�����SSMP��ͨ��setParameter�������ݸ�Ҫ�������߳�
 * @author bing.li
 *
 */
public interface ThreadSpectacle extends Runnable{
	/**
	 * SSMP��ʵ���������ͨ���÷����������ļ������õ�parameters���ݸ�ʵ�����Ķ���
	 * @param parameter
	 */
	public void setParameter(String parameter);
	/**
	 * SSMP��ÿ���̱߳�����һ��Ψһ�����ƣ��������������ļ������ã����SSMP���Ѿ�����һ��ͬ�����Ƶ��̣߳���ʵ����ʧ�ܡ�
	 * SSMPͨ���÷���֪ͨ�̣߳����߳���SSMP�е�����
	 * @param parameter
	 */
	public void setName(String name);
	
	/**
	 * ͨ���÷��������߳���SSMP��ע��ʱ�����ƣ��÷���������ΪSSMPֱ��ʹ�á�
	 * @return
	 */
	public String getName();
	
	/**
	 * SSMPͨ�����ø÷���ֹͣ�߳�
	 * @throws SSMPException
	 */
	public void stop() throws SSMPException;
	
	/**
	 * �÷��������̵߳�һЩ������Ϣ����SSMP����߳������һ����ѭһ���ĸ�ʽ����ʽ���£�
	 * T - threadName - information - bootTime - other - isAlive
	 * @return
	 */
	public String showInfo();
	
	/**
	 * �÷��������̵߳�һЩ��ϸ��Ϣ����SSMP����߳������һ����ѭһ���ĸ�ʽ����ʽ���£�
	 * @return
	 */
	public String showDetail();

	/**
	 * ͨ���÷�������߳��Ƿ������У�������ȷ���ø÷���ȷ�������̵߳���ʵ״̬��
	 * @return
	 */
	public boolean isAlive();
	
	/**
	 * SSMP���������ͨ���÷������ݸ��̣߳��߳̿ɽ������������Ӧ��������ͨ��PrintWriter��SSMP���ƽ̨��ӡ��Ϣ
	 * @param command
	 * @param pw
	 */
	public void spectacle(String command, PrintWriter pw);
}
