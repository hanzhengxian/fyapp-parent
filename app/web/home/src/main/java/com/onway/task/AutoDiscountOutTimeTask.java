package com.onway.task;

import java.util.Date;

import com.onway.fyapp.common.dal.daointerface.DiscountUserDAO;
import com.onway.model.enums.DiscountStatusEnum;
import com.onway.utils.DateUtil;

/**
 * 优惠券过期
 * 
 * @author yuhang.ni
 * @version $Id: AutoDiscountOutTimeTask.java, v 0.1 2019年9月11日 上午10:16:41 Administrator Exp $
 */
public class AutoDiscountOutTimeTask extends AbstractTask {

    private DiscountUserDAO discountUserDAO;

    public void setDiscountUserDAO(DiscountUserDAO discountUserDAO) {
        this.discountUserDAO = discountUserDAO;
    }

    @Override
    protected boolean canProcess() {
        return true;
    }

    @Override
    protected void process() {
        try {
            Date today = DateUtil.getToday();
            int discountOutTime = discountUserDAO.discountOutTime(
                DiscountStatusEnum.WAIT_USE.getCode(), today);
            logger.info("清理过期优惠券数:" + discountOutTime);
        } catch (Exception e) {
            logger.error("清理过期优惠券异常", e);
        }
    }

}
