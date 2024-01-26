/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : StringUtil.java
 * @Description : String 유틸 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	/**
	 * <p>해당 객체를 String 형으로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.toString(null)      = ""
	 * StringUtil.toString("")        = ""
	 * StringUtil.toString("   ")     = "   "
	 * StringUtil.toString(" abc ")   = " abc "
	 * StringUtil.toString("  123  ") = "  123  "
	 * StringUtil.toString(123)       = "123"
	 * StringUtil.toString(Object)    = Object.toString()
	 * </pre>
	 * 
	 * @param obj     (변경할 객체)
	 * @return String (변경후 문자)
	 */
	public static String toString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return String.valueOf(obj);
		}
	}
	
	/**
	 * <p>해당 값이 null, 공백문자("")이면 true 값을 반환한다.</P>
	 * 
	 * <pre>
	 * StringUtil.isEmpty(null)    = true
	 * StringUtil.isEmpty("")      = true
	 * StringUtil.isEmpty("   ")   = false
	 * StringUtil.isEmpty("str")   = false
	 * StringUtil.isEmpty(" str ") = false
	 * </pre>
	 * 
	 * @param obj      (문자형, 숫자형 객체)
	 * @return boolean (체크결과)
	 */
	public static boolean isEmpty(Object obj) {
		return obj == null || String.valueOf(obj).length() == 0;
	}
	
	/**
	 * <p>해당 값이 null, 공백문자(""), whitespace(" ")이면 true 값을 반환한다.</p>
	 * 
	 * <pre>
	 * StringUtil.isBlank(null)    = true
	 * StringUtil.isBlank("")      = true
	 * StringUtil.isBlank("   ")   = true
	 * StringUtil.isBlank("str")   = false
	 * StringUtil.isBlank(" str ") = false
	 * </pre>
	 * 
	 * @param obj      (문자형, 숫자형 객체)
	 * @return boolean (체크결과)
	 */
	public static boolean isBlank(Object obj) {
		String str = (obj == null) ? "" : String.valueOf(obj).trim();
		return str.length() == 0;
	}
	
	/**
	 * <p>해당 값이 null일 경우 null을 리턴하고, 그 외는 좌우 공백 제거후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.trim(null)    = null
	 * StringUtil.trim("")      = ""
	 * StringUtil.trim("   ")   = ""
	 * StringUtil.trim(" abc ") = "abc"
	 * </pre>
	 * 
	 * @param obj     (문자형 객체)
	 * @return String (공백제거 후 문자)
	 */
	public static String trim(Object obj) {
		return (obj == null) ? null : String.valueOf(obj).trim();
	}
	
	/**
	 * <p>해당 값이 null, 공백일 경우 null을 리턴하고, 그 외는 좌우 공백 제거후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.trimToNull(null)    = null
	 * StringUtil.trimToNull("")      = null
	 * StringUtil.trimToNull("   ")   = null
	 * StringUtil.trimToNull(" abc ") = "abc"
	 * </pre>
	 * 
	 * @param obj     (문자형 객체)
	 * @return String (공백제거 후 문자)
	 */
	public static String trimToNull(Object obj) {
		return isBlank(obj) ? null : String.valueOf(obj).trim();
	}
	
	/**
	 * <p>해당 값이 null, 공백일 경우 "" 반환하고, 그 외는 trim 적용후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.clean(null)    = ""
	 * StringUtil.clean("")      = ""
	 * StringUtil.clean("   ")   = ""
	 * StringUtil.clean(" abc ") = "abc"
	 * StringUtil.clean(12345)   = "12345"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형, 숫자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String clean(Object obj) {
		return (obj == null) ? "" : String.valueOf(obj).trim();
	}
	
	/**
	 * <p>해당 값이 null, 공백일 경우 새로운 문자를 반환하고, 그 외는 trim 적용후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.clean(null, "xyz")  = "xyz"
	 * StringUtil.clean("", "xyz")    = "xyz"
	 * StringUtil.clean("", " xyz ")  = " xyz "
	 * StringUtil.clean("   ", "xyz") = "xyz"
	 * StringUtil.clean(" abc ", *)   = "abc"
	 * StringUtil.clean(123, *)       = "123"
	 * </pre>
	 * 
	 * @param obj     (적용할 문자형, 숫자형 객체)
	 * @param newStr  (새로운 문자)
	 * @return String (적용후 문자)
	 */
	public static String clean(Object obj, String newStr) {
		return isBlank(obj) ? newStr : clean(obj);
	}
	
	/**
	 * <p>해당 값이 null, 공백일 경우 새로운 int 형을 반환하고, 그 외는 trim 적용후 int 형으로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.clean(null, 567)       = 567
	 * StringUtil.clean("", 567)         = 567
	 * StringUtil.clean("   ", 567)      = 567
	 * StringUtil.clean(" 123 ", *)      = 123
	 * StringUtil.clean(123, *)          = 123
	 * StringUtil.clean("2147483647", *) = 2147483647
	 * StringUtil.clean("2147483648", *) = throws java.lang.NumberFormatException
	 * </pre>
	 * 
	 * @param obj    (변경할 문자형, 숫자형 객체)
	 * @param newInt (새로운 숫자)
	 * @return int   (변경후 숫자)
	 */
	public static int clean(Object obj, int newInt) {
		return isBlank(obj) ? newInt : Integer.parseInt(clean(obj));
	}
	
	/**
	 * <p>해당 값이 null, 공백일 경우 새로운 long 형을 반환하고, 그 외는 trim 적용후 long 형으로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.clean(null, 567)       = 567
	 * StringUtil.clean("", 567)         = 567
	 * StringUtil.clean("   ", 567)      = 567
	 * StringUtil.clean(" 123 ", *)      = 123
	 * StringUtil.clean(123, *)          = 123
	 * StringUtil.clean("2147483647", *) = 2147483647
	 * StringUtil.clean("2147483648", *) = 2147483648
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형, 숫자형 객체)
	 * @param newLong (새로운 숫자)
	 * @return long   (변경후 숫자)
	 */
	public static long clean(Object obj, long newLong) {
		return isBlank(obj) ? newLong : Long.parseLong(clean(obj));
	}
	
	/**
	 * <p>해당 식을 평가하여 true인지 false인지에 따라 두 값 중 하나를 반환한다.</p>
	 * 
	 * <pre>
	 * StringUtil.iif(1 == 1, 111, 222)              = 111
	 * StringUtil.iif(1 == 1, "111", "222")          = "111"
	 * StringUtil.iif(1 == 1, true, false)           = true
	 * StringUtil.iif(1 == 1, "true", "false")       = "true"
	 * StringUtil.iif(1 == 2, 111, 222)              = 222
	 * StringUtil.iif(1 == 2, "111", "222")          = "222"
	 * StringUtil.iif(1 == 2, true, false)           = false
	 * StringUtil.iif(1 == 2, "true", "false")       = "false"
	 * StringUtil.iif("a".equals("a"), "aaa", "bbb") = "aaa"
	 * StringUtil.iif("a".equals("b"), "aaa", "bbb") = "bbb"
	 * StringUtil.iif(1 {@literal >} 2, "111", "222")           = "222"
	 * StringUtil.iif(1 {@literal >} 2, "111", null)            = null
	 * </pre>
	 * 
	 * @param <T>      (Type Parameter)
	 * @param expr     (평가할 식)
	 * @param trueVal  (true일 경우 반환할 값)
	 * @param falseVal (false일 경우 반환할 값)
	 * @return T       (평가결과 값)
	 */
	public static <T> T iif(boolean expr, T trueVal, T falseVal) {
		return expr ? trueVal : falseVal;
	}
	
	/**
	 * <p>문자열의 공백 제거 후 Left 에서부터 지정된 길이만큼 문자를 가져온다.</p>
	 * 
	 * <pre>
	 * StringUtil.left(null, *)      = ""
	 * StringUtil.left("", *)        = ""
	 * StringUtil.left(*, -ve)       = ""
	 * StringUtil.left("abcde", 0)   = ""
	 * StringUtil.left("abcde", 3)   = "abc"
	 * StringUtil.left(" abcde", 3)  = "abc"
	 * StringUtil.left("  abcde", 3) = "abc"
	 * StringUtil.left("abcde", 7)   = "abcde"
	 * StringUtil.left(12345, 3)     = "123"
	 * </pre>
	 * 
	 * @param obj     (추출할 문자형, 숫자형 객체)
	 * @param len     (가져올 길이)
	 * @return String (추출한 결과값)
	 */
	public static String left(Object obj, int len) {
		return left(obj, len, true);
	}
	
	/**
	 * <p>문자열의 Left 에서부터 지정된 길이만큼 문자를 가져온다.</p>
	 * 
	 * <pre>
	 * StringUtil.left(null, *, *)          = ""
	 * StringUtil.left("", *, *)            = ""
	 * StringUtil.left(*, -ve, *)           = ""
	 * StringUtil.left("abcde", 0, *)       = ""
	 * StringUtil.left("abcde", 3, *)       = "abc"
	 * StringUtil.left(" abcde", 3, false)  = " ab"
	 * StringUtil.left("  abcde", 3, false) = "  a"
	 * StringUtil.left("  abcde", 3, true)  = "abc"
	 * StringUtil.left("abcde", 7, *)       = "abcde"
	 * StringUtil.left(12345, 3, *)         = "123"
	 * </pre>
	 * 
	 * @param obj     (추출할 문자형, 숫자형 객체)
	 * @param len     (가져올 길이)
	 * @param isTrim  (문자 좌우 공백제거 여부)
	 * @return String (추출한 결과값)
	 */
	public static String left(Object obj, int len, boolean isTrim) {
		if (isEmpty(obj) || len < 0) {
			return "";
		}
		String str = toString(obj);
		if (isTrim) {
			str = clean(str);
		}
		if (str.length() <= len) {
			return str;
		} else {
			return str.substring(0, len);
		}
	}
	
	/**
	 * <p>문자열의 공백 제거 후 Right 에서부터 지정된 길이만큼 문자를 가져온다.</p>
	 * 
	 * <pre>
	 * StringUtil.right(null, *)      = ""
	 * StringUtil.right("", *)        = ""
	 * StringUtil.right(*, -ve)       = ""
	 * StringUtil.right("abcde", 0)   = ""
	 * StringUtil.right("abcde", 3)   = "cde"
	 * StringUtil.right("abcde ", 3)  = "cde "
	 * StringUtil.right("abcde  ", 3) = "cde"
	 * StringUtil.right("abcde", 7)   = "abcde"
	 * StringUtil.right(12345, 3)     = "345"
	 * </pre>
	 * 
	 * @param obj     (추출할 문자형, 숫자형 객체)
	 * @param len     (가져올 길이)
	 * @return String (추출한 결과값)
	 */
	public static String right(Object obj, int len) {
		return right(obj, len, true);
	}
	
	/**
	 * <p>문자열의 Right 에서부터 지정된 길이만큼 문자를 가져온다.</p>
	 * 
	 * <pre>
	 * StringUtil.right(null, *, *)          = ""
	 * StringUtil.right("", *, *)            = ""
	 * StringUtil.right(*, -ve, *)           = ""
	 * StringUtil.right("abcde", 0, *)       = ""
	 * StringUtil.right("abcde", 3, *)       = "cde"
	 * StringUtil.right("abcde ", 3, false)  = "de "
	 * StringUtil.right("abcde  ", 3, false) = "e  "
	 * StringUtil.right("abcde  ", 3, true)  = "cde"
	 * StringUtil.right("abcde", 7, *)       = "abcde"
	 * StringUtil.right(12345, 3, *)         = "345"
	 * </pre>
	 * 
	 * @param obj     (추출할 문자형, 숫자형 객체)
	 * @param len     (가져올 길이)
	 * @param isTrim  (문자 좌우 공백제거 여부)
	 * @return String (추출한 결과값)
	 */
	public static String right(Object obj, int len, boolean isTrim) {
		if (isEmpty(obj) || len < 0) {
			return "";
		}
		String str = toString(obj);
		if (isTrim) {
			str = clean(str);
		}
		if (str.length() <= len) {
			return str;
		} else {
			return str.substring(str.length() - len);
		}
	}
	
	/**
	 * <p>문자열의 공백 제거 후 지정된 시작 인덱스에서부터 문자열의 끝가지 가져온다.</p>
	 * 
	 * <pre>
	 * StringUtil.substring(null, *)       = ""
	 * StringUtil.substring("", *)         = ""
	 * StringUtil.substring("abcde", 5)    = ""
	 * StringUtil.substring("abcde", 0)    = "abcde"
	 * StringUtil.substring("abcde", -2)   = "abcde"
	 * StringUtil.substring("  abcde ", 0) = "abcde"
	 * StringUtil.substring("abcde", 3)    = "de"
	 * StringUtil.substring("  abcde ", 3) = "de"
	 * StringUtil.substring("a bcde", 1)   = " bcde"
	 * StringUtil.substring(12345, 2)      = "345"
	 * </pre>
	 * 
	 * @param obj      (추출할 문자형, 숫자형 객체)
	 * @param startIdx (시작할 위치)
	 * @return String  (추출한 결과값)
	 */
	public static String substring(Object obj, int startIdx) {
		return substring(obj, startIdx, true);
	}
	
	/**
	 * <p>문자열의 지정된 시작 인덱스에서부터 문자열의 끝가지 가져온다.</p>
	 * 
	 * <pre>
	 * StringUtil.substring(null, *, *)           = ""
	 * StringUtil.substring("", *, *)             = ""
	 * StringUtil.substring("abcde", 5, *)        = ""
	 * StringUtil.substring("abcde", 0, *)        = "abcde"
	 * StringUtil.substring("abcde", -2, *)       = "abcde"
	 * StringUtil.substring("abcde", 3, *)        = "de"
	 * StringUtil.substring("a bcde", 1, *)       = " bcde"
	 * StringUtil.substring("  abcde ", 0, false) = "  abcde "
	 * StringUtil.substring("  abcde ", 0, true)  = "abcde"
	 * StringUtil.substring("  abcde ", 3, false) = "bcde "
	 * StringUtil.substring("  abcde ", 3, true)  = "de"
	 * StringUtil.substring(12345, 2, *)          = "345"
	 * </pre>
	 * 
	 * @param obj      (추출할 문자형, 숫자형 객체)
	 * @param startIdx (시작할 위치)
	 * @param isTrim   (문자 좌우 공백제거 여부)
	 * @return String  (추출한 결과값)
	 */
	public static String substring(Object obj, int startIdx, boolean isTrim) {
		if (startIdx < 0) {
			startIdx = 0;
		}
		return substring(obj, startIdx, toString(obj).length() - startIdx, isTrim);
	}
	
	/**
	 * <p>문자열의 공백 제거 후 지정된 시작 인덱스에서부터 지정된 길이만큼 문자를 가져온다.</p>
	 * 
	 * <pre>
	 * StringUtil.substring(null, *, *)       = ""
	 * StringUtil.substring("", *, *)         = ""
	 * StringUtil.substring(*, *, -ve)        = ""
	 * StringUtil.substring("abcde", 5, *)    = ""
	 * StringUtil.substring("abcde", 0, 3)    = "abc"
	 * StringUtil.substring("abcde", -2, 3)   = "abc"
	 * StringUtil.substring("a bcde", 0, 3)   = "a b"
	 * StringUtil.substring("a  bcde", 1, 5)  = "  bcd"
	 * StringUtil.substring("abcde", 1, 3)    = "bcd"
	 * StringUtil.substring("abcde", 0, 7)    = "abcde"
	 * StringUtil.substring("abcde", 2, 17)   = "cde"
	 * StringUtil.substring("  abcde ", 0, 3) = "abc"
	 * StringUtil.substring("  abcde ", 3, 5) = "de"
	 * StringUtil.substring(12345, 2, 3)      = "345"
	 * </pre>
	 * 
	 * @param obj      (추출할 문자형, 숫자형 객체)
	 * @param startIdx (시작할 위치)
	 * @param len      (가져올 길이)
	 * @return String  (추출한 결과값)
	 */
	public static String substring(Object obj, int startIdx, int len) {
		return substring(obj, startIdx, len, true);
	}
	
	/**
	 * <p>문자열의 지정된 시작 인덱스에서부터 지정된 길이만큼 문자를 가져온다.</p>
	 * 
	 * <pre>
	 * StringUtil.substring(null, *, *, *)           = ""
	 * StringUtil.substring("", *, *, *)             = ""
	 * StringUtil.substring(*, *, -ve, *)            = ""
	 * StringUtil.substring("abcde", 5, *, *)        = ""
	 * StringUtil.substring("abcde", 0, 3, *)        = "abc"
	 * StringUtil.substring("abcde", -2, 3, *)       = "abc"
	 * StringUtil.substring("a bcde", 0, 3, *)       = "a b"
	 * StringUtil.substring("a  bcde", 1, 5, *)      = "  bcd"
	 * StringUtil.substring("abcde", 1, 3, *)        = "bcd"
	 * StringUtil.substring("abcde", 0, 7, *)        = "abcde"
	 * StringUtil.substring("abcde", 2, 17, *)       = "cde"
	 * StringUtil.substring("  abcde ", 0, 3, false) = "  a"
	 * StringUtil.substring("  abcde ", 0, 3, true)  = "abc"
	 * StringUtil.substring("  abcde ", 3, 5, false) = "bcde "
	 * StringUtil.substring("  abcde ", 3, 5, true)  = "de"
	 * StringUtil.substring(12345, 2, 3, *)          = "345"
	 * </pre>
	 * 
	 * @param obj      (추출할 문자형, 숫자형 객체)
	 * @param startIdx (시작할 위치)
	 * @param len      (가져올 길이)
	 * @param isTrim   (문자 좌우 공백제거 여부)
	 * @return String  (추출한 결과값)
	 */
	public static String substring(Object obj, int startIdx, int len, boolean isTrim) {
		String str = toString(obj);
		if (isTrim) {
			str = clean(str);
		}
		if (startIdx > str.length() || len < 0) {
			return "";
		}
		if (startIdx < 0) {
			startIdx = 0;
		}
		if (str.length() <= (startIdx + len)) {
			return str.substring(startIdx);
		} else {
			return str.substring(startIdx, startIdx + len);
		}
	}
	
	/**
	 * <p>해당 값에 검색어가 포함된 위치(index 번호)를 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.indexOf(null, *)      = -1
	 * StringUtil.indexOf(*, null)      = -1
	 * StringUtil.indexOf("", "")       = 0
	 * StringUtil.indexOf("abc", "")    = 0
	 * StringUtil.indexOf(12345, "a")   = -1
	 * StringUtil.indexOf("abc12", "a") = 0
	 * StringUtil.indexOf("abcaa", "a") = 0
	 * StringUtil.indexOf("abc12", "b") = 1
	 * StringUtil.indexOf("12345", "3") = 2
	 * StringUtil.indexOf(12345, "3")   = 2
	 * StringUtil.indexOf(12345, 3)     = 2
	 * </pre>
	 * 
	 * @param obj     (체크할 문자형, 숫자형 객체)
	 * @param keyword (검색어 문자형, 숫자형 객체)
	 * @return int    (검색어 포함된 위치)
	 */
	public static int indexOf(Object obj, Object keyword) {
		if (obj == null || keyword == null) {
			return -1;
		}
		return toString(obj).indexOf(toString(keyword));
	}
	
	/**
	 * <p>문자, 숫자, Collection, Map, Iterator, Enumeration, Array의 길이를 구해서 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.length(null)          = 0
	 * StringUtil.length("")            = 0
	 * StringUtil.length("   ")         = 3
	 * StringUtil.length(" abc123")     = 7
	 * StringUtil.length(1234567)       = 7
	 * StringUtil.length(Collection)    = Collection.size();
	 * StringUtil.length(Map)           = Map.size();
	 * StringUtil.length(Iterator)      = count
	 * StringUtil.length(Enumeration)   = count
	 * StringUtil.length(new int[3])    = 3
	 * StringUtil.length(new String[7]) = 7
	 * </pre>
	 * 
	 * @param obj  (체크할 객체)
	 * @return int (객체의 길이)
	 * @throws IllegalArgumentException 오류
	 */
	public static int length(Object obj) throws IllegalArgumentException {
		if (obj == null) {
			return 0;
		}
		
		if (obj instanceof String || obj instanceof Number) {
			return toString(obj).length();
		}
		if (obj instanceof Collection) {
			return ((Collection<?>)obj).size();
		}
		if (obj instanceof Map) {
			return ((Map<?, ?>)obj).size();
		}
		
		if (obj instanceof Iterator) {
			Iterator<?> iter = (Iterator<?>) obj;
			int count = 0;
			while (iter.hasNext()) {
				count++;
				iter.next();
			}
			return count;
		}
		if (obj instanceof Enumeration) {
			Enumeration<?> enume = (Enumeration<?>) obj;
			int count = 0;
			while (enume.hasMoreElements()) {
				count++;
				enume.nextElement();
			}
			return count;
		}
		
		try {
			return Array.getLength(obj);
		} catch (IllegalArgumentException e) {}
		
		throw new IllegalArgumentException("길이를 구할 수 없는 객체입니다.");
	}
	
	/**
	 * <p>해당 값을 구분자로 split 하여 리턴한다.</P>
	 * 
	 * <pre>
	 * StringUtil.split(null, *)           = new String[0]
	 * StringUtil.split("aa|bb|cc", null)  = {"aa|bb|cc"}
	 * StringUtil.split("aa|bb|cc", "")    = {"aa|bb|cc"}
	 * StringUtil.split("aa|bb|cc", "/")   = {"aa|bb|cc"}
	 * StringUtil.split("aa|bb|cc", "|")   = {"aa", "bb", "cc"}
	 * StringUtil.split("|aa|bb|cc|", "|") = {"aa", "bb", "cc"}
	 * StringUtil.split("aa|||bb|cc", "|") = {"aa", "bb", "cc"}
	 * StringUtil.split("aa1bb1cc", 1)     = {"aa", "bb", "cc"}
	 * StringUtil.split(11722733, 7)       = {"11", "22", "33"}
	 * StringUtil.split(11722733755, 7)    = {"11", "22", "33", "55"}
	 * </pre>
	 * 
	 * @param obj       (split할 문자형, 숫자형 객체)
	 * @param delim     (구분자)
	 * @return String[] (split 결과)
	 */
	public static String[] split(Object obj, Object delim) {
		if (isEmpty(obj)) {
			return new String[0];
		}
		StringTokenizer st = new StringTokenizer(toString(obj), toString(delim));
		String[] strArr = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			strArr[i++] = st.nextToken();
		}
		return strArr;
	}
	
	/**
	 * <p>문자형, 숫자형 배열을 "|" 구분자로 연결한 문자로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.join(null)            = ""
	 * StringUtil.join({"aaa", "bbb"})  = "aaa|bbb"
	 * StringUtil.join({111, 222, 333}) = "111|222|333"
	 * </pre>
	 * 
	 * @param obj     (문자형, 숫자형 배열)
	 * @return String (변경후 문자)
	 */
	public static String join(Object[] obj) {
		return join(obj, "|");
	}
	
	/**
	 * <p>문자형, 숫자형 배열을 해당 구분자로 연결한 문자로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.join(null, *)              = ""
	 * StringUtil.join({"aaa", "bbb"}, null) = "aaabbb"
	 * StringUtil.join({"aaa", "bbb"}, "")   = "aaabbb"
	 * StringUtil.join({"aaa", "bbb"}, ",")  = "aaa,bbb"
	 * StringUtil.join({"aaa", "bbb"}, "|")  = "aaa|bbb"
	 * StringUtil.join({"aaa", "bbb"}, 7)    = "aaa7bbb"
	 * StringUtil.join({111, 222, 333}, "/") = "111/222/333"
	 * StringUtil.join({111, 222, 333}, 7)   = "11172227333"
	 * </pre>
	 * 
	 * @param obj     (문자형, 숫자형 배열)
	 * @param delim   (구분자)
	 * @return String (변경후 문자)
	 */
	public static String join(Object[] obj, Object delim) {
		if (obj == null || obj.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Object str: obj) {
			if (sb.length() > 0) {
				sb.append(toString(delim));
			}
			sb.append(toString(str));
		}
		return sb.toString();
	}
	
	/**
	 * <p>해당 값의 특정 문자를 새로운 값으로 전체 변경한다.</P>
	 * 
	 * <pre>
	 * StringUtil.replaceAll(null, *, *)         = ""
	 * StringUtil.replaceAll("", *, *)           = ""
	 * StringUtil.replaceAll("abc", null, *)     = "abc"
	 * StringUtil.replaceAll("abc", "", *)       = "abc"
	 * StringUtil.replaceAll("abc", *, null)     = "abc"
	 * StringUtil.replaceAll("   ", "b", "ee")   = "   "
	 * StringUtil.replaceAll(" abc ", "b", "ee") = " aeec "
	 * StringUtil.replaceAll(117, "1", "33")     = "33337"
	 * StringUtil.replaceAll(117, 7, 33)         = "1133"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형, 숫자형 객체)
	 * @param oldObj  (이전 문자형, 숫자형 객체)
	 * @param newObj  (신규 문자형, 숫자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String replaceAll(Object obj, Object oldObj, Object newObj) {
		if (isEmpty(obj)) {
			return "";
		}
		if (isEmpty(oldObj) || newObj == null) {
			return toString(obj);
		}
		String str = toString(obj);
		String oldStr = toString(oldObj);
		String newStr = toString(newObj);
		int i = str.lastIndexOf(oldStr);
		if (i < 0) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (i >= 0) {
			sb.replace(i, (i + oldStr.length()), newStr);
			i = str.lastIndexOf(oldStr, i - 1);
		}
		return sb.toString();
	}
	
	/**
	 * <p>해당 값의 공백 제거 후 특정 검색어로 시작하는지 여부를 리턴한다.</P>
	 * 
	 * <pre>
	 * StringUtil.startsWith(null, *)          = false
	 * StringUtil.startsWith(*, null)          = false
	 * StringUtil.startsWith(" aabbcc", " aa") = false
	 * StringUtil.startsWith("aabbcc", "aaB")  = false
	 * StringUtil.startsWith("aabbcc", "aac")  = false
	 * StringUtil.startsWith(12345, 234)       = false
	 * StringUtil.startsWith("", "")           = true
	 * StringUtil.startsWith(*, "")            = true
	 * StringUtil.startsWith("  aabbcc", "aa") = true
	 * StringUtil.startsWith("aabbcc", "aab")  = true
	 * StringUtil.startsWith("11aabb", 11)     = true
	 * StringUtil.startsWith(12345, 123)       = true
	 * </pre>
	 * 
	 * @param obj      (체크할 문자형, 숫자형 객체)
	 * @param keyword  (문자형, 숫자형 검색어)
	 * @return boolean (검색어로 시작하는지 여부)
	 */
	public static boolean startsWith(Object obj, Object keyword) {
		return startsWith(obj, keyword, true);
	}
	
	/**
	 * <p>해당 값이 특정 검색어로 시작하는지 여부를 리턴한다.</P>
	 * 
	 * <pre>
	 * StringUtil.startsWith(null, *, *)              = false
	 * StringUtil.startsWith(*, null, *)              = false
	 * StringUtil.startsWith("aabbcc", "aaB", *)      = false
	 * StringUtil.startsWith("aabbcc", "aac", *)      = false
	 * StringUtil.startsWith(12345, 234, *)           = false
	 * StringUtil.startsWith(" aabbcc", "aa", false)  = false
	 * StringUtil.startsWith(" aabbcc", " aa", true)  = false
	 * StringUtil.startsWith("", "", *)               = true
	 * StringUtil.startsWith(*, "", *)                = true
	 * StringUtil.startsWith("aabbcc", "aab", *)      = true
	 * StringUtil.startsWith("11aabb", 11, *)         = true
	 * StringUtil.startsWith(12345, 123, *)           = true
	 * StringUtil.startsWith(" aabbcc", "aa", true)   = true
	 * StringUtil.startsWith(" aabbcc", " aa", false) = true
	 * </pre>
	 * 
	 * @param obj      (체크할 문자형, 숫자형 객체)
	 * @param keyword  (문자형, 숫자형 검색어)
	 * @param isTrim   (체크할 문자 좌우 공백제거 여부)
	 * @return boolean (검색어로 시작하는지 여부)
	 */
	public static boolean startsWith(Object obj, Object keyword, boolean isTrim) {
		return startsWith(obj, keyword, isTrim, null);
	}
	
	/**
	 * <p>해당 값이 구분자 또는 배열로 되어있는 검색어로 하나라도 시작하는지 여부를 리턴한다.</P>
	 * 
	 * <pre>
	 * StringUtil.startsWith(null, *, *, *)                   = false
	 * StringUtil.startsWith(*, null, *, *)                   = false
	 * StringUtil.startsWith("aabbcc", "aaB", *, *)           = false
	 * StringUtil.startsWith("aabbcc", "aac", *, *)           = false
	 * StringUtil.startsWith(12345, 234, *, *)                = false
	 * StringUtil.startsWith(" aabbcc", "aa", false, *)       = false
	 * StringUtil.startsWith(" aabbcc", " aa", true, *)       = false
	 * StringUtil.startsWith("aabbcc", "ab|bb|cc", *, "[|]")  = false
	 * StringUtil.startsWith("aabbcc", "ab/bb/cc", *, "/")    = false
	 * StringUtil.startsWith("aabbcc", "ab\\bb\\cc", *, "\\") = false
	 * StringUtil.startsWith("abc123", {"ac", "b1"}, *, null) = false
	 * StringUtil.startsWith("", "", *, *)                    = true
	 * StringUtil.startsWith(*, "", *, *)                     = true
	 * StringUtil.startsWith("aabbcc", "aab", *, null)        = true
	 * StringUtil.startsWith("11aabb", 11, *, null)           = true
	 * StringUtil.startsWith(12345, 123, *, null)             = true
	 * StringUtil.startsWith(" aabbcc", "aa", true, *)        = true
	 * StringUtil.startsWith(" aabbcc", " aa", false, *)      = true
	 * StringUtil.startsWith("aabbcc", "ab|aa|cc", *, "[|]")  = true
	 * StringUtil.startsWith("aabbcc", "ab/aa/cc", *, "/")    = true
	 * StringUtil.startsWith("aabbcc", "ab\\aa\\cc", *, "\\") = true
	 * StringUtil.startsWith("aabbcc", "ab|c|aab", *, "[|]")  = true
	 * StringUtil.startsWith("abc123", {"ac", "ab"}, *, null) = true
	 * </pre>
	 * 
	 * @param obj      (체크할 문자형, 숫자형 객체)
	 * @param keyword  (구분자 또는 배열로 되어있는 검색어)
	 * @param isTrim   (체크할 문자 좌우 공백제거 여부)
	 * @param delim    (구분자)
	 * @return boolean (검색어로 시작하는지 여부)
	 */
	public static boolean startsWith(Object obj, Object keyword, boolean isTrim, Object delim) {
		if (obj == null || keyword == null) {
			return false;
		}
		String str = toString(obj);
		if (isTrim) {
			str = clean(str);
		}
		Object[] keywordArr = null;
		if (keyword instanceof Number || keyword instanceof String) {
			if (isEmpty(delim)) {
				keywordArr = new String[1];
				keywordArr[0] = toString(keyword);
			} else {
				keywordArr = toString(keyword).split(toString(delim).replaceAll("\\\\", "\\\\\\\\"), -1);
			}
		} else if (keyword instanceof int[]) {
			keywordArr = ObjectUtil.toStringArr((int[]) keyword);
		} else if (keyword.getClass().isArray()) {
			keywordArr = (Object[]) keyword;
		}
		if (keywordArr != null) {
			for (Object val: keywordArr) {
				if (str.startsWith(toString(val))) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * <p>해당 값의 공백 제거 후 특정 검색어로 끝나는지 여부를 리턴한다.</P>
	 * 
	 * <pre>
	 * StringUtil.endsWith(null, *)          = false
	 * StringUtil.endsWith(*, null)          = false
	 * StringUtil.endsWith("aabbcc ", "cc ") = false
	 * StringUtil.endsWith("aabbcc", "Bcc")  = false
	 * StringUtil.endsWith("aabbcc", "acc")  = false
	 * StringUtil.endsWith(12345, 123)       = false
	 * StringUtil.endsWith("", "")           = true
	 * StringUtil.endsWith(*, "")            = true
	 * StringUtil.endsWith("aabbcc", "bcc")  = true
	 * StringUtil.endsWith("aabbcc  ", "cc") = true
	 * StringUtil.endsWith("aabb11", 11)     = true
	 * StringUtil.endsWith(12345, 345)       = true
	 * </pre>
	 * 
	 * @param obj      (체크할 문자형, 숫자형 객체)
	 * @param keyword  (문자형, 숫자형 검색어)
	 * @return boolean (검색어로 끝나는지 여부)
	 */
	public static boolean endsWith(Object obj, Object keyword) {
		return endsWith(obj, keyword, true);
	}
	
	/**
	 * <p>해당 값이 특정 검색어로 끝나는지 여부를 리턴한다.</P>
	 * 
	 * <pre>
	 * StringUtil.endsWith(null, *, *)              = false
	 * StringUtil.endsWith(*, null, *)              = false
	 * StringUtil.endsWith("aabbcc", "Bcc", *)      = false
	 * StringUtil.endsWith("aabbcc", "acc", *)      = false
	 * StringUtil.endsWith(12345, 123, *)           = false
	 * StringUtil.endsWith("aabbcc ", "cc", false)  = false
	 * StringUtil.endsWith("aabbcc ", "cc ", true)  = false
	 * StringUtil.endsWith("", "", *)               = true
	 * StringUtil.endsWith(*, "", *)                = true
	 * StringUtil.endsWith("aabbcc", "bcc", *)      = true
	 * StringUtil.endsWith("aabb11", 11, *)         = true
	 * StringUtil.endsWith(12345, 345, *)           = true
	 * StringUtil.endsWith("aabbcc ", "cc", true)   = true
	 * StringUtil.endsWith("aabbcc ", "cc ", false) = true
	 * </pre>
	 * 
	 * @param obj      (체크할 문자형, 숫자형 객체)
	 * @param keyword  (문자형, 숫자형 검색어)
	 * @param isTrim   (체크할 문자 좌우 공백제거 여부)
	 * @return boolean (검색어로 끝나는지 여부)
	 */
	public static boolean endsWith(Object obj, Object keyword, boolean isTrim) {
		return endsWith(obj, keyword, isTrim, null);
	}
	
	/**
	 * <p>해당 값이 구분자 또는 배열로 되어있는 검색어로 하나라도 끝나는지 여부를 리턴한다.</P>
	 * 
	 * <pre>
	 * StringUtil.endsWith(null, *, *, *)                     = false
	 * StringUtil.endsWith(*, null, *, *)                     = false
	 * StringUtil.endsWith("aabbcc", "Bcc", *, *)             = false
	 * StringUtil.endsWith("aabbcc", "acc", *, *)             = false
	 * StringUtil.endsWith(12345, 123, *, *)                  = false
	 * StringUtil.endsWith("aabbcc ", "cc", false, *)         = false
	 * StringUtil.endsWith("aabbcc ", "cc ", true, *)         = false
	 * StringUtil.endsWith("aabbcc", "aa|bb|bc", *, "[|]")    = false
	 * StringUtil.endsWith("aabbcc", "aa/bb/bc", *, "/")      = false
	 * StringUtil.endsWith("aabbcc", "aa\\bb\\bc", *, "\\")   = false
	 * StringUtil.endsWith("abc123", {"abc", "c12"}, *, null) = false
	 * StringUtil.endsWith("", "", *, *)                      = true
	 * StringUtil.endsWith(*, "", *, *)                       = true
	 * StringUtil.endsWith("aabbcc", "bcc", *, *)             = true
	 * StringUtil.endsWith("aabb11", 11, *, *)                = true
	 * StringUtil.endsWith(12345, 345, *, *)                  = true
	 * StringUtil.endsWith("aabbcc ", "cc", true, *)          = true
	 * StringUtil.endsWith("aabbcc ", "cc ", false, *)        = true
	 * StringUtil.endsWith("aabbcc", "aa|bb|cc", *, "[|]")    = true
	 * StringUtil.endsWith("aabbcc", "aa/bb/cc", *, "/")      = true
	 * StringUtil.endsWith("aabbcc", "aa\\bb\\cc", *, "\\")   = true
	 * StringUtil.endsWith("aabbcc", "ab|c|aab", *, "[|]")    = true
	 * StringUtil.endsWith("abc123", {"abc", "123"}, *, null) = true
	 * </pre>
	 * 
	 * @param obj      (체크할 문자형, 숫자형 객체)
	 * @param keyword  (구분자 또는 배열로 되어있는 검색어)
	 * @param isTrim   (체크할 문자 좌우 공백제거 여부)
	 * @param delim    (구분자)
	 * @return boolean (검색어로 끝나는지 여부)
	 */
	public static boolean endsWith(Object obj, Object keyword, boolean isTrim, Object delim) {
		if (obj == null || keyword == null) {
			return false;
		}
		String str = toString(obj);
		if (isTrim) {
			str = clean(str);
		}
		Object[] keywordArr = null;
		if (keyword instanceof Number || keyword instanceof String) {
			if (isEmpty(delim)) {
				keywordArr = new String[1];
				keywordArr[0] = toString(keyword);
			} else {
				keywordArr = toString(keyword).split(toString(delim).replaceAll("\\\\", "\\\\\\\\"), -1);
			}
		} else if (keyword instanceof int[]) {
			keywordArr = ObjectUtil.toStringArr((int[]) keyword);
		} else if (keyword.getClass().isArray()) {
			keywordArr = (Object[]) keyword;
		}
		if (keywordArr != null) {
			String keyStr = "";
			int idx = -1;
			for (Object val: keywordArr) {
				keyStr = toString(val);
				idx = str.lastIndexOf(keyStr);
				if (idx != -1) {
					if (idx == 0 && keyStr.length() == 0) {
						return true;
					}
					if (idx == str.length() - keyStr.length()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * <p>체크할 두 문자의 공백 제거 후 대소문자 상관없이 두 문자가 일치하는지 여부를 리턴한다.</p>
	 * <p>java.lang.String.equalsIgnoreCase(String anotherString) 확장형으로 null도 비교한다.</p>
	 * 
	 * <pre>
	 * StringUtil.equalsIgnoreCase(null, "abc")      = false
	 * StringUtil.equalsIgnoreCase("abc", null)      = false
	 * StringUtil.equalsIgnoreCase(null, null)       = true
	 * StringUtil.equalsIgnoreCase("abc", "abc")     = true
	 * StringUtil.equalsIgnoreCase("abc", "ABC")     = true
	 * StringUtil.equalsIgnoreCase("abc", "  abc")   = true
	 * StringUtil.equalsIgnoreCase("  abc", "abc")   = true
	 * StringUtil.equalsIgnoreCase("  abc", " ABC ") = true
	 * </pre>
	 * 
	 * @param obj1     (체크할 문자형 객체1)
	 * @param obj2     (체크할 문자형 객체2)
	 * @return boolean (체크결과)
	 */
	public static boolean equalsIgnoreCase(Object obj1, Object obj2) {
		return equalsIgnoreCase(obj1, obj2, true);
	}
	
	/**
	 * <p>대소문자 상관없이 두 문자가 일치하는지 여부를 리턴한다.</p>
	 * <p>java.lang.String.equalsIgnoreCase(String anotherString) 확장형으로 null도 비교한다.</p>
	 * 
	 * <pre>
	 * StringUtil.equalsIgnoreCase(null, "abc", *)        = false
	 * StringUtil.equalsIgnoreCase("abc", null, *)        = false
	 * StringUtil.equalsIgnoreCase("abc", "  abc", false) = false
	 * StringUtil.equalsIgnoreCase("  abc", "abc", false) = false
	 * StringUtil.equalsIgnoreCase(null, null, *)         = true
	 * StringUtil.equalsIgnoreCase("abc", "abc", *)       = true
	 * StringUtil.equalsIgnoreCase("abc", "ABC", *)       = true
	 * StringUtil.equalsIgnoreCase("abc", "  abc", true)  = true
	 * StringUtil.equalsIgnoreCase("  abc", "abc", true)  = true
	 * StringUtil.equalsIgnoreCase("  abc", "  abc", *)   = true
	 * StringUtil.equalsIgnoreCase("  abc", "  ABC", *)   = true
	 * </pre>
	 * 
	 * @param obj1     (체크할 문자형 객체1)
	 * @param obj2     (체크할 문자형 객체2)
	 * @param isTrim   (체크할 두 문자 좌우 공백제거 여부)
	 * @return boolean (체크결과)
	 */
	public static boolean equalsIgnoreCase(Object obj1, Object obj2, boolean isTrim) {
		if (obj1 == null) {
			return obj2 == null;
		}
		String str1 = toString(obj1);
		String str2 = toString(obj2);
		if (isTrim) {
			str1 = clean(str1);
			str2 = clean(str2);
		}
		return str1.equalsIgnoreCase(str2);
	}
	
	/**
	 * <p>구분자 또는 배열로 되어있는 값에 특정 검색어와 일치 하는것이 있는지 여부를 리턴한다.</P>
	 * 
	 * <pre>
	 * StringUtil.equalsSplit(null, *, *)                   = false
	 * StringUtil.equalsSplit("", *, *)                     = false
	 * StringUtil.equalsSplit(*, null, *)                   = false
	 * StringUtil.equalsSplit("abcde", "", *)               = false
	 * StringUtil.equalsSplit("abcde", "abc", *)            = false
	 * StringUtil.equalsSplit("ab|cd|e", "a", "[|]")        = false
	 * StringUtil.equalsSplit("ab|cd|e", "abc", "[|]")      = false
	 * StringUtil.equalsSplit("ab#cd#e", "", "#")           = false
	 * StringUtil.equalsSplit("ab//cd/e", " ", "/")         = false
	 * StringUtil.equalsSplit({"ab", "cd", "e"}, "c", null) = false
	 * StringUtil.equalsSplit({12, 37, 89}, "13", null)     = false
	 * StringUtil.equalsSplit("abc", "abc", *)              = true
	 * StringUtil.equalsSplit("ab|cd|e", "ab", "[|]")       = true
	 * StringUtil.equalsSplit("ab|cd|e", "e", "[|]")        = true
	 * StringUtil.equalsSplit("ab#cd#e#", "", "#")          = true
	 * StringUtil.equalsSplit("ab##cd#e", "", "#")          = true
	 * StringUtil.equalsSplit("ab/ /cd/e", " ", "/")        = true
	 * StringUtil.equalsSplit("ab,e,  ", "  ", ",")         = true
	 * StringUtil.equalsSplit("ab,12,e", 12, ",")           = true
	 * StringUtil.equalsSplit({"ab", "cd", "e"}, "e", null) = true
	 * StringUtil.equalsSplit({12, 37, 89}, "37", null)     = true
	 * </pre>
	 * 
	 * @param obj      (구분자 또는 배열로 되어있는 체크할 값)
	 * @param keyword  (문자형, 숫자형 검색어)
	 * @param delim    (구분자)
	 * @return boolean (검색어 포함여부)
	 */
	public static boolean equalsSplit(Object obj, Object keyword, Object delim) {
		if (isEmpty(obj) || keyword == null) {
			return false;
		}
		Object[] objArr = null;
		if (obj instanceof Number || obj instanceof String) {
			if (isEmpty(delim)) {
				objArr = new String[1];
				objArr[0] = toString(obj);
			} else {
				objArr = toString(obj).split(toString(delim), -1);
			}
		} else if (obj instanceof int[]) {
			objArr = ObjectUtil.toStringArr((int[]) obj);
		} else if (obj.getClass().isArray()) {
			objArr = (Object[]) obj;
		}
		if (objArr != null) {
			for (Object val: objArr) {
				if (toString(val).equals(toString(keyword))) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * <p>해당 값이 특정 검색어를 포함하고 있는지 여부를 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.contains(null, *)        = false
	 * StringUtil.contains(*, null)        = false
	 * StringUtil.contains("", *)          = false
	 * StringUtil.contains(*, "")          = false
	 * StringUtil.contains("ab de", "  ")  = false
	 * StringUtil.contains("abcde", "cba") = false
	 * StringUtil.contains(12345, "543")   = false
	 * StringUtil.contains("abcde", "bcd") = true
	 * StringUtil.contains("abc12", 12)    = true
	 * StringUtil.contains("ab de", " ")   = true
	 * StringUtil.contains(12345, "234")   = true
	 * StringUtil.contains(12345, 123)     = true
	 * </pre>
	 * 
	 * @param obj      (체크할 문자형, 숫자형 객체)
	 * @param keyword  (문자형, 숫자형 검색어)
	 * @return boolean (검색어 포함여부)
	 */
	public static boolean contains(Object obj, Object keyword) {
		if (isEmpty(obj) || isEmpty(keyword)) {
			return false;
		}
		return toString(obj).indexOf(toString(keyword)) != -1;
	}
	
	/**
	 * <p>해당 값이 구분자 또는 배열로 되어있는 검색어를 하나라도 포함하고 있는지 여부를 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.containsArr(null, *, *)                   = false
	 * StringUtil.containsArr(*, null, *)                   = false
	 * StringUtil.containsArr("", *, *)                     = false
	 * StringUtil.containsArr(*, "", *)                     = false
	 * StringUtil.containsArr("abcde", "abd", *)            = false
	 * StringUtil.containsArr("abcde", "aa|bb|cc", "[|]")   = false
	 * StringUtil.containsArr("abcde", "aa|bbcc|", "[|]")   = false
	 * StringUtil.containsArr("abcde", "aa|||bbb", "[|]")   = false
	 * StringUtil.containsArr("ab de", "aa/  /bb", "/")     = false
	 * StringUtil.containsArr(12345, "d,54,a,c", ",")       = false
	 * StringUtil.containsArr("abc123", {"ac", "b1"}, null) = false
	 * StringUtil.containsArr("abc123", {13, 37}, null)     = false
	 * StringUtil.containsArr("abcde", "abc", *)            = true
	 * StringUtil.containsArr("abcde", "aa|bc|cc", "[|]")   = true
	 * StringUtil.containsArr("abcee", "aaa|bb|e", "[|]")   = true
	 * StringUtil.containsArr("ab de", " #aa#bb", "#")      = true
	 * StringUtil.containsArr("abd  ", "aa# #bb", "#")      = true
	 * StringUtil.containsArr("  abc", "aa/  /bb", "/")     = true
	 * StringUtil.containsArr(12345, "d,45,a,c", ",")       = true
	 * StringUtil.containsArr("abc123", {"ab", "b1"}, null) = true
	 * StringUtil.containsArr("abc123", {12, 37}, null)     = true
	 * </pre>
	 * 
	 * @param obj      (체크할 문자형, 숫자형 객체)
	 * @param keyword  (구분자 또는 배열로 되어있는 검색어)
	 * @param delim    (구분자)
	 * @return boolean (검색어 포함여부)
	 */
	public static boolean containsArr(Object obj, Object keyword, Object delim) {
		if (isEmpty(obj) || isEmpty(keyword)) {
			return false;
		}
		Object[] keywordArr = null;
		if (keyword instanceof Number || keyword instanceof String) {
			if (isEmpty(delim)) {
				keywordArr = new String[1];
				keywordArr[0] = toString(keyword);
			} else {
				keywordArr = toString(keyword).split(toString(delim), -1);
			}
		} else if (keyword instanceof int[]) {
			keywordArr = ObjectUtil.toStringArr((int[]) keyword);
		} else if (keyword.getClass().isArray()) {
			keywordArr = (Object[]) keyword;
		}
		if (keywordArr != null) {
			for (Object val: keywordArr) {
				if (contains(obj, val)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * <p>해당 값이 제한된 길이보다 클경우 지정된 위치마다 특정 문자 삽입후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.insertion(null, *, *, *)      = ""
	 * StringUtil.insertion("", *, *, *)        = ""
	 * StringUtil.insertion("   ", *, *, *)     = "  "
	 * StringUtil.insertion("abcde", *, 0, *)   = "abcde"
	 * StringUtil.insertion("abcde", *, 1, 5)   = "abcde"
	 * StringUtil.insertion("abcde", "|", 1, 0) = "a|b|c|d|e"
	 * StringUtil.insertion("abcde", "|", 2, 4) = "ab|cd|e"
	 * StringUtil.insertion("abcde", "|", 2, 5) = "abcde"
	 * StringUtil.insertion("abcde", "|", 5, 0) = "abcde"
	 * StringUtil.insertion(12345, "|", 2, 0)   = "12|34|5"
	 * StringUtil.insertion(11111, 7, 2, 0)     = "1273475"
	 * </pre>
	 * 
	 * @param obj         (변경할 문자형, 숫자형 객체)
	 * @param insertObj   (삽입할 문자형, 숫자형 객체)
	 * @param insertIdx   (삽입할 위치)
	 * @param limitLen    (제한된 길이)
	 * @return String     (변경후 문자)
	 */
	public static String insertion(Object obj, Object insertObj, int insertIdx, int limitLen) {
		String str = toString(obj);
		if (isBlank(str) || insertIdx < 1 || str.length() <= limitLen) {
			return str;
		}
		int idx = 0;
		String insertStr = toString(insertObj);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			if (insertIdx == idx) {
				sb.append(insertStr);
				idx = 0;
			}
			sb.append(str.charAt(i));
			++idx;
		}
		return sb.toString();
	}
	
	/**
	 * <p>해당 값을 소문자로 변경한다.</P>
	 * 
	 * <pre>
	 * StringUtil.toLowerCase(null)  = ""
	 * StringUtil.toLowerCase("")    = ""
	 * StringUtil.toLowerCase("   ") = "  "
	 * StringUtil.toLowerCase("aBC") = "abc"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String toLowerCase(Object obj) {
		if (obj == null) {
			return "";
		}
		return toString(obj).toLowerCase();
	}
	
	/**
	 * <p>해당 값을 대문자로 변경한다.</P>
	 * 
	 * <pre>
	 * StringUtil.toUpperCase(null)  = ""
	 * StringUtil.toUpperCase("")    = ""
	 * StringUtil.toUpperCase("   ") = "  "
	 * StringUtil.toUpperCase("aBc") = "ABC"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String toUpperCase(Object obj) {
		if (obj == null) {
			return "";
		}
		return toString(obj).toUpperCase();
	}
	
	/**
	 * <p>'_' 가 포함되어 있는 문자열을 Camel Case 표기법으로 변경후 리턴한다.</p>
	 * <p>'_' 가 포함되어 있지 않고 첫 문자가 대문자인 경우 전체를 소문자로 변경한다.</p>
	 * <p>※ Camel Case : 둘 이상의 단어를 붙여서 쓸때 첫 문자는 소문자로 시작하고 새로 시작하는 단어는 대문자로 표기하는 방식.</p>
	 * 
	 * <pre>
	 * StringUtil.toCamelCase(null)      = ""
	 * StringUtil.toCamelCase("")        = ""
	 * StringUtil.toCamelCase("   ")     = ""
	 * StringUtil.toCamelCase("abc")     = "abc"
	 * StringUtil.toCamelCase("aBc")     = "aBc"
	 * StringUtil.toCamelCase("AbC")     = "abc"
	 * StringUtil.toCamelCase("abc_def") = "abcDef"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String toCamelCase(Object obj) {
		if (isBlank(obj)) {
			return "";
		}
		String str = clean(obj);
		if (str.indexOf('_') < 0 && Character.isLowerCase(str.charAt(0))) {
			return str;
		}
		StringBuilder sb = new StringBuilder();
		boolean nextUpper = false;
		for (int i = 0; i < str.length(); i++) {
			char currentChar = str.charAt(i);
			if (currentChar == '_') {
				nextUpper = true;
			} else {
				if (nextUpper) {
					sb.append(Character.toUpperCase(currentChar));
					nextUpper = false;
				} else {
					sb.append(Character.toLowerCase(currentChar));
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * <p>쿼리에 사용되는 문자를 표준형("''")으로 변경후 리턴한다.</P>
	 * 
	 * <pre>
	 * StringUtil.toQueryStr(null)   = ""
	 * StringUtil.toQueryStr("")     = ""
	 * StringUtil.toQueryStr("   ")  = "   "
	 * StringUtil.toQueryStr("abc")  = "abc"
	 * StringUtil.toQueryStr("ab'c") = "ab''c"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String toQueryStr(Object obj) {
		if (isEmpty(obj)) {
			return "";
		}
		String str = toString(obj);
		return str.replaceAll("'", "''");
	}
	
	/**
	 * <p>작은따옴표를 ASCII 코드로 변경 후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.encodeSingleMark(null)   = ""
	 * StringUtil.encodeSingleMark("")     = ""
	 * StringUtil.encodeSingleMark("   ")  = ""
	 * StringUtil.encodeSingleMark("abc")  = "abc"
	 * StringUtil.encodeSingleMark("ab'c") = "ab&amp;#39;c"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String encodeSingleMark(Object obj) {
		if (isBlank(obj)) {
			return "";
		}
		return toString(obj).replaceAll("\'", "&#39;");
	}
	
	/**
	 * <p>쌍따옴표를 ASCII 코드로 변경 후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.encodeDoubleMark(null)   = ""
	 * StringUtil.encodeDoubleMark("")     = ""
	 * StringUtil.encodeDoubleMark("   ")  = ""
	 * StringUtil.encodeDoubleMark("abc")  = "abc"
	 * StringUtil.encodeDoubleMark("ab"c") = "ab&amp;#34;c"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String encodeDoubleMark(Object obj) {
		if (isBlank(obj)) {
			return "";
		}
		return toString(obj).replaceAll("\"", "&#34;");
	}
	
	/**
	 * <p>작은따옴표, 쌍따옴표를 ASCII 코드로 변경 후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.encodeMark(null)   = ""
	 * StringUtil.encodeMark("")     = ""
	 * StringUtil.encodeMark("   ")  = ""
	 * StringUtil.encodeMark("abc")  = "abc"
	 * StringUtil.encodeMark("ab'c") = "ab&amp;#39;c"
	 * StringUtil.encodeMark("ab"c") = "ab&amp;#34;c"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String encodeMark(Object obj) {
		if (isBlank(obj)) {
			return "";
		}
		String str = toString(obj);
		str = encodeSingleMark(str);
		str = encodeDoubleMark(str);
		return str;
	}
	
	/**
	 * <p>해당 값을 지정된 길이 만큼 자르고 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.cutStr(null, *)  = ""
	 * StringUtil.cutStr("", *)    = ""
	 * StringUtil.cutStr("   ", *) = ""
	 * StringUtil.cutStr("abc", 3) = "abc"
	 * StringUtil.cutStr("abc", 2) = "ab"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형, 숫자형 객체)
	 * @param len     (가져올 문자길이)
	 * @return String (변경후 문자)
	 */
	public static String cutStr(Object obj, int len) {
		if (isBlank(obj)) {
			return "";
		}
		String str = toString(obj);
		if (str.length() > len) {
			str = str.substring(0, len);
		}
		return str;
	}
	
	/**
	 * <p>해당 값을 지정된 길이 만큼 자르고 접미사 처리후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.cutStr(null, *, *)      = ""
	 * StringUtil.cutStr("", *, *)        = ""
	 * StringUtil.cutStr("   ", *, *)     = ""
	 * StringUtil.cutStr("abc", 3, *)     = "abc"
	 * StringUtil.cutStr("abc", 2, "...") = "ab..."
	 * StringUtil.cutStr("abc", 2, 777)   = "ab777"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형, 숫자형 객체)
	 * @param len     (가져올 문자길이)
	 * @param suffix  (접미사 문자형, 숫자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String cutStr(Object obj, int len, Object suffix) {
		if (isBlank(obj)) {
			return "";
		}
		String str = toString(obj);
		if (str.length() > len) {
			str = str.substring(0, len) + toString(suffix);
		}
		return str;
	}
	
	/**
	 * <p>해당 값을 지정된 길이 만큼 자르고 "..." 처리후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.cutTitle(null, *)  = ""
	 * StringUtil.cutTitle("", *)    = ""
	 * StringUtil.cutTitle("   ", *) = ""
	 * StringUtil.cutTitle("abc", 3) = "abc"
	 * StringUtil.cutTitle("abc", 2) = "ab..."
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형, 숫자형 객체)
	 * @param len     (가져올 문자길이)
	 * @return String (변경후 문자)
	 */
	public static String cutTitle(Object obj, int len) {
		return cutStr(obj, len, "...");
	}
	
	/**
	 * <p>해당 값에서 특정 문자가 몇개인지 체크후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.countMatches(null, *)    = 0
	 * StringUtil.countMatches("", *)      = 0
	 * StringUtil.countMatches(*, null)    = 0
	 * StringUtil.countMatches(*, "")      = 0
	 * StringUtil.countMatches("abb1", 1)  = 1
	 * StringUtil.countMatches("abb", "b") = 2
	 * StringUtil.countMatches(1237377, 7) = 3
	 * </pre>
	 * 
	 * @param obj     (검색할 문자형, 숫자형 객체)
	 * @param keyword (문자형, 숫자형 검색어)
	 * @return int    (메칭되는 수)
	 */
	public static int countMatches(Object obj, Object keyword) {
		if (isEmpty(obj) || isEmpty(keyword)) {
			return 0;
		}
		int count = 0;
		int idx = 0;
		String keywordStr = toString(keyword);
		while ((idx = toString(obj).indexOf(keywordStr, idx)) != -1) {
			count++;
			idx += keywordStr.length();
		}
		return count;
	}
	
	/**
	 * <p>해당 값의 byte 길이를 Application 기본 캐릭터셋으로 계산해서 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.getByteLen(null)   = 0
	 * StringUtil.getByteLen("")     = 0
	 * StringUtil.getByteLen("123")  = 3
	 * StringUtil.getByteLen("1ab")  = 3
	 * StringUtil.getByteLen("아린") = 4 (기본 캐릭터셋 : EUC-KR)
	 * StringUtil.getByteLen("아린") = 6 (기본 캐릭터셋 : UTF-8)
	 * </pre>
	 * 
	 * @param obj  (계산할 문자형, 숫자형 객체)
	 * @return int (byte 수)
	 */
	public static int getByteLen(Object obj) {
		return getByteLen(obj, Const.ENCODING_TYPE);
	}
	
	/**
	 * <p>해당 값의 byte 길이를 특정 캐릭터셋으로 계산해서 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.getByteLen(null, *)            = 0
	 * StringUtil.getByteLen("", *)              = 0
	 * StringUtil.getByteLen("   ", *)           = 3
	 * StringUtil.getByteLen("12ab", *)          = 4
	 * StringUtil.getByteLen("아린1a", "EUC-KR") = 6
	 * StringUtil.getByteLen("아린1a", "UTF-8")  = 8
	 * </pre>
	 * 
	 * @param obj     (계산할 문자형, 숫자형 객체)
	 * @param charset (캐릭터셋)
	 * @return int    (byte 수)
	 */
	public static int getByteLen(Object obj, String charset) {
		if (isEmpty(obj)) {
			return 0;
		}
		try {
			return toString(obj).getBytes(charset).length;
		} catch (UnsupportedEncodingException e) {
			logger.error(ExceptionUtil.addMessage(e, "지원하지 않는 캐릭터셋 입니다."));
			return 0;
		}
	}
	
	/**
	 * <p>해당 값 앞에 0을 붙여서 원하는 길이의 문자를 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.prefixZero(null, *)  = ""
	 * StringUtil.prefixZero("", *)    = ""
	 * StringUtil.prefixZero("   ", *) = ""
	 * StringUtil.prefixZero("123", 2) = "123"
	 * StringUtil.prefixZero("123", 3) = "123"
	 * StringUtil.prefixZero("123", 5) = "00123"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형, 숫자형 객체)
	 * @param len     (가져올 길이)
	 * @return String (변경후 값)
	 */
	public static String prefixZero(Object obj, int len) {
		if (isBlank(obj)) {
			return "";
		}
		String str = clean(obj);
		String zeroNum = "";
		if (str.length() < len) {
			for (int i = 0; i < (len - str.length()); i++) {
				zeroNum += "0";
			}
		}
		return zeroNum + str;
	}
	
	/**
	 * <p>해당 값에 대괄호("[ ]")를 추가해서 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.addBracket(null)    = ""
	 * StringUtil.addBracket("")      = ""
	 * StringUtil.addBracket("   ")   = ""
	 * StringUtil.addBracket("abc")   = "[abc]"
	 * StringUtil.addBracket(" abc ") = "[abc]"
	 * StringUtil.addBracket(12345)   = "[12345]"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형, 숫자형 객체)
	 * @return String (대괄호 추가후 값)
	 */
	public static String addBracket(Object obj) {
		return addBracket(obj, "[]");
	}
	
	/**
	 * <p>해당 값에 특정 괄호 유형을 추가해서 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.addBracket(null, *)       = ""
	 * StringUtil.addBracket("", *)         = ""
	 * StringUtil.addBracket("   ", *)      = ""
	 * StringUtil.addBracket("abc", "()")   = "(abc)"
	 * StringUtil.addBracket("abc", "{}")   = "{abc}"
	 * StringUtil.addBracket("abc", "[]")   = "[abc]"
	 * StringUtil.addBracket("abc", "{@literal <>}")   = "{@literal <abc>}"
	 * StringUtil.addBracket("abc", "##")   = "abc"
	 * StringUtil.addBracket(" abc ", "[]") = "[abc]"
	 * StringUtil.addBracket(12345, "[]")   = "[12345]"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형, 숫자형 객체)
	 * @param bracket (괄호 유형)
	 * @return String (괄호 추가후 값)
	 */
	public static String addBracket(Object obj, String bracket) {
		if (isBlank(obj)) {
			return "";
		}
		String str = clean(obj);
		if ("()".equals(bracket)) {
			return "(" + str + ")";
		} else if ("{}".equals(bracket)) {
			return "{" + str + "}";
		} else if ("[]".equals(bracket)) {
			return "[" + str + "]";
		} else if ("<>".equals(bracket)) {
			return "<" + str + ">";
		}
		return str;
	}
	
	/**
	 * <p>해당 한글 단어에 맞는 조사를 구해서 리턴한다.</p>
	 * <p>1. 종성에 받침이 있는 경우 '은/이/을/과'</p>
	 * <p>2. 종성에 받침이 없는 경우 '는/가/를/와'</p>
	 * <p>3. 종성에 받침이 없거나 'ㄹ' 인경우 '로' 그 외는 '으로'</p>
	 * 
	 * <pre>
	 * StringUtil.getJosa(null, *)               = ""
	 * StringUtil.getJosa("", *)                 = ""
	 * StringUtil.getJosa("   ", *)              = ""
	 * StringUtil.getJosa(*, null)               = ""
	 * StringUtil.getJosa(*, "")                 = ""
	 * StringUtil.getJosa(*, "   ")              = ""
	 * StringUtil.getJosa(*, "은/가")            = ""
	 * StringUtil.getJosa("수박", "은/는")       = "은"
	 * StringUtil.getJosa("사과", "은/는")       = "는"
	 * StringUtil.getJosa("수박", "이/가")       = "이"
	 * StringUtil.getJosa("사과", "이/가")       = "가"
	 * StringUtil.getJosa("수박", "을/를")       = "을"
	 * StringUtil.getJosa("사과", "을/를")       = "를"
	 * StringUtil.getJosa("수박", "과/와")       = "과"
	 * StringUtil.getJosa("사과", "과/와")       = "와"
	 * StringUtil.getJosa("수박", "으로/로")     = "으로"
	 * StringUtil.getJosa("사과", "으로/로")     = "로"
	 * StringUtil.getJosa("날짜 1", "은/는")     = "은"
	 * StringUtil.getJosa("날짜 2", "은/는")     = "는"
	 * StringUtil.getJosa("과일 (귤)", "은/는")  = "은"
	 * StringUtil.getJosa("과일 (배)", "은/는")  = "는"
	 * StringUtil.getJosa("과일 price", "이/가") = "이(가)"
	 * </pre>
	 * 
	 * @param wordObj   (조사를 붙일 단어 문자열 객체)
	 * @param formatObj (조사 포멧 문자열 객체)
	 * @return String   (조사)
	 */
	public static String getJosa(Object wordObj, Object formatObj) {
		if (isBlank(wordObj)) {
			return "";
		}
		String[] formatArr = {"은/는", "이/가", "을/를", "과/와", "으로/로"};
		List<String> formatList = new ArrayList<String>(Arrays.asList(formatArr));
		if (formatList.indexOf(formatObj) >= 0) {
			String wordStr = toString(wordObj);
			String formatStr = toString(formatObj);
			// 단어의 마지막 문자를 구한다.
			char lastChar = ' ';
			for (int i = wordStr.length() - 1; i >= 0; i--) {
				lastChar = wordStr.charAt(i);
				if (toString(lastChar).matches("[0-9]")) {
					char[] numberArr = {'영', '일', '이', '삼', '사', '오', '육', '칠', '팔', '구'};
					lastChar = numberArr[Integer.parseInt(toString(lastChar))];
				}
				
				if (lastChar < 0xAC00 || lastChar > 0xD7A3) {
					if (toString(lastChar).matches("[a-zA-Z]")) {
						break;
					}
				} else {
					break;
				}
			}
			// 단어의 마지막 문자가 한글 또는 숫자가 아닐경우 처리
			if (lastChar < 0xAC00 || lastChar > 0xD7A3) {
				return formatStr.replace("/", "(") + ")";
			}
			// 조사의 인덱스 번호를 구한다.
			int i = 1;
			int finalSoundIndex = (lastChar - 0xAC00) % 28;
			if (finalSoundIndex > 0) {
				if ("으로/로".equals(formatStr) && finalSoundIndex == 8) {
					i = 1;
				} else {
					i = 0;
				}
			}
			return formatStr.split("/")[i];
		} else {
			logger.error("허용되지 않은 포멧입니다!!");
			return "";
		}
	}
	
	/**
	 * <p>해당 한글 단어에 맞는 조사를 붙여서 리턴한다.</p>
	 * <p>1. 종성에 받침이 있는 경우 '은/이/을/과'</p>
	 * <p>2. 종성에 받침이 없는 경우 '는/가/를/와'</p>
	 * <p>3. 종성에 받침이 없거나 'ㄹ' 인경우 '로' 그 외는 '으로'</p>
	 * 
	 * <pre>
	 * StringUtil.addJosa(null, *)               = ""
	 * StringUtil.addJosa("", *)                 = ""
	 * StringUtil.addJosa("   ", *)              = ""
	 * StringUtil.addJosa(***, null)             = "***"
	 * StringUtil.addJosa(***, "")               = "***"
	 * StringUtil.addJosa(***, "  ")             = "***"
	 * StringUtil.addJosa(***, "은/가")          = "***"
	 * StringUtil.addJosa("수박", "은/는")       = "수박은"
	 * StringUtil.addJosa("사과", "은/는")       = "사과는"
	 * StringUtil.addJosa("수박", "이/가")       = "수박이"
	 * StringUtil.addJosa("사과", "이/가")       = "사과가"
	 * StringUtil.addJosa("수박", "을/를")       = "수박을"
	 * StringUtil.addJosa("사과", "을/를")       = "사과를"
	 * StringUtil.addJosa("수박", "과/와")       = "수박과"
	 * StringUtil.addJosa("사과", "과/와")       = "사과와"
	 * StringUtil.addJosa("수박", "으로/로")     = "수박으로"
	 * StringUtil.addJosa("사과", "으로/로")     = "사과로"
	 * StringUtil.addJosa("날짜 1", "은/는")     = "날짜 1은"
	 * StringUtil.addJosa("날짜 2", "은/는")     = "날짜 2는"
	 * StringUtil.addJosa("과일 (귤)", "은/는")  = "과일 (귤)은"
	 * StringUtil.addJosa("과일 (배)", "은/는")  = "과일 (배)는"
	 * StringUtil.addJosa("과일 price", "이/가") = "과일 price이(가)"
	 * </pre>
	 * 
	 * @param wordObj   (조사를 붙일 단어 문자열 객체)
	 * @param formatObj (조사 포멧 문자열 객체)
	 * @return String   (조사를 붙인 후 문자열)
	 */
	public static String addJosa(Object wordObj, Object formatObj) {
		return clean(wordObj) + getJosa(wordObj, formatObj);
	}
	
	/**
	 * <p>XSS 공격에 사용되는 문자를 필터링하고 개행문자를 {@literal <br>} 태그로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.xss(null, *)  = ""
	 * StringUtil.xss("", *)    = ""
	 * StringUtil.xss("   ", *) = ""
	 * StringUtil.xss("{@literal &|"}")    = "{@literal &amp;|&quot;}"
	 * StringUtil.xss("{@literal <|>}")    = "{@literal &lt;|&gt;}"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String xss(Object obj) {
		return xss(obj, "", true);
	}
	
	/**
	 * <p>XSS 공격에 사용되는 문자를 필터링하고 개행문자 처리후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.xss(null, *)  = ""
	 * StringUtil.xss("", *)    = ""
	 * StringUtil.xss("   ", *) = ""
	 * StringUtil.xss("{@literal &|"}", *) = "{@literal &amp;|&quot;}"
	 * StringUtil.xss("{@literal <|>}", *) = "{@literal &lt;|&gt;}"
	 * </pre>
	 * 
	 * @param obj        (변경할 문자형 객체)
	 * @param isLineToBr (개행문자  {@literal <br>}로 변경여부)
	 * @return String    (변경후 문자)
	 */
	public static String xss(Object obj, boolean isLineToBr) {
		return xss(obj, "", isLineToBr);
	}
	
	/**
	 * <p>XSS 공격에 사용되는 문자를 필터링하고 개행문자를 {@literal <br>} 태그로 변경후 리턴한다.</p>
	 * <p>문자 또는 숫자가 null, 공백일 경우 새로운 문자를 반환한다.</p>
	 * 
	 * <pre>
	 * StringUtil.xss(null, "abc")  = "abc"
	 * StringUtil.xss("", "abc")    = "abc"
	 * StringUtil.xss("   ", "abc") = "abc"
	 * StringUtil.xss("{@literal &|"}", "abc") = "{@literal &amp;|&quot;}"
	 * StringUtil.xss("{@literal <|>}", "abc") = "{@literal &lt;|&gt;}"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @param newStr  (새로운 문자)
	 * @return String (변경후 문자)
	 */
	public static String xss(Object obj, String newStr) {
		return xss(obj, newStr, true);
	}
	
	/**
	 * <p>XSS 공격에 사용되는 문자를 필터링하고 개행문자 처리후 리턴한다.</p>
	 * <p>문자 또는 숫자가 null, 공백일 경우 새로운 문자를 반환한다.</p>
	 * 
	 * <pre>
	 * StringUtil.xss(null, "abc", *)  = "abc"
	 * StringUtil.xss("", "abc", *)    = "abc"
	 * StringUtil.xss("   ", "abc", *) = "abc"
	 * StringUtil.xss("{@literal &|"}", "abc", *) = "{@literal &amp;|&quot;}"
	 * StringUtil.xss("{@literal <|>}", "abc", *) = "{@literal &lt;|&gt;}"
	 * </pre>
	 * 
	 * @param obj        (변경할 문자형 객체)
	 * @param newStr     (새로운 문자)
	 * @param isLineToBr (개행문자  {@literal <br>}로 변경여부)
	 * @return String    (변경후 문자)
	 */
	public static String xss(Object obj, String newStr, boolean isLineToBr) {
		if (StringUtil.isBlank(obj)) {
			return newStr;
		}
		String str = String.valueOf(obj);
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("\'", "&#039;");
		str = str.replaceAll("\"", "&#034;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		//str = str.replaceAll("\\(", "&#040;");
		//str = str.replaceAll("\\)", "&#041;");
		//str = str.replaceAll("%", "&#037;");
		//str = str.replaceAll("\\+", "&#043;");
		//str = str.replaceAll(";", "&#059;");
		if (isLineToBr) {
			str = HtmlUtil.lineToBr(str);
		}
		return str;
	}
	
	/**
	 * <p>XSS 공격에 사용되는 Script 관련 태그를 필터링후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.xssScript(null)      = ""
	 * StringUtil.xssScript("")        = ""
	 * StringUtil.xssScript("   ")     = ""
	 * StringUtil.xssScript("{@literal <}script") = "{@literal &lt;}script"
	 * StringUtil.xssScript("{@literal <}object") = "{@literal &lt;}object"
	 * StringUtil.xssScript("{@literal <}applet") = "{@literal &lt;}applet"
	 * StringUtil.xssScript("{@literal <}embed")  = "{@literal &lt;}embed"
	 * StringUtil.xssScript("{@literal <}frame")  = "{@literal &lt;}frame"
	 * StringUtil.xssScript("{@literal <}iframe") = "{@literal &lt;}iframe"
	 * StringUtil.xssScript("{@literal <}form")   = "{@literal &lt;}form"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String xssScript(Object obj) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		String str = String.valueOf(obj);
		str = str.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
		str = str.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");
		str = str.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
		str = str.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");
		str = str.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
		str = str.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");
		str = str.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
		str = str.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;/embed");
		str = str.replaceAll("<(F|f)(R|r)(A|a)(M|m)(E|e)", "&lt;frame");
		str = str.replaceAll("</(F|f)(R|r)(A|a)(M|m)(E|e)", "&lt;/frame");
		str = str.replaceAll("<(I|i)(F|f)(R|r)(A|a)(M|m)(E|e)", "&lt;iframe");
		str = str.replaceAll("</(I|i)(F|f)(R|r)(A|a)(M|m)(E|e)", "&lt;/iframe");
		str = str.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
		str = str.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;/form");
		return str;
	}
	
	/**
	 * <p>XSS 공격에 사용되는 Script 관련 태그 및 블랙리스트 태그를 필터링후 리턴한다.</p>
	 * 
	 * <pre>
	 * StringUtil.xssBlacklist(null)       = ""
	 * StringUtil.xssBlacklist("")         = ""
	 * StringUtil.xssBlacklist("   ")      = ""
	 * StringUtil.xssBlacklist("{@literal <}script")  = "{@literal &lt;}script"
	 * StringUtil.xssBlacklist("{@literal <}object")  = "{@literal &lt;}object"
	 * StringUtil.xssBlacklist("{@literal <}applet")  = "{@literal &lt;}applet"
	 * StringUtil.xssBlacklist("{@literal <}embed")   = "{@literal &lt;}embed"
	 * StringUtil.xssBlacklist("{@literal <}frame")   = "{@literal &lt;}frame"
	 * StringUtil.xssBlacklist("{@literal <}iframe")  = "{@literal &lt;}iframe"
	 * StringUtil.xssBlacklist("{@literal <}form")    = "{@literal &lt;}form"
	 * StringUtil.xssBlacklist("vbscript") = "x-vbscript"
	 * StringUtil.xssBlacklist("document") = "x-document"
	 * StringUtil.xssBlacklist("onblur")   = "x-onblur"
	 * StringUtil.xssBlacklist("onclick")  = "x-onclick"
	 * StringUtil.xssBlacklist("onload")   = "x-onload"
	 * </pre>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String xssBlacklist(Object obj) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		String str = xssScript(obj);
		str = str.replaceAll("(J|j)(A|a)(V|v)(A|a)(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "x-javascript");
		str = str.replaceAll("(D|d)(O|o)(C|c)(U|u)(M|m)(E|e)(N|n)(T|t)", "x-document");
		str = str.replaceAll("(V|v)(B|b)(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "x-vbscript");
		str = str.replaceAll("(G|g)(R|r)(A|a)(M|m)(E|e)(S|s)(E|e)(T|t)", "x-grameset");
		str = str.replaceAll("(L|l)(A|a)(Y|y)(E|e)(R|r)", "x-layer");
		str = str.replaceAll("(B|b)(G|g)(S|s)(O|o)(U|u)(N|n)(D|d)", "x-bgsound");
		str = str.replaceAll("(A|a)(L|l)(E|e)(R|r)(T|t)", "x-alert");
		str = str.replaceAll("(O|o)(N|n)(B|b)(L|l)(U|u)(R|r)", "x-onblur");
		str = str.replaceAll("(O|o)(N|n)(F|f)(O|o)(C|c)(U|u)(S|s)", "x-onfocus");
		str = str.replaceAll("(O|o)(N|n)(C|c)(H|h)(A|a)(N|n)(G|g)(E|e)", "x-onchange");
		str = str.replaceAll("(O|o)(N|n)(C|c)(L|l)(I|i)(C|c)(K|k)", "x-onclick");
		str = str.replaceAll("(O|o)(N|n)(D|d)(B|b)(L|l)(C|c)(L|l)(I|i)(C|c)(K|k)", "x-ondblclick");
		str = str.replaceAll("(O|o)(N|n)(M|m)(O|o)(U|u)(S|s)(E|e)", "x-onmouse");
		str = str.replaceAll("(O|o)(N|n)(S|s)(C|c)(R|r)(O|o)(L|l)(L|l)", "x-onscroll");
		str = str.replaceAll("(O|o)(N|n)(S|s)(U|u)(B|b)(M|m)(I|i)(T|t)", "x-onsubmit");
		str = str.replaceAll("(O|o)(N|n)(L|l)(O|o)(A|a)(D|d)", "x-onload");
		str = str.replaceAll("(O|o)(N|n)(U|u)(N|N)(L|l)(O|o)(A|a)(D|d)", "x-onunload");
		str = str.replaceAll("(E|e)(N|n)(E|e)(R|r)(R|r)(O|o)(R|r)", "x-enerror");
		return str;
	}
	
}