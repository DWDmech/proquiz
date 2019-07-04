<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row content user-list col-12">
	${text}
	<c:forEach items="${users}" var="u">
	  <div class="user-item col-12 p-3">
	      <img src="img/fail.jpg" class="user-img" align="left">
	      <span>${u.name}</span>
	  </div>
	</c:forEach>	
</div>