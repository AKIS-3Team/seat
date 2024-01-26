/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : EgovBindingInitializer.java
 * @Description : 스프링에서 파라미터를 오브젝트에 자동으로 바인딩 할 때 개발자가 정의한 타입으로 자동으로
 *                형 변환을 하기 위해서 커스텀 프로퍼티 에디터(사용자 정의 편집기)를 등록한다.
 * @Version     : 3.0.0
 * @History     : [2011.11.29] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.egovframework;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class EgovBindingInitializer implements WebBindingInitializer {
	
	public void initBinder(WebDataBinder binder, WebRequest request) {
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//dateFormat.setLenient(false);
		//binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}
	
}
