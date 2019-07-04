package org.home.proquiz.json;

import org.home.proquiz.entities.UserAnswer;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAnswerResponseJson extends ResponseJson<UserAnswer> {
	@JsonProperty("can_vote")
	private boolean canVote;
	@JsonProperty("is_correct")
	private boolean isCorrect; 
	@JsonProperty("user_answer")
	private UserAnswer answer;

	private UserAnswerResponseJson(boolean canVote, boolean isCorrect, UserAnswer answer) {
		this.answer = answer;
		this.isCorrect = isCorrect;
		this.canVote = canVote;
	}
	
	public static UserAnswerResponseJson of(boolean canVote, boolean isCorrect, UserAnswer req) {
		return new UserAnswerResponseJson(canVote, isCorrect, req);
	}
}