<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<c:url value="/panel/interview" var="interviewUrl"/>
<c:url value="/api/interview/page" var="pageUrl"/>

<div class="row content" ng-controller="interviews-controller">
	<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
	    <h1 align="center" class="content-title">Опитування</h1>
	</div>
	
	<div ng-repeat="i in interviews"
		class="col-xl-4 col-lg-4 col-md-4 col-sm-12 r">
		<div class="card">
			<a href="${interviewUrl}/{{i.id}}"><img class="card-img-top" src="img/card-img-0.png"
				alt="Card image cap"></a>
			<div class="card-body">
				<p class="card-title">{{i.title}}</p>
				<p class="card-text"><b>Автор: </b>{{i.author.name}}</p>
				<p class="card-text">{{i.date}}</p>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
 angular.module('my-app', [])
	.controller('interviews-controller', function($scope, $http){
		$http({
			url: '${pageUrl}/${categoryId}/0',
			method: "GET",
			async:   true,
			headers: {
				   'Content-Type': 'application/json'
				 }
		}).then(function(res){
			$scope.interviews = res.data;	
		});
	
		var page = 1;
		var toScroll = angular.element(document).find('.content');
		toScroll.bind('scroll', function(){
			console.log("scroll");
			var element = document.getElementsByClassName('content')[0];
			
			if(element.scrollHeight - element.scrollTop === element.clientHeight) {
				$http({
					url: '${pageUrl}/${categoryId}/' + page,
					method: "GET",
					async:   true,
					headers: {
						   'Content-Type': 'application/json'
						 }
				}).then(function(res){
					page++;
					$scope.interviews = $scope.interviews.concat(res.data);
				});
			}
		});

		document.body.onscroll = function() {
			var style = getComputedStyle(document.getElementsByClassName('content')[0], "");
			if(style.overflow === "hidden") {
				if(window.scrollY === document.body.scrollHeight - window.innerHeight) {
					$http({
						url: '${pageUrl}/${categoryId}/' + page,
						method: "GET",
						async:   true,
						headers: {
							   'Content-Type': 'application/json'
							 }
					}).then(function(res){
						page++;
						$scope.interviews = $scope.interviews.concat(res.data);
					});
				}
			}
		};

	});		
</script>