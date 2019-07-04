<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/" var="home"/>
<c:url value="/about" var="about"/>
<c:url value="/contact" var="contact"/>
<c:url value="/login" var="login"/>

<div class="navbar navbar-expand-lg navbar-light">
  <div class="container">
    <div class="navbar-header">
      <a href="${home}" class="navbar-brand"><img src="img/logo.png"></a>
    </div>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#nav-menu" aria-controls="nav-menu" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse flex-row-reverse" id="nav-menu">
      <ul class="navbar-nav">
        <li class="nav-item ml-1"><a class="nav-link" href="${home}">Головна</a></li>
        <li class="nav-item ml-1"><a class="nav-link" href="${about}">Про нас</a></li>
        <li class="nav-item ml-1"><a class="nav-link" href="${contact}">Контакти</a></li>
        <li class="nav-item ml-1"><a class="nav-link btn-login" href="${login}">Увійти</a></li>
      </ul>
    </div>
  </div>
</div>
