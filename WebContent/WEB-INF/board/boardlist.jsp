<%@page import="kh.my.board.board.model.vo.Board"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String context_root = request.getContextPath(); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- JSTL -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 이 부분에 쓰는 경로는 무조건 절대경로로 작성한다.(모든 page와 URL에서 접근을 하기 때문에 위치의 영향을 받는 상대경로를 이용하면 안 된다.) -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css" />
<!-- EL을 이용해  contextPath 부분을 작성할 수도 있다. -->
<link rel="stylesheet" type="text/css" href="<%=context_root %>/css/main.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/template_header.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/template_footer.css" />
<link rel="stylesheet" type="text/css" href="<%=context_root %>/css/template.css" />
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js" ></script>
<script type="text/javascript" src="<%=context_root %>/js/common.js" ></script>
<script type="text/javascript" src="<%=context_root %>/js/template.js" ></script>
<title>Insert title here</title>
</head>
<body>
<%@ include file="../template_header.jsp" %>
<!-- 아래 부분없이 JSTL을 이용해 간단하게 표현할 수 있다. -->
<%-- 	<%
		ArrayList<Board> selectBoradList = (ArrayList<Board>)request.getAttribute("boardList");
		int startPage = (int)request.getAttribute("startPage");
		int endPage = (int)request.getAttribute("endPage");
		int currentPage = (int)request.getAttribute("currentPage");
		int pageCount = (int)request.getAttribute("pageCount");
	%> --%>
	
	<%ArrayList<Board> selectBoradList = (ArrayList<Board>)request.getAttribute("boardList"); %>
	
	<h1>글 목록 출력</h1>
	    <table border="1">
        <tr>
        	<td>번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>날짜</td>
        </tr>
        
        <!-- ${boardList} = (ArrayList<Board>)request.getAttribute("boardList"); -->
        <!-- ${boardList} ==> boardlist를 화면에 뿌리라는 의미, request 중에  boardlist를 찾아서 뿌린다. -->
        <%
			if(selectBoradList != null) {        
        		for(Board board : selectBoradList) {
        %>
        <tr>
        	<!--
        	<a> 태그의 링크를 절대 경로로 작성해야한다. 이때 context-root를 모두 일일이 작성할 수는 없다.
        	따라서 context-root를 아예 변수로 설정하면 편하다.
        	(controller와 달리 viewpage의 경로에는 context-root가 포함돼있지 않기에 직접 넣어줘야한다. WEB-INF 폴더에 넣고 말고는 상관없다.)
        	<%-- <% String context_root = request.getContextPath(); %> --> (context_root = /newmvc) --%>
        	-->
           	<td><a href="<%=context_root %>/board/boardcontent?bno=<%= board.getBno() %>"><%= board.getBno() %></a></td>
           	<!-- <a href>에는 URL에 들어가는 값을 작성해야하는 것이다.(폴더 구조를 기준으로 작성하는게 아니다.) -->
           	<!-- controller를 기준으로 작성한다. -->
            <td>
            	<%
            		for(int i = 0; i < board.getBreLevel(); i++){
            			%>Re:<%
            		}
            	%>
            	
            
            	<%=board.getTitle() %>
            	<!-- 이 부분에 값이 없어서 문제를 일으킬수도 있다. 그래서 있는게 JSTL -->	
            
            </td>
            <td><%=board.getWriter() %></td>
            <td><%=board.getCreateDate() %></td>
        </tr>
        
        <%
     		} 
        		} else { %> 글이 하나도 없습니다. <% }
        %>
        
        <p>

    </table>
    		<!-- JSTL은 Tag Library이다. 즉, (<% %>) 대신 <태그>를 이용하면 되는 것이다.-->
    		<!-- 따라서 HTML 태그 작성법과 동일한 방식으로 작성하면 된다. -->
			<!-- import 문에서 (prefix="c")라고 했기에 'c:'라고 쓰는 것이다. -->
    		<c:if test="${currentPage > 1}">이전</c:if>
		
			<c:forEach begin="${startPage}"  end="${endPage}" step="1" var="i">
				<a href="${pageContext.request.contextPath}/board/BoardList?pagenum=${i}"> ${i} </a>
				<%-- <a href="<%=context_root %>/board/BoardList?pagenum=${i}"> ${i} </a> --%>			
				<c:if test="${i ne endPage}"><!-- eq(같다) / ne(같지 않다.) -->
					,  				
				</c:if>
			</c:forEach>
		
		<c:if test="${endPage > pageCount}">다음</c:if>
		<br>현재 선택한 페이지 : ${currentPage}
		
		<br><a href="<%=context_root %>/board/boardWriteview">새글쓰기</a>
		<%@ include file="../template_footer.jsp" %>
</body>
</html>

<%-- 		이전
	<%
		}
		for (int i = startPage; i <= endPage; i++) {
	%>
	<a href="./boardlist?pagenum=<%=i%>"> <%=i%>  </a>
	<%
		if (i != endPage) {
	%>
	,
	<%
		}
	}
	if (endPage < pageCount) {
	%>
		다음
	<%
	}
	%> --%>