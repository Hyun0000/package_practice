<!-- 프로젝트에서 공통으로 적용할 커스텀(custom) 부분 -->
<!-- 아래의 내용을 복사해서 적용할 페이지 각각에 복붙하면 되는 것이다. -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String context_root = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- ============================= 이 만큼을 매번 복사해서 적용할 페이지 각각에 붙여넣는다. ============================= -->
<!-- 외부에서 import한 font나 아이콘 경로들도 들어간다. -->
<!-- 파일 형태로만 import해야한다. -->
<!-- 각 조마다 고유한 css 파일을 import 한다.-->
<!-- template만의 css 파일을 따로 만들어 그것도 import 해도 된다. -->
<link rel="stylesheet" type="text/css" href="<%=context_root %>/css/reset.css" />
<link rel="stylesheet" type="text/css" href="<%=context_root %>/css/template_header.css" />
<link rel="stylesheet" type="text/css" href="<%=context_root %>/css/template_footer.css" />
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js" ></script>
<script type="text/javascript" src="<%=context_root %>/js/common.js" ></script>
<script type="text/javascript" src="<%=context_root %>/js/template.js" ></script>
<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- ============================= 이 만큼을 매번 복사해서 적용할 페이지 각각에 붙여넣는다. ============================= -->
<title>Template</title>
</head>
<body>
	<%@ include file="template_header.jsp" %>
	<div>작성하고 싶은 내용</div>
	<%@ include file="template_footer.jsp" %>
</body>
</html>