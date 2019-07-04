<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <%@ include file="template/panel/blank/head.jsp" %>
  </head>
  <body ng-app="my-app">
    <div class="container-fluid height-max">
      <div class="row height-max">
        <div class="col-xl-2 col-lg-3 col-md-12 col-sm-12">
			<jsp:include page="${leftPanel}"/>
        </div>

        <div class="col-xl-10 col-lg-9 col-md-12 col-sm-12">
          <div class="container-fluid">
			<jsp:include page="${topPanel}"/>
            
            <!-- ~~~~~~~~~~CONTENT THINGS~~~~~~~~~ -->
			<jsp:include page="${content}"/> 
            <!-- ~~~~~~~~~~CONTENT THINGS~~~~~~~~~ -->
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
