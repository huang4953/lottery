/**
 * BatchPayServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.success.lottery.account._99bill.sandbox.apipay.services.BatchPayWS;

public class BatchPayServiceLocator extends org.apache.axis.client.Service implements com.success.lottery.account._99bill.sandbox.apipay.services.BatchPayWS.BatchPayService {

    public BatchPayServiceLocator() {
    }


    public BatchPayServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BatchPayServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BatchPayWS
    private java.lang.String BatchPayWS_address = "http://sandbox.99bill.com/apipay/services/BatchPayWS";

    public java.lang.String getBatchPayWSAddress() {
        return BatchPayWS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BatchPayWSWSDDServiceName = "BatchPayWS";

    public java.lang.String getBatchPayWSWSDDServiceName() {
        return BatchPayWSWSDDServiceName;
    }

    public void setBatchPayWSWSDDServiceName(java.lang.String name) {
        BatchPayWSWSDDServiceName = name;
    }

    public com.success.lottery.account._99bill.sandbox.apipay.services.BatchPayWS.BatchPay getBatchPayWS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BatchPayWS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBatchPayWS(endpoint);
    }

    public com.success.lottery.account._99bill.sandbox.apipay.services.BatchPayWS.BatchPay getBatchPayWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.success.lottery.account._99bill.sandbox.apipay.services.BatchPayWS.BatchPayWSSoapBindingStub _stub = new com.success.lottery.account._99bill.sandbox.apipay.services.BatchPayWS.BatchPayWSSoapBindingStub(portAddress, this);
            _stub.setPortName(getBatchPayWSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBatchPayWSEndpointAddress(java.lang.String address) {
        BatchPayWS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.success.lottery.account._99bill.sandbox.apipay.services.BatchPayWS.BatchPay.class.isAssignableFrom(serviceEndpointInterface)) {
                com.success.lottery.account._99bill.sandbox.apipay.services.BatchPayWS.BatchPayWSSoapBindingStub _stub = new com.success.lottery.account._99bill.sandbox.apipay.services.BatchPayWS.BatchPayWSSoapBindingStub(new java.net.URL(BatchPayWS_address), this);
                _stub.setPortName(getBatchPayWSWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BatchPayWS".equals(inputPortName)) {
            return getBatchPayWS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sandbox.99bill.com/apipay/services/BatchPayWS", "BatchPayService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sandbox.99bill.com/apipay/services/BatchPayWS", "BatchPayWS"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BatchPayWS".equals(portName)) {
            setBatchPayWSEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
