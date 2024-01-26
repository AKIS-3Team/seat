<%
/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : error404.jsp
 * @Description : 404 Error 처리 페이지
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
%>
<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<title>404 Error Report</title>
	<!-- Meta Tags -->
	<meta charset="utf-8" />
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta name="robots" content="noindex,nofollow" />
	<!-- css, javascript -->
	<link rel="stylesheet" type="text/css" href="/error/css/global.css" />
	<link rel="stylesheet" type="text/css" href="/error/css/error.css" />
	<script>
	//<![CDATA[
		function moveLimitTime(timeSecond){
			if (++timeSecond == "7") {
				location.href = "/";
			} else {
				window.setTimeout("moveLimitTime(" + timeSecond + ")",1000);
			}
		}
		
		window.onload = function() {
			//moveLimitTime(0);
		}
	//]]>
	</script>
</head>
<body>
	<div id="errorWrapper">
		<h1>이 페이지를 찾을 수 없습니다.</h1>
		<div id="message">
			<p class="intro">찾고 있는 페이지의 주소나 이름이 변경되었거나 일시적으로 사용할 수 없습니다.</p>
			<p>주소가 정확하다면 브라우저의 "새로 고침(reload)" 버튼을 눌러 확인 하시거나, 잠시후에 다시 접속을 시도해주십시요.</p>
			<a href="#errorWrapper" onclick="history.back();return false;" class="button black">뒤로 돌아가기</a>
			<a href="/" class="button gray">홈으로</a>
		</div>
	</div>
</body>
</html>