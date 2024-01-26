/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : FileUpload.java
 * @Description : 파일 업로드 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUpload.class);
	
	/**
	 * <p>단일 파일 업로드를 처리한다.</p>
	 * 
	 * @param file                            (MultipartFile 객체)
	 * @return {@literal Map<String, Object>} (저장한 파일정보)
	 */
	public static Map<String, Object> upload(MultipartFile file) {
		return upload(file, null);
	}
	
	/**
	 * <p>단일 파일 업로드를 처리한다.</p>
	 * 
	 * @param file                            (MultipartFile 객체)
	 * @param savePath                        (저장할 파일 서브경로)
	 * @return {@literal Map<String, Object>} (저장한 파일정보)
	 */
	public static Map<String, Object> upload(MultipartFile file, String savePath) {
		return upload(file, savePath, null);
	}
	
	/**
	 * <p>단일 파일 업로드를 처리한다.</p>
	 * 
	 * @param file                            (MultipartFile 객체)
	 * @param savePath                        (저장할 파일 서브경로)
	 * @param fileSummary                     (첨부파일 요약 내용)
	 * @return {@literal Map<String, Object>} (저장한 파일정보)
	 */
	public static Map<String, Object> upload(MultipartFile file, String savePath, String fileSummary) {
		Map<String, Object> fileMap = null;
		if (file != null && !file.isEmpty()) {
			fileMap = upload(file, savePath, null, fileSummary, true, true);
		}
		return fileMap;
	}
	
	/**
	 * <p>다중 파일 업로드를 처리한다.</p>
	 * 
	 * @param fileList              (MultipartFile 객체 리스트)
	 * @return {@literal List<Map>} (저장한 파일정보 리스트)
	 */
	public static List<Map<String, Object>> upload(List<MultipartFile> fileList) {
		return upload(fileList, null);
	}
	
	/**
	 * <p>다중 파일 업로드를 처리한다.</p>
	 * 
	 * @param fileList              (MultipartFile 객체 리스트)
	 * @param savePath              (저장할 파일 서브경로)
	 * @return {@literal List<Map>} (저장한 파일정보 리스트)
	 */
	public static List<Map<String, Object>> upload(List<MultipartFile> fileList, String savePath) {
		return upload(fileList, savePath, null);
	}
	
	/**
	 * <p>다중 파일 업로드를 처리한다.</p>
	 * 
	 * @param fileList              (MultipartFile 객체 리스트)
	 * @param savePath              (저장할 파일 서브경로)
	 * @param fileSummaryList       (첨부파일 요약 내용 리스트)
	 * @return {@literal List<Map>} (저장한 파일정보 리스트)
	 */
	public static List<Map<String, Object>> upload(List<MultipartFile> fileList, String savePath, List<String> fileSummaryList) {
		List<Map<String, Object>> uploadList = null;
		if (fileList != null && fileList.size() > 0) {
			uploadList = new ArrayList<Map<String, Object>>();
			MultipartFile file = null;
			Map<String, Object> fileMap = null;
			for (int i = 0; i < fileList.size(); i++) {
				file = fileList.get(i);
				if (file != null && !file.isEmpty()) {
					fileMap = upload(file, savePath, null, (fileSummaryList != null && fileSummaryList.size() > i) ? fileSummaryList.get(i) : null, true, true);
					if (fileMap != null && !fileMap.isEmpty()) {
						uploadList.add(fileMap);
					}
				}
			}
		}
		return uploadList;
	}
	
	/**
	 * <p>파일 업로드를 처리한다.</p>
	 * 
	 * @param file                            (MultipartFile 객체)
	 * @param savePath                        (저장할 파일 서브경로)
	 * @param saveName                        (저장할 파일이름)
	 * @param fileSummary                     (파일 요약 내용)
	 * @param isAddDatePath                   (저장할 파일경로에 년도, 월 추가 여부)
	 * @param isSaveWithExt                   (파일 저장시 파일확장자 포함 여부)
	 * @return {@literal Map<String, Object>} (저장한 파일정보)
	 */
	public static Map<String, Object> upload(MultipartFile file, String savePath, String saveName, String fileSummary, boolean isAddDatePath, boolean isSaveWithExt) {
		Map<String, Object> fileMap = null;
		File saveFolder = null;
		try {
			if (file != null && !file.isEmpty()) {
				Calendar calendar = Calendar.getInstance();
				String datePath = "/" + calendar.get(Calendar.YEAR) + "/" + StringUtil.prefixZero(calendar.get(Calendar.MONTH) + 1, 2);
				if (StringUtil.isBlank(savePath)) {
					savePath = datePath;
				} else {
					if (isAddDatePath) {
						savePath += datePath;
					}
				}
				savePath = FileUtil.cleanPath(savePath, "/");
				saveFolder = new File(FileUtil.cleanPath(Const.UPLOAD_PATH + savePath));
				saveFolder.mkdirs();
				if (StringUtil.isBlank(saveName)) {
					saveName = DateUtil.getTimeStamp() + NumberUtil.randomStr(1, 99999);
				}
				if (isSaveWithExt) {
					saveName += "." + FileUtil.getFileExt(file.getOriginalFilename());
				}
				// 첨부파일 저장
				file.transferTo(new File(saveFolder, saveName));
				// 첨부파일 정보 생성
				fileMap = new HashMap<String, Object>();
				fileMap.put("fileDiv", file.getName().replaceAll("File$", ""));
				fileMap.put("savePath", savePath);
				fileMap.put("saveName", saveName);
				fileMap.put("orgnName", file.getOriginalFilename());
				fileMap.put("fileExt", FileUtil.getFileExt(file.getOriginalFilename()));
				fileMap.put("fileSize", file.getSize());
				fileMap.put("contentType", file.getContentType());
				fileMap.put("fileSummary", StringUtil.clean(fileSummary));
			}
		} catch (Exception e) {
			logger.error(ExceptionUtil.addMessage(e, "첨부파일 업로드 실패!!"));
		} finally {
			saveFolder = null;
		}
		return fileMap;
	}
	
}