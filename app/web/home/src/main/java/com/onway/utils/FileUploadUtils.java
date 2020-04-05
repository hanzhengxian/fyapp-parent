package com.onway.utils;

/**
 * 通用文件上传类
 * @author liaoshengzhe
 */
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.onway.platform.common.configration.ConfigrationFactory;

public class FileUploadUtils {

	// 上传的真实路径
	public static String defaultBaseDir = ConfigrationFactory.getConfigration().getPropertyValue("upload_realpath");
	// 上传之后的访问路径
	public static String video_url = ConfigrationFactory.getConfigration().getPropertyValue("visit_url");

	/**
	 * 文件上传 MultipartFile 方式 文件名为日期加时间加三位随机数
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static final String upload(HttpServletRequest request,
			MultipartFile file) throws Exception {
		// 用户上传的文件名
		String uploadFileName = file.getOriginalFilename();
		// 通过.去截取出后缀名
		String suffix = uploadFileName.substring(uploadFileName.lastIndexOf("."));
		String filename = buildName() + suffix;
		File desc = getAbsoluteFile(defaultBaseDir, filename);
		file.transferTo(desc);
		StringBuffer url = new StringBuffer(video_url + "/" + filename);
		return new String(url);
	}

	// 上传
	private static final File getAbsoluteFile(String uploadDir, String filename)
			throws IOException {
		if (uploadDir.endsWith("/")) {
			uploadDir = uploadDir.substring(0, uploadDir.length() - 1);
		}
		if (filename.startsWith("/")) {
			filename = filename.substring(0, uploadDir.length() - 1);
		}
		File desc = new File(uploadDir + "/" + filename);
		if (!desc.getParentFile().exists()) {
			desc.getParentFile().mkdirs();
		}
		if (!desc.exists()) {
			desc.createNewFile();
		}
		return desc;
	}
	
	// 生成新文件名一
	public static String buildName() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddHHmmss");
		String name = sdf.format(new Date())
				+ RandomStringUtils.randomNumeric(3);
		return name;
	}

	// 生成新文件名二
	public String getRandomCode(int length) {
		if (length < 1 || length > 10) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= length; i++) {
			int rand = new Random().nextInt(10);
			sb.append(rand);
		}
		return String.valueOf(System.currentTimeMillis()) + sb.toString();
	}
}