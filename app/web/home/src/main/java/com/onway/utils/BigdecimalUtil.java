package com.onway.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.onway.common.lang.Money;

public class BigdecimalUtil {

    public static void main(String[] args) {

        System.out.println(cal(new BigDecimal(9801.05), new BigDecimal(0)));
    }

    public static Money toMoney(BigDecimal bigDecimal) {
        if (null == bigDecimal)
            return new Money(0);
        return new Money(bigDecimal.divide(new BigDecimal(100)));
    }

    public static double cal(BigDecimal a, BigDecimal b) {
        if (b.compareTo(new BigDecimal(0)) < 1) {
            return a.doubleValue();
        }
        double doubleValue = (a.subtract(b)).divide(b, 4, BigDecimal.ROUND_HALF_DOWN)
            .multiply(new BigDecimal(100)).doubleValue();
        return doubleValue;
    }

    public static double cal(Double a, Double b) {
        if (b < 1) {
            return format(a * 100);
        }
        double doubleValue = (a - b) / b * 100;
        return format(doubleValue);
    }

    public static double format(double format) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(format));
    }
    
    public static double calRate(BigDecimal a, BigDecimal b) {
        if (b.compareTo(new BigDecimal(0)) < 1) {
            return 0;
        }
        double doubleValue = a.divide(b, 4, BigDecimal.ROUND_HALF_DOWN)
            .multiply(new BigDecimal(100)).doubleValue();
        return doubleValue;
    }

}
