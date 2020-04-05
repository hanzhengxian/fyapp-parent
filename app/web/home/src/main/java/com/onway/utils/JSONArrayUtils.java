package com.onway.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParserConfig;
import com.onway.common.lang.CollectionUtils;
import com.onway.common.lang.StringUtils;

public class JSONArrayUtils {

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        if (StringUtils.isBlank(text)) {
            return new ArrayList<T>();
        }

        List<T> list;

        DefaultJSONParser parser = new DefaultJSONParser(text, ParserConfig.getGlobalInstance());
        JSONLexer lexer = parser.getLexer();
        if (lexer.token() == JSONToken.NULL) {
            lexer.nextToken();
            list = null;
        } else {
            list = new ArrayList<T>();
            parser.parseArray(clazz, list);

            parser.handleResovleTask(list);
        }

        parser.close();

        return list;
    }
    
    public static <T> String parseArrayToString(String text, Class<T> clazz) {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        List<T> list;

        DefaultJSONParser parser = new DefaultJSONParser(text, ParserConfig.getGlobalInstance());
        JSONLexer lexer = parser.getLexer();
        if (lexer.token() == JSONToken.NULL) {
            lexer.nextToken();
            list = null;
        } else {
            list = new ArrayList<T>();
            parser.parseArray(clazz, list);

            parser.handleResovleTask(list);
        }

        parser.close();
        
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        
        return listToString(list);
    }
    
    public static <T> String listToString(List<T> list) {
        StringBuilder builder = new StringBuilder();
        for (T t : list) {
            builder.append(",").append(t);
        }
        return (String) builder.toString().subSequence(1, builder.toString().length());
    }
}
