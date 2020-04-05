package com.onway.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.onway.common.lang.StringUtils;

/**
 * 后台处理emoji存库 返回
 * 
 * @author yuhang.ni
 *
 * @version EmojiUtil.java 2017年12月1日 下午3:31:37 yuhang.ni
 */
public class EmojiUtil {
	/**
	 * 这里转换后可以直接存在utf-8 取出来反馈给前端的时候调用emojiRecovery2 即可
	 * 
	 * @Description 将字符串中的emoji表情转换成可以在utf-8字符集数据库中保存的格式（表情占4个字节，需要utf8mb4字符集）
	 * @param str
	 *            待转换字符串
	 * @return 转换后字符串
	 * @throws UnsupportedEncodingException
	 *             exception
	 */
	public static String emojiConvert1(String str) throws UnsupportedEncodingException {
		if (StringUtils.isBlank(str))
			return str;

		String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			try {
				matcher.appendReplacement(sb,
						"[[" + URLEncoder.encode(matcher.group(1), "UTF-8")
								+ "]]");
			} catch (UnsupportedEncodingException e) {
				// LOG.error("emojiConvert error", e);
				throw e;
			}
		}
		matcher.appendTail(sb);
		// LOG.debug("emojiConvert " + str + " to " + sb.toString()
		// + ", len：" + sb.length());
		return sb.toString();
	}

	/**
	 * @Description 还原utf8数据库中保存的含转换后emoji表情的字符串
	 * @param str
	 *            转换后的字符串
	 * @return 转换前的字符串
	 * @throws UnsupportedEncodingException
	 *             exception
	 */
	public static String emojiRecovery2(String str)
			throws UnsupportedEncodingException {
		if (StringUtils.isBlank(str))
			return str;

		String patternString = "\\[\\[(.*?)\\]\\]";

		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(str);

		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			try {
				matcher.appendReplacement(sb,
						URLDecoder.decode(matcher.group(1), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// LOG.error("emojiRecovery error", e);
				throw e;
			}
		}
		matcher.appendTail(sb);
		// LOG.debug("emojiRecovery " + str + " to " + sb.toString());
		return sb.toString();
	}

}