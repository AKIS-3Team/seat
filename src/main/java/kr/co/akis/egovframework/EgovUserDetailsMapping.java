/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : EgovUserDetailsMapping.java
 * @Description : 로그인 사용자 정보 Mapping Class.
 * @Version     : 3.0.0
 * @History     : [2011.11.29] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.egovframework;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import egovframework.rte.fdl.security.userdetails.EgovUserDetails;
import egovframework.rte.fdl.security.userdetails.jdbc.EgovUsersByUsernameMapping;

public class EgovUserDetailsMapping extends EgovUsersByUsernameMapping {
	
	public EgovUserDetailsMapping(DataSource ds, String usersByUsernameQuery) {
		super(ds, usersByUsernameQuery);
	}
	
	@Override
	protected EgovUserDetails mapRow(ResultSet rs, int rownum) throws SQLException {
		Integer mberSid = rs.getInt("MBER_SID");
		String userId = rs.getString("USER_ID");
		String password = rs.getString("PASSWORD");
		String userNm = rs.getString("USER_NM");
		String email = rs.getString("EMAIL");
		boolean enabled = rs.getBoolean("ENABLED");
		
		EgovUserDetailsVO userVo = new EgovUserDetailsVO();
		userVo.setMberSid(mberSid);
		userVo.setUserId(userId);
		userVo.setPassword(password);
		userVo.setUserNm(userNm);
		userVo.setEmail(email);
		userVo.setEnabled(enabled);
		
		return new EgovUserDetails(userId, password, enabled, userVo);
	}
	
}