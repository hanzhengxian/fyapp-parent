package com.onway.shunfeng.print;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.sf.dto.WaybillDto;
import com.sf.util.Base64ImageTools;
import com.sf.util.MyJsonUtil;
import com.sf.util.PrintUtil;

public class PrintSfOrder {

    public static final Logger logger = Logger.getLogger(PrintSfOrder.class);

    /*********2联单**************/
    /**
     * 调用打印机 不弹出窗口 适用于批量打印【二联单】
     */
    public static String       url1   = "http://localhost:4040/sf/waybill/print?type=V2.0_poster_100mm150mm&output=noAlertPrint";
    /**
     * 调用打印机 弹出窗口 可选择份数 适用于单张打印【二联单】
     */
    public static String       url2   = "http://localhost:4040/sf/waybill/print?type=V2.0_poster_100mm150mm&output=print";

    /**
     * 直接输出图片的BASE64编码字符串 可以使用html标签直接转换成图片【二联单】
     */
    public static String       url3   = "http://localhost:4040/sf/waybill/print?type=V2.0_poster_100mm150mm&output=image";

    /*********3联单**************/
    /**
     * 调用打印机 不弹出窗口 适用于批量打印【三联单】
     */
    public static String       url4   = "http://localhost:4040/sf/waybill/print?type=V3.0_poster_100mm210mm&output=noAlertPrint";
    /**
     * 调用打印机 弹出窗口 可选择份数 适用于单张打印【三联单】
     */
    public static String       url5   = "http://localhost:4040/sf/waybill/print?type=V3.0_poster_100mm210mm&output=print";

    /**
     * 直接输出图片的BASE64编码字符串 可以使用html标签直接转换成图片【三联单】
     */
    public static String       url6   = "http://localhost:4040/sf/waybill/print?type=V3.0_poster_100mm210mm&output=image";

    /*********2联150 丰密运单**************/
    /**
     * 调用打印机 不弹出窗口 适用于批量打印【二联单】
     */
    public static String       url7   = "http://localhost:4040/sf/waybill/print?type=V2.0.FM_poster_100mm150mm&output=noAlertPrint";
    /**
     * 调用打印机 弹出窗口 可选择份数 适用于单张打印【二联单】
     */
    public static String       url8   = "http://localhost:4040/sf/waybill/print?type=V2.0.FM_poster_100mm150mm&output=print";

    /**
     * 直接输出图片的BASE64编码字符串 可以使用html标签直接转换成图片【二联单】
     */
    public static String       url9   = "http://localhost:4040/sf/waybill/print?type=V2.0.FM_poster_100mm150mm&output=image";

    /*********3联210 丰密运单**************/
    /**
     * 调用打印机 不弹出窗口 适用于批量打印【三联单】
     */
    public static String       url10  = "http://localhost:4040/sf/waybill/print?type=V3.0.FM_poster_100mm210mm&output=noAlertPrint";
    /**
     * 调用打印机 弹出窗口 可选择份数 适用于单张打印【三联单】
     */
    public static String       url11  = "http://localhost:4040/sf/waybill/print?type=V3.0.FM_poster_100mm210mm&output=print";

    /**
     * 直接输出图片的BASE64编码字符串 可以使用html标签直接转换成图片【三联单】
     */
    public static String       url12  = "http://localhost:4040/sf/waybill/print?type=V3.0.FM_poster_100mm210mm&output=image";

    public static void print(List<WaybillDto> waybillDtoList, String savePath1, String savePath2)
                                                                                                 throws Exception {
        String reqURL = url2;

        logger.error(MessageFormat.format("----------printUrl-------：，printUrl:{0}",
            new Object[] { reqURL }));

        //电子面单顶部是否需要logo 
        boolean topLogo = true;//true 需要logo  false 不需要logo
        if (reqURL.contains("V2.0") && topLogo) {
            reqURL = reqURL.replace("V2.0", "V2.1");
        }

        if (reqURL.contains("V3.0") && topLogo) {
            reqURL = reqURL.replace("V3.0", "V3.1");
        }

        /**注意 需要使用对应业务场景的url  **/
        URL myURL = new URL(reqURL);
        //其中127.0.0.1:4040为打印服务部署的地址（端口如未指定，默认为4040），
        //type为模板类型（支持两联、三联，尺寸为100mm*150mm和100mm*210mm，type为poster_100mm150mm和poster_100mm210mm）
        //A5 poster_100mm150mm   A5 poster_100mm210mm
        //output为输出类型,值为print或image，如不传，
        //默认为print（print 表示直接打印，image表示获取图片的BASE64编码字符串）
        //V2.0/V3.0模板顶部是带logo的  V2.1/V3.1顶部不带logo

        HttpURLConnection httpConn = (HttpURLConnection) myURL.openConnection();
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        httpConn.setUseCaches(false);
        httpConn.setRequestMethod("POST");
        //        httpConn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        httpConn.setRequestProperty("Content-Type", "text/plain;charset=utf-8");

        httpConn.setConnectTimeout(5000);
        httpConn.setReadTimeout(3 * 5000);

        logger.error(MessageFormat.format("请求参数：，waybillDtoList:{0}",
            new Object[] { MyJsonUtil.object2json(waybillDtoList) }));

        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, waybillDtoList);

        httpConn.getOutputStream().write(stringWriter.toString().getBytes("utf-8"));

        httpConn.getOutputStream().flush();
        httpConn.getOutputStream().close();
        InputStream in = httpConn.getInputStream();

        BufferedReader in2 = new BufferedReader(new InputStreamReader(in));

        String y = "";

        String strImg = "";
        while ((y = in2.readLine()) != null) {
            strImg = y.substring(y.indexOf("[") + 1, y.length() - "]".length() - 1);
            if (strImg.startsWith("\"")) {
                strImg = strImg.substring(1, strImg.length());
            }
            if (strImg.endsWith("\"")) {
                strImg = strImg.substring(0, strImg.length() - 1);
            }
        }

        //将换行全部替换成空    
        strImg = strImg.replace("\\n", "");

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String dateStr = format.format(new Date());

        List<String> files = new ArrayList<String>();
        if (strImg.contains("\",\"")) {
            //如子母单及签回单需要打印两份或者以上
            String[] arr = strImg.split("\",\"");
            /**输出图片到本地 支持.jpg、.png格式**/
            for (int i = 0; i < arr.length; i++) {
                String fileName = savePath1 + dateStr + "-" + i + ".jpg";
                Base64ImageTools.generateImage(arr[i].toString(), fileName);
                files.add(fileName);
            }
        } else {
            String fileName = savePath2 + dateStr + ".jpg";
            Base64ImageTools.generateImage(strImg, fileName);
            files.add(fileName);
        }

        //如需调用本地打印机(非服务端打印机请使用url3/url6/url9/url12 并且取消以下注释) 
        //        int high = 0;
        //        if (reqURL.contains("image") && !files.isEmpty()) {
        //            if (reqURL.contains("V2")) {
        //                high = 150;
        //            } else {
        //                high = 210;
        //            }
        //            for (String fileName : files) {
        //                PrintUtil.drawImage(fileName, high, false);//false为不弹出打印框
        //            }
        //        }
        //        System.exit(0);
    }

    public static void print(List<WaybillDto> waybillDtoList, String savePath1, String savePath2,
                             List<String> files) throws Exception {
        String reqURL = url9;

        logger.error(MessageFormat.format("----------printUrl-------：，printUrl:{0}",
            new Object[] { reqURL }));

        //电子面单顶部是否需要logo 
        boolean topLogo = true;//true 需要logo  false 不需要logo
        if (reqURL.contains("V2.0") && topLogo) {
            reqURL = reqURL.replace("V2.0", "V2.1");
        }

        if (reqURL.contains("V3.0") && topLogo) {
            reqURL = reqURL.replace("V3.0", "V3.1");
        }

        /**注意 需要使用对应业务场景的url  **/
        URL myURL = new URL(reqURL);
        //其中127.0.0.1:4040为打印服务部署的地址（端口如未指定，默认为4040），
        //type为模板类型（支持两联、三联，尺寸为100mm*150mm和100mm*210mm，type为poster_100mm150mm和poster_100mm210mm）
        //A5 poster_100mm150mm   A5 poster_100mm210mm
        //output为输出类型,值为print或image，如不传，
        //默认为print（print 表示直接打印，image表示获取图片的BASE64编码字符串）
        //V2.0/V3.0模板顶部是带logo的  V2.1/V3.1顶部不带logo

        HttpURLConnection httpConn = (HttpURLConnection) myURL.openConnection();
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        httpConn.setUseCaches(false);
        httpConn.setRequestMethod("POST");
        //        httpConn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        httpConn.setRequestProperty("Content-Type", "text/plain;charset=utf-8");

        httpConn.setConnectTimeout(5000);
        httpConn.setReadTimeout(3 * 5000);

        logger.error(MessageFormat.format("请求参数：，waybillDtoList:{0}",
            new Object[] { MyJsonUtil.object2json(waybillDtoList) }));

        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, waybillDtoList);

        httpConn.getOutputStream().write(stringWriter.toString().getBytes("utf-8"));

        httpConn.getOutputStream().flush();
        httpConn.getOutputStream().close();
        InputStream in = httpConn.getInputStream();

        BufferedReader in2 = new BufferedReader(new InputStreamReader(in));

        String y = "";

        String strImg = "";
        while ((y = in2.readLine()) != null) {
            strImg = y.substring(y.indexOf("[") + 1, y.length() - "]".length() - 1);
            if (strImg.startsWith("\"")) {
                strImg = strImg.substring(1, strImg.length());
            }
            if (strImg.endsWith("\"")) {
                strImg = strImg.substring(0, strImg.length() - 1);
            }
        }

        //将换行全部替换成空    
        strImg = strImg.replace("\\n", "");

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String dateStr = format.format(new Date());

        if (strImg.contains("\",\"")) {
            //如子母单及签回单需要打印两份或者以上
            String[] arr = strImg.split("\",\"");
            /**输出图片到本地 支持.jpg、.png格式**/
            for (int i = 0; i < arr.length; i++) {
                String fileName = savePath1 + dateStr + "-" + i + ".jpg";
                Base64ImageTools.generateImage(arr[i].toString(), fileName);
                files.add(fileName);
            }
        } else {
            String fileName = savePath2 + dateStr + ".jpg";
            Base64ImageTools.generateImage(strImg, fileName);
            files.add(fileName);
        }
    }

    public static void print(List<String> files) throws Exception {
        int high = 150;
        if (!files.isEmpty()) {
            for (String fileName : files) {
                PrintUtil.drawImage(fileName, high, true);//false为不弹出打印框
            }
        }
        //        System.exit(0);

    }

}
