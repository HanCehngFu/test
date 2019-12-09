package com.nest_lot.utils;

import java.security.MessageDigest;

/**
 * @author:chackchang
 * @time:2018-2-25
 * @desc:
 */
public class SecurityUtil {

	/*
	 * md5
	 */
	public static final String KEY_MD5 = "MD5";

	public static String encodemd5(String key) {

		try {
			MessageDigest digest = MessageDigest.getInstance(KEY_MD5);
			byte[] result = digest.digest(key.getBytes());
			StringBuffer buffer = new StringBuffer();
			// 把每一个byte 做一个与运算 0xff;
			for (byte b : result) {
				// 与运算
				int number = b & 0xff;// 加盐
				String str = Integer.toHexString(number);
				if (str.length() == 1) {
					buffer.append("0");
				}
				buffer.append(str);
			}
			return buffer.toString();
		} catch (Exception e) {
			return "";
		}

	}

}
