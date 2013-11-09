/**
 * QueryRequestBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.success.lottery.account.bill99.seashell.domain.dto.complatible;

public class QueryRequestBean  implements java.io.Serializable {
    private java.lang.String dealBeginDate;

    private java.lang.String dealEndDate;

    private java.lang.String dealId;

    private java.lang.String orderId;

    private java.lang.String queryType;

    public QueryRequestBean() {
    }

    public QueryRequestBean(
           java.lang.String dealBeginDate,
           java.lang.String dealEndDate,
           java.lang.String dealId,
           java.lang.String orderId,
           java.lang.String queryType) {
           this.dealBeginDate = dealBeginDate;
           this.dealEndDate = dealEndDate;
           this.dealId = dealId;
           this.orderId = orderId;
           this.queryType = queryType;
    }


    /**
     * Gets the dealBeginDate value for this QueryRequestBean.
     * 
     * @return dealBeginDate
     */
    public java.lang.String getDealBeginDate() {
        return dealBeginDate;
    }


    /**
     * Sets the dealBeginDate value for this QueryRequestBean.
     * 
     * @param dealBeginDate
     */
    public void setDealBeginDate(java.lang.String dealBeginDate) {
        this.dealBeginDate = dealBeginDate;
    }


    /**
     * Gets the dealEndDate value for this QueryRequestBean.
     * 
     * @return dealEndDate
     */
    public java.lang.String getDealEndDate() {
        return dealEndDate;
    }


    /**
     * Sets the dealEndDate value for this QueryRequestBean.
     * 
     * @param dealEndDate
     */
    public void setDealEndDate(java.lang.String dealEndDate) {
        this.dealEndDate = dealEndDate;
    }


    /**
     * Gets the dealId value for this QueryRequestBean.
     * 
     * @return dealId
     */
    public java.lang.String getDealId() {
        return dealId;
    }


    /**
     * Sets the dealId value for this QueryRequestBean.
     * 
     * @param dealId
     */
    public void setDealId(java.lang.String dealId) {
        this.dealId = dealId;
    }


    /**
     * Gets the orderId value for this QueryRequestBean.
     * 
     * @return orderId
     */
    public java.lang.String getOrderId() {
        return orderId;
    }


    /**
     * Sets the orderId value for this QueryRequestBean.
     * 
     * @param orderId
     */
    public void setOrderId(java.lang.String orderId) {
        this.orderId = orderId;
    }


    /**
     * Gets the queryType value for this QueryRequestBean.
     * 
     * @return queryType
     */
    public java.lang.String getQueryType() {
        return queryType;
    }


    /**
     * Sets the queryType value for this QueryRequestBean.
     * 
     * @param queryType
     */
    public void setQueryType(java.lang.String queryType) {
        this.queryType = queryType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryRequestBean)) return false;
        QueryRequestBean other = (QueryRequestBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dealBeginDate==null && other.getDealBeginDate()==null) || 
             (this.dealBeginDate!=null &&
              this.dealBeginDate.equals(other.getDealBeginDate()))) &&
            ((this.dealEndDate==null && other.getDealEndDate()==null) || 
             (this.dealEndDate!=null &&
              this.dealEndDate.equals(other.getDealEndDate()))) &&
            ((this.dealId==null && other.getDealId()==null) || 
             (this.dealId!=null &&
              this.dealId.equals(other.getDealId()))) &&
            ((this.orderId==null && other.getOrderId()==null) || 
             (this.orderId!=null &&
              this.orderId.equals(other.getOrderId()))) &&
            ((this.queryType==null && other.getQueryType()==null) || 
             (this.queryType!=null &&
              this.queryType.equals(other.getQueryType())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDealBeginDate() != null) {
            _hashCode += getDealBeginDate().hashCode();
        }
        if (getDealEndDate() != null) {
            _hashCode += getDealEndDate().hashCode();
        }
        if (getDealId() != null) {
            _hashCode += getDealId().hashCode();
        }
        if (getOrderId() != null) {
            _hashCode += getOrderId().hashCode();
        }
        if (getQueryType() != null) {
            _hashCode += getQueryType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryRequestBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "QueryRequestBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealBeginDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dealBeginDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealEndDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dealEndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dealId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dealId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orderId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
