package com.onway.core.service.pay;

/**
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */


import javax.servlet.http.HttpServletRequest;

public class IpKit {

    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getRealIpV2(HttpServletRequest request) {
        String accessIP = request.getHeader("x-forwarded-for");
        if (null == accessIP)
            return request.getRemoteAddr();
        return accessIP;
    }
    
    public static String getRealIpV3(HttpServletRequest request) {
        // 223.104.246.169, 123.151.77.70
        String accessIP = request.getHeader("x-forwarded-for");
        if (null != accessIP) {
            String[] split = accessIP.split(",");
            for (String ip : split) {
                if (!"unknown".equalsIgnoreCase(ip)) {
                    return ip.trim();
                }
            }
        } else {
            return null;
        }
        return accessIP.trim();
    }
}
