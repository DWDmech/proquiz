<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="questionUrl" value="/panel/interview/add/question"/>
<c:url var="successUrl" value="/panel/category/1"/>
<c:url var="addInterviewUrl" value="/api/interview/save"/>

<div class="row content">
	<div class="container-fluid create m-xl-5 p-xl-4 mt-5">
	  <div class="col-12 border-bottom h5 py-3">
	    Створити опитування
	  </div>
	  <div class="col-12">
	    <form class="interview-create">
	      <div class="errors text-danger">
		  </div>
	    
	      <div class="form-row py-4">
	        <div class="form-group col-xl-3">
	          <label for="inputName">Ім'я</label>
	          <input type="text" name="name" value="${user.name}" class="form-control" id="inputName" required readonly>
	        </div>
	        <div class="form-group col-xl-3">
	          <label for="inputEmail">Email</label>
	          <input type="email" name="email" value="${user.email}" class="form-control" id="inputEmail" required readonly>
	        </div>
	        <div class="form-group col-xl-3">
	          <label for="inputTitle">Назва опитування</label>
	          <input type="text" name="title" value="" class="form-control" id="inputTitle" required>
	        </div>
	        <div class="form-group col-xl-3">
	          <label for="inputCategory">Категорія опитування</label>
	          <select class="custom-select" name="catego" id="inputCategory" required>
	            <c:forEach items="${categories}" var="i">
		            <option value="${i.id}">${i.title}</option>
	            </c:forEach>
	          </select>
	        </div>
	      </div>
	      
	      <div class="form-row">
	        <div class="isComment col-xl-3">
	          <span class="d-block pb-3">Коментарі</span>
	          <div class="border pt-3 pb-2 px-3">
	            <div class="custom-control-inline">
	              <input type="radio" id="commentTrue" name="isComment" value="true" checked>
	              <label for="commentTrue">Анонімне</label>
	            </div>
	            <div class="custom-control-inline">
	              <input type="radio" id="commentFalse" name="isComment" value="false">
	              <label for="commentFalse">Не анонімне</label>
	            </div>
	          </div>
	        </div>
	        <div class="isAnonymous col-xl-3">
	          <span class="d-block pb-3">Голосування</span>
	          <div class="border pt-3 pb-2 px-3">
	            <div class="custom-control-inline">
	              <input type="radio" id="anonymousTrue" name="isAnonymous" value="true" checked>
	              <label for="anonymousTrue">Анонімне</label>
	            </div>
	            <div class="custom-control-inline">
	              <input type="radio" id="anonymousFalse" name="isAnonymous" value="false">
	              <label for="anonymousFalse">Не анонімне</label>
	            </div>
	          </div>
	        </div>
	      </div>
	      
	      <div class="form-row border-top mt-4">
		   <div class="col-12 question-wrapper bg-white p-xl-4">
			  <!-- Questions start -->
			  <div class="questions-btn pb-3">
			    <button id="btn-q-a" type="button" name="question-add" class="btn btn-primary">Додати запитання</button>
			    <button id="btn-q-r" type="button" name="question-remove" class="btn btn-primary">Видалити запитання</button>
			  </div>
			  <script type="text/javascript">
				var q = 1;
				$("#btn-q-a").click(function(){
					if(q <= 30) {
						$.ajax({
							"url":"${questionUrl}",
							"method": "GET",
							"dataType":"html",
							"data": "q=" + q,
							"success": function(html) {
								$('.questions').append(html);
								q++;
							}
						});
					}
				});
				$("#btn-q-r").click(function(){
					if(q > 1) {
						var count = $('.question').length - 1;
						$('.question')[count].remove();
						q--;
					}
				});
			  </script>
			  <div class="questions">
			  </div>
			  <!-- Questions end -->
			</div>
	      </div>
	      <input type="submit" value="Створити" class="btn btn-primary mt-4">
	    </form>
	    <script type="text/javascript">
	    	angular.module('my-app', []);
	    	$('.interview-create').submit(function(e){
	    		var questions = [];
	    		$('.question').each(function(k, q){
	    			var answers = [];
	    			var tmpAr = $(q).find('.answer');
	    			$(tmpAr).each(function(k, a){
	    				var obj = {
	    					"text": $(a).find('input[name="a"]').val(),
    						"correct": $(a).find('input[name="cha"]').prop("checked")
	    				};
	    				answers.push(obj);
	    			});
	    			
	    			var que = {
	        			"text": $(q).find('input[name="q"]').val(),
	    				"answers": answers
	        		}
	    			questions.push(que);
	    		});
	    		
	    		var data = 
	    		{
	    			"interview": {
	    				"title": $('#inputTitle').val(),
	    				"questions": questions,
	    				"isComment": $('input[name="isComment"]:checked').val(),
	    				"isAnonymous": $('input[name="isAnonymous"]:checked').val(),
	    				"author": {"id": ${user.id}}
	    			}, 
	    			"categoryId": $('#inputCategory').val()
	    		};
	    		
	    		$.ajax({
	    			url: "${addInterviewUrl}",
	    			method: "POST",
	    			dataType: 'json',
	    			data: JSON.stringify(data),
	    			contentType: "application/json; charset=utf-8",
	    			success: function(res) {
	    				location = "${successUrl}";
	    			},
	    			error: function(er){
	    				console.log("~~~~~~~~~~~ ERROR ~~~~~~~~~~~")
	    				console.log(er);
	    				if(er.status == 200) {
	    					location = "${successUrl}";
	    				}
	    				
	    				if(er.status == 400) {
	    					$('.errors').html(er.responseJSON.errMsg);
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