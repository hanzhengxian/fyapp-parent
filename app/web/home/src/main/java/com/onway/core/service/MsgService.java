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
 * Description: 消息操作服务类
 * </p>
 * 
 * @author yugang.ni
 * @date 2018年8月31日 下午3:59:47
 */
public interface MsgService {

    /**
     * 新增消息
     * 
     * @param userId
     * @param msgType
     * @param msgTitle
     * @param msgContent
     * @return
     */
    BaseResult newMsg(String userId, String msgType, String msgTitle, String msgContent);

    /**
     * 发送微信消息
     * 
     * @param openId
     * @param msgContent
     * @return
     */
    BaseResult sendWechatMsg(String openId, String msgContent);

    /**
     * 发送图文消息
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
