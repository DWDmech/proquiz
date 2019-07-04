package org.home.proquiz.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user_answers")
@Getter
@Setter
public class UserAnswer implements Serializable {
	private static final long serialVersionUID = 640674829645300434L;

	@Min(value = 1, message="Error: UserAnswer - userId less than zero")
	@NotNull(message="Error: UserAnswer - userId is null")
	@Column(name="user_id")
	private Long userId;
	
	@Min(value = 1, message="Error: UserAnswer - interviewId less than zero")
	@NotNull(message="Error: UserAnswer - interviewId is null")
	@Column(name="interview_id")
	private Long interviewId;
	
	@Min(value = 1, message="Error: UserAnswer - questionId less than zero")
	@NotNull(message="Error: UserAnswer - questionId is null")
	@Column(name="question_id")
	private Long questionId;
	
	@Min(value = 1, message="Error: UserAnswer - answerId less than zero")
	@NotNull(message="Error: UserAnswer - answerId is null")
	@Column(name="answer_id")
	private Long answerId;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Transient
	@JsonProperty("count_answer")
	private int countAnswers;
	
	public UserAnswer() { }

	public UserAnswer(Long userId, Long interviewId, Long questionId, Long answerId) {
		this.userId = userId;
		this.interviewId = interviewId;
		this.questionId = questionId;
		this.answerId = answerId;
	}
}