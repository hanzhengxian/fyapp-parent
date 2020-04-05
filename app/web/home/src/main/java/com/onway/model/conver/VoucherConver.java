package com.onway.model.conver;

import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.fyapp.common.dal.dataobject.VoucherRecordDO;
import com.onway.model.VoucherRecordDomain;
import com.onway.model.enums.VoucherRecordTypeEnum;
import com.onway.utils.DateUtil;

/**
 * 
 * 
 * @author yuhang.ni
 * @version $Id: VoucherConver.java, v 0.1 2019年9月17日 下午4:53:39 Administrator Exp $
 */
public class VoucherConver {

    public static VoucherRecordDomain buildReduceListResult(VoucherRecordDO voucherRecordDO) {
        if (null == voucherRecordDO)
            return null;

        VoucherRecordDomain infoResult = new VoucherRecordDomain();
        infoResult.setRecordType(voucherRecordDO.getRecordType());
        infoResult.setRecordTypeStr(VoucherRecordTypeEnum
            .getByCode(voucherRecordDO.getRecordType()).message());

        if (StringUtils.equals(voucherRecordDO.getRecordType(),
            VoucherRecordTypeEnum.GIFT_IN.getCode())
            || StringUtils.equals(voucherRecordDO.getRecordType(),
                VoucherRecordTypeEnum.ORDER_IN.getCode())
            || StringUtils.equals(voucherRecordDO.getRecordType(),
                VoucherRecordTypeEnum.ORDER_OUT_RETURN.getCode()))
            if (voucherRecordDO.getRecordAmount().lessThan(new Money(0))) {
                infoResult.setRecordAmount(voucherRecordDO.getRecordAmount().toSimpleString());
            } else {
                infoResult
                    .setRecordAmount("+" + voucherRecordDO.getRecordAmount().toSimpleString());
            }
        if (StringUtils.equals(voucherRecordDO.getRecordType(),
            VoucherRecordTypeEnum.GIFT_OUT.getCode())
            || StringUtils.equals(voucherRecordDO.getRecordType(),
                VoucherRecordTypeEnum.ORDER_OUT.getCode())) {
            if (voucherRecordDO.getRecordAmount().lessEqualThan(new Money(0))) {
                infoResult.setRecordAmount(voucherRecordDO.getRecordAmount().toSimpleString());
            } else {
                infoResult
                    .setRecordAmount("-" + voucherRecordDO.getRecordAmount().toSimpleString());
            }
        }

        infoResult.setRecordLink(voucherRecordDO.getRecordLink());
        infoResult
            .setTime(DateUtil.dateToString(voucherRecordDO.getGmtCreate(), DateUtil.newFormat));

        infoResult.setVoucherNo(voucherRecordDO.getVoucherNo());
        infoResult.setVoucherId(voucherRecordDO.getId());

        return infoResult;
    }

}
