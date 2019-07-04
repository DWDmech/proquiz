<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>


<c:forEach items="${comments}" var="c">
	<div class="comment border-bottom mb-4">
		<div class="comment-user-info">
			<img src="img/fail.jpg" alt="img" class="comment-user-img">
			<span class="comment-user-name ml-2">${c.author.name}</span><span class="comment-timestamp ml-1">- <javatime:format value="${c.date}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
		</div>
		<div class="comment-text pt-xl-3 pl-4">${c.text}</div>
	</div>
</c:forEach>