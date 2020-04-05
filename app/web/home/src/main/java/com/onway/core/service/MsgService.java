/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.core.service;

import com.onway.platform.common.base.BaseResult;

/**
 * 
 * <p>
 * Title: MsgService
 * </p>
 * <p>
 * Description: ��Ϣ����������
 * </p>
 * 
 * @author yugang.ni
 * @date 2018��8��31�� ����3:59:47
 */
public interface MsgService {

    /**
     * ������Ϣ
     * 
     * @param userId
     * @param msgType
     * @param msgTitle
     * @param msgContent
     * @return
     */
    BaseResult newMsg(String userId, String msgType, String msgTitle, String msgContent);

    /**
     * ����΢����Ϣ
     * 
     * @param openId
     * @param msgContent
     * @return
     */
    BaseResult sendWechatMsg(String openId, String msgContent);

    /**
     * ����ͼ����Ϣ
     * 
     * @param openId
     * @param linkUrl
     * @param pickUrl
     * @param title
     * @param desc
     * @return
     */
    BaseResult sendWechatCustomerMsg(String openId, String linkUrl, String pickUrl, String title,
                                     String desc);

}
