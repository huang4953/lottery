/**
 * SyncNotifySPSoapBindingSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.unicom.vac.bossagent.soap;

public class SyncNotifySPSoapBindingSkeleton implements com.unicom.vac.bossagent.soap.SyncNotifySPService, org.apache.axis.wsdl.Skeleton {
    private com.unicom.vac.bossagent.soap.SyncNotifySPService impl;
    private static java.util.Map _myOperations = new java.util.Hashtable();
    private static java.util.Collection _myOperationsList = new java.util.ArrayList();

    /**
    * Returns List of OperationDesc objects with this name
    */
    public static java.util.List getOperationDescByName(java.lang.String methodName) {
        return (java.util.List)_myOperations.get(methodName);
    }

    /**
    * Returns Collection of OperationDescs
    */
    public static java.util.Collection getOperationDescs() {
        return _myOperationsList;
    }

    static {
        org.apache.axis.description.OperationDesc _oper;
        org.apache.axis.description.FaultDesc _fault;
        org.apache.axis.description.ParameterDesc [] _params;
        _params = new org.apache.axis.description.ParameterDesc [] {
            new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "orderRelationUpdateNotifyRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://req.sync.soap.bossagent.vac.unicom.com", "OrderRelationUpdateNotifyRequest"), com.unicom.vac.bossagent.soap.sync.req.OrderRelationUpdateNotifyRequest.class, false, false), 
        };
        _oper = new org.apache.axis.description.OperationDesc("orderRelationUpdateNotify", _params, new javax.xml.namespace.QName("", "orderRelationUpdateNotifyReturn"));
        _oper.setReturnType(new javax.xml.namespace.QName("http://rsp.sync.soap.bossagent.vac.unicom.com", "OrderRelationUpdateNotifyResponse"));
        _oper.setElementQName(new javax.xml.namespace.QName("http://soap.bossagent.vac.unicom.com", "orderRelationUpdateNotify"));
        _myOperationsList.add(_oper);
        if (_myOperations.get("orderRelationUpdateNotify") == null) {
            _myOperations.put("orderRelationUpdateNotify", new java.util.ArrayList());
        }
        ((java.util.List)_myOperations.get("orderRelationUpdateNotify")).add(_oper);
    }

    public SyncNotifySPSoapBindingSkeleton() {
        this.impl = new com.unicom.vac.bossagent.soap.SyncNotifySPSoapBindingImpl();
    }

    public SyncNotifySPSoapBindingSkeleton(com.unicom.vac.bossagent.soap.SyncNotifySPService impl) {
        this.impl = impl;
    }
    public com.unicom.vac.bossagent.soap.sync.rsp.OrderRelationUpdateNotifyResponse orderRelationUpdateNotify(com.unicom.vac.bossagent.soap.sync.req.OrderRelationUpdateNotifyRequest orderRelationUpdateNotifyRequest) throws java.rmi.RemoteException
    {
        com.unicom.vac.bossagent.soap.sync.rsp.OrderRelationUpdateNotifyResponse ret = impl.orderRelationUpdateNotify(orderRelationUpdateNotifyRequest);
        return ret;
    }

}
