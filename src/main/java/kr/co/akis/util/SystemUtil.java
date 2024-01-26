/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : SystemUtil.java
 * @Description : System 유틸 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemUtil.class);
	
	/**
	 * <p>WAS 종류를 리턴한다.</p>
	 * 
	 * <pre>
	 * SystemUtil.getWas() = "tomcat"
	 * SystemUtil.getWas() = "jeus"
	 * SystemUtil.getWas() = "weblogic"
	 * SystemUtil.getWas() = "other"
	 * </pre>
	 * 
	 * @return String (WAS 종류)
	 */
	public static String getWas() {
		if (System.getProperty("tomcat.home") != null || System.getProperty("catalina.home") != null) {
			return "tomcat";
		} else if (System.getProperty("jeus.home") != null) {
			return "jeus";
		} else if (System.getProperty("weblogic.Name") != null) {
			return "weblogic";
		} else {
			return "other";
		}
	}
	
	/**
	 * <p>웹서버의 IP 주소를 리턴한다. (서버 이중화시 L4가 아닌 연결된 웹서버의 IP 주소를 리턴한다.)</p>
	 * 
	 * <pre>
	 * SystemUtil.getServerIp() = "74.125.71.106"
	 * </pre>
	 * 
	 * @return String (웹서버 IP)
	 */
	public static String getServerIp() {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			return inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			logger.error(ExceptionUtil.addMessage(e, "SystemUtil.getServerIp 에서 UnknownHostException 발생."));
			return "";
		}
	}
	
	/**
	 * <p>현재 Host의 IP 주소를 리턴한다. (서버 이중화시 L4의 IP 주소를 리턴한다.)</p>
	 * 
	 * <pre>
	 * SystemUtil.getHostIp(request) = "74.125.71.106"
	 * </pre>
	 * 
	 * @param request (HttpServletRequest)
	 * @return String (현재 Host의 IP 주소)
	 */
	public static String getHostIp(HttpServletRequest request) {
		return getHostIp(request.getServerName());
	}
	
	/**
	 * <p>해당 Host의 IP 주소를 리턴한다. (서버 이중화시 L4의 IP 주소를 리턴한다.)</p>
	 * 
	 * <pre>
	 * SystemUtil.getHostIp("www.redpeople.co.kr") = "119.206.195.246"
	 * SystemUtil.getHostIp("www.google.co.kr")    = "74.125.71.106"
	 * SystemUtil.getHostIp("www.naver1.com")      = ""
	 * </pre>
	 * 
	 * @param hostName (Host Name)
	 * @return String  (해당 Host의 IP 주소)
	 */
	public static String getHostIp(String hostName) {
		try {
			InetAddress inetAddress = InetAddress.getByName(hostName);
			return inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			logger.error(ExceptionUtil.addMessage(e, "SystemUtil.getHostIp 에서 UnknownHostException 발생."));
			return "";
		}
	}
	
	/**
	 * <p>https 통신 SSL security 설정</p>
	 */
	public static void disableSslVerification() {
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}
					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}
					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}
				}
			};
			
			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			
			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			
			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (Exception e) {
			logger.error(ExceptionUtil.addMessage(e, "https 통신 SSL security 설정 오류!!"));
		}
	}
	
	/**
	 * <p>Console에 문자 또는 숫자를 출력한다.</p>
	 * 
	 * @param obj (출력할 문자형, 숫자형 객체)
	 */
	public static void printConsole(Object obj) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("+---------------------------------------------------------------------------------------------------------------------------------+\n");
		sb.append("+---------------------------------------------------------------------------------------------------------------------------------+\n");
		sb.append(String.valueOf(obj)).append("\n");
		sb.append("+---------------------------------------------------------------------------------------------------------------------------------+\n");
		sb.append("+---------------------------------------------------------------------------------------------------------------------------------+");
		logger.debug(sb.toString());
		sb = null;
	}
	
	/**
	 * <p>Console에 Map 데이터를 출력한다.</p>
	 * 
	 * @param dataMap (출력할 Map 데이터)
	 */
	public static void printConsole(Map<String, String> dataMap) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("+---------------------------------------------------------------------------------------------------------------------------------+\n");
		sb.append("+---------------------------------------------------------------------------------------------------------------------------------+\n");
		for (Iterator<String> iter = dataMap.keySet().iterator(); iter.hasNext();) {
			String key = iter.next();
			String val = dataMap.get(key);
			sb.append(key).append(" = ").append(val).append("\n");
			if (iter.hasNext()) {
				sb.append("--------------------------------------------\n");
			}
		}
		sb.append("+---------------------------------------------------------------------------------------------------------------------------------+\n");
		sb.append("+---------------------------------------------------------------------------------------------------------------------------------+");
		logger.debug(sb.toString());
		sb = null;
	}
	
}