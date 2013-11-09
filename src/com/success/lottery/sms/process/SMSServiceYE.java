package com.success.lottery.sms.process;

import java.io.UnsupportedEncodingException;
import org.apache.commons.lang.RandomStringUtils;
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
 * ����process���MO����
 * ���ص��ַ���Ϊsms.properties�����õ�����
 * @author bing.li
 *
 */
public class SMSServiceYE extends SMSService{

	private Log logger = LogFactory.getLog(SMSServiceYE.class.getName());
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
		String content = "���ڴ���";
		try{
			AccountService accountService = ApplicationContextUtils.getService("accountService", AccountService.class);
			UserAccountModel user = accountService.getUserInfo(mo.getFromPhone());
			if(!user.isBindMobileFlag()){
				//û�ж����ֻ�����
				content = AutoProperties.getString(cmd + ".unsubscribe", "δ����", resource);
			} else {
				long fundsAccount = user.getFundsAccount();
				long prizeAccount = user.getPrizeAccount();
				long frozenAccount = user.getFrozenAccount();
				content = AutoProperties.getString(cmd + ".000000", "��ϲ������������ɹ���", resource);
				if(content.indexOf("{fundsAccount}") >= 0){
					content = content.replaceAll("\\{fundsAccount\\}", LibingUtils.getFormatMoney(fundsAccount));
				}
				if(content.indexOf("{prizeAccount}") >= 0){
					content = content.replaceAll("\\{prizeAccount\\}", LibingUtils.getFormatMoney(prizeAccount));
				}
				if(content.indexOf("{frozenAccount}") >= 0){
					content = content.replaceAll("\\{frozenAccount\\}", LibingUtils.getFormatMoney(frozenAccount));
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
	}
}
