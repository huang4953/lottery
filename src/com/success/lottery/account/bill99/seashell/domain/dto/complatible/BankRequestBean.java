/**
 * BankRequestBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.success.lottery.account.bill99.seashell.domain.dto.complatible;

public class BankRequestBean  implements java.io.Serializable {
    private double amount;

    private java.lang.String bankCardNumber;

    private java.lang.String bankName;

    private java.lang.String creditName;

    private java.lang.String description;

    private java.lang.String kaihuhang;

    private java.lang.String mac;

    private java.lang.String orderId;

    private java.lang.String province_city;

    public BankRequestBean() {
    }

    public BankRequestBean(
           double amount,
           java.lang.String bankCardNumber,
           java.lang.String bankName,
           java.lang.String creditName,
           java.lang.String description,
           java.lang.String kaihuhang,
           java.lang.String mac,
           java.lang.String orderId,
           java.lang.String province_city) {
           this.amount = amount;
           this.bankCardNumber = bankCardNumber;
           this.bankName = bankName;
           this.creditName = creditName;
           this.description = description;
           this.kaihuhang = kaihuhang;
           this.mac = mac;
           this.orderId = orderId;
           this.province_city = province_city;
    }


    /**
     * Gets the amount value for this BankRequestBean.
     * 
     * @return amount
     */
    public double getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this BankRequestBean.
     * 
     * @param amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }


    /**
     * Gets the bankCardNumber value for this BankRequestBean.
     * 
     * @return bankCardNumber
     */
    public java.lang.String getBankCardNumber() {
        return bankCardNumber;
    }


    /**
     * Sets the bankCardNumber value for this BankRequestBean.
     * 
     * @param bankCardNumber
     */
    public void setBankCardNumber(java.lang.String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }


    /**
     * Gets the bankName value for this BankRequestBean.
     * 
     * @return bankName
     */
    public java.lang.String getBankName() {
        return bankName;
    }


    /**
     * Sets the bankName value for this BankRequestBean.
     * 
     * @param bankName
     */
    public void setBankName(java.lang.String bankName) {
        this.bankName = bankName;
    }


    /**
     * Gets the creditName value for this BankRequestBean.
     * 
     * @return creditName
     */
    public java.lang.String getCreditName() {
        return creditName;
    }


    /**
     * Sets the creditName value for this BankRequestBean.
     * 
     * @param creditName
     */
    public void setCreditName(java.lang.String creditName) {
        this.creditName = creditName;
    }


    /**
     * Gets the description value for this BankRequestBean.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this BankRequestBean.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the kaihuhang value for this BankRequestBean.
     * 
     * @return kaihuhang
     */
    public java.lang.String getKaihuhang() {
        return kaihuhang;
    }


    /**
     * Sets the kaihuhang value for this BankRequestBean.
     * 
     * @param kaihuhang
     */
    public void setKaihuhang(java.lang.String kaihuhang) {
        this.kaihuhang = kaihuhang;
    }


    /**
     * Gets the mac value for this BankRequestBean.
     * 
     * @return mac
     */
    public java.lang.String getMac() {
        return mac;
    }


    /**
     * Sets the mac value for this BankRequestBean.
     * 
     * @param mac
     */
    public void setMac(java.lang.String mac) {
        this.mac = mac;
    }


    /**
     * Gets the orderId value for this BankRequestBean.
     * 
     * @return orderId
     */
    public java.lang.String getOrderId() {
        return orderId;
    }


    /**
     * Sets the orderId value for this BankRequestBean.
     * 
     * @param orderId
     */
    public void setOrderId(java.lang.String orderId) {
        this.orderId = orderId;
    }


    /**
     * Gets the province_city value for this BankRequestBean.
     * 
     * @return province_city
     */
    public java.lang.String getProvince_city() {
        return province_city;
    }


    /**
     * Sets the province_city value for this BankRequestBean.
     * 
     * @param province_city
     */
    public void setProvince_city(java.lang.String province_city) {
        this.province_city = province_city;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BankRequestBean)) return false;
        BankRequestBean other = (BankRequestBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.amount == other.getAmount() &&
            ((this.bankCardNumber==null && other.getBankCardNumber()==null) || 
             (this.bankCardNumber!=null &&
              this.bankCardNumber.equals(other.getBankCardNumber()))) &&
            ((this.bankName==null && other.getBankName()==null) || 
             (this.bankName!=null &&
              this.bankName.equals(other.getBankName()))) &&
            ((this.creditName==null && other.getCreditName()==null) || 
             (this.creditName!=null &&
              this.creditName.equals(other.getCreditName()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.kaihuhang==null && other.getKaihuhang()==null) || 
             (this.kaihuhang!=null &&
              this.kaihuhang.equals(other.getKaihuhang()))) &&
            ((this.mac==null && other.getMac()==null) || 
             (this.mac!=null &&
              this.mac.equals(other.getMac()))) &&
            ((this.orderId==null && other.getOrderId()==null) || 
             (this.orderId!=null &&
              this.orderId.equals(other.getOrderId()))) &&
            ((this.province_city==null && other.getProvince_city()==null) || 
             (this.province_city!=null &&
              this.province_city.equals(other.getProvince_city())));
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
        _hashCode += new Double(getAmount()).hashCode();
        if (getBankCardNumber() != null) {
            _hashCode += getBankCardNumber().hashCode();
        }
        if (getBankName() != null) {
            _hashCode += getBankName().hashCode();
        }
        if (getCreditName() != null) {
            _hashCode += getCreditName().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getKaihuhang() != null) {
            _hashCode += getKaihuhang().hashCode();
        }
        if (getMac() != null) {
            _hashCode += getMac().hashCode();
        }
        if (getOrderId() != null) {
            _hashCode += getOrderId().hashCode();
        }
        if (getProvince_city() != null) {
            _hashCode += getProvince_city().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BankRequestBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://complatible.dto.domain.seashell.bill99.com", "BankRequestBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankCardNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bankCardNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bankName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
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
        elemField.setFieldName("kaihuhang");
        elemField.setXmlName(new javax.xml.namespace.QName("", "kaihuhang"));
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
        elemField.setFieldName("province_city");
        elemField.setXmlName(new javax.xml.namespace.QName("", "province_city"));
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
