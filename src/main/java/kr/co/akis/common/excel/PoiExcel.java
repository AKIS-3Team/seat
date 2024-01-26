/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : PoiExcel.java
 * @Description : POI Excel 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.common.excel;

import java.awt.Point;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.akis.util.DateUtil;
import kr.co.akis.util.FileDownload;
import kr.co.akis.util.JsUtil;
import kr.co.akis.util.NumberUtil;
import kr.co.akis.util.StringUtil;

public class PoiExcel {
	
	private static final Logger logger = LoggerFactory.getLogger(PoiExcel.class);
	
	/**
	 * <p>엑셀 데이터 유효성 체크 후 결과를 리턴한다.</p>
	 * 
	 * @param sheet                           (Sheet 객체)
	 * @param validateArr                     (체크할 유효성 정보)
	 * @return {@literal Map<String, Object>} (유효성 체크 결과)
	 */
	public static Map<String, Object> validate(Sheet sheet, String[][] validateArr) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "SUCCESS");
		
		if (validateArr.length != sheet.getRow(0).getPhysicalNumberOfCells()) {
			resultMap.put("result", "ERROR_COLUMN_CNT");
		} else {
			loop1 :
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				resultMap.put("rowNo", i);
				Row row = sheet.getRow(i);
				if (row != null) {
					for (int j = 0; j < validateArr.length; j++) {
						String columnName = StringUtil.clean(validateArr[j][0]);
						String dataType = StringUtil.clean(validateArr[j][1]);
						String dataLimit = StringUtil.clean(validateArr[j][2]);
						String nullYn = StringUtil.clean(validateArr[j][3]);
						resultMap.put("columnNo", j);
						resultMap.put("columnName", columnName);
						resultMap.put("dataType", dataType);
						resultMap.put("dataLimit", dataLimit);
						resultMap.put("nullYn", nullYn);
						
						String cellVal = StringUtil.clean(getValue(row.getCell(j)));
						if (!StringUtil.isBlank(cellVal)) {
							// 데이터 타입 체크
							if ("Number".equals(dataType)) {
								if (!NumberUtil.isNumeric(cellVal)) {
									resultMap.put("result", "ERROR_DATA_TYPE");
									break loop1;
								}
							} else if ("Date".equals(dataType)) {
								if (StringUtil.isBlank(dataLimit)) {
									if (!DateUtil.isDate(cellVal)) {
										resultMap.put("result", "ERROR_DATA_TYPE");
										break loop1;
									}
								} else {
									if (!DateUtil.isDateFormat(StringUtil.left(cellVal, dataLimit.length()), dataLimit)) {
										resultMap.put("result", "ERROR_DATA_TYPE");
										break loop1;
									}
								}
							}
							// 데이터 길이 체크
							if (!StringUtil.isBlank(dataLimit)) {
								if ("Number".equals(dataType)) {
									String num = NumberUtil.cleanDouble(cellVal).replaceAll("\\.", "");
									if (num.length() > NumberUtil.toInt(dataLimit)) {
										resultMap.put("result", "ERROR_DATA_LENGTH");
										break loop1;
									}
								} else if ("String".equals(dataType)) {
									if (StringUtil.getByteLen(cellVal) > NumberUtil.toInt(dataLimit)) {
										resultMap.put("result", "ERROR_DATA_LENGTH");
										break loop1;
									}
								}
							}
						}
						// 필수항목 체크
						if ("N".equals(nullYn) && StringUtil.isBlank(cellVal)) {
							resultMap.put("result", "ERROR_DATA_NULL");
							break loop1;
						}
					}
				}
			}
		}
		
		return resultMap;
	}
	
	/**
	 * <p>유효성 체크 결과를 가지고 오류 메시지 생성해서 리턴한다.</p>
	 * 
	 * @param resultMap (유효성 체크 결과)
	 * @return String   (오류 메시지)
	 */
	public static String getErrorMessage(Map<String, Object> resultMap) {
		StringBuilder sb = new StringBuilder();
		if ("ERROR_COLUMN_CNT".equals(resultMap.get("result"))) {
			sb.append("엑셀 업로드 파일 양식이 옳바르지 않습니다!!");
		} else {
			int rowNo = NumberUtil.toInt(resultMap.get("rowNo")) + 1;
			int columnNo = NumberUtil.toInt(resultMap.get("columnNo")) + 1;
			sb.append("엑셀 데이터 반입을 실패하였습니다!!\\n");
			if ("ERROR_DATA_TYPE".equals(resultMap.get("result"))) {
				sb.append("데이터 타입을 확인하세요.\\n");
			} else if ("ERROR_DATA_LENGTH".equals(resultMap.get("result"))) {
				sb.append("데이터 길이를 확인하세요.\\n");
			} else if ("ERROR_DATA_NULL".equals(resultMap.get("result"))) {
				sb.append("해당 컬럼은 필수입력 항목입니다.\\n");
			} else {
				sb.append("알 수 없는 시스템 오류!!\\n");
			}
			sb.append("Row : ").append(rowNo).append("행, ");
			sb.append("Column : ").append(columnNo).append("열 (").append(resultMap.get("columnName")).append("), ");
			sb.append("Type : ").append(resultMap.get("dataType"));
			if ("Date".equals(resultMap.get("dataType"))) {
				if (!StringUtil.isBlank(resultMap.get("dataLimit"))) {
					sb.append(", Format : ").append(resultMap.get("dataLimit"));
				}
			} else {
				sb.append(", Length : ").append(resultMap.get("dataLimit"));
			}
		}
		return sb.toString();
	}
	
	/**
	 * <p>해당하는 Cell 값을 리턴한다.</p>
	 * 
	 * <pre>
	 * PoiExcel.getValue(BLANK)                        = ""
	 * PoiExcel.getValue(STRING  - "   ")              = "   "
	 * PoiExcel.getValue(STRING  - " JJang  ")         = " JJang  "
	 * PoiExcel.getValue(STRING  - "2023-09-07")       = "2023-09-07"
	 * PoiExcel.getValue(STRING  - "2023.09.07")       = "2023.09.07"
	 * PoiExcel.getValue(STRING  - "2023/09/07")       = "2023/09/07"
	 * PoiExcel.getValue(Date    - "2023-09-07")       = "2023-09-07 00:00:00"
	 * PoiExcel.getValue(Date    - "2023/09/07")       = "2023-09-07 00:00:00"
	 * PoiExcel.getValue(Date    - "2023-09-07 13:37") = "2023-09-07 13:37:00"
	 * PoiExcel.getValue(NUMERIC - "123.7")            = "123.7"
	 * PoiExcel.getValue(NUMERIC - "123")              = "123"
	 * </pre>
	 * 
	 * @param cell    (값을 가져올 Cell)
	 * @return String (Cell 값)
	 */
	public static String getValue(Cell cell) {
		return getValue(cell, null);
	}
	
	/**
	 * <p>해당하는 Cell 값을 리턴한다.</p>
	 * 
	 * <pre>
	 * PoiExcel.getValue(BLANK, *)                        = ""
	 * PoiExcel.getValue(STRING  - "   ", *)              = "   "
	 * PoiExcel.getValue(STRING  - " JJang  ", *)         = " JJang  "
	 * PoiExcel.getValue(STRING  - "2023-09-07", *)       = "2023-09-07"
	 * PoiExcel.getValue(STRING  - "2023.09.07", *)       = "2023.09.07"
	 * PoiExcel.getValue(STRING  - "2023/09/07", *)       = "2023/09/07"
	 * PoiExcel.getValue(Date    - "2023-09-07", *)       = "2023-09-07 00:00:00"
	 * PoiExcel.getValue(Date    - "2023/09/07", *)       = "2023-09-07 00:00:00"
	 * PoiExcel.getValue(Date    - "2023-09-07 13:37", *) = "2023-09-07 13:37:00"
	 * PoiExcel.getValue(NUMERIC - "123.7", *)            = "123.7"
	 * PoiExcel.getValue(NUMERIC - "123", *)              = "123"
	 * </pre>
	 * 
	 * @param cell          (값을 가져올 Cell)
	 * @param workbook      (Workbook 객체)
	 * @return String       (Cell 값)
	 */
	public static String getValue(Cell cell, Workbook workbook) {
		return getValue(cell, workbook, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * <p>해당하는 Cell 값을 리턴한다.</p>
	 * 
	 * <pre>
	 * PoiExcel.getValue(BLANK, *, *)                                         = ""
	 * PoiExcel.getValue(STRING  - "   ", *, *)                               = "   "
	 * PoiExcel.getValue(STRING  - " JJang  ", *, *)                          = " JJang  "
	 * PoiExcel.getValue(STRING  - "2023-09-07", *, *)                        = "2023-09-07"
	 * PoiExcel.getValue(STRING  - "2023.09.07", *, *)                        = "2023.09.07"
	 * PoiExcel.getValue(STRING  - "2023/09/07", *, *)                        = "2023/09/07"
	 * PoiExcel.getValue(Date    - "2023-09-07", "yyyy-MM-dd HH:mm:ss")       = "2023-09-07 00:00:00"
	 * PoiExcel.getValue(Date    - "2023/09/07", "yyyy-MM-dd HH:mm:ss")       = "2023-09-07 00:00:00"
	 * PoiExcel.getValue(Date    - "2023-09-07 13:37:33", "yyyy.MM.dd HH:mm") = "2023.09.07 13:37"
	 * PoiExcel.getValue(NUMERIC - "123.7", *, *)                             = "123.7"
	 * PoiExcel.getValue(NUMERIC - "123", *, *)                               = "123"
	 * </pre>
	 * 
	 * @param cell          (값을 가져올 Cell)
	 * @param workbook      (Workbook 객체)
	 * @param format        (날짜 포멧)
	 * @return String       (Cell 값)
	 */
	public static String getValue(Cell cell, Workbook workbook, String format) {
		String result = "";
		if (cell != null) {
			switch (cell.getCellTypeEnum()) {
				case STRING :
					result = cell.getStringCellValue();
					break;
				case NUMERIC :
					if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
						result = DateUtil.toDateFormat(cell.getDateCellValue(), format, Locale.KOREA);
					} else {
						result = NumberUtil.toDoubleStr(cell.getNumericCellValue());
						result = NumberUtil.cleanDouble(result, true);
					}
					break;
				case BOOLEAN :
					result = String.valueOf(cell.getBooleanCellValue());
					break;
				case FORMULA :
					if (workbook == null) {
						result = cell.getCellFormula();
					} else {
						FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
						CellValue cellValue = evaluator.evaluate(cell);
						if (evaluator.evaluateFormulaCellEnum(cell) == CellType.STRING) {
							result = cellValue.getStringValue();
						} else {
							result = cellValue.formatAsString();
						}
					}
					break;
				case BLANK :
					result = "";
					break;
				case ERROR :
					result = String.valueOf(cell.getErrorCellValue());
					break;
				default :
					result = "";
			}
		}
		return result;
	}
	
	/**
	 * <p>해당하는 Sheet에서 지정된 범위의 Cell 타입을 리턴한다.</p>
	 * 
	 * @param sheet       (Sheet 객체)
	 * @param point       (Cell 범위)
	 * @return CellType[] (Cell 타입)
	 */
	public static CellType[] getType(Sheet sheet, Point point) {
		CellType[] type = new CellType[point.x + 1];
		Row row = sheet.getRow(point.y);
		for (int i = 0; i < type.length; i++) {
			type[i] = row.getCell(i).getCellTypeEnum();
		}
		return type;
	}
	
	/**
	 * <p>해당하는 Sheet에서 지정된 범위의 Cell 스타일을 리턴한다.</p>
	 * 
	 * @param workbook     (Workbook 객체)
	 * @param sheetNo      (Sheet 번호)
	 * @param point        (Cell 범위)
	 * @return CellStyle[] (Cell 스타일)
	 */
	public static CellStyle[] getStyle(Workbook workbook, int sheetNo, Point point) {
		CellStyle[] style = null;
		if (workbook instanceof HSSFWorkbook) {
			style = new HSSFCellStyle[point.x + 1];
		} else if (workbook instanceof XSSFWorkbook) {
			style = new XSSFCellStyle[point.x + 1];
		}
		Row row = workbook.getSheetAt(sheetNo).getRow(point.y);
		for (int i = 0; i < style.length; i++) {
			style[i] = row.getCell(i).getCellStyle();
		}
		return style;
	}
	
	/**
	 * <p>해당하는 Sheet의 지정된 범위의 Cell을 병합하고 기본 스타일을 입힌다.</p>
	 * 
	 * @param sheet    (Sheet 객체)
	 * @param firstRow (병합할 Row 시작위치)
	 * @param lastRow  (병합할 Row 끝위치)
	 * @param firstCol (병합할 Cell 시작위치)
	 * @param lastCol  (병합할 Cell 끝위치)
	 */
	public static void addMergedRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
		// Font 를 설정한다.
		Workbook workbook = sheet.getWorkbook();
		Font font = workbook.createFont();
		font.setFontName("맑은 고딕");
		font.setFontHeightInPoints((short)10);
		// Cell 스타일을 설정한다.
		CellStyle style = workbook.createCellStyle();
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(font);
		// 병합할 Row 와 Cell 을 생성한다.
		addMergedRegion(sheet, firstRow, lastRow, firstCol, lastCol, style);
	}
	
	/**
	 * <p>해당하는 Sheet의 지정된 범위의 Cell을 병합하고 해당 스타일을 입힌다.</p>
	 * 
	 * @param sheet    (Sheet 객체)
	 * @param firstRow (병합할 Row 시작위치)
	 * @param lastRow  (병합할 Row 끝위치)
	 * @param firstCol (병합할 Cell 시작위치)
	 * @param lastCol  (병합할 Cell 끝위치)
	 * @param style    (CellStyle 객체)
	 */
	public static void addMergedRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, CellStyle style) {
		// 병합할 Row 와 Cell 을 생성한다.
		Row newRow = null;
		Cell newCell = null;
		for (int i = firstRow; i <= lastRow; i++) {
			newRow = sheet.createRow(i);
			for (int j = firstCol; j <= lastCol; j++) {
				newCell = newRow.createCell(j, CellType.BLANK);
				newCell.setCellStyle(style);
			}
		}
		// Cell 을 병합한다.
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
	}
	
	/**
	 * <p>파일 생성없이 직접 다운로드한다.</p>
	 * 
	 * @param request  (HttpServletRequest 객체)
	 * @param response (HttpServletResponse 객체)
	 * @param workbook (Workbook 객체)
	 * @param fileName (다운로드 받을 파일명)
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response, Workbook workbook, String fileName) {
		ServletOutputStream sos = null;
		try {
			sos = response.getOutputStream();
			response.reset();
			response.setContentType("application/octet-stream");
			FileDownload.setDisposition(request, response, fileName);
			workbook.write(sos);
		} catch (Exception e) {
			logger.error(e.toString(), e.fillInStackTrace());
			JsUtil.back(response, "Excel 파일 다운로드 실패!! ");
		} finally {
			if (sos != null) try { sos.close(); } catch (Exception e) {};
		}
	}
	
}