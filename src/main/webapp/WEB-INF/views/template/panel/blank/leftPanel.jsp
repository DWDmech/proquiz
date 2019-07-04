<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url value="/panel/category" var="home"/>
<c:url value="/panel/interview/add" var="add"/>
<c:url value="/panel/category" var="category"/>
<c:url value="/panel/statistics" var="statistics"/>
<c:url value="/admin/panel/statistics" var="adminStats"/>

<div class="panel">
  <div class="panel-header">
    <a href="${home}"><img src="img/logo_white.png" alt="logo"></a>
    <hr align="left">
  </div>

  <div class="panel-nav">
    <nav class="nav nav-pills nav-fill flex-column">
      <c:forEach items="${categories}" var="c">
      	<c:choose>
      		<c:when test="${toActive == c.id}">
      			<a class="nav-link active" href="${category}/${c.id}">${c.title}</a>
      		</c:when>
      		<c:otherwise>
      			<a class="nav-link" href="${category}/${c.id}">${c.title}</a>
      		</c:otherwise>
      	</c:choose>
      </c:forEach>
      <c:choose>
      		<c:when test="${toActive == 5}">
      			<a class="nav-link active" href="${statistics}">Результати опитування</a>
      		</c:when>
      		<c:otherwise>
      			<a class="nav-link" href="${statistics}">Результати опитування</a>
      		</c:otherwise>
      	</c:choose>
      	<sec:authorize access="hasRole('ROLE_ADMIN')">
	      	<c:choose>
	      		<c:when test="${toActive == 6}">
	      			<a class="nav-link active mt-5" href="${adminStats}">Панель адміністратора</a>
	      		</c:when>
	      		<c:otherwise>
	      			<a class="nav-link mt-5" href="${adminStats}">Панель адміністратора</a>
	      		</c:otherwise>
	      	</c:choose>
        </sec:authorize>
    </nav>
  </div>

  <div class="panel-bottom">
    <a href="${add}">Створити опитування &rArr;</a>
  </div>
</div>