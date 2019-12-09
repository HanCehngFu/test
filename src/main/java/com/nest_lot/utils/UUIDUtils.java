package com.nest_lot.utils;

import java.util.UUID;

/**
 * Created by HuangPeng on 2018/6/28 18:44
 */

public class UUIDUtils {

	/**
	 * 32位uuid
	 *
	 * @return
	 */
	public static String uuid() {
		try {
			UUID uuid = UUID.randomUUID();
			String str = uuid.toString();
			String uuidStr = str.replace("-", "");
			return uuidStr;
		} catch (Exception e) {
			return "";
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			System.out.println(UUIDUtils.uuid());
		}

	}
}
