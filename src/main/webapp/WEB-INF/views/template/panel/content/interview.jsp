<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="getInterviewUrl"   value="/api/interview" />
<c:url var="getUserAnswerUrl"  value="/api/interview/answer" />
<c:url var="saveUserAnswerUrl" value="/api/interview/save/answer"/>
<c:url var="saveCommentUrl"    value="/api/interview/comment/save"/>
<c:url var="getCommentsUrl"    value="/api/interview/comment"/>
<c:url var="checkAnswer"       value="/api/interview/answer/check"/>
<c:url var="eventSubscribeUrl" value="/api/sse/subscribe"/>

<div class="row content" ng-controller="interview-controller">
	<div class="mx-xl-5 my-xl-0 p-xl-4 mt-5 container-fluid">
		<div class="col-12 interview-wrapper bg-white p-xl-4">
			<span class="h3 pb-2 d-block border-bottom">Назва опитування: {{interview.title}}</span>
			<!-- Questions start -->
			<div class="questions">
				<!-- Question start -->
					<div ng-repeat="q in interview.questions" class="question border-bottom py-3" id="q-{{q.id}}">
						<div class="qurstion-input">
							<div class="input-group">
								<div class="input-group-prepend">
									<span class="input-group-text">{{$index + 1}}</span>
								</div>
								<div class="form-control">{{q.text}}</div>
							</div>
						</div>
						<!-- Answer start -->
						<div class="question-answers ml-auto col-10">
								<div ng-repeat="a in q.answers" class="answer">
									<div class="input-group">
										<div class="input-group-prepend">
											<span class="input-group-text">{{$index + 1}}</span>
										</div>
										<div id="a-{{a.id}}" class="answer-text form-control" ng-click="saveUserAnswer(interview.id, q.id, a.id)">{{a.text}}</div>
									</div>
								</div>
						</div>
						<!-- Answer end -->
					</div>
					<!-- Question end -->
			</div>
			<!-- Questions end -->
			<!-- ~~~~~~~~~~~~~ -->
			<!-- Comments Start -->
		      <div ng-controller="comment-controller" class="interview-comments border-top p-3">
		        <span class="comments-title h2 d-block mb-4">Коментарі</span>
		        <div class="comments">
		        	<div ng-repeat="c in comments" class="comment border-bottom mb-4">
						<div class="comment-user-info">
							<img src="img/fail.jpg" alt="img" class="comment-user-img">
							<span class="comment-user-name ml-2">{{interview.isComment ? 'Ім`я анонімне' : c.author.name}}</span><span class="comment-timestamp ml-1">- {{c.date}}</span>
						</div>
						<div class="comment-text pt-xl-3 pl-4">{{c.text}}</div>
					</div>
		        </div>
		        <div class="comment-input row">
		            <textarea rows="1" class="col-10 comment-textarea" placeholder="Введіть коментар" ng-model="commentText"></textarea>
		            <button class="col-2 btn btn-primary btn-comment" ng-click="saveComment(commentText)">Відправлення</button>
		        </div>
		      </div>
		      <!-- Comments End -->
		</div>
	</div>
</div>
<script>
	var url = "${eventSubscribeUrl}/${interviewId}";
	var eventSource = new EventSource(url);
    eventSource.addEventListener("open", function(){
	 console.log("event source connection open");
	});
	    
	eventSource.addEventListener("error", function(err){
	 if (this.readyState == EventSource.CONNECTING) {
	  console.log("Соединение порвалось, пересоединяемся...");
	 } else if (e.readyState == EventSource.CLOSED) {
	  console.log("Ошибка, состояние: " + this.readyState);
	  //alert("Помилка з'єднання, в опитуванні id: " + id + ", зверніть та розгоніть");
	  console.log("Помилка з'єднання, в опитуванні id: " + id);
	  eventSource = new EventSource(url);
	 }
	});
	
	eventSource.addEventListener("COMMENT", function(event){
	    console.log(event);
	    console.log(JSON.parse(event.data));
	    
	    var json = JSON.parse(event.data);
		angular
		  .element(".comments")
		  .append(json.html);
	});
	
	eventSource.addEventListener("RESET", function(event){
	 location.reload();
	});
	    
	window.onbeforeunload = function() {
	    eventSource.close();
	    return;
	};
	
	
	var app = angular.module("my-app", []);
	app.controller("interview-controller", function($scope, $http){
		$http({
			url: "${getInterviewUrl}/${interviewId}",
			method: "GET",
			async:   true,
			headers: { 'Content-Type': 'application/json' }
		}).then(function(res){
			$scope.interview = res.data;	
			$http({
				url: "${getUserAnswerUrl}/${interviewId}",
				method: "GET",
				async:   true,
				headers: {
					   'Content-Type': 'application/json'
					 }
			}).then(function(res){
				console.log(res);
				res.data.forEach(function(e){
					var el = angular.element('.question');
					angular.forEach(el, function(val, key){
						if(val.id == "q-"+e.user_answer.questionId) {
							angular
							.forEach(angular.element(val).find('.answer-text'), 
								function(v, k){
									if(v.id == "a-"+e.user_answer.answerId) {
										if(e.is_correct)
											angular.element(v).addClass("alert-success");
										else 
											angular.element(v).addClass("alert-danger");
									}
								});
						}
					});
				});
			});
		});

		$scope.saveUserAnswer = function(iid, qid, aid) {
				$http({
					url: "${saveUserAnswerUrl}",
					method: "POST",
					async:   true,
					headers: { 'Content-Type': 'application/json' },
					data: {
							userId: ${userId},
							interviewId: iid,
							questionId: qid,
							answerId: aid
						  }
				}).then(function(res){
					console.log(res);
					if(res.data.can_vote) {
						if(res.data.is_correct)
							angular.element('#a-'+aid).addClass("alert-success");
						else 
							angular.element('#a-'+aid).addClass("alert-danger");
					}
				});
		};
	});
	app.controller("comment-controller", function($scope, $http){
	    $scope.commentText = "";
		$scope.comments = [];
	    
		$scope.saveComment = function(text) {
			/*
			$http({
				url: "${saveCommentUrl}",
				method: "POST",
				async:   true,
				headers: { 'Content-Type': 'application/json' },
				data: {
						"comment": {
							"text": text,
							"author": { "id": ${userId} }
						},
						"interview_id": ${interviewId}
					  }
			}).then(function(res){
				$scope.commentText = "";				
			});
			*/
			$http({
				url: "${saveCommentUrl}",
				method: "POST",
				async:   true,
				headers: { 'Content-Type': 'application/json' },
				data: {
						"comment": {
							"text": text,
							"author": { "id": ${userId} }
						},
						"interview_id": ${interviewId}
					  }
			}).then(function successCallback(response) {
			   $scope.commentText = "";
			  }, function errorCallback(errRes) {
			   alert(errRes.data.errMsg);
			  });
		};
		
		  
		$http({
			url: "${getCommentsUrl}/${interviewId}",
			method: "GET",
			async:   true,
			headers: { 'Content-Type': 'application/json' }
		}).then(function(res){
			$scope.comments = res.data;
		});
	});

// JavaScript events listener for comment
</script>