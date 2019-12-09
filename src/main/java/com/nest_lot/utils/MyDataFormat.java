package com.nest_lot.utils;

import java.util.Calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import org.springframework.util.StringUtils;

public class MyDataFormat {

	public static String getBeforeDay(String dateTime, int num) {
		Calendar now = Calendar.getInstance();
		SimpleDateFormat simpledate = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = simpledate.parse(dateTime);
		} catch (ParseException ex) {
			System.out.println("日期格式不符合要求：" + ex.getMessage());
			return null;
		}
		now.setTime(date);
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		int day = now.get(Calendar.DAY_OF_MONTH) - num;
		now.set(year, month, day);
		String time = simpledate.format(now.getTime());
		return time;
	}

	public static void main(String args[]) {
		String time = MyDataFormat.currentDate() + " " + MyDataFormat.currentTime();
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

	public static String formtTime(String time) {
		if (StringUtils.isEmpty(time)) {
			return "";
		}
		String subtime = time.substring(5, 6);
		String time2 = time.substring(8, 9);
		if ("0".equals(subtime)) {
			if ("0".equals(time2)) {
				time = time.substring(0, 5) + time.substring(6, 8) + time.substring(9, time.length());
			} else {
				time = time.substring(0, 5) + time.substring(6, time.length());
			}

		}
		return time;
	}
}
