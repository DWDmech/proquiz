<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<c:url value="/api/user/save" var="saveUserUrl"/>
<c:url value="/login" var="loginUrl"/>

<div class="left-bg"></div>
<div class="col-xl-6 col-lg-12 col-md-12 col-sm-12 left" ng-app="my-app" ng-controller="my-cont">
  <div class="formBox mx-md-auto">
    <h3 class="pl-0 mb-md-5 mx-md-auto">${registryText}</h3>
    <div class="inputBox">
      <label class="labelText" for="email">Name:</label>
      <input ng-model="user.name" type="text" name="name" class="userInput" required>
    </div>
    <div class="inputBox">
      <label class="labelText" for="email">Email:</label>
      <input ng-model="user.email" type="email" name="email" class="userInput" required>
    </div>
    <div class="inputBox">
      <label class="labelText" for="password">Password:</label>
      <input ng-model="user.password" type="password" name="password" class="userInput" required>
    </div>
    <button ng-click="saveUser()" class="form-btn">Click me!</button>
  </div>
  <script type="text/javascript">
    $('.userInput').focus(function() {
      $(this).parent().addClass("focus");
    }).blur(function() {
      if($(this).val() === ''){
        $(this).parent().removeClass("focus");
      }
    });

    angular
    .module("my-app", [])
    .controller("my-cont", function($scope, $http){
		$scope.user = {
                        "email": "",
                        "password":"",
                        "name": ""
                      };
		$scope.saveUser = 
			function() {
				$http({
					url: "${saveUserUrl}",
					method: "POST",
					data: JSON.stringify($scope.user),
					headers: {
						       "Content-Type": "application/json; charset=utf-8"
						     }
				}).then(function(res){
					window.location = "${loginUrl}";
				}, function(err){
					alert(err.data.errMsg);					
				});
			};
    });
  </script>
</div>

<div class="col-xl-6 col-lg-12 col-md-12 col-sm-12 right">
  <img src="img/pages.png" alt="pages">
</div>