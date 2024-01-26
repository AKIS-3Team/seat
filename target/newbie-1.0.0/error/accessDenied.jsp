<%
/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : accessDenied.jsp
 * @Description : 접근권한 오류 처리 페이지
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
	<title>접근권한 오류</title>
	<!-- Meta Tags -->
	<meta charset="utf-8" />
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta name="robots" content="noindex,nofollow" />
	<!-- css, javascript -->
	<link rel="stylesheet" type="text/css" href="/error/css/global.css" />
	<link rel="stylesheet" type="text/css" href="/error/css/error.css" />
</head>
<body>
	<div id="errorWrapper" class="warning">
		<h1>해당 메뉴의 접근이 거부되었습니다.</h1>
		<div id="message">
			<p class="intro">접속한 계정으로 해당 메뉴 또는 페이지를 볼수있는 권한이 없습니다.</p>
			<p>
				접속권한에 따라 해당 메뉴에 대한 접속이 제한될 수 있습니다.<br />
				접속권한에 대한 내용은 관리자에게 문의해 주세요!!
			</p>
			<a href="#errorWrapper" onclick="history.back();return false;" class="button black">뒤로 돌아가기</a>
			<a href="/" class="button gray">홈으로</a>
		</div>
	</div>
</body>
</html>