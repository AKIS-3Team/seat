/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : FileUtil.java
 * @Description : 파일 유틸 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * <p>파일의 내용을 Application 기본 캐릭터셋 타입으로 읽어서 문자열로 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.read(null)           = null
	 * FileUtil.read("")             = null
	 * FileUtil.read(!file.exists()) = null
	 * FileUtil.read("D:/test.txt")  = 파일내용
	 * </pre>
	 * 
	 * @param location (파일 위치)
	 * @return String  (파일 내용)
	 */
	public static String read(String location) {
		return read(location, Const.ENCODING_TYPE);
	}
	
	/**
	 * <p>파일의 내용을 읽어서 문자열로 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.read(null, *)           = null
	 * FileUtil.read("", *)             = null
	 * FileUtil.read(!file.exists(), *) = null
	 * FileUtil.read("D:/test.txt", *)  = 파일내용
	 * </pre>
	 * 
	 * @param location (파일 위치)
	 * @param charset  (파일 캐릭터셋)
	 * @return String  (파일 내용)
	 */
	public static String read(String location, String charset) {
		if (StringUtil.isBlank(location)) {
			return null;
		}
		return read(new File(location), charset, true);
	}
	
	/**
	 * <p>파일의 내용을 Application 기본 캐릭터셋 타입으로 읽어서 문자열로 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.read(null)           = null
	 * FileUtil.read(!file.exists()) = null
	 * FileUtil.read(File)           = 파일내용
	 * </pre>
	 * 
	 * @param file    (파일 객체)
	 * @return String (파일 내용)
	 */
	public static String read(File file) {
		return read(file, Const.ENCODING_TYPE, true);
	}
	
	/**
	 * <p>파일의 내용을 읽어서 문자열로 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.read(null, *, *)           = null
	 * FileUtil.read(!file.exists(), *, *) = null
	 * FileUtil.read(File, *, *)           = 파일내용
	 * </pre>
	 * 
	 * @param file    (파일 객체)
	 * @param charset (파일 캐릭터셋)
	 * @param addLine (개행문자 추가 여부)
	 * @return String (파일 내용)
	 */
	public static String read(File file, String charset, boolean addLine) {
		if (file != null && file.exists()) {
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			StringBuilder sb = null;
			String line = null;
			try {
				fis = new FileInputStream(file);
				if (StringUtil.isBlank(charset)) {
					isr = new InputStreamReader(fis);
				} else {
					isr = new InputStreamReader(fis, charset);
				}
				br = new BufferedReader(isr);
				sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
					sb.append(line);
					if (addLine) {
						sb.append("\r\n");
					}
				}
				return sb.toString();
			} catch (Exception e) {
				logger.error(e.toString(), e.fillInStackTrace());
				return null;
			} finally {
				line = null;
				sb = null;
				if (br != null) try { br.close(); } catch (Exception e) {};
				if (isr != null) try { isr.close(); } catch (Exception e) {};
				if (fis != null) try { fis.close(); } catch (Exception e) {};
			}
		} else {
			logger.warn("파일이 존재하지 않습니다. [파일경로 : " + (file == null ? "null" : file.getPath()) + "]");
			return null;
		}
	}
	
	/**
	 * <p>해당 경로의 파일을 삭제한다.</p>
	 * 
	 * <pre>
	 * FileUtil.delete(null, *)           = false
	 * FileUtil.delete(*, null)           = false
	 * FileUtil.delete("", "")            = false
	 * FileUtil.delete(!file.exists(), *) = false
	 * FileUtil.delete("D:/", "test.txt") = true
	 * </pre>
	 * 
	 * @param filePath (삭제할 파일의 경로)
	 * @param fileName (삭제할 파일의 이름)
	 * @return boolean (파일삭제 성공여부)
	 */
	public static boolean delete(String filePath, String fileName) {
		return delete(new File(StringUtil.clean(filePath), StringUtil.clean(fileName)));
	}
	
	/**
	 * <p>해당 위치의 파일 또는 디렉토리를 삭제한다. (디렉토리는 비어있을 경우에만 삭제함)</p>
	 * 
	 * <pre>
	 * FileUtil.delete(null)           = false
	 * FileUtil.delete("")             = false
	 * FileUtil.delete(!file.exists()) = false
	 * FileUtil.delete("D:/test.txt")  = true
	 * </pre>
	 * 
	 * @param location (삭제할 파일 또는 디렉토리 위치)
	 * @return boolean (파일 또는 디렉토리 삭제 성공여부)
	 */
	public static boolean delete(String location) {
		return delete(new File(StringUtil.clean(location)));
	}
	
	/**
	 * <p>파일 또는 디렉토리를 삭제한다. (디렉토리는 비어있을 경우에만 삭제함)</p>
	 * 
	 * <pre>
	 * FileUtil.delete(null)           = false
	 * FileUtil.delete(!file.exists()) = false
	 * FileUtil.delete(File)           = true
	 * </pre>
	 * 
	 * @param file     (삭제할 파일 또는 디렉토리 객체)
	 * @return boolean (파일 또는 디렉토리 삭제 성공여부)
	 */
	public static boolean delete(File file) {
		return delete(file, false);
	}
	
	/**
	 * <p>파일 또는 디렉토리를 삭제한다.</p>
	 * <p>디렉토리일 경우 디렉토리에 있는 모든것을 포함해서 삭제할지 선택한다.</p>
	 * 
	 * <pre>
	 * FileUtil.delete(null, *)           = false
	 * FileUtil.delete(!file.exists(), *) = false
	 * FileUtil.delete(File, *)           = true
	 * </pre>
	 * 
	 * @param file        (삭제할 파일 또는 디렉토리 객체)
	 * @param isDeleteAll (디렉토리에 있는 모든것 포함 삭제여부)
	 * @return boolean    (파일 또는 디렉토리 삭제 성공여부)
	 */
	public static boolean delete(File file, boolean isDeleteAll) {
		if (file != null && file.exists()) {
			if (file.isFile() || (file.isDirectory() && !isDeleteAll)) {
				return file.delete();
			} else {
				File[] fileList = file.listFiles();
				for (int i = 0; i < fileList.length; i++) {
					if (fileList[i].isFile()) {
						fileList[i].delete();
					} else {
						delete(fileList[i], true);
					}
				}
				return file.delete();
			}
		} else {
			logger.warn("파일 또는 디렉토리가 존재하지 않습니다. [경로 : " + (file == null ? "null" : file.getPath()) + "]");
			return false;
		}
	}
	
	/**
	 * <p>파일을 복사한다.</p>
	 * 
	 * <pre>
	 * FileUtil.copy(null, *)                      = false
	 * FileUtil.copy(*, null)                      = false
	 * FileUtil.copy("", "")                       = false
	 * FileUtil.copy(!file.exists(), *)            = false
	 * FileUtil.copy("D:/test.txt", "D:/copy.txt") = true
	 * </pre>
	 * 
	 * @param from      (복사할 파일 위치)
	 * @param to        (복사될 파일 위치)
	 * @return boolean  (파일 복사 성공여부)
	 */
	public static boolean copy(String from, String to) {
		return copy(new File(cleanPath(from)), new File(cleanPath(to)));
	}
	
	/**
	 * <p>파일을 복사한다.</p>
	 * 
	 * <pre>
	 * FileUtil.copy(null, *)                      = false
	 * FileUtil.copy(*, null)                      = false
	 * FileUtil.copy("", "")                       = false
	 * FileUtil.copy(!file.exists(), *)            = false
	 * FileUtil.copy("D:/test.txt", "D:/copy.txt") = true
	 * </pre>
	 * 
	 * @param from      (복사할 파일 객체)
	 * @param to        (복사될 파일 객체)
	 * @return boolean  (파일 복사 성공여부)
	 */
	public static boolean copy(File from, File to) {
		if (from != null && to != null && from.exists()) {
			FileInputStream fis = null;
			FileOutputStream fos = null;
			FileChannel fic = null;
			FileChannel foc = null;
			try {
				fis = new FileInputStream(from);
				fos = new FileOutputStream(to);
				fic = fis.getChannel();
				foc = fos.getChannel();
				//MappedByteBuffer mbb = fic.map(FileChannel.MapMode.READ_ONLY, 0, fic.size());
				//foc.write(mbb);
				fic.transferTo(0, fic.size(), foc);
			} catch (Exception e) {
				logger.error(e.toString(), e.fillInStackTrace());
				return false;
			} finally {
				if (foc != null)  try { foc.close(); } catch (Exception e) {};
				if (fic != null)  try { fic.close(); } catch (Exception e) {};
				if (fos != null)  try { fos.close(); } catch (Exception e) {};
				if (fis != null)  try { fis.close(); } catch (Exception e) {};
			}
			return true;
		} else {
			logger.warn("파일 복사를 실패했습니다. [복사할파일경로 : " + (from == null ? "null" : from.getPath()) + ", 복사될파일경로 : " + (to == null ? "null" : to.getPath()) + "]");
			return false;
		}
	}
	
	/**
	 * <p>파일명을 변경한다.</p>
	 * 
	 * <pre>
	 * FileUtil.rename(null, *, *)                      = false
	 * FileUtil.rename(*, null, *)                      = false
	 * FileUtil.rename(*, *, null)                      = false
	 * FileUtil.rename("", "", "")                      = false
	 * FileUtil.rename(*, !file.exists(), *)            = false
	 * FileUtil.rename("D:/", "test.txt", "rename.txt") = true
	 * </pre>
	 * 
	 * @param path      (공통된 파일 경로)
	 * @param from      (변경할 파일명)
	 * @param to        (변경후 파일명)
	 * @return boolean  (파일명 변경 성공여부)
	 */
	public static boolean rename(String path, String from, String to) {
		return rename(path + from, path + to);
	}
	
	/**
	 * <p>파일명을 변경한다.</p>
	 * 
	 * <pre>
	 * FileUtil.rename(null, *)                        = false
	 * FileUtil.rename(*, null)                        = false
	 * FileUtil.rename("", "")                         = false
	 * FileUtil.rename(!file.exists(), *)              = false
	 * FileUtil.rename("D:/test.txt", "D:/rename.txt") = true
	 * </pre>
	 * 
	 * @param from      (변경할 파일 위치)
	 * @param to        (변경후 파일 위치)
	 * @return boolean  (파일명 변경 성공여부)
	 */
	public static boolean rename(String from, String to) {
		return rename(new File(cleanPath(from)), new File(cleanPath(to)));
	}
	
	/**
	 * <p>파일명을 변경한다.</p>
	 * 
	 * <pre>
	 * FileUtil.rename(null, *)                        = false
	 * FileUtil.rename(*, null)                        = false
	 * FileUtil.rename("", "")                         = false
	 * FileUtil.rename(!file.exists(), *)              = false
	 * FileUtil.rename("D:/test.txt", "D:/rename.txt") = true
	 * </pre>
	 * 
	 * @param from      (변경할 파일 객체)
	 * @param to        (변경후 파일 객체)
	 * @return boolean  (파일명 변경 성공여부)
	 */
	public static boolean rename(File from, File to) {
		if (from != null && to != null && from.exists()) {
			if (from.renameTo(to)) {
				return true;
			}
		}
		logger.warn("파일명 변경을 실패했습니다. [변경할파일경로 : " + (from == null ? "null" : from.getPath()) + ", 변경될파일경로 : " + (to == null ? "null" : to.getPath()) + "]");
		return false;
	}
	
	/**
	 * <p>파일 경로에서 디렉토리 분리기호를 시스템 환경에 맞게 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.toSystemPath(null)            = ""
     * FileUtil.toSystemPath("")              = ""
     * FileUtil.toSystemPath("   ")           = ""
     * FileUtil.toSystemPath("\aa/bb\")       = "/aa/bb/"       (유닉스계열 서버 일때)
     * FileUtil.toSystemPath("\aa/bb\")       = "\aa\bb\"       (윈도우계열 서버 일때)
     * FileUtil.toSystemPath("/aa\\bb\")      = "/aa//bb/"      (유닉스계열 서버 일때)
     * FileUtil.toSystemPath("/aa\\bb\")      = "\aa\\bb\"      (윈도우계열 서버 일때)
     * FileUtil.toSystemPath("D:\aa//bb.jsp") = "D:/aa//bb.jsp" (유닉스계열 서버 일때)
     * FileUtil.toSystemPath("D:\aa//bb.jsp") = "D:\aa\\bb.jsp" (윈도우계열 서버 일때)
	 * </pre>
	 * 
	 * @param filePath (변경할 파일경로)
	 * @return String  (변경후 파일경로)
	 */
	public static String toSystemPath(String filePath) {
		if (StringUtil.isBlank(filePath)) {
			return "";
		}
		String separator = java.io.File.separator;
		if ("\\".equals(separator)) {
			separator = "\\\\";
		}
		filePath = filePath.replaceAll("\\\\", separator);
		filePath = filePath.replaceAll("/", separator);
		return filePath;
	}
	
	/**
	 * <p>파일 경로에서 "\" 를 "/" 로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.toUnixPath(null)            = ""
     * FileUtil.toUnixPath("")              = ""
     * FileUtil.toUnixPath("   ")           = ""
     * FileUtil.toUnixPath("\aa\bb\")       = "/aa/bb/"
     * FileUtil.toUnixPath("/cc\dd/")       = "/cc/dd/"
     * FileUtil.toUnixPath("/cc\\dd/")      = "/cc//dd/"
     * FileUtil.toUnixPath("D:\aa//bb.jsp") = "D:/aa//bb.jsp"
	 * </pre>
	 * 
	 * @param filePath (변경할 파일경로)
	 * @return String  (변경후 파일경로)
	 */
	public static String toUnixPath(String filePath) {
		return StringUtil.isBlank(filePath) ? "" : filePath.replaceAll("\\\\", "/");
	}
	
	/**
	 * <p>파일 경로에서 "/" 를 "\" 로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.toWindowsPath(null)            = ""
	 * FileUtil.toWindowsPath("")              = ""
	 * FileUtil.toWindowsPath("   ")           = ""
	 * FileUtil.toWindowsPath("/aa/bb/")       = "\aa\bb\"
	 * FileUtil.toWindowsPath("\cc/dd\")       = "\cc\dd\"
	 * FileUtil.toWindowsPath("\cc//dd\")      = "\cc\\dd\"
	 * FileUtil.toWindowsPath("D:\aa//bb.jsp") = "D:\aa\\bb.jsp"
	 * </pre>
	 * 
	 * @param filePath (변경할 파일경로)
	 * @return String  (변경후 파일경로)
	 */
	public static String toWindowsPath(String filePath) {
		return StringUtil.isBlank(filePath) ? "" : filePath.replaceAll("/", "\\\\");
	}
	
	/**
	 * <p>파일 경로를 정리하고, 디렉토리 분리기호를 시스템 환경에 맞게 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.cleanPath(null)             = ""
	 * FileUtil.cleanPath("")               = ""
	 * FileUtil.cleanPath("   ")            = ""
	 * FileUtil.cleanPath("D:/aa\bb.jsp")   = "D:/aa/bb.jsp" (유닉스계열 서버 일때)
	 * FileUtil.cleanPath("D:/aa\bb.jsp")   = "D:\aa\bb.jsp" (윈도우계열 서버 일때)
	 * FileUtil.cleanPath("D:\aa//bb.jsp")  = "D:/aa/bb.jsp" (유닉스계열 서버 일때)
	 * FileUtil.cleanPath("D:\aa//bb.jsp")  = "D:\aa\bb.jsp" (윈도우계열 서버 일때)
	 * FileUtil.cleanPath("/D://aa/bb.jsp") = "D:/aa/bb.jsp" (유닉스계열 서버 일때)
	 * FileUtil.cleanPath("/D://aa/bb.jsp") = "D:\aa\bb.jsp" (윈도우계열 서버 일때)
	 * FileUtil.cleanPath("\D:/aa/bb.jsp")  = "D:/aa/bb.jsp" (유닉스계열 서버 일때)
	 * FileUtil.cleanPath("\D:/aa/bb.jsp")  = "D:\aa\bb.jsp" (윈도우계열 서버 일때)
	 * FileUtil.cleanPath("/aa/\bb.jsp")    = "/aa/bb.jsp"   (유닉스계열 서버 일때)
	 * FileUtil.cleanPath("/aa/\bb.jsp")    = "\aa\bb.jsp"   (윈도우계열 서버 일때)
	 * FileUtil.cleanPath("/aa\\bb//")      = "/aa/bb/"      (유닉스계열 서버 일때)
	 * FileUtil.cleanPath("\aa\\bb//")      = "\aa\bb\"      (윈도우계열 서버 일때)
	 * </pre>
	 * 
	 * @param filePath  (변경할 파일경로)
	 * @return String   (변경후 파일경로)
	 */
	public static String cleanPath(String filePath) {
		String separator = java.io.File.separator;
		if ("\\".equals(separator)) {
			separator = "\\\\";
		}
		return cleanPath(filePath, separator);
	}
	
	/**
	 * <p>파일 경로를 정리하고, 해당 디렉토리 분리기호로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.cleanPath(null)                  = ""
	 * FileUtil.cleanPath("")                    = ""
	 * FileUtil.cleanPath("   ")                 = ""
	 * FileUtil.cleanPath("D:/aa\bb.jsp", "/")   = "D:/aa/bb.jsp"
	 * FileUtil.cleanPath("D:/aa\bb.jsp", "\")   = "D:\aa\bb.jsp"
	 * FileUtil.cleanPath("D:\aa//bb.jsp", "/")  = "D:/aa/bb.jsp"
	 * FileUtil.cleanPath("D:\aa//bb.jsp", "\")  = "D:\aa\bb.jsp"
	 * FileUtil.cleanPath("/D://aa/bb.jsp", "/") = "D:/aa/bb.jsp"
	 * FileUtil.cleanPath("/D://aa/bb.jsp", "\") = "D:\aa\bb.jsp"
	 * FileUtil.cleanPath("\D:/aa/bb.jsp", "/")  = "D:/aa/bb.jsp"
	 * FileUtil.cleanPath("\D:/aa/bb.jsp", "\")  = "D:\aa\bb.jsp"
	 * FileUtil.cleanPath("/aa/\bb.jsp", "/")    = "/aa/bb.jsp"
	 * FileUtil.cleanPath("/aa/\bb.jsp", "\")    = "\aa\bb.jsp"
	 * FileUtil.cleanPath("/aa\\bb//", "/")      = "/aa/bb/"
	 * FileUtil.cleanPath("\aa\\bb//", "\")      = "\aa\bb\"
	 * </pre>
	 * 
	 * @param filePath  (변경할 파일경로)
	 * @param separator (디렉토리 분리기호)
	 * @return String   (변경후 파일경로)
	 */
	public static String cleanPath(String filePath, String separator) {
		if (StringUtil.isBlank(filePath)) {
			return "";
		}
		if ("\\".equals(separator)) {
			separator = "\\\\";
		}
		filePath = filePath.replaceAll("\\\\", separator);
		filePath = filePath.replaceAll("/", separator);
		if (filePath.indexOf(":") != -1 && (StringUtil.startsWith(filePath, "/") || StringUtil.startsWith(filePath, "\\"))) {
			filePath = filePath.replaceAll("^/*", "");
			filePath = filePath.replaceAll("^\\\\*", "");
		}
		filePath = filePath.replaceAll("/+", separator);
		filePath = filePath.replaceAll("\\\\+", separator);
		return filePath;
	}
	
	/**
	 * <p>지정된 위치의 리소스를 검색하여 URL 객체를 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.getResource(null)                  = null
	 * FileUtil.getResource("")                    = URL 객체 (Root Path)
	 * FileUtil.getResource("   ")                 = URL 객체 (Root Path)
	 * FileUtil.getResource("/WEB-INF/")           = URL 객체 (Root Path + "/WEB-INF/")
	 * FileUtil.getResource("/assets/js/")         = URL 객체 (Root Path + "/assets/js/")
	 * FileUtil.getResource("/global.properties")  = URL 객체 (Root Path + "/global.properties")
	 * FileUtil.getResource("classpath:")          = URL 객체 (Root Path + "/WEB-INF/classes/")
	 * FileUtil.getResource("classpath:/kr/co/")   = URL 객체 (Root Path + "/WEB-INF/classes/kr/co/")
	 * FileUtil.getResource("classpath:/test.txt") = URL 객체 (Root Path + "/WEB-INF/classes/test.txt")
	 * </pre>
	 * 
	 * @param location (검색할 리소스 위치)
	 * @return URL     (URL 객체)
	 */
	public static URL getResource(String location) {
		if (location == null) {
			return null;
		}
		location = StringUtil.clean(location);
		if (StringUtil.startsWith(location, "classpath:")) {
			location = location.substring("classpath:".length());
			if (!StringUtil.startsWith(location, "/")) {
				location = "/" + location;
			}
		} else {
			if (!StringUtil.startsWith(location, "/")) {
				location = "/" + location;
			}
			location = "/../.." + location;
		}
		return FileUtil.class.getResource(location);
	}
	
	/**
	 * <p>지정된 위치의 리소스를 검색하여 리소스 경로를 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.getResourcePath(null)                  = null
	 * FileUtil.getResourcePath("")                    = Root Path
	 * FileUtil.getResourcePath("   ")                 = Root Path
	 * FileUtil.getResourcePath("/WEB-INF/")           = Root Path + "/WEB-INF/"
	 * FileUtil.getResourcePath("/assets/js/")         = Root Path + "/assets/js/"
	 * FileUtil.getResourcePath("/global.properties")  = Root Path + "/global.properties"
	 * FileUtil.getResourcePath("classpath:")          = Root Path + "/WEB-INF/classes/"
	 * FileUtil.getResourcePath("classpath:/kr/co/")   = Root Path + "/WEB-INF/classes/kr/co/"
	 * FileUtil.getResourcePath("classpath:/test.txt") = Root Path + "/WEB-INF/classes/test.txt"
	 * </pre>
	 * 
	 * @param location (검색할 리소스 위치)
	 * @return String  (리소스 경로)
	 */
	public static String getResourcePath(String location) {
		try {
			return cleanPath(getResource(location).getPath(), "/");
		} catch (Exception e) {
			String resourcePath = "";
			if (StringUtil.startsWith(location, "classpath:")) {
				location = location.substring("classpath:".length());
				resourcePath = cleanPath(FileUtil.class.getResource("/").getPath(), "/") + (StringUtil.startsWith(location, "/") ? location.substring(1) : location);
			} else {
				resourcePath = cleanPath(FileUtil.class.getResource("/../../").getPath(), "/") + (StringUtil.startsWith(location, "/") ? location.substring(1) : location);
			}
			logger.error(ExceptionUtil.addMessage(e, "java.io.FileNotFoundException: 리소스가 존재하지 않거나 액세스할 수 없습니다. [" + resourcePath + "]"));
			return null;
		}
	}
	
	/**
	 * <p>해당 클래스를 기준으로 지정된 위치의 리소스를 검색하여 URL 객체를 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.getResource(null, null)              = null
	 * FileUtil.getResource(null, *)                 = null
	 * FileUtil.getResource(Class, null)             = null
	 * FileUtil.getResource(Class, "")               = URL 객체 (Root Path + Class Package Path)
	 * FileUtil.getResource(Class, "   ")            = URL 객체 (Root Path + Class Package Path)
	 * FileUtil.getResource(Class, "mapping.xml")    = URL 객체 (Root Path + Class Package Path + "/mapping.xml")
	 * FileUtil.getResource(Class, "./mapping.xml")  = URL 객체 (Root Path + Class Package Path + "/mapping.xml")
	 * FileUtil.getResource(Class, "../poi/poi.xml") = URL 객체 (Root Path + Class Package Path + "/../poi/poi.xml")
	 * FileUtil.getResource(Class, "/log4j2.xml")    = URL 객체 (Root Path + "/WEB-INF/classes/log4j2.xml")
	 * FileUtil.getResource(Class, "kr/co/")         = URL 객체 (Root Path + Class Package Path + "/kr/co/")
	 * FileUtil.getResource(Class, "/kr/co/")        = URL 객체 (Root Path + "/WEB-INF/classes/kr/co/")
	 * </pre>
	 * 
	 * @param clazz    (Class 객체)
	 * @param location (검색할 리소스 위치)
	 * @return URL     (URL 객체)
	 */
	public static URL getResource(Class<?> clazz, String location) {
		if (clazz == null || location == null) {
			return null;
		}
		return clazz.getResource(StringUtil.clean(location));
	}
	
	/**
	 * <p>해당 클래스를 기준으로 지정된 위치의 리소스를 검색하여 리소스 경로를 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.getResourcePath(null, null)              = null
	 * FileUtil.getResourcePath(null, *)                 = null
	 * FileUtil.getResourcePath(Class, null)             = null
	 * FileUtil.getResourcePath(Class, "")               = Root Path + Class Package Path
	 * FileUtil.getResourcePath(Class, "   ")            = Root Path + Class Package Path
	 * FileUtil.getResourcePath(Class, "mapping.xml")    = Root Path + Class Package Path + "/mapping.xml"
	 * FileUtil.getResourcePath(Class, "./mapping.xml")  = Root Path + Class Package Path + "/mapping.xml"
	 * FileUtil.getResourcePath(Class, "../poi/poi.xml") = Root Path + Class Package Path + "/../poi/poi.xml"
	 * FileUtil.getResourcePath(Class, "/log4j2.xml")    = Root Path + "/WEB-INF/classes/log4j2.xml"
	 * FileUtil.getResourcePath(Class, "kr/co/")         = Root Path + Class Package Path + "/kr/co/"
	 * FileUtil.getResourcePath(Class, "/kr/co/")        = Root Path + "/WEB-INF/classes/kr/co/"
	 * </pre>
	 * 
	 * @param clazz    (Class 객체)
	 * @param location (검색할 리소스 위치)
	 * @return String  (리소스 경로)
	 */
	public static String getResourcePath(Class<?> clazz, String location) {
		try {
			return cleanPath(getResource(clazz, location).getPath(), "/");
		} catch (Exception e) {
			String resourcePath = cleanPath(clazz.getResource("").getPath(), "/") + location;
			if (StringUtil.startsWith(location, "/")) {
				resourcePath = cleanPath(clazz.getResource("/").getPath(), "/") + location.substring(1);
			}
			logger.error(ExceptionUtil.addMessage(e, "java.io.FileNotFoundException: 리소스가 존재하지 않거나 액세스할 수 없습니다. [" + resourcePath + "]"));
			return null;
		}
	}
	
	/**
	 * <p>파일명을 제외한 파일 경로를 구해서 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.getFilePath(null)              = ""
	 * FileUtil.getFilePath("")                = ""
	 * FileUtil.getFilePath("   ")             = ""
	 * FileUtil.getFilePath("D:/aa/bb/cc.jsp") = "D:/aa/bb"
	 * FileUtil.getFilePath("D:\aa\bb\cc.jsp") = "D:\aa\bb"
	 * FileUtil.getFilePath("/aa/bb/cc.jsp")   = "/aa/bb"
	 * FileUtil.getFilePath("\aa\bb\cc.jsp")   = "\aa\bb"
	 * FileUtil.getFilePath("/aa/bb/cc/dd")    = "/aa/bb/cc"
	 * </pre>
	 * 
	 * @param filePath  (가져올 파일경로)
	 * @return String   (파일경로)
	 */
	public static String getFilePath(String filePath) {
		if (StringUtil.isBlank(filePath)) {
			return "";
		}
		String separator = "/";
		if (filePath.indexOf("\\") != -1) {
			separator = "\\";
		}
		return filePath.substring(0, filePath.lastIndexOf(separator));
	}
	
	/**
	 * <p>파일 경로에서 파일명을 구해서 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.getFileName(null)              = ""
	 * FileUtil.getFileName("")                = ""
	 * FileUtil.getFileName("   ")             = ""
	 * FileUtil.getFileName("D:/aa/bb/cc.jsp") = "cc.jsp"
	 * FileUtil.getFileName("D:\aa\bb\cc.jsp") = "cc.jsp"
	 * FileUtil.getFileName("/aa/bb/cc.jsp")   = "cc.jsp"
	 * FileUtil.getFileName("\aa\bb\cc.jsp")   = "cc.jsp"
	 * FileUtil.getFileName("/aa/bb/cc/dd")    = "dd"
	 * FileUtil.getFileName("ee.jsp")          = "ee.jsp"
	 * </pre>
	 * 
	 * @param filePath  (가져올 파일경로)
	 * @return String   (파일명)
	 */
	public static String getFileName(String filePath) {
		if (StringUtil.isBlank(filePath)) {
			return "";
		}
		String separator = "/";
		if (filePath.indexOf("\\") != -1) {
			separator = "\\";
		}
		return filePath.substring(filePath.lastIndexOf(separator) + 1);
	}
	
	/**
	 * <p>파일의 확장자를 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.getFileExt(null)       = ""
	 * FileUtil.getFileExt("")         = ""
	 * FileUtil.getFileExt("alin")     = ""
	 * FileUtil.getFileExt("alin.jpg") = "jpg"
	 * FileUtil.getFileExt("alin.GIF") = "GIF"
	 * </pre>
	 * 
	 * @param fileName (파일명)
	 * @return String  (파일의 확장자)
	 */
	public static String getFileExt(String fileName) {
		if (StringUtil.isBlank(fileName)) {
			return "";
		}
		if (fileName.lastIndexOf(".") == -1) {
			return "";
		}
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	/**
	 * <p>파일의 확장자를 소문자로 변경후 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.getFileExtLowerCase(null)       = ""
	 * FileUtil.getFileExtLowerCase("")         = ""
	 * FileUtil.getFileExtLowerCase("alin")     = ""
	 * FileUtil.getFileExtLowerCase("alin.jpg") = "jpg"
	 * FileUtil.getFileExtLowerCase("alin.GIF") = "gif"
	 * </pre>
	 * 
	 * @param fileName (파일명)
	 * @return String  (파일의 확장자)
	 */
	public static String getFileExtLowerCase(String fileName) {
		return getFileExt(fileName).toLowerCase();
	}
	
	/**
	 * <p>파일의 byte 크기에 따라 byte, KB, MB, GB 단위로 환산해서 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.toFileSize(null)          = ""
	 * FileUtil.toFileSize("")            = ""
	 * FileUtil.toFileSize("   ")         = ""
	 * FileUtil.toFileSize("123")         = "123byte"
	 * FileUtil.toFileSize("1023")        = "1023byte"
	 * FileUtil.toFileSize("1024")        = "1KB"
	 * FileUtil.toFileSize("10547")       = "10.3KB"
	 * FileUtil.toFileSize("3145728")     = "3MB"
	 * FileUtil.toFileSize("77457280")    = "73.9MB"
	 * FileUtil.toFileSize("35997280233") = "33.53GB"
	 * </pre>
	 * 
	 * @param obj     (파일의 byte 숫자형 객체)
	 * @return String (환산후 문자)
	 */
	public static String toFileSize(Object obj) {
		if (StringUtil.isBlank(obj)) {
			return "";
		}
		double fileSize = 0;
		String fileUnit = "";
		double byteSize = Double.parseDouble(StringUtil.clean(obj));
		if (byteSize >= (1024 * 1024 * 1024)) {
			fileSize = NumberUtil.round(byteSize / 1024 / 1024 / 1024, 2, 2);
			fileUnit = "GB";
		} else if (byteSize >= (1024 * 1024)) {
			fileSize = NumberUtil.round(byteSize / 1024 / 1024, 1, 2);
			fileUnit = "MB";
		} else if (byteSize >= 1024) {
			fileSize = NumberUtil.round(byteSize / 1024, 1, 2);
			fileUnit = "KB";
		} else {
			fileSize = NumberUtil.round(byteSize, 0, 1);
			fileUnit = "byte";
		}
		return NumberUtil.cleanDouble(fileSize) + fileUnit;
	}
	
	/**
	 * <p>파일의 확장자를 통해서 파일의 종류를 리턴한다.</p>
	 * 
	 * <pre>
	 * FileUtil.getFileCategory(null)        = "unknown"
	 * FileUtil.getFileCategory("")          = "unknown"
	 * FileUtil.getFileCategory("alin")      = "unknown"
	 * FileUtil.getFileCategory("alin.egg")  = "unknown"
	 * FileUtil.getFileCategory("alin.jpg")  = "jpg"
	 * FileUtil.getFileCategory("alin.jpeg") = "jpg"
	 * FileUtil.getFileCategory("alin.GIF")  = "gif"
	 * </pre>
	 * 
	 * @param fileName (파일명)
	 * @return String  (파일의 종류)
	 */
	public static String getFileCategory(String fileName) {
		String extension = getFileExtLowerCase(fileName);
		if (!StringUtil.isBlank(extension)) {
			if (StringUtil.equalsSplit("alz", extension, "[|]")) {
				return "alz";
			} else if (StringUtil.equalsSplit("avi|divx|asx|asf|wmv|mpg|mpeg|mpeg4|flv|mov", extension, "[|]")) {
				return "avi";
			} else if (StringUtil.equalsSplit("bmp", extension, "[|]")) {
				return "bmp";
			} else if (StringUtil.equalsSplit("cab", extension, "[|]")) {
				return "cab";
			} else if (StringUtil.equalsSplit("doc|docx", extension, "[|]")) {
				return "doc";
			} else if (StringUtil.equalsSplit("exe", extension, "[|]")) {
				return "exe";
			} else if (StringUtil.equalsSplit("fla", extension, "[|]")) {
				return "fla";
			} else if (StringUtil.equalsSplit("gif", extension, "[|]")) {
				return "gif";
			} else if (StringUtil.equalsSplit("htm|html", extension, "[|]")) {
				return "html";
			} else if (StringUtil.equalsSplit("hwp", extension, "[|]")) {
				return "hwp";
			} else if (StringUtil.equalsSplit("jpg|jpeg", extension, "[|]")) {
				return "jpg";
			} else if (StringUtil.equalsSplit("aac|ac3|ape|flac|mp3|ogg|wma", extension, "[|]")) {
				return "mp3";
			} else if (StringUtil.equalsSplit("mp4", extension, "[|]")) {
				return "mp4";
			} else if (StringUtil.equalsSplit("pdf", extension, "[|]")) {
				return "pdf";
			} else if (StringUtil.equalsSplit("png", extension, "[|]")) {
				return "png";
			} else if (StringUtil.equalsSplit("ppt|pptx", extension, "[|]")) {
				return "ppt";
			} else if (StringUtil.equalsSplit("psd", extension, "[|]")) {
				return "psd";
			} else if (StringUtil.equalsSplit("swf", extension, "[|]")) {
				return "swf";
			} else if (StringUtil.equalsSplit("txt", extension, "[|]")) {
				return "txt";
			} else if (StringUtil.equalsSplit("xls|xlsx", extension, "[|]")) {
				return "xls";
			} else if (StringUtil.equalsSplit("xml", extension, "[|]")) {
				return "xml";
			} else if (StringUtil.equalsSplit("zip", extension, "[|]")) {
				return "zip";
			}
		}
		return "unknown";
	}
	
}