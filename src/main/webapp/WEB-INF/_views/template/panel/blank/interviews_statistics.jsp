<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>

<c:url var="deleteInterviewUrl" value="/interview/delete"/>

<c:forEach items="${interviews}" var="i">
	  <!-- Interview Start -->
	  <div class="interview col-sm-12 col-md-12 com-lg-12 col-xl-8 mx-auto mb-4 mt-4 p-xl-3">
	    <div class="interview-header d-flex">
	      <div class="interview-header-title w-100 py-3 pl-3 pl-xl-0">
	        <span class="h4 d-block mb-3">${i.id} - ${i.title}</span>
	        <p>Повсякденна практика показує, що подальший розвиток різних форм діяльності вимагають визначення та уточнення систем масової участі.</p>
	      </div>
	      <a class="btn" data-toggle="collapse" href="#data-collapse-${i.id}" role="button" aria-expanded="false">
	        <span>&#9660;</span>
	      </a>
	    </div>
	    <!-- Collapse Start -->
	    <div class="collapse" id="data-collapse-${i.id}">
	      <!-- Data Start -->
	      <div class="interview-data border-top col-sm-12 col-md-12 com-lg-12 col-xl-12 p-3" id="i1">
	        <div class="interview-data-info col-sm-12 col-md-12 com-lg-12 col-xl-12 pb-4">
	          <div class="mr-3">
	            <span class="topic-icon pl-2"></span><b>Тема:</b> ${i.title}
	          </div>
	          <div class="mr-3">
	            <span class="author-icon pl-2"></span><b>Автор:</b> ${i.author.name}
	          </div>
	          <div class="mr-3">
	            <span class="date-icon pl-2"></span><b>Дата створення:</b> <javatime:format value="${i.date}" pattern="yyyy-MM-dd HH:mm:ss"/>
	          </div>
	          <div class="mr-3">
	            <span class="people-icon pl-2"></span><b>Проголосувавших:</b> ${i.count}
	          </div>
	          <div class="mr-3">
	            <b class="pl-2">Видалення:</b> <a id="delete-${i.id}" class="text-danger">Видалити це опитування</a>
	            <script type="text/javascript">
	            	$('#delete-${i.id}').click(function(){
	            		var data = 
	            		{
	            			"interview": {
	            				"id": ${i.id},
	            				"author": {
	            					"id": ${i.author.id}
	            				}
	            		    }
	            		};
	            		$.ajax({
	            			"url": "${deleteInterviewUrl}",
            				"contentType": "application/json",
            				"data": JSON.stringify(data),
            				"method": "DELETE",
            				"success": function(){
            					location.reload();
            				},
            				"error": function(er){
                				console.log(er);
            				}
	            		});
	            	});
	            </script>
	          </div>
	        </div>
	
	        <div class="interview-data-questions col-sm-12 col-md-12 com-lg-12 col-xl-12">
		        <c:forEach items="${i.questions}" var="q">
		          <div class="question-1 col-sm-12 col-md-12 com-lg-12 col-xl-12 mb-3">
		            <div class="input-group">
		              <div class="input-group-prepend">
		                <span class="input-group-text">1</span>
		              </div>
		              <input type="text" class="form-control" value="${q.text}" readonly>
		            </div>
					<div class="question-answers col-sm-12 col-md-12 com-lg-12 col-xl-12">
			            <c:forEach items="${q.answers}" var="a">
			              <div class="question-answer col-sm-12 col-md-9 com-lg-9 col-xl-9 ml-auto">
			                <div class="input-group">
			                  <span class="input-group-text">1</span>
			                  <input type="text" class="form-control" value="${a.text}" readonly>
			                  <span class="input-group-text">${a.count}</span>
			                </div>
			              </div>
		          	    </c:forEach>
		            </div>	          	
		          </div>
	            </c:forEach>
	        </div>
	      </div>
	      <!-- Data end -->
	      <!-- Comments Start -->
	      <div class="interview-comments border-top p-3">
	        <span class="comments-title h2 d-block mb-4">Коментарі</span>
	        <c:forEach items="${i.comments}" var="c">
				<div class="comment border-bottom mb-4">
					<div class="comment-user-info">
						<img src="img/fail.jpg" alt="img" class="comment-user-img">
						<span class="comment-user-name ml-2">${c.author.name}</span><span class="comment-timestamp ml-1">- <javatime:format value="${c.date}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
					</div>
					<div class="comment-text pt-xl-3 pl-4">${c.text}</div>
				</div>
			</c:forEach>
	      </div>
	      <!-- Comments End -->
	    </div>
	    <!-- Collapse end -->
	  </div>
	  <!-- Interview end -->
    </c:forEach>