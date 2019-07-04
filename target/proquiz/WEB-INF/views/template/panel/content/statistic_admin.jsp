<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<c:url var="statisicsPageURL"        value="/api/admin"/>
<c:url var="deleteURL"               value="/api/interview/delete/"/>
<c:url var="blockURL"                value="/api/admin/block/"/>
<c:url var="editUrl"                 value="/panel/interview/edit"/>

<div class="row content" ng-controller="statistics-controller">
	<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
	    <h1 align="center" class="content-title">Статистика для адміна</h1>
	</div>
	
	<!-- Interviews Start -->
	<div class="interviews col-sm-12 col-md-12 com-lg-12 col-xl-12 py-xl-4">
	    <div ng-repeat="i in interviews">
		  <!-- Interview Start -->
		  <div id="i-{{i.id}}" class="interview col-sm-12 col-md-12 com-lg-12 col-xl-8 mx-auto mb-4 mt-4 p-xl-3">
		    <div class="interview-header d-flex">
		      <div class="interview-header-title w-100 py-3 pl-3 pl-xl-0">
		        <span class="h4 d-block mb-3">{{i.title}}</span>
		        <b>Автор:</b> {{i.author.name}} - {{i.date}}
		      </div>
		      <a class="btn" ng-click="subscribe(i.id)" data-toggle="collapse" href="#data-collapse-{{i.id}}" role="button" aria-expanded="false">
		        <span>&#9660;</span>
		      </a>
		    </div>
		    <!-- Collapse Start -->
		    <div class="collapse" id="data-collapse-{{i.id}}">
		      <!-- Data Start -->
		      <div class="interview-data border-top col-sm-12 col-md-12 com-lg-12 col-xl-12 p-3">
		        <div class="interview-data-info col-sm-12 col-md-12 com-lg-12 col-xl-12 pb-4">
		          <div class="mr-3">
		            <span class="topic-icon pl-2"></span><b>Тема:</b> {{i.title}}
		          </div>
		          <div class="mr-3">
		            <span class="author-icon pl-2"></span><b>Автор:</b> {{i.author.name}}
		          </div>
		          <div class="mr-3">
		            <span class="date-icon pl-2"></span><b>Дата створення:</b> {{i.date}}
		          </div>
		          <div class="mr-3">
		            <b class="pl-2">Видалення:</b> <span ng-click="deleteInterview(i.id)" class="text-danger" style="cursor: pointer;">Видалити це опитування</span>
		          </div>
		          <div class="mr-3">
		            <b class="pl-2">Блокування:</b> <span ng-click="blockInterview(i.id)" class="text-danger" style="cursor: pointer;">Блокувати це опитування</span>
		          </div>
		        </div>
		
		        <div class="interview-data-questions col-sm-12 col-md-12 com-lg-12 col-xl-12">
			        <div ng-repeat="q in i.questions">
			          <div id="q-{{q.id}}" class="col-sm-12 col-md-12 com-lg-12 col-xl-12 mb-3">
			            <div class="input-group">
			              <div class="input-group-prepend">
			                <span class="input-group-text">{{$index + 1}}</span>
			              </div>
			              <div class="form-control">{{q.text}}</div>
			            </div>
						<div class="col-sm-12 col-md-12 com-lg-12 col-xl-12">
				            <div ng-repeat="a in q.answers">
				              <div id="qa-{{a.id}}" class="col-sm-12 col-md-9 com-lg-9 col-xl-9 ml-auto">
				                <div class="input-group">
				                  <span class="input-group-text">{{$index + 1}}</span>
				                  <span class="form-control">{{a.text}}</span>
				                </div>
				              </div>
				            </div>
			            </div>	          	
			          </div>
			        </div>
		        </div>
		      </div>
		      <!-- Data end -->
		      <!-- Comments Start -->
		      <div class="interview-comments border-top p-3">
		        <span class="comments-title h2 d-block mb-4">Коментарі</span>
		        <div ng-repeat="c in i.comments">
					<div class="comment border-bottom mb-4">
						<div class="comment-user-info">
							<img src="img/fail.jpg" alt="img" class="comment-user-img">
							<span class="comment-user-name ml-2">{{i.isComment ? 'Ім`я анонімне' : c.author.name}}</span><span class="comment-timestamp ml-1">- {{i.date}}</span>
						</div>
						<div class="comment-text pt-xl-3 pl-4">{{c.text}}</div>
					</div>
				</div>
		      </div>
		      <!-- Comments End -->
		    </div>
		    <!-- Collapse end -->
		  </div>
		  <!-- Interview end -->
		</div>
	</div>
</div>
<script>
	var app = angular.module("my-app", []);
	app.controller("statistics-controller", function($scope, $http){
		$scope.deleteInterview = function(id){
			$http({
				method: 'DELETE',
				url: '${deleteURL}'+id
			}).then(function(){
				location.reload();
			});
		};
		
		$scope.blockInterview = function(id) {
			$http({
				method: 'PUT',
				url: '${blockURL}'+id
			});
		};

		var page = 0;
		$http({
			url: '${statisicsPageURL}/' + page,
			method: "GET",
			headers: {
				   'Content-Type': 'application/json'
				 }
		}).then(function(res){
			$scope.interviews = res.data;	
			page++;
		});
		
		angular
		.element(document)
		.find('.content')
		.bind('scroll', function(){
			if(this.offsetHeight + this.scrollTop == this.scrollHeight) {
				$http({
					url: '${statisicsPageURL}/' + page,
					method: "GET",
					headers: { 'Content-Type': 'application/json' }
				}).then(function(res){
					page++;
					$scope.interviews = $scope.interviews.concat(res.data);
				});
			}
		});
	});
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
</script>
<!-- Interviews end -->