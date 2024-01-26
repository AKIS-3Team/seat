/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : JsUtil.java
 * @Description : 자바스크립트 유틸 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JsUtil.class);
	
	/**
	 * <p>JavaScript를 출력한다.</p>
	 * 
	 * @param response (HttpServletResponse)
	 * @param function (출력할 자바스크립트)
	 */
	public static void printScript(HttpServletResponse response, String function) {
		printScript(response, function, false);
	}
	
	/**
	 * <p>JavaScript를 출력한다.</p>
	 * 
	 * @param response   (HttpServletResponse)
	 * @param function   (출력할 자바스크립트)
	 * @param isClose    (스트림을 닫을지 여부)
	 */
	public static void printScript(HttpServletResponse response, String function, boolean isClose) {
		try {
			response.setContentType("text/html; charset=" + Const.ENCODING_TYPE);
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html lang=\"ko\">");
			out.println("<head>");
			out.println("    <title>자바스크립트 처리</title>");
			out.println("    <script>");
			out.println("    //<![CDATA[");
			out.println(function);
			out.println("    //]]>");
			out.println("    </script>");
			out.println("</head>");
			out.println("<body>");
			out.println("</body>");
			out.println("</html>");
			if (isClose) {
				out.close();
			}
		} catch (IOException e) {
			logger.error(ExceptionUtil.addMessage(e, "JavaScript 출력 에러."));
		}
	}
	
	/**
	 * <p>alert 메시지를 전송한다.</p>
	 * 
	 * @param response (HttpServletResponse)
	 * @param message  (전송할 메시지)
	 */
	public static void alert(HttpServletResponse response, String message) {
		printScript(response, "alert(\"" + message + "\");", false);
	}
	
	/**
	 * <p>팝업창을 Close 한다.</p>
	 * 
	 * @param response (HttpServletResponse)
	 */
	public static void close(HttpServletResponse response) {
		printScript(response, "self.close();", true);
	}
	
	/**
	 * <p>alert 메시지 전송후 팝업창을 Close 한다.</p>
	 * 
	 * @param response (HttpServletResponse)
	 * @param message  (전송할 메시지)
	 */
	public static void close(HttpServletResponse response, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append("alert(\"").append(message).append("\");\n");
		sb.append("self.close();");
		printScript(response, sb.toString(), true);
	}
	
	/**
	 * <p>이전 페이지로 이동한다.</p>
	 * 
	 * @param response (HttpServletResponse)
	 */
	public static void back(HttpServletResponse response) {
		printScript(response, "history.back();", true);
	}
	
	/**
	 * <p>alert 메시지 전송후 이전 페이지로 이동한다.</p>
	 * 
	 * @param response (HttpServletResponse)
	 * @param message  (전송할 메시지)
	 */
	public static void back(HttpServletResponse response, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append("alert(\"").append(message).append("\");\n");
		sb.append("history.back();");
		printScript(response, sb.toString(), true);
	}
	
	/**
	 * <p>location.href를 사용하여 해당 페이지로 이동한다.</p>
	 * 
	 * @param response (HttpServletResponse)
	 * @param linkUrl  (이동할 페이지 URL)
	 */
	public static void href(HttpServletResponse response, String linkUrl) {
		printScript(response, "location.href = \"" + linkUrl + "\";", true);
	}
	
	/**
	 * <p>location.href를 사용하여 alert 메시지 전송후 해당 페이지로 이동한다.</p>
	 * 
	 * @param response (HttpServletResponse)
	 * @param linkUrl  (이동할 페이지 URL)
	 * @param message  (전송할 메시지)
	 */
	public static void href(HttpServletResponse response, String linkUrl, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append("alert(\"").append(message).append("\");\n");
		sb.append("location.href = \"").append(linkUrl).append("\";");
		printScript(response, sb.toString(), true);
	}
	
	/**
	 * <p>location.replace를 사용하여 해당 페이지로 이동한다.</p>
	 * 
	 * @param response (HttpServletResponse)
	 * @param linkUrl  (이동할 페이지 URL)
	 */
	public static void replace(HttpServletResponse response, String linkUrl) {
		printScript(response, "location.replace(\"" + linkUrl + "\");", true);
	}
	
	/**
	 * <p>location.replace를 사용하여 alert 메시지 전송후 해당 페이지로 이동한다.</p>
	 * 
	 * @param response (HttpServletResponse)
	 * @param linkUrl  (이동할 페이지 URL)
	 * @param message  (전송할 메시지)
	 */
	public static void replace(HttpServletResponse response, String linkUrl, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append("alert(\"").append(message).append("\");\n");
		sb.append("location.replace(\"").append(linkUrl).append("\");");
		printScript(response, sb.toString(), true);
	}
	
	/**
	 * <p>메타태그를 이용해 파라미터로 받은 해당 페이지로 이동한다.</p>
	 * 
	 * @param response (HttpServletResponse)
	 * @param linkUrl  (이동할 페이지 URL)
	 */
	public static void metaRefresh(HttpServletResponse response, String linkUrl) {
		try {
			response.setContentType("text/html; charset=" + Const.ENCODING_TYPE);
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html lang=\"ko\">");
			out.println("<head>");
			out.println("    <title>페이지 이동</title>");
			out.println("    <meta http-equiv=\"refresh\" content=\"0;url=" + linkUrl + "\" />");
			out.println("</head>");
			out.println("<body>");
			out.println("</body>");
			out.println("</html>");
		} catch (IOException e) {
			logger.error(ExceptionUtil.addMessage(e, "메타태그를 이용한 페이지 이동 에러."));
		}
	}
	
	/**
	 * <p>alert 메시지 전송후 메타태그를 이용해 파라미터로 받은 해당 페이지로 이동한다.</p>
	 * 
	 * @param response (HttpServletResponse)
	 * @param linkUrl  (이동할 페이지 URL)
	 * @param message  (전송할 메시지)
	 */
	public static void metaRefresh(HttpServletResponse response, String linkUrl, String message) {
		try {
			response.setContentType("text/html; charset=" + Const.ENCODING_TYPE);
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html lang=\"ko\">");
			out.println("<head>");
			out.println("    <title>페이지 이동</title>");
			out.println("    <meta http-equiv=\"refresh\" content=\"0;url=" + linkUrl + "\" />");
			out.println("    <script>");
			out.println("    //<![CDATA[");
			out.println("        alert(\"" + message + "\");");
			out.println("    //]]>");
			out.println("    </script>");
			out.println("</head>");
			out.println("<body>");
			out.println("</body>");
			out.println("</html>");
		} catch (IOException e) {
			logger.error(ExceptionUtil.addMessage(e, "메타태그를 이용한 페이지 이동 에러."));
		}
	}
	
}