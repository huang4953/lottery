package com.success.lottery.account.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.account.dao.BillOrderDAO;
import com.success.lottery.account.model.AccountTransactionModel;
import com.success.lottery.account.model.BillOrderModel;
import com.success.lottery.account.model.IPSOrderModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.account.service.BillOrderService;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.util.LotterySequence;

public class BillOrderServiceImpl implements BillOrderService {
	
	private BillOrderDAO billOrderDAO;
	private AccountService	accountService;
	private Log logger = LogFactory.getLog(BillOrderServiceImpl.class.getName());
	
	public BillOrderDAO getBillOrderDAO() {
		return billOrderDAO;
	}
	public void setBillOrderDAO(BillOrderDAO billOrderDAO) {
		this.billOrderDAO = billOrderDAO;
	}
	
	@Override
	public String addBillOrder(long userId,String userName, long amount) throws LotteryException{
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMddHHmmss");
		try{
			String orderId = LotterySequence.getInstatce("99").getSequence();
			BillOrderModel orderModel=new BillOrderModel();
			orderModel.setOrderId(orderId);
			orderModel.setUserId(userId);
			orderModel.setUserName(userName);
			orderModel.setOrderAmount(amount);
			String orderDate=sf.format(new Date());
			orderModel.setOrderDate(orderDate);
			this.getBillOrderDAO().addBillOrder(orderModel);
			return orderId+"-"+orderDate;
		}catch(Exception e){
			logger.error("addBillOrder(" + userId + ", " + amount + ") exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(BillOrderService.ADDIPSORDEREXCEPTION, BillOrderService.ADDIPSORDEREXCEPTION_STR + e.toString());
		}
	}
	@Override
	public BillOrderModel getBillOrderByOrderId(String orderId) throws LotteryException {
		BillOrderModel model=this.getBillOrderDAO().findBillOrderByOrderid(orderId);
		if(model==null)
			   throw new LotteryException(this.ORDERNOTFOUND,this.ORDERNOTFOUND_STR);
		return model;
	}
	
	@Override
	public int processBILLOrder(String orderId,String dealId,String fee,String bankId,String bankDealId,String dealTime,String payAmount,String ext1,String ext2,String payResult,String errCode,String signMsg,String merchantSignMsg) throws LotteryException{
		int rc=0;
		BillOrderModel model=this.getBillOrderByOrderId(orderId);
		if(model.getOrderStatus()!=0)
			throw new LotteryException(BillOrderService.DUPLICATIONIPSRETURN,BillOrderService.DUPLICATIONIPSRETURN_STR);
	    if(bankId.equals("")||bankId.length()==0)
	    	bankId=null;
	    if(ext1.equals("")||ext1.length()==0)
	    	ext1=null;
	    if(ext2.equals("")||ext2.length()==0)
	    	ext2=null;
	    if(errCode.equals("")||errCode.length()==0)
	    	errCode=null;
		///首先进行签名字符串验证
	    if(signMsg.toUpperCase().equals(merchantSignMsg.toUpperCase())){
	    	///接着进行支付结果判断
	    	switch(Integer.parseInt(payResult)){
	    		  case 10:
	    			//添加明细
	    			this.getAccountService().accountTransaction(model.getUserId(), AccountService.TS_ADDFUNDS,(int)model.getOrderAmount(),AccountService.SRC_BILL, model.getOrderId());
	    	    	model.setOrderStatus(1);
	    			break;
	    		 default:
	    			model.setOrderStatus(3); 
	    			break;
	    	}
	    	
	    }else{
	    	model.setOrderStatus(2); 
	    }
	    //修改对账平衡
    	model.setCheckedStatus(1);
		model.setFee(Long.parseLong(fee));
		model.setBankId(bankId);
		model.setBankDealId(bankDealId);
		model.setDealTime(dealTime);
		model.setDealId(dealId);
		model.setExt1(ext1);
		model.setExt2(ext2);
    	model.setPayAmount(Integer.parseInt(payResult));
    	AccountTransactionModel accounttrans=this.getAccountService().getUserTransactionBySourceSequence(orderId, 1004);
    	if(accounttrans==null)
    		throw new LotteryException(ACCOUNTTRANSACTIONNOTFIND,ACCOUNTTRANSACTIONNOTFIND_STR);
    	model.setAccountTransactionId(accounttrans.getTransactionId());
    	rc=this.getBillOrderDAO().updateBillOrder(model);
		return rc;
	}
	public AccountService getAccountService() {
		return accountService;
	}
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	@Override
	public AccountTransactionModel getUserTransactionBySourceSequence(
			String sourceSequence, int sourceType) {
		// TODO Auto-generated method stub
		return this.getAccountService().getUserTransactionBySourceSequence(sourceSequence, sourceType);
	}
	@Override
	public List<BillOrderModel> getIPSOrderes(String userIdentify,
			Date startDate, Date endDate, int orderStatus, int start, int count)
			throws LotteryException {
		List<BillOrderModel> result = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp startTime = null, endTime = null;
			if(startDate != null){
				startTime = Timestamp.valueOf(format.format(startDate) + " 00:00:00");
			}
			if(endDate != null){
				endTime = Timestamp.valueOf(format.format(endDate) + " 23:59:59");
			}
			result=this.getBillOrderDAO().getBillOrderTotalInfo(userIdentify, startTime, endTime, orderStatus);
			BillOrderModel total = new BillOrderModel();
			if(result == null || result.size() == 0){
				result = new ArrayList<BillOrderModel>();
				//orderDate - 总条数
				//amount - 非成功的条数
				//userId - 非成功的金额
				//orderStatus - 成功的条数
				//checkedStatus - 成功的金额
				total.setMaxCount(0);
				total.setOrderAmount(0);
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
				for(BillOrderModel order : result){
					if(order.getOrderStatus() == 1){
						succCount += order.getOrderAmount();
						succAmount += order.getUserId();
					} else {
						failedCount += order.getOrderAmount();
						failedAmount += order.getUserId();
					}
				}
				total.setMaxCount(succCount + failedCount);
				total.setOrderAmount(failedCount);
				total.setUserId(failedAmount);
				total.setOrderStatus(succCount);
				total.setCheckedStatus(succAmount);
				result.add(0, total);

				result = this.getBillOrderDAO().getBillOrderes(userIdentify, startTime, endTime, orderStatus, start, count);
				if(result == null || result.size() == 0){
					result = new ArrayList<BillOrderModel>();
					total.setMaxCount(0);
					total.setOrderAmount(0);
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
	}
}
