/**
 * Title: BallSchedulerService.java
 * @Package com.success.lottery.term.service.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-19 上午10:40:26
 * @version V1.0
 */
package com.success.lottery.term.service.impl;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

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
 * <br>配置格式为：服务名称#线程休眠时间
 * <br>胜负彩和任选9的当前期截至配置为：Ball14StopTermSwitchService#10000
 * <br>胜负彩和任选9的预售期开始配置为:Ball14BeginTermSwitchService#10000
 * <br>6场半全场的当前期截至配置为:Ball6StopTermSwitchService#10000
 * <br>6场半全场的预售期开始配置为:Ball6BeginTermSwitchService#10000
 * <br>4场进球彩的当前期截至配置为:Ball4StopTermSwitchService#10000
 * <br>4场进球彩的预售期开始配置为:Ball4BeginTermSwitchService#10000
 * @author gaoboqin
 * 2010-4-19 上午10:40:26
 * 
 */

public class BallSchedulerService implements ThreadSpectacle {
	
	private static TermLog logger = TermLog.getInstance("BTS");
	private String name;
	
	private boolean isStop;
	private boolean isExit = false;
	
	private String bootTime;
	private int interval = 10000;
	
	private String termServiceName;
	

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
		bootTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		if(!StringUtils.isBlank(parameter)){
			this.setTermServiceName(parameter.split("#")[0]);
			this.setInterval(Integer.parseInt(parameter.split("#")[1]));
		}else{
			logger.logInfo(0, "", "足彩彩期切换线程参数没有设置");
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
		sb.append("termServiceName=" + this.termServiceName).append(",interval=").append(this.interval).append(", isExit=").append(this.isExit).append("\n");
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
		return "T - " + name + " - " + bootTime + " - " + isAlive();
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
		this.isStop = true;

	}

	/* (非 Javadoc)
	 *Title: run
	 *Description: 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		logger.logInfo(0, "", name + " is running...");
		while (!isStop) {
			try {
				LotteryTSScheduler tsScheduler = new LotteryTSScheduler();
				LotteryTermSwitchServiceInterf termService = ApplicationContextUtils.getService(termServiceName, LotteryTermSwitchServiceInterf.class);
				tsScheduler.setTermService(termService);
				tsScheduler.termSwitchScheduler();
				Thread.sleep(interval);
			} catch (Exception e) {
				logger.logInfo(0, "", "ballScheduler exception:"+e.toString());
				if (logger.isDebugEnabled()) {
					e.printStackTrace();
				}
			}
		}
		isExit = true;
		logger.logInfo(0, "", name + " is shutdown!");
	}

	public String getTermServiceName() {
		return termServiceName;
	}

	public void setTermServiceName(String termServiceName) {
		this.termServiceName = termServiceName;
	}
	
	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	public static void main(String[] args) {
		ThreadSpectacle th = new BallSchedulerService();
		th.setParameter("mySwrvice:10000");
		new Thread(th).start();
	}

}


