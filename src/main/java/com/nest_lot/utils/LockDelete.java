package com.nest_lot.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class LockDelete {

	public static String access_token = "6acab32a5411ec4994f4cacc7c783f5e68c0a697b198085cbe700dcfe9b3d771";

	/**
	 * 门锁密码删除
	 */
	public static void pwddelete(String tmp_1, String tmp_2) throws Exception {

		Map<String, String> header = new HashMap<String, String>();
		header.put("version", "1.0");
		header.put("s_id", Tools.getUUID());
		header.put("access_token", access_token);
		JSONObject pushParams = new JSONObject();
		pushParams.put("lock_no", tmp_1);
		pushParams.put("pwd_no", tmp_2);
		System.out.println(LockHttpPost.doPost("http://ops.huohetech.com:80/pwd/delete", header, pushParams));
	}

	/**
	 * 门锁密码
	 */
	public static JSONObject pwdlist(String tmp) throws Exception {

		Map<String, String> header = new HashMap<String, String>();
		header.put("version", "1.0");
		header.put("s_id", Tools.getUUID());
		header.put("access_token", access_token);
		JSONObject pushParams = new JSONObject();
		pushParams.put("lock_no", tmp);
		JSONObject json = LockHttpPost.doPost("http://ops.huohetech.com:80/pwd/list", header, pushParams);
		System.out.println(json);
		return json;
	}

	/**
	 * 门锁列表
	 */
	public static List<String> locklist() throws Exception {

		List<String> list_lock_no = new ArrayList<String>();
		Map<String, String> header = new HashMap<String, String>();
		header.put("version", "1.0");
		header.put("s_id", Tools.getUUID());
		header.put("access_token", access_token);
		JSONObject pushParams = new JSONObject();
		pushParams.put("page_size", "100");
		pushParams.put("current_page", "1");
		JSONObject json = LockHttpPost.doPost("http://ops.huohetech.com:80/lock/list", header, pushParams);
		System.out.println(json);

		// 获取门锁lock_no并且声称列表以备后续用到
		JSONArray arr = json.getJSONObject("resData").getJSONObject("data").getJSONArray("rows");
		JSONObject json_son = null;
		String lock_no = "";
		for (int i = 0; i < arr.size(); i++) {

			json_son = arr.getJSONObject(i);
			lock_no = json_son.getString("lock_no");
			list_lock_no.add(lock_no);
		}
		return list_lock_no;
	}

	/**
	 * 调用删除的整体方法
	 */
	public static void lockDeleteUtils() {
		try {
			// 获取锁编号列表
			List<String> list_lock = locklist();
			for (int i = 0; i < list_lock.size(); i++) {

				// 获取锁密码列表
				String tmp_lock_no = list_lock.get(i);
				JSONObject json = pwdlist(tmp_lock_no);
				JSONArray arr = json.getJSONObject("resData").getJSONArray("data");
				JSONObject json_son = null;
				String pwd_no = "";

				for (int j = 0; j < arr.size(); j++) {

					json_son = arr.getJSONObject(j);
					pwd_no = json_son.getString("pwd_no");
					String tmp_time = json_son.getString("valid_time_end");
					System.currentTimeMillis();
					long tmp_time_long = Long.parseLong(tmp_time);
					if (tmp_time_long < System.currentTimeMillis()) {

						System.out.println(pwd_no + "===" + j + "====" + tmp_time + "===");
						pwddelete(tmp_lock_no, pwd_no);
					}
					// System.out.println(pwd_no+"==="+j+"===="+tmp_time+"===");
				}
			}

		} catch (Exception e) {
		}

	}

	/**
	 * 删除的退单房间
	 */
	public static void lockDeleteSaleInfo(String lockUuidOut, String lockUuidIn) {
		try {
			// 获取锁编号列表
			List<String> list_lock = locklist();
			for (int i = 0; i < list_lock.size(); i++) {

				// 获取锁密码列表
				String tmp_lock_no = list_lock.get(i);
				if (!tmp_lock_no.equals(lockUuidOut) || !tmp_lock_no.equals(lockUuidIn)) {
					continue;
				}
				JSONObject json = pwdlist(tmp_lock_no);
				JSONArray arr = json.getJSONObject("resData").getJSONArray("data");
				JSONObject json_son = null;
				String pwd_no = "";

				for (int j = 0; j < arr.size(); j++) {

					json_son = arr.getJSONObject(j);
					pwd_no = json_son.getString("pwd_no");

					pwddelete(tmp_lock_no, pwd_no);
				}
			}

		} catch (Exception e) {
		}

	}

	public static void main(String[] args) {

		try {
			lockDeleteUtils();

			// JSONObject json = pwdlist("11.135.63.66");
			// JSONArray arr =
			// json.getJSONObject("resData").getJSONArray("data");
			// JSONObject json_son = null;
			// String pwd_no = "";
			//
			// for(int j = 0; j < arr.size();j++){
			//
			// json_son = arr.getJSONObject(j);
			// pwd_no = json_son.getString("pwd_no");
			// String tmp_time = json_son.getString("valid_time_end");
			// System.currentTimeMillis();
			// long tmp_time_long = Long.parseLong(tmp_time);
			// if(tmp_time_long < System.currentTimeMillis()){
			//
			// System.out.println(pwd_no+"==="+j+"===="+tmp_time+"===");
			// pwddelete("11.135.63.66",pwd_no);
			// }
			// //System.out.println(pwd_no+"==="+j+"===="+tmp_time+"===");
			// }

		} catch (Exception e) {
		}
	}
}
