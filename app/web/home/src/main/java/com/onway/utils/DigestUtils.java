/**
 * onway.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.onway.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加解密工具类
 * 
 * @author qiang.wq
 * @version $Id: Digest.java, v 0.1 2013-12-8 下午9:45:38 WJL Exp $
 */
public class DigestUtils {

    public static String hmacSign(String aValue, String charset) {
        try {
            byte[] input = null;
            if (charset == null) {
                input = aValue.getBytes();
            } else {
                input = aValue.getBytes(charset);
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(input);
            return toHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toHex(byte input[]) {
        if (input == null)
            return null;
        StringBuffer output = new StringBuffer(input.length * 2);
        for (int i = 0; i < input.length; i++) {
            int current = input[i] & 0xff;
            if (current < 16)
                output.append("0");
            output.append(Integer.toString(current, 16));
        }

        return output.toString();
    }
}
