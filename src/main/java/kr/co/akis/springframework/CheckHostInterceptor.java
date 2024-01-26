/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : CheckHostInterceptor.java
 * @Description : 현재 페이지와 이전 페이지의 Host가 같은지 체크하는 Interceptor 클래스.
 * @Version     : 3.0.0
 * @History     : [2010.09.06] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.springframework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.akis.util.HeaderUtil;

public class CheckHostInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private AntPathMatcher antPathMatcher;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * <p>현재 페이지와 이전 페이지의 Host가 같은지 체크한다.</p>
	 * 
	 * @param request  (HttpServletRequest)
	 * @param response (HttpServletResponse)
	 * @param Object   (Interceptor 대상 컨트롤러 객체)
	 * @return boolean (컨트롤러 실행 여부)
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!HeaderUtil.equalsHost(request)) {
			StringBuilder sb = new StringBuilder();
			sb.append("Illegal Access (").append(handler.getClass().getName()).append(")\n");
			sb.append("+------------------------------------------------------------------------------------------------------------------+\n");
			sb.append("+------------------------------------------------------------------------------------------------------------------+\n");
			sb.append("경고 내용 : 해당 페이지에 불법적인 방법으로 접근 하였습니다.\n");
			sb.append("접근 URL  : ").append(HeaderUtil.getRequestUrl(request)).append("\n");
			sb.append("이전 HOST : ").append(HeaderUtil.getRefererHost(request)).append("\n");
			sb.append("접근 IP   : ").append(request.getRemoteAddr()).append("\n");
			sb.append("+------------------------------------------------------------------------------------------------------------------+\n");
			sb.append("+------------------------------------------------------------------------------------------------------------------+");
			logger.warn(sb.toString());
			sb = null;
			
			String redirectUrl = "/";
			String rootFolderName = HeaderUtil.getFolderName(request, 1);
			if (rootFolderName.equals("siteAdmin")) {
				redirectUrl = "/siteAdmin/index";
			} else if (rootFolderName.equals("eng")) {
				redirectUrl = "/eng/index";
			} else if (antPathMatcher.match("/sample/siteAdmin/**", HeaderUtil.getRequestUri(request))) {
				redirectUrl = "/sample/siteAdmin/index";
			} else if (antPathMatcher.match("/sample/**", HeaderUtil.getRequestUri(request))) {
				redirectUrl = "/sample/index";
			}
			response.sendRedirect(redirectUrl);
			return false;
		}
		
		return true;
	}
	
}
