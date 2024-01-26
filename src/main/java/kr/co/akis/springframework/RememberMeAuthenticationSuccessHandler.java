/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : RememberMeAuthenticationSuccessHandler.java
 * @Description : remember-me 로그인 성공시 부가적인 작업을 하기 위한 Handler 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.springframework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class RememberMeAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		super.setAlwaysUseDefaultTargetUrl(true);
		super.setDefaultTargetUrl(request.getRequestURL() == null ? "/" : request.getRequestURL().toString());
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}