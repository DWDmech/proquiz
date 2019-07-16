<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/panel/search" var="searchUrl"/>
<c:url value="/logout" var="logoutUrl"/>

<div class="row top border-bottom">
  <!-- ~~~~~~~~~~SEARCH THINGS~~~~~~~~~ -->
  <div class="search col-xl-10 col-lg-8 col-md-8 col-sm-12">
    <form action="${searchUrl}" method="get">
    	<div class="input-group">
	      <div class="input-group-prepend">
	        <button class="btn btn-outline-secondary search-btn" type="submit"><span class="search-icon"></span></button>
	      </div>
	      <input type="text" class="form-control search-input" placeholder="Пошук" name="input-search" aria-describedby="search-input">
	      <select class="search-select px-2" name="mode">
            <option value="name" selected>Пошук по імені</option>
            <option value="title">Пошук по назві</option>
          </select>
	    </div>
    </form>
  </div>
  <!-- ~~~~~~~~~~SEARCH THINGS~~~~~~~~~ -->
  <!-- ~~~~~~~~~~USER THINGS~~~~~~~~~ -->
  <div class="user col-xl-2 col-lg-4 col-md-4 col-sm-12 border-left">
    <div class="user-panel">
      <a class="btn btn-primary user-btn" data-toggle="collapse" href="#userMenu" role="button" aria-expanded="false" aria-controls="collapseExample">
         <img src="img/fail.jpg" class="user-img mt-2" align="left">

         ${userName} &#9660;
      </a>
    </div>
		<div class="collapse" id="userMenu">
		  <ul class="nav flex-column user-nav">
			  <li class="nav-item">
				<a class="nav-link" href="${logoutUrl}">Logout</a>
			  </li>
			</ul>
		</div>
  </div>
  <!-- ~~~~~~~~~~USER THINGS~~~~~~~~~ -->
</div>