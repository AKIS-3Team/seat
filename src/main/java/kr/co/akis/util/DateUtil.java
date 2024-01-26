/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : DateUtil.java
 * @Description : 날짜 유틸 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.icu.util.ChineseCalendar;

import kr.co.akis.common.exception.ConditionException;

public class DateUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	/**
	 * <p>날짜형식 문자 또는 Date 객체에 해당하는 날짜 포멧을 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.getFormat(null)                  = ""
	 * DateUtil.getFormat("")                    = ""
	 * DateUtil.getFormat("   ")                 = ""
	 * DateUtil.getFormat("1978122")             = ""
	 * DateUtil.getFormat("19781220")            = "yyyyMMdd"
	 * DateUtil.getFormat("1978 12 20")          = "yyyy MM dd"
	 * DateUtil.getFormat("1978-12-20")          = "yyyy-MM-dd"
	 * DateUtil.getFormat("1978.12.20")          = "yyyy.MM.dd"
	 * DateUtil.getFormat("1978/12/20")          = "yyyy/MM/dd"
	 * DateUtil.getFormat("1978-12-20 13")       = "yyyy-MM-dd HH"
	 * DateUtil.getFormat("1978-12-20 13:11")    = "yyyy-MM-dd HH:mm"
	 * DateUtil.getFormat("1978-12-20 13:11:04") = "yyyy-MM-dd HH:mm:ss"
	 * DateUtil.getFormat(Date)                  = "yyyy-MM-dd HH:mm:ss"
	 * </pre>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (날짜 포멧)
	 */
	public static String getFormat(Object obj) {
		if (obj == null) {
			return "";
		}
		String format = "";
		if (obj instanceof Date) {
			format = "yyyy-MM-dd HH:mm:ss";
		} else if (obj instanceof String) {
			String str = StringUtil.clean(obj);
			if (StringUtil.isBlank(str) || str.length() < 8) {
				return "";
			}
			if (NumberUtil.isNumber(str) && str.length() == 8) {
				format = "yyyyMMdd";
			} else {
				if (StringUtil.contains(str, "-")) {
					format = "yyyy-MM-dd";
				} else if (StringUtil.contains(str, ".")) {
					format = "yyyy.MM.dd";
				} else if (StringUtil.contains(str, "/")) {
					format = "yyyy/MM/dd";
				} else {
					format = "yyyy MM dd";
				}
				if (str.length() > 10) {
					if (StringUtil.countMatches(str, ":") == 2) {
						format += " HH:mm:ss";
					} else if (StringUtil.countMatches(str, ":") == 1) {
						format += " HH:mm";
					} else {
						format += " HH";
					}
				}
			}
		}
		return format;
	}
	
	/**
	 * <p>날짜가 유효한지 여부를 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.isDate(null)                  = false
	 * DateUtil.isDate("")                    = false
	 * DateUtil.isDate("   ")                 = false
	 * DateUtil.isDate("2017-01-37")          = false
	 * DateUtil.isDate("2017-01-30 37:30:33") = false
	 * DateUtil.isDate("2017-01-30 13:77:37") = false
	 * DateUtil.isDate("2017-01-30 24:00:00") = false
	 * DateUtil.isDate("20170130")            = true
	 * DateUtil.isDate("2017 01 30")          = true
	 * DateUtil.isDate("2017.01.30")          = true
	 * DateUtil.isDate("2017/01/30")          = true
	 * DateUtil.isDate("2017-01-30")          = true
	 * DateUtil.isDate("2017-01-30 13:30")    = true
	 * DateUtil.isDate("2017-01-30 13:30:37") = true
	 * DateUtil.isDate("2017-01-30 23:59:59") = true
	 * DateUtil.isDate("2017-01-30 00:00:00") = true
	 * DateUtil.isDate(Date)                  = true
	 * </pre>
	 * 
	 * @param obj      (날짜형식 문자 또는 Date 객체)
	 * @return boolean (날짜 유효성 여부)
	 */
	public static boolean isDate(Object obj) {
		try {
			if (obj instanceof Date) {
				return true;
			} else {
				String format = getFormat(obj);
				DateFormat sdf = new SimpleDateFormat(format);
				sdf.setLenient(false);
				sdf.parse(StringUtil.clean(obj));
				return true;
			}
		} catch (ParseException e) {
			return false;
		}
	}
	
	/**
	 * <p>해당 날짜의 날짜 포맷(yyyy-MM-dd)이 유효한지 여부를 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.isDateFormat(null)               = false
	 * DateUtil.isDateFormat("")                 = false
	 * DateUtil.isDateFormat("   ")              = false
	 * DateUtil.isDateFormat("20170130")         = false
	 * DateUtil.isDateFormat("2017 01 30")       = false
	 * DateUtil.isDateFormat("2017.01.30")       = false
	 * DateUtil.isDateFormat("2017/01/30")       = false
	 * DateUtil.isDateFormat("2017-01-30 13:30") = false
	 * DateUtil.isDateFormat("2017-01-37")       = false
	 * DateUtil.isDateFormat("2017-01-30")       = true
	 * </pre>
	 * 
	 * @param obj      (날짜형식 문자형 객체)
	 * @return boolean (날짜 포맷 유효성 여부)
	 */
	public static boolean isDateFormat(Object obj) {
		return isDateFormat(obj, "yyyy-MM-dd");
	}
	
	/**
	 * <p>날짜가 해당 날짜 포맷에 유효한지 여부를 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.isDateFormat(null, *)                                      = false
	 * DateUtil.isDateFormat("", *)                                        = false
	 * DateUtil.isDateFormat("   ", *)                                     = false
	 * DateUtil.isDateFormat("20170130", "yyyy-MM-dd")                     = false
	 * DateUtil.isDateFormat("2017 01 30", "yyyy-MM-dd")                   = false
	 * DateUtil.isDateFormat("2017.01.30", "yyyy-MM-dd")                   = false
	 * DateUtil.isDateFormat("2017/01/30", "yyyy-MM-dd")                   = false
	 * DateUtil.isDateFormat("2017-01-37", "yyyy-MM-dd")                   = false
	 * DateUtil.isDateFormat("2017-01-30 13:30", "yyyy-MM-dd")             = false
	 * DateUtil.isDateFormat("2017-01-30 13:77:37", "yyyy-MM-dd HH:mm:ss") = false
	 * DateUtil.isDateFormat("2017-01-30 24:00:00", "yyyy-MM-dd HH:mm:ss") = false
	 * DateUtil.isDateFormat("20170130", "yyyyMMdd")                       = true
	 * DateUtil.isDateFormat("2017 01 30", "yyyy MM dd")                   = true
	 * DateUtil.isDateFormat("2017.01.30", "yyyy.MM.dd")                   = true
	 * DateUtil.isDateFormat("2017/01/30", "yyyy/MM/dd")                   = true
	 * DateUtil.isDateFormat("2017-01-30", "yyyy-MM-dd")                   = true
	 * DateUtil.isDateFormat("2017-01-30 13:30", "yyyy-MM-dd HH:mm")       = true
	 * DateUtil.isDateFormat("2017-01-30 13:30:37", "yyyy-MM-dd HH:mm:ss") = true
	 * DateUtil.isDateFormat("2017-01-30 23:59:59", "yyyy-MM-dd HH:mm:ss") = true
	 * DateUtil.isDateFormat("2017-01-30 00:00:00", "yyyy-MM-dd HH:mm:ss") = true
	 * </pre>
	 * 
	 * @param obj      (날짜형식 문자형 객체)
	 * @param format   (날짜 포맷)
	 * @return boolean (날짜 포맷 유효성 여부)
	 */
	public static boolean isDateFormat(Object obj, String format) {
		try {
			String str = StringUtil.clean(obj);
			DateFormat sdf = new SimpleDateFormat(format);
			sdf.setLenient(false);
			sdf.parse(str);
			if (str.length() != StringUtil.clean(format).length()) {
				return false;
			} else {
				return true;
			}
		} catch (ParseException e) {
			return false;
		}
	}
	
	/**
	 * <p>해당 객체를 Date 객체로 형변환 하거나 String 객체일 경우 Date 객체로 변경하고 리턴한다.</p>
	 * 
	 * @param obj   (날짜형식 문자 또는 Date 객체)
	 * @return Date (Date 객체)
	 */
	public static Date parseDate(Object obj) {
		if (obj == null) {
			return null;
		}
		Date date = null;
		if (obj instanceof String) {
			String str = StringUtil.clean(obj);
			if (StringUtil.isBlank(str)) {
				return null;
			}
			try {
				DateFormat sdf = new SimpleDateFormat(getFormat(str));
				date = sdf.parse(str);
			} catch (ParseException e) {
				logger.error(ExceptionUtil.addMessage(e, "지원하지 않는 날짜형식 입니다."));
			}
		} else if (obj instanceof Date) {
			date = (Date) obj;
		}
		return date;
	}
	
	/**
	 * <p>해당 객체를 Date 객체로 형변환 하거나 String 객체일 경우 지역설정 값으로 Date 객체로 변경후 리턴한다.</p>
	 * 
	 * @param obj    (날짜형식 문자 또는 Date 객체)
	 * @param locale (지역설정 값)
	 * @return Date  (Date 객체)
	 */
	public static Date parseDate(Object obj, Locale locale) {
		if (obj == null) {
			return null;
		}
		if (locale == null) {
			locale = Locale.KOREA;
		}
		Date date = null;
		if (obj instanceof String) {
			String str = StringUtil.clean(obj);
			if (StringUtil.isBlank(str)) {
				return null;
			}
			try {
				DateFormat sdf = new SimpleDateFormat(getFormat(str), locale);
				date = sdf.parse(str);
			} catch (ParseException e) {
				logger.error(ExceptionUtil.addMessage(e, "지원하지 않는 날짜형식 입니다."));
			}
		} else if (obj instanceof Date) {
			date = (Date) obj;
		}
		return date;
	}
	
	/**
	 * <p>날짜형식 문자 또는 Date 객체를 원하는 날짜 형식으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @param format  (변경할 포멧)
	 * @return String (변경후 날짜)
	 */
	public static String toDateFormat(Object obj, String format) {
		if (obj == null) {
			return "";
		}
		if (StringUtil.isBlank(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		Date date = null;
		if (obj instanceof String) {
			date = parseDate(obj);
			if (date == null) {
				return "";
			}
		} else if (obj instanceof Date) {
			date = (Date) obj;
		}
		DateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * <p>날짜형식 문자 또는 Date 객체를 지역설정 및 원하는 날짜 형식으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @param format  (변경할 포멧)
	 * @param locale  (지역설정 값)
	 * @return String (변경후 날짜)
	 */
	public static String toDateFormat(Object obj, String format, Locale locale) {
		if (obj == null) {
			return "";
		}
		if (StringUtil.isBlank(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		if (locale == null) {
			locale = Locale.KOREA;
		}
		Date date = null;
		if (obj instanceof String) {
			date = parseDate(obj, locale);
			if (date == null) {
				return "";
			}
		} else if (obj instanceof Date) {
			date = (Date) obj;
		}
		DateFormat sdf = new SimpleDateFormat(format, locale);
		return sdf.format(date);
	}
	
	/**
	 * <p>날짜를 한국 날짜형식 원하는 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @param format  (변경할 포멧)
	 * @return String (변경후 날짜)
	 */
	public static String toKorDate(Object obj, String format) {
		return toDateFormat(obj, format, Locale.KOREA);
	}
	
	/**
	 * <p>날짜를 한국 날짜형식 'yyyy-MM-dd' 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (변경후 날짜)
	 */
	public static String toKorDate1(Object obj) {
		return toDateFormat(obj, "yyyy-MM-dd", Locale.KOREA);
	}
	
	/**
	 * <p>날짜를 한국 날짜형식 'yyyy.MM.dd' 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (변경후 날짜)
	 */
	public static String toKorDate2(Object obj) {
		return toDateFormat(obj, "yyyy.MM.dd", Locale.KOREA);
	}
	
	/**
	 * <p>날짜를 한국 날짜형식 'yyyy-MM-dd HH:mm:ss' 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (변경후 날짜)
	 */
	public static String toKorDate3(Object obj) {
		return toDateFormat(obj, "yyyy-MM-dd HH:mm:ss", Locale.KOREA);
	}
	
	/**
	 * <p>날짜를 한국 날짜형식 'yyyy.MM.dd HH:mm:ss' 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (변경후 날짜)
	 */
	public static String toKorDate4(Object obj) {
		return toDateFormat(obj, "yyyy.MM.dd HH:mm:ss", Locale.KOREA);
	}
	
	/**
	 * <p>날짜를 한국 날짜형식 'MM.dd(월)' 또는 'MM.dd(월요일)' 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @param isLong  (요일의 스타일 false = 월, true = 월요일)
	 * @return String (변경후 날짜)
	 */
	public static String toKorDate5(Object obj, boolean isLong) {
		String format = isLong ? "MM.dd (EEEE)" : "MM.dd (EE)";
		return toDateFormat(obj, format, Locale.KOREA);
	}
	
	/**
	 * <p>날짜를 한국 날짜형식 'MM.dd(월요일)' 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (변경후 날짜)
	 */
	public static String toKorDate5(Object obj) {
		return toKorDate5(obj, true);
	}
	
	/**
	 * <p>날짜를 영어 날짜형식 원하는 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @param format  (변경할 포멧)
	 * @return String (변경후 날짜)
	 */
	public static String toEngDate(Object obj, String format) {
		return toDateFormat(obj, format, Locale.ENGLISH);
	}
	
	/**
	 * <p>날짜를 영어 날짜형식 'MM-dd-yyyy' 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (변경후 날짜)
	 */
	public static String toEngDate1(Object obj) {
		return toDateFormat(obj, "MM-dd-yyyy", Locale.ENGLISH);
	}
	
	/**
	 * <p>날짜를 영어 날짜형식 'MM.dd.yyyy' 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (변경후 날짜)
	 */
	public static String toEngDate2(Object obj) {
		return toDateFormat(obj, "MM.dd.yyyy", Locale.ENGLISH);
	}
	
	/**
	 * <p>날짜를 영어 날짜형식 'MM-dd-yyyy HH:mm:ss' 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (변경후 날짜)
	 */
	public static String toEngDate3(Object obj) {
		return toDateFormat(obj, "MM-dd-yyyy HH:mm:ss", Locale.ENGLISH);
	}
	
	/**
	 * <p>날짜를 영어 날짜형식 'MM.dd.yyyy HH:mm:ss' 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (변경후 날짜)
	 */
	public static String toEngDate4(Object obj) {
		return toDateFormat(obj, "MM.dd.yyyy HH:mm:ss", Locale.ENGLISH);
	}
	
	/**
	 * <p>날짜를 영어 날짜형식 'MM.dd(Mon)' 또는 'MM.dd(Monday)' 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @param isLong  (요일의 스타일 false = Mon, true = Monday)
	 * @return String (변경후 날짜)
	 */
	public static String toEngDate5(Object obj, boolean isLong) {
		String format = isLong ? "MM.dd (EEEE)" : "MM.dd (EE)";
		return toDateFormat(obj, format, Locale.ENGLISH);
	}
	
	/**
	 * <p>날짜를 영어 날짜형식 'MM.dd(Monday)' 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (변경후 날짜)
	 */
	public static String toEngDate5(Object obj) {
		return toEngDate5(obj, true);
	}
	
	/**
	 * <p>날짜를 영어 날짜형식 'Mar 05, 2010' 또는 'March 05, 2010' 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @param isLong  (월의 스타일 false = Mar, true = March)
	 * @return String (변경후 날짜)
	 */
	public static String toEngDateEtc(Object obj, boolean isLong) {
		String format = isLong ? "MMMM d, yyyy" : "MMM dd, yyyy";
		return toDateFormat(obj, format, Locale.ENGLISH);
	}
	
	/**
	 * <p>날짜를 영어 날짜형식 'March 05, 2010' 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (변경후 날짜)
	 */
	public static String toEngDateEtc(Object obj) {
		return toEngDateEtc(obj, true);
	}
	
	/**
	 * <p>날짜를 RSS 날짜형식 'EE, dd MMM yyyy HH:mm:ss +0000' 포멧으로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (변경후 날짜)
	 */
	public static String toRssDate(Object obj) {
		if (obj == null) {
			return "";
		}
		if (obj instanceof Date) {
			return toDateFormat(obj, "EE, dd MMM yyyy HH:mm:ss +0900", Locale.ENGLISH);
		} else {
			if (StringUtil.isBlank(obj)) {
				return "";
			}
			String str = StringUtil.clean(obj);
			if (str.length() == 10) {
				str += " 00:00:00.0";
			}
			return toDateFormat(str, "EE, dd MMM yyyy HH:mm:ss +0900", Locale.ENGLISH);
		}
	}
	
	/**
	 * <p>오늘의 날짜를 구해서 'yyyy-MM-dd' 포멧으로 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.getToday() = "2009-12-20"
	 * </pre>
	 *
	 * @return String (현재 날짜)
	 */
	public static String getToday() {
		return getToday("yyyy-MM-dd");
	}
	
	/**
	 * <p>오늘의 날짜를 구해서 해당 날짜 포멧으로 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.getToday("yyyy-MM-dd") = "2009-12-20"
	 * DateUtil.getToday("yyyy.MM.dd") = "2009.12.20"
	 * DateUtil.getToday("yyyy/MM/dd") = "2009/12/20"
	 * DateUtil.getToday("yyyy MM dd") = "2009 12 20"
	 * DateUtil.getToday("MM-dd-yyyy") = "12-20-2009"
	 * DateUtil.getToday("MM.dd.yyyy") = "12.20.2009"
	 * </pre>
	 *
	 * @param format  (날짜 포멧)
	 * @return String (현재 날짜)
	 */
	public static String getToday(String format) {
		return toDateFormat(new Date(), format);
	}
	
	/**
	 * <p>현재의 날짜와 시간을 구해서 'yyyy-MM-dd HH:mm:ss' 포멧으로 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.getNowAll() = "2009-12-20 20:15:57"
	 * </pre>
	 * 
	 * @return String (현재 날짜와 시간)
	 */
	public static String getNowAll() {
		return getNowAll("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * <p>현재의 날짜와 시간을 구해서 해당 날짜 포멧으로 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.getNowAll("yyyy-MM-dd HH:mm:ss") = "2009-12-20 20:15:57"
	 * DateUtil.getNowAll("yyyy.MM.dd HH:mm:ss") = "2009.12.20 20:15:57"
	 * DateUtil.getNowAll("yyyy/MM/dd HH:mm:ss") = "2009/12/20 20:15:57"
	 * DateUtil.getNowAll("yyyy MM dd HH:mm:ss") = "2009 12 20 20:15:57"
	 * DateUtil.getNowAll("MM-dd-yyyy HH:mm:ss") = "12-20-2009 20:15:57"
	 * DateUtil.getNowAll("MM.dd.yyyy HH:mm:ss") = "12.20.2009 20:15:57"
	 * </pre>
	 * 
	 * @param format  (날짜 포멧)
	 * @return String (현재 날짜와 시간)
	 */
	public static String getNowAll(String format) {
		return toDateFormat(new Date(), format);
	}
	
	/**
	 * <p>날짜에서 년도를 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.getYear(null)         = ""
	 * DateUtil.getYear("")           = ""
	 * DateUtil.getYear("   ")        = ""
	 * DateUtil.getYear("2010-12-20") = "2010"
	 * DateUtil.getYear(Date)         = "2010"
	 * </pre>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (년도)
	 */
	public static String getYear(Object obj) {
		return toDateFormat(obj, "yyyy", Locale.KOREA);
	}
	
	/**
	 * <p>날짜에서 원하는 형식의 월을 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.getMonth(null, *)             = ""
	 * DateUtil.getMonth("", *)               = ""
	 * DateUtil.getMonth("   ", *)            = ""
	 * DateUtil.getMonth("2010-06-05", true)  = "06"
	 * DateUtil.getMonth("2010-06-05", false) = "6"
	 * DateUtil.getMonth("2010.06.05", true)  = "06"
	 * DateUtil.getMonth("2010-6-5", true)    = "06"
	 * DateUtil.getMonth("20100605", false)   = "6"
	 * DateUtil.getMonth(Date, false)         = "6"
	 * DateUtil.getMonth(Date, true)          = "06"
	 * </pre>
	 * 
	 * @param obj       (날짜형식 문자 또는 Date 객체)
	 * @param isAddZero (1 ~ 9월 앞에 0을 붙일지 여부)
	 * @return String   (월)
	 */
	public static String getMonth(Object obj, boolean isAddZero) {
		return toDateFormat(obj, (isAddZero ? "MM" : "M"), Locale.KOREA);
	}

	/**
	 * <p>날짜에서 월을 리턴한다. (1 ~ 9월은 앞에 0을 붙인다.)</p>
	 * 
	 * <pre>
	 * DateUtil.getMonth(null)         = ""
	 * DateUtil.getMonth("")           = ""
	 * DateUtil.getMonth("   ")        = ""
	 * DateUtil.getMonth("2010-06-05") = "06"
	 * DateUtil.getMonth("2010.06.05") = "06"
	 * DateUtil.getMonth("2010-6-5")   = "06"
	 * DateUtil.getMonth("20100605")   = "06"
	 * DateUtil.getMonth(Date)         = "06"
	 * </pre>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (월)
	 */
	public static String getMonth(Object obj) {
		return getMonth(obj, true);
	}
	
	/**
	 * <p>날짜에서 원하는 형식의 일을 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.getDay(null, *)             = ""
	 * DateUtil.getDay("", *)               = ""
	 * DateUtil.getDay("   ", *)            = ""
	 * DateUtil.getDay("2010-11-04", true)  = "04"
	 * DateUtil.getDay("2010-11-04", false) = "4"
	 * DateUtil.getDay("2010.11.04", true)  = "04"
	 * DateUtil.getDay("2010-11-4", true)   = "04"
	 * DateUtil.getDay("20101104", false)   = "4"
	 * DateUtil.getDay(Date, false)         = "4"
	 * </pre>
	 * 
	 * @param obj       (날짜형식 문자 또는 Date 객체)
	 * @param isAddZero (1 ~ 9일 앞에 0을 붙일지 여부)
	 * @return String   (일)
	 */
	public static String getDay(Object obj, boolean isAddZero) {
		return toDateFormat(obj, (isAddZero ? "dd" : "d"), Locale.KOREA);
	}
	
	/**
	 * <p>날짜에서 일을 리턴한다. (1 ~ 9일은 앞에 0을 붙인다.)</p>
	 * 
	 * <pre>
	 * DateUtil.getDay(null)         = ""
	 * DateUtil.getDay("")           = ""
	 * DateUtil.getDay("   ")        = ""
	 * DateUtil.getDay("2010-11-04") = "04"
	 * DateUtil.getDay("2010.11.04") = "04"
	 * DateUtil.getDay("2010-11-4")  = "04"
	 * DateUtil.getDay("20101104")   = "04"
	 * DateUtil.getDay(Date)         = "04"
	 * </pre>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @return String (일)
	 */
	public static String getDay(Object obj) {
		return getDay(obj, true);
	}
	
	/**
	 * <p>날짜가 월 또는 년도를 기준으로 몇번째 주인지 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.getWeek(null, *)             = 0
	 * DateUtil.getWeek("", *)               = 0
	 * DateUtil.getWeek("   ", *)            = 0
	 * DateUtil.getWeek("2011-08-03", true)  = 1
	 * DateUtil.getWeek("2011-08-03", false) = 32
	 * DateUtil.getWeek("2011.08.03", true)  = 1
	 * DateUtil.getWeek("2011-08-3", false)  = 32
	 * DateUtil.getWeek("20110803", false)   = 32
	 * DateUtil.getWeek(Date, true)          = 1
	 * </pre>
	 * 
	 * @param obj           (날짜형식 문자 또는 Date 객체)
	 * @param isWeekOfMonth (월을 기준으로 주를 구할지 여부)
	 * @return int          (주)
	 */
	public static int getWeek(Object obj, boolean isWeekOfMonth) {
		Date date = parseDate(obj);
		if (date == null) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (isWeekOfMonth) {
			return calendar.get(Calendar.WEEK_OF_MONTH);
		} else {
			return calendar.get(Calendar.WEEK_OF_YEAR);
		}
	}
	
	/**
	 * <p>날짜가 월을 기준으로 몇번째 주인지 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.getWeekOfMonth(null)         = 0
	 * DateUtil.getWeekOfMonth("")           = 0
	 * DateUtil.getWeekOfMonth("   ")        = 0
	 * DateUtil.getWeekOfMonth("2011-08-01") = 1
	 * DateUtil.getWeekOfMonth("2011-08-08") = 2
	 * DateUtil.getWeekOfMonth("2011.08.01") = 1
	 * DateUtil.getWeekOfMonth("20110815")   = 3
	 * DateUtil.getWeekOfMonth(Date, true)   = 1
	 * </pre>
	 * 
	 * @param obj  (날짜형식 문자 또는 Date 객체)
	 * @return int (주)
	 */
	public static int getWeekOfMonth(Object obj) {
		return getWeek(obj, true);
	}
	
	/**
	 * <p>날짜가 년도를 기준으로 몇번째 주인지 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.getWeekOfYear(null)         = 0
	 * DateUtil.getWeekOfYear("")           = 0
	 * DateUtil.getWeekOfYear("   ")        = 0
	 * DateUtil.getWeekOfYear("2011-08-01") = 32
	 * DateUtil.getWeekOfYear("2011-08-08") = 33
	 * DateUtil.getWeekOfYear("2011.08.01") = 32
	 * DateUtil.getWeekOfYear("20110815")   = 34
	 * DateUtil.getWeekOfYear(Date, true)   = 32
	 * </pre>
	 * 
	 * @param obj  (날짜형식 문자 또는 Date 객체)
	 * @return int (주)
	 */
	public static int getWeekOfYear(Object obj) {
		return getWeek(obj, false);
	}

	/**
	 * <p>Date 객체의 날짜를 통해 17자리의 TimeStamp 값을 구해서 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.getTimeStamp(null, "*", "*")  = ""
	 * DateUtil.getTimeStamp("", "*", "*")    = ""
	 * DateUtil.getTimeStamp("   ", "*", "*") = ""
	 * DateUtil.getTimeStamp(Date, "*", "*")  = "20101119131734344"
	 * </pre>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @param format  (변경할 포멧)
	 * @param locale  (지역설정 값)
	 * @return String (TimeStamp 값)
	 */
	public static String getTimeStamp(Object obj, String format, Locale locale) {
		Date date = parseDate(obj);
		if (date == null) {
			return "";
		}
		if (StringUtil.isBlank(format)) {
			format = "yyyyMMddHHmmssSSS";
		}
		if (locale == null) {
			locale = Locale.KOREA;
		}
		return toDateFormat(date, format, locale);
	}
	
	/**
	 * <p>시스템의 현재날짜를 통해 17자리의 TimeStamp 값을 구해서 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.getTimeStamp() = "20101119131734344"
	 * </pre>
	 * 
	 * @return String (TimeStamp 값)
	 */
	public static String getTimeStamp() {
		return getTimeStamp(new Date(), null, null);
	}

	/**
	 * <p>해당 날짜에서 년,월,일,시,분,초를 더하거나 뺀후 원하는 날짜로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.addField(null, *, *, *)                      = ""
	 * DateUtil.addField("", *, *, *)                        = ""
	 * DateUtil.addField("   ", *, *, *)                     = ""
	 * DateUtil.addField("2010-12-20", 1, 3, null)           = "2013-12-20"
	 * DateUtil.addField("2010/12/20", 1, 3, null)           = "2013/12/20"
	 * DateUtil.addField("2010-12-20", 1, -3, null)          = "2007-12-20"
	 * DateUtil.addField("2010-12-20", 1, -3, "yyyy.MM.dd")  = "2007.12.20"
	 * DateUtil.addField("2010-12-20", 2, 3, null)           = "2011-03-20"
	 * DateUtil.addField("2010-12-20 23:59:59", 2, 3, null)  = "2011-03-20 23:59:59"
	 * DateUtil.addField("2010-12-20 23:59:59", 5, 12, null) = "2011-01-01 23:59:59"
	 * DateUtil.addField("2010-12-20 23:59:59", 13, 2, null) = "2010-12-21 00:00:01"
	 * DateUtil.addField("2010-12-20", 13, -1, null)         = "2010-12-19"
	 * DateUtil.addField(Date, 13, -1, null)                 = "2010-12-19 23:59:59"
	 * </pre>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @param field   (계산할 필드 - year=1, month=2, day=5, hour=10, minute=12, second=13)
	 * @param amount  (계산할 값)
	 * @param format  (계산후 날짜포멧)
	 * @return String (계산후 날짜)
	 */
	public static String addField(Object obj, int field, int amount, String format) {
		if (obj == null) {
			return "";
		}
		Date date = parseDate(obj, Locale.KOREA);
		if (date == null) {
			return "";
		}
		if (StringUtil.isBlank(format)) {
			format = getFormat(obj);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return toDateFormat(calendar.getTime(), format, Locale.KOREA);
	}
	
	/**
	 * <p>해당 날짜에서 년도를 더하거나 뺀후 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.addYear(null, *)                   = ""
	 * DateUtil.addYear("", *)                     = ""
	 * DateUtil.addYear("   ", *)                  = ""
	 * DateUtil.addYear("2010-12-20", 3)           = "2013-12-20"
	 * DateUtil.addYear("2010/12/20", 3)           = "2013/12/20"
	 * DateUtil.addYear("2010-12-20", -3)          = "2007-12-20"
	 * DateUtil.addYear("2010-12-20 23:59:59", -3) = "2007-12-20"
	 * DateUtil.addYear(Date, -3)                  = "2007-12-20"
	 * </pre>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @param amount  (계산할 값)
	 * @return String (계산후 날짜)
	 */
	public static String addYear(Object obj, int amount) {
		return StringUtil.substring(addField(obj, Calendar.YEAR, amount, null), 0, 10);
	}
	
	/**
	 * <p>해당 날짜에서 월을 더하거나 뺀후 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.addMonth(null, *)                   = ""
	 * DateUtil.addMonth("", *)                     = ""
	 * DateUtil.addMonth("   ", *)                  = ""
	 * DateUtil.addMonth("2010-12-20", 3)           = "2011-03-20"
	 * DateUtil.addMonth("2010/12/20", 3)           = "2011/03/20"
	 * DateUtil.addMonth("2010-12-20", -3)          = "2010-09-20"
	 * DateUtil.addMonth("2010-12-20 23:59:59", -3) = "2010-09-20"
	 * DateUtil.addMonth(Date, -3)                  = "2010-09-20"
	 * </pre>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @param amount  (계산할 값)
	 * @return String (계산후 날짜)
	 */
	public static String addMonth(Object obj, int amount) {
		return StringUtil.substring(addField(obj, Calendar.MONTH, amount, null), 0, 10);
	}
	
	/**
	 * <p>해당 날짜에서 일을 더하거나 뺀후 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.addDay(null, *)                    = ""
	 * DateUtil.addDay("", *)                      = ""
	 * DateUtil.addDay("   ", *)                   = ""
	 * DateUtil.addDay("2010-12-20", 13)           = "2011-01-02"
	 * DateUtil.addDay("2010/12/20", 13)           = "2011/01/02"
	 * DateUtil.addDay("2010-12-20", -13)          = "2010-12-07"
	 * DateUtil.addDay("2010-12-20 23:59:59", -13) = "2010-12-07"
	 * DateUtil.addDay(Date, -13)                  = "2010-12-07"
	 * </pre>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @param amount  (계산할 값)
	 * @return String (계산후 날짜)
	 */
	public static String addDay(Object obj, int amount) {
		return StringUtil.substring(addField(obj, Calendar.DATE, amount, null), 0, 10);
	}
	
	/**
	 * <p>해당 날짜에서 시간을 더하거나 뺀후 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.addHour(null, *)                    = ""
	 * DateUtil.addHour("", *)                      = ""
	 * DateUtil.addHour("   ", *)                   = ""
	 * DateUtil.addHour("2010-12-20", 24)           = "2010-12-21"
	 * DateUtil.addHour("2010/12/20", 23)           = "2010/12/20"
	 * DateUtil.addHour("2010-12-20", -24)          = "2010-12-19"
	 * DateUtil.addHour("2010-12-20 23:59:59", -13) = "2010-12-20 10:59:59"
	 * DateUtil.addHour(Date, -13)                  = "2010-12-20 10:59:59"
	 * </pre>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @param amount  (계산할 값)
	 * @return String (계산후 날짜)
	 */
	public static String addHour(Object obj, int amount) {
		return addField(obj, Calendar.HOUR, amount, null);
	}
	
	/**
	 * <p>해당 날짜에서 분을 더하거나 뺀후 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.addMinute(null, *)                    = ""
	 * DateUtil.addMinute("", *)                      = ""
	 * DateUtil.addMinute("   ", *)                   = ""
	 * DateUtil.addMinute("2010-12-20", 60*24)        = "2010-12-21"
	 * DateUtil.addMinute("2010/12/20", 37)           = "2010/12/20"
	 * DateUtil.addMinute("2010-12-20", -60*24)       = "2010-12-19"
	 * DateUtil.addMinute("2010-12-20 23:59:59", -70) = "2010-12-20 22:49:59"
	 * DateUtil.addMinute(Date, -70)                  = "2010-12-20 22:49:59"
	 * </pre>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @param amount  (계산할 값)
	 * @return String (계산후 날짜)
	 */
	public static String addMinute(Object obj, int amount) {
		return addField(obj, Calendar.MINUTE, amount, null);
	}
	
	/**
	 * <p>해당 날짜에서 초를 더하거나 뺀후 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.addSecond(null, *)                    = ""
	 * DateUtil.addSecond("", *)                      = ""
	 * DateUtil.addSecond("   ", *)                   = ""
	 * DateUtil.addSecond("2010-12-20", 60*60*24)     = "2010-12-21"
	 * DateUtil.addSecond("2010/12/20", 37)           = "2010/12/20"
	 * DateUtil.addSecond("2010-12-20", -60*60*24)    = "2010-12-19"
	 * DateUtil.addSecond("2010-12-20 23:59:59", -70) = "2010-12-20 23:58:49"
	 * DateUtil.addSecond(Date, -70)                  = "2010-12-20 23:58:49"
	 * </pre>
	 * 
	 * @param obj     (날짜형식 문자 또는 Date 객체)
	 * @param amount  (계산할 값)
	 * @return String (계산후 날짜)
	 */
	public static String addSecond(Object obj, int amount) {
		return addField(obj, Calendar.SECOND, amount, null);
	}
	
	/**
	 * <p>해당 날짜가 일요일인지 여부를 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.isSunday(null)         = false
	 * DateUtil.isSunday("")           = false
	 * DateUtil.isSunday("   ")        = false
	 * DateUtil.isSunday("2014-05-05") = false
	 * DateUtil.isSunday("2014-05-11") = true
	 * DateUtil.isSunday("2014-06-08") = true
	 * DateUtil.isSunday(Date)         = true
	 * </pre>
	 * 
	 * @param obj      (날짜형식 문자 또는 Date 객체)
	 * @return boolean (일요일인지 여부)
	 */
	public static boolean isSunday(Object obj) {
		Date date = parseDate(obj, Locale.KOREA);
		if (date == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * <p>해당 날짜가 음력 법정 공휴일에 해당하는지 여부를 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.isHolidayLunar(null)         = false
	 * DateUtil.isHolidayLunar("")           = false
	 * DateUtil.isHolidayLunar("   ")        = false
	 * DateUtil.isHolidayLunar("2014-01-29") = false
	 * DateUtil.isHolidayLunar("2014-01-30") = true
	 * DateUtil.isHolidayLunar("2014-01-31") = true
	 * DateUtil.isHolidayLunar("2014-02-01") = true
	 * DateUtil.isHolidayLunar("2014-05-06") = true
	 * DateUtil.isHolidayLunar(Date)         = true
	 * </pre>
	 * 
	 * @param obj      (날짜형식 문자 또는 Date 객체)
	 * @return boolean (법정 공휴일인지 여부)
	 */
	public static boolean isHolidayLunar(Object obj) {
		Date date = parseDate(obj, Locale.KOREA);
		if (date == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		ChineseCalendar chinaCal = new ChineseCalendar();
		chinaCal.setTimeInMillis(cal.getTimeInMillis());
		int lunarMonth = chinaCal.get(ChineseCalendar.MONTH) + 1;
		int lunarDay = chinaCal.get(ChineseCalendar.DAY_OF_MONTH);
		
		// 음력 법정 공휴일 정의
		String[][] lunarArr = new String[6][3];
		lunarArr[0] = new String[]{"01", "01", "설날"};
		lunarArr[1] = new String[]{"01", "02", ""};
		lunarArr[2] = new String[]{"04", "08", "석가탄신일"};
		lunarArr[3] = new String[]{"08", "14", ""};
		lunarArr[4] = new String[]{"08", "15", "추석"};
		lunarArr[5] = new String[]{"08", "16", ""};
		
		// 음력 12월의 마지막날인지 확인 (음력 설날 첫번째 휴일) - 연도마다 다름
		if (lunarMonth == 12 && lunarDay == chinaCal.getActualMaximum(ChineseCalendar.DAY_OF_MONTH)) {
			return true;
		}
		
		// 법정 공휴일 인지 체크
		for (String[] lunar: lunarArr) {
			if (lunarMonth == Integer.parseInt(lunar[0]) && lunarDay == Integer.parseInt(lunar[1])) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * <p>해당 날짜가 양력 법정 공휴일에 해당하는지 여부를 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.isHolidaySolar(null)         = false
	 * DateUtil.isHolidaySolar("")           = false
	 * DateUtil.isHolidaySolar("   ")        = false
	 * DateUtil.isHolidaySolar("2014-01-02") = false
	 * DateUtil.isHolidaySolar("2014-01-01") = true
	 * DateUtil.isHolidaySolar("2014-03-01") = true
	 * DateUtil.isHolidaySolar("2014-05-05") = true
	 * DateUtil.isHolidaySolar("2014-06-06") = true
	 * DateUtil.isHolidaySolar(Date)         = true
	 * </pre>
	 * 
	 * @param obj      (날짜형식 문자 또는 Date 객체)
	 * @return boolean (법정 공휴일인지 여부)
	 */
	public static boolean isHolidaySolar(Object obj) {
		Date date = parseDate(obj, Locale.KOREA);
		if (date == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int solarMonth = cal.get(ChineseCalendar.MONTH) + 1;
		int solarDay = cal.get(ChineseCalendar.DAY_OF_MONTH);
		
		// 양력 법정 공휴일 정의
		String[][] solarArr = new String[8][3];
		solarArr[0] = new String[]{"01", "01", "신정"};
		solarArr[1] = new String[]{"03", "01", "삼일절"};
		solarArr[2] = new String[]{"05", "05", "어린이날"};
		solarArr[3] = new String[]{"06", "06", "현충일"};
		solarArr[4] = new String[]{"08", "15", "광복절"};
		solarArr[5] = new String[]{"10", "03", "개천절"};
		solarArr[6] = new String[]{"10", "09", "한글날"};
		solarArr[7] = new String[]{"12", "25", "성탄절"};
		
		// 법정 공휴일 인지 체크
		for (String[] solar: solarArr) {
			if (solarMonth == Integer.parseInt(solar[0]) && solarDay == Integer.parseInt(solar[1])) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * <p>해당 날짜가 법정 공휴일(음력, 양력)에 해당하는지 여부를 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.isHoliday(null)         = false
	 * DateUtil.isHoliday("")           = false
	 * DateUtil.isHoliday("   ")        = false
	 * DateUtil.isHoliday("2014-01-29") = false
	 * DateUtil.isHoliday("2014-01-30") = true
	 * DateUtil.isHoliday("2014-01-31") = true
	 * DateUtil.isHoliday("2014-02-01") = true
	 * DateUtil.isHoliday("2014-05-05") = true
	 * DateUtil.isHoliday("2014-06-06") = true
	 * DateUtil.isHoliday(Date)         = true
	 * </pre>
	 * 
	 * @param obj      (날짜형식 문자 또는 Date 객체)
	 * @return boolean (법정 공휴일인지 여부)
	 */
	public static boolean isHoliday(Object obj) {
		boolean result = isHolidayLunar(obj);
		if (!result) {
			result = isHolidaySolar(obj);
		}
		return result;
	}
	
	/**
	 * <p>지정된 두 날짜의 년,월,일,시,분,초 간격을 계산해서 리턴한다.</p>
	 * 
	 * <pre>
	 * DateUtil.getDateDiff(*, *, "x")                                         = kr.co.akis.common.exception.ConditionException
	 * DateUtil.getDateDiff(null, *, *)                                        = throws java.lang.NullPointerException
	 * DateUtil.getDateDiff("", *, *)                                          = throws java.lang.NullPointerException
	 * DateUtil.getDateDiff(*, null, *)                                        = throws java.lang.NullPointerException
	 * DateUtil.getDateDiff(*, "", *)                                          = throws java.lang.NullPointerException
	 * DateUtil.getDateDiff("2019-10-20 17:59:30", "2019-10-20 17:59:37", "s") = -7
	 * DateUtil.getDateDiff("2019-10-20 17:59:37", "2019-10-20 17:59:30", "s") = 7
	 * DateUtil.getDateDiff("2019-03-07 13:00:00", "2019-03-07 13:03:59", "m") = -3
	 * DateUtil.getDateDiff("2019-03-07 13:03:59", "2019-03-07 13:00:00", "m") = 3
	 * DateUtil.getDateDiff("2019-03-07 13:00:00", "2019-03-08 13:59:59", "h") = -24
	 * DateUtil.getDateDiff("2019-03-08 13:59:59", "2019-03-07 13:00:00", "h") = 24
	 * DateUtil.getDateDiff("2019-10-20", "2019-10-23", "s")                   = -259200
	 * DateUtil.getDateDiff("2019-10-23", "2019-10-20", "s")                   = 259200
	 * DateUtil.getDateDiff("2019-10-20", "2019-10-23", "m")                   = -4320
	 * DateUtil.getDateDiff("2019-10-23", "2019-10-20", "m")                   = 4320
	 * DateUtil.getDateDiff("2019-10-20", "2019-10-23", "h")                   = -72
	 * DateUtil.getDateDiff("2019-10-23", "2019-10-20", "h")                   = 72
	 * DateUtil.getDateDiff("2019-10-20", "2019-10-23", "d")                   = -3
	 * DateUtil.getDateDiff("2019-10-23", "2019-10-20", "d")                   = 3
	 * DateUtil.getDateDiff("2019-10-23", "2019-12-31", "M")                   = -2
	 * DateUtil.getDateDiff("2019-12-31", "2019-10-23", "M")                   = 2
	 * DateUtil.getDateDiff("2018-10-23", "2019-12-31", "y")                   = -1
	 * DateUtil.getDateDiff("2019-12-31", "2018-10-23", "y")                   = 1
	 * DateUtil.getDateDiff(new Date(), new Date(), "y")                       = 0
	 * </pre>
	 * 
	 * @param obj1  (계산에 사용할 날짜형식 문자 또는 Date 객체1)
	 * @param obj2  (계산에 사용할 날짜형식 문자 또는 Date 객체2)
	 * @param gubun (구하고자 하는 간격 구분 값)
	 * @return long (해당 간격)
	 */
	public static long getDateDiff(Object obj1, Object obj2, String gubun) {
		if (!StringUtil.equalsSplit("s|m|h|d|M|y", gubun, "[|]")) {
			throw new ConditionException();
		}
		long result = 0;
		long millisecond = 1000;
		long second = 60;
		long minute = 60;
		long day = 24;
		long month = 30;
		long year = 12;
		long dateDiff = parseDate(obj1).getTime() - parseDate(obj2).getTime();
		if ("s".equals(gubun)) {
			result = dateDiff / (millisecond);
		} else if ("m".equals(gubun)) {
			result = dateDiff / (millisecond*second);
		} else if ("h".equals(gubun)) {
			result = dateDiff / (millisecond*second*minute);
		} else if ("d".equals(gubun)) {
			result = dateDiff / (millisecond*second*minute*day);
		} else if ("M".equals(gubun)) {
			result = dateDiff / (millisecond*second*minute*day*month);
		} else if ("y".equals(gubun)) {
			result = dateDiff / (millisecond*second*minute*day*month*year);
		}
		return result;
	}
	
}