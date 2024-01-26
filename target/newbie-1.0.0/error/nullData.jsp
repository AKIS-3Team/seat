<%
/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : nullData.jsp
 * @Description : 데이터 없음 오류 처리 페이지
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.co.akis.util.StringUtil"%>
<%
// Request Parameters
String returnUrl = StringUtil.xss(request.getAttribute("returnUrl"));
%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<title>데이터 없음 오류</title>
	<!-- Meta Tags -->
	<meta charset="utf-8" />
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta name="robots" content="noindex,nofollow" />
	<meta name="decorator" content="errorDecorator" />
	<!-- css, javascript -->
	<link rel="stylesheet" type="text/css" href="/error/css/global.css" />
	<link rel="stylesheet" type="text/css" href="/error/css/error.css" />
	<script src="/js/jquery/jquery-3.5.1.min.js"></script>
	<script src="/js/jquery/jquery-ui/jquery-ui.min.js"></script>
	<script src="/js/common.min.js"></script>
	<script>
	//<![CDATA[
		$(function() {
			//$("html, body").css("overflow", "hidden");
			$("#dialogWrapper").dialog({
				closeOnEscape: false,
				width: 630,
				height: 290
			});
		});
		
		function moveReturn(returnUrl) {
			$("#dialogWrapper").dialog("close");
			if (returnUrl.indexOf("#errorWrapper") >= 0) {
				if (location.href.indexOf("#errorWrapper") >= 0) {
					history.go(-2);
				} else {
					history.back();
				}
			} else {
				location.replace(returnUrl);
			}
			return false;
		}
	//]]>
	</script>
</head>
<body>
	<div id="dialogWrapper">
		<div id="errorWrapper">
			<h1 class="small">해당 데이터를 찾을 수 없거나 또는 삭제 되었습니다!!</h1>
			<div id="message">
				<p class="intro">
					시스템의 일시적인 현상 또는 장애일 수 있습니다.<br />
					사용중 불편을 드려서 대단히 죄송합니다.
				</p>
				<p>
					잠시후에 다시한번 시도해 주시길 바랍니다.<br />
					현재의 증상이 지속될 경우 관리자에게 문의해 주세요!!
				</p>
				<a href="<%=StringUtil.clean(returnUrl, "#errorWrapper")%>" onclick="return moveReturn(this.href);" class="button black">뒤로 돌아가기</a>
				<a href="/" class="button gray">홈으로</a>
			</div>
		</div>
	</div>
</body>
</html>