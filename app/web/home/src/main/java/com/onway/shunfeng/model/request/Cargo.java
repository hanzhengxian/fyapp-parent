package com.onway.shunfeng.model.request;

/**
 * 请求
 * 
 * @author yuhang.ni
 * @version $Id: Cargo.java, v 0.1 2019年3月13日 下午1:47:13 Administrator Exp $
 */
public class Cargo {

    //货物名称，如果需要生成电子运单，则为必填
    private String name;
    
    //货物数量 跨境件报关需要填写
    private String count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
    
}
