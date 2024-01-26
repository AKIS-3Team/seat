/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : HeaderUtil.java
 * @Description : HTTP Header 유틸 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeaderUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HeaderUtil.class);
	
	/**
	 * <p>사용자의 브라우저 종류를 리턴한다.</p>
	 * 
	 * @param request (HttpServletRequest)
	 * @return String (사용자 브라우저 종류)
	 */
	public static String getBrowser(HttpServletRequest request) {
		String agent = StringUtil.clean(request.getHeader("User-Agent"));
		if (agent.matches("(?i).*(Edge).*")) {
			return "Edge";
		} else if (agent.matches("(?i).*(Trident).*") || agent.matches("(?i).*(MSIE).*")) {
			return "MSIE";
		} else if (agent.matches("(?i).*(Chrome).*")) {
			return "Chrome";
		} else if (agent.matches("(?i).*(Opera).*")) {
			return "Opera";
		} else if (agent.matches("(?i).*(Firefox).*")) {
			return "Firefox";
		} else if (agent.matches("(?i).*(Safari).*")) {
			return "Safari";
		} else if (agent.matches("(?i).*(iPhone).*") && agent.matches("(?i).*(Mobile).*")) {
			return "iPhone";
		} else if (agent.matches("(?i).*(Android).*") && agent.matches("(?i).*(Mobile).*")) {
			return "Android";
		}
		return "Chrome";
	}
	
	/**
	 * <p>사용자의 브라우저가 모바일 브라우저인지 체크한다.</p>
	 * 
	 * @param request  (HttpServletRequest)
	 * @return boolean (모바일 브라우저인지 여부)
	 */
	public static boolean isMobileBrowser(HttpServletRequest request) {
		String agent = StringUtil.clean(request.getHeader("User-Agent"));
		Pattern p1 = Pattern.compile("(?i)(Mobi|Mobile|iPhone|iPad|iPod|Android|Windows CE|Windows Phone|BlackBerry|Symbian|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson|LG|SAMSUNG|Samsung)");
		Matcher m1 = p1.matcher(agent);
		return m1.find();
	}
	
	/**
	 * <p>사용자의 O/S 종류를 리턴한다.</p>
	 * 
	 * @param request (HttpServletRequest)
	 * @return String (사용자 O/S 종류)
	 */
	public static String getOs(HttpServletRequest request) {
		String agent = StringUtil.clean(request.getHeader("User-Agent"));
		if (agent.matches("(?i).*(Windows).*")) {
			return "Windows";
		} else if (agent.matches("(?i).*(Mac).*")) {
			return "macOS";
		} else if (agent.matches("(?i).*(X11).*")) {
			return "UNIX";
		} else if (agent.matches("(?i).*(Linux).*")) {
			return "Linux";
		} else if (agent.matches("(?i).*(SunOS).*")) {
			return "SunOS";
		} else if (agent.matches("(?i).*(Android).*")) {
			return "Android ";
		} else if (agent.matches("(?i).*(iPhone|iPad|iPod).*")) {
			return "iOS";
		} else if (agent.matches("(?i).*(OpenBSD).*")) {
			return "Open BSD";
		} else if (agent.matches("(?i).*(QNX|UNIX).*")) {
			return "QNX";
		} else if (agent.matches("(?i).*(BeOS).*")) {
			return "BeOS";
		} else if (agent.matches("(?i).*(OS\\/2).*")) {
			return "OS/2";
		}
		return "";
	}
	
	/**
	 * <p>사용자의 O/S 종류와 버전을 리턴한다.</p>
	 * 
	 * @param request (HttpServletRequest)
	 * @return String (사용자 O/S 종류와 버전)
	 */
	public static String getOsVersion(HttpServletRequest request) {
		String agent = StringUtil.clean(request.getHeader("User-Agent"));
		if (agent.matches("(?i).*(Win16).*")) {
			return "Windows 3.11";
		} else if (agent.matches("(?i).*(Windows 95|Win95|Windows_95).*")) {
			return "Windows 95";
		} else if (agent.matches("(?i).*(Win 9x 4.90|Windows ME).*")) {
			return "Windows Me";
		} else if (agent.matches("(?i).*(Windows 98|Win98).*")) {
			return "Windows 98";
		} else if (agent.matches("(?i).*(Windows CE).*")) {
			return "Windows CE";
		} else if (agent.matches("(?i).*(Windows NT 4.0|WinNT4.0).*")) {
			return "Windows NT 4.0";
		} else if (agent.matches("(?i).*(Windows NT 5.0|Windows 2000).*")) {
			return "Windows 2000";
		} else if (agent.matches("(?i).*(Windows NT 5.1|Windows XP).*")) {
			return "Windows XP";
		} else if (agent.matches("(?i).*(Windows NT 5.2).*")) {
			return "Windows Server 2003";
		} else if (agent.matches("(?i).*(Windows NT 6.0).*")) {
			return "Windows Vista";
		} else if (agent.matches("(?i).*(Windows NT 6.1).*")) {
			return "Windows 7";
		} else if (agent.matches("(?i).*(Windows NT 6.2).*")) {
			return "Windows 8";
		} else if (agent.matches("(?i).*(Windows NT 6.3).*")) {
			return "Windows 8.1";
		} else if (agent.matches("(?i).*(Windows NT 10.0).*")) {
			return "Windows 10";
		} else if (agent.matches("(?i).*(Windows).*")) {
			return "Windows";
		} else if (agent.matches("(?i).*(Mac).*")) {
			return "macOS";
		} else if (agent.matches("(?i).*(X11|UNIX).*")) {
			return "UNIX";
		} else if (agent.matches("(?i).*(Linux).*")) {
			return "Linux";
		} else if (agent.matches("(?i).*(SunOS).*")) {
			return "SunOS";
		} else if (agent.matches("(?i).*(Android).*")) {
			return "Android ";
		} else if (agent.matches("(?i).*(iPhone|iPad|iPod).*")) {
			return "iOS";
		} else if (agent.matches("(?i).*(OpenBSD).*")) {
			return "Open BSD";
		} else if (agent.matches("(?i).*(QNX).*")) {
			return "QNX";
		} else if (agent.matches("(?i).*(BeOS).*")) {
			return "BeOS";
		} else if (agent.matches("(?i).*(OS\\/2).*")) {
			return "OS/2";
		}
		return "";
	}
	
	/**
	 * <p>현재 페이지 Host와 이전 페이지 Host가 같은지 체크한다.</p>
	 * <p>현재, 이전 페이지의 Host가 같을경우 true를 리턴한다.</p>
	 * 
	 * @param request  (HttpServletRequest)
	 * @return boolean (체크 결과)
	 */
	public static boolean equalsHost(HttpServletRequest request) {
		if (StringUtil.clean(getRequestHost(request)).equals(StringUtil.clean(getRefererHost(request)))) {
			return true;
		}
		return false;
	}
	
	/**
	 * <p>현재 페이지 Host와 이전 페이지 Host가 다를경우 Default 페이지로 이동한다.</p>
	 * <p>현재, 이전 페이지의 Host가 같을경우 true를 리턴한다.</p>
	 * 
	 * @param request  (HttpServletRequest)
	 * @param response (HttpServletResponse)
	 * @return boolean (체크 결과)
	 */
	public static boolean checkEqualsHost(HttpServletRequest request, HttpServletResponse response) {
		return checkEqualsHost(request, response, "/");
	}
	
	/**
	 * <p>현재 페이지 Host와 이전 페이지 Host가 다를경우 해당 페이지로 이동한다.</p>
	 * <p>현재, 이전 페이지의 Host가 같을경우 true를 리턴한다.</p>
	 * 
	 * @param request     (HttpServletRequest)
	 * @param response    (HttpServletResponse)
	 * @param redirectUrl (이동할 주소)
	 * @return boolean    (체크 결과)
	 */
	public static boolean checkEqualsHost(HttpServletRequest request, HttpServletResponse response, String redirectUrl) {
		try {
			if (!equalsHost(request)) {
				response.sendRedirect(redirectUrl);
				return false;
			}
		} catch (Exception e) {
			logger.error(ExceptionUtil.addMessage(e, "현재 와 이전페이지 Host 체크 후 페이지 이동 에러."));
			return false;
		}
		return true;
	}
	
	/**
	 * <p>현재 페이지의 스키마를 제외한 Host를 리턴한다.</p>
	 * 
	 * @param request (HttpServletRequest)
	 * @return String (스키마 제외 Host)
	 */
	public static String getRequestHost(HttpServletRequest request) {
		return request.getHeader("host");
	}
	
	/**
	 * <p>현재 페이지의 스키마를 포함한 Host를 리턴한다.</p>
	 * 
	 * @param request (HttpServletRequest)
	 * @return String (스키마 포함 Host)
	 */
	public static String getFullRequestHost(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getHeader("host");
	}
	
	/**
	 * <p>현재 페이지의 URI를 리턴한다.</p>
	 * 
	 * @param request (HttpServletRequest)
	 * @return String (현재 페이지 URI)
	 */
	public static String getRequestUri(HttpServletRequest request) {
		String requestUri = (String)request.getAttribute("javax.servlet.forward.request_uri");
		if (StringUtil.isBlank(requestUri)) {
			requestUri = request.getRequestURI();
		}
		return requestUri;
	}
	
	/**
	 * <p>현재 페이지의 쿼리스트링을 제외한 URL을 리턴한다.</p>
	 * 
	 * @param request (HttpServletRequest)
	 * @return String (쿼리스트링 제외 URL)
	 */
	public static String getRequestUrl(HttpServletRequest request) {
		return getFullRequestHost(request) + getRequestUri(request);
	}
	
	/**
	 * <p>현재 페이지의 쿼리스트링 포함한 전체 URL을 리턴한다.</p>
	 * 
	 * @param request (HttpServletRequest)
	 * @return String (쿼리스트링 포함 URL)
	 */
	public static String getFullRequestUrl(HttpServletRequest request) {
		return getFullRequestUrl(request, true);
	}
	
	/**
	 * <p>현재 페이지의 쿼리스트링 포함한 전체 URL을 리턴한다.</p>
	 * 
	 * @param request    (HttpServletRequest)
	 * @param isGetBytes (파라미터 값을 getBytes 방식으로 인코딩할지 여부)
	 * @return String    (쿼리스트링 포함 URL)
	 */
	public static String getFullRequestUrl(HttpServletRequest request, boolean isGetBytes) {
		return getFullRequestUrl(request, isGetBytes, false);
	}
	
	/**
	 * <p>현재 페이지의 쿼리스트링 포함한 전체 URL을 리턴한다.</p>
	 * 
	 * @param request     (HttpServletRequest)
	 * @param isGetBytes  (파라미터 값을 getBytes 방식으로 인코딩할지 여부)
	 * @param isEncodeUrl (URL 인코딩할지 여부)
	 * @return String     (쿼리스트링 포함 URL)
	 */
	public static String getFullRequestUrl(HttpServletRequest request, boolean isGetBytes, boolean isEncodeUrl) {
		StringBuilder sb = new StringBuilder();
		sb.append(getRequestUrl(request));
		
		if ("GET".equals(request.getMethod())) {
			if (request.getQueryString() != null) {
				sb.append("?").append(isGetBytes ? EncodeUtil.getBytes(request.getQueryString()) : request.getQueryString());
			}
		} else {
			Enumeration<?> enumer = request.getParameterNames();
			if (enumer.hasMoreElements()) {
				sb.append("?");
				String name = "";
				String[] values = null;
				while (enumer.hasMoreElements()) {
					name = (String)enumer.nextElement();
					values = request.getParameterValues(name);
					for (int i = 0; i < values.length; i++) {
						if (i != 0) {
							sb.append("&");
						}
						sb.append(name).append("=").append(isGetBytes ? EncodeUtil.getBytes(values[i]) : values[i]);
					}
					if (enumer.hasMoreElements()) {
						sb.append("&");
					}
				}
			}
		}
		
		String httpUrl = sb.toString();
		if (isEncodeUrl) {
			httpUrl = EncodeUtil.encode(httpUrl);
		}
		sb = null;
		
		return httpUrl;
	}
	
	/**
	 * <p>이전 페이지의 스키마를 포함한 Host를 리턴한다.</p>
	 * 
	 * @param request (HttpServletRequest)
	 * @return String (이전 페이지 Host)
	 */
	public static String getFullRefererHost(HttpServletRequest request) {
		String refererUrl = request.getHeader("referer");
		if (!StringUtil.isBlank(refererUrl)) {
			int chkNum = (refererUrl.indexOf("://") >= 0) ? refererUrl.indexOf("://") + 3 : 0;
			int endNum = refererUrl.indexOf('/', chkNum);
			if (endNum >= 0) {
				return refererUrl.substring(0, endNum);
			} else {
				return refererUrl.substring(0);
			}
		}
		return "";
	}
	
	/**
	 * <p>이전 페이지의 스키마를 제외한 Host를 리턴한다.</p>
	 * 
	 * @param request (HttpServletRequest)
	 * @return String (이전 페이지 Host)
	 */
	public static String getRefererHost(HttpServletRequest request) {
		String allRefererHost = getFullRefererHost(request);
		if (!StringUtil.isBlank(allRefererHost)) {
			int startNum = (allRefererHost.indexOf("://") >= 0) ? allRefererHost.indexOf("://") + 3 : 0;
			return allRefererHost.substring(startNum);
		}
		return "";
	}
	
	/**
	 * <p>URI 경로에서 해당위치 폴더명을 리턴한다.</p>
	 * 
	 * @param request  (HttpServletRequest)
	 * @param location (해당위치)
	 * @return String  (폴더명)
	 */
	public static String getFolderName(HttpServletRequest request, int location) {
		return getFolderName(getRequestUri(request), location);
	}
	
	/**
	 * <p>URI 경로에서 해당위치 폴더명을 리턴한다.</p>
	 * 
	 * @param strUri   (URI 문자열)
	 * @param location (해당위치)
	 * @return String  (폴더명)
	 */
	public static String getFolderName(String strUri, int location) {
		String result = "";
		if (StringUtil.countMatches(strUri, "/") > location) {
			String[] splitUri = strUri.substring(1).split("/");
			result = splitUri[location - 1];
		}
		return result;
	}
	
	/**
	 * <p>URL 경로에서 현재 파일명을 리턴한다.</p>
	 * 
	 * @param request (HttpServletRequest)
	 * @return String (파일명)
	 */
	public static String getFileName(HttpServletRequest request) {
		return getFileName(getRequestUri(request));
	}
	
	/**
	 * <p>URL 경로에서 현재 파일명을 리턴한다.</p>
	 * 
	 * @param strUrl  (URL 문자열)
	 * @return String (파일명)
	 */
	public static String getFileName(String strUrl) {
		return strUrl.substring(strUrl.lastIndexOf("/") + 1);
	}
	
	/**
	 * <p>URL 경로에 http://[host]가 없을 경우 추가해서 리턴한다.</p>
	 * 
	 * <pre>
	 * HeaderUtil.addHttpHost(HttpServletRequest, null)        = ""
	 * HeaderUtil.addHttpHost(HttpServletRequest, "   ")       = ""
	 * HeaderUtil.addHttpHost(HttpServletRequest, "http://*")  = "http://*"
	 * HeaderUtil.addHttpHost(HttpServletRequest, "https://*") = "http://*"
	 * HeaderUtil.addHttpHost(HttpServletRequest, "index.jsp") = "http://host/index.jsp"
	 * </pre>
	 * 
	 * @param request (HttpServletRequest)
	 * @param strUrl  (URL 문자열)
	 * @return String (http://[host]를 추가한 URL)
	 */
	public static String addHttpHost(HttpServletRequest request, String strUrl) {
		if (StringUtil.isBlank(strUrl)) {
			return "";
		}
		if (strUrl.indexOf("http://") == 0) {
			return strUrl;
		}
		if (strUrl.indexOf("https://") == 0) {
			return strUrl.replace("https://", "http://");
		}
		if (strUrl.indexOf("/") != 0) {
			strUrl = "/" + strUrl;
		}
		return "http://" + getRequestHost(request) + strUrl;
	}
	
	/**
	 * <p>URL 경로에 https://[host]가 없을 경우 추가해서 리턴한다.</p>
	 * 
	 * <pre>
	 * HeaderUtil.addHttpsHost(HttpServletRequest, null)        = ""
	 * HeaderUtil.addHttpsHost(HttpServletRequest, "   ")       = ""
	 * HeaderUtil.addHttpsHost(HttpServletRequest, "http://*")  = "https://*"
	 * HeaderUtil.addHttpsHost(HttpServletRequest, "https://*") = "https://*"
	 * HeaderUtil.addHttpsHost(HttpServletRequest, "index.jsp") = "https://host/index.jsp"
	 * </pre>
	 * 
	 * @param request (HttpServletRequest)
	 * @param strUrl  (URL 문자열)
	 * @return String (https://[host]를 추가한 URL)
	 */
	public static String addHttpsHost(HttpServletRequest request, String strUrl) {
		if (StringUtil.isBlank(strUrl)) {
			return "";
		}
		if (strUrl.indexOf("http://") == 0) {
			return strUrl.replace("http://", "https://");
		}
		if (strUrl.indexOf("https://") == 0) {
			return strUrl;
		}
		if (strUrl.indexOf("/") != 0) {
			strUrl = "/" + strUrl;
		}
		return "https://" + getRequestHost(request) + strUrl;
	}
	
	/**
	 * <p>URL 경로에 스키마가 없을 경우 http:// 스키마를 추가해서 리턴한다.</p>
	 * 
	 * <pre>
	 * HeaderUtil.addHttpScheme(null)              = ""
	 * HeaderUtil.addHttpScheme("   ")             = ""
	 * HeaderUtil.addHttpScheme("http://*")        = "http://*"
	 * HeaderUtil.addHttpScheme("https://*")       = "https://*"
	 * HeaderUtil.addHttpScheme("index.jsp")       = "http://index.jsp"
	 * HeaderUtil.addHttpScheme("redpeople.co.kr") = "http://redpeople.co.kr"
	 * </pre>
	 * 
	 * @param obj     (URL 문자형 객체)
	 * @return String (http:// 스키마를 추가한 URL)
	 */
	public static String addHttpScheme(Object obj) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		String str = StringUtil.clean(obj);
		if (str.indexOf("http://") == 0) {
			return str;
		}
		if (str.indexOf("https://") == 0) {
			return str;
		}
		return "http://" + str;
	}
	
	/**
	 * <p>사용자로부터 넘어오는 파라미터를 맵에 저장해서 리턴한다.</p>
	 * <p>단일 파라미터 값은 String 형이고, 복수 파라미터 값은 {@literal List<String>} 형이다.</p>
	 * 
	 * <pre>
	 * HeaderUtil.getParameterMap(HttpServletRequest) = {@literal Map<String, Object>}
	 * </pre>
	 * 
	 * @param request (HttpServletRequest)
	 * @return Map    ({@literal Map<String, Object>} 객체)
	 */
	public static Map<String, Object> getParameterMap(HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Enumeration<?> enumer = request.getParameterNames();
		String key = "";
		String[] values = null;
		while (enumer.hasMoreElements()) {
			key = (String)enumer.nextElement();
			values = request.getParameterValues(key);
			if (values.length == 1) {
				paramMap.put(key, values[0]);
			} else {
				paramMap.put(key, ObjectUtil.arrayToList(values));
			}
		}
		return paramMap;
	}
	
	/**
	 * <p>Request Header에서 해당하는 헤더 이름의 값을 리턴한다.</p>
	 * 
	 * <pre>
	 * HeaderUtil.getHeaderValue(HttpServletRequest, null)             = ""
	 * HeaderUtil.getHeaderValue(HttpServletRequest, "")               = ""
	 * HeaderUtil.getHeaderValue(HttpServletRequest, "Content-Length") = "1393"
	 * HeaderUtil.getHeaderValue(HttpServletRequest, "Content-Type")   = "text/html;charset=UTF-8"
	 * HeaderUtil.getHeaderValue(HttpServletRequest, "Connection")     = "keep-alive"
	 * </pre>
	 * 
	 * @param request    (HttpServletRequest)
	 * @param headerName (헤더 이름)
	 * @return String    (헤더 값)
	 */
	public static String getHeaderValue(HttpServletRequest request, String headerName) {
		String result = "";
		String name = "";
		Enumeration<String> enumer = request.getHeaderNames();
		while(enumer.hasMoreElements()) {
			name = StringUtil.clean(enumer.nextElement());
			if (name.matches("(?i)" + headerName)) {
				result = request.getHeader(name);
				break;
			}
		}
		return result;
	}
	
	/**
	 * <p>URLConnection 응답 Header에서 해당하는 헤더 이름의 값을 리턴한다.</p>
	 * 
	 * <pre>
	 * HeaderUtil.getHeaderValue(URLConnection, null)             = ""
	 * HeaderUtil.getHeaderValue(URLConnection, "")               = ""
	 * HeaderUtil.getHeaderValue(URLConnection, "Content-Length") = "1393"
	 * HeaderUtil.getHeaderValue(URLConnection, "Content-Type")   = "text/html;charset=UTF-8"
	 * HeaderUtil.getHeaderValue(URLConnection, "Connection")     = "keep-alive"
	 * </pre>
	 * 
	 * @param conn       (URLConnection)
	 * @param headerName (헤더 이름)
	 * @return String    (헤더 값)
	 */
	public static String getHeaderValue(URLConnection conn, String headerName) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, List<String>> entry: conn.getHeaderFields().entrySet()) {
			if (!StringUtil.isBlank(entry.getKey()) && StringUtil.clean(entry.getKey()).equals(headerName)) {
				for (String value : entry.getValue()) {
					sb.append(value);
				}
				break;
			}
		}
		return sb.toString();
	}
	
	/**
	 * <p>URLConnection 응답 헤더(header)를 구해서 리턴한다.</p>
	 * 
	 * @param conn       (URLConnection)
	 * @return String    (응답 헤더)
	 * @throws Exception (Exception)
	 */
	public static String getHeader(URLConnection conn) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, List<String>> entry: conn.getHeaderFields().entrySet()) {
			for (String value : entry.getValue()) {
				if (!StringUtil.isBlank(entry.getKey())) {
					sb.append(StringUtil.clean(entry.getKey())).append(": ");
				}
				sb.append(StringUtil.clean(value)).append("\n");
			}
		}
		return sb.toString();
	}
	
	/**
	 * <p>URLConnection 응답 내용(body)을 구해서 리턴한다.</p>
	 * 
	 * @param conn    (URLConnection)
	 * @return String (응답 내용)
	 * @throws Exception (Exception)
	 */
	public static String getBody(URLConnection conn) throws Exception {
		return getBody(conn, Const.ENCODING_TYPE);
	}
	
	/**
	 * <p>URLConnection 응답 내용(body)을 구해서 리턴한다.</p>
	 * 
	 * @param conn       (URLConnection)
	 * @param enc        (인코딩 타입)
	 * @return String    (응답 내용)
	 * @throws Exception (Exception)
	 */
	public static String getBody(URLConnection conn, String enc) throws Exception {
		String result = "";
		ByteArrayOutputStream baos = null;
		try {
			InputStream is = conn.getInputStream();
			baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] b = new byte[2048];
			while ((len = is.read(b)) != -1) {
				baos.write(b, 0, len);
			}
			b = null;
			result = baos.toString(enc);
		} catch (Exception e) {
			logger.error(ExceptionUtil.addMessage(e, "URLConnection 응답 내용(body) 읽기 오류가 발생했습니다!!"));
			throw e;
		} finally {
			if (baos != null) {try {baos.close();} catch (Exception e) {}}
		}
		return result;
	}
	
	/**
	 * <p>HttpServletRequest 본문 내용(body)을 구해서 리턴한다.</p>
	 * 
	 * @param request (HttpServletRequest)
	 * @return String (본문 내용)
	 * @throws Exception (Exception)
	 */
	public static String getBody(HttpServletRequest request) throws Exception {
		return getBody(request, Const.ENCODING_TYPE);
	}
	
	/**
	 * <p>HttpServletRequest 본문 내용(body)을 구해서 리턴한다.</p>
	 * 
	 * @param request (HttpServletRequest)
	 * @param enc     (인코딩 타입)
	 * @return String (본문 내용)
	 * @throws Exception (Exception)
	 */
	public static String getBody(HttpServletRequest request, String enc) throws Exception {
		String requestBody = "";
		ByteArrayOutputStream baos = null;
		try {
			InputStream is = request.getInputStream();
			baos = new ByteArrayOutputStream();
			int len = 0;
			byte[] b = new byte[2048];
			while ((len = is.read(b)) != -1) {
				baos.write(b, 0, len);
			}
			b = null;
			requestBody = baos.toString(enc);
		} catch (Exception e) {
			logger.error(ExceptionUtil.addMessage(e, "HttpServletRequest 본문 내용(body) 읽기 오류가 발생했습니다!!"));
			throw e;
		} finally {
			if (baos != null) {try {baos.close();} catch (Exception e) {}}
		}
		return requestBody;
	}
	
	/**
	 * <p>Header 정보를 출력한다.</p>
	 * 
	 * @param request  (HttpServletRequest)
	 * @param response (HttpServletResponse)
	 */
	public static void printHeader(HttpServletRequest request, HttpServletResponse response) {
		printHeader(request, response, false);
	}
	
	/**
	 * <p>Header 정보를 출력한다.</p>
	 * 
	 * @param request  (HttpServletRequest)
	 * @param response (HttpServletResponse)
	 * @param isClose  (스트림을 닫을지 여부)
	 */
	public static void printHeader(HttpServletRequest request, HttpServletResponse response, boolean isClose) {
		try {
			response.setContentType("text/html; charset=" + Const.ENCODING_TYPE);
			PrintWriter out = response.getWriter();
			Enumeration<?> enumer = request.getHeaderNames();
			String name, value = "";
			StringBuilder sb = new StringBuilder();
			sb.append("<table width=\"700px\" border=\"1px\" cellspacing=\"0px\" cellpadding=\"1px\" borderColorDark=\"white\" borderColorLight=\"silver\">\n");
			sb.append("    <caption><strong>HTTP 요청 헤더 정보</strong></caption>\n");
			sb.append("    <colgroup>\n");
			sb.append("    <col style=\"width:200px\" />\n");
			sb.append("    <col />\n");
			sb.append("    </colgroup>\n");
			sb.append("    <thead style=\"height:30px;background:silver;\">\n");
			sb.append("        <tr>\n");
			sb.append("            <th scope=\"col\">Header Name</th>\n");
			sb.append("            <th scope=\"col\">Header Value</th>\n");
			sb.append("        </tr>\n");
			sb.append("    </thead>\n");
			sb.append("    <tbody>\n");
			sb.append("        <tr>\n");
			sb.append("            <td>Request URL</td>\n");
			sb.append("            <td>").append(getRequestUrl(request)).append("</td>\n");
			sb.append("        </tr>\n");
			sb.append("        <tr>\n");
			sb.append("            <td>Request URI</td>\n");
			sb.append("            <td>").append(getRequestUri(request)).append("</td>\n");
			sb.append("        </tr>\n");
			sb.append("        <tr>\n");
			sb.append("            <td>Request QueryString</td>\n");
			sb.append("            <td>").append(request.getQueryString()).append("</td>\n");
			sb.append("        </tr>\n");
			sb.append("        <tr>\n");
			sb.append("            <td>Request Method</td>\n");
			sb.append("            <td>").append(request.getMethod()).append("</td>\n");
			sb.append("        </tr>\n");
			while(enumer.hasMoreElements()) {
				name = (String)enumer.nextElement();
				value = request.getHeader(name);
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
			logger.error(ExceptionUtil.addMessage(e, "HTTP 요청 헤더 정보 출력 에러."));
		}
	}
	
}
