package com.nest_lot.utils;

import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author:chackchang
 * @time:2018-3-15
 * @desc:
 */
public class LockHttpPost {

	private static CloseableHttpClient httpClient;
	public static final String CHARSET = "UTF-8";

	static {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}

	/**
	 *
	 * @param url
	 * @param jsonParams
	 * @return
	 */
	public static JSONObject doPost(String url, Map<String, String> header, JSONObject jsonParams) {

		// httpClient =
		// HttpClientUtils.getHttpClient(httpClient,"ops.huohetech.com");
		httpClient = HttpClientUtils.createHttpClient(200, 40, 100, "ops.huohetech.com", 80);
		if (Tools.isEmpty(url)) {
			return null;
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(jsonParams.toString(), "utf-8");
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);

			Iterator<Map.Entry<String, String>> iterator = header.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> next = iterator.next();
				httpPost.setHeader(next.getKey(), next.getValue());
			}

			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity httpEntity = response.getEntity();
			String resData = EntityUtils.toString(httpEntity);
			System.out.println("锁返回信息内容==================" + resData);
			JSONObject resJson = new JSONObject();
			resJson.put("resData", resData);
			EntityUtils.consume(httpEntity);// 按照官方文档的说法：二者都释放了才可以正常的释放链接
			response.close();
			httpClient.close();
			// httpClient.getConnectionManager()..shutdown();
			return resJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
