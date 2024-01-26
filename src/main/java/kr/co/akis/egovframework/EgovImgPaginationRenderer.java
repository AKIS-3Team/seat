/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : EgovImgPaginationRenderer.java
 * @Description : 전자정부 표준 프레임워크 페이징 처리 Renderer 클래스.
 * @Version     : 3.0.0
 * @History     : [2011.11.29] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.egovframework;

import egovframework.rte.ptl.mvc.tags.ui.pagination.AbstractPaginationRenderer;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public class EgovImgPaginationRenderer extends AbstractPaginationRenderer {
	
	/**
	 * PaginationRenderer
	 * 
	 * @see 개발프레임웍크 실행환경 개발팀
	 */
	public EgovImgPaginationRenderer() {
		firstPageLabel = "<a href=\"?pageNo={1}\" onclick=\"{0}({1});return false;\" class=\"first\"><span>첫 페이지로 이동</span></a>\n";
		previousPageLabel = "<a href=\"?pageNo={1}\" onclick=\"{0}({1});return false;\" class=\"prev\"><span>이전 10 페이지 이동</span></a>\n";
		currentPageLabel = "<a href=\"?pageNo={0}\" onclick=\"return false;\" class=\"on\" title=\"현재위치\">{0}</a>\n";
		otherPageLabel = "<a href=\"?pageNo={1}\" onclick=\"{0}({1});return false;\">{2}</a>\n";
		nextPageLabel = "<a href=\"?pageNo={1}\" onclick=\"{0}({1});return false;\" class=\"next\"><span>다음 10 페이지 이동</span></a>\n";
		lastPageLabel = "<a href=\"?pageNo={1}\" onclick=\"{0}({1});return false;\" class=\"last\"><span>마지막 페이지로 이동</span></a>";
	}
	
	/**
	 * 리스트 페이지에서 데이터의 시작 번호를 구한다.
	 * 
	 * @param paginationInfo (페이징 처리 정보)
	 * @return int           (데이터 시작번호)
	 */
	public static int pageStartNum(PaginationInfo paginationInfo) {
		int result = 1;
		if (paginationInfo != null) {
			result = paginationInfo.getTotalRecordCount() - ((paginationInfo.getCurrentPageNo() - 1) * paginationInfo.getRecordCountPerPage());
		}
		return result;
	}
	
	/**
	 * 리스트 페이지에서 데이터의 시작 번호를 오름차순으로 구한다.
	 * 
	 * @param paginationInfo (페이징 처리 정보)
	 * @return int           (데이터 시작번호)
	 */
	public static int pageStartNumAsc(PaginationInfo paginationInfo) {
		int result = 1;
		if (paginationInfo != null) {
			result = (paginationInfo.getCurrentPageNo() - 1) * paginationInfo.getRecordCountPerPage() + 1;
		}
		return result;
	}
	
	/**
	 * 리스트 페이지에서 해당 데이터의 번호를 구한다.
	 * 
	 * @param paginationInfo (페이징 처리 정보)
	 * @param idx            (해당 데이터 인덱스 번호)
	 * @return int           (데이터 번호)
	 */
	public static int pageDataNum(PaginationInfo paginationInfo, int idx) {
		return pageStartNum(paginationInfo) - idx;
	}
	
	/**
	 * 리스트 페이지에서 해당 데이터의 번호를 오름차순으로 구한다.
	 * 
	 * @param paginationInfo (페이징 처리 정보)
	 * @param idx            (해당 데이터 인덱스 번호)
	 * @return int           (데이터 번호)
	 */
	public static int pageDataNumAsc(PaginationInfo paginationInfo, int idx) {
		return pageStartNumAsc(paginationInfo) + idx;
	}
	
}