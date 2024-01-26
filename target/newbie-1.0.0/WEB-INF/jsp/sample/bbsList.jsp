<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cu" uri="/WEB-INF/tld/CommonUtil.tld"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>

<html>
<head>
	<title>샘플 게시판 목록</title>
</head>
<body>
	<article id="mainArticle">
		<h2>샘플 게시판 목록</h2>
		
		<table class="boardList">
			<caption>데이터 목록</caption>
			<colgroup>
				<col style="width:60px" />
				<col />
				<col style="width:95px" />
				<col style="width:95px" />
				<col style="width:90px" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">제목</th>
					<th scope="col">작성자</th>
					<th scope="col">등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="resultVo" items="${resultList}" varStatus="status">
					<tr>
						<td><c:out value="${cu:pageDataNum(paginationInfo, status.index)}" /></td>
						<td class="title"><c:out value="${resultVo.sj}" /></td>
						<td><c:out value="${resultVo.regNm}" /></td>
						<td><c:out value="${cu:toKorDate1(resultVo.regDt)}" /></td>
					</tr>
				</c:forEach>
				<c:if test="${empty resultList}">
					<tr>
						<td colspan="4">데이터가 없습니다.</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</article>
</body>
</html>
