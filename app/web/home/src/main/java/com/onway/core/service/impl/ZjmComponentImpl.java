/**
 * 
 */
package com.onway.core.service.impl;

import com.onway.common.lang.StringUtils;
import com.onway.core.service.ZjmComponent;
import com.onway.fyapp.common.dal.daointerface.SysZjmDAO;
import com.onway.fyapp.common.dal.dataobject.SysZjmDO;
import com.onway.platform.common.service.template.AbstractServiceImpl;

public class ZjmComponentImpl extends AbstractServiceImpl implements ZjmComponent {

    private SysZjmDAO sysZjmDAO;

    public void setSysZjmDAO(SysZjmDAO sysZjmDAO) {
        this.sysZjmDAO = sysZjmDAO;
    }

    @Override
    public String getZjm(String sourceStr) {
        if (StringUtils.isBlank(sourceStr))
            return "";
        StringBuffer zjm = new StringBuffer();
        int length = sourceStr.length();
        for (int i = 0; i < length; i++) {
            String thisSource = sourceStr.substring(i, i + 1);
            SysZjmDO zjmDO = sysZjmDAO.getZjm(thisSource);
            String thisZjm = thisSource;
            if (null != zjmDO)
                thisZjm = zjmDO.getZjm();
            zjm.append(thisZjm);
        }
        return zjm.toString();
    }

}
