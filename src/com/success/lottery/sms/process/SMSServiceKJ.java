package com.success.lottery.sms.process;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.business.service.interf.BetServiceInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.sms.MO;
import com.success.lottery.sms.MT;
import com.success.lottery.sms.SMSDispatch;
import com.success.lottery.sms.SMSLog;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.util.LotteryTools;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.LibingUtils;

/**
 * 调用process完成MO处理
 * 返回的字符串为sms.properties中配置的命令
 * @author bing.li
 *
 */
public class SMSServiceKJ extends SMSService{

	private Log logger = LogFactory.getLog(SMSServiceKJ.class.getName());
	private String resource = "com.success.lottery.sms.sms";
	private String dlm1 = AutoProperties.getString("dlm1", "[ ,，]", resource);
	private String dlm2 = AutoProperties.getString("dlm2", "[#＃]", resource);
	private String dlm3 = AutoProperties.getString("dlm3", "[-－]", resource);

	/**
	 * 分为业务处理部分和短信下发部分
	 * 业务处理必须得到结果，并根据结果下发短信
	 * 短信必须发送，仅当MT放入队列出错时会无法发送
	 * 如果业务处理和短信下发都成功，则返回null
	 * 如果业务处理或短信下发有一种失败，则返回 以'&&' 分隔的字符串
	 * 前半部分为MO业务处理失败的错误信息，如成功则是空字符串；
	 * 后半部分为短信下发处理失败的错误信息，如果成功则是空字符串
	 * 如果返回 && 分隔的两个空字符串，则认为都成功。
	 * @param cmd: 短信指令
	 * @param mo: MO上行短信；
	 * 		系统借用了MO.getOutTime()获得的是MOProcessor获得该MO的时间，下发短信时必须设置MT的inTime=MO.getOutTime;
	 * 		还需要使用MT.setProcessorName(mo.getProcessorName);来设置该条MT是由哪个Processor生成的
	 * @return: 
	 */
	@Override
	public String process(String cmd, MO mo){
		MT mt = new MT(mo);
		mt.setChannelId(AutoProperties.getString(cmd + ".channel", "99", resource));
		mt.setInTime(mo.getOutTime());
		mt.setProcessorName(mo.getProcessorName());
		
		String moRs = null;
		String mtRs = null;
		String content = null;
		try{
			AccountService accountService = ApplicationContextUtils.getService("accountService", AccountService.class);
			UserAccountModel user = accountService.getUserInfo(mo.getFromPhone());
			if(!user.isBindMobileFlag()){
				//没有定制手机服务
				content = AutoProperties.getString(cmd + ".unsubscribe", "未订购", resource);
			} else {
				LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
				String[] parameters = mo.getMsgContent().split(dlm1);
				LotteryTermModel term = null;
				int lotteryId = -1;
				if(parameters.length == 3){
					if("DLT".equalsIgnoreCase(parameters[1].trim())){
						lotteryId = 1000001;
					}else if("QXC".equalsIgnoreCase(parameters[1].trim())){
						lotteryId = 1000002;
					}else if("PLS".equalsIgnoreCase(parameters[1].trim())){
						lotteryId = 1000003;
					}else if("PLW".equalsIgnoreCase(parameters[1].trim())){
						lotteryId = 1000004;
					}else if("ZQC".equalsIgnoreCase(parameters[1].trim())){
						lotteryId = 1300001;
					}else{
						content = AutoProperties.getString(cmd + ".880001", "查询参数错误", resource);
					}
					term = termService.queryTermInfo(lotteryId, parameters[2].trim());
				}else{
					content = AutoProperties.getString(cmd + ".880001", "查询参数错误", resource);
				}

				if(term != null){
					content = AutoProperties.getString(cmd + ".000000", "查询到订单", resource);
					if(content.indexOf("{lotteryTerm}") >= 0){
						content = content.replaceAll("\\{lotteryTerm\\}", term.getTermNo());
					}
					if(content.indexOf("{lotteryName}") >= 0){
						content = content.replaceAll("\\{lotteryName\\}", LotteryTools.getLotteryName(term.getLotteryId()));
					}
					if(content.indexOf("{lotteryResult}") >= 0){
						content = content.replaceAll("\\{lotteryResult\\}", term.getLotteryResult());
					}
				}
				if(content == null){
					content = AutoProperties.getString(cmd + ".000001", "未查询到开奖信息", resource);
				}
			}
		}catch(LotteryException e){
			String finalErr = "手机彩票服务：系统出现异常，请稍候再试！更多信息请拨打4008096566或访问http://www.lottery360.cn。";
			content = AutoProperties.getString(cmd + "." + e.getType(), AutoProperties.getString(cmd + ".unknow", finalErr, resource), resource);
			logger.debug("SMS process occur exception:" + e);
			if(content.indexOf("{errMsg}") > 0){
				content = content.replaceAll("\\{errMsg\\}", e.toString());
				moRs = "Fail:" + content;
			} else {
				moRs = "Fail:" + content + ":" + e.toString();
			}
		}catch(Exception e){
			logger.error("sms D process unknow exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			content = AutoProperties.getString(cmd + ".unknow", null, resource);
			if(content.indexOf("{errMsg}") > 0){
				content = content.replaceAll("\\{errMsg\\}", e.toString());
			}
			moRs = "Exception:" + e.toString();
		}

		try{
			content = new String(content.getBytes(), "GBK");
		}catch(UnsupportedEncodingException e){
			logger.warn("MO Processor encoding content exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		mt.setMsgLength(content.getBytes().length);
		mt.setMsgContent(content);

		//放入MT队列，记录日志
		mtRs = SMSDispatch.dispatch(mt);
		mt.setErrMsg(mtRs);
		SMSLog.getInstance("MT").log(mt);
		
		if(moRs == null && mtRs == null){
			return null;
		}
		return (moRs == null ? "" : moRs) + "&&" + (mtRs == null ? "" : mtRs);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){
		String ss = "TZ";
		String[] parameters = ss.split("[ ,，]");
		System.out.println(parameters.length);
		ss = "TZ ";
		parameters = ss.split("[ ,，]");
		System.out.println(parameters.length);
		ss = "TZ adasfds";
		parameters = ss.split("[ ,，]");
		System.out.println(parameters.length);

		System.out.println(LibingUtils.getFormatTime(Calendar.getInstance().getTimeInMillis(), "yyyy年MM月dd日HH时mm分"));
	}
}
