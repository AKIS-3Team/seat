/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : LoginAuthenticationSuccessHandler.java
 * @Description : 로그인 성공시 부가적인 작업을 하기 위한 Handler 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.springframework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class LoginAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		// http 와 https 이동간에 세션이 끊기지 않도록 하기위해 인증 세션을 가져와 새로운 쿠키 생성한다.
		Cookie cookie = new Cookie("JSESSIONID", request.getSession().getId());
		String contextPath = request.getContextPath();
		if (contextPath != null && contextPath.length() > 0) {
			cookie.setPath(contextPath);
		} else {
			cookie.setPath("/");
		}
		response.addCookie(cookie);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}