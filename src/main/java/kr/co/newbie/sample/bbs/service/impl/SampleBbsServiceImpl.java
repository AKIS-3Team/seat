package kr.co.newbie.sample.bbs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import kr.co.newbie.sample.bbs.service.SampleBbsService;
import kr.co.newbie.sample.bbs.service.SampleBbsVO;

/**
 * @Author      : 장선주
 * @Description : 샘플_게시판 Business Implement Class.
 */
@Service
public class SampleBbsServiceImpl extends EgovAbstractServiceImpl implements SampleBbsService {
	
	@Autowired
	private SampleBbsMapper mainMapper;
	
	/**
	 * 데이터 리스트를 조회한다.
	 * 
	 * @param mainVo - 조회할 정보가 담긴 VO
	 * @return 데이터 리스트
	 * @throws Exception
	 */
	public List<SampleBbsVO> selectList(SampleBbsVO mainVo) throws Exception {
		// 데이터 리스트 조회
		return mainMapper.selectList(mainVo);
	}
	
}
