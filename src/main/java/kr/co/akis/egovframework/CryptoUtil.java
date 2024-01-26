/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : CryptoUtil.java
 * @Description : 전자정부 표준 프레임워크 암호화, 복호화 클래스.
 * @Version     : 3.0.0
 * @History     : [2011.11.29] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.egovframework;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import egovframework.rte.fdl.cryptography.EgovCryptoService;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;
import egovframework.rte.fdl.cryptography.impl.EgovARIACryptoServiceImpl;
import kr.co.akis.util.Const;
import kr.co.akis.util.ExceptionUtil;
import kr.co.akis.util.StringUtil;

public class CryptoUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CryptoUtil.class);
	
	// 암호화/복호화 기본 클래스를 정의한다.
	private static final EgovCryptoService cryptoService;
	static {
		cryptoService = (EgovARIACryptoServiceImpl) ContextLoader.getCurrentWebApplicationContext().getBean("ARIACryptoService");
	}
	
	// 암호화/복호화 패스워드
	private static final String password = "JJang";
	
	/**
	 * <p>EgovARIACryptoServiceImpl 클래스를 이용해서 문자열을 암호화 한다.</p>
	 * 
	 * <pre>
	 * CryptoUtil.encrypt(null)     = ""
	 * CryptoUtil.encrypt("")       = ""
	 * CryptoUtil.encrypt("장아린") = "8iaP4YoJ8mrO64QQ5aViGQ=="
	 * CryptoUtil.encrypt("JJang")  = "YInGrx74biQaeER3u+7gxA=="
	 * CryptoUtil.encrypt("1234")   = "y3N0AhzgLii2CFZn/Q947w=="
	 * </pre>
	 * 
	 * @param str           (암호화할 문자열)
	 * @return String       (암호화후 문자열)
	 */
	public static String encrypt(String str) {
		return encrypt(cryptoService, str);
	}
	
	/**
	 * <p>문자열을 암호화 한다.</p> 
	 * 
	 * <pre>
	 * CryptoUtil.encrypt(*, null)                               = ""
	 * CryptoUtil.encrypt(*, "")                                 = ""
	 * CryptoUtil.encrypt(EgovARIACryptoServiceImpl, "장아린")   = "8iaP4YoJ8mrO64QQ5aViGQ=="
	 * CryptoUtil.encrypt(EgovARIACryptoServiceImpl, "JJang")    = "YInGrx74biQaeER3u+7gxA=="
	 * CryptoUtil.encrypt(EgovARIACryptoServiceImpl, "1234")     = "y3N0AhzgLii2CFZn/Q947w=="
	 * CryptoUtil.encrypt(EgovGeneralCryptoServiceImpl, "장아린") = "5Yk4i7laNI0XsEcc2no+zmTWrXe3yyof" - 값이 계속 변함
	 * CryptoUtil.encrypt(EgovGeneralCryptoServiceImpl, "JJang") = "i7AA8aMifJqyFZBkfp46VQ=="          - 값이 계속 변함
	 * CryptoUtil.encrypt(EgovGeneralCryptoServiceImpl, "1234")  = "I2oSnddAduP1QIdQ1F7biQ=="          - 값이 계속 변함
	 * </pre>
	 * 
	 * @param cryptoService (암호화 Class)
	 * @param str           (암호화할 문자열)
	 * @return String       (암호화후 문자열)
	 */
	public static String encrypt(EgovCryptoService cryptoService, String str) {
		if (StringUtil.isEmpty(str)) {
			return "";
		}
		try {
			byte[] encrypted = cryptoService.encrypt(str.getBytes(Const.ENCODING_TYPE), password);
			return Base64.encodeBase64String(encrypted);
		} catch (Exception e) {
			logger.error(ExceptionUtil.addMessage(e, "전자정부 표준 프레임워크 Encryption 에러!!"));
			return "";
		}
	}
	
	/**
	 * <p>EgovARIACryptoServiceImpl 클래스를 이용해서 문자열을 복호화 한다.</p>
	 * 
	 * <pre>
	 * CryptoUtil.decrypt(null)                       = ""
	 * CryptoUtil.decrypt("")                         = ""
	 * CryptoUtil.decrypt("8iaP4YoJ8mrO64QQ5aViGQ==") = "장아린"
	 * CryptoUtil.decrypt("YInGrx74biQaeER3u+7gxA==") = "JJang"
	 * CryptoUtil.decrypt("y3N0AhzgLii2CFZn/Q947w==") = "1234"
	 * </pre>
	 * 
	 * @param str           (복호화할 문자열)
	 * @return String       (복호화후 문자열)
	 */
	public static String decrypt(String str) {
		return decrypt(cryptoService, str);
	}
	
	/**
	 * <p>문자열을 복호화 한다.</p>
	 * 
	 * <pre>
	 * CryptoUtil.decrypt(*, null)                                                          = ""
	 * CryptoUtil.decrypt(*, "")                                                            = ""
	 * CryptoUtil.decrypt(EgovARIACryptoServiceImpl, "8iaP4YoJ8mrO64QQ5aViGQ==")            = "장아린"
	 * CryptoUtil.decrypt(EgovARIACryptoServiceImpl, "YInGrx74biQaeER3u+7gxA==")            = "JJang"
	 * CryptoUtil.decrypt(EgovARIACryptoServiceImpl, "y3N0AhzgLii2CFZn/Q947w==")            = "1234"
	 * CryptoUtil.decrypt(EgovGeneralCryptoServiceImpl, "5Yk4i7laNI0XsEcc2no+zmTWrXe3yyof") = "장아린"
	 * CryptoUtil.decrypt(EgovGeneralCryptoServiceImpl, "i7AA8aMifJqyFZBkfp46VQ==")         = "JJang"
	 * CryptoUtil.decrypt(EgovGeneralCryptoServiceImpl, "I2oSnddAduP1QIdQ1F7biQ==")         = "1234"
	 * </pre>
	 * 
	 * @param cryptoService (복호화 Class)
	 * @param str           (복호화할 문자열)
	 * @return String       (복호화후 문자열)
	 */
	public static String decrypt(EgovCryptoService cryptoService, String str) {
		if (StringUtil.isEmpty(str)) {
			return "";
		}
		try {
			byte[] base64dec = Base64.decodeBase64(str);
			return new String(cryptoService.decrypt(base64dec, password));
		} catch (Exception e) {
			logger.error(ExceptionUtil.addMessage(e, "전자정부 표준 프레임워크 Decryption 에러!!"));
			return "";
		}
	}
	
	/**
	 * <p>암복호화 서비스를 사용하기 위해서 패스워드에 대한 hash value를 구해서 리턴한다.</p>
	 * 
	 * <pre>
	 * CryptoUtil.getHashedPassword(null)        = ""
	 * CryptoUtil.getHashedPassword("")          = ""
	 * CryptoUtil.getHashedPassword("  ")        = "bBefIeb2K2KQVdirQPRU7QLki2hWORNHO4V9NjjiOyg="
	 * CryptoUtil.getHashedPassword("1234")      = "A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ="
	 * CryptoUtil.getHashedPassword("egovframe") = "gdyYs/IZqY86VcWhT8emCYfqY1ahw2vtLG+/FzNqtrQ="
	 * CryptoUtil.getHashedPassword("RP-SYSTEM") = "VNG0Lt/hSK9D+WZWlszCsmnAF6+Ce+z6cmQjwMClBmE="
	 * CryptoUtil.getHashedPassword("DK-CNS")    = "9fXJcDy2FHJ9lpHAQx3bUTYGHwlkYN6qC0a1ZPl1VM4="
	 * CryptoUtil.getHashedPassword("JJang")     = "aTcUpoix2MBnapQ6Kzyrl5VE1UJSVteSMWmrjoQl9xw="
	 * </pre>
	 * 
	 * @param password (hash value를 구할 패스워드)
	 * @return String  (패스워드의 hash value)
	 */
	public static String getHashedPassword(String password) {
		if (StringUtil.isEmpty(password)) {
			return "";
		}
		try {
			EgovPasswordEncoder encoder = new EgovPasswordEncoder();
			encoder.setAlgorithm("SHA-256");
			return encoder.encryptPassword(password);
		} catch (Exception e) {
			logger.error(ExceptionUtil.addMessage(e, "전자정부 표준 프레임워크 EgovPasswordEncoder.encryptPassword() 에러!!"));
			return "";
		}
	}
	
	/**
	 * <pre>
	 * 사용자 비밀번호를 암호화하여 리턴한다. (복호화가 되면 안되므로 SHA-256 인코딩 방식 적용)
	 * - 전자정부 프레임워크 사용자 비밀번호 암호화 방식
	 * </pre>
	 * 
	 * <pre>
	 * CryptoUtil.encryptPassword(null, *)               = ""
	 * CryptoUtil.encryptPassword("", *)                 = ""
	 * CryptoUtil.encryptPassword("1234", null)          = ""
	 * CryptoUtil.encryptPassword("1234", "")            = "A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ="
	 * CryptoUtil.encryptPassword("1234", "user")        = "gxwjeSjmISvtqkRRpRSs4xdFYvZ2H2oVei/lCCs24vs="
	 * CryptoUtil.encryptPassword("1234", "admin")       = "rJaJ4ickJwheNbnT4+i+2IyzQ0gotDuG/AWWytTG4nA="
	 * CryptoUtil.encryptPassword("1234", "sampleUser")  = "qQZ5CapiC/yV77Bo/apnL2S/xLRo1SNxIZDHLqhPVyE="
	 * CryptoUtil.encryptPassword("1234", "sampleAdmin") = "zxmCZLIs/0ucBbQNfCy4yZlOOXcjcy3XYnA6mS3/Ahw="
	 * </pre>
	 * 
	 * @param password (암호화될 비밀번호)
	 * @param id       (salt로 사용될 사용자 ID)
	 * @return String  (암호화된 비밀번호)
	 * @throws NoSuchAlgorithmException 
	 */
	public static String encryptPassword(String password, String id) throws NoSuchAlgorithmException {
		if (StringUtil.isBlank(password) || id == null) {
			return "";
		}
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.reset();
		md.update(id.getBytes());
		byte[] hashVal = md.digest(password.getBytes());
		return new String(Base64.encodeBase64(hashVal));
	}
	
}