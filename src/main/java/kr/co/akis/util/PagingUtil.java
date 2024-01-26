/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : PagingUtil.java
 * @Description : 페이징 처리 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

public class PagingUtil {
	
	/**
	 * <pre>
	 * ==============================================================
	 * - Required Fields -
	 * 이 필드들은 페이징 계산을 위해 반드시 입력되어야 하는 필드 값들이다.
	 * 
	 * pageNo     : 현재 페이지 번호
	 * cntPerPage : 페이지당 데이터 출력 수
	 * pageSize   : 페이지번호 출력 단위
	 * totalCnt   : 전체 데이터 수
	 * ==============================================================
	 * </pre>
	 */
	private int pageNo;
	private int cntPerPage;
	private int pageSize;
	private int totalCnt;
	
	/**
	 * <p>현재 페이지 번호 리턴</p>
	 * 
	 * @return int (페이지 번호)
	 */
	public int getPageNo() {
		return pageNo;
	}
	/**
	 * <p>현재 페이지 번호 정의</p>
	 * 
	 * @param pageNo (페이지 번호)
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * <p>페이지당 데이터 출력 수 리턴</p>
	 * 
	 * @return int (데이터 출력 수)
	 */
	public int getCntPerPage() {
		return cntPerPage;
	}
	/**
	 * <p>페이지당 데이터 출력 수 정의</p>
	 * 
	 * @param cntPerPage (데이터 출력 수)
	 */
	public void setCntPerPage(int cntPerPage) {
		this.cntPerPage = cntPerPage;
	}
	/**
	 * <p>페이지번호 출력 단위 리턴</p>
	 * 
	 * @return int (페이지번호 출력 단위)
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * <p>페이지번호 출력 단위 정의</p>
	 * 
	 * @param pageSize (페이지번호 출력 단위)
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * <p>전체 데이터 수 리턴</p>
	 * 
	 * @return int (전체 데이터 수)
	 */
	public int getTotalCnt() {
		return totalCnt;
	}
	/**
	 * <p>전체 데이터 수 정의</p>
	 * 
	 * @param totalCnt (전체 데이터 수)
	 */
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	
	/**
	 * <pre>
	 * ==============================================================
	 * 아래의 메소드들은 Required Fields 값을 바탕으로 계산한다.
	 * 따라서 Required Fields 값을 반드시 입력 해야 한다.
	 * ==============================================================
	 * </pre>
	 */

	/**
	 * <p>전체 페이지 수를 구한다.</p>
	 * 
	 * @return int (전체 페이지 수)
	 */
	public int getTotalPageCnt() {
		int totalPageCnt = 0;
		if (getCntPerPage() > 0 && getTotalCnt() >= 0) {
			totalPageCnt = (getTotalCnt() - 1) / getCntPerPage() + 1;
		}
		return totalPageCnt; 
	}

	/**
	 * <p>현재 페이지 번호를 전체 페이지 수와 비교하여 재정의 한다.</p>
	 * <p>현재 페이지 번호가 전체 페이지 수보다 클 경우 현재 페이지 번호를 전체 페이지 수로 Fix 시킨다.</p>
	 * 
	 * @return int (현재 페이지 번호)
	 */
	public int getFixPageNo() {
		int pageNo = 1;
		if (getPageNo() > 0 && getTotalPageCnt() > 0) {
			if (getPageNo() > getTotalPageCnt()) {
				pageNo = getTotalPageCnt();
			} else {
				pageNo = getPageNo();
			}
		}
		return pageNo;
	}
	
	/**
	 * <p>리스트 페이지에서 데이터의 시작 번호를 구한다.</p>
	 * 
	 * @return int (데이터 시작번호)
	 */
	public int getStartNum() {
		return getTotalCnt() - ((getFixPageNo() - 1) * getCntPerPage());
	}
	
	/**
	 * <p>리스트 페이지에서 데이터의 시작 번호를 오름차순으로 구한다.</p>
	 * 
	 * @return int (데이터 시작번호)
	 */
	public int getStartNumAsc() {
		return (getFixPageNo() - 1) * getCntPerPage() + 1;
	}
	
	/**
	 * <p>페이징 SQL 조건절에서 사용되는 시작 Index를 구한다.</p>
	 * 
	 * @return int (시작 인덱스)
	 */
	public int getStartIndex() {
		return ((getFixPageNo() - 1) * getCntPerPage()) + 1;
	}
	
	/**
	 * <p>페이징 SQL 조건절에서 사용되는 마지막 Index를 구한다.</p>
	 * 
	 * @return int (마지막 인덱스)
	 */
	public int getEndIndex() {
		return getFixPageNo() * getCntPerPage();
	}
	
	/**
	 * <p>페이징 SQL 조건절에서 사용되는 스킵 Index를 구한다.</p>
	 * 
	 * @return int (스킵 인덱스)
	 */
	public int getSkipIndex() {
		return (getFixPageNo() - 1) * getCntPerPage();
	}
	
	/**
	 * <p>국문 페이징 처리를 한다.</p>
	 * 
	 * @param linkUrl (링크 URL)
	 * @return String (페이징 HTML)
	 */
	public String getPagingKor(String linkUrl) {
		int prev10 = (int)Math.floor((getFixPageNo() - 1) / Math.floor(getPageSize())) * getPageSize();
		int next10 = prev10 + getPageSize() + 1;
		StringBuilder sb = new StringBuilder();
		sb.append("<div id=\"Page-Index\">\n");
		//if (getFixPageNo() != 1) {
		//	sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=1").append("\">");
		//	sb.append("<img src=\"/assets/img/btn/btn_prevs.gif\" alt=\"첫 페이지로 이동\" />");
		//	sb.append("</a>\n");
		//} else {
		//	sb.append("<img src=\"/assets/img/btn/btn_prevs.gif\" alt=\"첫 페이지로 이동\" />\n");
		//}
		if (prev10 > 0) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(prev10).append("\">");
			sb.append("<img src=\"/assets/img/kor/common/list_prev.gif\" alt=\"10 페이지 이전으로 이동\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/kor/common/list_prev.gif\" alt=\"10 페이지 이전으로 이동\" />\n");
		}
		if (getTotalPageCnt() > 0) {
			sb.append("<span>\n");
			for (int i = (prev10 + 1); i < next10 && i <= getTotalPageCnt(); i++) {
				sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(i).append("\"");
				if (i == getFixPageNo()) {
					sb.append(" class=\"on\"");
				}
				//if (i == (prev10 + 1)) {
				//	sb.append(" class=\"first\"");
				//}
				sb.append(">");
				if (i == getFixPageNo()) {
					sb.append("<strong>").append(i).append("</strong>");
				} else {
					sb.append(i);
				}
				sb.append("<span class=\"blind\"> 페이지로 이동</span></a>\n");
			}
			sb.append("</span>\n");
		} else {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=1").append("\" class=\"on\">");
			sb.append("<span><strong>1<span class=\"blind\"> 페이지</span></strong></span>");
			sb.append("</a>\n");
		}
		if (getTotalPageCnt() >= next10) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(next10).append("\">");
			sb.append("<img src=\"/assets/img/kor/common/list_next.gif\" alt=\"10 페이지 다음으로 이동\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/kor/common/list_next.gif\" alt=\"10 페이지 다음으로 이동\" />\n");
		}
		//if (getTotalPageCnt() > 0 && getFixPageNo() != getTotalPageCnt()) {
		//	sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(getTotalPageCnt()).append("\">");
		//	sb.append("<img src=\"/assets/img/btn/btn_nexts.gif\" alt=\"마지막 페이지로 이동\" />");
		//	sb.append("</a>\n");
		//} else {
		//	sb.append("<img src=\"/assets/img/btn/btn_nexts.gif\" alt=\"마지막 페이지로 이동\" />\n");
		//}
		sb.append("</div>\n");
		return sb.toString();
	}
	
	/**
	 * <p>영문 페이징 처리를 한다.</p>
	 * 
	 * @param linkUrl (링크 URL)
	 * @return String (페이징 HTML)
	 */
	public String getPagingEng(String linkUrl) {
		int prev10 = (int)Math.floor((getFixPageNo() - 1) / Math.floor(getPageSize())) * getPageSize();
		int next10 = prev10 + getPageSize() + 1;
		StringBuilder sb = new StringBuilder();
		sb.append("<div id=\"Eng-Page-Index\">\n");
		//if (getFixPageNo() != 1) {
		//	sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=1").append("\">");
		//	sb.append("<img src=\"/assets/img/kor/btn/btn_list_first.gif\" alt=\"Move to first page\" />");
		//	sb.append("</a>\n");
		//} else {
		//	sb.append("<img src=\"/assets/img/kor/btn/btn_list_first.gif\" alt=\"Move to first page\" />\n");
		//}
		if (prev10 > 0) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(prev10).append("\">");
			sb.append("<img src=\"/assets/img/kor/common/list_prev.gif\" alt=\"Move to 10 pages before\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/kor/common/list_prev.gif\" alt=\"Move to 10 pages before\" />\n");
		}
		if (getTotalPageCnt() > 0) {
			sb.append("<span>\n");
			for (int i = (prev10 + 1); i < next10 && i <= getTotalPageCnt(); i++) {
				sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(i).append("\"");
				if (i == getFixPageNo()) {
					sb.append(" class=\"on\"");
				}
				//if (i == (prev10 + 1)) {
				//	sb.append(" class=\"first\"");
				//}
				sb.append(">");
				sb.append("<span class=\"blind\">Move to page </span>");
				if (i == getFixPageNo()) {
					sb.append("<strong>").append(i).append("</strong>");
				} else {
					sb.append(i);
				}
				sb.append("</a>\n");
			}
			sb.append("</span>\n");
		} else {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=1").append("\" class=\"on\">");
			sb.append("<span><strong><span class=\"blind\">Page </span>1</strong></span>\n");
			sb.append("</a>\n");
		}
		if (getTotalPageCnt() >= next10) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(next10).append("\">");
			sb.append("<img src=\"/assets/img/kor/common/list_next.gif\" alt=\"Move to next 10 pages\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/kor/common/list_next.gif\" alt=\"Move to next 10 pages\" />\n");
		}
		//if (getTotalPageCnt() > 0 && getFixPageNo() != getTotalPageCnt()) {
		//	sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(getTotalPageCnt()).append("\">");
		//	sb.append("<img src=\"/assets/img/kor/btn/btn_list_last.gif\" alt=\"Move to last page\" />");
		//	sb.append("</a>\n");
		//} else {
		//	sb.append("<img src=\"/assets/img/kor/btn/btn_list_last.gif\" alt=\"Move to last page\" />\n");
		//}
		sb.append("</div>\n");
		return sb.toString();
	}
	
	/**
	 * <p>모바일 페이징 처리를 한다.</p>
	 * 
	 * @param linkUrl (링크 URL)
	 * @return String (페이징 HTML)
	 */
	public String getPagingMobile(String linkUrl) {
		int prev10 = (int)Math.floor((getFixPageNo() - 1) / Math.floor(getPageSize())) * getPageSize();
		int next10 = prev10 + getPageSize() + 1;
		StringBuilder sb = new StringBuilder();
		sb.append("<div id=\"Paging\">\n");
		if (prev10 > 0) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(prev10).append("\" class=\"pre\">이전</a>\n");
		} else {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=1\" class=\"pre\">이전</a>\n");
		}
		if (getTotalPageCnt() > 0) {
			for (int i = (prev10 + 1); i < next10 && i <= getTotalPageCnt(); i++) {
				sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(i).append("\"");
				if (i == getFixPageNo()) {
					sb.append(" class=\"on\"");
				}
				sb.append(">").append(i);
				sb.append("<span class=\"blind\"> 페이지로 이동</span></a>\n");
			}
		} else {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=1").append("\" class=\"on\">1<span class=\"blind\"> 페이지</span></a>\n");
		}
		if (getTotalPageCnt() >= next10) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(next10).append("\" class=\"next\">다음</a>\n");
		} else {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(getTotalPageCnt()).append("\" class=\"next\">다음</a>\n");
		}
		sb.append("</div>\n");
		return sb.toString();
	}
	
	/**
	 * <p>스페인어 페이징 처리를 한다.</p>
	 * 
	 * @param linkUrl (링크 URL)
	 * @return String (페이징 HTML)
	 */
	public String getPagingSpa(String linkUrl) {
		int prev10 = (int)Math.floor((getFixPageNo() - 1) / Math.floor(getPageSize())) * getPageSize();
		int next10 = prev10 + getPageSize() + 1;
		StringBuilder sb = new StringBuilder();
		sb.append("<div id=\"Page-Index\">\n");
		if (getFixPageNo() != 1) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=1").append("\">");
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_first.gif\" alt=\"Ir a la primera página\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_first.gif\" alt=\"Ir a la primera página\" />\n");
		}
		if (prev10 > 0) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(prev10).append("\">");
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_prev.gif\" alt=\"Ir a 10 páginas anteriores\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_prev.gif\" alt=\"Ir a 10 páginas anteriores\" />\n");
		}
		if (getTotalPageCnt() > 0) {
			sb.append("<span>\n");
			for (int i = (prev10 + 1); i < next10 && i <= getTotalPageCnt(); i++) {
				sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(i).append("\"");
				if (i == (prev10 + 1)) {
					sb.append(" class=\"first\"");
				}
				sb.append(">");
				sb.append("<span class=\"blind\">Ir a la página </span>");
				if (i == getFixPageNo()) {
					sb.append("<strong>").append(i).append("</strong>");
				} else {
					sb.append(i);
				}
				sb.append("</a>\n");
			}
			sb.append("</span>\n");
		} else {
			sb.append("<span><strong><span class=\"blind\">Página </span>1</strong></span>\n");
		}
		if (getTotalPageCnt() >= next10) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(next10).append("\">");
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_next.gif\" alt=\"Ir a 10 páginas posteriors\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_next.gif\" alt=\"Ir a 10 páginas posteriors\" />\n");
		}
		if (getTotalPageCnt() > 0 && getFixPageNo() != getTotalPageCnt()) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(getTotalPageCnt()).append("\">");
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_last.gif\" alt=\"Ir a a la última página\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_last.gif\" alt=\"Ir a a la última página\" />\n");
		}
		sb.append("</div>\n");
		return sb.toString();
	}
	
	/**
	 * <p>관리자 페이징 처리를 한다.</p>
	 * 
	 * @param linkUrl (링크 URL)
	 * @return String (페이징 HTML)
	 */
	public String getPagingAdmin(String linkUrl) {
		int prev10 = (int)Math.floor((getFixPageNo() - 1) / Math.floor(getPageSize())) * getPageSize();
		int next10 = prev10 + getPageSize() + 1;
		StringBuilder sb = new StringBuilder();
		sb.append("<div id=\"pageNo\">\n");
		if (getFixPageNo() != 1) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=1").append("\">");
			sb.append("<img src=\"/assets/img/admin/paging_first.gif\" alt=\"첫 페이지로 이동\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/admin/paging_first.gif\" alt=\"첫 페이지로 이동\" />\n");
		}
		if (prev10 > 0) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(prev10).append("\">");
			sb.append("<img src=\"/assets/img/admin/paging_prev.gif\" alt=\"10 페이지 이전으로 이동\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/admin/paging_prev.gif\" alt=\"10 페이지 이전으로 이동\" />\n");
		}
		if (getTotalPageCnt() > 0) {
			sb.append("<span>\n");
			for (int i = (prev10 + 1); i < next10 && i <= getTotalPageCnt(); i++) {
				sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(i).append("\"");
				if (i == (prev10 + 1)) {
					sb.append(" class=\"first\"");
				}
				sb.append(">");
				if (i == getFixPageNo()) {
					sb.append("<strong>").append(i).append("</strong>");
				} else {
					sb.append(i);
				}
				sb.append("<span class=\"blind\"> 페이지로 이동</span></a>\n");
			}
			sb.append("</span>\n");
		} else {
			sb.append("<span><strong>1<span class=\"blind\"> 페이지</span></strong></span>\n");
		}
		if (getTotalPageCnt() >= next10) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(next10).append("\">");
			sb.append("<img src=\"/assets/img/admin/paging_next.gif\" alt=\"10 페이지 다음으로 이동\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/admin/paging_next.gif\" alt=\"10 페이지 다음으로 이동\" />\n");
		}
		if (getTotalPageCnt() > 0 && getFixPageNo() != getTotalPageCnt()) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(getTotalPageCnt()).append("\">");
			sb.append("<img src=\"/assets/img/admin/paging_last.gif\" alt=\"마지막 페이지로 이동\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/admin/paging_last.gif\" alt=\"마지막 페이지로 이동\" />\n");
		}
		sb.append("</div>\n");
		return sb.toString();
	}
	
	/**
	 * <p>스크립트 혼용 페이징 처리를 한다.</p>
	 * 
	 * @param linkUrl (링크 URL)
	 * @return String (페이징 HTML)
	 */
	public String getPagingScript(String linkUrl) {
		int prev10 = (int)Math.floor((getFixPageNo() - 1) / Math.floor(getPageSize())) * getPageSize();
		int next10 = prev10 + getPageSize() + 1;
		StringBuilder sb = new StringBuilder();
		sb.append("<div id=\"Page-Index\">\n");
		if (getFixPageNo() != 1) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=1").append("\" onclick=\"moveList('pageNo|1');return false;\">");
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_first.gif\" alt=\"첫 페이지로 이동\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_first.gif\" alt=\"첫 페이지로 이동\" />\n");
		}
		if (prev10 > 0) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(prev10).append("\" onclick=\"moveList('pageNo|").append(prev10).append("');return false;\">");
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_prev.gif\" alt=\"10 페이지 이전으로 이동\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_prev.gif\" alt=\"10 페이지 이전으로 이동\" />\n");
		}
		if (getTotalPageCnt() > 0) {
			sb.append("<span>\n");
			for (int i = (prev10 + 1); i < next10 && i <= getTotalPageCnt(); i++) {
				sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(i).append("\" onclick=\"moveList('pageNo|").append(i).append("');return false;\"");
				if (i == (prev10 + 1)) {
					sb.append(" class=\"first\"");
				}
				sb.append(">");
				if (i == getFixPageNo()) {
					sb.append("<strong>").append(i).append("</strong>");
				} else {
					sb.append(i);
				}
				sb.append("<span class=\"blind\"> 페이지로 이동</span></a>\n");
			}
			sb.append("</span>\n");
		} else {
			sb.append("<span><strong>1<span class=\"blind\"> 페이지</span></strong></span>\n");
		}
		if (getTotalPageCnt() >= next10) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(next10).append("\" onclick=\"moveList('pageNo|").append(next10).append("');return false;\">");
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_next.gif\" alt=\"10 페이지 다음으로 이동\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_next.gif\" alt=\"10 페이지 다음으로 이동\" />\n");
		}
		if (getTotalPageCnt() > 0 && getFixPageNo() != getTotalPageCnt()) {
			sb.append("<a href=\"").append(linkUrl).append("&amp;pageNo=").append(getTotalPageCnt()).append("\" onclick=\"moveList('pageNo|").append(getTotalPageCnt()).append("');return false;\">");
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_last.gif\" alt=\"마지막 페이지로 이동\" />");
			sb.append("</a>\n");
		} else {
			sb.append("<img src=\"/assets/img/kor/btn/btn_list_last.gif\" alt=\"마지막 페이지로 이동\" />\n");
		}
		sb.append("</div>\n");
		return sb.toString();
	}
	
}