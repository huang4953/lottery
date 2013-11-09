/**
 * OrderRelationUpdateNotifyRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.unicom.vac.bossagent.soap.sync.req;

public class OrderRelationUpdateNotifyRequest  implements java.io.Serializable {
    private java.lang.String recordSequenceId;

    private java.lang.Integer userIdType;

    private java.lang.String userId;

    private java.lang.String serviceType;

    private java.lang.String spId;

    private java.lang.String productId;

    private java.lang.Integer updateType;

    private java.lang.String updateTime;

    private java.lang.String updateDesc;

    private java.lang.String linkId;

    private java.lang.String content;

    private java.lang.String effectiveDate;

    private java.lang.String expireDate;

    private java.lang.String time_stamp;

    private java.lang.String encodeStr;

    public OrderRelationUpdateNotifyRequest() {
    }

    public OrderRelationUpdateNotifyRequest(
           java.lang.String recordSequenceId,
           java.lang.Integer userIdType,
           java.lang.String userId,
           java.lang.String serviceType,
           java.lang.String spId,
           java.lang.String productId,
           java.lang.Integer updateType,
           java.lang.String updateTime,
           java.lang.String updateDesc,
           java.lang.String linkId,
           java.lang.String content,
           java.lang.String effectiveDate,
           java.lang.String expireDate,
           java.lang.String time_stamp,
           java.lang.String encodeStr) {
           this.recordSequenceId = recordSequenceId;
           this.userIdType = userIdType;
           this.userId = userId;
           this.serviceType = serviceType;
           this.spId = spId;
           this.productId = productId;
           this.updateType = updateType;
           this.updateTime = updateTime;
           this.updateDesc = updateDesc;
           this.linkId = linkId;
           this.content = content;
           this.effectiveDate = effectiveDate;
           this.expireDate = expireDate;
           this.time_stamp = time_stamp;
           this.encodeStr = encodeStr;
    }


    /**
     * Gets the recordSequenceId value for this OrderRelationUpdateNotifyRequest.
     * 
     * @return recordSequenceId
     */
    public java.lang.String getRecordSequenceId() {
        return recordSequenceId;
    }


    /**
     * Sets the recordSequenceId value for this OrderRelationUpdateNotifyRequest.
     * 
     * @param recordSequenceId
     */
    public void setRecordSequenceId(java.lang.String recordSequenceId) {
        this.recordSequenceId = recordSequenceId;
    }


    /**
     * Gets the userIdType value for this OrderRelationUpdateNotifyRequest.
     * 
     * @return userIdType
     */
    public java.lang.Integer getUserIdType() {
        return userIdType;
    }


    /**
     * Sets the userIdType value for this OrderRelationUpdateNotifyRequest.
     * 
     * @param userIdType
     */
    public void setUserIdType(java.lang.Integer userIdType) {
        this.userIdType = userIdType;
    }


    /**
     * Gets the userId value for this OrderRelationUpdateNotifyRequest.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this OrderRelationUpdateNotifyRequest.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }


    /**
     * Gets the serviceType value for this OrderRelationUpdateNotifyRequest.
     * 
     * @return serviceType
     */
    public java.lang.String getServiceType() {
        return serviceType;
    }


    /**
     * Sets the serviceType value for this OrderRelationUpdateNotifyRequest.
     * 
     * @param serviceType
     */
    public void setServiceType(java.lang.String serviceType) {
        this.serviceType = serviceType;
    }


    /**
     * Gets the spId value for this OrderRelationUpdateNotifyRequest.
     * 
     * @return spId
     */
    public java.lang.String getSpId() {
        return spId;
    }


    /**
     * Sets the spId value for this OrderRelationUpdateNotifyRequest.
     * 
     * @param spId
     */
    public void setSpId(java.lang.String spId) {
        this.spId = spId;
    }


    /**
     * Gets the productId value for this OrderRelationUpdateNotifyRequest.
     * 
     * @return productId
     */
    public java.lang.String getProductId() {
        return productId;
    }


    /**
     * Sets the productId value for this OrderRelationUpdateNotifyRequest.
     * 
     * @param productId
     */
    public void setProductId(java.lang.String productId) {
        this.productId = productId;
    }


    /**
     * Gets the updateType value for this OrderRelationUpdateNotifyRequest.
     * 
     * @return updateType
     */
    public java.lang.Integer getUpdateType() {
        return updateType;
    }


    /**
     * Sets the updateType value for this OrderRelationUpdateNotifyRequest.
     * 
     * @param updateType
     */
    public void setUpdateType(java.lang.Integer updateType) {
        this.updateType = updateType;
    }


    /**
     * Gets the updateTime value for this OrderRelationUpdateNotifyRequest.
     * 
     * @return updateTime
     */
    public java.lang.String getUpdateTime() {
        return updateTime;
    }


    /**
     * Sets the updateTime value for this OrderRelationUpdateNotifyRequest.
     * 
     * @param updateTime
     */
    public void setUpdateTime(java.lang.String updateTime) {
        this.updateTime = updateTime;
    }


    /**
     * Gets the updateDesc value for this OrderRelationUpdateNotifyRequest.
     * 
     * @return updateDesc
     */
    public java.lang.String getUpdateDesc() {
        return updateDesc;
    }


    /**
     * Sets the updateDesc value for this OrderRelationUpdateNotifyRequest.
     * 
     * @param updateDesc
     */
    public void setUpdateDesc(java.lang.String updateDesc) {
        this.updateDesc = updateDesc;
    }


    /**
     * Gets the linkId value for this OrderRelationUpdateNotifyRequest.
     * 
     * @return linkId
     */
    public java.lang.String getLinkId() {
        return linkId;
    }


    /**
     * Sets the linkId value for this OrderRelationUpdateNotifyRequest.
     * 
     * @param linkId
     */
    public void setLinkId(java.lang.String linkId) {
        this.linkId = linkId;
    }


    /**
     * Gets the content value for this OrderRelationUpdateNotifyRequest.
     * 
     * @return content
     */
    public java.lang.String getContent() {
        return content;
    }


    /**
     * Sets the content value for this OrderRelationUpdateNotifyRequest.
     * 
     * @param content
     */
    public void setContent(java.lang.String content) {
        this.content = content;
    }


    /**
     * Gets the effectiveDate value for this OrderRelationUpdateNotifyRequest.
     * 
     * @return effectiveDate
     */
    public java.lang.String getEffectiveDate() {
        return effectiveDate;
    }


    /**
     * Sets the effectiveDate value for this OrderRelationUpdateNotifyRequest.
     * 
     * @param effectiveDate
     */
    public void setEffectiveDate(java.lang.String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }


    /**
     * Gets the expireDate value for this OrderRelationUpdateNotifyRequest.
     * 
     * @return expireDate
     */
    public java.lang.String getExpireDate() {
        return expireDate;
    }


    /**
     * Sets the expireDate value for this OrderRelationUpdateNotifyRequest.
     * 
     * @param expireDate
     */
    public void setExpireDate(java.lang.String expireDate) {
        this.expireDate = expireDate;
    }


    /**
     * Gets the time_stamp value for this OrderRelationUpdateNotifyRequest.
     * 
     * @return time_stamp
     */
    public java.lang.String getTime_stamp() {
        return time_stamp;
    }


    /**
     * Sets the time_stamp value for this OrderRelationUpdateNotifyRequest.
     * 
     * @param time_stamp
     */
    public void setTime_stamp(java.lang.String time_stamp) {
        this.time_stamp = time_stamp;
    }


    /**
     * Gets the encodeStr value for this OrderRelationUpdateNotifyRequest.
     * 
     * @return encodeStr
     */
    public java.lang.String getEncodeStr() {
        return encodeStr;
    }


    /**
     * Sets the encodeStr value for this OrderRelationUpdateNotifyRequest.
     * 
     * @param encodeStr
     */
    public void setEncodeStr(java.lang.String encodeStr) {
        this.encodeStr = encodeStr;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderRelationUpdateNotifyRequest)) return false;
        OrderRelationUpdateNotifyRequest other = (OrderRelationUpdateNotifyRequest) obj;
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
            ((this.userIdType==null && other.getUserIdType()==null) || 
             (this.userIdType!=null &&
              this.userIdType.equals(other.getUserIdType()))) &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId()))) &&
            ((this.serviceType==null && other.getServiceType()==null) || 
             (this.serviceType!=null &&
              this.serviceType.equals(other.getServiceType()))) &&
            ((this.spId==null && other.getSpId()==null) || 
             (this.spId!=null &&
              this.spId.equals(other.getSpId()))) &&
            ((this.productId==null && other.getProductId()==null) || 
             (this.productId!=null &&
              this.productId.equals(other.getProductId()))) &&
            ((this.updateType==null && other.getUpdateType()==null) || 
             (this.updateType!=null &&
              this.updateType.equals(other.getUpdateType()))) &&
            ((this.updateTime==null && other.getUpdateTime()==null) || 
             (this.updateTime!=null &&
              this.updateTime.equals(other.getUpdateTime()))) &&
            ((this.updateDesc==null && other.getUpdateDesc()==null) || 
             (this.updateDesc!=null &&
              this.updateDesc.equals(other.getUpdateDesc()))) &&
            ((this.linkId==null && other.getLinkId()==null) || 
             (this.linkId!=null &&
              this.linkId.equals(other.getLinkId()))) &&
            ((this.content==null && other.getContent()==null) || 
             (this.content!=null &&
              this.content.equals(other.getContent()))) &&
            ((this.effectiveDate==null && other.getEffectiveDate()==null) || 
             (this.effectiveDate!=null &&
              this.effectiveDate.equals(other.getEffectiveDate()))) &&
            ((this.expireDate==null && other.getExpireDate()==null) || 
             (this.expireDate!=null &&
              this.expireDate.equals(other.getExpireDate()))) &&
            ((this.time_stamp==null && other.getTime_stamp()==null) || 
             (this.time_stamp!=null &&
              this.time_stamp.equals(other.getTime_stamp()))) &&
            ((this.encodeStr==null && other.getEncodeStr()==null) || 
             (this.encodeStr!=null &&
              this.encodeStr.equals(other.getEncodeStr())));
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
        if (getUserIdType() != null) {
            _hashCode += getUserIdType().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        if (getServiceType() != null) {
            _hashCode += getServiceType().hashCode();
        }
        if (getSpId() != null) {
            _hashCode += getSpId().hashCode();
        }
        if (getProductId() != null) {
            _hashCode += getProductId().hashCode();
        }
        if (getUpdateType() != null) {
            _hashCode += getUpdateType().hashCode();
        }
        if (getUpdateTime() != null) {
            _hashCode += getUpdateTime().hashCode();
        }
        if (getUpdateDesc() != null) {
            _hashCode += getUpdateDesc().hashCode();
        }
        if (getLinkId() != null) {
            _hashCode += getLinkId().hashCode();
        }
        if (getContent() != null) {
            _hashCode += getContent().hashCode();
        }
        if (getEffectiveDate() != null) {
            _hashCode += getEffectiveDate().hashCode();
        }
        if (getExpireDate() != null) {
            _hashCode += getExpireDate().hashCode();
        }
        if (getTime_stamp() != null) {
            _hashCode += getTime_stamp().hashCode();
        }
        if (getEncodeStr() != null) {
            _hashCode += getEncodeStr().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrderRelationUpdateNotifyRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://req.sync.soap.bossagent.vac.unicom.com", "OrderRelationUpdateNotifyRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recordSequenceId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recordSequenceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userIdType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userIdType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "spId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "productId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "updateType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "updateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "updateDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linkId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "linkId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("content");
        elemField.setXmlName(new javax.xml.namespace.QName("", "content"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effectiveDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "effectiveDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expireDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "expireDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("time_stamp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "time_stamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("encodeStr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "encodeStr"));
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
