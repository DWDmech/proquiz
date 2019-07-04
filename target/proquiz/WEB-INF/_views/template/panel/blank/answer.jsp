<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="answer">
	<div class="input-group">
		<div class="input-group-prepend">
			<span class="input-group-text">${answerPosition}</span>
		</div>
		<input type="text" class="form-control" placeholder="Відповідь" name="a">
		<div class="input-group-prepend">
			<span class="input-group-text">
				<div class="custom-control custom-checkbox mr-sm-2">
					<input name="cha" type="checkbox" class="custom-control-input" id="cha${answerPosition}"> 
					<label class="custom-control-label" for="cha${answerPosition}">Відповідь</label>
				</div>
			</span>
		</div>
	</div>
</div>
