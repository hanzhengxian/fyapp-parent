/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.utils;

import com.onway.common.lang.DateUtils;
import com.onway.platform.common.configration.ConfigrationFactory;

/**
 * 文件工具类
 * 
 * @author guangdong.li
 * @version $Id: FileUtils.java, v 0.1 21 Feb 2016 11:12:44 guangdong.li Exp $
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    private static final String SUFFIX     = ".jpg";

    private static final String SUFFIX_PDF = ".pdf";

    /**
     * 构建文件路径 格式: /201401/1234234123.jpg
     * 
     * @param cfgPath  配置的文件保存目录
     * @param userId
     * @return
     */
    public static String buildFilePath(String cfgPath, String userId) {
        return buildFilePathByName(cfgPath) + "/" + DateUtils.getTodayString().substring(0, 6)
               + "/" + userId + SUFFIX;
    }

    /**
     * 构建文件路径 格式: 888888888_1234234123_cert.jpg
     * 
     * @param userId
     *            : 888888888
     * @param authType
     *            :　cert
     * @return
     */
    public static String buildFilePath(String cfgPath, String userId, String authType) {
        return buildFilePathByName(cfgPath) + authType + "/" + DateUtils.getTodayString() + "/"
               + userId + "_" + System.currentTimeMillis() + "_" + authType + SUFFIX;
    }

    /**
     * 构建文件路径 格式: 888888888_1234234123.pdf
     * 
     * @param userId
     *            : 888888888
     * @param authType
     *            :　cert
     * @return
     */
    public static String buildFilePathForPDF(String cfgPath, String prefix) {
        return buildFilePathByName(cfgPath) + DateUtils.getTodayString() + "/" + prefix + "_"
               + System.currentTimeMillis() + SUFFIX_PDF;
    }

    public static String buildFilePathForPDF(String cfgPath, String fundCode, String prefix) {
        return buildFilePathByName(cfgPath) + DateUtils.getTodayString() + "/" + fundCode + "/"
               + prefix + SUFFIX_PDF;
    }

    /**
     * 构建文件路径 格式: 888888888_1234234123_temp.pdf
     * 
     * @param userId
     *            : 888888888
     * @param authType
     *            :　cert
     * @return
     */
    public static String buildFilePathForPDFTemp(String cfgPath, String userId) {
        return buildFilePathByName(cfgPath) + DateUtils.getTodayString() + "/" + userId + "_"
               + System.currentTimeMillis() + "_temp" + SUFFIX_PDF;
    }

    /**
     * 从内存中获取文件路径
     * 
     * @param cfgPath  配置的文件保存目录
     * @return
     */
    public static String buildFilePathByName(String cfgPath) {
        return ConfigrationFactory.getConfigration().getPropertyValue(cfgPath);
    }

}
