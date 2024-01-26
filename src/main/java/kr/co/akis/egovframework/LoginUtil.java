/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : LoginUtil.java
 * @Description : 사용자 로그인 정보 관련 유틸 클래스.
 * @Version     : 3.0.0
 * @History     : [2011.11.29] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.egovframework;

import java.util.List;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

public class LoginUtil {
	
	/**
	 * 로그인 사용자 객체(VO)를 리턴한다.
	 * 
	 * @return EgovUserDetailsVO (인증된 사용자 객체)
	 */
	public static EgovUserDetailsVO getUserVo() {
		if (EgovUserDetailsHelper.isAuthenticated() && EgovUserDetailsHelper.getAuthenticatedUser() != null) {
			return (EgovUserDetailsVO) EgovUserDetailsHelper.getAuthenticatedUser();
		}
		return null;
	}
	
	/**
	 * 로그인 사용자의 고유키를 리턴한다.
	 * 
	 * @return Integer (사용자 고유키)
	 */
	public static Integer getMberSid() {
		EgovUserDetailsVO userVo = getUserVo();
		if (userVo == null) {
			return null;
		}
		return userVo.getMberSid();
	}
	
	/**
	 * 로그인 사용자의 ID를 리턴한다.
	 * 
	 * @return String (사용자 ID)
	 */
	public static String getUserId() {
		EgovUserDetailsVO userVo = getUserVo();
		if (userVo == null) {
			return null;
		}
		return userVo.getUserId();
	}
	
	/**
	 * 로그인 사용자의 이름을 리턴한다.
	 * 
	 * @return String (사용자 이름)
	 */
	public static String getUserNm() {
		EgovUserDetailsVO userVo = getUserVo();
		if (userVo == null) {
			return null;
		}
		return userVo.getUserNm();
	}
	
	/**
	 * 로그인 사용자의 권한 정보 목록을 리턴한다.
	 * 
	 * @return List<String> (사용자 권한 정보 목록)
	 */
	public static List<String> getAuthorities() {
		if (EgovUserDetailsHelper.isAuthenticated() && EgovUserDetailsHelper.getAuthenticatedUser() != null) {
			return EgovUserDetailsHelper.getAuthorities();
		}
		return null;
	}
	
	/**
	 * 로그인 사용자가 해당 권한 정보를 가지고 있는지 여부를 리턴한다.
	 * 
	 * @param role     (체크할 권한)
	 * @return boolean (권한 소유 여부)
	 */
	public static boolean hasRole(String role) {
		List<String> authorities = getAuthorities();
		if (role == null || authorities == null) {
			return false;
		}
		for (String authority: authorities) {
			if (role.equals(authority)) {
				return true;
			}
		}
		return false;
	}
	
}