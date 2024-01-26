/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : NumberUtil.java
 * @Description : Number 유틸 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class NumberUtil {
	
	/**
	 * <p>해당 값이 null, 공백일 경우 "" 반환하고, 그 외는 콤마 제거하고 trim 적용후 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.clean(null)        = ""
	 * NumberUtil.clean("")          = ""
	 * NumberUtil.clean("   ")       = ""
	 * NumberUtil.clean(" 123 ")     = "123"
	 * NumberUtil.clean(12345)       = "12345"
	 * NumberUtil.clean("1,234,567") = "1234567"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형, 숫자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String clean(Object obj) {
		String str = StringUtil.clean(obj);
		if (!StringUtil.isBlank(str)) {
			str = str.replaceAll(",", "");
		}
		return str;
	}
	
	/**
	 * <p>숫자(0123456789)로만 구성되어 있는지 체크한다.</p>
	 *
	 * <pre>
	 * NumberUtil.isNumber(null)     = false
	 * NumberUtil.isNumber("")       = false
	 * NumberUtil.isNumber("   ")    = false
	 * NumberUtil.isNumber("111.1")  = false
	 * NumberUtil.isNumber("12345 ") = false
	 * NumberUtil.isNumber("12345")  = true
	 * NumberUtil.isNumber("01234")  = true
	 * </pre>
	 *
	 * @param obj      (체크할 문자형, 숫자형 객체)
	 * @return boolean (체크 결과)
	 */
	public static boolean isNumber(Object obj) {
		if (StringUtil.isBlank(obj)) {
			return false;
		}
		String str = StringUtil.toString(obj);
		return str.matches("^[0-9]*$");
	}
	
	/**
	 * <p>해당 문자열이 숫자인지 체크한다.</p>
	 *
	 * <pre>
	 * NumberUtil.isNumeric(null)     = false
	 * NumberUtil.isNumeric("")       = false
	 * NumberUtil.isNumeric("   ")    = false
	 * NumberUtil.isNumeric("12345 ") = false
	 * NumberUtil.isNumeric("123 45") = false
	 * NumberUtil.isNumeric("12345")  = true
	 * NumberUtil.isNumeric("01234")  = true
	 * NumberUtil.isNumeric("111.1")  = true
	 * NumberUtil.isNumeric("+12345") = true
	 * NumberUtil.isNumeric("-12345") = true
	 * </pre>
	 *
	 * @param obj      (체크할 문자형, 숫자형 객체)
	 * @return boolean (체크 결과)
	 */
	public static boolean isNumeric(Object obj) {
		if (StringUtil.isBlank(obj)) {
			return false;
		}
		String str = StringUtil.toString(obj);
		return str.matches("[+-]?\\d*(\\.\\d+)?");
	}
	
	/**
	 * <p>해당 값을 trim 적용후 int형으로 변경후 리턴한다.</p>
	 * <p>해당 값이 null, 공백일 경우 0으로 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.toInt("2147483648")    = throws java.lang.NumberFormatException
	 * NumberUtil.toInt(null)            = 0
	 * NumberUtil.toInt("")              = 0
	 * NumberUtil.toInt("   ")           = 0
	 * NumberUtil.toInt(10)              = 10
	 * NumberUtil.toInt(" 10 ")          = 10
	 * NumberUtil.toInt("10.3")          = 10
	 * NumberUtil.toInt("10.7")          = 10
	 * NumberUtil.toInt("2147483647")    = 2147483647
	 * NumberUtil.toInt("2,147,483,647") = 2147483647
	 * </pre>
	 * 
	 * @param obj  (변경할 문자형, 숫자형 객체)
	 * @return int (변경후 숫자)
	 */
	public static int toInt(Object obj) {
		return toInt(obj, 0);
	}
	
	/**
	 * <p>해당 값이 null, 공백일 경우 새로운 int형을 반환하고, 그 외는 trim 적용후 int형으로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.toInt("2147483648", *)    = throws java.lang.NumberFormatException
	 * NumberUtil.toInt("", "2147483648")   = throws java.lang.NumberFormatException
	 * NumberUtil.toInt(null, 7)            = 7
	 * NumberUtil.toInt(null, "7")          = 7
	 * NumberUtil.toInt("", 7)              = 7
	 * NumberUtil.toInt("   ", 7)           = 7
	 * NumberUtil.toInt("", "12,345")       = 12345
	 * NumberUtil.toInt(10, *)              = 10
	 * NumberUtil.toInt(" 10 ", *)          = 10
	 * NumberUtil.toInt("10.3", *)          = 10
	 * NumberUtil.toInt("10.7", *)          = 10
	 * NumberUtil.toInt("2147483647", *)    = 2147483647
	 * NumberUtil.toInt("2,147,483,647", *) = 2147483647
	 * </pre>
	 * 
	 * @param obj    (변경할 문자형, 숫자형 객체)
	 * @param newInt (새로운 문자형, 숫자형 객체)
	 * @return int   (변경후 숫자)
	 */
	public static int toInt(Object obj, Object newInt) {
		Double num = toDouble(obj, newInt);
		if (num.intValue() != num.longValue()) {
			throw new NumberFormatException("For input string: \"" + num.longValue() + "\"");
		}
		return num.intValue();
	}
	
	/**
	 * <p>해당 값을 trim 적용후 int형 문자로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.toInt("2147483648")    = throws java.lang.NumberFormatException
	 * NumberUtil.toInt(null)            = ""
	 * NumberUtil.toInt("")              = ""
	 * NumberUtil.toInt("   ")           = ""
	 * NumberUtil.toInt(10)              = "10"
	 * NumberUtil.toInt(" 10 ")          = "10"
	 * NumberUtil.toInt("10.3")          = "10"
	 * NumberUtil.toInt("10.7")          = "10"
	 * NumberUtil.toInt("2147483647")    = "2147483647"
	 * NumberUtil.toInt("2,147,483,647") = "2147483647"
	 * </pre>
	 * 
	 * @param obj  (변경할 문자형, 숫자형 객체)
	 * @return String (변경후 int형 문자)
	 */
	public static String toIntStr(Object obj) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		return String.valueOf(toInt(obj));
	}
	
	/**
	 * <p>해당 값이 null, 공백일 경우 새로운 int형 문자를 반환하고, 그 외는 trim 적용후 int형 문자로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.toInt("2147483648", *)    = throws java.lang.NumberFormatException
	 * NumberUtil.toInt("", "2147483648")   = throws java.lang.NumberFormatException
	 * NumberUtil.toInt(null, 7)            = "7"
	 * NumberUtil.toInt(null, "7")          = "7"
	 * NumberUtil.toInt("", 7)              = "7"
	 * NumberUtil.toInt("   ", 7)           = "7"
	 * NumberUtil.toInt("", "12,345")       = "12345"
	 * NumberUtil.toInt(10, *)              = "10"
	 * NumberUtil.toInt(" 10 ", *)          = "10"
	 * NumberUtil.toInt("10.3", *)          = "10"
	 * NumberUtil.toInt("10.7", *)          = "10"
	 * NumberUtil.toInt("2147483647", *)    = "2147483647"
	 * NumberUtil.toInt("2,147,483,647", *) = "2147483647"
	 * </pre>
	 * 
	 * @param obj    (변경할 문자형, 숫자형 객체)
	 * @param newInt (새로운 문자형, 숫자형 객체)
	 * @return int   (변경후 int형 문자)
	 */
	public static String toIntStr(Object obj, Object newInt) {
		return String.valueOf(toInt(obj, newInt));
	}
	
	/**
	 * <p>해당 값을 trim 적용후 long형으로 변경후 리턴한다.</p>
	 * <p>해당 값이 null, 공백일 경우 0으로 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.toLong(null)            = 0
	 * NumberUtil.toLong("")              = 0
	 * NumberUtil.toLong("   ")           = 0
	 * NumberUtil.toLong(10)              = 10
	 * NumberUtil.toLong(" 10 ")          = 10
	 * NumberUtil.toLong("10.3")          = 10
	 * NumberUtil.toLong("10.7")          = 10
	 * NumberUtil.toLong("2147483647")    = 2147483647
	 * NumberUtil.toLong("2147483648")    = 2147483648
	 * NumberUtil.toLong("2,147,483,648") = 2147483648
	 * </pre>
	 * 
	 * @param obj   (변경할 문자형, 숫자형 객체)
	 * @return long (변경후 숫자)
	 */
	public static long toLong(Object obj) {
		return toLong(obj, 0);
	}
	
	/**
	 * <p>해당 값이 null, 공백일 경우 새로운 long형을 반환하고, 그 외는 trim 적용후 long형으로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.toLong(null, 7)            = 7
	 * NumberUtil.toLong(null, "7")          = 7
	 * NumberUtil.toLong("", 7)              = 7
	 * NumberUtil.toLong("   ", 7)           = 7
	 * NumberUtil.toLong("", "12,345")       = 12345
	 * NumberUtil.toLong(10, *)              = 10
	 * NumberUtil.toLong(" 10 ", *)          = 10
	 * NumberUtil.toLong("10.3", *)          = 10
	 * NumberUtil.toLong("10.7", *)          = 10
	 * NumberUtil.toLong("2147483647", *)    = 2147483647
	 * NumberUtil.toLong("2147483648", *)    = 2147483648
	 * NumberUtil.toLong("2,147,483,648", *) = 2147483648
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형, 숫자형 객체)
	 * @param newLong (새로운 문자형, 숫자형 객체)
	 * @return long   (변경후 숫자)
	 */
	public static long toLong(Object obj, Object newLong) {
		return toDouble(obj, newLong).longValue();
	}
	
	/**
	 * <p>해당 값을 trim 적용후 long형 문자로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.toLong(null)            = ""
	 * NumberUtil.toLong("")              = ""
	 * NumberUtil.toLong("   ")           = ""
	 * NumberUtil.toLong(10)              = "10"
	 * NumberUtil.toLong(" 10 ")          = "10"
	 * NumberUtil.toLong("10.3")          = "10"
	 * NumberUtil.toLong("10.7")          = "10"
	 * NumberUtil.toLong("2147483647")    = "2147483647"
	 * NumberUtil.toLong("2147483648")    = "2147483648"
	 * NumberUtil.toLong("2,147,483,648") = "2147483648"
	 * </pre>
	 * 
	 * @param obj   (변경할 문자형, 숫자형 객체)
	 * @return long (변경후 long형 문자)
	 */
	public static String toLongStr(Object obj) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		return String.valueOf(toLong(obj));
	}
	
	/**
	 * <p>해당 값이 null, 공백일 경우 새로운 long형 문자를 반환하고, 그 외는 trim 적용후 long형 문자로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.toLong(null, 7)            = "7"
	 * NumberUtil.toLong(null, "7")          = "7"
	 * NumberUtil.toLong("", 7)              = "7"
	 * NumberUtil.toLong("   ", 7)           = "7"
	 * NumberUtil.toLong("", "12,345")       = "12345"
	 * NumberUtil.toLong(10, *)              = "10"
	 * NumberUtil.toLong(" 10 ", *)          = "10"
	 * NumberUtil.toLong("10.3", *)          = "10"
	 * NumberUtil.toLong("10.7", *)          = "10"
	 * NumberUtil.toLong("2147483647", *)    = "2147483647"
	 * NumberUtil.toLong("2147483648", *)    = "2147483648"
	 * NumberUtil.toLong("2,147,483,648", *) = "2147483648"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형, 숫자형 객체)
	 * @param newLong (새로운 문자형, 숫자형 객체)
	 * @return long   (변경후 long형 문자)
	 */
	public static String toLongStr(Object obj, Object newLong) {
		return String.valueOf(toLong(obj, newLong));
	}
	
	/**
	 * <p>해당 값을 trim 적용후 Double 객체형으로 변경후 리턴한다.</p>
	 * <p>해당 값이 null, 공백일 경우 0.0으로 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.toDouble(null)              = 0.0
	 * NumberUtil.toDouble("")                = 0.0
	 * NumberUtil.toDouble("   ")             = 0.0
	 * NumberUtil.toDouble(10)                = 10.0
	 * NumberUtil.toDouble(" 10 ")            = 10.0
	 * NumberUtil.toDouble(12.012)            = 12.012
	 * NumberUtil.toDouble("12.012")          = 12.012
	 * NumberUtil.toDouble("12,345.77")       = 12345.77
	 * NumberUtil.toDouble("2147483647")      = 2.14748365E9
	 * NumberUtil.toDouble("2147483647.1")    = 2.1474836471E9
	 * NumberUtil.toDouble("2,147,483,647.1") = 2.1474836471E9
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형, 숫자형 객체)
	 * @return Double (변경후 숫자)
	 */
	public static Double toDouble(Object obj) {
		return toDouble(obj, 0.0);
	}
	
	/**
	 * <p>해당 값이 null, 공백일 경우 새로운 Double 객체형을 반환하고, 그 외는 trim 적용후 Double 객체형으로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.toDouble(null, 7)           = 7.0
	 * NumberUtil.toDouble(null, "7")         = 7.0
	 * NumberUtil.toDouble("", 7)             = 7.0
	 * NumberUtil.toDouble("   ", 7)          = 7.0
	 * NumberUtil.toDouble("", "12,345")      = 12345.0
	 * NumberUtil.toDouble(10, *)             = 10.0
	 * NumberUtil.toDouble(" 10 ", *)         = 10.0
	 * NumberUtil.toDouble(12.012, *)         = 12.012
	 * NumberUtil.toDouble("12.012", *)       = 12.012
	 * NumberUtil.toDouble("2147483647", *)   = 2.14748365E9
	 * NumberUtil.toDouble("2147483647.1", *) = 2.1474836471E9
	 * NumberUtil.toDouble("2,147,483,647.1") = 2.1474836471E9
	 * </pre>
	 * 
	 * @param obj       (변경할 문자형, 숫자형 객체)
	 * @param newDouble (새로운 문자형, 숫자형 객체)
	 * @return Double   (변경후 숫자)
	 */
	public static Double toDouble(Object obj, Object newDouble) {
		if (StringUtil.isBlank(obj)) {
			return Double.parseDouble(clean(newDouble));
		} else {
			return Double.parseDouble(clean(obj));
		}
	}
	
	/**
	 * <p>해당 값을 trim 적용후 double형 문자로 변경후 리턴한다.</p>
	 * <p>자리수가 8자리 이상이고, '.0'으로 끝나는 경우 정수만 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.toDoubleStr(null)              = ""
	 * NumberUtil.toDoubleStr("")                = ""
	 * NumberUtil.toDoubleStr("   ")             = ""
	 * NumberUtil.toDoubleStr(" 10 ")            = "10"
	 * NumberUtil.toDoubleStr("12.012")          = "12.012"
	 * NumberUtil.toDoubleStr("2147483647")      = "2147483647"
	 * NumberUtil.toDoubleStr("2147483647.1")    = "2147483647.1"
	 * NumberUtil.toDoubleStr("2,147,483,647.1") = "2147483647.1"
	 * NumberUtil.toDoubleStr(10)                = "10"
	 * NumberUtil.toDoubleStr(10.0)              = "10.0"
	 * NumberUtil.toDoubleStr(1234567.0)         = "1234567.0"
	 * NumberUtil.toDoubleStr(12345678.0)        = "12345678"
	 * NumberUtil.toDoubleStr(12345678.1)        = "12345678.1"
	 * NumberUtil.toDoubleStr(12345678.001)      = "12345678.001"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형, 숫자형 객체)
	 * @return String (변경후 double형 문자)
	 */
	public static String toDoubleStr(Object obj) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		return toDoubleStr(obj, 0.0);
	}
	
	/**
	 * <p>해당 값이 null, 공백일 경우 새로운 double형 문자를 반환하고, 그 외는 trim 적용후 double형 문자로 변경후 리턴한다.</p>
	 * <p>자리수가 8자리 이상이고, '.0'으로 끝나는 경우 정수만 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.toDoubleStr(null, 7)              = "7"
	 * NumberUtil.toDoubleStr(null, "7")            = "7"
	 * NumberUtil.toDoubleStr("", 7)                = "7"
	 * NumberUtil.toDoubleStr("   ", 7)             = "7"
	 * NumberUtil.toDoubleStr("", "12,345")         = "12345"
	 * NumberUtil.toDoubleStr("", "12,345.0")       = "12345.0"
	 * NumberUtil.toDoubleStr(" 10 ", *)            = "10"
	 * NumberUtil.toDoubleStr("12.012", *)          = "12.012"
	 * NumberUtil.toDoubleStr("2147483647", *)      = "2147483647"
	 * NumberUtil.toDoubleStr("2147483647.1", *)    = "2147483647.1"
	 * NumberUtil.toDoubleStr("2,147,483,647.1", *) = "2147483647.1"
	 * NumberUtil.toDoubleStr(10, *)                = "10"
	 * NumberUtil.toDoubleStr(10.0, *)              = "10.0"
	 * NumberUtil.toDoubleStr(1234567.0, *)         = "1234567.0"
	 * NumberUtil.toDoubleStr(12345678.0, *)        = "12345678"
	 * NumberUtil.toDoubleStr(12345678.1, *)        = "12345678.1"
	 * NumberUtil.toDoubleStr(12345678.001, *)      = "12345678.001"
	 * </pre>
	 * 
	 * @param obj       (변경할 문자형, 숫자형 객체)
	 * @param newDouble (새로운 문자형, 숫자형 객체)
	 * @return double   (변경후 double형 문자)
	 */
	public static String toDoubleStr(Object obj, Object newDouble) {
		Object doubleObj = StringUtil.isBlank(obj) ? newDouble : obj;
		if (doubleObj instanceof Double) {
			Double castObj = (Double) doubleObj;
			if (castObj == toLong(castObj)) {
				return new BigDecimal(clean(doubleObj)).toPlainString();
			} else {
				return String.valueOf(new BigDecimal(clean(doubleObj)));
			}
		} else {
			return String.valueOf(new BigDecimal(clean(doubleObj)));
		}
	}
	
	/**
	 * <p>double형의 문자 또는 숫자에서 소수점(.0)을 정리해서 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.cleanDouble(null)         = ""
	 * NumberUtil.cleanDouble("")           = ""
	 * NumberUtil.cleanDouble("   ")        = ""
	 * NumberUtil.cleanDouble(123)          = "123"
	 * NumberUtil.cleanDouble("123")        = "123"
	 * NumberUtil.cleanDouble("100")        = "100"
	 * NumberUtil.cleanDouble("123.")       = "123"
	 * NumberUtil.cleanDouble("123.0")      = "123"
	 * NumberUtil.cleanDouble("123.000")    = "123"
	 * NumberUtil.cleanDouble("123.700")    = "123.7"
	 * NumberUtil.cleanDouble("123.070")    = "123.07"
	 * NumberUtil.cleanDouble(123.07000)    = "123.07"
	 * NumberUtil.cleanDouble("123.07000")  = "123.07"
	 * NumberUtil.cleanDouble("12,345.010") = "12,345.01"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형, 숫자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String cleanDouble(Object obj) {
		return cleanDouble(obj, false);
	}
	
	/**
	 * <p>double형의 문자 또는 숫자에서 소수점(.0)을 정리하고, 콤마 제거 여부에 따라 콤마 제거 후 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.cleanDouble(null, *)             = ""
	 * NumberUtil.cleanDouble("", *)               = ""
	 * NumberUtil.cleanDouble("   ", *)            = ""
	 * NumberUtil.cleanDouble(123, *)              = "123"
	 * NumberUtil.cleanDouble("123", *)            = "123"
	 * NumberUtil.cleanDouble("100", *)            = "100"
	 * NumberUtil.cleanDouble("123.", *)           = "123"
	 * NumberUtil.cleanDouble("123.0", *)          = "123"
	 * NumberUtil.cleanDouble("123.000", *)        = "123"
	 * NumberUtil.cleanDouble("123.700", *)        = "123.7"
	 * NumberUtil.cleanDouble("123.070", *)        = "123.07"
	 * NumberUtil.cleanDouble(123.07000, *)        = "123.07"
	 * NumberUtil.cleanDouble("123.07000", *)      = "123.07"
	 * NumberUtil.cleanDouble("12,345.010", true)  = "12345.01"
	 * NumberUtil.cleanDouble("12,345.010", false) = "12,345.01"
	 * </pre>
	 * 
	 * @param obj           (변경할 문자형, 숫자형 객체)
	 * @param isRemoveComma (콤마 제거 여부)
	 * @return String       (변경후 문자)
	 */
	public static String cleanDouble(Object obj, boolean isRemoveComma) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		String str = StringUtil.clean(obj);
		if (isRemoveComma) {
			str = clean(obj);
		}
		str = str.replaceAll("\\.0*$", "");
		if (!str.matches("^[0-9]*$")) {
			str = str.replaceAll("0*$", "");
		}
		return str;
	}
	
	/**
	 * <p>부동소수를 올림, 내림, 반올림하여 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.round(null, *, *)        = 0.0
	 * NumberUtil.round("", *, *)          = 0.0
	 * NumberUtil.round("   ", *, *)       = 0.0
	 * NumberUtil.round(123, *, *)         = 123.0
	 * NumberUtil.round("123", *, *)       = 123.0
	 * NumberUtil.round(123.44, 1, 0)      = 123.5
	 * NumberUtil.round(123.44, 1, 2)      = 123.4
	 * NumberUtil.round(123.45, 1, 1)      = 123.4
	 * NumberUtil.round(123.45, 1, 2)      = 123.5
	 * NumberUtil.round(123.33, 0, 0)      = 124.0
	 * NumberUtil.round(123.77, 0, 1)      = 123.0
	 * NumberUtil.round(123.45, 0, 2)      = 123.0
	 * NumberUtil.round(123.55, 0, 2)      = 124.0
	 * NumberUtil.round(123.45, 5, 2)      = 123.45
	 * NumberUtil.round(123.55, *, 5)      = 123.55
	 * NumberUtil.round("12,345.65", 1, 2) = 12345.7
	 * NumberUtil.round(*, -1, *)          = throws java.lang.ArithmeticException
	 * </pre>
	 * 
	 * @param obj     (부동소수)
	 * @param cipher  (처리할 자리 수)
	 * @param mode    (처리방법 - 0:올림, 1:내림, 2:반올림)
	 * @return Double (처리후 부동소수)
	 */
	public static Double round(Object obj, int cipher, int mode) {
		if (StringUtil.isBlank(obj)) {
			return 0.0;
		}
		BigDecimal bd = new BigDecimal(clean(obj));
		if (mode == 0) {
			return bd.setScale(cipher, BigDecimal.ROUND_UP).doubleValue();
		} else if (mode == 1) {
			return bd.setScale(cipher, BigDecimal.ROUND_DOWN).doubleValue();
		} else if (mode == 2) {
			return bd.setScale(cipher, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return toDouble(obj);
	}
	
	/**
	 * <p>설정한 범위안에서 랜덤한 숫자 int 형을 만들어 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.randomInt(1, 100)    = 99
	 * NumberUtil.randomInt(1, 100)    = 100
	 * NumberUtil.randomInt(1, 1000)   = 7
	 * NumberUtil.randomInt(100, 1000) = 111
	 * NumberUtil.randomInt(100, 2000) = 1234
	 * </pre>
	 * 
	 * @param startNum (숫자 시작 범위)
	 * @param endNum   (숫자 마지막 범위)
	 * @return int     (생성된 랜덤한 숫자)
	 */
	public static int randomInt(int startNum, int endNum) {
		int randomNum;
		if(endNum == 0) {
			randomNum = 0;
		} else {
			randomNum = (int)((endNum - startNum + 1) * Math.random() + startNum);
		}
		return randomNum;
	}
	
	/**
	 * <p>설정한 범위안에서 랜덤한 숫자를 만들고 마지막 범위 자릿수 만큼 앞에 0을 붙여서 리턴한다.</p>
	 * 
	 * <pre>
	 * NumberUtil.randomStr(1, 100)    = "099"
	 * NumberUtil.randomStr(1, 100)    = "100"
	 * NumberUtil.randomStr(1, 1000)   = "0007"
	 * NumberUtil.randomStr(100, 1000) = "0111"
	 * NumberUtil.randomStr(100, 2000) = "1234"
	 * </pre>
	 * 
	 * @param startNum (숫자 시작 범위)
	 * @param endNum   (숫자 마지막 범위)
	 * @return String  (생성된 랜덤한 숫자)
	 */
	public static String randomStr(int startNum, int endNum) {
		return StringUtil.prefixZero(randomInt(startNum, endNum), String.valueOf(endNum).length());
	}
	
	/**
	 * <p>문자가 숫자 0 으로만 되어 있는지 체크한다.</p>
	 *
	 * <pre>
	 * NumberUtil.isAllZero(null)   = false
	 * NumberUtil.isAllZero("")     = false
	 * NumberUtil.isAllZero("   ")  = false
	 * NumberUtil.isAllZero("ac0")  = false
	 * NumberUtil.isAllZero(" 00")  = false
	 * NumberUtil.isAllZero("0 0")  = false
	 * NumberUtil.isAllZero("0")    = true
	 * NumberUtil.isAllZero("000")  = true
	 * </pre>
	 * 
	 * @param obj      (체크할 문자)
	 * @return boolean (체크 결과)
	 */
	public static boolean isAllZero(Object obj) {
		if (StringUtil.isBlank(obj)) {
			return false;
		}
		String str = StringUtil.toString(obj);
		return str.matches("^0*$");
	}
	
	/**
	 * <p>숫자 3자리 마다 콤마 삽입후 리턴한다.</p>
	 *
	 * <pre>
	 * NumberUtil.toNumberFormat(null)       = ""
	 * NumberUtil.toNumberFormat("")         = ""
	 * NumberUtil.toNumberFormat("   ")      = ""
	 * NumberUtil.toNumberFormat(12345)      = "12,345"
	 * NumberUtil.toNumberFormat("12345")    = "12,345"
	 * NumberUtil.toNumberFormat(12345.67)   = "12,345.67"
	 * NumberUtil.toNumberFormat("12345.67") = "12,345.67"
	 * NumberUtil.toNumberFormat("12,345")   = "12,345"
	 * NumberUtil.toNumberFormat("12,345.6") = "12,345.6"
	 * </pre>
	 *
	 * @param obj     (적용할 숫자)
	 * @return String (적용후 숫자)
	 */
	public static String toNumberFormat(Object obj) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		String str = clean(obj);
		if (StringUtil.contains(str, ".")) {
			return NumberFormat.getInstance().format(Double.parseDouble(str)); 
		}
		return NumberFormat.getInstance().format(Long.parseLong(str));
	}
	
}