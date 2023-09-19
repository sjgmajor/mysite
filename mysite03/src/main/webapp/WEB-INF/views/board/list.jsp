<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value=""> <input
						type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items="${list }" var="list" varStatus="status">
						<tr>
							<td>${(pageVo.totalCount - ((pageVo.page -1) * pageVo.boardDisplay )) - status.index }</td>
							<td style="padding-left: ${(list.depth -1)*30 }px">
							<c:if	test="${list.depth >= 2}">
									<img
										src="${pageContext.request.contextPath }/assets/images/reply.png">
								</c:if> <a
								href="${pageContext.request.contextPath }/board/view/${list.no}">${list.title }</a></td>
							<td>${list.name }</td>
							<td>${list.hit }</td>
							<td><fmt:parseDate var="parsedRegDate"
									value="${list.regDate}" pattern="yyyy-MM-dd" /> <fmt:formatDate
									value="${parsedRegDate}" pattern="yyyy-MM-dd" type="date" /></td>
							<c:if test="${list.userNo eq authUser.no }">
								<td><a
									href="${pageContext.request.contextPath }/board/delete/${list.no }"
									class="del">삭제</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
					<c:choose>
							<c:when test="${pageVo.startPage == 1 }">
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.request.contextPath }/board/${1 }">◀◀</a></li>
								<li><a href="${pageContext.request.contextPath }/board/${pageVo.startPage - pageVo.intervalPage}">◀</a></li>
							</c:otherwise>
						</c:choose>
					
						<c:forEach begin="${pageVo.startPage }" end="${pageVo.endPage }" step="1" var="paging">
						<c:choose>
							<c:when test="${paging == pageVo.page }">
								<li class="selected"><a href="${pageContext.request.contextPath }/board/${paging}">${paging}</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.request.contextPath }/board/${paging}">${paging}</a></li>
							</c:otherwise>
						</c:choose>
						</c:forEach>
						<c:choose>
							<c:when test="${pageVo.totalPage == pageVo.endPage }">
							</c:when>
							<c:otherwise>
								<li><a href="${pageContext.request.contextPath }/board/${pageVo.endPage + 1}">▶</a></li>
								<li><a href="${pageContext.request.contextPath }/board/${pageVo.totalPage}">▶▶</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
				
			
				<c:choose>
					<c:when test="${empty authUser }">
					</c:when>
					<c:otherwise>
						<div class="bottom">
							<a href="${pageContext.request.contextPath }/board/write/${authUser.no }"
								id="new-book">글쓰기</a>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>