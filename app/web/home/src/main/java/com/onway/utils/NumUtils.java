package com.onway.utils;

import org.apache.commons.lang3.math.NumberUtils;

public class NumUtils {

    public static boolean checkeRate(String rate) {
        if (StringUtils.isBlank(rate))
            return false;
        if (NumberUtils.isNumber(rate)) {
            Double rateDouble = Double.valueOf(rate);
            if (rateDouble >= 0 && rateDouble <= 100)
                return true;
        }
        return false;
    }
    
    public static boolean checkeRatem(String rate) {
        if (StringUtils.isBlank(rate))
            return false;
        if (NumberUtils.isNumber(rate)) {
            Double rateDouble = Double.valueOf(rate);
            if (rateDouble >= 0 && rateDouble <= 1)
                return true;
        }
        return false;
    }
}
