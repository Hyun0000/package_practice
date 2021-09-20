<%@page import="kh.my.board.board.model.vo.Board"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
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
           	<td><a href="boardcontent?bno=<%= board.getBno() %>"><%= board.getBno() %></a></td>
            <td>
            	<%
            		// 답글이 몇단계 답글이냐에따라 'Re:' 붙여주기
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
			
			<a href="BoardList?pagenum=<%=i %>"> <%=i%></a>
			<%if (i!=endPage) {
				%>, <% 				
			}
		}
		
		if (endPage < pageCount) {
			%>  다음 >><%
		} %>
		
		<br>현재 선택한 페이지 : <%= currentPage%>
		
		<br><a href="boardWriteview">새글쓰기</a>
		<!--boardWrite.jsp가 아니라 view용 서블릿으로 이동하게 수정-->
    
</body>
</html>