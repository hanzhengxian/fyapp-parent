package com.onway.utils.sendMessage;

/**
 * 
 * �ظ�ͼ��Articles  
 * @author yugang.ni
 * @version $Id: Article.java, v 0.1 2018��9��19�� ����5:04:08 Administrator Exp $
 */
public class Article {
    // ͼ����Ϣ����  
    private String title;
    // ͼ����Ϣ����  
    private String description;
    // ���ͼ����Ϣ��ת����  
    private String url;
    // ͼƬ���ӣ�֧��JPG��PNG��ʽ���Ϻõ�Ч��Ϊ��ͼ640*320��Сͼ80*80������ͼƬ���ӵ�������Ҫ�뿪������д�Ļ��������е�Urlһ��   
    private String picurl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }
}
