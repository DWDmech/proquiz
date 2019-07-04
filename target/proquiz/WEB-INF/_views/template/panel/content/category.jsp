<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<c:url value="/panel/interview" var="interviewUrl"/>
<c:url value="/api/interview/page/first" var="firstPageUrl"/>
<c:url value="/api/interview/page" var="nextPageUrl"/>

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
				<p class="card-title">{{i.id}}- {{i.title}}</p>
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
			url: '${firstPageUrl}/${categoryId}',
			method: "GET",
			headers: {
				   'Content-Type': 'application/json'
				 }
		}).then(function(res){
			res.data.forEach(function(e){
				var ar = e.date;
				e.date = ar[0] + "-" + ar[1] + "-" + ar[2] + " " + ar[3] + ":" + ar[4] + ":" + ar[5];
				
			});
			$scope.interviews = res.data;	
		});
	
		var page = 0;
		var toScroll = angular.element(document).find('.content');
		toScroll.bind('scroll', function(){
			if(this.scrollTop == this.scrollTopMax) {
				$http({
					url: '${nextPageUrl}/${categoryId}/' + page,
					method: "GET",
					headers: {
						   'Content-Type': 'application/json'
						 }
				}).then(function(res){
					page++;
					res.data.forEach(function(e){
						var ar = e.date;
						e.date = ar[0] + "-" + ar[1] + "-" + ar[2] + " " + ar[3] + ":" + ar[4] + ":" + ar[5];
					});
					$scope.interviews = $scope.interviews.concat(res.data);
				});
			}
		});
	});		
</script>