/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : FileDownload.java
 * @Description : 파일 다운로드 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileDownload {
	
	private static final Logger logger = LoggerFactory.getLogger(FileDownload.class);
	
	/**
	 * <p>파일 다운로드를 처리한다.</p>
	 * 
	 * @param request  (HttpServletRequest 객체)
	 * @param response (HttpServletResponse 객체)
	 * @param fileMap  (파일정보 Map class)
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response, Map<String, Object> fileMap) {
		String savePath = StringUtil.clean(fileMap.get("savePath"));
		String saveName = StringUtil.clean(fileMap.get("saveName"));
		String orgnName = StringUtil.clean(fileMap.get("orgnName"));
		String contentType = StringUtil.clean(fileMap.get("contentType"));
		if (!StringUtil.isBlank(savePath) && !StringUtil.isBlank(saveName) && !StringUtil.isBlank(orgnName) && !StringUtil.isBlank(contentType)) {
			download(request, response, savePath, saveName, orgnName, contentType);
		} else if (!StringUtil.isBlank(savePath) && !StringUtil.isBlank(saveName) && !StringUtil.isBlank(orgnName)) {
			download(request, response, savePath, saveName, orgnName);
		} else if (!StringUtil.isBlank(savePath) && !StringUtil.isBlank(saveName)) {
			download(request, response, savePath, saveName);
		} else {
			JsUtil.back(response, "다운로드 받을 파일정보가 없습니다!!");
		}
	}
	
	/**
	 * <p>파일 다운로드를 처리한다.</p>
	 * 
	 * @param request  (HttpServletRequest 객체)
	 * @param response (HttpServletResponse 객체)
	 * @param filePath (파일 전체경로)
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response, String filePath) {
		download(request, response, FileUtil.getFilePath(filePath), FileUtil.getFileName(filePath));
	}
	
	/**
	 * <p>파일 다운로드를 처리한다.</p>
	 * 
	 * @param request  (HttpServletRequest 객체)
	 * @param response (HttpServletResponse 객체)
	 * @param savePath (파일 저장경로)
	 * @param saveName (저장 파일명)
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response, String savePath, String saveName) {
		download(request, response, savePath, saveName, saveName);
	}
	
	/**
	 * <p>파일 다운로드를 처리한다.</p>
	 * 
	 * @param request  (HttpServletRequest 객체)
	 * @param response (HttpServletResponse 객체)
	 * @param savePath (파일 저장경로)
	 * @param saveName (저장 파일명)
	 * @param orgnName (원본 파일명)
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response, String savePath, String saveName, String orgnName) {
		download(request, response, savePath, saveName, orgnName, null);
	}
	
	/**
	 * <p>브라우저별로 Content-Disposition을 지정한다.</p>
	 * 
	 * @param request  (HttpServletRequest 객체)
	 * @param response (HttpServletResponse 객체)
	 * @param orgnName (원본 파일명)
	 */
	public static void setDisposition(HttpServletRequest request, HttpServletResponse response, String orgnName) {
		String browser = HeaderUtil.getBrowser(request);
		String encodeFileName = null;
		if (StringUtil.equalsSplit("Edge|MSIE", browser, "[|]")) {
			encodeFileName = EncodeUtil.encode(orgnName, "UTF-8").replaceAll("\\+", "%20");
		} else if (StringUtil.equalsSplit("Firefox|Opera|Safari", browser, "[|]")) {
			//if (StringUtil.equalsSplit("Opera", browser, "[|]")) {
			//	response.setContentType("application/octet-stream;charset=UTF-8");
			//}
			if ("jeus".equals(SystemUtil.getWas())) {
				encodeFileName = "\"" + orgnName + "\"";
			} else {
				encodeFileName = "\"" + EncodeUtil.getBytes(orgnName, "UTF-8", "8859_1") + "\"";
			}
		} else if (StringUtil.equalsSplit("Chrome", browser, "[|]")) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < orgnName.length(); i++) {
				char c = orgnName.charAt(i);
				if (c > '~') {
					sb.append(EncodeUtil.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodeFileName = sb.toString();
		} else {
			encodeFileName = "\"" + EncodeUtil.getBytes(orgnName, "UTF-8", "8859_1") + "\"";
		}
		response.setHeader("Content-Disposition", "attachment; filename=" + encodeFileName);
	}
	
	/**
	 * <p>파일 다운로드를 처리한다.</p>
	 * 
	 * @param request     (HttpServletRequest 객체)
	 * @param response    (HttpServletResponse 객체)
	 * @param savePath    (파일 저장경로)
	 * @param saveName    (저장 파일명)
	 * @param orgnName    (원본 파일명)
	 * @param contentType (파일 콘텐츠타입)
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response, String savePath, String saveName, String orgnName, String contentType) {
		if (StringUtil.isBlank(savePath)) {
			JsUtil.back(response, "다운로드 파일경로가 지정되지 않았습니다!!");
		} else if (StringUtil.containsArr(savePath, "./|../|.\\|..\\|\r|\n|\r\n", "[|]")) {
			JsUtil.back(response, "잘못된 다운로드 파일경로가 지정 되었습니다!!");
		} else if (StringUtil.isBlank(saveName)) {
			JsUtil.back(response, "파일명이 지정되지 않았습니다!!");
		} else if (StringUtil.containsArr(saveName, "./|../|.\\|..\\|\r|\n|\r\n", "[|]")) {
			JsUtil.back(response, "잘못된 파일명이 지정 되었습니다!!");
		} else if (StringUtil.isBlank(orgnName)) {
			JsUtil.back(response, "다운로드 파일명이 지정되지 않았습니다!!");
		} else if (StringUtil.containsArr(orgnName, "./|../|.\\|..\\|\r|\n|\r\n", "[|]")) {
			JsUtil.back(response, "잘못된 다운로드 파일명이 지정 되었습니다!!");
		} else if (StringUtil.containsArr(contentType, "\r|\n|\r\n", "[|]")) {
			JsUtil.back(response, "잘못된 파일타입이 지정 되었습니다!!");
		} else {
			File downloadFile = new File(FileUtil.cleanPath(savePath), saveName);
			if (downloadFile.exists() && downloadFile.length() > 0) {
				BufferedInputStream bis = null;
				BufferedOutputStream bos = null;
				try {
					response.reset();
					//response.setBufferSize((int)downloadFile.length());
					response.setContentType(StringUtil.isBlank(contentType) ? "application/octet-stream" : contentType);
					response.setContentLength((int)downloadFile.length());
					setDisposition(request, response, orgnName);
					//response.setHeader("Content-Transfer-Encoding", "binary");
					bis = new BufferedInputStream(new FileInputStream(downloadFile));
					bos = new BufferedOutputStream(response.getOutputStream());
					//FileCopyUtils.copy(bis, bos);
					//bos.flush();
					int len = 0;
					byte[] b = new byte[2048];
					while ((len = bis.read(b)) != -1) {
						bos.write(b, 0, len);
					}
				} catch (Exception e) {
					logger.error(ExceptionUtil.addMessage(e, "첨부파일 다운로드 실패!! [파일경로 : " + downloadFile.toString() + "]"));
					JsUtil.back(response, "첨부파일 다운로드 실패!!");
				} finally {
					if (bos != null) try { bos.close(); } catch (Exception e) {};
					if (bis != null) try { bis.close(); } catch (Exception e) {};
				}
			} else {
				logger.warn("다운로드 받을 파일이 존재하지 않습니다. [파일경로 : " + downloadFile.toString() + "]");
				JsUtil.back(response, "다운로드 받을 파일이 존재하지 않습니다!!");
			}
		}
	}
	
}
