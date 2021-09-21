<!-- 이거 말고 다른 종류의 template를 더 만들어도 좋다.(template가 반드시 이것만 있어야할 이유가 없다.) -->

<!-- 프로젝트에서 공통으로 적용할 커스텀(custom) 부분 -->
<!-- 아래의 내용을 복사해서 적용할 페이지 각각에 복붙하면 되는 것이다. -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- JSTL -->
<% String context_root = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- ============================= 이 만큼을 매번 복사해서 적용할 페이지 각각에 붙여넣는다. ============================= -->
<!-- 외부에서 import한 font나 아이콘 경로들도 들어간다. -->
<!-- (template_header.jsp와 template_footer.jsp)에서 스타일을 적용시키지 못 했으므로 -->
<!-- (template_header.jsp와 template_footer.jsp)용 css 파일을 각각 따로 만든 후 이곳에서 파일 형태로  import해야한다. -->
<!-- (각 조마다 고유한 css 파일을 import 한다.)-->
<!-- (template.jsp)만의 css 파일을 따로 만들어 그것도 import 해도 된다.(각 page의 css 파일을 따로 만들어 관리한다는 의미) -->
<!-- <aside>나 <nav> 등도 같은 방식으로 각각의 template 파일과 css 파일을 만든 후 include하면 된다.  -->
<link rel="stylesheet" type="text/css" href="<%=context_root %>/css/reset.css" />
<link rel="stylesheet" type="text/css" href="<%=context_root %>/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%=context_root %>/css/template_header.css" />
<link rel="stylesheet" type="text/css" href="<%=context_root %>/css/template_footer.css" />
<link rel="stylesheet" type="text/css" href="<%=context_root %>/css/template.css" />
<!-- jquery도 거의 모든 page에 적용된다. -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js" ></script>
<!-- js도 공통적인 부분을  각각 별개의 파일로 만든후 import하면 된다.-->
<script type="text/javascript" src="<%=context_root %>/js/common.js" ></script>
<script type="text/javascript" src="<%=context_root %>/js/template.js" ></script>
<!-- ============================= <title>, <body> 부분은 당연히 각 page에 맞게 수정 ============================= -->
<title>Template</title>
</head>
<body>
	<%@ include file="template_header.jsp" %>
		<section>각 page마다 작성하고 싶은 내용</section>
	<%@ include file="template_footer.jsp" %>
</body>
</html>
<!-- ============================= 이 만큼을 매번 복사해서 적용할 페이지 각각에 붙여넣는다. ============================= -->