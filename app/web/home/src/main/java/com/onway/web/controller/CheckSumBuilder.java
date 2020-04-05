package com.onway.web.controller;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author yuhang.ni
 *
 * @version CheckSumBuilder.java 2017年11月8日 下午1:36:23 yuhang.ni
 */
public class CheckSumBuilder {
	
	/**
	 * AppKey   开发者平台分配的appkey
	 * Nonce    随机数（最大长度128个字符）
	 * CurTime  当前UTC时间戳，从1970年1月1日0点0 分0 秒开始到现在的秒数(String)
	 * CheckSum SHA1(AppSecret + Nonce + CurTime),三个参数拼接的字符串，进行SHA1哈希计算，转化成16进制字符(String，小写)
	 * 
	 */
	
    // 计算并获取CheckSum
    public static String getCheckSum(String appSecret, String nonce, String curTime) {
        return encode("sha1", appSecret + nonce + curTime);
    }

    // 计算并获取md5值
    public static String getMD5(String requestBody) {
        return encode("md5", requestBody);
    }

    private static String encode(String algorithm, String value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest messageDigest
                    = MessageDigest.getInstance(algorithm);
            messageDigest.update(value.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    
    
    public static String getRandom(int width) {
    	String random = "";
    	for (int i = 0; i < width ; i++) {
    		random =random + (int)Math.floor(Math.random() * 10);
		}
        return random;
    }
    
    public static void main(String[] args) { 
    	String appSecret = "abcdef123456ghijk";
    	String nonce = getRandom(5);
    	String curTime = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
    	System.out.println(getCheckSum(appSecret, nonce, curTime));
    }
}
