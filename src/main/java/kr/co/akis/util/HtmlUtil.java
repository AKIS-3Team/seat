/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : HtmlUtil.java
 * @Description : HTML 유틸 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HtmlUtil.class);
	
	/**
	 * <p>HTML 태그를 모두 제거후 리턴한다.</p>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String deleteHtml(Object obj) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		String str = String.valueOf(obj);
		str = str.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		str = str.replaceAll("\r\n", "");
		str = str.replaceAll("\r", "");
		str = str.replaceAll("\n", "");
		str = str.replaceAll("\t", "");
		return str;
	}
	
	/**
	 * <p>개행문자를 {@literal <br>} 태그로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String lineToBr(Object obj) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		String str = String.valueOf(obj);
		str = str.replaceAll("\r\n", "<br />");
		str = str.replaceAll("\r", "<br />");
		str = str.replaceAll("\n", "<br />");
		return str;
	}
	
	/**
	 * <p>{@literal <br>} 태그를 개행문자로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String brToLine(Object obj) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		String str = String.valueOf(obj);
		str = str.replaceAll("<br>", "\r\n");
		str = str.replaceAll("<br />", "\r\n");
		return str;
	}
	
	/**
	 * <p>HTML 태그를 인코딩하고 개행문자를 {@literal <br>} 태그로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String encodeHtml(Object obj) {
		return encodeHtml(obj, true);
	}
	
	/**
	 * <p>HTML 태그를 인코딩하고 개행문자 처리후 리턴한다.</p>
	 * 
	 * @param obj        (변경할 문자형 객체)
	 * @param isLineToBr (개행문자를 {@literal <br}로 변경여부)
	 * @return String    (변경후 문자)
	 */
	public static String encodeHtml(Object obj, boolean isLineToBr) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		String str = String.valueOf(obj);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			switch (str.charAt(i)) {
				case '&' :
					sb.append("&amp;");
					break;
				case '\'' :
					sb.append("&#039;");
					break;
				case '\"' :
					sb.append("&#034;");
					break;
				case '<' :
					sb.append("&lt;");
					break;
				case '>' :
					sb.append("&gt;");
					break;
				default :
					sb.append(str.charAt(i));
			}
		}
		return isLineToBr ? lineToBr(sb.toString()) : sb.toString();
	}
	
	/**
	 * <p>HTML 태그를 디코딩하고 {@literal <br>} 태그를 개행문자로 변경후 리턴한다.</p>
	 * 
	 * @param obj     (변경할 문자형 객체)
	 * @return String (변경후 문자)
	 */
	public static String decodeHtml(Object obj) {
		return decodeHtml(obj, true);
	}
	
	/**
	 * <p>HTML 태그를 디코딩하고 개행문자 처리후 리턴한다.</p>
	 * 
	 * @param obj        (변경할 문자형 객체)
	 * @param isBrToLine ({@literal <br>} 을 개행문자로 변경여부)
	 * @return String    (변경후 문자)
	 */
	public static String decodeHtml(Object obj, boolean isBrToLine) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		String str = String.valueOf(obj);
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&#034;", "\"");
		str = str.replaceAll("&#039;", "\'");
		str = str.replaceAll("&amp;", "&");
		return isBrToLine ? brToLine(str) : str;
	}
	
	/**
	 * <p>구분자 또는 배열로 되어있는 값에 체크할 값과 일치 하는것이 있을경우 select 박스의 selected 속성을 리턴한다.</P>
	 * 
	 * <pre>
	 * HtmlUtil.getSelected(null, null)         = " selected="selected""
	 * HtmlUtil.getSelected(null, "")           = " selected="selected""
	 * HtmlUtil.getSelected("", null)           = " selected="selected""
	 * HtmlUtil.getSelected("", "")             = " selected="selected""
	 * HtmlUtil.getSelected(null, *)            = ""
	 * HtmlUtil.getSelected(*, null)            = ""
	 * HtmlUtil.getSelected("   ", "")          = ""
	 * HtmlUtil.getSelected(" a ", "a")         = ""
	 * HtmlUtil.getSelected("ab|cd|e", "a")     = ""
	 * HtmlUtil.getSelected("ab|cd|e", "")      = ""
	 * HtmlUtil.getSelected({12, 37, 89}, "13") = ""
	 * HtmlUtil.getSelected("abc", "abc")       = " selected="selected""
	 * HtmlUtil.getSelected("ab|cd|e", "ab")    = " selected="selected""
	 * HtmlUtil.getSelected("ab|cd|e|", "")     = " selected="selected""
	 * HtmlUtil.getSelected({12, 37, 89}, "37") = " selected="selected""
	 * </pre>
	 * 
	 * @param obj     (구분자 또는 배열로 되어있는 체크할 값)
	 * @param keyword (체크할 문자형, 숫자형 객체)
	 * @return String ("" 또는 selected 속성)
	 */
	public static String getSelected(Object obj, Object keyword) {
		return getSelected(obj, keyword, "[|]");
	}
	
	/**
	 * <p>구분자 또는 배열로 되어있는 값에 체크할 값과 일치 하는것이 있을경우 select 박스의 selected 속성을 리턴한다.</P>
	 * 
	 * <pre>
	 * HtmlUtil.getSelected(null, null, *)            = " selected="selected""
	 * HtmlUtil.getSelected(null, "", *)              = " selected="selected""
	 * HtmlUtil.getSelected("", null, *)              = " selected="selected""
	 * HtmlUtil.getSelected("", "", *)                = " selected="selected""
	 * HtmlUtil.getSelected(null, *, *)               = ""
	 * HtmlUtil.getSelected(*, null, *)               = ""
	 * HtmlUtil.getSelected("   ", "", *)             = ""
	 * HtmlUtil.getSelected(" a ", "a", *)            = ""
	 * HtmlUtil.getSelected("ab|cd|e", "a", "[|]")    = ""
	 * HtmlUtil.getSelected("ab#cd#e", "", "#")       = ""
	 * HtmlUtil.getSelected({12, 37, 89}, "13", null) = ""
	 * HtmlUtil.getSelected("abc", "abc", *)          = " selected="selected""
	 * HtmlUtil.getSelected("ab|cd|e", "ab", "[|]")   = " selected="selected""
	 * HtmlUtil.getSelected("ab#cd#e#", "", "#")      = " selected="selected""
	 * HtmlUtil.getSelected({12, 37, 89}, "37", null) = " selected="selected""
	 * </pre>
	 * 
	 * @param obj     (구분자 또는 배열로 되어있는 체크할 값)
	 * @param keyword (체크할 문자형, 숫자형 객체)
	 * @param delim   (구분자)
	 * @return String ("" 또는 selected 속성)
	 */
	public static String getSelected(Object obj, Object keyword, Object delim) {
		if (StringUtil.isEmpty(obj) && StringUtil.isEmpty(keyword)) {
			return " selected=\"selected\"";
		}
		if (StringUtil.equalsSplit(obj, StringUtil.clean(keyword), delim)) {
			return " selected=\"selected\"";
		}
		return "";
	}
	
	/**
	 * <p>구분자 또는 배열로 되어있는 값에 체크할 값과 일치 하는것이 있을경우 check 박스, radio 버튼의 checked 속성을 리턴한다.</P>
	 * 
	 * <pre>
	 * HtmlUtil.getChecked(null, null)         = " checked="checked""
	 * HtmlUtil.getChecked(null, "")           = " checked="checked""
	 * HtmlUtil.getChecked("", null)           = " checked="checked""
	 * HtmlUtil.getChecked("", "")             = " checked="checked""
	 * HtmlUtil.getChecked(null, *)            = ""
	 * HtmlUtil.getChecked(*, null)            = ""
	 * HtmlUtil.getChecked("   ", "")          = ""
	 * HtmlUtil.getChecked(" a ", "a")         = ""
	 * HtmlUtil.getChecked("ab|cd|e", "a")     = ""
	 * HtmlUtil.getChecked("ab|cd|e", "")      = ""
	 * HtmlUtil.getChecked({12, 37, 89}, "13") = ""
	 * HtmlUtil.getChecked("abc", "abc")       = " checked="checked""
	 * HtmlUtil.getChecked("ab|cd|e", "ab")    = " checked="checked""
	 * HtmlUtil.getChecked("ab|cd|e|", "")     = " checked="checked""
	 * HtmlUtil.getChecked({12, 37, 89}, "37") = " checked="checked""
	 * </pre>
	 * 
	 * @param obj     (구분자 또는 배열로 되어있는 체크할 값)
	 * @param keyword (체크할 문자형, 숫자형 객체)
	 * @return String ("" 또는 checked 속성)
	 */
	public static String getChecked(Object obj, Object keyword) {
		return getChecked(obj, keyword, "[|]");
	}
	
	/**
	 * <p>구분자 또는 배열로 되어있는 값에 체크할 값과 일치 하는것이 있을경우 check 박스, radio 버튼의 checked 속성을 리턴한다.</P>
	 * 
	 * <pre>
	 * HtmlUtil.getChecked(null, null, *)            = " checked="checked""
	 * HtmlUtil.getChecked(null, "", *)              = " checked="checked""
	 * HtmlUtil.getChecked("", null, *)              = " checked="checked""
	 * HtmlUtil.getChecked("", "", *)                = " checked="checked""
	 * HtmlUtil.getChecked(null, *, *)               = ""
	 * HtmlUtil.getChecked(*, null, *)               = ""
	 * HtmlUtil.getChecked("   ", "", *)             = ""
	 * HtmlUtil.getChecked(" a ", "a", *)            = ""
	 * HtmlUtil.getChecked("ab|cd|e", "a", "[|]")    = ""
	 * HtmlUtil.getChecked("ab#cd#e", "", "#")       = ""
	 * HtmlUtil.getChecked({12, 37, 89}, "13", null) = ""
	 * HtmlUtil.getChecked("abc", "abc", *)          = " checked="checked""
	 * HtmlUtil.getChecked("ab|cd|e", "ab", "[|]")   = " checked="checked""
	 * HtmlUtil.getChecked("ab#cd#e#", "", "#")      = " checked="checked""
	 * HtmlUtil.getChecked({12, 37, 89}, "37", null) = " checked="checked""
	 * </pre>
	 * 
	 * @param obj     (구분자 또는 배열로 되어있는 체크할 값)
	 * @param keyword (체크할 문자형, 숫자형 객체)
	 * @param delim   (구분자)
	 * @return String ("" 또는 checked 속성)
	 */
	public static String getChecked(Object obj, Object keyword, Object delim) {
		if (StringUtil.isEmpty(obj) && StringUtil.isEmpty(keyword)) {
			return " checked=\"checked\"";
		}
		if (StringUtil.equalsSplit(obj, StringUtil.clean(keyword), delim)) {
			return " checked=\"checked\"";
		}
		return "";
	}
	
	/**
	 * <p>target 속성값이 존재할 경우 target 속성을 리턴한다.</p>
	 * 
	 * <pre>
	 * HtmlUtil.getTarget(null)        = ""
	 * HtmlUtil.getTarget("")          = ""
	 * HtmlUtil.getTarget("   ")       = ""
	 * HtmlUtil.getTarget(" _blank " ) = " target="_blank""
	 * HtmlUtil.getTarget("_blank" )   = " target="_blank""
	 * </pre>
	 * 
	 * @param obj     (target 속성값)
	 * @return String ("" 또는 target 속성)
	 */
	public static String getTarget(Object obj) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		return " target=\"" + StringUtil.clean(obj) + "\"";
	}
	
	/**
	 * <p>GET/POST 방식으로 전달된 데이터 화면에 출력한다.</p>
	 * 
	 * @param request  (HttpServletRequest)
	 * @param response (HttpServletResponse)
	 */
	public static void printRequest(HttpServletRequest request, HttpServletResponse response) {
		printRequest(request, response, false);
	}
	
	/**
	 * <p>GET/POST 방식으로 전달된 데이터 화면에 출력한다.</p>
	 * 
	 * @param request  (HttpServletRequest)
	 * @param response (HttpServletResponse)
	 * @param isClose  (스트림을 닫을지 여부)
	 */
	public static void printRequest(HttpServletRequest request, HttpServletResponse response, boolean isClose) {
		try {
			response.setContentType("text/html; charset=" + Const.ENCODING_TYPE);
			PrintWriter out = response.getWriter();
			Enumeration<?> enumer = request.getParameterNames();
			String name, value = "";
			StringBuilder sb = new StringBuilder();
			sb.append("<table width=\"700px\" border=\"1px\" cellspacing=\"0px\" cellpadding=\"1px\" borderColorDark=\"white\" borderColorLight=\"silver\">\n");
			sb.append("    <caption><strong>Request QueryString</strong></caption>\n");
			sb.append("    <colgroup>\n");
			sb.append("    <col style=\"width:200px\" />\n");
			sb.append("    <col />\n");
			sb.append("    </colgroup>\n");
			sb.append("    <thead style=\"height:30px;background:silver;\">\n");
			sb.append("        <tr>\n");
			sb.append("            <th scope=\"col\">Parameter Name</th>\n");
			sb.append("            <th scope=\"col\">Parameter Value</th>\n");
			sb.append("        </tr>\n");
			sb.append("    </thead>\n");
			sb.append("    <tbody>\n");
			while(enumer.hasMoreElements()) {
				name = (String)enumer.nextElement();
				value = request.getParameter(name);
				sb.append("        <tr>\n");
				sb.append("            <td>").append(name).append("</td>\n");
				sb.append("            <td>").append(value).append("</td>\n");
				sb.append("        </tr>\n");
			}
			sb.append("    </tbody>\n");
			sb.append("</table>\n");
			out.println(sb.toString());
			if (isClose) {
				out.close();
			}
		} catch (IOException e) {
			logger.error(ExceptionUtil.addMessage(e, "GET/POST 방식으로 전달된 데이터 화면에 출력 에러."));
		}
	}
	
}