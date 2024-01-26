package kr.co.newbie.sample.bbs.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.fdl.property.EgovPropertyService;
import kr.co.newbie.sample.bbs.service.SampleBbsService;
import kr.co.newbie.sample.bbs.service.SampleBbsVO;

/**
 * @Author      : 장선주
 * @Description : 샘플_게시판 Controller Class.
 */
@Controller
@RequestMapping(value="/sample")
public class SampleBbsController {
	
	@Autowired
	private SampleBbsService mainService;
	
	@Autowired
	protected EgovPropertyService egovProperties;
	
	/**
	 * 데이터 리스트를 조회한다.
	 */
	@RequestMapping(value="/bbsList")
	public String list(@ModelAttribute("searchVo") SampleBbsVO mainVo, HttpServletRequest request, ModelMap model) throws Exception {
		// 데이터 리스트 조회
		model.addAttribute("resultList", mainService.selectList(mainVo));
		
		return request.getServletPath();
	}
	
}