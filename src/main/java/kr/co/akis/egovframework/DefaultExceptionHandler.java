/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : DefaultExceptionHandler.java
 * @Description : Exception 별 특정 로직(후처리 로직이라고 부르기도 함)을 흐를 수 있도록 하여 Exception 에 따른 
 *                적절한 대응이 가능도록 하고자 함.
 * @Version     : 3.0.0
 * @History     : [2011.11.29] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.egovframework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;

public class DefaultExceptionHandler implements ExceptionHandler {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void occur(Exception ex, String packageName) {
		logger.error("--------------------------------------------------------------------------------");
		logger.error("DefaultExceptionHandler run...............");
		logger.error("Package Name : " + packageName);
		if ("kr.co.akis.common.exception.NullDataException".equals(ex.getClass().getName())) {
			logger.error("Caused by: 해당 데이터를 찾을 수 없거나 또는 삭제 되었습니다!!");
		} else {
			logger.error("Caused by: " + ex.getMessage());
		}
		logger.error("--------------------------------------------------------------------------------");
	}
	
}
