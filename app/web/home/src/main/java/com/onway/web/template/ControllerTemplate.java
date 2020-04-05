package com.onway.web.template;

import com.onway.result.JsonResult;


/**
 * onway.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */

/**
 * ����ģ��
 * @author Administrator
 *
 */
public interface ControllerTemplate {

    /**
     * <pre> ֧�ֱ�������ģ��ִ��ҵ����
     * 1. ���������װ
     * 2. �쳣���񣬼����ദ��
     * 3. ҵ����־��¼
     * </pre>
     * @param baseResult    ���ض���
     * @param action        ҵ������ص��Ľӿ�
     */
    void execute(JsonResult jsonResult, ControllerCallBack callBack);

}
