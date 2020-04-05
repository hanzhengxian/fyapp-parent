package com.onway.core.service;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.log4j.Logger;

import com.onway.common.lang.StringUtils;
import com.onway.utils.Base64;
import com.onway.web.controller.AbstractController;

/**
 * des3工具
 * 
 * @author Administrator
 * @version $Id: Des3.java, v 0.1 2018年8月17日 下午4:08:33 Administrator Exp $
 */
public class Des3 {
    public static final Logger      logger            = Logger.getLogger(AbstractController.class);
    
    private final static String secretKey = "hqytwechat@apricot888$#365#$";
    
    private final static String iv        = "01234567";
    
    private final static String encoding  = "utf-8";

    /**
     * des3加密
     * 
     * @param plainText
     * @return
     */
    public static String encode(String plainText) {
        if (StringUtils.isBlank(plainText)) {
            return plainText;
        }
        Key deskey = null;
        byte[] encryptData = null;
        try {
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            encryptData = cipher.doFinal(plainText.getBytes(encoding));
        } catch (Exception e) {
            logger.error("", e);
        }
        return Base64.encode(encryptData);
    }

    /**
     * des3解密
     * 
     * @param encryptText
     * @return
     */
    public static String decode(String encryptText) {
        Key deskey = null;
        byte[] decryptData = null;
        if (StringUtils.isBlank(encryptText) || StringUtils.equals(encryptText, "null")) {
            return "";
        }
        String clearString = null;
        try {
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

            decryptData = cipher.doFinal(Base64.decode(encryptText));
            clearString = new String(decryptData, encoding);
        } catch (Exception e) {
            logger.error("", e);
        } 

        return clearString;
    }

    public static void main(String[] args) {
            String msg = "fr";
            String str = "klkjlkjfr分局5456打火机";
            int indexOf = str.indexOf(msg);
            System.out.println(indexOf);
            String substring = str.substring(indexOf+msg.length(), str.length());
            System.out.println(substring);
            
            System.out.println(encode("123456"));
    }
}
