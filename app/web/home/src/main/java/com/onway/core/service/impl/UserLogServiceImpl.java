/**
 * 
 */
package com.onway.core.service.impl;

import com.onway.core.service.UserLogService;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.service.template.AbstractServiceImpl;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.platform.common.service.util.AssertUtil;

/**
 * @author guangdong.li
 *
 */
public class UserLogServiceImpl extends AbstractServiceImpl implements UserLogService {

    /*
     * (non-Javadoc)
     * 
     * @see com.onway.core.service.UserLogService#storeUserLog(java.lang.Object)
     */
    @Override
    public BaseResult storeUserLog(final Object cifUserLogInfo) {

        final BaseResult result = new BaseResult(true);

        serviceTemplate.executeWithoutTransaction(result, new ServiceCheckCallback() {

            @Override
            public void check() {
                AssertUtil.notNull(cifUserLogInfo, "cifUserLogInfo²»ÄÜÎª¿Õ");
            }

            @Override
            public void executeService() {
            }

        });

        return result;
    }

}
