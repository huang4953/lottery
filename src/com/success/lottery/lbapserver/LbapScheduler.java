/**
 * Title: BallSchedulerService.java
 * @Package com.success.lottery.term.service.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-19 上午10:40:26
 * @version V1.0
 */
package com.success.lottery.lbapserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.business.service.impl.BetService;
import com.success.lottery.lbapserver.LbapHttpServer.MyHttpHandler;
import com.success.lottery.term.service.TermLog;
import com.success.lottery.term.service.interf.LotteryTermSwitchServiceInterf;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.ssmp.monitor.SSMPException;
import com.success.utils.ssmp.monitor.ThreadSpectacle;

/**
 * com.success.lottery.term.service.impl
 * BallSchedulerService.java
 * BallSchedulerService
 * 参数配置：<br>
 * <br>配置格式为：服务上下文#端口号#最大请求数#线程休眠时间
 * <br>httpServer配置为：thread:wapHttpServer:com.success.lottery.lbapserver.LbapScheduler:lbap#6666#200#10000:
 * @author gaoboqin
 * 
 * 
 */

public class LbapScheduler implements ThreadSpectacle {
	
	//private static TermLog logger = TermLog.getInstance("LBAP");
	private static Log logger = LogFactory.getLog(LbapScheduler.class);
	
	private static LbapHttpServer lbapServer = null;
	
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
				this.setServiceContext(parameter.split("#")[0]);
				this.setPort(Integer.parseInt(parameter.split("#")[1]));
				this.setReqNum(Integer.parseInt(parameter.split("#")[2]));
				this.setInterval(Integer.parseInt(parameter.split("#")[3]));
			}else{
				logger.info("lbap http server 服务参数配置错误");
			}
		} catch (NumberFormatException e) {
			logger.info("lbap http server 服务参数配置错误");
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
		if(lbapServer != null){
			lbapServer.stop();
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
			lbapServer = LbapHttpServer.getInstance(this.getPort(), this.getReqNum());
			lbapServer.createContext(this.getServiceContext(),new LbapHttpHandler());
			lbapServer.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		while (!this.isStop) {
			try {
				//System.out.println("sleep...."+this.isStop);
				Thread.sleep(this.interval);
			} catch (Exception e) {
				logger.error("lbap http server is error :", e);
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
	
	
	
	public static void main(String[] args) {
		LbapScheduler th = new LbapScheduler();
		th.setParameter("lbap#6666#10#30000");
		
		while (true) {
			System.out.println("y[start]/n[stop]：");
			String str = "";
			try {
				BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
				str = buf.readLine();
				if (str.equalsIgnoreCase("y")) {
					System.out.println("启动服务");
					th.run();
				} else if (str.equalsIgnoreCase("n")) {
					System.out.println("正在停止服务");
					th.stop();
					System.out.println("is stopd");
					System.exit(-1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}


