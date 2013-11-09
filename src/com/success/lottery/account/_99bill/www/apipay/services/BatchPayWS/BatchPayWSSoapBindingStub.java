/**
 * BatchPayWSSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.success.lottery.account._99bill.www.apipay.services.BatchPayWS;

public class BatchPayWSSoapBindingStub extends org.apache.axis.client.Stub implements com.success.lottery.account._99bill.www.apipay.services.BatchPayWS.BatchPay {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[4];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryDeal");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "input"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "QueryRequestBean"), com.success.lottery.account.bill99.seashell.domain.dto.complatible.QueryRequestBean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "username"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ip"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/BatchPayWS", "ArrayOf_tns1_QueryResponseBean"));
        oper.setReturnClass(com.success.lottery.account.bill99.seashell.domain.dto.complatible.QueryResponseBean[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "queryDealReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("bankPay");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "input"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/BatchPayWS", "ArrayOf_tns1_BankRequestBean"), com.success.lottery.account.bill99.seashell.domain.dto.complatible.BankRequestBean[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "username"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ip"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/BatchPayWS", "ArrayOf_tns1_BankResponseBean"));
        oper.setReturnClass(com.success.lottery.account.bill99.seashell.domain.dto.complatible.BankResponseBean[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "bankPayReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("simplePay");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "input"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/BatchPayWS", "ArrayOf_tns1_SimpleRequestBean"), com.success.lottery.account.bill99.seashell.domain.dto.complatible.SimpleRequestBean[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "username"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ip"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/BatchPayWS", "ArrayOf_tns1_SimpleResponseBean"));
        oper.setReturnClass(com.success.lottery.account.bill99.seashell.domain.dto.complatible.SimpleResponseBean[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "simplePayReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("postPay");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "input"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/BatchPayWS", "ArrayOf_tns1_PostRequestBean"), com.success.lottery.account.bill99.seashell.domain.dto.complatible.PostRequestBean[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "username"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ip"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/BatchPayWS", "ArrayOf_tns1_PostResponseBean"));
        oper.setReturnClass(com.success.lottery.account.bill99.seashell.domain.dto.complatible.PostResponseBean[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "postPayReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

    }

    public BatchPayWSSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public BatchPayWSSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public BatchPayWSSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "BankRequestBean");
            cachedSerQNames.add(qName);
            cls = com.success.lottery.account.bill99.seashell.domain.dto.complatible.BankRequestBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "BankResponseBean");
            cachedSerQNames.add(qName);
            cls = com.success.lottery.account.bill99.seashell.domain.dto.complatible.BankResponseBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "PostRequestBean");
            cachedSerQNames.add(qName);
            cls = com.success.lottery.account.bill99.seashell.domain.dto.complatible.PostRequestBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "PostResponseBean");
            cachedSerQNames.add(qName);
            cls = com.success.lottery.account.bill99.seashell.domain.dto.complatible.PostResponseBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "QueryRequestBean");
            cachedSerQNames.add(qName);
            cls = com.success.lottery.account.bill99.seashell.domain.dto.complatible.QueryRequestBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "QueryResponseBean");
            cachedSerQNames.add(qName);
            cls = com.success.lottery.account.bill99.seashell.domain.dto.complatible.QueryResponseBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "SimpleRequestBean");
            cachedSerQNames.add(qName);
            cls = com.success.lottery.account.bill99.seashell.domain.dto.complatible.SimpleRequestBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "SimpleResponseBean");
            cachedSerQNames.add(qName);
            cls = com.success.lottery.account.bill99.seashell.domain.dto.complatible.SimpleResponseBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/BatchPayWS", "ArrayOf_tns1_BankRequestBean");
            cachedSerQNames.add(qName);
            cls = com.success.lottery.account.bill99.seashell.domain.dto.complatible.BankRequestBean[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "BankRequestBean");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/BatchPayWS", "ArrayOf_tns1_BankResponseBean");
            cachedSerQNames.add(qName);
            cls = com.success.lottery.account.bill99.seashell.domain.dto.complatible.BankResponseBean[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "BankResponseBean");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/BatchPayWS", "ArrayOf_tns1_PostRequestBean");
            cachedSerQNames.add(qName);
            cls = com.success.lottery.account.bill99.seashell.domain.dto.complatible.PostRequestBean[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "PostRequestBean");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/BatchPayWS", "ArrayOf_tns1_PostResponseBean");
            cachedSerQNames.add(qName);
            cls = com.success.lottery.account.bill99.seashell.domain.dto.complatible.PostResponseBean[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "PostResponseBean");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/BatchPayWS", "ArrayOf_tns1_QueryResponseBean");
            cachedSerQNames.add(qName);
            cls = com.success.lottery.account.bill99.seashell.domain.dto.complatible.QueryResponseBean[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "QueryResponseBean");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/BatchPayWS", "ArrayOf_tns1_SimpleRequestBean");
            cachedSerQNames.add(qName);
            cls = com.success.lottery.account.bill99.seashell.domain.dto.complatible.SimpleRequestBean[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "SimpleRequestBean");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/BatchPayWS", "ArrayOf_tns1_SimpleResponseBean");
            cachedSerQNames.add(qName);
            cls = com.success.lottery.account.bill99.seashell.domain.dto.complatible.SimpleResponseBean[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "SimpleResponseBean");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.success.lottery.account.bill99.seashell.domain.dto.complatible.QueryResponseBean[] queryDeal(com.success.lottery.account.bill99.seashell.domain.dto.complatible.QueryRequestBean input, java.lang.String username, java.lang.String ip) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://compatible.api.seashell.bill99.com", "queryDeal"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {input, username, ip});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.success.lottery.account.bill99.seashell.domain.dto.complatible.QueryResponseBean[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.success.lottery.account.bill99.seashell.domain.dto.complatible.QueryResponseBean[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.success.lottery.account.bill99.seashell.domain.dto.complatible.QueryResponseBean[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.success.lottery.account.bill99.seashell.domain.dto.complatible.BankResponseBean[] bankPay(com.success.lottery.account.bill99.seashell.domain.dto.complatible.BankRequestBean[] input, java.lang.String username, java.lang.String ip) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://compatible.api.seashell.bill99.com", "bankPay"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {input, username, ip});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.success.lottery.account.bill99.seashell.domain.dto.complatible.BankResponseBean[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.success.lottery.account.bill99.seashell.domain.dto.complatible.BankResponseBean[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.success.lottery.account.bill99.seashell.domain.dto.complatible.BankResponseBean[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.success.lottery.account.bill99.seashell.domain.dto.complatible.SimpleResponseBean[] simplePay(com.success.lottery.account.bill99.seashell.domain.dto.complatible.SimpleRequestBean[] input, java.lang.String username, java.lang.String ip) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://compatible.api.seashell.bill99.com", "simplePay"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {input, username, ip});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.success.lottery.account.bill99.seashell.domain.dto.complatible.SimpleResponseBean[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.success.lottery.account.bill99.seashell.domain.dto.complatible.SimpleResponseBean[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.success.lottery.account.bill99.seashell.domain.dto.complatible.SimpleResponseBean[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.success.lottery.account.bill99.seashell.domain.dto.complatible.PostResponseBean[] postPay(com.success.lottery.account.bill99.seashell.domain.dto.complatible.PostRequestBean[] input, java.lang.String username, java.lang.String ip) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://compatible.api.seashell.bill99.com", "postPay"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {input, username, ip});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.success.lottery.account.bill99.seashell.domain.dto.complatible.PostResponseBean[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.success.lottery.account.bill99.seashell.domain.dto.complatible.PostResponseBean[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.success.lottery.account.bill99.seashell.domain.dto.complatible.PostResponseBean[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
