<%
/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : notLogin.jsp
 * @Description : 로그인 체크 오류 처리 페이지
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<title>로그인 체크 오류</title>
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
			<h1>로그인 후 이용해 주세요!!</h1>
			<div id="message">
				<p class="intro">해당 메뉴는 로그인이 필요합니다. 로그인 후 이용해 주세요!!</p>
				<p>
					접속권한에 따라 해당 메뉴에 대한 접속이 제한될 수도 있습니다.<br />
					계정이 없으실 경우 회원가입 후 이용해 주세요!!
				</p>
				<a href="#errorWrapper" onclick="return moveReturn(this.href);" class="button black">뒤로 돌아가기</a>
				<a href="/" class="button gray">홈으로</a>
			</div>
		</div>
	</div>
</body>
</html>