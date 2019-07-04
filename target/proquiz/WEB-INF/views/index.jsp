<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
  <%@include file="template/home/blank/head.jsp" %>    
  
  <body>
    <header>
		
  		<%@include file="template/home/blank/navbar.jsp" %>
      
    </header>

    <div id="content" class="container">
      <div class="row">

        <jsp:include page="${content}"/>
      </div>
    </div>

  </body>
</html>