/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : EncodeUtil.java
 * @Description : Encode, Decode 유틸 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncodeUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(EncodeUtil.class);
	
	/**
	 * <p>문자 또는 숫자를 URLEncoder.encode를 사용하여 Application 인코딩 타입으로 인코딩한다.</p>
	 * 
	 * @param obj     (인코딩할 문자형, 숫자형 객체)
	 * @return String (인코딩후 문자)
	 */
	public static String encode(Object obj) {
		return encode(obj, Const.ENCODING_TYPE);
	}
	
	/**
	 * <p>문자 또는 숫자를 URLEncoder.encode를 사용하여 해당 인코딩 타입으로 인코딩한다.</p>
	 * 
	 * @param obj     (인코딩할 문자형, 숫자형 객체)
	 * @param enc     (인코딩 타입)
	 * @return String (인코딩후 문자)
	 */
	public static String encode(Object obj, String enc) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		try {
			return URLEncoder.encode(StringUtil.clean(obj), enc);
		} catch (UnsupportedEncodingException e) {
			logger.error(ExceptionUtil.addMessage(e, "지원하지 않는 인코딩 타입 입니다."));
			return "";
		}
	}
	
	/**
	 * <p>문자 또는 숫자를 URLDecoder.decode를 사용하여 Application 인코딩 타입으로 디코딩한다.</p>
	 * 
	 * @param obj     (디코딩할 문자형, 숫자형 객체)
	 * @return String (디코딩후 문자)
	 */
	public static String decode(Object obj) {
		return decode(obj, Const.ENCODING_TYPE);
	}
	
	/**
	 * <p>문자 또는 숫자를 URLDecoder.decode를 사용하여 해당 인코딩 타입으로 디코딩한다.</p>
	 * 
	 * @param obj     (디코딩할 문자형, 숫자형 객체)
	 * @param enc     (인코딩 타입)
	 * @return String (디코딩후 문자)
	 */
	public static String decode(Object obj, String enc) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		try {
			return URLDecoder.decode(StringUtil.clean(obj), enc);
		} catch (UnsupportedEncodingException e) {
			logger.error(ExceptionUtil.addMessage(e, "지원하지 않는 인코딩 타입 입니다."));
			return "";
		}
	}
	
	/**
	 * <p>문자 또는 숫자를 getBytes 방식으로 8859_1를 Application 인코딩 타입으로 변경한다.</p>
	 * 
	 * @param obj     (인코딩할 문자형, 숫자형 객체)
	 * @return String (인코딩후 문자)
	 */
	public static String getBytes(Object obj) {
		return getBytes(obj, "8859_1", Const.ENCODING_TYPE);
	}
	
	/**
	 * <p>문자 또는 숫자를 getBytes 방식으로 enc1에서  enc2로 인코딩 타입을 변경한다.</p>
	 * 
	 * @param obj     (인코딩할 문자형, 숫자형 객체)
	 * @param enc1    (현재의 인코딩 타입)
	 * @param enc2    (변경할 인코딩 타입)
	 * @return String (인코딩후 문자)
	 */
	public static String getBytes(Object obj, String enc1, String enc2) {
		if (obj == null) {
			return "";
		}
		String str = String.valueOf(obj);
		try {
			return new String(str.getBytes(enc1), enc2);
		} catch (UnsupportedEncodingException e) {
			logger.error(ExceptionUtil.addMessage(e, "지원하지 않는 인코딩 타입 입니다."));
			return str;
		}
	}
	
	/**
	 * <p>문자 또는 숫자를 getBytes 방식으로 MS949를 8859_1 인코딩 타입으로 변경한다.</p>
	 * 
	 * @param obj     (인코딩할 문자형, 숫자형 객체)
	 * @return String (인코딩후 문자)
	 */
	public static String toEnglish(Object obj) {
		return getBytes(obj, "MS949", "8859_1");
	}
	
	/**
	 * <p>문자 또는 숫자를 getBytes 방식으로 8859_1를 MS949 인코딩 타입으로 변경한다.</p>
	 * 
	 * @param obj     (인코딩할 문자형, 숫자형 객체)
	 * @return String (인코딩후 문자)
	 */
	public static String toKorean(Object obj) {
		return getBytes(obj, "8859_1", "MS949");
	}
	
}