/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : CustomEgovJdbcUserDetailsManager.java
 * @Description : 사용자 계정 정보를 DB에서 관리할수 있도록 구현한 EgovJdbcUserDetailsManager 커스터마이징.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.egovframework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import egovframework.rte.fdl.security.userdetails.EgovUserDetails;
import egovframework.rte.fdl.security.userdetails.jdbc.EgovJdbcUserDetailsManager;
import kr.co.akis.util.StringUtil;

public class CustomEgovJdbcUserDetailsManager extends EgovJdbcUserDetailsManager {
	
	protected final String forceAuthPrefix = "ADMIN＾";
	protected RoleHierarchy roleHierarchy = null;
	
	@Override
	public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
		this.roleHierarchy = roleHierarchy;
	}
	
	@Override
	public EgovUserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		if (StringUtil.startsWith(username, forceAuthPrefix)) {
			String[] usernameArr = StringUtil.clean(username).split("＾");
			
			List<UserDetails> users = loadUsersByUsername(usernameArr[1]);
			
			if (users.size() == 0) {
				logger.debug("Query returned no results for user '" + usernameArr[1] + "'");
				throw new UsernameNotFoundException(messages.getMessage("CustomEgovJdbcUserDetailsManager.notFound", new Object[]{usernameArr[1]}, "Username {0} not found"));
			}
			
			UserDetails obj = users.get(0);
			EgovUserDetails userDetails = (EgovUserDetails) obj;
			
			Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();
			dbAuthsSet.addAll(loadUserAuthorities(usernameArr[1]));
			
			List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);
			addCustomAuthorities(username, dbAuths);
			
			if (dbAuths.size() == 0) {
				throw new UsernameNotFoundException(messages.getMessage("CustomEgovJdbcUserDetailsManager.noAuthority", new Object[]{usernameArr[1]}, "User {0} has no GrantedAuthority"));
			}
			
			// RoleHierarchyImpl 에서 저장한 Role Hierarchy 정보가 저장된다.
			Collection<? extends GrantedAuthority> authorities = roleHierarchy.getReachableGrantedAuthorities(dbAuths);
			
			// JdbcDaoImpl 클래스의 createUserDetails 메소드 재정의
			return new EgovUserDetails(username, userDetails.getPassword(), userDetails.isEnabled(), true, true, true, authorities, userDetails.getEgovUserVO());
		} else {
			super.setRoleHierarchy(roleHierarchy);
			return super.loadUserByUsername(username);
		}
	}
	
}