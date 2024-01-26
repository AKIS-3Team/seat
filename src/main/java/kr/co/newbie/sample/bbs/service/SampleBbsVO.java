package kr.co.newbie.sample.bbs.service;

import org.apache.ibatis.type.Alias;

/**
 * @Author      : 장선주
 * @Description : 샘플_게시판 VO Class.
 */
@Alias("sampleBbsVo")
public class SampleBbsVO extends SampleBbsDefaultVO {
	
	private static final long serialVersionUID = 1L;
	
	/** 게시판_고유키 */
	private Integer bbsSid;
	
	/** 제목 */
	private String sj;
	
	/** HTML_MODE */
	private String htmlMode;
	
	/** 내용 */
	private String cn;
	
	/** 삭제여부 (Y:삭제, N:미삭제) */
	private String delYn;
	
	/** 등록자_고유키 */
	private Integer regSid;
	
	/** 등록_이름 */
	private String regNm;
	
	/** 등록_IP */
	private String regIp;
	
	/** 등록일시 */
	private String regDt;
	
	/** 수정자_고유키 */
	private Integer mdfcnSid;
	
	/** 수정_IP */
	private String mdfcnIp;
	
	/** 수정일시 */
	private String mdfcnDt;

	public Integer getBbsSid() {
		return bbsSid;
	}

	public void setBbsSid(Integer bbsSid) {
		this.bbsSid = bbsSid;
	}

	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}

	public String getHtmlMode() {
		return htmlMode;
	}

	public void setHtmlMode(String htmlMode) {
		this.htmlMode = htmlMode;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public Integer getRegSid() {
		return regSid;
	}

	public void setRegSid(Integer regSid) {
		this.regSid = regSid;
	}

	public String getRegNm() {
		return regNm;
	}

	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public Integer getMdfcnSid() {
		return mdfcnSid;
	}

	public void setMdfcnSid(Integer mdfcnSid) {
		this.mdfcnSid = mdfcnSid;
	}

	public String getMdfcnIp() {
		return mdfcnIp;
	}

	public void setMdfcnIp(String mdfcnIp) {
		this.mdfcnIp = mdfcnIp;
	}

	public String getMdfcnDt() {
		return mdfcnDt;
	}

	public void setMdfcnDt(String mdfcnDt) {
		this.mdfcnDt = mdfcnDt;
	}
	
}