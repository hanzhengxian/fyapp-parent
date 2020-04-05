package com.onway.result.express;

import com.onway.platform.common.base.ToString;

public class ExpressDataPojo extends ToString {

    /**  */
    private static final long serialVersionUID = 24637627L;

    private String            time;

    private String            context;

    private String            location;

    private String            Date;

    private String            expressTime;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getExpressTime() {
        return expressTime;
    }

    public void setExpressTime(String expressTime) {
        this.expressTime = expressTime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
