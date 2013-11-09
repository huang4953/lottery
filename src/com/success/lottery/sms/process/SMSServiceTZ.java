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
import com.success.lottery.util.LotteryTools;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.LibingUtils;

/**
 * ����process���MO����
 * ���ص��ַ���Ϊsms.properties�����õ�����
 * @author bing.li
 *
 */
public class SMSServiceTZ extends SMSService{

	private Log logger = LogFactory.getLog(SMSServiceTZ.class.getName());
	private String resource = "com.success.lottery.sms.sms";
	private String dlm1 = AutoProperties.getString("dlm1", "[ ,��]", resource);
	private String dlm2 = AutoProperties.getString("dlm2", "[#��]", resource);
	private String dlm3 = AutoProperties.getString("dlm3", "[-��]", resource);

	/**
	 * ��Ϊҵ�����ֺͶ����·�����
	 * ҵ�������õ�����������ݽ���·�����
	 * ���ű��뷢�ͣ�����MT������г���ʱ���޷�����
	 * ���ҵ����Ͷ����·����ɹ����򷵻�null
	 * ���ҵ���������·���һ��ʧ�ܣ��򷵻� ��'&&' �ָ����ַ���
	 * ǰ�벿��ΪMOҵ����ʧ�ܵĴ�����Ϣ����ɹ����ǿ��ַ�����
	 * ��벿��Ϊ�����·�����ʧ�ܵĴ�����Ϣ������ɹ����ǿ��ַ���
	 * ������� && �ָ����������ַ���������Ϊ���ɹ���
	 * @param cmd: ����ָ��
	 * @param mo: MO���ж��ţ�
	 * 		ϵͳ������MO.getOutTime()��õ���MOProcessor��ø�MO��ʱ�䣬�·�����ʱ��������MT��inTime=MO.getOutTime;
	 * 		����Ҫʹ��MT.setProcessorName(mo.getProcessorName);�����ø���MT�����ĸ�Processor���ɵ�
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
				//û�ж����ֻ�����
				content = AutoProperties.getString(cmd + ".unsubscribe", "δ����", resource);
			} else {
				BetPlanOrderServiceInterf orderService = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
				String[] parameters = mo.getMsgContent().split(dlm1);
				BetOrderDomain order = null;
				if(parameters.length == 1){
					List<BetOrderDomain> orders = orderService.getUserOrders(user.getUserId(), -1, null, -1, 800, -1, null, null, 0, 1);
					if(orders == null || orders.size() < 1){
						content = AutoProperties.getString(cmd + ".880002", "��ѯ���ؿ�", resource);
					}else if(orders.size() == 1){
						content = AutoProperties.getString(cmd + ".000001", "δ��ѯ��Ͷע��¼", resource);
					}else{
						order = orders.get(1);
					}
				}else if(parameters.length > 1){
					String orderId = parameters[1].trim();
					order = orderService.queryBetOrderByOrderId(orderId);
					if(order != null && order.getUserId() != user.getUserId()){
						order = null;
						content = AutoProperties.getString(cmd + ".880003", "�����Ǳ��˶���", resource);
					}
				}else{
					content = AutoProperties.getString(cmd + ".880001", "��ѯ��������", resource);
				}
				if(order != null){
					content = AutoProperties.getString(cmd + ".000000", "��ѯ������", resource);
					if(content.indexOf("{orderTime}") >= 0){
						content = content.replaceAll("\\{orderTime\\}", LibingUtils.getFormatTime(order.getOrderTime().getTime(), "yyyy��MM��dd��HHʱmm��"));
					}
					if(content.indexOf("{lotteryTerm}") >= 0){
						content = content.replaceAll("\\{lotteryTerm\\}", order.getBetTerm());
					}			
					if(content.indexOf("{betType}") >= 0){
						content = content.replaceAll("\\{betType\\}", LotteryTools.getLotteryPlayBetTypeList(order.getLotteryId(), order.getPlayType()).get(order.getBetType()));
					}
					if(content.indexOf("{lotteryName}") >= 0){
						content = content.replaceAll("\\{lotteryName\\}", LotteryTools.getLotteryName(order.getLotteryId()));
					}
					if(content.indexOf("{betCode}") >= 0){
						content = content.replaceAll("\\{betCode\\}", order.getBetCode());
					}
					if(content.indexOf("{betMoney}") >= 0){
						content = content.replaceAll("\\{betMoney\\}", LibingUtils.getFormatMoney((long)order.getBetAmount()));
					}
				}
				if(content == null){
					content = AutoProperties.getString(cmd + ".000001", "δ��ѯ��Ͷע��¼", resource);
				}
			}
		}catch(LotteryException e){
			String finalErr = "�ֻ���Ʊ����ϵͳ�����쳣�����Ժ����ԣ�������Ϣ�벦��4008096566�����http://www.lottery360.cn��";
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

		//����MT���У���¼��־
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
		String[] parameters = ss.split("[ ,��]");
		System.out.println(parameters.length);
		ss = "TZ ";
		parameters = ss.split("[ ,��]");
		System.out.println(parameters.length);
		ss = "TZ adasfds";
		parameters = ss.split("[ ,��]");
		System.out.println(parameters.length);

		System.out.println(LibingUtils.getFormatTime(Calendar.getInstance().getTimeInMillis(), "yyyy��MM��dd��HHʱmm��"));
	}
}
