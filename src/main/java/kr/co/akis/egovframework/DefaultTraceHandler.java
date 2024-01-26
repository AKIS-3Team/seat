/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : DefaultTraceHandler.java
 * @Description : Exception 이거나 Exception 이 아닌 경우에 Trace 후처리 로직을 실행 시키고자 할 때 사용한다.
 * @Version     : 3.0.0
 * @History     : [2011.11.29] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.egovframework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.rte.fdl.cmmn.trace.handler.TraceHandler;

public class DefaultTraceHandler implements TraceHandler {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@SuppressWarnings("rawtypes")
	public void todo(Class clazz, String message) {
		logger.info("--------------------------------------------------------------------------------");
		logger.info("DefaultTraceHandler run...............");
		logger.info("Package Name : " + clazz.getName());
		logger.info("Message : " + message);
		logger.info("--------------------------------------------------------------------------------");
	}
	
}