package com.onway.utils;

import javax.servlet.http.HttpServletRequest;

public class PathUtil {
	/**
	 *
	 * @param request
	 * @return 返回结果类似于 “F:\workSpace\bookQr\src\main\webapp\”
	 */
	public static String getAppRootPath(HttpServletRequest request) {
		// ServletActionContext.getServletContext().getRealPath("/")+"upload";
		return request.getSession().getServletContext().getRealPath("/");
	}

	/**
	 *
	 * @param request
	 * @return http://www.qh.com:8080/projectName
	 */
	public static String getHttpURL(HttpServletRequest request) {
		StringBuffer buff = new StringBuffer();
		buff.append("http://");
		buff.append(request.getServerName());
		buff.append(":");
		buff.append(request.getServerPort());
		buff.append(request.getContextPath());
		return buff.toString();
	}
}
