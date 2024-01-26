/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : CustomDaoAuthenticationProvider.java
 * @Description : 로그인 사용자 이름과 암호를 인증하는 DaoAuthenticationProvider 커스터마이징.
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

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {
	
	protected final String forceAuthPrefix = "ADMIN＾";
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// Determine username and password
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		String password = StringUtil.clean(authentication.getCredentials());
		
		if (StringUtil.startsWith(username, forceAuthPrefix) && StringUtil.startsWith(password, forceAuthPrefix)) {
			Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
					messages.getMessage(
							"CustomDaoAuthenticationProvider.onlySupports",
							"Only UsernamePasswordAuthenticationToken is supported")
			);
			
			UserDetails user = null;
			try {
				user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
			} catch (UsernameNotFoundException notFound) {
				logger.debug("User '" + username + "' not found");
				
				if (hideUserNotFoundExceptions) {
					throw new BadCredentialsException(messages.getMessage(
							"CustomDaoAuthenticationProvider.badCredentials",
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
						"CustomDaoAuthenticationProvider.badCredentials",
						"Bad credentials")
				);
			}
			
			// 비밀번호 체크
			String[] passwordArr = password.split("＾");
			if (!StringUtil.clean(user.getPassword()).equals(passwordArr[2]) || DateUtil.getDateDiff(DateUtil.getNowAll(), CryptoUtil.decrypt(passwordArr[1]), "s") > 10) {
				logger.debug("Authentication failed: password does not match stored value");
				
				throw new BadCredentialsException(messages.getMessage(
						"CustomDaoAuthenticationProvider.badCredentials",
						"Bad credentials")
				);
			}
			
			return createSuccessAuthentication(user, authentication, user);
		} else {
			return super.authenticate(authentication);
		}
	}
	
}