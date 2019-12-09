package com.nest_lot.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 功能：工具类 作者：wf 日期：2016年6月16日
 */
public class Tools {
	private static final Logger logger = LoggerFactory.getLogger(Tools.class);

	public static String genNull(String str) {
		return str == null ? "" : str;
	}

	public static boolean isEmpty(String str) {
		return (str == null) || (str.trim().equals(""));
	}

	public static boolean isTrimEmpty(String str) {
		return (str == null) || (str.equals("")) || (isEmpty(str.trim()));
	}

	public static String getAllUrl(String url) {
		if (url.indexOf("http://") != -1) {
			return url;
		}
		//String urlAll = "http://p5tfgxo99.bkt.clouddn.com/" + url;
		String urlAll = "http://qiniu.nestlot.com/" + url;
		return urlAll;
	}

	public static String genString(Object value) {
		if (value == null)
			return null;
		else if (value instanceof String)
			return (String) value;
		else
			return value.toString();
	}

	public static String genString(Object value, String defaultValue) {
		if (value == null)
			return defaultValue;
		else if (value instanceof String)
			return (String) value;
		else
			return value.toString();
	}

	public static String strToDateFormat(String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(false);
		Date newDate = formatter.parse(date);
		formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(newDate);
	}

	/**
	 * MultipartFile 转换成File
	 *
	 * @param multfile
	 *            原文件类型
	 * @return File
	 * @throws IOException
	 */
	public static File multipartToFile(MultipartFile multfile) {

		CommonsMultipartFile cf = (CommonsMultipartFile) multfile;
		// 这个myfile是MultipartFile的
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File file = fi.getStoreLocation();
		return file;
	}

	public static int genInt(String sValue) {
		return Tools.genInt(sValue, 0);
	}

	public static int genInt(String sValue, int defaultValue) {
		if (Tools.isEmpty(sValue)) {
			return defaultValue;
		}

		try {
			return Integer.parseInt(sValue);
		} catch (Exception ex) {
			return defaultValue;
		}
	}

	public static int genInt(Object value) {
		if (value == null)
			return 0;
		else if (value instanceof Integer)
			return (int) value;
		else
			return Tools.genInt(value.toString());
	}

	public static long genLong(String sValue) {
		long lValue;
		try {
			lValue = Long.parseLong(sValue);
		} catch (Exception ex) {
			lValue = 0L;
		}

		return lValue;
	}

	public static long genLong(Object value) {
		if (value == null)
			return 0L;
		else if (value instanceof Long)
			return (long) value;
		else
			return Tools.genLong(value.toString());
	}

	/**
	 * 创建微信签名
	 * 
	 * @param parameters
	 * @param key
	 * @return
	 */
	public static String creatSign(SortedMap<Object, Object> parameters, String key) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		// wxconfig.apikey。这句代码是获取商务号设置的api秘钥。这里不方便贴出来，
		// 复制签名代码的人，需要把该常量改成自己商务号的key值。原因是Api规定了签名必须加上自己的key值哦
		sb.append("key=" + key);
		String sign = "";
		try {
			sign = MD5Util.MD5Encode(sb.toString(), "utf-8").toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(sign);
		return sign;
	}

	public static Date genDate(String sValue, String sFormat) {
		Date dValue;
		try {
			if ((sFormat == null) || (sFormat.trim().equals(""))) {
				sFormat = "yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(sFormat);

			dValue = dateFormat.parse(sValue);
		} catch (Exception ex) {
			dValue = new Date(0L);
		}

		return dValue;
	}

	public static Date genDate(String sValue) {
		return genDate(sValue, "");
	}

	public static Date genJustDate(String sValue) {
		return genDate(sValue, "yyyy-MM-dd");
	}

	public static Date genDate(long lValue, String sFormat) {
		if ((sFormat == null) || (sFormat.trim().equals(""))) {
			sFormat = "yyyy-MM-dd HH:mm:ss";
		}
		return genDate(formatDate(new Date(lValue), sFormat), sFormat);
	}

	public static long genLong(Date date) {
		if (date != null) {
			return date.getTime();
		}
		return 0L;
	}

	public static Date genDate(long value) {
		if (value != 0L) {
			return genDate(value, "");
		}
		return null;
	}

	public static Date genJustDate(long lValue) {
		return genDate(lValue, "yyyy-MM-dd");
	}

	public static double formatData(double fData, String sFormatString) {
		try {
			DecimalFormat df = new DecimalFormat(sFormatString);

			return Double.parseDouble(df.format(fData));
		} catch (NumberFormatException nfe) {
			logger.error("", nfe);
		}
		return 0.0D;
	}

	public static String formatDate(long lDate, String sFormat) {
		return formatDate(new Date(lDate), sFormat);
	}

	public static String formatDate(long lDate) {
		return formatDate(new Date(lDate), "");
	}

	public static List<Date> getMonthBetweenDate(Date beginDate, Date endDate) {
		if (beginDate.getTime() == endDate.getTime()) {
			return null;
		}
		List lDate = new ArrayList();
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);// 把结束时间加入集合
		return lDate;
	}

	public static String formatJustDate(long lDate) {
		return formatDate(new Date(lDate), "yyyy-MM-dd");
	}

	public static String formatDate(Date date, String sFormat) {
		if (date == null)
			return "";

		if ((sFormat == null) || (sFormat.trim().equals(""))) {
			sFormat = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat df = new SimpleDateFormat(sFormat);

		return df.format(date);
	}

	/**
	 * 获取时间并转换成日期格式字符串
	 * 
	 * @param seconds
	 *            精确到秒的字符串
	 * @param formatStr
	 * @return
	 */
	public static String timeStamp2Date() {
		long time = System.currentTimeMillis();
		String t = String.valueOf(time / 1000);
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(t + "000")));
	}

	/**
	 * 取得当前时间戳（精确到秒）
	 * 
	 * @return
	 */
	public static String timeStamp() {
		long time = System.currentTimeMillis();
		String t = String.valueOf(time / 1000);
		return t;
	}

	public static String ajaxEncode(String str, String encode) {
		String encodeStr = "";
		try {
			encodeStr = java.net.URLDecoder.decode(str, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeStr;
	}

	/**
	 * 
	 * <p>
	 * Description:判断list是否为空
	 * </p>
	 * 
	 * @param list
	 * @return
	 * @date 2016年4月25日 下午3:17:10
	 */
	public static <T> boolean isEmpty(List<T> list) {
		if (list == null || list.size() == 0)
			return true;
		return false;
	}

	/**
	 * 自动生成32位的UUid，对应数据库的主键id进行插入用。
	 * 
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
		return temp;
	}

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * 
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100
	 * 
	 * 用户真实IP为： 192.168.1.110
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (Tools.isTrimEmpty(ip) || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("Proxy-Client-IP");
		if (Tools.isTrimEmpty(ip) || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (Tools.isTrimEmpty(ip) || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("HTTP_CLIENT_IP");
		if (Tools.isTrimEmpty(ip) || "unknown".equalsIgnoreCase(ip))
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (Tools.isTrimEmpty(ip) || "unknown".equalsIgnoreCase(ip))
			ip = request.getRemoteAddr();
		return ip;
	}

	/**
	 * 根据键值获取数据
	 *
	 * @param data
	 *            数据map
	 * @param key
	 *            键值
	 * 
	 * @return
	 */
	public static String getStringFromMap(Map<String, Object> data, String key) {
		String result = "";
		if (data == null || data.isEmpty() || !data.containsKey(key))
			return result;

		result = data.get(key).toString();
		return result;
	}

	private static final String charStr = "0123456789";

	/**
	 * 生成随机码
	 *
	 * @param length
	 *            需要生成几位
	 * 
	 * @return 生成的随机码
	 */
	public static String genRandomCode(int length) {
		String result = "";
		for (int i = 0; i < length; i++) {
			result += charStr.charAt((int) (Math.random() * charStr.length()));
		}

		return result;
	}

	/**
	 * 日期转星期
	 * 
	 * @param datetime
	 * @return
	 */
	public static String dateToWeek(String datetime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = f.parse(datetime);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

}
