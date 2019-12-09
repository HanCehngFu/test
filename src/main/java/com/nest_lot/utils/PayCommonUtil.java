package com.nest_lot.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

import javax.net.ssl.SSLContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author 吴榧
 *
 */
public class PayCommonUtil {



	private static final Logger log = LoggerFactory.getLogger(PayCommonUtil.class);

	public static String CreateNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.indexOf(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	/**
	 * XML格式字符串转换为Map
	 *
	 * @param strXML
	 *            XML字符串
	 * @return XML数据转换后的Map
	 * @throws Exception
	 */
	public static Map<String, String> xmlToMap(String strXML) throws Exception {
		try {
			Map<String, String> data = new HashMap<String, String>();
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
			org.w3c.dom.Document doc = documentBuilder.parse(stream);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getDocumentElement().getChildNodes();
			for (int idx = 0; idx < nodeList.getLength(); ++idx) {
				Node node = nodeList.item(idx);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					org.w3c.dom.Element element = (org.w3c.dom.Element) node;
					data.put(element.getNodeName(), element.getTextContent());
				}
			}
			try {
				stream.close();
			} catch (Exception ex) {
				// do nothing
			}
			return data;
		} catch (Exception ex) {
			throw ex;
		}

	}

	/**
	 * 
	 * 
	 * @Description：将请求参数转换为xml格式的string
	 * @param parameters
	 *            请求参数
	 * @return
	 */
	/*
	 * public static String getRequestXml(SortedMap<Object,Object> parameters){
	 * String entity = ""; try { StringBuffer sb = new StringBuffer();
	 * sb.append("<xml>"); Set es = parameters.entrySet(); Iterator it =
	 * es.iterator(); while(it.hasNext()) { Map.Entry entry = (Map.Entry)it.next();
	 * String k = (String)entry.getKey(); Object v = entry.getValue(); if
	 * ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".
	 * equalsIgnoreCase(k)) { sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
	 * }else { sb.append("<"+k+">"+v+"</"+k+">"); } } sb.append("</xml>"); entity =
	 * new String(sb.toString().getBytes("utf-8"),"ISO8859-1"); } catch
	 * (UnsupportedEncodingException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return entity; }
	 */

	public static String getRequestXml(SortedMap<Object, Object> map) {
		String xmlResult = "";

		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		for (Object key : map.keySet()) {
			System.out.println(key + "========" + map.get(key));

			String value = "<![CDATA[" + map.get(key) + "]]>";
			sb.append("<" + key + ">" + value + "</" + key + ">");
		}
		sb.append("</xml>");
		xmlResult = sb.toString();

		return xmlResult;
	}

	public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		Set<?> es = parameters.entrySet();
		Iterator<?> it = es.iterator();
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + WxConfigUtil.mch_key);
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}

	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
	}

	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		CloseableHttpResponse response=null;
		try {

			// 拼接证书的路径
			String path = GetResource.class.getClassLoader().getResource("apiclient_cert.p12").getPath();
			KeyStore keyStore = KeyStore.getInstance("PKCS12");

			// 加载本地的证书进行https加密传输
			FileInputStream instream = new FileInputStream(new File(path));

			keyStore.load(instream, WxConfigUtil.mch_id.toCharArray()); // 加载证书密码，默认为商户ID

			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WxConfigUtil.mch_id.toCharArray()) // 加载证书密码，默认为商户ID
					.build();
			// Allow TLSv1 protocol only
			@SuppressWarnings("deprecation")
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPost httpost = new HttpPost(requestUrl); // 设置响应头信息
			httpost.setEntity(new StringEntity(new String(outputStr.getBytes("UTF-8"), "ISO8859-1")));
			response = httpclient.execute(httpost);
			String strResult = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
			log.debug("统一下单返回xml：" + strResult);
			return strResult;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}finally {
			if(response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
