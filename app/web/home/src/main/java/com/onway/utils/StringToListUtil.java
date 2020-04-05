package com.onway.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.onway.common.lang.StringUtils;

/**
 * 字符转list   需,隔开
 * 
 * @author yugang.ni
 * @version $Id: StringToListUtil.java, v 0.1 2018年9月27日 上午10:35:12 Administrator Exp $
 */
public class StringToListUtil {

    public static void main(String[] args) {
        System.out.println(toList("1,2,3"));
    }

    public static List<String> toList(String str) {
        if (StringUtils.isBlank(str))
            return null;

        String[] strArray = str.split(",");
        List<String> asList = Arrays.asList(strArray);
        return asList;
    }

    public static List<String> toListArr(String str) {
        if (StringUtils.isBlank(str))
            return new ArrayList<String>();

        String[] strArray = str.split(",");
        List<String> asList = Arrays.asList(strArray);
        return asList;
    }

    public static List<String> arrToList(String[] str) {
        if (null == str || str.length <= 0)
            return null;

        List<String> asList = Arrays.asList(str);
        return asList;
    }

}
