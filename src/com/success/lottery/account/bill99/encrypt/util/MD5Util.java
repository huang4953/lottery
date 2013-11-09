package com.success.lottery.account.bill99.encrypt.util; 

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * MD5工具类
 * */
public class MD5Util {

    private MD5Util() {
    }

    //实例化MessageDigest类的函数
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

    //对字符数组进行加密
    public static String md5Hex(byte[] data) {
        return HexUtil.toHexString(md5(data));
    }
    
    //对字符串进行加密
    public static String md5Hex(String data) {
        return HexUtil.toHexString(md5(data));
    }
}
