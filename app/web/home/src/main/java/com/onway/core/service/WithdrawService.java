/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.core.service;

import javax.servlet.http.HttpServletRequest;

import com.onway.platform.common.base.BaseResult;

/**
 * 提现操作服务
 */
public interface WithdrawService {

    /**
     * 提现审核
     * @return
     */
    public BaseResult withDrawCheck(final Integer id, final String withdrawNo,
                                    final String checkType, final String failReason,
                                    HttpServletRequest request);

}
