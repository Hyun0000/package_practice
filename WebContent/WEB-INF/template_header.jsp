<!-- 여기에는 css 파일 import나 <style> ~ </style>을 적용시킬 수 없다.(애초에 적용시키면 안 된다.) -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<header>
	<div id="login">
		<form action="#" method="post">
			<fieldset>
				<legend> 로그인 </legend>
				<table>
					<tr>
						<td>아이디</td>
						<td><input type="text" name="id"></td>
					</tr>
					<tr>
						<td>패스워드</td>
						<td><input type="password" name="pwd"></td>
					</tr>
					<tr>
						<td colspan="2" sytle="text-align:center"><input
							type="button" value="확인"> <input type="submit"
							value="전달/제출"> <input type="reset" value="지우기"></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	<div id="logo">
		<img
			src="https://iei.or.kr/resources/images/main/main_renewal/top_logo_s.jpg"
			width="200" height="50">
	</div>
</header>