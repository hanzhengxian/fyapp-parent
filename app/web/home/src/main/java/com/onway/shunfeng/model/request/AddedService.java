package com.onway.shunfeng.model.request;

/**
 * 请求 （可选）
 * 
 * @author yuhang.ni
 * @version $Id: Cargo.java, v 0.1 2019年3月13日 下午1:47:13 Administrator Exp $
 */
public class AddedService {

    //增值服务名，如COD等。
    private String name;

    //增值服务扩展属性，参考增值服务传值说明。
    private String value;

    //增值服务扩展属性
    private String value1;

    //增值服务扩展属性2
    private String value2;

    //增值服务扩展属性3
    private String value3;

    //增值服务扩展属性4
    private String Value4;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getValue4() {
        return Value4;
    }

    public void setValue4(String value4) {
        Value4 = value4;
    }

}
