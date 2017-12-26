package com.project.hepet.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * 
 * @author chwang
 * 
 */
public class DateTimeUtil {

	private static String SIMPLE_TIME_FORMAT = "yyyyMMddHHmmss";
	private static String COMPLEX_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String formatTime(String timeStamp) {
		if ((timeStamp == null) ||(timeStamp.length() != 19))
			return null;
		SimpleDateFormat format = new SimpleDateFormat(COMPLEX_TIME_FORMAT);
		Date date = null;
		try {
			date = format.parse(timeStamp);
			format.applyPattern(SIMPLE_TIME_FORMAT);
		} catch (Exception e) {
			return null;
		}
		return format.format(date);
	}

	public static String getCurrentTime() {
		return new SimpleDateFormat(SIMPLE_TIME_FORMAT).format(new Date());
	}

	public static long dateTimeStrToMilsec(String strDateTime, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				(pattern == null) ? SIMPLE_TIME_FORMAT : pattern);

		try {
			return sdf.parse(strDateTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();

			return 0;
		}
	}

	public static String dateTimeToStr(Date dateTime, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				(pattern == null) ? SIMPLE_TIME_FORMAT : pattern);

		return (dateTime == null) ? null : sdf.format(dateTime);
	}

	public static String dateTimeStrChangePattern(String dateTimeStr,
			String srcPattern, String destPattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(srcPattern);
		Date date;

		try {
			date = sdf.parse(dateTimeStr);
			sdf.applyPattern(destPattern);

			return sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();

			return null;
		}
	}

	public static String formatTime2(String timeStamp) {
		SimpleDateFormat format = new SimpleDateFormat(SIMPLE_TIME_FORMAT);
		Date date = null;
		try {
			date = format.parse(timeStamp);
			format.applyPattern(COMPLEX_TIME_FORMAT);
		} catch (Exception e) {
			format.applyPattern(COMPLEX_TIME_FORMAT);
			return format.format(new Date());
		}
		return format.format(date);
	}

	public static String formatTime(Date timeStamp) {
		SimpleDateFormat format = new SimpleDateFormat();
		try {
			format.applyPattern(COMPLEX_TIME_FORMAT);
			return format.format(timeStamp);
		} catch (Exception e) {
			return null;
			//format.applyPattern(SRCDATEFORMAT);
			//return format.format(new Date());
		}
		//return format.format(timeStamp);
	}

	public static String formatTime2(Date timeStamp) {
		SimpleDateFormat format = new SimpleDateFormat();
		try {
			format.applyPattern(SIMPLE_TIME_FORMAT);
		} catch (Exception e) {
			format.applyPattern(SIMPLE_TIME_FORMAT);
			return format.format(new Date());
		}
		return format.format(timeStamp);
	}

	public static String getCurrentTime2() {
		return new SimpleDateFormat(COMPLEX_TIME_FORMAT).format(new Date());
	}

	public static String getSecondAfter(Date now, int second) {
		SimpleDateFormat format = new SimpleDateFormat(SIMPLE_TIME_FORMAT);
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.set(Calendar.SECOND, c.get(Calendar.SECOND) + second);
		try {
			format.applyPattern(SIMPLE_TIME_FORMAT);
		} catch (Exception e) {
			format.applyPattern(SIMPLE_TIME_FORMAT);
			return format.format(new Date());
		}
		return format.format(c.getTime());
	}

	public static boolean validateDateTime1(String dateTime) {
		if (dateTime == null)
			return false;
		SimpleDateFormat format = new SimpleDateFormat(COMPLEX_TIME_FORMAT);
		//Date date = null;
		try {
			format.parse(dateTime);
		} catch (Exception e) {
			return false;
		}
		return true;

	}
	
	public static boolean isLaterTime(String dateTime) {
		if (dateTime == null)
			return false;
		SimpleDateFormat format = new SimpleDateFormat(COMPLEX_TIME_FORMAT);
		Date date = null;
		try {
			date = format.parse(dateTime);
		} catch (Exception e) {
			return false;
		}
		if(date.before(new Date()))
			return false;
		return true;

	}
	
	public static java.sql.Date XMLGregorianCalendarToDate(XMLGregorianCalendar xgc) {
		if (xgc == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(xgc.getYear(), xgc.getMonth() - 1, xgc.getDay(), xgc
				.getHour(), xgc.getMinute(), xgc.getSecond());
		return new java.sql.Date(calendar.getTimeInMillis());//.getTime();
	}

	public static XMLGregorianCalendar dateToXMLGregorianCalendar(Date date) {

		if (date == null) {
			return null;
		}

		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(date);
		DatatypeFactory dtf = null;
		try {
			dtf = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		XMLGregorianCalendar xgc = dtf.newXMLGregorianCalendar();
		int year, month, day, hour, minute, second;
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1; // calendar
		day = calendar.get(Calendar.DAY_OF_MONTH);
		hour = calendar.get(Calendar.HOUR);
		minute = calendar.get(Calendar.MINUTE);
		second = calendar.get(Calendar.SECOND);
		if (year > 0) {
			xgc.setYear(year);
		}
		if (month > 0 && month < 13) {
			xgc.setMonth(month);
		}
		if (day > 0 && day < 32) {
			xgc.setDay(day);
		}
		if (hour >= 0 && hour < 25) {
			xgc.setHour(hour);
		}
		if (minute >= 0 && minute < 60) {
			xgc.setMinute(minute);
		}
		if (second >= 0 && second < 60) {
			xgc.setSecond(second);
		}
		return xgc;

	}
	
	public static String getYesterdayDateStr() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}

	public static java.sql.Date dateToSqlDate(Date timeStamp) {
		if (timeStamp == null) {
			return null;
		}
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(timeStamp);
		return new java.sql.Date(calendar.getTimeInMillis());
	}

	public static Date parseDate(String timeStamp) {
		SimpleDateFormat format = new SimpleDateFormat(COMPLEX_TIME_FORMAT);
		Date date = null;
		try {
			date = format.parse(timeStamp);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return date;
	}

	public static boolean oneDayBefore(String accountRetryTime) {
		if(accountRetryTime == null || accountRetryTime.equals("")) {
			return false;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		SimpleDateFormat format = new SimpleDateFormat(SIMPLE_TIME_FORMAT);
		Date lastRetryTime = null;
		try {
			lastRetryTime = format.parse(accountRetryTime);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return calendar.getTime().after(lastRetryTime);
	}
	
	public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        XMLGregorianCalendar gc = null;
        try {
            gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {

             e.printStackTrace();
        }
        return gc;
    }
 
     public static Date convertToDate(XMLGregorianCalendar cal) throws Exception{
         GregorianCalendar ca = cal.toGregorianCalendar();
         return ca.getTime();
     }

	public static boolean checkComplexTime(String timeStamp) {
		if(timeStamp == null || timeStamp.length() != 19) {
			return false;
		}
		SimpleDateFormat format = new SimpleDateFormat(COMPLEX_TIME_FORMAT);
		Date date = null;
		try {
			date = format.parse(timeStamp);
		} catch (Exception e) {
			return false;
		}
		return date != null;
	}

	public static void main(String[] args) {
		System.out.println(new Date().getTime());
	}
}
