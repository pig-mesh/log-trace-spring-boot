package com.pig4cloud.trace.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * url 工具
 *
 * @author L.cm
 */
public class URLUtil {

	/**
	 * url 解码
	 * @param url url
	 * @return String
	 */
	public static String decode(String url) {
		try {
			return URLDecoder.decode(url, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
