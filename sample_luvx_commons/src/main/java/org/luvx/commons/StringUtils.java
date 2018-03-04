package org.luvx.commons;

public class StringUtils {
	/**
	 * 字符串翻转
	 */
	public static String reverse(String str) {
		if (str == null || str.length() <= 1)
			return str;
		return reverse(str.substring(1)) + str.charAt(0);
	}
}
