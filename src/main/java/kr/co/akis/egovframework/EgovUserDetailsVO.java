/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : EgovUserDetailsVO.java
 * @Description : 로그인 사용자 정보 VO Class.
 * @Version     : 3.0.0
 * @History     : [2011.11.29] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.egovframework;

public class EgovUserDetailsVO {
	
	/** 회원_고유키 */
	private Integer mberSid;
	
	/** 사용자_ID */
	private String userId;
	
	/** 비밀번호 */
	private String password;
	
	/** 사용자_이름 */
	private String userNm;
	
	/** 이메일 */
	private String email;
	
	/** 활성여부 */
	private boolean enabled;

	public Integer getMberSid() {
		return mberSid;
	}

	public void setMberSid(Integer mberSid) {
		this.mberSid = mberSid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}