package com.onway.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetFileTest {
	
	public static void main(String[] args) throws IOException {
        URL url = new URL("http://app.scjapp.com:8093/image/20171226/media/8d92362c-e4e9-4212-fac0-6cae59f8abf6.mp4");
        HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
        // 根据响应获取文件大小
        int fileLength = urlcon.getContentLength();
        System.out.println(fileLength);
    }
}
