<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<c:url var="statisicsOfInterviewURL" value="/api/interview/page/statistics"/>
<c:url var="statisticsSubscribeUrl"  value="/api/sse/subscribe/statistics"/>
<c:url var="deleteURL"               value="/api/interview/delete"/>
<c:url var="editUrl"                 value="/panel/interview/edit"/>
<c:url var="deleteAnswersUrl"        value="/api/interview/delete/answers"/>
<c:url var="userVoteList"        	 value="/panel/interview"/>


<div class="row content" ng-controller="statistics-controller">
	<div class="col-xl-12 col-lg-12 col-md-12 col-sm-12">
	    <h1 align="center" class="content-title">Статистика</h1>
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
		            <span class="people-icon pl-2"></span><b>Проголосувавших:</b> <span id="people-count">{{i.count}}</span>
		          </div>
		          <div class="mr-3">
		            <b class="pl-2">Редагування:</b> <a href="${editUrl}/{{i.id}}" style="cursor: pointer;">Посилання для редагування</a>
		          </div>
		          <div class="mr-3">
		            <b class="pl-2">Відповіді:</b> <span ng-click="resetAnswers(i.id)" class="text-danger" style="cursor: pointer;">Видалити всі відповіді</span>
		          </div>
		          <hr/>
		          <div class="mr-3">
		            <b class="pl-2">Видалення:</b> <span ng-click="deleteInterview(i.id)" class="text-danger" style="cursor: pointer;">Видалити це опитування</span>
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
				                  <span class="form-control answer-text" ng-mouseover="onHover(a.id, i.id)">{{a.text}}</span>
				                  <span class="qa-count input-group-text" ng-bind="a.count"></span>
				                  <div class="descr"></div>
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
	var eventSource;
	var app = angular.module("my-app", []);
	
	app.controller("statistics-controller", function($scope, $http){
		$scope.deleteInterview = function(id){
			$http({
				method: 'DELETE',
				async:   true,
				url: '${deleteURL}/'+id
			}).then(function(){
				console.log("event source connecting closing...");
				eventSource.close();
				console.log("event source connecting closed");
				location.reload();
			});
		};
		$scope.resetAnswers = function(id) {
			$http({
				method: 'DELETE',
				async:   true,
				url: '${deleteAnswersUrl}/'+id
			});
			//.then(function(){
			//	location.reload();
			//});
		};
		
		$scope.userList = [];
		$scope.onHover = function(answer_id, interview_id) {
			angular
				.element("#qa-"+answer_id)
				.addClass("poster");
				
			$http({
				method: 'GET',
				async:   true,
				url: "${userVoteList}/" + interview_id + "/users/" + answer_id
			}).then(function(res){
				angular.element("#qa-"+answer_id).find(".descr").html(res.data);
			});
		}
		
		window.onbeforeunload = function() {
			eventSource.close();
		};

		function closeOther(id) {
			var el = angular
						.element(".collapse");
			angular.forEach(el, function(value, key) {
				if(value.id != "data-collapse-"+id) {
					angular
						.element(value)
						.removeClass("show");	
				}
			});
			if(eventSource) {
				console.log("event source connecting closing...");
				eventSource.close();
				console.log("event source connecting closed");
			}
		}

		$scope.subscribe = function(id) {
			closeOther(id);
			var el = document.getElementById("data-collapse-"+id);
			
			if(!angular.element(el).hasClass('show')) {
				var url = '${statisticsSubscribeUrl}/'+id;
				eventSource = new EventSource(url);
				
				eventSource.addEventListener("VOTE", function(event){
				 console.log(event);
				 var json = JSON.parse(event.data);
				 var el = angular
							.element("#i-"+json.vote.interviewId)
							.find("#q-"+json.vote.questionId)
							.find("#qa-"+json.vote.answerId)
							.find(".qa-count");
				 el[0].innerHTML = json.vote.count_answer;
				});
				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				eventSource.addEventListener("COMMENT", function(event){
				 var json = JSON.parse(event.data);
				 
				 angular.forEach($scope.interviews, function(v, k){
				  if(v.id == json.interview_id) {
				   v.comments = json.comment;
				   var el = angular
							.element("#i-"+json.interview_id)
							.find(".interview-comments")
							.append(json.html);
				  }
				 });
				});
				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				eventSource.addEventListener("RESET", function(event){
				 var json = JSON.parse(event.data);
				 var el = angular
							.element("#i-"+json.interview_id)
							.find(".qa-count");
				 angular.forEach(el, function(value, key) {
				  value.innerHTML = 0;
				 });
				 document.getElementById("people-count").innerHTML = 0;
				});
				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				eventSource.onopen = function() {
					console.log("event source connection open");
				};
				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				eventSource.onerror = function(err) {
					if (this.readyState == EventSource.CONNECTING) {
					    console.log("Соединение порвалось, пересоединяемся...");
					  } else {
					    console.log("Ошибка, состояние: " + this.readyState);
					    console.log("Помилка з'єднання, в опитуванні id: " + id);
					  }
				};
				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			} else {
				console.log("event source connecting closing...");
				eventSource.close();
				console.log("event source connecting closed");
			}
		}
		
		
		
		var page = 0;
		$http({
			url: '${statisicsOfInterviewURL}/' + page,
			method: "GET",
			async:   true,
			headers: { 'Content-Type': 'application/json' }
		}).then(function(res){
			$scope.interviews = res.data;
			console.log($scope.interviews);
			page++;
		});
		
		angular
		.element(document)
		.find('.content')
		.bind('scroll', function(){
			if(this.offsetHeight + this.scrollTop == this.scrollHeight) {
				$http({
					url: '${statisicsOfInterviewURL}/' + page,
					method: "GET",
					async:   true,
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