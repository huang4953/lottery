package com.success.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;




public class NetWorkUtil {
		/** �̳߳� */
	private static ExecutorService threadExec = Executors.newCachedThreadPool();

	/**
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String requestNet(String url) throws Exception {
		return requestNet(new GetMethod(url), "gbk", 0, 15);
	}

	/**
	 * @param url
	 * @param maxWait
	 *            ��ȴ���
	 * @return
	 * @throws Exception
	 */
	public static String requestNet(String url, int maxWait) throws Exception {
		return requestNet(new GetMethod(url), "gbk", 0, maxWait);
	}

	/**
	 * @param url
	 * @param code
	 *            ����
	 * @param maxWait
	 *            ��ȴ���
	 * @return
	 * @throws Exception
	 */
	public static String requestNet(String url, String code, int maxWait)
			throws Exception {
		return requestNet(new GetMethod(url), code, 0, maxWait);
	}


	/**
	 * @param url
	 * @param code
	 *            ����
	 * @param retryCount
	 *            ���Դ���
	 * @param maxWait
	 *            ��ȴ�����
	 * @return
	 * @throws Exception
	 */
	public static String requestNet(final HttpMethod method, String code,int retryCount, int maxWait) throws Exception {
		HttpClientParams clientParams = new HttpClientParams();
		clientParams.setParameter(HttpClientParams.HTTP_CONTENT_CHARSET, code);
		clientParams.setSoTimeout(maxWait * 1000);
		HttpClient client = new HttpClient(clientParams);
		return executeMethod(method, client, maxWait);
	}

	public static String executeMethod(final HttpMethod method,
			final HttpClient client, long maxWait) throws InterruptedException,
			ExecutionException {
		Future<String> future = threadExec.submit(new Callable<String>() {
			public String call() {
				try {
					client.executeMethod(method);
					return method.getResponseBodyAsString();
				} catch (Exception e) {
					return null;
				} finally {
					method.releaseConnection();
				}
			}
		});
		try {
			String date = future.get(maxWait, TimeUnit.SECONDS);
			return date;
		} catch (TimeoutException e) {
			future.cancel(true);
			return null;
		}

	}

	/**
	 * @param Я�����ݽű���Դ���ַ�����ʾ
	 * @param �������ݽű���Դ���ַ�����ʾ
	 * @param �������ݽű���Դ��java��
	 * 
	 * �������ϵĽű���Դ���н���
	 */
	public static void ParseJsToJava(String data, String parsejs, Object parseClass) throws ScriptException {

		ScriptEngineManager scriptEngineMgr = new ScriptEngineManager();
		ScriptEngine jsEngine = scriptEngineMgr.getEngineByName("JavaScript");

		jsEngine.put("result", parseClass);
		Compilable compilable = (Compilable) jsEngine;
		CompiledScript compiledScript = compilable.compile(data);
		compiledScript.eval();

		jsEngine.eval(parsejs);
	}
}
