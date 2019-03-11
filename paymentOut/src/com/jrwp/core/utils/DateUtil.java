package com.jrwp.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	public static String ysqx(String csrq) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(csrq);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (date == null) {
			return "";
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int csrq_year = c.get(Calendar.YEAR);
		int csrq_month = c.get(Calendar.MONTH);
		int csrq_day = c.get(Calendar.DATE);
		Date now = new Date();
		c.setTime(now);
		int now_year = c.get(Calendar.YEAR);
		int now_month = c.get(Calendar.MONTH);
		int now_day = c.get(Calendar.DATE);
		int time = now_year - csrq_year;
		if (csrq_month > now_month) {
			time -= 1;
		}
		if (csrq_month == now_month && csrq_day > now_day) {
			time -= 1;
		}
		if (time < 16) {
			return "5年";
		} else if (time < 26) {
			return "10年";
		} else if (time < 46) {
			return "20年";
		} else {
			return "长期有效";
		}
	}

	public static int ysqxTime(String csrq) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(csrq);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (date == null) {
			return -1;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int csrq_year = c.get(Calendar.YEAR);
		int csrq_month = c.get(Calendar.MONTH);
		int csrq_day = c.get(Calendar.DATE);
		Date now = new Date();
		c.setTime(now);
		int now_year = c.get(Calendar.YEAR);
		int now_month = c.get(Calendar.MONTH);
		int now_day = c.get(Calendar.DATE);
		int time = now_year - csrq_year;
		if (csrq_month > now_month) {
			time -= 1;
		}
		if (csrq_month == now_month && csrq_day > now_day) {
			time -= 1;
		}
		if (time < 16) {
			return 5;
		} else if (time < 26) {
			return 10;
		} else if (time < 46) {
			return 20;
		} else {
			return 0;
		}
	}

	public static String yxqjzrq(String csrq) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		int ysqxTime = ysqxTime(csrq);
		System.out.println(ysqxTime);
		if (ysqxTime == 0) {
			return "长期";
		}
		gc.add(1, ysqxTime);
		String format = sdf.format(gc.getTime());
		return format;
	}
}
