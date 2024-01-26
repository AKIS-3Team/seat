/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : ConditionException.java
 * @Description : 해당하는 조건이 없을때 발생하는 Exception 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.common.exception;

@SuppressWarnings("serial")
public class ConditionException extends RuntimeException {

	/**
	 * Constructs a new exception with <code>null</code> as its detail message.
	 */
	public ConditionException() {
		super("지원하지 않는 잘못된 조건입니다!!");
	}
	
	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param s - the detail message.
	 */
	public ConditionException(String s) {
		super(s);
	}
	
}
