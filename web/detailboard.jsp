<%@page import="kh.my.board.board.model.vo.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% Board board = (Board)request.getAttribute("DetailBoard"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>글 상세 페이지</h1>
    <table border="1">
        <tr>
            <td>글번호</td>
            <td><%=board.getBno()%></td>
            <td><%=board.getCreateDate()%></td>
        </tr>
        <tr>
            <td>제목</td>
            <td colspan="2"><%=board.getTitle() %></td>
        </tr>
        <tr>
            <td colspan="3"><%=board.getContent() %></td>
        </tr>
    </table>	
    
    <a href="boardWriteview?bno=<%=board.getBno()%>">답글작성</a>
    <!--이걸 누르면 제목, 내용, 등록버튼, 취소 버튼 등이 나와야한다. 기존에 만들어놓은 새글쓰기 화면과 구성이 동일하다.
    즉, JSP 파일을 또 만들 필요없이 화면을 빌려오면 된다.
    단. 답글은 어떤 원본글 번호에 답글을 다는 것인지 알아야 한다.
    즉, 상세글을 보기위해 들고온 bno를 게속 가지고 다녀야한다.
    계속 들고가다가 최종적으로 boardrewrite에 제목,내용,bno를 들고가야한다.
    bno여부에따라 새글쓰기인지 답글쓰기인지 알아야한다.
    -->
</body>
</html>















