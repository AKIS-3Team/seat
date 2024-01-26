/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : ExceptionUtil.java
 * @Description : Exception 유틸 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);
	
	/**
	 * <p>Error 정보를 Console에 출력한다.</p>
	 * 
	 * @param e       (Exception 객체)
	 * @param clazz   (에러가 발생한 Class 객체)
	 * @param message (추가할 Exception 메시지)
	 */
	public static void printConsole(Exception e, Class<?> clazz, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("+---------------------------------------------------------------------------------------------------------------------------------+\n");
		sb.append("+---------------------------------------------------------------------------------------------------------------------------------+\n");
		sb.append("[").append(DateUtil.getNowAll()).append("] [ERROR] ").append(clazz.getName()).append("\n");
		sb.append("(").append(e.toString()).append(") - ").append(StringUtil.clean(message)).append("\n");
		sb.append("+---------------------------------------------------------------------------------------------------------------------------------+\n");
		sb.append("+---------------------------------------------------------------------------------------------------------------------------------+");
		logger.error(sb.toString());
		sb = null;
		e.printStackTrace();
	}
	
	/**
	 * <p>짧은 Exception StackTrace를 리턴한다.</p>
	 * 
	 * @param e       (Exception 객체)
	 * @return String (Exception StackTrace)
	 */
	public static String getShortStackTrace(Exception e) {
		StackTraceElement[] ste = e.getStackTrace();
		String className = ste[0].getClassName();
		String methodName = ste[0].getMethodName();
		String fileName = ste[0].getFileName();
		int lineNumber = ste[0].getLineNumber();
		String exceptionClassName = StringUtil.clean(e.getClass()).replaceFirst("class ", "");
		StringBuilder sb = new StringBuilder();
		sb.append(exceptionClassName).append("\n");
		sb.append(exceptionClassName).append(": ").append(e.getMessage()).append("\n");
		sb.append("\tat ").append(className).append(".").append(methodName);
		sb.append("(").append(fileName).append(":").append(lineNumber).append(")");
		return sb.toString();
	}
	
	/**
	 * <p>Exception StackTrace를 리턴한다.</p>
	 * 
	 * @param e       (Exception 객체)
	 * @return String (Exception StackTrace)
	 */
	public static String getStackTrace(Exception e) {
		StringBuffer sb = null;
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			sb = sw.getBuffer();
			return sb.toString();
		} catch (Exception e2) {
			logger.warn("Exception StackTrace를 가져올 수 없습니다.");
		} finally {
			if (pw != null) try { pw.close(); } catch (Exception e2) {};
			if (sw != null) try { sw.close(); } catch (Exception e2) {};
		}
		return "";
	}
	
	/**
	 * <p>Exception 메시지에 사용자 메시지를 추가하여 리턴한다.</p>
	 * 
	 * @param e       (Exception 객체)
	 * @param message (추가할 Exception 메시지)
	 * @return String (Exception 메시지)
	 */
	public static String addMessage(Exception e, String message) {
		StringBuilder sb = new StringBuilder();
		sb.append(e.toString()).append("\n");
		sb.append("Stacktrace: ").append(getStackTrace(e));
		sb.append("Caused by: ").append(message).append("\n");
		return sb.toString();
	}
	
}