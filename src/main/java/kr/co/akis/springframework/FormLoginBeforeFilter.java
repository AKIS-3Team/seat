/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : FormLoginBeforeFilter.java
 * @Description : FORM_LOGIN_FILTER 이전에 중복 로그인 등을 체크하기 위한 필터.
 * @Version     : 3.0.0
 * @History     : [2011.11.29] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.springframework;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.GenericFilterBean;

import kr.co.akis.util.StringUtil;

public class FormLoginBeforeFilter extends GenericFilterBean {
	
	@Autowired
	private AntPathMatcher antPathMatcher;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final UserDetailsService userDetailsService;
	private String loginPage = "/login";
	private String loginProcessingUrl = "/loginProcess";
	private String usernameParameter = "j_username";
	private String passwordParameter = "j_password";
	private String rememberMeParameter = "remember-me";
	
	/**
	 * Creates a new instance.
	 */
	public FormLoginBeforeFilter(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	/**
	 * Setter Method
	 */
	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}
	public void setLoginProcessingUrl(String loginProcessingUrl) {
		this.loginProcessingUrl = loginProcessingUrl;
	}
	public void setUsernameParameter(String usernameParameter) {
		this.usernameParameter = usernameParameter;
	}
	public void setPasswordParameter(String passwordParameter) {
		this.passwordParameter = passwordParameter;
	}
	public void setRememberMeParameter(String rememberMeParameter) {
		this.rememberMeParameter = rememberMeParameter;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		if (antPathMatcher.match(request.getRequestURI(), loginProcessingUrl)) {
			// 파라미터 정의
			String userId = StringUtil.clean(request.getParameter(usernameParameter));
			String password = StringUtil.clean(request.getParameter(passwordParameter));
			String rememberMe = StringUtil.clean(request.getParameter(rememberMeParameter));
			String forceLogin = StringUtil.clean(request.getParameter("forceLogin"));
			
			// 로그인이 되어있을 경우 인증정보 및 세션을 삭제한다.
			SecurityContext securityContext = SecurityContextHolder.getContext();
			Authentication authentication = securityContext.getAuthentication();
			if (authentication != null && authentication.getPrincipal() != null) {
				securityContext.setAuthentication(null);
				HttpSession session = request.getSession();
				session.invalidate();
			}
			
			// 중복된 로그인을 체크한다.
			if (!"Y".equals(forceLogin)) {
				UserDetails details = null;
				try {
					details = userDetailsService.loadUserByUsername(userId);
				} catch (UsernameNotFoundException e) {}
				if (details != null && passwordEncoder.matches(password, details.getPassword())) {
					if (isLoginUser(userId)) {
						HttpSession session = request.getSession();
						session.setAttribute("SPRING_SECURITY_TEMP_userId", userId);
						session.setAttribute("SPRING_SECURITY_TEMP_password", password);
						session.setAttribute("SPRING_SECURITY_TEMP_rememberMe", rememberMe);
						response.sendRedirect(loginPage + "?error=3");
						return;
					}
				}
			}
		}
		
		chain.doFilter(req, res);
	}
	
	/**
	 * 해당 아이디로 이미 로그인한 사용자가 있는지 리턴한다.
	 * 
	 * @param userId   (사용자 아이디)
	 * @return boolean (중복 로그인 여부)
	 */
	public boolean isLoginUser(String userId) {
		if (userId != null && sessionRegistry != null) {
			for (Object principal: sessionRegistry.getAllPrincipals()) {
				for (SessionInformation sessionInformation: sessionRegistry.getAllSessions(principal, false)) {
					Object userPrincipal = sessionInformation.getPrincipal();
					if (userPrincipal instanceof User) {
						User user = (User) userPrincipal;
						if (userId.equals(user.getUsername())) {
							return true;
						}
					}
				}
			}
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("\n");
			sb.append("+---------------------------------------------------------------------------+\n");
			sb.append("- userId is null = ").append(userId == null).append("\n");
			sb.append("- sessionRegistry is null = ").append(sessionRegistry == null).append("\n");
			sb.append("+---------------------------------------------------------------------------+\n");
			logger.error(sb.toString());
			sb = null;
		}
		return false;
	}
	
}