package com.success.lottery.account.bill99.encrypt.util; 

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * MD5������
 * */
public class MD5Util {

    private MD5Util() {
    }

    //ʵ����MessageDigest��ĺ���
    static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] md5(byte[] data) {
        return getDigest().digest(data);
    }


    public static byte[] md5(String data) {
        return md5(data.getBytes());
    }

    //���ַ�������м���
    public static String md5Hex(byte[] data) {
        return HexUtil.toHexString(md5(data));
    }
    
    //���ַ������м���
    public static String md5Hex(String data) {
        return HexUtil.toHexString(md5(data));
    }
}
