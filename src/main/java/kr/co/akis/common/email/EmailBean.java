/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : EmailBean.java
 * @Description : Email Bean 클래스
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.common.email;

import kr.co.akis.util.Const;

public class EmailBean {
	
	/** 메시지 캐릭터셋 */
	private String charset;
	
	/** SMTP 서버 호스트명 */
	private String hostName;
	
	/** SMTP 서버 포트 */
	private int port;
	
	/** SMTP 서버 인증 ID */
	private String userName;
	
	/** SMTP 서버 인증 비밀번호 */
	private String password;
	
	/** 보내는사람 이메일 주소 */
	private String senderEmail;
	
	/** 보내는사람 이름 */
	private String senderName;
	
	/** 받는사람 이메일 주소 */
	private String receiverEmail;
	
	/** 받는사람 이름 */
	private String receiverName;
	
	/** 이메일 제목 */
	private String emailSubject;
	
	/** 이메일 내용 */
	private String emailBody;
	
	/**
	 * <p>Constructor</p>
	 */
	public EmailBean() {
		this.setCharset(Const.ENCODING_TYPE);
		this.setHostName(Const.properties.getString("mail.smtp.hostName"));
		this.setPort(Const.properties.getInt("mail.smtp.port"));
		this.setUserName(Const.properties.getString("mail.smtp.userName"));
		this.setPassword(Const.properties.getString("mail.smtp.password"));
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	
}