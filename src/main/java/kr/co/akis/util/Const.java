/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : Const.java
 * @Description : 프로그램에서 공통으로 사용되는 변수 및 상수 정의 클래스. 
 *                global.properties, application-${spring.profiles.active}.properties 파일을
 *                읽어서 기본변수 및 상수를 정의한다.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import org.springframework.web.context.ContextLoader;

import egovframework.rte.fdl.property.impl.EgovPropertyServiceImpl;

public class Const {
	
	public static final EgovPropertyServiceImpl properties; // Properties Loader
	
	private static final String ACTIVE_PROFILES;            // spring.profiles.active
	public static final boolean IS_ACTIVE_LOCAL;            // 로컬서버 여부
	public static final boolean IS_ACTIVE_DEV;              // 개발서버 여부
	public static final boolean IS_ACTIVE_PROD;             // 운영서버 여부
	public static final String ENCODING_TYPE;               // Project 인코딩 타입
	public static final String ROOT_PATH;                   // Project Root 경로
	public static final String UPLOAD_PATH;                 // Project 파일 업로드 경로
	
	static {
		properties = (EgovPropertyServiceImpl) ContextLoader.getCurrentWebApplicationContext().getBean("propertiesService");
		
		ACTIVE_PROFILES = properties.getString("spring.profiles.active");
		IS_ACTIVE_LOCAL = "local".equals(ACTIVE_PROFILES);
		IS_ACTIVE_DEV = "dev".equals(ACTIVE_PROFILES);
		IS_ACTIVE_PROD = "prod".equals(ACTIVE_PROFILES);
		ENCODING_TYPE = properties.getString("project.encoding.type");
		ROOT_PATH = properties.getString("project.root.path");
		UPLOAD_PATH = properties.getString("project.upload.path");
	}
	
}