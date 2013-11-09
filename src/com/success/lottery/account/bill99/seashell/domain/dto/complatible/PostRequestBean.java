/**
 * PostRequestBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.success.lottery.account.bill99.seashell.domain.dto.complatible;

public class PostRequestBean  implements java.io.Serializable {
    private java.lang.String address;

    private double amount;

    private java.lang.String creditName;

    private java.lang.String description;

    private java.lang.String mac;

    private java.lang.String orderId;

    private java.lang.String postcode;

    public PostRequestBean() {
    }

    public PostRequestBean(
           java.lang.String address,
           double amount,
           java.lang.String creditName,
           java.lang.String description,
           java.lang.String mac,
           java.lang.String orderId,
           java.lang.String postcode) {
           this.address = address;
           this.amount = amount;
           this.creditName = creditName;
           this.description = description;
           this.mac = mac;
           this.orderId = orderId;
           this.postcode = postcode;
    }


    /**
     * Gets the address value for this PostRequestBean.
     * 
     * @return address
     */
    public java.lang.String getAddress() {
        return address;
    }


    /**
     * Sets the address value for this PostRequestBean.
     * 
     * @param address
     */
    public void setAddress(java.lang.String address) {
        this.address = address;
    }


    /**
     * Gets the amount value for this PostRequestBean.
     * 
     * @return amount
     */
    public double getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this PostRequestBean.
     * 
     * @param amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }


    /**
     * Gets the creditName value for this PostRequestBean.
     * 
     * @return creditName
     */
    public java.lang.String getCreditName() {
        return creditName;
    }


    /**
     * Sets the creditName value for this PostRequestBean.
     * 
     * @param creditName
     */
    public void setCreditName(java.lang.String creditName) {
        this.creditName = creditName;
    }


    /**
     * Gets the description value for this PostRequestBean.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this PostRequestBean.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the mac value for this PostRequestBean.
     * 
     * @return mac
     */
    public java.lang.String getMac() {
        return mac;
    }


    /**
     * Sets the mac value for this PostRequestBean.
     * 
     * @param mac
     */
    public void setMac(java.lang.String mac) {
        this.mac = mac;
    }


    /**
     * Gets the orderId value for this PostRequestBean.
     * 
     * @return orderId
     */
    public java.lang.String getOrderId() {
        return orderId;
    }


    /**
     * Sets the orderId value for this PostRequestBean.
     * 
     * @param orderId
     */
    public void setOrderId(java.lang.String orderId) {
        this.orderId = orderId;
    }


    /**
     * Gets the postcode value for this PostRequestBean.
     * 
     * @return postcode
     */
    public java.lang.String getPostcode() {
        return postcode;
    }


    /**
     * Sets the postcode value for this PostRequestBean.
     * 
     * @param postcode
     */
    public void setPostcode(java.lang.String postcode) {
        this.postcode = postcode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PostRequestBean)) return false;
        PostRequestBean other = (PostRequestBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            this.amount == other.getAmount() &&
            ((this.creditName==null && other.getCreditName()==null) || 
             (this.creditName!=null &&
              this.creditName.equals(other.getCreditName()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.mac==null && other.getMac()==null) || 
             (this.mac!=null &&
              this.mac.equals(other.getMac()))) &&
            ((this.orderId==null && other.getOrderId()==null) || 
             (this.orderId!=null &&
              this.orderId.equals(other.getOrderId()))) &&
            ((this.postcode==null && other.getPostcode()==null) || 
             (this.postcode!=null &&
              this.postcode.equals(other.getPostcode())));
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
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        _hashCode += new Double(getAmount()).hashCode();
        if (getCreditName() != null) {
            _hashCode += getCreditName().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getMac() != null) {
            _hashCode += getMac().hashCode();
        }
        if (getOrderId() != null) {
            _hashCode += getOrderId().hashCode();
        }
        if (getPostcode() != null) {
            _hashCode += getPostcode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PostRequestBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "PostRequestBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("", "address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creditName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mac");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mac"));
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
        elemField.setFieldName("postcode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "postcode"));
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
