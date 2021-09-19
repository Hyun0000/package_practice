<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 삭제</title>
</head>
<body>
<% int result = (int)request.getAttribute("deleteResult"); %>
    <h1>게시글 삭제</h1>
    <form action="BoardDelete" method="POST">
        <input type="text" name="bno" autofocus placeholder="게시글 번호"><br>
        <input type="text" name="title" autofocus placeholder="게시글 제목"><br>
        <input type="submit" value="삭제">
    </form>
    
  	<% if (result == 1) {
			%>삭제 완료<%
		} else if (result == 0) {
			 %>정상이긴하나 삭제한게 없다.<%
		} else {
			 %>오류 발생<% 
		}%>
</body>
</html>