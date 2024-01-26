/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : ObjectUtil.java
 * @Description : Object 유틸 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectUtil {
	
	/**
	 * <p>두 객체가 같은지 비교한다.</p>
	 * <p>java.lang.Object.equals(Object obj) 확장형으로 null도 비교한다.</p>
	 *
	 * <pre>
	 * ObjectUtil.equals(null, null)                 = true
	 * ObjectUtil.equals(null, "")                   = false
	 * ObjectUtil.equals("", null)                   = false
	 * ObjectUtil.equals("", "")                     = true
	 * ObjectUtil.equals(Boolean.TRUE, "true")       = false
	 * ObjectUtil.equals(Boolean.TRUE, Boolean.TRUE) = true
	 * </pre>
	 *
	 * @param obj1     (비교할 객체1)
	 * @param obj2     (비교할 객체2)
	 * @return boolean (체크결과)
	 */
	public static boolean equals(Object obj1, Object obj2) {
		if (obj1 == obj2) {
			return true;
		}
		if (obj1 == null || obj2 == null) {
			return false;
		}
		return obj1.equals(obj2);
	}
	
	/**
	 * <p>객체가 null 일경우 새로운 객체를 반환하고, 그 외는 해당 객체를 리턴한다.</p>
	 * 
	 * <pre>
	 * ObjectUtil.replaceNull(null, null)      = null
	 * ObjectUtil.replaceNull(null, "")        = ""
	 * ObjectUtil.replaceNull(null, "zz")      = "zz"
	 * ObjectUtil.replaceNull("abc", *)        = "abc"
	 * ObjectUtil.replaceNull(Boolean.TRUE, *) = Boolean.TRUE
	 * </pre>
	 *
	 * @param obj     (적용될 객체)
	 * @param newObj  (새로운 객체)
	 * @return Object (적용후 객체)
	 */
	public static Object replaceNull(Object obj, Object newObj) {
		return (obj != null) ? obj : newObj;
	}
	
	/**
	 * <p>객체의 해쉬코드 값을 가져온다.</p>
	 * <p>java.lang.Object.hashCode() 확장형으로 null 일때도 처리한다.</p>
	 *
	 * <pre>
	 * ObjectUtil.getHashCode(null)   = 0
	 * ObjectUtil.getHashCode(obj)    = obj.hashCode()
	 * </pre>
	 *
	 * @param obj  (가져올 객체)
	 * @return int (해쉬코드 값)
	 */
	public static int getHashCode(Object obj) {
		return (obj == null) ? 0 : obj.hashCode();
	}
	
	/**
	 * <p>String 배열을 {@literal List<String>} 객체로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * ObjectUtil.arrayToList(null)           = null
	 * ObjectUtil.arrayToList({"aaa", "bbb"}) = {@literal List<String>}
	 * </pre>
	 * 
	 * @param strArr (String 배열)
	 * @return List  ({@literal List<String>} 객체)
	 */
	public static List<String> arrayToList(String[] strArr) {
		if (strArr == null) {
			return null;
		}
		List<String> list = new ArrayList<String>(Arrays.asList(strArr));
		return list;
	}
	
	/**
	 * <p>{@literal List<String>} 객체를 String 배열로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * ObjectUtil.listToArray(null)                    = null
	 * ObjectUtil.listToArray({@literal List<String>}) = {"aaa", "bbb"}
	 * </pre>
	 * 
	 * @param list      ({@literal List<String>} 객체)
	 * @return String[] (String 배열)
	 */
	public static String[] listToArray(List<String> list) {
		if (list == null) {
			return null;
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * <p>{@literal List<String>} 객체를 "|" 구분자로 연결한 문자로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * ObjectUtil.listToString(null)                    = ""
	 * ObjectUtil.listToString({@literal List<String>}) = "aaa|bbb"
	 * </pre>
	 * 
	 * @param list     ({@literal List<String>} 객체)
	 * @return String  (변경한 문자)
	 */
	public static String listToString(List<String> list) {
		return listToString(list, "|");
	}
	
	/**
	 * <p>{@literal List<String>} 객체를 해당 구분자로 연결한 문자로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * ObjectUtil.listToString(null)                         = ""
	 * ObjectUtil.listToString({@literal List<String>}, ",") = "aaa,bbb"
	 * ObjectUtil.listToString({@literal List<String>}, "|") = "aaa|bbb"
	 * </pre>
	 * 
	 * @param list    ({@literal List<String>} 객체)
	 * @param delim   (구분자)
	 * @return String (변경한 문자)
	 */
	public static String listToString(List<String> list, String delim) {
		if (list == null || list.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (String str: list) {
			if (sb.length() > 0) {
				sb.append(delim);
			}
			sb.append(str);
		}
		return sb.toString();
	}
	
	/**
	 * <p>int 배열을 String 배열로 변환해서 리턴한다.</p>
	 * 
	 * <pre>
	 * ObjectUtil.toStringArr(null)       = null
	 * ObjectUtil.toStringArr(int[0])     = String[0]
	 * ObjectUtil.toStringArr({123, 567}) = {"123", "567"}
	 * </pre>
	 * 
	 * @param intArr    (int 배열)
	 * @return String[] (String 배열)
	 */
	public static String[] toStringArr(int[] intArr) {
		if (intArr == null) {
			return null;
		}
		String[] strArr = new String[intArr.length];
		for (int i = 0; i < intArr.length; i++) {
			strArr[i] = StringUtil.toString(intArr[i]);
		}
		return strArr;
	}
	
}