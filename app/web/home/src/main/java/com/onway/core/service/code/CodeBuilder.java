/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.core.service.code;

import com.onway.common.lang.DateUtils;
import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.PlatformCodeEnum;

/**
 * 编码生成器抽象类
 * 
 * @author guangdong.li
 * @version $Id: CodeBuilder.java, v 0.1 2015年11月2日 下午4:56:19 guangdong.li Exp $
 */
public abstract class CodeBuilder {

    /** 场景 */
    protected PlatformCodeEnum       platformCode;

    /** C端平台 */
    private static CustomerNoBuilder customerNoBuilder = new CustomerNoBuilder(
                                                           PlatformCodeEnum.CUSTOMER_PLATFORM);

    /**
     * 根据业务类型得到builder实例
     * 
     * @param type
     * @return
     */
    public static CodeBuilder getCodeBuilder(PlatformCodeEnum platformCode) {
        switch (platformCode) {
            case CUSTOMER_PLATFORM:
                return customerNoBuilder;

            default:
                return customerNoBuilder;
        }
    }

    /**
    *  编号：  前缀（n位） + 时间戳（8位）+seq(NO_CIRCLE_LENGTH位)+后缀（m位）
    * 
    * @param seq
    * @return
    */
    public static String getCode(int seq, String PREFFIX) {

        return new StringBuilder(PREFFIX) //前缀
            .append(DateUtils.getTodayString())
            //日期 8位
            .append(
                StringUtils.fillPrefix(Long.toString(seq % CodeGenerateConfig.NO_CIRCLE), "0",
                    CodeGenerateConfig.NO_CIRCLE_LENGTH)) //填充0
            .toString();
    }

    /**
     * 编号： 前缀（n位） + 时间戳（8位）+seq(NO_CIRCLE_LENGTH位)+后缀（m位）
     * 
     * @param seq
     * @return
     */
    public String getCode(int seq) {
        StringBuilder sb = new StringBuilder(getPrefix());
        sb.append(DateUtils.getTodayString());
        sb.append(StringUtils.fillPrefix(Long.toString(seq % CodeGenerateConfig.NO_CIRCLE), "0",
            CodeGenerateConfig.NO_CIRCLE_LENGTH));
        sb.append(getSuffix());
        return sb.toString();
    }

    /**
     * 构造方法
     * 
     * @param sceneType
     */
    protected CodeBuilder(PlatformCodeEnum platformCode) {
        this.platformCode = platformCode;
    }

    /** 获得前缀 */
    protected String getPrefix() {
        return platformCode.getValue() + "";
    }

    /** 获得后缀 */
    protected String getSuffix() {
        return "";
    }
}
