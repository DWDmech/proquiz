<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="answerUrl"        value="/panel/interview/add/answer"/>
<c:url var="questionUrl"      value="/panel/interview/add/question"/>
<c:url var="successUrl"       value="/panel/category/1"/>
<c:url var="editInterviewUrl" value="/api/interview/edit"/>

<div class="row content">
	<div class="container-fluid create m-xl-5 p-xl-4 mt-5">
	  <div class="col-12 border-bottom h5 py-3">
	    Редагування опитування
	  </div>
	  <div class="col-12">
	    <form class="interview-create">
	      <div class="errors text-danger">
		  </div>
	    
	      <div class="form-row py-4">
	        <div class="form-group col-xl-6">
	          <label for="inputTitle">Title</label>
	          <input type="text" name="title" value="${i.title}" class="form-control" id="inputTitle" required>
	        </div>
	      </div>
	      
	      <div class="form-row">
	        <div class="isActive col-xl-3">
	          <span class="d-block pb-3">Активність опитування</span>
	          <div class="border pt-3 pb-2 px-3">
	          	<c:choose>
				    <c:when test="${i.active}">
				    	<div class="custom-control-inline">
			              <input type="radio" id="activeTrue" name="active" value="true" checked>
			              <label for="activeTrue">Активне</label>
			            </div>
			            <div class="custom-control-inline">
			              <input type="radio" id="activeFalse" name="active" value="false">
			              <label for="activeFalse">Не активне</label>
			            </div>
				    </c:when>    
				    <c:otherwise>
				    	<div class="custom-control-inline">
			              <input type="radio" id="activeTrue" name="active" value="true">
			              <label for="activeTrue">Активне</label>
			            </div>
			            <div class="custom-control-inline">
			              <input type="radio" id="activeFalse" name="active" value="false" checked>
			              <label for="activeFalse">Не активне</label>
			            </div>
				    </c:otherwise>
				</c:choose>
	          </div>
	        </div>
	      </div>
	      
	      <div class="form-row border-top mt-4">
		   <div class="col-12 question-wrapper bg-white p-xl-4">
			  <!-- Questions start -->
			  <div class="questions">
			  	<% int q = 1, a = 1;  %>
			  	<c:forEach items="${i.questions}" var="q">
			  		<!-- Question start -->
					<div class="question pb-4">
						<div class="qurstion-input">
							<div class="input-group">
								<div class="input-group-prepend">
									<span class="input-group-text"><%= q %></span>
								</div>
								<input type="text" name="q" placeholder="Запитання" value="${q.text}" class="form-control">
								<input type="text" name="qid" value="${q.id}" style="display: none">
							</div>
						</div>
						<div class="question-answers ml-auto col-10">
							<% a=1; %>
							<c:forEach items="${q.answers}" var="a">
								<div class="answer">
									<div class="input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"><%= a %></span>
										</div>
										<input type="text" value="${a.text}" name="a" class="form-control" placeholder="Відповідь">
										<input type="text" value="${a.id}" name="aid" style="display: none">
									</div>
								</div>
								<% a++; %>
							</c:forEach>
						</div>
					</div>
					<!-- Question end -->
					<% q++; %>
			  	</c:forEach>
			  </div>
			  <!-- Questions end -->
			</div>
	      </div>
	      <input type="submit" value="Редагувати" class="btn btn-primary mt-4">
	    </form>
	    <script type="text/javascript">
	    	angular.module('my-app', []);
	    	$('.interview-create').submit(function(e){
	    		var questions = [];
	    		$('.question').each(function(k, q){
	    			var answers = [];
	    			var tmpAr = $(q).find('.answer');
	    			$(tmpAr).each(function(k, a){
		    			var obj;
		    			if($(a).find('input[name="aid"]').val() != "") {
		    				obj = {
			    	    			"id": $(a).find('input[name="aid"]').val(),
			    					"text": $(a).find('input[name="a"]').val()
			    				  };
				    	} else {
				    		obj = {
			    					"text": $(a).find('input[name="a"]').val()
			    				  };
					    }
	    				
	    				answers.push(obj);
	    			});

	    			var que;
	    			if($(q).find('input[name="qid"]').val() != "") {
	    				que = {
	    	    	    		"id": $(q).find('input[name="qid"]').val(),
	    	        			"text": $(q).find('input[name="q"]').val(),
	    	    				"answers": answers
	    	        		  };
			    	} else {
			    		que = {
			        			"text": $(q).find('input[name="q"]').val(),
			    				"answers": answers
			        		  };
					}
	    			
	    			questions.push(que);
	    		});
	    		
	    		var data = 
	    		{
	    			"id": ${i.id},
	   				"date":  '${interviewDate}', 
	   				"isAnonymous": ${i.isAnonymous},
	   				"isComment": ${i.isComment},
	   				"active": $("input[name='active']:checked").val(),
	   				"title": $('#inputTitle').val(),
	   				"questions": questions,
	   				"author": {"id": ${userId}}
	    		};

	    		console.log(JSON.stringify(data));
				console.log(data);
	    		
				$.ajax({
	    			url: "${editInterviewUrl}",
	    			method: "PUT",
	    			dataType: 'json',
	    			data: JSON.stringify(data),
	    			contentType: "application/json; charset=utf-8",
	    			success: function(res) {
	    				location = "${successUrl}";
	    			},
	    			error: function(er) {
	    				console.log("~~~~~~~~~~~ ERROR ~~~~~~~~~~~")
	    				console.log(er);
	    				if(er.status == 200) {
	    					location = "${successUrl}";
	    				}
	    				
	    				if(er.status == 400) {
	    					//var err = er.responseJSON.errors;
	    					//var html = "";
	    					//if(err.questions) html = html + err.questions + "<br>";
	    					//if(err.answers  ) html = html + err.answers + "<br>";
	    					//if(err.title    ) html = html + err.title + "<br>";
	    					//if(err.text     ) html = html + err.text + "<br>";
	    					
	    					$('.errors').html(er.responseJSON.data);
	    					return false;
	    				}	
	    			}
	    		});
	    		e.preventDefault();
	    	});
	    </script>
	  </div>
	</div>
</div>