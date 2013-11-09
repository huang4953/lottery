/**
 * BatchPay.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.success.lottery.account._99bill.www.apipay.services.BatchPayWS;

public interface BatchPay extends java.rmi.Remote {
    public com.success.lottery.account.bill99.seashell.domain.dto.complatible.QueryResponseBean[] queryDeal(com.success.lottery.account.bill99.seashell.domain.dto.complatible.QueryRequestBean input, java.lang.String username, java.lang.String ip) throws java.rmi.RemoteException;
    public com.success.lottery.account.bill99.seashell.domain.dto.complatible.BankResponseBean[] bankPay(com.success.lottery.account.bill99.seashell.domain.dto.complatible.BankRequestBean[] input, java.lang.String username, java.lang.String ip) throws java.rmi.RemoteException;
    public com.success.lottery.account.bill99.seashell.domain.dto.complatible.SimpleResponseBean[] simplePay(com.success.lottery.account.bill99.seashell.domain.dto.complatible.SimpleRequestBean[] input, java.lang.String username, java.lang.String ip) throws java.rmi.RemoteException;
    public com.success.lottery.account.bill99.seashell.domain.dto.complatible.PostResponseBean[] postPay(com.success.lottery.account.bill99.seashell.domain.dto.complatible.PostRequestBean[] input, java.lang.String username, java.lang.String ip) throws java.rmi.RemoteException;
}
