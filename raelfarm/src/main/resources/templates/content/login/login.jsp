<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	
	<form action="/loginProc" method="post">
		<input type="hidden" value="${_csrf.token}" name="${_csrf.parameterName}">
		<input type="text" name="username">
		<input type="password" name="password">
		<button type="submit">로그인</button>
	</form>
	<a href="/register_agree">회원가입</a>
	<c:if test="${result eq 1}">
		<script>
			alert("수정 완료!")
		</script>
	</c:if>
</body>
</html>