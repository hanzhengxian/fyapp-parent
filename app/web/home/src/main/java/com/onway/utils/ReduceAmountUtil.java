package com.onway.utils;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.model.arrObj.OrderDiscountJson;

public class ReduceAmountUtil {

    public static Money getChildOrderReduceAmountS(String reducePro, String reducePlat,
                                                    String reduceTeam) {
        Money allReduce = new Money();
        if (StringUtils.isNotBlank(reducePro)) {
            List<OrderDiscountJson> planJsonList = JSONArray.parseArray(reducePro,
                OrderDiscountJson.class);
            for (OrderDiscountJson planJson : planJsonList) {
                if (null != planJson && StringUtils.isNotBlank(planJson.getDiscountId())
                    && StringUtils.isNotBlank(planJson.getAmount())) {
                    allReduce = allReduce.add(new Money(planJson.getAmount()));
                }
            }
        }
        if (StringUtils.isNotBlank(reducePlat)) {
            List<OrderDiscountJson> planJsonList = JSONArray.parseArray(reducePlat,
                OrderDiscountJson.class);
            for (OrderDiscountJson planJson : planJsonList) {
                if (null != planJson && StringUtils.isNotBlank(planJson.getDiscountId())
                    && StringUtils.isNotBlank(planJson.getAmount())) {
                    allReduce = allReduce.add(new Money(planJson.getAmount()));
                }
            }

        }
        if (StringUtils.isNotBlank(reduceTeam)) {
            List<OrderDiscountJson> planJsonList = JSONArray.parseArray(reduceTeam,
                OrderDiscountJson.class);
            for (OrderDiscountJson planJson : planJsonList) {
                if (null != planJson && StringUtils.isNotBlank(planJson.getDiscountId())
                    && StringUtils.isNotBlank(planJson.getAmount())) {
                    allReduce = allReduce.add(new Money(planJson.getAmount()));
                }
            }
        }
        return allReduce;
    }
}
