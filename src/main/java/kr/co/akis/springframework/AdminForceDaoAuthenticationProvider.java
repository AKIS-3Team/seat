/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : AdminForceDaoAuthenticationProvider.java
 * @Description : 관리자에서 강제로 해당 사용자로 로그인하는 Authentication Provider.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.springframework;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import kr.co.akis.egovframework.CryptoUtil;
import kr.co.akis.util.DateUtil;
import kr.co.akis.util.StringUtil;

public class AdminForceDaoAuthenticationProvider extends DaoAuthenticationProvider {
	
	protected final String forceAuthPrefix = "ADMIN＾";
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// Determine username and password
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		String password = StringUtil.clean(authentication.getCredentials());
		
		if (StringUtil.startsWith(username, forceAuthPrefix) && StringUtil.startsWith(password, forceAuthPrefix)) {
			Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
					messages.getMessage(
							"AdminForceDaoAuthenticationProvider.onlySupports",
							"Only UsernamePasswordAuthenticationToken is supported")
			);
			
			UserDetails user = null;
			String[] usernameArr = StringUtil.clean(username).split("＾");
			try {
				user = retrieveUser(usernameArr[1], (UsernamePasswordAuthenticationToken) authentication);
			} catch (UsernameNotFoundException notFound) {
				logger.debug("User '" + usernameArr[1] + "' not found");
				
				if (hideUserNotFoundExceptions) {
					throw new BadCredentialsException(messages.getMessage(
							"AdminForceDaoAuthenticationProvider.badCredentials",
							"Bad credentials")
					);
				} else {
					throw notFound;
				}
			}
			
			Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
			
			if (authentication.getCredentials() == null) {
				logger.debug("Authentication failed: no credentials provided");
				
				throw new BadCredentialsException(messages.getMessage(
						"AdminForceDaoAuthenticationProvider.badCredentials",
						"Bad credentials")
				);
			}
			
			// 비밀번호 체크
			String[] passwordArr = password.split("＾");
			if (!StringUtil.clean(user.getPassword()).equals(passwordArr[2]) || DateUtil.getDateDiff(DateUtil.getNowAll(), CryptoUtil.decrypt(passwordArr[1]), "s") > 10) {
				logger.debug("Authentication failed: password does not match stored value");
				
				throw new BadCredentialsException(messages.getMessage(
						"AdminForceDaoAuthenticationProvider.badCredentials",
						"Bad credentials")
				);
			}
			
			return createSuccessAuthentication(user, authentication, user);
		} else {
			logger.debug("Authentication skip: username, password does not match force login");
			
			throw new BadCredentialsException(messages.getMessage(
					"AdminForceDaoAuthenticationProvider.badCredentials",
					"Skip credentials")
			);
		}
	}
	
}