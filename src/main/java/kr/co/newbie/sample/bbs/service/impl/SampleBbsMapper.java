package kr.co.newbie.sample.bbs.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.newbie.sample.bbs.service.SampleBbsVO;

/**
 * @Author      : 장선주
 * @Description : 샘플_게시판 Mapper Class.
 */
@Mapper
public interface SampleBbsMapper {
	
	/**
	 * 데이터 리스트를 조회한다.
	 * 
	 * @param mainVo - 조회할 정보가 담긴 VO
	 * @return 데이터 리스트
	 * @throws Exception
	 */
	public List<SampleBbsVO> selectList(SampleBbsVO mainVo) throws Exception;
		
}