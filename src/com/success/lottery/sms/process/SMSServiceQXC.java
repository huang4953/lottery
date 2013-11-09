package com.success.lottery.sms.process;

import java.io.UnsupportedEncodingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.business.service.interf.BetServiceInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.sms.MO;
import com.success.lottery.sms.MT;
import com.success.lottery.sms.SMSDispatch;
import com.success.lottery.sms.SMSLog;
import com.success.lottery.util.LotteryTools;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.LibingUtils;

/**
 * 调用process完成排列三的投注，返回投注结果
 * 返回的字符串为sms.properties中配置的命令
 * @author bing.li
 *
 */
public class SMSServiceQXC extends SMSService{

	private Log logger = LogFactory.getLog(SMSServiceQXC.class.getName());
	private String resource = "com.success.lottery.sms.sms";
	private String dlm1 = AutoProperties.getString("dlm1", "[ ,，]", resource);
	private String dlm2 = AutoProperties.getString("dlm2", "[#＃]", resource);
	private String dlm3 = AutoProperties.getString("dlm3", "[-－]", resource);
	
	//123#234 -> 1*2*3^2*3*4
	//123-12-24#1-234-32 -> 123*12*24^1*234*32
	public String convertBetCodeFormat(String betCode){
		dlm2="[#＃]";
		dlm3="[-－]";
		if(betCode.indexOf("-") > 0 || betCode.indexOf("－") > 0){
			return betCode.replaceAll(dlm3, "*").replaceAll(dlm2, "^");
		}else{
			String str = "";
			for(int i = 0; i < betCode.length(); i++){
				if(betCode.charAt(i) == '#' || betCode.charAt(i) == '＃'){
					str = str.substring(0, str.length() - 1) + "^";
				}else{
					str = str + betCode.charAt(i) + "*";
				}
			}
			return str.substring(0, str.length() - 1);
		}
	}

	private int getBetMultiple(String ss){
		//mBnQ, mB, nQ, nQmB
		try{
			String s = ss.trim().toUpperCase();
			int posB = s.indexOf("B");
			int posQ = s.indexOf("Q");
			if(posB < 0){
				return 1;
			}
			if(posB < posQ){
				s = s.substring(0, posB);
			} else {
				if(posQ > 0){
					s = s.substring(posQ + 1, posB);
				} else {
					s = s.substring(0, posB);
				}
			}
			System.out.println(ss + "-b:" + s);
			return Integer.parseInt(s);
		}catch(Exception e){
			return -1;
		}
	}
	
	private int getBetTraceCount(String ss){
		//mBnQ, mB, nQ, nQmB
		try{
			String s = ss.trim().toUpperCase();
			int posB = s.indexOf("B");
			int posQ = s.indexOf("Q");
			if(posQ < 0){
				return 0;
			}
			if(posQ < posB){
				s = s.substring(0, posQ);
			} else {
				if(posB > 0){
					s = s.substring(posB + 1, posQ);
				} else {
					s = s.substring(0, posQ);
				}
			}
			System.out.println(ss + "-q:" + s);
			return Integer.parseInt(s);
		}catch(Exception e){
			return -1;
		}
	}
	
	/**
	 * 分为业务处理部分和短信下发部分 业务处理必须得到结果，并根据结果下发短信 短信必须发送，仅当MT放入队列出错时会无法发送
	 * 如果业务处理和短信下发都成功，则返回null 如果业务处理或短信下发有一种失败，则返回 以'&&' 分隔的字符串
	 * 前半部分为MO业务处理失败的错误信息，如成功则是空字符串； 后半部分为短信下发处理失败的错误信息，如果成功则是空字符串 如果返回 &&
	 * 分隔的两个字符串，则认为都成功。
	 */
	@Override
	public String process(String cmd, MO mo){
		MT mt = new MT(mo);
		mt.setChannelId(AutoProperties.getString(cmd + ".channel", "99", resource));
		mt.setInTime(mo.getOutTime());
		mt.setProcessorName(mo.getProcessorName());
		String moRs = null;
		String mtRs = null;
		String content = "正在投注";
		
		try{
			AccountService accountService = ApplicationContextUtils.getService("accountService", AccountService.class);
			UserAccountModel user = accountService.getUserInfo(mo.getFromPhone());
			if(!user.isBindMobileFlag()){
				// 没有定制手机服务
				content = AutoProperties.getString(cmd + ".unsubscribe", "未订购", resource);
			}else{
				int lotteryId = 1000002;
				int playType = 0;
				int betType = 0;
				int betMultiple = 1;
				int betTraceCount = 0;
				String betCode = "";
				String[] parameters = mo.getMsgContent().split(dlm1);
				if(parameters.length < 2){
					throw new LotteryException(880001, "短信投注格式错误");
				}else if(parameters.length == 2){
					if(!"10".equalsIgnoreCase(parameters[1].trim())){
						throw new LotteryException(880001, "短信投注格式错误");
					}else{
						//机选一注
						betCode = LotteryTools.lotteryRandomBetCode(lotteryId);
					}
				}else if(parameters.length >= 3){
					if("10".equalsIgnoreCase(parameters[1].trim())){
						//机选n注
						int count = 1;
						try{
							count = Integer.parseInt(parameters[2]);							
						}catch(Exception e){
							throw new LotteryException(880001, "短信投注格式错误");
						}
						for(int i = 0; i < count; i++){
							betCode = betCode + LotteryTools.lotteryRandomBetCode(lotteryId) + "^";
						}
						betCode = betCode.substring(0, betCode.length() - 1);
					}else if("11".equalsIgnoreCase(parameters[1].trim())){
						//直选单式 12345#24537#52523
						betCode = parameters[2].trim();
						betCode = this.convertBetCodeFormat(betCode);
						if(parameters.length >= 4){
							//处理多倍追号mBnQ
							betMultiple = getBetMultiple(parameters[3].trim());
							betTraceCount = getBetTraceCount(parameters[3].trim());
							if(betMultiple < 0 || betTraceCount < 0){
								throw new LotteryException(880001, "短信投注格式错误");
							}
						}
					}else{
						throw new LotteryException(880001, "短信投注格式错误");
					}
				}
				BetServiceInterf betService = ApplicationContextUtils.getService("busLotteryBetService", BetServiceInterf.class);
				String betResult = betService.betSms(user.getUserId(), lotteryId, playType, betType, betMultiple, betCode);
				
				String term = null, planNo = null, betCount = null;
				long amount = -1L, betMoney = 0L;
				try{
					String strs[] = betResult.split("#");
					term = strs[0].trim();
					planNo = strs[1].trim();
					betCount = strs[2].trim();
					try{
						betMoney = Long.parseLong(strs[3].trim());
					}catch(Exception e){
					}
					amount = user.getFundsAccount() + user.getPrizeAccount();
					amount = amount - betMoney;
					content = AutoProperties.getString(cmd + ".000000", "恭喜您，投注成功：", resource);
				}catch(Exception e){
					content = AutoProperties.getString(cmd + ".000001", "恭喜您，投注成功：", resource);
				}

				if(content.indexOf("{lotteryTerm}") >= 0 && term != null){
					content = content.replaceAll("\\{lotteryTerm\\}", term);
				}			
				if(content.indexOf("{planNo}") >= 0 && planNo != null){
					content = content.replaceAll("\\{planNo\\}", planNo);
				}
				if(content.indexOf("{betCount}") >= 0 && betCount != null){
					content = content.replaceAll("\\{betCount\\}", betCount);
				}
				if(content.indexOf("{betMoney}") >= 0 && betMoney > 0){
					content = content.replaceAll("\\{betMoney\\}", LibingUtils.getFormatMoney(betMoney));
				}
				if(content.indexOf("{betMultiple}") >= 0){
					content = content.replaceAll("\\{betMultiple\\}", "" + betMultiple);
				}
				if(content.indexOf("{betType}") >= 0){
					content = content.replaceAll("\\{betType\\}", "" + LotteryTools.getLotteryPlayBetTypeList(lotteryId, 0).get(betType));
				}
				if(content.indexOf("{amount}") >= 0 && amount >= 0){
					content = content.replaceAll("\\{amount\\}", LibingUtils.getFormatMoney(amount));
				}
			}
		}catch(LotteryException e){
			// 此处根据投注Service抛出的一场编号获得下发内容
			String finalErr = "手机彩票服务：系统出现异常，请稍候再试！更多信息请拨打4008096566或访问http://www.lottery360.cn。";
			content = AutoProperties.getString(cmd + "." + e.getType(), AutoProperties.getString(cmd + ".unknow", finalErr, resource), resource);
			logger.debug("SMS process occur exception:" + e);
			if(content.indexOf("{errMsg}") > 0){
				content = content.replaceAll("\\{errMsg\\}", e.toString());
				moRs = "Fail:" + content;
			}else{
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
		// 放入MT队列，记录日志
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
		//123#234 -> 1*2*3^2*3*4
		//123-12-24#1-234-32 -> 123*12*24^1*234*32
		SMSServicePLS pls = new SMSServicePLS();
		String betCode = "123#234";
		System.out.println("123#234 -> " + pls.convertBetCodeFormat(betCode));
		betCode = "123＃234";
		System.out.println("123＃234 -> " + pls.convertBetCodeFormat(betCode));
		betCode = "123-12-24#1-234-32";
		System.out.println("123-12-24#1-234-32 -> " + pls.convertBetCodeFormat(betCode));
		betCode = "123-12－24＃1-234-32";
		System.out.println("123-12－24＃1-234-32 -> " + pls.convertBetCodeFormat(betCode));
		System.out.println(SMSServicePLS.class.getName());
		System.out.println(Long.parseLong("3097184470"));
	}
}
