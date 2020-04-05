package com.onway.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onway.result.LinkProStock;

public class ListToMap {

    public static void main(String[] args) {

    }

    public static Map<String, List<Integer>> linkProStocktoMapInteger(List<LinkProStock> linkProAttrS) {

        Map<String, List<Integer>> result = new HashMap<String, List<Integer>>();
        for (LinkProStock linkProStock : linkProAttrS) {
            result.put(linkProStock.getProductId(), linkProStock.getStockId());
        }
        return result;
    }
    
    public static Map<String, LinkProStock> linkProStocktoMapObj(List<LinkProStock> linkProAttrS) {

        Map<String, LinkProStock> result = new HashMap<String, LinkProStock>();
        for (LinkProStock linkProStock : linkProAttrS) {
            result.put(linkProStock.getProductId(), linkProStock);
        }
        return result;
    }
}
