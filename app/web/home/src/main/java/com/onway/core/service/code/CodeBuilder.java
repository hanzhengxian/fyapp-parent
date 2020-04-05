/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.core.service.code;

import com.onway.common.lang.DateUtils;
import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.PlatformCodeEnum;

/**
 * ����������������
 * 
 * @author guangdong.li
 * @version $Id: CodeBuilder.java, v 0.1 2015��11��2�� ����4:56:19 guangdong.li Exp $
 */
public abstract class CodeBuilder {

    /** ���� */
    protected PlatformCodeEnum       platformCode;

    /** C��ƽ̨ */
    private static CustomerNoBuilder customerNoBuilder = new CustomerNoBuilder(
                                                           PlatformCodeEnum.CUSTOMER_PLATFORM);

    /**
     * ����ҵ�����͵õ�builderʵ��
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
    *  ��ţ�  ǰ׺��nλ�� + ʱ�����8λ��+seq(NO_CIRCLE_LENGTHλ)+��׺��mλ��
    * 
    * @param seq
    * @return
    */
    public static String getCode(int seq, String PREFFIX) {

        return new StringBuilder(PREFFIX) //ǰ׺
            .append(DateUtils.getTodayString())
            //���� 8λ
            .append(
                StringUtils.fillPrefix(Long.toString(seq % CodeGenerateConfig.NO_CIRCLE), "0",
                    CodeGenerateConfig.NO_CIRCLE_LENGTH)) //���0
            .toString();
    }

    /**
     * ��ţ� ǰ׺��nλ�� + ʱ�����8λ��+seq(NO_CIRCLE_LENGTHλ)+��׺��mλ��
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
     * ���췽��
     * 
     * @param sceneType
     */
    protected CodeBuilder(PlatformCodeEnum platformCode) {
        this.platformCode = platformCode;
    }

    /** ���ǰ׺ */
    protected String getPrefix() {
        return platformCode.getValue() + "";
    }

    /** ��ú�׺ */
    protected String getSuffix() {
        return "";
    }
}
