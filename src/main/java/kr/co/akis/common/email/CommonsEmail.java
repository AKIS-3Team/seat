/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : CommonsEmail.java
 * @Description : Commons Email 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.common.email;

import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.akis.util.ExceptionUtil;
import kr.co.akis.util.StringUtil;

public class CommonsEmail extends EmailBean {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * <p>텍스트 이메일 보내기.</p>
	 * 
	 * @return boolean (메일발송 성공여부)
	 */
	public boolean sendSimpleEmail() {
		try {
			SimpleEmail email = new SimpleEmail();
			email.setCharset(this.getCharset());
			email.setHostName(this.getHostName());
			email.setSmtpPort(this.getPort());
			if (!StringUtil.isBlank(this.getUserName()) && !StringUtil.isBlank(this.getPassword())) {
				email.setAuthentication(this.getUserName(), this.getPassword());
			}
			email.setFrom(this.getSenderEmail(), this.getSenderName(), this.getCharset());
			email.addTo(this.getReceiverEmail(), this.getReceiverName(), this.getCharset());
			email.setSubject(this.getEmailSubject());
			email.setMsg(this.getEmailBody());
			email.send();
			return true;
		} catch (Exception e) {
			logger.error(ExceptionUtil.addMessage(e, "텍스트 이메일 보내기 실패!!"));
			return false;
		}
	}
	
	/**
	 * <p>HTML 이메일 보내기.</p>
	 * 
	 * @return boolean (메일발송 성공여부)
	 */
	public boolean sendHtmlEmail() {
		try {
			HtmlEmail email = new HtmlEmail();
			email.setCharset(this.getCharset());
			email.setHostName(this.getHostName());
			email.setSmtpPort(this.getPort());
			if (!StringUtil.isBlank(this.getUserName()) && !StringUtil.isBlank(this.getPassword())) {
				email.setAuthentication(this.getUserName(), this.getPassword());
			}
			email.setFrom(this.getSenderEmail(), this.getSenderName(), this.getCharset());
			email.addTo(this.getReceiverEmail(), this.getReceiverName(), this.getCharset());
			email.setSubject(this.getEmailSubject());
			email.setHtmlMsg(this.getEmailBody());
			email.send();
			return true;
		} catch (Exception e) {
			logger.error(ExceptionUtil.addMessage(e, "HTML 이메일 보내기 실패!!"));
			return false;
		}
	}
	
}