package com.nest_lot.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.json.JSONException;
import com.nest_lot.constant.ResultBase;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.RSClient;
import com.qiniu.api.rs.URLUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiniuUtil {

	// 设置好账号的ACCESS_KEY和SECRET_KEY
	String ACCESS_KEY = "BkeUA4LZ03G4SYlWpq6bRo_w6QoimPrC8pLdBwC3"; // 这两个登录七牛
																	// 账号里面可以找到
	String SECRET_KEY = "85Ms6NNJaHjblYbG244yJSilAXx1c0pZvn1-I-xv";
	public static String  domain = "http://qiniu.nestlot.com/";
	// String domain ="http://p5tfgxo99.bkt.clouddn.com/";
	// 要上传的空间
	String bucketname = "chaoluo888"; // 对应要上传到七牛上 你的那个路径（自己建文件夹 注意设置公开）

	// 密钥配置
	Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	// 创建上传对象
	UploadManager uploadManager = new UploadManager();

	// 简单上传，使用默认策略，只需要设置上传的空间名就可以了
	public String getUpToken() {
		return auth.uploadToken(bucketname);
	}

	// 普通上传
	public ResultBase upload(String FilePath) throws IOException {
		ResultBase resultBase = new ResultBase();
		try {
			String key = Tools.getUUID();
			// 调用put方法上传
			Response res = uploadManager.put(FilePath, key, getUpToken());
			resultBase.setEntity(res.bodyString());
			// 打印返回的信息
			System.out.println(res.bodyString());
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				resultBase.setEntity(r.bodyString());
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return resultBase;
	}

	// 通过文件路径上传文件
	public ResultBase uploadFile(String localFile) throws AuthException, JSONException {
		File file = new File(localFile);
		/**
		 * 文件后缀名 文件扩展名
		 */
		String filenameExtension = localFile.substring(localFile.lastIndexOf("."), localFile.length());
		return uploadFile(file, filenameExtension);
	}

	// 通过File上传
	public ResultBase uploadFile(File file, String filenameExtension) throws AuthException, JSONException {
		ResultBase resultBase = new ResultBase();
		String uptoken = getUpToken();

		// 可选的上传选项，具体说明请参见使用手册。
		PutExtra extra = new PutExtra();
		SimpleDateFormat time = new SimpleDateFormat("yyyy/MM/dd");
		// 上传文件
		PutRet ret = IoApi.putFile(uptoken, time.format(new Date()) + "/" + UUID.randomUUID() + filenameExtension, file.getAbsolutePath(), extra);
		if (ret.ok()) {
			resultBase.setCode(ret.statusCode);
			resultBase.setMessage("上传成功!");
			resultBase.setEntity(ret.getKey());
			// +"?watermark/1/image/aHR0cDovL3d3dy5iMS5xaW5pdWRuLmNvbS9pbWFnZXMvbG9nby0yLnBuZw==/dissolve/50/gravity/SouthEast/dx/20/dy/20"
		} else {
			resultBase.setCode(ret.statusCode);
			resultBase.setMessage("上传失败!");
		}
		return resultBase;
	}

	/**
	 * 从 inputstream 中写入七牛
	 *
	 * @param key
	 *            文件名
	 * @param content
	 *            要写入的内容
	 * @return
	 * @throws AuthException
	 * @throws JSONException
	 */
	public boolean uploadFile(String key, String content) throws AuthException, JSONException {
		// 读取的时候按的二进制，所以这里要同一
		ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());

		String uptoken = getUpToken();

		// 可选的上传选项，具体说明请参见使用手册。
		PutExtra extra = new PutExtra();

		// 上传文件
		PutRet ret = IoApi.Put(uptoken, key, inputStream, extra);

		if (ret.ok()) {
			return true;
		} else {
			return false;
		}
	}

	// 获得下载地址
	public String getDownloadFileUrl(String filename) throws Exception {
		Mac mac = getMac();
		String baseUrl = URLUtils.makeBaseUrl(domain, filename);
		GetPolicy getPolicy = new GetPolicy();
		String downloadUrl = getPolicy.makeRequest(baseUrl, mac);
		return downloadUrl;
	}

	// 删除文件
	public void deleteFile(String filename) {
		Mac mac = getMac();
		RSClient client = new RSClient(mac);
		client.delete(domain, filename);
	}

	private Mac getMac() {
		Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);
		return mac;
	}

}
