<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
   //String bnoStr = request.getParameter("bno");
   //request.setAttribute("bno", bnoStr);
   //이걸 안 했으므로 최초로 꺼내는 위치를 여기로 정하는 것이다.
   String bno = request.getParameter("bno");
   if(bno == null){ bno = ""; }
	// getParameter()는 if 조건문을 통해 null 상태를 걸러내야한다.
	// request.getParameter("bno")가 없으면 (String bno = null) 이다.
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>답글 달려는 글 번호 : <%=bno %></h1>
	<!--rewrite servlet으로 url맵핑수정-->
	<!--기존에는 새글만 쓸 수 있는 servlet인 boardWrite로 맵핑 URL을 설정했다.-->
    <form action="boardrewrite" method="get">
    답글 달려는 글 번호 : <input type="text" name="bno" value="<%=bno %>" readonly><br><!--type="hidden" 화면에 보이지만 않게-->
     제목 : <input type="text" name="title" required><br>
     내용 : <input type="text" name="content" required><br>
        <input type="submit" value="등록">
        <input type="reset" value="취소">
    </form>
    
    <a href="BoardList">글 목록 가기</a>
</body>
</html>