<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/" var="base" />
<c:url value="/favicon.ico" var="favicon" />

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>${title}</title>
  <base href="${base}">
  <link href="${favicon}" rel="icon">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <c:forEach items="${style}" var="s">
  	<link rel="stylesheet" href="${s}">  
  </c:forEach>
  <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">

  <script src="js/jquery/jquery.js" type="text/javascript"></script>
  <script src="js/bootstrap/bootstrap.js" type="text/javascript"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.5/angular.min.js" type="text/javascript"></script>
</head>