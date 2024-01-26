/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : PropertiesLoader.java
 * @Description : properties 파일 로더 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletException;

import org.apache.commons.collections.ExtendedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);
	private ExtendedProperties properties = null;
	
	/**
	 * <p>Constructor</p>
	 */
	public PropertiesLoader() {
		this("classpath:/global.properties");
	}
	
	/**
	 * <p>Constructor</p>
	 * 
	 * @param location (프로퍼티 파일 위치)
	 * @param property (추가할 속성)
	 */
	@SuppressWarnings("unchecked")
	public PropertiesLoader(Set<Object> location, Map<String, String> property) {
		this(location);
		Iterator<Entry<String, String>> it = property.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
			String key = (String)entry.getKey();
			String val = (String)entry.getValue();
			properties.put(key, val);
		}
	}
	
	/**
	 * <p>Constructor</p>
	 * 
	 * @param location (프로퍼티 파일 위치 - String, String[], Set)
	 */
	public PropertiesLoader(Object location) {
		this(location, "UTF-8");
	}
	
	/**
	 * <p>Constructor</p>
	 * 
	 * @param location (프로퍼티 파일 위치 - String, String[], Set)
	 * @param enc      (인코딩 타입)
	 */
	@SuppressWarnings("unchecked")
	public PropertiesLoader(Object location, String enc) {
		try {
			properties = new ExtendedProperties();
			if (location instanceof String) {
				load(String.valueOf(location), enc);
			} else if (location instanceof String[]) {
				String[] locationArr = (String[])location;
				for (int i = 0; i < locationArr.length; i++) {
					load(locationArr[i], enc);
				}
			} else if (location instanceof Set) {
				Iterator<Object> it = ((Set<Object>)location).iterator();
				while (it.hasNext()) {
					Object element = it.next();
					String newLocation = null;
					String newEncoding = enc;
					if (element instanceof Map) {
						Map<String, String> map = (Map<String, String>)element;
						newLocation = (String)map.get("fileName");
						if (map.get("encoding") != null) {
							newEncoding = (String)map.get("encoding");
						}
					} else {
						newLocation = (String)element;
					}
					load(newLocation, newEncoding);
				}
			} else {
				String objName = (location == null) ? "null" : location.getClass().getName();
				throw new ServletException("생성자 PropertiesLoader(" + objName + ", \"" + enc + "\")가 정의되지 않았습니다.");
			}
		} catch (Exception e) {
			properties = null;
			logger.error(e.toString(), e.fillInStackTrace());
		}
	}
	
	/**
	 * <p>프로퍼티 파일 정보를 읽는다.</p>
	 * 
	 * @param location (프로퍼티 파일 위치)
	 * @param enc      (인코딩 타입)
	 * @throws Exception 
	 */
	private void load(String location, String enc) throws Exception {
		URL url = FileUtil.getResource(location);
		if (url == null) {
			String resourcePath = "";
			if (location == null) {
				resourcePath = "프로퍼티 파일 경로 : null";
			} else if (StringUtil.startsWith(location, "classpath:")) {
				location = location.substring("classpath:".length());
				resourcePath = FileUtil.cleanPath(PropertiesLoader.class.getResource("/").getPath(), "/") + (StringUtil.startsWith(location, "/") ? location.substring(1) : location);
			} else {
				resourcePath = FileUtil.cleanPath(PropertiesLoader.class.getResource("/../../").getPath(), "/") + (StringUtil.startsWith(location, "/") ? location.substring(1) : location);
			}
			throw new FileNotFoundException("프로퍼티 파일이 존재하지 않습니다. [" + resourcePath + "]");
		}
		ExtendedProperties newProperties = new ExtendedProperties();
		newProperties.load(url.openStream(), enc);
		properties.combine(newProperties);
		newProperties = null;
	}
	
	/**
	 * Destroy Method
	 */
	public void destroy() {
		this.properties = null;
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 boolean 타입으로 리턴한다.</p>
	 * 
	 * @param key      (프로퍼티 키)
	 * @return boolean (프로퍼티 값)
	 */
	public boolean getBoolean(String key) {
		return properties.getBoolean(key);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 boolean 타입으로 리턴 또는 값이 없을경우 기본값을 리턴한다.</p>
	 * 
	 * @param key          (프로퍼티 키)
	 * @param defaultValue (기본값)
	 * @return boolean     (프로퍼티 값)
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		return properties.getBoolean(key, defaultValue);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 double 타입으로 리턴한다.</p>
	 * 
	 * @param key     (프로퍼티 키)
	 * @return double (프로퍼티 값)
	 */
	public double getDouble(String key) {
		return properties.getDouble(key);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 double 타입으로 리턴 또는 값이 없을경우 기본값을 리턴한다.</p>
	 * 
	 * @param key          (프로퍼티 키)
	 * @param defaultValue (기본값)
	 * @return double      (프로퍼티 값)
	 */
	public double getDouble(String key, double defaultValue) {
		return properties.getDouble(key, defaultValue);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 float 타입으로 리턴한다.</p>
	 * 
	 * @param key    (프로퍼티 키)
	 * @return float (프로퍼티 값)
	 */
	public float getFloat(String key) {
		return properties.getFloat(key);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 float 타입으로 리턴 또는 값이 없을경우 기본값을 리턴한다.</p>
	 * 
	 * @param key          (프로퍼티 키)
	 * @param defaultValue (기본값)
	 * @return float       (프로퍼티 값)
	 */
	public float getFloat(String key, float defaultValue) {
		return properties.getFloat(key, defaultValue);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 int 타입으로 리턴한다.</p>
	 * 
	 * @param key  (프로퍼티 키)
	 * @return int (프로퍼티 값)
	 */
	public int getInt(String key) {
		return properties.getInt(key);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 int 타입으로 리턴 또는 값이 없을경우 기본값을 리턴한다.</p>
	 * 
	 * @param key          (프로퍼티 키)
	 * @param defaultValue (기본값)
	 * @return int         (프로퍼티 값)
	 */
	public int getInt(String key, int defaultValue) {
		return properties.getInt(key, defaultValue);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 {@literal List<Object>} 타입으로 리턴한다.</p>
	 * 
	 * @param key                      (프로퍼티 키)
	 * @return {@literal List<Object>} (프로퍼티 값)
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getList(String key) {
		return properties.getList(key);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 {@literal List<Object>} 타입으로 리턴 또는 값이 없을경우 기본값을 리턴한다.</p>
	 * 
	 * @param key                      (프로퍼티 키)
	 * @param defaultValue             (기본값)
	 * @return {@literal List<Object>} (프로퍼티 값)
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getList(String key, List<Object> defaultValue) {
		return properties.getList(key, defaultValue);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 long 타입으로 리턴한다.</p>
	 * 
	 * @param key   (프로퍼티 키)
	 * @return long (프로퍼티 값)
	 */
	public long getLong(String key) {
		return properties.getLong(key);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 long 타입으로 리턴 또는 값이 없을경우 기본값을 리턴한다.</p>
	 * 
	 * @param key          (프로퍼티 키)
	 * @param defaultValue (기본값)
	 * @return long        (프로퍼티 값)
	 */
	public long getLong(String key, long defaultValue) {
		return properties.getLong(key, defaultValue);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 String 타입으로 리턴한다.</p>
	 * 
	 * @param key     (프로퍼티 키)
	 * @return String (프로퍼티 값)
	 */
	public String getString(String key) {
		return properties.getString(key);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 String 타입으로 리턴 또는 값이 없을경우 기본값을 리턴한다.</p>
	 * 
	 * @param key          (프로퍼티 키)
	 * @param defaultValue (기본값)
	 * @return String      (프로퍼티 값)
	 */
	public String getString(String key, String defaultValue) {
		return properties.getString(key, defaultValue);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 String[] 타입으로 리턴한다.</p>
	 * 
	 * @param key       (프로퍼티 키)
	 * @return String[] (프로퍼티 값)
	 */
	public String[] getStringArray(String key) {
		return properties.getStringArray(key);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 {@literal Vector<Object>} 타입으로 리턴한다.</p>
	 * 
	 * @param key                        (프로퍼티 키)
	 * @return {@literal Vector<Object>} (프로퍼티 값)
	 */
	@SuppressWarnings("unchecked")
	public Vector<Object> getVector(String key) {
		return properties.getVector(key);
	}
	
	/**
	 * <p>지정된 프로퍼티 키에대한 프로퍼티 값을 {@literal Vector<Object>} 타입으로 리턴 또는 값이 없을경우 기본값을 리턴한다.</p>
	 * 
	 * @param key                        (프로퍼티 키)
	 * @param defaultValue               (기본값)
	 * @return {@literal Vector<Object>} (프로퍼티 값)
	 */
	@SuppressWarnings("unchecked")
	public Vector<Object> getVector(String key, Vector<Object> defaultValue) {
		return properties.getVector(key, defaultValue);
	}
	
	/**
	 * <p>모든 프로퍼티 키 목록을 {@literal Iterator<String>} 타입으로 리턴한다.</p>
	 * 
	 * @return {@literal Iterator<String>} (프로퍼티 키 목록)
	 */
	@SuppressWarnings("unchecked")
	public Iterator<String> getKeys() {
		return properties.getKeys();
	}
	
	/**
	 * <p>prefix에 매칭되는 모든 프로퍼티 키 목록을 {@literal Iterator<String>} 타입으로 리턴한다.</p>
	 * 
	 * @param prefix                       (매칭할 prefix)
	 * @return {@literal Iterator<String>} (prefix에 매칭되는 프로퍼티 키 목록)
	 */
	@SuppressWarnings("unchecked")
	public Iterator<String> getKeys(String prefix) {
		return properties.getKeys(prefix);
	}
	
	/**
	 * <p>모든 프로퍼티 값 목록을 {@literal Collection<Object>} 타입으로 리턴한다.</p>
	 * 
	 * @return {@literal Collection<Object>} (프로퍼티 값 목록)
	 */
	@SuppressWarnings("unchecked")
	public Collection<Object> getValues() {
		return properties.values();
	}
	
}