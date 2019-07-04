<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="answerUrl" value="/panel/interview/add/answer"/>

<!-- Question start -->
<div class="question pb-4">
	<div class="qurstion-input">
		<div class="input-group">
			<div class="input-group-prepend">
				<span class="input-group-text">${questionPosition}</span>
			</div>
			<input type="text" name="q" placeholder="Запитання" value="" class="form-control" >
		</div>
	</div>
	<div class="question-answers ml-auto col-10">
		
		<div class="answer-btn">
			<button id="btn-a-a-${questionPosition}" type="button" name="answer-add" class="btn btn-primary">Додати відповідь</button>
			<button id="btn-a-r-${questionPosition}" type="button" name="answer-remove" class="btn btn-primary">Видалити відповідь</button>
		</div>
		<script type="text/javascript">
		var a${questionPosition} = 1;
		$("#btn-a-a-${questionPosition}").click(function(){
			if(a${questionPosition} <= 5)
			$.ajax({
				"url":"${answerUrl}",
				"method": "GET",
				"dataType":"html",
				"data": "a=" + a${questionPosition},
				"success": function(html) {
					$("#btn-a-a-${questionPosition}").parent().before(html);
					a${questionPosition}++;
				}
			});
		});
		$("#btn-a-r-${questionPosition}").click(function(){
			if(a${questionPosition} > 1) {
				var count = $(this).parent().parent().find('.answer').length - 1;
				$(this).parent().parent().find('.answer')[count].remove();
				a${questionPosition}--;	
			}
		});
		</script>
	</div>
</div>
<!-- Question end -->