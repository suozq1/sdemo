package com.suo.sdemo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateUtils {
	private static final String DEFAULT_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	
	private static final String DEFAULT_FORMAT_YMD = "yyyy-MM-dd";
	
	private DateUtils() {}
	
	public static Date parseDate(String date) throws ParseException {
		Date d = null;
		try {
			d = parseDate(date,DEFAULT_FORMAT_YMDHMS);
		}catch (Exception e) {
			d = parseDate(date,DEFAULT_FORMAT_YMD);
		}
		return d;
	}
	
	public static Date parseDate(String date,String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return parseDate(date,sdf);
	}
	public static Date parseDate(String date,SimpleDateFormat sdf) throws ParseException {
		return sdf.parse(date);
	}
	
	public static LocalDate getFirstMonth(LocalDate date) {
		return LocalDate.ofYearDay(date.getYear(), 1);
	}
	
	public static String format(Date date,String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static void main(String[] args) throws ParseException {
		System.out.println(getFirstMonth(LocalDate.now()));
	}
}
