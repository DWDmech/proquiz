package org.home.proquiz.json;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.home.proquiz.entities.Comment;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestJson extends RequestJson {
	@NotNull(message="Error: Comment in CommentRequestJson is null")
	@Valid
	private Comment comment;
	
	@NotNull(message="Error: interviewid in CommentRequestJson is null")
	@Valid
	@JsonProperty("interview_id")
	private Long interviewId;
}
