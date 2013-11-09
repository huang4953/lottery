/**
 * Title: EhandScheduler.java
 * @Package com.success.lottery.ehandserver
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-18 上午11:24:52
 * @version V1.0
 */
package com.success.lottery.ehand.ehandserver.httpserver;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.utils.ssmp.monitor.SSMPException;
import com.success.utils.ssmp.monitor.ThreadSpectacle;

/**
 * com.success.lottery.ehandserver
 * EhandScheduler.java
 * EhandScheduler
 * 
 * *参数配置：<br>
 * <br>配置格式为：服务上下文#端口号#最大请求数#线程休眠时间
 * <br>httpServer配置为：thread:ehandHttpServer:com.success.lottery.ehandserver.EhandScheduler:ehand#7777#200#10000:
 * @author gaoboqin
 * 2011-1-18 上午11:24:52
 * 
 */

public class EhandScheduler implements ThreadSpectacle {
	private static Log logger = LogFactory.getLog(EhandScheduler.class);
	
	private static EhandHttpServer ehandServer = null;
	
	private String name;
	private boolean isStop;
	private boolean isExit = false;
	
	private String bootTime;
	
	private int interval = 60000;
	
	private String serviceContext;//服务上下文
	private int port;//端口号
	private int reqNum;//最大请求数
	

	/* (非 Javadoc)
	 *Title: getName
	 *Description: 
	 * @return
	 * @see com.success.utils.ssmp.monitor.ThreadSpectacle#getName()
	 */
	public String getName() {
		return this.name;
	}

	/* (非 Javadoc)
	 *Title: isAlive
	 *Description: 
	 * @return
	 * @see com.success.utils.ssmp.monitor.ThreadSpectacle#isAlive()
	 */
	public boolean isAlive() {
		return !isStop && !isExit;
	}

	/* (非 Javadoc)
	 *Title: setName
	 *Description: 
	 * @param name
	 * @see com.success.utils.ssmp.monitor.ThreadSpectacle#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (非 Javadoc)
	 *Title: setParameter
	 *Description: 
	 * @param parameter
	 * @see com.success.utils.ssmp.monitor.ThreadSpectacle#setParameter(java.lang.String)
	 */
	public void setParameter(String parameter) {
		this.bootTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		try {
			if(StringUtils.isNotBlank(parameter)){
				this.setServiceContext("");
				this.setPort(Integer.parseInt(parameter.split("#")[0]));
				this.setReqNum(Integer.parseInt(parameter.split("#")[1]));
				this.setInterval(Integer.parseInt(parameter.split("#")[2]));
			}else{
				logger.info("ehand http server 服务参数配置错误");
			}
		} catch (NumberFormatException e) {
			logger.info("ehand http server 服务参数配置错误");
			e.printStackTrace();
		}
	}

	/* (非 Javadoc)
	 *Title: showDetail
	 *Description: 
	 * @return
	 * @see com.success.utils.ssmp.monitor.ThreadSpectacle#showDetail()
	 */
	public String showDetail() {
		StringBuffer sb = new StringBuffer();
		sb.append(showInfo() + "\n");
		sb.append("\t").append(this.name + "'s Parameter:").append("\n");
		sb.append("\t\t");
		sb.append("termServiceName=" + this.getServiceContext()).append(",port=").append(this.port).append(",reqNum=").append(this.reqNum)
		.append(",interval=").append(this.interval).append(", isExit=").append(this.isExit).append("\n");
		sb.append("\t\t");
		sb.append("isStop=" + this.isStop).append(" , ").append("isAlive=").append(this.isAlive()).append("\n");
		sb.append("\t\t");
		sb.append("to be done");
		return sb.toString();
	}

	/* (非 Javadoc)
	 *Title: showInfo
	 *Description: 
	 * @return
	 * @see com.success.utils.ssmp.monitor.ThreadSpectacle#showInfo()
	 */
	public String showInfo() {
		return "T - " + this.name + " - " + this.bootTime + " - " + isAlive();
	}

	/* (非 Javadoc)
	 *Title: spectacle
	 *Description: 
	 * @param command
	 * @param pw
	 * @see com.success.utils.ssmp.monitor.ThreadSpectacle#spectacle(java.lang.String, java.io.PrintWriter)
	 */
	public void spectacle(String command, PrintWriter pw) {
		// TODO 自动生成方法存根

	}

	/* (非 Javadoc)
	 *Title: stop
	 *Description: 
	 * @throws SSMPException
	 * @see com.success.utils.ssmp.monitor.ThreadSpectacle#stop()
	 */
	public void stop() throws SSMPException {
		if(ehandServer != null){
			ehandServer.stop();
		}
		this.isStop = true;
	}

	/* (非 Javadoc)
	 *Title: run
	 *Description: 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		logger.info(this.name+" is runing");
		try {
			ehandServer = EhandHttpServer.getInstance(this.getPort(), this.getReqNum());
			ehandServer.createContext(this.getServiceContext(),new EhandHttpHandler());
			ehandServer.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		while (!this.isStop) {
			try {
				Thread.sleep(this.interval);
			} catch (Exception e) {
				logger.error("ehand http server is error :", e);
			}
		}
		this.isExit = true;
		logger.info(name + " is shutdown!");
	}
	
	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getReqNum() {
		return reqNum;
	}

	public void setReqNum(int reqNum) {
		this.reqNum = reqNum;
	}

	public String getServiceContext() {
		return serviceContext;
	}

	public void setServiceContext(String serviceContext) {
		this.serviceContext = serviceContext;
	}

}
