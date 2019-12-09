package com.nest_lot.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author wuxiaoxing
 * @Title BaseModel.java
 * @description TODO
 * @time 2018-6-19 下午3:25:16
 **/
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss",
			"yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM" };

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
	 * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 *
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取过去的小时
	 *
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 60 * 1000);
	}

	/**
	 * 获取过去的分钟
	 *
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 1000);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}

	/**
	 * 获取两个日期之间的天数
	 *
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	public static long dateDiff(String interval, String bDate) {
		Date dateBegin = parseDate(bDate);
		return dateDiff(interval, dateBegin, new Date());
	}

	public static long dateDiff(String interval, Date bDate, Date eDate) {
		long ret = eDate.getTime() - bDate.getTime();
		if (interval.equals("s"))
			ret = ret / 1000;
		if (interval.equals("m"))
			ret = ret / (1000 * 60);
		if (interval.equals("h"))
			ret = ret / (1000 * 60 * 60);
		if (interval.equals("d"))
			ret = ret / (1000 * 60 * 60 * 24);
		return ret;
	}

	public static long dateDiff(String interval, String bDate, String eDate) {
		Date dateEnd = parseDate(eDate);
		Date dateBegin = parseDate(bDate);
		return dateDiff(interval, dateBegin, dateEnd);
	}

	public static int getDayOfWeek(String strDate) {
		// Date date = parseDate(strDate);
		Calendar dCal = Calendar.getInstance();
		int n = dCal.getFirstDayOfWeek();
		// dCal.setTime(date );
		// int n=dCal.getFirstDayOfWeek() ;

		return n;
	}

	/**
	 * 将指定的字符串转换为日期类型。
	 *
	 * @param strDate
	 * @return
	 */
	public static Date parseDate(String strDate) {
		long retMill = 0;
		try {
			StringTokenizer token = new StringTokenizer(strDate, " ");

			String myDate = token.nextToken();
			String myTime = token.nextToken();
			StringTokenizer tkTime = new StringTokenizer(myTime, ":");
			Date newDate = java.sql.Date.valueOf(myDate);
			retMill = newDate.getTime();
			retMill += Integer.parseInt(tkTime.nextToken()) * 60 * 60 * 1000;
			retMill += Integer.parseInt(tkTime.nextToken()) * 60 * 1000;
			retMill += Integer.parseInt(tkTime.nextToken()) * 1000;
		} catch (Exception ex) {
			return new Date();
		}
		return new Date(retMill);
	}

	/**
	 * 指定日期加一个时间段后产生的新日期。
	 */
	public static String dateAdd(String interval, int num, String strInitDate) {
		Calendar myCal = Calendar.getInstance();
		Date initDate = parseDate(strInitDate);
		myCal.setTime(initDate);
		if (interval.equals("s"))
			myCal.add(Calendar.SECOND, num);
		if (interval.equals("mm"))
			myCal.add(Calendar.MINUTE, num);
		if (interval.equals("h"))
			myCal.add(Calendar.HOUR, num);
		if (interval.equals("d"))
			myCal.add(Calendar.DAY_OF_MONTH, num);
		if (interval.equals("w"))
			myCal.add(Calendar.WEEK_OF_YEAR, num);
		if (interval.equals("m"))
			myCal.add(Calendar.MONTH, num);
		if (interval.equals("y"))
			myCal.add(Calendar.YEAR, num);

		return commonTime(myCal.getTime());
	}

	/**
	 * 按指定格式得到当前时间的字符串。type=0为完整时间，1为日期，2为时间
	 *
	 * @return
	 */
	public static String commonTime() {
		return commonTime(new Date(), 0);
	}

	public static String commonTime(int type) {
		return commonTime(new Date(), type);
	}

	public static String commonTime(long mills) {
		return commonTime(new Date(mills), 0);
	}

	public static String commonTime(long mills, int type) {
		return commonTime(new Date(mills), type);
	}

	public static String commonTime(Date mydate) {
		return commonTime(mydate, 0);
	}

	public static String commonTime(Date mydate, int type) {
		String strRet = "";
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			strRet = formater.format(mydate);
		} catch (Exception ex) {
			// strRet=formater.format(new Date());
		}

		StringTokenizer token = new StringTokenizer(strRet, " ");
		String myDate = token.nextToken();
		String myTime = token.nextToken();

		if (type == 1)
			strRet = myDate;
		if (type == 2)
			strRet = myTime;
		return strRet;
	}

	public static String commonTime(String strDate, int type) {
		String ret = strDate;
		StringTokenizer token = new StringTokenizer(strDate, " ");
		String myDate = token.nextToken();
		String myTime = token.nextToken();

		if (type == 1)
			ret = myDate;
		if (type == 2)
			ret = myTime;
		return ret;
	}

	public static String currentDate(String strDate) {
		String ret = "";
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		try {
			ret = formater.format(parseDate(strDate));
		} catch (Exception ex) {
			// ret=formater.format(new Date());
		}
		return ret;
	}

	public static String currentDateMonthDay(String strDate) {
		String ret = "";
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-01");
		try {
			ret = formater.format(parseDate(strDate));
		} catch (Exception ex) {
			ret = formater.format(new Date());
		}
		return ret;
	}

	public static String currentDate() {
		return currentDate(commonTime());
	}

	public static String currentTime(String strDate) {
		String ret = "";
		SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");
		try {
			ret = formater.format(parseDate(strDate));
		} catch (Exception ex) {
			ret = formater.format(new Date());
		}
		return ret;
	}

	public static String currentTime() {
		return currentTime(commonTime());
	}

	/*
	 * 将时间转换为时间戳
	 */
	public static String dateToStamp(String s) throws ParseException {
		String res = "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = simpleDateFormat.parse(s);
		long ts = date.getTime();
		res = String.valueOf(ts);
		return res;
	}

	/*
	 * 将时间戳转换为时间
	 */
	public static String stampToDate(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	/**
	 * 计算时间差（天）
	 */
	public static String getDateValueDay(String timeStart, String timeEnd) {

		String res = "";
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// String startDate = simpleDateFormat.format(timeStart);
			// String endDate = simpleDateFormat.format(timeEnd);
			long start = simpleDateFormat.parse(timeStart).getTime();
			long end = simpleDateFormat.parse(timeEnd).getTime();
			int days = (int) ((end - start) / (1000 * 60 * 60 * 24));
			days = days + 1;
			res = days + "";
		} catch (Exception e) {
		}

		return res;
	}

	public static void main(String[] args) {
		/*String dateValueHour = getDateValueHour(commonTime(), "2019-10-15 10:30:00");
		System.out.println(dateValueHour);
		System.out.println("qq");
		System.out.println(dateValueHour);*/
		String nowTime = DateUtils.commonTime();
		String time=Tools.timeStamp2Date();
		String a=getDateValueDay(time,"2019-10-15 10:30:00");
		//System.out.println("-----------------> " + DateUtils.dateDiff("s", "2018-12-10 09:21:00", nowTime));
		//System.out.println(DateUtils.formatDateTime(getTodaySomeTime(0, 0, 0)));
		// String s = "2018-08-16 12:00:00";
		// System.out.println(s.substring(0,16));
		System.out.println(a);
	}

	/**
	 * 计算时间差(小时)
	 */
	public static String getDateValueHour(String timeStart, String timeEnd) {

		String res = "";
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			/*
			 * String startDate = simpleDateFormat.format(timeStart); String endDate =
			 * simpleDateFormat.format(timeEnd);
			 */
			long start = simpleDateFormat.parse(timeStart).getTime();
			long end = simpleDateFormat.parse(timeEnd).getTime();

			DecimalFormat df = new DecimalFormat("#.0");
			String time_cont = df.format(end - start);

			double time_double = Double.parseDouble(time_cont);
			double time_result_double = time_double / (1000 * 60 * 60);
			String time_result = df.format(time_result_double);
			String time_rtn = time_result + "";
			if (time_rtn.contains(".0")) {

				time_rtn = time_rtn.replace(".", "=");
				time_rtn = time_rtn.split("=")[0];
			}

			// System.out.println("long2====="+tmp1);
			// df.format(number)
			// String days = ((end - start)/(1000 * 60 * 60))+"";
			// res = days+"";
			res = time_rtn + "";
			if ("".equals(res)) {
				res = "0";
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

		return res;
	}

	public static double getDateValueHour1(String timeStart, String timeEnd) {
		double result = 0;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long start = simpleDateFormat.parse(timeStart).getTime();
			long end = simpleDateFormat.parse(timeEnd).getTime();
			DecimalFormat df = new DecimalFormat("#.0");
			String time_cont = df.format(end - start);
			double time_double = Double.parseDouble(time_cont);
			double time_result_double = time_double / (1000 * 60 * 60);
			String time_result = df.format(time_result_double);
			result = Double.parseDouble(time_result);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return result;

	}

	/**
	 * 格式处理时间字符串
	 */
	public static String getFormat(String time) {

		String res = "";
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			long start = simpleDateFormat.parse(time).getTime();
			Date date = new Date(start);
			res = simpleDateFormat.format(date);
		} catch (Exception e) {
		}

		return res;
	}

	/**
	 * 获取当月第一天
	 * 
	 * @return
	 */
	public static String getCurrentMonthFirstDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return format.format(c.getTime());
	}

	/**
	 * 获取某月的最后一天
	 * 
	 * @param num
	 * @return
	 */
	public static String getEndOfMonthDay(int num) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, num);
		c.set(Calendar.DAY_OF_MONTH, 0);
		return format.format(c.getTime());
	}

	/**
	 * 获得当天某一时刻的时间 例如传入:15,30,12
	 * 
	 * @param hourOfDay
	 *            时间
	 * @param minute
	 *            分钟
	 * @param second
	 *            秒
	 * @return 今天的15:30:12的 Date
	 */
	public static Date getTodaySomeTime(int hourOfDay, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return cal.getTime();
	}
	
	
	/**
	 * localDateTime 转换为Data
	 * @param localDateTime
	 * @return
	 */
	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		ZoneId zone = ZoneId.systemDefault();
	    Instant instant = localDateTime.atZone(zone).toInstant();
	    return Date.from(instant);
	}
	/**
	 * localDateTime 转换为String
	 * @param localDateTime
	 * @return
	 */
	public static String localDateTimeToString(LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(parsePatterns[1]);
		return localDateTime.format(formatter);
	}
	
}
