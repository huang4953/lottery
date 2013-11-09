package com.success.lottery.account.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.account.dao.IPSOrderDAO;
import com.success.lottery.account.model.IPSOrderModel;
import com.success.lottery.account.model.IPSReturnInfo;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.account.service.IPSOrderService;
import com.success.lottery.account.service.IPSTools;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.util.LotterySequence;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.LibingUtils;
public class IPSOrderServiceImpl implements IPSOrderService{

	private IPSOrderDAO		ipsOrderDao;
	private AccountService	accountService;
	private Log logger = LogFactory.getLog(IPSOrderServiceImpl.class.getName());
	private String dlm = AutoProperties.getString("logDelimiter", ",", IPSOrderService.resource);
	
	public IPSOrderDAO getIpsOrderDao(){
		return ipsOrderDao;
	}

	public void setIpsOrderDao(IPSOrderDAO ipsOrderDao){
		this.ipsOrderDao = ipsOrderDao;
	}

	public AccountService getAccountService(){
		return accountService;
	}

	public void setAccountService(AccountService accountService){
		this.accountService = accountService;
	}

	@Override
	public String addIPSOrder(long userId, int amount, String attach) throws LotteryException{
		try{
			IPSOrderModel ipsOrder = new IPSOrderModel();
			ipsOrder.setUserId(userId);
			ipsOrder.setAmount(amount);
			ipsOrder.setAttach(attach);
			ipsOrder.setOrderDate(Integer.parseInt(String.format("%1$tY%1$tm%1$td", Calendar.getInstance())));
			String orderId = LotterySequence.getInstatce("90").getSequence();
			ipsOrder.setOrderId(orderId);
			ipsOrderDao.addIPSOrder(ipsOrder);
			return orderId;
		}catch(Exception e){
			logger.error("addIPSOrder(" + userId + ", " + amount + ", " + attach + ") exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(IPSOrderService.ADDIPSORDEREXCEPTION, IPSOrderService.ADDIPSORDEREXCEPTION_STR + e.toString());
		}
	}

	@Override
	public IPSOrderModel getIPSOrder(String orderId) throws LotteryException{
		IPSOrderModel order = null;
		try{
			order = ipsOrderDao.getIPSOrder(orderId);
			if(order == null){
				throw new LotteryException(IPSOrderService.ORDERNOTFOUND, IPSOrderService.ORDERNOTFOUND_STR);
			}
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("getIPSOrder(" + orderId + ") exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(IPSOrderService.GETORDEREXCEPTION, IPSOrderService.GETORDEREXCEPTION_STR + e.toString());
		}
		return order;
	}
	
	@Override
	public int processIPSOrder(String orderId, String ipsBillNo, char succFlag, String ipsMsg, String ipsBankTime) throws LotteryException{
		IPSReturnInfo info = new IPSReturnInfo();
		info.setIpsOrderId(orderId);
		info.setBillNo(ipsBillNo);
		info.setSucc(succFlag);
		info.setMsg(ipsMsg);
		info.setIpsBankTime(ipsBankTime);
		return processIPSOrder(info);
	}

	@Override
	public int processIPSOrder(String orderId, String ipsBillNo, char succFlag, String ipsMsg, String ipsBankTime, String retEncodeType, String signature) throws LotteryException{
		IPSReturnInfo info = new IPSReturnInfo();
		info.setIpsOrderId(orderId);
		info.setBillNo(ipsBillNo);
		info.setSucc(succFlag);
		info.setMsg(ipsMsg);
		info.setIpsBankTime(ipsBankTime);
		info.setRetenCodeType(retEncodeType);
		info.setSignature(signature);
		return processIPSOrder(info);
	}
	
	public int processIPSOrder(IPSReturnInfo info) throws LotteryException{
		int rc = 0;
		if(info == null){
			throw new LotteryException(IPSOrderService.IPSRETURNERROR, IPSOrderService.IPSRETURNERROR_STR);
		}
		info.setInTime(System.currentTimeMillis());
		try{
			IPSOrderModel order = null;
			order = ipsOrderDao.getIPSOrderForUpdate(info.getBillNo());
			if(order == null){
				throw new LotteryException(IPSOrderService.ORDERNOTFOUND, IPSOrderService.ORDERNOTFOUND_STR);
			}
			//如果环迅返回信息中的签名方式为null则不校验
			if(info.getRetenCodeType() != null && !"".equals(info.getRetenCodeType().trim()) && !IPSTools.verificationSignature(info, info.getRetenCodeType())){
				info.addRemark("retEncodeType", info.getRetenCodeType());
				info.addRemark("signature", info.getSignature());
				throw new LotteryException(IPSOrderService.VERIFYIPSRETURNERROR, IPSOrderService.VERIFYIPSRETURNERROR_STR); 
			}
			if(order.getOrderStatus() != 0 && order.getIpsBillNo() != null && !"".equals(order.getIpsBillNo().trim())){
				info.addRemark("orderStatus", order.getOrderStatus() + "");
				info.addRemark("orderIpsBillNo", order.getOrderId());
				info.addRemark("checkedStatus", order.getCheckedStatus() + "");
				throw new LotteryException(IPSOrderService.DUPLICATIONIPSRETURN, IPSOrderService.DUPLICATIONIPSRETURN_STR);
			}else{
				float money = -1;
				try{
					money = Float.parseFloat(info.getAmount());
				}catch(Exception e){
					money = -1;
				}
				if(((int)(money * 100)) != order.getAmount()){
					info.addRemark("Amount", order.getAmount() + "");
					throw new LotteryException(IPSOrderService.AMOUNTERRORIPSRETURN, IPSOrderService.AMOUNTERRORIPSRETURN_STR);
				}
				if(info.getSucc() == 'Y' || info.getSucc() == 'y'){
					accountService.accountTransaction(order.getUserId(), AccountService.TS_ADDFUNDS, order.getAmount(), AccountService.SRC_IPS, order.getOrderId());
					order.setSuccFlag((byte)'Y');
					order.setOrderStatus(1);
				}
				if(info.getSucc() == 'N' || info.getSucc() == 'n'){
					order.setSuccFlag((byte)'N');
					order.setOrderStatus(3);
				}
				order.setCheckedStatus(1);
				order.setIpsBillNo(info.getIpsOrderId());
				order.setIpsBankTime(info.getIpsBankTime());
				order.setIpsMsg(info.getMsg());
				rc = ipsOrderDao.updateIPSOrder(order);
				if(rc != 1){
					throw new LotteryException(IPSOrderService.NOIPSORDERUPDATE, IPSOrderService.NOIPSORDERUPDATE_STR);
				}
			}
		}catch(LotteryException e){
			info.setResult(e.toString());
			log(info);
			throw e;
		}catch(Exception e){
			info.setResult(IPSOrderService.PROCESSIPSRETURNEXCEPTION_STR+ e.toString());
			log(info);
			logger.error("getIPSOrder(" + info.getIpsOrderId() + ") exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(IPSOrderService.PROCESSIPSRETURNEXCEPTION, IPSOrderService.PROCESSIPSRETURNEXCEPTION_STR+ e.toString());
		}
		return rc;
	}
	
	/**
	 * inTime, billNo, ipsOrderId, mercode, currencyType, amount, date, succ, msg, attach, retenCodeType, signature, ipsBankTime, remark, result 
	 * @param info
	 */
	private void log(IPSReturnInfo info){
		Log ipslogger = LogFactory.getLog("com.success.lottery.account.ips.IPS");
		StringBuffer sb=new StringBuffer();
		sb.append(LibingUtils.getFormatTime(info.getInTime(), "yyyy-MM-dd HH:mm:ss")).append(dlm);
		sb.append(info.getBillNo()).append(dlm);
		sb.append(info.getIpsOrderId()).append(dlm);
		sb.append(info.getMercode()).append(dlm);
		sb.append(info.getCurrencyType()).append(dlm);
		sb.append(info.getAmount()).append(dlm);
		sb.append(info.getDate()).append(dlm);
		sb.append(info.getSucc()).append(dlm);
		sb.append(info.getMsg()).append(dlm);
		sb.append(info.getAttach().replaceAll(dlm, "<2C>")).append(dlm);
		sb.append(info.getRetenCodeType()).append(dlm);
		sb.append(info.getSignature()).append(dlm);
		sb.append(info.getIpsBankTime()).append(dlm);
		info.trimSrcRemark();
		String remark = info.getRemark();
		remark = (remark == null ? "" : remark.trim().replaceAll(dlm, "<2C>"));
		sb.append(remark).append(dlm);
		if(StringUtils.isBlank(info.getResult())){
			sb.append("(OK)");
		}else{
			int trimLen =AutoProperties.getInt("log.trimLength", 200, IPSOrderService.resource);
			String result = LibingUtils.trim0D0AStr(info.getResult());
			if(result.length() > trimLen){
				sb.append(result.substring(0, trimLen));
			}else{
				sb.append(result);
			}
		}
		ipslogger.info(sb.toString());		
	}
	
	@Override
	public List<IPSOrderModel> getIPSOrderes(String userIdentify, Date startDate, Date endDate, int orderStatus, int start, int count) throws LotteryException{
		List<IPSOrderModel> result = null;
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp startTime = null, endTime = null;
			if(startDate != null){
				startTime = Timestamp.valueOf(format.format(startDate) + " 00:00:00");
			}
			if(endDate != null){
				endTime = Timestamp.valueOf(format.format(endDate) + " 23:59:59");
			}
			result = ipsOrderDao.getIPSOrderTotalInfo(userIdentify, startTime, endTime, orderStatus);
			IPSOrderModel total = new IPSOrderModel();
			if(result == null || result.size() == 0){
				result = new ArrayList<IPSOrderModel>();
				//orderDate - 总条数
				//amount - 非成功的条数
				//userId - 非成功的金额
				//orderStatus - 成功的条数
				//checkedStatus - 成功的金额
				total.setOrderDate(0);
				total.setAmount(0);
				total.setUserId(0);
				total.setOrderStatus(0);
				total.setCheckedStatus(0);
				result.add(0, total);
				return result;
			} else {
				int succCount = 0;
				int succAmount = 0;
				int failedCount = 0;
				long failedAmount = 0;
				for(IPSOrderModel order : result){
					if(order.getOrderStatus() == 1){
						succCount += order.getAmount();
						succAmount += order.getUserId();
					} else {
						failedCount += order.getAmount();
						failedAmount += order.getUserId();
					}
				}
				total.setOrderDate(succCount + failedCount);
				total.setAmount(failedCount);
				total.setUserId(failedAmount);
				total.setOrderStatus(succCount);
				total.setCheckedStatus(succAmount);

				result = ipsOrderDao.getIPSOrderes(userIdentify, startTime, endTime, orderStatus, start, count);
				if(result == null || result.size() == 0){
					result = new ArrayList<IPSOrderModel>();
					total.setOrderDate(0);
					total.setAmount(0);
					total.setUserId(0);
					total.setOrderStatus(0);
					total.setCheckedStatus(0);
					result.add(0, total);
					return result;
				} else {
					result.add(0, total);
				}
				return result;
			}
		}catch(Exception e){
			logger.error("getIPSOrderes(" + userIdentify + ", " + startDate + ", " + endDate + ", " + orderStatus + ", " + start + ", " + count + ") exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(IPSOrderService.GETORDERESEXCEPTION, IPSOrderService.GETORDERESEXCEPTION_STR + e.toString());
		}
	}


	public static void main(String[] args) throws LotteryException{
		
		IPSOrderService ipsService = ApplicationContextUtils.getService("ipsService", IPSOrderService.class);
		
		IPSReturnInfo info = new IPSReturnInfo();
		info.setIpsOrderId("90201005141911340001");
		info.setBillNo("IPS20000010022");
		info.setMercode("888888");
		info.setAmount("100.00");
		info.setDate(20100514);
		info.setSucc('Y');
		info.setMsg("test");
		info.setAttach("dasf");
		info.setIpsBankTime(String.format("%1$tY%1$tm%1$td%1$TH%1$TM%1$TS", Calendar.getInstance()));
		ipsService.processIPSOrder(info);
		IPSOrderModel order = ipsService.getIPSOrder("90201005141911340001");
		System.out.println(order.getIpsBillNo());
		System.out.println(order.getIpsBankTime());
	}

}
