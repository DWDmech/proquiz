<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<c:url value="/panel/interview" var="interviewUrl"/>

<c:forEach items="${interviews}" var="i">
	<div class="col-xl-4 col-lg-4 col-md-4 col-sm-12 r">
		<div class="card">
  	      <a href="${interviewUrl}/${i.id}"><img class="card-img-top" src="img/card-img-0.png" alt="Card image cap"></a>
		  <div class="card-body">
		    <p class="card-title">${i.id} - ${i.title}</p>
		    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
		    <p class="card-text"><javatime:format value="${i.date}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
		  </div>
		</div>
	</div>
</c:forEach>