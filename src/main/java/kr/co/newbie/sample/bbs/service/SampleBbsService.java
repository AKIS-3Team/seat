package kr.co.newbie.sample.bbs.service;

import java.util.List;

/**
 * @Author      : 장선주
 * @Description : 샘플_게시판 Business Class.
 */
public interface SampleBbsService {
	
	/**
	 * 데이터 리스트를 조회한다.
	 * 
	 * @param mainVo - 조회할 정보가 담긴 VO
	 * @param paginationInfo - 페이징 처리 정보
	 * @return 데이터 리스트
	 * @throws Exception
	 */
	List<SampleBbsVO> selectList(SampleBbsVO mainVo) throws Exception;

}
