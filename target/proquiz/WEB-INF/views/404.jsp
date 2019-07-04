<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/favicon.ico" var="favicon" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>404 Page not found</title>
	<link href="${favicon}" rel="icon">
	<base href="${pageContext.request.contextPath}">
</head>
<body>
	<img src="${pageContext.request.contextPath}/img/404.png" alt="error" style="display: block;margin: auto;">
</body>
</html>