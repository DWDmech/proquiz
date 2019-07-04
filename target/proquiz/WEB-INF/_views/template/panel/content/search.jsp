<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<c:url value="/panel/interview" var="interviewUrl"/>

<div class="row content" >
	<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
	    <h1 align="center" class="content-title">${text}</h1>
	</div>
	
	<c:forEach items="${interviews}" var="i">
		<div class="col-xl-4 col-lg-4 col-md-4 col-sm-12 r">
			<div class="card">
	  	      <a href="${interviewUrl}/${i.id}"><img class="card-img-top" src="img/card-img-0.png" alt="Card image cap"></a>
			  <div class="card-body">
			    <p class="card-title">${i.id} - ${i.title}</p>
			    <p class="card-text"><b>Автор: </b>${i.author.name}</p>
			    <p class="card-text"><javatime:format value="${i.date}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
			  </div>
			</div>
		</div>
	</c:forEach>
</div>