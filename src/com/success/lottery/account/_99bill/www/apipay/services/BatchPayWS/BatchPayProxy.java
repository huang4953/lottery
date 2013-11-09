package com.success.lottery.account._99bill.www.apipay.services.BatchPayWS;

public class BatchPayProxy implements com.success.lottery.account._99bill.www.apipay.services.BatchPayWS.BatchPay {
  private String _endpoint = null;
  private com.success.lottery.account._99bill.www.apipay.services.BatchPayWS.BatchPay batchPay = null;
  
  public BatchPayProxy() {
    _initBatchPayProxy();
  }
  
  public BatchPayProxy(String endpoint) {
    _endpoint = endpoint;
    _initBatchPayProxy();
  }
  
  private void _initBatchPayProxy() {
    try {
      batchPay = (new com.success.lottery.account._99bill.www.apipay.services.BatchPayWS.BatchPayServiceLocator()).getBatchPayWS();
      if (batchPay != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)batchPay)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)batchPay)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (batchPay != null)
      ((javax.xml.rpc.Stub)batchPay)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.success.lottery.account._99bill.www.apipay.services.BatchPayWS.BatchPay getBatchPay() {
    if (batchPay == null)
      _initBatchPayProxy();
    return batchPay;
  }
  
  public com.success.lottery.account.bill99.seashell.domain.dto.complatible.QueryResponseBean[] queryDeal(com.success.lottery.account.bill99.seashell.domain.dto.complatible.QueryRequestBean input, java.lang.String username, java.lang.String ip) throws java.rmi.RemoteException{
    if (batchPay == null)
      _initBatchPayProxy();
    return batchPay.queryDeal(input, username, ip);
  }
  
  public com.success.lottery.account.bill99.seashell.domain.dto.complatible.BankResponseBean[] bankPay(com.success.lottery.account.bill99.seashell.domain.dto.complatible.BankRequestBean[] input, java.lang.String username, java.lang.String ip) throws java.rmi.RemoteException{
    if (batchPay == null)
      _initBatchPayProxy();
    return batchPay.bankPay(input, username, ip);
  }
  
  public com.success.lottery.account.bill99.seashell.domain.dto.complatible.SimpleResponseBean[] simplePay(com.success.lottery.account.bill99.seashell.domain.dto.complatible.SimpleRequestBean[] input, java.lang.String username, java.lang.String ip) throws java.rmi.RemoteException{
    if (batchPay == null)
      _initBatchPayProxy();
    return batchPay.simplePay(input, username, ip);
  }
  
  public com.success.lottery.account.bill99.seashell.domain.dto.complatible.PostResponseBean[] postPay(com.success.lottery.account.bill99.seashell.domain.dto.complatible.PostRequestBean[] input, java.lang.String username, java.lang.String ip) throws java.rmi.RemoteException{
    if (batchPay == null)
      _initBatchPayProxy();
    return batchPay.postPay(input, username, ip);
  }
  
  
}