/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : NotLoginException.java
 * @Description : 데이터가 없을때 발생하는 Exception 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.common.exception;

@SuppressWarnings("serial")
public class NotLoginException extends RuntimeException {
	
	/**
	 * Constructs a new exception with <code>null</code> as its detail message.
	 */
	public NotLoginException() {
		super("로그인이 되어 있지 않습니다!!");
	}
	
	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param s - the detail message.
	 */
	public NotLoginException(String s) {
		super(s);
	}
	
}