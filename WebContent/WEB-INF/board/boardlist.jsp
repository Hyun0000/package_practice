<%@page import="kh.my.board.board.model.vo.Board"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String context_root = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 이 부분에 쓰는 경로는 무조건 절대경로로 작성한다. -->
<link rel="stylesheet" type="text/css" href="<%=context_root %>/css/template_header.css" />
<link rel="stylesheet" type="text/css" href="<%=context_root %>/css/template_footer.css" />
<title>Insert title here</title>
</head>
<body>
<%@ include file="../template_header.jsp" %>
	<%
		ArrayList<Board> selectBoradList = (ArrayList<Board>)request.getAttribute("boardList");
		// 다형성으로 인해 (ArrayList<Board>)로 다운 캐스팅을 해야한다.
		int startPage = (int)request.getAttribute("startPage");
		int endPage = (int)request.getAttribute("endPage");
		int currentPage = (int)request.getAttribute("currentPage");
		int pageCount = (int)request.getAttribute("pageCount");
	%>
	
	<h1>글 목록 출력</h1>
	    <table border="1">
        <tr>
        	<td>번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>날짜</td>
        </tr>
        
        <%
			if(selectBoradList != null) {        
        		for(Board board : selectBoradList) {
        %>
        <tr>
        	<!--
        	<a> 태그의 링크를 절대 경로로 작성해야한다.
        	이때 context-root를 모두 일일이 작성할 수는 없다. 따라서 context-root를 아예 변수로 설정하면 편하다.
        	<%--
        	<% String context_root = request.getContextPath(); %>
        		context_root = /newmvc
        	--%>
        	-->
           	<td><a href="<%=context_root %>/board/boardcontent?bno=<%= board.getBno() %>"><%= board.getBno() %></a></td>
            <td>
            	<%
            		for(int i = 0; i < board.getBreLevel(); i++){
            			%>Re:<%
            		}
            	%>
            	
            
            	<%=board.getTitle() %>
            
            
            </td>
            <td><%=board.getWriter() %></td>
            <td><%=board.getCreateDate() %></td>
        </tr>
        
        <%
     		} 
        		} else { %> 글이 하나도 없습니다. <% }
        %>
        
    </table>
    		<% if (currentPage > 1) {
				%> << 이전 <% 
			}
		
			for (int i = startPage; i <= endPage; i++) { %>
			
			<a href="<%=context_root %>/board/BoardList?pagenum=<%=i %>"> <%=i%></a>
			<%if (i!=endPage) {
				%>, <% 				
			}
		}
		
		if (endPage < pageCount) {
			%>  다음 >><%
		} %>
		
		<br>현재 선택한 페이지 : <%= currentPage%>
		
		<br><a href="<%=context_root %>/board/boardWriteview">새글쓰기</a>
		<%@ include file="../template_footer.jsp" %>
</body>
</html>