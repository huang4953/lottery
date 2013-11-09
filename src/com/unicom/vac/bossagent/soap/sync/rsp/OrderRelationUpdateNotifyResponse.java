/**
 * OrderRelationUpdateNotifyResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.unicom.vac.bossagent.soap.sync.rsp;

public class OrderRelationUpdateNotifyResponse  implements java.io.Serializable {
    private java.lang.String recordSequenceId;

    private int resultCode;

    public OrderRelationUpdateNotifyResponse() {
    }

    public OrderRelationUpdateNotifyResponse(
           java.lang.String recordSequenceId,
           int resultCode) {
           this.recordSequenceId = recordSequenceId;
           this.resultCode = resultCode;
    }


    /**
     * Gets the recordSequenceId value for this OrderRelationUpdateNotifyResponse.
     * 
     * @return recordSequenceId
     */
    public java.lang.String getRecordSequenceId() {
        return recordSequenceId;
    }


    /**
     * Sets the recordSequenceId value for this OrderRelationUpdateNotifyResponse.
     * 
     * @param recordSequenceId
     */
    public void setRecordSequenceId(java.lang.String recordSequenceId) {
        this.recordSequenceId = recordSequenceId;
    }


    /**
     * Gets the resultCode value for this OrderRelationUpdateNotifyResponse.
     * 
     * @return resultCode
     */
    public int getResultCode() {
        return resultCode;
    }


    /**
     * Sets the resultCode value for this OrderRelationUpdateNotifyResponse.
     * 
     * @param resultCode
     */
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderRelationUpdateNotifyResponse)) return false;
        OrderRelationUpdateNotifyResponse other = (OrderRelationUpdateNotifyResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.recordSequenceId==null && other.getRecordSequenceId()==null) || 
             (this.recordSequenceId!=null &&
              this.recordSequenceId.equals(other.getRecordSequenceId()))) &&
            this.resultCode == other.getResultCode();
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
        if (getRecordSequenceId() != null) {
            _hashCode += getRecordSequenceId().hashCode();
        }
        _hashCode += getResultCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrderRelationUpdateNotifyResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://rsp.sync.soap.bossagent.vac.unicom.com", "OrderRelationUpdateNotifyResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recordSequenceId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recordSequenceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
