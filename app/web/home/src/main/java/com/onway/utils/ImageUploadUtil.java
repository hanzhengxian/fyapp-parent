package com.onway.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.onway.common.lang.ArrayUtils;
import com.onway.common.lang.DateUtils;
import com.onway.common.lang.StringUtils;
import com.onway.platform.common.helper.SystemHelper;

/**
 * 图片上传工具类
 * 
 * @author wwf
 * @version $Id: ImageUploadUtil.java, v 0.1 2016年4月13日 下午3:58:08 Administrator Exp $
 */
public class ImageUploadUtil {

    @SuppressWarnings("unused")
    private static final Logger   log          = Logger.getLogger(ImageUploadUtil.class);

    private static final String   OS_WINDOWS   = "Windows";

    private static final String   BACK_SYMBOL  = "\\";

    @SuppressWarnings("unused")
    private static final String[] REPLACE_STRS = new String[] { "data:image/jpg;base64,",
            "data:image/jpeg;base64,", "data:image/png;base64,", "data:image/gif;base64,",
            "data:image/bmp;base64,", "data:image/tiff;base64,", "data:image/psd;base64,",
            "data:image/svg;base64,"          };

    private static final String   SPLIT_STRS   = ";base64,";

    private static final String   SYMBOL       = "/";

    /**
     * 文件上传的基本路径
     */
    @SuppressWarnings("unused")
    private static String         BASE_PATH    = "/usr/local/upload";

    //定义一个数组，用于保存可上传的文件类型 
    private static List<String>   fileTypes    = null;
    static {
        /**
         * 根据不同系统，初始化不同的图片保存路径
         */
        String os = System.getProperty("os.name");
        if (os != null && os.startsWith(OS_WINDOWS)) {
            BASE_PATH = "D:/upload/file/";
        }

        //定义一个数组，用于保存可上传的文件类型 
        fileTypes = new ArrayList<String>();
        fileTypes.add("jpg");
        fileTypes.add("jpeg");
        fileTypes.add("bmp");
        fileTypes.add("gif");
        fileTypes.add("txt");
    }

    /**
     * 判断请求里面是否存在数据
     * 
     * @param request
     * @param keyName
     * @return
     */
    public static boolean exitImage(HttpServletRequest request, String keyName) {
        //转型为MultipartHttpRequest(重点的所在)  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //  获得第1张图片（根据前台的name名称得到上传的文件）   
        MultipartFile imgFile1 = multipartRequest.getFile(keyName);//"imgFile"

        //保存第一张图片  
        if (!(imgFile1.getOriginalFilename() == null || "".equals(imgFile1.getOriginalFilename()))) {
            return true;
        }
        return false;
    }

    /*<p>  
        </p><p>其实上面的代码还是比较简单的，重点再于将我们常见的request对象转换为<span style="white-space: pre;">MultipartHttpRequest对象，有了这个对象，我们就可以得到用户上传的文件了。得到用户上传的文件之后，</span></p>  
        <p><span style="white-space: pre;">我们就可以做一些我们想做的事情了。在上面我们还做了一些事，那就是判断用户上传的文件类型是否属于我们所定义的那个</span></p>  
        <p>数组内的类型，至于如何判断是否属于允许上传的类型，我会在下面的方法当中给出。其实也可以将下面的代码写在一个方法里，但是了为重用，我就分开写了。也许我的做法不是最好的。相当于给大家一个方向吧！</p>  
        <p> </p>  
        <p>我们来看下面的两个方法，这两个方法最主要做两件事。一、判断用户上传的文件是否属于我们定义的类型范围之内，第二、将文件保存到指定的路径，这个路径是我们自己创建的。</p>  
        <p>  
        </p>
        <pre class="java" name="code">/** 
         * 通过传入页面读取到的文件，处理后保存到本地磁盘，并返回一个已经创建好的File 
         * @param imgFile 从页面中读取到的文件 
         * @param typeName  商品的分类名称 
         * @param brandName 商品的品牌名称 
         * @param fileTypes 允许的文件扩展名集合 
         * @return 
         */
    public static File getFile(MultipartFile imgFile, String typeName, String brandName) {
        String fileName = imgFile.getOriginalFilename();
        //获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名  
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        //对扩展名进行小写转换  
        //        ext = ext.toLowerCase();

        File file = null;
        if (fileTypes.contains(ext.toLowerCase())) { //如果扩展名属于允许上传的类型，则创建文件  
            //            file = creatFolder(typeName, brandName, UUID.randomUUID().toString() + "." + ext);
            try {
                file = creatFolder(typeName, UUID.randomUUID().toString() + "." + ext);
                imgFile.transferTo(file); //保存上传的文件  
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 文件上传
     * @param imgFile
     * @param absolutePath
     * @return
     * @throws IOException
     */
    public static File getFile(MultipartFile imgFile, String absolutePath) throws IOException {

        if (-1 != absolutePath.indexOf(BACK_SYMBOL)) {
            //absolutePath = absolutePath.replace(BACK_SYMBOL, "/"); //去掉"/"File.separator
        }

        String fileName = imgFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

        File userAuthImage = new File(absolutePath + File.separator + UUID.randomUUID().toString()
                                      + "." + ext);
        String os = System.getProperty("os.name");
        if (!(os != null && os.startsWith(OS_WINDOWS))) {
            userAuthImage.setExecutable(true);//设置可执行权限  
            userAuthImage.setReadable(true);//设置可读权限  
            userAuthImage.setWritable(true);//设置可写权限
        }

        if (imgFile == null || ArrayUtils.isEmpty(imgFile.getBytes())) {
            return null;
        }
        FileUtils.writeByteArrayToFile(userAuthImage, imgFile.getBytes());
        return userAuthImage;
    }

    public static File createFolder(String fileUrl, String data) throws IOException {
        File userAuthImage = new File(fileUrl);
        FileUtils.writeByteArrayToFile(userAuthImage, data.getBytes());
        return userAuthImage;
    }

    /** 
     * 检测与创建一级、二级文件夹、文件名 
                 这里我通过传入的两个字符串来做一级文件夹和二级文件夹名称 
                通过此种办法我们可以做到根据用户的选择保存到相应的文件夹下 
     */
    public static File creatFolder(String typeName, String brandName, String fileName) {
        File file = null;
        typeName = typeName.replaceAll("/", ""); //去掉"/"  
        typeName = typeName.replaceAll(" ", ""); //替换半角空格  
        typeName = typeName.replaceAll(" ", ""); //替换全角空格  

        brandName = brandName.replaceAll("/", ""); //去掉"/"  
        brandName = brandName.replaceAll(" ", ""); //替换半角空格  
        brandName = brandName.replaceAll(" ", ""); //替换全角空格  

        File firstFolder = new File(typeName); //一级文件夹  
        if (firstFolder.exists()) { //如果一级文件夹存在，则检测二级文件夹  
            File secondFolder = new File(firstFolder, brandName);
            if (secondFolder.exists()) { //如果二级文件夹也存在，则创建文件  
                file = new File(secondFolder, fileName);
            } else { //如果二级文件夹不存在，则创建二级文件夹  
                secondFolder.mkdir();
                file = new File(secondFolder, fileName); //创建完二级文件夹后，再合建文件  
            }
        } else { //如果一级不存在，则创建一级文件夹  
            firstFolder.mkdir();
            File secondFolder = new File(firstFolder, brandName);
            if (secondFolder.exists()) { //如果二级文件夹也存在，则创建文件  
                file = new File(secondFolder, fileName);
            } else { //如果二级文件夹不存在，则创建二级文件夹  
                secondFolder.mkdir();
                file = new File(secondFolder, fileName);
            }
        }
        return file;
    }

    /**
     * 创建文件
     * 
     * @param url
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File creatFolder(String url, String fileName) throws IOException {
        try {
            File file = creatFolder(url, "", fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (file.exists()) {
                file.delete();
            }

            if (!file.createNewFile()) {
                String os = System.getProperty("os.name");
                if (!(os != null && os.startsWith(OS_WINDOWS))) {
                    file.setExecutable(true);//设置可执行权限  
                    file.setReadable(true);//设置可读权限  
                    file.setWritable(true);//设置可写权限
                }
                return null;
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拷贝文件
     * 
     * @param oldPath
     * @param newPath
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时 
                InputStream inStream = new FileInputStream(oldPath); //读入原文件 
                @SuppressWarnings("resource")
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小 
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 获取文件的文件后缀
     * 
     * @param imgFile
     * @return
     */
    public static String getfileExt(MultipartFile imgFile) {
        String fileName = imgFile.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    /**
     * 获取文件的文件后缀
     * 
     * @param imgFile
     * @return
     */
    public static String getStrfileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    /**
     * 文件上传
     * @param imgFile
     * @param absolutePath
     * @return
     * @throws IOException
     */
    public static File doUploadFile(MultipartFile imgFile, String absolutePath) throws IOException {

        File userAuthImage = new File(absolutePath);
        String os = System.getProperty("os.name");
        if (!(os != null && os.startsWith(OS_WINDOWS))) {
            userAuthImage.setExecutable(true);//设置可执行权限  
            userAuthImage.setReadable(true);//设置可读权限  
            userAuthImage.setWritable(true);//设置可写权限
        }

        if (imgFile == null) {//|| ArrayUtils.isEmpty(imgFile.getBytes())
            return null;
        }
        FileUtils.writeByteArrayToFile(userAuthImage, imgFile.getBytes());
        return userAuthImage;
    }

    /**
     * 组织文件地址
     * 
     * @param uploadRealpath
     * @return
     */
    public static String filePath(String uploadRealpath) {

        String todayStr = DateUtils.getTodayString();
        String real_path = SystemHelper.getSystemConfig(uploadRealpath);
        real_path = real_path.replace(BACK_SYMBOL, File.separator);

        String os = System.getProperty("os.name");
        if (os != null && os.startsWith(OS_WINDOWS)) {
            real_path = real_path + SYMBOL + todayStr + SYMBOL;
        } else {
            real_path = real_path + File.separator + todayStr + File.separator;
        }
        return real_path;

    }

    // 获得UUID为名字的文件
    public static String fileUUIDName(MultipartFile imgFile) {

        String fileName = imgFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

        return UUID.randomUUID().toString() + "." + ext;
    }

    /***
     * 
     * 
     * @param url
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File creatFile(String filePath) throws IOException {
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (file.exists()) {
                file.delete();
            }

            if (!file.createNewFile()) {
                String os = System.getProperty("os.name");
                if (!(os != null && os.startsWith(OS_WINDOWS))) {
                    file.setExecutable(true);//设置可执行权限  
                    file.setReadable(true);//设置可读权限  
                    file.setWritable(true);//设置可写权限
                }
                return null;
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件上传
     * @param imgFile
     * @param absolutePath
     * @return
     * @throws IOException
     */
    public static File doUploadBase64File(String imgFile, String absolutePath) throws IOException {

        if (StringUtils.contains(imgFile, SPLIT_STRS)) {
            String imgStr[] = imgFile.split(SPLIT_STRS);
            if (imgStr.length != 2) {
                return null;
            }
            imgFile = imgStr[1];
        }

        File userAuthImage = new File(absolutePath);
        String os = System.getProperty("os.name");
        if (!(os != null && os.startsWith(OS_WINDOWS))) {
            userAuthImage.setExecutable(true);//设置可执行权限  
            userAuthImage.setReadable(true);//设置可读权限  
            userAuthImage.setWritable(true);//设置可写权限
        }

        if (imgFile == null || ArrayUtils.isEmpty(imgFile.getBytes())) {
            return null;
        }

        BASE64Decoder decoder = new BASE64Decoder();
        FileUtils.writeByteArrayToFile(userAuthImage, decoder.decodeBuffer(imgFile));

        return userAuthImage;

    }

    /**
     * 将Base64位编码的图片进行解码，并保存到指定目录
     * 
     * @param base64
     *          base64编码的图片信息
     * @return
     */
    public static File decodeBase64ToImage(String base64, String absolutePath) {
        File file = new File(absolutePath);
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            FileOutputStream write = new FileOutputStream(file);
            byte[] decoderBytes = decoder.decodeBuffer(base64);
            write.write(decoderBytes);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    public static void main(String[] args) {
        // 测试从Base64编码转换为图片文件  //data:image/jpg;base64,

        String strImg = "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ceaLj3NsPV9jWhVtflaf3O58xaHr2v8AhwznSZZLfz9vmfuFfdtzj7ynHU1sf8LG8cf9BGT/AMA4v/iK+haK41hJxVlN/wBfM+gqZ/h6kuephotvq7N/+knzbpXhnxD4x1gyvDOfOfdPeTqQq++T1OOgH8q6X4ieGdR0TxBb65p0DvaRJDtkVd3lPGAo3D0wq89K9toqlg48rV9e5nLiKs68aiglFJrl8nbr8ux4YPjN4jA/489LP/bKT/4uuT8TeJr3xVqSX99FbxypEIQIFIXAJPcnn5jX0/RSlhak1aU/w/4JVDPMLQnz0sMk/wDF/wAA8P8AiP8A8if4K/68v/acVeh/DP8A5J5pX0l/9GvXW0VtChy1HO/SxwYnM/bYSOF5bWk3e/e+lreZ82eN/CV34W1ubET/ANnyuWtpwvy4PO3PZh0x7ZrpdH+M+o2VkkGo6dHfyIoUTCYxM3u3BBP5V7XNDFcRNFNGkkbDDI6ggj3BqlBoOjWpzb6TYRH1jtkX+Qr6R5tSrUlDFUuZrrex4nsmneLPH/EPjvV/GGn2kem+HLuGS2u0uo54Waf5lDYGAg9c/hWq/wAZb+xRI9R8MNHPj5sztEG9wrISB+Jr1sAAAAYAqKe3guojFcQxzRnqkihgfwNYfXsK0oSoe6v7zvr5j5Jb3Pnfxz45/wCEz+wf8S77H9k8z/lv5m/ft/2RjG39a6z4O+GbqO7m1+6haOAxGK23jG/JGWHsAMZ75PpXpsfhnQIpBJHoemo453LaRg/nitQAAAAYArXEZrB4b6th4csfW/mKNJ83NJnnnxl/5E22/wCv5P8A0B65Hw38Vv8AhH/D9ppX9i/aPs6keb9q2bssT02HHX1r3GivnJ0ZOfPGVvke9h8yoQwqw1ejzpO/xNfkjyP/AIXf/wBS9/5O/wD2uuL8S+JJPGfiezvI7BoHCJAsKv5hbDE56D+90xX0hRUTw9SatKenob4fNsJhp+0o4a0v8bf5o8i+N/39D+k//tOm+IviTplwt3ol/wCGxeJBI0IL3GMlSV3DC5U8djmvX6KqVCTlKUZWv5GNHNKMaNOlVpc3Jez5mt3foj5wsPDvi6XQ724sLa/h048vArsvmj2T+PHfitfwv480rwpa+UnhgC+A2zXHn/O579VJX6DiveKKiOEcGnGWvpc6qufxrxlCvRvFvpJx++25BZ3H2uxt7nbs86JZNuc4yM4zU9FFdh867N6BRRRQIKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAP/2Q==";
        GenerateImage(strImg, "d:\\test\\1.jpg");// C:\\Users\\Administrator\\Desktop\\test\\1.jpg"

        // 测试从图片文件转换为Base64编码  
        System.out.println(getImageToBASE64Str("D:\\test\\yx\\1.jpg"));

    }

    public static String getBytesToBASE64Str(byte[] data) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        // 对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串  
    }

    public static String getImageToBASE64Str(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        byte[] data = null;

        // 读取图片字节数组  
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串  
    }

    public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) // 图像数据为空  
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码  
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据  
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片  
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
