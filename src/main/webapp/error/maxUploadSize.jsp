<%
/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : maxUploadSize.jsp
 * @Description : 최대 업로드 사이즈 초과 오류 처리 페이지
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
%>
<%@ page language="java" isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.ByteArrayOutputStream
				,java.io.PrintStream
				,org.slf4j.Logger
				,org.slf4j.LoggerFactory
				,kr.co.akis.util.HtmlUtil,kr.co.akis.util.StringUtil"%>
<%
boolean isPrintErrorMessage = false;
//boolean isPrintErrorMessage = true;
//if (exception == null) isPrintErrorMessage = false;

// Request Parameters
String maxSize = StringUtil.xss(request.getAttribute("maxSize"), "1GB", false);

String stackTrace = "";
try {
	// 에러 내용을 가져온다.
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	PrintStream ps = new PrintStream(baos);
	exception.printStackTrace(ps);
	stackTrace = baos.toString();
	if (ps != null) ps.close();
	if (baos != null) baos.close();
	// 에러 로그를 등록한다.
	Logger logger = LoggerFactory.getLogger(exception.getClass());
	logger.error(stackTrace);
} catch (Exception e) {}
%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<title>최대 업로드 사이즈 초과 오류</title>
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
	<% if (!isPrintErrorMessage) { %>
		<div id="errorWrapper" class="warning">
			<h1>첨부파일 업로드 오류 입니다!!</h1>
			<div id="message">
				<p class="intro">가능한 최대 업로드 사이즈(<%=maxSize%>)를 초과하였습니다.</p>
				<p>
					첨부파일 사이즈를 줄여 주시길 바랍니다.<br />
					사이즈를 줄여도 오류를 일으킬 경우 관리자에게 문의해 주세요!!
				</p>
				<a href="#errorWrapper" onclick="history.back();return false;" class="button black">뒤로 돌아가기</a>
				<a href="/" class="button gray">홈으로</a>
			</div>
		</div>
	<% } else { %>
		<div id="errorReport">
			<table>
				<caption>To check a exception class, exception message, exception stack trace.</caption>
				<colgroup>
					<col style="width:180px" />
					<col />
				</colgroup>
				<thead>
					<tr>
						<th colspan="2" scope="colgroup">최대 업로드 사이즈 초과 오류!!</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th scope="row">Exception Class</th>
						<td><%=exception.getClass().getName()%></td>
					</tr>
					<tr>
						<th scope="row">Exception Message</th>
						<td><%=exception.getMessage() == null ? "System Error!!" : HtmlUtil.encodeHtml(exception.getMessage(), true)%></td>
					</tr>
					<tr>
						<th scope="row">Exception StackTrace</th>
						<td><%=HtmlUtil.encodeHtml(stackTrace, true)%></td>
					</tr>
				</tbody>
			</table>
		</div>
	<% } %>
</body>
</html>