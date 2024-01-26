package kr.co.newbie.sample.bbs.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author      : 장선주
 * @Description : 샘플_게시판 Default VO Class.
 */
public class SampleBbsDefaultVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 현재 페이지 번호 */
	private int pageNo = 1;
	
	/** 페이지당 데이터 출력 수 */
	private int pageUnit = 10;
	
	/** 페이지번호 출력 사이즈 */
	private int pageSize = 10;
	
	/** 시작 인덱스 */
	private int firstIndex = 1;
	
	/** 마지막 인덱스 */
	private int lastIndex = 1;
	
	/** 검색조건 - 검색 항목 */
	private String shItem;
	
	/** 검색조건 - 검색 단어 */
	private String shWord;
	
	/** 추가 파라미터 - 업로드할 첨부파일 객체 리스트 */
	private List<MultipartFile> attachFile;
	
	/** 추가 파라미터 - 삭제할 첨부파일 고유키 */
	private String delFileSid;
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public String getShItem() {
		return shItem;
	}

	public void setShItem(String shItem) {
		this.shItem = shItem;
	}

	public String getShWord() {
		return shWord;
	}

	public void setShWord(String shWord) {
		this.shWord = shWord;
	}

	public List<MultipartFile> getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(List<MultipartFile> attachFile) {
		this.attachFile = attachFile;
	}

	public String getDelFileSid() {
		return delFileSid;
	}

	public void setDelFileSid(String delFileSid) {
		this.delFileSid = delFileSid;
	}

}